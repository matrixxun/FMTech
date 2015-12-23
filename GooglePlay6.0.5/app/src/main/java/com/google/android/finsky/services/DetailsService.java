package com.google.android.finsky.services;

import android.accounts.Account;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.details.IDetailsService.Stub;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Details.DetailsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PurchaseButtonHelper;
import com.google.android.finsky.utils.PurchaseButtonHelper.DocumentAction;
import com.google.android.finsky.utils.PurchaseButtonHelper.DocumentActions;
import com.google.android.finsky.utils.PurchaseButtonHelper.TextStyle;
import com.google.android.finsky.utils.SignatureUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DetailsService
  extends Service
{
  private static boolean addImage(Document paramDocument, int paramInt, Bundle paramBundle)
  {
    List localList = paramDocument.getImages(paramInt);
    if ((localList != null) && (!localList.isEmpty()))
    {
      Common.Image localImage = (Common.Image)localList.get(0);
      if (localImage.hasImageUrl)
      {
        if ((localImage.hasSupportsFifeUrlOptions) && (localImage.supportsFifeUrlOptions)) {}
        for (String str = "fife_url";; str = "image_url")
        {
          paramBundle.putString(str, localImage.imageUrl);
          return true;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = paramDocument.mDocument.docid;
          arrayOfObject[1] = Integer.valueOf(paramInt);
          FinskyLog.d("App %s no FIFE URL for %d", arrayOfObject);
        }
      }
    }
    return false;
  }
  
  public static Bundle getBundle(Context paramContext, Document paramDocument, Account paramAccount, DfeToc paramDfeToc, Installer paramInstaller, AppStates paramAppStates, Libraries paramLibraries, String paramString, FinskyEventLog paramFinskyEventLog)
  {
    String str = paramDocument.mDocument.backendDocid;
    int i = paramDocument.mDocument.backendId;
    if (i != 3)
    {
      Object[] arrayOfObject5 = new Object[2];
      arrayOfObject5[0] = str;
      arrayOfObject5[1] = Integer.valueOf(i);
      FinskyLog.d("Document %s is not an app, backend=%d", arrayOfObject5);
      SignatureUtils.logEvent(paramFinskyEventLog, 512, str, "doc-backend", paramString, null);
      return null;
    }
    int j = paramDocument.mDocument.docType;
    if (j != 1)
    {
      Object[] arrayOfObject4 = new Object[2];
      arrayOfObject4[0] = str;
      arrayOfObject4[1] = Integer.valueOf(j);
      FinskyLog.d("Document %s is not an app, doc_type=%d", arrayOfObject4);
      SignatureUtils.logEvent(paramFinskyEventLog, 512, str, "doc-type", paramString, null);
      return null;
    }
    PurchaseButtonHelper.DocumentActions localDocumentActions = new PurchaseButtonHelper.DocumentActions();
    PurchaseButtonHelper.getDocumentActions(paramAccount, paramInstaller, paramLibraries, paramAppStates, paramDfeToc, 0, paramDocument, localDocumentActions);
    int k = 0;
    PurchaseButtonHelper.DocumentAction localDocumentAction;
    if (k < localDocumentActions.actionCount)
    {
      localDocumentAction = localDocumentActions.getAction(k);
      if ((localDocumentAction.actionType != 7) && (localDocumentAction.actionType != 15) && (localDocumentAction.actionType != 1)) {}
    }
    for (;;)
    {
      if (localDocumentAction != null) {
        break label266;
      }
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = str;
      arrayOfObject3[1] = localDocumentActions.toString();
      FinskyLog.d("App %s has no buy or install action, actions=%s", arrayOfObject3);
      SignatureUtils.logEvent(paramFinskyEventLog, 512, str, "doc-actions", paramString, null);
      return null;
      k++;
      break;
      localDocumentAction = null;
    }
    label266:
    Locale localLocale = paramContext.getResources().getConfiguration().locale;
    Bundle localBundle = new Bundle();
    localBundle.putString("title", paramDocument.mDocument.title);
    localBundle.putString("creator", paramDocument.mDocument.creator.toUpperCase(localLocale));
    if (paramDocument.hasRating())
    {
      localBundle.putFloat("star_rating", paramDocument.getStarRating());
      localBundle.putLong("rating_count", paramDocument.getRatingCount());
    }
    if (!addImage(paramDocument, 4, localBundle))
    {
      if (!addImage(paramDocument, 0, localBundle)) {
        break label603;
      }
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramDocument.mDocument.docid;
      FinskyLog.d("App %s using thumbnail image", arrayOfObject2);
    }
    for (;;)
    {
      PurchaseButtonHelper.TextStyle localTextStyle = new PurchaseButtonHelper.TextStyle();
      PurchaseButtonHelper.getActionStyleForFormat(localDocumentAction, i, false, false, localTextStyle);
      localBundle.putString("purchase_button_text", localTextStyle.getString(paramContext).toUpperCase(localLocale));
      Intent localIntent1 = LightPurchaseFlowActivity.createExternalPurchaseIntent$109c371d(paramDocument.mDocument.backendDocid, paramDocument.mDocument.docid, null, paramDocument.mDocument.backendId, paramDocument.mDocument.docType);
      localIntent1.setData(Uri.fromParts("detailsservice", str, null));
      localBundle.putParcelable("purchase_intent", PendingIntent.getActivity(paramContext, 0, localIntent1, 0));
      Uri localUri = new Uri.Builder().scheme("market").authority("details").appendQueryParameter("id", str).appendQueryParameter("api_source", "DetailsService").appendQueryParameter("referrer_package", paramString).build();
      Intent localIntent2 = new Intent(paramContext, MainActivity.class);
      localIntent2.setAction("android.intent.action.VIEW");
      localIntent2.setData(localUri);
      localBundle.putParcelable("details_intent", PendingIntent.getActivity(paramContext, 0, localIntent2, 0));
      SignatureUtils.logEvent(paramFinskyEventLog, 512, str, null, paramString, null);
      return localBundle;
      label603:
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramDocument.mDocument.docid;
      FinskyLog.d("App %s failed to find any image", arrayOfObject1);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    new IDetailsService.Stub()
    {
      public final Bundle getAppDetails(String paramAnonymousString)
        throws RemoteException
      {
        FinskyApp localFinskyApp = FinskyApp.get();
        Account localAccount = localFinskyApp.getCurrentAccount();
        if (localAccount == null) {
          return null;
        }
        FinskyEventLog localFinskyEventLog = localFinskyApp.getEventLogger(localAccount);
        if (!((Boolean)G.enableDetailsApi.get()).booleanValue())
        {
          FinskyLog.d("API access is blocked for all apps", new Object[0]);
          SignatureUtils.logEvent(localFinskyEventLog, 512, paramAnonymousString, "all-access-blocked", null, null);
          return null;
        }
        String str1 = SignatureUtils.getCallingAppIfAuthorized(DetailsService.this, paramAnonymousString, G.enableThirdPartyDetailsApi, localFinskyEventLog, 512);
        if (str1 == null) {
          return null;
        }
        FinskyLog.d("Received app details request for %s from %s", new Object[] { paramAnonymousString, str1 });
        String str2 = DfeUtils.createDetailsUrlFromId(paramAnonymousString);
        DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
        RequestFuture localRequestFuture = RequestFuture.newFuture();
        localDfeApi.getDetails(str2, true, true, null, localRequestFuture, localRequestFuture);
        try
        {
          Details.DetailsResponse localDetailsResponse = (Details.DetailsResponse)localRequestFuture.get();
          localDocV2 = localDetailsResponse.docV2;
          if (localDocV2 == null)
          {
            FinskyLog.d("No doc in details response for %s", new Object[] { paramAnonymousString });
            SignatureUtils.logEvent(localFinskyEventLog, 512, paramAnonymousString, "empty-details-response", str1, null);
            return null;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          FinskyLog.d("Interrupted while trying to retrieve app details", new Object[0]);
          SignatureUtils.logEvent(localFinskyEventLog, 512, paramAnonymousString, "fetch-interrupted", str1, null);
          return null;
        }
        catch (ExecutionException localExecutionException)
        {
          DocV2 localDocV2;
          Throwable localThrowable = localExecutionException.getCause();
          if (localThrowable == null) {}
          for (String str3 = null;; str3 = localThrowable.getClass().getSimpleName())
          {
            FinskyLog.d("Unable to retrieve app details: %s", new Object[] { str3 });
            SignatureUtils.logEvent(localFinskyEventLog, 512, paramAnonymousString, "fetch-error", str1, str3);
            return null;
          }
          DfeToc localDfeToc = localFinskyApp.mToc;
          Installer localInstaller = localFinskyApp.mInstaller;
          AppStates localAppStates = localFinskyApp.mAppStates;
          localAppStates.mStateStore.load();
          Libraries localLibraries = localFinskyApp.mLibraries;
          localLibraries.blockingLoad();
          DetailsService localDetailsService = DetailsService.this;
          Document localDocument = new Document(localDocV2);
          return DetailsService.getBundle(localDetailsService, localDocument, localAccount, localDfeToc, localInstaller, localAppStates, localLibraries, str1, localFinskyEventLog);
        }
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.DetailsService
 * JD-Core Version:    0.7.0.1
 */