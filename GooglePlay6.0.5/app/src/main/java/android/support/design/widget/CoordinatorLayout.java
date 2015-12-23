package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.design.R.styleable;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
  extends ViewGroup
  implements NestedScrollingParent
{
  static final Class<?>[] CONSTRUCTOR_PARAMS;
  static final CoordinatorLayoutInsetsHelper INSETS_HELPER;
  static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
  static final String WIDGET_PACKAGE_NAME;
  static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
  private View mBehaviorTouchView;
  private final List<View> mDependencySortedChildren;
  private boolean mDrawStatusBarBackground;
  private boolean mIsAttachedToWindow;
  private int[] mKeylines;
  private WindowInsetsCompat mLastInsets;
  final Comparator<View> mLayoutDependencyComparator;
  private boolean mNeedsPreDrawListener;
  private View mNestedScrollingDirectChild;
  private final NestedScrollingParentHelper mNestedScrollingParentHelper;
  private View mNestedScrollingTarget;
  private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
  private OnPreDrawListener mOnPreDrawListener;
  private Drawable mStatusBarBackground;
  private final int[] mTempIntPair;
  private final List<View> mTempList1;
  private final Rect mTempRect1;
  private final Rect mTempRect2;
  private final Rect mTempRect3;
  
  static
  {
    Package localPackage = CoordinatorLayout.class.getPackage();
    String str;
    if (localPackage != null)
    {
      str = localPackage.getName();
      WIDGET_PACKAGE_NAME = str;
      if (Build.VERSION.SDK_INT < 21) {
        break label80;
      }
      TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
    }
    for (INSETS_HELPER = new CoordinatorLayoutInsetsHelperLollipop();; INSETS_HELPER = null)
    {
      CONSTRUCTOR_PARAMS = new Class[] { Context.class, AttributeSet.class };
      sConstructors = new ThreadLocal();
      return;
      str = null;
      break;
      label80:
      TOP_SORTED_CHILDREN_COMPARATOR = null;
    }
  }
  
  private void ensurePreDrawListener()
  {
    int i = getChildCount();
    for (int j = 0;; j++)
    {
      int k = 0;
      View localView1;
      LayoutParams localLayoutParams;
      int i1;
      if (j < i)
      {
        localView1 = getChildAt(j);
        localLayoutParams = (LayoutParams)localView1.getLayoutParams();
        if (localLayoutParams.mAnchorView == null) {
          break label104;
        }
        i1 = 1;
      }
      while (i1 != 0)
      {
        k = 1;
        if (k != this.mNeedsPreDrawListener)
        {
          if (k == 0) {
            break label191;
          }
          if (this.mIsAttachedToWindow)
          {
            if (this.mOnPreDrawListener == null) {
              this.mOnPreDrawListener = new OnPreDrawListener();
            }
            getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
          }
          this.mNeedsPreDrawListener = true;
        }
        return;
        label104:
        int m = getChildCount();
        label173:
        for (int n = 0;; n++)
        {
          if (n >= m) {
            break label179;
          }
          View localView2 = getChildAt(n);
          if (localView2 != localView1)
          {
            if (localView2 != localLayoutParams.mAnchorDirectChild) {
              if (localLayoutParams.mBehavior == null) {}
            }
            for (int i2 = 0;; i2 = 1)
            {
              if (i2 == 0) {
                break label173;
              }
              i1 = 1;
              break;
            }
          }
        }
        label179:
        i1 = 0;
      }
    }
    label191:
    if ((this.mIsAttachedToWindow) && (this.mOnPreDrawListener != null)) {
      getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
    }
    this.mNeedsPreDrawListener = false;
  }
  
  private void getChildRect(View paramView, boolean paramBoolean, Rect paramRect)
  {
    if ((paramView.isLayoutRequested()) || (paramView.getVisibility() == 8))
    {
      paramRect.set(0, 0, 0, 0);
      return;
    }
    if (paramBoolean)
    {
      ViewGroupUtils.getDescendantRect(this, paramView, paramRect);
      return;
    }
    paramRect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
  }
  
  private void getDesiredAnchoredChildRect(View paramView, int paramInt, Rect paramRect1, Rect paramRect2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = localLayoutParams.gravity;
    if (i == 0) {
      i = 17;
    }
    int j = GravityCompat.getAbsoluteGravity(i, paramInt);
    int k = GravityCompat.getAbsoluteGravity(resolveGravity(localLayoutParams.anchorGravity), paramInt);
    int m = j & 0x7;
    int n = j & 0x70;
    int i1 = k & 0x7;
    int i2 = k & 0x70;
    int i3 = paramView.getMeasuredWidth();
    int i4 = paramView.getMeasuredHeight();
    int i5;
    int i6;
    switch (i1)
    {
    default: 
      i5 = paramRect1.left;
      switch (i2)
      {
      default: 
        i6 = paramRect1.top;
        switch (m)
        {
        default: 
          i5 -= i3;
        case 5: 
          label158:
          switch (n)
          {
          default: 
            label195:
            i6 -= i4;
          }
          break;
        }
        break;
      }
      break;
    }
    for (;;)
    {
      int i7 = getWidth();
      int i8 = getHeight();
      int i9 = Math.max(getPaddingLeft() + localLayoutParams.leftMargin, Math.min(i5, i7 - getPaddingRight() - i3 - localLayoutParams.rightMargin));
      int i10 = Math.max(getPaddingTop() + localLayoutParams.topMargin, Math.min(i6, i8 - getPaddingBottom() - i4 - localLayoutParams.bottomMargin));
      paramRect2.set(i9, i10, i9 + i3, i10 + i4);
      return;
      i5 = paramRect1.right;
      break;
      i5 = paramRect1.left + paramRect1.width() / 2;
      break;
      i6 = paramRect1.bottom;
      break label158;
      i6 = paramRect1.top + paramRect1.height() / 2;
      break label158;
      i5 -= i3 / 2;
      break label195;
      i6 -= i4 / 2;
    }
  }
  
  private int getKeyline(int paramInt)
  {
    if (this.mKeylines == null)
    {
      Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + paramInt);
      return 0;
    }
    if ((paramInt < 0) || (paramInt >= this.mKeylines.length))
    {
      Log.e("CoordinatorLayout", "Keyline index " + paramInt + " out of range for " + this);
      return 0;
    }
    return this.mKeylines[paramInt];
  }
  
  private static LayoutParams getResolvedLayoutParams(View paramView)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    DefaultBehavior localDefaultBehavior;
    if (!localLayoutParams.mBehaviorResolved)
    {
      Class localClass = paramView.getClass();
      localDefaultBehavior = null;
      while (localClass != null)
      {
        localDefaultBehavior = (DefaultBehavior)localClass.getAnnotation(DefaultBehavior.class);
        if (localDefaultBehavior != null) {
          break;
        }
        localClass = localClass.getSuperclass();
      }
      if (localDefaultBehavior == null) {}
    }
    try
    {
      localLayoutParams.setBehavior((Behavior)localDefaultBehavior.value().newInstance());
      localLayoutParams.mBehaviorResolved = true;
      return localLayoutParams;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e("CoordinatorLayout", "Default behavior class " + localDefaultBehavior.value().getName() + " could not be instantiated. Did you forget a default constructor?", localException);
      }
    }
  }
  
  static Behavior parseBehavior(Context paramContext, AttributeSet paramAttributeSet, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    if (paramString.startsWith(".")) {}
    for (str = paramContext.getPackageName() + paramString;; str = paramString)
    {
      try
      {
        Object localObject = (Map)sConstructors.get();
        if (localObject == null)
        {
          localObject = new HashMap();
          sConstructors.set(localObject);
        }
        Constructor localConstructor = (Constructor)((Map)localObject).get(str);
        if (localConstructor == null)
        {
          localConstructor = Class.forName(str, true, paramContext.getClassLoader()).getConstructor(CONSTRUCTOR_PARAMS);
          localConstructor.setAccessible(true);
          ((Map)localObject).put(str, localConstructor);
        }
        Behavior localBehavior = (Behavior)localConstructor.newInstance(new Object[] { paramContext, paramAttributeSet });
        return localBehavior;
      }
      catch (Exception localException)
      {
        throw new RuntimeException("Could not inflate Behavior subclass " + str, localException);
      }
      if (paramString.indexOf('.') < 0) {
        break;
      }
    }
    if (!TextUtils.isEmpty(WIDGET_PACKAGE_NAME)) {}
    for (str = WIDGET_PACKAGE_NAME + '.' + paramString;; str = paramString) {
      break;
    }
  }
  
  private boolean performIntercept(MotionEvent paramMotionEvent, int paramInt)
  {
    boolean bool1 = false;
    int i = 0;
    MotionEvent localMotionEvent = null;
    int j = MotionEventCompat.getActionMasked(paramMotionEvent);
    List localList = this.mTempList1;
    localList.clear();
    boolean bool2 = isChildrenDrawingOrderEnabled();
    int k = getChildCount();
    int m = k - 1;
    if (m >= 0)
    {
      if (bool2) {}
      for (int i2 = getChildDrawingOrder(k, m);; i2 = m)
      {
        localList.add(getChildAt(i2));
        m--;
        break;
      }
    }
    if (TOP_SORTED_CHILDREN_COMPARATOR != null) {
      Collections.sort(localList, TOP_SORTED_CHILDREN_COMPARATOR);
    }
    int n = localList.size();
    int i1 = 0;
    View localView;
    LayoutParams localLayoutParams;
    Behavior localBehavior;
    label284:
    boolean bool4;
    if (i1 < n)
    {
      localView = (View)localList.get(i1);
      localLayoutParams = (LayoutParams)localView.getLayoutParams();
      localBehavior = localLayoutParams.mBehavior;
      if (((bool1) || (i != 0)) && (j != 0))
      {
        if (localBehavior != null)
        {
          if (localMotionEvent == null)
          {
            long l = SystemClock.uptimeMillis();
            localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
          }
          switch (paramInt)
          {
          }
        }
        for (;;)
        {
          i1++;
          break;
          localBehavior.onInterceptTouchEvent(this, localView, localMotionEvent);
          continue;
          localBehavior.onTouchEvent$29533e61(localMotionEvent);
        }
      }
      if ((!bool1) && (localBehavior != null)) {}
      switch (paramInt)
      {
      default: 
        if (bool1) {
          this.mBehaviorTouchView = localView;
        }
        if (localLayoutParams.mBehavior == null) {
          localLayoutParams.mDidBlockInteraction = false;
        }
        boolean bool3 = localLayoutParams.mDidBlockInteraction;
        if (localLayoutParams.mDidBlockInteraction)
        {
          bool4 = true;
          label326:
          if ((!bool4) || (bool3)) {
            break label400;
          }
        }
        break;
      }
    }
    label400:
    for (i = 1; (bool4) && (i == 0); i = 0)
    {
      localList.clear();
      return bool1;
      bool1 = localBehavior.onInterceptTouchEvent(this, localView, paramMotionEvent);
      break label284;
      bool1 = localBehavior.onTouchEvent$29533e61(paramMotionEvent);
      break label284;
      bool4 = false | localLayoutParams.mDidBlockInteraction;
      localLayoutParams.mDidBlockInteraction = bool4;
      break label326;
    }
  }
  
  private void resetTouchBehaviors()
  {
    if (this.mBehaviorTouchView != null)
    {
      Behavior localBehavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).mBehavior;
      if (localBehavior != null)
      {
        long l = SystemClock.uptimeMillis();
        MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        localBehavior.onTouchEvent$29533e61(localMotionEvent);
        localMotionEvent.recycle();
      }
      this.mBehaviorTouchView = null;
    }
    int i = getChildCount();
    for (int j = 0; j < i; j++) {
      ((LayoutParams)getChildAt(j).getLayoutParams()).mDidBlockInteraction = false;
    }
  }
  
  private static int resolveGravity(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = 8388659;
    }
    return paramInt;
  }
  
  private static int resolveKeylineGravity(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = 8388661;
    }
    return paramInt;
  }
  
  private static void selectionSort(List<View> paramList, Comparator<View> paramComparator)
  {
    if ((paramList == null) || (paramList.size() < 2)) {}
    for (;;)
    {
      return;
      View[] arrayOfView = new View[paramList.size()];
      paramList.toArray(arrayOfView);
      int i = arrayOfView.length;
      for (int j = 0; j < i; j++)
      {
        int m = j;
        for (int n = j + 1; n < i; n++) {
          if (paramComparator.compare(arrayOfView[n], arrayOfView[m]) < 0) {
            m = n;
          }
        }
        if (j != m)
        {
          View localView = arrayOfView[m];
          arrayOfView[m] = arrayOfView[j];
          arrayOfView[j] = localView;
        }
      }
      paramList.clear();
      for (int k = 0; k < i; k++) {
        paramList.add(arrayOfView[k]);
      }
    }
  }
  
  private void setWindowInsets(WindowInsetsCompat paramWindowInsetsCompat)
  {
    boolean bool1 = true;
    int i = 0;
    if (this.mLastInsets != paramWindowInsetsCompat)
    {
      this.mLastInsets = paramWindowInsetsCompat;
      boolean bool2;
      if ((paramWindowInsetsCompat != null) && (paramWindowInsetsCompat.getSystemWindowInsetTop() > 0))
      {
        bool2 = bool1;
        this.mDrawStatusBarBackground = bool2;
        if ((this.mDrawStatusBarBackground) || (getBackground() != null)) {
          break label144;
        }
      }
      for (;;)
      {
        setWillNotDraw(bool1);
        if (paramWindowInsetsCompat.isConsumed()) {
          break label149;
        }
        int j = getChildCount();
        WindowInsetsCompat localWindowInsetsCompat = paramWindowInsetsCompat;
        while (i < j)
        {
          View localView = getChildAt(i);
          if (ViewCompat.getFitsSystemWindows(localView))
          {
            if ((((LayoutParams)localView.getLayoutParams()).mBehavior != null) && (localWindowInsetsCompat.isConsumed())) {
              break;
            }
            localWindowInsetsCompat = ViewCompat.dispatchApplyWindowInsets(localView, localWindowInsetsCompat);
            if (localWindowInsetsCompat.isConsumed()) {
              break;
            }
          }
          i++;
        }
        bool2 = false;
        break;
        label144:
        bool1 = false;
      }
      label149:
      requestLayout();
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams));
  }
  
  final void dispatchOnDependentViewChanged$1385ff()
  {
    int i = ViewCompat.getLayoutDirection(this);
    int j = this.mDependencySortedChildren.size();
    for (int k = 0; k < j; k++)
    {
      View localView1 = (View)this.mDependencySortedChildren.get(k);
      LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      for (int m = 0; m < k; m++)
      {
        View localView2 = (View)this.mDependencySortedChildren.get(m);
        if (localLayoutParams1.mAnchorDirectChild == localView2)
        {
          LayoutParams localLayoutParams2 = (LayoutParams)localView1.getLayoutParams();
          if (localLayoutParams2.mAnchorView != null)
          {
            Rect localRect3 = this.mTempRect1;
            Rect localRect4 = this.mTempRect2;
            Rect localRect5 = this.mTempRect3;
            ViewGroupUtils.getDescendantRect(this, localLayoutParams2.mAnchorView, localRect3);
            getChildRect(localView1, false, localRect4);
            getDesiredAnchoredChildRect(localView1, i, localRect3, localRect5);
            int i1 = localRect5.left - localRect4.left;
            int i2 = localRect5.top - localRect4.top;
            if (i1 != 0) {
              localView1.offsetLeftAndRight(i1);
            }
            if (i2 != 0) {
              localView1.offsetTopAndBottom(i2);
            }
          }
        }
      }
      Rect localRect1 = this.mTempRect1;
      Rect localRect2 = this.mTempRect2;
      localRect1.set(((LayoutParams)localView1.getLayoutParams()).mLastChildRect);
      getChildRect(localView1, true, localRect2);
      if (!localRect1.equals(localRect2))
      {
        ((LayoutParams)localView1.getLayoutParams()).mLastChildRect.set(localRect2);
        for (int n = k + 1; n < j; n++) {
          ((View)this.mDependencySortedChildren.get(n)).getLayoutParams();
        }
      }
    }
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    paramView.getLayoutParams();
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  public int getNestedScrollAxes()
  {
    return this.mNestedScrollingParentHelper.mNestedScrollAxes;
  }
  
  public Drawable getStatusBarBackground()
  {
    return this.mStatusBarBackground;
  }
  
  protected int getSuggestedMinimumHeight()
  {
    return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
  }
  
  protected int getSuggestedMinimumWidth()
  {
    return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
  }
  
  public final boolean isPointInChildBounds(View paramView, int paramInt1, int paramInt2)
  {
    Rect localRect = this.mTempRect1;
    ViewGroupUtils.getDescendantRect(this, paramView, localRect);
    return localRect.contains(paramInt1, paramInt2);
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    resetTouchBehaviors();
    if (this.mNeedsPreDrawListener)
    {
      if (this.mOnPreDrawListener == null) {
        this.mOnPreDrawListener = new OnPreDrawListener();
      }
      getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    }
    if ((this.mLastInsets == null) && (ViewCompat.getFitsSystemWindows(this))) {
      ViewCompat.requestApplyInsets(this);
    }
    this.mIsAttachedToWindow = true;
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    resetTouchBehaviors();
    if ((this.mNeedsPreDrawListener) && (this.mOnPreDrawListener != null)) {
      getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
    }
    if (this.mNestedScrollingTarget != null) {
      onStopNestedScroll(this.mNestedScrollingTarget);
    }
    this.mIsAttachedToWindow = false;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mDrawStatusBarBackground) && (this.mStatusBarBackground != null)) {
      if (this.mLastInsets == null) {
        break label61;
      }
    }
    label61:
    for (int i = this.mLastInsets.getSystemWindowInsetTop();; i = 0)
    {
      if (i > 0)
      {
        this.mStatusBarBackground.setBounds(0, 0, getWidth(), i);
        this.mStatusBarBackground.draw(paramCanvas);
      }
      return;
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    if (i == 0) {
      resetTouchBehaviors();
    }
    boolean bool = performIntercept(paramMotionEvent, 0);
    if ((i == 1) || (i == 3)) {
      resetTouchBehaviors();
    }
    return bool;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = ViewCompat.getLayoutDirection(this);
    int j = this.mDependencySortedChildren.size();
    int k = 0;
    if (k < j)
    {
      View localView1 = (View)this.mDependencySortedChildren.get(k);
      localView1.getLayoutParams();
      LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      if ((localLayoutParams1.mAnchorView == null) && (localLayoutParams1.mAnchorId != -1)) {}
      for (int m = 1; m != 0; m = 0) {
        throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
      }
      if (localLayoutParams1.mAnchorView != null)
      {
        View localView2 = localLayoutParams1.mAnchorView;
        localView1.getLayoutParams();
        Rect localRect3 = this.mTempRect1;
        Rect localRect4 = this.mTempRect2;
        ViewGroupUtils.getDescendantRect(this, localView2, localRect3);
        getDesiredAnchoredChildRect(localView1, i, localRect3, localRect4);
        localView1.layout(localRect4.left, localRect4.top, localRect4.right, localRect4.bottom);
      }
      for (;;)
      {
        k++;
        break;
        if (localLayoutParams1.keyline >= 0)
        {
          int n = localLayoutParams1.keyline;
          LayoutParams localLayoutParams3 = (LayoutParams)localView1.getLayoutParams();
          int i1 = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(localLayoutParams3.gravity), i);
          int i2 = i1 & 0x7;
          int i3 = i1 & 0x70;
          int i4 = getWidth();
          int i5 = getHeight();
          int i6 = localView1.getMeasuredWidth();
          int i7 = localView1.getMeasuredHeight();
          if (i == 1) {
            n = i4 - n;
          }
          int i8 = getKeyline(n) - i6;
          label316:
          int i9;
          switch (i2)
          {
          default: 
            i9 = 0;
            switch (i3)
            {
            }
            break;
          }
          for (;;)
          {
            int i10 = Math.max(getPaddingLeft() + localLayoutParams3.leftMargin, Math.min(i8, i4 - getPaddingRight() - i6 - localLayoutParams3.rightMargin));
            int i11 = Math.max(getPaddingTop() + localLayoutParams3.topMargin, Math.min(i9, i5 - getPaddingBottom() - i7 - localLayoutParams3.bottomMargin));
            localView1.layout(i10, i11, i10 + i6, i11 + i7);
            break;
            i8 += i6;
            break label316;
            i8 += i6 / 2;
            break label316;
            i9 = i7 + 0;
            continue;
            i9 = 0 + i7 / 2;
          }
        }
        LayoutParams localLayoutParams2 = (LayoutParams)localView1.getLayoutParams();
        Rect localRect1 = this.mTempRect1;
        localRect1.set(getPaddingLeft() + localLayoutParams2.leftMargin, getPaddingTop() + localLayoutParams2.topMargin, getWidth() - getPaddingRight() - localLayoutParams2.rightMargin, getHeight() - getPaddingBottom() - localLayoutParams2.bottomMargin);
        if ((this.mLastInsets != null) && (ViewCompat.getFitsSystemWindows(this)) && (!ViewCompat.getFitsSystemWindows(localView1)))
        {
          localRect1.left += this.mLastInsets.getSystemWindowInsetLeft();
          localRect1.top += this.mLastInsets.getSystemWindowInsetTop();
          localRect1.right -= this.mLastInsets.getSystemWindowInsetRight();
          localRect1.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect localRect2 = this.mTempRect2;
        GravityCompat.apply(resolveGravity(localLayoutParams2.gravity), localView1.getMeasuredWidth(), localView1.getMeasuredHeight(), localRect1, localRect2, i);
        localView1.layout(localRect2.left, localRect2.top, localRect2.right, localRect2.bottom);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mDependencySortedChildren.clear();
    int i = getChildCount();
    int j = 0;
    if (j < i)
    {
      View localView2 = getChildAt(j);
      LayoutParams localLayoutParams2 = getResolvedLayoutParams(localView2);
      if (localLayoutParams2.mAnchorId == -1)
      {
        localLayoutParams2.mAnchorDirectChild = null;
        localLayoutParams2.mAnchorView = null;
      }
      for (;;)
      {
        this.mDependencySortedChildren.add(localView2);
        j++;
        break;
        int i26;
        label104:
        View localView3;
        if (localLayoutParams2.mAnchorView != null)
        {
          if (localLayoutParams2.mAnchorView.getId() != localLayoutParams2.mAnchorId)
          {
            i26 = 0;
            if (i26 != 0) {
              break label279;
            }
          }
        }
        else
        {
          localLayoutParams2.mAnchorView = findViewById(localLayoutParams2.mAnchorId);
          if (localLayoutParams2.mAnchorView == null) {
            break label329;
          }
          localView3 = localLayoutParams2.mAnchorView;
        }
        for (ViewParent localViewParent1 = localLayoutParams2.mAnchorView.getParent();; localViewParent1 = localViewParent1.getParent())
        {
          if ((localViewParent1 == this) || (localViewParent1 == null)) {
            break label319;
          }
          if (localViewParent1 == localView2)
          {
            if (isInEditMode())
            {
              localLayoutParams2.mAnchorDirectChild = null;
              localLayoutParams2.mAnchorView = null;
              break;
              View localView4 = localLayoutParams2.mAnchorView;
              for (ViewParent localViewParent2 = localLayoutParams2.mAnchorView.getParent();; localViewParent2 = localViewParent2.getParent())
              {
                if (localViewParent2 == this) {
                  break label268;
                }
                if ((localViewParent2 == null) || (localViewParent2 == localView2))
                {
                  localLayoutParams2.mAnchorDirectChild = null;
                  localLayoutParams2.mAnchorView = null;
                  i26 = 0;
                  break;
                }
                if ((localViewParent2 instanceof View)) {
                  localView4 = (View)localViewParent2;
                }
              }
              label268:
              localLayoutParams2.mAnchorDirectChild = localView4;
              i26 = 1;
              break label104;
              label279:
              break;
            }
            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
          }
          if ((localViewParent1 instanceof View)) {
            localView3 = (View)localViewParent1;
          }
        }
        label319:
        localLayoutParams2.mAnchorDirectChild = localView3;
        continue;
        label329:
        if (!isInEditMode()) {
          break label351;
        }
        localLayoutParams2.mAnchorDirectChild = null;
        localLayoutParams2.mAnchorView = null;
      }
      label351:
      throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + getResources().getResourceName(localLayoutParams2.mAnchorId) + " to anchor view " + localView2);
    }
    selectionSort(this.mDependencySortedChildren, this.mLayoutDependencyComparator);
    ensurePreDrawListener();
    int k = getPaddingLeft();
    int m = getPaddingTop();
    int n = getPaddingRight();
    int i1 = getPaddingBottom();
    int i2 = ViewCompat.getLayoutDirection(this);
    int i3;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    int i9;
    int i10;
    int i11;
    int i12;
    int i13;
    label522:
    int i15;
    label536:
    View localView1;
    LayoutParams localLayoutParams1;
    int i18;
    int i24;
    int i25;
    if (i2 == 1)
    {
      i3 = 1;
      i4 = View.MeasureSpec.getMode(paramInt1);
      i5 = View.MeasureSpec.getSize(paramInt1);
      i6 = View.MeasureSpec.getMode(paramInt2);
      i7 = View.MeasureSpec.getSize(paramInt2);
      i8 = k + n;
      i9 = m + i1;
      i10 = getSuggestedMinimumWidth();
      i11 = getSuggestedMinimumHeight();
      i12 = 0;
      if ((this.mLastInsets == null) || (!ViewCompat.getFitsSystemWindows(this))) {
        break label829;
      }
      i13 = 1;
      int i14 = this.mDependencySortedChildren.size();
      i15 = 0;
      if (i15 >= i14) {
        break label877;
      }
      localView1 = (View)this.mDependencySortedChildren.get(i15);
      localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
      int i17 = localLayoutParams1.keyline;
      i18 = 0;
      if (i17 >= 0)
      {
        i18 = 0;
        if (i4 != 0)
        {
          i24 = getKeyline(localLayoutParams1.keyline);
          i25 = 0x7 & GravityCompat.getAbsoluteGravity(resolveKeylineGravity(localLayoutParams1.gravity), i2);
          if (((i25 != 3) || (i3 != 0)) && ((i25 != 5) || (i3 == 0))) {
            break label835;
          }
          i18 = Math.max(0, i5 - n - i24);
        }
      }
    }
    for (;;)
    {
      int i19 = paramInt1;
      int i20 = paramInt2;
      if ((i13 != 0) && (!ViewCompat.getFitsSystemWindows(localView1)))
      {
        int i22 = this.mLastInsets.getSystemWindowInsetLeft() + this.mLastInsets.getSystemWindowInsetRight();
        int i23 = this.mLastInsets.getSystemWindowInsetTop() + this.mLastInsets.getSystemWindowInsetBottom();
        i19 = View.MeasureSpec.makeMeasureSpec(i5 - i22, i4);
        i20 = View.MeasureSpec.makeMeasureSpec(i7 - i23, i6);
      }
      measureChildWithMargins(localView1, i19, i18, i20, 0);
      int i21 = i8 + localView1.getMeasuredWidth() + localLayoutParams1.leftMargin + localLayoutParams1.rightMargin;
      i10 = Math.max(i10, i21);
      i11 = Math.max(i11, i9 + localView1.getMeasuredHeight() + localLayoutParams1.topMargin + localLayoutParams1.bottomMargin);
      i12 = ViewCompat.combineMeasuredStates(i12, ViewCompat.getMeasuredState(localView1));
      i15++;
      break label536;
      i3 = 0;
      break;
      label829:
      i13 = 0;
      break label522;
      label835:
      if ((i25 != 5) || (i3 != 0))
      {
        i18 = 0;
        if (i25 == 3)
        {
          i18 = 0;
          if (i3 == 0) {}
        }
      }
      else
      {
        i18 = Math.max(0, i24 - k);
      }
    }
    label877:
    int i16 = 0xFF000000 & i12;
    setMeasuredDimension(ViewCompat.resolveSizeAndState(i10, paramInt1, i16), ViewCompat.resolveSizeAndState(i11, paramInt2, i12 << 16));
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++) {
      getChildAt(j).getLayoutParams();
    }
    return false;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++) {
      getChildAt(j).getLayoutParams();
    }
    return false;
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = getChildCount();
    int n = 0;
    if (n < m)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(n).getLayoutParams();
      if ((localLayoutParams.mDidAcceptNestedScroll) && (localLayoutParams.mBehavior != null))
      {
        int[] arrayOfInt = this.mTempIntPair;
        this.mTempIntPair[1] = 0;
        arrayOfInt[0] = 0;
        if (paramInt1 <= 0) {
          break label116;
        }
        i = Math.max(i, this.mTempIntPair[0]);
        label90:
        if (paramInt2 <= 0) {
          break label132;
        }
      }
      label132:
      for (j = Math.max(j, this.mTempIntPair[1]);; j = Math.min(j, this.mTempIntPair[1]))
      {
        k = 1;
        n++;
        break;
        label116:
        i = Math.min(i, this.mTempIntPair[0]);
        break label90;
      }
    }
    paramArrayOfInt[0] = i;
    paramArrayOfInt[1] = j;
    if (k != 0) {
      dispatchOnDependentViewChanged$1385ff();
    }
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(k).getLayoutParams();
      if ((localLayoutParams.mDidAcceptNestedScroll) && (localLayoutParams.mBehavior != null)) {
        j = 1;
      }
    }
    if (j != 0) {
      dispatchOnDependentViewChanged$1385ff();
    }
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt)
  {
    this.mNestedScrollingParentHelper.mNestedScrollAxes = paramInt;
    this.mNestedScrollingDirectChild = paramView1;
    this.mNestedScrollingTarget = paramView2;
    int i = getChildCount();
    for (int j = 0; j < i; j++) {
      getChildAt(j).getLayoutParams();
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    SparseArray localSparseArray = localSavedState.behaviorStates;
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      View localView = getChildAt(i);
      int k = localView.getId();
      Behavior localBehavior = getResolvedLayoutParams(localView).mBehavior;
      if ((k != -1) && (localBehavior != null)) {
        localSparseArray.get(k);
      }
      i++;
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    SparseArray localSparseArray = new SparseArray();
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      View localView = getChildAt(i);
      int k = localView.getId();
      Behavior localBehavior = ((LayoutParams)localView.getLayoutParams()).mBehavior;
      if ((k != -1) && (localBehavior != null))
      {
        AbsSavedState localAbsSavedState = View.BaseSavedState.EMPTY_STATE;
        if (localAbsSavedState != null) {
          localSparseArray.append(k, localAbsSavedState);
        }
      }
      i++;
    }
    localSavedState.behaviorStates = localSparseArray;
    return localSavedState;
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++) {
      ((LayoutParams)getChildAt(j).getLayoutParams()).mDidAcceptNestedScroll = false;
    }
    return false;
  }
  
  public void onStopNestedScroll(View paramView)
  {
    this.mNestedScrollingParentHelper.mNestedScrollAxes = 0;
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(j).getLayoutParams();
      if (localLayoutParams.mDidAcceptNestedScroll)
      {
        localLayoutParams.mDidAcceptNestedScroll = false;
        localLayoutParams.mDidChangeAfterNestedScroll = false;
      }
    }
    this.mNestedScrollingDirectChild = null;
    this.mNestedScrollingTarget = null;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    MotionEvent localMotionEvent = null;
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    View localView = this.mBehaviorTouchView;
    boolean bool1 = false;
    boolean bool2;
    if (localView == null)
    {
      bool1 = performIntercept(paramMotionEvent, 1);
      bool2 = false;
      if (!bool1) {}
    }
    else
    {
      Behavior localBehavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).mBehavior;
      bool2 = false;
      if (localBehavior != null) {
        bool2 = localBehavior.onTouchEvent$29533e61(paramMotionEvent);
      }
    }
    if (this.mBehaviorTouchView == null) {
      bool2 |= super.onTouchEvent(paramMotionEvent);
    }
    for (;;)
    {
      if (localMotionEvent != null) {
        localMotionEvent.recycle();
      }
      if ((i == 1) || (i == 3)) {
        resetTouchBehaviors();
      }
      return bool2;
      localMotionEvent = null;
      if (bool1)
      {
        long l = SystemClock.uptimeMillis();
        localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        super.onTouchEvent(localMotionEvent);
      }
    }
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    if (paramBoolean) {
      resetTouchBehaviors();
    }
  }
  
  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
  {
    this.mOnHierarchyChangeListener = paramOnHierarchyChangeListener;
  }
  
  public void setStatusBarBackground(Drawable paramDrawable)
  {
    this.mStatusBarBackground = paramDrawable;
    invalidate();
  }
  
  public void setStatusBarBackgroundColor(int paramInt)
  {
    setStatusBarBackground(new ColorDrawable(paramInt));
  }
  
  public void setStatusBarBackgroundResource(int paramInt)
  {
    if (paramInt != 0) {}
    for (Drawable localDrawable = ContextCompat.getDrawable(getContext(), paramInt);; localDrawable = null)
    {
      setStatusBarBackground(localDrawable);
      return;
    }
  }
  
  public static abstract class Behavior<V extends View>
  {
    public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean onTouchEvent$29533e61(MotionEvent paramMotionEvent)
    {
      return false;
    }
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface DefaultBehavior
  {
    Class<? extends CoordinatorLayout.Behavior> value();
  }
  
  public static final class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public int anchorGravity = 0;
    public int gravity = 0;
    public int keyline = -1;
    View mAnchorDirectChild;
    int mAnchorId = -1;
    View mAnchorView;
    CoordinatorLayout.Behavior mBehavior;
    boolean mBehaviorResolved = false;
    Object mBehaviorTag;
    boolean mDidAcceptNestedScroll;
    boolean mDidBlockInteraction;
    boolean mDidChangeAfterNestedScroll;
    final Rect mLastChildRect = new Rect();
    
    public LayoutParams()
    {
      super(-2);
    }
    
    LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CoordinatorLayout_LayoutParams);
      this.gravity = localTypedArray.getInteger(R.styleable.CoordinatorLayout_LayoutParams_android_layout_gravity, 0);
      this.mAnchorId = localTypedArray.getResourceId(R.styleable.CoordinatorLayout_LayoutParams_layout_anchor, -1);
      this.anchorGravity = localTypedArray.getInteger(R.styleable.CoordinatorLayout_LayoutParams_layout_anchorGravity, 0);
      this.keyline = localTypedArray.getInteger(R.styleable.CoordinatorLayout_LayoutParams_layout_keyline, -1);
      this.mBehaviorResolved = localTypedArray.hasValue(R.styleable.CoordinatorLayout_LayoutParams_layout_behavior);
      if (this.mBehaviorResolved) {
        this.mBehavior = CoordinatorLayout.parseBehavior(paramContext, paramAttributeSet, localTypedArray.getString(R.styleable.CoordinatorLayout_LayoutParams_layout_behavior));
      }
      localTypedArray.recycle();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public final void setBehavior(CoordinatorLayout.Behavior paramBehavior)
    {
      if (this.mBehavior != paramBehavior)
      {
        this.mBehavior = paramBehavior;
        this.mBehaviorTag = null;
        this.mBehaviorResolved = true;
      }
    }
  }
  
  final class OnPreDrawListener
    implements ViewTreeObserver.OnPreDrawListener
  {
    OnPreDrawListener() {}
    
    public final boolean onPreDraw()
    {
      CoordinatorLayout.this.dispatchOnDependentViewChanged$1385ff();
      return true;
    }
  }
  
  protected static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks() {});
    SparseArray<Parcelable> behaviorStates;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super();
      int i = paramParcel.readInt();
      int[] arrayOfInt = new int[i];
      paramParcel.readIntArray(arrayOfInt);
      Parcelable[] arrayOfParcelable = paramParcel.readParcelableArray(paramClassLoader);
      this.behaviorStates = new SparseArray(i);
      for (int j = 0; j < i; j++) {
        this.behaviorStates.append(arrayOfInt[j], arrayOfParcelable[j]);
      }
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      if (this.behaviorStates != null) {}
      int[] arrayOfInt;
      Parcelable[] arrayOfParcelable;
      for (int i = this.behaviorStates.size();; i = 0)
      {
        paramParcel.writeInt(i);
        arrayOfInt = new int[i];
        arrayOfParcelable = new Parcelable[i];
        for (int j = 0; j < i; j++)
        {
          arrayOfInt[j] = this.behaviorStates.keyAt(j);
          arrayOfParcelable[j] = ((Parcelable)this.behaviorStates.valueAt(j));
        }
      }
      paramParcel.writeIntArray(arrayOfInt);
      paramParcel.writeParcelableArray(arrayOfParcelable, paramInt);
    }
  }
  
  static final class ViewElevationComparator
    implements Comparator<View>
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.CoordinatorLayout
 * JD-Core Version:    0.7.0.1
 */