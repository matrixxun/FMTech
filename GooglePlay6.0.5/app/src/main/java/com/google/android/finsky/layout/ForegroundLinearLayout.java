package com.google.android.finsky.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.google.android.play.layout.WithForegroundLayer;

public class ForegroundLinearLayout
  extends LinearLayout
  implements WithForegroundLayer
{
  private static boolean IS_HC_OR_ABOVE;
  private static boolean IS_JBMR1_OR_ABOVE;
  private boolean mForegroundBoundsChanged = false;
  private Drawable mForegroundDrawable;
  private int mForegroundPaddingBottom = 0;
  private int mForegroundPaddingLeft = 0;
  private int mForegroundPaddingRight = 0;
  private int mForegroundPaddingTop = 0;
  private final Rect mOverlayBounds = new Rect();
  private final Rect mSelfBounds = new Rect();
  
  static
  {
    boolean bool1 = true;
    boolean bool2;
    if (Build.VERSION.SDK_INT >= 11)
    {
      bool2 = bool1;
      IS_HC_OR_ABOVE = bool2;
      if (Build.VERSION.SDK_INT < 17) {
        break label34;
      }
    }
    for (;;)
    {
      IS_JBMR1_OR_ABOVE = bool1;
      return;
      bool2 = false;
      break;
      label34:
      bool1 = false;
    }
  }
  
  public ForegroundLinearLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ForegroundLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initializeForeground(paramContext, paramAttributeSet);
  }
  
  public ForegroundLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initializeForeground(paramContext, paramAttributeSet);
  }
  
  private void initializeForeground(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16843017 });
    Drawable localDrawable = localTypedArray.getDrawable(0);
    if (localDrawable != null) {
      setForeground(localDrawable);
    }
    localTypedArray.recycle();
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    Drawable localDrawable;
    Rect localRect1;
    Rect localRect2;
    if (this.mForegroundDrawable != null)
    {
      localDrawable = this.mForegroundDrawable;
      if (this.mForegroundBoundsChanged)
      {
        this.mForegroundBoundsChanged = false;
        localRect1 = this.mSelfBounds;
        localRect2 = this.mOverlayBounds;
        int i = getWidth();
        int j = getHeight();
        localRect1.set(this.mForegroundPaddingLeft, this.mForegroundPaddingTop, i - this.mForegroundPaddingRight, j - this.mForegroundPaddingBottom);
        if (!IS_JBMR1_OR_ABOVE) {
          break label120;
        }
        int k = getLayoutDirection();
        GravityCompat.apply(119, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight(), localRect1, localRect2, k);
      }
    }
    for (;;)
    {
      localDrawable.setBounds(localRect2);
      localDrawable.draw(paramCanvas);
      return;
      label120:
      this.mOverlayBounds.set(localRect1);
    }
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2)
  {
    super.drawableHotspotChanged(paramFloat1, paramFloat2);
    if (this.mForegroundDrawable != null) {
      this.mForegroundDrawable.setHotspot(paramFloat1, paramFloat2);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    if ((this.mForegroundDrawable != null) && (this.mForegroundDrawable.isStateful())) {
      this.mForegroundDrawable.setState(getDrawableState());
    }
  }
  
  public Drawable getForeground()
  {
    return this.mForegroundDrawable;
  }
  
  @TargetApi(11)
  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    if ((IS_HC_OR_ABOVE) && (this.mForegroundDrawable != null)) {
      this.mForegroundDrawable.jumpToCurrentState();
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mForegroundBoundsChanged = true;
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mForegroundBoundsChanged = true;
  }
  
  public void setForeground(Drawable paramDrawable)
  {
    if (this.mForegroundDrawable != paramDrawable)
    {
      if (this.mForegroundDrawable != null)
      {
        this.mForegroundDrawable.setCallback(null);
        unscheduleDrawable(this.mForegroundDrawable);
      }
      this.mForegroundDrawable = paramDrawable;
      this.mForegroundPaddingLeft = 0;
      this.mForegroundPaddingTop = 0;
      this.mForegroundPaddingRight = 0;
      this.mForegroundPaddingBottom = 0;
      if (paramDrawable == null) {
        break label143;
      }
      setWillNotDraw(false);
      paramDrawable.setCallback(this);
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(getDrawableState());
      }
      Rect localRect = new Rect();
      if (paramDrawable.getPadding(localRect))
      {
        this.mForegroundPaddingLeft = localRect.left;
        this.mForegroundPaddingTop = localRect.top;
        this.mForegroundPaddingRight = localRect.right;
        this.mForegroundPaddingBottom = localRect.bottom;
      }
    }
    for (;;)
    {
      requestLayout();
      invalidate();
      return;
      label143:
      setWillNotDraw(true);
    }
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (this.mForegroundDrawable != null) {
      if (paramInt != 0) {
        break label29;
      }
    }
    label29:
    for (boolean bool = true;; bool = false)
    {
      this.mForegroundDrawable.setVisible(bool, false);
      return;
    }
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    return (super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mForegroundDrawable);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ForegroundLinearLayout
 * JD-Core Version:    0.7.0.1
 */