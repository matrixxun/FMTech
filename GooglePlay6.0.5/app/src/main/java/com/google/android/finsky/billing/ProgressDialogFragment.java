package com.google.android.finsky.billing;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public final class ProgressDialogFragment
  extends DialogFragment
{
  public static ProgressDialogFragment newInstance(int paramInt)
  {
    ProgressDialogFragment localProgressDialogFragment = new ProgressDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("message_id", paramInt);
    localProgressDialogFragment.setArguments(localBundle);
    return localProgressDialogFragment;
  }
  
  public static ProgressDialogFragment newInstance$1c1250dc()
  {
    ProgressDialogFragment localProgressDialogFragment = new ProgressDialogFragment();
    localProgressDialogFragment.setArguments(new Bundle());
    return localProgressDialogFragment;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    setCancelable(false);
    ProgressDialog localProgressDialog = new ProgressDialog(getActivity());
    localProgressDialog.setProgressStyle(0);
    int i = this.mArguments.getInt("message_id", 2131362306);
    if (this.mArguments.containsKey("message")) {
      localProgressDialog.setMessage(this.mArguments.getString("message"));
    }
    for (;;)
    {
      localProgressDialog.setCancelable(false);
      localProgressDialog.setIndeterminate(true);
      return localProgressDialog;
      if (i != 0) {
        localProgressDialog.setMessage(getResources().getString(i));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.ProgressDialogFragment
 * JD-Core Version:    0.7.0.1
 */