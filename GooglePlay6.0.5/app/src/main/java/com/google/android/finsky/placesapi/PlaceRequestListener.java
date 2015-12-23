package com.google.android.finsky.placesapi;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class PlaceRequestListener<T>
  implements Response.Listener<JSONObject>
{
  private final Response.ErrorListener mErrorListener;
  private final Response.Listener<T> mListener;
  
  public PlaceRequestListener(Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener)
  {
    this.mListener = paramListener;
    this.mErrorListener = paramErrorListener;
  }
  
  protected abstract T parseFromJson(JSONObject paramJSONObject)
    throws JSONException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.PlaceRequestListener
 * JD-Core Version:    0.7.0.1
 */