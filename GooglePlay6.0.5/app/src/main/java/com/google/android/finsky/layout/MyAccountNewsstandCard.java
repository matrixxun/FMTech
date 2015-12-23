package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.layout.PlayActionButton;

public class MyAccountNewsstandCard
  extends CardLinearLayout
{
  public TextView mDescriptionView;
  public PlayActionButton mDismissButton;
  public TextView mTitleView;
  
  public MyAccountNewsstandCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyAccountNewsstandCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleView = ((TextView)findViewById(2131755173));
    this.mDescriptionView = ((TextView)findViewById(2131755211));
    this.mDismissButton = ((PlayActionButton)findViewById(2131755721));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountNewsstandCard
 * JD-Core Version:    0.7.0.1
 */