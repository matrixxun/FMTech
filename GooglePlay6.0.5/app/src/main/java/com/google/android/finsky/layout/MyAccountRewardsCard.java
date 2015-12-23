package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyAccountRewardsCard
  extends MyAccountCard<RewardRowView>
{
  public MyAccountRewardsCard(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyAccountRewardsCard(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 2660, 2661, 2);
  }
  
  protected final void onBindNoDataView(TextView paramTextView)
  {
    paramTextView.setText(2131362391);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyAccountRewardsCard
 * JD-Core Version:    0.7.0.1
 */