package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.R.attr;

final class ThemeUtils
{
  private static final int[] APPCOMPAT_CHECK_ATTRS;
  
  static
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.colorPrimary;
    APPCOMPAT_CHECK_ATTRS = arrayOfInt;
  }
  
  static void checkAppCompatTheme(Context paramContext)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
    boolean bool = localTypedArray.hasValue(0);
    int i = 0;
    if (!bool) {
      i = 1;
    }
    if (localTypedArray != null) {
      localTypedArray.recycle();
    }
    if (i != 0) {
      throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.ThemeUtils
 * JD-Core Version:    0.7.0.1
 */