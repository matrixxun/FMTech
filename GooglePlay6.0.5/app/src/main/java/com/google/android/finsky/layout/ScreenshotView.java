package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeImageView.OnLoadedListener;

public class ScreenshotView
  extends FrameLayout
  implements FifeImageView.OnLoadedListener
{
  private Runnable mFadeInRunnable = new FadeInViewRunnable((byte)0);
  private Handler mHandler = new Handler();
  private FifeImageView mImageView;
  private ProgressBar mProgressBar;
  
  public ScreenshotView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ScreenshotView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ScreenshotView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mHandler.removeCallbacks(this.mFadeInRunnable);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mImageView = ((FifeImageView)findViewById(2131756090));
    this.mProgressBar = ((ProgressBar)findViewById(2131756089));
  }
  
  public final void onLoaded(FifeImageView paramFifeImageView, Bitmap paramBitmap)
  {
    this.mHandler.removeCallbacks(this.mFadeInRunnable);
    if (this.mProgressBar.getVisibility() == 0)
    {
      Animation localAnimation = AnimationUtils.loadAnimation(getContext(), 2131034127);
      localAnimation.setAnimationListener(new HideAfterEndAnimationListener((byte)0));
      this.mProgressBar.startAnimation(localAnimation);
    }
  }
  
  public final void onLoadedAndFadedIn(FifeImageView paramFifeImageView) {}
  
  public final void setImage(Common.Image paramImage, BitmapLoader paramBitmapLoader)
  {
    this.mImageView.setOnLoadedListener(this);
    this.mImageView.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, paramBitmapLoader);
    if (!this.mImageView.isLoaded()) {
      this.mHandler.postDelayed(this.mFadeInRunnable, 500L);
    }
  }
  
  private final class FadeInViewRunnable
    implements Runnable
  {
    private FadeInViewRunnable() {}
    
    public final void run()
    {
      Animation localAnimation = AnimationUtils.loadAnimation(ScreenshotView.this.mProgressBar.getContext(), 2131034129);
      ScreenshotView.this.mProgressBar.setVisibility(0);
      ScreenshotView.this.mProgressBar.startAnimation(localAnimation);
    }
  }
  
  private final class HideAfterEndAnimationListener
    implements Animation.AnimationListener
  {
    private HideAfterEndAnimationListener() {}
    
    public final void onAnimationEnd(Animation paramAnimation)
    {
      ScreenshotView.this.mProgressBar.setVisibility(8);
    }
    
    public final void onAnimationRepeat(Animation paramAnimation) {}
    
    public final void onAnimationStart(Animation paramAnimation) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ScreenshotView
 * JD-Core Version:    0.7.0.1
 */