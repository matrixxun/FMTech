package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import com.google.android.play.image.FifeImageView;

public class FadingEdgeImageView
  extends FifeImageView
{
  private int mFadingEdgeBackgroundColor;
  private boolean mHasFadingLeftEdge;
  private boolean mHasFadingRightEdge;
  
  public FadingEdgeImageView(Context paramContext)
  {
    super(paramContext);
  }
  
  public FadingEdgeImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void clearFadingEdges()
  {
    setHorizontalFadingEdgeEnabled(false);
    setFadingEdgeLength(0);
    this.mHasFadingLeftEdge = false;
    this.mHasFadingRightEdge = false;
    invalidate();
  }
  
  public final void configureFadingEdges(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
  {
    setHorizontalFadingEdgeEnabled(true);
    setFadingEdgeLength(paramInt1);
    this.mHasFadingLeftEdge = paramBoolean1;
    this.mHasFadingRightEdge = paramBoolean2;
    this.mFadingEdgeBackgroundColor = paramInt2;
    invalidate();
  }
  
  protected float getLeftFadingEdgeStrength()
  {
    if (this.mHasFadingLeftEdge) {
      return 1.0F;
    }
    return 0.0F;
  }
  
  protected float getRightFadingEdgeStrength()
  {
    if (this.mHasFadingRightEdge) {
      return 1.0F;
    }
    return 0.0F;
  }
  
  @ViewDebug.ExportedProperty(category="drawing")
  public int getSolidColor()
  {
    if ((this.mHasFadingLeftEdge) || (this.mHasFadingRightEdge)) {
      return this.mFadingEdgeBackgroundColor;
    }
    return super.getSolidColor();
  }
  
  public boolean hasOverlappingRendering()
  {
    return true;
  }
  
  protected boolean onSetAlpha(int paramInt)
  {
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.FadingEdgeImageView
 * JD-Core Version:    0.7.0.1
 */