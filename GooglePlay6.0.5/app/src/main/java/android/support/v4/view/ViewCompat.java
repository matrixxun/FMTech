package android.support.v4.view;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public final class ViewCompat
{
  static final ViewCompatImpl IMPL = new BaseViewCompatImpl();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 23)
    {
      IMPL = new MarshmallowViewCompatImpl();
      return;
    }
    if (i >= 21)
    {
      IMPL = new LollipopViewCompatImpl();
      return;
    }
    if (i >= 19)
    {
      IMPL = new KitKatViewCompatImpl();
      return;
    }
    if (i >= 17)
    {
      IMPL = new JbMr1ViewCompatImpl();
      return;
    }
    if (i >= 16)
    {
      IMPL = new JBViewCompatImpl();
      return;
    }
    if (i >= 15)
    {
      IMPL = new ICSMr1ViewCompatImpl();
      return;
    }
    if (i >= 14)
    {
      IMPL = new ICSViewCompatImpl();
      return;
    }
    if (i >= 11)
    {
      IMPL = new HCViewCompatImpl();
      return;
    }
    if (i >= 9)
    {
      IMPL = new GBViewCompatImpl();
      return;
    }
    if (i >= 7)
    {
      IMPL = new EclairMr1ViewCompatImpl();
      return;
    }
  }
  
  public static ViewPropertyAnimatorCompat animate(View paramView)
  {
    return IMPL.animate(paramView);
  }
  
  public static boolean canScrollHorizontally(View paramView, int paramInt)
  {
    return IMPL.canScrollHorizontally(paramView, paramInt);
  }
  
  public static boolean canScrollVertically(View paramView, int paramInt)
  {
    return IMPL.canScrollVertically(paramView, paramInt);
  }
  
  public static int combineMeasuredStates(int paramInt1, int paramInt2)
  {
    return IMPL.combineMeasuredStates(paramInt1, paramInt2);
  }
  
  public static WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
  {
    return IMPL.dispatchApplyWindowInsets(paramView, paramWindowInsetsCompat);
  }
  
  public static float getAlpha(View paramView)
  {
    return IMPL.getAlpha(paramView);
  }
  
  public static float getElevation(View paramView)
  {
    return IMPL.getElevation(paramView);
  }
  
  public static boolean getFitsSystemWindows(View paramView)
  {
    return IMPL.getFitsSystemWindows(paramView);
  }
  
  public static int getImportantForAccessibility(View paramView)
  {
    return IMPL.getImportantForAccessibility(paramView);
  }
  
  public static int getLayerType(View paramView)
  {
    return IMPL.getLayerType(paramView);
  }
  
  public static int getLayoutDirection(View paramView)
  {
    return IMPL.getLayoutDirection(paramView);
  }
  
  public static int getMeasuredHeightAndState(View paramView)
  {
    return IMPL.getMeasuredHeightAndState(paramView);
  }
  
  public static int getMeasuredState(View paramView)
  {
    return IMPL.getMeasuredState(paramView);
  }
  
  public static int getMeasuredWidthAndState(View paramView)
  {
    return IMPL.getMeasuredWidthAndState(paramView);
  }
  
  public static int getMinimumHeight(View paramView)
  {
    return IMPL.getMinimumHeight(paramView);
  }
  
  public static int getMinimumWidth(View paramView)
  {
    return IMPL.getMinimumWidth(paramView);
  }
  
  public static int getOverScrollMode(View paramView)
  {
    return IMPL.getOverScrollMode(paramView);
  }
  
  public static int getPaddingEnd(View paramView)
  {
    return IMPL.getPaddingEnd(paramView);
  }
  
  public static int getPaddingStart(View paramView)
  {
    return IMPL.getPaddingStart(paramView);
  }
  
  public static ViewParent getParentForAccessibility(View paramView)
  {
    return IMPL.getParentForAccessibility(paramView);
  }
  
  public static float getScaleX(View paramView)
  {
    return IMPL.getScaleX(paramView);
  }
  
  public static float getTranslationX(View paramView)
  {
    return IMPL.getTranslationX(paramView);
  }
  
  public static float getTranslationY(View paramView)
  {
    return IMPL.getTranslationY(paramView);
  }
  
  public static int getWindowSystemUiVisibility(View paramView)
  {
    return IMPL.getWindowSystemUiVisibility(paramView);
  }
  
  public static float getZ(View paramView)
  {
    return IMPL.getZ(paramView);
  }
  
  public static boolean hasAccessibilityDelegate(View paramView)
  {
    return IMPL.hasAccessibilityDelegate(paramView);
  }
  
  public static boolean hasOnClickListeners(View paramView)
  {
    return IMPL.hasOnClickListeners(paramView);
  }
  
  public static boolean hasOverlappingRendering(View paramView)
  {
    return IMPL.hasOverlappingRendering(paramView);
  }
  
  public static boolean hasTransientState(View paramView)
  {
    return IMPL.hasTransientState(paramView);
  }
  
  public static boolean isAttachedToWindow(View paramView)
  {
    return IMPL.isAttachedToWindow(paramView);
  }
  
  public static boolean isLaidOut(View paramView)
  {
    return IMPL.isLaidOut(paramView);
  }
  
  public static boolean isNestedScrollingEnabled(View paramView)
  {
    return IMPL.isNestedScrollingEnabled(paramView);
  }
  
  public static boolean isPaddingRelative(View paramView)
  {
    return IMPL.isPaddingRelative(paramView);
  }
  
  public static void jumpDrawablesToCurrentState(View paramView)
  {
    IMPL.jumpDrawablesToCurrentState(paramView);
  }
  
  public static WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
  {
    return IMPL.onApplyWindowInsets(paramView, paramWindowInsetsCompat);
  }
  
  public static void postInvalidateOnAnimation(View paramView)
  {
    IMPL.postInvalidateOnAnimation(paramView);
  }
  
  public static void postOnAnimation(View paramView, Runnable paramRunnable)
  {
    IMPL.postOnAnimation(paramView, paramRunnable);
  }
  
  public static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
  {
    IMPL.postOnAnimationDelayed(paramView, paramRunnable, paramLong);
  }
  
  public static void requestApplyInsets(View paramView)
  {
    IMPL.requestApplyInsets(paramView);
  }
  
  public static int resolveSizeAndState(int paramInt1, int paramInt2, int paramInt3)
  {
    return IMPL.resolveSizeAndState(paramInt1, paramInt2, paramInt3);
  }
  
  public static void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
  {
    IMPL.setAccessibilityDelegate(paramView, paramAccessibilityDelegateCompat);
  }
  
  public static void setAccessibilityLiveRegion$5359dc9a(View paramView)
  {
    IMPL.setAccessibilityLiveRegion(paramView, 1);
  }
  
  public static void setActivated(View paramView, boolean paramBoolean)
  {
    IMPL.setActivated(paramView, paramBoolean);
  }
  
  public static void setAlpha(View paramView, float paramFloat)
  {
    IMPL.setAlpha(paramView, paramFloat);
  }
  
  public static void setBackgroundTintList(View paramView, ColorStateList paramColorStateList)
  {
    IMPL.setBackgroundTintList(paramView, paramColorStateList);
  }
  
  public static void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode)
  {
    IMPL.setBackgroundTintMode(paramView, paramMode);
  }
  
  public static void setChildrenDrawingOrderEnabled$4d3af60(ViewGroup paramViewGroup)
  {
    IMPL.setChildrenDrawingOrderEnabled(paramViewGroup, true);
  }
  
  public static void setElevation(View paramView, float paramFloat)
  {
    IMPL.setElevation(paramView, paramFloat);
  }
  
  public static void setImportantForAccessibility(View paramView, int paramInt)
  {
    IMPL.setImportantForAccessibility(paramView, paramInt);
  }
  
  public static void setLayerType(View paramView, int paramInt, Paint paramPaint)
  {
    IMPL.setLayerType(paramView, paramInt, paramPaint);
  }
  
  public static void setOnApplyWindowInsetsListener(View paramView, OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
  {
    IMPL.setOnApplyWindowInsetsListener(paramView, paramOnApplyWindowInsetsListener);
  }
  
  public static void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.setPaddingRelative(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setSaveFromParentEnabled$53599cc9(View paramView)
  {
    IMPL.setSaveFromParentEnabled(paramView, false);
  }
  
  public static void setScaleX(View paramView, float paramFloat)
  {
    IMPL.setScaleX(paramView, paramFloat);
  }
  
  public static void setScaleY(View paramView, float paramFloat)
  {
    IMPL.setScaleY(paramView, paramFloat);
  }
  
  public static void setScrollIndicators(View paramView, int paramInt1, int paramInt2)
  {
    IMPL.setScrollIndicators(paramView, paramInt1, 3);
  }
  
  public static void setTranslationX(View paramView, float paramFloat)
  {
    IMPL.setTranslationX(paramView, paramFloat);
  }
  
  public static void setTranslationY(View paramView, float paramFloat)
  {
    IMPL.setTranslationY(paramView, paramFloat);
  }
  
  public static void stopNestedScroll(View paramView)
  {
    IMPL.stopNestedScroll(paramView);
  }
  
  static class BaseViewCompatImpl
    implements ViewCompat.ViewCompatImpl
  {
    WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;
    
    public ViewPropertyAnimatorCompat animate(View paramView)
    {
      return new ViewPropertyAnimatorCompat(paramView);
    }
    
    public boolean canScrollHorizontally(View paramView, int paramInt)
    {
      if ((paramView instanceof ScrollingView))
      {
        ScrollingView localScrollingView = (ScrollingView)paramView;
        int i = localScrollingView.computeHorizontalScrollOffset();
        int j = localScrollingView.computeHorizontalScrollRange() - localScrollingView.computeHorizontalScrollExtent();
        int k;
        if (j != 0) {
          if (paramInt < 0) {
            if (i > 0) {
              k = 1;
            }
          }
        }
        while (k != 0)
        {
          return true;
          k = 0;
          continue;
          if (i < j - 1) {
            k = 1;
          } else {
            k = 0;
          }
        }
      }
      return false;
    }
    
    public boolean canScrollVertically(View paramView, int paramInt)
    {
      if ((paramView instanceof ScrollingView))
      {
        ScrollingView localScrollingView = (ScrollingView)paramView;
        int i = localScrollingView.computeVerticalScrollOffset();
        int j = localScrollingView.computeVerticalScrollRange() - localScrollingView.computeVerticalScrollExtent();
        int k;
        if (j != 0) {
          if (paramInt < 0) {
            if (i > 0) {
              k = 1;
            }
          }
        }
        while (k != 0)
        {
          return true;
          k = 0;
          continue;
          if (i < j - 1) {
            k = 1;
          } else {
            k = 0;
          }
        }
      }
      return false;
    }
    
    public int combineMeasuredStates(int paramInt1, int paramInt2)
    {
      return paramInt1 | paramInt2;
    }
    
    public WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
    {
      return paramWindowInsetsCompat;
    }
    
    public float getAlpha(View paramView)
    {
      return 1.0F;
    }
    
    public float getElevation(View paramView)
    {
      return 0.0F;
    }
    
    public boolean getFitsSystemWindows(View paramView)
    {
      return false;
    }
    
    long getFrameTime()
    {
      return 10L;
    }
    
    public int getImportantForAccessibility(View paramView)
    {
      return 0;
    }
    
    public int getLayerType(View paramView)
    {
      return 0;
    }
    
    public int getLayoutDirection(View paramView)
    {
      return 0;
    }
    
    public int getMeasuredHeightAndState(View paramView)
    {
      return paramView.getMeasuredHeight();
    }
    
    public int getMeasuredState(View paramView)
    {
      return 0;
    }
    
    public int getMeasuredWidthAndState(View paramView)
    {
      return paramView.getMeasuredWidth();
    }
    
    public int getMinimumHeight(View paramView)
    {
      return ViewCompatBase.getMinimumHeight(paramView);
    }
    
    public int getMinimumWidth(View paramView)
    {
      return ViewCompatBase.getMinimumWidth(paramView);
    }
    
    public int getOverScrollMode(View paramView)
    {
      return 2;
    }
    
    public int getPaddingEnd(View paramView)
    {
      return paramView.getPaddingRight();
    }
    
    public int getPaddingStart(View paramView)
    {
      return paramView.getPaddingLeft();
    }
    
    public ViewParent getParentForAccessibility(View paramView)
    {
      return paramView.getParent();
    }
    
    public float getScaleX(View paramView)
    {
      return 0.0F;
    }
    
    public float getTranslationX(View paramView)
    {
      return 0.0F;
    }
    
    public float getTranslationY(View paramView)
    {
      return 0.0F;
    }
    
    public float getTranslationZ(View paramView)
    {
      return 0.0F;
    }
    
    public int getWindowSystemUiVisibility(View paramView)
    {
      return 0;
    }
    
    public float getZ(View paramView)
    {
      return getTranslationZ(paramView) + getElevation(paramView);
    }
    
    public boolean hasAccessibilityDelegate(View paramView)
    {
      return false;
    }
    
    public boolean hasOnClickListeners(View paramView)
    {
      return false;
    }
    
    public boolean hasOverlappingRendering(View paramView)
    {
      return true;
    }
    
    public boolean hasTransientState(View paramView)
    {
      return false;
    }
    
    public boolean isAttachedToWindow(View paramView)
    {
      return paramView.getWindowToken() != null;
    }
    
    public boolean isLaidOut(View paramView)
    {
      return (paramView.getWidth() > 0) && (paramView.getHeight() > 0);
    }
    
    public boolean isNestedScrollingEnabled(View paramView)
    {
      if ((paramView instanceof NestedScrollingChild)) {
        return ((NestedScrollingChild)paramView).isNestedScrollingEnabled();
      }
      return false;
    }
    
    public boolean isPaddingRelative(View paramView)
    {
      return false;
    }
    
    public void jumpDrawablesToCurrentState(View paramView) {}
    
    public WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
    {
      return paramWindowInsetsCompat;
    }
    
    public void postInvalidateOnAnimation(View paramView)
    {
      paramView.invalidate();
    }
    
    public void postOnAnimation(View paramView, Runnable paramRunnable)
    {
      paramView.postDelayed(paramRunnable, getFrameTime());
    }
    
    public void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postDelayed(paramRunnable, paramLong + getFrameTime());
    }
    
    public void requestApplyInsets(View paramView) {}
    
    public int resolveSizeAndState(int paramInt1, int paramInt2, int paramInt3)
    {
      return View.resolveSize(paramInt1, paramInt2);
    }
    
    public void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat) {}
    
    public void setAccessibilityLiveRegion(View paramView, int paramInt) {}
    
    public void setActivated(View paramView, boolean paramBoolean) {}
    
    public void setAlpha(View paramView, float paramFloat) {}
    
    public void setBackgroundTintList(View paramView, ColorStateList paramColorStateList)
    {
      if ((paramView instanceof TintableBackgroundView)) {
        ((TintableBackgroundView)paramView).setSupportBackgroundTintList(paramColorStateList);
      }
    }
    
    public void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode)
    {
      if ((paramView instanceof TintableBackgroundView)) {
        ((TintableBackgroundView)paramView).setSupportBackgroundTintMode(paramMode);
      }
    }
    
    public void setChildrenDrawingOrderEnabled(ViewGroup paramViewGroup, boolean paramBoolean) {}
    
    public void setElevation(View paramView, float paramFloat) {}
    
    public void setImportantForAccessibility(View paramView, int paramInt) {}
    
    public void setLayerType(View paramView, int paramInt, Paint paramPaint) {}
    
    public void setOnApplyWindowInsetsListener(View paramView, OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener) {}
    
    public void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void setSaveFromParentEnabled(View paramView, boolean paramBoolean) {}
    
    public void setScaleX(View paramView, float paramFloat) {}
    
    public void setScaleY(View paramView, float paramFloat) {}
    
    public void setScrollIndicators(View paramView, int paramInt1, int paramInt2) {}
    
    public void setTranslationX(View paramView, float paramFloat) {}
    
    public void setTranslationY(View paramView, float paramFloat) {}
    
    public void stopNestedScroll(View paramView)
    {
      if ((paramView instanceof NestedScrollingChild)) {
        ((NestedScrollingChild)paramView).stopNestedScroll();
      }
    }
  }
  
  static class EclairMr1ViewCompatImpl
    extends ViewCompat.BaseViewCompatImpl
  {
    public final void setChildrenDrawingOrderEnabled(ViewGroup paramViewGroup, boolean paramBoolean)
    {
      if (ViewCompatEclairMr1.sChildrenDrawingOrderMethod == null) {}
      try
      {
        Class[] arrayOfClass = new Class[1];
        arrayOfClass[0] = Boolean.TYPE;
        ViewCompatEclairMr1.sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", arrayOfClass);
        ViewCompatEclairMr1.sChildrenDrawingOrderMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;)
        {
          try
          {
            Method localMethod = ViewCompatEclairMr1.sChildrenDrawingOrderMethod;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(true);
            localMethod.invoke(paramViewGroup, arrayOfObject);
            return;
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", localIllegalAccessException);
            return;
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", localIllegalArgumentException);
            return;
          }
          catch (InvocationTargetException localInvocationTargetException)
          {
            Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", localInvocationTargetException);
          }
          localNoSuchMethodException = localNoSuchMethodException;
          Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", localNoSuchMethodException);
        }
      }
    }
  }
  
  static class GBViewCompatImpl
    extends ViewCompat.EclairMr1ViewCompatImpl
  {
    public final int getOverScrollMode(View paramView)
    {
      return paramView.getOverScrollMode();
    }
  }
  
  static class HCViewCompatImpl
    extends ViewCompat.GBViewCompatImpl
  {
    public final int combineMeasuredStates(int paramInt1, int paramInt2)
    {
      return View.combineMeasuredStates(paramInt1, paramInt2);
    }
    
    public final float getAlpha(View paramView)
    {
      return paramView.getAlpha();
    }
    
    final long getFrameTime()
    {
      return ValueAnimator.getFrameDelay();
    }
    
    public final int getLayerType(View paramView)
    {
      return paramView.getLayerType();
    }
    
    public final int getMeasuredHeightAndState(View paramView)
    {
      return paramView.getMeasuredHeightAndState();
    }
    
    public final int getMeasuredState(View paramView)
    {
      return paramView.getMeasuredState();
    }
    
    public final int getMeasuredWidthAndState(View paramView)
    {
      return paramView.getMeasuredWidthAndState();
    }
    
    public final float getScaleX(View paramView)
    {
      return paramView.getScaleX();
    }
    
    public final float getTranslationX(View paramView)
    {
      return paramView.getTranslationX();
    }
    
    public final float getTranslationY(View paramView)
    {
      return paramView.getTranslationY();
    }
    
    public final void jumpDrawablesToCurrentState(View paramView)
    {
      paramView.jumpDrawablesToCurrentState();
    }
    
    public final int resolveSizeAndState(int paramInt1, int paramInt2, int paramInt3)
    {
      return View.resolveSizeAndState(paramInt1, paramInt2, paramInt3);
    }
    
    public final void setActivated(View paramView, boolean paramBoolean)
    {
      paramView.setActivated(paramBoolean);
    }
    
    public final void setAlpha(View paramView, float paramFloat)
    {
      paramView.setAlpha(paramFloat);
    }
    
    public final void setLayerType(View paramView, int paramInt, Paint paramPaint)
    {
      paramView.setLayerType(paramInt, paramPaint);
    }
    
    public final void setSaveFromParentEnabled(View paramView, boolean paramBoolean)
    {
      paramView.setSaveFromParentEnabled(false);
    }
    
    public final void setScaleX(View paramView, float paramFloat)
    {
      paramView.setScaleX(paramFloat);
    }
    
    public final void setScaleY(View paramView, float paramFloat)
    {
      paramView.setScaleY(paramFloat);
    }
    
    public final void setTranslationX(View paramView, float paramFloat)
    {
      paramView.setTranslationX(paramFloat);
    }
    
    public final void setTranslationY(View paramView, float paramFloat)
    {
      paramView.setTranslationY(paramFloat);
    }
  }
  
  static class ICSMr1ViewCompatImpl
    extends ViewCompat.ICSViewCompatImpl
  {
    public final boolean hasOnClickListeners(View paramView)
    {
      return paramView.hasOnClickListeners();
    }
  }
  
  static class ICSViewCompatImpl
    extends ViewCompat.HCViewCompatImpl
  {
    static boolean accessibilityDelegateCheckFailed = false;
    static Field mAccessibilityDelegateField;
    
    public final ViewPropertyAnimatorCompat animate(View paramView)
    {
      if (this.mViewPropertyAnimatorCompatMap == null) {
        this.mViewPropertyAnimatorCompatMap = new WeakHashMap();
      }
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat)this.mViewPropertyAnimatorCompatMap.get(paramView);
      if (localViewPropertyAnimatorCompat == null)
      {
        localViewPropertyAnimatorCompat = new ViewPropertyAnimatorCompat(paramView);
        this.mViewPropertyAnimatorCompatMap.put(paramView, localViewPropertyAnimatorCompat);
      }
      return localViewPropertyAnimatorCompat;
    }
    
    public final boolean canScrollHorizontally(View paramView, int paramInt)
    {
      return paramView.canScrollHorizontally(paramInt);
    }
    
    public final boolean canScrollVertically(View paramView, int paramInt)
    {
      return paramView.canScrollVertically(paramInt);
    }
    
    public final boolean hasAccessibilityDelegate(View paramView)
    {
      if (accessibilityDelegateCheckFailed) {}
      for (;;)
      {
        return false;
        if (mAccessibilityDelegateField == null) {}
        try
        {
          Field localField = View.class.getDeclaredField("mAccessibilityDelegate");
          mAccessibilityDelegateField = localField;
          localField.setAccessible(true);
        }
        catch (Throwable localThrowable2)
        {
          try
          {
            Object localObject = mAccessibilityDelegateField.get(paramView);
            if (localObject == null) {
              continue;
            }
            return true;
          }
          catch (Throwable localThrowable1)
          {
            accessibilityDelegateCheckFailed = true;
          }
          localThrowable2 = localThrowable2;
          accessibilityDelegateCheckFailed = true;
          return false;
        }
      }
      return false;
    }
    
    public final void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
    {
      if (paramAccessibilityDelegateCompat == null) {}
      for (Object localObject = null;; localObject = paramAccessibilityDelegateCompat.mBridge)
      {
        paramView.setAccessibilityDelegate((View.AccessibilityDelegate)localObject);
        return;
      }
    }
  }
  
  static class JBViewCompatImpl
    extends ViewCompat.ICSMr1ViewCompatImpl
  {
    public final boolean getFitsSystemWindows(View paramView)
    {
      return paramView.getFitsSystemWindows();
    }
    
    public final int getImportantForAccessibility(View paramView)
    {
      return paramView.getImportantForAccessibility();
    }
    
    public final int getMinimumHeight(View paramView)
    {
      return paramView.getMinimumHeight();
    }
    
    public final int getMinimumWidth(View paramView)
    {
      return paramView.getMinimumWidth();
    }
    
    public final ViewParent getParentForAccessibility(View paramView)
    {
      return paramView.getParentForAccessibility();
    }
    
    public final boolean hasOverlappingRendering(View paramView)
    {
      return paramView.hasOverlappingRendering();
    }
    
    public final boolean hasTransientState(View paramView)
    {
      return paramView.hasTransientState();
    }
    
    public final void postInvalidateOnAnimation(View paramView)
    {
      paramView.postInvalidateOnAnimation();
    }
    
    public final void postOnAnimation(View paramView, Runnable paramRunnable)
    {
      paramView.postOnAnimation(paramRunnable);
    }
    
    public final void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postOnAnimationDelayed(paramRunnable, paramLong);
    }
    
    public void requestApplyInsets(View paramView)
    {
      paramView.requestFitSystemWindows();
    }
    
    public void setImportantForAccessibility(View paramView, int paramInt)
    {
      if (paramInt == 4) {
        paramInt = 2;
      }
      paramView.setImportantForAccessibility(paramInt);
    }
  }
  
  static class JbMr1ViewCompatImpl
    extends ViewCompat.JBViewCompatImpl
  {
    public final int getLayoutDirection(View paramView)
    {
      return paramView.getLayoutDirection();
    }
    
    public final int getPaddingEnd(View paramView)
    {
      return paramView.getPaddingEnd();
    }
    
    public final int getPaddingStart(View paramView)
    {
      return paramView.getPaddingStart();
    }
    
    public final int getWindowSystemUiVisibility(View paramView)
    {
      return paramView.getWindowSystemUiVisibility();
    }
    
    public final boolean isPaddingRelative(View paramView)
    {
      return paramView.isPaddingRelative();
    }
    
    public final void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  static class JbMr2ViewCompatImpl
    extends ViewCompat.JbMr1ViewCompatImpl
  {}
  
  static class KitKatViewCompatImpl
    extends ViewCompat.JbMr2ViewCompatImpl
  {
    public final boolean isAttachedToWindow(View paramView)
    {
      return paramView.isAttachedToWindow();
    }
    
    public final boolean isLaidOut(View paramView)
    {
      return paramView.isLaidOut();
    }
    
    public final void setAccessibilityLiveRegion(View paramView, int paramInt)
    {
      paramView.setAccessibilityLiveRegion(1);
    }
    
    public final void setImportantForAccessibility(View paramView, int paramInt)
    {
      paramView.setImportantForAccessibility(paramInt);
    }
  }
  
  static class LollipopViewCompatImpl
    extends ViewCompat.KitKatViewCompatImpl
  {
    public final WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
    {
      if ((paramWindowInsetsCompat instanceof WindowInsetsCompatApi21))
      {
        WindowInsets localWindowInsets1 = ((WindowInsetsCompatApi21)paramWindowInsetsCompat).mSource;
        WindowInsets localWindowInsets2 = paramView.dispatchApplyWindowInsets(localWindowInsets1);
        if (localWindowInsets2 != localWindowInsets1) {
          paramWindowInsetsCompat = new WindowInsetsCompatApi21(localWindowInsets2);
        }
      }
      return paramWindowInsetsCompat;
    }
    
    public final float getElevation(View paramView)
    {
      return paramView.getElevation();
    }
    
    public final float getTranslationZ(View paramView)
    {
      return paramView.getTranslationZ();
    }
    
    public final float getZ(View paramView)
    {
      return paramView.getZ();
    }
    
    public final boolean isNestedScrollingEnabled(View paramView)
    {
      return paramView.isNestedScrollingEnabled();
    }
    
    public final WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
    {
      if ((paramWindowInsetsCompat instanceof WindowInsetsCompatApi21))
      {
        WindowInsets localWindowInsets1 = ((WindowInsetsCompatApi21)paramWindowInsetsCompat).mSource;
        WindowInsets localWindowInsets2 = paramView.onApplyWindowInsets(localWindowInsets1);
        if (localWindowInsets2 != localWindowInsets1) {
          paramWindowInsetsCompat = new WindowInsetsCompatApi21(localWindowInsets2);
        }
      }
      return paramWindowInsetsCompat;
    }
    
    public final void requestApplyInsets(View paramView)
    {
      paramView.requestApplyInsets();
    }
    
    public final void setBackgroundTintList(View paramView, ColorStateList paramColorStateList)
    {
      paramView.setBackgroundTintList(paramColorStateList);
    }
    
    public final void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode)
    {
      paramView.setBackgroundTintMode(paramMode);
    }
    
    public final void setElevation(View paramView, float paramFloat)
    {
      paramView.setElevation(paramFloat);
    }
    
    public final void setOnApplyWindowInsetsListener(View paramView, OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
    {
      paramView.setOnApplyWindowInsetsListener(new ViewCompatLollipop.1(paramOnApplyWindowInsetsListener));
    }
    
    public final void stopNestedScroll(View paramView)
    {
      paramView.stopNestedScroll();
    }
  }
  
  static final class MarshmallowViewCompatImpl
    extends ViewCompat.LollipopViewCompatImpl
  {
    public final void setScrollIndicators(View paramView, int paramInt1, int paramInt2)
    {
      paramView.setScrollIndicators(paramInt1, paramInt2);
    }
  }
  
  static abstract interface ViewCompatImpl
  {
    public abstract ViewPropertyAnimatorCompat animate(View paramView);
    
    public abstract boolean canScrollHorizontally(View paramView, int paramInt);
    
    public abstract boolean canScrollVertically(View paramView, int paramInt);
    
    public abstract int combineMeasuredStates(int paramInt1, int paramInt2);
    
    public abstract WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat);
    
    public abstract float getAlpha(View paramView);
    
    public abstract float getElevation(View paramView);
    
    public abstract boolean getFitsSystemWindows(View paramView);
    
    public abstract int getImportantForAccessibility(View paramView);
    
    public abstract int getLayerType(View paramView);
    
    public abstract int getLayoutDirection(View paramView);
    
    public abstract int getMeasuredHeightAndState(View paramView);
    
    public abstract int getMeasuredState(View paramView);
    
    public abstract int getMeasuredWidthAndState(View paramView);
    
    public abstract int getMinimumHeight(View paramView);
    
    public abstract int getMinimumWidth(View paramView);
    
    public abstract int getOverScrollMode(View paramView);
    
    public abstract int getPaddingEnd(View paramView);
    
    public abstract int getPaddingStart(View paramView);
    
    public abstract ViewParent getParentForAccessibility(View paramView);
    
    public abstract float getScaleX(View paramView);
    
    public abstract float getTranslationX(View paramView);
    
    public abstract float getTranslationY(View paramView);
    
    public abstract int getWindowSystemUiVisibility(View paramView);
    
    public abstract float getZ(View paramView);
    
    public abstract boolean hasAccessibilityDelegate(View paramView);
    
    public abstract boolean hasOnClickListeners(View paramView);
    
    public abstract boolean hasOverlappingRendering(View paramView);
    
    public abstract boolean hasTransientState(View paramView);
    
    public abstract boolean isAttachedToWindow(View paramView);
    
    public abstract boolean isLaidOut(View paramView);
    
    public abstract boolean isNestedScrollingEnabled(View paramView);
    
    public abstract boolean isPaddingRelative(View paramView);
    
    public abstract void jumpDrawablesToCurrentState(View paramView);
    
    public abstract WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat);
    
    public abstract void postInvalidateOnAnimation(View paramView);
    
    public abstract void postOnAnimation(View paramView, Runnable paramRunnable);
    
    public abstract void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong);
    
    public abstract void requestApplyInsets(View paramView);
    
    public abstract int resolveSizeAndState(int paramInt1, int paramInt2, int paramInt3);
    
    public abstract void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat);
    
    public abstract void setAccessibilityLiveRegion(View paramView, int paramInt);
    
    public abstract void setActivated(View paramView, boolean paramBoolean);
    
    public abstract void setAlpha(View paramView, float paramFloat);
    
    public abstract void setBackgroundTintList(View paramView, ColorStateList paramColorStateList);
    
    public abstract void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode);
    
    public abstract void setChildrenDrawingOrderEnabled(ViewGroup paramViewGroup, boolean paramBoolean);
    
    public abstract void setElevation(View paramView, float paramFloat);
    
    public abstract void setImportantForAccessibility(View paramView, int paramInt);
    
    public abstract void setLayerType(View paramView, int paramInt, Paint paramPaint);
    
    public abstract void setOnApplyWindowInsetsListener(View paramView, OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener);
    
    public abstract void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void setSaveFromParentEnabled(View paramView, boolean paramBoolean);
    
    public abstract void setScaleX(View paramView, float paramFloat);
    
    public abstract void setScaleY(View paramView, float paramFloat);
    
    public abstract void setScrollIndicators(View paramView, int paramInt1, int paramInt2);
    
    public abstract void setTranslationX(View paramView, float paramFloat);
    
    public abstract void setTranslationY(View paramView, float paramFloat);
    
    public abstract void stopNestedScroll(View paramView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewCompat
 * JD-Core Version:    0.7.0.1
 */