package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import com.google.android.play.utils.PlayUtils;

public class PlaylistHeader
  extends RelativeLayout
{
  private View mHeader;
  private final int mMinFullHeight;
  private View mPlaylistControl;
  private View mSubheader;
  
  public PlaylistHeader(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaylistHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mMinFullHeight = paramContext.getResources().getDimensionPixelSize(2131493076);
  }
  
  private boolean isCompactMode()
  {
    return (this.mSubheader.getVisibility() == 8) && (this.mPlaylistControl.getVisibility() == 8);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeader = findViewById(2131755487);
    this.mSubheader = findViewById(2131755614);
    this.mPlaylistControl = findViewById(2131756134);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (isCompactMode())
    {
      int i9 = getHeight() - getPaddingBottom();
      this.mHeader.layout(0, i9 - this.mHeader.getMeasuredHeight(), this.mHeader.getMeasuredWidth(), i9);
      return;
    }
    int i = getWidth();
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int j = getPaddingTop();
      int k = getPaddingTop();
      int m = this.mHeader.getMeasuredHeight();
      if (this.mSubheader.getVisibility() != 8) {
        m += this.mSubheader.getMeasuredHeight();
      }
      int n = j + (getHeight() - j - m - k) / 2;
      int i1 = this.mHeader.getMeasuredWidth();
      int i2 = PlayUtils.getViewLeftFromParentStart(i, i1, bool, 0);
      this.mHeader.layout(i2, n, i2 + i1, n + this.mHeader.getMeasuredHeight());
      int i3 = MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)this.mPlaylistControl.getLayoutParams());
      int i4 = PlayUtils.getViewLeftFromParentEnd(i, this.mPlaylistControl.getMeasuredWidth(), bool, i3);
      int i5 = n + this.mHeader.getBaseline() - this.mPlaylistControl.getBaseline();
      this.mPlaylistControl.layout(i4, i5, i4 + this.mPlaylistControl.getMeasuredWidth(), i5 + this.mPlaylistControl.getMeasuredHeight());
      if (this.mSubheader.getVisibility() == 8) {
        break;
      }
      int i6 = this.mHeader.getBottom();
      int i7 = this.mSubheader.getMeasuredWidth();
      int i8 = PlayUtils.getViewLeftFromParentStart(i, i7, bool, 0);
      this.mSubheader.layout(i8, i6, i8 + i7, i6 + this.mSubheader.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getPaddingTop();
    int j = getPaddingBottom();
    int k = View.MeasureSpec.getSize(paramInt1);
    if (isCompactMode())
    {
      this.mHeader.measure(paramInt1, 0);
      int i1 = this.mHeader.getMeasuredHeight();
      int i2 = j + (i1 + i);
      if (i2 > this.mMinFullHeight) {}
      for (int i3 = i2;; i3 = j + (i + i1 + (this.mMinFullHeight - i1 - i - j) / 2))
      {
        setMeasuredDimension(k, i3);
        return;
      }
    }
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mPlaylistControl.getLayoutParams();
    this.mPlaylistControl.measure(0, 0);
    int m = View.MeasureSpec.makeMeasureSpec(k - this.mPlaylistControl.getMeasuredWidth() - MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams), 1073741824);
    this.mHeader.measure(m, 0);
    int n = i + this.mHeader.getMeasuredHeight();
    if (this.mSubheader.getVisibility() != 8)
    {
      this.mSubheader.measure(m, 0);
      n += this.mSubheader.getMeasuredHeight();
    }
    setMeasuredDimension(k, Math.max(n + j, this.mMinFullHeight));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.PlaylistHeader
 * JD-Core Version:    0.7.0.1
 */