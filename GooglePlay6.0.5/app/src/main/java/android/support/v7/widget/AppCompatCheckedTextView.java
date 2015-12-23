package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

public final class AppCompatCheckedTextView
  extends CheckedTextView
{
  private static final int[] TINT_ATTRS = { 16843016 };
  private AppCompatTextHelper mTextHelper = AppCompatTextHelper.create(this);
  private TintManager mTintManager;
  
  public AppCompatCheckedTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, (byte)0);
  }
  
  private AppCompatCheckedTextView(Context paramContext, AttributeSet paramAttributeSet, byte paramByte)
  {
    super(paramContext, paramAttributeSet, 16843720);
    this.mTextHelper.loadFromAttributes(paramAttributeSet, 16843720);
    this.mTextHelper.applyCompoundDrawablesTints();
    if (TintManager.SHOULD_BE_USED)
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(getContext(), paramAttributeSet, TINT_ATTRS, 16843720);
      setCheckMarkDrawable(localTintTypedArray.getDrawable(0));
      localTintTypedArray.mWrapped.recycle();
      this.mTintManager = localTintTypedArray.getTintManager();
    }
  }
  
  protected final void drawableStateChanged()
  {
    super.drawableStateChanged();
    if (this.mTextHelper != null) {
      this.mTextHelper.applyCompoundDrawablesTints();
    }
  }
  
  public final void setCheckMarkDrawable(int paramInt)
  {
    if (this.mTintManager != null)
    {
      setCheckMarkDrawable(this.mTintManager.getDrawable(paramInt, false));
      return;
    }
    super.setCheckMarkDrawable(paramInt);
  }
  
  public final void setTextAppearance(Context paramContext, int paramInt)
  {
    super.setTextAppearance(paramContext, paramInt);
    if (this.mTextHelper != null) {
      this.mTextHelper.onSetTextAppearance(paramContext, paramInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatCheckedTextView
 * JD-Core Version:    0.7.0.1
 */