package com.google.android.finsky.api.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.AlbumDetails;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.ArtistDetails;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.BadgeContainer;
import com.google.android.finsky.protos.BannerWithContentContainer;
import com.google.android.finsky.protos.BookDetails;
import com.google.android.finsky.protos.BookSeriesDetails;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.Dismissal;
import com.google.android.finsky.protos.DocDetails.DocumentDetails;
import com.google.android.finsky.protos.DocDetails.PersonDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EditorialSeriesContainer;
import com.google.android.finsky.protos.FilterRules.Availability;
import com.google.android.finsky.protos.FriendReviewContainer;
import com.google.android.finsky.protos.HighlightsContainer;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.protos.MagazineDetails;
import com.google.android.finsky.protos.MoreByCreatorContainer;
import com.google.android.finsky.protos.MusicDetails;
import com.google.android.finsky.protos.Rating.AggregateRating;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.protos.SelectedChild;
import com.google.android.finsky.protos.Snippet;
import com.google.android.finsky.protos.Snow;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.finsky.protos.SuggestionReasons;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.protos.TvEpisodeDetails;
import com.google.android.finsky.protos.VideoAnnotations;
import com.google.android.finsky.protos.VideoDetails;
import com.google.android.finsky.protos.VoucherInfo;
import com.google.android.finsky.protos.Warning;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document
  implements Parcelable
{
  public static final Parcelable.Creator<Document> CREATOR = new Parcelable.Creator() {};
  private static final String[] sForcedPreregistrationSet = Utils.commaUnpackStrings((String)G.preregistrationForceList.get());
  private Document[] mChildDocuments;
  public CharSequence mDescription;
  public final DocV2 mDocument;
  public boolean mHasCachedDescription;
  private Map<Integer, List<Common.Image>> mImageTypeMap;
  private float mRoundStarRating = -1.0F;
  private List<Document> mSubscriptionsList;
  public Document mTrustedSourceProfileDocument;
  
  public Document(DocV2 paramDocV2)
  {
    this.mDocument = paramDocV2;
  }
  
  @SuppressLint({"UseSparseArrays"})
  private Map<Integer, List<Common.Image>> getImageTypeMap()
  {
    if (this.mImageTypeMap == null)
    {
      this.mImageTypeMap = new HashMap();
      for (Common.Image localImage : this.mDocument.image)
      {
        int k = localImage.imageType;
        if (!this.mImageTypeMap.containsKey(Integer.valueOf(k))) {
          this.mImageTypeMap.put(Integer.valueOf(k), new ArrayList());
        }
        ((List)this.mImageTypeMap.get(Integer.valueOf(k))).add(localImage);
      }
    }
    return this.mImageTypeMap;
  }
  
  public static boolean isPreorderOffer(Common.Offer paramOffer)
  {
    return (paramOffer != null) && ((paramOffer.offerType == 1) || (paramOffer.offerType == 7)) && (paramOffer.hasOnSaleDate) && (paramOffer.onSaleDate > System.currentTimeMillis());
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final AlbumDetails getAlbumDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.albumDetails;
    }
    return null;
  }
  
  public final AppDetails getAppDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.appDetails;
    }
    return null;
  }
  
  public final String getApplicableVoucherDescription()
  {
    return this.mDocument.annotations.applicableVoucherDescription;
  }
  
  public final int getAvailabilityRestriction()
  {
    if (this.mDocument.availability != null) {
      return this.mDocument.availability.restriction;
    }
    return -1;
  }
  
  public final BadgeContainer getBadgeContainer()
  {
    return this.mDocument.annotations.docBadgeContainer[0];
  }
  
  public final Badge getBadgeForContentRating()
  {
    return this.mDocument.annotations.badgeForContentRating;
  }
  
  public final BannerWithContentContainer getBannerWithContentContainer()
  {
    Template localTemplate = getTemplate();
    if (localTemplate != null) {
      return localTemplate.bannerWithContentContainer;
    }
    return null;
  }
  
  public final BookDetails getBookDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.bookDetails;
    }
    return null;
  }
  
  public final Document getChildAt(int paramInt)
  {
    if (this.mChildDocuments == null) {
      this.mChildDocuments = new Document[getChildCount()];
    }
    if (this.mChildDocuments[paramInt] == null) {
      this.mChildDocuments[paramInt] = new Document(this.mDocument.child[paramInt]);
    }
    return this.mChildDocuments[paramInt];
  }
  
  public final int getChildCount()
  {
    return this.mDocument.child.length;
  }
  
  public final Document[] getChildren()
  {
    if (this.mChildDocuments == null) {
      this.mChildDocuments = new Document[getChildCount()];
    }
    int i = getChildCount();
    for (int j = 0; j < i; j++) {
      if (this.mChildDocuments[j] == null) {
        this.mChildDocuments[j] = new Document(this.mDocument.child[j]);
      }
    }
    return this.mChildDocuments;
  }
  
  public final String getClickUrl()
  {
    Template localTemplate = getTemplate();
    if ((localTemplate != null) && (localTemplate.snow != null)) {
      return localTemplate.snow.clickUrl;
    }
    return null;
  }
  
  public final String getCoreContentHeader()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.sectionCoreContent != null)) {
      return localAnnotations.sectionCoreContent.header;
    }
    return "";
  }
  
  public final String getCoreContentListUrl()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.sectionCoreContent != null)) {
      return localAnnotations.sectionCoreContent.listUrl;
    }
    return "";
  }
  
  public final Badge[] getCreatorBadges()
  {
    return this.mDocument.annotations.badgeForCreator;
  }
  
  public final Document getCreatorDoc()
  {
    if (hasCreatorDoc()) {
      return new Document(this.mDocument.annotations.creatorDoc);
    }
    return null;
  }
  
  public final CharSequence getDescription()
  {
    if (!this.mHasCachedDescription)
    {
      String str = this.mDocument.descriptionHtml;
      if (!TextUtils.isEmpty(str)) {
        this.mDescription = FastHtmlParser.fromHtml(str);
      }
      this.mHasCachedDescription = true;
    }
    return this.mDescription;
  }
  
  public final ArtistDetails getDisplayArtist()
  {
    if (getAlbumDetails() != null) {
      return getAlbumDetails().displayArtist;
    }
    return null;
  }
  
  public final EditorialSeriesContainer getEditorialSeriesContainer()
  {
    return this.mDocument.annotations.template.editorialSeriesContainer;
  }
  
  public final Badge[] getFeatureBadges()
  {
    return this.mDocument.annotations.featureBadge;
  }
  
  public final Badge getFirstCreatorBadge()
  {
    return this.mDocument.annotations.badgeForCreator[0];
  }
  
  public final String getFormattedPrice$47921032()
  {
    Common.Offer localOffer = getOffer(1);
    if ((localOffer != null) && (localOffer.hasFormattedAmount)) {
      return localOffer.formattedAmount;
    }
    return null;
  }
  
  public final Review getFriendReview()
  {
    if (isFriendReview()) {
      return getTemplate().friendReviewContainer.review;
    }
    return null;
  }
  
  public final Common.Docid getFullDocid()
  {
    return DocUtils.createDocid(this.mDocument.backendId, this.mDocument.docType, this.mDocument.backendDocid);
  }
  
  public final List<Common.Image> getImages(int paramInt)
  {
    return (List)getImageTypeMap().get(Integer.valueOf(paramInt));
  }
  
  public final long getInstallationSize()
  {
    if ((hasDetails()) && (getAppDetails() != null)) {
      return getAppDetails().installationSize;
    }
    return 0L;
  }
  
  public final Link getLinkAnnotation()
  {
    if (this.mDocument.annotations != null) {
      return this.mDocument.annotations.link;
    }
    return null;
  }
  
  public final MagazineDetails getMagazineDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.magazineDetails;
    }
    return null;
  }
  
  public final MoreByCreatorContainer getMoreByCreatorContainer()
  {
    if (getTemplate() != null) {
      return getTemplate().moreByCreatorContainer;
    }
    return null;
  }
  
  public final Dismissal getNeutralDismissal()
  {
    if (hasNeutralDismissal()) {
      return getSuggestionReasons().neutralDismissal;
    }
    return null;
  }
  
  public final Common.Offer getOffer(int paramInt)
  {
    for (Common.Offer localOffer : this.mDocument.offer) {
      if (localOffer.offerType == paramInt) {
        return localOffer;
      }
    }
    return null;
  }
  
  public final Warning getOptimalDeviceClassWarning()
  {
    if (hasOptimalDeviceClassWarning()) {
      return this.mDocument.annotations.optimalDeviceClassWarning;
    }
    return null;
  }
  
  public final DocDetails.PersonDetails getPersonDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.personDetails;
    }
    return null;
  }
  
  public final SectionMetadata getPostPurchaseCrossSellSection()
  {
    if (this.mDocument.annotations != null) {
      return this.mDocument.annotations.sectionPurchaseCrossSell;
    }
    return null;
  }
  
  public final long getRatingCount()
  {
    return this.mDocument.aggregateRating.ratingsCount;
  }
  
  public final int[] getRatingHistogram()
  {
    if (!hasRating()) {
      return new int[] { 0, 0, 0, 0, 0 };
    }
    Rating.AggregateRating localAggregateRating = this.mDocument.aggregateRating;
    int[] arrayOfInt = new int[5];
    arrayOfInt[0] = ((int)localAggregateRating.fiveStarRatings);
    arrayOfInt[1] = ((int)localAggregateRating.fourStarRatings);
    arrayOfInt[2] = ((int)localAggregateRating.threeStarRatings);
    arrayOfInt[3] = ((int)localAggregateRating.twoStarRatings);
    arrayOfInt[4] = ((int)localAggregateRating.oneStarRatings);
    return arrayOfInt;
  }
  
  public final String getRelatedDocTypeListUrl()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.sectionRelatedDocType != null)) {
      return localAnnotations.sectionRelatedDocType.listUrl;
    }
    return "";
  }
  
  public final SectionMetadata[] getSectionMetaDataList()
  {
    if (this.mDocument.annotations != null) {
      return this.mDocument.annotations.detailsPageCluster;
    }
    return null;
  }
  
  public final SelectedChild getSelectedChild()
  {
    if ((this.mDocument.annotations != null) && (this.mDocument.annotations.selectedChild != null)) {}
    for (int i = 1; i != 0; i = 0) {
      return this.mDocument.annotations.selectedChild;
    }
    return null;
  }
  
  public final String getSeriesComposition()
  {
    if ((hasDetails()) && (this.mDocument.details.bookSeriesDetails != null)) {
      return this.mDocument.details.bookSeriesDetails.seriesComposition;
    }
    return null;
  }
  
  public final String getSeriesLine()
  {
    if ((hasDetails()) && (this.mDocument.details.bookDetails != null)) {
      return this.mDocument.details.bookDetails.seriesLine;
    }
    return null;
  }
  
  public final String getSnippetHtml()
  {
    if ((this.mDocument.annotations != null) && (this.mDocument.annotations.snippet != null)) {
      return this.mDocument.annotations.snippet.snippetHtml;
    }
    return null;
  }
  
  public final SongDetails getSongDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.songDetails;
    }
    return null;
  }
  
  public final float getStarRating()
  {
    if (this.mRoundStarRating < 0.0F) {
      this.mRoundStarRating = BadgeUtils.roundRating(this.mDocument.aggregateRating.starRating);
    }
    return this.mRoundStarRating;
  }
  
  public final List<Document> getSubscriptionsList()
  {
    if (!hasSubscriptions()) {
      return null;
    }
    if (this.mSubscriptionsList == null)
    {
      this.mSubscriptionsList = new ArrayList(this.mDocument.annotations.subscription.length);
      DocV2[] arrayOfDocV2 = this.mDocument.annotations.subscription;
      for (int i = 0; i < arrayOfDocV2.length; i++)
      {
        DocV2 localDocV2 = arrayOfDocV2[i];
        this.mSubscriptionsList.add(new Document(localDocV2));
      }
    }
    return this.mSubscriptionsList;
  }
  
  public final SectionMetadata getSuggestForRatingSection()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if (localAnnotations != null) {
      return localAnnotations.sectionSuggestForRating;
    }
    return null;
  }
  
  public final SuggestionReasons getSuggestionReasons()
  {
    if ((this.mDocument.annotations != null) && (this.mDocument.annotations.suggestionReasons != null)) {
      return this.mDocument.annotations.suggestionReasons;
    }
    return null;
  }
  
  public final int getTargetSdk()
  {
    AppDetails localAppDetails = getAppDetails();
    if (localAppDetails == null) {
      return 0;
    }
    String str = localAppDetails.packageName;
    String[] arrayOfString = Utils.commaUnpackStrings((String)G.targetsSdk23Whitelist.get());
    for (int i = 0; i < arrayOfString.length; i++) {
      if (str.equals(arrayOfString[i])) {
        return 23;
      }
    }
    return localAppDetails.targetSdkVersion;
  }
  
  public final Template getTemplate()
  {
    if (this.mDocument.annotations != null) {
      return this.mDocument.annotations.template;
    }
    return null;
  }
  
  public final TvEpisodeDetails getTvEpisodeDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.tvEpisodeDetails;
    }
    return null;
  }
  
  public final int getVersionCode()
  {
    if (this.mDocument.docType == 1) {
      return getAppDetails().versionCode;
    }
    return -1;
  }
  
  public final String getVideoAlsoAvailableInListUrl()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.videoAnnotations != null)) {
      return localAnnotations.videoAnnotations.alsoAvailableInListUrl;
    }
    return "";
  }
  
  public final String getVideoBundleContentListUrl()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.videoAnnotations != null)) {
      return localAnnotations.videoAnnotations.bundleContentListUrl;
    }
    return "";
  }
  
  public final VideoDetails getVideoDetails()
  {
    if (hasDetails()) {
      return this.mDocument.details.videoDetails;
    }
    return null;
  }
  
  public final String getVideoExtrasContentListUrl()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.videoAnnotations != null)) {
      return localAnnotations.videoAnnotations.extrasContentListUrl;
    }
    return "";
  }
  
  public final VoucherInfo[] getVouchers()
  {
    return this.mDocument.annotations.voucherInfo;
  }
  
  public final CharSequence getWarningMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Annotations localAnnotations = this.mDocument.annotations;
    int i = localAnnotations.warning.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0) {
        localStringBuilder.append("<br />");
      }
      localStringBuilder.append(localAnnotations.warning[j].localizedMessage);
    }
    return FastHtmlParser.fromHtml(localStringBuilder.toString());
  }
  
  public final boolean hasAntennaInfo()
  {
    return (getTemplate() != null) && (getTemplate().seriesAntenna != null);
  }
  
  public final boolean hasApplicableVoucherDescription()
  {
    return (this.mDocument.annotations != null) && (!TextUtils.isEmpty(this.mDocument.annotations.applicableVoucherDescription));
  }
  
  public final boolean hasBadgeContainer()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.docBadgeContainer != null) && (this.mDocument.annotations.docBadgeContainer.length > 0);
  }
  
  public final boolean hasCensoring()
  {
    AlbumDetails localAlbumDetails = getAlbumDetails();
    return (localAlbumDetails != null) && (localAlbumDetails.details != null) && (localAlbumDetails.details.hasCensoring);
  }
  
  public final boolean hasContainerAnnotation()
  {
    return this.mDocument.containerMetadata != null;
  }
  
  public final boolean hasCreatorBadges()
  {
    return this.mDocument.annotations.badgeForCreator.length > 0;
  }
  
  public final boolean hasCreatorDoc()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.creatorDoc != null);
  }
  
  public final boolean hasDealOfTheDayInfo()
  {
    return (getTemplate() != null) && (getTemplate().dealOfTheDay != null);
  }
  
  public final boolean hasDetails()
  {
    return this.mDocument.details != null;
  }
  
  public final boolean hasEmptyContainer()
  {
    return (getTemplate() != null) && (getTemplate().emptyContainer != null);
  }
  
  public final boolean hasFeatureBadges()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.featureBadge != null) && (this.mDocument.annotations.featureBadge.length > 0);
  }
  
  public final boolean hasHighlightsContainer()
  {
    Template localTemplate = getTemplate();
    if (localTemplate != null) {}
    for (HighlightsContainer localHighlightsContainer = localTemplate.highlightsContainer; localHighlightsContainer != null; localHighlightsContainer = null) {
      return true;
    }
    return false;
  }
  
  public final boolean hasImages(int paramInt)
  {
    return getImageTypeMap().containsKey(Integer.valueOf(paramInt));
  }
  
  public final boolean hasItemBadges()
  {
    return this.mDocument.annotations.badgeForDoc.length > 0;
  }
  
  public final boolean hasLinkAnnotation()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    return (localAnnotations != null) && (localAnnotations.link != null);
  }
  
  public final boolean hasMyRewardDetails()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.myRewardDetails != null);
  }
  
  public final boolean hasMySubscriptionDetails()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.mySubscriptionDetails != null);
  }
  
  public final boolean hasNeutralDismissal()
  {
    SuggestionReasons localSuggestionReasons = getSuggestionReasons();
    return (localSuggestionReasons != null) && (localSuggestionReasons.neutralDismissal != null);
  }
  
  public final boolean hasOptimalDeviceClassWarning()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    return (localAnnotations != null) && (localAnnotations.optimalDeviceClassWarning != null);
  }
  
  public final boolean hasPreorderOffer()
  {
    if (isPreorderOffer(getOffer(1))) {}
    while (isPreorderOffer(getOffer(7))) {
      return true;
    }
    return false;
  }
  
  public final boolean hasProductDetails()
  {
    return this.mDocument.productDetails != null;
  }
  
  public final boolean hasRating()
  {
    return this.mDocument.aggregateRating != null;
  }
  
  public final boolean hasReasons()
  {
    return (this.mDocument != null) && (getSuggestionReasons() != null) && (getSuggestionReasons().reason.length > 0);
  }
  
  public final boolean hasRecommendationsContainerWithHeaderTemplate()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.template != null) && (this.mDocument.annotations.template.recommendationsContainerWithHeader != null);
  }
  
  public final boolean hasReleaseType()
  {
    AlbumDetails localAlbumDetails = getAlbumDetails();
    return (localAlbumDetails != null) && (localAlbumDetails.details != null) && (localAlbumDetails.details.releaseType.length > 0);
  }
  
  public final boolean hasReviewHistogramData()
  {
    int[] arrayOfInt = getRatingHistogram();
    for (int i = 0; i < 5; i++) {
      if (arrayOfInt[i] > 0) {
        return true;
      }
    }
    return false;
  }
  
  public final boolean hasScreenshots()
  {
    List localList = getImages(1);
    return (localList != null) && (!localList.isEmpty()) && (1 != this.mDocument.backendId);
  }
  
  public final boolean hasSeriesLine()
  {
    return (hasDetails()) && (this.mDocument.details.bookDetails != null) && (this.mDocument.details.bookDetails.hasSeriesLine);
  }
  
  public final boolean hasSubscriptions()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.subscription.length > 0);
  }
  
  public final boolean hasVouchers()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.voucherInfo.length > 0);
  }
  
  public final boolean hasWarningMessage()
  {
    return (this.mDocument.annotations != null) && (this.mDocument.annotations.warning.length > 0);
  }
  
  public final boolean isActionBanner()
  {
    return (getTemplate() != null) && (getTemplate().actionBanner != null);
  }
  
  public final boolean isAdvertisement()
  {
    return (getTemplate() != null) && (getTemplate().snow != null);
  }
  
  public final boolean isAvatarCardContainer()
  {
    return (getTemplate() != null) && (getTemplate().avatarCardContainer != null);
  }
  
  public final boolean isBundleBanner()
  {
    return (getTemplate() != null) && (getTemplate().bundleBanner != null);
  }
  
  public final boolean isDismissable()
  {
    return (hasReasons()) && (hasNeutralDismissal());
  }
  
  public final boolean isFriendReview()
  {
    Template localTemplate = getTemplate();
    return (localTemplate != null) && (localTemplate.friendReviewContainer != null) && (localTemplate.friendReviewContainer.review != null);
  }
  
  public final boolean isMoreByCreatorContainer()
  {
    return (getTemplate() != null) && (getTemplate().moreByCreatorContainer != null);
  }
  
  public final boolean isMyCirclesContainer()
  {
    return (getTemplate() != null) && (getTemplate().myCirclesContainer != null);
  }
  
  public final boolean isNewsArticleContainer()
  {
    Template localTemplate = getTemplate();
    return (localTemplate != null) && (localTemplate.newsArticleContainer != null);
  }
  
  public final boolean isPreregistration()
  {
    if (this.mDocument.availableForPreregistration) {
      return true;
    }
    for (int i = 0;; i++)
    {
      if (i >= sForcedPreregistrationSet.length) {
        break label46;
      }
      if (this.mDocument.docid.equals(sForcedPreregistrationSet[i])) {
        break;
      }
    }
    label46:
    return false;
  }
  
  public final boolean isRateAndSuggestCluster()
  {
    return (getTemplate() != null) && (getTemplate().rateAndSuggestContainer != null);
  }
  
  public final boolean isRateCluster()
  {
    return (getTemplate() != null) && (getTemplate().rateContainer != null);
  }
  
  public final boolean isTopChartsContainer()
  {
    Template localTemplate = getTemplate();
    return (localTemplate != null) && (localTemplate.topChartsContainer != null);
  }
  
  public final boolean isVideoBundle()
  {
    Annotations localAnnotations = this.mDocument.annotations;
    return (localAnnotations != null) && (localAnnotations.videoAnnotations != null) && (localAnnotations.videoAnnotations.bundle);
  }
  
  public final boolean isWarmWelcome()
  {
    return (getTemplate() != null) && (getTemplate().warmWelcome != null);
  }
  
  public final boolean isWarmWelcomeV2()
  {
    return (getTemplate() != null) && (getTemplate().warmWelcomeV2 != null);
  }
  
  public final boolean isYoutubeVideoContainer()
  {
    Template localTemplate = getTemplate();
    return (localTemplate != null) && (localTemplate.youtubeVideoContainer != null);
  }
  
  public final boolean needsCheckoutFlow(int paramInt)
  {
    Common.Offer localOffer = getOffer(1);
    if (localOffer != null) {
      return localOffer.checkoutFlowRequired;
    }
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('{');
    localStringBuilder.append(this.mDocument.docid);
    if (this.mDocument.docType == 1) {
      localStringBuilder.append(" v=").append(getAppDetails().versionCode);
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(ParcelableProto.forProto(this.mDocument), 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.Document
 * JD-Core Version:    0.7.0.1
 */