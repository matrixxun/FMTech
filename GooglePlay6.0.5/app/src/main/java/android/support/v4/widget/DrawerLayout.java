package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout
  extends ViewGroup
  implements DrawerLayoutImpl
{
  private static final boolean CAN_HIDE_DESCENDANTS;
  static final DrawerLayoutCompatImpl IMPL;
  private static final int[] LAYOUT_ATTRS;
  public static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
  private final ChildAccessibilityDelegate mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
  boolean mChildrenCanceledTouch;
  private boolean mDisallowInterceptRequested;
  private boolean mDrawStatusBarBackground;
  private float mDrawerElevation;
  int mDrawerState;
  private boolean mFirstLayout = true;
  private boolean mInLayout;
  private float mInitialMotionX;
  private float mInitialMotionY;
  private Object mLastInsets;
  private final ViewDragCallback mLeftCallback;
  final ViewDragHelper mLeftDragger;
  DrawerListener mListener;
  private int mLockModeLeft;
  private int mLockModeRight;
  private int mMinDrawerMargin;
  private final ArrayList<View> mNonDrawerViews;
  private final ViewDragCallback mRightCallback;
  final ViewDragHelper mRightDragger;
  private int mScrimColor = -1728053248;
  private float mScrimOpacity;
  private Paint mScrimPaint = new Paint();
  public Drawable mShadowEnd = null;
  public Drawable mShadowLeft = null;
  private Drawable mShadowLeftResolved;
  public Drawable mShadowRight = null;
  private Drawable mShadowRightResolved;
  public Drawable mShadowStart = null;
  private Drawable mStatusBarBackground;
  public CharSequence mTitleLeft;
  public CharSequence mTitleRight;
  
  static
  {
    boolean bool1 = true;
    int[] arrayOfInt = new int[bool1];
    arrayOfInt[0] = 16842931;
    LAYOUT_ATTRS = arrayOfInt;
    boolean bool2;
    if (Build.VERSION.SDK_INT >= 19)
    {
      bool2 = bool1;
      CAN_HIDE_DESCENDANTS = bool2;
      if (Build.VERSION.SDK_INT < 21) {
        break label65;
      }
    }
    for (;;)
    {
      SET_DRAWER_SHADOW_FROM_ELEVATION = bool1;
      if (Build.VERSION.SDK_INT < 21) {
        break label70;
      }
      IMPL = new DrawerLayoutCompatImplApi21();
      return;
      bool2 = false;
      break;
      label65:
      bool1 = false;
    }
    label70:
    IMPL = new DrawerLayoutCompatImplBase();
  }
  
  public DrawerLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setDescendantFocusability(262144);
    float f1 = getResources().getDisplayMetrics().density;
    this.mMinDrawerMargin = ((int)(0.5F + 64.0F * f1));
    float f2 = 400.0F * f1;
    this.mLeftCallback = new ViewDragCallback(3);
    this.mRightCallback = new ViewDragCallback(5);
    this.mLeftDragger = ViewDragHelper.create(this, 1.0F, this.mLeftCallback);
    this.mLeftDragger.mTrackingEdges = 1;
    this.mLeftDragger.mMinVelocity = f2;
    this.mLeftCallback.mDragger = this.mLeftDragger;
    this.mRightDragger = ViewDragHelper.create(this, 1.0F, this.mRightCallback);
    this.mRightDragger.mTrackingEdges = 2;
    this.mRightDragger.mMinVelocity = f2;
    this.mRightCallback.mDragger = this.mRightDragger;
    setFocusableInTouchMode(true);
    ViewCompat.setImportantForAccessibility(this, 1);
    ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
    ViewGroupCompat.setMotionEventSplittingEnabled$4d3af60(this);
    if (ViewCompat.getFitsSystemWindows(this))
    {
      IMPL.configureApplyInsets(this);
      this.mStatusBarBackground = IMPL.getDefaultStatusBarBackground(paramContext);
    }
    this.mDrawerElevation = (10.0F * f1);
    this.mNonDrawerViews = new ArrayList();
  }
  
  private void closeDrawers(boolean paramBoolean)
  {
    boolean bool = false;
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if ((isDrawerView(localView)) && ((!paramBoolean) || (localLayoutParams.isPeeking)))
      {
        int k = localView.getWidth();
        if (!checkDrawerViewAbsoluteGravity(localView, 3)) {
          break label103;
        }
        bool |= this.mLeftDragger.smoothSlideViewTo(localView, -k, localView.getTop());
      }
      for (;;)
      {
        localLayoutParams.isPeeking = false;
        j++;
        break;
        label103:
        bool |= this.mRightDragger.smoothSlideViewTo(localView, getWidth(), localView.getTop());
      }
    }
    this.mLeftCallback.removeCallbacks();
    this.mRightCallback.removeCallbacks();
    if (bool) {
      invalidate();
    }
  }
  
  private View findOpenDrawer()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (((LayoutParams)localView.getLayoutParams()).knownOpen) {
        return localView;
      }
    }
    return null;
  }
  
  private View findVisibleDrawer()
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (isDrawerView(localView))
      {
        if (!isDrawerView(localView)) {
          throw new IllegalArgumentException("View " + localView + " is not a drawer");
        }
        if (((LayoutParams)localView.getLayoutParams()).onScreen > 0.0F) {}
        for (int k = 1; k != 0; k = 0) {
          return localView;
        }
      }
    }
    return null;
  }
  
  static float getDrawerViewOffset(View paramView)
  {
    return ((LayoutParams)paramView.getLayoutParams()).onScreen;
  }
  
  private static boolean isContentView(View paramView)
  {
    return ((LayoutParams)paramView.getLayoutParams()).gravity == 0;
  }
  
  public static boolean isDrawerOpen(View paramView)
  {
    if (!isDrawerView(paramView)) {
      throw new IllegalArgumentException("View " + paramView + " is not a drawer");
    }
    return ((LayoutParams)paramView.getLayoutParams()).knownOpen;
  }
  
  static boolean isDrawerView(View paramView)
  {
    return (0x7 & GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(paramView))) != 0;
  }
  
  private static boolean mirror(Drawable paramDrawable, int paramInt)
  {
    if ((paramDrawable == null) || (!DrawableCompat.isAutoMirrored(paramDrawable))) {
      return false;
    }
    DrawableCompat.setLayoutDirection(paramDrawable, paramInt);
    return true;
  }
  
  private void setDrawerLockMode(int paramInt1, int paramInt2)
  {
    int i = GravityCompat.getAbsoluteGravity(paramInt2, ViewCompat.getLayoutDirection(this));
    ViewDragHelper localViewDragHelper;
    if (i == 3)
    {
      this.mLockModeLeft = paramInt1;
      if (paramInt1 != 0)
      {
        if (i != 3) {
          break label78;
        }
        localViewDragHelper = this.mLeftDragger;
        label34:
        localViewDragHelper.cancel();
      }
      switch (paramInt1)
      {
      }
    }
    label78:
    View localView1;
    do
    {
      View localView2;
      do
      {
        return;
        if (i != 5) {
          break;
        }
        this.mLockModeRight = paramInt1;
        break;
        localViewDragHelper = this.mRightDragger;
        break label34;
        localView2 = findDrawerWithGravity(i);
      } while (localView2 == null);
      openDrawer(localView2);
      return;
      localView1 = findDrawerWithGravity(i);
    } while (localView1 == null);
    closeDrawer(localView1);
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    if (getDescendantFocusability() == 393216) {
      return;
    }
    int i = getChildCount();
    int j = 0;
    int k = 0;
    if (k < i)
    {
      View localView2 = getChildAt(k);
      if (isDrawerView(localView2)) {
        if (isDrawerOpen(localView2))
        {
          j = 1;
          localView2.addFocusables(paramArrayList, paramInt1, paramInt2);
        }
      }
      for (;;)
      {
        k++;
        break;
        this.mNonDrawerViews.add(localView2);
      }
    }
    if (j == 0)
    {
      int m = this.mNonDrawerViews.size();
      for (int n = 0; n < m; n++)
      {
        View localView1 = (View)this.mNonDrawerViews.get(n);
        if (localView1.getVisibility() == 0) {
          localView1.addFocusables(paramArrayList, paramInt1, paramInt2);
        }
      }
    }
    this.mNonDrawerViews.clear();
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    if ((findOpenDrawer() != null) || (isDrawerView(paramView))) {
      ViewCompat.setImportantForAccessibility(paramView, 4);
    }
    for (;;)
    {
      if (!CAN_HIDE_DESCENDANTS) {
        ViewCompat.setAccessibilityDelegate(paramView, this.mChildAccessibilityDelegate);
      }
      return;
      ViewCompat.setImportantForAccessibility(paramView, 1);
    }
  }
  
  final boolean checkDrawerViewAbsoluteGravity(View paramView, int paramInt)
  {
    return (paramInt & getDrawerViewAbsoluteGravity(paramView)) == paramInt;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams));
  }
  
  public final void closeDrawer(View paramView)
  {
    if (!isDrawerView(paramView)) {
      throw new IllegalArgumentException("View " + paramView + " is not a sliding drawer");
    }
    if (this.mFirstLayout)
    {
      LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      localLayoutParams.onScreen = 0.0F;
      localLayoutParams.knownOpen = false;
    }
    for (;;)
    {
      invalidate();
      return;
      if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
        this.mLeftDragger.smoothSlideViewTo(paramView, -paramView.getWidth(), paramView.getTop());
      } else {
        this.mRightDragger.smoothSlideViewTo(paramView, getWidth(), paramView.getTop());
      }
    }
  }
  
  public void computeScroll()
  {
    int i = getChildCount();
    float f = 0.0F;
    for (int j = 0; j < i; j++) {
      f = Math.max(f, ((LayoutParams)getChildAt(j).getLayoutParams()).onScreen);
    }
    this.mScrimOpacity = f;
    if ((this.mLeftDragger.continueSettling$138603() | this.mRightDragger.continueSettling$138603())) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    int i = getHeight();
    boolean bool1 = isContentView(paramView);
    int j = getWidth();
    int k = paramCanvas.save();
    int m = 0;
    if (bool1)
    {
      int i8 = getChildCount();
      int i9 = 0;
      if (i9 < i8)
      {
        View localView = getChildAt(i9);
        int i10;
        if ((localView != paramView) && (localView.getVisibility() == 0))
        {
          Drawable localDrawable = localView.getBackground();
          if (localDrawable == null) {
            break label157;
          }
          if (localDrawable.getOpacity() != -1) {
            break label151;
          }
          i10 = 1;
          label94:
          if ((i10 != 0) && (isDrawerView(localView)) && (localView.getHeight() >= i))
          {
            if (!checkDrawerViewAbsoluteGravity(localView, 3)) {
              break label163;
            }
            int i12 = localView.getRight();
            if (i12 > m) {
              m = i12;
            }
          }
        }
        for (;;)
        {
          i9++;
          break;
          label151:
          i10 = 0;
          break label94;
          label157:
          i10 = 0;
          break label94;
          label163:
          int i11 = localView.getLeft();
          if (i11 < j) {
            j = i11;
          }
        }
      }
      paramCanvas.clipRect(m, 0, j, getHeight());
    }
    boolean bool2 = super.drawChild(paramCanvas, paramView, paramLong);
    paramCanvas.restoreToCount(k);
    if ((this.mScrimOpacity > 0.0F) && (bool1))
    {
      int i7 = (int)(((0xFF000000 & this.mScrimColor) >>> 24) * this.mScrimOpacity) << 24 | 0xFFFFFF & this.mScrimColor;
      this.mScrimPaint.setColor(i7);
      paramCanvas.drawRect(m, 0.0F, j, getHeight(), this.mScrimPaint);
    }
    do
    {
      return bool2;
      if ((this.mShadowLeftResolved != null) && (checkDrawerViewAbsoluteGravity(paramView, 3)))
      {
        int i4 = this.mShadowLeftResolved.getIntrinsicWidth();
        int i5 = paramView.getRight();
        int i6 = this.mLeftDragger.mEdgeSize;
        float f2 = Math.max(0.0F, Math.min(i5 / i6, 1.0F));
        this.mShadowLeftResolved.setBounds(i5, paramView.getTop(), i5 + i4, paramView.getBottom());
        this.mShadowLeftResolved.setAlpha((int)(255.0F * f2));
        this.mShadowLeftResolved.draw(paramCanvas);
        return bool2;
      }
    } while ((this.mShadowRightResolved == null) || (!checkDrawerViewAbsoluteGravity(paramView, 5)));
    int n = this.mShadowRightResolved.getIntrinsicWidth();
    int i1 = paramView.getLeft();
    int i2 = getWidth() - i1;
    int i3 = this.mRightDragger.mEdgeSize;
    float f1 = Math.max(0.0F, Math.min(i2 / i3, 1.0F));
    this.mShadowRightResolved.setBounds(i1 - n, paramView.getTop(), i1, paramView.getBottom());
    this.mShadowRightResolved.setAlpha((int)(255.0F * f1));
    this.mShadowRightResolved.draw(paramCanvas);
    return bool2;
  }
  
  public final View findDrawerWithGravity(int paramInt)
  {
    int i = 0x7 & GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this));
    int j = getChildCount();
    for (int k = 0; k < j; k++)
    {
      View localView = getChildAt(k);
      if ((0x7 & getDrawerViewAbsoluteGravity(localView)) == i) {
        return localView;
      }
    }
    return null;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof LayoutParams)) {
      return new LayoutParams((LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public float getDrawerElevation()
  {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
      return this.mDrawerElevation;
    }
    return 0.0F;
  }
  
  public final int getDrawerLockMode(View paramView)
  {
    int i = getDrawerViewAbsoluteGravity(paramView);
    if (i == 3) {
      return this.mLockModeLeft;
    }
    if (i == 5) {
      return this.mLockModeRight;
    }
    return 0;
  }
  
  final int getDrawerViewAbsoluteGravity(View paramView)
  {
    return GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
  }
  
  public Drawable getStatusBarBackgroundDrawable()
  {
    return this.mStatusBarBackground;
  }
  
  public final boolean isDrawerOpen$134632()
  {
    View localView = findDrawerWithGravity(8388611);
    if (localView != null) {
      return isDrawerOpen(localView);
    }
    return false;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mDrawStatusBarBackground) && (this.mStatusBarBackground != null))
    {
      int i = IMPL.getTopInset(this.mLastInsets);
      if (i > 0)
      {
        this.mStatusBarBackground.setBounds(0, 0, getWidth(), i);
        this.mStatusBarBackground.draw(paramCanvas);
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    boolean bool1 = this.mLeftDragger.shouldInterceptTouchEvent(paramMotionEvent) | this.mRightDragger.shouldInterceptTouchEvent(paramMotionEvent);
    int j = 0;
    int m;
    switch (i)
    {
    default: 
      if ((!bool1) && (j == 0))
      {
        int k = getChildCount();
        m = 0;
        label74:
        if (m >= k) {
          break label416;
        }
        if (!((LayoutParams)getChildAt(m).getLayoutParams()).isPeeking) {
          break label410;
        }
      }
      break;
    }
    label410:
    label416:
    for (int n = 1;; n = 0)
    {
      if ((n == 0) && (!this.mChildrenCanceledTouch)) {
        break label422;
      }
      return true;
      float f3 = paramMotionEvent.getX();
      float f4 = paramMotionEvent.getY();
      this.mInitialMotionX = f3;
      this.mInitialMotionY = f4;
      boolean bool2 = this.mScrimOpacity < 0.0F;
      j = 0;
      if (bool2)
      {
        View localView = this.mLeftDragger.findTopChildUnder((int)f3, (int)f4);
        j = 0;
        if (localView != null)
        {
          boolean bool3 = isContentView(localView);
          j = 0;
          if (bool3) {
            j = 1;
          }
        }
      }
      this.mDisallowInterceptRequested = false;
      this.mChildrenCanceledTouch = false;
      break;
      ViewDragHelper localViewDragHelper = this.mLeftDragger;
      int i1 = localViewDragHelper.mInitialMotionX.length;
      int i2 = 0;
      label227:
      int i4;
      label250:
      int i5;
      if (i2 < i1) {
        if ((localViewDragHelper.mPointersDown & 1 << i2) != 0)
        {
          i4 = 1;
          if (i4 == 0) {
            break label371;
          }
          float f1 = localViewDragHelper.mLastMotionX[i2] - localViewDragHelper.mInitialMotionX[i2];
          float f2 = localViewDragHelper.mLastMotionY[i2] - localViewDragHelper.mInitialMotionY[i2];
          if (f1 * f1 + f2 * f2 <= localViewDragHelper.mTouchSlop * localViewDragHelper.mTouchSlop) {
            break label365;
          }
          i5 = 1;
          label323:
          if (i5 == 0) {
            break label377;
          }
        }
      }
      for (int i3 = 1;; i3 = 0)
      {
        j = 0;
        if (i3 == 0) {
          break;
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        j = 0;
        break;
        i4 = 0;
        break label250;
        label365:
        i5 = 0;
        break label323;
        label371:
        i5 = 0;
        break label323;
        label377:
        i2++;
        break label227;
      }
      closeDrawers(true);
      this.mDisallowInterceptRequested = false;
      this.mChildrenCanceledTouch = false;
      j = 0;
      break;
      m++;
      break label74;
    }
    label422:
    return false;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (findVisibleDrawer() != null) {}
      for (int i = 1; i != 0; i = 0)
      {
        KeyEventCompat.startTracking(paramKeyEvent);
        return true;
      }
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      View localView = findVisibleDrawer();
      if ((localView != null) && (getDrawerLockMode(localView) == 0)) {
        closeDrawers(false);
      }
      boolean bool = false;
      if (localView != null) {
        bool = true;
      }
      return bool;
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mInLayout = true;
    int i = paramInt3 - paramInt1;
    int j = getChildCount();
    int k = 0;
    while (k < j)
    {
      View localView = getChildAt(k);
      LayoutParams localLayoutParams;
      if (localView.getVisibility() != 8)
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (isContentView(localView)) {
          localView.layout(localLayoutParams.leftMargin, localLayoutParams.topMargin, localLayoutParams.leftMargin + localView.getMeasuredWidth(), localLayoutParams.topMargin + localView.getMeasuredHeight());
        }
      }
      else
      {
        k++;
        continue;
      }
      int m = localView.getMeasuredWidth();
      int n = localView.getMeasuredHeight();
      int i1;
      float f;
      label158:
      int i2;
      if (checkDrawerViewAbsoluteGravity(localView, 3))
      {
        i1 = -m + (int)(m * localLayoutParams.onScreen);
        f = (m + i1) / m;
        if (f == localLayoutParams.onScreen) {
          break label309;
        }
        i2 = 1;
        label172:
        switch (0x70 & localLayoutParams.gravity)
        {
        default: 
          localView.layout(i1, localLayoutParams.topMargin, i1 + m, n + localLayoutParams.topMargin);
          label233:
          if (i2 != 0) {
            setDrawerViewOffset(localView, f);
          }
          if (localLayoutParams.onScreen <= 0.0F) {
            break;
          }
        }
      }
      for (int i5 = 0; localView.getVisibility() != i5; i5 = 4)
      {
        localView.setVisibility(i5);
        break;
        i1 = i - (int)(m * localLayoutParams.onScreen);
        f = (i - i1) / m;
        break label158;
        label309:
        i2 = 0;
        break label172;
        int i6 = paramInt4 - paramInt2;
        localView.layout(i1, i6 - localLayoutParams.bottomMargin - localView.getMeasuredHeight(), i1 + m, i6 - localLayoutParams.bottomMargin);
        break label233;
        int i3 = paramInt4 - paramInt2;
        int i4 = (i3 - n) / 2;
        if (i4 < localLayoutParams.topMargin) {
          i4 = localLayoutParams.topMargin;
        }
        for (;;)
        {
          localView.layout(i1, i4, i1 + m, i4 + n);
          break;
          if (i4 + n > i3 - localLayoutParams.bottomMargin) {
            i4 = i3 - localLayoutParams.bottomMargin - n;
          }
        }
      }
    }
    this.mInLayout = false;
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getSize(paramInt2);
    if ((i != 1073741824) || (j != 1073741824))
    {
      if (!isInEditMode()) {
        break label254;
      }
      if ((i != -2147483648) && (i == 0)) {
        k = 300;
      }
      if ((j != -2147483648) && (j == 0)) {
        m = 300;
      }
    }
    setMeasuredDimension(k, m);
    int n;
    int i3;
    label119:
    View localView;
    LayoutParams localLayoutParams;
    int i5;
    if ((this.mLastInsets != null) && (ViewCompat.getFitsSystemWindows(this)))
    {
      n = 1;
      int i1 = ViewCompat.getLayoutDirection(this);
      int i2 = getChildCount();
      i3 = 0;
      if (i3 >= i2) {
        return;
      }
      localView = getChildAt(i3);
      if (localView.getVisibility() != 8)
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (n != 0)
        {
          i5 = GravityCompat.getAbsoluteGravity(localLayoutParams.gravity, i1);
          if (!ViewCompat.getFitsSystemWindows(localView)) {
            break label271;
          }
          IMPL.dispatchChildInsets(localView, this.mLastInsets, i5);
        }
        label195:
        if (!isContentView(localView)) {
          break label290;
        }
        localView.measure(View.MeasureSpec.makeMeasureSpec(k - localLayoutParams.leftMargin - localLayoutParams.rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec(m - localLayoutParams.topMargin - localLayoutParams.bottomMargin, 1073741824));
      }
    }
    for (;;)
    {
      i3++;
      break label119;
      label254:
      throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
      n = 0;
      break;
      label271:
      IMPL.applyMarginInsets(localLayoutParams, this.mLastInsets, i5);
      break label195;
      label290:
      if (!isDrawerView(localView)) {
        break label476;
      }
      if ((SET_DRAWER_SHADOW_FROM_ELEVATION) && (ViewCompat.getElevation(localView) != this.mDrawerElevation)) {
        ViewCompat.setElevation(localView, this.mDrawerElevation);
      }
      int i4 = 0x7 & getDrawerViewAbsoluteGravity(localView);
      if ((i4 & 0x0) != 0)
      {
        StringBuilder localStringBuilder = new StringBuilder("Child drawer has absolute gravity ");
        String str;
        if ((i4 & 0x3) == 3) {
          str = "LEFT";
        }
        for (;;)
        {
          IllegalStateException localIllegalStateException = new IllegalStateException(str + " but this DrawerLayout already has a drawer view along that edge");
          throw localIllegalStateException;
          if ((i4 & 0x5) == 5) {
            str = "RIGHT";
          } else {
            str = Integer.toHexString(i4);
          }
        }
      }
      localView.measure(getChildMeasureSpec(paramInt1, this.mMinDrawerMargin + localLayoutParams.leftMargin + localLayoutParams.rightMargin, localLayoutParams.width), getChildMeasureSpec(paramInt2, localLayoutParams.topMargin + localLayoutParams.bottomMargin, localLayoutParams.height));
    }
    label476:
    throw new IllegalStateException("Child " + localView + " at index " + i3 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (localSavedState.openDrawerGravity != 0)
    {
      View localView = findDrawerWithGravity(localSavedState.openDrawerGravity);
      if (localView != null) {
        openDrawer(localView);
      }
    }
    setDrawerLockMode(localSavedState.lockModeLeft, 3);
    setDrawerLockMode(localSavedState.lockModeRight, 5);
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    resolveShadowDrawables();
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    View localView = findOpenDrawer();
    if (localView != null) {
      localSavedState.openDrawerGravity = ((LayoutParams)localView.getLayoutParams()).gravity;
    }
    localSavedState.lockModeLeft = this.mLockModeLeft;
    localSavedState.lockModeRight = this.mLockModeRight;
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mLeftDragger.processTouchEvent(paramMotionEvent);
    this.mRightDragger.processTouchEvent(paramMotionEvent);
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 2: 
    default: 
      return true;
    case 0: 
      float f5 = paramMotionEvent.getX();
      float f6 = paramMotionEvent.getY();
      this.mInitialMotionX = f5;
      this.mInitialMotionY = f6;
      this.mDisallowInterceptRequested = false;
      this.mChildrenCanceledTouch = false;
      return true;
    case 1: 
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      boolean bool = true;
      View localView1 = this.mLeftDragger.findTopChildUnder((int)f1, (int)f2);
      if ((localView1 != null) && (isContentView(localView1)))
      {
        float f3 = f1 - this.mInitialMotionX;
        float f4 = f2 - this.mInitialMotionY;
        int i = this.mLeftDragger.mTouchSlop;
        if (f3 * f3 + f4 * f4 < i * i)
        {
          View localView2 = findOpenDrawer();
          if (localView2 != null) {
            if (getDrawerLockMode(localView2) != 2) {
              break label216;
            }
          }
        }
      }
      label216:
      for (bool = true;; bool = false)
      {
        closeDrawers(bool);
        this.mDisallowInterceptRequested = false;
        return true;
      }
    }
    closeDrawers(true);
    this.mDisallowInterceptRequested = false;
    this.mChildrenCanceledTouch = false;
    return true;
  }
  
  public final void openDrawer(View paramView)
  {
    if (!isDrawerView(paramView)) {
      throw new IllegalArgumentException("View " + paramView + " is not a sliding drawer");
    }
    if (this.mFirstLayout)
    {
      LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      localLayoutParams.onScreen = 1.0F;
      localLayoutParams.knownOpen = true;
      updateChildrenImportantForAccessibility(paramView, true);
    }
    for (;;)
    {
      invalidate();
      return;
      if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
        this.mLeftDragger.smoothSlideViewTo(paramView, 0, paramView.getTop());
      } else {
        this.mRightDragger.smoothSlideViewTo(paramView, getWidth() - paramView.getWidth(), paramView.getTop());
      }
    }
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    this.mDisallowInterceptRequested = paramBoolean;
    if (paramBoolean) {
      closeDrawers(true);
    }
  }
  
  public void requestLayout()
  {
    if (!this.mInLayout) {
      super.requestLayout();
    }
  }
  
  public final void resolveShadowDrawables()
  {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
      return;
    }
    int i = ViewCompat.getLayoutDirection(this);
    Drawable localDrawable1;
    int j;
    Drawable localDrawable2;
    if (i == 0)
    {
      if (this.mShadowStart == null) {
        break label104;
      }
      mirror(this.mShadowStart, i);
      localDrawable1 = this.mShadowStart;
      this.mShadowLeftResolved = localDrawable1;
      j = ViewCompat.getLayoutDirection(this);
      if (j != 0) {
        break label112;
      }
      if (this.mShadowEnd == null) {
        break label137;
      }
      mirror(this.mShadowEnd, j);
      localDrawable2 = this.mShadowEnd;
    }
    label137:
    for (;;)
    {
      this.mShadowRightResolved = localDrawable2;
      return;
      if (this.mShadowEnd != null)
      {
        mirror(this.mShadowEnd, i);
        localDrawable1 = this.mShadowEnd;
        break;
      }
      label104:
      localDrawable1 = this.mShadowLeft;
      break;
      label112:
      if (this.mShadowStart != null)
      {
        mirror(this.mShadowStart, j);
        localDrawable2 = this.mShadowStart;
      }
      else
      {
        localDrawable2 = this.mShadowRight;
      }
    }
  }
  
  public final void setChildInsets(Object paramObject, boolean paramBoolean)
  {
    this.mLastInsets = paramObject;
    this.mDrawStatusBarBackground = paramBoolean;
    if ((!paramBoolean) && (getBackground() == null)) {}
    for (boolean bool = true;; bool = false)
    {
      setWillNotDraw(bool);
      requestLayout();
      return;
    }
  }
  
  public void setDrawerElevation(float paramFloat)
  {
    this.mDrawerElevation = paramFloat;
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if (isDrawerView(localView)) {
        ViewCompat.setElevation(localView, this.mDrawerElevation);
      }
    }
  }
  
  public void setDrawerListener(DrawerListener paramDrawerListener)
  {
    this.mListener = paramDrawerListener;
  }
  
  public void setDrawerLockMode(int paramInt)
  {
    setDrawerLockMode(paramInt, 3);
    setDrawerLockMode(paramInt, 5);
  }
  
  final void setDrawerViewOffset(View paramView, float paramFloat)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (paramFloat == localLayoutParams.onScreen) {}
    do
    {
      return;
      localLayoutParams.onScreen = paramFloat;
    } while (this.mListener == null);
    this.mListener.onDrawerSlide(paramView, paramFloat);
  }
  
  public void setScrimColor(int paramInt)
  {
    this.mScrimColor = paramInt;
    invalidate();
  }
  
  public void setStatusBarBackground(int paramInt)
  {
    if (paramInt != 0) {}
    for (Drawable localDrawable = ContextCompat.getDrawable(getContext(), paramInt);; localDrawable = null)
    {
      this.mStatusBarBackground = localDrawable;
      invalidate();
      return;
    }
  }
  
  public void setStatusBarBackground(Drawable paramDrawable)
  {
    this.mStatusBarBackground = paramDrawable;
    invalidate();
  }
  
  public void setStatusBarBackgroundColor(int paramInt)
  {
    this.mStatusBarBackground = new ColorDrawable(paramInt);
    invalidate();
  }
  
  final void updateChildrenImportantForAccessibility(View paramView, boolean paramBoolean)
  {
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView = getChildAt(j);
      if (((!paramBoolean) && (!isDrawerView(localView))) || ((paramBoolean) && (localView == paramView))) {
        ViewCompat.setImportantForAccessibility(localView, 1);
      }
      for (;;)
      {
        j++;
        break;
        ViewCompat.setImportantForAccessibility(localView, 4);
      }
    }
  }
  
  final class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    private final Rect mTmpRect = new Rect();
    
    AccessibilityDelegate() {}
    
    public final boolean dispatchPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      if (paramAccessibilityEvent.getEventType() == 32)
      {
        List localList = paramAccessibilityEvent.getText();
        View localView = DrawerLayout.this.findVisibleDrawer();
        DrawerLayout localDrawerLayout;
        int j;
        CharSequence localCharSequence;
        if (localView != null)
        {
          int i = DrawerLayout.this.getDrawerViewAbsoluteGravity(localView);
          localDrawerLayout = DrawerLayout.this;
          j = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(localDrawerLayout));
          if (j != 3) {
            break label86;
          }
          localCharSequence = localDrawerLayout.mTitleLeft;
        }
        for (;;)
        {
          if (localCharSequence != null) {
            localList.add(localCharSequence);
          }
          return true;
          label86:
          if (j == 5) {
            localCharSequence = localDrawerLayout.mTitleRight;
          } else {
            localCharSequence = null;
          }
        }
      }
      return super.dispatchPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
    }
    
    public final void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(DrawerLayout.class.getName());
    }
    
    public final void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
        super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      }
      for (;;)
      {
        paramAccessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
        paramAccessibilityNodeInfoCompat.setFocusable(false);
        paramAccessibilityNodeInfoCompat.setFocused(false);
        paramAccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
        paramAccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        return;
        AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(paramAccessibilityNodeInfoCompat);
        super.onInitializeAccessibilityNodeInfo(paramView, localAccessibilityNodeInfoCompat);
        AccessibilityNodeInfoCompat.IMPL.setSource(paramAccessibilityNodeInfoCompat.mInfo, paramView);
        ViewParent localViewParent = ViewCompat.getParentForAccessibility(paramView);
        if ((localViewParent instanceof View)) {
          paramAccessibilityNodeInfoCompat.setParent((View)localViewParent);
        }
        Rect localRect = this.mTmpRect;
        localAccessibilityNodeInfoCompat.getBoundsInParent(localRect);
        AccessibilityNodeInfoCompat.IMPL.setBoundsInParent(paramAccessibilityNodeInfoCompat.mInfo, localRect);
        localAccessibilityNodeInfoCompat.getBoundsInScreen(localRect);
        AccessibilityNodeInfoCompat.IMPL.setBoundsInScreen(paramAccessibilityNodeInfoCompat.mInfo, localRect);
        boolean bool1 = AccessibilityNodeInfoCompat.IMPL.isVisibleToUser(localAccessibilityNodeInfoCompat.mInfo);
        AccessibilityNodeInfoCompat.IMPL.setVisibleToUser(paramAccessibilityNodeInfoCompat.mInfo, bool1);
        CharSequence localCharSequence1 = localAccessibilityNodeInfoCompat.getPackageName();
        AccessibilityNodeInfoCompat.IMPL.setPackageName(paramAccessibilityNodeInfoCompat.mInfo, localCharSequence1);
        paramAccessibilityNodeInfoCompat.setClassName(localAccessibilityNodeInfoCompat.getClassName());
        CharSequence localCharSequence2 = localAccessibilityNodeInfoCompat.getContentDescription();
        AccessibilityNodeInfoCompat.IMPL.setContentDescription(paramAccessibilityNodeInfoCompat.mInfo, localCharSequence2);
        boolean bool2 = localAccessibilityNodeInfoCompat.isEnabled();
        AccessibilityNodeInfoCompat.IMPL.setEnabled(paramAccessibilityNodeInfoCompat.mInfo, bool2);
        boolean bool3 = localAccessibilityNodeInfoCompat.isClickable();
        AccessibilityNodeInfoCompat.IMPL.setClickable(paramAccessibilityNodeInfoCompat.mInfo, bool3);
        paramAccessibilityNodeInfoCompat.setFocusable(localAccessibilityNodeInfoCompat.isFocusable());
        paramAccessibilityNodeInfoCompat.setFocused(localAccessibilityNodeInfoCompat.isFocused());
        boolean bool4 = AccessibilityNodeInfoCompat.IMPL.isAccessibilityFocused(localAccessibilityNodeInfoCompat.mInfo);
        AccessibilityNodeInfoCompat.IMPL.setAccessibilityFocused(paramAccessibilityNodeInfoCompat.mInfo, bool4);
        boolean bool5 = localAccessibilityNodeInfoCompat.isSelected();
        AccessibilityNodeInfoCompat.IMPL.setSelected(paramAccessibilityNodeInfoCompat.mInfo, bool5);
        boolean bool6 = localAccessibilityNodeInfoCompat.isLongClickable();
        AccessibilityNodeInfoCompat.IMPL.setLongClickable(paramAccessibilityNodeInfoCompat.mInfo, bool6);
        paramAccessibilityNodeInfoCompat.addAction(localAccessibilityNodeInfoCompat.getActions());
        AccessibilityNodeInfoCompat.IMPL.recycle(localAccessibilityNodeInfoCompat.mInfo);
        ViewGroup localViewGroup = (ViewGroup)paramView;
        int i = localViewGroup.getChildCount();
        for (int j = 0; j < i; j++)
        {
          View localView = localViewGroup.getChildAt(j);
          if (DrawerLayout.access$400(localView)) {
            AccessibilityNodeInfoCompat.IMPL.addChild(paramAccessibilityNodeInfoCompat.mInfo, localView);
          }
        }
      }
    }
    
    public final boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      if ((DrawerLayout.CAN_HIDE_DESCENDANTS) || (DrawerLayout.access$400(paramView))) {
        return super.onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
      }
      return false;
    }
  }
  
  final class ChildAccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    ChildAccessibilityDelegate() {}
    
    public final void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      if (!DrawerLayout.access$400(paramView)) {
        paramAccessibilityNodeInfoCompat.setParent(null);
      }
    }
  }
  
  static abstract interface DrawerLayoutCompatImpl
  {
    public abstract void applyMarginInsets(ViewGroup.MarginLayoutParams paramMarginLayoutParams, Object paramObject, int paramInt);
    
    public abstract void configureApplyInsets(View paramView);
    
    public abstract void dispatchChildInsets(View paramView, Object paramObject, int paramInt);
    
    public abstract Drawable getDefaultStatusBarBackground(Context paramContext);
    
    public abstract int getTopInset(Object paramObject);
  }
  
  static final class DrawerLayoutCompatImplApi21
    implements DrawerLayout.DrawerLayoutCompatImpl
  {
    public final void applyMarginInsets(ViewGroup.MarginLayoutParams paramMarginLayoutParams, Object paramObject, int paramInt)
    {
      DrawerLayoutCompatApi21.applyMarginInsets(paramMarginLayoutParams, paramObject, paramInt);
    }
    
    public final void configureApplyInsets(View paramView)
    {
      DrawerLayoutCompatApi21.configureApplyInsets(paramView);
    }
    
    public final void dispatchChildInsets(View paramView, Object paramObject, int paramInt)
    {
      DrawerLayoutCompatApi21.dispatchChildInsets(paramView, paramObject, paramInt);
    }
    
    public final Drawable getDefaultStatusBarBackground(Context paramContext)
    {
      return DrawerLayoutCompatApi21.getDefaultStatusBarBackground(paramContext);
    }
    
    public final int getTopInset(Object paramObject)
    {
      return DrawerLayoutCompatApi21.getTopInset(paramObject);
    }
  }
  
  static final class DrawerLayoutCompatImplBase
    implements DrawerLayout.DrawerLayoutCompatImpl
  {
    public final void applyMarginInsets(ViewGroup.MarginLayoutParams paramMarginLayoutParams, Object paramObject, int paramInt) {}
    
    public final void configureApplyInsets(View paramView) {}
    
    public final void dispatchChildInsets(View paramView, Object paramObject, int paramInt) {}
    
    public final Drawable getDefaultStatusBarBackground(Context paramContext)
    {
      return null;
    }
    
    public final int getTopInset(Object paramObject)
    {
      return 0;
    }
  }
  
  public static abstract interface DrawerListener
  {
    public abstract void onDrawerClosed(View paramView);
    
    public abstract void onDrawerOpened(View paramView);
    
    public abstract void onDrawerSlide(View paramView, float paramFloat);
    
    public abstract void onDrawerStateChanged(int paramInt);
  }
  
  public static final class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public int gravity = 0;
    boolean isPeeking;
    boolean knownOpen;
    float onScreen;
    
    public LayoutParams()
    {
      super(-1);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, DrawerLayout.LAYOUT_ATTRS);
      this.gravity = localTypedArray.getInt(0, 0);
      localTypedArray.recycle();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.gravity = paramLayoutParams.gravity;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  protected static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    int lockModeLeft = 0;
    int lockModeRight = 0;
    int openDrawerGravity = 0;
    
    public SavedState(Parcel paramParcel)
    {
      super();
      this.openDrawerGravity = paramParcel.readInt();
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.openDrawerGravity);
    }
  }
  
  private final class ViewDragCallback
    extends ViewDragHelper.Callback
  {
    final int mAbsGravity;
    ViewDragHelper mDragger;
    private final Runnable mPeekRunnable = new Runnable()
    {
      public final void run()
      {
        int i = 0;
        DrawerLayout.ViewDragCallback localViewDragCallback = DrawerLayout.ViewDragCallback.this;
        int j = localViewDragCallback.mDragger.mEdgeSize;
        int k;
        int i2;
        label54:
        Object localObject;
        int n;
        if (localViewDragCallback.mAbsGravity == 3)
        {
          k = 1;
          if (k == 0) {
            break label234;
          }
          View localView2 = localViewDragCallback.this$0.findDrawerWithGravity(3);
          if (localView2 == null) {
            break label228;
          }
          i2 = -localView2.getWidth();
          int i3 = i2 + j;
          localObject = localView2;
          n = i3;
        }
        for (;;)
        {
          label68:
          if ((localObject != null) && (((k != 0) && (((View)localObject).getLeft() < n)) || ((k == 0) && (((View)localObject).getLeft() > n) && (localViewDragCallback.this$0.getDrawerLockMode((View)localObject) == 0))))
          {
            DrawerLayout.LayoutParams localLayoutParams = (DrawerLayout.LayoutParams)((View)localObject).getLayoutParams();
            localViewDragCallback.mDragger.smoothSlideViewTo((View)localObject, n, ((View)localObject).getTop());
            localLayoutParams.isPeeking = true;
            localViewDragCallback.this$0.invalidate();
            localViewDragCallback.closeOtherDrawer();
            DrawerLayout localDrawerLayout = localViewDragCallback.this$0;
            if (!localDrawerLayout.mChildrenCanceledTouch)
            {
              long l = SystemClock.uptimeMillis();
              MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
              int i1 = localDrawerLayout.getChildCount();
              for (;;)
              {
                if (i < i1)
                {
                  localDrawerLayout.getChildAt(i).dispatchTouchEvent(localMotionEvent);
                  i++;
                  continue;
                  k = 0;
                  break;
                  label228:
                  i2 = 0;
                  break label54;
                  label234:
                  View localView1 = localViewDragCallback.this$0.findDrawerWithGravity(5);
                  int m = localViewDragCallback.this$0.getWidth() - j;
                  localObject = localView1;
                  n = m;
                  break label68;
                }
              }
              localMotionEvent.recycle();
              localDrawerLayout.mChildrenCanceledTouch = true;
            }
          }
        }
      }
    };
    
    public ViewDragCallback(int paramInt)
    {
      this.mAbsGravity = paramInt;
    }
    
    public final int clampViewPositionHorizontal$17e143b0(View paramView, int paramInt)
    {
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, 3)) {
        return Math.max(-paramView.getWidth(), Math.min(paramInt, 0));
      }
      int i = DrawerLayout.this.getWidth();
      return Math.max(i - paramView.getWidth(), Math.min(paramInt, i));
    }
    
    public final int clampViewPositionVertical$17e143b0(View paramView)
    {
      return paramView.getTop();
    }
    
    final void closeOtherDrawer()
    {
      int i = 3;
      if (this.mAbsGravity == i) {
        i = 5;
      }
      View localView = DrawerLayout.this.findDrawerWithGravity(i);
      if (localView != null) {
        DrawerLayout.this.closeDrawer(localView);
      }
    }
    
    public final int getViewHorizontalDragRange(View paramView)
    {
      if (DrawerLayout.isDrawerView(paramView)) {
        return paramView.getWidth();
      }
      return 0;
    }
    
    public final void onEdgeDragStarted(int paramInt1, int paramInt2)
    {
      if ((paramInt1 & 0x1) == 1) {}
      for (View localView = DrawerLayout.this.findDrawerWithGravity(3);; localView = DrawerLayout.this.findDrawerWithGravity(5))
      {
        if ((localView != null) && (DrawerLayout.this.getDrawerLockMode(localView) == 0)) {
          this.mDragger.captureChildView(localView, paramInt2);
        }
        return;
      }
    }
    
    public final void onEdgeTouched$255f295()
    {
      DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
    }
    
    public final void onViewCaptured$5359dc9a(View paramView)
    {
      ((DrawerLayout.LayoutParams)paramView.getLayoutParams()).isPeeking = false;
      closeOtherDrawer();
    }
    
    public final void onViewDragStateChanged(int paramInt)
    {
      DrawerLayout localDrawerLayout = DrawerLayout.this;
      View localView1 = this.mDragger.mCapturedView;
      int i = localDrawerLayout.mLeftDragger.mDragState;
      int j = localDrawerLayout.mRightDragger.mDragState;
      int k;
      DrawerLayout.LayoutParams localLayoutParams1;
      if ((i == 1) || (j == 1))
      {
        k = 1;
        if ((localView1 != null) && (paramInt == 0))
        {
          localLayoutParams1 = (DrawerLayout.LayoutParams)localView1.getLayoutParams();
          if (localLayoutParams1.onScreen != 0.0F) {
            break label202;
          }
          DrawerLayout.LayoutParams localLayoutParams3 = (DrawerLayout.LayoutParams)localView1.getLayoutParams();
          if (localLayoutParams3.knownOpen)
          {
            localLayoutParams3.knownOpen = false;
            if (localDrawerLayout.mListener != null) {
              localDrawerLayout.mListener.onDrawerClosed(localView1);
            }
            localDrawerLayout.updateChildrenImportantForAccessibility(localView1, false);
            if (localDrawerLayout.hasWindowFocus())
            {
              View localView2 = localDrawerLayout.getRootView();
              if (localView2 != null) {
                localView2.sendAccessibilityEvent(32);
              }
            }
          }
        }
      }
      for (;;)
      {
        if (k != localDrawerLayout.mDrawerState)
        {
          localDrawerLayout.mDrawerState = k;
          if (localDrawerLayout.mListener != null) {
            localDrawerLayout.mListener.onDrawerStateChanged(k);
          }
        }
        return;
        if ((i == 2) || (j == 2))
        {
          k = 2;
          break;
        }
        k = 0;
        break;
        label202:
        if (localLayoutParams1.onScreen == 1.0F)
        {
          DrawerLayout.LayoutParams localLayoutParams2 = (DrawerLayout.LayoutParams)localView1.getLayoutParams();
          if (!localLayoutParams2.knownOpen)
          {
            localLayoutParams2.knownOpen = true;
            if (localDrawerLayout.mListener != null) {
              localDrawerLayout.mListener.onDrawerOpened(localView1);
            }
            localDrawerLayout.updateChildrenImportantForAccessibility(localView1, true);
            if (localDrawerLayout.hasWindowFocus()) {
              localDrawerLayout.sendAccessibilityEvent(32);
            }
            localView1.requestFocus();
          }
        }
      }
    }
    
    public final void onViewPositionChanged$5b6f797d(View paramView, int paramInt)
    {
      int i = paramView.getWidth();
      float f;
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, 3))
      {
        f = (i + paramInt) / i;
        DrawerLayout.this.setDrawerViewOffset(paramView, f);
        if (f != 0.0F) {
          break label78;
        }
      }
      label78:
      for (int j = 4;; j = 0)
      {
        paramView.setVisibility(j);
        DrawerLayout.this.invalidate();
        return;
        f = (DrawerLayout.this.getWidth() - paramInt) / i;
        break;
      }
    }
    
    public final void onViewReleased$17e2ac03(View paramView, float paramFloat)
    {
      float f = DrawerLayout.getDrawerViewOffset(paramView);
      int i = paramView.getWidth();
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, 3))
      {
        if ((paramFloat > 0.0F) || ((paramFloat == 0.0F) && (f > 0.5F))) {}
        for (k = 0;; k = -i)
        {
          this.mDragger.settleCapturedViewAt(k, paramView.getTop());
          DrawerLayout.this.invalidate();
          return;
        }
      }
      int j = DrawerLayout.this.getWidth();
      if ((paramFloat < 0.0F) || ((paramFloat == 0.0F) && (f > 0.5F))) {}
      for (int k = j - i;; k = j) {
        break;
      }
    }
    
    public final void removeCallbacks()
    {
      DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
    }
    
    public final boolean tryCaptureView$5359dc96(View paramView)
    {
      return (DrawerLayout.isDrawerView(paramView)) && (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, this.mAbsGravity)) && (DrawerLayout.this.getDrawerLockMode(paramView) == 0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.DrawerLayout
 * JD-Core Version:    0.7.0.1
 */