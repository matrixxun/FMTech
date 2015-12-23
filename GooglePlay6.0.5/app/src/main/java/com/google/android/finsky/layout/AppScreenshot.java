package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AppScreenshot
  extends FrameLayout
{
  private ImageView mDownloadIcon;
  private Handler mHandler;
  private ProgressBar mProgressBar;
  ImageView mScreenshot;
  private Runnable mShowProgressbarRunnable;
  public int mState;
  
  public AppScreenshot(Context paramContext)
  {
    super(paramContext);
  }
  
  public AppScreenshot(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mHandler != null) {
      this.mHandler.removeCallbacks(this.mShowProgressbarRunnable);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mScreenshot = ((ImageView)findViewById(2131755255));
    this.mProgressBar = ((ProgressBar)findViewById(2131755257));
    this.mDownloadIcon = ((ImageView)findViewById(2131755256));
  }
  
  public void setScreenshotDrawable(Drawable paramDrawable)
  {
    this.mScreenshot.setImageDrawable(paramDrawable);
    if ((paramDrawable instanceof TransitionDrawable))
    {
      this.mDownloadIcon.setVisibility(8);
      return;
    }
    this.mDownloadIcon.setVisibility(0);
  }
  
  public void setState(int paramInt)
  {
    if ((this.mState == 0) && (paramInt == 1))
    {
      if ((this.mHandler == null) && (this.mShowProgressbarRunnable == null))
      {
        this.mShowProgressbarRunnable = new ShowProgressBarRunnable((byte)0);
        this.mHandler = new Handler();
      }
      this.mHandler.postDelayed(this.mShowProgressbarRunnable, 500L);
      Animation localAnimation = AnimationUtils.loadAnimation(getContext(), 2131034127);
      localAnimation.setAnimationListener(new HideIconAfterEndAnimationListener((byte)0));
      this.mDownloadIcon.startAnimation(localAnimation);
    }
    for (;;)
    {
      this.mState = paramInt;
      return;
      if ((this.mState == 1) && (paramInt == 2))
      {
        this.mHandler.removeCallbacks(this.mShowProgressbarRunnable);
        if (this.mProgressBar.getVisibility() == 0) {
          this.mProgressBar.setVisibility(8);
        }
      }
      else if ((this.mState == 1) && (paramInt == 0))
      {
        this.mHandler.removeCallbacks(this.mShowProgressbarRunnable);
        if (this.mProgressBar.getVisibility() == 0) {
          this.mProgressBar.setVisibility(8);
        }
        this.mDownloadIcon.setAnimation(null);
        this.mDownloadIcon.setVisibility(0);
      }
    }
  }
  
  private final class HideIconAfterEndAnimationListener
    implements Animation.AnimationListener
  {
    private HideIconAfterEndAnimationListener() {}
    
    public final void onAnimationEnd(Animation paramAnimation)
    {
      AppScreenshot.this.mDownloadIcon.setVisibility(8);
    }
    
    public final void onAnimationRepeat(Animation paramAnimation) {}
    
    public final void onAnimationStart(Animation paramAnimation) {}
  }
  
  private final class ShowProgressBarRunnable
    implements Runnable
  {
    private ShowProgressBarRunnable() {}
    
    public final void run()
    {
      AppScreenshot.this.mProgressBar.setVisibility(0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AppScreenshot
 * JD-Core Version:    0.7.0.1
 */