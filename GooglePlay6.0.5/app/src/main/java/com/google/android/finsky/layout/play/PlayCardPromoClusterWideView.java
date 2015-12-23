package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.utils.PlayUtils;

public class PlayCardPromoClusterWideView
  extends PlayCardPromoClusterBaseView
{
  public PlayCardPromoClusterWideView(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlayCardPromoClusterWideView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      int j = getPaddingTop();
      int k = ViewCompat.getPaddingStart(this);
      int m = j + this.mHeader.getMeasuredHeight();
      int n = this.mHeader.getMeasuredWidth();
      int i1 = PlayUtils.getViewLeftFromParentStart(i, n, bool, k);
      int i2 = this.mClusterContentView.getMeasuredWidth();
      int i3 = PlayUtils.getViewLeftFromParentStart(i, i2, bool, k);
      this.mHeader.layout(i1, j, i1 + n, m);
      this.mClusterContentView.layout(i3, m, i3 + i2, m + this.mClusterContentView.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = getPaddingBottom() + getPaddingTop();
    int n = View.MeasureSpec.makeMeasureSpec(i - j - k, 1073741824);
    this.mClusterContentView.measure(n, 0);
    this.mHeader.measure(paramInt1, 0);
    setMeasuredDimension(i, m + (this.mHeader.getMeasuredHeight() + this.mClusterContentView.getMeasuredHeight()));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardPromoClusterWideView
 * JD-Core Version:    0.7.0.1
 */