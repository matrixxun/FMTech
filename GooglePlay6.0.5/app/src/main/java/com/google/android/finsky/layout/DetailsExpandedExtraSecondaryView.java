package com.google.android.finsky.layout;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsExpandedExtraSecondaryView
  extends LinearLayout
{
  public TextView mDescription;
  public TextView mTitle;
  
  public DetailsExpandedExtraSecondaryView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsExpandedExtraSecondaryView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitle = ((TextView)findViewById(2131755424));
    this.mDescription = ((TextView)findViewById(2131755425));
    this.mDescription.setMovementMethod(LinkMovementMethod.getInstance());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsExpandedExtraSecondaryView
 * JD-Core Version:    0.7.0.1
 */