package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;

public class PlayCardViewArtist
  extends PlayCardViewBase
{
  public PlayCardViewArtist(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewArtist(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCardType()
  {
    return 7;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    int m = i - j - k - localMarginLayoutParams.leftMargin - localMarginLayoutParams.rightMargin;
    int n = (int)(this.mThumbnailAspectRatio * m);
    this.mThumbnail.getLayoutParams().height = n;
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewArtist
 * JD-Core Version:    0.7.0.1
 */