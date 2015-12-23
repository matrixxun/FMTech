package com.google.android.vending.remoting.api;

import android.accounts.Account;
import android.text.TextUtils;
import android.util.Base64;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.protos.VendingProtos.PendingNotificationsProto;
import com.google.android.finsky.protos.VendingProtos.RequestProto;
import com.google.android.finsky.protos.VendingProtos.RequestProto.Request;
import com.google.android.finsky.protos.VendingProtos.ResponsePropertiesProto;
import com.google.android.finsky.protos.VendingProtos.ResponseProto;
import com.google.android.finsky.protos.VendingProtos.ResponseProto.Response;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.play.dfe.utils.NanoProtoHelper;
import com.google.protobuf.nano.MessageNano;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public final class VendingRequest<T extends MessageNano, U extends MessageNano>
  extends Request<VendingProtos.ResponseProto>
{
  public AdIdProvider mAdIdProvider;
  protected final VendingApiContext mApiContext;
  public boolean mAvoidBulkCancel = false;
  private Map<String, String> mExtraHeaders;
  public boolean mIncludeAdIdAsHeader;
  private final Response.Listener<U> mListener;
  private final T mRequest;
  private final Class<T> mRequestClass;
  private final Class<U> mResponseClass;
  private final boolean mUseSecureAuthToken;
  
  private VendingRequest(String paramString, Class<T> paramClass, T paramT, Class<U> paramClass1, Response.Listener<U> paramListener, VendingApiContext paramVendingApiContext, Response.ErrorListener paramErrorListener)
  {
    super(1, paramString, paramErrorListener);
    this.mUseSecureAuthToken = paramString.startsWith("https");
    this.mRequest = paramT;
    this.mRequestClass = paramClass;
    this.mResponseClass = paramClass1;
    this.mListener = paramListener;
    this.mApiContext = paramVendingApiContext;
    this.mRetryPolicy = new VendingRetryPolicy(this.mApiContext, this.mUseSecureAuthToken);
  }
  
  public static <T extends MessageNano, U extends MessageNano> VendingRequest<T, U> make(String paramString, Class<T> paramClass, T paramT, Class<U> paramClass1, Response.Listener<U> paramListener, VendingApiContext paramVendingApiContext, Response.ErrorListener paramErrorListener)
  {
    return new VendingRequest(paramString, paramClass, paramT, paramClass1, paramListener, paramVendingApiContext, paramErrorListener);
  }
  
  public final void addExtraHeader(String paramString1, String paramString2)
  {
    if (this.mExtraHeaders == null) {
      this.mExtraHeaders = new HashMap();
    }
    this.mExtraHeaders.put(paramString1, paramString2);
  }
  
  public final void deliverError(VolleyError paramVolleyError)
  {
    if ((paramVolleyError instanceof AuthFailureError)) {
      this.mApiContext.invalidateAuthToken(this.mUseSecureAuthToken);
    }
    super.deliverError(paramVolleyError);
  }
  
  public final Map<String, String> getHeaders()
  {
    Object localObject = this.mApiContext.getHeaders();
    if ((this.mExtraHeaders != null) && (!this.mExtraHeaders.isEmpty()))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.putAll((Map)localObject);
      localHashMap.putAll(this.mExtraHeaders);
      localObject = localHashMap;
    }
    if (this.mIncludeAdIdAsHeader)
    {
      String str = this.mAdIdProvider.getAdIdBlocking();
      if (TextUtils.isEmpty(str)) {
        break label121;
      }
      ((Map)localObject).put("X-Ad-Id", str);
    }
    for (;;)
    {
      Boolean localBoolean = this.mAdIdProvider.isLimitAdTrackingEnabled();
      if (localBoolean != null) {
        ((Map)localObject).put("X-Limit-Ad-Tracking-Enabled", localBoolean.toString());
      }
      return localObject;
      label121:
      FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(new BackgroundEventBuilder(1101).setReason("ad_id_fetch_done_no_id_set").event);
    }
  }
  
  public final Map<String, String> getParams()
    throws AuthFailureError
  {
    HashMap localHashMap = new HashMap();
    MessageNano localMessageNano = this.mRequest;
    VendingProtos.RequestProto.Request localRequest = new VendingProtos.RequestProto.Request();
    NanoProtoHelper.setRequestInWrapper(localRequest, VendingProtos.RequestProto.Request.class, localMessageNano, this.mRequestClass);
    VendingProtos.RequestProto localRequestProto = new VendingProtos.RequestProto();
    localRequestProto.requestProperties = this.mApiContext.getRequestProperties(this.mUseSecureAuthToken);
    localRequestProto.request = new VendingProtos.RequestProto.Request[] { localRequest };
    localHashMap.put("request", Base64.encodeToString(MessageNano.toByteArray(localRequestProto), 11));
    localHashMap.put("version", "2");
    return localHashMap;
  }
  
  protected final Response<VendingProtos.ResponseProto> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    try
    {
      byte[] arrayOfByte = Utils.readBytes(new GZIPInputStream(new ByteArrayInputStream(paramNetworkResponse.data), paramNetworkResponse.data.length));
      VendingProtos.ResponseProto localResponseProto = (VendingProtos.ResponseProto)MessageNano.mergeFrom$1ec43da(new VendingProtos.ResponseProto(), arrayOfByte, arrayOfByte.length);
      if (localResponseProto.response.length != 1) {
        return Response.error(new ServerError());
      }
      if (localResponseProto.response[0].responseProperties.result != 0) {
        return Response.error(new ServerError());
      }
      if (localResponseProto.pendingNotifications != null)
      {
        VendingProtos.PendingNotificationsProto localPendingNotificationsProto = localResponseProto.pendingNotifications;
        PendingNotificationsHandler localPendingNotificationsHandler = FinskyApp.get().mPendingNotificationsHandler;
        if (localPendingNotificationsHandler != null) {
          localPendingNotificationsHandler.handlePendingNotifications(FinskyApp.get().getApplicationContext(), this.mApiContext.mAuthenticator.mAccount.name, localPendingNotificationsProto, true);
        }
      }
      Response localResponse = Response.success(localResponseProto, null);
      return localResponse;
    }
    catch (IOException localIOException)
    {
      FinskyLog.w("Cannot parse Vending ResponseProto: " + localIOException, new Object[0]);
    }
    return Response.error(new VolleyError());
  }
  
  public final String toString()
  {
    return super.toString() + " " + this.mRequestClass.getSimpleName();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.remoting.api.VendingRequest
 * JD-Core Version:    0.7.0.1
 */