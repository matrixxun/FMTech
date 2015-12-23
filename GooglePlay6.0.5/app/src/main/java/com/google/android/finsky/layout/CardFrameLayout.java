package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;

public class CardFrameLayout
  extends FrameLayout
{
  public CardFrameLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CardFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CardFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, paramInt);
  }
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardViewGroupDelegates.IMPL;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.CardFrameLayout
 * JD-Core Version:    0.7.0.1
 */