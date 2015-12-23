package com.google.android.vending.verifier;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.activities.SimpleAlertDialog;

public final class PackageWarningSimpleAlertDialog
  extends SimpleAlertDialog
  implements View.OnClickListener
{
  private PackageWarningDialogView packageWarningDialogView;
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    if (this.packageWarningDialogView.getAction() == 2)
    {
      doNegativeClick();
      return;
    }
    doPositiveClick();
  }
  
  public final void onClick(View paramView)
  {
    doNegativeClick();
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Dialog localDialog = super.onCreateDialog(paramBundle);
    if (localDialog != null)
    {
      this.packageWarningDialogView = ((PackageWarningDialogView)getConfigurableView());
      this.packageWarningDialogView.setOnContinueAnywayClickListener(this);
      localDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public final boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if ((paramAnonymousInt == 4) && (paramAnonymousKeyEvent.getAction() == 1)) {
            PackageWarningSimpleAlertDialog.this.packageWarningDialogView.mResult.putBoolean("pressed_back_button", true);
          }
          return false;
        }
      });
    }
    return localDialog;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageWarningSimpleAlertDialog
 * JD-Core Version:    0.7.0.1
 */