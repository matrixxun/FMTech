package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;

public final class ThemeUtils
{
  static final int[] ACTIVATED_STATE_SET;
  static final int[] CHECKED_STATE_SET;
  static final int[] DISABLED_STATE_SET;
  static final int[] EMPTY_STATE_SET = new int[0];
  static final int[] FOCUSED_STATE_SET;
  static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET;
  static final int[] PRESSED_STATE_SET;
  static final int[] SELECTED_STATE_SET;
  private static final int[] TEMP_ARRAY = new int[1];
  private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal();
  
  static
  {
    DISABLED_STATE_SET = new int[] { -16842910 };
    FOCUSED_STATE_SET = new int[] { 16842908 };
    ACTIVATED_STATE_SET = new int[] { 16843518 };
    PRESSED_STATE_SET = new int[] { 16842919 };
    CHECKED_STATE_SET = new int[] { 16842912 };
    SELECTED_STATE_SET = new int[] { 16842913 };
    NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[] { -16842919, -16842908 };
  }
  
  public static int getDisabledThemeAttrColor(Context paramContext, int paramInt)
  {
    ColorStateList localColorStateList = getThemeAttrColorStateList(paramContext, paramInt);
    if ((localColorStateList != null) && (localColorStateList.isStateful())) {
      return localColorStateList.getColorForState(DISABLED_STATE_SET, localColorStateList.getDefaultColor());
    }
    TypedValue localTypedValue1 = (TypedValue)TL_TYPED_VALUE.get();
    TypedValue localTypedValue2;
    if (localTypedValue1 == null)
    {
      localTypedValue2 = new TypedValue();
      TL_TYPED_VALUE.set(localTypedValue2);
    }
    for (;;)
    {
      paramContext.getTheme().resolveAttribute(16842803, localTypedValue2, true);
      return getThemeAttrColor(paramContext, paramInt, localTypedValue2.getFloat());
      localTypedValue2 = localTypedValue1;
    }
  }
  
  public static int getThemeAttrColor(Context paramContext, int paramInt)
  {
    TEMP_ARRAY[0] = paramInt;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(null, TEMP_ARRAY);
    try
    {
      int i = localTypedArray.getColor(0, 0);
      return i;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  static int getThemeAttrColor(Context paramContext, int paramInt, float paramFloat)
  {
    int i = getThemeAttrColor(paramContext, paramInt);
    return ColorUtils.setAlphaComponent(i, Math.round(paramFloat * Color.alpha(i)));
  }
  
  public static ColorStateList getThemeAttrColorStateList(Context paramContext, int paramInt)
  {
    TEMP_ARRAY[0] = paramInt;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(null, TEMP_ARRAY);
    try
    {
      ColorStateList localColorStateList = localTypedArray.getColorStateList(0);
      return localColorStateList;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ThemeUtils
 * JD-Core Version:    0.7.0.1
 */