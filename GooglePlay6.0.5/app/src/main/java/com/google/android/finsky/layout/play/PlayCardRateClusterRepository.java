package com.google.android.finsky.layout.play;

public final class PlayCardRateClusterRepository
{
  private static final PlayCardClusterMetadata[] sClusters = new PlayCardClusterMetadata[6];
  
  static
  {
    PlayCardClusterMetadata.CardMetadata localCardMetadata1 = new PlayCardClusterMetadata.CardMetadata(2130968949, 4, 2, 1.0F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata2 = new PlayCardClusterMetadata.CardMetadata(2130968949, 6, 2, 1.0F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata3 = new PlayCardClusterMetadata.CardMetadata(2130968949, 5, 2, 1.0F);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata1 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(4, 2).addTile(localCardMetadata1, 0, 0).addTile(localCardMetadata1, 4, 0);
    localPlayCardClusterMetadata1.mRespectChildThumbnailAspectRatio = true;
    arrayOfPlayCardClusterMetadata1[0] = localPlayCardClusterMetadata1;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata2 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = new PlayCardClusterMetadata(6, 2).addTile(localCardMetadata2, 0, 0).addTile(localCardMetadata2, 6, 0);
    localPlayCardClusterMetadata2.mRespectChildThumbnailAspectRatio = true;
    arrayOfPlayCardClusterMetadata2[1] = localPlayCardClusterMetadata2;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata3 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(8, 2).addTile(localCardMetadata1, 0, 0).addTile(localCardMetadata1, 4, 0).addTile(localCardMetadata1, 8, 0);
    localPlayCardClusterMetadata3.mRespectChildThumbnailAspectRatio = true;
    arrayOfPlayCardClusterMetadata3[3] = localPlayCardClusterMetadata3;
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata4 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = new PlayCardClusterMetadata(10, 2).addTile(localCardMetadata3, 0, 0).addTile(localCardMetadata3, 5, 0).addTile(localCardMetadata3, 10, 0);
    localPlayCardClusterMetadata4.mRespectChildThumbnailAspectRatio = true;
    arrayOfPlayCardClusterMetadata4[5] = localPlayCardClusterMetadata4;
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt)
  {
    return sClusters[PlayCardClusterRepository.getConfigurationKey(paramInt, false)];
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardRateClusterRepository
 * JD-Core Version:    0.7.0.1
 */