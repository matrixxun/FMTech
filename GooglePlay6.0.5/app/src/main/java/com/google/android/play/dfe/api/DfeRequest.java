package com.google.android.play.dfe.api;

import android.accounts.Account;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.android.finsky.protos.PlayResponse.PlayResponseWrapper;
import com.google.android.finsky.protos.PreFetch;
import com.google.android.finsky.protos.ServerCommands;
import com.google.android.finsky.protos.ServerMetadata;
import com.google.android.play.utils.PlayCommonLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.MessageNanoPrinter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public final class DfeRequest<T extends MessageNano>
  extends Request<PlayResponse.PlayResponseWrapper>
{
  private static final boolean DEBUG = PlayCommonLog.DEBUG;
  private static final boolean PROTO_DEBUG = Log.isLoggable("DfeProto", 2);
  private boolean mAllowMultipleResponses = false;
  private final PlayDfeApiContext mApiContext;
  private boolean mAvoidBulkCancel = false;
  private StringBuilder mCacheHeadersBuilder;
  private Map<String, String> mExtraHeaders;
  private Response.Listener<T> mListener;
  private final Class<T> mResponseClass;
  private boolean mResponseDelivered;
  private DfeResponseVerifier mResponseVerifier;
  private long mServerLatencyMs = -1L;
  
  public DfeRequest(String paramString, PlayDfeApiContext paramPlayDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
  {
    this(paramString, paramPlayDfeApiContext, paramClass, paramListener, paramErrorListener, (byte)0);
  }
  
  private DfeRequest(String paramString, PlayDfeApiContext paramPlayDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener, byte paramByte)
  {
    super(0, Uri.withAppendedPath(PlayDfeApi.BASE_URI, paramString).toString(), paramErrorListener);
    if (TextUtils.isEmpty(paramString)) {
      PlayCommonLog.wtf("Empty DFE URL", new Object[0]);
    }
    if (!((Boolean)PlayG.skipAllCaches.get()).booleanValue()) {}
    for (boolean bool = true;; bool = false)
    {
      this.mShouldCache = bool;
      this.mRetryPolicy = new DfeRetryPolicy(paramPlayDfeApiContext);
      this.mApiContext = paramPlayDfeApiContext;
      this.mListener = paramListener;
      this.mResponseClass = paramClass;
      return;
    }
  }
  
  private static void copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[4096];
    try
    {
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        paramOutputStream.write(arrayOfByte, 0, i);
      }
    }
    finally
    {
      paramInputStream.close();
    }
  }
  
  private Response<PlayResponse.PlayResponseWrapper> handleServerCommands(PlayResponse.PlayResponseWrapper paramPlayResponseWrapper)
  {
    if (paramPlayResponseWrapper.commands == null) {}
    ServerCommands localServerCommands;
    do
    {
      return null;
      localServerCommands = paramPlayResponseWrapper.commands;
      if (localServerCommands.hasLogErrorStacktrace)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localServerCommands.logErrorStacktrace;
        PlayCommonLog.d("%s", arrayOfObject);
      }
      if (localServerCommands.clearCache) {
        this.mApiContext.mCache.clear();
      }
    } while (!localServerCommands.hasDisplayErrorMessage);
    return Response.error(new DfeServerError(localServerCommands.displayErrorMessage));
  }
  
  private String makeCacheKey(String paramString)
  {
    StringBuilder localStringBuilder1 = new StringBuilder(256).append(paramString).append("/account=");
    Account localAccount = this.mApiContext.mAuthenticator.mAccount;
    if (localAccount == null) {}
    for (String str = null;; str = localAccount.name)
    {
      StringBuilder localStringBuilder2 = localStringBuilder1.append(str);
      if (this.mCacheHeadersBuilder != null) {
        localStringBuilder2.append(this.mCacheHeadersBuilder);
      }
      return localStringBuilder2.toString();
    }
  }
  
  private static Cache.Entry parseCacheHeaders(NetworkResponse paramNetworkResponse)
  {
    localEntry = HttpHeaderParser.parseCacheHeaders(paramNetworkResponse);
    if (localEntry == null) {
      return null;
    }
    long l = System.currentTimeMillis();
    try
    {
      String str1 = (String)paramNetworkResponse.headers.get("X-DFE-Soft-TTL");
      if (str1 != null) {
        localEntry.softTtl = (l + Long.parseLong(str1));
      }
      String str2 = (String)paramNetworkResponse.headers.get("X-DFE-Hard-TTL");
      if (str2 != null) {
        localEntry.ttl = (l + Long.parseLong(str2));
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramNetworkResponse.headers;
        PlayCommonLog.d("Invalid TTL: %s", arrayOfObject);
        localEntry.softTtl = 0L;
        localEntry.ttl = 0L;
      }
    }
    localEntry.ttl = Math.max(localEntry.ttl, localEntry.softTtl);
    return localEntry;
  }
  
  private PlayResponse.PlayResponseWrapper parseWrapperAndVerifySignature(NetworkResponse paramNetworkResponse, boolean paramBoolean)
  {
    PlayResponse.PlayResponseWrapper localPlayResponseWrapper;
    try
    {
      String str = (String)paramNetworkResponse.headers.get("X-DFE-Signature-Response");
      if (paramBoolean)
      {
        byte[] arrayOfByte = readBytes(new GZIPInputStream(new ByteArrayInputStream(paramNetworkResponse.data)));
        localPlayResponseWrapper = PlayResponse.PlayResponseWrapper.parseFrom(arrayOfByte);
        if (this.mResponseVerifier != null)
        {
          this.mResponseVerifier.verify(arrayOfByte, str);
          return localPlayResponseWrapper;
        }
      }
      else
      {
        localPlayResponseWrapper = PlayResponse.PlayResponseWrapper.parseFrom(paramNetworkResponse.data);
        if (this.mResponseVerifier != null)
        {
          this.mResponseVerifier.verify(paramNetworkResponse.data, str);
          addMarker("signature-verification-succeeded");
          return localPlayResponseWrapper;
        }
      }
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      while (!paramBoolean) {
        paramBoolean = true;
      }
      PlayCommonLog.d("Cannot parse response as PlayResponseWrapper proto.", new Object[0]);
      return null;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        PlayCommonLog.w("IOException while manually unzipping request.", new Object[0]);
      }
    }
    catch (DfeResponseVerifier.DfeResponseVerifierException localDfeResponseVerifierException)
    {
      for (;;)
      {
        addMarker("signature-verification-failed");
        PlayCommonLog.e("Could not verify request: %s, exception %s", new Object[] { this, localDfeResponseVerifierException });
      }
    }
    return localPlayResponseWrapper;
  }
  
  private static byte[] readBytes(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      copy(paramInputStream, localByteArrayOutputStream);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      return arrayOfByte;
    }
    finally
    {
      localByteArrayOutputStream.close();
    }
  }
  
  private void stripForCache(PlayResponse.PlayResponseWrapper paramPlayResponseWrapper, Cache.Entry paramEntry)
  {
    if ((paramPlayResponseWrapper.preFetch.length <= 0) && (paramPlayResponseWrapper.commands == null)) {
      return;
    }
    Cache localCache = this.mApiContext.mCache;
    long l = System.currentTimeMillis();
    for (PreFetch localPreFetch : paramPlayResponseWrapper.preFetch)
    {
      Cache.Entry localEntry = new Cache.Entry();
      localEntry.data = localPreFetch.response;
      localEntry.etag = localPreFetch.etag;
      localEntry.serverDate = paramEntry.serverDate;
      localEntry.ttl = (l + localPreFetch.ttl);
      localEntry.softTtl = (l + localPreFetch.softTtl);
      localCache.put(makeCacheKey(Uri.withAppendedPath(PlayDfeApi.BASE_URI, localPreFetch.url).toString()), localEntry);
    }
    paramPlayResponseWrapper.preFetch = PreFetch.emptyArray();
    paramPlayResponseWrapper.commands = null;
    paramEntry.data = MessageNano.toByteArray(paramPlayResponseWrapper);
  }
  
  public final void deliverError(VolleyError paramVolleyError)
  {
    if ((paramVolleyError instanceof AuthFailureError)) {
      this.mApiContext.invalidateAuthToken();
    }
    if (!this.mResponseDelivered)
    {
      super.deliverError(paramVolleyError);
      return;
    }
    PlayCommonLog.d("Not delivering error response for request=[%s], error=[%s] because response already delivered.", new Object[] { this, paramVolleyError });
  }
  
  public final String getCacheKey()
  {
    return makeCacheKey(super.getUrl());
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    Map localMap = this.mApiContext.getHeaders();
    if (this.mExtraHeaders != null) {
      localMap.putAll(this.mExtraHeaders);
    }
    if (this.mResponseVerifier != null) {}
    try
    {
      localMap.put("X-DFE-Signature-Request", this.mResponseVerifier.getSignatureRequest());
      RetryPolicy localRetryPolicy = this.mRetryPolicy;
      String str = "timeoutMs=" + localRetryPolicy.getCurrentTimeout();
      int i = localRetryPolicy.getCurrentRetryCount();
      if (i > 0) {
        str = str + "; retryAttempt=" + i;
      }
      localMap.put("X-DFE-Request-Params", str);
      return localMap;
    }
    catch (DfeResponseVerifier.DfeResponseVerifierException localDfeResponseVerifierException)
    {
      for (;;)
      {
        PlayCommonLog.d("Couldn't create signature request: %s", new Object[] { localDfeResponseVerifierException });
        cancel();
      }
    }
  }
  
  public final String getUrl()
  {
    char c1 = '&';
    String str = super.getUrl();
    int i;
    if (!TextUtils.isEmpty((CharSequence)PlayG.ipCountryOverride.get()))
    {
      i = 1;
      if (TextUtils.isEmpty((CharSequence)PlayG.mccMncOverride.get())) {
        break label116;
      }
    }
    boolean bool1;
    boolean bool2;
    boolean bool3;
    label116:
    for (int j = 1;; j = 0)
    {
      bool1 = ((Boolean)PlayG.skipAllCaches.get()).booleanValue();
      bool2 = ((Boolean)PlayG.showStagingData.get()).booleanValue();
      bool3 = ((Boolean)PlayG.prexDisabled.get()).booleanValue();
      if ((i != 0) || (j != 0) || (bool1) || (bool2) || (bool3)) {
        break label122;
      }
      return str;
      i = 0;
      break;
    }
    label122:
    StringBuilder localStringBuilder = new StringBuilder(str);
    int k;
    char c5;
    label157:
    char c4;
    label205:
    char c3;
    label253:
    char c2;
    if (str.indexOf('?') != -1)
    {
      k = 1;
      if (i != 0)
      {
        if (k == 0) {
          break label344;
        }
        c5 = c1;
        localStringBuilder.append(c5);
        k = 1;
        localStringBuilder.append("ipCountryOverride=");
        localStringBuilder.append((String)PlayG.ipCountryOverride.get());
      }
      if (j != 0)
      {
        if (k == 0) {
          break label351;
        }
        c4 = c1;
        localStringBuilder.append(c4);
        k = 1;
        localStringBuilder.append("mccmncOverride=");
        localStringBuilder.append((String)PlayG.mccMncOverride.get());
      }
      if (bool1)
      {
        if (k == 0) {
          break label358;
        }
        c3 = c1;
        localStringBuilder.append(c3);
        k = 1;
        localStringBuilder.append("skipCache=true");
      }
      if (bool2)
      {
        if (k == 0) {
          break label365;
        }
        c2 = c1;
        label286:
        localStringBuilder.append(c2);
        k = 1;
        localStringBuilder.append("showStagingData=true");
      }
      if (bool3) {
        if (k == 0) {
          break label372;
        }
      }
    }
    for (;;)
    {
      localStringBuilder.append(c1);
      localStringBuilder.append("p13n=false");
      return localStringBuilder.toString();
      k = 0;
      break;
      label344:
      c5 = '?';
      break label157;
      label351:
      c4 = '?';
      break label205;
      label358:
      c3 = '?';
      break label253;
      label365:
      c2 = '?';
      break label286;
      label372:
      c1 = '?';
    }
  }
  
  protected final VolleyError parseNetworkError(VolleyError paramVolleyError)
  {
    if (((paramVolleyError instanceof ServerError)) && (paramVolleyError.networkResponse != null))
    {
      PlayResponse.PlayResponseWrapper localPlayResponseWrapper = parseWrapperAndVerifySignature(paramVolleyError.networkResponse, false);
      if (localPlayResponseWrapper != null)
      {
        Response localResponse = handleServerCommands(localPlayResponseWrapper);
        if (localResponse == null) {
          break label45;
        }
        paramVolleyError = localResponse.error;
      }
    }
    return paramVolleyError;
    label45:
    int i = paramVolleyError.networkResponse.statusCode;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(i);
    PlayCommonLog.e("Received a null response in ResponseWrapper, error %d", arrayOfObject);
    return paramVolleyError;
  }
  
  public final Response<PlayResponse.PlayResponseWrapper> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    if (DEBUG)
    {
      Map localMap = paramNetworkResponse.headers;
      int k = 0;
      if (localMap != null)
      {
        boolean bool = paramNetworkResponse.headers.containsKey("X-DFE-Content-Length");
        k = 0;
        if (bool) {
          k = Integer.parseInt((String)paramNetworkResponse.headers.get("X-DFE-Content-Length")) / 1024;
        }
      }
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = getUrl();
      arrayOfObject2[1] = Integer.valueOf(k);
      PlayCommonLog.v("Parsed response for url=[%s] contentLength=[%d KB]", arrayOfObject2);
    }
    PlayResponse.PlayResponseWrapper localPlayResponseWrapper = parseWrapperAndVerifySignature(paramNetworkResponse, false);
    Response localResponse1;
    if (localPlayResponseWrapper == null)
    {
      localResponse1 = Response.error(new ParseError(paramNetworkResponse));
      return localResponse1;
    }
    String str1;
    if (PROTO_DEBUG)
    {
      str1 = (String)PlayG.protoLogUrlRegexp.get();
      if (!getUrl().matches(str1)) {
        break label353;
      }
    }
    for (;;)
    {
      try
      {
        Log.v("DfeProto", "Response for " + getUrl());
        String[] arrayOfString = MessageNanoPrinter.print(localPlayResponseWrapper).split("\n");
        int i = arrayOfString.length;
        int j = 0;
        if (j < i)
        {
          String str2 = arrayOfString[j];
          Log.v("DfeProto", "| " + str2);
          j++;
          continue;
        }
        localResponse1 = handleServerCommands(localPlayResponseWrapper);
        if (localResponse1 != null) {
          break;
        }
        if (localPlayResponseWrapper.serverMetadata != null)
        {
          ServerMetadata localServerMetadata = localPlayResponseWrapper.serverMetadata;
          if (localServerMetadata.hasLatencyMillis) {
            this.mServerLatencyMs = localServerMetadata.latencyMillis;
          }
        }
        if (this.mResponseVerifier == null) {
          break label393;
        }
        localEntry = null;
        if (localEntry != null) {
          stripForCache(localPlayResponseWrapper, localEntry);
        }
        Response localResponse2 = Response.success(localPlayResponseWrapper, localEntry);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = getUrl();
        PlayCommonLog.logTiming("DFE response %s", arrayOfObject1);
        return localResponse2;
      }
      finally {}
      label353:
      Log.v("DfeProto", "Url does not match regexp: url=" + getUrl() + " / regexp=" + str1);
      continue;
      label393:
      Cache.Entry localEntry = parseCacheHeaders(paramNetworkResponse);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.DfeRequest
 * JD-Core Version:    0.7.0.1
 */