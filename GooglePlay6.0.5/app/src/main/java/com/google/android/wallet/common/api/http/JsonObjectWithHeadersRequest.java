package com.google.android.wallet.common.api.http;

import android.support.v4.util.ArrayMap;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import java.util.Map;
import org.json.JSONObject;

public final class JsonObjectWithHeadersRequest
  extends JsonObjectRequest
{
  private final Map<String, String> mExtraHeaders;
  
  public JsonObjectWithHeadersRequest(String paramString, Map<String, String> paramMap, Response.Listener<JSONObject> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramString, null, paramListener, paramErrorListener);
    this.mExtraHeaders = paramMap;
    if ((this.mExtraHeaders == null) || (this.mExtraHeaders.isEmpty())) {
      throw new IllegalArgumentException("extraHeaders should not be null or empty");
    }
  }
  
  public final Map<String, String> getHeaders()
    throws AuthFailureError
  {
    Map localMap = super.getHeaders();
    if (!localMap.isEmpty())
    {
      ArrayMap localArrayMap = new ArrayMap(localMap.size() + this.mExtraHeaders.size());
      localArrayMap.putAll(localMap);
      localArrayMap.putAll(this.mExtraHeaders);
      return localArrayMap;
    }
    return this.mExtraHeaders;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.api.http.JsonObjectWithHeadersRequest
 * JD-Core Version:    0.7.0.1
 */