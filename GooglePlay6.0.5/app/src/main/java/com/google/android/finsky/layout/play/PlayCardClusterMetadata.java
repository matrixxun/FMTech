package com.google.android.finsky.layout.play;

import java.util.ArrayList;
import java.util.List;

public class PlayCardClusterMetadata
{
  public static final CardMetadata CARD_AVATAR = new CardMetadata(2130968919, 2, 3, 1.0F);
  public static final CardMetadata CARD_LARGE;
  public static final CardMetadata CARD_LARGEMINUS_16x9;
  public static final CardMetadata CARD_LARGE_16x9;
  public static final CardMetadata CARD_MEDIUM;
  public static final CardMetadata CARD_MEDIUMPLUS;
  public static final CardMetadata CARD_MEDIUMPLUS_16x9;
  public static final CardMetadata CARD_MEDIUM_16x9;
  public static final CardMetadata CARD_MINI = new CardMetadata(2130968939, 2, 3, 1.0F);
  public static final CardMetadata CARD_MINI_16x9;
  public static final CardMetadata CARD_PERSON;
  public static final CardMetadata CARD_SMALL = new CardMetadata(2130968954, 2, 3, 1.0F);
  public static final CardMetadata CARD_SMALL_16x9;
  boolean mAlignToParentEndIfNecessary;
  CardMetadata mCardMetadataForMinCellHeight;
  int mHeight;
  int mLeadingGap;
  boolean mRespectChildHeight;
  boolean mRespectChildThumbnailAspectRatio;
  List<ClusterTileMetadata> mTiles = new ArrayList();
  int mViewportWidth;
  int mWidth;
  
  static
  {
    CARD_MEDIUM = new CardMetadata(2130968935, 4, 2, 1.0F);
    CARD_MEDIUMPLUS = new CardMetadata(2130968936, 4, 3, 1.0F);
    CARD_LARGE = new CardMetadata(2130968933, 4, 6, 1.0F);
    CARD_MINI_16x9 = new CardMetadata(2130968939, 2, 4, 1.441F);
    CARD_SMALL_16x9 = new CardMetadata(2130968954, 2, 4, 1.441F);
    CARD_MEDIUM_16x9 = new CardMetadata(2130968935, 4, 2, 1.441F);
    CARD_MEDIUMPLUS_16x9 = new CardMetadata(2130968936, 4, 4, 1.441F);
    CARD_LARGEMINUS_16x9 = new CardMetadata(2130968934, 6, 4, 1.441F);
    CARD_LARGE_16x9 = new CardMetadata(2130968933, 4, 8, 1.441F);
    CARD_PERSON = new CardMetadata(2130968944, 2, 3, 1.0F);
  }
  
  public PlayCardClusterMetadata(int paramInt1, int paramInt2)
  {
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
    this.mViewportWidth = paramInt1;
  }
  
  public static float getAspectRatio(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 1.441F;
    case 3: 
      return 0.5F;
    }
    return 1.0F;
  }
  
  public final PlayCardClusterMetadata addFlexiTile(CardMetadata paramCardMetadata, int paramInt1, int paramInt2)
  {
    this.mTiles.add(new ClusterTileMetadata(paramCardMetadata, paramInt1, paramInt2, true));
    return this;
  }
  
  public PlayCardClusterMetadata addTile(CardMetadata paramCardMetadata, int paramInt1, int paramInt2)
  {
    this.mTiles.add(new ClusterTileMetadata(paramCardMetadata, paramInt1, paramInt2, false));
    return this;
  }
  
  public final int getTileCount()
  {
    return this.mTiles.size();
  }
  
  public final ClusterTileMetadata getTileMetadata(int paramInt)
  {
    return (ClusterTileMetadata)this.mTiles.get(paramInt);
  }
  
  public int getTrailingGap()
  {
    return 0;
  }
  
  public static final class CardMetadata
  {
    final int mHSpan;
    final int mLayoutId;
    final float mThumbnailAspectRatio;
    final int mVSpan;
    
    public CardMetadata(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
    {
      this.mLayoutId = paramInt1;
      this.mHSpan = paramInt2;
      this.mVSpan = paramInt3;
      this.mThumbnailAspectRatio = paramFloat;
    }
  }
  
  public static final class ClusterTileMetadata
  {
    final PlayCardClusterMetadata.CardMetadata mCardMetadata;
    final boolean mRespectChildThumbnailAspectRatio;
    final int mXStart;
    final int mYStart;
    
    public ClusterTileMetadata(PlayCardClusterMetadata.CardMetadata paramCardMetadata, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.mCardMetadata = paramCardMetadata;
      this.mXStart = paramInt1;
      this.mYStart = paramInt2;
      this.mRespectChildThumbnailAspectRatio = paramBoolean;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterMetadata
 * JD-Core Version:    0.7.0.1
 */