package com.google.android.libraries.bind.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract interface BindingViewGroup
  extends DataView
{
  public abstract void blendCapturedBitmap(Bitmap paramBitmap, Rect paramRect, long paramLong, BlendMode paramBlendMode);
  
  public abstract boolean captureToBitmap(Bitmap paramBitmap, float paramFloat1, float paramFloat2);
  
  public abstract boolean isOwnedByParent();
  
  public abstract void prepareForRecycling();
  
  public abstract void setMeasuredDimensionProxy$255f295();
  
  public abstract void setOwnedByParent(boolean paramBoolean);
  
  public abstract boolean startEditingIfPossible();
  
  public abstract void superDrawProxy(Canvas paramCanvas);
  
  public abstract void updateBoundDataProxy(Data paramData);
  
  public static enum BlendMode
  {
    static
    {
      BlendMode[] arrayOfBlendMode = new BlendMode[2];
      arrayOfBlendMode[0] = FADE_SOURCE_ONLY;
      arrayOfBlendMode[1] = SHOW_SOURCE_HIDE_DESTINATION;
      $VALUES = arrayOfBlendMode;
    }
    
    private BlendMode() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.BindingViewGroup
 * JD-Core Version:    0.7.0.1
 */