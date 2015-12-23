package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.PlayUtils;

public class LightPurchaseButtonBarLayout
  extends ViewGroup
{
  private int mContentFormat;
  private final Rect mContinueArea;
  private View mContinueButton;
  private View mContinueButtonFrame;
  private final int mHorizontalItemGap;
  private View mLogo;
  private final int mMinimumTouchTargetSize;
  private final Rect mOldContinueArea;
  private final Rect mOldSecondaryArea;
  private final Rect mSecondaryArea;
  private View mSecondaryButton;
  private View mSecondaryButtonFrame;
  private final int mVerticalItemGap;
  
  public LightPurchaseButtonBarLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LightPurchaseButtonBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getResources();
    this.mHorizontalItemGap = localResources.getDimensionPixelSize(2131493335);
    this.mVerticalItemGap = localResources.getDimensionPixelSize(2131493338);
    this.mMinimumTouchTargetSize = localResources.getDimensionPixelSize(2131493521);
    this.mContinueArea = new Rect();
    this.mOldContinueArea = new Rect();
    this.mSecondaryArea = new Rect();
    this.mOldSecondaryArea = new Rect();
  }
  
  private boolean isLogoVisible()
  {
    return this.mLogo.getVisibility() == 0;
  }
  
  private boolean isSecondaryButtonVisible()
  {
    return (this.mSecondaryButtonFrame != null) && (this.mSecondaryButtonFrame.getVisibility() == 0);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mLogo = findViewById(2131755606);
    this.mContinueButtonFrame = findViewById(2131755630);
    this.mContinueButton = findViewById(2131755631);
    this.mSecondaryButtonFrame = findViewById(2131755632);
    this.mSecondaryButton = findViewById(2131755633);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    int i;
    int k;
    int n;
    int i16;
    label100:
    int i3;
    int i4;
    int i5;
    label171:
    int i7;
    int i8;
    int i9;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      bool = true;
      i = getWidth();
      int j = ViewCompat.getPaddingStart(this);
      k = ViewCompat.getPaddingEnd(this);
      int m = getHeight();
      n = getPaddingTop();
      int i1 = getPaddingBottom();
      int i2 = m - n - i1;
      if (isLogoVisible())
      {
        int i14 = this.mLogo.getMeasuredWidth();
        int i15 = this.mLogo.getMeasuredHeight();
        if (this.mContentFormat != 0) {
          break label352;
        }
        i16 = n + (i2 - i15) / 2;
        int i17 = PlayUtils.getViewLeftFromParentStart(i, i14, bool, j);
        this.mLogo.layout(i17, i16, i17 + i14, i16 + i15);
      }
      i3 = this.mContinueButtonFrame.getMeasuredWidth();
      i4 = this.mContinueButtonFrame.getMeasuredHeight();
      if (this.mContentFormat != 0) {
        break label359;
      }
      i5 = n + (i2 - i4) / 2;
      int i6 = PlayUtils.getViewLeftFromParentEnd(i, i3, bool, k);
      this.mContinueButtonFrame.layout(i6, i5, i6 + i3, i5 + i4);
      if (isSecondaryButtonVisible())
      {
        i7 = this.mSecondaryButtonFrame.getMeasuredWidth();
        i8 = this.mSecondaryButtonFrame.getMeasuredHeight();
        if (this.mContentFormat != 0) {
          break label394;
        }
        i9 = n + (i2 - i8) / 2;
      }
    }
    for (int i10 = i3 + (i7 + this.mHorizontalItemGap);; i10 = i7)
    {
      int i11 = PlayUtils.getViewLeftFromParentEnd(i, i10, bool, k);
      View localView = this.mSecondaryButtonFrame;
      int i12 = i11 + i7;
      int i13 = i9 + i8;
      localView.layout(i11, i9, i12, i13);
      UiUtils.ensureMinimumTouchTargetSize(this.mContinueButton, this.mContinueArea, this.mOldContinueArea, this.mMinimumTouchTargetSize);
      UiUtils.ensureMinimumTouchTargetSize(this.mSecondaryButton, this.mSecondaryArea, this.mOldSecondaryArea, this.mMinimumTouchTargetSize);
      return;
      bool = false;
      break;
      label352:
      i16 = n;
      break label100;
      label359:
      if (this.mContentFormat == 1)
      {
        i5 = n + this.mLogo.getMeasuredHeight() + this.mVerticalItemGap;
        break label171;
      }
      i5 = n;
      break label171;
      label394:
      i9 = i5 + i4 + this.mVerticalItemGap;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mContinueButtonFrame.setVisibility(this.mContinueButton.getVisibility());
    int i;
    int j;
    int m;
    int k;
    if ((this.mSecondaryButtonFrame != null) && (this.mSecondaryButton != null))
    {
      this.mSecondaryButtonFrame.setVisibility(this.mSecondaryButton.getVisibility());
      if ((this.mContinueButton.getVisibility() == 0) && (this.mSecondaryButton.getVisibility() == 0)) {
        this.mLogo.setVisibility(8);
      }
    }
    else
    {
      i = View.MeasureSpec.getSize(paramInt1);
      j = i - ViewCompat.getPaddingStart(this) - ViewCompat.getPaddingEnd(this);
      if (!isLogoVisible()) {
        break label358;
      }
      this.mLogo.measure(0, 0);
      m = this.mLogo.getMeasuredWidth() + this.mHorizontalItemGap;
      k = this.mLogo.getMeasuredHeight();
    }
    for (;;)
    {
      this.mContinueButtonFrame.measure(0, 0);
      int n = this.mContinueButtonFrame.getMeasuredWidth();
      int i1 = this.mContinueButtonFrame.getMeasuredHeight();
      if (isSecondaryButtonVisible())
      {
        this.mSecondaryButtonFrame.measure(0, 0);
        int i3 = this.mSecondaryButtonFrame.getMeasuredWidth();
        i1 = Math.max(i1, this.mSecondaryButtonFrame.getMeasuredHeight());
        n += i3 + this.mHorizontalItemGap;
      }
      int i2;
      if (m + n <= j)
      {
        this.mContentFormat = 0;
        i2 = Math.max(k, i1);
      }
      for (;;)
      {
        setMeasuredDimension(i, i2 + getPaddingTop() + getPaddingBottom());
        return;
        this.mLogo.setVisibility(0);
        break;
        if (!isSecondaryButtonVisible())
        {
          this.mContinueButtonFrame.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
          this.mContentFormat = 0;
          i2 = this.mContinueButtonFrame.getMeasuredHeight();
        }
        else
        {
          this.mContinueButtonFrame.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
          this.mSecondaryButtonFrame.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
          this.mContentFormat = 2;
          i2 = this.mContinueButtonFrame.getMeasuredHeight() + this.mVerticalItemGap + this.mSecondaryButtonFrame.getMeasuredHeight();
        }
      }
      label358:
      k = 0;
      m = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.LightPurchaseButtonBarLayout
 * JD-Core Version:    0.7.0.1
 */