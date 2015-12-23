package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.styleable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;

public class ButtonBarLayout
  extends LinearLayout
{
  private boolean mAllowStacking;
  private int mLastWidthSize = -1;
  
  public ButtonBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ButtonBarLayout);
    this.mAllowStacking = localTypedArray.getBoolean(R.styleable.ButtonBarLayout_allowStacking, false);
    localTypedArray.recycle();
  }
  
  private boolean isStacked()
  {
    return getOrientation() == 1;
  }
  
  private void setStacked(boolean paramBoolean)
  {
    int i;
    int j;
    label17:
    View localView;
    if (paramBoolean)
    {
      i = 1;
      setOrientation(i);
      if (!paramBoolean) {
        break label86;
      }
      j = 5;
      setGravity(j);
      localView = findViewById(R.id.spacer);
      if (localView != null) {
        if (!paramBoolean) {
          break label92;
        }
      }
    }
    label86:
    label92:
    for (int m = 8;; m = 4)
    {
      localView.setVisibility(m);
      for (int k = -2 + getChildCount(); k >= 0; k--) {
        bringChildToFront(getChildAt(k));
      }
      i = 0;
      break;
      j = 80;
      break label17;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mAllowStacking)
    {
      if ((i > this.mLastWidthSize) && (isStacked())) {
        setStacked(false);
      }
      this.mLastWidthSize = i;
    }
    int j;
    if ((!isStacked()) && (View.MeasureSpec.getMode(paramInt1) == 1073741824)) {
      j = View.MeasureSpec.makeMeasureSpec(i, -2147483648);
    }
    for (int k = 1;; k = 0)
    {
      super.onMeasure(j, paramInt2);
      if ((this.mAllowStacking) && (!isStacked()) && ((0xFF000000 & getMeasuredWidthAndState()) == 16777216))
      {
        setStacked(true);
        k = 1;
      }
      if (k != 0) {
        super.onMeasure(paramInt1, paramInt2);
      }
      return;
      j = paramInt1;
    }
  }
  
  public void setAllowStacking(boolean paramBoolean)
  {
    if (this.mAllowStacking != paramBoolean)
    {
      this.mAllowStacking = paramBoolean;
      if ((!this.mAllowStacking) && (getOrientation() == 1)) {
        setStacked(false);
      }
      requestLayout();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ButtonBarLayout
 * JD-Core Version:    0.7.0.1
 */