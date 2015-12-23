package com.google.android.finsky.navigationmanager;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.config.G;
import com.google.android.finsky.download.inlineappinstaller.InlineConsumptionAppInstallerActivity;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.config.GservicesValue;

public final class ConsumptionUtils
{
  public static int getConsumptionAppRequiredString(int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
    case 5: 
    case 7: 
    case 8: 
    default: 
      return -1;
    case 1: 
      return 2131361906;
    case 4: 
      return 2131362838;
    case 2: 
      return 2131362345;
    case 6: 
      return 2131362368;
    }
    return 2131362196;
  }
  
  private static Intent getConsumptionIntent(Context paramContext, Document paramDocument, Account paramAccount)
  {
    if (paramDocument == null) {}
    int i;
    do
    {
      return null;
      i = paramDocument.mDocument.backendId;
    } while (paramDocument.mDocument.backendDocid == null);
    switch (i)
    {
    case 5: 
    default: 
      throw new IllegalStateException("Cannot open an item from the corpus " + i);
    }
    return IntentUtils.buildConsumptionAppViewItemIntent(paramContext, paramDocument, paramAccount.name);
  }
  
  public static boolean isConsumptionAppNeeded(Context paramContext, Document paramDocument, Account paramAccount)
  {
    boolean bool1 = true;
    String str = IntentUtils.getPackageName(paramDocument.mDocument.backendId);
    if (TextUtils.isEmpty(str)) {
      bool1 = false;
    }
    label264:
    label286:
    label303:
    label359:
    label365:
    label371:
    label375:
    for (;;)
    {
      return bool1;
      PackageStateRepository.PackageState localPackageState = FinskyApp.get().mAppStates.mPackageManager.get(str);
      int i;
      if (localPackageState == null) {
        i = 0;
      }
      for (;;)
      {
        if (i == 0) {
          break label375;
        }
        Intent localIntent = getConsumptionIntent(paramContext, paramDocument, paramAccount);
        if ((localIntent == null) || (!IntentUtils.canResolveIntent(paramContext.getPackageManager(), localIntent))) {
          break;
        }
        return false;
        if (("com.google.android.videos".equals(str)) && (localPackageState.installedVersion < IntentUtils.getMinimumRequiredVideosAppVersion()))
        {
          i = 0;
        }
        else if (("com.google.android.apps.magazines".equals(str)) && (localPackageState.installedVersion < ((Integer)G.firstNewsstandAppVersion.get()).intValue()))
        {
          i = 0;
        }
        else
        {
          if ((paramDocument != null) && ("com.google.android.apps.magazines".equals(str)))
          {
            Common.Offer[] arrayOfOffer = paramDocument.mDocument.offer;
            if ((arrayOfOffer != null) && (arrayOfOffer.length > 0)) {
              if (paramDocument.mDocument.docType == 15)
              {
                if ((!arrayOfOffer[0].checkoutFlowRequired) && (localPackageState.installedVersion < ((Integer)G.newsstandFreeSubscriptionCompletionAppVersion.get()).intValue())) {
                  i = 0;
                }
              }
              else if ((paramDocument.mDocument.docType == 17) && (FinskyApp.get().getExperiments().isEnabled(12602952L)))
              {
                int j = arrayOfOffer.length;
                int k = 0;
                boolean bool2;
                boolean bool3;
                if (k < j) {
                  if (2 == arrayOfOffer[k].offerType)
                  {
                    bool2 = bool1;
                    if (LibraryUtils.getOwnerWithCurrentAccount(paramDocument, FinskyApp.get().mLibraries, paramAccount) == null) {
                      break label359;
                    }
                    bool3 = bool1;
                    if (localPackageState.installedVersion >= ((Integer)G.newsstandFreeSampleMagazinesAppVersion.get()).intValue()) {
                      break label365;
                    }
                  }
                }
                for (boolean bool4 = bool1;; bool4 = false)
                {
                  if ((!bool4) || (!bool2) || (bool3)) {
                    break label371;
                  }
                  i = 0;
                  break;
                  k++;
                  break label264;
                  bool2 = false;
                  break label286;
                  bool3 = false;
                  break label303;
                }
              }
            }
          }
          i = bool1;
        }
      }
    }
  }
  
  public static void maybeBindAppIcon(Document paramDocument, View paramView)
  {
    FifeImageView localFifeImageView = (FifeImageView)paramView.findViewById(2131755610);
    int i = 0;
    if (paramDocument != null)
    {
      ViewGroup.LayoutParams localLayoutParams = localFifeImageView.getLayoutParams();
      Common.Image localImage = ThumbnailUtils.getImageFromDocument(paramDocument, localLayoutParams.width, localLayoutParams.height, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
      i = 0;
      if (localImage != null)
      {
        localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
        localFifeImageView.setVisibility(0);
        i = 1;
      }
    }
    if (i == 0) {
      localFifeImageView.setVisibility(8);
    }
  }
  
  public static boolean openItem(Context paramContext, Account paramAccount, Document paramDocument, FragmentManager paramFragmentManager, Fragment paramFragment, int paramInt, PurchaseParams paramPurchaseParams)
  {
    if (showAppNeededDialogIfNeeded(paramContext, paramAccount, paramDocument, paramFragmentManager, paramFragment, paramInt)) {
      return true;
    }
    Intent localIntent = getConsumptionIntent(paramContext, paramDocument, paramAccount);
    ResolveInfo localResolveInfo = paramContext.getPackageManager().resolveActivity(localIntent, 0);
    if ((localIntent != null) && (localResolveInfo != null))
    {
      if (paramDocument.getAppDetails() != null) {
        FinskyApp.get().mNotificationHelper.hideAllMessagesForPackage(paramDocument.getAppDetails().packageName);
      }
      if (paramPurchaseParams != null)
      {
        localIntent.putExtra("backend", paramPurchaseParams.docid.backend);
        localIntent.putExtra("backend_docid", paramPurchaseParams.docid.backendDocid);
        localIntent.putExtra("document_type", paramPurchaseParams.docid.type);
        localIntent.putExtra("full_docid", paramPurchaseParams.docidStr);
        localIntent.putExtra("calling_package", paramPurchaseParams.callingPackage);
      }
      paramContext.startActivity(localIntent);
      return false;
    }
    Toast.makeText(paramContext, paramContext.getString(2131362288), 0).show();
    return false;
  }
  
  public static void showAppNeededDialog(Context paramContext, int paramInt1, FragmentManager paramFragmentManager, Fragment paramFragment, int paramInt2)
  {
    String str = IntentUtils.getPackageName(paramInt1);
    if (str == null) {
      Toast.makeText(paramContext, paramContext.getString(2131362288), 0).show();
    }
    while (paramFragmentManager.findFragmentByTag("app_needed_dialog") != null) {
      return;
    }
    int i = getConsumptionAppRequiredString(paramInt1);
    Bundle localBundle = new Bundle();
    localBundle.putString("dialog_details_url", DfeUtils.createDetailsUrlFromId(str));
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageId(i).setPositiveId(2131362418).setNegativeId(2131361915);
    localBuilder.setCallback(paramFragment, paramInt2, localBundle);
    localBuilder.build().show(paramFragmentManager, "app_needed_dialog");
  }
  
  public static boolean showAppNeededDialogIfNeeded(Context paramContext, Account paramAccount, Document paramDocument, FragmentManager paramFragmentManager, Fragment paramFragment, int paramInt)
  {
    if (isConsumptionAppNeeded(paramContext, paramDocument, paramAccount))
    {
      int i = paramDocument.mDocument.backendId;
      if ((FinskyApp.get().getExperiments().isEnabled(12603704L)) && ((i == 1) || (i == 6)) && ((paramContext instanceof Activity)))
      {
        Intent localIntent = InlineConsumptionAppInstallerActivity.createIntent$6a617ce3(paramAccount, paramDocument.mDocument);
        ((Activity)paramContext).startActivityForResult(localIntent, 25);
        return true;
      }
      showAppNeededDialog(paramContext, i, paramFragmentManager, paramFragment, paramInt);
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.navigationmanager.ConsumptionUtils
 * JD-Core Version:    0.7.0.1
 */