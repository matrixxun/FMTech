package com.google.android.finsky.billing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import com.google.android.finsky.activities.SettingsActivity;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class DownloadNetworkSettingsDialogFragment
  extends DialogFragment
{
  private PreAppDownloadWarnings.Listener getListener()
  {
    if (this.mTarget != null) {
      return (PreAppDownloadWarnings.Listener)this.mTarget;
    }
    return (PreAppDownloadWarnings.Listener)getActivity();
  }
  
  public static DownloadNetworkSettingsDialogFragment newInstance$1b0e3152()
  {
    return new DownloadNetworkSettingsDialogFragment();
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    getListener().onDoAcquisition();
    super.onCancel(paramDialogInterface);
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localFragmentActivity, (byte)0);
    localAlertDialogBuilderCompat.setView(LayoutInflater.from(localFragmentActivity).inflate(2130968720, null)).setPositiveButton(2131363009, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        DownloadNetworkSettingsDialogFragment.this.getListener().onDoAcquisition();
      }
    }).setNegativeButton(2131362722, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        DownloadNetworkSettingsDialogFragment.this.getListener().onDoAcquisition();
        Intent localIntent = new Intent(DownloadNetworkSettingsDialogFragment.this.getContext(), SettingsActivity.class);
        localIntent.putExtra("setting-key-to-scroll", "download-mode");
        DownloadNetworkSettingsDialogFragment.this.startActivity(localIntent);
      }
    });
    return localAlertDialogBuilderCompat.create();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.DownloadNetworkSettingsDialogFragment
 * JD-Core Version:    0.7.0.1
 */