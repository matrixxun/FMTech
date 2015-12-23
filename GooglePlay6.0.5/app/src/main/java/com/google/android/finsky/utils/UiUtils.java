package com.google.android.finsky.utils;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Containers.ContainerMetadata;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EditorialSeriesContainer;
import com.google.android.finsky.protos.NextBanner;
import com.google.android.finsky.protos.SeriesAntenna;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public final class UiUtils
{
  private static Boolean sIsAndroidTv = null;
  private static Rect sTempRect = new Rect();
  
  public static void ensureMinimumTouchTargetSize(View paramView, Rect paramRect1, Rect paramRect2, int paramInt)
  {
    ViewParent localViewParent = paramView.getParent();
    if (!(localViewParent instanceof View)) {}
    View localView;
    do
    {
      return;
      localView = (View)localViewParent;
      if ((paramView.getVisibility() != 0) || ((paramView.getWidth() >= paramInt) && (paramView.getHeight() >= paramInt)))
      {
        paramRect1.setEmpty();
        localView.setTouchDelegate(null);
        return;
      }
      getTouchTarget(paramView, paramRect1, paramInt, paramInt);
    } while (paramRect1.equals(paramRect2));
    paramRect2.set(paramRect1);
    localView.setTouchDelegate(new TouchDelegate(paramRect1, paramView));
  }
  
  public static void fadeOutCluster(View paramView, final ClusterFadeOutListener paramClusterFadeOutListener, long paramLong)
  {
    new Handler(Looper.myLooper()).postDelayed(new Runnable()
    {
      public final void run()
      {
        Animation localAnimation = PlayAnimationUtils.getFadeOutAnimation(this.val$cluster.getContext(), 150L, new PlayAnimationUtils.AnimationListenerAdapter()
        {
          public final void onAnimationEnd(Animation paramAnonymous2Animation)
          {
            if (UiUtils.3.this.val$listener != null) {
              UiUtils.3.this.val$listener.onClusterFadeOutFinish();
            }
          }
        });
        this.val$cluster.startAnimation(localAnimation);
      }
    }, paramLong);
  }
  
  private static int getColor(String paramString, int paramInt)
  {
    if (paramString.length() > 0) {}
    try
    {
      int i = Color.parseColor(paramString.trim());
      paramInt = i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (!((Boolean)DfeApiConfig.showStagingData.get()).booleanValue()) {}
      FinskyLog.wtf("Bad color: " + paramString, new Object[] { localIllegalArgumentException });
      throw localIllegalArgumentException;
    }
    return paramInt;
  }
  
  public static int getDetailsCardColumnCount(Resources paramResources)
  {
    int i = getFeaturedGridColumnCount(paramResources, 1.0D);
    if (paramResources.getBoolean(2131427339)) {
      i = paramResources.getInteger(2131623941);
    }
    return i;
  }
  
  public static int getFeaturedGridColumnCount(Resources paramResources, double paramDouble)
  {
    if (paramResources.getBoolean(2131427340))
    {
      int i = getGridColumnContentWidth(paramResources);
      int j = paramResources.getDimensionPixelSize(2131493088);
      return Math.min((int)(paramDouble * i) / j, 5);
    }
    return paramResources.getInteger(2131623939);
  }
  
  public static int getFillColor(Common.Image paramImage, int paramInt)
  {
    return getColor(paramImage.fillColorRgb, paramInt);
  }
  
  public static int getFillColor(EditorialSeriesContainer paramEditorialSeriesContainer, int paramInt)
  {
    return getColor(paramEditorialSeriesContainer.colorThemeArgb, paramInt);
  }
  
  public static int getFillColor(SeriesAntenna paramSeriesAntenna, int paramInt)
  {
    return getColor(paramSeriesAntenna.colorThemeArgb, paramInt);
  }
  
  public static int getGridColumnContentWidth(Resources paramResources)
  {
    return paramResources.getDisplayMetrics().widthPixels - 2 * getGridHorizontalPadding(paramResources);
  }
  
  public static int getGridHorizontalPadding(Resources paramResources)
  {
    int i = paramResources.getDisplayMetrics().widthPixels;
    return Math.max(paramResources.getDimensionPixelSize(2131492924), (i - paramResources.getDimensionPixelSize(2131492882)) / 2);
  }
  
  public static String getMoreResultsStringForCluster(Context paramContext, Document paramDocument, int paramInt, View.OnClickListener paramOnClickListener, String paramString, boolean paramBoolean)
  {
    String str1;
    if (paramOnClickListener == null) {
      str1 = null;
    }
    for (;;)
    {
      return str1;
      str1 = paramContext.getString(2131362331);
      Containers.ContainerMetadata localContainerMetadata = paramDocument.mDocument.containerMetadata;
      int i;
      if (localContainerMetadata != null) {
        if (localContainerMetadata.ordered)
        {
          i = 1;
          int m = (int)localContainerMetadata.estimatedResults;
          if (m > 0)
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(m);
            str1 = paramContext.getString(2131362330, arrayOfObject);
          }
        }
      }
      while (i == 0)
      {
        return null;
        int j = paramDocument.getChildCount();
        int k = Math.min(j, paramInt);
        label107:
        String str2;
        if (paramString != null)
        {
          str2 = localContainerMetadata.nextPageUrl;
          if (!TextUtils.isEmpty(paramString)) {
            break label138;
          }
          i = 0;
        }
        for (;;)
        {
          break;
          paramString = localContainerMetadata.browseUrl;
          break label107;
          label138:
          if ((j <= k) && (paramBoolean) && (TextUtils.isEmpty(str2))) {
            i = 0;
          } else {
            i = 1;
          }
        }
        boolean bool = paramDocument.hasAntennaInfo();
        i = 0;
        if (bool) {
          i = 1;
        }
      }
    }
  }
  
  public static Pair<Integer, Integer> getPortraitScreenDimensions(Context paramContext)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i = Math.min(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels);
    int j = Math.max(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels);
    return new Pair(Integer.valueOf(i), Integer.valueOf(j));
  }
  
  public static int getRegularGridColumnCount(Resources paramResources)
  {
    if (paramResources.getBoolean(2131427340)) {
      return Math.min(getGridColumnContentWidth(paramResources) / paramResources.getDimensionPixelSize(2131493088), 5);
    }
    return paramResources.getInteger(2131623940);
  }
  
  public static int getStatusBarHeight(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    int i = localResources.getIdentifier("status_bar_height", "dimen", "android");
    if (i > 0) {
      return localResources.getDimensionPixelSize(i);
    }
    return localResources.getDimensionPixelSize(2131493522);
  }
  
  public static int getStreamQuickLinkColumnCount(Resources paramResources, int paramInt1, int paramInt2)
  {
    int i = getFeaturedGridColumnCount(paramResources, 1.0D);
    int k;
    if (paramInt1 > i) {
      for (k = i;; k--)
      {
        int m = paramInt1 % k;
        if ((m == 0) || (k - m <= 1) || (k <= 2)) {
          break;
        }
      }
    }
    int j = paramInt1 + paramInt2;
    if ((j == 1) && (i == 2))
    {
      k = i;
      return k;
    }
    return Math.min(i, Math.max((int)Math.ceil(i / 2.0F), j));
  }
  
  public static int getTextColor(NextBanner paramNextBanner, int paramInt)
  {
    return getColor(paramNextBanner.colorTextArgb, paramInt);
  }
  
  public static void getTouchTarget(View paramView, Rect paramRect, int paramInt1, int paramInt2)
  {
    int i = (paramInt1 - paramView.getHeight()) / 2;
    int j = (paramInt2 - paramView.getWidth()) / 2;
    paramView.getHitRect(paramRect);
    if (j > 0) {}
    for (int k = -j;; k = 0)
    {
      int m = 0;
      if (i > 0) {
        m = -i;
      }
      paramRect.inset(k, m);
      return;
    }
  }
  
  public static void hideKeyboard(Activity paramActivity, View paramView)
  {
    ((InputMethodManager)paramActivity.getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
  }
  
  public static int interpolateColor$4868c7be(int paramInt)
  {
    int i = 0xFF & paramInt >> 24;
    int j = 0xFF & paramInt >> 16;
    int k = 0xFF & paramInt >> 8;
    int m = paramInt & 0xFF;
    int n = (int)(216.75F + 0.15F * i);
    int i1 = (int)(216.75F + 0.15F * j);
    int i2 = (int)(216.75F + 0.15F * k);
    return (int)(216.75F + 0.15F * m) | n << 24 | i1 << 16 | i2 << 8;
  }
  
  @TargetApi(14)
  public static boolean isAccessibilityEnabled(Context paramContext)
  {
    AccessibilityManager localAccessibilityManager = (AccessibilityManager)paramContext.getSystemService("accessibility");
    if (Build.VERSION.SDK_INT >= 14) {
      return localAccessibilityManager.isTouchExplorationEnabled();
    }
    return localAccessibilityManager.isEnabled();
  }
  
  public static boolean isAndroidTv()
  {
    try
    {
      if (sIsAndroidTv == null)
      {
        FinskyApp localFinskyApp = FinskyApp.get();
        boolean bool2 = localFinskyApp.getPackageManager().hasSystemFeature("android.software.leanback");
        boolean bool3 = false;
        if (bool2)
        {
          UiModeManager localUiModeManager = (UiModeManager)localFinskyApp.getSystemService("uimode");
          bool3 = false;
          if (localUiModeManager != null)
          {
            int i = localUiModeManager.getCurrentModeType();
            bool3 = false;
            if (i == 4) {
              bool3 = true;
            }
          }
        }
        sIsAndroidTv = Boolean.valueOf(bool3);
      }
      boolean bool1 = sIsAndroidTv.booleanValue();
      return bool1;
    }
    finally {}
  }
  
  public static boolean isColorBright(int paramInt)
  {
    int i = Color.red(paramInt);
    int j = Color.green(paramInt);
    int k = Color.blue(paramInt);
    return i * 21 + j * 72 + k * 7 >= 12800;
  }
  
  public static boolean isSvgExperimentEnabled()
  {
    return FinskyApp.get().getExperiments().isEnabled(12603159L);
  }
  
  public static boolean isVisibleOnScreen(View paramView)
  {
    return paramView.getGlobalVisibleRect(sTempRect);
  }
  
  @TargetApi(11)
  public static void playShakeAnimationIfPossible(Context paramContext, TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT < 11) {
      return;
    }
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramTextView, "translationX", new float[] { 0.0F, 1.0F });
    localObjectAnimator.setInterpolator(new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        return (1.0F - paramAnonymousFloat) * this.val$shakeDelta * (float)Math.sin(3.0F * (3.141593F * (2.0F * paramAnonymousFloat)));
      }
    });
    localObjectAnimator.start();
  }
  
  @TargetApi(16)
  public static void sendAccessibilityEventWithText(Context paramContext, CharSequence paramCharSequence, View paramView)
  {
    sendAccessibilityEventWithText(paramContext, paramCharSequence, paramView, false);
  }
  
  @TargetApi(16)
  public static void sendAccessibilityEventWithText(Context paramContext, CharSequence paramCharSequence, View paramView, boolean paramBoolean)
  {
    if (!isAccessibilityEnabled(paramContext)) {
      return;
    }
    if ((paramBoolean) && (Build.VERSION.SDK_INT >= 19))
    {
      paramView.setAccessibilityLiveRegion(1);
      return;
    }
    if (Build.VERSION.SDK_INT >= 16) {}
    for (int i = 16384;; i = 8)
    {
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(i);
      localAccessibilityEvent.getText().add(paramCharSequence);
      localAccessibilityEvent.setEnabled(true);
      if (paramView != null) {
        AccessibilityEventCompat.asRecord(localAccessibilityEvent).setSource(paramView);
      }
      ((AccessibilityManager)paramContext.getSystemService("accessibility")).sendAccessibilityEvent(localAccessibilityEvent);
      return;
    }
  }
  
  public static void setBackground(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT < 16)
    {
      paramView.setBackgroundDrawable(paramDrawable);
      return;
    }
    paramView.setBackground(paramDrawable);
  }
  
  public static void setErrorOnTextView(TextView paramTextView, String paramString1, String paramString2)
  {
    paramTextView.setError(paramString2);
    String str = paramTextView.getResources().getString(2131361813, new Object[] { paramString1, paramString2 });
    sendAccessibilityEventWithText(paramTextView.getContext(), str, paramTextView, false);
  }
  
  public static void showKeyboard(Activity paramActivity, final EditText paramEditText)
  {
    paramEditText.requestFocus();
    paramEditText.postDelayed(new Runnable()
    {
      public final void run()
      {
        this.val$imm.showSoftInput(paramEditText, 1);
      }
    }, 300L);
  }
  
  public static void syncContainerVisibility(ViewGroup paramViewGroup, int paramInt)
  {
    int i = paramViewGroup.getChildCount();
    for (int j = 0; j < i; j++) {
      if (paramViewGroup.getChildAt(j).getVisibility() == 0)
      {
        paramViewGroup.setVisibility(0);
        return;
      }
    }
    paramViewGroup.setVisibility(paramInt);
  }
  
  public static abstract interface ClusterFadeOutListener
  {
    public abstract void onClusterFadeOutFinish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.UiUtils
 * JD-Core Version:    0.7.0.1
 */