package com.google.android.finsky.placesapi;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public final class PlaceAutocompleteRequest
  extends JsonObjectRequest
{
  public PlaceAutocompleteRequest(String paramString, Response.Listener<PlaceAutocompleteResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramString, null, new InnerListener(paramListener, paramErrorListener), paramErrorListener);
  }
  
  static final class InnerListener
    extends PlaceRequestListener<PlaceAutocompleteResponse>
  {
    public InnerListener(Response.Listener<PlaceAutocompleteResponse> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramErrorListener);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.PlaceAutocompleteRequest
 * JD-Core Version:    0.7.0.1
 */