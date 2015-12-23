package com.google.android.libraries.bind.bidi;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPagerShim;
import android.util.AttributeSet;

public class BidiAwareViewPager
  extends ViewPagerShim
{
  public BidiAwareViewPager(Context paramContext)
  {
    super(paramContext);
  }
  
  public BidiAwareViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getCurrentLogicalItem()
  {
    return BidiPagingHelper.getLogicalPosition(getAdapter(), super.getCurrentItem());
  }
  
  public int getCurrentVisualItem()
  {
    return super.getCurrentItem();
  }
  
  @SuppressLint({"NewApi"})
  public final boolean isRtl()
  {
    return (Build.VERSION.SDK_INT >= 18) && (getLayoutDirection() == 1);
  }
  
  @TargetApi(17)
  public void onRtlPropertiesChanged(int paramInt)
  {
    int i = 1;
    super.onRtlPropertiesChanged(paramInt);
    int j = getCurrentLogicalItem();
    BidiAwarePagerAdapter localBidiAwarePagerAdapter;
    if ((getAdapter() instanceof BidiAwarePagerAdapter))
    {
      localBidiAwarePagerAdapter = (BidiAwarePagerAdapter)getAdapter();
      if (paramInt != i) {
        break label50;
      }
    }
    for (;;)
    {
      localBidiAwarePagerAdapter.setRtl(i);
      setCurrentLogicalItem(j);
      return;
      label50:
      i = 0;
    }
  }
  
  public void setCurrentLogicalItem(int paramInt)
  {
    setCurrentItem(BidiPagingHelper.getVisualPosition(getAdapter(), paramInt));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.bidi.BidiAwareViewPager
 * JD-Core Version:    0.7.0.1
 */