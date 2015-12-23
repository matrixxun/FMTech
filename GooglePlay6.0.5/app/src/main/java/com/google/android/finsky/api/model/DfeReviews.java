package com.google.android.finsky.api.model;

import com.android.volley.Request;
import com.android.volley.Response.Listener;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.CriticReviewsResponse;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.ReviewResponse;
import com.google.android.finsky.protos.ReviewTip;

public final class DfeReviews
  extends PaginatedList<ReviewResponse, Review>
  implements Response.Listener<ReviewResponse>
{
  private DfeApi mDfeApi;
  public boolean mFilterByDevice;
  public boolean mFilterByVersion;
  public int mRating;
  public CriticReviewsResponse mRottenTomatoesReviewData;
  public int mSortType;
  public ReviewTip mTip;
  private int mVersionCode;
  
  public DfeReviews(DfeApi paramDfeApi, String paramString, int paramInt, boolean paramBoolean)
  {
    super(paramString, paramBoolean);
    this.mDfeApi = paramDfeApi;
    this.mFilterByVersion = false;
    this.mFilterByDevice = false;
    this.mRating = 0;
    this.mVersionCode = paramInt;
    this.mSortType = -1;
  }
  
  protected final void clearDiskCache()
  {
    throw new IllegalStateException("not supported");
  }
  
  public final int getVersionFilter()
  {
    if (this.mFilterByVersion) {
      return this.mVersionCode;
    }
    return -1;
  }
  
  protected final Request<?> makeRequest(String paramString)
  {
    DfeApi localDfeApi = this.mDfeApi;
    boolean bool = this.mFilterByDevice;
    if (this.mFilterByVersion) {}
    for (int i = this.mVersionCode;; i = -1) {
      return localDfeApi.getReviews(paramString, bool, i, this.mRating, this.mSortType, this, this);
    }
  }
  
  public final void refetchReviews()
  {
    resetItems();
    startLoadItems();
  }
  
  public final void resetItems()
  {
    super.resetItems();
    this.mTip = null;
  }
  
  public final void setFilters(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFilterByVersion = paramBoolean1;
    this.mFilterByDevice = paramBoolean2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeReviews
 * JD-Core Version:    0.7.0.1
 */