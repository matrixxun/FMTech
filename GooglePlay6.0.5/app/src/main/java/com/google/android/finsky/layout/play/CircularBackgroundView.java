package com.google.android.finsky.layout.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import com.google.android.play.image.FifeImageView;

public class CircularBackgroundView
  extends FifeImageView
{
  private Paint mBackgroundPaint;
  
  public CircularBackgroundView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CircularBackgroundView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CircularBackgroundView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mBackgroundPaint != null) {
      paramCanvas.drawCircle((getRight() - getLeft()) / 2, (getBottom() - getTop()) / 2, Math.min(getWidth(), getHeight()) / 2, this.mBackgroundPaint);
    }
    super.onDraw(paramCanvas);
  }
  
  public void setBackgroundPaintColor(int paramInt)
  {
    if (this.mBackgroundPaint == null)
    {
      this.mBackgroundPaint = new Paint();
      this.mBackgroundPaint.setAntiAlias(true);
      this.mBackgroundPaint.setStyle(Paint.Style.FILL);
    }
    this.mBackgroundPaint.setColor(paramInt);
    setWillNotDraw(false);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.CircularBackgroundView
 * JD-Core Version:    0.7.0.1
 */