package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.finsky.fragments.LoggingDialogFragment;

public final class VerifyAssociationDialogFragment
  extends LoggingDialogFragment
  implements DialogInterface.OnClickListener
{
  public VerifyAssociationListener mListener;
  
  public static VerifyAssociationDialogFragment newInstance(String paramString)
  {
    VerifyAssociationDialogFragment localVerifyAssociationDialogFragment = new VerifyAssociationDialogFragment();
    localVerifyAssociationDialogFragment.setCancelable(false);
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localVerifyAssociationDialogFragment.setArguments(localBundle);
    return localVerifyAssociationDialogFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 842;
  }
  
  public final void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (this.mListener != null) {
      this.mListener.onVerifyCancel();
    }
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    ProgressDialog localProgressDialog = new ProgressDialog(getActivity());
    localProgressDialog.setTitle(getResources().getString(2131362835));
    localProgressDialog.setMessage(getResources().getString(2131361865));
    localProgressDialog.setProgressStyle(0);
    localProgressDialog.setIndeterminate(true);
    localProgressDialog.setCancelable(false);
    localProgressDialog.setCanceledOnTouchOutside(false);
    localProgressDialog.setButton(-1, getResources().getString(2131362093), this);
    return localProgressDialog;
  }
  
  public static abstract interface VerifyAssociationListener
  {
    public abstract void onVerifyCancel();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.VerifyAssociationDialogFragment
 * JD-Core Version:    0.7.0.1
 */