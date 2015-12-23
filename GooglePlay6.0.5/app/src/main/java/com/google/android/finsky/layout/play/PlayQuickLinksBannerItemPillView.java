package com.google.android.finsky.layout.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.play.image.BitmapLoader;

public class PlayQuickLinksBannerItemPillView
  extends PlayQuickLinksBannerItemBaseView
{
  private int mAdditionalWidth;
  private final RectF mPillBounds;
  private final Paint mPillPaint;
  
  public PlayQuickLinksBannerItemPillView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayQuickLinksBannerItemPillView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayQuickLinksBannerItemPillView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setWillNotDraw(false);
    this.mPillPaint = new Paint();
    this.mPillPaint.setAntiAlias(true);
    this.mPillPaint.setStyle(Paint.Style.FILL);
    this.mPillBounds = new RectF();
  }
  
  public final void bindData(Browse.QuickLink paramQuickLink, BitmapLoader paramBitmapLoader, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.bindData(paramQuickLink, paramBitmapLoader, paramPlayStoreUiElementNode);
    this.mPillPaint.setColor(this.mFillColor);
    if (paramQuickLink.hasName) {
      this.mTitleView.setText(paramQuickLink.name.toUpperCase());
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    float f = this.mPillBounds.height();
    paramCanvas.drawRoundRect(this.mPillBounds, f / 2.0F, f / 2.0F, this.mPillPaint);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(getMeasuredWidth() + this.mAdditionalWidth, paramInt2);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mPillBounds.set(getPaddingLeft(), getPaddingTop(), paramInt1 - getPaddingRight(), paramInt2 - getPaddingBottom());
  }
  
  public void setAdditionalWidth(int paramInt)
  {
    this.mAdditionalWidth = paramInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayQuickLinksBannerItemPillView
 * JD-Core Version:    0.7.0.1
 */