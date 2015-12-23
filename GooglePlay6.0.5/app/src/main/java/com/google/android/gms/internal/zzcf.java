package com.google.android.gms.internal;

import android.text.TextUtils;

@zzhb
public abstract class zzcf
{
  @zzhb
  public static final zzcf zzxu = new zzcf()
  {
    public final String zze(String paramAnonymousString1, String paramAnonymousString2)
    {
      return paramAnonymousString2;
    }
  };
  @zzhb
  public static final zzcf zzxv = new zzcf()
  {
    public final String zze(String paramAnonymousString1, String paramAnonymousString2)
    {
      if (paramAnonymousString1 != null) {
        return paramAnonymousString1;
      }
      return paramAnonymousString2;
    }
  };
  @zzhb
  public static final zzcf zzxw = new zzcf()
  {
    private static String zzQ(String paramAnonymousString)
    {
      if (TextUtils.isEmpty(paramAnonymousString)) {}
      int i;
      int j;
      do
      {
        return paramAnonymousString;
        i = 0;
        j = paramAnonymousString.length();
        while ((i < paramAnonymousString.length()) && (paramAnonymousString.charAt(i) == ',')) {
          i++;
        }
        while ((j > 0) && (paramAnonymousString.charAt(j - 1) == ',')) {
          j--;
        }
      } while ((i == 0) && (j == paramAnonymousString.length()));
      return paramAnonymousString.substring(i, j);
    }
    
    public final String zze(String paramAnonymousString1, String paramAnonymousString2)
    {
      String str1 = zzQ(paramAnonymousString1);
      String str2 = zzQ(paramAnonymousString2);
      if (TextUtils.isEmpty(str1)) {
        return str2;
      }
      if (TextUtils.isEmpty(str2)) {
        return str1;
      }
      return str1 + "," + str2;
    }
  };
  
  public abstract String zze(String paramString1, String paramString2);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzcf
 * JD-Core Version:    0.7.0.1
 */