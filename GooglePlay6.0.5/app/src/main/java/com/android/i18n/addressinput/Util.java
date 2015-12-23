package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util
{
  private static final Map<String, String> nonLatinLocalLanguageCountries;
  
  static
  {
    HashMap localHashMap = new HashMap();
    nonLatinLocalLanguageCountries = localHashMap;
    localHashMap.put("AM", "hy");
    nonLatinLocalLanguageCountries.put("CN", "zh");
    nonLatinLocalLanguageCountries.put("HK", "zh");
    nonLatinLocalLanguageCountries.put("JP", "ja");
    nonLatinLocalLanguageCountries.put("KP", "ko");
    nonLatinLocalLanguageCountries.put("KR", "ko");
    nonLatinLocalLanguageCountries.put("MO", "zh");
    nonLatinLocalLanguageCountries.put("TH", "th");
    nonLatinLocalLanguageCountries.put("TW", "zh");
    nonLatinLocalLanguageCountries.put("VN", "vi");
  }
  
  static Map<String, String> buildNameToKeyMap(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
  {
    Object localObject;
    if (paramArrayOfString1 == null) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = new HashMap();
      int i = paramArrayOfString1.length;
      int j = paramArrayOfString1.length;
      for (int k = 0; k < j; k++)
      {
        String str = paramArrayOfString1[k];
        ((Map)localObject).put(str.toLowerCase(), str);
      }
      if (paramArrayOfString2 != null)
      {
        if (paramArrayOfString2.length > i) {
          throw new IllegalStateException("names length (" + paramArrayOfString2.length + ") is greater than keys length (" + paramArrayOfString1.length + ")");
        }
        for (int n = 0; n < i; n++) {
          if ((n < paramArrayOfString2.length) && (paramArrayOfString2[n].length() > 0)) {
            ((Map)localObject).put(paramArrayOfString2[n].toLowerCase(), paramArrayOfString1[n]);
          }
        }
      }
      if (paramArrayOfString3 != null)
      {
        if (paramArrayOfString3.length > i) {
          throw new IllegalStateException("lnames length (" + paramArrayOfString3.length + ") is greater than keys length (" + paramArrayOfString1.length + ")");
        }
        for (int m = 0; m < i; m++) {
          if ((m < paramArrayOfString3.length) && (paramArrayOfString3[m].length() > 0)) {
            ((Map)localObject).put(paramArrayOfString3[m].toLowerCase(), paramArrayOfString1[m]);
          }
        }
      }
    }
  }
  
  static void checkNotNull(Object paramObject)
    throws NullPointerException
  {
    checkNotNull(paramObject, "This object should not be null.");
  }
  
  static void checkNotNull(Object paramObject, String paramString)
    throws NullPointerException
  {
    if (paramObject == null) {
      throw new NullPointerException(paramString);
    }
  }
  
  static String getLanguageSubtag(String paramString)
  {
    Matcher localMatcher = Pattern.compile("(\\w{2,3})(?:[-_]\\w{4})?(?:[-_]\\w{2})?").matcher(paramString);
    if (localMatcher.matches()) {
      return localMatcher.group(1).toLowerCase();
    }
    return "und";
  }
  
  public static String getWidgetCompatibleLanguageCode(Locale paramLocale, String paramString)
  {
    String str1 = paramString.toUpperCase();
    if (nonLatinLocalLanguageCountries.containsKey(str1))
    {
      String str2 = paramLocale.getLanguage();
      if (!str2.equals(nonLatinLocalLanguageCountries.get(str1)))
      {
        StringBuilder localStringBuilder = new StringBuilder(str2);
        localStringBuilder.append("_latn");
        if (paramLocale.getCountry().length() > 0)
        {
          localStringBuilder.append("_");
          localStringBuilder.append(paramLocale.getCountry());
        }
        return localStringBuilder.toString();
      }
    }
    return paramLocale.toString();
  }
  
  static boolean isExplicitLatinScript(String paramString)
  {
    String str = paramString.toUpperCase();
    Matcher localMatcher = Pattern.compile("\\w{2,3}[-_](\\w{4})").matcher(str);
    return (localMatcher.lookingAt()) && (localMatcher.group(1).equals("LATN"));
  }
  
  static String joinAndSkipNulls(String paramString, String... paramVarArgs)
  {
    StringBuilder localStringBuilder = null;
    int i = 0;
    if (i < 2)
    {
      String str1 = paramVarArgs[i];
      String str2;
      if (str1 != null)
      {
        str2 = str1.trim();
        if (str2.length() > 0)
        {
          if (localStringBuilder != null) {
            break label54;
          }
          localStringBuilder = new StringBuilder(str2);
        }
      }
      for (;;)
      {
        i++;
        break;
        label54:
        localStringBuilder.append(paramString).append(str2);
      }
    }
    if (localStringBuilder == null) {
      return null;
    }
    return localStringBuilder.toString();
  }
  
  static String trimToNull(String paramString)
  {
    String str;
    if (paramString == null) {
      str = null;
    }
    do
    {
      return str;
      str = paramString.trim();
    } while (str.length() != 0);
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.Util
 * JD-Core Version:    0.7.0.1
 */