package com.google.android.libraries.bind.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.libraries.bind.R.styleable;
import com.google.android.libraries.bind.data.Bound;
import com.google.android.libraries.bind.data.BoundHelper;
import com.google.android.libraries.bind.data.Data;
import com.google.android.libraries.bind.util.Util;

public class BoundImageView
  extends ImageView
  implements Bound
{
  private Integer bindDrawableKey;
  private Integer bindImageUriKey;
  private final BoundHelper boundHelper = new BoundHelper(paramContext, paramAttributeSet, this);
  private Integer currentDrawableRef;
  
  public BoundImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BoundImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BoundImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BoundImageView, paramInt, 0);
    this.bindImageUriKey = BoundHelper.getInteger(localTypedArray, R.styleable.BoundImageView_bindImageUri);
    this.bindDrawableKey = BoundHelper.getInteger(localTypedArray, R.styleable.BoundImageView_bindDrawable);
    localTypedArray.recycle();
  }
  
  public final void updateBoundData(Data paramData)
  {
    this.boundHelper.updateBoundData(paramData);
    Object localObject1;
    if (this.bindImageUriKey != null)
    {
      Object localObject2 = paramData.get(this.bindImageUriKey.intValue());
      if ((localObject2 instanceof Uri)) {
        setImageURI((Uri)localObject2);
      }
    }
    else if (this.bindDrawableKey != null)
    {
      if (paramData != null) {
        break label94;
      }
      localObject1 = null;
      label58:
      if (!Util.objectsEqual(this.currentDrawableRef, localObject1))
      {
        this.currentDrawableRef = ((Integer)localObject1);
        if (localObject1 != null) {
          break label109;
        }
      }
    }
    label94:
    label109:
    for (Drawable localDrawable = null;; localDrawable = getContext().getResources().getDrawable(((Integer)localObject1).intValue()))
    {
      setImageDrawable(localDrawable);
      return;
      setImageURI(null);
      break;
      localObject1 = paramData.getAsInteger(this.bindDrawableKey.intValue());
      break label58;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.widget.BoundImageView
 * JD-Core Version:    0.7.0.1
 */