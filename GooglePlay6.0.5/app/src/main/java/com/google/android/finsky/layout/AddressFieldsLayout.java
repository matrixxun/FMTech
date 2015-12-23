package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.util.List;

public abstract class AddressFieldsLayout
  extends FrameLayout
{
  protected LinearLayout mFieldContainer;
  protected ProgressBar mProgressBar;
  protected ProgressBar mUpperRightProgressBar;
  
  public AddressFieldsLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AddressFieldsLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AddressFieldsLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void disableOneFieldMode() {}
  
  public abstract void hideUpperRightProgressBar();
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mProgressBar = ((ProgressBar)findViewById(2131755216));
    this.mFieldContainer = ((LinearLayout)findViewById(2131755215));
    this.mUpperRightProgressBar = ((ProgressBar)findViewById(2131755217));
  }
  
  public abstract void setFields(List<View> paramList);
  
  public abstract void setOnHeightOffsetChangedListener(OnHeightOffsetChangedListener paramOnHeightOffsetChangedListener);
  
  public abstract void showFields();
  
  public abstract void showProgressBar();
  
  public abstract void showUpperRightProgressBar();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AddressFieldsLayout
 * JD-Core Version:    0.7.0.1
 */