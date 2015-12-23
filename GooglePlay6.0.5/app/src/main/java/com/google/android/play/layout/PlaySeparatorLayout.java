package com.google.android.play.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.styleable;

public class PlaySeparatorLayout
  extends RelativeLayout
{
  private final int mHalfSeparatorThickness;
  private final int mSeparatorPaddingBottom;
  private final int mSeparatorPaddingLeft;
  private final int mSeparatorPaddingRight;
  private final int mSeparatorPaddingTop;
  private final Paint mSeparatorPaint;
  private final int mSeparatorThickness;
  private boolean mSeparatorVisible;
  
  public PlaySeparatorLayout(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PlaySeparatorLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlaySeparatorLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setWillNotDraw(false);
    this.mSeparatorVisible = false;
    Resources localResources = paramContext.getResources();
    this.mSeparatorThickness = localResources.getDimensionPixelSize(R.dimen.play_hairline_separator_thickness);
    this.mHalfSeparatorThickness = ((1 + this.mSeparatorThickness) / 2);
    this.mSeparatorPaint = new Paint();
    this.mSeparatorPaint.setColor(localResources.getColor(R.color.play_reason_separator));
    this.mSeparatorPaint.setStrokeWidth(this.mSeparatorThickness);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlaySeparatorLayout);
    this.mSeparatorPaddingTop = localTypedArray.getDimensionPixelSize(R.styleable.PlaySeparatorLayout_separator_padding_top, 0);
    this.mSeparatorPaddingBottom = localTypedArray.getDimensionPixelSize(R.styleable.PlaySeparatorLayout_separator_padding_bottom, 0);
    this.mSeparatorPaddingLeft = localTypedArray.getDimensionPixelSize(R.styleable.PlaySeparatorLayout_separator_padding_left, 0);
    this.mSeparatorPaddingRight = localTypedArray.getDimensionPixelSize(R.styleable.PlaySeparatorLayout_separator_padding_right, 0);
    localTypedArray.recycle();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mSeparatorVisible)
    {
      int i = this.mSeparatorPaddingTop + this.mHalfSeparatorThickness;
      paramCanvas.drawLine(this.mSeparatorPaddingLeft, i, getWidth() - this.mSeparatorPaddingRight, i, this.mSeparatorPaint);
    }
  }
  
  public void setSeparatorVisible(boolean paramBoolean)
  {
    if (this.mSeparatorVisible == paramBoolean) {
      return;
    }
    this.mSeparatorVisible = paramBoolean;
    if (paramBoolean) {}
    for (int i = this.mSeparatorPaddingTop + this.mSeparatorPaddingBottom + this.mSeparatorThickness;; i = 0)
    {
      ViewCompat.setPaddingRelative(this, 0, i, 0, 0);
      invalidate();
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlaySeparatorLayout
 * JD-Core Version:    0.7.0.1
 */