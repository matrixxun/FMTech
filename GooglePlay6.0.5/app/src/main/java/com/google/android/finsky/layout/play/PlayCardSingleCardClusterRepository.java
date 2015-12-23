package com.google.android.finsky.layout.play;

import android.util.SparseArray;

public final class PlayCardSingleCardClusterRepository
{
  public static final PlayCardClusterMetadata.CardMetadata SINGLE_CARD_COL2;
  public static final PlayCardClusterMetadata.CardMetadata SINGLE_CARD_COL2_16x9;
  public static final PlayCardClusterMetadata.CardMetadata SINGLE_CARD_COL3;
  public static final PlayCardClusterMetadata.CardMetadata SINGLE_CARD_COL3_16x9;
  private static final SparseArray<PlayCardSingleCardClusterMetadata> sClusters16x9;
  private static final SparseArray<PlayCardSingleCardClusterMetadata> sClusters1x1 = new SparseArray();
  
  static
  {
    sClusters16x9 = new SparseArray();
    SINGLE_CARD_COL2 = new PlayCardClusterMetadata.CardMetadata(2130968953, 4, 2, 1.0F);
    SINGLE_CARD_COL3 = new PlayCardClusterMetadata.CardMetadata(2130968953, 6, 2, 1.0F);
    SINGLE_CARD_COL2_16x9 = new PlayCardClusterMetadata.CardMetadata(2130968953, 4, 3, 1.441F);
    SINGLE_CARD_COL3_16x9 = new PlayCardClusterMetadata.CardMetadata(2130968953, 6, 3, 1.441F);
    SparseArray localSparseArray1 = sClusters1x1;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata1 = new PlayCardSingleCardClusterMetadata(4, 2);
    localPlayCardSingleCardClusterMetadata1.mMerchImageHSpan = 0;
    localSparseArray1.append(0, localPlayCardSingleCardClusterMetadata1.addTile(SINGLE_CARD_COL2, 0, 0));
    SparseArray localSparseArray2 = sClusters1x1;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata2 = new PlayCardSingleCardClusterMetadata(6, 2);
    localPlayCardSingleCardClusterMetadata2.mMerchImageHSpan = 2;
    localSparseArray2.append(1, localPlayCardSingleCardClusterMetadata2.addTile(SINGLE_CARD_COL2, 0, 0));
    SparseArray localSparseArray3 = sClusters1x1;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata3 = new PlayCardSingleCardClusterMetadata(8, 2);
    localPlayCardSingleCardClusterMetadata3.mMerchImageHSpan = 2;
    localSparseArray3.append(3, localPlayCardSingleCardClusterMetadata3.addTile(SINGLE_CARD_COL3, 0, 0));
    SparseArray localSparseArray4 = sClusters1x1;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata4 = new PlayCardSingleCardClusterMetadata(10, 2);
    localPlayCardSingleCardClusterMetadata4.mMerchImageHSpan = 4;
    localSparseArray4.append(5, localPlayCardSingleCardClusterMetadata4.addTile(SINGLE_CARD_COL3, 0, 0));
    SparseArray localSparseArray5 = sClusters16x9;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata5 = new PlayCardSingleCardClusterMetadata(4, 3);
    localPlayCardSingleCardClusterMetadata5.mMerchImageHSpan = 0;
    localSparseArray5.append(0, localPlayCardSingleCardClusterMetadata5.addTile(SINGLE_CARD_COL2_16x9, 0, 0));
    SparseArray localSparseArray6 = sClusters16x9;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata6 = new PlayCardSingleCardClusterMetadata(6, 3);
    localPlayCardSingleCardClusterMetadata6.mMerchImageHSpan = 2;
    localSparseArray6.append(1, localPlayCardSingleCardClusterMetadata6.addTile(SINGLE_CARD_COL2_16x9, 0, 0));
    SparseArray localSparseArray7 = sClusters16x9;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata7 = new PlayCardSingleCardClusterMetadata(8, 3);
    localPlayCardSingleCardClusterMetadata7.mMerchImageHSpan = 2;
    localSparseArray7.append(3, localPlayCardSingleCardClusterMetadata7.addTile(SINGLE_CARD_COL3_16x9, 0, 0));
    SparseArray localSparseArray8 = sClusters16x9;
    PlayCardSingleCardClusterMetadata localPlayCardSingleCardClusterMetadata8 = new PlayCardSingleCardClusterMetadata(10, 3);
    localPlayCardSingleCardClusterMetadata8.mMerchImageHSpan = 4;
    localSparseArray8.append(5, localPlayCardSingleCardClusterMetadata8.addTile(SINGLE_CARD_COL3_16x9, 0, 0));
  }
  
  public static PlayCardSingleCardClusterMetadata getMetadata(int paramInt1, int paramInt2)
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
      return (PlayCardSingleCardClusterMetadata)localSparseArray.get(PlayCardClusterRepository.getConfigurationKey(paramInt2, false));
      i = 0;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardSingleCardClusterRepository
 * JD-Core Version:    0.7.0.1
 */