package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzx;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class zzp
{
  public static final Map<String, Integer> zzbxJ;
  public static Iterable<?> zzbxK = new zze();
  public static final Handler zzbxL = new Handler(Looper.getMainLooper());
  public static final String[] zzbxM = new String[0];
  public static final Pattern zzbxN = Pattern.compile("\\,");
  public static final Pattern zzbxO = Pattern.compile("[     ᠎             　\t\013\f\034\035\036\037\n\r]+");
  public static final Pattern zzbxP = Pattern.compile(Pattern.quote("\001"));
  public static final Pattern zzbxQ = Pattern.compile(Pattern.quote("\002"));
  public static final String zzbxR = "\001";
  public static final String zzbxS = "\002";
  public static final SecureRandom zzbxT = new SecureRandom();
  private static final ThreadLocal<StringBuilder> zzbxU = new ThreadLocal() {};
  private static final ThreadLocal<String[]> zzbxV = new ThreadLocal() {};
  private static final ThreadLocal<String[]> zzbxW = new ThreadLocal() {};
  private static final ThreadLocal<String[]> zzbxX = new ThreadLocal() {};
  private static final ThreadLocal<String[]> zzbxY = new ThreadLocal() {};
  private static final ThreadLocal<String[]> zzbxZ = new ThreadLocal() {};
  
  static
  {
    HashMap localHashMap = new HashMap();
    zzbxJ = localHashMap;
    localHashMap.put("circle", Integer.valueOf(-1));
    zzbxJ.put("extendedCircles", Integer.valueOf(4));
    zzbxJ.put("myCircles", Integer.valueOf(3));
    zzbxJ.put("domain", Integer.valueOf(2));
    zzbxJ.put("public", Integer.valueOf(1));
    zzbxJ.put(null, Integer.valueOf(-2));
  }
  
  public static String zzS(Bundle paramBundle)
  {
    return zza(paramBundle, "", new StringBuilder()).toString();
  }
  
  private static StringBuilder zza(Object paramObject, String paramString, StringBuilder paramStringBuilder)
  {
    if (paramObject == null)
    {
      paramStringBuilder.append("[null]\n");
      return paramStringBuilder;
    }
    String str1 = paramString + "  ";
    paramStringBuilder.append("(").append(paramObject.getClass().getSimpleName()).append(") ");
    if ((paramObject instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramObject;
      if (localBundle.isEmpty())
      {
        paramStringBuilder.append("{ }\n");
        return paramStringBuilder;
      }
      paramStringBuilder.append("{\n");
      Iterator localIterator = localBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        paramStringBuilder.append(str1).append(str2).append(" : ");
        zza(localBundle.get(str2), str1, paramStringBuilder);
      }
      paramStringBuilder.append(paramString).append("}\n");
      return paramStringBuilder;
    }
    if ((paramObject instanceof DataHolder))
    {
      DataHolder localDataHolder = (DataHolder)paramObject;
      paramStringBuilder.append(" [");
      if (localDataHolder.isClosed()) {
        paramStringBuilder.append("CLOSED");
      }
      for (;;)
      {
        paramStringBuilder.append("] ").append(paramObject).append("\n");
        return paramStringBuilder;
        paramStringBuilder.append(localDataHolder.zzarB);
      }
    }
    if ((paramObject instanceof ArrayList))
    {
      ArrayList localArrayList = (ArrayList)paramObject;
      if (localArrayList.isEmpty())
      {
        paramStringBuilder.append("[ ]\n");
        return paramStringBuilder;
      }
      paramStringBuilder.append("[\n");
      for (int k = 0; k < localArrayList.size(); k++)
      {
        paramStringBuilder.append(str1).append(k).append(" : ");
        zza(localArrayList.get(k), str1, paramStringBuilder);
      }
      paramStringBuilder.append(paramString).append("]\n");
      return paramStringBuilder;
    }
    if ((paramObject instanceof byte[]))
    {
      int j = ((byte[])paramObject).length;
      paramStringBuilder.append(" [").append(j).append("] ");
      byte[] arrayOfByte = new byte[Math.min(j, 56)];
      System.arraycopy(paramObject, 0, arrayOfByte, 0, arrayOfByte.length);
      paramStringBuilder.append(Base64.encodeToString(arrayOfByte, 0));
      return paramStringBuilder;
    }
    if ((paramObject instanceof char[]))
    {
      paramStringBuilder.append("\"").append(new String((char[])paramObject)).append("\"\n");
      return paramStringBuilder;
    }
    if (paramObject.getClass().isArray())
    {
      if (Array.getLength(paramObject) == 0)
      {
        paramStringBuilder.append("[ ]\n");
        return paramStringBuilder;
      }
      paramStringBuilder.append("[ ");
      paramStringBuilder.append(Array.get(paramObject, 0));
      for (int i = 1; i < Array.getLength(paramObject); i++) {
        paramStringBuilder.append(", ").append(Array.get(paramObject, i));
      }
      paramStringBuilder.append(" ]\n");
      return paramStringBuilder;
    }
    if ((paramObject instanceof String))
    {
      paramStringBuilder.append("\"").append(paramObject).append("\"\n");
      return paramStringBuilder;
    }
    paramStringBuilder.append(paramObject).append("\n");
    return paramStringBuilder;
  }
  
  public static void zzaf(String paramString1, String paramString2)
  {
    zzx.zzi(paramString1, paramString2);
    if ((paramString1.startsWith("g:")) || (paramString1.startsWith("e:"))) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzb(bool, paramString2 + ": Expecting qualified-id, not gaia-id");
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzp
 * JD-Core Version:    0.7.0.1
 */