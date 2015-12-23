package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.utils.PlayUtils;

public final class PurchaseButtonHelper
{
  static boolean sAppSizeInOverflowMenuEnabled;
  static String sCachedExperimentAccountName;
  static boolean sYoutubeCardLabelsHdPricesExperimentEnabled;
  static boolean sYoutubeCardLabelsNoPricesExperimentEnabled;
  static boolean sYoutubePurchaseActionsHdPricesExperimentEnabled;
  
  private static void addOfferActions(Document paramDocument, Common.Offer paramOffer, Account paramAccount, Library paramLibrary, int paramInt, DocumentActions paramDocumentActions)
  {
    ensureExperimentsLoaded(paramAccount);
    boolean bool2;
    DocUtils.OfferFilter localOfferFilter3;
    label88:
    Common.Offer localOffer1;
    label108:
    Common.Offer[] arrayOfOffer;
    int j;
    label182:
    Common.Offer localOffer2;
    label205:
    int k;
    label213:
    int m;
    label221:
    int n;
    int i4;
    label242:
    Common.Offer localOffer3;
    int i5;
    if ((paramInt != 0) && (paramDocument.mDocument.backendId == 4))
    {
      bool2 = false;
      switch (paramInt)
      {
      default: 
        if ((bool2) && (!DocUtils.OfferFilter.HIGH_DEF.matches(paramOffer.offerType))) {
          if (DocUtils.OfferFilter.RENTAL.matches(paramOffer.offerType))
          {
            localOfferFilter3 = DocUtils.OfferFilter.RENTAL_HIGH_DEF;
            localOffer1 = DocUtils.getLowestPricedOffer(paramDocument.mDocument.offer, true, localOfferFilter3);
            if (localOffer1 == null) {
              break label324;
            }
            paramDocumentActions.status = 4;
            if ((paramInt != 1) || (paramDocument.mDocument.backendId != 4) || (!sYoutubeCardLabelsNoPricesExperimentEnabled))
            {
              paramDocumentActions.listingOfferText = localOffer1.formattedAmount;
              if (shouldAddFullText(localOffer1)) {
                paramDocumentActions.listingOfferFullText = localOffer1.formattedFullAmount;
              }
            }
            arrayOfOffer = paramDocument.mDocument.offer;
            int i = arrayOfOffer.length;
            j = 0;
            if (j >= i) {
              break label336;
            }
            localOffer2 = arrayOfOffer[j];
            if (localOffer2.offerType != 2) {
              break label330;
            }
            if (localOffer2 == null) {
              break label342;
            }
            k = 1;
            if (k == 0) {
              break label348;
            }
            m = 1;
            n = 2 - m;
            if (arrayOfOffer.length > 2) {
              break label529;
            }
            int i3 = arrayOfOffer.length;
            i4 = 0;
            if (i4 >= i3) {
              break label662;
            }
            localOffer3 = arrayOfOffer[i4];
            i5 = localOffer3.offerType;
            if ((i5 != 2) && (i5 != 11))
            {
              if (!Document.isPreorderOffer(localOffer3)) {
                break label354;
              }
              paramDocumentActions.addOfferAction(5, localOffer3, paramDocument, paramAccount);
            }
          }
        }
        break;
      }
    }
    label324:
    label330:
    label336:
    label342:
    label470:
    for (;;)
    {
      i4++;
      break label242;
      bool2 = sYoutubeCardLabelsHdPricesExperimentEnabled;
      break;
      bool2 = sYoutubePurchaseActionsHdPricesExperimentEnabled;
      break;
      localOfferFilter3 = DocUtils.OfferFilter.PURCHASE_HIGH_DEF;
      break label88;
      localOffer1 = paramOffer;
      break label108;
      j++;
      break label182;
      localOffer2 = null;
      break label205;
      k = 0;
      break label213;
      label348:
      m = 0;
      break label221;
      label354:
      if (DocUtils.OfferFilter.RENTAL.matches(i5))
      {
        paramDocumentActions.addOfferAction(3, localOffer3, paramDocument, paramAccount);
      }
      else
      {
        if (DocUtils.OfferFilter.PURCHASE.matches(i5))
        {
          int i6;
          if ((sAppSizeInOverflowMenuEnabled) && (paramInt == 2) && (paramDocument.mDocument.backendId == 3) && (paramDocument.getInstallationSize() > 0L))
          {
            i6 = 1;
            if (localOffer3.micros != 0L) {
              break label470;
            }
            if (i6 == 0) {
              break label463;
            }
          }
          for (int i8 = 18;; i8 = 7)
          {
            paramDocumentActions.addOfferAction(i8, localOffer3, paramDocument, paramAccount);
            break;
            i6 = 0;
            break label424;
          }
          if (i6 != 0) {}
          for (int i7 = 17;; i7 = 1)
          {
            paramDocumentActions.addOfferAction(i7, localOffer3, paramDocument, paramAccount);
            break;
          }
        }
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(i5);
        arrayOfObject2[1] = paramDocument;
        FinskyLog.w("Don't know how to show action for offer type %d on document %s", arrayOfObject2);
      }
    }
    label424:
    label463:
    if (n >= 2)
    {
      int i1;
      DocUtils.OfferFilter localOfferFilter1;
      if ((paramDocument.mDocument.backendId == 4) && ((paramInt == 2) || (paramInt == 3)) && (sYoutubePurchaseActionsHdPricesExperimentEnabled))
      {
        i1 = 1;
        if (i1 == 0) {
          break label694;
        }
        localOfferFilter1 = DocUtils.OfferFilter.PURCHASE_HIGH_DEF;
        if (i1 == 0) {
          break label702;
        }
      }
      for (DocUtils.OfferFilter localOfferFilter2 = DocUtils.OfferFilter.RENTAL_HIGH_DEF;; localOfferFilter2 = DocUtils.OfferFilter.RENTAL)
      {
        int i2 = 0 + paramDocumentActions.addActionFromOfferList(1, arrayOfOffer, DocUtils.OfferFilter.PURCHASE, localOfferFilter1, paramDocument, paramAccount) + paramDocumentActions.addActionFromOfferList(3, arrayOfOffer, DocUtils.OfferFilter.RENTAL, localOfferFilter2, paramDocument, paramAccount);
        if (i2 != arrayOfOffer.length)
        {
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Integer.valueOf(i2);
          arrayOfObject1[1] = Integer.valueOf(arrayOfOffer.length);
          FinskyLog.wtf("Could only handle %d of %d offers", arrayOfObject1);
        }
        if (k != 0)
        {
          if (!LibraryUtils.isOfferOwned(paramDocument, paramLibrary, 2)) {
            break label741;
          }
          paramDocumentActions.addOfferAction(10, localOffer2, paramDocument, paramAccount);
        }
        return;
        i1 = 0;
        break;
        localOfferFilter1 = DocUtils.OfferFilter.PURCHASE;
        break label577;
      }
    }
    label529:
    label577:
    if (localOffer1 == paramOffer) {}
    label662:
    label694:
    label702:
    for (boolean bool1 = true;; bool1 = false)
    {
      paramDocumentActions.addDisambiguationAction(13, localOffer1, bool1, paramDocument, paramAccount);
      break;
    }
    label741:
    paramDocumentActions.addOfferAction(11, localOffer2, paramDocument, paramAccount);
  }
  
