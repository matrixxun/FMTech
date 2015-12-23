package com.google.android.finsky.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.play.utils.config.GservicesValue;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class SkyjamJsonObjectRequest
  extends JsonObjectRequest
{
  public SkyjamJsonObjectRequest(String paramString, Response.Listener<JSONObject> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(1, paramString, null, paramListener, paramErrorListener);
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("X-Device-Logging-ID", DfeApiConfig.loggingId.get());
    localHashMap.put("X-Device-ID", Long.toHexString(((Long)DfeApiConfig.androidId.get()).longValue()));
    return localHashMap;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.SkyjamJsonObjectRequest
 * JD-Core Version:    0.7.0.1
 */