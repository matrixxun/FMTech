package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;

public class ViewPagerShim
  extends ViewPager
{
  public ViewPagerShim(Context paramContext)
  {
    super(paramContext);
  }
  
  public ViewPagerShim(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean pageLeft()
  {
    return super.pageLeft();
  }
  
  public boolean pageRight()
  {
    return super.pageRight();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewPagerShim
 * JD-Core Version:    0.7.0.1
 */