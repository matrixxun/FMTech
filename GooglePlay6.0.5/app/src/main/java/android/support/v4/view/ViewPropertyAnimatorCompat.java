package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class ViewPropertyAnimatorCompat
{
  public static final ViewPropertyAnimatorCompatImpl IMPL = new BaseViewPropertyAnimatorCompatImpl();
  private Runnable mEndAction = null;
  private int mOldLayerType = -1;
  private Runnable mStartAction = null;
  public WeakReference<View> mView;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 21)
    {
      IMPL = new LollipopViewPropertyAnimatorCompatImpl();
      return;
    }
    if (i >= 19)
    {
      IMPL = new KitKatViewPropertyAnimatorCompatImpl();
      return;
    }
    if (i >= 18)
    {
      IMPL = new JBMr2ViewPropertyAnimatorCompatImpl();
      return;
    }
    if (i >= 16)
    {
      IMPL = new JBViewPropertyAnimatorCompatImpl();
      return;
    }
    if (i >= 14)
    {
      IMPL = new ICSViewPropertyAnimatorCompatImpl();
      return;
    }
  }
  
  ViewPropertyAnimatorCompat(View paramView)
  {
    this.mView = new WeakReference(paramView);
  }
  
  public final ViewPropertyAnimatorCompat alpha(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.alpha(this, localView, paramFloat);
    }
    return this;
  }
  
  public final void cancel()
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.cancel(this, localView);
    }
  }
  
  public final ViewPropertyAnimatorCompat setDuration(long paramLong)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.setDuration$65a8a4c6(localView, paramLong);
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompat setInterpolator(Interpolator paramInterpolator)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.setInterpolator$4b3df29b(localView, paramInterpolator);
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.setListener(this, localView, paramViewPropertyAnimatorListener);
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompat setStartDelay(long paramLong)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.setStartDelay$65a8a4c6(localView, paramLong);
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener paramViewPropertyAnimatorUpdateListener)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.setUpdateListener$587f161e(localView, paramViewPropertyAnimatorUpdateListener);
    }
    return this;
  }
  
  public final void start()
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.start(this, localView);
    }
  }
  
  public final ViewPropertyAnimatorCompat translationX(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.translationX(this, localView, paramFloat);
    }
    return this;
  }
  
  public final ViewPropertyAnimatorCompat translationY(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null) {
      IMPL.translationY(this, localView, paramFloat);
    }
    return this;
  }
  
  static class BaseViewPropertyAnimatorCompatImpl
    implements ViewPropertyAnimatorCompat.ViewPropertyAnimatorCompatImpl
  {
    WeakHashMap<View, Runnable> mStarterMap = null;
    
    private void postStartMessage(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
    {
      WeakHashMap localWeakHashMap = this.mStarterMap;
      Object localObject = null;
      if (localWeakHashMap != null) {
        localObject = (Runnable)this.mStarterMap.get(paramView);
      }
      if (localObject == null)
      {
        localObject = new Starter(paramViewPropertyAnimatorCompat, paramView, (byte)0);
        if (this.mStarterMap == null) {
          this.mStarterMap = new WeakHashMap();
        }
        this.mStarterMap.put(paramView, localObject);
      }
      paramView.removeCallbacks((Runnable)localObject);
      paramView.post((Runnable)localObject);
    }
    
    public void alpha(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat)
    {
      postStartMessage(paramViewPropertyAnimatorCompat, paramView);
    }
    
    public void cancel(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
    {
      postStartMessage(paramViewPropertyAnimatorCompat, paramView);
    }
    
    public long getDuration$66604b42(View paramView)
    {
      return 0L;
    }
    
    public void setDuration$65a8a4c6(View paramView, long paramLong) {}
    
    public void setInterpolator$4b3df29b(View paramView, Interpolator paramInterpolator) {}
    
    public void setListener(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
    {
      paramView.setTag(2113929216, paramViewPropertyAnimatorListener);
    }
    
    public void setStartDelay$65a8a4c6(View paramView, long paramLong) {}
    
    public void setUpdateListener$587f161e(View paramView, ViewPropertyAnimatorUpdateListener paramViewPropertyAnimatorUpdateListener) {}
    
    public void start(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
    {
      if (this.mStarterMap != null)
      {
        Runnable localRunnable = (Runnable)this.mStarterMap.get(paramView);
        if (localRunnable != null) {
          paramView.removeCallbacks(localRunnable);
        }
      }
      startAnimation(paramViewPropertyAnimatorCompat, paramView);
    }
    
    final void startAnimation(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
    {
      Object localObject = paramView.getTag(2113929216);
      boolean bool = localObject instanceof ViewPropertyAnimatorListener;
      ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
      if (bool) {
        localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
      }
      Runnable localRunnable1 = paramViewPropertyAnimatorCompat.mStartAction;
      Runnable localRunnable2 = paramViewPropertyAnimatorCompat.mEndAction;
      if (localRunnable1 != null) {
        localRunnable1.run();
      }
      if (localViewPropertyAnimatorListener != null)
      {
        localViewPropertyAnimatorListener.onAnimationStart(paramView);
        localViewPropertyAnimatorListener.onAnimationEnd(paramView);
      }
      if (localRunnable2 != null) {
        localRunnable2.run();
      }
      if (this.mStarterMap != null) {
        this.mStarterMap.remove(paramView);
      }
    }
    
    public void translationX(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat)
    {
      postStartMessage(paramViewPropertyAnimatorCompat, paramView);
    }
    
    public void translationY(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat)
    {
      postStartMessage(paramViewPropertyAnimatorCompat, paramView);
    }
    
    public void withEndAction(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, Runnable paramRunnable)
    {
      ViewPropertyAnimatorCompat.access$002(paramViewPropertyAnimatorCompat, paramRunnable);
      postStartMessage(paramViewPropertyAnimatorCompat, paramView);
    }
    
    final class Starter
      implements Runnable
    {
      WeakReference<View> mViewRef;
      ViewPropertyAnimatorCompat mVpa;
      
      private Starter(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
      {
        this.mViewRef = new WeakReference(paramView);
        this.mVpa = paramViewPropertyAnimatorCompat;
      }
      
      public final void run()
      {
        View localView = (View)this.mViewRef.get();
        if (localView != null) {
          ViewPropertyAnimatorCompat.BaseViewPropertyAnimatorCompatImpl.this.startAnimation(this.mVpa, localView);
        }
      }
    }
  }
  
  static class ICSViewPropertyAnimatorCompatImpl
    extends ViewPropertyAnimatorCompat.BaseViewPropertyAnimatorCompatImpl
  {
    WeakHashMap<View, Integer> mLayerMap = null;
    
    public final void alpha(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat)
    {
      paramView.animate().alpha(paramFloat);
    }
    
    public final void cancel(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
    {
      paramView.animate().cancel();
    }
    
    public final long getDuration$66604b42(View paramView)
    {
      return paramView.animate().getDuration();
    }
    
    public final void setDuration$65a8a4c6(View paramView, long paramLong)
    {
      paramView.animate().setDuration(paramLong);
    }
    
    public final void setInterpolator$4b3df29b(View paramView, Interpolator paramInterpolator)
    {
      paramView.animate().setInterpolator(paramInterpolator);
    }
    
    public void setListener(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
    {
      paramView.setTag(2113929216, paramViewPropertyAnimatorListener);
      ViewPropertyAnimatorCompatICS.setListener(paramView, new MyVpaListener(paramViewPropertyAnimatorCompat));
    }
    
    public final void setStartDelay$65a8a4c6(View paramView, long paramLong)
    {
      paramView.animate().setStartDelay(paramLong);
    }
    
    public final void start(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView)
    {
      paramView.animate().start();
    }
    
    public final void translationX(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat)
    {
      paramView.animate().translationX(paramFloat);
    }
    
    public final void translationY(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat)
    {
      paramView.animate().translationY(paramFloat);
    }
    
    public void withEndAction(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, Runnable paramRunnable)
    {
      ViewPropertyAnimatorCompatICS.setListener(paramView, new MyVpaListener(paramViewPropertyAnimatorCompat));
      ViewPropertyAnimatorCompat.access$002(paramViewPropertyAnimatorCompat, paramRunnable);
    }
    
    static final class MyVpaListener
      implements ViewPropertyAnimatorListener
    {
      ViewPropertyAnimatorCompat mVpa;
      
      MyVpaListener(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat)
      {
        this.mVpa = paramViewPropertyAnimatorCompat;
      }
      
      public final void onAnimationCancel(View paramView)
      {
        Object localObject = paramView.getTag(2113929216);
        boolean bool = localObject instanceof ViewPropertyAnimatorListener;
        ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
        if (bool) {
          localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
        }
        if (localViewPropertyAnimatorListener != null) {
          localViewPropertyAnimatorListener.onAnimationCancel(paramView);
        }
      }
      
      public final void onAnimationEnd(View paramView)
      {
        if (this.mVpa.mOldLayerType >= 0)
        {
          ViewCompat.setLayerType(paramView, this.mVpa.mOldLayerType, null);
          ViewPropertyAnimatorCompat.access$402$3efd0312(this.mVpa);
        }
        if (this.mVpa.mEndAction != null) {
          this.mVpa.mEndAction.run();
        }
        Object localObject = paramView.getTag(2113929216);
        boolean bool = localObject instanceof ViewPropertyAnimatorListener;
        ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
        if (bool) {
          localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
        }
        if (localViewPropertyAnimatorListener != null) {
          localViewPropertyAnimatorListener.onAnimationEnd(paramView);
        }
      }
      
      public final void onAnimationStart(View paramView)
      {
        if (this.mVpa.mOldLayerType >= 0) {
          ViewCompat.setLayerType(paramView, 2, null);
        }
        if (this.mVpa.mStartAction != null) {
          this.mVpa.mStartAction.run();
        }
        Object localObject = paramView.getTag(2113929216);
        boolean bool = localObject instanceof ViewPropertyAnimatorListener;
        ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
        if (bool) {
          localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
        }
        if (localViewPropertyAnimatorListener != null) {
          localViewPropertyAnimatorListener.onAnimationStart(paramView);
        }
      }
    }
  }
  
  static class JBMr2ViewPropertyAnimatorCompatImpl
    extends ViewPropertyAnimatorCompat.JBViewPropertyAnimatorCompatImpl
  {}
  
  static class JBViewPropertyAnimatorCompatImpl
    extends ViewPropertyAnimatorCompat.ICSViewPropertyAnimatorCompatImpl
  {
    public final void setListener(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
    {
      if (paramViewPropertyAnimatorListener != null)
      {
        paramView.animate().setListener(new ViewPropertyAnimatorCompatJB.1(paramViewPropertyAnimatorListener, paramView));
        return;
      }
      paramView.animate().setListener(null);
    }
    
    public final void withEndAction(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, Runnable paramRunnable)
    {
      paramView.animate().withEndAction(paramRunnable);
    }
  }
  
  static class KitKatViewPropertyAnimatorCompatImpl
    extends ViewPropertyAnimatorCompat.JBMr2ViewPropertyAnimatorCompatImpl
  {
    public final void setUpdateListener$587f161e(View paramView, ViewPropertyAnimatorUpdateListener paramViewPropertyAnimatorUpdateListener)
    {
      paramView.animate().setUpdateListener(new ViewPropertyAnimatorCompatKK.1(paramViewPropertyAnimatorUpdateListener, paramView));
    }
  }
  
  static final class LollipopViewPropertyAnimatorCompatImpl
    extends ViewPropertyAnimatorCompat.KitKatViewPropertyAnimatorCompatImpl
  {}
  
  public static abstract interface ViewPropertyAnimatorCompatImpl
  {
    public abstract void alpha(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat);
    
    public abstract void cancel(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView);
    
    public abstract long getDuration$66604b42(View paramView);
    
    public abstract void setDuration$65a8a4c6(View paramView, long paramLong);
    
    public abstract void setInterpolator$4b3df29b(View paramView, Interpolator paramInterpolator);
    
    public abstract void setListener(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, ViewPropertyAnimatorListener paramViewPropertyAnimatorListener);
    
    public abstract void setStartDelay$65a8a4c6(View paramView, long paramLong);
    
    public abstract void setUpdateListener$587f161e(View paramView, ViewPropertyAnimatorUpdateListener paramViewPropertyAnimatorUpdateListener);
    
    public abstract void start(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView);
    
    public abstract void translationX(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat);
    
    public abstract void translationY(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, float paramFloat);
    
    public abstract void withEndAction(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat, View paramView, Runnable paramRunnable);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewPropertyAnimatorCompat
 * JD-Core Version:    0.7.0.1
 */