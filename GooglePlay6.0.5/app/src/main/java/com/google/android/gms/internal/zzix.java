package com.google.android.gms.internal;

import android.content.Context;

@zzhb
public final class zzix
  extends zzim
{
  private final Context mContext;
  private final String zzE;
  private String zzKB = null;
  private final String zzsi;
  
  public zzix(Context paramContext, String paramString1, String paramString2)
  {
    this.mContext = paramContext;
    this.zzsi = paramString1;
    this.zzE = paramString2;
  }
  
  public zzix(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    this.mContext = paramContext;
    this.zzsi = paramString1;
    this.zzE = paramString2;
    this.zzKB = paramString3;
  }
  
  /* Error */
  public final void zzbB()
  {
    // Byte code:
    //   0: new 34	java/lang/StringBuilder
    //   3: dup
    //   4: ldc 36
    //   6: invokespecial 39	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   9: aload_0
    //   10: getfield 24	com/google/android/gms/internal/zzix:zzE	Ljava/lang/String;
    //   13: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: invokevirtual 47	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   19: invokestatic 52	com/google/android/gms/ads/internal/util/client/zzb:v	(Ljava/lang/String;)V
    //   22: new 54	java/net/URL
    //   25: dup
    //   26: aload_0
    //   27: getfield 24	com/google/android/gms/internal/zzix:zzE	Ljava/lang/String;
    //   30: invokespecial 55	java/net/URL:<init>	(Ljava/lang/String;)V
    //   33: invokevirtual 59	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   36: checkcast 61	java/net/HttpURLConnection
    //   39: astore 4
    //   41: aload_0
    //   42: getfield 18	com/google/android/gms/internal/zzix:zzKB	Ljava/lang/String;
    //   45: invokestatic 67	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   48: ifeq +81 -> 129
    //   51: invokestatic 73	com/google/android/gms/ads/internal/zzp:zzbI	()Lcom/google/android/gms/internal/zziq;
    //   54: aload_0
    //   55: getfield 20	com/google/android/gms/internal/zzix:mContext	Landroid/content/Context;
    //   58: aload_0
    //   59: getfield 22	com/google/android/gms/internal/zzix:zzsi	Ljava/lang/String;
    //   62: iconst_1
    //   63: aload 4
    //   65: invokevirtual 79	com/google/android/gms/internal/zziq:zza	(Landroid/content/Context;Ljava/lang/String;ZLjava/net/HttpURLConnection;)V
    //   68: aload 4
    //   70: invokevirtual 83	java/net/HttpURLConnection:getResponseCode	()I
    //   73: istore 7
    //   75: iload 7
    //   77: sipush 200
    //   80: if_icmplt +11 -> 91
    //   83: iload 7
    //   85: sipush 300
    //   88: if_icmplt +35 -> 123
    //   91: new 34	java/lang/StringBuilder
    //   94: dup
    //   95: ldc 85
    //   97: invokespecial 39	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   100: iload 7
    //   102: invokevirtual 88	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   105: ldc 90
    //   107: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload_0
    //   111: getfield 24	com/google/android/gms/internal/zzix:zzE	Ljava/lang/String;
    //   114: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: invokevirtual 47	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   120: invokestatic 93	com/google/android/gms/ads/internal/util/client/zzb:w	(Ljava/lang/String;)V
    //   123: aload 4
    //   125: invokevirtual 96	java/net/HttpURLConnection:disconnect	()V
    //   128: return
    //   129: invokestatic 73	com/google/android/gms/ads/internal/zzp:zzbI	()Lcom/google/android/gms/internal/zziq;
    //   132: pop
    //   133: aload 4
    //   135: aload_0
    //   136: getfield 18	com/google/android/gms/internal/zzix:zzKB	Ljava/lang/String;
    //   139: invokestatic 100	com/google/android/gms/internal/zziq:zza$2d8d796a$11657ff2	(Ljava/net/HttpURLConnection;Ljava/lang/String;)V
    //   142: goto -74 -> 68
    //   145: astore 5
    //   147: aload 4
    //   149: invokevirtual 96	java/net/HttpURLConnection:disconnect	()V
    //   152: aload 5
    //   154: athrow
    //   155: astore_3
    //   156: new 34	java/lang/StringBuilder
    //   159: dup
    //   160: ldc 102
    //   162: invokespecial 39	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   165: aload_0
    //   166: getfield 24	com/google/android/gms/internal/zzix:zzE	Ljava/lang/String;
    //   169: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: ldc 104
    //   174: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: aload_3
    //   178: invokevirtual 107	java/lang/IndexOutOfBoundsException:getMessage	()Ljava/lang/String;
    //   181: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: invokevirtual 47	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   187: invokestatic 93	com/google/android/gms/ads/internal/util/client/zzb:w	(Ljava/lang/String;)V
    //   190: return
    //   191: astore_2
    //   192: new 34	java/lang/StringBuilder
    //   195: dup
    //   196: ldc 109
    //   198: invokespecial 39	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   201: aload_0
    //   202: getfield 24	com/google/android/gms/internal/zzix:zzE	Ljava/lang/String;
    //   205: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: ldc 104
    //   210: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   213: aload_2
    //   214: invokevirtual 110	java/io/IOException:getMessage	()Ljava/lang/String;
    //   217: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: invokevirtual 47	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   223: invokestatic 93	com/google/android/gms/ads/internal/util/client/zzb:w	(Ljava/lang/String;)V
    //   226: return
    //   227: astore_1
    //   228: new 34	java/lang/StringBuilder
    //   231: dup
    //   232: ldc 109
    //   234: invokespecial 39	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   237: aload_0
    //   238: getfield 24	com/google/android/gms/internal/zzix:zzE	Ljava/lang/String;
    //   241: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: ldc 104
    //   246: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   249: aload_1
    //   250: invokevirtual 111	java/lang/RuntimeException:getMessage	()Ljava/lang/String;
    //   253: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: invokevirtual 47	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   259: invokestatic 93	com/google/android/gms/ads/internal/util/client/zzb:w	(Ljava/lang/String;)V
    //   262: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	263	0	this	zzix
    //   227	23	1	localRuntimeException	java.lang.RuntimeException
    //   191	23	2	localIOException	java.io.IOException
    //   155	23	3	localIndexOutOfBoundsException	java.lang.IndexOutOfBoundsException
    //   39	109	4	localHttpURLConnection	java.net.HttpURLConnection
    //   145	8	5	localObject	java.lang.Object
    //   73	28	7	i	int
    // Exception table:
    //   from	to	target	type
    //   41	68	145	finally
    //   68	75	145	finally
    //   91	123	145	finally
    //   129	142	145	finally
    //   0	41	155	java/lang/IndexOutOfBoundsException
    //   123	128	155	java/lang/IndexOutOfBoundsException
    //   147	155	155	java/lang/IndexOutOfBoundsException
    //   0	41	191	java/io/IOException
    //   123	128	191	java/io/IOException
    //   147	155	191	java/io/IOException
    //   0	41	227	java/lang/RuntimeException
    //   123	128	227	java/lang/RuntimeException
    //   147	155	227	java/lang/RuntimeException
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzix
 * JD-Core Version:    0.7.0.1
 */