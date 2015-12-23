package com.google.android.finsky.layout.play;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.play.image.AvatarCropTransformation;

public class PlayCirclesIcon
  extends ImageView
{
  public PlayCirclesIcon(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCirclesIcon(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setWillNotDraw(false);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    invalidate();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    if ((isPressed()) && (isClickable())) {
      AvatarCropTransformation.getFullAvatarCrop(getResources()).drawPressedOverlay(paramCanvas, i, j);
    }
    while (!isFocused()) {
      return;
    }
    AvatarCropTransformation.getFullAvatarCrop(getResources()).drawFocusedOverlay(paramCanvas, i, j);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCirclesIcon
 * JD-Core Version:    0.7.0.1
 */