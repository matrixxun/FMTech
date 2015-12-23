package com.google.android.finsky.layout.play;

public final class PlayCardRateAndSuggestClusterRepository
{
  private static final PlayCardClusterMetadata[] sClusters16x9;
  private static final PlayCardClusterMetadata[] sClusters1x1 = new PlayCardClusterMetadata[6];
  
  static
  {
    sClusters16x9 = new PlayCardClusterMetadata[6];
    PlayCardClusterMetadata.CardMetadata localCardMetadata1 = new PlayCardClusterMetadata.CardMetadata(2130968949, 6, 3, 1.0F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata2 = new PlayCardClusterMetadata.CardMetadata(2130968949, 6, 4, 1.441F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata3 = new PlayCardClusterMetadata.CardMetadata(2130968947, 2, 3, 1.0F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata4 = new PlayCardClusterMetadata.CardMetadata(2130968947, 2, 4, 1.441F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata5 = new PlayCardClusterMetadata.CardMetadata(2130968949, 4, 3, 1.0F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata6 = new PlayCardClusterMetadata.CardMetadata(2130968949, 4, 4, 1.441F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata7 = new PlayCardClusterMetadata.CardMetadata(2130968948, 2, 3, 1.0F);
    PlayCardClusterMetadata.CardMetadata localCardMetadata8 = new PlayCardClusterMetadata.CardMetadata(2130968948, 2, 4, 1.441F);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata1 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(12, 3);
    localPlayCardClusterMetadata1.mViewportWidth = 6;
    arrayOfPlayCardClusterMetadata1[0] = localPlayCardClusterMetadata1.addTile(localCardMetadata1, 0, 0).addTile(localCardMetadata3, 6, 0).addTile(localCardMetadata3, 8, 0).addTile(localCardMetadata3, 10, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata2 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = new PlayCardClusterMetadata(14, 3);
    localPlayCardClusterMetadata2.mViewportWidth = 8;
    arrayOfPlayCardClusterMetadata2[1] = localPlayCardClusterMetadata2.addTile(localCardMetadata1, 0, 0).addTile(localCardMetadata3, 6, 0).addTile(localCardMetadata3, 8, 0).addTile(localCardMetadata3, 10, 0).addTile(localCardMetadata3, 12, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata3 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(10, 3);
    localPlayCardClusterMetadata3.mViewportWidth = 6;
    arrayOfPlayCardClusterMetadata3[2] = localPlayCardClusterMetadata3.addTile(localCardMetadata5, 0, 0).addTile(localCardMetadata7, 4, 0).addTile(localCardMetadata7, 6, 0).addTile(localCardMetadata7, 8, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata4 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = new PlayCardClusterMetadata(12, 3);
    localPlayCardClusterMetadata4.mViewportWidth = 8;
    arrayOfPlayCardClusterMetadata4[3] = localPlayCardClusterMetadata4.addTile(localCardMetadata5, 0, 0).addTile(localCardMetadata7, 4, 0).addTile(localCardMetadata7, 6, 0).addTile(localCardMetadata7, 8, 0).addTile(localCardMetadata7, 10, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata5 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata5 = new PlayCardClusterMetadata(12, 3);
    localPlayCardClusterMetadata5.mViewportWidth = 8;
    arrayOfPlayCardClusterMetadata5[4] = localPlayCardClusterMetadata5.addTile(localCardMetadata5, 0, 0).addTile(localCardMetadata7, 4, 0).addTile(localCardMetadata7, 6, 0).addTile(localCardMetadata7, 8, 0).addTile(localCardMetadata7, 10, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata6 = sClusters1x1;
    PlayCardClusterMetadata localPlayCardClusterMetadata6 = new PlayCardClusterMetadata(14, 3);
    localPlayCardClusterMetadata6.mViewportWidth = 10;
    arrayOfPlayCardClusterMetadata6[5] = localPlayCardClusterMetadata6.addTile(localCardMetadata5, 0, 0).addTile(localCardMetadata7, 4, 0).addTile(localCardMetadata7, 6, 0).addTile(localCardMetadata7, 8, 0).addTile(localCardMetadata7, 10, 0).addTile(localCardMetadata7, 12, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata7 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata7 = new PlayCardClusterMetadata(12, 4);
    localPlayCardClusterMetadata7.mViewportWidth = 6;
    arrayOfPlayCardClusterMetadata7[0] = localPlayCardClusterMetadata7.addTile(localCardMetadata2, 0, 0).addTile(localCardMetadata4, 6, 0).addTile(localCardMetadata4, 8, 0).addTile(localCardMetadata4, 10, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata8 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata8 = new PlayCardClusterMetadata(14, 4);
    localPlayCardClusterMetadata8.mViewportWidth = 8;
    arrayOfPlayCardClusterMetadata8[1] = localPlayCardClusterMetadata8.addTile(localCardMetadata2, 0, 0).addTile(localCardMetadata4, 6, 0).addTile(localCardMetadata4, 8, 0).addTile(localCardMetadata4, 10, 0).addTile(localCardMetadata4, 12, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata9 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata9 = new PlayCardClusterMetadata(10, 4);
    localPlayCardClusterMetadata9.mViewportWidth = 6;
    arrayOfPlayCardClusterMetadata9[2] = localPlayCardClusterMetadata9.addTile(localCardMetadata6, 0, 0).addTile(localCardMetadata8, 4, 0).addTile(localCardMetadata8, 6, 0).addTile(localCardMetadata8, 8, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata10 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata10 = new PlayCardClusterMetadata(12, 4);
    localPlayCardClusterMetadata10.mViewportWidth = 8;
    arrayOfPlayCardClusterMetadata10[3] = localPlayCardClusterMetadata10.addTile(localCardMetadata6, 0, 0).addTile(localCardMetadata8, 4, 0).addTile(localCardMetadata8, 6, 0).addTile(localCardMetadata8, 8, 0).addTile(localCardMetadata8, 10, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata11 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata11 = new PlayCardClusterMetadata(12, 4);
    localPlayCardClusterMetadata11.mViewportWidth = 8;
    arrayOfPlayCardClusterMetadata11[4] = localPlayCardClusterMetadata11.addTile(localCardMetadata6, 0, 0).addTile(localCardMetadata8, 4, 0).addTile(localCardMetadata8, 6, 0).addTile(localCardMetadata8, 8, 0).addTile(localCardMetadata8, 10, 0);
    PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata12 = sClusters16x9;
    PlayCardClusterMetadata localPlayCardClusterMetadata12 = new PlayCardClusterMetadata(14, 4);
    localPlayCardClusterMetadata12.mViewportWidth = 10;
    arrayOfPlayCardClusterMetadata12[5] = localPlayCardClusterMetadata12.addTile(localCardMetadata6, 0, 0).addTile(localCardMetadata8, 4, 0).addTile(localCardMetadata8, 6, 0).addTile(localCardMetadata8, 8, 0).addTile(localCardMetadata8, 10, 0).addTile(localCardMetadata8, 12, 0);
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    if (PlayCardClusterMetadata.getAspectRatio(paramInt1) == 1.0F)
    {
      i = 1;
      if (i == 0) {
        break label34;
      }
    }
    label34:
    for (PlayCardClusterMetadata[] arrayOfPlayCardClusterMetadata = sClusters1x1;; arrayOfPlayCardClusterMetadata = sClusters16x9)
    {
      return arrayOfPlayCardClusterMetadata[PlayCardClusterRepository.getConfigurationKey(paramInt2, paramBoolean)];
      i = 0;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardRateAndSuggestClusterRepository
 * JD-Core Version:    0.7.0.1
 */