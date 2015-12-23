package android.support.v4.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import android.widget.Scroller;

public final class ScrollerCompat
{
  ScrollerCompatImpl mImpl;
  Object mScroller;
  
  private ScrollerCompat(int paramInt, Context paramContext, Interpolator paramInterpolator)
  {
    if (paramInt >= 14) {
      this.mImpl = new ScrollerCompatImplIcs();
    }
    for (;;)
    {
      this.mScroller = this.mImpl.createScroller(paramContext, paramInterpolator);
      return;
      if (paramInt >= 9) {
        this.mImpl = new ScrollerCompatImplGingerbread();
      } else {
        this.mImpl = new ScrollerCompatImplBase();
      }
    }
  }
  
  ScrollerCompat(Context paramContext, Interpolator paramInterpolator)
  {
    this(Build.VERSION.SDK_INT, paramContext, paramInterpolator);
  }
  
  public static ScrollerCompat create(Context paramContext, Interpolator paramInterpolator)
  {
    return new ScrollerCompat(paramContext, paramInterpolator);
  }
  
  public final void abortAnimation()
  {
    this.mImpl.abortAnimation(this.mScroller);
  }
  
  public final boolean computeScrollOffset()
  {
    return this.mImpl.computeScrollOffset(this.mScroller);
  }
  
  public final void fling$69c647f5(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mImpl.fling(this.mScroller, 0, 0, paramInt1, paramInt2, paramInt3, paramInt4, -2147483648, 2147483647);
  }
  
  public final float getCurrVelocity()
  {
    return this.mImpl.getCurrVelocity(this.mScroller);
  }
  
  public final int getCurrX()
  {
    return this.mImpl.getCurrX(this.mScroller);
  }
  
  public final int getCurrY()
  {
    return this.mImpl.getCurrY(this.mScroller);
  }
  
  public final int getFinalX()
  {
    return this.mImpl.getFinalX(this.mScroller);
  }
  
  public final int getFinalY()
  {
    return this.mImpl.getFinalY(this.mScroller);
  }
  
  public final boolean isFinished()
  {
    return this.mImpl.isFinished(this.mScroller);
  }
  
  public final boolean springBack$6046c8d9(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.mImpl.springBack(this.mScroller, paramInt1, paramInt2, 0, 0, 0, paramInt3);
  }
  
  public final void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.mImpl.startScroll(this.mScroller, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  static abstract interface ScrollerCompatImpl
  {
    public abstract void abortAnimation(Object paramObject);
    
    public abstract boolean computeScrollOffset(Object paramObject);
    
    public abstract Object createScroller(Context paramContext, Interpolator paramInterpolator);
    
    public abstract void fling(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
    
    public abstract void fling(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);
    
    public abstract float getCurrVelocity(Object paramObject);
    
    public abstract int getCurrX(Object paramObject);
    
    public abstract int getCurrY(Object paramObject);
    
    public abstract int getFinalX(Object paramObject);
    
    public abstract int getFinalY(Object paramObject);
    
    public abstract boolean isFinished(Object paramObject);
    
    public abstract boolean springBack(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
    
    public abstract void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  }
  
  static final class ScrollerCompatImplBase
    implements ScrollerCompat.ScrollerCompatImpl
  {
    public final void abortAnimation(Object paramObject)
    {
      ((Scroller)paramObject).abortAnimation();
    }
    
    public final boolean computeScrollOffset(Object paramObject)
    {
      return ((Scroller)paramObject).computeScrollOffset();
    }
    
    public final Object createScroller(Context paramContext, Interpolator paramInterpolator)
    {
      if (paramInterpolator != null) {
        return new Scroller(paramContext, paramInterpolator);
      }
      return new Scroller(paramContext);
    }
    
    public final void fling(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
    {
      ((Scroller)paramObject).fling(0, 0, paramInt3, paramInt4, paramInt5, paramInt6, -2147483648, 2147483647);
    }
    
    public final void fling(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
    {
      ((Scroller)paramObject).fling(paramInt1, paramInt2, 0, paramInt4, 0, 0, 0, paramInt8);
    }
    
    public final float getCurrVelocity(Object paramObject)
    {
      return 0.0F;
    }
    
    public final int getCurrX(Object paramObject)
    {
      return ((Scroller)paramObject).getCurrX();
    }
    
    public final int getCurrY(Object paramObject)
    {
      return ((Scroller)paramObject).getCurrY();
    }
    
    public final int getFinalX(Object paramObject)
    {
      return ((Scroller)paramObject).getFinalX();
    }
    
    public final int getFinalY(Object paramObject)
    {
      return ((Scroller)paramObject).getFinalY();
    }
    
    public final boolean isFinished(Object paramObject)
    {
      return ((Scroller)paramObject).isFinished();
    }
    
    public final boolean springBack(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      return false;
    }
    
    public final void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      ((Scroller)paramObject).startScroll(paramInt1, paramInt2, 0, paramInt4);
    }
    
    public final void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      ((Scroller)paramObject).startScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
  }
  
  static class ScrollerCompatImplGingerbread
    implements ScrollerCompat.ScrollerCompatImpl
  {
    public final void abortAnimation(Object paramObject)
    {
      ((OverScroller)paramObject).abortAnimation();
    }
    
    public final boolean computeScrollOffset(Object paramObject)
    {
      return ((OverScroller)paramObject).computeScrollOffset();
    }
    
    public final Object createScroller(Context paramContext, Interpolator paramInterpolator)
    {
      if (paramInterpolator != null) {
        return new OverScroller(paramContext, paramInterpolator);
      }
      return new OverScroller(paramContext);
    }
    
    public final void fling(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
    {
      ((OverScroller)paramObject).fling(0, 0, paramInt3, paramInt4, paramInt5, paramInt6, -2147483648, 2147483647);
    }
    
    public final void fling(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
    {
      ((OverScroller)paramObject).fling(paramInt1, paramInt2, 0, paramInt4, 0, 0, 0, paramInt8, 0, paramInt10);
    }
    
    public float getCurrVelocity(Object paramObject)
    {
      return 0.0F;
    }
    
    public final int getCurrX(Object paramObject)
    {
      return ((OverScroller)paramObject).getCurrX();
    }
    
    public final int getCurrY(Object paramObject)
    {
      return ((OverScroller)paramObject).getCurrY();
    }
    
    public final int getFinalX(Object paramObject)
    {
      return ((OverScroller)paramObject).getFinalX();
    }
    
    public final int getFinalY(Object paramObject)
    {
      return ((OverScroller)paramObject).getFinalY();
    }
    
    public final boolean isFinished(Object paramObject)
    {
      return ((OverScroller)paramObject).isFinished();
    }
    
    public final boolean springBack(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      return ((OverScroller)paramObject).springBack(paramInt1, paramInt2, 0, 0, 0, paramInt6);
    }
    
    public final void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      ((OverScroller)paramObject).startScroll(paramInt1, paramInt2, 0, paramInt4);
    }
    
    public final void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      ((OverScroller)paramObject).startScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
  }
  
  static final class ScrollerCompatImplIcs
    extends ScrollerCompat.ScrollerCompatImplGingerbread
  {
    public final float getCurrVelocity(Object paramObject)
    {
      return ((OverScroller)paramObject).getCurrVelocity();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.ScrollerCompat
 * JD-Core Version:    0.7.0.1
 */