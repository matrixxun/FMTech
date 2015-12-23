package com.google.android.finsky.layout;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.play.image.FifeImageView;

public class DetailsExpandedExtraPrimaryView
  extends RelativeLayout
{
  public TextView mDescription;
  public FifeImageView mThumbnail;
  public TextView mTitle;
  
  public DetailsExpandedExtraPrimaryView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsExpandedExtraPrimaryView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mThumbnail = ((FifeImageView)findViewById(2131755423));
    this.mTitle = ((TextView)findViewById(2131755424));
    this.mDescription = ((TextView)findViewById(2131755425));
    this.mDescription.setMovementMethod(LinkMovementMethod.getInstance());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsExpandedExtraPrimaryView
 * JD-Core Version:    0.7.0.1
 */