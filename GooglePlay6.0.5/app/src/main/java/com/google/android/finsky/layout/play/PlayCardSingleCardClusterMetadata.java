package com.google.android.finsky.layout.play;

public final class PlayCardSingleCardClusterMetadata
  extends PlayCardClusterMetadata
{
  int mMerchImageHSpan;
  
  public PlayCardSingleCardClusterMetadata(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }
  
  public final PlayCardSingleCardClusterMetadata addTile(PlayCardClusterMetadata.CardMetadata paramCardMetadata, int paramInt1, int paramInt2)
  {
    super.addTile(paramCardMetadata, paramInt1, paramInt2);
    return this;
  }
  
  public final int getTrailingGap()
  {
    return this.mMerchImageHSpan;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardSingleCardClusterMetadata
 * JD-Core Version:    0.7.0.1
 */