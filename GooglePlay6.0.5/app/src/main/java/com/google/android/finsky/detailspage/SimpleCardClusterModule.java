package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.layout.PlayCardViewBase;

public abstract class SimpleCardClusterModule
  extends SimpleDfeListModule<Data>
  implements View.OnClickListener, ClusterContentBinder<PlayCardViewBase>, PlayStoreUiElementNode
{
  protected boolean mShouldUseScrollableClusters;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(400);
  
  public SimpleCardClusterModule()
  {
    FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
    if ((localFinskyExperiments.isH20StoreEnabled()) && (!localFinskyExperiments.isEnabled(12604101L))) {}
    for (boolean bool = true;; bool = false)
    {
      this.mShouldUseScrollableClusters = bool;
      return;
    }
  }
  
  public final void bindView(View paramView)
  {
    Document localDocument = ((Data)this.mModuleData).dfeList.mContainerDocument;
    if (!TextUtils.isEmpty(((Data)this.mModuleData).sectionMetadata.header)) {}
    for (String str1 = ((Data)this.mModuleData).sectionMetadata.header; (paramView instanceof CardClusterModuleLayout); str1 = localDocument.mDocument.title)
    {
      CardClusterModuleLayout localCardClusterModuleLayout = (CardClusterModuleLayout)paramView;
      String str3 = UiUtils.getMoreResultsStringForCluster(this.mContext, localDocument, localCardClusterModuleLayout.getMaxItemsInLayout(false), this, ((Data)this.mModuleData).sectionMetadata.browseUrl, true);
      int k = ((Data)this.mModuleData).dfeList.getBackendId();
      boolean bool = ((Data)this.mModuleData).supportsTwoRows;
      localCardClusterModuleLayout.bind(this, k, str1, null, str3, bool, this);
      return;
    }
    CardClusterModuleLayoutV2 localCardClusterModuleLayoutV2 = (CardClusterModuleLayoutV2)paramView;
    int i = ((Data)this.mModuleData).dfeList.getBackendId();
    String str2 = UiUtils.getMoreResultsStringForCluster(this.mContext, localDocument, localCardClusterModuleLayoutV2.getMaxItemsPerPage(), this, ((Data)this.mModuleData).sectionMetadata.browseUrl, true);
    int j = ((Data)this.mModuleData).cardLayoutResId;
    ClusterContentConfiguratorRepository localClusterContentConfiguratorRepository = this.mClusterConfiguratorRepository;
    RecyclerView.RecycledViewPool localRecycledViewPool = this.mRecycledViewPool;
    Bundle localBundle = ((Data)this.mModuleData).clusterState;
    PlayStoreUiElementNode localPlayStoreUiElementNode = getParentNode();
    byte[] arrayOfByte = localDocument.mDocument.serverLogsCookie;
    localCardClusterModuleLayoutV2.bind(i, str1, str2, this, j, this, localClusterContentConfiguratorRepository, localRecycledViewPool, localBundle, localPlayStoreUiElementNode, arrayOfByte);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getAvailableChildCount()
  {
    return ((Data)this.mModuleData).dfeList.getCount();
  }
  
  protected int getCardLayoutResId()
  {
    if ((this.mContext.getResources().getBoolean(2131427339)) && (FinskyApp.get().getExperiments().isEnabled(12604267L))) {}
    for (int i = 1; i != 0; i = 0) {
      return 2130968939;
    }
    return 2130968954;
  }
  
  public final String getChildContentSourceId()
  {
    return ((Data)this.mModuleData).dfeList.mInitialListUrl;
  }
  
  public final float getChildCoverAspectRatio(int paramInt)
  {
    DfeList localDfeList = ((Data)this.mModuleData).dfeList;
    if (paramInt < localDfeList.getCount()) {}
    for (Document localDocument = (Document)localDfeList.getItem(paramInt); localDocument != null; localDocument = null) {
      return PlayCardClusterMetadata.getAspectRatio(localDocument.mDocument.docType);
    }
    return 0.0F;
  }
  
  public final int getChildLayoutId$134621()
  {
    return ((Data)this.mModuleData).cardLayoutResId;
  }
  
  public int getLayoutResId()
  {
    if (this.mShouldUseScrollableClusters) {
      return 2130968654;
    }
    return 2130968652;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  protected abstract SectionMetadata getSectionMetadata(Document paramDocument);
  
  public final boolean isMoreDataAvailable()
  {
    return false;
  }
  
  public void onClick(View paramView)
  {
    if (!readyForDisplay())
    {
      FinskyLog.wtf("Module is not ready to handle click", new Object[0]);
      return;
    }
    this.mNavigationManager.goBrowse(((Data)this.mModuleData).sectionMetadata.browseUrl, null, ((Data)this.mModuleData).dfeList.getBackendId(), this.mDfeToc, this);
  }
  
  public final void onDataChanged()
  {
    if (((Data)this.mModuleData).dfeList.getCount() != 0) {
      this.mFinskyModuleController.refreshModule(this, false);
    }
    Document localDocument = ((Data)this.mModuleData).dfeList.mContainerDocument;
    if ((localDocument != null) && (localDocument.mDocument.serverLogsCookie != null)) {
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, localDocument.mDocument.serverLogsCookie);
    }
  }
  
  public final void onRecycleView(View paramView)
  {
    CardClusterModuleLayoutV2 localCardClusterModuleLayoutV2;
    if ((paramView instanceof CardClusterModuleLayoutV2))
    {
      localCardClusterModuleLayoutV2 = (CardClusterModuleLayoutV2)paramView;
      if (localCardClusterModuleLayoutV2 != null)
      {
        if (((Data)this.mModuleData).clusterState != null) {
          break label61;
        }
        ((Data)this.mModuleData).clusterState = new Bundle();
      }
    }
    for (;;)
    {
      localCardClusterModuleLayoutV2.onSaveInstanceState(((Data)this.mModuleData).clusterState);
      return;
      label61:
      ((Data)this.mModuleData).clusterState.clear();
    }
  }
  
  public final BitmapLoader.BitmapContainer preloadChildCoverImage(int paramInt1, int paramInt2, int paramInt3, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler, int[] paramArrayOfInt)
  {
    DfeList localDfeList = ((Data)this.mModuleData).dfeList;
    if (paramInt1 < localDfeList.getCount()) {}
    for (Document localDocument = (Document)localDfeList.getItem(paramInt1);; localDocument = null) {
      return ThumbnailUtils.preloadCoverImage(this.mContext, localDocument, this.mBitmapLoader, paramInt2, paramInt3, paramBitmapLoadedHandler, paramArrayOfInt);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return (super.readyForDisplay()) && (((Data)this.mModuleData).dfeList.getCount() != 0);
  }
  
  protected boolean supportsTwoRows()
  {
    return false;
  }
  
  protected static final class Data
    extends SimpleDfeListModule.Data
  {
    int cardLayoutResId = -1;
    Bundle clusterState;
    SectionMetadata sectionMetadata;
    boolean supportsTwoRows;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SimpleCardClusterModule
 * JD-Core Version:    0.7.0.1
 */