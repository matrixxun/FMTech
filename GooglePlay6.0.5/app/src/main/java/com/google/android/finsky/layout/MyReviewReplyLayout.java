package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyReviewReplyLayout
  extends LinearLayout
{
  public TextView mReadMoreLabel;
  public TextView mReplyDeveloperName;
  public TextView mReplySubtitle;
  public TextView mReplyText;
  
  public MyReviewReplyLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MyReviewReplyLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mReplyDeveloperName = ((TextView)findViewById(2131755741));
    this.mReplySubtitle = ((TextView)findViewById(2131755742));
    this.mReplyText = ((TextView)findViewById(2131755743));
    this.mReadMoreLabel = ((TextView)findViewById(2131755744));
    this.mReadMoreLabel.setText(getContext().getString(2131362634).toUpperCase());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.MyReviewReplyLayout
 * JD-Core Version:    0.7.0.1
 */