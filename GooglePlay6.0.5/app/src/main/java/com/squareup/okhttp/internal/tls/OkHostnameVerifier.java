package com.squareup.okhttp.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class OkHostnameVerifier
  implements HostnameVerifier
{
  public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
  private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");
  
  public static List<String> allSubjectAltNames(X509Certificate paramX509Certificate)
  {
    List localList1 = getSubjectAltNames(paramX509Certificate, 7);
    List localList2 = getSubjectAltNames(paramX509Certificate, 2);
    ArrayList localArrayList = new ArrayList(localList1.size() + localList2.size());
    localArrayList.addAll(localList1);
    localArrayList.addAll(localList2);
    return localArrayList;
  }
  
  private static List<String> getSubjectAltNames(X509Certificate paramX509Certificate, int paramInt)
  {
    localObject = new ArrayList();
    try
    {
      Collection localCollection = paramX509Certificate.getSubjectAlternativeNames();
      if (localCollection == null) {
        return Collections.emptyList();
      }
      Iterator localIterator = localCollection.iterator();
      while (localIterator.hasNext())
      {
        List localList = (List)localIterator.next();
        if ((localList != null) && (localList.size() >= 2))
        {
          Integer localInteger = (Integer)localList.get(0);
          if ((localInteger != null) && (localInteger.intValue() == paramInt))
          {
            String str = (String)localList.get(1);
            if (str != null) {
              ((List)localObject).add(str);
            }
          }
        }
      }
      return localObject;
    }
    catch (CertificateParsingException localCertificateParsingException)
    {
      localObject = Collections.emptyList();
    }
  }
  
  private static boolean verifyHostName(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0) || (paramString1.startsWith(".")) || (paramString1.endsWith(".."))) {}
    int i;
    do
    {
      String str2;
      do
      {
        String str1;
        do
        {
          do
          {
            return false;
          } while ((paramString2 == null) || (paramString2.length() == 0) || (paramString2.startsWith(".")) || (paramString2.endsWith("..")));
          if (!paramString1.endsWith(".")) {
            paramString1 = paramString1 + '.';
          }
          if (!paramString2.endsWith(".")) {
            paramString2 = paramString2 + '.';
          }
          str1 = paramString2.toLowerCase(Locale.US);
          if (!str1.contains("*")) {
            return paramString1.equals(str1);
          }
        } while ((!str1.startsWith("*.")) || (str1.indexOf('*', 1) != -1) || (paramString1.length() < str1.length()) || ("*.".equals(str1)));
        str2 = str1.substring(1);
      } while (!paramString1.endsWith(str2));
      i = paramString1.length() - str2.length();
    } while ((i > 0) && (paramString1.lastIndexOf('.', i - 1) != -1));
    return true;
  }
  
  public final boolean verify(String paramString, SSLSession paramSSLSession)
  {
    try
    {
      X509Certificate localX509Certificate = (X509Certificate)paramSSLSession.getPeerCertificates()[0];
      int n;
      int j;
      int k;
      label107:
      DistinguishedNameParser localDistinguishedNameParser;
      String str2;
      String str3;
      if (VERIFY_AS_IP_ADDRESS.matcher(paramString).matches())
      {
        List localList2 = getSubjectAltNames(localX509Certificate, 7);
        int m = localList2.size();
        n = 0;
        if (n >= m) {
          break label755;
        }
        if (paramString.equalsIgnoreCase((String)localList2.get(n))) {
          return true;
        }
      }
      else
      {
        String str1 = paramString.toLowerCase(Locale.US);
        List localList1 = getSubjectAltNames(localX509Certificate, 2);
        int i = localList1.size();
        j = 0;
        k = 0;
        if (j < i)
        {
          if (!verifyHostName(str1, (String)localList1.get(j))) {
            break label757;
          }
          return true;
        }
        if (k == 0)
        {
          localDistinguishedNameParser = new DistinguishedNameParser(localX509Certificate.getSubjectX500Principal());
          localDistinguishedNameParser.pos = 0;
          localDistinguishedNameParser.beg = 0;
          localDistinguishedNameParser.end = 0;
          localDistinguishedNameParser.cur = 0;
          localDistinguishedNameParser.chars = localDistinguishedNameParser.dn.toCharArray();
          str2 = localDistinguishedNameParser.nextAT();
          if (str2 != null) {
            break label742;
          }
          str3 = null;
          label207:
          if (str3 != null) {
            return verifyHostName(str1, str3);
          }
        }
      }
      for (;;)
      {
        str3 = "";
        if (localDistinguishedNameParser.pos == localDistinguishedNameParser.length)
        {
          str3 = null;
          break label207;
        }
        switch (localDistinguishedNameParser.chars[localDistinguishedNameParser.pos])
        {
        default: 
          str3 = localDistinguishedNameParser.escapedAV();
        }
        while (!"cn".equalsIgnoreCase((String)localObject))
        {
          if (localDistinguishedNameParser.pos < localDistinguishedNameParser.length) {
            break label612;
          }
          str3 = null;
          break;
          localDistinguishedNameParser.pos = (1 + localDistinguishedNameParser.pos);
          localDistinguishedNameParser.beg = localDistinguishedNameParser.pos;
          localDistinguishedNameParser.end = localDistinguishedNameParser.beg;
          if (localDistinguishedNameParser.pos == localDistinguishedNameParser.length) {
            throw new IllegalStateException("Unexpected end of DN: " + localDistinguishedNameParser.dn);
          }
          if (localDistinguishedNameParser.chars[localDistinguishedNameParser.pos] == '"') {
            for (localDistinguishedNameParser.pos = (1 + localDistinguishedNameParser.pos); (localDistinguishedNameParser.pos < localDistinguishedNameParser.length) && (localDistinguishedNameParser.chars[localDistinguishedNameParser.pos] == ' '); localDistinguishedNameParser.pos = (1 + localDistinguishedNameParser.pos)) {}
          }
          if (localDistinguishedNameParser.chars[localDistinguishedNameParser.pos] == '\\') {
            localDistinguishedNameParser.chars[localDistinguishedNameParser.end] = localDistinguishedNameParser.getEscaped();
          }
          for (;;)
          {
            localDistinguishedNameParser.pos = (1 + localDistinguishedNameParser.pos);
            localDistinguishedNameParser.end = (1 + localDistinguishedNameParser.end);
            break;
            localDistinguishedNameParser.chars[localDistinguishedNameParser.end] = localDistinguishedNameParser.chars[localDistinguishedNameParser.pos];
          }
          str3 = new String(localDistinguishedNameParser.chars, localDistinguishedNameParser.beg, localDistinguishedNameParser.end - localDistinguishedNameParser.beg);
          continue;
          str3 = localDistinguishedNameParser.hexAV();
        }
        label612:
        if ((localDistinguishedNameParser.chars[localDistinguishedNameParser.pos] != ',') && (localDistinguishedNameParser.chars[localDistinguishedNameParser.pos] != ';') && (localDistinguishedNameParser.chars[localDistinguishedNameParser.pos] != '+')) {
          throw new IllegalStateException("Malformed DN: " + localDistinguishedNameParser.dn);
        }
        localDistinguishedNameParser.pos = (1 + localDistinguishedNameParser.pos);
        String str4 = localDistinguishedNameParser.nextAT();
        if (str4 == null)
        {
          throw new IllegalStateException("Malformed DN: " + localDistinguishedNameParser.dn);
          return false;
          label742:
          localObject = str2;
          continue;
          n++;
          break;
          label755:
          return false;
          label757:
          j++;
          k = 1;
          break label107;
        }
        Object localObject = str4;
      }
      return false;
    }
    catch (SSLException localSSLException) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.tls.OkHostnameVerifier
 * JD-Core Version:    0.7.0.1
 */