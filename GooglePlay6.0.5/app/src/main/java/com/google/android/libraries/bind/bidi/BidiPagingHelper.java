package com.google.android.libraries.bind.bidi;

import android.support.v4.view.PagerAdapter;

public final class BidiPagingHelper
{
  public static int getLogicalPosition(PagerAdapter paramPagerAdapter, int paramInt)
  {
    if (((paramPagerAdapter instanceof BidiAwarePagerAdapter)) && (((BidiAwarePagerAdapter)paramPagerAdapter).isRtl())) {
      paramInt = swapPosition(paramPagerAdapter, paramInt);
    }
    return paramInt;
  }
  
  public static int getVisualPosition(PagerAdapter paramPagerAdapter, int paramInt)
  {
    if (((paramPagerAdapter instanceof BidiAwarePagerAdapter)) && (((BidiAwarePagerAdapter)paramPagerAdapter).isRtl())) {
      paramInt = swapPosition(paramPagerAdapter, paramInt);
    }
    return paramInt;
  }
  
  private static int swapPosition(PagerAdapter paramPagerAdapter, int paramInt)
  {
    if (paramInt < 0) {
      return paramInt;
    }
    return -1 + paramPagerAdapter.getCount() - paramInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.bidi.BidiPagingHelper
 * JD-Core Version:    0.7.0.1
 */