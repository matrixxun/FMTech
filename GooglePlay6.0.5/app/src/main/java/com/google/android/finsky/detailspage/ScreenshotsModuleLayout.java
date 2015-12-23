package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.finsky.adapters.ImageStripAdapter;
import com.google.android.finsky.adapters.ImageStripAdapter.OnImageChildViewTapListener;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.layout.HorizontalStrip;
import com.google.android.finsky.layout.HorizontalStrip.OnLoadAllScreenshotsListener;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher.RetryButtonListener;
import com.google.android.finsky.layout.play.HorizontalScrollerContainer;

public class ScreenshotsModuleLayout
  extends FrameLayout
  implements ImageStripAdapter.OnImageChildViewTapListener, Recyclable, ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, ModuleMarginItemDecoration.EdgeToEdge, HorizontalStrip.OnLoadAllScreenshotsListener, LayoutSwitcher.RetryButtonListener, HorizontalScrollerContainer
{
  ScreenshotsClickListener mClickListener;
  HorizontalStrip mImageStrip;
  ImageStripAdapter mImageStripAdapter;
  LayoutSwitcher mLayoutSwitcher;
  ScreenshotsLoader mScreenshotsLoader;
  
  public ScreenshotsModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public ScreenshotsModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public static int getMaxNumImagesShown(Resources paramResources)
  {
    int i = paramResources.getDimensionPixelSize(2131492967) / 2;
    return paramResources.getDisplayMetrics().widthPixels / i;
  }
  
  Drawable getPlaceholderDrawable()
  {
    int i = (int)getContext().getResources().getDimension(2131492967);
    Bitmap localBitmap = Bitmap.createBitmap(i * 9 / 16, i, Bitmap.Config.ARGB_8888);
    localBitmap.eraseColor(getContext().getResources().getColor(2131689534));
    return new BitmapDrawable(getContext().getResources(), localBitmap);
  }
  
  public final boolean isPointInHorizontalScroller(float paramFloat1, float paramFloat2)
  {
    return (paramFloat1 >= this.mImageStrip.getLeft()) && (paramFloat1 < this.mImageStrip.getRight()) && (paramFloat2 >= this.mImageStrip.getTop()) && (paramFloat2 < this.mImageStrip.getBottom());
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mImageStrip = ((HorizontalStrip)findViewById(2131755393));
    Resources localResources = getResources();
    boolean bool = localResources.getBoolean(2131427334);
    int i;
    if (bool)
    {
      int k = ModuleMarginItemDecoration.getDefaultSideMargin(localResources, bool);
      i = Math.max(k - localResources.getDimensionPixelSize(2131493502), 0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)findViewById(2131755392).getLayoutParams();
      localLayoutParams.leftMargin = k;
      localLayoutParams.rightMargin = k;
    }
    for (;;)
    {
      int j = localResources.getDimensionPixelOffset(2131493503);
      this.mImageStrip.setMargins(i, j);
      this.mLayoutSwitcher = new LayoutSwitcher(this, 2131755393, this);
      this.mLayoutSwitcher.switchToLoadingDelayed(500);
      return;
      i = 0;
    }
  }
  
  public final void onIgnoreNextTouchSequence() {}
  
  public final void onImageChildViewTap(int paramInt)
  {
    if (this.mClickListener != null) {
      this.mClickListener.onImageClick(paramInt);
    }
  }
  
  public final void onLoadAllScreenshots()
  {
    if (this.mScreenshotsLoader != null) {
      this.mScreenshotsLoader.loadAllScreenshots();
    }
  }
  
  public final void onRecycle()
  {
    if (this.mImageStripAdapter != null) {
      this.mImageStripAdapter.mDataSetObservable.unregisterAll();
    }
    this.mImageStrip.setAdapter(null);
    this.mImageStripAdapter = null;
  }
  
  public final void onRetry()
  {
    if (this.mClickListener != null) {
      this.mClickListener.onRetryClick();
    }
  }
  
  public static abstract interface ScreenshotsClickListener
  {
    public abstract void onImageClick(int paramInt);
    
    public abstract void onRetryClick();
  }
  
  public static abstract interface ScreenshotsLoader
  {
    public abstract void loadAllScreenshots();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ScreenshotsModuleLayout
 * JD-Core Version:    0.7.0.1
 */