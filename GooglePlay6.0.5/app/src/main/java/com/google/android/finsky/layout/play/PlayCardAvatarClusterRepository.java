package com.google.android.finsky.layout.play;

import android.util.SparseArray;

public final class PlayCardAvatarClusterRepository
{
  private static final SparseArray<PlayCardClusterMetadata> sClusters;
  
  static
  {
    SparseArray localSparseArray1 = new SparseArray();
    sClusters = localSparseArray1;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(6, 3);
    localPlayCardClusterMetadata1.mRespectChildHeight = true;
    localSparseArray1.append(0, localPlayCardClusterMetadata1.addTile(PlayCardClusterMetadata.CARD_AVATAR, 0, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 2, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 4, 0));
    SparseArray localSparseArray2 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata2.mRespectChildHeight = true;
    localSparseArray2.append(1, localPlayCardClusterMetadata2.addTile(PlayCardClusterMetadata.CARD_AVATAR, 0, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 2, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 4, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 6, 0));
    SparseArray localSparseArray3 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata3.mRespectChildHeight = true;
    localSparseArray3.append(2, localPlayCardClusterMetadata3.addTile(PlayCardClusterMetadata.CARD_AVATAR, 0, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 2, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 4, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 6, 0));
    SparseArray localSparseArray4 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata4.mRespectChildHeight = true;
    localSparseArray4.append(3, localPlayCardClusterMetadata4.addTile(PlayCardClusterMetadata.CARD_AVATAR, 0, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 2, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 4, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 6, 0));
    SparseArray localSparseArray5 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata5 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata5.mRespectChildHeight = true;
    localSparseArray5.append(4, localPlayCardClusterMetadata5.addTile(PlayCardClusterMetadata.CARD_AVATAR, 0, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 2, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 4, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 6, 0));
    SparseArray localSparseArray6 = sClusters;
    PlayCardClusterMetadata localPlayCardClusterMetadata6 = new PlayCardClusterMetadata(10, 3);
    localPlayCardClusterMetadata6.mRespectChildHeight = true;
    localSparseArray6.append(5, localPlayCardClusterMetadata6.addTile(PlayCardClusterMetadata.CARD_AVATAR, 0, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 2, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 4, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 6, 0).addTile(PlayCardClusterMetadata.CARD_AVATAR, 8, 0));
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt, boolean paramBoolean)
  {
    return (PlayCardClusterMetadata)sClusters.get(PlayCardClusterRepository.getConfigurationKey(paramInt, paramBoolean));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardAvatarClusterRepository
 * JD-Core Version:    0.7.0.1
 */