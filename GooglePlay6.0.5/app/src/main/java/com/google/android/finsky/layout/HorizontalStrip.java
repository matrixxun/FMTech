package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import com.google.android.finsky.adapters.ImageStripAdapter;
import com.google.android.finsky.adapters.ImageStripAdapter.1;
import com.google.android.finsky.protos.Common.Image.Dimension;

public class HorizontalStrip
  extends AbsHorizontalStrip
{
  private ImageStripAdapter mAdapter;
  private int[] mAppScreenshotStates;
  private final Common.Image.Dimension mDimension = new Common.Image.Dimension();
  private int mEdgeFadeColor;
  private OnLoadAllScreenshotsListener mLoadAllScreenshotsListener;
  protected final float mScreenScaleFactor;
  
  public HorizontalStrip(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public HorizontalStrip(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = paramContext.getResources();
    this.mScreenScaleFactor = localResources.getDisplayMetrics().density;
    this.mEdgeFadeColor = localResources.getColor(2131689706);
  }
  
  private int getTotalChildWidth(int paramInt)
  {
    int i = getChildAt(paramInt).getWidth();
    if (paramInt == 0) {}
    for (int j = this.mLeadingMargin;; j = this.mGapMargin) {
      return j + i;
    }
  }
  
  private void recreateChildViews()
  {
    removeAllViews();
    if (this.mAdapter == null) {
      return;
    }
    for (final int i = 0; i < this.mAdapter.mImageCount; i++)
    {
      ImageStripAdapter localImageStripAdapter = this.mAdapter;
      View localView = LayoutInflater.from(this.mContext).inflate(2130968618, this, false);
      localView.setOnClickListener(new ImageStripAdapter.1(localImageStripAdapter, i));
      localView.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public final void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean) {
            HorizontalStrip.this.onChildAcquiredFocus(i);
          }
        }
      });
      addView(localView);
    }
    syncChildViews();
  }
  
  private void syncChildViews()
  {
    int i = 1;
    int j = 0;
    if (j < this.mAdapter.mImageCount)
    {
      View localView = getChildAt(j);
      Drawable localDrawable = this.mAdapter.mImages[j];
      AppScreenshot localAppScreenshot;
      int k;
      if ((localView instanceof AppScreenshot))
      {
        localAppScreenshot = (AppScreenshot)localView;
        if (localDrawable == null) {
          break label113;
        }
        if (localAppScreenshot.mScreenshot.getDrawable() == null) {
          break label107;
        }
        k = 1;
        label64:
        if (k != 0) {
          break label113;
        }
        localAppScreenshot.setScreenshotDrawable(localDrawable);
      }
      for (;;)
      {
        if (localAppScreenshot.mState != this.mAppScreenshotStates[j]) {
          localAppScreenshot.setState(this.mAppScreenshotStates[j]);
        }
        j++;
        break;
        label107:
        k = 0;
        break label64;
        label113:
        if ((localDrawable != null) && (localAppScreenshot.mState != 2) && (this.mAppScreenshotStates[j] == 2)) {
          localAppScreenshot.setScreenshotDrawable(localDrawable);
        } else {
          i = 0;
        }
      }
    }
    if (i != 0) {
      requestLayout();
    }
  }
  
  protected final float getLeftEdgeOfChild(int paramInt)
  {
    int i = 0;
    int j = 0;
    for (int k = 0; k < paramInt; k++)
    {
      j += getTotalChildWidth(k);
      i = j;
    }
    if (k != 0) {
      i += this.mGapMargin;
    }
    return i;
  }
  
  protected final float getLeftEdgeOfChildOnLeft(float paramFloat)
  {
    int i = 0;
    int j = 0;
    for (int k = 0; k < getChildCount(); k++)
    {
      j += getTotalChildWidth(k);
      if (j > paramFloat) {
        break;
      }
      i = j;
    }
    return i;
  }
  
  protected final float getLeftEdgeOfChildOnRight(float paramFloat)
  {
    int i = 0;
    int j = 0;
    for (int k = 0; k < getChildCount(); k++)
    {
      j += getTotalChildWidth(k);
      i = j;
      if (j > paramFloat) {
        break;
      }
    }
    return i + this.mGapMargin;
  }
  
  protected float getLeftFadingEdgeStrength()
  {
    float f1 = getScrollPosition();
    if (f1 >= 0.0F) {
      return 0.0F;
    }
    float f2 = -f1;
    int i = getHorizontalFadingEdgeLength();
    if (f2 > i) {
      return 1.0F;
    }
    return f2 / i;
  }
  
  protected float getRightFadingEdgeStrength()
  {
    float f = getScrollPosition() + this.mTotalChildrenWidth - getWidth();
    if (f <= 0.0F) {
      return 0.0F;
    }
    int i = getHorizontalFadingEdgeLength();
    if (f > i) {
      return 1.0F;
    }
    return f / i;
  }
  
  public int getSolidColor()
  {
    return this.mEdgeFadeColor;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mAdapter == null) {
      return;
    }
    int i = getHeight();
    this.mTotalChildrenWidth = 0.0F;
    int j = getPaddingLeft() + this.mLeadingMargin;
    for (int k = 0; k < getChildCount(); k++)
    {
      View localView = getChildAt(k);
      int m = localView.getMeasuredWidth();
      localView.layout(j, 0, j + m, i);
      j += m + this.mGapMargin;
      this.mTotalChildrenWidth += m;
    }
    this.mTotalChildrenWidth += this.mGapMargin * (-1 + getChildCount());
    this.mTotalChildrenWidth += this.mLeadingMargin;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt2);
    for (int j = 0; j < getChildCount(); j++)
    {
      View localView = getChildAt(j);
      this.mAdapter.getImageDimensionAt(j, this.mDimension, this.mScreenScaleFactor);
      int k = this.mDimension.width;
      this.mAdapter.getImageDimensionAt(j, this.mDimension, this.mScreenScaleFactor);
      int m = this.mDimension.height;
      if (m != 0)
      {
        float f = i / m;
        if (f < 1.0D) {
          k = (int)(f * k);
        }
      }
      localView.measure(View.MeasureSpec.makeMeasureSpec(k, 1073741824), paramInt2);
    }
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), i);
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.mLoadAllScreenshotsListener != null) && (paramInt1 > paramInt3)) {
      this.mLoadAllScreenshotsListener.onLoadAllScreenshots();
    }
  }
  
  public void setAdapter(ImageStripAdapter paramImageStripAdapter)
  {
    this.mAdapter = paramImageStripAdapter;
    if (this.mAdapter != null)
    {
      ImageStripAdapter localImageStripAdapter = this.mAdapter;
      DataSetObserver local1 = new DataSetObserver()
      {
        public final void onChanged()
        {
          HorizontalStrip.this.syncChildViews();
        }
        
        public final void onInvalidated()
        {
          HorizontalStrip.this.recreateChildViews();
        }
      };
      localImageStripAdapter.mDataSetObservable.registerObserver(local1);
    }
    recreateChildViews();
  }
  
  public void setAppScreenshotStates(int[] paramArrayOfInt)
  {
    this.mAppScreenshotStates = paramArrayOfInt;
  }
  
  public void setLoadAllScreenshotsListener(OnLoadAllScreenshotsListener paramOnLoadAllScreenshotsListener)
  {
    this.mLoadAllScreenshotsListener = paramOnLoadAllScreenshotsListener;
  }
  
  public static abstract interface OnLoadAllScreenshotsListener
  {
    public abstract void onLoadAllScreenshots();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.HorizontalStrip
 * JD-Core Version:    0.7.0.1
 */