package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.WishlistPlayActionButton;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.Common.SubscriptionContentTerms;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.utils.config.GservicesValue;

public final class DetailsSummaryMusicViewBinder
  extends DetailsSummaryViewBinder
{
  public DetailsSummaryMusicViewBinder(DfeToc paramDfeToc, Account paramAccount)
  {
    super(paramDfeToc, paramAccount);
  }
  
  protected final boolean displayActionButtonsIfNecessary(PlayActionButton paramPlayActionButton1, PlayActionButton paramPlayActionButton2, PlayActionButton paramPlayActionButton3, PlayActionButton paramPlayActionButton4, PlayActionButton paramPlayActionButton5, WishlistPlayActionButton paramWishlistPlayActionButton)
  {
    boolean bool1 = super.displayActionButtonsIfNecessary(paramPlayActionButton1, paramPlayActionButton2, paramPlayActionButton3, paramPlayActionButton4, paramPlayActionButton5, paramWishlistPlayActionButton);
    boolean bool2 = FinskyApp.get().getExperiments().isEnabled(87L);
    boolean bool3 = FinskyApp.get().getExperiments().isEnabled(12602761L);
    if ((!bool2) && (!bool3)) {
      return bool1;
    }
    Common.Offer localOffer = this.mDoc.getOffer(11);
    int i;
    if ((localOffer == null) || (localOffer.subscriptionContentTerms == null)) {
      i = 0;
    }
    while (i == 0)
    {
      return bool1;
      Common.Docid localDocid = localOffer.subscriptionContentTerms.requiredSubscription;
      int n = localDocid.backend;
      String str1 = AccountLibrary.getLibraryIdFromBackend(n);
      String str2 = localDocid.backendDocid;
      if (!new LibraryEntry(LibraryEntry.UNKNOWN_ACCOUNT, str1, n, str2, localDocid.type, 1).equals(LibraryUtils.getMusicAllAccessLibraryEntry())) {
        i = 0;
      } else {
        i = 1;
      }
    }
    Libraries localLibraries = FinskyApp.get().mLibraries;
    final Account localAccount = FinskyApp.get().getCurrentAccount();
    boolean bool4 = localLibraries.getAccountLibrary(localAccount).contains(LibraryUtils.getMusicAllAccessLibraryEntry());
    int j;
    Resources localResources;
    LayoutInflater localLayoutInflater;
    ViewGroup localViewGroup1;
    ViewGroup localViewGroup2;
    View.OnClickListener local1;
    label324:
    int k;
    if (paramPlayActionButton2.getVisibility() != 0)
    {
      j = 1;
      localResources = this.mContext.getResources();
      localLayoutInflater = LayoutInflater.from(this.mContext);
      localViewGroup1 = (ViewGroup)findViewById(2131755409);
      localViewGroup2 = (ViewGroup)localViewGroup1.findViewById(2131755410);
      localViewGroup2.removeAllViews();
      if (bool4) {
        break label387;
      }
      paramPlayActionButton5.setVisibility(0);
      paramPlayActionButton5.setEnabled(true);
      bool1 = true;
      local1 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(286, null, DetailsSummaryMusicViewBinder.this.mParentNode);
          if (!IntentUtils.isMusicAppWithAllAccessFlowInstalled(DetailsSummaryMusicViewBinder.this.mContext.getPackageManager()))
          {
            DetailsSummaryMusicViewBinder.this.mNavigationManager.showAppNeededDialog(2);
            return;
          }
          Intent localIntent = IntentUtils.buildConsumptionAppUrlIntent$5928b7f1(2, (String)G.musicAllAccessSignUpIntentUrl.get(), localAccount.name);
          DetailsSummaryMusicViewBinder.this.mContext.startActivity(localIntent);
        }
      };
      if (!bool3) {
        break label351;
      }
      paramPlayActionButton5.configure(this.mDoc.mDocument.backendId, 2131361833, local1);
      addExtraLabelBottom(localLayoutInflater, localViewGroup2, localResources.getString(2131361834));
      k = 1;
      label327:
      if (k == 0) {
        break label461;
      }
    }
    label387:
    label461:
    for (int m = 0;; m = 8)
    {
      localViewGroup1.setVisibility(m);
      return bool1;
      j = 0;
      break;
      label351:
      paramPlayActionButton5.configure(this.mDoc.mDocument.backendId, 2131361832, local1);
      addExtraLabelBottom(localLayoutInflater, localViewGroup2, localResources.getString(2131361836));
      break label324;
      k = 0;
      if (j != 0) {
        break label327;
      }
      paramPlayActionButton5.setVisibility(0);
      paramPlayActionButton5.setEnabled(true);
      bool1 = true;
      View.OnClickListener local2 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(285, null, DetailsSummaryMusicViewBinder.this.mParentNode);
          int i = DetailsSummaryMusicViewBinder.this.mDoc.mDocument.backendId;
          if (!IntentUtils.isConsumptionAppInstalled(DetailsSummaryMusicViewBinder.this.mContext.getPackageManager(), i))
          {
            DetailsSummaryMusicViewBinder.this.mNavigationManager.showAppNeededDialog(i);
            return;
          }
          Intent localIntent = IntentUtils.buildConsumptionAppViewItemIntent(DetailsSummaryMusicViewBinder.this.mContext, DetailsSummaryMusicViewBinder.this.mDoc, localAccount.name);
          localIntent.addFlags(268435456);
          DetailsSummaryMusicViewBinder.this.mContext.startActivity(localIntent);
        }
      };
      paramPlayActionButton5.configure(this.mDoc.mDocument.backendId, 2131362305, local2);
      addExtraLabelBottom(localLayoutInflater, localViewGroup2, localResources.getString(2131361835));
      k = 1;
      break label327;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsSummaryMusicViewBinder
 * JD-Core Version:    0.7.0.1
 */