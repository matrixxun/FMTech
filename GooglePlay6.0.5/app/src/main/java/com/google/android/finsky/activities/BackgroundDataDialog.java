package com.google.android.finsky.activities;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;

public final class BackgroundDataDialog
  extends SimpleAlertDialog
{
  private BackgroundDataSettingListener mListener;
  
  public static void dismissExisting(FragmentManager paramFragmentManager)
  {
    Fragment localFragment = paramFragmentManager.findFragmentByTag("bg_data_dialog");
    if (localFragment != null) {
      ((DialogFragment)localFragment).dismissInternal(false);
    }
  }
  
  public static void show(FragmentManager paramFragmentManager, Activity paramActivity)
  {
    if (paramFragmentManager.findFragmentByTag("bg_data_dialog") != null) {
      return;
    }
    BackgroundDataDialog localBackgroundDataDialog = new BackgroundDataDialog();
    if ((paramActivity instanceof BackgroundDataSettingListener)) {
      localBackgroundDataDialog.mListener = ((BackgroundDataSettingListener)paramActivity);
    }
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder().setTitleId(2131361899).setNegativeId(2131361898);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = G.helpCenterBackgroundDataUrl.get();
    localBuilder.setMessageHtml(paramActivity.getString(2131361897, arrayOfObject)).setCanceledOnTouchOutside(false).setEventLog(320, null, -1, -1, FinskyApp.get().getCurrentAccount()).configureDialog(localBackgroundDataDialog);
    localBackgroundDataDialog.show(paramFragmentManager, "bg_data_dialog");
  }
  
  protected final void onNegativeClick()
  {
    super.onNegativeClick();
    if (this.mListener != null) {
      this.mListener.onBackgroundDataNotEnabled();
    }
  }
  
  public static abstract interface BackgroundDataSettingListener
  {
    public abstract void onBackgroundDataNotEnabled();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.BackgroundDataDialog
 * JD-Core Version:    0.7.0.1
 */