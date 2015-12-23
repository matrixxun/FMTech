package com.google.android.gms.internal;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.cookie.DateUtils;

public final class zzt
  implements zzf
{
  protected static final boolean DEBUG = zzs.DEBUG;
  private static int zzan = 3000;
  private static int zzao = 4096;
  protected final zzy zzap;
  protected final zzu zzaq;
  
  public zzt(zzy paramzzy)
  {
    this(paramzzy, new zzu(zzao));
  }
  
  private zzt(zzy paramzzy, zzu paramzzu)
  {
    this.zzap = paramzzy;
    this.zzaq = paramzzu;
  }
  
  private static Map<String, String> zza(Header[] paramArrayOfHeader)
  {
    TreeMap localTreeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    for (int i = 0; i < paramArrayOfHeader.length; i++) {
      localTreeMap.put(paramArrayOfHeader[i].getName(), paramArrayOfHeader[i].getValue());
    }
    return localTreeMap;
  }
  
  private static void zza(String paramString, zzk<?> paramzzk, zzr paramzzr)
    throws zzr
  {
    zzo localzzo = paramzzk.zzN;
    int i = paramzzk.zzs();
    try
    {
      localzzo.zza(paramzzr);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramString;
      arrayOfObject2[1] = Integer.valueOf(i);
      paramzzk.zzc(String.format("%s-retry [timeout=%s]", arrayOfObject2));
      return;
    }
    catch (zzr localzzr)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = Integer.valueOf(i);
      paramzzk.zzc(String.format("%s-timeout-giveup [timeout=%s]", arrayOfObject1));
      throw localzzr;
    }
  }
  
  private byte[] zza(HttpEntity paramHttpEntity)
    throws IOException, zzp
  {
    zzaa localzzaa = new zzaa(this.zzaq, (int)paramHttpEntity.getContentLength());
    byte[] arrayOfByte1 = null;
    InputStream localInputStream;
    try
    {
      localInputStream = paramHttpEntity.getContent();
      arrayOfByte1 = null;
      if (localInputStream == null) {
        throw new zzp();
      }
    }
    finally {}
    try
    {
      paramHttpEntity.consumeContent();
      this.zzaq.zza(arrayOfByte1);
      localzzaa.close();
      throw localObject;
      arrayOfByte1 = this.zzaq.zzb(1024);
      for (;;)
      {
        int i = localInputStream.read(arrayOfByte1);
        if (i == -1) {
          break;
        }
        localzzaa.write(arrayOfByte1, 0, i);
      }
      byte[] arrayOfByte2 = localzzaa.toByteArray();
      try
      {
        paramHttpEntity.consumeContent();
        this.zzaq.zza(arrayOfByte1);
        localzzaa.close();
        return arrayOfByte2;
      }
      catch (IOException localIOException2)
      {
        for (;;)
        {
          zzs.zza("Error occured when calling consumingContent", new Object[0]);
        }
      }
    }
    catch (IOException localIOException1)
    {
      for (;;)
      {
        zzs.zza("Error occured when calling consumingContent", new Object[0]);
      }
    }
  }
  
  public final zzi zza(zzk<?> paramzzk)
    throws zzr
  {
    long l1 = SystemClock.elapsedRealtime();
    for (;;)
    {
      localObject1 = null;
      Map localMap = Collections.emptyMap();
      try
      {
        HashMap localHashMap = new HashMap();
        zzb.zza localzza1 = paramzzk.zzO;
        if (localzza1 != null)
        {
          if (localzza1.zzb != null) {
            localHashMap.put("If-None-Match", localzza1.zzb);
          }
          if (localzza1.zzd > 0L) {
            localHashMap.put("If-Modified-Since", DateUtils.formatDate(new Date(localzza1.zzd)));
          }
        }
        localHttpResponse = this.zzap.zza(paramzzk, localHashMap);
        try
        {
          localStatusLine = localHttpResponse.getStatusLine();
          j = localStatusLine.getStatusCode();
          localMap = zza(localHttpResponse.getAllHeaders());
          if (j == 304)
          {
            zzb.zza localzza2 = paramzzk.zzO;
            if (localzza2 == null) {
              return new zzi(304, null, localMap, true, SystemClock.elapsedRealtime() - l1);
            }
            localzza2.zzg.putAll(localMap);
            return new zzi(304, localzza2.data, localzza2.zzg, true, SystemClock.elapsedRealtime() - l1);
          }
          if (localHttpResponse.getEntity() != null)
          {
            byte[] arrayOfByte2 = zza(localHttpResponse.getEntity());
            arrayOfByte1 = arrayOfByte2;
          }
        }
        catch (IOException localIOException2)
        {
          for (;;)
          {
            StatusLine localStatusLine;
            long l2;
            Object[] arrayOfObject2;
            Object localObject2;
            label342:
            zzi localzzi2;
            int i;
            Object[] arrayOfObject1;
            zzi localzzi1;
            localObject1 = localHttpResponse;
            byte[] arrayOfByte1 = null;
          }
        }
        try
        {
          l2 = SystemClock.elapsedRealtime() - l1;
          if ((!DEBUG) && (l2 <= zzan)) {
            break label626;
          }
          arrayOfObject2 = new Object[5];
          arrayOfObject2[0] = paramzzk;
          arrayOfObject2[1] = Long.valueOf(l2);
          if (arrayOfByte1 == null) {
            break label377;
          }
          localObject2 = Integer.valueOf(arrayOfByte1.length);
          arrayOfObject2[2] = localObject2;
          arrayOfObject2[3] = Integer.valueOf(localStatusLine.getStatusCode());
          arrayOfObject2[4] = Integer.valueOf(paramzzk.zzN.zze());
          zzs.zzb("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", arrayOfObject2);
        }
        catch (IOException localIOException3)
        {
          localObject1 = localHttpResponse;
          break label465;
          if (j < 200) {
            break label342;
          }
          if (j <= 299) {
            break label385;
          }
          break label342;
        }
        throw new IOException();
      }
      catch (SocketTimeoutException localSocketTimeoutException)
      {
        for (;;)
        {
          zza("socket", paramzzk, new zzq());
          break;
          arrayOfByte1 = new byte[0];
          continue;
          localObject2 = "null";
        }
        localzzi2 = new zzi(j, arrayOfByte1, localMap, false, SystemClock.elapsedRealtime() - l1);
        return localzzi2;
      }
      catch (ConnectTimeoutException localConnectTimeoutException)
      {
        zza("connection", paramzzk, new zzq());
      }
      catch (MalformedURLException localMalformedURLException)
      {
        throw new RuntimeException("Bad URL " + paramzzk.zzE, localMalformedURLException);
      }
      catch (IOException localIOException1)
      {
        label377:
        arrayOfByte1 = null;
        label385:
        label465:
        if (localObject1 != null)
        {
          i = localObject1.getStatusLine().getStatusCode();
          arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Integer.valueOf(i);
          arrayOfObject1[1] = paramzzk.zzE;
          zzs.zzc("Unexpected response code %d for %s", arrayOfObject1);
          if (arrayOfByte1 == null) {
            break label596;
          }
          localzzi1 = new zzi(i, arrayOfByte1, localMap, false, SystemClock.elapsedRealtime() - l1);
          if ((i == 401) || (i == 403)) {
            zza("auth", paramzzk, new zza(localzzi1));
          }
        }
        else
        {
          throw new zzj(localIOException1);
        }
      }
    }
    throw new zzp(localzzi1);
    label596:
    throw new zzh((byte)0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzt
 * JD-Core Version:    0.7.0.1
 */