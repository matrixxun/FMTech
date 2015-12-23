package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.finsky.adapters.CardLookDecoration.CardSection;

public class CategorySpacer
  extends View
  implements CardLookDecoration.CardSection
{
  public CategorySpacer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CategorySpacer(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CategorySpacer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.CategorySpacer
 * JD-Core Version:    0.7.0.1
 */