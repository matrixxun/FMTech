package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Image.Dimension;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MoreByCreatorContainer;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayCardViewBase;
import java.util.List;

public final class DynamicCardClusterModule
  extends FinskyModule<Data>
  implements View.OnClickListener, OnDataChangedListener, ClusterContentBinder<PlayCardViewBase>, PlayStoreUiElementNode
{
  int mSectionMetadataIndex = -1;
  private boolean mShouldUseScrollableClusters;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(400);
  
  public DynamicCardClusterModule()
  {
    FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
    if ((localFinskyExperiments.isH20StoreEnabled()) && (!localFinskyExperiments.isEnabled(12604101L))) {}
    for (boolean bool = true;; bool = false)
    {
      this.mShouldUseScrollableClusters = bool;
      return;
    }
  }
  
  private void updateLayoutResId()
  {
    int i = 1;
    Document localDocument = ((Data)this.mModuleData).dfeList.mContainerDocument;
    if (localDocument.isMoreByCreatorContainer())
    {
      if ((localDocument.mDocument.backendId == i) && (!FinskyApp.get().getExperiments().isEnabled(12603140L)))
      {
        ((Data)this.mModuleData).layoutResId = 2130968673;
        return;
      }
      ((Data)this.mModuleData).layoutResId = 2130968674;
      return;
    }
    int j;
    if ((localDocument.getTemplate() != null) && (localDocument.getTemplate().artistClusterContainer != null)) {
      j = i;
    }
    while (j != 0) {
      if (this.mShouldUseScrollableClusters)
      {
        ((Data)this.mModuleData).layoutResId = 2130968654;
        ((Data)this.mModuleData).cardLayoutResId = 2130968918;
        return;
        j = 0;
      }
      else
      {
        ((Data)this.mModuleData).layoutResId = 2130968619;
        ((Data)this.mModuleData).supportsTwoRows = i;
        return;
      }
    }
    if ((localDocument.getTemplate() != null) && (localDocument.getTemplate().multiRowContainer != null)) {}
    for (int k = i; k != 0; k = 0)
    {
      ((Data)this.mModuleData).layoutResId = 2130968652;
      ((Data)this.mModuleData).supportsTwoRows = i;
      return;
    }
    int m;
    if ((localDocument.getTemplate() != null) && (localDocument.getTemplate().featuredAppsContainer != null)) {
      m = i;
    }
    while (m != 0) {
      if (HeroGraphicView.getHeroGraphic((Document)((Data)this.mModuleData).dfeList.getItem(0)) != null)
      {
        if (UiUtils.getDetailsCardColumnCount(this.mContext.getResources()) >= 4)
        {
          ((Data)this.mModuleData).layoutResId = 2130968748;
          return;
          m = 0;
        }
        else
        {
          ((Data)this.mModuleData).layoutResId = 2130968746;
        }
      }
      else
      {
        ((Data)this.mModuleData).layoutResId = 2130968652;
        return;
      }
    }
    if (localDocument.isAvatarCardContainer())
    {
      if (this.mShouldUseScrollableClusters)
      {
        ((Data)this.mModuleData).layoutResId = 2130968654;
        ((Data)this.mModuleData).cardLayoutResId = 2130968919;
        return;
      }
      ((Data)this.mModuleData).layoutResId = 2130968621;
      return;
    }
    DfeList localDfeList = ((Data)this.mModuleData).dfeList;
    int n;
    if ((localDfeList.getCount() > 0) && (((Document)localDfeList.getItem(0)).mDocument.docType == 44)) {
      n = i;
    }
    while (n != 0) {
      if (this.mShouldUseScrollableClusters)
      {
        ((Data)this.mModuleData).layoutResId = 2130968654;
        ((Data)this.mModuleData).cardLayoutResId = 2130968954;
        return;
        n = 0;
      }
      else
      {
        ((Data)this.mModuleData).layoutResId = 2130968645;
        return;
      }
    }
    if (this.mShouldUseScrollableClusters)
    {
      ((Data)this.mModuleData).layoutResId = 2130968654;
      Data localData;
      if ((this.mContext.getResources().getBoolean(2131427339)) && (FinskyApp.get().getExperiments().isEnabled(12604267L)))
      {
        localData = (Data)this.mModuleData;
        if (i == 0) {
          break label551;
        }
      }
      label551:
      for (int i1 = 2130968939;; i1 = 2130968954)
      {
        localData.cardLayoutResId = i1;
        return;
        i = 0;
        break;
      }
    }
    ((Data)this.mModuleData).layoutResId = 2130968652;
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (!paramBoolean) {}
    SectionMetadata[] arrayOfSectionMetadata;
    do
    {
      do
      {
        return;
      } while (this.mModuleData != null);
      arrayOfSectionMetadata = paramDocument1.getSectionMetaDataList();
    } while (arrayOfSectionMetadata == null);
    this.mModuleData = new Data();
    ((Data)this.mModuleData).sectionMetadata = arrayOfSectionMetadata[this.mSectionMetadataIndex];
    ((Data)this.mModuleData).dfeList = new DfeList(this.mDfeApi, ((Data)this.mModuleData).sectionMetadata.listUrl, false);
    ((Data)this.mModuleData).dfeList.addDataChangedListener(this);
    ((Data)this.mModuleData).dfeList.startLoadItems();
  }
  
  public final void bindView(View paramView)
  {
    CreatorAvatarCardClusterModuleLayoutV2 localCreatorAvatarCardClusterModuleLayoutV2;
    String str7;
    String str8;
    Document localDocument4;
    Common.Image localImage3;
    label210:
    Common.Image localImage6;
    switch (((Data)this.mModuleData).layoutResId)
    {
    default: 
      FinskyLog.wtf("Unrecognized layoutResId", new Object[0]);
      return;
    case 2130968674: 
      localCreatorAvatarCardClusterModuleLayoutV2 = (CreatorAvatarCardClusterModuleLayoutV2)paramView;
      if (!TextUtils.isEmpty(((Data)this.mModuleData).sectionMetadata.header))
      {
        str7 = ((Data)this.mModuleData).sectionMetadata.header;
        str8 = this.mContext.getString(2131362326);
        localDocument4 = new Document(((Data)this.mModuleData).dfeList.mContainerDocument.getMoreByCreatorContainer().creatorInformation);
        if (!localDocument4.hasImages(14)) {
          break label642;
        }
        localImage3 = (Common.Image)localDocument4.getImages(14).get(0);
        if (localImage3 != null) {
          break label1361;
        }
        if (!localDocument4.hasImages(2)) {
          break label648;
        }
        localImage6 = (Common.Image)localDocument4.getImages(2).get(0);
      }
      break;
    }
    label241:
    for (Common.Image localImage4 = localImage6;; localImage4 = localImage3)
    {
      Common.Image localImage5;
      int i1;
      if (localDocument4.hasImages(4))
      {
        localImage5 = (Common.Image)localDocument4.getImages(4).get(0);
        BitmapLoader localBitmapLoader = this.mBitmapLoader;
        int n = ((Data)this.mModuleData).dfeList.getCount();
        i1 = localDocument4.mDocument.docType;
        String str9 = localDocument4.mDocument.docid;
        View.OnClickListener localOnClickListener = this.mNavigationManager.getClickListener(localDocument4, this, null, -1, localCreatorAvatarCardClusterModuleLayoutV2);
        localCreatorAvatarCardClusterModuleLayoutV2.mNumOfShownItems = Math.min(-2 + localCreatorAvatarCardClusterModuleLayoutV2.mColumnCount, n);
        if (localImage4 == null) {
          break label673;
        }
        if ((localImage4.dimension == null) || (!localImage4.dimension.hasWidth) || (!localImage4.dimension.hasHeight)) {
          break label660;
        }
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverImageAspectRatio = (localImage4.dimension.width / localImage4.dimension.height);
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverImage.setImage(localImage4.imageUrl, localImage4.supportsFifeUrlOptions, localBitmapLoader);
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverImage.setVisibility(0);
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverImage.setColorFilter(ColorUtils.setAlphaComponent(localCreatorAvatarCardClusterModuleLayoutV2.mCoverImageFillColor, 115));
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverFill.setBackgroundColor(localCreatorAvatarCardClusterModuleLayoutV2.mCoverImageFillColor);
        localCreatorAvatarCardClusterModuleLayoutV2.mTitle.setText(str7);
        if (!TextUtils.isEmpty(str8)) {
          break label686;
        }
        localCreatorAvatarCardClusterModuleLayoutV2.mMoreView.setVisibility(8);
        if (localImage5 == null) {
          break label711;
        }
        localCreatorAvatarCardClusterModuleLayoutV2.mAvatarImage.setImage(localImage5.imageUrl, localImage5.supportsFifeUrlOptions, localBitmapLoader);
        localCreatorAvatarCardClusterModuleLayoutV2.mAvatarImage.setVisibility(0);
        if (NavigationManager.areTransitionsEnabled())
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("transition_generic_circle:");
          localStringBuilder.append(":");
          localStringBuilder.append(str9);
          localCreatorAvatarCardClusterModuleLayoutV2.setTransitionName(localStringBuilder.toString());
        }
        localCreatorAvatarCardClusterModuleLayoutV2.inflateCards();
        localCreatorAvatarCardClusterModuleLayoutV2.bindBucketRow(this);
        localCreatorAvatarCardClusterModuleLayoutV2.computeCardContentAspectRatio(this);
        localCreatorAvatarCardClusterModuleLayoutV2.setOnClickListener(localOnClickListener);
        if (localOnClickListener == null) {
          break label724;
        }
      }
      for (boolean bool2 = true;; bool2 = false)
      {
        localCreatorAvatarCardClusterModuleLayoutV2.setClickable(bool2);
        return;
        str7 = ((Data)this.mModuleData).dfeList.mContainerDocument.mDocument.title;
        break;
        localImage3 = null;
        break label210;
        localImage6 = null;
        break label241;
        localImage5 = null;
        break label271;
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverImageAspectRatio = HeroGraphicView.getHeroAspectRatio(i1);
        break label405;
        localCreatorAvatarCardClusterModuleLayoutV2.mCoverImage.setVisibility(8);
        break label434;
        localCreatorAvatarCardClusterModuleLayoutV2.mMoreView.setText(str8.toUpperCase());
        localCreatorAvatarCardClusterModuleLayoutV2.mMoreView.setVisibility(0);
        break label493;
        localCreatorAvatarCardClusterModuleLayoutV2.mAvatarImage.setVisibility(8);
        break label527;
      }
      CreatorAvatarCardClusterModuleLayout localCreatorAvatarCardClusterModuleLayout = (CreatorAvatarCardClusterModuleLayout)paramView;
      int m = ((Data)this.mModuleData).dfeList.getBackendId();
      String str5;
      String str6;
      Document localDocument3;
      Object localObject;
      Common.Image localImage2;
      label884:
      Common.Image localImage1;
      if (!TextUtils.isEmpty(((Data)this.mModuleData).sectionMetadata.header))
      {
        str5 = ((Data)this.mModuleData).sectionMetadata.header;
        str6 = this.mContext.getString(2131362137);
        localDocument3 = new Document(((Data)this.mModuleData).dfeList.mContainerDocument.getMoreByCreatorContainer().creatorInformation);
        if (!localDocument3.hasImages(14)) {
          break label997;
        }
        localObject = (Common.Image)localDocument3.getImages(14).get(0);
        if (localObject == null)
        {
          if (!localDocument3.hasImages(2)) {
            break label1003;
          }
          localImage2 = (Common.Image)localDocument3.getImages(2).get(0);
          localObject = localImage2;
        }
        if (!localDocument3.hasImages(4)) {
          break label1009;
        }
        localImage1 = (Common.Image)localDocument3.getImages(4).get(0);
        if (!localDocument3.hasCreatorBadges()) {
          break label1015;
        }
      }
      label1015:
      for (Badge localBadge = localDocument3.getFirstCreatorBadge();; localBadge = null)
      {
        localCreatorAvatarCardClusterModuleLayout.bind$3617df9(this, this.mBitmapLoader, m, str5, str6, ((Data)this.mModuleData).supportsTwoRows, localImage1, (Common.Image)localObject, localBadge, this, this.mNavigationManager.getClickListener(localDocument3, this));
        return;
        str5 = ((Data)this.mModuleData).dfeList.mContainerDocument.mDocument.title;
        break;
        localObject = null;
        break label853;
        localImage2 = null;
        break label884;
        localImage1 = null;
        break label914;
      }
      CardClusterModuleLayout localCardClusterModuleLayout = (CardClusterModuleLayout)paramView;
      int k = ((Data)this.mModuleData).dfeList.getBackendId();
      Document localDocument2 = ((Data)this.mModuleData).dfeList.mContainerDocument;
      if (!TextUtils.isEmpty(((Data)this.mModuleData).sectionMetadata.header)) {}
      for (String str3 = ((Data)this.mModuleData).sectionMetadata.header;; str3 = localDocument2.mDocument.title)
      {
        String str4 = UiUtils.getMoreResultsStringForCluster(this.mContext, localDocument2, localCardClusterModuleLayout.getMaxItemsInLayout(((Data)this.mModuleData).supportsTwoRows), this, ((Data)this.mModuleData).sectionMetadata.browseUrl, true);
        boolean bool1 = ((Data)this.mModuleData).supportsTwoRows;
        localCardClusterModuleLayout.bind(this, k, str3, null, str4, bool1, this);
        return;
      }
      CardClusterModuleLayoutV2 localCardClusterModuleLayoutV2 = (CardClusterModuleLayoutV2)paramView;
      int i = ((Data)this.mModuleData).dfeList.getBackendId();
      Document localDocument1 = ((Data)this.mModuleData).dfeList.mContainerDocument;
      if (!TextUtils.isEmpty(((Data)this.mModuleData).sectionMetadata.header)) {}
      for (String str1 = ((Data)this.mModuleData).sectionMetadata.header;; str1 = localDocument1.mDocument.title)
      {
        String str2 = UiUtils.getMoreResultsStringForCluster(this.mContext, localDocument1, localCardClusterModuleLayoutV2.getMaxItemsPerPage(), this, ((Data)this.mModuleData).sectionMetadata.browseUrl, true);
        int j = ((Data)this.mModuleData).cardLayoutResId;
        ClusterContentConfiguratorRepository localClusterContentConfiguratorRepository = this.mClusterConfiguratorRepository;
        RecyclerView.RecycledViewPool localRecycledViewPool = this.mRecycledViewPool;
        Bundle localBundle = ((Data)this.mModuleData).clusterState;
        PlayStoreUiElementNode localPlayStoreUiElementNode = getParentNode();
        byte[] arrayOfByte = localDocument1.mDocument.serverLogsCookie;
        localCardClusterModuleLayoutV2.bind(i, str1, str2, this, j, this, localClusterContentConfiguratorRepository, localRecycledViewPool, localBundle, localPlayStoreUiElementNode, arrayOfByte);
        return;
      }
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getAvailableChildCount()
  {
    return ((Data)this.mModuleData).dfeList.getCount();
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
  
  public final int getLayoutResId()
  {
    return ((Data)this.mModuleData).layoutResId;
  }
  
  public final PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final boolean isMoreDataAvailable()
  {
    return false;
  }
  
  public final void onClick(View paramView)
  {
    this.mNavigationManager.goBrowse(((Data)this.mModuleData).sectionMetadata.browseUrl, null, ((Data)this.mModuleData).dfeList.getBackendId(), this.mDfeToc, this);
  }
  
  public final void onDataChanged()
  {
    if (readyForDisplay())
    {
      Document localDocument = ((Data)this.mModuleData).dfeList.mContainerDocument;
      if (localDocument.mDocument.serverLogsCookie != null) {
        FinskyEventLog.setServerLogCookie(this.mUiElementProto, localDocument.mDocument.serverLogsCookie);
      }
      updateLayoutResId();
      this.mFinskyModuleController.refreshModule(this, false);
    }
  }
  
  public final void onDestroyModule()
  {
    if ((this.mModuleData != null) && (((Data)this.mModuleData).dfeList != null)) {
      ((Data)this.mModuleData).dfeList.removeDataChangedListener(this);
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
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if ((this.mModuleData != null) && (((Data)this.mModuleData).dfeList != null) && (!((Data)this.mModuleData).dfeList.isReady()))
    {
      ((Data)this.mModuleData).dfeList.addDataChangedListener(this);
      ((Data)this.mModuleData).dfeList.startLoadItems();
    }
    while (!readyForDisplay()) {
      return;
    }
    updateLayoutResId();
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
    if ((this.mModuleData == null) || (((Data)this.mModuleData).dfeList == null) || (!((Data)this.mModuleData).dfeList.isReady()) || (((Data)this.mModuleData).dfeList.mContainerDocument == null)) {
      return false;
    }
    return (((Data)this.mModuleData).dfeList.getCount() != 0) || (((Data)this.mModuleData).dfeList.mContainerDocument.isMoreByCreatorContainer());
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    int cardLayoutResId = -1;
    Bundle clusterState;
    DfeList dfeList;
    int layoutResId = -1;
    SectionMetadata sectionMetadata;
    boolean supportsTwoRows;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.DynamicCardClusterModule
 * JD-Core Version:    0.7.0.1
 */