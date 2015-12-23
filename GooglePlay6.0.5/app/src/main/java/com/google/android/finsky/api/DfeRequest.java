package com.google.android.finsky.api;

import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.Notification;
import com.google.android.finsky.protos.PreFetch;
import com.google.android.finsky.protos.Response.ResponseWrapper;
import com.google.android.finsky.protos.ServerCommands;
import com.google.android.finsky.protos.ServerCookies;
import com.google.android.finsky.protos.ServerMetadata;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens;
import com.google.android.finsky.utils.DeviceManagementHelper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.play.dfe.api.DfeResponseVerifier;
import com.google.android.play.dfe.api.DfeResponseVerifier.DfeResponseVerifierException;
import com.google.android.play.utils.config.GservicesValue;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.MessageNanoPrinter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class DfeRequest<T extends MessageNano>
  extends Request<Response.ResponseWrapper>
{
  private static final boolean DEBUG = FinskyLog.DEBUG;
  private static final boolean PROTO_DEBUG = Log.isLoggable("DfeProto", 2);
  boolean mAllowMultipleResponses = false;
  private final DfeApiContext mApiContext;
  public boolean mAvoidBulkCancel = false;
  private StringBuilder mCacheHeadersBuilder;
  private CancelListener mCancelListener;
  private Map<String, String> mExtraHeaders;
  boolean mIncludeAdIdAsHeader;
  boolean mIncludeCheckinConsistencyToken;
  boolean mIncludeManagedContextFlag;
  private Response.Listener<T> mListener;
  private long mNetworkTimeMs;
  private int mResponseBodySizeBytes;
  private final Class<T> mResponseClass;
  private boolean mResponseDelivered;
  DfeResponseVerifier mResponseVerifier;
  public long mServerLatencyMs = -1L;
  private NetworkInfo mStartNetworkInfo;
  UserSettingsConsistencyTokens mUserSettingsConsistencyTokens;
  
  public DfeRequest(int paramInt, String paramString, DfeApiContext paramDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener, CancelListener paramCancelListener)
  {
    super(paramInt, Uri.withAppendedPath(DfeApi.BASE_URI, paramString).toString(), paramErrorListener);
    if (TextUtils.isEmpty(paramString)) {
      FinskyLog.wtf("Empty DFE URL", new Object[0]);
    }
    if (!((Boolean)DfeApiConfig.skipAllCaches.get()).booleanValue()) {}
    for (boolean bool = true;; bool = false)
    {
      this.mShouldCache = bool;
      this.mRetryPolicy = new DfeRetryPolicy(DfeUtils.getRequestTimeoutMs(), paramDfeApiContext);
      this.mApiContext = paramDfeApiContext;
      this.mListener = paramListener;
      this.mResponseClass = paramClass;
      this.mStartNetworkInfo = this.mApiContext.getCurrentNetworkInfo();
      this.mCancelListener = paramCancelListener;
      return;
    }
  }
  
  public DfeRequest(String paramString, DfeApiContext paramDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
  {
    this(0, paramString, paramDfeApiContext, paramClass, paramListener, paramErrorListener, null);
  }
  
  public DfeRequest(String paramString, DfeApiContext paramDfeApiContext, Class<T> paramClass, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener, byte paramByte)
  {
    this(1, paramString, paramDfeApiContext, paramClass, paramListener, paramErrorListener, null);
  }
  
  private void handleNotifications(Response.ResponseWrapper paramResponseWrapper)
  {
    if ((this.mApiContext.mNotificationManager == null) || (paramResponseWrapper.notification.length == 0)) {}
    for (;;)
    {
      return;
      for (Notification localNotification : paramResponseWrapper.notification) {
        this.mApiContext.mNotificationManager.processNotification(localNotification);
      }
    }
  }
  
  private Response<Response.ResponseWrapper> handleServerCommands(Response.ResponseWrapper paramResponseWrapper)
  {
    if (paramResponseWrapper.commands == null) {}
    ServerCommands localServerCommands;
    do
    {
      return null;
      localServerCommands = paramResponseWrapper.commands;
      if (localServerCommands.hasLogErrorStacktrace)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localServerCommands.logErrorStacktrace;
        FinskyLog.d("%s", arrayOfObject);
      }
      if (localServerCommands.clearCache) {
        this.mApiContext.mCache.clear();
      }
      if ((localServerCommands.userSettingDirtyData != null) && (localServerCommands.userSettingDirtyData.length > 0)) {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public final void run()
          {
            UserSettingsCache.updateUserSettings(DfeRequest.this.mApiContext.getAccountName());
          }
        });
      }
    } while (!localServerCommands.hasDisplayErrorMessage);
    return Response.error(new DfeServerError(localServerCommands.displayErrorMessage));
  }
  
  private void logNetworkEvent(boolean paramBoolean1, VolleyError paramVolleyError, boolean paramBoolean2)
  {
    if (this.mApiContext.mEventLogger == null) {
      return;
    }
    if ((this.mRetryPolicy instanceof DfeRetryPolicy)) {}
    for (float f = ((DfeRetryPolicy)this.mRetryPolicy).mBackoffMultiplier;; f = 0.0F)
    {
      this.mApiContext.mEventLogger.logRpcReport(getUrl(), this.mNetworkTimeMs, this.mServerLatencyMs, 1 + this.mRetryPolicy.getCurrentRetryCount(), this.mRetryPolicy.getCurrentTimeout(), f, paramBoolean1, paramVolleyError, this.mStartNetworkInfo, this.mApiContext.getCurrentNetworkInfo(), this.mResponseBodySizeBytes, paramBoolean2);
      return;
    }
  }
  
  private String makeCacheKey(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(256).append(paramString).append("/account=").append(this.mApiContext.getAccountName());
    if (this.mCacheHeadersBuilder != null) {
      localStringBuilder.append(this.mCacheHeadersBuilder);
    }
    return localStringBuilder.toString();
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
        FinskyLog.d("Invalid TTL: %s", arrayOfObject);
        localEntry.softTtl = 0L;
        localEntry.ttl = 0L;
      }
    }
    localEntry.ttl = Math.max(localEntry.ttl, localEntry.softTtl);
    return localEntry;
  }
  
  private Response.ResponseWrapper parseWrapperAndVerifySignature(NetworkResponse paramNetworkResponse, boolean paramBoolean)
  {
    Response.ResponseWrapper localResponseWrapper;
    try
    {
      String str = (String)paramNetworkResponse.headers.get("X-DFE-Signature-Response");
      if (paramBoolean)
      {
        byte[] arrayOfByte = Utils.readBytes(new GZIPInputStream(new ByteArrayInputStream(paramNetworkResponse.data)));
        localResponseWrapper = Response.ResponseWrapper.parseFrom(arrayOfByte);
        if (this.mResponseVerifier != null)
        {
          this.mResponseVerifier.verify(arrayOfByte, str);
          return localResponseWrapper;
        }
      }
      else
      {
        localResponseWrapper = Response.ResponseWrapper.parseFrom(paramNetworkResponse.data);
        if (this.mResponseVerifier != null)
        {
          this.mResponseVerifier.verify(paramNetworkResponse.data, str);
          addMarker("signature-verification-succeeded");
          return localResponseWrapper;
        }
      }
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      while (!paramBoolean) {
        paramBoolean = true;
      }
      FinskyLog.d("Cannot parse response as ResponseWrapper proto.", new Object[0]);
      return null;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        FinskyLog.w("IOException while manually unzipping request.", new Object[0]);
      }
    }
    catch (DfeResponseVerifier.DfeResponseVerifierException localDfeResponseVerifierException)
    {
      for (;;)
      {
        addMarker("signature-verification-failed");
        FinskyLog.e("Could not verify request: %s, exception %s", new Object[] { this, localDfeResponseVerifierException });
      }
    }
    return localResponseWrapper;
  }
  
  private void stripForCache(Response.ResponseWrapper paramResponseWrapper, Cache.Entry paramEntry)
  {
    if ((paramResponseWrapper.preFetch.length <= 0) && (paramResponseWrapper.commands == null) && (paramResponseWrapper.notification.length <= 0) && (paramResponseWrapper.serverCookies == null)) {
      return;
    }
    Cache localCache = this.mApiContext.mCache;
    long l = System.currentTimeMillis();
    for (PreFetch localPreFetch : paramResponseWrapper.preFetch)
    {
      Cache.Entry localEntry = new Cache.Entry();
      localEntry.data = localPreFetch.response;
      localEntry.etag = localPreFetch.etag;
      localEntry.serverDate = paramEntry.serverDate;
      localEntry.ttl = (l + localPreFetch.ttl);
      localEntry.softTtl = (l + localPreFetch.softTtl);
      localCache.put(makeCacheKey(Uri.withAppendedPath(DfeApi.BASE_URI, localPreFetch.url).toString()), localEntry);
    }
    paramResponseWrapper.preFetch = PreFetch.emptyArray();
    paramResponseWrapper.commands = null;
    paramResponseWrapper.notification = Notification.emptyArray();
    paramResponseWrapper.serverCookies = null;
    paramEntry.data = MessageNano.toByteArray(paramResponseWrapper);
  }
  
  public final void addExtraHeader(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (this.mExtraHeaders == null) {
      this.mExtraHeaders = new HashMap();
    }
    this.mExtraHeaders.put(paramString1, paramString2);
    if (paramBoolean)
    {
      if (this.mCacheHeadersBuilder == null) {
        this.mCacheHeadersBuilder = new StringBuilder();
      }
      this.mCacheHeadersBuilder.append("/").append(paramString1).append("=").append(paramString2);
    }
  }
  
  public final void cancel()
  {
    super.cancel();
    if (this.mCancelListener != null) {
      this.mCancelListener.onRequestCanceled();
    }
  }
  
  public final void deliverError(VolleyError paramVolleyError)
  {
    this.mNetworkTimeMs = paramVolleyError.networkTimeMs;
    logNetworkEvent(false, paramVolleyError, false);
    if ((paramVolleyError instanceof AuthFailureError)) {
      this.mApiContext.invalidateAuthToken();
    }
    if (!this.mResponseDelivered)
    {
      super.deliverError(paramVolleyError);
      return;
    }
    FinskyLog.d("Not delivering error response for request=[%s], error=[%s] because response already delivered.", new Object[] { this, paramVolleyError });
  }
  
  public String getCacheKey()
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
      String str1 = "timeoutMs=" + localRetryPolicy.getCurrentTimeout();
      int i = localRetryPolicy.getCurrentRetryCount();
      if (i > 0) {
        str1 = str1 + "; retryAttempt=" + i;
      }
      localMap.put("X-DFE-Request-Params", str1);
      DfeApiContext localDfeApiContext1 = this.mApiContext;
      String str2 = (String)FinskyPreferences.serverCookies.get(localDfeApiContext1.getAccountName()).get();
      if (!TextUtils.isEmpty(str2)) {
        localMap.put("X-DFE-Server-Cookies", str2);
      }
      if (this.mIncludeCheckinConsistencyToken)
      {
        localDfeApiContext5 = this.mApiContext;
        if (localDfeApiContext5.mTokenHelper != null) {
          localDfeApiContext5.mTokenHelper.addCheckinConsistencyToken(localDfeApiContext5, localMap);
        }
      }
      else
      {
        if (this.mUserSettingsConsistencyTokens != null) {
          DfeApiContext.addUserSettingsConsistencyTokens(this.mUserSettingsConsistencyTokens, localMap);
        }
        if (this.mIncludeManagedContextFlag)
        {
          DfeApiContext localDfeApiContext4 = this.mApiContext;
          if ((localDfeApiContext4.mDeviceManagementHelper != null) && (localDfeApiContext4.mDeviceManagementHelper.isManaged(localDfeApiContext4.getAccount()))) {
            localMap.put("X-DFE-Managed-Context", "true");
          }
        }
        if (this.mIncludeAdIdAsHeader)
        {
          localDfeApiContext2 = this.mApiContext;
          Utils.ensureNotOnMainThread();
          if (localDfeApiContext2.mAdIdProvider != null) {
            break label417;
          }
          localObject = null;
          if (TextUtils.isEmpty((CharSequence)localObject)) {
            break label432;
          }
          localMap.put("X-Ad-Id", localObject);
          localDfeApiContext3 = this.mApiContext;
          AdIdProvider localAdIdProvider = localDfeApiContext3.mAdIdProvider;
          localBoolean = null;
          if (localAdIdProvider != null) {
            break label499;
          }
          if (localBoolean != null) {
            localMap.put("X-Limit-Ad-Tracking-Enabled", localBoolean.toString());
          }
        }
        return localMap;
      }
    }
    catch (DfeResponseVerifier.DfeResponseVerifierException localDfeResponseVerifierException)
    {
      for (;;)
      {
        DfeApiContext localDfeApiContext5;
        DfeApiContext localDfeApiContext2;
        Object localObject;
        DfeApiContext localDfeApiContext3;
        Boolean localBoolean;
        FinskyLog.d("Couldn't create signature request: %s", new Object[] { localDfeResponseVerifierException });
        cancel();
        continue;
        String str4 = localDfeApiContext5.getCheckinConsistencyToken();
        if (!TextUtils.isEmpty(str4))
        {
          localMap.put("X-DFE-Device-Checkin-Consistency-Token", str4);
          continue;
          label417:
          localObject = localDfeApiContext2.mAdIdProvider.getAdIdBlocking();
          continue;
          label432:
          int j;
          if (this.mApiContext.mAdIdProvider != null)
          {
            j = 1;
            label445:
            if (j != 0) {
              break label491;
            }
          }
          label491:
          for (String str3 = "no_ad_id_provider";; str3 = "ad_id_fetch_done_no_id_set")
          {
            FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(new BackgroundEventBuilder(1101).setReason(str3).event);
            break;
            j = 0;
            break label445;
          }
          label499:
          localBoolean = localDfeApiContext3.mAdIdProvider.isLimitAdTrackingEnabled();
        }
      }
    }
  }
  
  public final String getUrl()
  {
    char c1 = '&';
    String str = super.getUrl();
    int i;
    if (!TextUtils.isEmpty((CharSequence)DfeApiConfig.ipCountryOverride.get()))
    {
      i = 1;
      if (TextUtils.isEmpty((CharSequence)DfeApiConfig.mccMncOverride.get())) {
        break label116;
      }
    }
    boolean bool1;
    boolean bool2;
    boolean bool3;
    label116:
    for (int j = 1;; j = 0)
    {
      bool1 = ((Boolean)DfeApiConfig.skipAllCaches.get()).booleanValue();
      bool2 = ((Boolean)DfeApiConfig.showStagingData.get()).booleanValue();
      bool3 = ((Boolean)DfeApiConfig.prexDisabled.get()).booleanValue();
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
        localStringBuilder.append((String)DfeApiConfig.ipCountryOverride.get());
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
        localStringBuilder.append((String)DfeApiConfig.mccMncOverride.get());
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
      Response.ResponseWrapper localResponseWrapper = parseWrapperAndVerifySignature(paramVolleyError.networkResponse, false);
      if (localResponseWrapper != null)
      {
        Response localResponse = handleServerCommands(localResponseWrapper);
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
    FinskyLog.e("Received a null response in ResponseWrapper, error %d", arrayOfObject);
    return paramVolleyError;
  }
  
  public final Response<Response.ResponseWrapper> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    if ((paramNetworkResponse.headers != null) && (paramNetworkResponse.headers.containsKey("X-DFE-Content-Length"))) {
      this.mResponseBodySizeBytes = Integer.parseInt((String)paramNetworkResponse.headers.get("X-DFE-Content-Length"));
    }
    this.mNetworkTimeMs = paramNetworkResponse.networkTimeMs;
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
      FinskyLog.v("Parsed response for url=[%s] contentLength=[%d KB]", arrayOfObject2);
    }
    Response.ResponseWrapper localResponseWrapper = parseWrapperAndVerifySignature(paramNetworkResponse, false);
    Response localResponse1;
    if (localResponseWrapper == null)
    {
      localResponse1 = Response.error(new ParseError(paramNetworkResponse));
      return localResponse1;
    }
    String str1;
    if (PROTO_DEBUG)
    {
      str1 = (String)DfeApiConfig.protoLogUrlRegexp.get();
      if (!getUrl().matches(str1)) {
        break label463;
      }
    }
    for (;;)
    {
      try
      {
        Log.v("DfeProto", "Response for " + getUrl());
        String[] arrayOfString = MessageNanoPrinter.print(localResponseWrapper).split("\n");
        int i = arrayOfString.length;
        int j = 0;
        if (j < i)
        {
          String str2 = arrayOfString[j];
          Log.v("DfeProto", "| " + str2);
          j++;
          continue;
        }
        localResponse1 = handleServerCommands(localResponseWrapper);
        if (localResponse1 != null) {
          break;
        }
        if (localResponseWrapper.serverMetadata != null)
        {
          ServerMetadata localServerMetadata = localResponseWrapper.serverMetadata;
          if (localServerMetadata.hasLatencyMillis) {
            this.mServerLatencyMs = localServerMetadata.latencyMillis;
          }
        }
        handleNotifications(localResponseWrapper);
        if ((localResponseWrapper.targets != null) && (this.mApiContext.mExperiments != null)) {
          break label503;
        }
        ServerCookies localServerCookies = localResponseWrapper.serverCookies;
        if (localServerCookies != null) {
          FinskyPreferences.serverCookies.get(this.mApiContext.getAccountName()).put(Base64.encodeToString(MessageNano.toByteArray(localServerCookies), 3));
        }
        if (this.mResponseVerifier == null) {
          break label520;
        }
        localEntry = null;
        if (localEntry != null) {
          stripForCache(localResponseWrapper, localEntry);
        }
        Response localResponse2 = Response.success(localResponseWrapper, localEntry);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = getUrl();
        FinskyLog.logTiming("DFE response %s", arrayOfObject1);
        return localResponse2;
      }
      finally {}
      label463:
      Log.v("DfeProto", "Url does not match regexp: url=" + getUrl() + " / regexp=" + str1);
      continue;
      label503:
      this.mApiContext.mExperiments.setTargets(localResponseWrapper.targets);
      continue;
      label520:
      Cache.Entry localEntry = parseCacheHeaders(paramNetworkResponse);
    }
  }
  
  public static abstract interface CancelListener
  {
    public abstract void onRequestCanceled();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeRequest
 * JD-Core Version:    0.7.0.1
 */