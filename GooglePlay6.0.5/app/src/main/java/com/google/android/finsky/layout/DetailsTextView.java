package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.play.layout.PlayTextView;

public class DetailsTextView
  extends PlayTextView
{
  public DetailsTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    super.scrollTo(paramInt1, 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsTextView
 * JD-Core Version:    0.7.0.1
 */