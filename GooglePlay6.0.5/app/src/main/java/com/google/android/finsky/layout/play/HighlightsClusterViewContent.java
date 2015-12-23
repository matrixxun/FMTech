package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;

public class HighlightsClusterViewContent
  extends PlayClusterViewContentV2
{
  public HighlightsClusterViewContent(Context paramContext)
  {
    super(paramContext);
  }
  
  public HighlightsClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected int getPreloadRadius()
  {
    return 2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.HighlightsClusterViewContent
 * JD-Core Version:    0.7.0.1
 */