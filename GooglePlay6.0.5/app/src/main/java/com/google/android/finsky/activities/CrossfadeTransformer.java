package com.google.android.finsky.activities;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public final class CrossfadeTransformer
  implements ViewPager.PageTransformer
{
  private IndexablePager mIndexer;
  
  public CrossfadeTransformer(IndexablePager paramIndexablePager)
  {
    this.mIndexer = paramIndexablePager;
  }
  
  public final void transformPage(View paramView, float paramFloat)
  {
    if (this.mIndexer.getViewByIndex$7529eef0() == paramView)
    {
      float f = 1.0F - Math.abs(paramFloat);
      if (f < 0.0F) {
        f = 0.0F;
      }
      ViewCompat.setAlpha(paramView, f);
    }
    if ((paramFloat >= 1.0F) || (paramFloat <= -1.0F))
    {
      ViewCompat.setTranslationX(paramView, paramFloat);
      return;
    }
    ViewCompat.setTranslationX(paramView, -0.9F * (paramFloat * paramView.getWidth()));
  }
  
  public static abstract interface IndexablePager
  {
    public abstract View getViewByIndex$7529eef0();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.CrossfadeTransformer
 * JD-Core Version:    0.7.0.1
 */