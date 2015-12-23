package com.google.android.finsky.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.SetupWizardIllustration;
import com.google.android.finsky.layout.SetupWizardIllustration.1;
import com.google.android.finsky.setup.SetupWizardNavBar;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.config.GservicesValue;

public final class SetupWizardUtils
{
  public static final String ILLUSTRATION_URL_PAYMENT = (String)G.setupWizardPaymentHeaderBlueGraphicUrl.get();
  public static final String ILLUSTRATION_URL_PAYMENT_WIDE = (String)G.setupWizardPaymentHeaderWideBlueGraphicUrl.get();
  public static final String ILLUSTRATION_URL_RESTORE = (String)G.setupWizardRestoreHeaderBlueGraphicUrl.get();
  public static final String ILLUSTRATION_URL_RESTORE_WIDE = (String)G.setupWizardRestoreWideHeaderBlueGraphicUrl.get();
  
  public static void animateSliding(Activity paramActivity, boolean paramBoolean)
  {
    if (!shouldUseMaterialTheme()) {
      return;
    }
    if (paramBoolean)
    {
      paramActivity.overridePendingTransition(2131034136, 2131034137);
      return;
    }
    paramActivity.overridePendingTransition(2131034140, 2131034141);
  }
  
  public static void configureBasicMaterialUiWithoutNavBarOrImage(Activity paramActivity, SetupWizardParams paramSetupWizardParams, boolean paramBoolean)
  {
    View localView = paramActivity.findViewById(16908290);
    final int i;
    if (paramSetupWizardParams.mUseImmersiveMode)
    {
      i = 4610;
      paramActivity.getWindow().addFlags(-2147483648);
    }
    for (;;)
    {
      localView.setSystemUiVisibility(i);
      localView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
      {
        public final boolean onPreDraw()
        {
          this.val$view.setSystemUiVisibility(i);
          return true;
        }
      });
      if ((paramBoolean) && (paramSetupWizardParams.mUseImmersiveMode) && (((Boolean)G.setupWizardForceResizeForKeyboardInFullscreen.get()).booleanValue())) {
        new FullscreenAdjustResizeWorkaround(paramActivity);
      }
      return;
      paramActivity.getWindow().clearFlags(-2147483648);
      i = 0;
    }
  }
  
  @TargetApi(11)
  public static void configureBasicUi(Activity paramActivity, SetupWizardParams paramSetupWizardParams, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    configureBasicUiWithoutNavBarOrImage(paramActivity, paramSetupWizardParams, paramBoolean1);
    String str;
    SetupWizardIllustration localSetupWizardIllustration;
    if (shouldUseMaterialTheme())
    {
      configureBasicMaterialUiWithoutNavBarOrImage(paramActivity, paramSetupWizardParams, paramBoolean3);
      getNavBar(paramActivity).mBackButton.setEnabled(paramBoolean1);
      str = getIllustrationImageUrl(paramActivity, paramInt);
      localSetupWizardIllustration = (SetupWizardIllustration)paramActivity.findViewById(2131756117);
      if (localSetupWizardIllustration.mImageView != null)
      {
        if ((!localSetupWizardIllustration.mCollapsable) || (!paramBoolean2)) {
          break label80;
        }
        localSetupWizardIllustration.mImageView.setVisibility(8);
      }
    }
    return;
    label80:
    localSetupWizardIllustration.mImageView.setVisibility(0);
    localSetupWizardIllustration.mImageView.setImage(str, true, FinskyApp.get().mBitmapLoader);
    localSetupWizardIllustration.mImageView.setOnLoadedListener(new SetupWizardIllustration.1(localSetupWizardIllustration));
  }
  
  @TargetApi(11)
  public static void configureBasicUiWithoutNavBarOrImage(Activity paramActivity, SetupWizardParams paramSetupWizardParams, boolean paramBoolean)
  {
    View localView = paramActivity.getWindow().getDecorView();
    int i = localView.getSystemUiVisibility();
    if ((!shouldUseMaterialTheme()) && (paramSetupWizardParams.mOnInitialSetup))
    {
      i |= 0x2370000;
      paramBoolean = false;
    }
    if (paramBoolean) {}
    for (int j = i & 0xFFBFFFFF;; j = i | 0x400000)
    {
      localView.setSystemUiVisibility(j);
      return;
    }
  }
  
  public static String getIllustrationImageUrl(Context paramContext, int paramInt)
  {
    boolean bool = paramContext.getResources().getBoolean(2131427344);
    if (paramInt == 0)
    {
      if (bool) {
        return ILLUSTRATION_URL_PAYMENT_WIDE;
      }
      return ILLUSTRATION_URL_PAYMENT;
    }
    if (paramInt == 1)
    {
      if (bool) {
        return ILLUSTRATION_URL_RESTORE_WIDE;
      }
      return ILLUSTRATION_URL_RESTORE;
    }
    throw new IllegalStateException("Unknown context: " + paramContext);
  }
  
  @TargetApi(11)
  private static SetupWizardNavBar getNavBar(Activity paramActivity)
  {
    return (SetupWizardNavBar)paramActivity.getFragmentManager().findFragmentById(2131756116);
  }
  
  public static SetupWizardNavBar getNavBarIfPossible(Activity paramActivity)
  {
    if (shouldUseMaterialTheme()) {
      return getNavBar(paramActivity);
    }
    return null;
  }
  
  public static boolean shouldUseMaterialTheme()
  {
    return Build.VERSION.SDK_INT >= 21;
  }
  
  private static final class FullscreenAdjustResizeWorkaround
  {
    View mActivityView;
    FrameLayout.LayoutParams mFrameLayoutParams;
    int mPreviousHeight;
    int mStatusBarHeight = 0;
    
    FullscreenAdjustResizeWorkaround(Activity paramActivity)
    {
      int i = paramActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (i > 0) {
        this.mStatusBarHeight = paramActivity.getResources().getDimensionPixelSize(i);
      }
      this.mActivityView = ((FrameLayout)paramActivity.findViewById(16908290)).getChildAt(0);
      this.mActivityView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public final void onGlobalLayout()
        {
          SetupWizardUtils.FullscreenAdjustResizeWorkaround localFullscreenAdjustResizeWorkaround = SetupWizardUtils.FullscreenAdjustResizeWorkaround.this;
          Rect localRect = new Rect();
          localFullscreenAdjustResizeWorkaround.mActivityView.getWindowVisibleDisplayFrame(localRect);
          int i = localRect.bottom - localRect.top + localFullscreenAdjustResizeWorkaround.mStatusBarHeight;
          int j;
          int k;
          if (i != localFullscreenAdjustResizeWorkaround.mPreviousHeight)
          {
            j = localFullscreenAdjustResizeWorkaround.mActivityView.getRootView().getHeight();
            k = j - i;
            if (k <= j / 4) {
              break label96;
            }
          }
          label96:
          for (localFullscreenAdjustResizeWorkaround.mFrameLayoutParams.height = (j - k);; localFullscreenAdjustResizeWorkaround.mFrameLayoutParams.height = j)
          {
            localFullscreenAdjustResizeWorkaround.mActivityView.requestLayout();
            localFullscreenAdjustResizeWorkaround.mPreviousHeight = i;
            return;
          }
        }
      });
      this.mFrameLayoutParams = ((FrameLayout.LayoutParams)this.mActivityView.getLayoutParams());
    }
  }
  
  public static class SetupWizardParams
    implements Parcelable
  {
    public static final Parcelable.Creator<SetupWizardParams> CREATOR = new Parcelable.Creator() {};
    public final boolean mIsLightTheme;
    final boolean mOnInitialSetup;
    final boolean mUseImmersiveMode;
    
    public SetupWizardParams(Intent paramIntent)
    {
      this.mOnInitialSetup = paramIntent.getBooleanExtra("on_initial_setup", bool);
      this.mUseImmersiveMode = paramIntent.getBooleanExtra("useImmersiveMode", false);
      if (SetupWizardUtils.shouldUseMaterialTheme())
      {
        String str = paramIntent.getStringExtra("theme");
        if ((!TextUtils.isEmpty(str)) && (!str.equalsIgnoreCase("material_light"))) {}
      }
      for (;;)
      {
        this.mIsLightTheme = bool;
        return;
        bool = false;
      }
    }
    
    public SetupWizardParams(Parcel paramParcel)
    {
      int j;
      int k;
      if (paramParcel.readByte() == i)
      {
        j = i;
        this.mOnInitialSetup = j;
        if (paramParcel.readByte() != i) {
          break label57;
        }
        k = i;
        label32:
        this.mUseImmersiveMode = k;
        if (paramParcel.readByte() != i) {
          break label63;
        }
      }
      for (;;)
      {
        this.mIsLightTheme = i;
        return;
        j = 0;
        break;
        label57:
        k = 0;
        break label32;
        label63:
        i = 0;
      }
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      int i = 1;
      int j;
      int k;
      if (this.mOnInitialSetup)
      {
        j = i;
        paramParcel.writeByte((byte)j);
        if (!this.mUseImmersiveMode) {
          break label56;
        }
        k = i;
        label29:
        paramParcel.writeByte((byte)k);
        if (!this.mIsLightTheme) {
          break label62;
        }
      }
      for (;;)
      {
        paramParcel.writeByte((byte)i);
        return;
        j = 0;
        break;
        label56:
        k = 0;
        break label29;
        label62:
        i = 0;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.SetupWizardUtils
 * JD-Core Version:    0.7.0.1
 */