package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.InfiniteWrappingClusterContentBinder;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayCardClusterViewV2;
import com.google.android.finsky.layout.play.PlayHighlightsBannerItemView;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;

public final class HighlightsContentBinder
  implements ClusterContentBinder<PlayHighlightsBannerItemView>, InfiniteWrappingClusterContentBinder
{
  private final BitmapLoader mBitmapLoader;
  private final PlayCardClusterViewV2 mCluster;
  private final Document mClusterDoc;
  private final Context mContext;
  private final int mHighlightsBannerCardHeight;
  private final int mHighlightsBannerCardWidth;
  public HighlightsItemLoadedListener mHighlightsItemImageLoadedListener;
  private final boolean mIsFullBleed;
  private final NavigationManager mNavigationManager;
  private final DfeList mSourceList;
  
  public HighlightsContentBinder(Context paramContext, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeList paramDfeList, PlayCardClusterViewV2 paramPlayCardClusterViewV2)
  {
    this.mClusterDoc = paramDocument;
    this.mSourceList = paramDfeList;
    this.mCluster = paramPlayCardClusterViewV2;
    this.mContext = paramContext;
    this.mBitmapLoader = paramBitmapLoader;
    this.mNavigationManager = paramNavigationManager;
    this.mIsFullBleed = FinskyApp.get().getExperiments().isEnabled(12603505L);
    Resources localResources = paramContext.getResources();
    this.mHighlightsBannerCardWidth = localResources.getDimensionPixelSize(2131493321);
    this.mHighlightsBannerCardHeight = localResources.getDimensionPixelSize(2131493319);
  }
  
  public final int adjustPosition(int paramInt)
  {
    int i = this.mSourceList.getCount();
    if (i > 0) {
      return paramInt % i;
    }
    return 0;
  }
  
  public final int getAvailableChildCount()
  {
    if (this.mSourceList.getCount() == 0) {
      return 0;
    }
    if (this.mIsFullBleed) {
      return 200000000;
    }
    return this.mSourceList.getCount();
  }
  
  public final String getChildContentSourceId()
  {
    return this.mClusterDoc.mDocument.docid;
  }
  
  public final float getChildCoverAspectRatio(int paramInt)
  {
    return PlayCardClusterMetadata.getAspectRatio(((Document)this.mSourceList.getItem(adjustPosition(paramInt), false)).mDocument.docType);
  }
  
  public final int getChildLayoutId$134621()
  {
    if (this.mIsFullBleed) {
      return 2130968989;
    }
    return 2130968990;
  }
  
  public final int getDefaultFirstVisibleChildPosition()
  {
    if (!this.mIsFullBleed) {}
    int i;
    do
    {
      return 0;
      i = this.mSourceList.getCount();
    } while (i <= 0);
    return i * (100000000 / i);
  }
  
  public final boolean isMoreDataAvailable()
  {
    return this.mSourceList.mMoreAvailable;
  }
  
  public final void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2)
  {
    AccessibilityRecordCompat localAccessibilityRecordCompat = AccessibilityEventCompat.asRecord(paramAccessibilityEvent);
    int i = this.mSourceList.getCount();
    localAccessibilityRecordCompat.setItemCount(i);
    if (i > 0)
    {
      localAccessibilityRecordCompat.setFromIndex(adjustPosition(paramInt1));
      localAccessibilityRecordCompat.setToIndex(adjustPosition(paramInt2));
    }
  }
  
  public final BitmapLoader.BitmapContainer preloadChildCoverImage(int paramInt1, int paramInt2, int paramInt3, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler, int[] paramArrayOfInt)
  {
    Document localDocument = (Document)this.mSourceList.getItem(adjustPosition(paramInt1), false);
    return ThumbnailUtils.preloadCoverImage(this.mContext, localDocument, this.mBitmapLoader, paramInt2, paramInt3, paramBitmapLoadedHandler, paramArrayOfInt);
  }
  
  public static abstract interface HighlightsItemLoadedListener
  {
    public abstract void onItemImageLoaded$60682d84(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.HighlightsContentBinder
 * JD-Core Version:    0.7.0.1
 */