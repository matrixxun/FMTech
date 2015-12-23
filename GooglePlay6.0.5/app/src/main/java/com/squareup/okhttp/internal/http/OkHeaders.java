package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Platform;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public final class OkHeaders
{
  private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator() {};
  static final String PREFIX;
  public static final String RECEIVED_MILLIS = PREFIX + "-Received-Millis";
  public static final String SELECTED_PROTOCOL = PREFIX + "-Selected-Protocol";
  public static final String SENT_MILLIS;
  
  static
  {
    Platform.get();
    PREFIX = Platform.getPrefix();
    SENT_MILLIS = PREFIX + "-Sent-Millis";
  }
  
  public static void addCookies(Request.Builder paramBuilder, Map<String, List<String>> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = (String)localEntry.getKey();
      if ((("Cookie".equalsIgnoreCase(str1)) || ("Cookie2".equalsIgnoreCase(str1))) && (!((List)localEntry.getValue()).isEmpty()))
      {
        List localList = (List)localEntry.getValue();
        if (localList.size() == 1) {}
        StringBuilder localStringBuilder;
        for (String str2 = (String)localList.get(0);; str2 = localStringBuilder.toString())
        {
          paramBuilder.addHeader(str1, str2);
          break;
          localStringBuilder = new StringBuilder();
          int i = localList.size();
          for (int j = 0; j < i; j++)
          {
            if (j > 0) {
              localStringBuilder.append("; ");
            }
            localStringBuilder.append((String)localList.get(j));
          }
        }
      }
    }
  }
  
  public static long contentLength(Headers paramHeaders)
  {
    return stringToLong(paramHeaders.get("Content-Length"));
  }
  
  public static long contentLength(Request paramRequest)
  {
    return contentLength(paramRequest.headers);
  }
  
  public static long contentLength(Response paramResponse)
  {
    return contentLength(paramResponse.headers);
  }
  
  static boolean isEndToEnd(String paramString)
  {
    return (!"Connection".equalsIgnoreCase(paramString)) && (!"Keep-Alive".equalsIgnoreCase(paramString)) && (!"Proxy-Authenticate".equalsIgnoreCase(paramString)) && (!"Proxy-Authorization".equalsIgnoreCase(paramString)) && (!"TE".equalsIgnoreCase(paramString)) && (!"Trailers".equalsIgnoreCase(paramString)) && (!"Transfer-Encoding".equalsIgnoreCase(paramString)) && (!"Upgrade".equalsIgnoreCase(paramString));
  }
  
  public static List<Challenge> parseChallenges(Headers paramHeaders, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = paramHeaders.namesAndValues.length / 2;
    while (i < j)
    {
      if (paramString.equalsIgnoreCase(paramHeaders.name(i)))
      {
        String str1 = paramHeaders.value(i);
        int k = 0;
        while (k < str1.length())
        {
          int m = k;
          int n = HeaderParser.skipUntil(str1, k, " ");
          String str2 = str1.substring(m, n).trim();
          int i1 = HeaderParser.skipWhitespace(str1, n);
          if (!str1.regionMatches(true, i1, "realm=\"", 0, 7)) {
            break;
          }
          int i2 = i1 + 7;
          int i3 = HeaderParser.skipUntil(str1, i2, "\"");
          String str3 = str1.substring(i2, i3);
          k = HeaderParser.skipWhitespace(str1, 1 + HeaderParser.skipUntil(str1, i3 + 1, ","));
          localArrayList.add(new Challenge(str2, str3));
        }
      }
      i++;
    }
    return localArrayList;
  }
  
  public static Request processAuthHeader(Authenticator paramAuthenticator, Response paramResponse, Proxy paramProxy)
    throws IOException
  {
    if (paramResponse.code == 407) {
      return paramAuthenticator.authenticateProxy(paramProxy, paramResponse);
    }
    return paramAuthenticator.authenticate(paramProxy, paramResponse);
  }
  
  private static long stringToLong(String paramString)
  {
    if (paramString == null) {
      return -1L;
    }
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return -1L;
  }
  
  public static Map<String, List<String>> toMultimap$29224fb9(Headers paramHeaders)
  {
    TreeMap localTreeMap = new TreeMap(FIELD_NAME_COMPARATOR);
    int i = 0;
    int j = paramHeaders.namesAndValues.length / 2;
    while (i < j)
    {
      String str1 = paramHeaders.name(i);
      String str2 = paramHeaders.value(i);
      ArrayList localArrayList = new ArrayList();
      List localList = (List)localTreeMap.get(str1);
      if (localList != null) {
        localArrayList.addAll(localList);
      }
      localArrayList.add(str2);
      localTreeMap.put(str1, Collections.unmodifiableList(localArrayList));
      i++;
    }
    return Collections.unmodifiableMap(localTreeMap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.OkHeaders
 * JD-Core Version:    0.7.0.1
 */