package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import com.google.android.finsky.layout.ClusterContentConfigurator;
import com.google.android.finsky.layout.DocImageView;

public class PlayHighlightsBannerView
  extends PlayCardClusterViewV2
{
  private HighlightsBannerListener mBannerListener;
  
  public PlayHighlightsBannerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHighlightsBannerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void notifyBannerListener(int paramInt)
  {
    if (this.mBannerListener != null)
    {
      if (paramInt != 0) {
        this.mBannerListener.onHighlightsScrolled(paramInt);
      }
      int i = this.mContent.getScrolledToItemPosition();
      if (i >= 0) {
        this.mBannerListener.onCurrentBannerChanged(i);
      }
    }
  }
  
  public int[] getImageTypePreference()
  {
    return PlayHighlightsBannerItemView.HIGHLIGHTS_BANNER_IMAGE_TYPES;
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 428;
  }
  
  public final PlayHighlightsBannerItemView getViewForItemPosition(int paramInt)
  {
    return this.mContent.getViewForItemPosition(paramInt);
  }
  
  public final boolean isItemLoaded(int paramInt)
  {
    PlayHighlightsBannerItemView localPlayHighlightsBannerItemView = this.mContent.getViewForItemPosition(paramInt);
    return (localPlayHighlightsBannerItemView != null) && (localPlayHighlightsBannerItemView.mCoverImageView.isLoaded());
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mContent.addOnScrollListener(new RecyclerView.OnScrollListener()
    {
      public final void onScrolled(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        PlayHighlightsBannerView.this.notifyBannerListener(paramAnonymousInt1);
      }
    });
    this.mContent.setOnScrollToPositionListener(new PlayClusterViewContentV2.ScrollToPositionListener()
    {
      public final void onScrollToPositionWithOffset(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (PlayHighlightsBannerView.this.mBannerListener != null) {
          PlayHighlightsBannerView.this.mBannerListener.onScrolledToPosition(paramAnonymousInt1, paramAnonymousInt2);
        }
      }
    });
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mContent.getClusterContentConfigurator() != null) && (setCardContentHorizontalPadding((getMeasuredWidth() - this.mContent.getClusterContentConfigurator().getFixedChildWidth$255f288(getMeasuredHeight())) / 2))) {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public void setHighlightBannerListener(HighlightsBannerListener paramHighlightsBannerListener)
  {
    this.mBannerListener = paramHighlightsBannerListener;
    notifyBannerListener(0);
  }
  
  public static abstract interface HighlightsBannerListener
  {
    public abstract void onCurrentBannerChanged(int paramInt);
    
    public abstract void onHighlightsScrolled(int paramInt);
    
    public abstract void onScrolledToPosition(int paramInt1, int paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayHighlightsBannerView
 * JD-Core Version:    0.7.0.1
 */