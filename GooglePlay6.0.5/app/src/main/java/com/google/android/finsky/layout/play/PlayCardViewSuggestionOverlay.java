package com.google.android.finsky.layout.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class PlayCardViewSuggestionOverlay
  extends View
{
  private final Drawable mOverlayDrawable;
  
  public PlayCardViewSuggestionOverlay(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewSuggestionOverlay(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mOverlayDrawable = ContextCompat.getDrawable(paramContext, 2130837776);
    setBackgroundResource(2130837616);
    setWillNotDraw(false);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    float f1 = 1.0F;
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    int k = this.mOverlayDrawable.getIntrinsicWidth();
    int m = this.mOverlayDrawable.getIntrinsicHeight();
    float f2;
    if (k <= i)
    {
      f2 = f1;
      if (m > j) {
        break label136;
      }
    }
    for (;;)
    {
      float f3 = Math.min(f2, f1);
      int n = (int)(f3 * k);
      int i1 = (int)(f3 * m);
      int i2 = (i - n) / 2;
      int i3 = (j - i1) / 2;
      this.mOverlayDrawable.setBounds(i2, i3, i2 + n, i3 + i1);
      this.mOverlayDrawable.draw(paramCanvas);
      return;
      f2 = i / k;
      break;
      label136:
      f1 = j / m;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewSuggestionOverlay
 * JD-Core Version:    0.7.0.1
 */