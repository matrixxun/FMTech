package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.layout.AccessibleLinearLayout;
import com.google.android.play.image.FifeImageView;

public class WarmWelcomeCardButton
  extends AccessibleLinearLayout
  implements Recyclable
{
  FifeImageView mIcon;
  TextView mText;
  
  public WarmWelcomeCardButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public WarmWelcomeCardButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mIcon = ((FifeImageView)findViewById(2131755150));
    this.mText = ((TextView)findViewById(2131755151));
  }
  
  public final void onRecycle()
  {
    this.mIcon.clearImage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.WarmWelcomeCardButton
 * JD-Core Version:    0.7.0.1
 */