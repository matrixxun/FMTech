package android.support.v4.text;

import android.os.Build.VERSION;
import java.util.Locale;

public final class ICUCompat
{
  private static final ICUCompatImpl IMPL = new ICUCompatImplBase();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 21)
    {
      IMPL = new ICUCompatImplLollipop();
      return;
    }
    if (i >= 14)
    {
      IMPL = new ICUCompatImplIcs();
      return;
    }
  }
  
  public static String maximizeAndGetScript(Locale paramLocale)
  {
    return IMPL.maximizeAndGetScript(paramLocale);
  }
  
  static abstract interface ICUCompatImpl
  {
    public abstract String maximizeAndGetScript(Locale paramLocale);
  }
  
  static final class ICUCompatImplBase
    implements ICUCompat.ICUCompatImpl
  {
    public final String maximizeAndGetScript(Locale paramLocale)
    {
      return null;
    }
  }
  
  static final class ICUCompatImplIcs
    implements ICUCompat.ICUCompatImpl
  {
    public final String maximizeAndGetScript(Locale paramLocale)
    {
      return ICUCompatIcs.maximizeAndGetScript(paramLocale);
    }
  }
  
  static final class ICUCompatImplLollipop
    implements ICUCompat.ICUCompatImpl
  {
    public final String maximizeAndGetScript(Locale paramLocale)
    {
      return ICUCompatApi23.maximizeAndGetScript(paramLocale);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.text.ICUCompat
 * JD-Core Version:    0.7.0.1
 */