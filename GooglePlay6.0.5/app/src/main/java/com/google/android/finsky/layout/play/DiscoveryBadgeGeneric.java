package com.google.android.finsky.layout.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.FifeImageView;

public class DiscoveryBadgeGeneric
  extends DiscoveryBadgeBase
{
  private Paint mPaint = new Paint(1);
  
  public DiscoveryBadgeGeneric(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DiscoveryBadgeGeneric(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    setWillNotDraw(false);
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1801;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth() / 2;
    int j = this.mBadgeRadius;
    this.mPaint.setColor(this.mCurrentFillColor);
    paramCanvas.drawCircle(i, j, this.mBadgeRadius, this.mPaint);
  }
  
  protected final void onPreImageLoad()
  {
    AvatarCropTransformation localAvatarCropTransformation = AvatarCropTransformation.getNoRingAvatarCrop(getResources(), this.mCurrentFillColor);
    this.mIcon.setBitmapTransformation(localAvatarCropTransformation);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.DiscoveryBadgeGeneric
 * JD-Core Version:    0.7.0.1
 */