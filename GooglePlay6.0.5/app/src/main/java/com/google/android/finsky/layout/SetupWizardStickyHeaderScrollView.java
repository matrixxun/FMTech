package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ScrollView;

public class SetupWizardStickyHeaderScrollView
  extends ScrollView
{
  private int mStatusBarInset = 0;
  private View mSticky;
  private View mStickyContainer;
  private RectF mStickyRect = new RectF();
  
  public SetupWizardStickyHeaderScrollView(Context paramContext)
  {
    super(paramContext);
  }
  
  public SetupWizardStickyHeaderScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SetupWizardStickyHeaderScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mStickyRect.contains(paramMotionEvent.getX(), paramMotionEvent.getY()))
    {
      paramMotionEvent.offsetLocation(-this.mStickyRect.left, -this.mStickyRect.top);
      return this.mStickyContainer.dispatchTouchEvent(paramMotionEvent);
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public void draw(Canvas paramCanvas)
  {
    setVerticalScrollBarEnabled(false);
    super.draw(paramCanvas);
    int i;
    View localView;
    int j;
    if (this.mSticky != null)
    {
      i = paramCanvas.save();
      if (this.mStickyContainer == null) {
        break label168;
      }
      localView = this.mStickyContainer;
      if (this.mStickyContainer == null) {
        break label176;
      }
      j = this.mSticky.getTop();
      label50:
      int k = localView.getTop() - getScrollY();
      if ((k + j >= this.mStatusBarInset) && (localView.isShown())) {
        break label182;
      }
      this.mStickyRect.set(0.0F, -j + this.mStatusBarInset, localView.getWidth(), localView.getHeight() - j + this.mStatusBarInset);
      paramCanvas.translate(0.0F, -k + this.mStickyRect.top);
      paramCanvas.clipRect(0, 0, localView.getWidth(), localView.getHeight());
      localView.draw(paramCanvas);
    }
    for (;;)
    {
      paramCanvas.restoreToCount(i);
      setVerticalScrollBarEnabled(true);
      onDrawScrollBars(paramCanvas);
      return;
      label168:
      localView = this.mSticky;
      break;
      label176:
      j = 0;
      break label50;
      label182:
      this.mStickyRect.setEmpty();
    }
  }
  
  public WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets)
  {
    if (getFitsSystemWindows()) {
      this.mStatusBarInset = paramWindowInsets.getSystemWindowInsetTop();
    }
    return paramWindowInsets;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mSticky == null)
    {
      this.mSticky = findViewWithTag("sticky");
      this.mStickyContainer = findViewWithTag("stickyContainer");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SetupWizardStickyHeaderScrollView
 * JD-Core Version:    0.7.0.1
 */