package android.support.v7.internal.view;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

public final class ViewPropertyAnimatorCompatSet
{
  public final ArrayList<ViewPropertyAnimatorCompat> mAnimators = new ArrayList();
  private long mDuration = -1L;
  private Interpolator mInterpolator;
  boolean mIsStarted;
  ViewPropertyAnimatorListener mListener;
  private final ViewPropertyAnimatorListenerAdapter mProxyListener = new ViewPropertyAnimatorListenerAdapter()
  {
    private int mProxyEndCount = 0;
    private boolean mProxyStarted = false;
    
    public final void onAnimationEnd(View paramAnonymousView)
    {
      int i = 1 + this.mProxyEndCount;
      this.mProxyEndCount = i;
      if (i == ViewPropertyAnimatorCompatSet.this.mAnimators.size())
      {
        if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
          ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null);
        }
        this.mProxyEndCount = 0;
        this.mProxyStarted = false;
        ViewPropertyAnimatorCompatSet.this.mIsStarted = false;
      }
    }
    
    public final void onAnimationStart(View paramAnonymousView)
    {
      if (this.mProxyStarted) {}
      do
      {
        return;
        this.mProxyStarted = true;
      } while (ViewPropertyAnimatorCompatSet.this.mListener == null);
      ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null);
    }
  };
  
  public final void cancel()
  {
    if (!this.mIsStarted) {
      return;
    }
    Iterator localIterator = this.mAnimators.iterator();
    while (localIterator.hasNext()) {
      ((ViewPropertyAnimatorCompat)localIterator.next()).cancel();
    }
    this.mIsStarted = false;
  }
  
  public final ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat)
  {
    if (!this.mIsStarted) {
      this.mAnimators.add(paramViewPropertyAnimatorCompat);
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompatSet setDuration$795ab51d()
  {
    if (!this.mIsStarted) {
      this.mDuration = 250L;
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompatSet setInterpolator(Interpolator paramInterpolator)
  {
    if (!this.mIsStarted) {
      this.mInterpolator = paramInterpolator;
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
  {
    if (!this.mIsStarted) {
      this.mListener = paramViewPropertyAnimatorListener;
    }
    return this;
  }
  
  public final void start()
  {
    if (this.mIsStarted) {
      return;
    }
    Iterator localIterator = this.mAnimators.iterator();
    while (localIterator.hasNext())
    {
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat)localIterator.next();
      if (this.mDuration >= 0L) {
        localViewPropertyAnimatorCompat.setDuration(this.mDuration);
      }
      if (this.mInterpolator != null) {
        localViewPropertyAnimatorCompat.setInterpolator(this.mInterpolator);
      }
      if (this.mListener != null) {
        localViewPropertyAnimatorCompat.setListener(this.mProxyListener);
      }
      localViewPropertyAnimatorCompat.start();
    }
    this.mIsStarted = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.ViewPropertyAnimatorCompatSet
 * JD-Core Version:    0.7.0.1
 */