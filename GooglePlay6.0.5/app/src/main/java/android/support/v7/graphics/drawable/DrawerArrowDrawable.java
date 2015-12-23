package android.support.v7.graphics.drawable;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.style;
import android.support.v7.appcompat.R.styleable;

public class DrawerArrowDrawable
  extends Drawable
{
  private static final float ARROW_HEAD_ANGLE = (float)Math.toRadians(45.0D);
  private float mArrowHeadLength;
  private float mArrowShaftLength;
  private float mBarGap;
  private float mBarLength;
  private int mDirection = 2;
  private float mMaxCutForBarSize;
  private final Paint mPaint = new Paint();
  private final Path mPath = new Path();
  public float mProgress;
  private final int mSize;
  private boolean mSpin;
  private boolean mVerticalMirror = false;
  
  public DrawerArrowDrawable(Context paramContext)
  {
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeJoin(Paint.Join.MITER);
    this.mPaint.setStrokeCap(Paint.Cap.BUTT);
    this.mPaint.setAntiAlias(true);
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
    int i = localTypedArray.getColor(R.styleable.DrawerArrowToggle_color, 0);
    if (i != this.mPaint.getColor())
    {
      this.mPaint.setColor(i);
      invalidateSelf();
    }
    float f1 = localTypedArray.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0F);
    if (this.mPaint.getStrokeWidth() != f1)
    {
      this.mPaint.setStrokeWidth(f1);
      this.mMaxCutForBarSize = ((float)(f1 / 2.0F * Math.cos(ARROW_HEAD_ANGLE)));
      invalidateSelf();
    }
    boolean bool = localTypedArray.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true);
    if (this.mSpin != bool)
    {
      this.mSpin = bool;
      invalidateSelf();
    }
    float f2 = Math.round(localTypedArray.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0F));
    if (f2 != this.mBarGap)
    {
      this.mBarGap = f2;
      invalidateSelf();
    }
    this.mSize = localTypedArray.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
    this.mBarLength = Math.round(localTypedArray.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0F));
    this.mArrowHeadLength = Math.round(localTypedArray.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0F));
    this.mArrowShaftLength = localTypedArray.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0F);
    localTypedArray.recycle();
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    int i;
    float f11;
    label164:
    float f12;
    label172:
    int j;
    switch (this.mDirection)
    {
    case 2: 
    default: 
      if (DrawableCompat.getLayoutDirection(this) == 1)
      {
        i = 1;
        float f1 = (float)Math.sqrt(2.0F * (this.mArrowHeadLength * this.mArrowHeadLength));
        float f2 = this.mBarLength;
        float f3 = f2 + this.mProgress * (f1 - f2);
        float f4 = this.mBarLength;
        float f5 = this.mArrowShaftLength;
        float f6 = f4 + this.mProgress * (f5 - f4);
        float f7 = this.mMaxCutForBarSize;
        float f8 = Math.round(0.0F + this.mProgress * (f7 - 0.0F));
        float f9 = ARROW_HEAD_ANGLE;
        float f10 = 0.0F + this.mProgress * (f9 - 0.0F);
        if (i == 0) {
          break label495;
        }
        f11 = 0.0F;
        if (i == 0) {
          break label503;
        }
        f12 = 180.0F;
        float f13 = f11 + this.mProgress * (f12 - f11);
        float f14 = (float)Math.round(f3 * Math.cos(f10));
        float f15 = (float)Math.round(f3 * Math.sin(f10));
        this.mPath.rewind();
        float f16 = this.mBarGap + this.mPaint.getStrokeWidth();
        float f17 = -this.mMaxCutForBarSize;
        float f18 = f16 + this.mProgress * (f17 - f16);
        float f19 = -f6 / 2.0F;
        this.mPath.moveTo(f19 + f8, 0.0F);
        this.mPath.rLineTo(f6 - 2.0F * f8, 0.0F);
        this.mPath.moveTo(f19, f18);
        this.mPath.rLineTo(f14, f15);
        this.mPath.moveTo(f19, -f18);
        this.mPath.rLineTo(f14, -f15);
        this.mPath.close();
        paramCanvas.save();
        float f20 = this.mPaint.getStrokeWidth();
        float f21 = (float)(2 * ((int)(localRect.height() - 3.0F * f20 - 2.0F * this.mBarGap) / 4) + (1.5D * f20 + this.mBarGap));
        paramCanvas.translate(localRect.centerX(), f21);
        if (!this.mSpin) {
          break label515;
        }
        if ((i ^ this.mVerticalMirror) == 0) {
          break label509;
        }
        j = -1;
        label436:
        paramCanvas.rotate(f13 * j);
      }
      break;
    }
    for (;;)
    {
      paramCanvas.drawPath(this.mPath, this.mPaint);
      paramCanvas.restore();
      return;
      i = 0;
      break;
      i = 1;
      break;
      if (DrawableCompat.getLayoutDirection(this) == 0) {}
      for (i = 1;; i = 0) {
        break;
      }
      i = 0;
      break;
      label495:
      f11 = -180.0F;
      break label164;
      label503:
      f12 = 0.0F;
      break label172;
      label509:
      j = 1;
      break label436;
      label515:
      if (i != 0) {
        paramCanvas.rotate(180.0F);
      }
    }
  }
  
  public int getIntrinsicHeight()
  {
    return this.mSize;
  }
  
  public int getIntrinsicWidth()
  {
    return this.mSize;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public void setAlpha(int paramInt)
  {
    if (paramInt != this.mPaint.getAlpha())
    {
      this.mPaint.setAlpha(paramInt);
      invalidateSelf();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public final void setProgress(float paramFloat)
  {
    if (this.mProgress != paramFloat)
    {
      this.mProgress = paramFloat;
      invalidateSelf();
    }
  }
  
  public final void setVerticalMirror(boolean paramBoolean)
  {
    if (this.mVerticalMirror != paramBoolean)
    {
      this.mVerticalMirror = paramBoolean;
      invalidateSelf();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.graphics.drawable.DrawerArrowDrawable
 * JD-Core Version:    0.7.0.1
 */