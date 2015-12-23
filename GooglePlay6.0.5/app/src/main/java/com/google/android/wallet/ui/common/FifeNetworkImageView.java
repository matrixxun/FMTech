package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.wallet.uicomponents.R.styleable;

public class FifeNetworkImageView
  extends NetworkImageView
{
  private static int DOMINANT_DIMENSION_HEIGHT = 0;
  private static int DOMINANT_DIMENSION_WIDTH = 1;
  private float mAspectRatio;
  private String mDeferredUntilViewSizedImageUrl;
  private int mDominantDimension;
  private boolean mFadeIn;
  private int mFadeInDuration;
  private ImageLoader mImageLoader;
  private boolean mLazyLoad;
  private String mLazyLoadImageUrl;
  private OnLoadedListener mOnLoadedListener;
  private boolean mPreferWebP;
  
  public FifeNetworkImageView(Context paramContext)
  {
    super(paramContext);
  }
  
  public FifeNetworkImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  public FifeNetworkImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  private void readAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    int i = 1;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FifeNetworkImageView);
    this.mAspectRatio = localTypedArray.getFloat(R.styleable.FifeNetworkImageView_internalUicAspectRatio, -1.0F);
    this.mDominantDimension = localTypedArray.getInt(R.styleable.FifeNetworkImageView_internalUicDominantDimension, -1);
    int j;
    if (this.mAspectRatio == -1.0F)
    {
      j = i;
      if (this.mDominantDimension != -1) {
        break label83;
      }
    }
    for (;;)
    {
      if ((j ^ i) == 0) {
        break label88;
      }
      throw new RuntimeException("Both internalUicAspectRatio and internalUicDominantDimension must be specified.");
      j = 0;
      break;
      label83:
      i = 0;
    }
    label88:
    localTypedArray.recycle();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    this.mLazyLoad = false;
    if (this.mLazyLoadImageUrl != null)
    {
      super.setImageUrl(this.mLazyLoadImageUrl, this.mImageLoader);
      this.mLazyLoadImageUrl = null;
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mDeferredUntilViewSizedImageUrl != null)
    {
      final String str = this.mDeferredUntilViewSizedImageUrl;
      this.mDeferredUntilViewSizedImageUrl = null;
      post(new Runnable()
      {
        public final void run()
        {
          FifeNetworkImageView.this.setImageUrl(WalletUiUtils.createFifeUrl(str, FifeNetworkImageView.this.getWidth(), FifeNetworkImageView.this.getHeight(), FifeNetworkImageView.this.mPreferWebP), FifeNetworkImageView.this.mImageLoader);
        }
      });
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mAspectRatio > 0.0F)
    {
      int i = getDefaultSize(getSuggestedMinimumWidth(), paramInt1);
      int j = getDefaultSize(getSuggestedMinimumHeight(), paramInt2);
      if (this.mDominantDimension == DOMINANT_DIMENSION_HEIGHT) {
        i = Math.max((int)(j * this.mAspectRatio), getSuggestedMinimumWidth());
      }
      for (;;)
      {
        setMeasuredDimension(i, j);
        return;
        if (this.mDominantDimension == DOMINANT_DIMENSION_WIDTH) {
          j = Math.max((int)(i / this.mAspectRatio), getSuggestedMinimumHeight());
        }
      }
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setFadeIn(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT < 14) {
      this.mFadeIn = false;
    }
    do
    {
      return;
      this.mFadeIn = paramBoolean;
    } while (!this.mFadeIn);
    this.mFadeInDuration = getContext().getResources().getInteger(17694720);
  }
  
  public final void setFifeImageUrl(String paramString, ImageLoader paramImageLoader, boolean paramBoolean)
  {
    this.mImageLoader = paramImageLoader;
    this.mPreferWebP = paramBoolean;
    int i = getWidth();
    int j = getHeight();
    if ((i == 0) && (j == 0))
    {
      ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
      if ((localLayoutParams != null) && (localLayoutParams.width > 0) && (localLayoutParams.height > 0))
      {
        i = localLayoutParams.width;
        j = localLayoutParams.height;
      }
    }
    if ((j != 0) && (i != 0))
    {
      this.mDeferredUntilViewSizedImageUrl = null;
      setImageUrl(WalletUiUtils.createFifeUrl(paramString, i, j, this.mPreferWebP), this.mImageLoader);
      return;
    }
    this.mDeferredUntilViewSizedImageUrl = paramString;
  }
  
  @TargetApi(14)
  public void setImageBitmap(Bitmap paramBitmap)
  {
    super.setImageBitmap(paramBitmap);
    if (this.mOnLoadedListener != null) {
      this.mOnLoadedListener.onLoaded$70e9aa75(paramBitmap);
    }
    if (this.mFadeIn)
    {
      if (getVisibility() != 0) {
        break label78;
      }
      i = 1;
      if (Build.VERSION.SDK_INT >= 14) {
        if ((i == 0) || (getAlpha() != 1.0F)) {
          break label83;
        }
      }
    }
    label78:
    label83:
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        WalletUiUtils.animateViewAppearing(this, 0, 0, this.mFadeInDuration, null);
      }
      return;
      i = 0;
      break;
    }
  }
  
  public final void setImageUrl(String paramString, ImageLoader paramImageLoader)
  {
    if (this.mLazyLoad)
    {
      this.mLazyLoadImageUrl = paramString;
      return;
    }
    super.setImageUrl(paramString, paramImageLoader);
  }
  
  public void setLazyLoad(boolean paramBoolean)
  {
    this.mLazyLoad = paramBoolean;
  }
  
  public void setOnLoadedListener(OnLoadedListener paramOnLoadedListener)
  {
    this.mOnLoadedListener = paramOnLoadedListener;
  }
  
  public static abstract interface OnLoadedListener
  {
    public abstract void onLoaded$70e9aa75(Bitmap paramBitmap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FifeNetworkImageView
 * JD-Core Version:    0.7.0.1
 */