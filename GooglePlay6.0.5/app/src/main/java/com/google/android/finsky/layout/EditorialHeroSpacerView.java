package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.StreamSpacer;
import com.google.android.finsky.utils.UiUtils;

public class EditorialHeroSpacerView
  extends View
  implements FinskyHeaderListLayout.StreamSpacer
{
  public float mHeroImageAspectRatio;
  public boolean mShowsHeroImage;
  
  public EditorialHeroSpacerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public EditorialHeroSpacerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = HeroGraphicView.getSpacerHeight(getContext(), i, this.mShowsHeroImage, this.mHeroImageAspectRatio);
    if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
      j -= UiUtils.getStatusBarHeight(getContext());
    }
    setMeasuredDimension(i, j);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.EditorialHeroSpacerView
 * JD-Core Version:    0.7.0.1
 */