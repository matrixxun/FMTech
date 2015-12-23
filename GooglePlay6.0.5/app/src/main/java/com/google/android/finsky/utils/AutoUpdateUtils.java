package com.google.android.finsky.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class AutoUpdateUtils
{
  public static boolean isAutoUpdateEnabled(String paramString)
  {
    AppStates.AppState localAppState = FinskyApp.get().mAppStates.getApp(paramString);
    boolean bool = ((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue();
    if (localAppState.installerData != null)
    {
      if ((bool) && (localAppState.installerData.autoUpdate == 1)) {
        bool = true;
      }
    }
    else {
      return bool;
    }
    return false;
  }
  
  public static final class AutoUpdateDialog
    extends DialogFragment
    implements DialogInterface.OnClickListener
  {
    public final void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      }
      for (;;)
      {
        FragmentActivity localFragmentActivity = getActivity();
        if ((localFragmentActivity != null) && ((localFragmentActivity instanceof MainActivity))) {
          ((MainActivity)localFragmentActivity).updateActionBarTitle(null);
        }
        return;
        dismissInternal(false);
        FinskyPreferences.autoUpdateEnabled.put(Boolean.valueOf(true));
        FinskyPreferences.autoUpdateWifiOnly.put(Boolean.valueOf(true));
        continue;
        dismissInternal(false);
      }
    }
    
    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(getActivity());
      localAlertDialogBuilderCompat.setMessage(2131361877);
      localAlertDialogBuilderCompat.setPositiveButton(2131362937, this);
      localAlertDialogBuilderCompat.setNegativeButton(2131362370, this);
      return localAlertDialogBuilderCompat.create();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.AutoUpdateUtils
 * JD-Core Version:    0.7.0.1
 */