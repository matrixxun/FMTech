package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.finsky.activities.SpacerHeightProvider;

public class SpacerHeightAwareFrameLayout
  extends FrameLayout
{
  private SpacerHeightProvider mSpacerHeightProvider;
  
  public SpacerHeightAwareFrameLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public SpacerHeightAwareFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SpacerHeightAwareFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public SpacerHeightAwareFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mSpacerHeightProvider != null)
    {
      int i = this.mSpacerHeightProvider.getLeadingSpacerHeight();
      if (i != getPaddingTop()) {
        setPadding(getPaddingLeft(), i, getPaddingRight(), getPaddingBottom());
      }
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setSpacerHeightProvider(SpacerHeightProvider paramSpacerHeightProvider)
  {
    this.mSpacerHeightProvider = paramSpacerHeightProvider;
    requestLayout();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SpacerHeightAwareFrameLayout
 * JD-Core Version:    0.7.0.1
 */