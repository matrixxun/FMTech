package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.text.AllCapsTransformationMethod;
import android.support.v7.internal.widget.TintInfo;
import android.support.v7.internal.widget.TintManager;
import android.util.AttributeSet;
import android.widget.TextView;

class AppCompatTextHelper
{
  private static final int[] TEXT_APPEARANCE_ATTRS;
  private static final int[] VIEW_ATTRS = { 16842804, 16843119, 16843117, 16843120, 16843118 };
  private TintInfo mDrawableBottomTint;
  private TintInfo mDrawableLeftTint;
  private TintInfo mDrawableRightTint;
  private TintInfo mDrawableTopTint;
  final TextView mView;
  
  static
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.textAllCaps;
    TEXT_APPEARANCE_ATTRS = arrayOfInt;
  }
  
  AppCompatTextHelper(TextView paramTextView)
  {
    this.mView = paramTextView;
  }
  
  static AppCompatTextHelper create(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return new AppCompatTextHelperV17(paramTextView);
    }
    return new AppCompatTextHelper(paramTextView);
  }
  
  final void applyCompoundDrawableTint(Drawable paramDrawable, TintInfo paramTintInfo)
  {
    if ((paramDrawable != null) && (paramTintInfo != null)) {
      TintManager.tintDrawable(paramDrawable, paramTintInfo, this.mView.getDrawableState());
    }
  }
  
  void applyCompoundDrawablesTints()
  {
    if ((this.mDrawableLeftTint != null) || (this.mDrawableTopTint != null) || (this.mDrawableRightTint != null) || (this.mDrawableBottomTint != null))
    {
      Drawable[] arrayOfDrawable = this.mView.getCompoundDrawables();
      applyCompoundDrawableTint(arrayOfDrawable[0], this.mDrawableLeftTint);
      applyCompoundDrawableTint(arrayOfDrawable[1], this.mDrawableTopTint);
      applyCompoundDrawableTint(arrayOfDrawable[2], this.mDrawableRightTint);
      applyCompoundDrawableTint(arrayOfDrawable[3], this.mDrawableBottomTint);
    }
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    Context localContext = this.mView.getContext();
    TintManager localTintManager = TintManager.get(localContext);
    TypedArray localTypedArray1 = localContext.obtainStyledAttributes(paramAttributeSet, VIEW_ATTRS, paramInt, 0);
    int i = localTypedArray1.getResourceId(0, -1);
    if (localTypedArray1.hasValue(1))
    {
      this.mDrawableLeftTint = new TintInfo();
      this.mDrawableLeftTint.mHasTintList = true;
      this.mDrawableLeftTint.mTintList = localTintManager.getTintList(localTypedArray1.getResourceId(1, 0));
    }
    if (localTypedArray1.hasValue(2))
    {
      this.mDrawableTopTint = new TintInfo();
      this.mDrawableTopTint.mHasTintList = true;
      this.mDrawableTopTint.mTintList = localTintManager.getTintList(localTypedArray1.getResourceId(2, 0));
    }
    if (localTypedArray1.hasValue(3))
    {
      this.mDrawableRightTint = new TintInfo();
      this.mDrawableRightTint.mHasTintList = true;
      this.mDrawableRightTint.mTintList = localTintManager.getTintList(localTypedArray1.getResourceId(3, 0));
    }
    if (localTypedArray1.hasValue(4))
    {
      this.mDrawableBottomTint = new TintInfo();
      this.mDrawableBottomTint.mHasTintList = true;
      this.mDrawableBottomTint.mTintList = localTintManager.getTintList(localTypedArray1.getResourceId(4, 0));
    }
    localTypedArray1.recycle();
    if (i != -1)
    {
      TypedArray localTypedArray3 = localContext.obtainStyledAttributes(i, R.styleable.TextAppearance);
      if (localTypedArray3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
        setAllCaps(localTypedArray3.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
      }
      localTypedArray3.recycle();
    }
    TypedArray localTypedArray2 = localContext.obtainStyledAttributes(paramAttributeSet, TEXT_APPEARANCE_ATTRS, paramInt, 0);
    if (localTypedArray2.getBoolean(0, false)) {
      setAllCaps(true);
    }
    localTypedArray2.recycle();
  }
  
  final void onSetTextAppearance(Context paramContext, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, TEXT_APPEARANCE_ATTRS);
    if (localTypedArray.hasValue(0)) {
      setAllCaps(localTypedArray.getBoolean(0, false));
    }
    localTypedArray.recycle();
  }
  
  final void setAllCaps(boolean paramBoolean)
  {
    TextView localTextView = this.mView;
    if (paramBoolean) {}
    for (AllCapsTransformationMethod localAllCapsTransformationMethod = new AllCapsTransformationMethod(this.mView.getContext());; localAllCapsTransformationMethod = null)
    {
      localTextView.setTransformationMethod(localAllCapsTransformationMethod);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatTextHelper
 * JD-Core Version:    0.7.0.1
 */