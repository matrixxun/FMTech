package com.google.android.finsky.layout.play;

import android.util.SparseArray;

public final class PlayCardMerchClusterRepository
{
  private static final SparseArray<PlayCardClusterMetadata> sClusters16x9;
  private static final SparseArray<PlayCardClusterMetadata> sClusters1x1 = new SparseArray();
  
  static
  {
    sClusters16x9 = new SparseArray();
    SparseArray localSparseArray1 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(6, 3);
    localPlayCardClusterMetadata1.mLeadingGap = 2;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = localPlayCardClusterMetadata1.addTile(PlayCardClusterMetadata.CARD_MINI, 2, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 4, 0);
    localPlayCardClusterMetadata2.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata2.mRespectChildHeight = true;
    localPlayCardClusterMetadata2.mAlignToParentEndIfNecessary = true;
    localSparseArray1.append(0, localPlayCardClusterMetadata2);
    SparseArray localSparseArray2 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata3.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = localPlayCardClusterMetadata3.addTile(PlayCardClusterMetadata.CARD_MINI, 4, 0).addTile(PlayCardClusterMetadata.CARD_MINI, 6, 0);
    localPlayCardClusterMetadata4.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata4.mRespectChildHeight = true;
    localPlayCardClusterMetadata4.mAlignToParentEndIfNecessary = true;
    localSparseArray2.append(1, localPlayCardClusterMetadata4);
    SparseArray localSparseArray3 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata5 = new PlayCardClusterMetadata(6, 3);
    localPlayCardClusterMetadata5.mLeadingGap = 2;
    PlayCardClusterMetadata localPlayCardClusterMetadata6 = localPlayCardClusterMetadata5.addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata6.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata6.mRespectChildHeight = true;
    localPlayCardClusterMetadata6.mAlignToParentEndIfNecessary = true;
    localSparseArray3.append(2, localPlayCardClusterMetadata6);
    SparseArray localSparseArray4 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata7 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata7.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata8 = localPlayCardClusterMetadata7.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata8.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata8.mRespectChildHeight = true;
    localPlayCardClusterMetadata8.mAlignToParentEndIfNecessary = true;
    localSparseArray4.append(3, localPlayCardClusterMetadata8);
    SparseArray localSparseArray5 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata9 = new PlayCardClusterMetadata(8, 3);
    localPlayCardClusterMetadata9.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata10 = localPlayCardClusterMetadata9.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata10.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata10.mRespectChildHeight = true;
    localPlayCardClusterMetadata10.mAlignToParentEndIfNecessary = true;
    localSparseArray5.append(4, localPlayCardClusterMetadata10);
    SparseArray localSparseArray6 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata11 = new PlayCardClusterMetadata(10, 3);
    localPlayCardClusterMetadata11.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata12 = localPlayCardClusterMetadata11.addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata12.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata12.mRespectChildHeight = true;
    localPlayCardClusterMetadata12.mAlignToParentEndIfNecessary = true;
    localSparseArray6.append(5, localPlayCardClusterMetadata12);
    SparseArray localSparseArray7 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata13 = new PlayCardClusterMetadata(6, 4);
    localPlayCardClusterMetadata13.mLeadingGap = 2;
    PlayCardClusterMetadata localPlayCardClusterMetadata14 = localPlayCardClusterMetadata13.addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 4, 0);
    localPlayCardClusterMetadata14.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata14.mRespectChildHeight = true;
    localPlayCardClusterMetadata14.mAlignToParentEndIfNecessary = true;
    localSparseArray7.append(0, localPlayCardClusterMetadata14);
    SparseArray localSparseArray8 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata15 = new PlayCardClusterMetadata(8, 4);
    localPlayCardClusterMetadata15.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata16 = localPlayCardClusterMetadata15.addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_MINI_16x9, 6, 0);
    localPlayCardClusterMetadata16.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata16.mRespectChildHeight = true;
    localPlayCardClusterMetadata16.mAlignToParentEndIfNecessary = true;
    localSparseArray8.append(1, localPlayCardClusterMetadata16);
    SparseArray localSparseArray9 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata17 = new PlayCardClusterMetadata(6, 4);
    localPlayCardClusterMetadata17.mLeadingGap = 2;
    PlayCardClusterMetadata localPlayCardClusterMetadata18 = localPlayCardClusterMetadata17.addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata18.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata18.mRespectChildHeight = true;
    localPlayCardClusterMetadata18.mAlignToParentEndIfNecessary = true;
    localSparseArray9.append(2, localPlayCardClusterMetadata18);
    SparseArray localSparseArray10 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata19 = new PlayCardClusterMetadata(8, 4);
    localPlayCardClusterMetadata19.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata20 = localPlayCardClusterMetadata19.addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata20.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata20.mRespectChildHeight = true;
    localPlayCardClusterMetadata20.mAlignToParentEndIfNecessary = true;
    localSparseArray10.append(3, localPlayCardClusterMetadata20);
    SparseArray localSparseArray11 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata21 = new PlayCardClusterMetadata(8, 4);
    localPlayCardClusterMetadata21.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata22 = localPlayCardClusterMetadata21.addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata22.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata22.mRespectChildHeight = true;
    localPlayCardClusterMetadata22.mAlignToParentEndIfNecessary = true;
    localSparseArray11.append(4, localPlayCardClusterMetadata22);
    SparseArray localSparseArray12 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata23 = new PlayCardClusterMetadata(10, 4);
    localPlayCardClusterMetadata23.mLeadingGap = 4;
    PlayCardClusterMetadata localPlayCardClusterMetadata24 = localPlayCardClusterMetadata23.addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 8, 0);
    localPlayCardClusterMetadata24.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata24.mRespectChildHeight = true;
    localPlayCardClusterMetadata24.mAlignToParentEndIfNecessary = true;
    localSparseArray12.append(5, localPlayCardClusterMetadata24);
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    if (PlayCardClusterMetadata.getAspectRatio(paramInt1) == 1.0F)
    {
      i = 1;
      if (i == 0) {
        break label39;
      }
    }
    label39:
    for (SparseArray localSparseArray = sClusters1x1;; localSparseArray = sClusters16x9)
    {
      return (PlayCardClusterMetadata)localSparseArray.get(PlayCardClusterRepository.getConfigurationKey(paramInt2, paramBoolean));
      i = 0;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardMerchClusterRepository
 * JD-Core Version:    0.7.0.1
 */