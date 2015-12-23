package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.layout.AccessibleLinearLayout;

public class BylinesModuleCellLayout
  extends AccessibleLinearLayout
{
  ImageView mThumbnail;
  TextView mTitle;
  
  public BylinesModuleCellLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BylinesModuleCellLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mThumbnail = ((ImageView)findViewById(2131755305));
    this.mTitle = ((TextView)findViewById(2131755306));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BylinesModuleCellLayout
 * JD-Core Version:    0.7.0.1
 */