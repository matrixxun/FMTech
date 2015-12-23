package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.layout.ForegroundLinearLayout;
import com.google.android.play.cardview.CardBubbleViewGroupDelegates;
import com.google.android.play.cardview.CardViewGroupDelegate;

public class CardBubbleLinearLayout
  extends ForegroundLinearLayout
{
  public CardBubbleLinearLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CardBubbleLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, 0);
  }
  
  public CardBubbleLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, paramInt);
  }
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardBubbleViewGroupDelegates.IMPL;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.CardBubbleLinearLayout
 * JD-Core Version:    0.7.0.1
 */