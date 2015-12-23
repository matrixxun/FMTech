package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public final class TintTypedArray
{
  private final Context mContext;
  private TintManager mTintManager;
  public final TypedArray mWrapped;
  
  private TintTypedArray(Context paramContext, TypedArray paramTypedArray)
  {
    this.mContext = paramContext;
    this.mWrapped = paramTypedArray;
  }
  
  public static TintTypedArray obtainStyledAttributes(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfInt)
  {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt));
  }
  
  public static TintTypedArray obtainStyledAttributes$1a6c1917(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfInt, int paramInt)
  {
    return new TintTypedArray(paramContext, paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt, paramInt, 0));
  }
  
  public final boolean getBoolean(int paramInt, boolean paramBoolean)
  {
    return this.mWrapped.getBoolean(paramInt, paramBoolean);
  }
  
  public final int getColor$255f288(int paramInt)
  {
    return this.mWrapped.getColor(paramInt, -1);
  }
  
  public final int getDimensionPixelOffset(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getDimensionPixelOffset(paramInt1, paramInt2);
  }
  
  public final int getDimensionPixelSize(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getDimensionPixelSize(paramInt1, paramInt2);
  }
  
  public final Drawable getDrawable(int paramInt)
  {
    if (this.mWrapped.hasValue(paramInt))
    {
      int i = this.mWrapped.getResourceId(paramInt, 0);
      if (i != 0) {
        return getTintManager().getDrawable(i, false);
      }
    }
    return this.mWrapped.getDrawable(paramInt);
  }
  
  public final Drawable getDrawableIfKnown(int paramInt)
  {
    if (this.mWrapped.hasValue(paramInt))
    {
      int i = this.mWrapped.getResourceId(paramInt, 0);
      if (i != 0) {
        return getTintManager().getDrawable(i, true);
      }
    }
    return null;
  }
  
  public final int getInt(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getInt(paramInt1, paramInt2);
  }
  
  public final int getLayoutDimension(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getLayoutDimension(paramInt1, paramInt2);
  }
  
  public final int getResourceId(int paramInt1, int paramInt2)
  {
    return this.mWrapped.getResourceId(paramInt1, paramInt2);
  }
  
  public final CharSequence getText(int paramInt)
  {
    return this.mWrapped.getText(paramInt);
  }
  
  public final TintManager getTintManager()
  {
    if (this.mTintManager == null) {
      this.mTintManager = TintManager.get(this.mContext);
    }
    return this.mTintManager;
  }
  
  public final boolean hasValue(int paramInt)
  {
    return this.mWrapped.hasValue(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.TintTypedArray
 * JD-Core Version:    0.7.0.1
 */