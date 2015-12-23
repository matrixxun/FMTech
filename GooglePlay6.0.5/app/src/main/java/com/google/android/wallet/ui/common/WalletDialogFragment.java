package com.google.android.wallet.ui.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;

public final class WalletDialogFragment
  extends BaseWalletUiComponentDialogFragment
  implements DialogInterface.OnClickListener
{
  private void sendDismissedCallback(int paramInt)
  {
    OnWalletDialogDismissedListener localOnWalletDialogDismissedListener;
    if ((this.mTarget instanceof OnWalletDialogDismissedListener)) {
      localOnWalletDialogDismissedListener = (OnWalletDialogDismissedListener)this.mTarget;
    }
    for (;;)
    {
      if (localOnWalletDialogDismissedListener != null)
      {
        int i = this.mArguments.getInt("requestCode");
        this.mArguments.getParcelable("tag");
        localOnWalletDialogDismissedListener.onWalletDialogDismissed$71cf5c62(paramInt, i);
      }
      return;
      boolean bool = getActivity() instanceof OnWalletDialogDismissedListener;
      localOnWalletDialogDismissedListener = null;
      if (bool) {
        localOnWalletDialogDismissedListener = (OnWalletDialogDismissedListener)getActivity();
      }
    }
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    sendDismissedCallback(-2);
  }
  
  public final void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    sendDismissedCallback(paramInt);
    dismissInternal(false);
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(getThemedContext());
    localAlertDialogBuilderCompat.setTitle(localBundle.getString("title"));
    View localView = getThemedLayoutInflater().inflate(R.layout.view_wallet_dialog, null);
    TextView localTextView1 = (TextView)localView.findViewById(R.id.message);
    String str1 = localBundle.getString("message");
    TextView localTextView2;
    String str2;
    if (localBundle.getBoolean("linkifyMessage"))
    {
      localTextView1.setText(WalletUiUtils.linkifyHtml$24df1acc(str1));
      localTextView1.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView2 = (TextView)localView.findViewById(R.id.details);
      str2 = localBundle.getString("details");
      if (!TextUtils.isEmpty(str2)) {
        break label183;
      }
      localTextView2.setVisibility(8);
    }
    for (;;)
    {
      localAlertDialogBuilderCompat.setView(localView);
      localAlertDialogBuilderCompat.setPositiveButton(localBundle.getString("positiveButtonText"), this);
      String str3 = localBundle.getString("negativeButtonText");
      if (!TextUtils.isEmpty(str3)) {
        localAlertDialogBuilderCompat.setNegativeButton(str3, this);
      }
      return localAlertDialogBuilderCompat.create();
      localTextView1.setText(str1);
      break;
      label183:
      localTextView2.setText(str2);
    }
  }
  
  public static final class Builder
  {
    public String mDetails;
    private boolean mLinkifyMessage = true;
    public String mMessage;
    private String mNegativeButtonText;
    public String mPositiveButtonText;
    public int mRequestCode;
    private Parcelable mTag;
    public int mThemeResourceId;
    public String mTitle;
    
    public final WalletDialogFragment build()
    {
      return WalletDialogFragment.access$000(this.mRequestCode, this.mTitle, this.mMessage, this.mDetails, this.mPositiveButtonText, this.mNegativeButtonText, this.mLinkifyMessage, this.mTag, this.mThemeResourceId);
    }
  }
  
  public static abstract interface OnWalletDialogDismissedListener
  {
    public abstract void onWalletDialogDismissed$71cf5c62(int paramInt1, int paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.WalletDialogFragment
 * JD-Core Version:    0.7.0.1
 */