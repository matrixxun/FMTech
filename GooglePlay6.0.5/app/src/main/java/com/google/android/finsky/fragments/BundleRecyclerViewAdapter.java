package com.google.android.finsky.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MultiInstallActivity;
import com.google.android.finsky.adapters.CardRecyclerViewAdapter;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.layout.play.PlayBundleContainerView;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.List;

public final class BundleRecyclerViewAdapter<T extends ContainerList<?>>
  extends CardRecyclerViewAdapter<T>
{
  final Document mContainerDocument;
  public int mInstallerEvent;
  public String mInstallerEventPackageName;
  
  public BundleRecyclerViewAdapter(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, Document paramDocument, MultiDfeList paramMultiDfeList, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramContext, paramDfeApi, paramNavigationManager, paramBitmapLoader, paramDfeToc, paramClientMutationCache, paramMultiDfeList, null, null, paramBoolean, false, 2, paramPlayStoreUiElementNode, null);
    this.mContainerDocument = paramDocument;
    FinskyApp.get().mInstaller.addListener(new InstallerListener()
    {
      public final void onInstallPackageEvent(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        BundleRecyclerViewAdapter.this.mInstallerEvent = paramAnonymousInt1;
        BundleRecyclerViewAdapter.this.mInstallerEventPackageName = paramAnonymousString;
        BundleRecyclerViewAdapter.this.mObservable.notifyChanged();
      }
    });
  }
  
  public final void configureBackgroundView(HeroGraphicView paramHeroGraphicView, int paramInt)
  {
    paramHeroGraphicView.bindGeneric$3dce7526(this.mContainerDocument);
  }
  
  public final int getBackgroundViewSpacerHeight()
  {
    Context localContext = this.mContext;
    int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
    if (HeroGraphicView.getHeroGraphic(this.mContainerDocument) != null) {}
    for (boolean bool = true;; bool = false)
    {
      int j = HeroGraphicView.getSpacerHeight(localContext, i, bool, HeroGraphicView.getHeroAspectRatio(this.mContainerDocument.mDocument.docType));
      if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
        j -= UiUtils.getStatusBarHeight(this.mContext);
      }
      return j;
    }
  }
  
  protected final int getDataRowsCount()
  {
    return 1;
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 21;
    }
    int i = paramInt - 1;
    if ((hasExtraLeadingSpacer()) && (i == 0)) {
      return 22;
    }
    return 40;
  }
  
  public final int getPrependedRowsCount()
  {
    return 0;
  }
  
  public final boolean hasBackgroundView()
  {
    return true;
  }
  
  protected final boolean hasLeadingSpacer()
  {
    return true;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    int i = paramViewHolder.mItemViewType;
    View localView = paramViewHolder.itemView;
    switch (i)
    {
    default: 
      super.onBindViewHolder(paramViewHolder, paramInt);
      return;
    }
    PlayBundleContainerView localPlayBundleContainerView = (PlayBundleContainerView)localView;
    View.OnClickListener local2 = new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        ArrayList localArrayList = new ArrayList();
        AppStates localAppStates = FinskyApp.get().mAppStates;
        Document[] arrayOfDocument = BundleRecyclerViewAdapter.this.mContainerDocument.getChildren();
        int i = arrayOfDocument.length;
        int j = 0;
        if (j < i)
        {
          Document localDocument = arrayOfDocument[j];
          if (localDocument.mDocument.backendId == 3)
          {
            AppStates.AppState localAppState = localAppStates.getApp(localDocument.mDocument.docid);
            if ((localAppState == null) || (localAppState.packageManagerState == null) || (localAppState.packageManagerState.installedVersion == -1)) {
              break label123;
            }
          }
          label123:
          for (int k = 1;; k = 0)
          {
            if (k == 0) {
              localArrayList.add(localDocument);
            }
            j++;
            break;
          }
        }
        FinskyApp.get().getEventLogger().logClickEvent(1236, null, this.val$clusterUiElementNode);
        Intent localIntent = MultiInstallActivity.getInstallIntent(BundleRecyclerViewAdapter.this.mContext, localArrayList, FinskyApp.get().getCurrentAccountName());
        BundleRecyclerViewAdapter.this.mContext.startActivity(localIntent);
      }
    };
    localPlayBundleContainerView.configureExtraContent(this.mBitmapLoader, this.mNavigationManager, this.mContainerDocument, local2, this.mParentNode, this.mInstallerEvent, this.mInstallerEventPackageName);
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return super.onCreateViewHolder(paramViewGroup, paramInt);
    }
    return new PlayRecyclerView.ViewHolder(inflate(2130968912, paramViewGroup, false));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.BundleRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */