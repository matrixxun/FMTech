package com.google.android.pseudonymous_http;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.message.BasicHeader;

public abstract interface PseudonymousCookieSource
{
  public abstract String getCookieName();
  
  public abstract String getCookieValue();
  
  public static final class Helper
  {
    private static final Pattern COOKIE_PAIR = Pattern.compile("(^|[\\s;,]+)([^()<>@,;:\\\"/\\[\\]\\?={}\\s]+)\\s*=\\s*(\"[^\"]*\"|[^,;\\s\"]+)");
    private static final int DOT = ".".codePointAt(0);
    private static final String[] PSEUDONYMOUS_ID_DOMAINS = { "google.com", "googleapis.com" };
    private static final Pattern TOKEN = Pattern.compile("[^()<>@,;:\\\"/\\[\\]\\?={}\\s]+");
    private static final Pattern VALUE = Pattern.compile("[^,;\\s\"]+");
    
    private static boolean cookieSourceApplies(HttpUriRequest paramHttpUriRequest, PseudonymousCookieSource paramPseudonymousCookieSource)
    {
      if (paramPseudonymousCookieSource != null)
      {
        label144:
        for (String str1 : PSEUDONYMOUS_ID_DOMAINS)
        {
          String str2 = paramHttpUriRequest.getURI().getHost();
          int k;
          if (str1.length() <= str2.length()) {
            if (str1.equalsIgnoreCase(str2)) {
              k = 1;
            }
          }
          while (k != 0)
          {
            return true;
            int m = -1 + str2.length();
            for (int n = -1 + str1.length(); n >= 0; n--)
            {
              if (Character.toLowerCase(str2.codePointAt(m)) != Character.toLowerCase(str1.codePointAt(n))) {
                break label144;
              }
              m--;
            }
            if (str2.codePointAt(m) == DOT) {
              k = 1;
            } else {
              k = 0;
            }
          }
        }
        return false;
      }
      return false;
    }
    
    private static String getCookieValue(String paramString1, String paramString2)
    {
      Matcher localMatcher = COOKIE_PAIR.matcher(paramString1);
      while (localMatcher.find()) {
        if (localMatcher.group(2).equals(paramString2))
        {
          String str1 = localMatcher.group();
          String str2 = str1.substring(1 + str1.indexOf("=")).trim();
          if (str2.startsWith("\"")) {
            str2 = str2.substring(1, -1 + str2.length());
          }
          return str2;
        }
      }
      return null;
    }
    
