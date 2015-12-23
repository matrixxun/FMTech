package com.google.android.play.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract interface BitmapTransformation
{
  public abstract void drawFocusedOverlay(Canvas paramCanvas, int paramInt1, int paramInt2);
  
  public abstract void drawPressedOverlay(Canvas paramCanvas, int paramInt1, int paramInt2);
  
  public abstract int getTransformationInset(int paramInt1, int paramInt2);
  
  public abstract Bitmap transform(Bitmap paramBitmap, int paramInt1, int paramInt2);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.BitmapTransformation
 * JD-Core Version:    0.7.0.1
 */