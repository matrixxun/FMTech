package com.google.android.libraries.bind.card;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.libraries.bind.R.layout;
import com.google.android.libraries.bind.widget.BindingFrameLayout;

public class CardEmpty
  extends BindingFrameLayout
{
  public static final int[] EQUALITY_FIELDS = new int[0];
  public static final int LAYOUT = R.layout.bind__card_empty;
  
  public CardEmpty(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public CardEmpty(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CardEmpty(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.card.CardEmpty
 * JD-Core Version:    0.7.0.1
 */