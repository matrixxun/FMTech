package com.squareup.okhttp;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaType
{
  private static final Pattern PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
  private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
  private final String charset;
  private final String mediaType;
  public final String subtype;
  public final String type;
  
  private MediaType(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.mediaType = paramString1;
    this.type = paramString2;
    this.subtype = paramString3;
    this.charset = paramString4;
  }
  
  public static MediaType parse(String paramString)
  {
    Matcher localMatcher1 = TYPE_SUBTYPE.matcher(paramString);
    if (!localMatcher1.lookingAt()) {
      return null;
    }
    String str1 = localMatcher1.group(1).toLowerCase(Locale.US);
    String str2 = localMatcher1.group(2).toLowerCase(Locale.US);
    Object localObject = null;
    Matcher localMatcher2 = PARAMETER.matcher(paramString);
    for (int i = localMatcher1.end();; i = localMatcher2.end())
    {
      if (i >= paramString.length()) {
        break label192;
      }
      localMatcher2.region(i, paramString.length());
      if (!localMatcher2.lookingAt()) {
        break;
      }
      String str3 = localMatcher2.group(1);
      if ((str3 != null) && (str3.equalsIgnoreCase("charset")))
      {
        if (localMatcher2.group(2) != null) {}
        for (String str4 = localMatcher2.group(2); (localObject != null) && (!str4.equalsIgnoreCase((String)localObject)); str4 = localMatcher2.group(3)) {
          throw new IllegalArgumentException("Multiple different charsets: " + paramString);
        }
        localObject = str4;
      }
    }
    label192:
    return new MediaType(paramString, str1, str2, (String)localObject);
  }
  
  public final boolean equals(Object paramObject)
  {
    return ((paramObject instanceof MediaType)) && (((MediaType)paramObject).mediaType.equals(this.mediaType));
  }
  
  public final int hashCode()
  {
    return this.mediaType.hashCode();
  }
  
  public final String toString()
  {
    return this.mediaType;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.MediaType
 * JD-Core Version:    0.7.0.1
 */