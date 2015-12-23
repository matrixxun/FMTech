package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.R.styleable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;

public class ForegroundLinearLayout
  extends LinearLayoutCompat
{
  private Drawable mForeground;
  boolean mForegroundBoundsChanged = false;
  private int mForegroundGravity = 119;
  protected boolean mForegroundInPadding = true;
  private final Rect mOverlayBounds = new Rect();
  private final Rect mSelfBounds = new Rect();
  
  public ForegroundLinearLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public ForegroundLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ForegroundLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ForegroundLinearLayout, paramInt, 0);
    this.mForegroundGravity = localTypedArray.getInt(R.styleable.ForegroundLinearLayout_android_foregroundGravity, this.mForegroundGravity);
    Drawable localDrawable = localTypedArray.getDrawable(R.styleable.ForegroundLinearLayout_android_foreground);
    if (localDrawable != null) {
      setForeground(localDrawable);
    }
    this.mForegroundInPadding = localTypedArray.getBoolean(R.styleable.ForegroundLinearLayout_foregroundInsidePadding, true);
    localTypedArray.recycle();
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    Drawable localDrawable;
    Rect localRect1;
    Rect localRect2;
    int i;
    int j;
    if (this.mForeground != null)
    {
      localDrawable = this.mForeground;
      if (this.mForegroundBoundsChanged)
      {
        this.mForegroundBoundsChanged = false;
        localRect1 = this.mSelfBounds;
        localRect2 = this.mOverlayBounds;
        i = getRight() - getLeft();
        j = getBottom() - getTop();
        if (!this.mForegroundInPadding) {
          break label109;
        }
        localRect1.set(0, 0, i, j);
      }
    }
    for (;;)
    {
      Gravity.apply(this.mForegroundGravity, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight(), localRect1, localRect2);
      localDrawable.setBounds(localRect2);
      localDrawable.draw(paramCanvas);
      return;
      label109:
      localRect1.set(getPaddingLeft(), getPaddingTop(), i - getPaddingRight(), j - getPaddingBottom());
    }
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2)
  {
    super.drawableHotspotChanged(paramFloat1, paramFloat2);
    if (this.mForeground != null) {
      this.mForeground.setHotspot(paramFloat1, paramFloat2);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    if ((this.mForeground != null) && (this.mForeground.isStateful())) {
      this.mForeground.setState(getDrawableState());
    }
  }
  
  public Drawable getForeground()
  {
    return this.mForeground;
  }
  
  public int getForegroundGravity()
  {
    return this.mForegroundGravity;
  }
  
  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    if (this.mForeground != null) {
      this.mForeground.jumpToCurrentState();
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mForegroundBoundsChanged = (paramBoolean | this.mForegroundBoundsChanged);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mForegroundBoundsChanged = true;
  }
  
  public void setForeground(Drawable paramDrawable)
  {
    if (this.mForeground != paramDrawable)
    {
      if (this.mForeground != null)
      {
        this.mForeground.setCallback(null);
        unscheduleDrawable(this.mForeground);
      }
      this.mForeground = paramDrawable;
      if (paramDrawable == null) {
        break label96;
      }
      setWillNotDraw(false);
      paramDrawable.setCallback(this);
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(getDrawableState());
      }
      if (this.mForegroundGravity == 119) {
        paramDrawable.getPadding(new Rect());
      }
    }
    for (;;)
    {
      requestLayout();
      invalidate();
      return;
      label96:
      setWillNotDraw(true);
    }
  }
  
  public void setForegroundGravity(int paramInt)
  {
    if (this.mForegroundGravity != paramInt)
    {
      if ((0x800007 & paramInt) == 0) {
        paramInt |= 0x800003;
      }
      if ((paramInt & 0x70) == 0) {
        paramInt |= 0x30;
      }
      this.mForegroundGravity = paramInt;
      if ((this.mForegroundGravity == 119) && (this.mForeground != null))
      {
        Rect localRect = new Rect();
        this.mForeground.getPadding(localRect);
      }
      requestLayout();
    }
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    return (super.verifyDrawable(paramDrawable)) || (paramDrawable == this.mForeground);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.design.internal.ForegroundLinearLayout
 * JD-Core Version:    0.7.0.1
 */