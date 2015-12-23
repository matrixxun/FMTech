package com.google.android.play.cardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract interface CardViewGroupDelegate
{
  public abstract void initialize(View paramView, Context paramContext, AttributeSet paramAttributeSet, int paramInt);
  
  public abstract void setBackgroundColor(View paramView, int paramInt);
  
  public abstract void setBackgroundResource(View paramView, int paramInt);
  
  public abstract void setCardElevation(View paramView, float paramFloat);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardViewGroupDelegate
 * JD-Core Version:    0.7.0.1
 */