package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HeaderParser;
import java.util.concurrent.TimeUnit;

public final class CacheControl
{
  public static final CacheControl FORCE_CACHE;
  public static final CacheControl FORCE_NETWORK;
  public final boolean isPublic;
  public final int maxAgeSeconds;
  public final int maxStaleSeconds;
  public final int minFreshSeconds;
  public final boolean mustRevalidate;
  public final boolean noCache;
  public final boolean noStore;
  private final boolean noTransform;
  public final boolean onlyIfCached;
  public final int sMaxAgeSeconds;
  
  static
  {
    Builder localBuilder1 = new Builder();
    localBuilder1.noCache = true;
    FORCE_NETWORK = localBuilder1.build();
    Builder localBuilder2 = new Builder();
    localBuilder2.onlyIfCached = true;
    long l = TimeUnit.SECONDS.toSeconds(2147483647L);
    if (l > 2147483647L) {}
    for (int i = 2147483647;; i = (int)l)
    {
      localBuilder2.maxStaleSeconds = i;
      FORCE_CACHE = localBuilder2.build();
      return;
    }
  }
  
  private CacheControl(Builder paramBuilder)
  {
    this.noCache = paramBuilder.noCache;
    this.noStore = paramBuilder.noStore;
    this.maxAgeSeconds = paramBuilder.maxAgeSeconds;
    this.sMaxAgeSeconds = -1;
    this.isPublic = false;
    this.mustRevalidate = false;
    this.maxStaleSeconds = paramBuilder.maxStaleSeconds;
    this.minFreshSeconds = paramBuilder.minFreshSeconds;
    this.onlyIfCached = paramBuilder.onlyIfCached;
    this.noTransform = paramBuilder.noTransform;
  }
  
  private CacheControl(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, int paramInt4, boolean paramBoolean5, boolean paramBoolean6)
  {
    this.noCache = paramBoolean1;
    this.noStore = paramBoolean2;
    this.maxAgeSeconds = paramInt1;
    this.sMaxAgeSeconds = paramInt2;
    this.isPublic = paramBoolean3;
    this.mustRevalidate = paramBoolean4;
    this.maxStaleSeconds = paramInt3;
    this.minFreshSeconds = paramInt4;
    this.onlyIfCached = paramBoolean5;
    this.noTransform = paramBoolean6;
  }
  
  public static CacheControl parse(Headers paramHeaders)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    int i = -1;
    int j = -1;
    boolean bool3 = false;
    boolean bool4 = false;
    int k = -1;
    int m = -1;
    boolean bool5 = false;
    boolean bool6 = false;
    int n = 0;
    int i1 = paramHeaders.namesAndValues.length / 2;
    while (n < i1)
    {
      if ((paramHeaders.name(n).equalsIgnoreCase("Cache-Control")) || (paramHeaders.name(n).equalsIgnoreCase("Pragma")))
      {
        String str1 = paramHeaders.value(n);
        int i2 = 0;
        for (;;)
        {
          int i3 = str1.length();
          if (i2 >= i3) {
            break;
          }
          int i4 = i2;
          int i5 = HeaderParser.skipUntil(str1, i2, "=,;");
          String str2 = str1.substring(i4, i5).trim();
          String str3;
          if ((i5 == str1.length()) || (str1.charAt(i5) == ',') || (str1.charAt(i5) == ';'))
          {
            i2 = i5 + 1;
            str3 = null;
          }
          for (;;)
          {
            if (!"no-cache".equalsIgnoreCase(str2)) {
              break label284;
            }
            bool1 = true;
            break;
            int i6 = HeaderParser.skipWhitespace(str1, i5 + 1);
            if ((i6 < str1.length()) && (str1.charAt(i6) == '"'))
            {
              int i7 = i6 + 1;
              int i8 = HeaderParser.skipUntil(str1, i7, "\"");
              str3 = str1.substring(i7, i8);
              i2 = i8 + 1;
            }
            else
            {
              i2 = HeaderParser.skipUntil(str1, i6, ",;");
              str3 = str1.substring(i6, i2).trim();
            }
          }
          label284:
          if ("no-store".equalsIgnoreCase(str2)) {
            bool2 = true;
          } else if ("max-age".equalsIgnoreCase(str2)) {
            i = HeaderParser.parseSeconds(str3, -1);
          } else if ("s-maxage".equalsIgnoreCase(str2)) {
            j = HeaderParser.parseSeconds(str3, -1);
          } else if ("public".equalsIgnoreCase(str2)) {
            bool3 = true;
          } else if ("must-revalidate".equalsIgnoreCase(str2)) {
            bool4 = true;
          } else if ("max-stale".equalsIgnoreCase(str2)) {
            k = HeaderParser.parseSeconds(str3, 2147483647);
          } else if ("min-fresh".equalsIgnoreCase(str2)) {
            m = HeaderParser.parseSeconds(str3, -1);
          } else if ("only-if-cached".equalsIgnoreCase(str2)) {
            bool5 = true;
          } else if ("no-transform".equalsIgnoreCase(str2)) {
            bool6 = true;
          }
        }
      }
      n++;
    }
    return new CacheControl(bool1, bool2, i, j, bool3, bool4, k, m, bool5, bool6);
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.noCache) {
      localStringBuilder.append("no-cache, ");
    }
    if (this.noStore) {
      localStringBuilder.append("no-store, ");
    }
    if (this.maxAgeSeconds != -1) {
      localStringBuilder.append("max-age=").append(this.maxAgeSeconds).append(", ");
    }
    if (this.sMaxAgeSeconds != -1) {
      localStringBuilder.append("s-maxage=").append(this.sMaxAgeSeconds).append(", ");
    }
    if (this.isPublic) {
      localStringBuilder.append("public, ");
    }
    if (this.mustRevalidate) {
      localStringBuilder.append("must-revalidate, ");
    }
    if (this.maxStaleSeconds != -1) {
      localStringBuilder.append("max-stale=").append(this.maxStaleSeconds).append(", ");
    }
    if (this.minFreshSeconds != -1) {
      localStringBuilder.append("min-fresh=").append(this.minFreshSeconds).append(", ");
    }
    if (this.onlyIfCached) {
      localStringBuilder.append("only-if-cached, ");
    }
    if (this.noTransform) {
      localStringBuilder.append("no-transform, ");
    }
    if (localStringBuilder.length() == 0) {
      return "";
    }
    localStringBuilder.delete(-2 + localStringBuilder.length(), localStringBuilder.length());
    return localStringBuilder.toString();
  }
  
  public static final class Builder
  {
    int maxAgeSeconds = -1;
    int maxStaleSeconds = -1;
    int minFreshSeconds = -1;
    boolean noCache;
    boolean noStore;
    boolean noTransform;
    boolean onlyIfCached;
    
    public final CacheControl build()
    {
      return new CacheControl(this, (byte)0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.CacheControl
 * JD-Core Version:    0.7.0.1
 */