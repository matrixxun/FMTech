package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import java.util.Iterator;
import java.util.List;

public class AddressFieldsLayoutFroyo
  extends AddressFieldsLayout
{
  private boolean mLoading = false;
  
  public AddressFieldsLayoutFroyo(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AddressFieldsLayoutFroyo(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AddressFieldsLayoutFroyo(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void setChildrenViewVisibility(int paramInt)
  {
    int i = this.mFieldContainer.getChildCount();
    for (int j = 0; j < i; j++) {
      this.mFieldContainer.getChildAt(j).setVisibility(paramInt);
    }
  }
  
  public final void hideUpperRightProgressBar()
  {
    this.mUpperRightProgressBar.setVisibility(8);
  }
  
  public void setFields(List<View> paramList)
  {
    this.mFieldContainer.removeAllViews();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
      this.mFieldContainer.addView(localView, localLayoutParams);
    }
    if (this.mLoading)
    {
      this.mProgressBar.setVisibility(8);
      setChildrenViewVisibility(0);
      return;
    }
    this.mProgressBar.setVisibility(0);
    setChildrenViewVisibility(8);
  }
  
  public void setOnHeightOffsetChangedListener(OnHeightOffsetChangedListener paramOnHeightOffsetChangedListener) {}
  
  public final void showFields()
  {
    this.mProgressBar.setVisibility(8);
    setChildrenViewVisibility(0);
  }
  
  public final void showProgressBar()
  {
    this.mProgressBar.setVisibility(0);
    setChildrenViewVisibility(8);
  }
  
  public final void showUpperRightProgressBar()
  {
    this.mUpperRightProgressBar.setVisibility(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AddressFieldsLayoutFroyo
 * JD-Core Version:    0.7.0.1
 */