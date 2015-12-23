package com.google.android.finsky.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class FlagItemUserMessageDialog
  extends DialogFragment
{
  private String mMessage;
  
  public static FlagItemUserMessageDialog newInstance(int paramInt)
  {
    FlagItemUserMessageDialog localFlagItemUserMessageDialog = new FlagItemUserMessageDialog();
    Bundle localBundle = new Bundle();
    localBundle.putInt("prompt_string_res_id", paramInt);
    localFlagItemUserMessageDialog.setArguments(localBundle);
    return localFlagItemUserMessageDialog;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Bundle localBundle1 = this.mArguments;
    int i = localBundle1.getInt("prompt_string_res_id");
    if (paramBundle != null) {}
    for (Bundle localBundle2 = paramBundle;; localBundle2 = localBundle1)
    {
      this.mMessage = localBundle2.getString("previous_message");
      View localView = LayoutInflater.from(getActivity()).inflate(2130968754, null, false);
      final TextView localTextView = (TextView)localView.findViewById(2131755519);
      localTextView.setText(this.mMessage);
      AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(localFragmentActivity);
      localAlertDialogBuilderCompat.setTitle(i);
      localAlertDialogBuilderCompat.setView(localView);
      localAlertDialogBuilderCompat.setCancelable(true);
      localAlertDialogBuilderCompat.setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FlagItemUserMessageDialog.Listener localListener = FlagItemUserMessageDialog.access$000(FlagItemUserMessageDialog.this);
          if (localListener != null) {
            localListener.onPositiveClick(localTextView.getText().toString());
          }
        }
      });
      localAlertDialogBuilderCompat.setNegativeButton(17039360, null);
      return localAlertDialogBuilderCompat.create();
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    if (!TextUtils.isEmpty(this.mMessage)) {
      paramBundle.putString("previous_message", this.mMessage);
    }
  }
  
  public final void onStart()
  {
    super.onStart();
    AlertDialog localAlertDialog = (AlertDialog)this.mDialog;
    final Button localButton = localAlertDialog.getButton$717182de();
    if (!TextUtils.isEmpty(this.mMessage)) {}
    for (boolean bool = true;; bool = false)
    {
      localButton.setEnabled(bool);
      ((TextView)localAlertDialog.findViewById(2131755519)).addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
          FlagItemUserMessageDialog localFlagItemUserMessageDialog = FlagItemUserMessageDialog.this;
          String str;
          Button localButton;
          if (paramAnonymousEditable == null)
          {
            str = null;
            FlagItemUserMessageDialog.access$102(localFlagItemUserMessageDialog, str);
            localButton = localButton;
            if (TextUtils.isEmpty(FlagItemUserMessageDialog.this.mMessage)) {
              break label55;
            }
          }
          label55:
          for (boolean bool = true;; bool = false)
          {
            localButton.setEnabled(bool);
            return;
            str = paramAnonymousEditable.toString();
            break;
          }
        }
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      });
      return;
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onPositiveClick(String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FlagItemUserMessageDialog
 * JD-Core Version:    0.7.0.1
 */