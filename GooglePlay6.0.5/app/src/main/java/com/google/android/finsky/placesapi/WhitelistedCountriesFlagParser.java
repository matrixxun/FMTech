package com.google.android.finsky.placesapi;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class WhitelistedCountriesFlagParser
{
  public Set<String> mEnabledCountries;
  
  public WhitelistedCountriesFlagParser(Context paramContext)
  {
    try
    {
      this.mEnabledCountries = parse((String)G.whitelistedPlacesApiCountries.get(), getApplicationVersion(paramContext));
      return;
    }
    catch (ParseException localParseException)
    {
      FinskyLog.e("Malformatted format for places api whitelisting flag: %s", new Object[] { localParseException });
    }
  }
  
  private static int getApplicationVersion(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException(localNameNotFoundException);
    }
  }
  
  private static Set<String> parse(String paramString, int paramInt)
    throws WhitelistedCountriesFlagParser.ParseException
  {
    if (TextUtils.isEmpty(paramString))
    {
      localObject = Collections.emptySet();
      return localObject;
    }
    Object localObject = new HashSet();
    String[] arrayOfString1 = paramString.split(";");
    int i = arrayOfString1.length;
    int j = 0;
    while (j < i)
    {
      String str = arrayOfString1[j];
      String[] arrayOfString2 = str.split(":");
      if (arrayOfString2.length != 2) {
        throw new ParseException("The following part must have exactly one ':': " + str);
      }
      try
      {
        int k = Integer.parseInt(arrayOfString2[0]);
        if (paramInt >= k) {
          ((Set)localObject).addAll(Arrays.asList(arrayOfString2[1].split(",")));
        }
        j++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new ParseException("Can't parse version: " + arrayOfString2[0]);
      }
    }
  }
  
  private static final class ParseException
    extends Exception
  {
    ParseException(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.WhitelistedCountriesFlagParser
 * JD-Core Version:    0.7.0.1
 */