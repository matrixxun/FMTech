package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.NewsstandArticleHandler;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.activities.MultiInstallActivity;
import com.google.android.finsky.activities.SpacerHeightProvider;
import com.google.android.finsky.activities.TabbedBrowseFragment.BackgroundViewConfigurator;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.DocImageView;
import com.google.android.finsky.layout.FadingEdgeImageView;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.Identifiable;
import com.google.android.finsky.layout.play.PlayAvatarPack;
import com.google.android.finsky.layout.play.PlayBundleBannerView;
import com.google.android.finsky.layout.play.PlayCardActionBannerClusterRepository;
import com.google.android.finsky.layout.play.PlayCardActionBannerClusterView;
import com.google.android.finsky.layout.play.PlayCardActionBannerSingleCardClusterRepository;
import com.google.android.finsky.layout.play.PlayCardAvatarClusterRepository;
import com.google.android.finsky.layout.play.PlayCardBannerWithContentClusterView;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayCardClusterRepository;
import com.google.android.finsky.layout.play.PlayCardClusterView;
import com.google.android.finsky.layout.play.PlayCardClusterViewHeader;
import com.google.android.finsky.layout.play.PlayCardClusterWithNoticeRepository;
import com.google.android.finsky.layout.play.PlayCardClusterWithNoticeView;
import com.google.android.finsky.layout.play.PlayCardClusterWithNoticeView.OnNoticeShownListener;
import com.google.android.finsky.layout.play.PlayCardDismissListener;
import com.google.android.finsky.layout.play.PlayCardEmptyClusterView;
import com.google.android.finsky.layout.play.PlayCardFriendReviewClusterView;
import com.google.android.finsky.layout.play.PlayCardHeap;
import com.google.android.finsky.layout.play.PlayCardMerchClusterRepository;
import com.google.android.finsky.layout.play.PlayCardMerchClusterView;
import com.google.android.finsky.layout.play.PlayCardMiniClusterRepository;
import com.google.android.finsky.layout.play.PlayCardOrderedClusterView;
import com.google.android.finsky.layout.play.PlayCardPersonClusterRepository;
import com.google.android.finsky.layout.play.PlayCardPromoClusterBaseView;
import com.google.android.finsky.layout.play.PlayCardRateAndSuggestClusterRepository;
import com.google.android.finsky.layout.play.PlayCardRateAndSuggestClusterView;
import com.google.android.finsky.layout.play.PlayCardRateClusterRepository;
import com.google.android.finsky.layout.play.PlayCardRateClusterView;
import com.google.android.finsky.layout.play.PlayCardSingleCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayCardSingleCardClusterRepository;
import com.google.android.finsky.layout.play.PlayCardTrustedSourceClusterView;
import com.google.android.finsky.layout.play.PlayCardViewPerson;
import com.google.android.finsky.layout.play.PlayListView;
import com.google.android.finsky.layout.play.PlayMerchBannerView;
import com.google.android.finsky.layout.play.PlayNewsstandCardContentView;
import com.google.android.finsky.layout.play.PlayNewsstandCardContentView.1;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlaySocialHeader;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.PlayYoutubeCardContentView;
import com.google.android.finsky.layout.play.TopChartsClusterContentView;
import com.google.android.finsky.layout.play.WarmWelcomeCard;
import com.google.android.finsky.layout.play.WarmWelcomeV2Card;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.ActionBanner;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.BannerWithContentContainer;
import com.google.android.finsky.protos.BundleBanner;
import com.google.android.finsky.protos.CallToAction;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.ContainerWithBanner;
import com.google.android.finsky.protos.ContainerWithNotice;
import com.google.android.finsky.protos.Containers.ContainerMetadata;
import com.google.android.finsky.protos.Containers.ContainerView;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.NewsArticleContainer;
import com.google.android.finsky.protos.NextBanner;
import com.google.android.finsky.protos.Reason;
import com.google.android.finsky.protos.RecommendationsContainerWithHeader;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.protos.TrustedSourceContainer;
import com.google.android.finsky.protos.WarmWelcome;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.UiUtils.ClusterFadeOutListener;
import com.google.android.finsky.utils.UserSettingsUtils;
import com.google.android.play.article.NewsstandArticleLoader;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CardRecyclerViewAdapter<T extends ContainerList<?>>
  extends FinskyRecyclerViewAdapter<T>
  implements TabbedBrowseFragment.BackgroundViewConfigurator, PlayCardDismissListener
{
  protected HashSet<RecyclerView.ViewHolder> mActiveViewHolders;
  public final BitmapLoader mBitmapLoader;
  protected final int mCardContentPadding;
  protected final PlayCardHeap mCardHeap;
  protected final ClientMutationCache mClientMutationCache;
  private UiUtils.ClusterFadeOutListener mClusterFadeOutListener;
  protected final int mColumnCount;
  private final String mContainerId;
  protected final DfeApi mDfeApi;
  private final int mExtraLeadingSpacerHeight;
  protected boolean mH2oIsEnabled;
  protected boolean mH2oStoreQuickLinksActive;
  private final boolean mHasBannerHeader;
  private boolean mHasFilters;
  private boolean mHasHighlights;
  private final boolean mHasPlainHeader;
  private boolean mHasSearchAd;
  private final boolean mHasSocialHeader;
  private boolean mIsOnTablet;
  private final boolean mIsOrdered;
  protected boolean mIsUsingScrollableClusters;
  public ArrayList<ItemEntry> mItems = new ArrayList();
  private final List<Document> mLooseDocsWithReasons;
  protected int mLooseItemCellId;
  protected int mLooseItemColCount;
  private final int mNumQuickLinkRows;
  private final int mNumQuickLinksPerRow;
  public final PlayStoreUiElementNode mParentNode;
  private final Map<Integer, PlayStoreUiElementNode> mPlayClusterUiElementNodeMap = new HashMap();
  private final int mQuickLinkCount;
  protected final QuickLinkHelper.QuickLinkInfo[] mQuickLinks;
  private final boolean mShowLooseItemReasons;
  private final SpacerHeightProvider mSpacerHeightProvider;
  private final int mTabMode;
  private final String mTitle;
  protected final DfeToc mToc;
  protected final boolean mUseMiniCards;
  private final boolean mUseTallTemplates;
  private final int mWarmWelcomeCardColumns;
  private final boolean mWarmWelcomeHideGraphic;
  
  public CardRecyclerViewAdapter(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, MultiDfeList<T> paramMultiDfeList, QuickLinkHelper.QuickLinkInfo[] paramArrayOfQuickLinkInfo, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode, SpacerHeightProvider paramSpacerHeightProvider)
  {
    super(paramContext, paramNavigationManager, paramMultiDfeList);
    FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
    this.mH2oIsEnabled = localFinskyExperiments.isH20StoreEnabled();
    boolean bool1;
    Resources localResources;
    boolean bool2;
    label167:
    int i;
    label217:
    int m;
    label260:
    boolean bool3;
    label298:
    ContainerList localContainerList;
    Document localDocument;
    boolean bool10;
    label345:
    boolean bool4;
    label353:
    int i1;
    label390:
    boolean bool5;
    label398:
    String str;
    label419:
    int n;
    label498:
    boolean bool6;
    label506:
    boolean bool7;
    label528:
    boolean bool9;
    if ((this.mH2oIsEnabled) && (!localFinskyExperiments.isEnabled(12604101L)))
    {
      bool1 = true;
      this.mIsUsingScrollableClusters = bool1;
      localResources = paramContext.getResources();
      this.mH2oStoreQuickLinksActive = FinskyApp.get().getExperiments().isEnabled(12603731L);
      this.mDfeApi = paramDfeApi;
      this.mBitmapLoader = paramBitmapLoader;
      this.mToc = paramDfeToc;
      this.mClientMutationCache = paramClientMutationCache;
      this.mTitle = paramString;
      this.mColumnCount = UiUtils.getFeaturedGridColumnCount(localResources, 1.0D);
      this.mCardHeap = new PlayCardHeap();
      if (localResources.getDisplayMetrics().heightPixels <= localResources.getDimensionPixelSize(2131493433)) {
        break label674;
      }
      bool2 = true;
      this.mUseTallTemplates = bool2;
      this.mUseMiniCards = localResources.getBoolean(2131427339);
      this.mSpacerHeightProvider = paramSpacerHeightProvider;
      this.mTabMode = paramInt;
      this.mCardContentPadding = UiUtils.getGridHorizontalPadding(paramContext.getResources());
      if (paramArrayOfQuickLinkInfo == null) {
        break label680;
      }
      i = paramArrayOfQuickLinkInfo.length;
      this.mQuickLinkCount = i;
      this.mNumQuickLinksPerRow = UiUtils.getStreamQuickLinkColumnCount(localResources, this.mQuickLinkCount, 0);
      this.mQuickLinks = paramArrayOfQuickLinkInfo;
      if (!this.mH2oIsEnabled) {
        break label692;
      }
      if (this.mQuickLinkCount != 0) {
        break label686;
      }
      m = 0;
      this.mNumQuickLinkRows = m;
      this.mWarmWelcomeCardColumns = localResources.getInteger(2131623942);
      if ((this.mWarmWelcomeCardColumns != 1) || (localResources.getBoolean(2131427343))) {
        break label719;
      }
      bool3 = true;
      this.mWarmWelcomeHideGraphic = bool3;
      this.mParentNode = paramPlayStoreUiElementNode;
      localContainerList = this.mMultiDfeList.mContainerList;
      localDocument = localContainerList.mContainerDocument;
      if (localDocument == null) {
        break label741;
      }
      if (localDocument.mDocument.containerMetadata != null) {
        break label725;
      }
      bool10 = false;
      if (!bool10) {
        break label741;
      }
      bool4 = true;
      this.mIsOrdered = bool4;
      if (localDocument == null) {
        break label753;
      }
      if ((!localDocument.hasContainerAnnotation()) || (localDocument.mDocument.containerMetadata.containerView.length <= 0)) {
        break label747;
      }
      i1 = 1;
      if (i1 == 0) {
        break label753;
      }
      bool5 = true;
      this.mHasFilters = bool5;
      if (localDocument == null) {
        break label759;
      }
      str = localDocument.mDocument.docid;
      this.mContainerId = str;
      this.mShowLooseItemReasons = paramBoolean2;
      computeLooseItemRowsValues(localResources);
      this.mLooseDocsWithReasons = new ArrayList();
      if (localDocument == null) {
        break label784;
      }
      if ((localDocument.mDocument.annotations == null) || (localDocument.mDocument.annotations.template == null) || (localDocument.mDocument.annotations.template.containerWithBanner == null)) {
        break label778;
      }
      n = 1;
      if (n == 0) {
        break label784;
      }
      bool6 = true;
      this.mHasBannerHeader = bool6;
      if ((localDocument == null) || (!localDocument.hasRecommendationsContainerWithHeaderTemplate())) {
        break label790;
      }
      bool7 = true;
      this.mHasSocialHeader = bool7;
      if (localDocument != null)
      {
        if ((!localContainerList.hasUnflushedItem(0)) || (!((Document)localContainerList.getItem(0, false)).hasHighlightsContainer())) {
          break label796;
        }
        bool9 = true;
        label567:
        this.mHasHighlights = bool9;
      }
      if ((this.mHasBannerHeader) || (this.mHasSocialHeader) || (TextUtils.isEmpty(this.mTitle))) {
        break label802;
      }
    }
    label778:
    label784:
    label790:
    label796:
    label802:
    for (boolean bool8 = true;; bool8 = false)
    {
      this.mHasPlainHeader = bool8;
      this.mIsOnTablet = this.mContext.getResources().getBoolean(2131427347);
      this.mExtraLeadingSpacerHeight = localResources.getDimensionPixelSize(2131492937);
      this.mActiveViewHolders = new HashSet();
      if (!paramBoolean1) {
        syncItemEntries();
      }
      this.mClusterFadeOutListener = new UiUtils.ClusterFadeOutListener()
      {
        public final void onClusterFadeOutFinish()
        {
          CardRecyclerViewAdapter.this.mItems.clear();
          CardRecyclerViewAdapter.this.onDataChanged();
        }
      };
      return;
      bool1 = false;
      break;
      label674:
      bool2 = false;
      break label167;
      label680:
      i = 0;
      break label217;
      label686:
      m = 1;
      break label260;
      label692:
      int j = this.mQuickLinkCount;
      int k = this.mNumQuickLinksPerRow;
      m = (-1 + (j + k)) / k;
      break label260;
      label719:
      bool3 = false;
      break label298;
      label725:
      bool10 = localDocument.mDocument.containerMetadata.ordered;
      break label345;
      label741:
      bool4 = false;
      break label353;
      label747:
      i1 = 0;
      break label390;
      label753:
      bool5 = false;
      break label398;
      label759:
      str = (String)localContainerList.getListPageUrls().get(0);
      break label419;
      n = 0;
      break label498;
      bool6 = false;
      break label506;
      bool7 = false;
      break label528;
      bool9 = false;
      break label567;
    }
  }
  
  private static int adjustOldFirstVisibleRow(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 + paramInt1) / paramInt2 > 0.5F) {}
    for (int i = 1; i != 0; i = 0) {
      return paramInt3;
    }
    return paramInt3 + 1;
  }
  
  private void bindBannerWithContentCluster(int paramInt, View paramView)
  {
    PlayCardBannerWithContentClusterView localPlayCardBannerWithContentClusterView = (PlayCardBannerWithContentClusterView)paramView;
    Document localDocument1 = this.mMultiDfeList.getTopLevelItem(paramInt);
    PlayCardClusterMetadata localPlayCardClusterMetadata = getGenericClusterMetadata(localDocument1, 0);
    localPlayCardBannerWithContentClusterView.showHeader(localDocument1.mDocument.backendId, localDocument1.mDocument.title, localDocument1.mDocument.subtitle, null, null, this.mCardContentPadding);
    DocV2[] arrayOfDocV2 = localDocument1.getBannerWithContentContainer().content;
    Document[] arrayOfDocument = new Document[arrayOfDocV2.length];
    for (int i = 0;; i++)
    {
      int j = arrayOfDocV2.length;
      if (i >= j) {
        break;
      }
      arrayOfDocument[i] = new Document(arrayOfDocV2[i]);
    }
    localPlayCardBannerWithContentClusterView.withLooseDocumentsData(Arrays.asList(arrayOfDocument), localDocument1.mDocument.docid).createContent(localPlayCardClusterMetadata, this.mClientMutationCache, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, getPlayCardDismissListener(), this.mCardHeap, this.mParentNode);
    localPlayCardBannerWithContentClusterView.setCardContentHorizontalPadding(this.mCardContentPadding);
    Document localDocument2 = localDocument1.getChildAt(0);
    View.OnClickListener localOnClickListener = getClusterClickListener(localDocument2, localPlayCardBannerWithContentClusterView.getPlayStoreUiElementNode());
    localPlayCardBannerWithContentClusterView.setIdentifier(localDocument2.mDocument.docid);
    localPlayCardBannerWithContentClusterView.configure(this.mBitmapLoader, (Common.Image)localDocument2.getImages(14).get(0), localDocument2.mDocument.title, localOnClickListener);
  }
  
  private void bindCluster(Document paramDocument, PlayCardClusterMetadata paramPlayCardClusterMetadata, PlayCardClusterView paramPlayCardClusterView, boolean paramBoolean)
  {
    paramPlayCardClusterView.withClusterDocumentData(paramDocument).createContent(paramPlayCardClusterMetadata, this.mClientMutationCache, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, getPlayCardDismissListener(), this.mCardHeap, this.mParentNode);
    if (paramBoolean) {}
    for (View.OnClickListener localOnClickListener = getClusterClickListener(paramDocument, paramPlayCardClusterView.getPlayStoreUiElementNode());; localOnClickListener = null)
    {
      if (paramPlayCardClusterView.hasHeader())
      {
        String str = getMoreResultsStringForCluster(this.mContext, paramDocument, paramPlayCardClusterMetadata.getTileCount(), localOnClickListener);
        paramPlayCardClusterView.showHeader(paramDocument.mDocument.backendId, paramDocument.mDocument.title, paramDocument.mDocument.subtitle, str, localOnClickListener, this.mCardContentPadding);
      }
      paramPlayCardClusterView.setCardContentHorizontalPadding(this.mCardContentPadding);
      paramPlayCardClusterView.setIdentifier(paramDocument.mDocument.docid);
      return;
    }
  }
  
  private void bindListViewClusterRowWithoutHeader(View paramView, ItemEntry paramItemEntry)
  {
    BucketRow localBucketRow = (BucketRow)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramItemEntry.mTrueStartIndex);
    localBucketRow.setIdentifier(localDocument.mDocument.docid);
    localBucketRow.setVisibility(0);
    int i = UiUtils.getRegularGridColumnCount(this.mContext.getResources());
    Object localObject = (PlayStoreUiElementNode)this.mPlayClusterUiElementNodeMap.get(Integer.valueOf(paramItemEntry.mTrueStartIndex));
    if (localObject == null)
    {
      localObject = new GenericUiElementNode(400, localDocument.mDocument.serverLogsCookie, null, this.mParentNode);
      this.mPlayClusterUiElementNodeMap.put(Integer.valueOf(paramItemEntry.mTrueStartIndex), localObject);
    }
    int j = 0;
    if (j < i)
    {
      PlayCardViewBase localPlayCardViewBase = (PlayCardViewBase)localBucketRow.getChildAt(j);
      int k = j + paramItemEntry.mStartIndexInContainerCluster;
      if (k < localDocument.getChildCount())
      {
        PlayCardUtils.bindCard(localPlayCardViewBase, localDocument.getChildAt(k), localDocument.mDocument.docid, this.mBitmapLoader, this.mNavigationManager, false, null, (PlayStoreUiElementNode)localObject, true, -1, false);
        localPlayCardViewBase.setVisibility(0);
      }
      for (;;)
      {
        j++;
        break;
        localPlayCardViewBase.setVisibility(4);
      }
    }
  }
  
  private void bindPersonCardCluster(int paramInt, View paramView, boolean paramBoolean)
  {
    PlayCardClusterView localPlayCardClusterView = (PlayCardClusterView)paramView;
    bindCluster(this.mMultiDfeList.getTopLevelItem(paramInt), PlayCardPersonClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates), localPlayCardClusterView, true);
    for (int i = 0; i < localPlayCardClusterView.getCardChildCount(); i++) {
      ((PlayCardViewPerson)localPlayCardClusterView.getCardChildAt(i)).showCirclesIcon(paramBoolean);
    }
  }
  
  private void bindRowOfLooseItemsWithReasons(int paramInt1, int paramInt2, View paramView)
  {
    PlayCardClusterView localPlayCardClusterView = (PlayCardClusterView)paramView;
    this.mLooseDocsWithReasons.clear();
    Object localObject = null;
    for (int i = paramInt1; i <= paramInt2; i++)
    {
      Document localDocument = this.mMultiDfeList.getTopLevelItem(i);
      if (localDocument != null)
      {
        if (localObject == null) {
          localObject = localDocument;
        }
        this.mLooseDocsWithReasons.add(localDocument);
      }
    }
    int j;
    String str;
    label100:
    int k;
    if (localObject != null)
    {
      j = localObject.mDocument.docType;
      if (localObject == null) {
        break label202;
      }
      str = localObject.mDocument.docid;
      localPlayCardClusterView.setIdentifier(str);
      if (j != 28) {
        break label210;
      }
      k = 1;
      label117:
      if (k == 0) {
        break label216;
      }
    }
    label202:
    label210:
    label216:
    for (PlayCardClusterMetadata localPlayCardClusterMetadata = PlayCardPersonClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates);; localPlayCardClusterMetadata = PlayCardClusterRepository.getMetadata(j, this.mColumnCount, this.mUseTallTemplates, 0))
    {
      localPlayCardClusterView.withLooseDocumentsData(this.mLooseDocsWithReasons, this.mContainerId).createContent(localPlayCardClusterMetadata, this.mClientMutationCache, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, getPlayCardDismissListener(), this.mCardHeap, this.mParentNode);
      localPlayCardClusterView.setCardContentHorizontalPadding(this.mCardContentPadding);
      localPlayCardClusterView.hideHeader();
      return;
      j = -1;
      break;
      str = "";
      break label100;
      k = 0;
      break label117;
    }
  }
  
  private void bindRowOfLooseItemsWithoutReasons(int paramInt1, int paramInt2, View paramView, boolean paramBoolean)
  {
    int i;
    BucketRow localBucketRow;
    Object localObject;
    int j;
    label27:
    int k;
    int m;
    label49:
    Document localDocument;
    label57:
    PlayCardViewBase localPlayCardViewBase;
    ContainerList localContainerList;
    if ((paramBoolean) && (!this.mIsOnTablet))
    {
      i = 1;
      localBucketRow = (BucketRow)paramView;
      localObject = null;
      j = 0;
      if (j >= i) {
        break label269;
      }
      k = paramInt1 + j;
      if (k <= paramInt2) {
        break label147;
      }
      m = 1;
      if (m == 0) {
        break label153;
      }
      localDocument = null;
      if ((TextUtils.isEmpty((CharSequence)localObject)) && (localDocument != null)) {
        localObject = localDocument.mDocument.docid;
      }
      localPlayCardViewBase = (PlayCardViewBase)localBucketRow.getChildAt(j);
      localContainerList = this.mMultiDfeList.mContainerList;
      if (localDocument != null) {
        break label175;
      }
      if ((m != 0) && (k >= localContainerList.getCount())) {
        break label167;
      }
      localPlayCardViewBase.setVisibility(0);
      localPlayCardViewBase.bindLoading();
    }
    label147:
    label153:
    label167:
    label175:
    do
    {
      for (;;)
      {
        j++;
        break label27;
        i = this.mLooseItemColCount;
        break;
        m = 0;
        break label49;
        localDocument = this.mMultiDfeList.getTopLevelItem(k);
        break label57;
        localPlayCardViewBase.clearCardState();
      }
      boolean bool1 = isDismissed(localDocument);
      PlayCardUtils.bindCard(localPlayCardViewBase, localDocument, this.mContainerId, this.mBitmapLoader, this.mNavigationManager, bool1, getPlayCardDismissListener(), this.mParentNode, true, getDisplayIndex(k), false);
    } while (!(localPlayCardViewBase instanceof PlayCardViewPerson));
    boolean bool2 = localContainerList.mContainerDocument.isMyCirclesContainer();
    PlayCardViewPerson localPlayCardViewPerson = (PlayCardViewPerson)localPlayCardViewBase;
    if (!bool2) {}
    for (boolean bool3 = true;; bool3 = false)
    {
      localPlayCardViewPerson.showCirclesIcon(bool3);
      break;
    }
    label269:
    localBucketRow.setIdentifier((String)localObject);
    configureContainerOfLooseItemsWithoutReasons(localBucketRow);
  }
  
  private void bindSplitContainerClusterHeaderView(View paramView, int paramInt)
  {
    ItemEntry localItemEntry1 = (ItemEntry)this.mItems.get(paramInt);
    int i = localItemEntry1.mTrueStartIndex;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(localItemEntry1.mTrueStartIndex);
    PlayCardClusterViewHeader localPlayCardClusterViewHeader = (PlayCardClusterViewHeader)paramView;
    Object localObject = (PlayStoreUiElementNode)this.mPlayClusterUiElementNodeMap.get(Integer.valueOf(localItemEntry1.mTrueStartIndex));
    if (localObject == null)
    {
      byte[] arrayOfByte = localDocument.mDocument.serverLogsCookie;
      PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
      localObject = new GenericUiElementNode(400, arrayOfByte, null, localPlayStoreUiElementNode);
      this.mPlayClusterUiElementNodeMap.put(Integer.valueOf(i), localObject);
    }
    View.OnClickListener localOnClickListener = getClusterClickListener(localDocument, (PlayStoreUiElementNode)localObject);
    int j = 0;
    for (int k = paramInt + 1; k < this.mItems.size(); k++)
    {
      ItemEntry localItemEntry2 = (ItemEntry)this.mItems.get(k);
      if (localItemEntry2.mTrueStartIndex != i) {
        break;
      }
      j += 1 + (localItemEntry2.mEndIndexInContainerCluster - localItemEntry2.mStartIndexInContainerCluster);
    }
    String str = getMoreResultsStringForCluster(this.mContext, localDocument, j, localOnClickListener);
    if ((this.mH2oIsEnabled) && (this.mIsUsingScrollableClusters) && (localDocument.hasContainerAnnotation())) {}
    for (Common.Image localImage = localDocument.mDocument.containerMetadata.leftIcon;; localImage = null)
    {
      localPlayCardClusterViewHeader.setContent(localDocument.mDocument.backendId, localDocument.mDocument.title, localDocument.mDocument.subtitle, str, localOnClickListener, localImage, this.mBitmapLoader);
      localPlayCardClusterViewHeader.setExtraHorizontalPadding(this.mCardContentPadding);
      localPlayCardClusterViewHeader.setIdentifier("container_cluster_header:" + this.mTitle);
      ViewCompat.setPaddingRelative(localPlayCardClusterViewHeader, ViewCompat.getPaddingStart(localPlayCardClusterViewHeader), localPlayCardClusterViewHeader.mTopPadding, ViewCompat.getPaddingEnd(localPlayCardClusterViewHeader), localPlayCardClusterViewHeader.getPaddingBottom());
      return;
    }
  }
  
  private void bindWarmWelcomeCardView(int paramInt, View paramView)
  {
    WarmWelcomeCard localWarmWelcomeCard = (WarmWelcomeCard)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    WarmWelcome localWarmWelcome;
    List localList;
    Common.Image localImage1;
    label59:
    Common.Image localImage2;
    label86:
    int i;
    label119:
    String str2;
    Common.Image localImage3;
    View.OnClickListener localOnClickListener;
    if (localDocument.isWarmWelcome())
    {
      localWarmWelcome = localDocument.getTemplate().warmWelcome;
      localList = localDocument.getImages(4);
      if ((localList != null) && (!localList.isEmpty())) {
        break label198;
      }
      localImage1 = null;
      String str1 = localDocument.mDocument.title;
      CharSequence localCharSequence = localDocument.getDescription();
      if (!this.mWarmWelcomeHideGraphic) {
        break label214;
      }
      localImage2 = null;
      localWarmWelcomeCard.configureContent(str1, localCharSequence, localImage2, localDocument.mDocument.backendId, this.mParentNode, localDocument.mDocument.serverLogsCookie);
      i = 0;
      if (i >= localWarmWelcome.action.length) {
        break label227;
      }
      CallToAction localCallToAction = localWarmWelcome.action[i];
      str2 = localCallToAction.buttonText;
      localImage3 = localCallToAction.buttonIcon;
      localOnClickListener = getWarmWelcomeActionCallback(localDocument, localCallToAction, localWarmWelcomeCard, localWarmWelcomeCard);
      if (i != 0) {
        break label221;
      }
    }
    label198:
    label214:
    label221:
    for (boolean bool = true;; bool = false)
    {
      localWarmWelcomeCard.configureActionButton(str2, localImage3, localOnClickListener, bool);
      i++;
      break label119;
      localWarmWelcome = null;
      break;
      localImage1 = (Common.Image)localList.get(0);
      break label59;
      localImage2 = localImage1;
      break label86;
    }
    label227:
    if (localWarmWelcome.action.length < 2) {
      localWarmWelcomeCard.hideSecondaryAction();
    }
    ViewCompat.setPaddingRelative(localWarmWelcomeCard, this.mCardContentPadding, 0, this.mCardContentPadding, 0);
    localWarmWelcomeCard.setIdentifier(localDocument.mDocument.docid);
  }
  
  private void bindWarmWelcomeV2CardView(int paramInt, View paramView)
  {
    WarmWelcomeV2Card localWarmWelcomeV2Card = (WarmWelcomeV2Card)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    WarmWelcome localWarmWelcome;
    List localList;
    Common.Image localImage;
    label59:
    int j;
    label135:
    int k;
    label147:
    label208:
    int m;
    label274:
    String str2;
    View.OnClickListener localOnClickListener;
    int n;
    if (localDocument.isWarmWelcomeV2())
    {
      localWarmWelcome = localDocument.getTemplate().warmWelcomeV2;
      localList = localDocument.getImages(4);
      if ((localList != null) && (!localList.isEmpty())) {
        break label376;
      }
      localImage = null;
      String str1 = localDocument.mDocument.title;
      CharSequence localCharSequence = localDocument.getDescription();
      int i = localDocument.mDocument.backendId;
      PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
      byte[] arrayOfByte = localDocument.mDocument.serverLogsCookie;
      localWarmWelcomeV2Card.mTitle.setText(str1);
      localWarmWelcomeV2Card.mBody.setText(localCharSequence);
      if ((i == 0) || (i == 9)) {
        break label392;
      }
      j = 1;
      if (j == 0) {
        break label398;
      }
      k = CorpusResourceUtils.getSecondaryColorResId(i);
      localWarmWelcomeV2Card.mCardLayout.getCardViewGroupDelegate().setBackgroundColor(localWarmWelcomeV2Card.mCardLayout, localWarmWelcomeV2Card.getResources().getColor(k));
      if (localImage == null) {
        break label406;
      }
      localWarmWelcomeV2Card.mGraphic.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      localWarmWelcomeV2Card.mGraphicBox.setVisibility(0);
      localWarmWelcomeV2Card.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(516);
      FinskyEventLog.setServerLogCookie(localWarmWelcomeV2Card.mUiElementProto, arrayOfByte);
      localWarmWelcomeV2Card.mParentNode = localPlayStoreUiElementNode;
      localWarmWelcomeV2Card.mPrimaryButton.setVisibility(8);
      localWarmWelcomeV2Card.mSecondaryButton.setVisibility(8);
      localWarmWelcomeV2Card.mButtonPanel.setVisibility(8);
      localWarmWelcomeV2Card.getParentNode().childImpression(localWarmWelcomeV2Card);
      m = 0;
      if (m >= localWarmWelcome.action.length) {
        break label433;
      }
      CallToAction localCallToAction = localWarmWelcome.action[m];
      str2 = localCallToAction.buttonText;
      localOnClickListener = getWarmWelcomeActionCallback(localDocument, localCallToAction, localWarmWelcomeV2Card, localWarmWelcomeV2Card);
      if (m != 0) {
        break label418;
      }
      n = 1;
      label322:
      if (n == 0) {
        break label424;
      }
    }
    label392:
    label398:
    label406:
    label418:
    label424:
    for (TextView localTextView = localWarmWelcomeV2Card.mPrimaryButton;; localTextView = localWarmWelcomeV2Card.mSecondaryButton)
    {
      localTextView.setVisibility(0);
      localTextView.setText(str2.toUpperCase());
      localTextView.setOnClickListener(localOnClickListener);
      localWarmWelcomeV2Card.mButtonPanel.setVisibility(0);
      m++;
      break label274;
      localWarmWelcome = null;
      break;
      label376:
      localImage = (Common.Image)localList.get(0);
      break label59;
      j = 0;
      break label135;
      k = 2131689727;
      break label147;
      localWarmWelcomeV2Card.mGraphicBox.setVisibility(8);
      break label208;
      n = 0;
      break label322;
    }
    label433:
    ViewCompat.setPaddingRelative(localWarmWelcomeV2Card, this.mCardContentPadding, 0, this.mCardContentPadding, 0);
    localWarmWelcomeV2Card.setIdentifier(localDocument.mDocument.docid);
  }
  
  private View createRowOfLooseItemsWithoutReasons(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.mIsOnTablet)) {}
    BucketRow localBucketRow;
    for (int i = 1;; i = this.mLooseItemColCount)
    {
      localBucketRow = (BucketRow)inflate(2130968646, paramViewGroup, false);
      localBucketRow.setContentHorizontalPadding(this.mCardContentPadding);
      for (int j = 0; j < i; j++) {
        localBucketRow.addView(inflate(this.mLooseItemCellId, localBucketRow, false));
      }
    }
    return localBucketRow;
  }
  
  private void endLastContainerClusterRowEntry(int paramInt)
  {
    int i = this.mItems.size();
    if (i > 0)
    {
      ItemEntry localItemEntry = (ItemEntry)this.mItems.get(i - 1);
      if (localItemEntry.isSplitContainerClusterNonHeaderRow()) {
        localItemEntry.mEndIndexInContainerCluster = (paramInt - 1);
      }
    }
  }
  
  private void endLastEntry(int paramInt)
  {
    int i = this.mItems.size();
    if (i > 0)
    {
      ItemEntry localItemEntry = (ItemEntry)this.mItems.get(i - 1);
      if (localItemEntry.isLooseItemRow()) {
        localItemEntry.mTrueEndIndex = (paramInt - 1);
      }
    }
  }
  
  private int getBaseDataRowsCount()
  {
    int i = this.mItems.size();
    if (getFooterMode() != 0) {
      i++;
    }
    return i;
  }
  
  private int getBasePrependedRowsCount()
  {
    int i = 1;
    int j = this.mNumQuickLinkRows;
    int k;
    int n;
    label31:
    int i2;
    label48:
    int i4;
    label65:
    int i6;
    label82:
    int i7;
    if (isShowingPlainHeader())
    {
      k = i;
      int m = j + k;
      if (!this.mHasBannerHeader) {
        break label113;
      }
      n = i;
      int i1 = m + n;
      if (!hasFilters()) {
        break label119;
      }
      i2 = i;
      int i3 = i1 + i2;
      if (!this.mHasSocialHeader) {
        break label125;
      }
      i4 = i;
      int i5 = i3 + i4;
      if (!shouldShowSecondaryHeader()) {
        break label131;
      }
      i6 = i;
      i7 = i6 + i5;
      if ((!this.mH2oStoreQuickLinksActive) || (!this.mHasHighlights)) {
        break label137;
      }
    }
    for (;;)
    {
      return i7 + i;
      k = 0;
      break;
      label113:
      n = 0;
      break label31;
      label119:
      i2 = 0;
      break label48;
      label125:
      i4 = 0;
      break label65;
      label131:
      i6 = 0;
      break label82;
      label137:
      i = 0;
    }
  }
  
  private static int getCardListAdapterViewTypeForDocument(Document paramDocument)
  {
    int i = 1;
    if (paramDocument == null) {}
    boolean bool;
    do
    {
      return 6;
      if ((paramDocument.getTemplate() != null) && (paramDocument.getTemplate().nextBanner != null)) {}
      for (int j = i; j != 0; j = 0) {
        return 3;
      }
      if (paramDocument.hasDealOfTheDayInfo()) {
        return 5;
      }
      if (paramDocument.isRateCluster()) {
        return 12;
      }
      if (paramDocument.isRateAndSuggestCluster()) {
        return 13;
      }
      if ((paramDocument.getTemplate() != null) && (paramDocument.getTemplate().addToCirclesContainer != null)) {}
      for (int k = i; k != 0; k = 0) {
        return 10;
      }
      if (paramDocument.isMyCirclesContainer()) {
        return 19;
      }
      if (paramDocument.isAvatarCardContainer()) {
        return 26;
      }
      if ((paramDocument.getTemplate() != null) && (paramDocument.getTemplate().trustedSourceContainer != null) && (paramDocument.getTemplate().trustedSourceContainer.source != null)) {}
      for (int m = i; m != 0; m = 0) {
        return 11;
      }
      if ((paramDocument.getTemplate() != null) && (paramDocument.getTemplate().emptyContainer != null)) {}
      for (int n = i; n != 0; n = 0) {
        return 15;
      }
      if (paramDocument.isFriendReview()) {
        return 31;
      }
      if (paramDocument.isYoutubeVideoContainer()) {
        return 33;
      }
      if (paramDocument.isNewsArticleContainer()) {
        return 36;
      }
      if (paramDocument.isActionBanner()) {
        return 14;
      }
      if (paramDocument.isTopChartsContainer()) {
        return 34;
      }
      if (paramDocument.isWarmWelcome()) {
        return 18;
      }
      if (paramDocument.isWarmWelcomeV2()) {
        return 30;
      }
      if (paramDocument.isAdvertisement()) {
        return 23;
      }
      if (paramDocument.isBundleBanner()) {
        return 24;
      }
      if (paramDocument.getBannerWithContentContainer() != null) {}
      for (int i1 = i; i1 != 0; i1 = 0) {
        return 27;
      }
      if ((paramDocument.getTemplate() != null) && (paramDocument.getTemplate().containerWithNotice != null)) {}
      for (int i2 = i; i2 != 0; i2 = 0) {
        return 32;
      }
      if (paramDocument.hasHighlightsContainer()) {
        return 35;
      }
      Template localTemplate = paramDocument.getTemplate();
      if ((localTemplate != null) && (localTemplate.listViewContainer != null)) {}
      while (i != 0)
      {
        return 37;
        i = 0;
      }
      bool = paramDocument.hasContainerAnnotation();
      if (((bool) || (paramDocument.hasAntennaInfo())) && (paramDocument.hasImages(14))) {
        return 4;
      }
    } while (!bool);
    if (paramDocument.mDocument.containerMetadata.ordered) {
      return 9;
    }
    return 20;
  }
  
  private PlayCardClusterMetadata getGenericClusterMetadata(Document paramDocument, int paramInt)
  {
    int i = 1;
    int j = paramDocument.mDocument.docType;
    if ((!this.mUseMiniCards) || (getSignalStrengthForCluster(paramDocument) != 0)) {
      i = 0;
    }
    label95:
    while (i != 0)
    {
      return PlayCardMiniClusterRepository.getMetadata(j, 1 + this.mColumnCount);
      if ((paramDocument.getChildCount() > 0) && ((PlayCardUtils.findHighestPriorityReason(paramDocument.getChildAt(0).getSuggestionReasons()) != null) || (!TextUtils.isEmpty(paramDocument.getChildAt(0).getSnippetHtml())))) {}
      for (int k = i;; k = 0)
      {
        if (k == 0) {
          break label95;
        }
        i = 0;
        break;
      }
    }
    return PlayCardClusterRepository.getMetadata(j, this.mColumnCount, this.mUseTallTemplates, paramInt);
  }
  
  protected static String getMoreResultsStringForCluster(Context paramContext, Document paramDocument, int paramInt, View.OnClickListener paramOnClickListener)
  {
    return UiUtils.getMoreResultsStringForCluster(paramContext, paramDocument, paramInt, paramOnClickListener, null, false);
  }
  
  protected static int getSignalStrengthForCluster(Document paramDocument)
  {
    if (paramDocument.getChildCount() < 2) {
      FinskyLog.w("Not enough children in cluster.", new Object[0]);
    }
    Reason localReason1;
    do
    {
      Reason localReason2;
      do
      {
        return 0;
        Document localDocument1 = paramDocument.getChildAt(0);
        Document localDocument2 = paramDocument.getChildAt(1);
        localReason1 = PlayCardUtils.findHighestPriorityReason(localDocument1.getSuggestionReasons());
        localReason2 = PlayCardUtils.findHighestPriorityReason(localDocument2.getSuggestionReasons());
      } while (localReason1 == null);
      if (localReason1.reasonReview != null)
      {
        if ((localReason2 != null) && (localReason2.reasonReview != null)) {
          return 4;
        }
        return 2;
      }
    } while (localReason1.reasonPlusOne == null);
    return 3;
  }
  
  private int getSpacersCount()
  {
    int i = 1;
    int j;
    if (hasLeadingSpacer())
    {
      j = i;
      if (!hasExtraLeadingSpacer()) {
        break label27;
      }
    }
    for (;;)
    {
      return j + i;
      j = 0;
      break;
      label27:
      i = 0;
    }
  }
  
  private View.OnClickListener getWarmWelcomeActionCallback(final Document paramDocument, final CallToAction paramCallToAction, final View paramView, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        int i = 1;
        FinskyEventLog localFinskyEventLog;
        if (paramCallToAction.type == i)
        {
          localFinskyEventLog = FinskyApp.get().getEventLogger();
          if (i == 0) {
            break label99;
          }
        }
        label99:
        for (int j = 1231;; j = 1232)
        {
          localFinskyEventLog.logClickEvent(j, null, paramPlayStoreUiElementNode);
          CardRecyclerViewAdapter.this.mNavigationManager.resolveCallToAction(paramCallToAction, CardRecyclerViewAdapter.this.mDfeApi, CardRecyclerViewAdapter.this.mToc, FinskyApp.get().getPackageManager());
          if (i != 0) {
            CardRecyclerViewAdapter.access$000(CardRecyclerViewAdapter.this, paramDocument, paramView);
          }
          return;
          i = 0;
          break;
        }
      }
    };
  }
  
  public static boolean hasRestoreData(Bundle paramBundle)
  {
    return (paramBundle != null) && (paramBundle.containsKey("CardRecyclerViewAdapter.itemEntriesList"));
  }
  
  private static boolean isRateClusterDismissed(ClientMutationCache paramClientMutationCache, Document paramDocument, boolean paramBoolean)
  {
    DocV2 localDocV21 = paramDocument.mDocument;
    int i;
    if (paramBoolean) {
      i = ((Integer)G.positiveRateThreshold.get()).intValue();
    }
    for (int j = 0;; j++)
    {
      if (j >= localDocV21.child.length) {
        break label107;
      }
      DocV2 localDocV22 = localDocV21.child[j];
      if ((localDocV22 != null) && (!paramClientMutationCache.isDismissedRecommendation(localDocV22.docid)))
      {
        Review localReview = paramClientMutationCache.getCachedReview(localDocV22.docid, null);
        if ((localReview == null) || ((paramBoolean) && (localReview.starRating >= i)))
        {
          return false;
          i = 0;
          break;
        }
      }
    }
    label107:
    return true;
  }
  
  private boolean isShowingPlainHeader()
  {
    if (!this.mHasPlainHeader) {}
    int i;
    do
    {
      return false;
      if (this.mMultiDfeList.mContainerList.getCount() > 0) {
        return true;
      }
      i = getFooterMode();
    } while (((shouldHidePlainHeaderDuringInitialLoading()) && (i == 1)) || ((shouldHidePlainHeaderOnEmpty()) && (i == 0)));
    return true;
  }
  
  private static boolean shouldSplitCluster(int paramInt)
  {
    return paramInt == 37;
  }
  
  private void splitContainerClusterAndAddItemEntries(int paramInt1, int paramInt2, Document paramDocument)
  {
    switch (paramInt2)
    {
    default: 
      throw new UnsupportedOperationException("Splitting of this viewType is not supported");
    }
    int i = UiUtils.getRegularGridColumnCount(this.mContext.getResources());
    int j = paramDocument.getChildCount() / this.mColumnCount;
    ItemEntry localItemEntry1 = new ItemEntry();
    localItemEntry1.mTrueStartIndex = paramInt1;
    localItemEntry1.mTrueEndIndex = paramInt1;
    localItemEntry1.mViewType = 39;
    this.mItems.add(localItemEntry1);
    int k = 0;
    int m = paramDocument.getChildCount();
    Object localObject = null;
    for (int n = 0; (n < m) && (k < j); n++)
    {
      int i2 = 0;
      if (localObject != null)
      {
        boolean bool = localObject.isSplitContainerClusterNonHeaderRow();
        i2 = 0;
        if (bool)
        {
          int i3 = n - localObject.mStartIndexInContainerCluster;
          i2 = 0;
          if (i3 < i) {
            i2 = 1;
          }
        }
      }
      if (i2 == 0)
      {
        endLastContainerClusterRowEntry(n);
        ItemEntry localItemEntry2 = new ItemEntry();
        localItemEntry2.mTrueStartIndex = paramInt1;
        localItemEntry2.mTrueEndIndex = paramInt1;
        localItemEntry2.mViewType = 38;
        localItemEntry2.mStartIndexInContainerCluster = n;
        localObject = localItemEntry2;
        this.mItems.add(localItemEntry2);
        k++;
      }
    }
    int i1 = Math.min(k * i, paramDocument.getChildCount());
    if (m > 0) {
      endLastContainerClusterRowEntry(i1);
    }
  }
  
  protected void bindAvatarCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    PlayCardClusterView localPlayCardClusterView = (PlayCardClusterView)paramViewHolder.itemView;
    bindCluster(this.mMultiDfeList.getTopLevelItem(paramInt), PlayCardAvatarClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates), localPlayCardClusterView, true);
  }
  
  protected void bindGenericCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    PlayCardClusterView localPlayCardClusterView = (PlayCardClusterView)paramViewHolder.itemView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    bindCluster(localDocument, getGenericClusterMetadata(localDocument, getSignalStrengthForCluster(localDocument)), localPlayCardClusterView, true);
  }
  
  protected void bindHighlightsBanner$39fb0461(RecyclerView.ViewHolder paramViewHolder)
  {
    throw new UnsupportedOperationException("This is not supported by the base adapter");
  }
  
  protected void bindLeadingSpacer(View paramView)
  {
    paramView.getLayoutParams().height = getBackgroundViewSpacerHeight();
    paramView.setId(2131755059);
  }
  
  protected void bindMerchCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    PlayCardMerchClusterView localPlayCardMerchClusterView = (PlayCardMerchClusterView)paramViewHolder.itemView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    if (localDocument.getChildCount() > 0) {}
    for (int i = localDocument.getChildAt(0).mDocument.docType;; i = localDocument.mDocument.docType)
    {
      bindCluster(localDocument, PlayCardMerchClusterRepository.getMetadata(i, this.mColumnCount, this.mUseTallTemplates), localPlayCardMerchClusterView, true);
      localPlayCardMerchClusterView.configureMerch(this.mBitmapLoader, 0, (Common.Image)localDocument.getImages(14).get(0), localDocument.mDocument.title, getClusterClickListener(localDocument, localPlayCardMerchClusterView.getPlayStoreUiElementNode()));
      return;
    }
  }
  
  protected void bindOrderedCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    PlayCardOrderedClusterView localPlayCardOrderedClusterView = (PlayCardOrderedClusterView)paramViewHolder.itemView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    localPlayCardOrderedClusterView.setIdentifier(localDocument.mDocument.docid);
    Document[] arrayOfDocument = localDocument.getChildren();
    BitmapLoader localBitmapLoader = this.mBitmapLoader;
    NavigationManager localNavigationManager = this.mNavigationManager;
    View.OnClickListener localOnClickListener = getClusterClickListener(localDocument, localPlayCardOrderedClusterView);
    PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
    FinskyEventLog.setServerLogCookie(localPlayCardOrderedClusterView.mUiElementProto, localDocument.mDocument.serverLogsCookie);
    localPlayCardOrderedClusterView.mParentNode = localPlayStoreUiElementNode;
    localPlayCardOrderedClusterView.bindEntries(localDocument, arrayOfDocument, localBitmapLoader, localNavigationManager);
    localPlayCardOrderedClusterView.bindHeader((int)localDocument.mDocument.containerMetadata.estimatedResults, localDocument.mDocument.backendId, localDocument.mDocument.title, localOnClickListener);
  }
  
  protected void bindQuicklinksBanner(View paramView)
  {
    throw new UnsupportedOperationException("This is not supported by the base adapter");
  }
  
  protected void bindSecondaryHeader(View paramView) {}
  
  protected void bindSpinnerData(Identifiable paramIdentifiable, final Spinner paramSpinner, View paramView)
  {
    Document localDocument = this.mMultiDfeList.mContainerList.mContainerDocument;
    paramIdentifiable.setIdentifier(localDocument.mDocument.docid);
    paramSpinner.setBackgroundResource(CorpusResourceUtils.getCorpusSpinnerDrawable(localDocument.mDocument.backendId));
    if (paramView != null) {
      paramView.setBackgroundColor(CorpusResourceUtils.getPrimaryColor(this.mContext, localDocument.mDocument.backendId));
    }
    int i = paramSpinner.getResources().getDimensionPixelSize(2131493505) + this.mCardContentPadding;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramSpinner.getLayoutParams();
    localMarginLayoutParams.leftMargin = i;
    localMarginLayoutParams.rightMargin = i;
    paramSpinner.setLayoutParams(localMarginLayoutParams);
    final Containers.ContainerView[] arrayOfContainerView = localDocument.mDocument.containerMetadata.containerView;
    paramSpinner.setAdapter(new ContainerViewSpinnerAdapter(this.mContext, arrayOfContainerView));
    for (int j = 0;; j++) {
      if (j < arrayOfContainerView.length)
      {
        if (arrayOfContainerView[j].selected) {
          paramSpinner.setSelection(j);
        }
      }
      else
      {
        paramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
          public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            Containers.ContainerView localContainerView1 = (Containers.ContainerView)paramSpinner.getAdapter().getItem(paramAnonymousInt);
            if ((paramSpinner.getVisibility() == 0) && (!localContainerView1.selected))
            {
              FinskyApp.get().getEventLogger().logClickEvent(249, localContainerView1.serverLogsCookie, CardRecyclerViewAdapter.this.mParentNode);
              FinskyEventLog.startNewImpression(CardRecyclerViewAdapter.this.mParentNode);
              Containers.ContainerView[] arrayOfContainerView = arrayOfContainerView;
              int i = arrayOfContainerView.length;
              int j = 0;
              if (j < i)
              {
                Containers.ContainerView localContainerView2 = arrayOfContainerView[j];
                if (localContainerView2 == localContainerView1) {}
                for (boolean bool = true;; bool = false)
                {
                  localContainerView2.selected = bool;
                  localContainerView2.hasSelected = true;
                  j++;
                  break;
                }
              }
              CardRecyclerViewAdapter.this.mItems.clear();
              ContainerList localContainerList = CardRecyclerViewAdapter.this.mMultiDfeList.mContainerList;
              localContainerList.clearDataAndReplaceInitialUrl(localContainerView1.listUrl);
              localContainerList.startLoadItems();
            }
          }
          
          public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
        });
        return;
      }
    }
  }
  
  protected void bindTopChartsCluster(int paramInt, View paramView)
  {
    PlayCardPromoClusterBaseView localPlayCardPromoClusterBaseView = (PlayCardPromoClusterBaseView)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    localPlayCardPromoClusterBaseView.bindData$5dbdb9dd(localDocument, this.mBitmapLoader, this.mNavigationManager, this.mParentNode, getSingleCardClusterDismissListener(paramView), 426, 2130837773, null, false);
    localPlayCardPromoClusterBaseView.setContentHorizontalPadding(this.mCardContentPadding);
    ((TopChartsClusterContentView)localPlayCardPromoClusterBaseView.findViewById(2131756161)).bind(localDocument, this.mBitmapLoader, this.mDfeApi, this.mToc, this.mNavigationManager, localPlayCardPromoClusterBaseView.getParentOfChildren());
  }
  
  protected void bindYoutubeCardCluster(int paramInt, View paramView)
  {
    PlayCardPromoClusterBaseView localPlayCardPromoClusterBaseView = (PlayCardPromoClusterBaseView)paramView;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    localPlayCardPromoClusterBaseView.bindData$5dbdb9dd(localDocument, this.mBitmapLoader, this.mNavigationManager, this.mParentNode, getSingleCardClusterDismissListener(paramView), 427, 2130837774, null, false);
    localPlayCardPromoClusterBaseView.setContentHorizontalPadding(this.mCardContentPadding);
    ((PlayYoutubeCardContentView)localPlayCardPromoClusterBaseView.findViewById(2131755970)).bind(localDocument, this.mBitmapLoader, localPlayCardPromoClusterBaseView.getParentOfChildren());
  }
  
  protected void computeLooseItemRowsValues(Resources paramResources)
  {
    ContainerList localContainerList = this.mMultiDfeList.mContainerList;
    Document localDocument = localContainerList.mContainerDocument;
    int i;
    if ((this.mUseMiniCards) && (!this.mShowLooseItemReasons) && (!this.mIsOrdered))
    {
      i = 1;
      if (localContainerList.getBackendId() != 9) {
        break label88;
      }
    }
    label88:
    for (int j = 1;; j = 0)
    {
      if (!localDocument.isAvatarCardContainer()) {
        break label94;
      }
      this.mLooseItemCellId = 2130968919;
      this.mLooseItemColCount = PlayCardAvatarClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates).getTileCount();
      return;
      i = 0;
      break;
    }
    label94:
    if (j != 0)
    {
      this.mLooseItemCellId = 2130968944;
      this.mLooseItemColCount = PlayCardPersonClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates).getTileCount();
      return;
    }
    if (this.mShowLooseItemReasons)
    {
      this.mLooseItemCellId = 2130968954;
      this.mLooseItemColCount = this.mColumnCount;
      return;
    }
    if (i != 0)
    {
      this.mLooseItemCellId = 2130968939;
      this.mLooseItemColCount = paramResources.getInteger(2131623941);
      return;
    }
    this.mLooseItemCellId = 2130969190;
    this.mLooseItemColCount = UiUtils.getRegularGridColumnCount(paramResources);
  }
  
  public void configureBackgroundView(HeroGraphicView paramHeroGraphicView, int paramInt)
  {
    if (this.mHasHighlights)
    {
      paramHeroGraphicView.bindBlankBackend$2549578(paramInt);
      return;
    }
    paramHeroGraphicView.bindGeneric$3dce7526(this.mMultiDfeList.mContainerList.mContainerDocument);
  }
  
  protected void configureContainerOfLooseItemsWithoutReasons(BucketRow paramBucketRow) {}
  
  protected View createAvatarCluster(ViewGroup paramViewGroup)
  {
    return inflate(2130968920, paramViewGroup, false);
  }
  
  protected View createGenericCluster(ViewGroup paramViewGroup)
  {
    return inflate(2130968925, paramViewGroup, false);
  }
  
  protected View createMerchCluster(ViewGroup paramViewGroup)
  {
    return inflate(2130968937, paramViewGroup, false);
  }
  
  protected View createOrderedCluster(ViewGroup paramViewGroup)
  {
    PlayCardOrderedClusterView localPlayCardOrderedClusterView = (PlayCardOrderedClusterView)inflate(2130968943, paramViewGroup, false);
    localPlayCardOrderedClusterView.inflateGrid(this.mContext.getResources().getInteger(2131623952), UiUtils.getRegularGridColumnCount(this.mContext.getResources()), this.mCardContentPadding);
    return localPlayCardOrderedClusterView;
  }
  
  protected View createSecondaryHeader(ViewGroup paramViewGroup)
  {
    return null;
  }
  
  @Deprecated
  public int getBackgroundViewSpacerHeight()
  {
    if (this.mSpacerHeightProvider != null)
    {
      int i = this.mSpacerHeightProvider.getLeadingSpacerHeight();
      if (i > 0) {
        return i;
      }
    }
    return FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(this.mContext, this.mTabMode);
  }
  
  protected View.OnClickListener getClusterClickListener(Document paramDocument, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (NavigationManager.hasClickListener(paramDocument)) {
      return this.mNavigationManager.getClickListener(paramDocument, paramPlayStoreUiElementNode);
    }
    return null;
  }
  
  public int getDataRowsCount()
  {
    return getBaseDataRowsCount();
  }
  
  protected int getDisplayIndex(int paramInt)
  {
    boolean bool = this.mHasSearchAd;
    int i = 0;
    int j;
    if (bool)
    {
      if (this.mIsOnTablet)
      {
        j = 1;
        i = -j;
      }
    }
    else {
      if (!this.mIsOrdered) {
        break label45;
      }
    }
    for (;;)
    {
      return paramInt + i;
      j = this.mLooseItemColCount;
      break;
      label45:
      paramInt = -1;
    }
  }
  
  public final int getHeaderShadowMode()
  {
    if (this.mHasHighlights) {
      return 2;
    }
    return 1;
  }
  
  public final int getItemCount()
  {
    int i = getDataRowsCount();
    int j = getPrependedRowsCount();
    if (i + j == 0) {
      return 0;
    }
    return i + j + getSpacersCount();
  }
  
  public int getItemViewType(int paramInt)
  {
    int i = 1;
    if (hasLeadingSpacer()) {
      if (paramInt == 0) {
        i = 21;
      }
    }
    int j;
    do
    {
      return i;
      paramInt--;
      if (hasExtraLeadingSpacer())
      {
        if (paramInt == 0) {
          return 22;
        }
        paramInt--;
      }
      j = getFooterMode();
      if ((j == 0) || (paramInt != -1 + (getBaseDataRowsCount() + getBasePrependedRowsCount()))) {
        break;
      }
    } while (j == i);
    if (j == 2) {
      return 0;
    }
    if (j == 3) {
      return 28;
    }
    Object[] arrayOfObject = new Object[i];
    arrayOfObject[0] = Integer.valueOf(j);
    FinskyLog.wtf("Unexpected footer mode: %d", arrayOfObject);
    return 0;
    if (this.mH2oIsEnabled)
    {
      if ((this.mHasHighlights) && (this.mH2oStoreQuickLinksActive))
      {
        if (paramInt == 0) {
          return 35;
        }
        paramInt--;
      }
      if (this.mQuickLinkCount > 0)
      {
        if (paramInt == 0) {
          return 2;
        }
        paramInt--;
      }
    }
    while (this.mHasSocialHeader) {
      if (paramInt == 0)
      {
        return 17;
        if (paramInt < this.mNumQuickLinkRows) {
          return 2;
        }
        paramInt -= this.mNumQuickLinkRows;
      }
      else
      {
        paramInt--;
      }
    }
    if (this.mHasBannerHeader)
    {
      if (paramInt == 0) {
        return 16;
      }
      paramInt--;
    }
    if (isShowingPlainHeader())
    {
      if (paramInt == 0) {
        return 7;
      }
      paramInt--;
    }
    if (hasFilters())
    {
      if (paramInt == 0) {
        return 8;
      }
      paramInt--;
    }
    if (shouldShowSecondaryHeader())
    {
      if (paramInt == 0) {
        return 29;
      }
      paramInt--;
    }
    ItemEntry localItemEntry = (ItemEntry)this.mItems.get(paramInt);
    if (this.mMultiDfeList.getTopLevelItem(localItemEntry.mTrueStartIndex) == null) {
      return 25;
    }
    return localItemEntry.mViewType;
  }
  
  protected PlayCardDismissListener getPlayCardDismissListener()
  {
    return this;
  }
  
  public int getPrependedRowsCount()
  {
    return getBasePrependedRowsCount();
  }
  
  int getRowCountBeforeItemEntries()
  {
    return getPrependedRowsCount() + getSpacersCount();
  }
  
  protected final PlayCardDismissListener getSingleCardClusterDismissListener(final View paramView)
  {
    new PlayCardDismissListener()
    {
      public final void onDismissDocument(Document paramAnonymousDocument, PlayCardViewBase paramAnonymousPlayCardViewBase)
      {
        CardRecyclerViewAdapter.access$000(CardRecyclerViewAdapter.this, paramAnonymousDocument, paramView);
      }
    };
  }
  
  public boolean hasBackgroundView()
  {
    return (this.mHasSocialHeader) || (this.mHasHighlights);
  }
  
  public boolean hasExtraLeadingSpacer()
  {
    return (!this.mHasBannerHeader) && (!this.mHasSocialHeader) && (!this.mHasHighlights);
  }
  
  protected boolean hasFilters()
  {
    return this.mHasFilters;
  }
  
  public boolean hasLeadingSpacer()
  {
    return !this.mHasSocialHeader;
  }
  
  protected boolean isDismissed(Document paramDocument)
  {
    return (paramDocument.isDismissable()) && (this.mClientMutationCache.isDismissedRecommendation(paramDocument.mDocument.docid));
  }
  
  public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    this.mActiveViewHolders.add(paramViewHolder);
    int i = paramViewHolder.mItemViewType;
    int j = paramInt - getRowCountBeforeItemEntries();
    int k = this.mItems.size();
    ItemEntry localItemEntry = null;
    if (j < k)
    {
      localItemEntry = null;
      if (j >= 0) {
        localItemEntry = (ItemEntry)this.mItems.get(j);
      }
    }
    View localView1 = paramViewHolder.itemView;
    switch (i)
    {
    case 20: 
    case 37: 
    default: 
      bindGenericCluster(localItemEntry.mTrueStartIndex, paramViewHolder);
    case 25: 
    case 28: 
      return;
    case 0: 
      bindErrorFooterView(localView1);
      return;
    case 1: 
      bindLoadingFooterView(localView1);
      return;
    case 21: 
      bindLeadingSpacer(localView1);
      return;
    case 22: 
      localView1.getLayoutParams().height = this.mExtraLeadingSpacerHeight;
      return;
    case 2: 
      if (this.mH2oIsEnabled)
      {
        bindQuicklinksBanner(localView1);
        return;
      }
      QuickLinkHelper.bindQuickLinksRow(this.mToc, this.mNavigationManager, this.mBitmapLoader, (ViewGroup)localView1, this.mQuickLinks, paramInt - getSpacersCount(), this.mNumQuickLinksPerRow, this.mParentNode);
      ((Identifiable)localView1).setIdentifier("quick_link_row:" + paramInt);
      ViewCompat.setPaddingRelative(localView1, ViewCompat.getPaddingStart(localView1) + this.mCardContentPadding, localView1.getPaddingTop(), ViewCompat.getPaddingEnd(localView1) + this.mCardContentPadding, localView1.getPaddingBottom());
      return;
    case 7: 
      PlayCardClusterViewHeader localPlayCardClusterViewHeader = (PlayCardClusterViewHeader)localView1;
      localPlayCardClusterViewHeader.setContent(this.mMultiDfeList.mContainerList.getBackendId(), this.mTitle, null, null, null);
      localPlayCardClusterViewHeader.setExtraHorizontalPadding(this.mCardContentPadding);
      localPlayCardClusterViewHeader.setIdentifier("plain_header:" + this.mTitle);
      return;
    case 16: 
      DocImageView localDocImageView = (DocImageView)localView1;
      Document localDocument12 = this.mMultiDfeList.mContainerList.mContainerDocument;
      ContainerWithBanner localContainerWithBanner = localDocument12.mDocument.annotations.template.containerWithBanner;
      if (!TextUtils.isEmpty(localContainerWithBanner.colorThemeArgb)) {
        localDocImageView.setBackgroundColor(Color.parseColor(localContainerWithBanner.colorThemeArgb));
      }
      localDocImageView.bind(localDocument12, this.mBitmapLoader, new int[] { 9 });
      return;
    case 17: 
      PlaySocialHeader localPlaySocialHeader = (PlaySocialHeader)localView1;
      RecommendationsContainerWithHeader localRecommendationsContainerWithHeader = this.mMultiDfeList.mContainerList.mContainerDocument.mDocument.annotations.template.recommendationsContainerWithHeader;
      DocV2 localDocV2 = localRecommendationsContainerWithHeader.primaryFace;
      DocV2[] arrayOfDocV2 = localRecommendationsContainerWithHeader.secondaryFace;
      NavigationManager localNavigationManager3 = this.mNavigationManager;
      PlayStoreUiElementNode localPlayStoreUiElementNode7 = this.mParentNode;
      localPlaySocialHeader.mAvatarPack.setData(localDocV2, arrayOfDocV2, localNavigationManager3, localPlayStoreUiElementNode7);
      localPlaySocialHeader.setId(2131755059);
      return;
    case 3: 
      int i14 = localItemEntry.mTrueStartIndex;
      PlayMerchBannerView localPlayMerchBannerView = (PlayMerchBannerView)localView1;
      int i15 = this.mColumnCount;
      int i16 = this.mCardContentPadding;
      NextBanner localNextBanner;
      View.OnClickListener localOnClickListener2;
      PlayStoreUiElementNode localPlayStoreUiElementNode6;
      byte[] arrayOfByte;
      if (i15 <= 0)
      {
        FinskyLog.wtf("Merch banner doesn't support non-positive number of columns: " + i15 + " passed", new Object[0]);
        Document localDocument11 = this.mMultiDfeList.getTopLevelItem(i14);
        localPlayMerchBannerView.setIdentifier(localDocument11.mDocument.docid);
        localNextBanner = localDocument11.getTemplate().nextBanner;
        Common.Image localImage2 = (Common.Image)localDocument11.getImages(14).get(0);
        BitmapLoader localBitmapLoader4 = this.mBitmapLoader;
        localOnClickListener2 = this.mNavigationManager.getClickListener(localDocument11, localPlayMerchBannerView);
        localPlayStoreUiElementNode6 = this.mParentNode;
        arrayOfByte = localDocument11.mDocument.serverLogsCookie;
        localPlayMerchBannerView.mMerchColor = UiUtils.getFillColor(localImage2, localPlayMerchBannerView.mFallbackMerchColor);
        localPlayMerchBannerView.mMerchImage.setOnLoadedListener(localPlayMerchBannerView);
        localPlayMerchBannerView.mMerchImage.setImage(localImage2.imageUrl, localImage2.supportsFifeUrlOptions, localBitmapLoader4);
        if (localPlayMerchBannerView.mMerchImage.getDrawable() == null) {
          break label1008;
        }
        localPlayMerchBannerView.configureImageFadingEdge();
      }
      for (;;)
      {
        localPlayMerchBannerView.setBackgroundDrawable(new InsetDrawable(new PaintDrawable(localPlayMerchBannerView.mMerchColor), 0, localPlayMerchBannerView.getPaddingTop(), 0, localPlayMerchBannerView.getPaddingBottom()));
        localPlayMerchBannerView.mTitle.setText(localNextBanner.title);
        localPlayMerchBannerView.mSubtitle.setText(localNextBanner.subtitle);
        int i17 = UiUtils.getTextColor(localNextBanner, localPlayMerchBannerView.getFallbackMerchTextColor());
        localPlayMerchBannerView.mTitle.setTextColor(i17);
        localPlayMerchBannerView.mSubtitle.setTextColor(i17);
        localPlayMerchBannerView.setOnClickListener(localOnClickListener2);
        localPlayMerchBannerView.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(409);
        FinskyEventLog.setServerLogCookie(localPlayMerchBannerView.mUiElementProto, arrayOfByte);
        localPlayMerchBannerView.mParentNode = localPlayStoreUiElementNode6;
        localPlayMerchBannerView.getParentNode().childImpression(localPlayMerchBannerView);
        localPlayMerchBannerView.requestLayout();
        return;
        localPlayMerchBannerView.mColumnCount = i15;
        localPlayMerchBannerView.mContentHorizontalPadding = i16;
        break;
        localPlayMerchBannerView.mMerchImage.clearFadingEdges();
      }
    case 8: 
      Spinner localSpinner = (Spinner)localView1.findViewById(2131755968);
      View localView2 = localView1.findViewById(2131755969);
      bindSpinnerData((Identifiable)localView1, localSpinner, localView2);
      if (shouldShowSecondaryHeader()) {
        ViewCompat.setPaddingRelative(localView1, ViewCompat.getPaddingStart(localView1), localView1.getPaddingTop(), ViewCompat.getPaddingEnd(localView1), 0);
      }
      localSpinner.setVisibility(0);
      return;
    case 39: 
      bindSplitContainerClusterHeaderView(localView1, j);
      return;
    case 6: 
      int i12 = localItemEntry.mTrueStartIndex;
      int i13 = localItemEntry.mTrueEndIndex;
      if (this.mShowLooseItemReasons)
      {
        bindRowOfLooseItemsWithReasons(i12, i13, localView1);
        return;
      }
      bindRowOfLooseItemsWithoutReasons(i12, i13, localView1, false);
      return;
    case 4: 
      bindMerchCluster(localItemEntry.mTrueStartIndex, paramViewHolder);
      return;
    case 10: 
      bindPersonCardCluster(localItemEntry.mTrueStartIndex, localView1, true);
      return;
    case 11: 
      int i11 = localItemEntry.mTrueStartIndex;
      PlayCardTrustedSourceClusterView localPlayCardTrustedSourceClusterView = (PlayCardTrustedSourceClusterView)localView1;
      Document localDocument10 = this.mMultiDfeList.getTopLevelItem(i11);
      bindCluster(localDocument10, PlayCardActionBannerClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates), localPlayCardTrustedSourceClusterView, true);
      NavigationManager localNavigationManager2 = this.mNavigationManager;
      BitmapLoader localBitmapLoader3 = this.mBitmapLoader;
      if (localDocument10.mTrustedSourceProfileDocument == null) {
        localDocument10.mTrustedSourceProfileDocument = new Document(localDocument10.getTemplate().trustedSourceContainer.source);
      }
      localPlayCardTrustedSourceClusterView.configurePersonProfile(localNavigationManager2, localBitmapLoader3, localDocument10.mTrustedSourceProfileDocument, localPlayCardTrustedSourceClusterView.getPlayStoreUiElementNode());
      return;
    case 24: 
      int i10 = localItemEntry.mTrueStartIndex;
      final PlayBundleBannerView localPlayBundleBannerView = (PlayBundleBannerView)localView1;
      final Document localDocument9 = this.mMultiDfeList.getTopLevelItem(i10);
      final PlayStoreUiElementNode localPlayStoreUiElementNode5 = localPlayBundleBannerView.getPlayStoreUiElementNode();
      View.OnClickListener local3 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          ArrayList localArrayList = new ArrayList();
          FinskyApp.get().getEventLogger().logClickEvent(1235, null, localPlayStoreUiElementNode5);
          AppStates localAppStates = FinskyApp.get().mAppStates;
          Document[] arrayOfDocument = localDocument9.getChildren();
          int i = arrayOfDocument.length;
          int j = 0;
          if (j < i)
          {
            Document localDocument = arrayOfDocument[j];
            if (localDocument.mDocument.backendId == 3)
            {
              AppStates.AppState localAppState = localAppStates.getApp(localDocument.mDocument.docid);
              if ((localAppState == null) || (localAppState.packageManagerState == null) || (localAppState.packageManagerState.installedVersion == -1)) {
                break label137;
              }
            }
            label137:
            for (int k = 1;; k = 0)
            {
              if (k == 0) {
                localArrayList.add(localDocument);
              }
              j++;
              break;
            }
          }
          Intent localIntent = MultiInstallActivity.getInstallIntent(CardRecyclerViewAdapter.this.mContext, localArrayList, FinskyApp.get().getCurrentAccountName());
          CardRecyclerViewAdapter.this.mContext.startActivity(localIntent);
        }
      };
      BundleBanner localBundleBanner = localDocument9.getTemplate().bundleBanner;
      if (localBundleBanner.action.length > 0) {}
      for (final CallToAction localCallToAction3 = localBundleBanner.action[0];; localCallToAction3 = null)
      {
        View.OnClickListener local4 = null;
        if (localCallToAction3 != null) {
          local4 = new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              FinskyApp.get().getEventLogger().logClickEvent(212, null, localPlayStoreUiElementNode5);
              CardRecyclerViewAdapter.this.mNavigationManager.resolveCallToAction(localCallToAction3, CardRecyclerViewAdapter.this.mDfeApi, CardRecyclerViewAdapter.this.mToc, FinskyApp.get().getPackageManager());
              CardRecyclerViewAdapter.access$000(CardRecyclerViewAdapter.this, localDocument9, localPlayBundleBannerView);
            }
          };
        }
        localPlayBundleBannerView.configureExtraContent(this.mBitmapLoader, localDocument9, getClusterClickListener(localDocument9, localPlayBundleBannerView.getPlayStoreUiElementNode()), local3, this.mColumnCount, local4);
        return;
      }
    case 14: 
      int i9 = localItemEntry.mTrueStartIndex;
      PlayCardActionBannerClusterView localPlayCardActionBannerClusterView = (PlayCardActionBannerClusterView)localView1;
      Document localDocument8 = this.mMultiDfeList.getTopLevelItem(i9);
      PlayCardClusterMetadata localPlayCardClusterMetadata2;
      ActionBanner localActionBanner;
      final CallToAction localCallToAction2;
      if (localDocument8.getChildren().length <= 1)
      {
        localPlayCardClusterMetadata2 = PlayCardActionBannerSingleCardClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates);
        if (!localDocument8.isActionBanner()) {
          break label1594;
        }
        localActionBanner = localDocument8.getTemplate().actionBanner;
        CallToAction[] arrayOfCallToAction = localActionBanner.action;
        if ((arrayOfCallToAction == null) || (arrayOfCallToAction.length <= 0)) {
          break label1600;
        }
        localCallToAction2 = arrayOfCallToAction[0];
        bindCluster(localDocument8, localPlayCardClusterMetadata2, localPlayCardActionBannerClusterView, false);
        if (localCallToAction2 == null) {
          break label1606;
        }
      }
      for (String str5 = localCallToAction2.buttonText;; str5 = null)
      {
        final PlayStoreUiElementNode localPlayStoreUiElementNode4 = localPlayCardActionBannerClusterView.getPlayStoreUiElementNode();
        View.OnClickListener local2 = new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().getEventLogger().logClickEvent(1230, null, localPlayStoreUiElementNode4);
            CardRecyclerViewAdapter.this.mNavigationManager.resolveCallToAction(localCallToAction2, CardRecyclerViewAdapter.this.mDfeApi, CardRecyclerViewAdapter.this.mToc, FinskyApp.get().getPackageManager());
          }
        };
        localPlayCardActionBannerClusterView.configureExtraContent(this.mNavigationManager, this.mBitmapLoader, localDocument8, localActionBanner.primaryFace, localActionBanner.secondaryFace, str5, localPlayStoreUiElementNode4, local2);
        return;
        localPlayCardClusterMetadata2 = PlayCardActionBannerClusterRepository.getMetadata(this.mColumnCount, this.mUseTallTemplates);
        break;
        localActionBanner = null;
        break label1478;
        localCallToAction2 = null;
        break label1502;
      }
    case 31: 
      int i8 = localItemEntry.mTrueStartIndex;
      PlayCardFriendReviewClusterView localPlayCardFriendReviewClusterView = (PlayCardFriendReviewClusterView)localView1;
      localPlayCardFriendReviewClusterView.bindData(this.mMultiDfeList.getTopLevelItem(i8), this.mBitmapLoader, this.mNavigationManager, this.mParentNode, getSingleCardClusterDismissListener(localView1));
      localPlayCardFriendReviewClusterView.setContentHorizontalPadding(this.mCardContentPadding);
      return;
    case 34: 
      bindTopChartsCluster(localItemEntry.mTrueStartIndex, localView1);
      return;
    case 5: 
      int i7 = localItemEntry.mTrueStartIndex;
      PlayCardMerchClusterView localPlayCardMerchClusterView = (PlayCardMerchClusterView)localView1;
      Document localDocument6 = this.mMultiDfeList.getTopLevelItem(i7);
      Document localDocument7 = localDocument6.getChildAt(0);
      localPlayCardMerchClusterView.setIdentifier(localDocument7.mDocument.docid);
      String str3 = localDocument6.mDocument.descriptionHtml;
      if (!TextUtils.isEmpty(str3))
      {
        localDocument7.mDescription = null;
        localDocument7.mHasCachedDescription = false;
        localDocument7.mDocument.descriptionHtml = str3;
        localDocument7.mDocument.hasDescriptionHtml = true;
      }
      View.OnClickListener localOnClickListener1 = getClusterClickListener(localDocument6, localPlayCardMerchClusterView.getPlayStoreUiElementNode());
      String str4 = getMoreResultsStringForCluster(this.mContext, localDocument6, 1, localOnClickListener1);
      PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata = PlayCardSingleCardClusterRepository.getMetadata(localDocument7.mDocument.docType, this.mColumnCount);
      localPlayCardMerchClusterView.withClusterDocumentData(localDocument6).createContent(localPlayCardSingleCardClusterMetadata, this.mClientMutationCache, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, null, this.mCardHeap, this.mParentNode);
      List localList2 = localDocument6.getImages(14);
      if ((this.mColumnCount > 2) && (localList2 != null) && (!localList2.isEmpty())) {
        localPlayCardMerchClusterView.configureMerch(this.mBitmapLoader, 1, (Common.Image)localList2.get(0), localDocument6.mDocument.title, localOnClickListener1);
      }
      for (;;)
      {
        localPlayCardMerchClusterView.showHeader(localDocument7.mDocument.backendId, localDocument6.mDocument.title, localDocument6.mDocument.subtitle, str4, localOnClickListener1, this.mCardContentPadding);
        localPlayCardMerchClusterView.setCardContentHorizontalPadding(this.mCardContentPadding);
        return;
        localPlayCardMerchClusterView.configureNoMerch();
      }
    case 9: 
      bindOrderedCluster(localItemEntry.mTrueStartIndex, paramViewHolder);
      return;
    case 38: 
      bindListViewClusterRowWithoutHeader(localView1, localItemEntry);
      return;
    case 12: 
      int i6 = localItemEntry.mTrueStartIndex;
      PlayCardRateClusterView localPlayCardRateClusterView = (PlayCardRateClusterView)localView1;
      Document localDocument5 = this.mMultiDfeList.getTopLevelItem(i6);
      localPlayCardRateClusterView.setClusterFadeOutListener(this.mClusterFadeOutListener);
      bindCluster(localDocument5, PlayCardRateClusterRepository.getMetadata(this.mColumnCount), localPlayCardRateClusterView, false);
      return;
    case 13: 
      int i5 = localItemEntry.mTrueStartIndex;
      PlayCardRateAndSuggestClusterView localPlayCardRateAndSuggestClusterView = (PlayCardRateAndSuggestClusterView)localView1;
      Document localDocument4 = this.mMultiDfeList.getTopLevelItem(i5);
      localPlayCardRateAndSuggestClusterView.setClusterFadeOutListener(this.mClusterFadeOutListener);
      bindCluster(localDocument4, PlayCardRateAndSuggestClusterRepository.getMetadata(localDocument4.getChildAt(0).mDocument.docType, this.mColumnCount, this.mUseTallTemplates), localPlayCardRateAndSuggestClusterView, false);
      return;
    case 15: 
      int i4 = localItemEntry.mTrueStartIndex;
      PlayCardEmptyClusterView localPlayCardEmptyClusterView = (PlayCardEmptyClusterView)localView1;
      Document localDocument3 = this.mMultiDfeList.getTopLevelItem(i4);
      localPlayCardEmptyClusterView.showHeader(localDocument3.mDocument.backendId, localDocument3.mDocument.title, localDocument3.mDocument.subtitle, null, null, this.mCardContentPadding);
      localPlayCardEmptyClusterView.createContent(localDocument3, this.mParentNode);
      localPlayCardEmptyClusterView.setIdentifier(localDocument3.mDocument.docid);
      return;
    case 18: 
      bindWarmWelcomeCardView(localItemEntry.mTrueStartIndex, localView1);
      return;
    case 30: 
      bindWarmWelcomeV2CardView(localItemEntry.mTrueStartIndex, localView1);
      return;
    case 19: 
      bindPersonCardCluster(localItemEntry.mTrueStartIndex, localView1, false);
      return;
    case 26: 
      bindAvatarCluster(localItemEntry.mTrueStartIndex, paramViewHolder);
      return;
    case 23: 
      bindRowOfLooseItemsWithoutReasons(localItemEntry.mTrueStartIndex, localItemEntry.mTrueEndIndex, localView1, true);
      return;
    case 27: 
      bindBannerWithContentCluster(localItemEntry.mTrueStartIndex, localView1);
      return;
    case 29: 
      bindSecondaryHeader(localView1);
      return;
    case 32: 
      final int n = localItemEntry.mTrueStartIndex;
      PlayCardClusterWithNoticeView localPlayCardClusterWithNoticeView = (PlayCardClusterWithNoticeView)localView1;
      Document localDocument2 = this.mMultiDfeList.getTopLevelItem(n);
      ContainerWithNotice localContainerWithNotice = localDocument2.getTemplate().containerWithNotice;
      int i1;
      int i3;
      final CallToAction localCallToAction1;
      String str2;
      View.OnClickListener local7;
      View.OnClickListener local8;
      View.OnClickListener local9;
      PlayCardClusterWithNoticeView.OnNoticeShownListener localOnNoticeShownListener;
      BitmapLoader localBitmapLoader2;
      PreferenceFile.SharedPreference localSharedPreference;
      boolean bool;
      if (localDocument2.getChildCount() > 0)
      {
        i1 = localDocument2.getChildAt(0).mDocument.docType;
        PlayCardClusterMetadata localPlayCardClusterMetadata1 = PlayCardClusterWithNoticeRepository.getMetadata(i1, this.mColumnCount, this.mUseTallTemplates, localDocument2.getChildCount());
        final int i2 = localContainerWithNotice.settingType;
        switch (localContainerWithNotice.settingType)
        {
        default: 
          i3 = -1;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(localContainerWithNotice.settingType);
          FinskyLog.wtf("Unknown PrivacySettingType for cluster's notice: %d", arrayOfObject);
          if (i3 != -1)
          {
            localPlayCardClusterWithNoticeView.mPlayClusterUiElementNode = new GenericUiElementNode(425, localDocument2.mDocument.serverLogsCookie, null, this.mParentNode);
            localPlayCardClusterWithNoticeView.mHasCustomUiElementNode = true;
          }
          bindCluster(localDocument2, localPlayCardClusterMetadata1, localPlayCardClusterWithNoticeView, false);
          localCallToAction1 = localContainerWithNotice.action;
          if (localCallToAction1 == null)
          {
            str2 = null;
            final PlayStoreUiElementNode localPlayStoreUiElementNode2 = localPlayCardClusterWithNoticeView.getPlayStoreUiElementNode();
            final PlayStoreUiElementNode localPlayStoreUiElementNode3 = localPlayCardClusterWithNoticeView.getNoticeUiElementNode();
            local7 = new View.OnClickListener()
            {
              public final void onClick(View paramAnonymousView)
              {
                FinskyApp.get().getEventLogger().logClickEvent(1237, null, localPlayStoreUiElementNode2);
                CardRecyclerViewAdapter.this.mNavigationManager.resolveCallToAction(localCallToAction1, CardRecyclerViewAdapter.this.mDfeApi, CardRecyclerViewAdapter.this.mToc, FinskyApp.get().getPackageManager());
              }
            };
            local8 = new View.OnClickListener()
            {
              public final void onClick(View paramAnonymousView)
              {
                FinskyApp.get().getEventLogger().logClickEvent(2901, null, localPlayStoreUiElementNode3);
                NavigationManager localNavigationManager = CardRecyclerViewAdapter.this.mNavigationManager;
                String str = UserSettingsUtils.getPreferenceKeyForSetting(i2);
                localNavigationManager.mActivity.openSettings(str);
              }
            };
            local9 = new View.OnClickListener()
            {
              public final void onClick(View paramAnonymousView)
              {
                FinskyApp.get().getEventLogger().logClickEvent(2902, null, localPlayStoreUiElementNode3);
                UserSettingsUtils.updateUserSetting$2f7b0d8d(i2, 1, null);
                CardRecyclerViewAdapter localCardRecyclerViewAdapter1 = CardRecyclerViewAdapter.this;
                CardRecyclerViewAdapter localCardRecyclerViewAdapter2 = CardRecyclerViewAdapter.this;
                localCardRecyclerViewAdapter1.notifyItemChanged(n + localCardRecyclerViewAdapter2.getRowCountBeforeItemEntries());
              }
            };
            localOnNoticeShownListener = UserSettingsUtils.getNoticeListenerForSetting(i2);
            localBitmapLoader2 = this.mBitmapLoader;
            localSharedPreference = UserSettingsUtils.getPreferenceForUserSetting(localContainerWithNotice.settingType, FinskyApp.get().getCurrentAccountName());
            if (localSharedPreference != null) {
              break label2644;
            }
            bool = true;
          }
          break;
        }
      }
      for (;;)
      {
        localPlayCardClusterWithNoticeView.configureExtraContent$786eba84(localBitmapLoader2, localDocument2, bool, localDocument2.mDocument.backendId, str2, localContainerWithNotice.explanationTextHtml, local7, local8, local9, localOnNoticeShownListener);
        return;
        i1 = localDocument2.mDocument.docType;
        break;
        i3 = 425;
        break label2417;
        str2 = localCallToAction1.buttonText;
        break label2483;
        Integer localInteger = (Integer)localSharedPreference.get();
        if ((localInteger == null) || (localInteger.equals(Integer.valueOf(0)))) {
          bool = true;
        } else {
          bool = false;
        }
      }
    case 33: 
      label1594:
      label1600:
      label1606:
      label2644:
      bindYoutubeCardCluster(localItemEntry.mTrueStartIndex, localView1);
      label2483:
      return;
    case 35: 
      label1008:
      label2417:
      bindHighlightsBanner$39fb0461(paramViewHolder);
      label1478:
      label1502:
      return;
    }
    int m = localItemEntry.mTrueStartIndex;
    Document localDocument1 = this.mMultiDfeList.getTopLevelItem(m);
    PlayCardPromoClusterBaseView localPlayCardPromoClusterBaseView = (PlayCardPromoClusterBaseView)localView1;
    localPlayCardPromoClusterBaseView.bindData$5dbdb9dd(localDocument1, this.mBitmapLoader, this.mNavigationManager, this.mParentNode, getSingleCardClusterDismissListener(localView1), 423, 0, null, true);
    localPlayCardPromoClusterBaseView.setContentHorizontalPadding(this.mCardContentPadding);
    PlayNewsstandCardContentView localPlayNewsstandCardContentView = (PlayNewsstandCardContentView)localView1.findViewById(2131755934);
    BitmapLoader localBitmapLoader1 = this.mBitmapLoader;
    NavigationManager localNavigationManager1 = this.mNavigationManager;
    DfeToc localDfeToc = this.mToc;
    PlayStoreUiElementNode localPlayStoreUiElementNode1 = localPlayCardPromoClusterBaseView.getParentOfChildren();
    Template localTemplate = localDocument1.getTemplate();
    NewsArticleContainer localNewsArticleContainer;
    Common.Image localImage1;
    if (localTemplate == null)
    {
      localNewsArticleContainer = null;
      List localList1 = localDocument1.getImages(4);
      if ((localList1 == null) || (localList1.size() <= 0)) {
        break label3005;
      }
      localImage1 = (Common.Image)localList1.get(0);
      label2855:
      localPlayNewsstandCardContentView.mArticleTitle.setText(localNewsArticleContainer.title);
      localPlayNewsstandCardContentView.mArticleSnippet.setText(FastHtmlParser.fromHtml(localNewsArticleContainer.snippet));
      localPlayNewsstandCardContentView.mPublisherName.setText(localNewsArticleContainer.publisher);
      localPlayNewsstandCardContentView.mPublishedDate.setText(localNewsArticleContainer.publishDateString);
      if (localImage1 == null) {
        break label3011;
      }
      localPlayNewsstandCardContentView.mPublisherImage.setImage(localImage1.imageUrl, localImage1.supportsFifeUrlOptions, localBitmapLoader1);
      localPlayNewsstandCardContentView.mPublisherImage.setVisibility(0);
    }
    for (;;)
    {
      NewsstandArticleHandler localNewsstandArticleHandler = FinskyApp.get().getArticleHandler();
      String str1 = localNewsArticleContainer.newsstandArticleId;
      localNewsstandArticleHandler.mArticleLoader.load(str1, null);
      localPlayNewsstandCardContentView.setOnClickListener(new PlayNewsstandCardContentView.1(localPlayNewsstandCardContentView, localNavigationManager1, localNewsArticleContainer, localDfeToc, localDocument1, localPlayStoreUiElementNode1));
      return;
      localNewsArticleContainer = localTemplate.newsArticleContainer;
      break;
      label3005:
      localImage1 = null;
      break label2855;
      label3011:
      localPlayNewsstandCardContentView.mPublisherImage.setVisibility(8);
    }
  }
  
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    Object localObject;
    switch (paramInt)
    {
    case 20: 
    case 37: 
    default: 
      localObject = createGenericCluster(paramViewGroup);
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder((View)localObject);
      localObject = createErrorFooterView(paramViewGroup);
      continue;
      localObject = createLoadingFooterView(paramViewGroup);
      continue;
      localObject = inflate(2130968893, paramViewGroup, false);
      continue;
      localObject = inflate(2130968827, paramViewGroup, false);
      continue;
      localObject = createLeadingSpacerView(paramViewGroup);
      continue;
      localObject = inflate(2130968994, paramViewGroup, false);
      continue;
      if (this.mH2oIsEnabled) {}
      for (localObject = inflate(2130969012, paramViewGroup, false);; localObject = QuickLinkHelper.inflateQuickLinksRow(paramViewGroup, this.mLayoutInflater, this.mNumQuickLinksPerRow)) {
        break;
      }
      localObject = inflate(2130968926, paramViewGroup, false);
      continue;
      localObject = inflate(2130968623, paramViewGroup, false);
      continue;
      localObject = inflate(2130969120, paramViewGroup, false);
      continue;
      localObject = inflate(2130968995, paramViewGroup, false);
      continue;
      localObject = inflate(2130969019, paramViewGroup, false);
      continue;
      if (this.mShowLooseItemReasons) {}
      for (localObject = inflate(2130968925, paramViewGroup, false);; localObject = createRowOfLooseItemsWithoutReasons(paramViewGroup, false)) {
        break;
      }
      localObject = createRowOfLooseItemsWithoutReasons(paramViewGroup, true);
      continue;
      localObject = createMerchCluster(paramViewGroup);
      continue;
      localObject = inflate(2130968917, paramViewGroup, false);
      continue;
      localObject = inflate(2130968962, paramViewGroup, false);
      continue;
      localObject = inflate(2130968915, paramViewGroup, false);
      continue;
      localObject = inflate(2130968930, paramViewGroup, false);
      continue;
      localObject = inflate(2130968945, paramViewGroup, false);
      inflate(2130969137, (FrameLayout)((View)localObject).findViewById(2131755876), true);
      continue;
      localObject = inflate(2130968937, paramViewGroup, false);
      continue;
      localObject = createOrderedCluster(paramViewGroup);
      continue;
      localObject = (BucketRow)inflate(2130968646, paramViewGroup, false);
      ((BucketRow)localObject).setContentHorizontalPadding(this.mCardContentPadding);
      int k = UiUtils.getRegularGridColumnCount(this.mContext.getResources());
      for (int m = 0; m < k; m++)
      {
        View localView = inflate(2130969190, (ViewGroup)localObject, false);
        localView.setVisibility(8);
        ((BucketRow)localObject).addView(localView);
      }
      continue;
      localObject = inflate(2130968951, paramViewGroup, false);
      continue;
      localObject = inflate(2130968950, paramViewGroup, false);
      continue;
      localObject = inflate(2130968929, paramViewGroup, false);
      continue;
      if (this.mWarmWelcomeCardColumns == 1) {}
      for (int j = 2130969179;; j = 2130969178)
      {
        localObject = inflate(j, paramViewGroup, false);
        break;
      }
      if (this.mWarmWelcomeCardColumns == 1) {}
      for (int i = 2130969182;; i = 2130969181)
      {
        localObject = inflate(i, paramViewGroup, false);
        break;
      }
      localObject = inflate(2130968940, paramViewGroup, false);
      continue;
      localObject = createAvatarCluster(paramViewGroup);
      continue;
      localObject = inflate(2130968911, paramViewGroup, false);
      continue;
      localObject = inflate(2130968987, paramViewGroup, false);
      continue;
      localObject = inflate(2130968922, paramViewGroup, false);
      continue;
      localObject = createSecondaryHeader(paramViewGroup);
      continue;
      localObject = inflate(2130968928, paramViewGroup, false);
      continue;
      localObject = inflate(2130968945, paramViewGroup, false);
      inflate(2130969021, (FrameLayout)((View)localObject).findViewById(2131755876), true);
      continue;
      localObject = inflate(2130968945, paramViewGroup, false);
      inflate(2130968996, (FrameLayout)((View)localObject).findViewById(2131755876), true);
    }
  }
  
  public void onDataChanged()
  {
    syncItemEntries();
    super.onDataChanged();
  }
  
  public final void onDestroy()
  {
    RecyclerView.ViewHolder[] arrayOfViewHolder = (RecyclerView.ViewHolder[])this.mActiveViewHolders.toArray(new RecyclerView.ViewHolder[this.mActiveViewHolders.size()]);
    for (int i = 0; i < arrayOfViewHolder.length; i++) {
      onViewRecycled(arrayOfViewHolder[i]);
    }
    super.onDestroy();
  }
  
  public final void onDismissDocument(Document paramDocument, PlayCardViewBase paramPlayCardViewBase)
  {
    this.mClientMutationCache.dismissRecommendation(paramDocument.mDocument.docid);
    this.mObservable.notifyChanged();
  }
  
  public final boolean onFailedToRecycleView$cb3a904()
  {
    return true;
  }
  
  public void onRestoreInstanceState(PlayRecyclerView paramPlayRecyclerView, Bundle paramBundle)
  {
    ArrayList localArrayList = paramBundle.getParcelableArrayList("CardRecyclerViewAdapter.itemEntriesList");
    SparseArray localSparseArray = paramBundle.getSparseParcelableArray("CardRecyclerViewAdapter.splitDocList");
    int i = paramBundle.getInt("CardRecyclerViewAdapter.firstVisibleRow");
    int j = paramBundle.getInt("CardRecyclerViewAdapter.firstVisibleItemEntry", -1);
    int k = paramBundle.getInt("CardRecyclerViewAdapter.rowPixelOffset");
    int m = paramBundle.getInt("CardRecyclerViewAdapter.rowPixelHeight");
    int n = paramBundle.getInt("CardRecyclerViewAdapter.columnCount", -1);
    int i1 = paramBundle.getInt("CardRecyclerViewAdapter.looseItemColumnCount", -1);
    int i2 = paramBundle.getInt("CardRecyclerViewAdapter.prependedRowsCount", -1);
    if ((localArrayList == null) || (localArrayList.size() == 0) || (i1 == -1) || (i2 == -1) || (n == -1))
    {
      syncItemEntries();
      this.mObservable.notifyChanged();
      this.mMultiDfeList.mContainerList.startLoadItems();
      super.onRestoreInstanceState(paramPlayRecyclerView, paramBundle);
    }
    label620:
    label629:
    do
    {
      return;
      int i3;
      if ((n != this.mColumnCount) || (i1 != this.mLooseItemColCount) || (i2 != getPrependedRowsCount())) {
        i3 = 1;
      }
      while (i3 != 0)
      {
        int i4 = 0;
        if (i4 < localArrayList.size())
        {
          ItemEntry localItemEntry3 = (ItemEntry)localArrayList.get(i4);
          int i8 = localItemEntry3.mTrueStartIndex;
          Document localDocument = (Document)localSparseArray.get(i8);
          int i9;
          if (localDocument != null)
          {
            int i19 = i4 + 1;
            int i20 = 0;
            int i21 = i19;
            for (;;)
            {
              if ((i21 < localArrayList.size()) && (((ItemEntry)localArrayList.get(i21)).mTrueStartIndex == i8))
              {
                i20++;
                i21++;
                continue;
                i3 = 0;
                break;
              }
            }
            splitContainerClusterAndAddItemEntries(i8, getCardListAdapterViewTypeForDocument(localDocument), localDocument);
            i9 = i20 + i4;
          }
          for (;;)
          {
            i4 = i9 + 1;
            break;
            if (localItemEntry3.isLooseItemRow())
            {
              int i10 = i4;
              int i11 = 0;
              i9 = i4;
              while (i10 < localArrayList.size())
              {
                ItemEntry localItemEntry5 = (ItemEntry)localArrayList.get(i10);
                if (!localItemEntry5.isLooseItemRow()) {
                  break;
                }
                i11 += 1 + localItemEntry5.mTrueEndIndex - localItemEntry5.mTrueStartIndex;
                int i18 = i10 + 1;
                i9 = i10;
                i10 = i18;
              }
              int i12 = this.mLooseItemColCount;
              int i13 = (-1 + (i11 + i12)) / i12;
              int i14 = ((ItemEntry)localArrayList.get(i4)).mTrueStartIndex;
              int i15 = ((ItemEntry)localArrayList.get(i9)).mTrueEndIndex;
              for (int i16 = 0; i16 < i13; i16++)
              {
                int i17 = Math.min(-1 + (i14 + this.mLooseItemColCount), i15);
                ItemEntry localItemEntry4 = new ItemEntry();
                localItemEntry4.mTrueStartIndex = i14;
                localItemEntry4.mTrueEndIndex = i17;
                localItemEntry4.mViewType = 6;
                this.mItems.add(localItemEntry4);
                i14 += this.mLooseItemColCount;
              }
            }
            else
            {
              this.mItems.add(localArrayList.get(i4));
              i9 = i4;
            }
          }
        }
        int i6;
        int i7;
        ItemEntry localItemEntry2;
        int i5;
        if (j >= 0)
        {
          ItemEntry localItemEntry1 = (ItemEntry)localArrayList.get(j);
          i6 = localItemEntry1.mStartIndexInContainerCluster;
          i7 = 0;
          if (i7 < this.mItems.size())
          {
            localItemEntry2 = (ItemEntry)this.mItems.get(i7);
            if (localItemEntry2.mTrueStartIndex >= localItemEntry1.mTrueStartIndex)
            {
              if (localItemEntry2.mTrueStartIndex > localItemEntry1.mTrueStartIndex) {
                break label681;
              }
              if (i6 == -1)
              {
                i5 = i7 + getRowCountBeforeItemEntries();
                if (i5 >= getItemCount()) {
                  break label728;
                }
              }
            }
          }
        }
        for (;;)
        {
          this.mObservable.notifyChanged();
          paramPlayRecyclerView.getLayoutManager().scrollToPosition(i5);
          return;
          if ((localItemEntry2.mStartIndexInContainerCluster <= i6) && (i6 <= localItemEntry2.mEndIndexInContainerCluster)) {
            break label620;
          }
          i7++;
          break;
          i7--;
          break label620;
          i5 = adjustOldFirstVisibleRow(k, m, i);
          if (i5 < getSpacersCount() + getPrependedRowsCount()) {
            break label629;
          }
          i5 = -1 + (getSpacersCount() + getPrependedRowsCount());
          break label629;
          i5 = -1 + getItemCount();
        }
      }
      this.mItems = localArrayList;
      this.mObservable.notifyChanged();
    } while (!(paramPlayRecyclerView.getLayoutManager() instanceof LinearLayoutManager));
    label681:
    label728:
    ((LinearLayoutManager)paramPlayRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, k);
  }
  
  public void onSaveInstanceState(PlayRecyclerView paramPlayRecyclerView, Bundle paramBundle)
  {
    super.onSaveInstanceState(paramPlayRecyclerView, paramBundle);
    if (this.mItems.size() == 0) {
      return;
    }
    boolean bool = paramPlayRecyclerView.getLayoutManager() instanceof LinearLayoutManager;
    int i = 0;
    if (bool) {
      i = ((LinearLayoutManager)paramPlayRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    }
    int j = paramPlayRecyclerView.getChildCount();
    int k = 0;
    int m = 0;
    if (j > 0)
    {
      View localView = paramPlayRecyclerView.getChildAt(0);
      m = localView.getTop();
      k = localView.getHeight();
    }
    int n = adjustOldFirstVisibleRow(m, k, i) - getRowCountBeforeItemEntries();
    paramBundle.putInt("CardRecyclerViewAdapter.firstVisibleRow", i);
    paramBundle.putInt("CardRecyclerViewAdapter.firstVisibleItemEntry", n);
    paramBundle.putInt("CardRecyclerViewAdapter.rowPixelOffset", m);
    paramBundle.putInt("CardRecyclerViewAdapter.rowPixelHeight", k);
    paramBundle.putInt("CardRecyclerViewAdapter.looseItemColumnCount", this.mLooseItemColCount);
    paramBundle.putInt("CardRecyclerViewAdapter.columnCount", this.mColumnCount);
    paramBundle.putInt("CardRecyclerViewAdapter.prependedRowsCount", getPrependedRowsCount());
    paramBundle.putParcelableArrayList("CardRecyclerViewAdapter.itemEntriesList", this.mItems);
    SparseArray localSparseArray = new SparseArray();
    ContainerList localContainerList = this.mMultiDfeList.mContainerList;
    for (int i1 = 0; i1 < localContainerList.getCount(); i1++)
    {
      Document localDocument = (Document)localContainerList.getItem(i1, false);
      if (shouldSplitCluster(getCardListAdapterViewTypeForDocument(localDocument))) {
        localSparseArray.append(i1, localDocument);
      }
    }
    paramBundle.putSparseParcelableArray("CardRecyclerViewAdapter.splitDocList", localSparseArray);
  }
  
  public void onViewRecycled(RecyclerView.ViewHolder paramViewHolder)
  {
    this.mActiveViewHolders.remove(paramViewHolder);
    View localView = paramViewHolder.itemView;
    if ((localView instanceof Recyclable)) {
      ((Recyclable)localView).onRecycle();
    }
    if ((localView instanceof PlayCardClusterView))
    {
      PlayCardClusterView localPlayCardClusterView = (PlayCardClusterView)localView;
      this.mCardHeap.recycle(localPlayCardClusterView);
    }
  }
  
  protected boolean shouldHidePlainHeaderDuringInitialLoading()
  {
    return false;
  }
  
  protected boolean shouldHidePlainHeaderOnEmpty()
  {
    return false;
  }
  
  protected boolean shouldShowSecondaryHeader()
  {
    return false;
  }
  
  protected final void syncItemEntries()
  {
    ContainerList localContainerList = this.mMultiDfeList.mContainerList;
    int i = localContainerList.getCount();
    int j = this.mItems.size();
    int k = 0;
    if (j > 0) {
      k = 1 + ((ItemEntry)this.mItems.get(-1 + this.mItems.size())).mTrueEndIndex;
    }
    int m = k;
    if (m < i)
    {
      localDocument = (Document)localContainerList.getItem(m, false);
      if (localDocument == null)
      {
        FinskyLog.d("Loaded null doc, forcing a hard reload of list data.", new Object[0]);
        localContainerList.resetItems();
        localContainerList.startLoadItems();
        this.mItems.clear();
      }
    }
    label293:
    label344:
    label350:
    while (i <= 0)
    {
      Document localDocument;
      return;
      if (localDocument.isAdvertisement()) {
        this.mHasSearchAd = true;
      }
      int n = getCardListAdapterViewTypeForDocument(localDocument);
      int i5;
      if ((n != 6) && (n != 23))
      {
        endLastEntry(m);
        if (((!this.mHasHighlights) || (!this.mH2oIsEnabled) || (n != 35)) && ((!PlayListView.ENABLE_ANIMATION) || (((!localDocument.isRateCluster()) || (!isRateClusterDismissed(this.mClientMutationCache, localDocument, false))) && ((!localDocument.isRateAndSuggestCluster()) || (!isRateClusterDismissed(this.mClientMutationCache, localDocument, true))))) && (((!localDocument.isWarmWelcome()) && (!localDocument.isWarmWelcomeV2()) && (!localDocument.isBundleBanner())) || (!this.mClientMutationCache.isDismissedRecommendation(localDocument.mDocument.docid))))
        {
          if ((!localDocument.isTopChartsContainer()) && (!localDocument.isNewsArticleContainer()) && (!localDocument.isYoutubeVideoContainer()) && (!localDocument.isFriendReview())) {
            break label344;
          }
          i5 = 1;
          if ((i5 == 0) || (!this.mClientMutationCache.isDismissedRecommendation(localDocument.getChildAt(0).mDocument.docid)))
          {
            if (!shouldSplitCluster(n)) {
              break label350;
            }
            splitContainerClusterAndAddItemEntries(m, n, localDocument);
          }
        }
      }
      for (;;)
      {
        m++;
        break;
        i5 = 0;
        break label293;
        ArrayList localArrayList2 = this.mItems;
        ItemEntry localItemEntry3 = new ItemEntry();
        localItemEntry3.mTrueStartIndex = m;
        localItemEntry3.mTrueEndIndex = m;
        localItemEntry3.mViewType = n;
        localArrayList2.add(localItemEntry3);
        continue;
        int i1 = this.mItems.size();
        int i2 = 0;
        if (i1 > 0)
        {
          ItemEntry localItemEntry2 = (ItemEntry)this.mItems.get(-1 + this.mItems.size());
          boolean bool = localItemEntry2.isLooseItemRow();
          i2 = 0;
          if (bool)
          {
            int i3 = m - localItemEntry2.mTrueStartIndex;
            int i4 = this.mLooseItemColCount;
            i2 = 0;
            if (i3 < i4) {
              i2 = 1;
            }
          }
        }
        if (i2 == 0)
        {
          endLastEntry(m);
          ArrayList localArrayList1 = this.mItems;
          ItemEntry localItemEntry1 = new ItemEntry();
          localItemEntry1.mTrueStartIndex = m;
          localItemEntry1.mViewType = n;
          localArrayList1.add(localItemEntry1);
        }
      }
    }
    endLastEntry(localContainerList.getCount());
  }
  
  public final void updateAdapterData(T paramT)
  {
    super.updateAdapterData(paramT);
    this.mItems.clear();
    syncItemEntries();
    this.mObservable.notifyChanged();
  }
  
  public static class ItemEntry
    implements Parcelable
  {
    public static final Parcelable.Creator<ItemEntry> CREATOR = new Parcelable.Creator() {};
    int mEndIndexInContainerCluster = -1;
    int mStartIndexInContainerCluster = -1;
    int mTrueEndIndex = -1;
    int mTrueStartIndex = -1;
    int mViewType = -1;
    
    public int describeContents()
    {
      return 0;
    }
    
    public final boolean isLooseItemRow()
    {
      return (this.mViewType == 6) || (this.mViewType == 23);
    }
    
    public final boolean isSplitContainerClusterNonHeaderRow()
    {
      return this.mStartIndexInContainerCluster != -1;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mTrueStartIndex);
      paramParcel.writeInt(this.mTrueEndIndex);
      paramParcel.writeInt(this.mViewType);
      paramParcel.writeInt(this.mStartIndexInContainerCluster);
      paramParcel.writeInt(this.mEndIndexInContainerCluster);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.CardRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */