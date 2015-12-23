package com.google.android.finsky.billing;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class DownloadNetworkDialogFragment
  extends DialogFragment
{
  private PreAppDownloadWarnings.Listener getListener()
  {
    if (this.mTarget != null) {
      return (PreAppDownloadWarnings.Listener)this.mTarget;
    }
    return (PreAppDownloadWarnings.Listener)getActivity();
  }
  
  public static DownloadNetworkDialogFragment newInstance(Fragment paramFragment, Bundle paramBundle)
  {
    DownloadNetworkDialogFragment localDownloadNetworkDialogFragment = new DownloadNetworkDialogFragment();
    if (paramFragment != null)
    {
      if ((paramFragment instanceof PreAppDownloadWarnings.Listener)) {
        localDownloadNetworkDialogFragment.setTargetFragment(paramFragment, -1);
      }
    }
    else
    {
      localDownloadNetworkDialogFragment.setArguments(paramBundle);
      return localDownloadNetworkDialogFragment;
    }
    throw new IllegalArgumentException("targetFragment must implement PreAppDownloadWarnings.Listener");
  }
  
  public static DownloadNetworkDialogFragment newInstance$41b8249f(long paramLong)
  {
    Bundle localBundle = new Bundle();
    localBundle.putLong("installationSize", paramLong);
    return newInstance(null, localBundle);
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    getListener().onDownloadCancel();
    super.onCancel(paramDialogInterface);
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    long l = this.mArguments.getLong("installationSize");
    FragmentActivity localFragmentActivity = getActivity();
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localFragmentActivity, (byte)0);
    localAlertDialogBuilderCompat.setTitle(2131363046);
    View localView = LayoutInflater.from(localFragmentActivity).inflate(2130968721, null);
    TextView localTextView = (TextView)localView.findViewById(2131755449);
    final RadioButton localRadioButton = (RadioButton)localView.findViewById(2131755450);
    localRadioButton.setChecked(true);
    if (l > 0L)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Formatter.formatFileSize(localFragmentActivity, l);
      localTextView.setText(localFragmentActivity.getString(2131363045, arrayOfObject));
      localTextView.setVisibility(0);
    }
    localAlertDialogBuilderCompat.setView(localView).setPositiveButton(2131363007, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        boolean bool;
        if (!localRadioButton.isChecked())
        {
          bool = true;
          if (!bool) {
            break label50;
          }
        }
        label50:
        for (int i = 3;; i = 2)
        {
          FinskyPreferences.downloadNetworkPreference.put(Integer.valueOf(i));
          DownloadNetworkDialogFragment.this.getListener().onDownloadOk(bool, true);
          return;
          bool = false;
          break;
        }
      }
    }).setNegativeButton(2131363008, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (!localRadioButton.isChecked()) {}
        for (boolean bool = true;; bool = false)
        {
          DownloadNetworkDialogFragment.this.getListener().onDownloadOk(bool, false);
          return;
        }
      }
    });
    return localAlertDialogBuilderCompat.create();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.DownloadNetworkDialogFragment
 * JD-Core Version:    0.7.0.1
 */