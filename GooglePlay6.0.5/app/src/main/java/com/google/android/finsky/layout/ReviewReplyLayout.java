package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReviewReplyLayout
  extends LinearLayout
{
  boolean mIsExpanded;
  TextView mReplyHeader;
  TextView mReplyText;
  ImageView mReplyToggle;
  
  public ReviewReplyLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewReplyLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  final void disableToggle()
  {
    this.mReplyToggle.setVisibility(8);
    this.mReplyText.setVisibility(0);
    setOnClickListener(null);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mReplyHeader = ((TextView)findViewById(2131756064));
    this.mReplyText = ((TextView)findViewById(2131755743));
    this.mReplyToggle = ((ImageView)findViewById(2131756065));
  }
  
  final void showMoreIndicator()
  {
    this.mReplyToggle.setImageResource(2130837795);
    this.mReplyToggle.setContentDescription(getContext().getString(2131361986));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ReviewReplyLayout
 * JD-Core Version:    0.7.0.1
 */