package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.EmptyRecyclerViewAdapter;
import com.google.android.finsky.adapters.PeopleDetailsStreamAdapter;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarBackgroundUpdater;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocDetails.PersonDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.MyPeoplePageHelper;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public final class PeopleDetailsFragment
  extends DetailsDataBasedFragment
{
  private ActionBarBackgroundUpdater mActionBarBackgroundUpdater;
  private HeroGraphicView mBackgroundView;
  private FinskyHeaderListLayout mHeaderListLayout;
  private PeopleDetailsStreamViewBinder mStreamViewBinder = new PeopleDetailsStreamViewBinder();
  
  public static PeopleDetailsFragment newInstance(Document paramDocument, String paramString)
  {
    PeopleDetailsFragment localPeopleDetailsFragment = new PeopleDetailsFragment();
    localPeopleDetailsFragment.setDfeAccount(FinskyApp.get().getCurrentAccountName());
    localPeopleDetailsFragment.setDfeTocAndUrl(FinskyApp.get().mToc, paramString);
    localPeopleDetailsFragment.setArgument("finsky.DetailsDataBasedFragment.document", paramDocument);
    return localPeopleDetailsFragment;
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  protected final int getLayoutRes()
  {
    return 2130968781;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 4;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mStreamViewBinder.init(this.mContext, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, this.mDfeToc, FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName()), this);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContentFrame localContentFrame = (ContentFrame)super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mHeaderListLayout = ((FinskyHeaderListLayout)this.mDataView);
    this.mHeaderListLayout.configure(new FinskyHeaderListLayout.FinskyConfigurator(this.mHeaderListLayout.getContext())
    {
      protected final void addBackgroundView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        PeopleDetailsFragment.access$002(PeopleDetailsFragment.this, (HeroGraphicView)paramAnonymousLayoutInflater.inflate(2130968785, paramAnonymousViewGroup, false));
        PeopleDetailsFragment.this.mBackgroundView.setFocusable(false);
        paramAnonymousViewGroup.addView(PeopleDetailsFragment.this.mBackgroundView);
      }
      
      protected final void addContentView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousLayoutInflater.inflate(2130968901, paramAnonymousViewGroup);
      }
      
      protected final boolean allowImmersiveBackground()
      {
        return true;
      }
      
      protected final boolean alwaysUseFloatingBackground()
      {
        return false;
      }
      
      protected final float getBackgroundViewParallaxRatio()
      {
        return 0.8F;
      }
      
      protected final int getHeaderHeight()
      {
        Resources localResources = this.mContext.getResources();
        int i = HeroGraphicView.getSpacerHeight(this.mContext, localResources.getDisplayMetrics().widthPixels, true, 0.5625F);
        if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
          i -= UiUtils.getStatusBarHeight(this.mContext);
        }
        return i;
      }
      
      protected final int getListViewId()
      {
        return 2131755814;
      }
      
      protected final int getStatusBarOverlayColor()
      {
        return this.mContext.getResources().getColor(2131689730);
      }
      
      protected final int getStatusBarUnderlayColor()
      {
        return CorpusResourceUtils.getPrimaryColor(this.mContext, 9);
      }
      
      protected final int getToolbarTitleMode()
      {
        return 0;
      }
    });
    this.mHeaderListLayout.setContentViewId(2131755813);
    this.mHeaderListLayout.setBackgroundViewForTouchPassthrough(this.mBackgroundView);
    this.mHeaderListLayout.setShouldUseScrollLocking(false);
    this.mActionBarBackgroundUpdater = new ActionBarBackgroundUpdater(getActivity().getWindow(), this.mHeaderListLayout);
    this.mHeaderListLayout.setOnLayoutChangedListener(this.mActionBarBackgroundUpdater);
    this.mActionBarBackgroundUpdater.updateActionBar();
    return localContentFrame;
  }
  
  public final void onDestroyView()
  {
    PeopleDetailsStreamViewBinder localPeopleDetailsStreamViewBinder = this.mStreamViewBinder;
    if ((localPeopleDetailsStreamViewBinder.mRecyclerView != null) && (localPeopleDetailsStreamViewBinder.mRecyclerView.getVisibility() == 0) && (localPeopleDetailsStreamViewBinder.mAdapter != null)) {
      localPeopleDetailsStreamViewBinder.mAdapter.onSaveInstanceState(localPeopleDetailsStreamViewBinder.mRecyclerView, localPeopleDetailsStreamViewBinder.mRecyclerViewRestoreBundle);
    }
    localPeopleDetailsStreamViewBinder.mRecyclerView = null;
    if (localPeopleDetailsStreamViewBinder.mAdapter != null)
    {
      localPeopleDetailsStreamViewBinder.mAdapter.onDestroy();
      localPeopleDetailsStreamViewBinder.mAdapter = null;
      localPeopleDetailsStreamViewBinder.mIsAdapterSet = false;
    }
    if ((localPeopleDetailsStreamViewBinder.mDoc != null) && (localPeopleDetailsStreamViewBinder.mDfeList != null))
    {
      DocDetails.PersonDetails localPersonDetails = localPeopleDetailsStreamViewBinder.mDoc.getPersonDetails();
      int i = 0;
      if (localPersonDetails != null)
      {
        boolean bool = localPersonDetails.personIsRequester;
        i = 0;
        if (bool) {
          i = 1;
        }
      }
      if (i != 0) {
        MyPeoplePageHelper.addPeoplePageListUrls(localPeopleDetailsStreamViewBinder.mDfeList.getListPageUrls());
      }
    }
    localPeopleDetailsStreamViewBinder.mLayoutSwitcher = null;
    if (this.mHeaderListLayout != null) {
      this.mHeaderListLayout.detachIfNeeded();
    }
    if (this.mActionBarBackgroundUpdater != null)
    {
      this.mActionBarBackgroundUpdater.reset();
      this.mActionBarBackgroundUpdater = null;
    }
    super.onDestroyView();
  }
  
  public final void onEnterActionBarSearchMode()
  {
    this.mActionBarBackgroundUpdater.setSearchMode(true);
  }
  
  public final void onExitActionBarSearchMode()
  {
    this.mActionBarBackgroundUpdater.setSearchMode(false);
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mDocument.mDocument.title);
    this.mPageFragmentHost.updateCurrentBackendId(0, true);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews$79e5e33f()
  {
    rebindActionBar();
    this.mActionBarController.enableActionBarOverlay();
    PeopleDetailsStreamViewBinder localPeopleDetailsStreamViewBinder = this.mStreamViewBinder;
    localPeopleDetailsStreamViewBinder.mRecyclerView = ((PlayRecyclerView)this.mDataView.findViewById(2131755814));
    if (localPeopleDetailsStreamViewBinder.mRecyclerView.getLayoutManager() == null) {
      localPeopleDetailsStreamViewBinder.mRecyclerView.setLayoutManager(new LinearLayoutManager());
    }
    if (localPeopleDetailsStreamViewBinder.mRecyclerView.getAdapter() == null) {
      localPeopleDetailsStreamViewBinder.mRecyclerView.setAdapter(new EmptyRecyclerViewAdapter());
    }
    Document localDocument;
    HeroGraphicView localHeroGraphicView;
    BitmapLoader localBitmapLoader;
    Common.Image localImage;
    if (hasDetailsDataLoaded())
    {
      localDocument = this.mDocument;
      this.mStreamViewBinder.bind(this.mDataView, localDocument);
      if (this.mBackgroundView != null)
      {
        localHeroGraphicView = this.mBackgroundView;
        localBitmapLoader = FinskyApp.get().mBitmapLoader;
        localImage = (Common.Image)localDocument.getImages(15).get(0);
        if (localImage != null) {
          break label166;
        }
        localHeroGraphicView.mHeroImageView.setVisibility(8);
      }
    }
    return;
    label166:
    localHeroGraphicView.setCorpusFillMode(1);
    localHeroGraphicView.setCorpusForFill(localDocument.mDocument.backendId);
    localHeroGraphicView.mHeroImageView.setVisibility(0);
    localHeroGraphicView.mHeroImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
    localHeroGraphicView.configureDetailsAspectRatio(localImage, localDocument);
    localHeroGraphicView.mFullScreenModePeekAmount = (2 * FinskySearchToolbar.getToolbarHeight(localHeroGraphicView.getContext()));
    localHeroGraphicView.mIsInBackgroundLayer = true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.PeopleDetailsFragment
 * JD-Core Version:    0.7.0.1
 */