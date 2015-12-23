package com.google.android.finsky.transition;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.util.ArrayMap;
import java.util.ArrayList;

@TargetApi(19)
public final class NoPauseAnimator
  extends Animator
{
  private final Animator mAnimator;
  private final ArrayMap<Animator.AnimatorListener, Animator.AnimatorListener> mListeners = new ArrayMap();
  
  public NoPauseAnimator(Animator paramAnimator)
  {
    this.mAnimator = paramAnimator;
  }
  
  public final void addListener(Animator.AnimatorListener paramAnimatorListener)
  {
    AnimatorListenerWrapper localAnimatorListenerWrapper = new AnimatorListenerWrapper(this, paramAnimatorListener);
    if (!this.mListeners.containsKey(paramAnimatorListener))
    {
      this.mListeners.put(paramAnimatorListener, localAnimatorListenerWrapper);
      this.mAnimator.addListener(localAnimatorListenerWrapper);
    }
  }
  
  public final void cancel()
  {
    this.mAnimator.cancel();
  }
  
  public final void end()
  {
    this.mAnimator.end();
  }
  
  public final long getDuration()
  {
    return this.mAnimator.getDuration();
  }
  
  public final TimeInterpolator getInterpolator()
  {
    return this.mAnimator.getInterpolator();
  }
  
  public final ArrayList<Animator.AnimatorListener> getListeners()
  {
    return new ArrayList(this.mListeners.keySet());
  }
  
  public final long getStartDelay()
  {
    return this.mAnimator.getStartDelay();
  }
  
  public final boolean isPaused()
  {
    return this.mAnimator.isPaused();
  }
  
  public final boolean isRunning()
  {
    return this.mAnimator.isRunning();
  }
  
  public final boolean isStarted()
  {
    return this.mAnimator.isStarted();
  }
  
  public final void removeAllListeners()
  {
    this.mListeners.clear();
    this.mAnimator.removeAllListeners();
  }
  
  public final void removeListener(Animator.AnimatorListener paramAnimatorListener)
  {
    Animator.AnimatorListener localAnimatorListener = (Animator.AnimatorListener)this.mListeners.get(paramAnimatorListener);
    if (localAnimatorListener != null)
    {
      this.mListeners.remove(paramAnimatorListener);
      this.mAnimator.removeListener(localAnimatorListener);
    }
  }
  
  public final Animator setDuration(long paramLong)
  {
    this.mAnimator.setDuration(paramLong);
    return this;
  }
  
  public final void setInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    this.mAnimator.setInterpolator(paramTimeInterpolator);
  }
  
  public final void setStartDelay(long paramLong)
  {
    this.mAnimator.setStartDelay(paramLong);
  }
  
  public final void setTarget(Object paramObject)
  {
    this.mAnimator.setTarget(paramObject);
  }
  
  public final void setupEndValues()
  {
    this.mAnimator.setupEndValues();
  }
  
  public final void setupStartValues()
  {
    this.mAnimator.setupStartValues();
  }
  
  public final void start()
  {
    this.mAnimator.start();
  }
  
  private static final class AnimatorListenerWrapper
    implements Animator.AnimatorListener
  {
    private final Animator mAnimator;
    private final Animator.AnimatorListener mListener;
    
    public AnimatorListenerWrapper(Animator paramAnimator, Animator.AnimatorListener paramAnimatorListener)
    {
      this.mAnimator = paramAnimator;
      this.mListener = paramAnimatorListener;
    }
    
    public final void onAnimationCancel(Animator paramAnimator)
    {
      this.mListener.onAnimationCancel(this.mAnimator);
    }
    
    public final void onAnimationEnd(Animator paramAnimator)
    {
      this.mListener.onAnimationEnd(this.mAnimator);
    }
    
    public final void onAnimationRepeat(Animator paramAnimator)
    {
      this.mListener.onAnimationRepeat(this.mAnimator);
    }
    
    public final void onAnimationStart(Animator paramAnimator)
    {
      this.mListener.onAnimationStart(this.mAnimator);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.transition.NoPauseAnimator
 * JD-Core Version:    0.7.0.1
 */