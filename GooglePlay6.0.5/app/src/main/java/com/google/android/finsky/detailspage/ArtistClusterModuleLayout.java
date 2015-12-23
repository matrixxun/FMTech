package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

public class ArtistClusterModuleLayout
  extends CardClusterModuleLayout
{
  public ArtistClusterModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public ArtistClusterModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected final int getCardLayoutId(Resources paramResources)
  {
    return 2130968918;
  }
  
  protected final int getMaxItemsPerRow(Resources paramResources)
  {
    return paramResources.getInteger(2131623962);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ArtistClusterModuleLayout
 * JD-Core Version:    0.7.0.1
 */