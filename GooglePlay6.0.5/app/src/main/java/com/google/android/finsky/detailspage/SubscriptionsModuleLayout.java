package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.google.android.finsky.layout.DetailsSectionStack;

public class SubscriptionsModuleLayout
  extends DetailsSectionStack
{
  boolean mHasBinded;
  final LayoutInflater mLayoutInflater;
  
  public SubscriptionsModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SubscriptionsModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mLayoutInflater = LayoutInflater.from(paramContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SubscriptionsModuleLayout
 * JD-Core Version:    0.7.0.1
 */