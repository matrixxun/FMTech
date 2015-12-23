package com.google.android.finsky.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.PreregistrationDialogView;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;

public final class PreregistrationDialog
  extends SimpleAlertDialog
{
  public static void show(Document paramDocument, FragmentManager paramFragmentManager)
  {
    if (paramFragmentManager.findFragmentByTag("preregistration_dialog") != null) {
      return;
    }
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("PreregistrationDialogView.document", paramDocument);
    PreregistrationDialog localPreregistrationDialog = new PreregistrationDialog();
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setLayoutId(2130969022).setViewConfiguration(localBundle).setPositiveId(2131362594).setNegativeId(2131362753).setCancelDoesNegativeAction$5e554932().setEventLog(321, null, -1, 202, null);
    localBuilder.configureDialog(localPreregistrationDialog);
    localPreregistrationDialog.show(paramFragmentManager, "preregistration_dialog");
  }
  
  protected final void onNegativeClick()
  {
    super.onNegativeClick();
    PreregistrationDialogView localPreregistrationDialogView = (PreregistrationDialogView)getConfigurableView();
    if (localPreregistrationDialogView == null)
    {
      FinskyLog.wtf("PreregistrationDialog unable to retrieve its view", new Object[0]);
      return;
    }
    Document localDocument = localPreregistrationDialogView.getDocument();
    if (localDocument == null)
    {
      FinskyLog.wtf("PreregistrationDialog unable to retrieve its document from its view", new Object[0]);
      return;
    }
    IntentUtils.shareDocument(getActivity(), localDocument);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.PreregistrationDialog
 * JD-Core Version:    0.7.0.1
 */