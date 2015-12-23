package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import java.util.Arrays;

public final class ViewDragHelper
{
  private static final Interpolator sInterpolator = new Interpolator()
  {
    public final float getInterpolation(float paramAnonymousFloat)
    {
      float f = paramAnonymousFloat - 1.0F;
      return 1.0F + f * (f * (f * (f * f)));
    }
  };
  private int mActivePointerId = -1;
  private final Callback mCallback;
  View mCapturedView;
  public int mDragState;
  private int[] mEdgeDragsInProgress;
  private int[] mEdgeDragsLocked;
  int mEdgeSize;
  private int[] mInitialEdgesTouched;
  float[] mInitialMotionX;
  float[] mInitialMotionY;
  float[] mLastMotionX;
  float[] mLastMotionY;
  private float mMaxVelocity;
  float mMinVelocity;
  private final ViewGroup mParentView;
  int mPointersDown;
  private boolean mReleaseInProgress;
  private ScrollerCompat mScroller;
  private final Runnable mSetIdleRunnable = new Runnable()
  {
    public final void run()
    {
      ViewDragHelper.this.setDragState(0);
    }
  };
  int mTouchSlop;
  int mTrackingEdges;
  private VelocityTracker mVelocityTracker;
  
  private ViewDragHelper(Context paramContext, ViewGroup paramViewGroup, Callback paramCallback)
  {
    if (paramViewGroup == null) {
      throw new IllegalArgumentException("Parent view may not be null");
    }
    if (paramCallback == null) {
      throw new IllegalArgumentException("Callback may not be null");
    }
    this.mParentView = paramViewGroup;
    this.mCallback = paramCallback;
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
    this.mEdgeSize = ((int)(0.5F + 20.0F * paramContext.getResources().getDisplayMetrics().density));
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMaxVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mMinVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mScroller = ScrollerCompat.create(paramContext, sInterpolator);
  }
  
  private boolean checkNewEdgeDrag(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
  {
    float f1 = Math.abs(paramFloat1);
    float f2 = Math.abs(paramFloat2);
    if (((paramInt2 & this.mInitialEdgesTouched[paramInt1]) != paramInt2) || ((paramInt2 & this.mTrackingEdges) == 0) || ((paramInt2 & this.mEdgeDragsLocked[paramInt1]) == paramInt2) || ((paramInt2 & this.mEdgeDragsInProgress[paramInt1]) == paramInt2) || ((f1 <= this.mTouchSlop) && (f2 <= this.mTouchSlop))) {}
    while (((paramInt2 & this.mEdgeDragsInProgress[paramInt1]) != 0) || (f1 <= this.mTouchSlop)) {
      return false;
    }
    return true;
  }
  
  private boolean checkTouchSlop$17e2abff(View paramView, float paramFloat)
  {
    if (paramView == null) {}
    for (;;)
    {
      return false;
      if (this.mCallback.getViewHorizontalDragRange(paramView) > 0) {}
      for (int i = 1; (i != 0) && (Math.abs(paramFloat) > this.mTouchSlop); i = 0) {
        return true;
      }
    }
  }
  
  private static float clampMag(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f = Math.abs(paramFloat1);
    if (f < paramFloat2) {
      paramFloat3 = 0.0F;
    }
    do
    {
      return paramFloat3;
      if (f <= paramFloat3) {
        break;
      }
    } while (paramFloat1 > 0.0F);
    return -paramFloat3;
    return paramFloat1;
  }
  
  private static int clampMag(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = Math.abs(paramInt1);
    if (i < paramInt2) {
      paramInt3 = 0;
    }
    do
    {
      return paramInt3;
      if (i <= paramInt3) {
        break;
      }
    } while (paramInt1 > 0);
    return -paramInt3;
    return paramInt1;
  }
  
  private void clearMotionHistory(int paramInt)
  {
    if (this.mInitialMotionX == null) {
      return;
    }
    this.mInitialMotionX[paramInt] = 0.0F;
    this.mInitialMotionY[paramInt] = 0.0F;
    this.mLastMotionX[paramInt] = 0.0F;
    this.mLastMotionY[paramInt] = 0.0F;
    this.mInitialEdgesTouched[paramInt] = 0;
    this.mEdgeDragsInProgress[paramInt] = 0;
    this.mEdgeDragsLocked[paramInt] = 0;
    this.mPointersDown &= (0xFFFFFFFF ^ 1 << paramInt);
  }
  
  private int computeAxisDuration(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 0) {
      return 0;
    }
    int i = this.mParentView.getWidth();
    int j = i / 2;
    float f1 = Math.min(1.0F, Math.abs(paramInt1) / i);
    float f2 = j + j * (float)Math.sin((float)(0.47123891676382D * (f1 - 0.5F)));
    int k = Math.abs(paramInt2);
    if (k > 0) {}
    for (int m = 4 * Math.round(1000.0F * Math.abs(f2 / k));; m = (int)(256.0F * (1.0F + Math.abs(paramInt1) / paramInt3))) {
      return Math.min(m, 600);
    }
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, float paramFloat, Callback paramCallback)
  {
    ViewDragHelper localViewDragHelper = create(paramViewGroup, paramCallback);
    localViewDragHelper.mTouchSlop = ((int)(localViewDragHelper.mTouchSlop * (1.0F / paramFloat)));
    return localViewDragHelper;
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, Callback paramCallback)
  {
    return new ViewDragHelper(paramViewGroup.getContext(), paramViewGroup, paramCallback);
  }
  
