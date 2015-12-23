package com.google.android.play.dfe.api;

import android.net.Uri;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.android.finsky.protos.PlusProfileResponse;

public abstract interface PlayDfeApi
{
  public static final Uri BASE_URI = Uri.parse("https://android.clients.google.com/fdfe/");
  public static final Uri EXPERIMENTS_URI = Uri.parse("api/experiments");
  public static final Uri PLUS_PROFILE_URI = Uri.parse("api/plusProfile");
  
  public abstract Request<?> getPlusProfile(Response.Listener<PlusProfileResponse> paramListener, Response.ErrorListener paramErrorListener, boolean paramBoolean);
  
  public abstract void invalidatePlusProfile$1385ff();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.PlayDfeApi
 * JD-Core Version:    0.7.0.1
 */