package com.google.android.finsky.placesapi;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public final class PlaceDetailRequest
  extends JsonObjectRequest
{
  public PlaceDetailRequest(String paramString, AdrMicroformatParser paramAdrMicroformatParser, Response.Listener<PlaceDetailResponse> paramListener, Response.ErrorListener paramErrorListener)
  {
    super(paramString, null, new InnerListener(paramAdrMicroformatParser, paramListener, paramErrorListener), paramErrorListener);
  }
  
  static final class InnerListener
    extends PlaceRequestListener<PlaceDetailResponse>
  {
    private AdrMicroformatParser mParser;
    
    public InnerListener(AdrMicroformatParser paramAdrMicroformatParser, Response.Listener<PlaceDetailResponse> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramErrorListener);
      this.mParser = paramAdrMicroformatParser;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.PlaceDetailRequest
 * JD-Core Version:    0.7.0.1
 */