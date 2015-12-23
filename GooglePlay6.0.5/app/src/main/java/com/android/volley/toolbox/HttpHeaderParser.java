package com.android.volley.toolbox;

import com.android.volley.Cache.Entry;
import com.android.volley.NetworkResponse;
import java.util.Date;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public final class HttpHeaderParser
{
  public static Cache.Entry parseCacheHeaders(NetworkResponse paramNetworkResponse)
  {
    long l1 = System.currentTimeMillis();
    Map localMap = paramNetworkResponse.headers;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    long l5 = 0L;
    long l6 = 0L;
    long l7 = 0L;
    long l8 = 0L;
    String str1 = (String)localMap.get("Date");
    if (str1 != null) {
      l2 = parseDateAsEpoch(str1);
    }
    String str2 = (String)localMap.get("Cache-Control");
    int i = 0;
    int j = 0;
    int k;
    String str6;
    if (str2 != null)
    {
      i = 1;
      String[] arrayOfString = str2.split(",");
      k = 0;
      if (k < arrayOfString.length)
      {
        str6 = arrayOfString[k].trim();
        if ((str6.equals("no-cache")) || (str6.equals("no-store"))) {
          return null;
        }
        if (!str6.startsWith("max-age=")) {}
      }
    }
    for (;;)
    {
      try
      {
        long l10 = Long.parseLong(str6.substring(8));
        l7 = l10;
        k++;
      }
      catch (Exception localException2)
      {
        String str3;
        String str4;
        String str5;
        Cache.Entry localEntry;
        continue;
      }
      break;
      if (str6.startsWith("stale-while-revalidate=")) {}
      try
      {
        long l9 = Long.parseLong(str6.substring(23));
        l8 = l9;
      }
      catch (Exception localException1) {}
      if ((str6.equals("must-revalidate")) || (str6.equals("proxy-revalidate")))
      {
        j = 1;
        continue;
        str3 = (String)localMap.get("Expires");
        if (str3 != null) {
          l4 = parseDateAsEpoch(str3);
        }
        str4 = (String)localMap.get("Last-Modified");
        if (str4 != null) {
          l3 = parseDateAsEpoch(str4);
        }
        str5 = (String)localMap.get("ETag");
        if (i != 0)
        {
          l5 = l1 + 1000L * l7;
          if (j != 0)
          {
            l6 = l5;
            localEntry = new Cache.Entry();
            localEntry.data = paramNetworkResponse.data;
            localEntry.etag = str5;
            localEntry.softTtl = l5;
            localEntry.ttl = l6;
            localEntry.serverDate = l2;
            localEntry.lastModified = l3;
            localEntry.responseHeaders = localMap;
            return localEntry;
          }
          l6 = l5 + 1000L * l8;
          continue;
        }
        if ((l2 > 0L) && (l4 >= l2))
        {
          l5 = l1 + (l4 - l2);
          l6 = l5;
        }
      }
    }
  }
  
  public static String parseCharset(Map<String, String> paramMap)
  {
    return parseCharset(paramMap, "ISO-8859-1");
  }
  
  public static String parseCharset(Map<String, String> paramMap, String paramString)
  {
    String str = (String)paramMap.get("Content-Type");
    String[] arrayOfString1;
    if (str != null) {
      arrayOfString1 = str.split(";");
    }
    for (int i = 1;; i++) {
      if (i < arrayOfString1.length)
      {
        String[] arrayOfString2 = arrayOfString1[i].trim().split("=");
        if ((arrayOfString2.length == 2) && (arrayOfString2[0].equals("charset"))) {
          paramString = arrayOfString2[1];
        }
      }
      else
      {
        return paramString;
      }
    }
  }
  
  private static long parseDateAsEpoch(String paramString)
  {
    try
    {
      long l = DateUtils.parseDate(paramString).getTime();
      return l;
    }
    catch (DateParseException localDateParseException) {}
    return 0L;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.HttpHeaderParser
 * JD-Core Version:    0.7.0.1
 */