package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.layout.PlayTextView;

public class DecoratedTextView
  extends PlayTextView
  implements BitmapLoader.BitmapLoadedHandler
{
  public DecoratedTextView(Context paramContext)
  {
    super(paramContext);
  }
  
  public DecoratedTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void loadDecoration(BitmapLoader paramBitmapLoader, Common.Image paramImage, int paramInt)
  {
    if (paramImage.supportsFifeUrlOptions) {}
    for (int i = paramInt;; i = 0)
    {
      Bitmap localBitmap = paramBitmapLoader.get(paramImage.imageUrl, i, i, this).mBitmap;
      if (localBitmap != null) {
        setDecorationBitmap(localBitmap);
      }
      return;
    }
  }
  
  public final void onResponse(BitmapLoader.BitmapContainer paramBitmapContainer)
  {
    Bitmap localBitmap = paramBitmapContainer.mBitmap;
    if (localBitmap == null) {
      return;
    }
    setDecorationBitmap(localBitmap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DecoratedTextView
 * JD-Core Version:    0.7.0.1
 */