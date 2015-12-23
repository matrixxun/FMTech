package com.google.android.finsky.api.model;

import com.android.volley.Response.Listener;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.Details.DetailsResponse;
import java.util.Collection;

public final class DfeDetails
  extends DfeModel
  implements Response.Listener<Details.DetailsResponse>
{
  public Details.DetailsResponse mDetailsResponse;
  private final String mDetailsUrl;
  
  public DfeDetails(DfeApi paramDfeApi, String paramString)
  {
    this(paramDfeApi, paramString, false, null);
  }
  
  public DfeDetails(DfeApi paramDfeApi, String paramString, boolean paramBoolean, Collection<String> paramCollection)
  {
    this.mDetailsUrl = paramString;
    paramDfeApi.getDetails(this.mDetailsUrl, paramBoolean, false, paramCollection, this, this);
  }
  
  public final Document getDocument()
  {
    if ((this.mDetailsResponse == null) || (this.mDetailsResponse.docV2 == null)) {
      return null;
    }
    return new Document(this.mDetailsResponse.docV2);
  }
  
  public final byte[] getServerLogsCookie()
  {
    if ((this.mDetailsResponse == null) || (this.mDetailsResponse.serverLogsCookie.length == 0)) {
      return null;
    }
    return this.mDetailsResponse.serverLogsCookie;
  }
  
  public final boolean isReady()
  {
    return this.mDetailsResponse != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeDetails
 * JD-Core Version:    0.7.0.1
 */