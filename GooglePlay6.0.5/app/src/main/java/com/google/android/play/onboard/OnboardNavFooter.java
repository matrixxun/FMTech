package com.google.android.play.onboard;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.play.R.color;
import com.google.android.play.R.id;
import com.google.android.play.R.layout;
import com.google.android.play.widget.PageIndicator;

public class OnboardNavFooter
  extends FrameLayout
{
  protected final TextView mEndButton;
  protected final PageIndicator mPageIndicator;
  protected final TextView mStartButton;
  
  public OnboardNavFooter(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public OnboardNavFooter(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public OnboardNavFooter(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public OnboardNavFooter(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1);
    LayoutInflater.from(paramContext).inflate(R.layout.play_onboard_nav_footer, this);
    setBackgroundColor(paramContext.getResources().getColor(R.color.play_onboard_app_color_dark));
    this.mStartButton = ((TextView)findViewById(R.id.start_button));
    this.mEndButton = ((TextView)findViewById(R.id.end_button));
    this.mPageIndicator = ((PageIndicator)findViewById(R.id.page_indicator));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.onboard.OnboardNavFooter
 * JD-Core Version:    0.7.0.1
 */