  public static boolean canCreateClickListener(DocumentAction paramDocumentAction)
  {
    return (paramDocumentAction.actionType != 9) && (paramDocumentAction.actionType != 12) && (paramDocumentAction.actionType != 16);
  }
  
  private static void ensureExperimentsLoaded(Account paramAccount)
  {
    for (;;)
    {
      try
      {
        boolean bool1 = TextUtils.equals(sCachedExperimentAccountName, paramAccount.name);
        if (bool1) {
          return;
        }
        FinskyApp localFinskyApp = FinskyApp.get();
        FinskyExperiments localFinskyExperiments = localFinskyApp.getExperiments(paramAccount.name);
        boolean bool2 = localFinskyExperiments.isEnabled(12602778L);
        sYoutubeCardLabelsNoPricesExperimentEnabled = bool2;
        if ((!bool2) && (localFinskyExperiments.isEnabled(12602779L)))
        {
          bool3 = true;
          sYoutubeCardLabelsHdPricesExperimentEnabled = bool3;
          sYoutubePurchaseActionsHdPricesExperimentEnabled = localFinskyExperiments.isEnabled(12602780L);
          if (GooglePlayServicesUtil.isSidewinderDevice(localFinskyApp)) {
            break label141;
          }
          boolean bool5 = localFinskyExperiments.isEnabled(12603516L);
          bool4 = false;
          if (bool5) {
            break label141;
          }
          sAppSizeInOverflowMenuEnabled = bool4;
          sCachedExperimentAccountName = paramAccount.name;
          continue;
        }
        boolean bool3 = false;
      }
      finally {}
      continue;
      label141:
      boolean bool4 = true;
    }
  }
  
