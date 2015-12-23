package com.google.android.finsky.layout.play;

public final class PlayCardActionBannerClusterRepository
{
  private static final PlayCardClusterMetadata[] sClusters;
  
  static
  {
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata1 = new PlayCardClusterMetadata[6];
    sClusters = arrayOfPlayCardClusterMetadata1;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0);
    localPlayCardClusterMetadata1.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata1.mRespectChildHeight = true;
    arrayOfPlayCardClusterMetadata1[0] = localPlayCardClusterMetadata1;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata2 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = new PlayCardClusterMetadata(6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata2.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata2.mRespectChildHeight = true;
    arrayOfPlayCardClusterMetadata2[1] = localPlayCardClusterMetadata2;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata3 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(6, 0);
    localPlayCardClusterMetadata3.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = localPlayCardClusterMetadata3.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata4.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata4.mRespectChildHeight = true;
    localPlayCardClusterMetadata4.mAlignToParentEndIfNecessary = true;
    arrayOfPlayCardClusterMetadata3[2] = localPlayCardClusterMetadata4;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata4 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata5 = new PlayCardClusterMetadata(8, 0);
    localPlayCardClusterMetadata5.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata6 = localPlayCardClusterMetadata5.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata6.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata6.mRespectChildHeight = true;
    arrayOfPlayCardClusterMetadata4[3] = localPlayCardClusterMetadata6;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata5 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata7 = new PlayCardClusterMetadata(8, 0);
    localPlayCardClusterMetadata7.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata8 = localPlayCardClusterMetadata7.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata8.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata8.mRespectChildHeight = true;
    localPlayCardClusterMetadata8.mAlignToParentEndIfNecessary = true;
    arrayOfPlayCardClusterMetadata5[4] = localPlayCardClusterMetadata8;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata6 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata9 = new PlayCardClusterMetadata(10, 0);
    localPlayCardClusterMetadata9.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata10 = localPlayCardClusterMetadata9.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata10.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata10.mRespectChildHeight = true;
    localPlayCardClusterMetadata10.mAlignToParentEndIfNecessary = true;
    arrayOfPlayCardClusterMetadata6[5] = localPlayCardClusterMetadata10;
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt, boolean paramBoolean)
  {
    return sClusters[PlayCardClusterRepository.getConfigurationKey(paramInt, paramBoolean)];
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardActionBannerClusterRepository
 * JD-Core Version:    0.7.0.1
 */