    private static String replaceCookie(String paramString1, String paramString2, String paramString3)
    {
      Matcher localMatcher = COOKIE_PAIR.matcher(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      while (localMatcher.find()) {
        if (localMatcher.group(2).equals(paramString2)) {
          localMatcher.appendReplacement(localStringBuffer, localMatcher.group(1) + paramString2 + "=" + wrapInQuotesIfNeeded(paramString3));
        } else {
          localMatcher.appendReplacement(localStringBuffer, localMatcher.group());
        }
      }
      localMatcher.appendTail(localStringBuffer);
      return localStringBuffer.toString();
    }
    
    public static HttpUriRequest setRequestCookie(HttpUriRequest paramHttpUriRequest, PseudonymousCookieSource paramPseudonymousCookieSource)
      throws IOException
    {
      if (cookieSourceApplies(paramHttpUriRequest, paramPseudonymousCookieSource))
      {
        String str1 = paramPseudonymousCookieSource.getCookieName();
        String str2 = paramPseudonymousCookieSource.getCookieValue();
        int i = 0;
        Header[] arrayOfHeader = paramHttpUriRequest.getHeaders("Cookie");
        int j = arrayOfHeader.length;
        int k = 0;
        if (k < j)
        {
          Header localHeader = arrayOfHeader[k];
          String str3 = localHeader.getValue();
          Matcher localMatcher = COOKIE_PAIR.matcher(str3);
          do
          {
            if (!localMatcher.find()) {
              break;
            }
          } while (!localMatcher.group(2).equals(str1));
          for (int m = 1;; m = 0)
          {
            if (m != 0)
            {
              if ((i == 0) && (!(paramHttpUriRequest instanceof RequestWrapper))) {
                paramHttpUriRequest = wrapRequest(paramHttpUriRequest);
              }
              i = 1;
              paramHttpUriRequest.removeHeader(localHeader);
              paramHttpUriRequest.addHeader(new BasicHeader(localHeader.getName(), replaceCookie(localHeader.getValue(), str1, str2)));
            }
            k++;
            break;
          }
        }
        if (i == 0) {
          paramHttpUriRequest.addHeader(new BasicHeader("Cookie", str1 + "=" + wrapInQuotesIfNeeded(str2)));
        }
      }
      return paramHttpUriRequest;
    }
    
    public static HttpResponse updateFromResponseCookie(HttpUriRequest paramHttpUriRequest, HttpResponse paramHttpResponse, PseudonymousCookieSource paramPseudonymousCookieSource)
    {
      if (cookieSourceApplies(paramHttpUriRequest, paramPseudonymousCookieSource))
      {
        String str = paramPseudonymousCookieSource.getCookieName();
        Header[] arrayOfHeader1 = paramHttpResponse.getHeaders("Set-Cookie");
        int i = arrayOfHeader1.length;
        for (int j = 0; j < i; j++) {
          getCookieValue(arrayOfHeader1[j].getValue(), str);
        }
        Header[] arrayOfHeader2 = paramHttpResponse.getHeaders("Set-Cookie2");
        int k = arrayOfHeader2.length;
        for (int m = 0; m < k; m++) {
          getCookieValue(arrayOfHeader2[m].getValue(), str);
        }
      }
      return paramHttpResponse;
    }
    
    private static String wrapInQuotesIfNeeded(String paramString)
    {
      if (paramString == null) {
        paramString = "\"\"";
      }
      while (VALUE.matcher(paramString).matches()) {
        return paramString;
      }
      return "\"" + paramString + "\"";
    }
    
    /* Error */
    private static RequestWrapper wrapRequest(HttpUriRequest paramHttpUriRequest)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: instanceof 217
      //   4: ifeq +21 -> 25
      //   7: new 219	org/apache/http/impl/client/EntityEnclosingRequestWrapper
      //   10: dup
      //   11: aload_0
      //   12: checkcast 217	org/apache/http/HttpEntityEnclosingRequest
      //   15: invokespecial 222	org/apache/http/impl/client/EntityEnclosingRequestWrapper:<init>	(Lorg/apache/http/HttpEntityEnclosingRequest;)V
      //   18: astore_2
      //   19: aload_2
      //   20: invokevirtual 225	org/apache/http/impl/client/RequestWrapper:resetHeaders	()V
      //   23: aload_2
      //   24: areturn
      //   25: new 173	org/apache/http/impl/client/RequestWrapper
      //   28: dup
      //   29: aload_0
      //   30: invokespecial 228	org/apache/http/impl/client/RequestWrapper:<init>	(Lorg/apache/http/HttpRequest;)V
      //   33: astore_2
      //   34: goto -15 -> 19
      //   37: astore_1
      //   38: new 230	org/apache/http/client/ClientProtocolException
      //   41: dup
      //   42: aload_1
      //   43: invokespecial 233	org/apache/http/client/ClientProtocolException:<init>	(Ljava/lang/Throwable;)V
      //   46: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	47	0	paramHttpUriRequest	HttpUriRequest
      //   37	6	1	localProtocolException	org.apache.http.ProtocolException
      //   18	16	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   0	19	37	org/apache/http/ProtocolException
      //   19	23	37	org/apache/http/ProtocolException
      //   25	34	37	org/apache/http/ProtocolException
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.pseudonymous_http.PseudonymousCookieSource
 * JD-Core Version:    0.7.0.1
 */