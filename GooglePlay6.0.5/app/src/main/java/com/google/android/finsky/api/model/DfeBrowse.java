package com.google.android.finsky.api.model;

import com.android.volley.Response.Listener;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.Browse.BrowseResponse;
import com.google.android.finsky.protos.UserContext;

public final class DfeBrowse
  extends DfeModel
  implements Response.Listener<Browse.BrowseResponse>
{
  public Browse.BrowseResponse mBrowseResponse;
  
  public DfeBrowse(DfeApi paramDfeApi, String paramString, UserContext paramUserContext)
  {
    paramDfeApi.getBrowseLayout(paramString, paramUserContext, this, this);
  }
  
  public final boolean isFamilySafeSearchModeDefined()
  {
    return (this.mBrowseResponse != null) && (this.mBrowseResponse.hasIsFamilySafe);
  }
  
  public final boolean isReady()
  {
    return this.mBrowseResponse != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeBrowse
 * JD-Core Version:    0.7.0.1
 */