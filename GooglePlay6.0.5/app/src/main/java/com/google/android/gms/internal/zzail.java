package com.google.android.gms.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzail
{
  private static void zza(String paramString, Object paramObject, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
    throws IllegalAccessException, InvocationTargetException
  {
    if (paramObject != null)
    {
      if (!(paramObject instanceof zzaik)) {
        break label410;
      }
      int i = paramStringBuffer1.length();
      if (paramString != null)
      {
        paramStringBuffer2.append(paramStringBuffer1).append(zzjx(paramString)).append(" <\n");
        paramStringBuffer1.append("  ");
      }
      Class localClass1 = paramObject.getClass();
      for (Field localField : localClass1.getFields())
      {
        int i1 = localField.getModifiers();
        String str6 = localField.getName();
        if ((!"cachedSize".equals(str6)) && ((i1 & 0x1) == 1) && ((i1 & 0x8) != 8) && (!str6.startsWith("_")) && (!str6.endsWith("_")))
        {
          Class localClass2 = localField.getType();
          Object localObject = localField.get(paramObject);
          if ((localClass2.isArray()) && (localClass2.getComponentType() != Byte.TYPE))
          {
            if (localObject == null) {}
            for (int i2 = 0;; i2 = Array.getLength(localObject)) {
              for (int i3 = 0; i3 < i2; i3++) {
                zza(str6, Array.get(localObject, i3), paramStringBuffer1, paramStringBuffer2);
              }
            }
          }
          zza(str6, localObject, paramStringBuffer1, paramStringBuffer2);
        }
      }
      Method[] arrayOfMethod = localClass1.getMethods();
      int m = arrayOfMethod.length;
      int n = 0;
      while (n < m)
      {
        String str4 = arrayOfMethod[n].getName();
        String str5;
        if (str4.startsWith("set")) {
          str5 = str4.substring(3);
        }
        for (;;)
        {
          try
          {
            Method localMethod1 = localClass1.getMethod("has" + str5, new Class[0]);
            if (!((Boolean)localMethod1.invoke(paramObject, new Object[0])).booleanValue()) {}
          }
          catch (NoSuchMethodException localNoSuchMethodException1)
          {
            Method localMethod2;
            String str1;
            String str2;
            String str3;
            continue;
          }
          try
          {
            localMethod2 = localClass1.getMethod("get" + str5, new Class[0]);
            zza(str5, localMethod2.invoke(paramObject, new Object[0]), paramStringBuffer1, paramStringBuffer2);
            n++;
          }
          catch (NoSuchMethodException localNoSuchMethodException2) {}
        }
      }
      if (paramString != null)
      {
        paramStringBuffer1.setLength(i);
        paramStringBuffer2.append(paramStringBuffer1).append(">\n");
      }
    }
    return;
    label410:
    str1 = zzjx(paramString);
    paramStringBuffer2.append(paramStringBuffer1).append(str1).append(": ");
    if ((paramObject instanceof String))
    {
      str2 = (String)paramObject;
      if ((!str2.startsWith("http")) && (str2.length() > 200)) {
        str2 = str2.substring(0, 200) + "[...]";
      }
      str3 = zzcM(str2);
      paramStringBuffer2.append("\"").append(str3).append("\"");
    }
    for (;;)
    {
      paramStringBuffer2.append("\n");
      return;
      if ((paramObject instanceof byte[])) {
        zza((byte[])paramObject, paramStringBuffer2);
      } else {
        paramStringBuffer2.append(paramObject);
      }
    }
  }
  
  private static void zza(byte[] paramArrayOfByte, StringBuffer paramStringBuffer)
  {
    if (paramArrayOfByte == null)
    {
      paramStringBuffer.append("\"\"");
      return;
    }
    paramStringBuffer.append('"');
    int i = 0;
    if (i < paramArrayOfByte.length)
    {
      int j = 0xFF & paramArrayOfByte[i];
      if ((j == 92) || (j == 34)) {
        paramStringBuffer.append('\\').append((char)j);
      }
      for (;;)
      {
        i++;
        break;
        if ((j >= 32) && (j < 127))
        {
          paramStringBuffer.append((char)j);
        }
        else
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(j);
          paramStringBuffer.append(String.format("\\%03o", arrayOfObject));
        }
      }
    }
    paramStringBuffer.append('"');
  }
  
  private static String zzcM(String paramString)
  {
    int i = paramString.length();
    StringBuilder localStringBuilder = new StringBuilder(i);
    int j = 0;
    if (j < i)
    {
      char c = paramString.charAt(j);
      if ((c >= ' ') && (c <= '~') && (c != '"') && (c != '\'')) {
        localStringBuilder.append(c);
      }
      for (;;)
      {
        j++;
        break;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(c);
        localStringBuilder.append(String.format("\\u%04x", arrayOfObject));
      }
    }
    return localStringBuilder.toString();
  }
  
  public static <T extends zzaik> String zzf(T paramT)
  {
    if (paramT == null) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      zza(null, paramT, new StringBuffer(), localStringBuffer);
      return localStringBuffer.toString();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      return "Error printing proto: " + localIllegalAccessException.getMessage();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return "Error printing proto: " + localInvocationTargetException.getMessage();
    }
  }
  
  private static String zzjx(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramString.length())
    {
      char c = paramString.charAt(i);
      if (i == 0) {
        localStringBuffer.append(Character.toLowerCase(c));
      }
      for (;;)
      {
        i++;
        break;
        if (Character.isUpperCase(c)) {
          localStringBuffer.append('_').append(Character.toLowerCase(c));
        } else {
          localStringBuffer.append(c);
        }
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzail
 * JD-Core Version:    0.7.0.1
 */