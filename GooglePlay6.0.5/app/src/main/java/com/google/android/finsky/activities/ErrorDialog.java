package com.google.android.finsky.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.utils.FinskyLog;

public final class ErrorDialog
  extends SimpleAlertDialog
{
  private boolean mIsRemoved = false;
  
  public ErrorDialog()
  {
    setCancelable(true);
  }
  
  private static void checkPreviousErrorState(FragmentManager paramFragmentManager)
  {
    paramFragmentManager.executePendingTransactions();
    ErrorDialog localErrorDialog = (ErrorDialog)paramFragmentManager.findFragmentByTag("error_dialog");
    FragmentTransaction localFragmentTransaction;
    if (localErrorDialog != null) {
      localFragmentTransaction = paramFragmentManager.beginTransaction();
    }
    try
    {
      localErrorDialog.mIsRemoved = true;
      localFragmentTransaction.remove(localErrorDialog).addToBackStack(null).commit();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      FinskyLog.w("Double remove of error dialog fragment: " + localErrorDialog, new Object[0]);
    }
  }
  
  public static ErrorDialog show(FragmentManager paramFragmentManager, String paramString1, String paramString2, boolean paramBoolean)
  {
    checkPreviousErrorState(paramFragmentManager);
    ErrorDialog localErrorDialog = ((Builder)((Builder)((Builder)((Builder)new Builder().setTitle(paramString1)).setMessageHtml(paramString2)).setPositiveId(17039370)).setEventLog(323, null, 2903, -1, null)).build();
    localErrorDialog.mArguments.putBoolean("go_back", paramBoolean);
    localErrorDialog.show(paramFragmentManager, "error_dialog");
    return localErrorDialog;
  }
  
  public final void onDismiss(DialogInterface paramDialogInterface)
  {
    if ((!this.mIsRemoved) && (getActivity() != null) && (this.mArguments.getBoolean("go_back")))
    {
      if (!(getActivity() instanceof PageFragmentHost)) {
        break label54;
      }
      ((PageFragmentHost)getActivity()).goBack();
    }
    for (;;)
    {
      super.onDismiss(paramDialogInterface);
      return;
      label54:
      FinskyLog.wtf("Dialog not hosted by PageFragmentHost. Cannot navigate back.", new Object[0]);
    }
  }
  
  public final void show(FragmentManager paramFragmentManager)
  {
    checkPreviousErrorState(paramFragmentManager);
    this.mArguments.putBoolean("go_back", false);
    super.show(paramFragmentManager, "error_dialog");
  }
  
  public static final class Builder
    extends SimpleAlertDialog.Builder<Builder>
  {
    public final ErrorDialog build()
    {
      ErrorDialog localErrorDialog = new ErrorDialog();
      configureDialog(localErrorDialog);
      return localErrorDialog;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ErrorDialog
 * JD-Core Version:    0.7.0.1
 */