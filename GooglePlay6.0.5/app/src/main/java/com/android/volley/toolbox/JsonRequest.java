package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import java.io.UnsupportedEncodingException;

public abstract class JsonRequest<T>
  extends Request<T>
{
  private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", new Object[] { "utf-8" });
  private final Response.Listener<T> mListener;
  private final String mRequestBody;
  
  public JsonRequest(int paramInt, String paramString1, String paramString2, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramInt, paramString1, paramErrorListener);
    this.mListener = paramListener;
    this.mRequestBody = paramString2;
  }
  
  protected final void deliverResponse(T paramT)
  {
    this.mListener.onResponse(paramT);
  }
  
  public final byte[] getBody()
  {
    try
    {
      if (this.mRequestBody == null) {
        return null;
      }
      byte[] arrayOfByte = this.mRequestBody.getBytes("utf-8");
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.mRequestBody;
      arrayOfObject[1] = "utf-8";
      VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", arrayOfObject);
    }
    return null;
  }
  
  public final String getBodyContentType()
  {
    return PROTOCOL_CONTENT_TYPE;
  }
  
  public final byte[] getPostBody()
  {
    return getBody();
  }
  
  public final String getPostBodyContentType()
  {
    return PROTOCOL_CONTENT_TYPE;
  }
  
  public abstract Response<T> parseNetworkResponse(NetworkResponse paramNetworkResponse);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.JsonRequest
 * JD-Core Version:    0.7.0.1
 */