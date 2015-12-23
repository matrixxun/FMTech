package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.IOException;

public final class zzal
  extends zzak
{
  private zzat zzns = new zzat(1000);
  AdvertisingIdClient.Info zznt;
  
  zzal(Context paramContext, zzaq paramzzaq, zzar paramzzar)
  {
    super(paramContext, paramzzaq, paramzzar);
  }
  
  public final String zza(String paramString1, String paramString2)
  {
    try
    {
      this.zzns.reset();
      byte[] arrayOfByte1;
      if (paramString1.length() < 3)
      {
        arrayOfByte1 = paramString1.getBytes("UTF-8");
        this.zzns.zza(1, arrayOfByte1);
        if (paramString2.length() >= 3) {
          break label92;
        }
      }
      label92:
      byte[] arrayOfByte2;
      for (Object localObject = paramString2.getBytes("UTF-8");; localObject = arrayOfByte2)
      {
        this.zzns.zza(2, (byte[])localObject);
        return this.zznp.zza$5a238448(this.zzns.zzad());
        arrayOfByte1 = this.zznp.zza(paramString1, true);
        break;
        arrayOfByte2 = this.zznp.zza(paramString2, true);
      }
      return Integer.toString(3);
    }
    catch (IOException localIOException) {}
  }
  
  /* Error */
  protected final void zzc(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 69	com/google/android/gms/internal/zzal:zznt	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   4: ifnull +47 -> 51
    //   7: bipush 24
    //   9: aload_0
    //   10: aload_0
    //   11: getfield 69	com/google/android/gms/internal/zzal:zznt	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   14: getfield 75	com/google/android/gms/ads/identifier/AdvertisingIdClient$Info:zzpp	Ljava/lang/String;
    //   17: invokevirtual 79	com/google/android/gms/internal/zzal:zzk	(Ljava/lang/String;)Ljava/lang/String;
    //   20: invokestatic 82	com/google/android/gms/internal/zzal:zza	(ILjava/lang/String;)V
    //   23: bipush 26
    //   25: ldc2_w 83
    //   28: invokestatic 87	com/google/android/gms/internal/zzal:zza	(IJ)V
    //   31: aload_0
    //   32: getfield 69	com/google/android/gms/internal/zzal:zznt	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   35: getfield 91	com/google/android/gms/ads/identifier/AdvertisingIdClient$Info:zzpq	Z
    //   38: ifeq +19 -> 57
    //   41: lconst_1
    //   42: lstore 4
    //   44: bipush 28
    //   46: lload 4
    //   48: invokestatic 87	com/google/android/gms/internal/zzal:zza	(IJ)V
    //   51: aload_0
    //   52: aconst_null
    //   53: putfield 69	com/google/android/gms/internal/zzal:zznt	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   56: return
    //   57: lconst_0
    //   58: lstore 4
    //   60: goto -16 -> 44
    //   63: astore_3
    //   64: aload_0
    //   65: aconst_null
    //   66: putfield 69	com/google/android/gms/internal/zzal:zznt	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   69: return
    //   70: astore_2
    //   71: aload_0
    //   72: aconst_null
    //   73: putfield 69	com/google/android/gms/internal/zzal:zznt	Lcom/google/android/gms/ads/identifier/AdvertisingIdClient$Info;
    //   76: aload_2
    //   77: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	78	0	this	zzal
    //   0	78	1	paramContext	Context
    //   70	7	2	localObject	Object
    //   63	1	3	localIOException	IOException
    //   42	17	4	l	long
    // Exception table:
    //   from	to	target	type
    //   0	41	63	java/io/IOException
    //   44	51	63	java/io/IOException
    //   0	41	70	finally
    //   44	51	70	finally
  }
  
  protected final void zzd(Context paramContext) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzal
 * JD-Core Version:    0.7.0.1
 */