package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import com.google.android.finsky.FinskyApp;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class CarrierBillingErrorDialog
  extends DialogFragment
  implements DialogInterface.OnClickListener
{
  public CarrierBillingErrorListener mListener;
  
  public static CarrierBillingErrorDialog newInstance(String paramString, boolean paramBoolean)
  {
    CarrierBillingErrorDialog localCarrierBillingErrorDialog = new CarrierBillingErrorDialog();
    localCarrierBillingErrorDialog.setCancelable(false);
    Bundle localBundle = new Bundle();
    localBundle.putString("error_message", paramString);
    localBundle.putBoolean("fatal_error", paramBoolean);
    localCarrierBillingErrorDialog.setArguments(localBundle);
    return localCarrierBillingErrorDialog;
  }
  
  public final void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (this.mListener != null) {
      this.mListener.onErrorDismiss(this.mArguments.getBoolean("fatal_error"));
    }
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(getActivity());
    localAlertDialogBuilderCompat.setTitle(FinskyApp.get().getString(2131362123));
    localAlertDialogBuilderCompat.setMessage(this.mArguments.getString("error_message"));
    localAlertDialogBuilderCompat.setPositiveButton(17039370, this).setCancelable(false);
    return localAlertDialogBuilderCompat.create();
  }
  
  public static abstract interface CarrierBillingErrorListener
  {
    public abstract void onErrorDismiss(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingErrorDialog
 * JD-Core Version:    0.7.0.1
 */