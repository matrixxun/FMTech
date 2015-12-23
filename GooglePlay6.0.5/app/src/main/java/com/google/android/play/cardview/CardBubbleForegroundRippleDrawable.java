package com.google.android.play.cardview;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import com.google.android.play.R.color;

@TargetApi(21)
public final class CardBubbleForegroundRippleDrawable
  extends RippleDrawable
{
  CardBubbleForegroundDrawable mForegroundDrawable;
  private Bitmap mMaskBitmap;
  private Resources mResources;
  
  CardBubbleForegroundRippleDrawable(Resources paramResources, CardBubbleForegroundDrawable paramCardBubbleForegroundDrawable)
  {
    super(paramResources.getColorStateList(R.color.play_overlay_highlight_fill), null, paramCardBubbleForegroundDrawable);
    this.mResources = paramResources;
    this.mForegroundDrawable = paramCardBubbleForegroundDrawable;
  }
  
  protected final void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.mForegroundDrawable.setBounds(paramRect);
    refreshMask(paramRect);
  }
  
  final void refreshMask(Rect paramRect)
  {
    int i = paramRect.width();
    int j = paramRect.height();
    if ((i <= 0) || (j <= 0)) {
      return;
    }
    try
    {
      if (this.mMaskBitmap == null) {
        this.mMaskBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ALPHA_8);
      }
      while (this.mMaskBitmap != null)
      {
        Canvas localCanvas = new Canvas(this.mMaskBitmap);
        localCanvas.drawColor(0, PorterDuff.Mode.SRC);
        this.mForegroundDrawable.setBounds(0, 0, i, j);
        this.mForegroundDrawable.draw(localCanvas);
        setDrawableByLayerId(16908334, new BitmapDrawable(this.mResources, this.mMaskBitmap));
        invalidateSelf();
        return;
        this.mMaskBitmap.reconfigure(i, j, Bitmap.Config.ALPHA_8);
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        this.mMaskBitmap = null;
        continue;
        setDrawableByLayerId(16908334, new ColorDrawable(-1));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardBubbleForegroundRippleDrawable
 * JD-Core Version:    0.7.0.1
 */