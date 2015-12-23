package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.PlayUtils;

public class AuthChoicesButtonBarLayout
  extends ViewGroup
{
  private final Rect mEverytimeArea;
  private View mEverytimeButton;
  private View mEverytimeFrame;
  private final int mHorizontalButtonGap;
  private final int mMinimumTouchTargetSize;
  private final Rect mOldEverytimeArea;
  private final Rect mOldSessionArea;
  private final Rect mSessionArea;
  private View mSessionButton;
  private View mSessionFrame;
  private final int mTopBottomPadding;
  private final int mVerticalButtonGap;
  
  public AuthChoicesButtonBarLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AuthChoicesButtonBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getResources();
    this.mTopBottomPadding = localResources.getDimensionPixelSize(2131493337);
    this.mVerticalButtonGap = localResources.getDimensionPixelSize(2131493338);
    this.mHorizontalButtonGap = localResources.getDimensionPixelSize(2131493335);
    this.mMinimumTouchTargetSize = getResources().getDimensionPixelSize(2131493521);
    this.mEverytimeArea = new Rect();
    this.mOldEverytimeArea = new Rect();
    this.mSessionArea = new Rect();
    this.mOldSessionArea = new Rect();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mEverytimeFrame = findViewById(2131755693);
    this.mEverytimeButton = findViewById(2131755694);
    this.mSessionFrame = findViewById(2131755695);
    this.mSessionButton = findViewById(2131755696);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    int i;
    int k;
    int i1;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i = getWidth();
      int j = ViewCompat.getPaddingStart(this);
      k = ViewCompat.getPaddingEnd(this);
      int m = i - j - k;
      int n = getHeight();
      i1 = getPaddingTop();
      int i2 = getPaddingBottom();
      int i3 = n - i1 - i2;
      i4 = this.mEverytimeFrame.getMeasuredWidth();
      i5 = this.mEverytimeFrame.getMeasuredHeight();
      i6 = this.mSessionFrame.getMeasuredWidth();
      i7 = this.mSessionFrame.getMeasuredHeight();
      i8 = PlayUtils.getViewLeftFromParentEnd(i, i6, bool, k);
      if (i6 + (i4 + this.mHorizontalButtonGap) > m) {
        break label464;
      }
      int i11 = PlayUtils.getViewLeftFromParentEnd(i, i4, bool, k + i6 + this.mHorizontalButtonGap);
      int i12 = i1 + (i3 - i7) / 2;
      this.mSessionFrame.layout(i8, i12, i8 + i6, i12 + i7);
      int i13 = i1 + (i3 - i5) / 2;
      this.mEverytimeFrame.layout(i11, i13, i11 + i4, i13 + i5);
      label217:
      if ((this.mEverytimeFrame == null) || (this.mEverytimeButton == null) || (this.mEverytimeButton.getVisibility() == 8) || ((this.mEverytimeButton.getHeight() >= this.mMinimumTouchTargetSize) && (this.mEverytimeButton.getWidth() >= this.mMinimumTouchTargetSize))) {
        break label529;
      }
      UiUtils.getTouchTarget(this.mEverytimeButton, this.mEverytimeArea, this.mMinimumTouchTargetSize, this.mMinimumTouchTargetSize);
      if (!this.mEverytimeArea.equals(this.mOldEverytimeArea))
      {
        this.mEverytimeFrame.setTouchDelegate(new TouchDelegate(this.mEverytimeArea, this.mEverytimeButton));
        this.mOldEverytimeArea.set(this.mEverytimeArea);
      }
    }
    for (;;)
    {
      if ((this.mSessionFrame == null) || (this.mSessionButton == null) || (this.mSessionButton.getVisibility() == 8) || ((this.mSessionButton.getHeight() >= this.mMinimumTouchTargetSize) && (this.mSessionButton.getWidth() >= this.mMinimumTouchTargetSize))) {
        break label547;
      }
      UiUtils.getTouchTarget(this.mSessionButton, this.mSessionArea, this.mMinimumTouchTargetSize, this.mMinimumTouchTargetSize);
      if (!this.mSessionArea.equals(this.mOldSessionArea))
      {
        this.mSessionFrame.setTouchDelegate(new TouchDelegate(this.mSessionArea, this.mSessionButton));
        this.mOldSessionArea.set(this.mSessionArea);
      }
      return;
      bool = false;
      break;
      label464:
      int i9 = PlayUtils.getViewLeftFromParentEnd(i, i4, bool, k);
      this.mSessionFrame.layout(i8, i1, i8 + i6, i1 + i7);
      int i10 = i1 + i7;
      this.mEverytimeFrame.layout(i9, i10, i9 + i4, i10 + i5);
      break label217;
      label529:
      this.mOldEverytimeArea.setEmpty();
      this.mEverytimeFrame.setTouchDelegate(null);
    }
    label547:
    this.mOldSessionArea.setEmpty();
    this.mSessionFrame.setTouchDelegate(null);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - ViewCompat.getPaddingStart(this) - ViewCompat.getPaddingEnd(this);
    int k = getPaddingTop() + getPaddingBottom();
    this.mSessionFrame.measure(0, 0);
    this.mEverytimeFrame.measure(0, 0);
    if (this.mSessionFrame.getMeasuredWidth() + this.mHorizontalButtonGap + this.mEverytimeFrame.getMeasuredWidth() <= j)
    {
      this.mSessionFrame.setPadding(this.mSessionFrame.getPaddingLeft(), this.mTopBottomPadding, this.mSessionFrame.getPaddingRight(), this.mTopBottomPadding);
      this.mEverytimeFrame.setPadding(this.mEverytimeFrame.getPaddingLeft(), this.mTopBottomPadding, this.mEverytimeFrame.getPaddingRight(), this.mTopBottomPadding);
      this.mSessionFrame.measure(0, 0);
      this.mEverytimeFrame.measure(0, 0);
      setMeasuredDimension(i, k + Math.max(this.mSessionFrame.getMeasuredHeight(), this.mEverytimeFrame.getMeasuredHeight()));
      return;
    }
    this.mSessionFrame.setPadding(this.mSessionFrame.getPaddingLeft(), this.mTopBottomPadding, this.mSessionFrame.getPaddingRight(), this.mVerticalButtonGap / 2);
    this.mEverytimeFrame.setPadding(this.mEverytimeFrame.getPaddingLeft(), this.mVerticalButtonGap / 2, this.mEverytimeFrame.getPaddingRight(), this.mTopBottomPadding);
    int m = View.MeasureSpec.makeMeasureSpec(j, -2147483648);
    this.mSessionFrame.measure(m, 0);
    this.mEverytimeFrame.measure(m, 0);
    setMeasuredDimension(i, k + this.mSessionFrame.getMeasuredHeight() + this.mEverytimeFrame.getMeasuredHeight());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AuthChoicesButtonBarLayout
 * JD-Core Version:    0.7.0.1
 */