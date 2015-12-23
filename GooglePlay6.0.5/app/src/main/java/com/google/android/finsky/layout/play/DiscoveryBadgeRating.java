package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.StarRatingBar;

public class DiscoveryBadgeRating
  extends DiscoveryBadgeBase
{
  private TextView mAverageValue;
  private TextView mCount;
  private int mFocusedOutlineColor;
  private Path mOctagonPath;
  private float mOutlineStrokeWidth;
  private Paint mPaint;
  private int mPressedFillColor;
  private int mPressedOutlineColor;
  private StarRatingBar mRatingBar;
  private PointF[] mVertices;
  private float mWhiteOctagonRadius;
  private int mWhiteOctagonStrokeWidth;
  
  public DiscoveryBadgeRating(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeRating(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getResources();
    this.mBadgeRadius = (localResources.getDimensionPixelSize(2131493007) / 2);
    this.mPaint = new Paint(1);
    this.mOctagonPath = new Path();
    this.mOctagonPath.setFillType(Path.FillType.EVEN_ODD);
    setWillNotDraw(false);
    this.mVertices = new PointF[8];
    for (int i = 0; i < 8; i++) {
      this.mVertices[i] = new PointF();
    }
    this.mWhiteOctagonStrokeWidth = localResources.getDimensionPixelSize(2131493299);
    this.mWhiteOctagonRadius = (this.mBadgeRadius - this.mWhiteOctagonStrokeWidth - this.mWhiteOctagonStrokeWidth / 2);
    this.mPressedFillColor = localResources.getColor(2131689475);
    this.mPressedOutlineColor = localResources.getColor(2131689476);
    this.mFocusedOutlineColor = localResources.getColor(2131689473);
    this.mOutlineStrokeWidth = (0.5F * localResources.getDimensionPixelSize(2131493377));
  }
  
  private void setOctagonPath(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    double d = Math.sin(0.7853981633974483D);
    this.mVertices[0].x = (-1.0F * paramFloat3);
    this.mVertices[0].y = 0.0F;
    this.mVertices[1].x = (-(int)(d * paramFloat3));
    this.mVertices[1].y = (-(int)(d * paramFloat3));
    this.mVertices[2].x = 0.0F;
    this.mVertices[2].y = (-paramFloat3);
    this.mVertices[3].x = ((int)(d * paramFloat3));
    this.mVertices[3].y = (-(int)(d * paramFloat3));
    this.mVertices[4].x = paramFloat3;
    this.mVertices[4].y = 0.0F;
    this.mVertices[5].x = ((int)(d * paramFloat3));
    this.mVertices[5].y = ((int)(d * paramFloat3));
    this.mVertices[6].x = 0.0F;
    this.mVertices[6].y = paramFloat3;
    this.mVertices[7].x = (-(int)(d * paramFloat3));
    this.mVertices[7].y = ((int)(d * paramFloat3));
    for (PointF localPointF : this.mVertices)
    {
      localPointF.x = (paramFloat1 + localPointF.x);
      localPointF.y = (paramFloat2 + localPointF.y);
    }
    this.mOctagonPath.reset();
    this.mOctagonPath.moveTo(this.mVertices[0].x, this.mVertices[0].y);
    for (int k = 1; k < this.mVertices.length; k++) {
      this.mOctagonPath.lineTo(this.mVertices[k].x, this.mVertices[k].y);
    }
    this.mOctagonPath.close();
  }
  
  public final void bind(Details.DiscoveryBadge paramDiscoveryBadge, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, DfeToc paramDfeToc, PackageManager paramPackageManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bind(paramDiscoveryBadge, paramBitmapLoader, paramNavigationManager, paramDocument, paramDfeToc, paramPackageManager, paramPlayStoreUiElementNode);
    String str = BadgeUtils.formatRating(paramDiscoveryBadge.aggregateRating);
    this.mAverageValue.setText(str);
    this.mRatingBar.setRating(BadgeUtils.roundRating(paramDiscoveryBadge.aggregateRating));
    StarRatingBar localStarRatingBar = this.mRatingBar;
    if (!BadgeUtils.shouldUseCompactStarRating()) {}
    for (boolean bool = true;; bool = false)
    {
      localStarRatingBar.setShowEmptyStars(bool);
      this.mCount.setText(paramDiscoveryBadge.title);
      this.mAverageValue.setContentDescription(null);
      return;
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    invalidate();
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1802;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i = getWidth() / 2;
    int j = this.mBadgeRadius;
    this.mPaint.setColor(this.mCurrentFillColor);
    this.mPaint.setStyle(Paint.Style.FILL);
    setOctagonPath(i, j, this.mBadgeRadius);
    paramCanvas.drawPath(this.mOctagonPath, this.mPaint);
    this.mPaint.setStrokeWidth(this.mWhiteOctagonStrokeWidth);
    this.mPaint.setColor(-1);
    this.mPaint.setStyle(Paint.Style.STROKE);
    setOctagonPath(i, j, this.mWhiteOctagonRadius);
    paramCanvas.drawPath(this.mOctagonPath, this.mPaint);
    int k;
    if ((isPressed()) && ((isDuplicateParentStateEnabled()) || (isClickable())))
    {
      k = 1;
      if (k == 0) {
        break label238;
      }
      setOctagonPath(i, j, this.mBadgeRadius);
      this.mPaint.setColor(this.mPressedFillColor);
      this.mPaint.setStyle(Paint.Style.FILL);
      paramCanvas.drawPath(this.mOctagonPath, this.mPaint);
      this.mPaint.setColor(this.mPressedOutlineColor);
      this.mPaint.setStyle(Paint.Style.STROKE);
      this.mPaint.setStrokeWidth(this.mOutlineStrokeWidth);
      paramCanvas.drawPath(this.mOctagonPath, this.mPaint);
    }
    label238:
    while (!isFocused())
    {
      return;
      k = 0;
      break;
    }
    this.mPaint.setColor(this.mFocusedOutlineColor);
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeWidth(this.mOutlineStrokeWidth);
    setOctagonPath(i, j, this.mBadgeRadius);
    paramCanvas.drawPath(this.mOctagonPath, this.mPaint);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAverageValue = ((TextView)findViewById(2131755440));
    this.mRatingBar = ((StarRatingBar)findViewById(2131755441));
    this.mCount = ((TextView)findViewById(2131755173));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeRating
 * JD-Core Version:    0.7.0.1
 */