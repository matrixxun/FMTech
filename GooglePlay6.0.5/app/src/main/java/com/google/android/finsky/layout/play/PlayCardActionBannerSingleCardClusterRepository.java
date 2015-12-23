package com.google.android.finsky.layout.play;

public final class PlayCardActionBannerSingleCardClusterRepository
{
  private static final PlayCardClusterMetadata[] sClusters;
  
  static
  {
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata1 = new PlayCardClusterMetadata[6];
    sClusters = arrayOfPlayCardClusterMetadata1;
    arrayOfPlayCardClusterMetadata1[0] = new PlayCardClusterMetadata(4, 2).addTile(PlayCardClusterMetadata.CARD_MEDIUM, 0, 0);
    sClusters[1] = new PlayCardClusterMetadata(6, 2).addTile(PlayCardClusterMetadata.CARD_MEDIUM, 1, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata2 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(6, 0);
    localPlayCardClusterMetadata1.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = localPlayCardClusterMetadata1.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata2.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata2.mRespectChildHeight = true;
    localPlayCardClusterMetadata2.mAlignToParentEndIfNecessary = true;
    arrayOfPlayCardClusterMetadata2[2] = localPlayCardClusterMetadata2;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata3 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(8, 2);
    localPlayCardClusterMetadata3.mLeadingGap = 4;
    arrayOfPlayCardClusterMetadata3[3] = localPlayCardClusterMetadata3.addTile(PlayCardClusterMetadata.CARD_MEDIUM, 4, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata4 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = new PlayCardClusterMetadata(8, 2);
    localPlayCardClusterMetadata4.mLeadingGap = 4;
    arrayOfPlayCardClusterMetadata4[4] = localPlayCardClusterMetadata4.addTile(PlayCardClusterMetadata.CARD_MEDIUM, 4, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata5 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata5 = new PlayCardClusterMetadata(10, 2);
    localPlayCardClusterMetadata5.mLeadingGap = 4;
    arrayOfPlayCardClusterMetadata5[5] = localPlayCardClusterMetadata5.addTile(PlayCardClusterMetadata.CARD_MEDIUM, 5, 0);
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt, boolean paramBoolean)
  {
    return sClusters[PlayCardClusterRepository.getConfigurationKey(paramInt, paramBoolean)];
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardActionBannerSingleCardClusterRepository
 * JD-Core Version:    0.7.0.1
 */