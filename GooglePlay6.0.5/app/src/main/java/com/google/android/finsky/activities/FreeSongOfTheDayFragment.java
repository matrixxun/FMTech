package com.google.android.finsky.activities;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.FreeSongOfTheDayAlbumView;
import com.google.android.finsky.layout.FreeSongOfTheDaySummary;
import com.google.android.finsky.layout.FreeSongOfTheDaySummary.1;
import com.google.android.finsky.layout.FreeSongOfTheDaySummary.2;
import com.google.android.finsky.layout.SongIndex;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.previews.PreviewController;
import com.google.android.finsky.protos.DealOfTheDay;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.play.layout.PlayActionButton;

public final class FreeSongOfTheDayFragment
  extends DetailsDataBasedFragment
{
  public static FreeSongOfTheDayFragment newInstance(Document paramDocument, String paramString)
  {
    FreeSongOfTheDayFragment localFreeSongOfTheDayFragment = new FreeSongOfTheDayFragment();
    localFreeSongOfTheDayFragment.setDfeTocAndUrl(FinskyApp.get().mToc, paramString);
    localFreeSongOfTheDayFragment.setArgument("finsky.DetailsDataBasedFragment.document", paramDocument);
    return localFreeSongOfTheDayFragment;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968773;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 8;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mPageFragmentHost.getActionBarController().disableActionBarOverlay();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(null);
    this.mPageFragmentHost.switchToRegularActionBar();
    if (this.mDocument != null) {
      this.mPageFragmentHost.updateCurrentBackendId(this.mDocument.mDocument.backendId, false);
    }
  }
  
  protected final void rebindViews$79e5e33f()
  {
    rebindActionBar();
    Document localDocument1 = this.mDocument;
    Document localDocument2 = localDocument1.getChildAt(0);
    DealOfTheDay localDealOfTheDay = localDocument1.getTemplate().dealOfTheDay;
    View localView = this.mView;
    TextView localTextView = (TextView)localView.findViewById(2131755487);
    localTextView.setText(localDealOfTheDay.featuredHeader.toUpperCase());
    localTextView.setTextColor(CorpusResourceUtils.getSecondaryTextColor(this.mContext, localDocument1.mDocument.backendId));
    localView.findViewById(2131755564).setBackgroundColor(CorpusResourceUtils.getPrimaryColor(this.mContext, localDocument1.mDocument.backendId));
    FreeSongOfTheDaySummary localFreeSongOfTheDaySummary = (FreeSongOfTheDaySummary)localView.findViewById(2131755565);
    NavigationManager localNavigationManager = this.mNavigationManager;
    localFreeSongOfTheDaySummary.mTitle.setText(localDocument2.mDocument.title);
    localFreeSongOfTheDaySummary.mCreator.setText(localDocument2.mDocument.creator);
    Account localAccount1 = FinskyApp.get().getCurrentAccount();
    Account localAccount2 = LibraryUtils.getOwnerWithCurrentAccount(localDocument2, FinskyApp.get().mLibraries, localAccount1);
    if (localAccount2 != null) {
      localFreeSongOfTheDaySummary.mBuyButton.configure(localDocument2.mDocument.backendId, 2131362305, new FreeSongOfTheDaySummary.1(localFreeSongOfTheDaySummary, this, localNavigationManager, localAccount2, localDocument2));
    }
    for (;;)
    {
      localFreeSongOfTheDaySummary.mSongIndex.setState(5);
      localFreeSongOfTheDaySummary.mPlaybackLegend.setText(2131362802);
      localFreeSongOfTheDaySummary.setOnClickListener(new FreeSongOfTheDaySummary.2(localFreeSongOfTheDaySummary, localDocument2.getSongDetails()));
      PreviewController.setupOnBackStackChangedListener(localNavigationManager);
      ((TextView)localView.findViewById(2131755211)).setText(localDocument1.getDescription());
      FreeSongOfTheDayAlbumView localFreeSongOfTheDayAlbumView = (FreeSongOfTheDayAlbumView)localView.findViewById(2131755566);
      if (localFreeSongOfTheDayAlbumView != null)
      {
        localFreeSongOfTheDayAlbumView.init(this.mDfeApi, this.mNavigationManager, this.mBitmapLoader);
        String str2 = localDocument2.mDocument.detailsUrl;
        localFreeSongOfTheDayAlbumView.mHeader.setText(2131362346);
        localFreeSongOfTheDayAlbumView.mParentDoc = localDocument1;
        localFreeSongOfTheDayAlbumView.mUrl = str2;
        localFreeSongOfTheDayAlbumView.mParentNode = this;
        localFreeSongOfTheDayAlbumView.setupView();
      }
      return;
      String str1 = localDocument2.getFormattedPrice$47921032();
      if (!TextUtils.isEmpty(str1)) {
        localFreeSongOfTheDaySummary.mBuyButton.configure(localDocument2.mDocument.backendId, str1, localNavigationManager.getBuyImmediateClickListener(localAccount1, localDocument2, 1, null, null, 223, null));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FreeSongOfTheDayFragment
 * JD-Core Version:    0.7.0.1
 */