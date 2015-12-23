package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.WishlistPlayActionButton;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.play.layout.PlayActionButton;

public final class DetailsSummaryMoviesViewBinder
  extends DetailsSummaryViewBinder
{
  public DetailsSummaryMoviesViewBinder(DfeToc paramDfeToc, Account paramAccount)
  {
    super(paramDfeToc, paramAccount);
  }
  
  protected final boolean displayActionButtonsIfNecessary(PlayActionButton paramPlayActionButton1, PlayActionButton paramPlayActionButton2, PlayActionButton paramPlayActionButton3, PlayActionButton paramPlayActionButton4, PlayActionButton paramPlayActionButton5, WishlistPlayActionButton paramWishlistPlayActionButton)
  {
    return displayActionButtonsIfNecessaryNew(paramPlayActionButton1, paramPlayActionButton2, paramPlayActionButton3, paramPlayActionButton4, paramPlayActionButton5, paramWishlistPlayActionButton);
  }
  
  protected final View.OnClickListener getDownloadClickListener(final Document paramDocument, final Account paramAccount)
  {
    new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        DetailsSummaryMoviesViewBinder.this.mEventLogger.logClickEvent(224, null, DetailsSummaryMoviesViewBinder.this.mParentNode);
        if (!IntentUtils.isConsumptionAppInstalled(DetailsSummaryMoviesViewBinder.this.mContext.getPackageManager(), paramDocument.mDocument.backendId))
        {
          DetailsSummaryMoviesViewBinder.this.mNavigationManager.showAppNeededDialog(paramDocument.mDocument.backendId);
          return;
        }
        Intent localIntent = IntentUtils.buildConsumptionAppManageItemIntent(DetailsSummaryMoviesViewBinder.this.mContext, paramDocument, paramAccount.name);
        DetailsSummaryMoviesViewBinder.this.mContext.startActivity(localIntent);
      }
    };
  }
  
  protected final View.OnClickListener getViewBundleClickListener$1f5226f(final Document paramDocument)
  {
    new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        DetailsSummaryMoviesViewBinder.this.mEventLogger.logClickEvent(2703, null, DetailsSummaryMoviesViewBinder.this.mParentNode);
        AccountLibrary localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(DetailsSummaryMoviesViewBinder.this.mDetailsAccount);
        Common.Docid localDocid = LibraryUtils.getBundlePreorderForMovie(paramDocument, localAccountLibrary);
        if (localDocid != null)
        {
          String str = DfeUtils.createDetailsUrlFromId(DocUtils.getMovieDocid(localDocid.backendDocid));
          DetailsSummaryMoviesViewBinder.this.mNavigationManager.goToDocPage(str);
        }
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsSummaryMoviesViewBinder
 * JD-Core Version:    0.7.0.1
 */