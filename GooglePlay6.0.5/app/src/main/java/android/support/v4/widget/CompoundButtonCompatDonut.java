package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

final class CompoundButtonCompatDonut
{
  private static Field sButtonDrawableField;
  private static boolean sButtonDrawableFieldFetched;
  
  static Drawable getButtonDrawable(CompoundButton paramCompoundButton)
  {
    if (!sButtonDrawableFieldFetched) {}
    try
    {
      Field localField = CompoundButton.class.getDeclaredField("mButtonDrawable");
      sButtonDrawableField = localField;
      localField.setAccessible(true);
      sButtonDrawableFieldFetched = true;
      if (sButtonDrawableField == null) {}
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;)
      {
        try
        {
          Drawable localDrawable = (Drawable)sButtonDrawableField.get(paramCompoundButton);
          return localDrawable;
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          Log.i("CompoundButtonCompatDonut", "Failed to get button drawable via reflection", localIllegalAccessException);
          sButtonDrawableField = null;
        }
        localNoSuchFieldException = localNoSuchFieldException;
        Log.i("CompoundButtonCompatDonut", "Failed to retrieve mButtonDrawable field", localNoSuchFieldException);
      }
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.CompoundButtonCompatDonut
 * JD-Core Version:    0.7.0.1
 */