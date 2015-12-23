package com.google.android.finsky.billing.challenge;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Priority;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.auth.AuthResponseListener;
import com.google.android.finsky.utils.FinskyLog;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public final class ClientLoginApi
{
  public static String CLIENT_LOGIN_URI = "https://www.google.com/accounts/ClientLogin";
  private static String REQUEST_PARAM_ACCOUNT_TYPE = "accountType";
  private static String REQUEST_PARAM_EMAIL = "Email";
  private static String REQUEST_PARAM_PASSWD = "Passwd";
  private static String REQUEST_PARAM_SERVICE = "service";
  private static String REQUEST_PARAM_SOURCE = "source";
  private static String REQUEST_VALUE_ACCOUNT_TYPE = "HOSTED_OR_GOOGLE";
  private static String REQUEST_VALUE_SERVICE = "apps";
  private static String REQUEST_VALUE_SOURCE = "Google-GooglePlay-";
  private static String RESULT_ERROR_BAD_AUTH = "Error=BadAuthentication";
  private static String RESULT_ERROR_CAPTCHA_REQUIRED = "Error=CaptchaRequired";
  private static String RESULT_ERROR_INFO_TWO_FACTOR = "Info=InvalidSecondFactor";
  public final RequestQueue mQueue;
  
  public ClientLoginApi(RequestQueue paramRequestQueue)
  {
    this.mQueue = paramRequestQueue;
  }
  
  private final class ClientLoginRequest
    extends StringRequest
  {
    private final Map<String, String> mPostParams = new HashMap();
    
    public ClientLoginRequest(String paramString1, String paramString2, Response.Listener<String> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramString1, paramErrorListener, localErrorListener);
      this.mPostParams.put(ClientLoginApi.REQUEST_PARAM_ACCOUNT_TYPE, ClientLoginApi.REQUEST_VALUE_ACCOUNT_TYPE);
      this.mPostParams.put(ClientLoginApi.REQUEST_PARAM_EMAIL, paramString2);
      this.mPostParams.put(ClientLoginApi.REQUEST_PARAM_PASSWD, paramListener);
      this.mPostParams.put(ClientLoginApi.REQUEST_PARAM_SERVICE, ClientLoginApi.REQUEST_VALUE_SERVICE);
      this.mPostParams.put(ClientLoginApi.REQUEST_PARAM_SOURCE, ClientLoginApi.REQUEST_VALUE_SOURCE + FinskyApp.get().getVersionCode());
    }
    
    public final Map<String, String> getParams()
    {
      return this.mPostParams;
    }
    
    public final Request.Priority getPriority()
    {
      return Request.Priority.HIGH;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.challenge.ClientLoginApi
 * JD-Core Version:    0.7.0.1
 */