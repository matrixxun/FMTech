package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat
{
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener)
  {
    this(paramContext, paramOnGestureListener, (byte)0);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, byte paramByte)
  {
    if (Build.VERSION.SDK_INT > 17)
    {
      this.mImpl = new GestureDetectorCompatImplJellybeanMr2(paramContext, paramOnGestureListener, null);
      return;
    }
    this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, null);
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  static abstract interface GestureDetectorCompatImpl
  {
    public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
  }
  
  static final class GestureDetectorCompatImplBase
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int LONGPRESS_TIMEOUT = ;
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    private MotionEvent mCurrentDownEvent;
    private boolean mDeferConfirmSingleTap;
    private GestureDetector.OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler = new GestureHandler();
    private boolean mInLongPress;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    private final GestureDetector.OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;
    
    public GestureDetectorCompatImplBase(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      this.mListener = paramOnGestureListener;
      if ((paramOnGestureListener instanceof GestureDetector.OnDoubleTapListener)) {
        this.mDoubleTapListener = ((GestureDetector.OnDoubleTapListener)paramOnGestureListener);
      }
      if (paramContext == null) {
        throw new IllegalArgumentException("Context must not be null");
      }
      if (this.mListener == null) {
        throw new IllegalArgumentException("OnGestureListener must not be null");
      }
      this.mIsLongpressEnabled = true;
      ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
      int i = localViewConfiguration.getScaledTouchSlop();
      int j = localViewConfiguration.getScaledDoubleTapSlop();
      this.mMinimumFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
      this.mMaximumFlingVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
      this.mTouchSlopSquare = (i * i);
      this.mDoubleTapSlopSquare = (j * j);
    }
    
    public final boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      int j;
      if ((i & 0xFF) == 6)
      {
        j = 1;
        if (j == 0) {
          break label111;
        }
      }
      float f1;
      float f2;
      int m;
      label111:
      for (int k = MotionEventCompat.getActionIndex(paramMotionEvent);; k = -1)
      {
        f1 = 0.0F;
        f2 = 0.0F;
        m = MotionEventCompat.getPointerCount(paramMotionEvent);
        for (int n = 0; n < m; n++) {
          if (k != n)
          {
            f1 += MotionEventCompat.getX(paramMotionEvent, n);
            f2 += MotionEventCompat.getY(paramMotionEvent, n);
          }
        }
        j = 0;
        break;
      }
      int i1;
      float f3;
      float f4;
      boolean bool1;
      if (j != 0)
      {
        i1 = m - 1;
        f3 = f1 / i1;
        f4 = f2 / i1;
        int i2 = i & 0xFF;
        bool1 = false;
        switch (i2)
        {
        }
      }
      label774:
      label780:
      label985:
      label1012:
      boolean bool2;
      do
      {
        float f7;
        float f8;
        boolean bool7;
        do
        {
          int i6;
          do
          {
            boolean bool5;
            do
            {
              boolean bool10;
              do
              {
                return bool1;
                i1 = m;
                break;
                this.mLastFocusX = f3;
                this.mDownFocusX = f3;
                this.mLastFocusY = f4;
                this.mDownFocusY = f4;
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
                this.mHandler.removeMessages(3);
                this.mIsDoubleTapping = false;
                this.mAlwaysInTapRegion = false;
                this.mAlwaysInBiggerTapRegion = false;
                this.mDeferConfirmSingleTap = false;
                bool10 = this.mInLongPress;
                bool1 = false;
              } while (!bool10);
              this.mInLongPress = false;
              return false;
              this.mLastFocusX = f3;
              this.mDownFocusX = f3;
              this.mLastFocusY = f4;
              this.mDownFocusY = f4;
              this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
              int i11 = MotionEventCompat.getActionIndex(paramMotionEvent);
              int i12 = MotionEventCompat.getPointerId(paramMotionEvent, i11);
              float f9 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, i12);
              float f10 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, i12);
              for (int i13 = 0;; i13++)
              {
                int i14 = i13;
                bool1 = false;
                if (i14 >= m) {
                  break;
                }
                if (i13 != i11)
                {
                  int i15 = MotionEventCompat.getPointerId(paramMotionEvent, i13);
                  if (f9 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, i15) + f10 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, i15) < 0.0F)
                  {
                    this.mVelocityTracker.clear();
                    return false;
                  }
                }
              }
              GestureDetector.OnDoubleTapListener localOnDoubleTapListener = this.mDoubleTapListener;
              boolean bool8 = false;
              int i8;
              if (localOnDoubleTapListener != null)
              {
                boolean bool9 = this.mHandler.hasMessages(3);
                if (bool9) {
                  this.mHandler.removeMessages(3);
                }
                if ((this.mCurrentDownEvent == null) || (this.mPreviousUpEvent == null) || (!bool9)) {
                  break label780;
                }
                MotionEvent localMotionEvent2 = this.mCurrentDownEvent;
                MotionEvent localMotionEvent3 = this.mPreviousUpEvent;
                if ((!this.mAlwaysInBiggerTapRegion) || (paramMotionEvent.getEventTime() - localMotionEvent3.getEventTime() > DOUBLE_TAP_TIMEOUT)) {
                  break label774;
                }
                int i9 = (int)localMotionEvent2.getX() - (int)paramMotionEvent.getX();
                int i10 = (int)localMotionEvent2.getY() - (int)paramMotionEvent.getY();
                if (i9 * i9 + i10 * i10 >= this.mDoubleTapSlopSquare) {
                  break label774;
                }
                i8 = 1;
                if (i8 == 0) {
                  break label780;
                }
                this.mIsDoubleTapping = true;
              }
              for (bool8 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);; bool8 = false)
              {
                this.mLastFocusX = f3;
                this.mDownFocusX = f3;
                this.mLastFocusY = f4;
                this.mDownFocusY = f4;
                if (this.mCurrentDownEvent != null) {
                  this.mCurrentDownEvent.recycle();
                }
                this.mCurrentDownEvent = MotionEvent.obtain(paramMotionEvent);
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInBiggerTapRegion = true;
                this.mStillDown = true;
                this.mInLongPress = false;
                this.mDeferConfirmSingleTap = false;
                if (this.mIsLongpressEnabled)
                {
                  this.mHandler.removeMessages(2);
                  this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + LONGPRESS_TIMEOUT);
                }
                this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                return bool8 | this.mListener.onDown(paramMotionEvent);
                i8 = 0;
                break;
                this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
              }
              bool5 = this.mInLongPress;
              bool1 = false;
            } while (bool5);
            f7 = this.mLastFocusX - f3;
            f8 = this.mLastFocusY - f4;
            if (this.mIsDoubleTapping) {
              return false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
            }
            if (!this.mAlwaysInTapRegion) {
              break label985;
            }
            int i4 = (int)(f3 - this.mDownFocusX);
            int i5 = (int)(f4 - this.mDownFocusY);
            i6 = i4 * i4 + i5 * i5;
            int i7 = this.mTouchSlopSquare;
            bool1 = false;
            if (i6 > i7)
            {
              bool1 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f7, f8);
              this.mLastFocusX = f3;
              this.mLastFocusY = f4;
              this.mAlwaysInTapRegion = false;
              this.mHandler.removeMessages(3);
              this.mHandler.removeMessages(1);
              this.mHandler.removeMessages(2);
            }
          } while (i6 <= this.mTouchSlopSquare);
          this.mAlwaysInBiggerTapRegion = false;
          return bool1;
          if (Math.abs(f7) >= 1.0F) {
            break label1012;
          }
          bool7 = Math.abs(f8) < 1.0F;
          bool1 = false;
        } while (bool7);
        boolean bool6 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f7, f8);
        this.mLastFocusX = f3;
        this.mLastFocusY = f4;
        return bool6;
        this.mStillDown = false;
        MotionEvent localMotionEvent1 = MotionEvent.obtain(paramMotionEvent);
        boolean bool3;
        if (this.mIsDoubleTapping) {
          bool3 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
        }
        for (;;)
        {
          if (this.mPreviousUpEvent != null) {
            this.mPreviousUpEvent.recycle();
          }
          this.mPreviousUpEvent = localMotionEvent1;
          if (this.mVelocityTracker != null)
          {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
          }
          this.mIsDoubleTapping = false;
          this.mDeferConfirmSingleTap = false;
          this.mHandler.removeMessages(1);
          this.mHandler.removeMessages(2);
          return bool3;
          if (this.mInLongPress)
          {
            this.mHandler.removeMessages(3);
            this.mInLongPress = false;
            bool3 = false;
          }
          else if (this.mAlwaysInTapRegion)
          {
            bool3 = this.mListener.onSingleTapUp(paramMotionEvent);
            if ((this.mDeferConfirmSingleTap) && (this.mDoubleTapListener != null)) {
              this.mDoubleTapListener.onSingleTapConfirmed(paramMotionEvent);
            }
          }
          else
          {
            VelocityTracker localVelocityTracker = this.mVelocityTracker;
            int i3 = MotionEventCompat.getPointerId(paramMotionEvent, 0);
            localVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            float f5 = VelocityTrackerCompat.getYVelocity(localVelocityTracker, i3);
            float f6 = VelocityTrackerCompat.getXVelocity(localVelocityTracker, i3);
            if (Math.abs(f5) <= this.mMinimumFlingVelocity)
            {
              boolean bool4 = Math.abs(f6) < this.mMinimumFlingVelocity;
              bool3 = false;
              if (!bool4) {}
            }
            else
            {
              bool3 = this.mListener.onFling(this.mCurrentDownEvent, paramMotionEvent, f6, f5);
            }
          }
        }
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        this.mIsDoubleTapping = false;
        this.mStillDown = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        bool2 = this.mInLongPress;
        bool1 = false;
      } while (!bool2);
      this.mInLongPress = false;
      return false;
    }
    
    private final class GestureHandler
      extends Handler
    {
      GestureHandler() {}
      
      public final void handleMessage(Message paramMessage)
      {
        switch (paramMessage.what)
        {
        default: 
          throw new RuntimeException("Unknown message " + paramMessage);
        case 1: 
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
        }
        do
        {
          return;
          GestureDetectorCompat.GestureDetectorCompatImplBase.access$200(GestureDetectorCompat.GestureDetectorCompatImplBase.this);
          return;
        } while (GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener == null);
        if (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown)
        {
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
          return;
        }
        GestureDetectorCompat.GestureDetectorCompatImplBase.access$502$5abea42(GestureDetectorCompat.GestureDetectorCompatImplBase.this);
      }
    }
  }
  
  static final class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private final GestureDetector mDetector;
    
    public GestureDetectorCompatImplJellybeanMr2(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      this.mDetector = new GestureDetector(paramContext, paramOnGestureListener, null);
    }
    
    public final boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return this.mDetector.onTouchEvent(paramMotionEvent);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.GestureDetectorCompat
 * JD-Core Version:    0.7.0.1
 */