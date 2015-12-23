package com.google.android.play.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.string;
import com.google.android.play.R.styleable;
import java.text.NumberFormat;

public class StarRatingBar
  extends View
{
  private static SparseArray<String> sFormattedRatings;
  private static NumberFormat sRatingDescriptionFormatter;
  private String mFormattedRating;
  private final float mGap;
  private final float mHalfStarWidth;
  private boolean mIsInCompactMode;
  private final Path mLeftHalfStarPath;
  private final double mRadius;
  private float mRange;
  private float mRating;
  private final Path mRightHalfStarPath;
  private final double mShortRadius;
  private boolean mShowEmptyStars;
  private final Paint mStarBackgroundPaint;
  private final double mStarHeight;
  private final Paint mStarPaint;
  private final Path mStarPath;
  private final int mTextBaseline;
  private final int mTextHeight;
  private final TextPaint mTextPaint;
  private int mTextSize;
  private float mTextWidth;
  private final PointF[] mVertices;
  
  public StarRatingBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public StarRatingBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.StarRatingBar);
    this.mGap = localTypedArray.getDimensionPixelSize(R.styleable.StarRatingBar_gap, 0);
    this.mRating = localTypedArray.getFloat(R.styleable.StarRatingBar_rating, 0.0F);
    this.mStarHeight = localTypedArray.getDimensionPixelSize(R.styleable.StarRatingBar_star_height, localResources.getDimensionPixelSize(R.dimen.play_star_height_default));
    this.mRange = localTypedArray.getInt(R.styleable.StarRatingBar_range, 5);
    this.mShowEmptyStars = localTypedArray.getBoolean(R.styleable.StarRatingBar_show_empty_stars, true);
    this.mIsInCompactMode = localTypedArray.getBoolean(R.styleable.StarRatingBar_compact_mode, false);
    this.mTextSize = localTypedArray.getDimensionPixelSize(R.styleable.StarRatingBar_text_size, localResources.getDimensionPixelSize(R.dimen.play_medium_size));
    int i = localTypedArray.getColor(R.styleable.StarRatingBar_star_color, localResources.getColor(R.color.play_white));
    int j = localTypedArray.getColor(R.styleable.StarRatingBar_star_bg_color, localResources.getColor(R.color.play_transparent));
    localTypedArray.recycle();
    this.mStarPaint = new Paint(1);
    this.mStarPaint.setColor(i);
    this.mStarPaint.setStyle(Paint.Style.FILL);
    this.mStarBackgroundPaint = new Paint(1);
    this.mStarBackgroundPaint.setColor(j);
    this.mStarBackgroundPaint.setStyle(Paint.Style.FILL);
    this.mStarPath = new Path();
    this.mStarPath.setFillType(Path.FillType.EVEN_ODD);
    this.mLeftHalfStarPath = new Path();
    this.mLeftHalfStarPath.setFillType(Path.FillType.EVEN_ODD);
    this.mRightHalfStarPath = new Path();
    this.mRightHalfStarPath.setFillType(Path.FillType.EVEN_ODD);
    this.mRadius = (this.mStarHeight / (1.0D + Math.sin(0.9424777960769379D)));
    this.mShortRadius = (Math.sin(0.3926990816987241D) * this.mRadius / Math.sin(2.12057504117311D));
    this.mHalfStarWidth = ((float)(this.mRadius * Math.sin(1.256637061435917D)));
    this.mVertices = new PointF[10];
    for (int k = 0; k < 10; k++) {
      this.mVertices[k] = new PointF();
    }
    this.mTextPaint = new TextPaint(1);
    this.mTextPaint.density = localResources.getDisplayMetrics().density;
    this.mTextPaint.setTextSize(this.mTextSize);
    this.mTextPaint.setFakeBoldText(false);
    Paint.FontMetrics localFontMetrics = this.mTextPaint.getFontMetrics();
    this.mTextHeight = ((int)(Math.abs(localFontMetrics.top) + Math.abs(localFontMetrics.bottom)));
    this.mTextBaseline = ((int)Math.abs(localFontMetrics.top));
    updateRatingDescription();
    this.mVertices[0].x = 0.0F;
    this.mVertices[0].y = (-1.0F * (float)this.mRadius);
    this.mVertices[1].x = ((float)(this.mShortRadius * Math.sin(0.6283185307179586D)));
    this.mVertices[1].y = (-1.0F * (float)(this.mShortRadius * Math.cos(0.6283185307179586D)));
    this.mVertices[2].x = ((float)(this.mRadius * Math.sin(1.256637061435917D)));
    this.mVertices[2].y = (-1.0F * (float)(this.mRadius * Math.cos(1.256637061435917D)));
    this.mVertices[3].x = ((float)(this.mShortRadius * Math.sin(1.256637061435917D)));
    this.mVertices[3].y = ((float)(this.mShortRadius * Math.cos(1.256637061435917D)));
    this.mVertices[4].x = ((float)(this.mRadius * Math.sin(0.6283185307179586D)));
    this.mVertices[4].y = ((float)((float)this.mRadius * Math.cos(0.6283185307179586D)));
    this.mVertices[5].x = 0.0F;
    this.mVertices[5].y = ((float)this.mShortRadius);
    this.mVertices[6].x = (-1.0F * this.mVertices[4].x);
    this.mVertices[6].y = this.mVertices[4].y;
    this.mVertices[7].x = (-1.0F * this.mVertices[3].x);
    this.mVertices[7].y = this.mVertices[3].y;
    this.mVertices[8].x = (-1.0F * this.mVertices[2].x);
    this.mVertices[8].y = this.mVertices[2].y;
    this.mVertices[9].x = (-1.0F * this.mVertices[1].x);
    this.mVertices[9].y = this.mVertices[1].y;
    initializeStarPaths();
    setWillNotDraw(false);
  }
  
  private int getNumOfHalfStars()
  {
    if (Float.compare(this.mRating % 1.0F, 0.0F) > 0) {
      return 1;
    }
    return 0;
  }
  
  private void initializeStarPaths()
  {
    this.mStarPath.reset();
    this.mStarPath.moveTo(this.mVertices[0].x, this.mVertices[0].y);
    for (int i = 1; i < this.mVertices.length; i++) {
      this.mStarPath.lineTo(this.mVertices[i].x, this.mVertices[i].y);
    }
    this.mStarPath.close();
    this.mLeftHalfStarPath.reset();
    this.mLeftHalfStarPath.moveTo(this.mVertices[0].x, this.mVertices[0].y);
    for (int j = 5; j < this.mVertices.length; j++) {
      this.mLeftHalfStarPath.lineTo(this.mVertices[j].x, this.mVertices[j].y);
    }
    this.mLeftHalfStarPath.close();
    this.mRightHalfStarPath.reset();
    this.mRightHalfStarPath.moveTo(this.mVertices[0].x, this.mVertices[0].y);
    for (int k = 1; k <= 5; k++) {
      this.mRightHalfStarPath.lineTo(this.mVertices[k].x, this.mVertices[k].y);
    }
    this.mRightHalfStarPath.close();
  }
  
  private void updateRatingDescription()
  {
    if (sRatingDescriptionFormatter == null)
    {
      NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
      sRatingDescriptionFormatter = localNumberFormat;
      localNumberFormat.setMinimumFractionDigits(1);
      sRatingDescriptionFormatter.setMaximumFractionDigits(1);
      sFormattedRatings = new SparseArray();
    }
    int i = Math.round(10.0F * this.mRating);
    this.mFormattedRating = ((String)sFormattedRatings.get(i));
    if (this.mFormattedRating == null)
    {
      this.mFormattedRating = sRatingDescriptionFormatter.format(this.mRating);
      sFormattedRatings.put(i, this.mFormattedRating);
    }
  }
  
  public int getBaseline()
  {
    if (this.mIsInCompactMode) {
      return getPaddingTop() + this.mTextBaseline;
    }
    return getMeasuredHeight();
  }
  
  public float getRating()
  {
    return this.mRating;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = paramCanvas.save(1);
    if ((ViewCompat.getLayoutDirection(this) == 1) && (!this.mIsInCompactMode))
    {
      paramCanvas.translate(getWidth(), 0.0F);
      paramCanvas.scale(-1.0F, 1.0F);
    }
    int j = getPaddingTop();
    int k = ViewCompat.getPaddingStart(this);
    if (this.mIsInCompactMode)
    {
      float f3 = k;
      float f4 = j;
      paramCanvas.save();
      paramCanvas.translate(f3, f4);
      paramCanvas.drawText(this.mFormattedRating, f3, f4 + this.mTextBaseline, this.mTextPaint);
      paramCanvas.restore();
      paramCanvas.save();
      paramCanvas.translate(f3 + (this.mHalfStarWidth + this.mTextWidth + 2.0F * this.mGap), j - getPaddingBottom() + getMeasuredHeight() - this.mTextHeight / 2 + (float)(this.mRadius - this.mHalfStarWidth));
      paramCanvas.drawPath(this.mStarPath, this.mStarPaint);
      paramCanvas.restore();
    }
    for (;;)
    {
      paramCanvas.restoreToCount(i);
      return;
      int m = (int)this.mRating;
      int n = getNumOfHalfStars();
      int i1 = 0;
      float f1 = k + this.mHalfStarWidth;
      float f2 = j + (float)this.mRadius;
      while (i1 < m)
      {
        paramCanvas.save();
        paramCanvas.translate(f1 + i1 * (2.0F * this.mHalfStarWidth + this.mGap), f2);
        paramCanvas.drawPath(this.mStarPath, this.mStarPaint);
        paramCanvas.restore();
        i1++;
      }
      if (n == 1)
      {
        paramCanvas.save();
        paramCanvas.translate(f1 + i1 * (2.0F * this.mHalfStarWidth + this.mGap), f2);
        paramCanvas.drawPath(this.mLeftHalfStarPath, this.mStarPaint);
        paramCanvas.drawPath(this.mRightHalfStarPath, this.mStarBackgroundPaint);
        paramCanvas.restore();
        i1++;
      }
      if (this.mShowEmptyStars) {
        while (i1 < this.mRange)
        {
          paramCanvas.save();
          paramCanvas.translate(f1 + i1 * (2.0F * this.mHalfStarWidth + this.mGap), f2);
          paramCanvas.drawPath(this.mStarPath, this.mStarBackgroundPaint);
          paramCanvas.restore();
          i1++;
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((View.MeasureSpec.getMode(paramInt1) == 1073741824) && (View.MeasureSpec.getMode(paramInt2) == 1073741824))
    {
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
      return;
    }
    int i;
    int j;
    if (this.mIsInCompactMode)
    {
      this.mTextWidth = this.mTextPaint.measureText(this.mFormattedRating);
      i = (int)(getPaddingLeft() + getPaddingRight() + this.mTextWidth + 2.0F * this.mGap + 2.0F * this.mHalfStarWidth);
      j = (int)(getPaddingTop() + getPaddingBottom() + Math.max(this.mTextHeight, this.mStarHeight));
      setMeasuredDimension(i, j);
      return;
    }
    if (this.mShowEmptyStars) {}
    for (float f = (int)this.mRange;; f = this.mRating + getNumOfHalfStars())
    {
      i = (int)(getPaddingLeft() + getPaddingRight() + f * 2.0F * this.mHalfStarWidth + (f - 1.0F) * this.mGap);
      j = (int)(getPaddingTop() + getPaddingBottom() + this.mStarHeight);
      break;
    }
  }
  
  public void setCompactMode(boolean paramBoolean)
  {
    if (this.mIsInCompactMode == paramBoolean) {
      return;
    }
    this.mIsInCompactMode = paramBoolean;
    requestLayout();
    invalidate();
  }
  
  public void setRating(float paramFloat)
  {
    if (this.mRating == paramFloat) {
      return;
    }
    this.mRating = paramFloat;
    updateRatingDescription();
    Resources localResources = getResources();
    int i = R.string.play_star_rating_content_description;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mFormattedRating;
    setContentDescription(localResources.getString(i, arrayOfObject));
    if ((this.mIsInCompactMode) || (!this.mShowEmptyStars)) {
      requestLayout();
    }
    invalidate();
  }
  
  public void setShowEmptyStars(boolean paramBoolean)
  {
    if (this.mShowEmptyStars == paramBoolean) {
      return;
    }
    this.mShowEmptyStars = paramBoolean;
    requestLayout();
    invalidate();
  }
  
  public void setTextSize(int paramInt)
  {
    if (this.mTextSize == paramInt) {
      return;
    }
    this.mTextSize = paramInt;
    requestLayout();
    invalidate();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.StarRatingBar
 * JD-Core Version:    0.7.0.1
 */