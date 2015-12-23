package android.support.v4.view;

import android.view.View;
import java.lang.reflect.Field;

final class ViewCompatBase
{
  private static Field sMinHeightField;
  private static boolean sMinHeightFieldFetched;
  private static Field sMinWidthField;
  private static boolean sMinWidthFieldFetched;
  
  static int getMinimumHeight(View paramView)
  {
    if (!sMinHeightFieldFetched) {}
    try
    {
      Field localField = View.class.getDeclaredField("mMinHeight");
      sMinHeightField = localField;
      localField.setAccessible(true);
      label26:
      sMinHeightFieldFetched = true;
      if (sMinHeightField != null) {
        try
        {
          int i = ((Integer)sMinHeightField.get(paramView)).intValue();
          return i;
        }
        catch (Exception localException) {}
      }
      return 0;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      break label26;
    }
  }
  
  static int getMinimumWidth(View paramView)
  {
    if (!sMinWidthFieldFetched) {}
    try
    {
      Field localField = View.class.getDeclaredField("mMinWidth");
      sMinWidthField = localField;
      localField.setAccessible(true);
      label26:
      sMinWidthFieldFetched = true;
      if (sMinWidthField != null) {
        try
        {
          int i = ((Integer)sMinWidthField.get(paramView)).intValue();
          return i;
        }
        catch (Exception localException) {}
      }
      return 0;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      break label26;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewCompatBase
 * JD-Core Version:    0.7.0.1
 */