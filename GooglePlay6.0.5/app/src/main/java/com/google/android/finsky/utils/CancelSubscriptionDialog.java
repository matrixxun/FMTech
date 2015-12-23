package com.google.android.finsky.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.library.RevokeListenerWrapper;
import com.google.android.finsky.protos.DocV2;

public final class CancelSubscriptionDialog
  extends SimpleAlertDialog
{
  public static void show(FragmentManager paramFragmentManager, Document paramDocument, LibrarySubscriptionEntry paramLibrarySubscriptionEntry)
  {
    if (paramFragmentManager.findFragmentByTag("CancelSubscriptionDialog.cancel_subscripiton_dialog") != null) {
      return;
    }
    if (System.currentTimeMillis() < paramLibrarySubscriptionEntry.trialUntilTimestampMs) {}
    for (int i = 2131361967;; i = 2131361966)
    {
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      FinskyApp localFinskyApp = FinskyApp.get();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDocument.mDocument.title;
      localBuilder.setMessage(localFinskyApp.getString(i, arrayOfObject)).setPositiveId(2131362937).setNegativeId(2131362370).setEventLog(304, paramDocument.mDocument.serverLogsCookie, 243, 244, null);
      CancelSubscriptionDialog localCancelSubscriptionDialog = new CancelSubscriptionDialog();
      localBuilder.configureDialog(localCancelSubscriptionDialog);
      Bundle localBundle = localCancelSubscriptionDialog.mArguments;
      localBundle.putString("authAccount", paramLibrarySubscriptionEntry.mAccountName);
      localBundle.putParcelable("CancelSubscriptionDialog.doc", paramDocument);
      localCancelSubscriptionDialog.setArguments(localBundle);
      localCancelSubscriptionDialog.show(paramFragmentManager, "CancelSubscriptionDialog.cancel_subscripiton_dialog");
      return;
    }
  }
  
  protected final void onPositiveClick()
  {
    super.onPositiveClick();
    Bundle localBundle = this.mArguments;
    String str = localBundle.getString("authAccount");
    final Document localDocument = (Document)localBundle.getParcelable("CancelSubscriptionDialog.doc");
    final FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger(str);
    localFinskyEventLog.sendBackgroundEventToSinks(new BackgroundEventBuilder(850).setDocument(localDocument.mDocument.docid).setOfferType(1).event);
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(str);
    localDfeApi.revoke$b40b8a6(localDocument.mDocument.docid, new RevokeListenerWrapper(FinskyApp.get().mLibraryReplicators, localDfeApi.getAccount(), new Runnable()
    {
      public final void run()
      {
        localFinskyEventLog.sendBackgroundEventToSinks(new BackgroundEventBuilder(851).setDocument(localDocument.mDocument.docid).setOfferType(1).event);
        MyAccountHelper.sLastMutationTimeMs = System.currentTimeMillis();
        Toast.makeText(FinskyApp.get(), 2131361921, 0).show();
      }
    }), new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        localFinskyEventLog.sendBackgroundEventToSinks(new BackgroundEventBuilder(851).setDocument(localDocument.mDocument.docid).setOfferType(1).setErrorCode(1).setExceptionType(paramAnonymousVolleyError).event);
        Toast.makeText(FinskyApp.get(), 2131361920, 0).show();
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.CancelSubscriptionDialog
 * JD-Core Version:    0.7.0.1
 */