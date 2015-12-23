package android.support.v4.graphics.drawable;

import android.content.res.Resources.Theme;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

final class DrawableWrapperLollipop
  extends DrawableWrapperKitKat
{
  DrawableWrapperLollipop(Drawable paramDrawable)
  {
    super(paramDrawable);
  }
  
  public final void applyTheme(Resources.Theme paramTheme)
  {
    this.mDrawable.applyTheme(paramTheme);
  }
  
  public final boolean canApplyTheme()
  {
    return this.mDrawable.canApplyTheme();
  }
  
  public final Rect getDirtyBounds()
  {
    return this.mDrawable.getDirtyBounds();
  }
  
  public final void getOutline(Outline paramOutline)
  {
    this.mDrawable.getOutline(paramOutline);
  }
  
  public final void setHotspot(float paramFloat1, float paramFloat2)
  {
    this.mDrawable.setHotspot(paramFloat1, paramFloat2);
  }
  
  public final void setHotspotBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mDrawable.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.graphics.drawable.DrawableWrapperLollipop
 * JD-Core Version:    0.7.0.1
 */