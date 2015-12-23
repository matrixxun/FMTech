package com.google.android.play.layout;

import android.content.Context;
import android.util.AttributeSet;

public class PlayCardViewMedium
  extends PlayCardViewBase
{
  public PlayCardViewMedium(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewMedium(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCardType()
  {
    return 2;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    measureThumbnailSpanningHeight(paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardViewMedium
 * JD-Core Version:    0.7.0.1
 */