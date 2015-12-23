package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsExpandedExtraCreditsView
  extends LinearLayout
{
  public TextView mCredit;
  public TextView mNames;
  
  public DetailsExpandedExtraCreditsView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsExpandedExtraCreditsView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCredit = ((TextView)findViewById(2131755421));
    this.mNames = ((TextView)findViewById(2131755422));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsExpandedExtraCreditsView
 * JD-Core Version:    0.7.0.1
 */