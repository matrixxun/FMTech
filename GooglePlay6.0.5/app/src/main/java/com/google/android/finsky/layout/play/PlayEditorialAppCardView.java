package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayEditorialAppCardView
  extends PlayCardViewBase
  implements PlayCardImageTypeSequence
{
  public PlayEditorialAppCardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayEditorialAppCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCardType()
  {
    return 5;
  }
  
  public int[] getImageTypePreference()
  {
    return PlayCardImageTypeSequence.PROMO_IMAGE_TYPES;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mThumbnailAspectRatio = 0.4882813F;
    measureThumbnailSpanningWidth(paramInt1);
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayEditorialAppCardView
 * JD-Core Version:    0.7.0.1
 */