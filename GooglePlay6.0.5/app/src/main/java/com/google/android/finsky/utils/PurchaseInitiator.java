package com.google.android.finsky.utils;

import android.accounts.Account;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Buy.BuyResponse;
import com.google.android.finsky.protos.Buy.PurchaseStatusResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;

public final class PurchaseInitiator
{
  public static void initiateDownload(Account paramAccount, Document paramDocument)
  {
    if (paramDocument.getAppDetails() == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDocument.mDocument.docid;
      FinskyLog.wtf("Document does not contain AppDetails, cannot download: %s", arrayOfObject);
    }
    FinskyApp.get().mInstaller.requestInstall(paramDocument.getAppDetails().packageName, paramDocument.getAppDetails().versionCode, paramAccount.name, paramDocument.mDocument.title, false, "single_install", 2, FinskyApp.get().mInstaller.extractInstallLocation(paramDocument));
  }
  
  public static void makeFreePurchase(Account paramAccount, final Document paramDocument, final int paramInt, final String paramString, final SuccessListener paramSuccessListener, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    FinskyApp.get().getEventLogger(paramAccount).logPurchaseBackgroundEvent$2c9b4795(300, paramDocument.mDocument.docid, paramInt, null, 0, null);
    FinskyApp.get().getDfeApi(paramAccount.name).makePurchase$668028e1(paramDocument, paramInt, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyApp.get().getEventLogger(this.val$account).logPurchaseBackgroundEvent$2c9b4795(301, paramDocument.mDocument.docid, paramInt, paramAnonymousVolleyError.getClass().getSimpleName(), 0, null);
        if (paramBoolean2)
        {
          String str1 = FinskyApp.get().getString(2131362123);
          String str2 = ErrorStrings.get(FinskyApp.get(), paramAnonymousVolleyError);
          FinskyApp.get().mNotificationHelper.showPurchaseErrorMessage(str1, str2, str2, paramDocument.mDocument.docid, paramDocument.mDocument.detailsUrl);
        }
      }
    });
  }
  
  public static abstract interface SuccessListener
  {
    public abstract void onFreePurchaseSuccess$5f474518();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PurchaseInitiator
 * JD-Core Version:    0.7.0.1
 */