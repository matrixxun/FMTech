package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.config.G;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;
import com.google.android.wallet.ui.common.FifeNetworkImageView;

public final class NfcInfoDialogFragment
  extends NfcDialogFragment
  implements DialogInterface.OnClickListener
{
  private View mContentView;
  FifeNetworkImageView mInstructionImage;
  TextView mMessage;
  private View mProgressView;
  private boolean mShowingSpinner;
  
  public static NfcInfoDialogFragment newInstance(int paramInt, boolean paramBoolean)
  {
    NfcInfoDialogFragment localNfcInfoDialogFragment = new NfcInfoDialogFragment();
    Bundle localBundle = createArgs(paramInt);
    localBundle.putBoolean("nfcEnabled", paramBoolean);
    localNfcInfoDialogFragment.setArguments(localBundle);
    return localNfcInfoDialogFragment;
  }
  
  public final void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1) {
      if (Build.VERSION.SDK_INT < 16) {
        break label29;
      }
    }
    label29:
    for (String str = "android.settings.NFC_SETTINGS";; str = "android.settings.WIRELESS_SETTINGS")
    {
      startActivity(new Intent(str));
      return;
    }
  }
  
  @TargetApi(10)
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    View localView = getThemedLayoutInflater().inflate(R.layout.view_nfc_instruction, null);
    this.mShowingSpinner = false;
    this.mMessage = ((TextView)localView.findViewById(R.id.nfc_popup_message));
    this.mInstructionImage = ((FifeNetworkImageView)localView.findViewById(R.id.nfc_instruction_image));
    this.mContentView = localView.findViewById(R.id.nfc_instruction_layout);
    this.mProgressView = localView.findViewById(R.id.nfc_instruction_spinner);
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(getThemedContext()).setView(localView);
    if (this.mArguments.getBoolean("nfcEnabled"))
    {
      localAlertDialogBuilderCompat.setTitle(R.string.wallet_im_nfc_popup_title).setNegativeButton(R.string.wallet_uic_close, null);
      this.mMessage.setText(R.string.wallet_im_nfc_popup_enabled_information);
      String str = (String)G.ccNfcInstructionImageFifeUrl.get();
      if (!TextUtils.isEmpty(str))
      {
        this.mInstructionImage.setFifeImageUrl(str, PaymentUtils.getImageLoader(getContext().getApplicationContext()), ((Boolean)G.images.useWebPForFife.get()).booleanValue());
        this.mInstructionImage.setFadeIn(true);
        this.mInstructionImage.setVisibility(0);
      }
    }
    for (;;)
    {
      return localAlertDialogBuilderCompat.create();
      localAlertDialogBuilderCompat.setTitle(R.string.wallet_im_nfc_enable_title).setPositiveButton(R.string.wallet_im_nfc_enable_button, this);
      this.mMessage.setText(R.string.wallet_im_nfc_popup_disabled_information);
      this.mInstructionImage.setVisibility(8);
    }
  }
  
  public final void showSpinner()
  {
    this.mShowingSpinner = true;
    this.mContentView.setVisibility(4);
    this.mProgressView.setVisibility(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.NfcInfoDialogFragment
 * JD-Core Version:    0.7.0.1
 */