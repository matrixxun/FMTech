package com.google.android.wallet.common.address;

import android.text.TextUtils;
import java.util.Locale;

public final class RegionCode
{
  public static int safeToRegionCode(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return 0;
    }
    try
    {
      int i = toRegionCode(paramString);
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return 858;
  }
  
  public static String toCountryCode(int paramInt)
  {
    if ((paramInt == 0) || ((paramInt & 0xFFFFFC00) != 0)) {
      return "ZZ";
    }
    char c1 = (char)(-1 + (65 + ((paramInt & 0x3E0) >> 5)));
    char c2 = (char)(-1 + (65 + (paramInt & 0x1F)));
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Character.valueOf(c1);
    arrayOfObject[1] = Character.valueOf(c2);
    return String.format(localLocale, "%c%c", arrayOfObject);
  }
  
  public static int toRegionCode(String paramString)
  {
    if (paramString.length() != 2) {
      throw new IllegalArgumentException("CountryCode must have length of 2!");
    }
    if (paramString.equals("UK")) {
      paramString = "GB";
    }
    int i = Character.toUpperCase(paramString.charAt(0));
    int j = Character.toUpperCase(paramString.charAt(1));
    return 1 + (i - 65) << 5 | 1 + (j - 65);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.RegionCode
 * JD-Core Version:    0.7.0.1
 */