package com.caverock.androidsvg;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;

public final class SVGDrawable
  extends Drawable
{
  private SVG.Colour mFill;
  private SVG.Colour mStroke;
  private SVG mSvg;
  
  public SVGDrawable(SVG paramSVG, SVG.Colour paramColour)
  {
    this.mSvg = paramSVG;
    this.mFill = paramColour;
    this.mStroke = null;
  }
  
  public final void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    paramCanvas.save();
    paramCanvas.clipRect(localRect);
    paramCanvas.translate(localRect.left, localRect.top);
    this.mSvg.renderToCanvas(paramCanvas, this.mFill, this.mStroke);
    paramCanvas.restore();
  }
  
  public final int getIntrinsicHeight()
  {
    return (int)Math.ceil(this.mSvg.getDocumentHeight());
  }
  
  public final int getIntrinsicWidth()
  {
    return (int)Math.ceil(this.mSvg.getDocumentWidth());
  }
  
  public final int getOpacity()
  {
    return -3;
  }
  
  public final void setAlpha(int paramInt)
  {
    if (this.mFill != null) {
      this.mFill = new SVG.Colour(ColorUtils.setAlphaComponent(this.mFill.colour, paramInt));
    }
    if (this.mStroke != null) {
      this.mStroke = new SVG.Colour(ColorUtils.setAlphaComponent(this.mStroke.colour, paramInt));
    }
  }
  
  public final void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGDrawable
 * JD-Core Version:    0.7.0.1
 */