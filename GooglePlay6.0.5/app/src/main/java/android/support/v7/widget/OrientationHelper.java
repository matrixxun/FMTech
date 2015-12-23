package android.support.v7.widget;

import android.view.View;

public abstract class OrientationHelper
{
  int mLastTotalSpace = -2147483648;
  protected final RecyclerView.LayoutManager mLayoutManager;
  
  private OrientationHelper(RecyclerView.LayoutManager paramLayoutManager)
  {
    this.mLayoutManager = paramLayoutManager;
  }
  
  public abstract int getDecoratedEnd(View paramView);
  
  public abstract int getDecoratedMeasurement(View paramView);
  
  public abstract int getDecoratedMeasurementInOther(View paramView);
  
  public abstract int getDecoratedStart(View paramView);
  
  public abstract int getEnd();
  
  public abstract int getEndAfterPadding();
  
  public abstract int getEndPadding();
  
  public abstract int getStartAfterPadding();
  
  public abstract int getTotalSpace();
  
  public final int getTotalSpaceChange()
  {
    if (-2147483648 == this.mLastTotalSpace) {
      return 0;
    }
    return getTotalSpace() - this.mLastTotalSpace;
  }
  
  public abstract void offsetChildren(int paramInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.OrientationHelper
 * JD-Core Version:    0.7.0.1
 */