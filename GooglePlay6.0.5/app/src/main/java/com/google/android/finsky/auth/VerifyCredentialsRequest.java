package com.google.android.finsky.auth;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Priority;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class VerifyCredentialsRequest
  extends JsonObjectRequest
{
  private final Map<String, String> mHeaders = new HashMap();
  
  public VerifyCredentialsRequest(String paramString1, String paramString2, JSONObject paramJSONObject, Response.Listener<JSONObject> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramString1, paramJSONObject, paramListener, paramErrorListener);
    AuthTokenUtils.putOauth2AuthTokenInHeader(this.mHeaders, paramString2);
  }
  
  public static JSONObject createJsonPayload(String paramString, boolean paramBoolean)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramBoolean) {}
    for (String str = "pin";; str = "password") {
      try
      {
        localJSONObject.put("credentialType", str);
        localJSONObject.put("credential", paramString);
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        throw new RuntimeException("Error while creating verification request");
      }
    }
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    return this.mHeaders;
  }
  
  public final Request.Priority getPriority()
  {
    return Request.Priority.HIGH;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.VerifyCredentialsRequest
 * JD-Core Version:    0.7.0.1
 */