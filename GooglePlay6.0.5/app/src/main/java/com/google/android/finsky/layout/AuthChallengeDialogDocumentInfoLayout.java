package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.layout.play.SingleLineContainer;

public class AuthChallengeDialogDocumentInfoLayout
  extends SingleLineContainer
{
  public TextView mPriceView;
  public TextView mTitleView;
  
  public AuthChallengeDialogDocumentInfoLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public AuthChallengeDialogDocumentInfoLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleView = ((TextView)findViewById(2131755635));
    this.mPriceView = ((TextView)findViewById(2131755638));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AuthChallengeDialogDocumentInfoLayout
 * JD-Core Version:    0.7.0.1
 */