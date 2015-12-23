package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.play.layout.CardLinearLayout;
import com.google.android.play.utils.PlayUtils;

public class PlayCardPromoClusterDefaultView
  extends PlayCardPromoClusterBaseView
{
  public PlayCardPromoClusterDefaultView(Context paramContext)
  {
    super(paramContext);
  }
  
  public PlayCardPromoClusterDefaultView(Context paramContext, AttributeSet paramAttributeSet)
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
      int j = ViewCompat.getPaddingStart(this);
      int k = getPaddingTop();
      int m = k + this.mHeader.getMeasuredHeight();
      int n = this.mHeader.getMeasuredWidth();
      int i1 = PlayUtils.getViewLeftFromParentStart(i, n, bool, j);
      int i2 = this.mClusterContentView.getMeasuredWidth();
      int i3 = PlayUtils.getViewLeftFromParentStart(i, i2, bool, j);
      this.mHeader.layout(i1, k, i1 + n, m);
      this.mClusterContentView.layout(i3, m, i3 + i2, m + this.mClusterContentView.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - getPaddingLeft() - getPaddingRight();
    int k = getPaddingTop() + getPaddingBottom();
    this.mClusterContentView.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), 0);
    this.mHeader.measure(paramInt1, 0);
    setMeasuredDimension(i, k + (this.mHeader.getMeasuredHeight() + this.mClusterContentView.getMeasuredHeight()));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardPromoClusterDefaultView
 * JD-Core Version:    0.7.0.1
 */