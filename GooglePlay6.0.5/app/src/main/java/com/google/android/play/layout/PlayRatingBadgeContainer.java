package com.google.android.play.layout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.play.R.id;
import com.google.android.play.utils.PlayUtils;

public class PlayRatingBadgeContainer
  extends FrameLayout
{
  private View mBadge;
  private View mRatingBar;
  
  public PlayRatingBadgeContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayRatingBadgeContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public int getBaseline()
  {
    if (this.mBadge.getVisibility() != 8) {
      return this.mBadge.getBaseline();
    }
    if (this.mRatingBar.getVisibility() != 8) {
      return this.mRatingBar.getBaseline();
    }
    return getMeasuredHeight();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRatingBar = findViewById(R.id.li_rating);
    this.mBadge = findViewById(R.id.li_badge);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      if (this.mRatingBar.getVisibility() != 8)
      {
        int m = this.mRatingBar.getMeasuredWidth();
        int n = PlayUtils.getViewLeftFromParentStart(i, m, bool, 0);
        this.mRatingBar.layout(n, 0, n + m, this.mRatingBar.getMeasuredHeight());
      }
      if (this.mBadge.getVisibility() != 8)
      {
        int j = this.mBadge.getMeasuredWidth();
        int k = PlayUtils.getViewLeftFromParentStart(i, j, bool, 0);
        this.mBadge.layout(k, 0, k + j, this.mBadge.getMeasuredHeight());
      }
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = this.mRatingBar.getVisibility();
    int k = 0;
    int m = 0;
    if (j != 8)
    {
      this.mRatingBar.measure(0, 0);
      if (this.mRatingBar.getMeasuredWidth() > i)
      {
        int n = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
        this.mRatingBar.measure(n, n);
      }
      m = this.mRatingBar.getMeasuredWidth();
      k = this.mRatingBar.getMeasuredHeight();
    }
    if (this.mBadge.getVisibility() != 8)
    {
      this.mBadge.measure(View.MeasureSpec.makeMeasureSpec(i, -2147483648), 0);
      m = Math.max(m, this.mBadge.getMeasuredWidth());
      k = Math.max(k, this.mBadge.getMeasuredHeight());
    }
    setMeasuredDimension(m, k);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayRatingBadgeContainer
 * JD-Core Version:    0.7.0.1
 */