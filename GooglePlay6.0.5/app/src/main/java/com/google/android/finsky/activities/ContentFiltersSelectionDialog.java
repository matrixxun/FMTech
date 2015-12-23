package com.google.android.finsky.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public final class ContentFiltersSelectionDialog
  extends SimpleAlertDialog
  implements View.OnClickListener
{
  public final void onClick(View paramView)
  {
    doPositiveClick();
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Dialog localDialog = super.onCreateDialog(paramBundle);
    ((ContentFiltersDialogView)this.mConfigurableView).setOnPositiveClickListener(this);
    return localDialog;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ContentFiltersSelectionDialog
 * JD-Core Version:    0.7.0.1
 */