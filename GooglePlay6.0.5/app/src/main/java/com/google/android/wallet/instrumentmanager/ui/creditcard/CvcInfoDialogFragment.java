package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;
import com.google.android.wallet.ui.common.BaseWalletUiComponentDialogFragment;

public final class CvcInfoDialogFragment
  extends BaseWalletUiComponentDialogFragment
{
  public static CvcInfoDialogFragment newInstance(int paramInt)
  {
    CvcInfoDialogFragment localCvcInfoDialogFragment = new CvcInfoDialogFragment();
    localCvcInfoDialogFragment.setArguments(createArgs(paramInt));
    return localCvcInfoDialogFragment;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    View localView = getThemedLayoutInflater().inflate(R.layout.view_cvc_information, null, false);
    return new AlertDialogBuilderCompat(getThemedContext()).setView(localView).setPositiveButton(R.string.wallet_uic_close, null).create();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CvcInfoDialogFragment
 * JD-Core Version:    0.7.0.1
 */