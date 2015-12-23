package com.google.android.gms.measurement.internal;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.zzaic;
import com.google.android.gms.internal.zzso.zzb;
import com.google.android.gms.internal.zzso.zzc;
import com.google.android.gms.internal.zzso.zze;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class zzae
  extends zzv
{
  zzae(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  static Object zza(int paramInt, Object paramObject, boolean paramBoolean)
  {
    if (paramObject == null) {
      paramObject = null;
    }
    do
    {
      do
      {
        return paramObject;
      } while (((paramObject instanceof Long)) || ((paramObject instanceof Float)));
      if ((paramObject instanceof Integer)) {
        return Long.valueOf(((Integer)paramObject).intValue());
      }
      if ((paramObject instanceof Byte)) {
        return Long.valueOf(((Byte)paramObject).byteValue());
      }
      if ((paramObject instanceof Short)) {
        return Long.valueOf(((Short)paramObject).shortValue());
      }
      if ((paramObject instanceof Boolean))
      {
        if (((Boolean)paramObject).booleanValue()) {}
        for (long l = 1L;; l = 0L) {
          return Long.valueOf(l);
        }
      }
      if ((paramObject instanceof Double)) {
        return Float.valueOf((float)((Double)paramObject).doubleValue());
      }
      if ((!(paramObject instanceof String)) && (!(paramObject instanceof Character)) && (!(paramObject instanceof CharSequence))) {
        break;
      }
      paramObject = String.valueOf(paramObject);
    } while (paramObject.length() <= paramInt);
    if (paramBoolean) {
      return paramObject.substring(0, paramInt);
    }
    return null;
    return null;
  }
  
  private void zza(String paramString1, String paramString2, int paramInt, Object paramObject)
  {
    if (paramObject == null) {
      super.zzBh().zzbnb.zzm(paramString1 + " value can't be null. Ignoring " + paramString1, paramString2);
    }
    String str;
    do
    {
      do
      {
        return;
      } while (((paramObject instanceof Long)) || ((paramObject instanceof Float)) || ((paramObject instanceof Integer)) || ((paramObject instanceof Byte)) || ((paramObject instanceof Short)) || ((paramObject instanceof Boolean)) || ((paramObject instanceof Double)) || ((!(paramObject instanceof String)) && (!(paramObject instanceof Character)) && (!(paramObject instanceof CharSequence))));
      str = String.valueOf(paramObject);
    } while (str.length() <= paramInt);
    super.zzBh().zzbnb.zze("Ignoring " + paramString1 + ". Value is too long. name, value length", paramString2, Integer.valueOf(str.length()));
  }
  
  public static boolean zza(Context paramContext, Class<? extends Service> paramClass)
  {
    try
    {
      ServiceInfo localServiceInfo = paramContext.getPackageManager().getServiceInfo(new ComponentName(paramContext, paramClass), 4);
      if (localServiceInfo != null)
      {
        boolean bool = localServiceInfo.enabled;
        if (bool) {
          return true;
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
  
  public static boolean zza$6aa51a6e(Context paramContext, Class<? extends BroadcastReceiver> paramClass)
  {
    try
    {
      ActivityInfo localActivityInfo = paramContext.getPackageManager().getReceiverInfo(new ComponentName(paramContext, paramClass), 2);
      if (localActivityInfo != null)
      {
        boolean bool = localActivityInfo.enabled;
        if (bool) {
          return true;
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
  
  public static void zzeH(String paramString)
  {
    zzc.zzBx();
    if (paramString == null) {
      throw new IllegalArgumentException("user attribute" + " name is required and can't be null");
    }
    if (paramString.length() == 0) {
      throw new IllegalArgumentException("user attribute" + " name is required and can't be empty");
    }
    char c1 = paramString.charAt(0);
    if ((!Character.isLetter(c1)) && (c1 != '_')) {
      throw new IllegalArgumentException("user attribute" + " name must start with a letter or _");
    }
    for (int i = 1; i < paramString.length(); i++)
    {
      char c2 = paramString.charAt(i);
      if ((c2 != '_') && (!Character.isLetterOrDigit(c2))) {
        throw new IllegalArgumentException("user attribute" + " name must consist of letters, digits or _ (underscores)");
      }
    }
    if (paramString.length() > 24) {
      throw new IllegalArgumentException("user attribute" + " name is too long. The maximum supported length is 24");
    }
  }
  
  private static int zzeJ(String paramString)
  {
    if ("_ldl".equals(paramString)) {
      return zzc.zzBB();
    }
    return zzc.zzBA();
  }
  
  public final byte[] zzA(byte[] paramArrayOfByte)
    throws IOException
  {
    ByteArrayInputStream localByteArrayInputStream;
    ByteArrayOutputStream localByteArrayOutputStream;
    try
    {
      localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
      GZIPInputStream localGZIPInputStream = new GZIPInputStream(localByteArrayInputStream);
      localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte1 = new byte[1024];
      for (;;)
      {
        int i = localGZIPInputStream.read(arrayOfByte1);
        if (i <= 0) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
      localGZIPInputStream.close();
    }
    catch (IOException localIOException)
    {
      super.zzBh().zzbmW.zzm("Failed to ungzip content", localIOException);
      throw localIOException;
    }
    localByteArrayInputStream.close();
    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    return arrayOfByte2;
  }
  
  public final void zza(Bundle paramBundle, String paramString, Object paramObject)
  {
    if ((paramObject instanceof Long)) {
      paramBundle.putLong(paramString, ((Long)paramObject).longValue());
    }
    do
    {
      return;
      if ((paramObject instanceof Float))
      {
        paramBundle.putFloat(paramString, ((Float)paramObject).floatValue());
        return;
      }
      if ((paramObject instanceof String))
      {
        paramBundle.putString(paramString, String.valueOf(paramObject));
        return;
      }
    } while (paramString == null);
    super.zzBh().zzbnb.zze("Not putting event parameter. Invalid value type. name, type", paramString, paramObject.getClass().getSimpleName());
  }
  
  public final void zza(zzso.zzb paramzzb, Object paramObject)
  {
    zzx.zzC(paramObject);
    paramzzb.stringValue = null;
    paramzzb.zzbpa = null;
    paramzzb.zzboU = null;
    if ((paramObject instanceof String))
    {
      paramzzb.stringValue = ((String)paramObject);
      return;
    }
    if ((paramObject instanceof Long))
    {
      paramzzb.zzbpa = ((Long)paramObject);
      return;
    }
    if ((paramObject instanceof Float))
    {
      paramzzb.zzboU = ((Float)paramObject);
      return;
    }
    super.zzBh().zzbmW.zzm("Ignoring invalid (type) event param value", paramObject);
  }
  
  public final void zza(zzso.zze paramzze, Object paramObject)
  {
    zzx.zzC(paramObject);
    paramzze.stringValue = null;
    paramzze.zzbpa = null;
    paramzze.zzboU = null;
    if ((paramObject instanceof String))
    {
      paramzze.stringValue = ((String)paramObject);
      return;
    }
    if ((paramObject instanceof Long))
    {
      paramzze.zzbpa = ((Long)paramObject);
      return;
    }
    if ((paramObject instanceof Float))
    {
      paramzze.zzboU = ((Float)paramObject);
      return;
    }
    super.zzBh().zzbmW.zzm("Ignoring invalid (type) user attribute value", paramObject);
  }
  
  public final byte[] zza(zzso.zzc paramzzc)
  {
    try
    {
      byte[] arrayOfByte = new byte[paramzzc.getSerializedSize()];
      zzaic localzzaic = zzaic.zzb(arrayOfByte, 0, arrayOfByte.length);
      paramzzc.writeTo(localzzaic);
      localzzaic.zzOW();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      super.zzBh().zzbmW.zzm("Data loss. Failed to serialize batch", localIOException);
    }
    return null;
  }
  
  public final boolean zzaZ(String paramString)
  {
    super.checkOnWorkerThread();
    return super.getContext().checkCallingOrSelfPermission(paramString) == 0;
  }
  
  public final byte[] zzg(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
      localGZIPOutputStream.write(paramArrayOfByte);
      localGZIPOutputStream.close();
      localByteArrayOutputStream.close();
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      super.zzBh().zzbmW.zzm("Failed to gzip content", localIOException);
      throw localIOException;
    }
  }
  
  public final boolean zzh(long paramLong1, long paramLong2)
  {
    if ((paramLong1 == 0L) || (paramLong2 <= 0L)) {}
    while (Math.abs(super.getClock().currentTimeMillis() - paramLong1) > paramLong2) {
      return true;
    }
    return false;
  }
  
  public final void zzo(String paramString, Object paramObject)
  {
    if ("_ldl".equals(paramString))
    {
      zza("user attribute referrer", paramString, zzeJ(paramString), paramObject);
      return;
    }
    zza("user attribute", paramString, zzeJ(paramString), paramObject);
  }
  
  public final Object zzp(String paramString, Object paramObject)
  {
    if ("_ldl".equals(paramString)) {
      return zza(zzeJ(paramString), paramObject, true);
    }
    return zza(zzeJ(paramString), paramObject, false);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzae
 * JD-Core Version:    0.7.0.1
 */