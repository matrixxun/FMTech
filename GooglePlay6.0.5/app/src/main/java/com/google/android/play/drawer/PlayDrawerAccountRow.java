package com.google.android.play.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.play.R.id;
import com.google.android.play.image.FifeImageView;

class PlayDrawerAccountRow
  extends RelativeLayout
{
  TextView mAccountName;
  FifeImageView mAvatar;
  
  public PlayDrawerAccountRow(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlayDrawerAccountRow(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAvatar = ((FifeImageView)findViewById(R.id.avatar));
    this.mAccountName = ((TextView)findViewById(R.id.account_name));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerAccountRow
 * JD-Core Version:    0.7.0.1
 */