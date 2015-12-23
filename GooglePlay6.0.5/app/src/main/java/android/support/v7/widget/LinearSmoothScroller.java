package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public abstract class LinearSmoothScroller
  extends RecyclerView.SmoothScroller
{
  private final float MILLISECONDS_PER_PX;
  protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
  protected int mInterimTargetDx = 0;
  protected int mInterimTargetDy = 0;
  protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
  protected PointF mTargetVector;
  
  public LinearSmoothScroller(Context paramContext)
  {
    this.MILLISECONDS_PER_PX = (25.0F / paramContext.getResources().getDisplayMetrics().densityDpi);
  }
  
  private static int calculateDtToFit(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i;
    switch (paramInt5)
    {
    default: 
      throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
    case -1: 
      i = paramInt3 - paramInt1;
    }
    do
    {
      return i;
      return paramInt4 - paramInt2;
      i = paramInt3 - paramInt1;
    } while (i > 0);
    int j = paramInt4 - paramInt2;
    if (j < 0) {
      return j;
    }
    return 0;
  }
  
  private int calculateTimeForScrolling(int paramInt)
  {
    return (int)Math.ceil(Math.abs(paramInt) * this.MILLISECONDS_PER_PX);
  }
  
  private static int clampApplyScroll(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - paramInt2;
    if (paramInt1 * i <= 0) {
      i = 0;
    }
    return i;
  }
  
  public abstract PointF computeScrollVectorForPosition(int paramInt);
  
  protected final void onSeekTargetStep$64702b56(int paramInt1, int paramInt2, RecyclerView.SmoothScroller.Action paramAction)
  {
    if (RecyclerView.access$1100(this.mRecyclerView).getChildCount() == 0) {
      stop();
    }
    do
    {
      return;
      this.mInterimTargetDx = clampApplyScroll(this.mInterimTargetDx, paramInt1);
      this.mInterimTargetDy = clampApplyScroll(this.mInterimTargetDy, paramInt2);
    } while ((this.mInterimTargetDx != 0) || (this.mInterimTargetDy != 0));
    PointF localPointF = computeScrollVectorForPosition(this.mTargetPosition);
    if ((localPointF == null) || ((localPointF.x == 0.0F) && (localPointF.y == 0.0F)))
    {
      Log.e("LinearSmoothScroller", "To support smooth scrolling, you should override \nLayoutManager#computeScrollVectorForPosition.\nFalling back to instant scroll");
      paramAction.mJumpToPosition = this.mTargetPosition;
      stop();
      return;
    }
    double d = Math.sqrt(localPointF.x * localPointF.x + localPointF.y * localPointF.y);
    localPointF.x = ((float)(localPointF.x / d));
    localPointF.y = ((float)(localPointF.y / d));
    this.mTargetVector = localPointF;
    this.mInterimTargetDx = ((int)(10000.0F * localPointF.x));
    this.mInterimTargetDy = ((int)(10000.0F * localPointF.y));
    int i = calculateTimeForScrolling(10000);
    paramAction.update((int)(1.2F * this.mInterimTargetDx), (int)(1.2F * this.mInterimTargetDy), (int)(1.2F * i), this.mLinearInterpolator);
  }
  
  protected final void onStop()
  {
    this.mInterimTargetDy = 0;
    this.mInterimTargetDx = 0;
    this.mTargetVector = null;
  }
  
  protected final void onTargetFound$68abd3fe(View paramView, RecyclerView.SmoothScroller.Action paramAction)
  {
    int i;
    RecyclerView.LayoutManager localLayoutManager1;
    int j;
    label38:
    int k;
    label60:
    RecyclerView.LayoutManager localLayoutManager2;
    if ((this.mTargetVector == null) || (this.mTargetVector.x == 0.0F))
    {
      i = 0;
      localLayoutManager1 = this.mLayoutManager;
      if (localLayoutManager1.canScrollHorizontally()) {
        break label152;
      }
      j = 0;
      if ((this.mTargetVector != null) && (this.mTargetVector.y != 0.0F)) {
        break label206;
      }
      k = 0;
      localLayoutManager2 = this.mLayoutManager;
      if (localLayoutManager2.canScrollVertically()) {
        break label230;
      }
    }
    label152:
    label206:
    label230:
    RecyclerView.LayoutParams localLayoutParams2;
    for (int m = 0;; m = calculateDtToFit(RecyclerView.LayoutManager.getDecoratedTop(paramView) - localLayoutParams2.topMargin, RecyclerView.LayoutManager.getDecoratedBottom(paramView) + localLayoutParams2.bottomMargin, localLayoutManager2.getPaddingTop(), localLayoutManager2.getHeight() - localLayoutManager2.getPaddingBottom(), k))
    {
      int n = (int)Math.ceil(calculateTimeForScrolling((int)Math.sqrt(j * j + m * m)) / 0.3356D);
      if (n > 0) {
        paramAction.update(-j, -m, n, this.mDecelerateInterpolator);
      }
      return;
      if (this.mTargetVector.x > 0.0F)
      {
        i = 1;
        break;
      }
      i = -1;
      break;
      RecyclerView.LayoutParams localLayoutParams1 = (RecyclerView.LayoutParams)paramView.getLayoutParams();
      j = calculateDtToFit(RecyclerView.LayoutManager.getDecoratedLeft(paramView) - localLayoutParams1.leftMargin, RecyclerView.LayoutManager.getDecoratedRight(paramView) + localLayoutParams1.rightMargin, localLayoutManager1.getPaddingLeft(), localLayoutManager1.getWidth() - localLayoutManager1.getPaddingRight(), i);
      break label38;
      if (this.mTargetVector.y > 0.0F)
      {
        k = 1;
        break label60;
      }
      k = -1;
      break label60;
      localLayoutParams2 = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.LinearSmoothScroller
 * JD-Core Version:    0.7.0.1
 */