  public static View.OnClickListener getActionClickListener(DocumentAction paramDocumentAction, int paramInt, NavigationManager paramNavigationManager, String paramString, PlayStoreUiElementNode paramPlayStoreUiElementNode, final Context paramContext)
  {
    int i;
    switch (paramDocumentAction.actionType)
    {
    case 9: 
    case 12: 
    case 16: 
    default: 
      FinskyLog.wtf("Unrecognized action %d", new Object[] { paramDocumentAction });
      return null;
    case 6: 
    case 10: 
      return paramNavigationManager.getOpenClickListener(paramDocumentAction.document, paramDocumentAction.account, paramPlayStoreUiElementNode);
    case 14: 
      new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          String str = this.val$action.document.mDocument.docid;
          paramContext.getPackageManager().setApplicationEnabledSetting(str, 1, 0);
        }
      };
    case 1: 
    case 13: 
    case 15: 
    case 17: 
      if (paramDocumentAction.offerType == 7) {
        i = 232;
      }
      break;
    }
    for (;;)
    {
      return paramNavigationManager.getBuyImmediateClickListener(paramDocumentAction.account, paramDocumentAction.document, paramDocumentAction.offerType, paramDocumentAction.offerFilter, paramString, i, paramPlayStoreUiElementNode);
      if (paramInt == 4)
      {
        i = 231;
      }
      else
      {
        i = 200;
        continue;
        if (paramDocumentAction.offerType == 4)
        {
          i = 229;
        }
        else if (paramInt == 4)
        {
          i = 228;
        }
        else
        {
          i = 200;
          continue;
          i = 200;
          continue;
          i = 200;
          continue;
          i = 226;
          continue;
          i = 221;
          continue;
          i = 217;
          continue;
          i = 222;
        }
      }
    }
  }
  
  public static void getActionStyleForFormat(DocumentAction paramDocumentAction, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TextStyle paramTextStyle)
  {
    int i = 2131361909;
    paramTextStyle.reset();
    switch (paramDocumentAction.actionType)
    {
    default: 
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(paramDocumentAction.actionType);
      FinskyLog.wtf("Unrecognized action %d", arrayOfObject3);
      return;
    case 2: 
      paramTextStyle.resourceId = 2131362314;
      return;
    case 15: 
      paramTextStyle.resourceId = 2131361911;
      return;
    case 1: 
      if (paramDocumentAction.offerType == 0)
      {
        if (paramDocumentAction.isLowestPriced) {
          i = 2131362616;
        }
        paramTextStyle.resourceId = i;
      }
      for (;;)
      {
        paramTextStyle.offerText = paramDocumentAction.offerText;
        paramTextStyle.offerFullText = paramDocumentAction.offerFullText;
        return;
        if (paramInt == 3)
        {
          if (paramBoolean2) {}
          for (;;)
          {
            paramTextStyle.resourceId = i;
            break;
            i = -1;
          }
        }
        if (paramInt == 4)
        {
          if (paramDocumentAction.offerType == 7) {
            paramTextStyle.resourceId = 2131361910;
          } else {
            paramTextStyle.resourceId = 2131361912;
          }
        }
        else if (paramBoolean2) {
          paramTextStyle.resourceId = i;
        } else {
          paramTextStyle.resourceId = -1;
        }
      }
    case 17: 
      if (paramInt == 3)
      {
        paramTextStyle.resourceId = 2131361913;
        paramTextStyle.offerText = paramDocumentAction.offerText;
        paramTextStyle.offerFullText = paramDocumentAction.offerFullText;
        paramTextStyle.appBytes = paramDocumentAction.appBytes;
        return;
      }
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramDocumentAction.actionType);
      FinskyLog.wtf("Unsupported backend for %d", arrayOfObject2);
      return;
    case 3: 
      int k;
      if (paramDocumentAction.offerType == 0) {
        if (paramDocumentAction.isLowestPriced)
        {
          k = 2131362656;
          paramTextStyle.resourceId = k;
        }
      }
      for (;;)
      {
        paramTextStyle.offerText = paramDocumentAction.offerText;
        paramTextStyle.offerFullText = paramDocumentAction.offerFullText;
        return;
        k = 2131362654;
        break;
        if (paramInt == 4)
        {
          if (paramDocumentAction.offerType == 4) {
            paramTextStyle.resourceId = 2131362655;
          } else {
            paramTextStyle.resourceId = 2131362657;
          }
        }
        else {
          paramTextStyle.resourceId = 2131362654;
        }
      }
    case 13: 
      paramTextStyle.resourceId = 2131362615;
      paramTextStyle.offerText = paramDocumentAction.offerText;
      paramTextStyle.offerFullText = paramDocumentAction.offerFullText;
      return;
    case 5: 
      if (paramInt == 4) {
        if (paramDocumentAction.offerType == 7) {
          paramTextStyle.resourceId = 2131362587;
        }
      }
      for (;;)
      {
        paramTextStyle.offerText = paramDocumentAction.offerText;
        paramTextStyle.offerFullText = paramDocumentAction.offerFullText;
        return;
        paramTextStyle.resourceId = 2131362588;
        continue;
        paramTextStyle.resourceId = 2131362586;
      }
    case 4: 
      if (paramBoolean1) {}
      for (int j = 2131362310;; j = 2131362309)
      {
        paramTextStyle.resourceId = j;
        paramTextStyle.offerText = paramDocumentAction.offerText;
        paramTextStyle.offerFullText = paramDocumentAction.offerFullText;
        return;
      }
    case 6: 
      switch (paramInt)
      {
      case 3: 
      case 5: 
      default: 
        paramTextStyle.resourceId = 2131362438;
        return;
      case 1: 
      case 6: 
        paramTextStyle.resourceId = 2131362633;
        return;
      case 2: 
        paramTextStyle.resourceId = 2131362305;
        return;
      }
      paramTextStyle.resourceId = 2131362550;
      return;
    case 7: 
      if (paramInt == 3)
      {
        paramTextStyle.resourceId = 2131362224;
        return;
      }
      if (paramInt == 6)
      {
        paramTextStyle.resourceId = 2131361826;
        return;
      }
      paramTextStyle.resourceId = 2131361825;
      return;
    case 18: 
      if (paramInt == 3)
      {
        paramTextStyle.resourceId = 2131362247;
        paramTextStyle.appBytes = paramDocumentAction.appBytes;
        return;
      }
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramDocumentAction.actionType);
      FinskyLog.wtf("Unsupported backend for %d", arrayOfObject1);
      return;
    case 8: 
      paramTextStyle.resourceId = 2131362820;
      return;
    case 9: 
      paramTextStyle.resourceId = 2131361916;
      return;
    case 10: 
    case 11: 
      paramTextStyle.resourceId = 2131362708;
      return;
    case 12: 
      paramTextStyle.resourceId = 2131362099;
      return;
    case 14: 
      paramTextStyle.resourceId = 2131362113;
      return;
    }
    paramTextStyle.resourceId = 2131362840;
  }
  
  public static void getActionStyleLong(DocumentAction paramDocumentAction, int paramInt, TextStyle paramTextStyle)
  {
    getActionStyleForFormat(paramDocumentAction, paramInt, true, true, paramTextStyle);
  }
  
  public static void getDocumentActions(Account paramAccount, Installer paramInstaller, Libraries paramLibraries, AppStates paramAppStates, DfeToc paramDfeToc, int paramInt, Document paramDocument, DocumentActions paramDocumentActions)
  {
    paramDocumentActions.reset();
    paramDocumentActions.backend = paramDocument.mDocument.backendId;
    AccountLibrary localAccountLibrary = paramLibraries.getAccountLibrary(paramAccount);
    Common.Offer localOffer1 = DocUtils.getListingOffer(paramDocument, paramDfeToc, localAccountLibrary);
    Account localAccount1 = LibraryUtils.getOwnerWithCurrentAccount(paramDocument, paramLibraries, paramAccount);
    boolean bool1;
    int i;
    label71:
    int j;
    if (localAccount1 != null)
    {
      bool1 = true;
      if ((!bool1) || (!LibraryUtils.isPreordered(paramDocument, localAccountLibrary))) {
        break label119;
      }
      i = 1;
      if ((bool1) || (!LibraryUtils.isPreorderedThroughBundle(paramDocument, localAccountLibrary))) {
        break label125;
      }
      j = 1;
      label89:
      if ((localOffer1 != null) || (i != 0) || (j != 0) || (paramDocument.isPreregistration())) {
        break label131;
      }
    }
    label119:
    label125:
    do
    {
      for (;;)
      {
        return;
        bool1 = false;
        break;
        i = 0;
        break label71;
        j = 0;
        break label89;
        paramDocumentActions.displayAsOwned = bool1;
        switch (paramDocumentActions.backend)
        {
        case 5: 
        default: 
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(paramDocument.mDocument.backendId);
          FinskyLog.wtf("Unsupported backend: %d", arrayOfObject);
          if (LibraryUtils.isAvailable(paramDocument, paramDfeToc, localAccountLibrary))
          {
            addOfferActions(paramDocument, localOffer1, paramAccount, localAccountLibrary, paramInt, paramDocumentActions);
            return;
          }
          break;
        case 3: 
          ensureExperimentsLoaded(paramAccount);
          paramDocumentActions.displayAsOwned = false;
          Account localAccount2;
          if (localAccount1 != null) {
            localAccount2 = localAccount1;
          }
          for (;;)
          {
            if (localOffer1 == null)
            {
              if (!paramDocument.isPreregistration()) {
                break;
              }
              if (FinskyApp.get().mPreregistrationHelper.isPreregistered(paramDocument.mDocument.docid, localAccount2))
              {
                paramDocumentActions.status = 11;
                return;
                localAccount2 = paramAccount;
              }
              else
              {
                paramDocumentActions.status = 10;
                return;
              }
            }
          }
          if (paramDocument.mDocument.docType == 1)
          {
            AppDetails localAppDetails = paramDocument.getAppDetails();
            AppActionAnalyzer localAppActionAnalyzer = new AppActionAnalyzer(localAppDetails.packageName, paramAppStates, paramLibraries);
            if (localAppActionAnalyzer.isInstalled)
            {
              if (localAppActionAnalyzer.isDisabled)
              {
                paramDocumentActions.status = 2;
                paramDocumentActions.addAction(14, paramDocument, localAccount2);
              }
              for (;;)
              {
                paramDocumentActions.displayAsOwned = true;
                return;
                if (localAppActionAnalyzer.hasUpdateAvailable(paramDocument))
                {
                  if (paramInstaller.getState(localAppDetails.packageName) != 0)
                  {
                    paramDocumentActions.status = 9;
                  }
                  else
                  {
                    paramDocumentActions.addOfferAction(8, localOffer1, paramDocument, localAccount2);
                    paramDocumentActions.status = 5;
                  }
                }
                else {
                  paramDocumentActions.addAction(6, paramDocument, localAccount2);
                }
              }
            }
            if (paramInstaller.getState(localAppDetails.packageName) != 0)
            {
              paramDocumentActions.status = 1;
              return;
            }
          }
          if (LibraryUtils.isAvailable(paramDocument, paramDfeToc, localAccountLibrary))
          {
            if ((bool1) && (localOffer1.checkoutFlowRequired))
            {
              int i4;
              if ((sAppSizeInOverflowMenuEnabled) && (paramInt == 2) && (paramDocument.mDocument.backendId == 3) && (paramDocument.getInstallationSize() > 0L))
              {
                i4 = 1;
                paramDocumentActions.displayAsOwned = true;
                if (i4 == 0) {
                  break label582;
                }
              }
              for (int i5 = 18;; i5 = 7)
              {
                paramDocumentActions.addOfferAction(i5, localOffer1, paramDocument, localAccount2);
                paramDocumentActions.status = 6;
                return;
                i4 = 0;
                break;
              }
            }
            if (LibraryUtils.isAvailable(paramDocument, paramDfeToc, localAccountLibrary))
            {
              addOfferActions(paramDocument, localOffer1, localAccount2, localAccountLibrary, paramInt, paramDocumentActions);
              return;
            }
          }
          break;
        case 6: 
          boolean bool2 = FinskyApp.get().getExperiments().isEnabled(12602952L);
          if (localOffer1 != null)
          {
            paramDocumentActions.listingOfferText = localOffer1.formattedAmount;
            if (shouldAddFullText(localOffer1)) {
              paramDocumentActions.listingOfferFullText = localOffer1.formattedFullAmount;
            }
          }
          Document localDocument1;
          Document localDocument2;
          int k;
          int m;
          int n;
          int i1;
          int i2;
          Common.Offer localOffer2;
          if ((paramDocument.mDocument.docType == 17) || (paramDocument.mDocument.docType == 25))
          {
            localDocument1 = paramDocument;
            if ((paramDocument.mDocument.docType != 16) && (paramDocument.mDocument.docType != 24)) {
              break label1017;
            }
            localDocument2 = DocUtils.getMagazineCurrentIssueDocument(paramDocument);
            k = 0;
            m = 0;
            if (localDocument2 != null)
            {
              if (LibraryUtils.getOwnerWithCurrentAccount(localDocument2, paramLibraries, paramAccount) == null) {
                break label1024;
              }
              m = 1;
              if ((m != 0) || (k != 0))
              {
                paramDocumentActions.displayAsOwned = true;
                paramDocumentActions.addAction(6, localDocument2, paramAccount);
              }
            }
            if ((paramDocument.mDocument.docType != 16) && (paramDocument.mDocument.docType != 17)) {
              break label1065;
            }
            n = 1;
            if (!paramDocument.hasSubscriptions()) {
              break label1173;
            }
            if (LibraryUtils.getOwnerWithCurrentAccount(paramDocument.getSubscriptionsList(), paramLibraries, paramAccount) == null) {
              break label1071;
            }
            if (localDocument2 == null)
            {
              paramDocumentActions.displayAsOwned = true;
              paramDocumentActions.addAction(6, paramDocument, paramAccount);
            }
            paramDocumentActions.status = 8;
            i1 = 0;
            if ((m != 0) || (localDocument2 == null) || (!LibraryUtils.isAvailable(localDocument2, paramDfeToc, localAccountLibrary))) {
              continue;
            }
            if (17 != localDocument2.mDocument.docType) {
              break label1179;
            }
            i2 = 1;
            localOffer2 = DocUtils.getMagazineIssueOffer(localDocument2, paramDfeToc, localAccountLibrary, 1);
            Common.Offer localOffer3 = DocUtils.getMagazineIssueOffer(localDocument2, paramDfeToc, localAccountLibrary, 2);
            if ((localOffer3 == null) || (k != 0)) {
              break label1198;
            }
            if ((!bool2) || (i2 == 0)) {
              break label1185;
            }
            paramDocumentActions.addOfferAction(11, localOffer3, localDocument2, paramAccount);
            if ((bool2) && (i2 != 0) && (!paramDocument.hasSubscriptions()) && (localOffer2 != null) && (localOffer2.micros > 0L)) {
              paramDocumentActions.addOfferAction(4, localOffer2, localDocument2, paramAccount);
            }
          }
          do
          {
            while (!paramDocumentActions.hasStatus())
            {
              paramDocumentActions.status = 4;
              return;
              localDocument1 = null;
              break label694;
              localDocument2 = localDocument1;
              break label727;
              k = 0;
              m = 0;
              if (!bool2) {
                break label751;
              }
              boolean bool3 = LibraryUtils.isOfferOwned(localDocument2, localAccountLibrary, 2);
              k = 0;
              m = 0;
              if (!bool3) {
                break label751;
              }
              k = 1;
              m = 0;
              break label751;
              n = 0;
              break label806;
              if (LibraryUtils.isAvailable(paramDocument, paramDfeToc, localAccountLibrary))
              {
                Common.Offer localOffer4 = DocUtils.getMagazineIssueOffer(localDocument2, paramDfeToc, localAccountLibrary, 1);
                Common.Offer localOffer5 = DocUtils.getMagazineIssueOffer(localDocument2, paramDfeToc, localAccountLibrary, 2);
                if ((bool2) && (n != 0) && (m == 0) && (((localOffer4 != null) && (localOffer4.micros > 0L)) || (localOffer5 != null)))
                {
                  paramDocumentActions.addDisambiguationAction(15, localOffer1, false, paramDocument, paramAccount);
                  i1 = 1;
                  break label858;
                }
                paramDocumentActions.addDisambiguationAction(2, localOffer1, false, paramDocument, paramAccount);
              }
              i1 = 0;
              break label858;
              i2 = 0;
              break label896;
              paramDocumentActions.addAction(6, localDocument2, paramAccount);
              break label952;
              if (localOffer2 != null)
              {
                if (localOffer2.checkoutFlowRequired) {
                  break label1224;
                }
                paramDocumentActions.addAction(6, localDocument2, paramAccount);
              }
            }
          } while ((bool2) && (i1 != 0) && (localOffer2.micros != 0L));
          if (localOffer2.micros > 0L) {}
          for (int i3 = 4;; i3 = 7)
          {
            paramDocumentActions.addOfferAction(i3, localOffer2, localDocument2, paramAccount);
            break;
          }
        case 1: 
        case 2: 
        case 4: 
          if (!bool1) {
            break label1394;
          }
          if (i != 0)
          {
            paramDocumentActions.addAction(9, paramDocument, localAccount1);
            paramDocumentActions.status = 3;
            return;
          }
          paramDocumentActions.addAction(6, paramDocument, localAccount1);
          if ((LibraryUtils.isOfferOwned(paramDocument, localAccountLibrary, 3)) || (LibraryUtils.isOfferOwned(paramDocument, localAccountLibrary, 4))) {}
          for (paramDocumentActions.status = 7; (paramDocumentActions.backend == 4) && (!PlayUtils.isTv(FinskyApp.get())) && (!paramDocument.isVideoBundle()); paramDocumentActions.status = 6)
          {
            paramDocumentActions.addAction(12, paramDocument, paramAccount);
            return;
          }
        }
      }
      if (j != 0)
      {
        paramDocumentActions.addAction(16, paramDocument, paramAccount);
        return;
      }
    } while (!LibraryUtils.isAvailable(paramDocument, paramDfeToc, localAccountLibrary));
    label131:
    label582:
    label727:
    label751:
    label1017:
    addOfferActions(paramDocument, localOffer1, paramAccount, localAccountLibrary, paramInt, paramDocumentActions);
    label694:
    label858:
    label1394:
    return;
  }
  
  static boolean shouldAddFullText(Common.Offer paramOffer)
  {
    return (paramOffer.hasMicros) && (paramOffer.hasFullPriceMicros) && (paramOffer.hasFormattedFullAmount) && (paramOffer.fullPriceMicros > paramOffer.micros);
  }
  
  public static final class DocumentAction
  {
    public Account account;
    public int actionType;
    public long appBytes;
    public Document document;
    public boolean isLowestPriced;
    public DocUtils.OfferFilter offerFilter;
    public String offerFullText;
    public String offerText;
    public int offerType;
    
    public DocumentAction()
    {
      reset();
    }
    
    public final void reset()
    {
      this.actionType = 0;
      this.offerText = null;
      this.offerFullText = null;
      this.isLowestPriced = false;
      this.offerType = -1;
      this.offerFilter = null;
      this.appBytes = 0L;
      this.document = null;
      this.account = null;
    }
  }
  
  public static final class DocumentActions
  {
    public int actionCount;
    public final PurchaseButtonHelper.DocumentAction[] actions = new PurchaseButtonHelper.DocumentAction[2];
    public int backend;
    public boolean displayAsOwned;
    public String listingOfferFullText;
    public String listingOfferText;
    public int status;
    
    public DocumentActions()
    {
      for (int i = 0; i < 2; i++) {
        this.actions[i] = new PurchaseButtonHelper.DocumentAction();
      }
      reset();
    }
    
    private void addAction(int paramInt1, String paramString1, String paramString2, boolean paramBoolean, int paramInt2, DocUtils.OfferFilter paramOfferFilter, Document paramDocument, Account paramAccount)
    {
      if (this.actionCount < 2)
      {
        this.actions[this.actionCount].reset();
        this.actions[this.actionCount].actionType = paramInt1;
        this.actions[this.actionCount].offerText = paramString1;
        this.actions[this.actionCount].offerFullText = paramString2;
        this.actions[this.actionCount].isLowestPriced = paramBoolean;
        this.actions[this.actionCount].offerType = paramInt2;
        this.actions[this.actionCount].offerFilter = paramOfferFilter;
        this.actions[this.actionCount].appBytes = paramDocument.getInstallationSize();
        this.actions[this.actionCount].document = paramDocument;
        this.actions[this.actionCount].account = paramAccount;
        this.actionCount = (1 + this.actionCount);
        return;
      }
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      FinskyLog.wtf("Trying to add action %d but no more room for actions", arrayOfObject);
    }
    
    public final void addAction(int paramInt, Document paramDocument, Account paramAccount)
    {
      addAction(paramInt, null, null, false, -1, null, paramDocument, paramAccount);
    }
    
    public final int addActionFromOfferList(int paramInt, Common.Offer[] paramArrayOfOffer, DocUtils.OfferFilter paramOfferFilter1, DocUtils.OfferFilter paramOfferFilter2, Document paramDocument, Account paramAccount)
    {
      int i = 0;
      Object localObject1 = null;
      int j = paramArrayOfOffer.length;
      for (int k = 0; k < j; k++)
      {
        Common.Offer localOffer2 = paramArrayOfOffer[k];
        if (paramOfferFilter1.matches(localOffer2.offerType))
        {
          localObject1 = localOffer2;
          i++;
        }
      }
      if (i == 0) {
        return 0;
      }
      if (i == 1)
      {
        addOfferAction(paramInt, localObject1, paramDocument, paramAccount);
        return 1;
      }
      Object localObject2 = DocUtils.getLowestPricedOffer(paramArrayOfOffer, true, paramOfferFilter1);
      boolean bool = true;
      if (paramOfferFilter2 != paramOfferFilter1)
      {
        Common.Offer localOffer1 = DocUtils.getLowestPricedOffer(paramArrayOfOffer, true, paramOfferFilter2);
        if ((localOffer1 != null) && (localOffer1 != localObject2))
        {
          localObject2 = localOffer1;
          bool = false;
        }
      }
      String str1 = ((Common.Offer)localObject2).formattedAmount;
      if (PurchaseButtonHelper.shouldAddFullText((Common.Offer)localObject2)) {}
      for (String str2 = ((Common.Offer)localObject2).formattedFullAmount;; str2 = null)
      {
        addAction(paramInt, str1, str2, bool, 0, paramOfferFilter1, paramDocument, paramAccount);
        return i;
      }
    }
    
    public final void addDisambiguationAction(int paramInt, Common.Offer paramOffer, boolean paramBoolean, Document paramDocument, Account paramAccount)
    {
      String str1 = paramOffer.formattedAmount;
      if (PurchaseButtonHelper.shouldAddFullText(paramOffer)) {}
      for (String str2 = paramOffer.formattedFullAmount;; str2 = null)
      {
        addAction(paramInt, str1, str2, paramBoolean, 0, null, paramDocument, paramAccount);
        return;
      }
    }
    
    public final void addOfferAction(int paramInt, Common.Offer paramOffer, Document paramDocument, Account paramAccount)
    {
      String str1 = paramOffer.formattedAmount;
      if (PurchaseButtonHelper.shouldAddFullText(paramOffer)) {}
      for (String str2 = paramOffer.formattedFullAmount;; str2 = null)
      {
        addAction(paramInt, str1, str2, false, paramOffer.offerType, null, paramDocument, paramAccount);
        return;
      }
    }
    
    public final PurchaseButtonHelper.DocumentAction getAction(int paramInt)
    {
      if (paramInt < this.actionCount) {
        return this.actions[paramInt];
      }
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = Integer.valueOf(this.actionCount);
      FinskyLog.wtf("Request for invalid action #%d (%d available actions)", arrayOfObject);
      return null;
    }
    
    public final boolean hasAction()
    {
      return this.actionCount > 0;
    }
    
    public final boolean hasStatus()
    {
      return this.status != 0;
    }
    
    public final void reset()
    {
      this.displayAsOwned = false;
      this.status = 0;
      this.backend = -1;
      this.actionCount = 0;
      this.listingOfferText = null;
      this.listingOfferFullText = null;
      for (int i = 0; i < 2; i++) {
        this.actions[i].reset();
      }
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      for (int i = 0; i < this.actionCount; i++)
      {
        if (i != 0) {
          localStringBuilder.append(',');
        }
        localStringBuilder.append(this.actions[i].actionType);
      }
      return localStringBuilder.toString();
    }
  }
  
  public static final class TextStyle
  {
    public long appBytes;
    public String offerFullText = null;
    public String offerText = null;
    public int resourceId = -1;
    public int usage;
    
    public TextStyle() {}
    
    public TextStyle(TextStyle paramTextStyle)
    {
      this.resourceId = paramTextStyle.resourceId;
      this.offerText = paramTextStyle.offerText;
      this.offerFullText = paramTextStyle.offerFullText;
      this.appBytes = paramTextStyle.appBytes;
      this.usage = paramTextStyle.usage;
    }
    
    public final String getString(Context paramContext)
    {
      Resources localResources = paramContext.getResources();
      if (this.appBytes > 0L) {}
      for (String str = Formatter.formatShortFileSize(paramContext, this.appBytes); this.resourceId == -1; str = null) {
        return this.offerText;
      }
      if ((this.offerText == null) && (str == null)) {
        return localResources.getString(this.resourceId);
      }
      if (this.offerText == null) {
        return localResources.getString(this.resourceId, new Object[] { str });
      }
      if (str == null)
      {
        int j = this.resourceId;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.offerText;
        return localResources.getString(j, arrayOfObject2);
      }
      int i = this.resourceId;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = this.offerText;
      arrayOfObject1[1] = str;
      return localResources.getString(i, arrayOfObject1);
    }
    
    public final void reset()
    {
      this.resourceId = -1;
      this.offerText = null;
      this.offerFullText = null;
      this.appBytes = 0L;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PurchaseButtonHelper
 * JD-Core Version:    0.7.0.1
 */