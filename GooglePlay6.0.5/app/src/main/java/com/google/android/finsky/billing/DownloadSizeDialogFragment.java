package com.google.android.finsky.billing;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertController.AlertParams;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;
import java.util.List;

public final class DownloadSizeDialogFragment
  extends DialogFragment
{
  private PreAppDownloadWarnings.Listener getListener()
  {
    if (this.mTarget != null) {
      return (PreAppDownloadWarnings.Listener)this.mTarget;
    }
    return (PreAppDownloadWarnings.Listener)getActivity();
  }
  
  public static Bundle makeArguments$409d583d(boolean paramBoolean1, boolean paramBoolean2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("setWifiOnly", true);
    localBundle.putBoolean("showWifiOnly", paramBoolean1);
    localBundle.putBoolean("onMobileNetwork", paramBoolean2);
    return localBundle;
  }
  
  public static DownloadSizeDialogFragment newInstance(Fragment paramFragment, Bundle paramBundle)
  {
    DownloadSizeDialogFragment localDownloadSizeDialogFragment = new DownloadSizeDialogFragment();
    if (paramFragment != null)
    {
      if ((paramFragment instanceof PreAppDownloadWarnings.Listener)) {
        localDownloadSizeDialogFragment.setTargetFragment(paramFragment, -1);
      }
    }
    else
    {
      localDownloadSizeDialogFragment.setArguments(paramBundle);
      return localDownloadSizeDialogFragment;
    }
    throw new IllegalArgumentException("targetFragment must implement PreAppDownloadWarnings.Listener");
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    getListener().onDownloadCancel();
    super.onCancel(paramDialogInterface);
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    final boolean bool1 = localBundle.getBoolean("showWifiOnly");
    final boolean bool2 = localBundle.getBoolean("setWifiOnly");
    boolean bool3 = localBundle.getBoolean("onMobileNetwork");
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localFragmentActivity, (byte)0);
    localAlertDialogBuilderCompat.setTitle(2131362828);
    View localView = LayoutInflater.from(localFragmentActivity).inflate(2130968722, null);
    TextView localTextView = (TextView)localView.findViewById(2131755452);
    int i;
    DialogInterface.OnClickListener local3;
    if (bool1)
    {
      i = 2131362829;
      localTextView.setText(i);
      final CheckBox localCheckBox = (CheckBox)localView.findViewById(2131755453);
      if (bool1)
      {
        localCheckBox.setVisibility(0);
        if (paramBundle == null) {
          localCheckBox.setChecked(bool2);
        }
      }
      localAlertDialogBuilderCompat.setView(localView);
      localAlertDialogBuilderCompat.setPositiveButton(2131362827, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if (bool1) {}
          for (boolean bool = localCheckBox.isChecked();; bool = bool2)
          {
            DownloadSizeDialogFragment.this.getListener().onDownloadOk(bool, false);
            return;
          }
        }
      });
      localAlertDialogBuilderCompat.setNegativeButton(2131361915, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          DownloadSizeDialogFragment.this.getListener().onDownloadCancel();
        }
      });
      if (FinskyApp.get().mInstallPolicies.isMobileNetwork())
      {
        Intent localIntent = new Intent("android.settings.WIFI_SETTINGS");
        if (localFragmentActivity.getPackageManager().queryIntentActivities(localIntent, 65536).size() > 0)
        {
          local3 = new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              DownloadSizeDialogFragment.this.getListener().onSetupWifi();
            }
          };
          if (localAlertDialogBuilderCompat.mPlatformBuilder == null) {
            break label271;
          }
          localAlertDialogBuilderCompat.mPlatformBuilder.setNeutralButton(2131362734, local3);
        }
      }
    }
    for (;;)
    {
      return localAlertDialogBuilderCompat.create();
      if (bool3)
      {
        i = 2131362825;
        break;
      }
      i = 2131362826;
      break;
      label271:
      android.support.v7.app.AlertDialog.Builder localBuilder = localAlertDialogBuilderCompat.mSupportBuilder;
      localBuilder.P.mNeutralButtonText = localBuilder.P.mContext.getText(2131362734);
      localBuilder.P.mNeutralButtonListener = local3;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.DownloadSizeDialogFragment
 * JD-Core Version:    0.7.0.1
 */