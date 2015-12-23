package com.google.android.finsky.api.model;

import com.android.volley.Request;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.Search.RelatedSearch;
import com.google.android.finsky.protos.Search.SearchResponse;

public final class DfeSearch
  extends ContainerList<Search.SearchResponse>
{
  private Boolean mAggregatedQuery = null;
  public boolean mFullPageReplaced = false;
  private final String mInitialUrl;
  public String mQuery;
  public String mSuggestedQuery;
  
  public DfeSearch(DfeApi paramDfeApi, String paramString1, String paramString2)
  {
    super(paramDfeApi, paramString2);
    this.mInitialUrl = paramString2;
    this.mQuery = paramString1;
  }
  
  protected final void clearDiskCache()
  {
    throw new IllegalStateException("not supported");
  }
  
  public final boolean containsAd()
  {
    return ((Search.SearchResponse)this.mLastResponse).containsSnow;
  }
  
  public final int getBackendId()
  {
    if (this.mAggregatedQuery.booleanValue()) {
      return 0;
    }
    return super.getBackendId();
  }
  
  public final Search.RelatedSearch[] getRelatedSearches()
  {
    return ((Search.SearchResponse)this.mLastResponse).relatedSearch;
  }
  
  public final byte[] getServerLogsCookie()
  {
    if ((this.mLastResponse == null) || (((Search.SearchResponse)this.mLastResponse).serverLogsCookie.length == 0)) {
      return null;
    }
    return ((Search.SearchResponse)this.mLastResponse).serverLogsCookie;
  }
  
  public final boolean hasBackendId()
  {
    return this.mAggregatedQuery != null;
  }
  
  protected final Request<?> makeRequest(String paramString)
  {
    return this.mDfeApi.search(paramString, this, this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeSearch
 * JD-Core Version:    0.7.0.1
 */