  private void dispatchViewReleased$2548a35(float paramFloat)
  {
    this.mReleaseInProgress = true;
    this.mCallback.onViewReleased$17e2ac03(this.mCapturedView, paramFloat);
    this.mReleaseInProgress = false;
    if (this.mDragState == 1) {
      setDragState(0);
    }
  }
  
  private boolean forceSettleCapturedViewAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mCapturedView.getLeft();
    int j = this.mCapturedView.getTop();
    int k = paramInt1 - i;
    int m = paramInt2 - j;
    if ((k == 0) && (m == 0))
    {
      this.mScroller.abortAnimation();
      setDragState(0);
      return false;
    }
    View localView = this.mCapturedView;
    int n = clampMag(paramInt3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    int i1 = clampMag(paramInt4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    int i2 = Math.abs(k);
    int i3 = Math.abs(m);
    int i4 = Math.abs(n);
    int i5 = Math.abs(i1);
    int i6 = i4 + i5;
    int i7 = i2 + i3;
    float f1;
    if (n != 0)
    {
      f1 = i4 / i6;
      if (i1 == 0) {
        break label245;
      }
    }
    label245:
    for (float f2 = i5 / i6;; f2 = i3 / i7)
    {
      int i8 = computeAxisDuration(k, n, this.mCallback.getViewHorizontalDragRange(localView));
      int i9 = computeAxisDuration(m, i1, 0);
      int i10 = (int)(f1 * i8 + f2 * i9);
      this.mScroller.startScroll(i, j, k, m, i10);
      setDragState(2);
      return true;
      f1 = i2 / i7;
      break;
    }
  }
  
  private void releaseViewForPointerUp()
  {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
    float f = clampMag(VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity);
    clampMag(VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity);
    dispatchViewReleased$2548a35(f);
  }
  
  private void reportNewEdgeDrags(float paramFloat1, float paramFloat2, int paramInt)
  {
    boolean bool = checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 1);
    int i = 0;
    if (bool) {
      i = 1;
    }
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 4)) {
      i |= 0x4;
    }
    if (checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 2)) {
      i |= 0x2;
    }
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 8)) {
      i |= 0x8;
    }
    if (i != 0)
    {
      int[] arrayOfInt = this.mEdgeDragsInProgress;
      arrayOfInt[paramInt] = (i | arrayOfInt[paramInt]);
      this.mCallback.onEdgeDragStarted(i, paramInt);
    }
  }
  
  private void saveInitialMotion(float paramFloat1, float paramFloat2, int paramInt)
  {
    if ((this.mInitialMotionX == null) || (this.mInitialMotionX.length <= paramInt))
    {
      float[] arrayOfFloat1 = new float[paramInt + 1];
      float[] arrayOfFloat2 = new float[paramInt + 1];
      float[] arrayOfFloat3 = new float[paramInt + 1];
      float[] arrayOfFloat4 = new float[paramInt + 1];
      int[] arrayOfInt1 = new int[paramInt + 1];
      int[] arrayOfInt2 = new int[paramInt + 1];
      int[] arrayOfInt3 = new int[paramInt + 1];
      if (this.mInitialMotionX != null)
      {
        System.arraycopy(this.mInitialMotionX, 0, arrayOfFloat1, 0, this.mInitialMotionX.length);
        System.arraycopy(this.mInitialMotionY, 0, arrayOfFloat2, 0, this.mInitialMotionY.length);
        System.arraycopy(this.mLastMotionX, 0, arrayOfFloat3, 0, this.mLastMotionX.length);
        System.arraycopy(this.mLastMotionY, 0, arrayOfFloat4, 0, this.mLastMotionY.length);
        System.arraycopy(this.mInitialEdgesTouched, 0, arrayOfInt1, 0, this.mInitialEdgesTouched.length);
        System.arraycopy(this.mEdgeDragsInProgress, 0, arrayOfInt2, 0, this.mEdgeDragsInProgress.length);
        System.arraycopy(this.mEdgeDragsLocked, 0, arrayOfInt3, 0, this.mEdgeDragsLocked.length);
      }
      this.mInitialMotionX = arrayOfFloat1;
      this.mInitialMotionY = arrayOfFloat2;
      this.mLastMotionX = arrayOfFloat3;
      this.mLastMotionY = arrayOfFloat4;
      this.mInitialEdgesTouched = arrayOfInt1;
      this.mEdgeDragsInProgress = arrayOfInt2;
      this.mEdgeDragsLocked = arrayOfInt3;
    }
    float[] arrayOfFloat5 = this.mInitialMotionX;
    this.mLastMotionX[paramInt] = paramFloat1;
    arrayOfFloat5[paramInt] = paramFloat1;
    float[] arrayOfFloat6 = this.mInitialMotionY;
    this.mLastMotionY[paramInt] = paramFloat2;
    arrayOfFloat6[paramInt] = paramFloat2;
    int[] arrayOfInt4 = this.mInitialEdgesTouched;
    int i = (int)paramFloat1;
    int j = (int)paramFloat2;
    int k = this.mParentView.getLeft() + this.mEdgeSize;
    int m = 0;
    if (i < k) {
      m = 1;
    }
    if (j < this.mParentView.getTop() + this.mEdgeSize) {
      m |= 0x4;
    }
    if (i > this.mParentView.getRight() - this.mEdgeSize) {
      m |= 0x2;
    }
    if (j > this.mParentView.getBottom() - this.mEdgeSize) {
      m |= 0x8;
    }
    arrayOfInt4[paramInt] = m;
    this.mPointersDown |= 1 << paramInt;
  }
  
  private void saveLastMotion(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getPointerCount(paramMotionEvent);
    for (int j = 0; j < i; j++)
    {
      int k = MotionEventCompat.getPointerId(paramMotionEvent, j);
      float f1 = MotionEventCompat.getX(paramMotionEvent, j);
      float f2 = MotionEventCompat.getY(paramMotionEvent, j);
      this.mLastMotionX[k] = f1;
      this.mLastMotionY[k] = f2;
    }
  }
  
  private boolean tryCaptureViewForDrag(View paramView, int paramInt)
  {
    if ((paramView == this.mCapturedView) && (this.mActivePointerId == paramInt)) {
      return true;
    }
    if ((paramView != null) && (this.mCallback.tryCaptureView$5359dc96(paramView)))
    {
      this.mActivePointerId = paramInt;
      captureChildView(paramView, paramInt);
      return true;
    }
    return false;
  }
  
  public final void cancel()
  {
    this.mActivePointerId = -1;
    if (this.mInitialMotionX != null)
    {
      Arrays.fill(this.mInitialMotionX, 0.0F);
      Arrays.fill(this.mInitialMotionY, 0.0F);
      Arrays.fill(this.mLastMotionX, 0.0F);
      Arrays.fill(this.mLastMotionY, 0.0F);
      Arrays.fill(this.mInitialEdgesTouched, 0);
      Arrays.fill(this.mEdgeDragsInProgress, 0);
      Arrays.fill(this.mEdgeDragsLocked, 0);
      this.mPointersDown = 0;
    }
    if (this.mVelocityTracker != null)
    {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  public final void captureChildView(View paramView, int paramInt)
  {
    if (paramView.getParent() != this.mParentView) {
      throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
    }
    this.mCapturedView = paramView;
    this.mActivePointerId = paramInt;
    this.mCallback.onViewCaptured$5359dc9a(paramView);
    setDragState(1);
  }
  
  public final boolean continueSettling$138603()
  {
    if (this.mDragState == 2)
    {
      boolean bool = this.mScroller.computeScrollOffset();
      int i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      int k = i - this.mCapturedView.getLeft();
      int m = j - this.mCapturedView.getTop();
      if (k != 0) {
        this.mCapturedView.offsetLeftAndRight(k);
      }
      if (m != 0) {
        this.mCapturedView.offsetTopAndBottom(m);
      }
      if ((k != 0) || (m != 0)) {
        this.mCallback.onViewPositionChanged$5b6f797d(this.mCapturedView, i);
      }
      if ((bool) && (i == this.mScroller.getFinalX()) && (j == this.mScroller.getFinalY()))
      {
        this.mScroller.abortAnimation();
        bool = false;
      }
      if (!bool) {
        this.mParentView.post(this.mSetIdleRunnable);
      }
    }
    return this.mDragState == 2;
  }
  
  public final View findTopChildUnder(int paramInt1, int paramInt2)
  {
    for (int i = -1 + this.mParentView.getChildCount(); i >= 0; i--)
    {
      View localView = this.mParentView.getChildAt(i);
      if ((paramInt1 >= localView.getLeft()) && (paramInt1 < localView.getRight()) && (paramInt2 >= localView.getTop()) && (paramInt2 < localView.getBottom())) {
        return localView;
      }
    }
    return null;
  }
  
  public final void processTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    int j = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (i == 0) {
      cancel();
    }
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (i)
    {
    case 4: 
    default: 
    case 0: 
    case 5: 
      int i13;
      float f8;
      float f9;
      do
      {
        int i17;
        do
        {
          return;
          float f10 = paramMotionEvent.getX();
          float f11 = paramMotionEvent.getY();
          i17 = MotionEventCompat.getPointerId(paramMotionEvent, 0);
          View localView3 = findTopChildUnder((int)f10, (int)f11);
          saveInitialMotion(f10, f11, i17);
          tryCaptureViewForDrag(localView3, i17);
        } while ((this.mInitialEdgesTouched[i17] & this.mTrackingEdges) == 0);
        this.mCallback.onEdgeTouched$255f295();
        return;
        i13 = MotionEventCompat.getPointerId(paramMotionEvent, j);
        f8 = MotionEventCompat.getX(paramMotionEvent, j);
        f9 = MotionEventCompat.getY(paramMotionEvent, j);
        saveInitialMotion(f8, f9, i13);
        if (this.mDragState != 0) {
          break;
        }
        tryCaptureViewForDrag(findTopChildUnder((int)f8, (int)f9), i13);
      } while ((this.mInitialEdgesTouched[i13] & this.mTrackingEdges) == 0);
      this.mCallback.onEdgeTouched$255f295();
      return;
      int i14 = (int)f8;
      int i15 = (int)f9;
      View localView2 = this.mCapturedView;
      if ((localView2 != null) && (i14 >= localView2.getLeft()) && (i14 < localView2.getRight()) && (i15 >= localView2.getTop()) && (i15 < localView2.getBottom())) {}
      for (int i16 = 1; i16 != 0; i16 = 0)
      {
        tryCaptureViewForDrag(this.mCapturedView, i13);
        return;
      }
    case 2: 
      if (this.mDragState == 1)
      {
        int i6 = MotionEventCompat.findPointerIndex(paramMotionEvent, this.mActivePointerId);
        float f6 = MotionEventCompat.getX(paramMotionEvent, i6);
        float f7 = MotionEventCompat.getY(paramMotionEvent, i6);
        int i7 = (int)(f6 - this.mLastMotionX[this.mActivePointerId]);
        int i8 = (int)(f7 - this.mLastMotionY[this.mActivePointerId]);
        int i9 = i7 + this.mCapturedView.getLeft();
        this.mCapturedView.getTop();
        int i10 = this.mCapturedView.getLeft();
        int i11 = this.mCapturedView.getTop();
        if (i7 != 0)
        {
          i9 = this.mCallback.clampViewPositionHorizontal$17e143b0(this.mCapturedView, i9);
          this.mCapturedView.offsetLeftAndRight(i9 - i10);
        }
        if (i8 != 0)
        {
          int i12 = this.mCallback.clampViewPositionVertical$17e143b0(this.mCapturedView);
          this.mCapturedView.offsetTopAndBottom(i12 - i11);
        }
        if ((i7 != 0) || (i8 != 0)) {
          this.mCallback.onViewPositionChanged$5b6f797d(this.mCapturedView, i9);
        }
        saveLastMotion(paramMotionEvent);
        return;
      }
      int i3 = MotionEventCompat.getPointerCount(paramMotionEvent);
      for (int i4 = 0; i4 < i3; i4++)
      {
        int i5 = MotionEventCompat.getPointerId(paramMotionEvent, i4);
        float f3 = MotionEventCompat.getX(paramMotionEvent, i4);
        float f4 = MotionEventCompat.getY(paramMotionEvent, i4);
        float f5 = f3 - this.mInitialMotionX[i5];
        reportNewEdgeDrags(f5, f4 - this.mInitialMotionY[i5], i5);
        if (this.mDragState == 1) {
          break;
        }
        View localView1 = findTopChildUnder((int)f3, (int)f4);
        if ((checkTouchSlop$17e2abff(localView1, f5)) && (tryCaptureViewForDrag(localView1, i5))) {
          break;
        }
      }
      saveLastMotion(paramMotionEvent);
      return;
    case 6: 
      int k = MotionEventCompat.getPointerId(paramMotionEvent, j);
      int m;
      int n;
      if ((this.mDragState == 1) && (k == this.mActivePointerId))
      {
        m = -1;
        n = MotionEventCompat.getPointerCount(paramMotionEvent);
      }
      for (int i1 = 0;; i1++) {
        if (i1 < n)
        {
          int i2 = MotionEventCompat.getPointerId(paramMotionEvent, i1);
          if (i2 != this.mActivePointerId)
          {
            float f1 = MotionEventCompat.getX(paramMotionEvent, i1);
            float f2 = MotionEventCompat.getY(paramMotionEvent, i1);
            if ((findTopChildUnder((int)f1, (int)f2) == this.mCapturedView) && (tryCaptureViewForDrag(this.mCapturedView, i2))) {
              m = this.mActivePointerId;
            }
          }
        }
        else
        {
          if (m == -1) {
            releaseViewForPointerUp();
          }
          clearMotionHistory(k);
          return;
        }
      }
    case 1: 
      if (this.mDragState == 1) {
        releaseViewForPointerUp();
      }
      cancel();
      return;
    }
    if (this.mDragState == 1) {
      dispatchViewReleased$2548a35(0.0F);
    }
    cancel();
  }
  
  final void setDragState(int paramInt)
  {
    this.mParentView.removeCallbacks(this.mSetIdleRunnable);
    if (this.mDragState != paramInt)
    {
      this.mDragState = paramInt;
      this.mCallback.onViewDragStateChanged(paramInt);
      if (this.mDragState == 0) {
        this.mCapturedView = null;
      }
    }
  }
  
  public final boolean settleCapturedViewAt(int paramInt1, int paramInt2)
  {
    if (!this.mReleaseInProgress) {
      throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }
    return forceSettleCapturedViewAt(paramInt1, paramInt2, (int)VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int)VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
  }
  
  public final boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    int j = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (i == 0) {
      cancel();
    }
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (i)
    {
    }
    while (this.mDragState == 1)
    {
      return true;
      float f7 = paramMotionEvent.getX();
      float f8 = paramMotionEvent.getY();
      int i7 = MotionEventCompat.getPointerId(paramMotionEvent, 0);
      saveInitialMotion(f7, f8, i7);
      View localView3 = findTopChildUnder((int)f7, (int)f8);
      if ((localView3 == this.mCapturedView) && (this.mDragState == 2)) {
        tryCaptureViewForDrag(localView3, i7);
      }
      if ((this.mInitialEdgesTouched[i7] & this.mTrackingEdges) != 0)
      {
        this.mCallback.onEdgeTouched$255f295();
        continue;
        int i6 = MotionEventCompat.getPointerId(paramMotionEvent, j);
        float f5 = MotionEventCompat.getX(paramMotionEvent, j);
        float f6 = MotionEventCompat.getY(paramMotionEvent, j);
        saveInitialMotion(f5, f6, i6);
        if (this.mDragState == 0)
        {
          if ((this.mInitialEdgesTouched[i6] & this.mTrackingEdges) != 0) {
            this.mCallback.onEdgeTouched$255f295();
          }
        }
        else if (this.mDragState == 2)
        {
          View localView2 = findTopChildUnder((int)f5, (int)f6);
          if (localView2 == this.mCapturedView)
          {
            tryCaptureViewForDrag(localView2, i6);
            continue;
            if ((this.mInitialMotionX != null) && (this.mInitialMotionY != null))
            {
              int k = MotionEventCompat.getPointerCount(paramMotionEvent);
              for (int m = 0;; m++)
              {
                int n;
                float f3;
                float f4;
                View localView1;
                int i1;
                if (m < k)
                {
                  n = MotionEventCompat.getPointerId(paramMotionEvent, m);
                  float f1 = MotionEventCompat.getX(paramMotionEvent, m);
                  float f2 = MotionEventCompat.getY(paramMotionEvent, m);
                  f3 = f1 - this.mInitialMotionX[n];
                  f4 = f2 - this.mInitialMotionY[n];
                  localView1 = findTopChildUnder((int)f1, (int)f2);
                  if ((localView1 == null) || (!checkTouchSlop$17e2abff(localView1, f3))) {
                    break label484;
                  }
                  i1 = 1;
                  label399:
                  if (i1 == 0) {
                    break label490;
                  }
                  int i2 = localView1.getLeft();
                  int i3 = i2 + (int)f3;
                  int i4 = this.mCallback.clampViewPositionHorizontal$17e143b0(localView1, i3);
                  localView1.getTop();
                  this.mCallback.clampViewPositionVertical$17e143b0(localView1);
                  int i5 = this.mCallback.getViewHorizontalDragRange(localView1);
                  if ((i5 != 0) && ((i5 <= 0) || (i4 != i2))) {
                    break label490;
                  }
                }
                label484:
                label490:
                do
                {
                  saveLastMotion(paramMotionEvent);
                  break;
                  i1 = 0;
                  break label399;
                  reportNewEdgeDrags(f3, f4, n);
                } while ((this.mDragState == 1) || ((i1 != 0) && (tryCaptureViewForDrag(localView1, n))));
              }
              clearMotionHistory(MotionEventCompat.getPointerId(paramMotionEvent, j));
              continue;
              cancel();
            }
          }
        }
      }
    }
    return false;
  }
  
  public final boolean smoothSlideViewTo(View paramView, int paramInt1, int paramInt2)
  {
    this.mCapturedView = paramView;
    this.mActivePointerId = -1;
    boolean bool = forceSettleCapturedViewAt(paramInt1, paramInt2, 0, 0);
    if ((!bool) && (this.mDragState == 0) && (this.mCapturedView != null)) {
      this.mCapturedView = null;
    }
    return bool;
  }
  
  public static abstract class Callback
  {
    public int clampViewPositionHorizontal$17e143b0(View paramView, int paramInt)
    {
      return 0;
    }
    
    public int clampViewPositionVertical$17e143b0(View paramView)
    {
      return 0;
    }
    
    public int getViewHorizontalDragRange(View paramView)
    {
      return 0;
    }
    
    public void onEdgeDragStarted(int paramInt1, int paramInt2) {}
    
    public void onEdgeTouched$255f295() {}
    
    public void onViewCaptured$5359dc9a(View paramView) {}
    
    public void onViewDragStateChanged(int paramInt) {}
    
    public void onViewPositionChanged$5b6f797d(View paramView, int paramInt) {}
    
    public void onViewReleased$17e2ac03(View paramView, float paramFloat) {}
    
    public abstract boolean tryCaptureView$5359dc96(View paramView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.ViewDragHelper
 * JD-Core Version:    0.7.0.1
 */