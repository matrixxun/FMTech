package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;

public class CardTextView
  extends TextView
{
  public CardTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CardTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CardTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
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
 * Qualified Name:     com.google.android.finsky.layout.play.CardTextView
 * JD-Core Version:    0.7.0.1
 */