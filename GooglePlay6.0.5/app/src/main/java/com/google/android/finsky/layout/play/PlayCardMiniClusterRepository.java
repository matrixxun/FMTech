package com.google.android.finsky.layout.play;

import android.util.SparseArray;

public final class PlayCardMiniClusterRepository
{
  private static final SparseArray<PlayCardClusterMetadata> sClusters16x9;
  private static final SparseArray<PlayCardClusterMetadata> sClusters1x1 = new SparseArray();
  
  static
  {
    sClusters16x9 = new SparseArray();
    SparseArray localSparseArray1 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(6, 3);
    localPlayCardClusterMetadata1.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata1.mRespectChildHeight = true;
    localSparseArray1.append(1, localPlayCardClusterMetadata1.addTile(PlayCardClusterMetadata.CARD_MINI, 0, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 2, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 4, 0));
    SparseArray localSparseArray2 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata2.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata2.mRespectChildHeight = true;
    localSparseArray2.append(3, localPlayCardClusterMetadata2.addTile(PlayCardClusterMetadata.CARD_MINI, 0, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 2, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 4, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 6, 0));
    SparseArray localSparseArray3 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(6, 4);
    localPlayCardClusterMetadata3.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata3.mRespectChildHeight = true;
    localSparseArray3.append(1, localPlayCardClusterMetadata3.addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 4, 0));
    SparseArray localSparseArray4 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = new PlayCardClusterMetadata(8, 4);
    localPlayCardClusterMetadata4.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata4.mRespectChildHeight = true;
    localSparseArray4.append(3, localPlayCardClusterMetadata4.addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 6, 0));
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt1, int paramInt2)
  {
    int i;
    if (PlayCardClusterMetadata.getAspectRatio(paramInt1) == 1.0F)
    {
      i = 1;
      if (i == 0) {
        break label37;
      }
    }
    label37:
    for (SparseArray localSparseArray = sClusters1x1;; localSparseArray = sClusters16x9)
    {
      return (PlayCardClusterMetadata)localSparseArray.get(PlayCardClusterRepository.getConfigurationKey(paramInt2, false));
      i = 0;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardMiniClusterRepository
 * JD-Core Version:    0.7.0.1
 */