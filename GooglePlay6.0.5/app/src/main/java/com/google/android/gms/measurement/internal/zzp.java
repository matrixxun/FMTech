package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.internal.zzx;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class zzp
  extends zzw
{
  public zzp(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  static byte[] zzd(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    InputStream localInputStream = null;
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      localInputStream = paramHttpURLConnection.getInputStream();
      byte[] arrayOfByte1 = new byte[1024];
      for (;;)
      {
        int i = localInputStream.read(arrayOfByte1);
        if (i <= 0) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
      arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    }
    finally
    {
      if (localInputStream != null) {
        localInputStream.close();
      }
    }
    byte[] arrayOfByte2;
    if (localInputStream != null) {
      localInputStream.close();
    }
    return arrayOfByte2;
  }
  
  public final boolean isNetworkConnected()
  {
    zziL();
    ConnectivityManager localConnectivityManager = (ConnectivityManager)super.getContext().getSystemService("connectivity");
    try
    {
      NetworkInfo localNetworkInfo2 = localConnectivityManager.getActiveNetworkInfo();
      localNetworkInfo1 = localNetworkInfo2;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        NetworkInfo localNetworkInfo1 = null;
      }
    }
    return (localNetworkInfo1 != null) && (localNetworkInfo1.isConnected());
  }
  
  protected final void onInitialize() {}
  
  public final void zza(URL paramURL, byte[] paramArrayOfByte, zza paramzza)
  {
    super.checkOnWorkerThread();
    zziL();
    zzx.zzC(paramURL);
    zzx.zzC(paramArrayOfByte);
    zzx.zzC(paramzza);
    super.zzBY().zzh(new zzc(paramURL, paramArrayOfByte, paramzza));
  }
  
  static abstract interface zza
  {
    public abstract void zza(int paramInt, Throwable paramThrowable, byte[] paramArrayOfByte);
  }
  
  private static final class zzb
    implements Runnable
  {
    private final int zzAK;
    private final zzp.zza zzbnj;
    private final Throwable zzbnk;
    private final byte[] zzbnl;
    
    private zzb(zzp.zza paramzza, int paramInt, Throwable paramThrowable, byte[] paramArrayOfByte)
    {
      this.zzbnj = paramzza;
      this.zzAK = paramInt;
      this.zzbnk = paramThrowable;
      this.zzbnl = paramArrayOfByte;
    }
    
    public final void run()
    {
      this.zzbnj.zza(this.zzAK, this.zzbnk, this.zzbnl);
    }
  }
  
  private final class zzc
    implements Runnable
  {
    private final byte[] zzbnm;
    private final zzp.zza zzbnn;
    private final URL zzzr;
    
    public zzc(URL paramURL, byte[] paramArrayOfByte, zzp.zza paramzza)
    {
      this.zzzr = paramURL;
      this.zzbnm = paramArrayOfByte;
      this.zzbnn = paramzza;
    }
    
    /* Error */
    public final void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   4: invokevirtual 35	com/google/android/gms/measurement/internal/zzp:zzBU	()V
      //   7: aload_0
      //   8: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   11: invokevirtual 39	com/google/android/gms/measurement/internal/zzp:zzBX	()Lcom/google/android/gms/measurement/internal/zzae;
      //   14: aload_0
      //   15: getfield 25	com/google/android/gms/measurement/internal/zzp$zzc:zzbnm	[B
      //   18: invokevirtual 45	com/google/android/gms/measurement/internal/zzae:zzg	([B)[B
      //   21: astore 12
      //   23: aload_0
      //   24: getfield 23	com/google/android/gms/measurement/internal/zzp$zzc:zzzr	Ljava/net/URL;
      //   27: invokevirtual 51	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   30: astore 13
      //   32: aload 13
      //   34: instanceof 53
      //   37: ifne +72 -> 109
      //   40: new 30	java/io/IOException
      //   43: dup
      //   44: ldc 55
      //   46: invokespecial 58	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   49: athrow
      //   50: astore 7
      //   52: iconst_0
      //   53: istore 8
      //   55: aconst_null
      //   56: astore 9
      //   58: aconst_null
      //   59: astore 10
      //   61: aload 9
      //   63: ifnull +8 -> 71
      //   66: aload 9
      //   68: invokevirtual 63	java/io/OutputStream:close	()V
      //   71: aload 10
      //   73: ifnull +8 -> 81
      //   76: aload 10
      //   78: invokevirtual 66	java/net/HttpURLConnection:disconnect	()V
      //   81: aload_0
      //   82: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   85: invokevirtual 70	com/google/android/gms/measurement/internal/zzp:zzBY	()Lcom/google/android/gms/measurement/internal/zzs;
      //   88: new 72	com/google/android/gms/measurement/internal/zzp$zzb
      //   91: dup
      //   92: aload_0
      //   93: getfield 27	com/google/android/gms/measurement/internal/zzp$zzc:zzbnn	Lcom/google/android/gms/measurement/internal/zzp$zza;
      //   96: iload 8
      //   98: aload 7
      //   100: aconst_null
      //   101: iconst_0
      //   102: invokespecial 75	com/google/android/gms/measurement/internal/zzp$zzb:<init>	(Lcom/google/android/gms/measurement/internal/zzp$zza;ILjava/lang/Throwable;[BB)V
      //   105: invokevirtual 80	com/google/android/gms/measurement/internal/zzs:zzg	(Ljava/lang/Runnable;)V
      //   108: return
      //   109: aload 13
      //   111: checkcast 53	java/net/HttpURLConnection
      //   114: astore 5
      //   116: aload 5
      //   118: iconst_0
      //   119: invokevirtual 84	java/net/HttpURLConnection:setDefaultUseCaches	(Z)V
      //   122: invokestatic 90	com/google/android/gms/measurement/internal/zzc:zzBE	()J
      //   125: pop2
      //   126: aload 5
      //   128: ldc 91
      //   130: invokevirtual 95	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   133: invokestatic 98	com/google/android/gms/measurement/internal/zzc:zzBF	()J
      //   136: pop2
      //   137: aload 5
      //   139: ldc 99
      //   141: invokevirtual 102	java/net/HttpURLConnection:setReadTimeout	(I)V
      //   144: aload 5
      //   146: iconst_0
      //   147: invokevirtual 105	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
      //   150: aload 5
      //   152: iconst_1
      //   153: invokevirtual 108	java/net/HttpURLConnection:setDoInput	(Z)V
      //   156: aload 5
      //   158: iconst_1
      //   159: invokevirtual 111	java/net/HttpURLConnection:setDoOutput	(Z)V
      //   162: aload 5
      //   164: ldc 113
      //   166: ldc 115
      //   168: invokevirtual 119	java/net/HttpURLConnection:addRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   171: aload 5
      //   173: aload 12
      //   175: arraylength
      //   176: invokevirtual 122	java/net/HttpURLConnection:setFixedLengthStreamingMode	(I)V
      //   179: aload 5
      //   181: invokevirtual 125	java/net/HttpURLConnection:connect	()V
      //   184: aload 5
      //   186: invokevirtual 129	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
      //   189: astore 19
      //   191: aload 19
      //   193: astore 4
      //   195: aload 4
      //   197: aload 12
      //   199: invokevirtual 133	java/io/OutputStream:write	([B)V
      //   202: aload 4
      //   204: invokevirtual 63	java/io/OutputStream:close	()V
      //   207: aload 5
      //   209: invokevirtual 137	java/net/HttpURLConnection:getResponseCode	()I
      //   212: istore 22
      //   214: iload 22
      //   216: istore_3
      //   217: aload 5
      //   219: invokestatic 141	com/google/android/gms/measurement/internal/zzp:zzd	(Ljava/net/HttpURLConnection;)[B
      //   222: astore 24
      //   224: aload 5
      //   226: ifnull +8 -> 234
      //   229: aload 5
      //   231: invokevirtual 66	java/net/HttpURLConnection:disconnect	()V
      //   234: aload_0
      //   235: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   238: invokevirtual 70	com/google/android/gms/measurement/internal/zzp:zzBY	()Lcom/google/android/gms/measurement/internal/zzs;
      //   241: new 72	com/google/android/gms/measurement/internal/zzp$zzb
      //   244: dup
      //   245: aload_0
      //   246: getfield 27	com/google/android/gms/measurement/internal/zzp$zzc:zzbnn	Lcom/google/android/gms/measurement/internal/zzp$zza;
      //   249: iload_3
      //   250: aconst_null
      //   251: aload 24
      //   253: iconst_0
      //   254: invokespecial 75	com/google/android/gms/measurement/internal/zzp$zzb:<init>	(Lcom/google/android/gms/measurement/internal/zzp$zza;ILjava/lang/Throwable;[BB)V
      //   257: invokevirtual 80	com/google/android/gms/measurement/internal/zzs:zzg	(Ljava/lang/Runnable;)V
      //   260: return
      //   261: astore 11
      //   263: aload_0
      //   264: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   267: invokevirtual 145	com/google/android/gms/measurement/internal/zzp:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   270: getfield 151	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   273: ldc 153
      //   275: aload 11
      //   277: invokevirtual 159	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
      //   280: goto -209 -> 71
      //   283: astore_1
      //   284: aload_1
      //   285: astore_2
      //   286: iconst_0
      //   287: istore_3
      //   288: aconst_null
      //   289: astore 4
      //   291: aconst_null
      //   292: astore 5
      //   294: aload 4
      //   296: ifnull +8 -> 304
      //   299: aload 4
      //   301: invokevirtual 63	java/io/OutputStream:close	()V
      //   304: aload 5
      //   306: ifnull +8 -> 314
      //   309: aload 5
      //   311: invokevirtual 66	java/net/HttpURLConnection:disconnect	()V
      //   314: aload_0
      //   315: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   318: invokevirtual 70	com/google/android/gms/measurement/internal/zzp:zzBY	()Lcom/google/android/gms/measurement/internal/zzs;
      //   321: new 72	com/google/android/gms/measurement/internal/zzp$zzb
      //   324: dup
      //   325: aload_0
      //   326: getfield 27	com/google/android/gms/measurement/internal/zzp$zzc:zzbnn	Lcom/google/android/gms/measurement/internal/zzp$zza;
      //   329: iload_3
      //   330: aconst_null
      //   331: aconst_null
      //   332: iconst_0
      //   333: invokespecial 75	com/google/android/gms/measurement/internal/zzp$zzb:<init>	(Lcom/google/android/gms/measurement/internal/zzp$zza;ILjava/lang/Throwable;[BB)V
      //   336: invokevirtual 80	com/google/android/gms/measurement/internal/zzs:zzg	(Ljava/lang/Runnable;)V
      //   339: aload_2
      //   340: athrow
      //   341: astore 6
      //   343: aload_0
      //   344: getfield 18	com/google/android/gms/measurement/internal/zzp$zzc:zzbno	Lcom/google/android/gms/measurement/internal/zzp;
      //   347: invokevirtual 145	com/google/android/gms/measurement/internal/zzp:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   350: getfield 151	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   353: ldc 153
      //   355: aload 6
      //   357: invokevirtual 159	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
      //   360: goto -56 -> 304
      //   363: astore 18
      //   365: aload 18
      //   367: astore_2
      //   368: aconst_null
      //   369: astore 4
      //   371: iconst_0
      //   372: istore_3
      //   373: goto -79 -> 294
      //   376: astore 21
      //   378: aload 21
      //   380: astore_2
      //   381: iconst_0
      //   382: istore_3
      //   383: goto -89 -> 294
      //   386: astore 23
      //   388: aload 23
      //   390: astore_2
      //   391: aconst_null
      //   392: astore 4
      //   394: goto -100 -> 294
      //   397: astore 7
      //   399: aload 5
      //   401: astore 10
      //   403: aconst_null
      //   404: astore 9
      //   406: iconst_0
      //   407: istore 8
      //   409: goto -348 -> 61
      //   412: astore 7
      //   414: aload 4
      //   416: astore 20
      //   418: aload 5
      //   420: astore 10
      //   422: aload 20
      //   424: astore 9
      //   426: iconst_0
      //   427: istore 8
      //   429: goto -368 -> 61
      //   432: astore 7
      //   434: iload_3
      //   435: istore 8
      //   437: aload 5
      //   439: astore 10
      //   441: aconst_null
      //   442: astore 9
      //   444: goto -383 -> 61
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	447	0	this	zzc
      //   283	2	1	localObject1	Object
      //   285	106	2	localObject2	Object
      //   216	219	3	i	int
      //   193	222	4	localOutputStream1	java.io.OutputStream
      //   114	324	5	localHttpURLConnection	HttpURLConnection
      //   341	15	6	localIOException1	IOException
      //   50	49	7	localIOException2	IOException
      //   397	1	7	localIOException3	IOException
      //   412	1	7	localIOException4	IOException
      //   432	1	7	localIOException5	IOException
      //   53	383	8	j	int
      //   56	387	9	localObject3	Object
      //   59	381	10	localObject4	Object
      //   261	15	11	localIOException6	IOException
      //   21	177	12	arrayOfByte1	byte[]
      //   30	80	13	localURLConnection	java.net.URLConnection
      //   363	3	18	localObject5	Object
      //   189	3	19	localOutputStream2	java.io.OutputStream
      //   416	7	20	localOutputStream3	java.io.OutputStream
      //   376	3	21	localObject6	Object
      //   212	3	22	k	int
      //   386	3	23	localObject7	Object
      //   222	30	24	arrayOfByte2	byte[]
      // Exception table:
      //   from	to	target	type
      //   7	50	50	java/io/IOException
      //   109	156	50	java/io/IOException
      //   66	71	261	java/io/IOException
      //   7	50	283	finally
      //   109	156	283	finally
      //   299	304	341	java/io/IOException
      //   156	191	363	finally
      //   207	214	363	finally
      //   195	207	376	finally
      //   217	224	386	finally
      //   156	191	397	java/io/IOException
      //   207	214	397	java/io/IOException
      //   195	207	412	java/io/IOException
      //   217	224	432	java/io/IOException
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzp
 * JD-Core Version:    0.7.0.1
 */