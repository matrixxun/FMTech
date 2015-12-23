package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Template
  extends MessageNano
{
  public ActionBanner actionBanner = null;
  public AddToCirclesContainer addToCirclesContainer = null;
  public ArtistClusterContainer artistClusterContainer = null;
  public AvatarCardContainer avatarCardContainer = null;
  public BannerWithContentContainer bannerWithContentContainer = null;
  public BundleBanner bundleBanner = null;
  public BundleContainer bundleContainer = null;
  public ContainerWithBanner containerWithBanner = null;
  public ContainerWithNotice containerWithNotice = null;
  public DealOfTheDay dealOfTheDay = null;
  public EditorialSeriesContainer editorialSeriesContainer = null;
  public EmptyContainer emptyContainer = null;
  public FeaturedAppsContainer featuredAppsContainer = null;
  public FriendReviewContainer friendReviewContainer = null;
  public HighlightsContainer highlightsContainer = null;
  public HighlightsEditorialContainer highlightsEditorialContainer = null;
  public ListViewContainer listViewContainer = null;
  public MediumCardContainer mediumCardContainer = null;
  public MoreByCreatorContainer moreByCreatorContainer = null;
  public MultiRowContainer multiRowContainer = null;
  public MyCirclesContainer myCirclesContainer = null;
  public NewsArticleContainer newsArticleContainer = null;
  public NextBanner nextBanner = null;
  public PlayDailyCard playDaily = null;
  public PurchaseHistoryContainer purchaseHistoryContainer = null;
  public RateAndSuggestContainer rateAndSuggestContainer = null;
  public RateContainer rateContainer = null;
  public RecommendationsContainer recommendationsContainer = null;
  public RecommendationsContainerWithHeader recommendationsContainerWithHeader = null;
  public SeriesAntenna seriesAntenna = null;
  public SingleCardContainer singleCardContainer = null;
  public Snow snow = null;
  public TileTemplate tileDetailsReflectedGraphic2X2 = null;
  public TileTemplate tileFourBlock4X2 = null;
  public TileTemplate tileGraphic2X1 = null;
  public TileTemplate tileGraphic4X2 = null;
  public TileTemplate tileGraphicColoredTitle2X1 = null;
  public TileTemplate tileGraphicColoredTitle4X2 = null;
  public TileTemplate tileGraphicUpperLeftTitle2X1 = null;
  public TopChartsContainer topChartsContainer = null;
  public TrustedSourceContainer trustedSourceContainer = null;
  public VerticallyStackedCardContainer verticallyStackedCardContainer = null;
  public WarmWelcome warmWelcome = null;
  public WarmWelcome warmWelcomeV2 = null;
  public YoutubeVideoContainer youtubeVideoContainer = null;
  
  public Template()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.seriesAntenna != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(1, this.seriesAntenna);
    }
    if (this.tileGraphic2X1 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.tileGraphic2X1);
    }
    if (this.tileGraphic4X2 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.tileGraphic4X2);
    }
    if (this.tileGraphicColoredTitle2X1 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.tileGraphicColoredTitle2X1);
    }
    if (this.tileGraphicUpperLeftTitle2X1 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(5, this.tileGraphicUpperLeftTitle2X1);
    }
    if (this.tileDetailsReflectedGraphic2X2 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(6, this.tileDetailsReflectedGraphic2X2);
    }
    if (this.tileFourBlock4X2 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.tileFourBlock4X2);
    }
    if (this.containerWithBanner != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(8, this.containerWithBanner);
    }
    if (this.dealOfTheDay != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.dealOfTheDay);
    }
    if (this.tileGraphicColoredTitle4X2 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(10, this.tileGraphicColoredTitle4X2);
    }
    if (this.editorialSeriesContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(11, this.editorialSeriesContainer);
    }
    if (this.recommendationsContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(12, this.recommendationsContainer);
    }
    if (this.nextBanner != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(13, this.nextBanner);
    }
    if (this.rateContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(14, this.rateContainer);
    }
    if (this.addToCirclesContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(15, this.addToCirclesContainer);
    }
    if (this.trustedSourceContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(16, this.trustedSourceContainer);
    }
    if (this.rateAndSuggestContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(17, this.rateAndSuggestContainer);
    }
    if (this.actionBanner != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(18, this.actionBanner);
    }
    if (this.warmWelcome != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(19, this.warmWelcome);
    }
    if (this.recommendationsContainerWithHeader != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(20, this.recommendationsContainerWithHeader);
    }
    if (this.emptyContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(21, this.emptyContainer);
    }
    if (this.myCirclesContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(22, this.myCirclesContainer);
    }
    if (this.singleCardContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(23, this.singleCardContainer);
    }
    if (this.moreByCreatorContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(24, this.moreByCreatorContainer);
    }
    if (this.purchaseHistoryContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(25, this.purchaseHistoryContainer);
    }
    if (this.snow != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(26, this.snow);
    }
    if (this.multiRowContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(28, this.multiRowContainer);
    }
    if (this.avatarCardContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(30, this.avatarCardContainer);
    }
    if (this.bundleBanner != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(31, this.bundleBanner);
    }
    if (this.bundleContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(32, this.bundleContainer);
    }
    if (this.featuredAppsContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(33, this.featuredAppsContainer);
    }
    if (this.artistClusterContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(34, this.artistClusterContainer);
    }
    if (this.bannerWithContentContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(35, this.bannerWithContentContainer);
    }
    if (this.highlightsContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(36, this.highlightsContainer);
    }
    if (this.newsArticleContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(37, this.newsArticleContainer);
    }
    if (this.friendReviewContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(38, this.friendReviewContainer);
    }
    if (this.warmWelcomeV2 != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(39, this.warmWelcomeV2);
    }
    if (this.containerWithNotice != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(40, this.containerWithNotice);
    }
    if (this.highlightsEditorialContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(41, this.highlightsEditorialContainer);
    }
    if (this.playDaily != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(42, this.playDaily);
    }
    if (this.topChartsContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(43, this.topChartsContainer);
    }
    if (this.youtubeVideoContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(44, this.youtubeVideoContainer);
    }
    if (this.verticallyStackedCardContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(45, this.verticallyStackedCardContainer);
    }
    if (this.mediumCardContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(46, this.mediumCardContainer);
    }
    if (this.listViewContainer != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(47, this.listViewContainer);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.seriesAntenna != null) {
      paramCodedOutputByteBufferNano.writeMessage(1, this.seriesAntenna);
    }
    if (this.tileGraphic2X1 != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.tileGraphic2X1);
    }
    if (this.tileGraphic4X2 != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.tileGraphic4X2);
    }
    if (this.tileGraphicColoredTitle2X1 != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.tileGraphicColoredTitle2X1);
    }
    if (this.tileGraphicUpperLeftTitle2X1 != null) {
      paramCodedOutputByteBufferNano.writeMessage(5, this.tileGraphicUpperLeftTitle2X1);
    }
    if (this.tileDetailsReflectedGraphic2X2 != null) {
      paramCodedOutputByteBufferNano.writeMessage(6, this.tileDetailsReflectedGraphic2X2);
    }
    if (this.tileFourBlock4X2 != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.tileFourBlock4X2);
    }
    if (this.containerWithBanner != null) {
      paramCodedOutputByteBufferNano.writeMessage(8, this.containerWithBanner);
    }
    if (this.dealOfTheDay != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.dealOfTheDay);
    }
    if (this.tileGraphicColoredTitle4X2 != null) {
      paramCodedOutputByteBufferNano.writeMessage(10, this.tileGraphicColoredTitle4X2);
    }
    if (this.editorialSeriesContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(11, this.editorialSeriesContainer);
    }
    if (this.recommendationsContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(12, this.recommendationsContainer);
    }
    if (this.nextBanner != null) {
      paramCodedOutputByteBufferNano.writeMessage(13, this.nextBanner);
    }
    if (this.rateContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(14, this.rateContainer);
    }
    if (this.addToCirclesContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(15, this.addToCirclesContainer);
    }
    if (this.trustedSourceContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(16, this.trustedSourceContainer);
    }
    if (this.rateAndSuggestContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(17, this.rateAndSuggestContainer);
    }
    if (this.actionBanner != null) {
      paramCodedOutputByteBufferNano.writeMessage(18, this.actionBanner);
    }
    if (this.warmWelcome != null) {
      paramCodedOutputByteBufferNano.writeMessage(19, this.warmWelcome);
    }
    if (this.recommendationsContainerWithHeader != null) {
      paramCodedOutputByteBufferNano.writeMessage(20, this.recommendationsContainerWithHeader);
    }
    if (this.emptyContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(21, this.emptyContainer);
    }
    if (this.myCirclesContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(22, this.myCirclesContainer);
    }
    if (this.singleCardContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(23, this.singleCardContainer);
    }
    if (this.moreByCreatorContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(24, this.moreByCreatorContainer);
    }
    if (this.purchaseHistoryContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(25, this.purchaseHistoryContainer);
    }
    if (this.snow != null) {
      paramCodedOutputByteBufferNano.writeMessage(26, this.snow);
    }
    if (this.multiRowContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(28, this.multiRowContainer);
    }
    if (this.avatarCardContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(30, this.avatarCardContainer);
    }
    if (this.bundleBanner != null) {
      paramCodedOutputByteBufferNano.writeMessage(31, this.bundleBanner);
    }
    if (this.bundleContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(32, this.bundleContainer);
    }
    if (this.featuredAppsContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(33, this.featuredAppsContainer);
    }
    if (this.artistClusterContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(34, this.artistClusterContainer);
    }
    if (this.bannerWithContentContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(35, this.bannerWithContentContainer);
    }
    if (this.highlightsContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(36, this.highlightsContainer);
    }
    if (this.newsArticleContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(37, this.newsArticleContainer);
    }
    if (this.friendReviewContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(38, this.friendReviewContainer);
    }
    if (this.warmWelcomeV2 != null) {
      paramCodedOutputByteBufferNano.writeMessage(39, this.warmWelcomeV2);
    }
    if (this.containerWithNotice != null) {
      paramCodedOutputByteBufferNano.writeMessage(40, this.containerWithNotice);
    }
    if (this.highlightsEditorialContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(41, this.highlightsEditorialContainer);
    }
    if (this.playDaily != null) {
      paramCodedOutputByteBufferNano.writeMessage(42, this.playDaily);
    }
    if (this.topChartsContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(43, this.topChartsContainer);
    }
    if (this.youtubeVideoContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(44, this.youtubeVideoContainer);
    }
    if (this.verticallyStackedCardContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(45, this.verticallyStackedCardContainer);
    }
    if (this.mediumCardContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(46, this.mediumCardContainer);
    }
    if (this.listViewContainer != null) {
      paramCodedOutputByteBufferNano.writeMessage(47, this.listViewContainer);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Template
 * JD-Core Version:    0.7.0.1
 */