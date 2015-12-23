package com.google.android.libraries.bind.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.google.android.libraries.bind.R.styleable;
import com.google.android.libraries.bind.data.Bound;
import com.google.android.libraries.bind.data.BoundHelper;
import com.google.android.libraries.bind.data.Data;

public class BoundTextView
  extends TextView
  implements Bound
{
  private Integer bindDrawableEndKey;
  private Integer bindDrawableStartKey;
  private Integer bindTextColorKey;
  private Integer bindTextKey;
  private final BoundHelper boundHelper = new BoundHelper(paramContext, paramAttributeSet, this);
  
  public BoundTextView(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public BoundTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BoundTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public BoundTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BoundTextView, paramInt1, paramInt2);
    if (isInEditMode())
    {
      String str = localTypedArray.getString(R.styleable.BoundTextView_bind__editModeText);
      if (str != null) {
        setText(str);
      }
    }
    this.bindTextKey = BoundHelper.getInteger(localTypedArray, R.styleable.BoundTextView_bindText);
    this.bindTextColorKey = BoundHelper.getInteger(localTypedArray, R.styleable.BoundTextView_bindTextColor);
    this.bindDrawableStartKey = BoundHelper.getInteger(localTypedArray, R.styleable.BoundTextView_bindDrawableStart);
    this.bindDrawableEndKey = BoundHelper.getInteger(localTypedArray, R.styleable.BoundTextView_bindDrawableEnd);
    localTypedArray.recycle();
  }
  
  private void updateBoundDrawableIfSpecified(Drawable[] paramArrayOfDrawable, Data paramData, Integer paramInteger, boolean paramBoolean)
  {
    Integer localInteger;
    int i;
    if (paramInteger != null)
    {
      if (paramData != null) {
        break label41;
      }
      localInteger = null;
      if ((ViewCompat.getLayoutDirection(this) != 0) || (!paramBoolean)) {
        break label54;
      }
      i = 0;
      label26:
      if (localInteger != null) {
        break label60;
      }
    }
    label41:
    label54:
    label60:
    for (Drawable localDrawable = null;; localDrawable = getContext().getResources().getDrawable(localInteger.intValue()))
    {
      paramArrayOfDrawable[i] = localDrawable;
      return;
      localInteger = paramData.getAsInteger(paramInteger.intValue());
      break;
      i = 2;
      break label26;
    }
  }
  
  @TargetApi(17)
  public final void updateBoundData(Data paramData)
  {
    this.boundHelper.updateBoundData(paramData);
    Object localObject3;
    BoundTextView localBoundTextView;
    label32:
    Object localObject1;
    if (this.bindTextKey != null)
    {
      if (paramData == null)
      {
        localObject3 = null;
        localBoundTextView = this;
        localBoundTextView.setText((CharSequence)localObject3);
      }
    }
    else if (this.bindTextColorKey != null)
    {
      if (paramData != null) {
        break label175;
      }
      localObject1 = null;
      label45:
      if (localObject1 != null) {
        break label190;
      }
      setTextColor(-1);
    }
    for (;;)
    {
      if ((this.bindDrawableStartKey != null) || (this.bindDrawableEndKey != null))
      {
        Drawable[] arrayOfDrawable = getCompoundDrawables();
        updateBoundDrawableIfSpecified(arrayOfDrawable, paramData, this.bindDrawableStartKey, true);
        updateBoundDrawableIfSpecified(arrayOfDrawable, paramData, this.bindDrawableEndKey, false);
        setCompoundDrawablesWithIntrinsicBounds(arrayOfDrawable[0], arrayOfDrawable[1], arrayOfDrawable[2], arrayOfDrawable[3]);
      }
      return;
      Object localObject2 = paramData.get(this.bindTextKey.intValue());
      if ((localObject2 instanceof SpannableString))
      {
        setText((SpannableString)localObject2, TextView.BufferType.SPANNABLE);
        break label32;
      }
      if (localObject2 == null)
      {
        localBoundTextView = this;
        localObject3 = null;
        break;
      }
      localObject3 = localObject2.toString();
      localBoundTextView = this;
      break;
      label175:
      localObject1 = paramData.get(this.bindTextColorKey.intValue());
      break label45;
      label190:
      if ((localObject1 instanceof ColorStateList)) {
        setTextColor((ColorStateList)localObject1);
      } else {
        setTextColor(getContext().getResources().getColor(((Integer)localObject1).intValue()));
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.BoundTextView
 * JD-Core Version:    0.7.0.1
 */