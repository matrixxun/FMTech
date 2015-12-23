package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class PlayCardViewRateOverlay
  extends View
{
  final TextPaint mRatingLabelPaint;
  final Rect mRatingLabelRect;
  String mRatingLabelText;
  final TextPaint mRatingStarsPaint;
  final Rect mRatingStarsRect;
  String mRatingStarsText;
  private final int mTopMargin;
  
  public PlayCardViewRateOverlay(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewRateOverlay(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mRatingStarsPaint = new TextPaint();
    this.mRatingStarsPaint.setAntiAlias(true);
    this.mRatingStarsPaint.setTextSize(localResources.getDimensionPixelSize(2131493492));
    if (Build.VERSION.SDK_INT >= 16) {
      this.mRatingStarsPaint.setTypeface(Typeface.create("sans-serif-light", 0));
    }
    this.mRatingLabelPaint = new TextPaint();
    this.mRatingLabelPaint.setAntiAlias(true);
    this.mRatingLabelPaint.setTextSize(localResources.getDimensionPixelSize(2131493490));
    this.mRatingLabelPaint.setTypeface(Typeface.create("sans-serif", 0));
    this.mRatingStarsRect = new Rect();
    this.mRatingLabelRect = new Rect();
    this.mTopMargin = localResources.getDimensionPixelSize(2131493491);
    setWillNotDraw(false);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    int k = this.mRatingStarsRect.height();
    int m = k + 2 * this.mRatingLabelRect.height();
    int n = k + (getPaddingTop() + this.mTopMargin + (j - m) / 2);
    if (!TextUtils.isEmpty(this.mRatingStarsText))
    {
      int i3 = (i - this.mRatingStarsRect.width()) / 2 - this.mRatingStarsRect.left;
      paramCanvas.drawText(this.mRatingStarsText, i3, n, this.mRatingStarsPaint);
    }
    if (!TextUtils.isEmpty(this.mRatingLabelText))
    {
      int i1 = n + 2 * this.mRatingLabelRect.height();
      int i2 = (i - this.mRatingLabelRect.width()) / 2 - this.mRatingLabelRect.left;
      paramCanvas.drawText(this.mRatingLabelText, i2, i1, this.mRatingLabelPaint);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewRateOverlay
 * JD-Core Version:    0.7.0.1
 */