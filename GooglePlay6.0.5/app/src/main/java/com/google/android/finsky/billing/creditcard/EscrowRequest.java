package com.google.android.finsky.billing.creditcard;

import android.text.TextUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class EscrowRequest
  extends StringRequest
{
  private final String mCvc;
  private final String mUserId;
  
  public EscrowRequest(String paramString, Response.Listener<String> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(1, str2, paramListener, paramErrorListener);
    this.mRetryPolicy = new DefaultRetryPolicy(10000, 0, 0.0F);
    this.mUserId = Integer.toString(Math.abs(0xFFFFFFFE & new Random(System.currentTimeMillis() ^ ((Long)DfeApiConfig.androidId.get()).longValue()).nextInt()));
    if (TextUtils.isEmpty(paramString)) {
      throw new IllegalArgumentException("CVC cannot be NULL.");
    }
    this.mCvc = paramString;
  }
  
  protected final Map<String, String> getParams()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("gid", this.mUserId);
    localHashMap.put("cvv", this.mCvc);
    return localHashMap;
  }
  
  protected final Response<String> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    if (paramNetworkResponse.data.length == 0)
    {
      if (((Boolean)G.enableSensitiveLogging.get()).booleanValue())
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mCvc;
        FinskyLog.w("Empty escrow handle for cvc %s", arrayOfObject2);
      }
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mUserId;
      FinskyLog.wtf("Null response for Escrow string with id %s", arrayOfObject1);
      return Response.error(new ServerError(paramNetworkResponse));
    }
    return super.parseNetworkResponse(paramNetworkResponse);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.creditcard.EscrowRequest
 * JD-Core Version:    0.7.0.1
 */