package com.google.android.play.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.play.R.id;

class PlayDrawerMiniAccountRow
  extends FrameLayout
{
  TextView mAccountName;
  
  public PlayDrawerMiniAccountRow(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlayDrawerMiniAccountRow(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAccountName = ((TextView)findViewById(R.id.mini_account_name));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerMiniAccountRow
 * JD-Core Version:    0.7.0.1
 */