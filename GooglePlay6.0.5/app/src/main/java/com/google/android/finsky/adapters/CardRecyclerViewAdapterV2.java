package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SpacerHeightProvider;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfigurator;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayCardClusterViewV2;
import com.google.android.finsky.layout.play.PlayCardMerchClusterViewV2;
import com.google.android.finsky.layout.play.PlayCardPromoClusterBaseView;
import com.google.android.finsky.layout.play.PlayQuickLinksBannerViewV2;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.PlayYoutubeCardContentView;
import com.google.android.finsky.layout.play.TopChartsClusterContentView;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Containers.ContainerMetadata;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.layout.PlayCardViewBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CardRecyclerViewAdapterV2<T extends ContainerList<?>>
  extends CardRecyclerViewAdapter<T>
{
  private ClusterContentConfiguratorRepository mClusterConfiguratorRepository;
  private RecyclerView.RecycledViewPool mHeapRecycledPool;
  private final int mQuickLinksItemLayoutId;
  private Map<String, Bundle> mRowClusterScrollStateMap;
  
  public CardRecyclerViewAdapterV2(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, MultiDfeList<T> paramMultiDfeList, QuickLinkHelper.QuickLinkInfo[] paramArrayOfQuickLinkInfo, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode, SpacerHeightProvider paramSpacerHeightProvider)
  {
    super(paramContext, paramDfeApi, paramNavigationManager, paramBitmapLoader, paramDfeToc, paramClientMutationCache, paramMultiDfeList, paramArrayOfQuickLinkInfo, paramString, paramBoolean1, paramBoolean2, paramInt, paramPlayStoreUiElementNode, paramSpacerHeightProvider);
    if (FinskyApp.get().getExperiments().isEnabled(12603505L)) {}
    for (int i = 2130969011;; i = 2130969009)
    {
      this.mQuickLinksItemLayoutId = i;
      this.mRowClusterScrollStateMap = new HashMap();
      if (this.mIsUsingScrollableClusters) {
        this.mClusterConfiguratorRepository = new ClusterContentConfiguratorRepository(paramContext);
      }
      if (!paramBoolean1)
      {
        this.mItems.clear();
        syncItemEntries();
      }
      return;
    }
  }
  
  private static String getScrollStateKey(String paramString)
  {
    return "CardRecyclerViewAdapterV2.rowClusterScrollStatePrefix." + paramString;
  }
  
  private void initializeHeapRecycledPool(int paramInt)
  {
    if (this.mHeapRecycledPool == null) {
      this.mHeapRecycledPool = new RecyclerView.RecycledViewPool();
    }
    this.mHeapRecycledPool.setMaxRecycledViews(paramInt, 50);
  }
  
  protected final void bindAvatarCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindAvatarCluster(paramInt, paramViewHolder);
      return;
    }
    bindCluster(this.mMultiDfeList.getTopLevelItem(paramInt), paramViewHolder, 2130968919);
  }
  
  protected final void bindCluster(Document paramDocument, RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    initializeHeapRecycledPool(paramInt);
    PlayCardClusterViewV2 localPlayCardClusterViewV2 = (PlayCardClusterViewV2)paramViewHolder.itemView;
    localPlayCardClusterViewV2.setCardContentHorizontalPadding(this.mCardContentPadding);
    String str1 = paramDocument.mDocument.docid;
    DfeList localDfeList = this.mMultiDfeList.getSecondaryList(str1);
    boolean bool;
    label115:
    byte[] arrayOfByte;
    if (localDfeList == null)
    {
      String str3 = paramDocument.mDocument.containerMetadata.nextPageUrl;
      DfeApi localDfeApi = this.mDfeApi;
      ArrayList localArrayList = Lists.newArrayList(paramDocument.getChildren());
      if (!TextUtils.isEmpty(str3))
      {
        bool = true;
        localDfeList = new DfeList(localDfeApi, localArrayList, str3, bool);
        this.mMultiDfeList.setSecondaryList(str1, localDfeList);
        localDfeList.addDataChangedListener(localPlayCardClusterViewV2);
        GenericClusterContentBinder localGenericClusterContentBinder = new GenericClusterContentBinder(paramDocument, paramInt, localDfeList, localPlayCardClusterViewV2);
        Bundle localBundle = (Bundle)this.mRowClusterScrollStateMap.get(getScrollStateKey(localGenericClusterContentBinder.getChildContentSourceId()));
        ClusterContentConfigurator localClusterContentConfigurator = this.mClusterConfiguratorRepository.getClusterContentConfigurator(paramInt);
        int i = this.mColumnCount;
        RecyclerView.RecycledViewPool localRecycledViewPool = this.mHeapRecycledPool;
        PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
        if (paramDocument != null) {
          break label342;
        }
        arrayOfByte = null;
        label197:
        localPlayCardClusterViewV2.createContent(localGenericClusterContentBinder, localClusterContentConfigurator, i, localRecycledViewPool, localBundle, localPlayStoreUiElementNode, arrayOfByte);
        if (localPlayCardClusterViewV2.hasHeader()) {
          if (!paramDocument.hasContainerAnnotation()) {
            break label354;
          }
        }
      }
    }
    label342:
    label354:
    for (Common.Image localImage = paramDocument.mDocument.containerMetadata.leftIcon;; localImage = null)
    {
      View.OnClickListener localOnClickListener = getClusterClickListener(paramDocument, localPlayCardClusterViewV2.getPlayStoreUiElementNode());
      String str2 = getMoreResultsStringForCluster(this.mContext, paramDocument, paramDocument.getChildCount(), localOnClickListener);
      localPlayCardClusterViewV2.showHeader(paramDocument.mDocument.backendId, paramDocument.mDocument.title, paramDocument.mDocument.subtitle, str2, localOnClickListener, this.mCardContentPadding, localImage, this.mBitmapLoader);
      localPlayCardClusterViewV2.setIdentifier(paramDocument.mDocument.docid);
      return;
      bool = false;
      break;
      localDfeList.setInitialDocs(paramDocument.getChildren());
      break label115;
      arrayOfByte = paramDocument.mDocument.serverLogsCookie;
      break label197;
    }
  }
  
  protected void bindGenericCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    int i = 1;
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindGenericCluster(paramInt, paramViewHolder);
      return;
    }
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    int j = getSignalStrengthForCluster(localDocument);
    int k;
    int m;
    if ((this.mUseMiniCards) && (FinskyApp.get().getExperiments().isEnabled(12604267L)))
    {
      k = i;
      if (k == 0) {
        break label99;
      }
      m = 2130968939;
      label68:
      if (j < 2) {
        break label107;
      }
    }
    for (;;)
    {
      if (i != 0) {
        m = 2130968935;
      }
      bindCluster(localDocument, paramViewHolder, m);
      return;
      k = 0;
      break;
      label99:
      m = 2130968954;
      break label68;
      label107:
      i = 0;
    }
  }
  
  protected final void bindHighlightsBanner$39fb0461(RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mIsUsingScrollableClusters) {
      return;
    }
    Document localDocument = this.mMultiDfeList.getTopLevelItem(0);
    initializeHeapRecycledPool(2130968990);
    PlayCardClusterViewV2 localPlayCardClusterViewV2 = (PlayCardClusterViewV2)paramViewHolder.itemView;
    localPlayCardClusterViewV2.setCardContentHorizontalPadding(this.mCardContentPadding);
    String str1 = localDocument.mDocument.docid;
    DfeList localDfeList = this.mMultiDfeList.getSecondaryList(str1);
    boolean bool;
    label132:
    HighlightsContentBinder localHighlightsContentBinder;
    Bundle localBundle;
    ClusterContentConfigurator localClusterContentConfigurator;
    int i;
    RecyclerView.RecycledViewPool localRecycledViewPool;
    PlayStoreUiElementNode localPlayStoreUiElementNode;
    if (localDfeList == null)
    {
      String str2 = localDocument.mDocument.containerMetadata.nextPageUrl;
      DfeApi localDfeApi = this.mDfeApi;
      ArrayList localArrayList = Lists.newArrayList(localDocument.getChildren());
      if (!TextUtils.isEmpty(str2))
      {
        bool = true;
        localDfeList = new DfeList(localDfeApi, localArrayList, str2, bool);
        this.mMultiDfeList.setSecondaryList(str1, localDfeList);
        localDfeList.addDataChangedListener(localPlayCardClusterViewV2);
        localHighlightsContentBinder = new HighlightsContentBinder(this.mContext, this.mBitmapLoader, this.mNavigationManager, localDocument, localDfeList, localPlayCardClusterViewV2);
        localBundle = (Bundle)this.mRowClusterScrollStateMap.get(getScrollStateKey(localHighlightsContentBinder.getChildContentSourceId()));
        localClusterContentConfigurator = this.mClusterConfiguratorRepository.getClusterContentConfigurator(2130968990);
        i = this.mColumnCount;
        localRecycledViewPool = this.mHeapRecycledPool;
        localPlayStoreUiElementNode = this.mParentNode;
        if (localDocument != null) {
          break label272;
        }
      }
    }
    label272:
    for (byte[] arrayOfByte = null;; arrayOfByte = localDocument.mDocument.serverLogsCookie)
    {
      localPlayCardClusterViewV2.createContent(localHighlightsContentBinder, localClusterContentConfigurator, i, localRecycledViewPool, localBundle, localPlayStoreUiElementNode, arrayOfByte);
      localPlayCardClusterViewV2.setIdentifier(localDocument.mDocument.docid);
      return;
      bool = false;
      break;
      localDfeList.setInitialDocs(localDocument.getChildren());
      break label132;
    }
  }
  
  protected final void bindMerchCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindMerchCluster(paramInt, paramViewHolder);
      return;
    }
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    bindCluster(localDocument, paramViewHolder, 2130968954);
    PlayCardMerchClusterViewV2 localPlayCardMerchClusterViewV2 = (PlayCardMerchClusterViewV2)paramViewHolder.itemView;
    BitmapLoader localBitmapLoader = this.mBitmapLoader;
    Common.Image localImage = (Common.Image)localDocument.getImages(14).get(0);
    String str1 = localDocument.mDocument.title;
    View.OnClickListener localOnClickListener = getClusterClickListener(localDocument, localPlayCardMerchClusterViewV2.getPlayStoreUiElementNode());
    localPlayCardMerchClusterViewV2.mMerchColor = UiUtils.getFillColor(localImage, localPlayCardMerchClusterViewV2.mFallbackMerchColor);
    localPlayCardMerchClusterViewV2.mMerchImage.setVisibility(0);
    localPlayCardMerchClusterViewV2.mMerchImage.setOnLoadedListener(localPlayCardMerchClusterViewV2);
    localPlayCardMerchClusterViewV2.mMerchImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
    boolean bool;
    label180:
    FadingEdgeImageView localFadingEdgeImageView2;
    if (localPlayCardMerchClusterViewV2.mMerchImage.getDrawable() != null)
    {
      localPlayCardMerchClusterViewV2.configureImageFadingEdge();
      localPlayCardMerchClusterViewV2.mMerchImage.setOnClickListener(localOnClickListener);
      FadingEdgeImageView localFadingEdgeImageView1 = localPlayCardMerchClusterViewV2.mMerchImage;
      if (localOnClickListener == null) {
        break label244;
      }
      bool = true;
      localFadingEdgeImageView1.setClickable(bool);
      localFadingEdgeImageView2 = localPlayCardMerchClusterViewV2.mMerchImage;
      if (localOnClickListener == null) {
        break label250;
      }
    }
    label244:
    label250:
    for (String str2 = str1;; str2 = null)
    {
      localFadingEdgeImageView2.setContentDescription(str2);
      localPlayCardMerchClusterViewV2.mMerchFill.setVisibility(0);
      localPlayCardMerchClusterViewV2.mMerchFill.setBackgroundColor(localPlayCardMerchClusterViewV2.mMerchColor);
      return;
      localPlayCardMerchClusterViewV2.mMerchImage.clearFadingEdges();
      break;
      bool = false;
      break label180;
    }
  }
  
  protected void bindOrderedCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindOrderedCluster(paramInt, paramViewHolder);
      return;
    }
    bindGenericCluster(paramInt, paramViewHolder);
  }
  
  protected final void bindQuicklinksBanner(View paramView)
  {
    if (!this.mH2oIsEnabled)
    {
      super.bindQuicklinksBanner(paramView);
      return;
    }
    ((PlayQuickLinksBannerViewV2)paramView).bindContent(this.mBitmapLoader, this.mQuickLinks, this.mNavigationManager, this.mToc, this.mParentNode);
  }
  
  protected final void bindTopChartsCluster(int paramInt, View paramView)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindTopChartsCluster(paramInt, paramView);
      return;
    }
    PlayCardPromoClusterBaseView localPlayCardPromoClusterBaseView = (PlayCardPromoClusterBaseView)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    int i = CorpusResourceUtils.getPrimaryColor(this.mContext, localDocument.mDocument.backendId);
    localPlayCardPromoClusterBaseView.bindData$5dbdb9dd(localDocument, this.mBitmapLoader, this.mNavigationManager, this.mParentNode, getSingleCardClusterDismissListener(paramView), 426, 2130837700, Integer.valueOf(i), false);
    localPlayCardPromoClusterBaseView.setContentHorizontalPadding(this.mCardContentPadding);
    ((TopChartsClusterContentView)localPlayCardPromoClusterBaseView.findViewById(2131756161)).bind(localDocument, this.mBitmapLoader, this.mDfeApi, this.mToc, this.mNavigationManager, localPlayCardPromoClusterBaseView.getParentOfChildren());
  }
  
  protected final void bindYoutubeCardCluster(int paramInt, View paramView)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindYoutubeCardCluster(paramInt, paramView);
      return;
    }
    PlayCardPromoClusterBaseView localPlayCardPromoClusterBaseView = (PlayCardPromoClusterBaseView)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    int i = this.mContext.getResources().getColor(2131689685);
    localPlayCardPromoClusterBaseView.bindData$5dbdb9dd(localDocument, this.mBitmapLoader, this.mNavigationManager, this.mParentNode, getSingleCardClusterDismissListener(paramView), 427, 2130837701, Integer.valueOf(i), false);
    localPlayCardPromoClusterBaseView.setContentHorizontalPadding(this.mCardContentPadding);
    ((PlayYoutubeCardContentView)localPlayCardPromoClusterBaseView.findViewById(2131755970)).bind(localDocument, this.mBitmapLoader, localPlayCardPromoClusterBaseView.getParentOfChildren());
  }
  
  protected final View createAvatarCluster(ViewGroup paramViewGroup)
  {
    if (!this.mIsUsingScrollableClusters) {
      return super.createAvatarCluster(paramViewGroup);
    }
    return inflate(2130968927, paramViewGroup, false);
  }
  
  protected final View createGenericCluster(ViewGroup paramViewGroup)
  {
    if (!this.mIsUsingScrollableClusters) {
      return super.createGenericCluster(paramViewGroup);
    }
    return inflate(2130968927, paramViewGroup, false);
  }
  
  protected final View createMerchCluster(ViewGroup paramViewGroup)
  {
    if (!this.mIsUsingScrollableClusters) {
      return super.createMerchCluster(paramViewGroup);
    }
    return inflate(2130968938, paramViewGroup, false);
  }
  
  protected final View createOrderedCluster(ViewGroup paramViewGroup)
  {
    if (!this.mIsUsingScrollableClusters) {
      return super.createOrderedCluster(paramViewGroup);
    }
    return createGenericCluster(paramViewGroup);
  }
  
  public final void onRestoreInstanceState(PlayRecyclerView paramPlayRecyclerView, Bundle paramBundle)
  {
    this.mRowClusterScrollStateMap.clear();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("CardRecyclerViewAdapterV2.rowClusterScrollStatePrefix.")) {
        this.mRowClusterScrollStateMap.put(str, paramBundle.getBundle(str));
      }
    }
    super.onRestoreInstanceState(paramPlayRecyclerView, paramBundle);
  }
  
  public final void onSaveInstanceState(PlayRecyclerView paramPlayRecyclerView, Bundle paramBundle)
  {
    super.onSaveInstanceState(paramPlayRecyclerView, paramBundle);
    Iterator localIterator1 = this.mActiveViewHolders.iterator();
    while (localIterator1.hasNext())
    {
      View localView = ((RecyclerView.ViewHolder)localIterator1.next()).itemView;
      if ((localView instanceof PlayCardClusterViewV2))
      {
        PlayCardClusterViewV2 localPlayCardClusterViewV2 = (PlayCardClusterViewV2)localView;
        Bundle localBundle = new Bundle();
        localPlayCardClusterViewV2.onSaveInstanceState(localBundle);
        this.mRowClusterScrollStateMap.put(getScrollStateKey(localPlayCardClusterViewV2.getContentId()), localBundle);
      }
    }
    Iterator localIterator2 = this.mRowClusterScrollStateMap.entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator2.next();
      paramBundle.putBundle((String)localEntry.getKey(), (Bundle)localEntry.getValue());
    }
  }
  
  public final void onViewRecycled(RecyclerView.ViewHolder paramViewHolder)
  {
    View localView = paramViewHolder.itemView;
    PlayCardClusterViewV2 localPlayCardClusterViewV2;
    String str1;
    String str2;
    Bundle localBundle;
    if ((localView instanceof PlayCardClusterViewV2))
    {
      localPlayCardClusterViewV2 = (PlayCardClusterViewV2)localView;
      str1 = localPlayCardClusterViewV2.getContentId();
      str2 = getScrollStateKey(str1);
      localBundle = (Bundle)this.mRowClusterScrollStateMap.get(str2);
      if (localBundle == null) {
        break label104;
      }
      localBundle.clear();
    }
    for (;;)
    {
      localPlayCardClusterViewV2.onSaveInstanceState(localBundle);
      this.mRowClusterScrollStateMap.put(str2, localBundle);
      DfeList localDfeList = this.mMultiDfeList.getSecondaryList(str1);
      if (localDfeList != null) {
        localDfeList.removeDataChangedListener(localPlayCardClusterViewV2);
      }
      super.onViewRecycled(paramViewHolder);
      return;
      label104:
      localBundle = new Bundle();
    }
  }
  
  private final class GenericClusterContentBinder
    implements ClusterContentBinder<PlayCardViewBase>
  {
    private final int mCardLayoutId;
    private final PlayCardClusterViewV2 mCluster;
    private final Document mClusterDoc;
    private final DfeList mSourceList;
    
    public GenericClusterContentBinder(Document paramDocument, int paramInt, DfeList paramDfeList, PlayCardClusterViewV2 paramPlayCardClusterViewV2)
    {
      this.mClusterDoc = paramDocument;
      this.mCardLayoutId = paramInt;
      this.mSourceList = paramDfeList;
      this.mCluster = paramPlayCardClusterViewV2;
    }
    
    public final int getAvailableChildCount()
    {
      return this.mSourceList.getCount();
    }
    
    public final String getChildContentSourceId()
    {
      return this.mClusterDoc.mDocument.docid;
    }
    
    public final float getChildCoverAspectRatio(int paramInt)
    {
      Document localDocument = (Document)this.mSourceList.getItem(paramInt, false);
      if (localDocument == null) {
        return -1.0F;
      }
      return PlayCardClusterMetadata.getAspectRatio(localDocument.mDocument.docType);
    }
    
    public final int getChildLayoutId$134621()
    {
      return this.mCardLayoutId;
    }
    
    public final boolean isMoreDataAvailable()
    {
      return this.mSourceList.mMoreAvailable;
    }
    
    public final BitmapLoader.BitmapContainer preloadChildCoverImage(int paramInt1, int paramInt2, int paramInt3, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler, int[] paramArrayOfInt)
    {
      Document localDocument = (Document)this.mSourceList.getItem(paramInt1, false);
      return ThumbnailUtils.preloadCoverImage(CardRecyclerViewAdapterV2.this.mContext, localDocument, CardRecyclerViewAdapterV2.this.mBitmapLoader, paramInt2, paramInt3, paramBitmapLoadedHandler, paramArrayOfInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.CardRecyclerViewAdapterV2
 * JD-Core Version:    0.7.0.1
 */