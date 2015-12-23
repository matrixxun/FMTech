package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.play.utils.PlayAccessibilityHelper;

public class ProfileButton
  extends TextView
{
  Drawable mIcon;
  private final int mIconTextGap;
  private int mOriginalStartPadding;
  
  public ProfileButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ProfileButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mIconTextGap = paramContext.getResources().getDimensionPixelSize(2131493455);
    this.mOriginalStartPadding = ViewCompat.getPaddingStart(this);
    setWillNotDraw(false);
  }
  
  final void configure(String paramString, int paramInt)
  {
    int i = this.mOriginalStartPadding;
    int j = ViewCompat.getPaddingEnd(this);
    int k = getPaddingTop();
    int m = getPaddingBottom();
    setText(paramString);
    setBackgroundResource(paramInt);
    if (this.mIcon != null)
    {
      this.mIcon.setBounds(0, 0, this.mIcon.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight());
      i += this.mIcon.getIntrinsicWidth() + this.mIconTextGap;
    }
    ViewCompat.setPaddingRelative(this, i, k, j, m);
  }
  
  public final void configure(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt1 > 0) {}
    for (Drawable localDrawable = ContextCompat.getDrawable(getContext(), paramInt1);; localDrawable = null)
    {
      this.mIcon = localDrawable;
      configure(paramString, paramInt2);
      return;
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mIcon == null) {
      return;
    }
    int i = (getHeight() - this.mIcon.getIntrinsicHeight()) / 2;
    Layout localLayout = getLayout();
    int j = getScrollX();
    int k = getPaddingLeft();
    int m;
    label73:
    int i4;
    if (ViewCompat.getLayoutDirection(this) == 0)
    {
      m = 1;
      n = 0;
      if (localLayout == null) {
        break label155;
      }
      if (m == 0) {
        break label126;
      }
      n = getWidth();
      int i3 = localLayout.getLineCount();
      i4 = 0;
      label82:
      if (i4 >= i3) {
        break label155;
      }
      if (m == 0) {
        break label132;
      }
    }
    label132:
    for (int n = Math.min(n, k + (int)localLayout.getLineLeft(i4) - j);; n = Math.max(n, k + (int)localLayout.getLineRight(i4) - j))
    {
      i4++;
      break label82;
      m = 0;
      break;
      label126:
      n = 0;
      break label73;
    }
    label155:
    int i1 = this.mIcon.getIntrinsicWidth();
    paramCanvas.save();
    if (m != 0) {}
    for (int i2 = n - i1 - this.mIconTextGap;; i2 = n + this.mIconTextGap)
    {
      paramCanvas.translate(j + i2, i);
      this.mIcon.draw(paramCanvas);
      paramCanvas.restore();
      return;
    }
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    if (isClickable()) {
      PlayAccessibilityHelper.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo, Button.class.getName());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.ProfileButton
 * JD-Core Version:    0.7.0.1
 */