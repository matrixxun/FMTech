package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import com.google.android.finsky.utils.UiUtils;

public class BookSeriesClusterModuleLayout
  extends CardClusterModuleLayout
{
  public BookSeriesClusterModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public BookSeriesClusterModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected final int getCardLayoutId(Resources paramResources)
  {
    return 2130968954;
  }
  
  protected final int getMaxItemsPerRow(Resources paramResources)
  {
    return UiUtils.getFeaturedGridColumnCount(paramResources, 1.0D);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BookSeriesClusterModuleLayout
 * JD-Core Version:    0.7.0.1
 */