package com.google.android.gms.internal;

import android.os.SystemClock;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzv
  implements zzb
{
  private final Map<String, zza> zzaw = new LinkedHashMap(16, 0.75F, true);
  private long zzax = 0L;
  private final File zzay;
  private final int zzaz;
  
  private zzv(File paramFile)
  {
    this.zzay = paramFile;
    this.zzaz = 5242880;
  }
  
  public zzv(File paramFile, byte paramByte)
  {
    this(paramFile);
  }
  
  private void remove(String paramString)
  {
    try
    {
      boolean bool = zzf(paramString).delete();
      zza localzza = (zza)this.zzaw.get(paramString);
      if (localzza != null)
      {
        this.zzax -= localzza.zzaA;
        this.zzaw.remove(paramString);
      }
      if (!bool)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = zze(paramString);
        zzs.zzb("Could not delete cache entry for key=%s, filename=%s", arrayOfObject);
      }
      return;
    }
    finally {}
  }
  
  private static int zza(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1) {
      throw new EOFException();
    }
    return i;
  }
  
  static void zza(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(0xFF & paramInt >> 0);
    paramOutputStream.write(0xFF & paramInt >> 8);
    paramOutputStream.write(0xFF & paramInt >> 16);
    paramOutputStream.write(0xFF & paramInt >> 24);
  }
  
  static void zza(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    paramOutputStream.write((byte)(int)(paramLong >>> 0));
    paramOutputStream.write((byte)(int)(paramLong >>> 8));
    paramOutputStream.write((byte)(int)(paramLong >>> 16));
    paramOutputStream.write((byte)(int)(paramLong >>> 24));
    paramOutputStream.write((byte)(int)(paramLong >>> 32));
    paramOutputStream.write((byte)(int)(paramLong >>> 40));
    paramOutputStream.write((byte)(int)(paramLong >>> 48));
    paramOutputStream.write((byte)(int)(paramLong >>> 56));
  }
  
  static void zza(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    zza(paramOutputStream, arrayOfByte.length);
    paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
  }
  
  private void zza(String paramString, zza paramzza)
  {
    if (!this.zzaw.containsKey(paramString)) {}
    zza localzza;
    for (this.zzax += paramzza.zzaA;; this.zzax += paramzza.zzaA - localzza.zzaA)
    {
      this.zzaw.put(paramString, paramzza);
      return;
      localzza = (zza)this.zzaw.get(paramString);
    }
  }
  
  private static byte[] zza(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (i < paramInt)
    {
      int j = paramInputStream.read(arrayOfByte, i, paramInt - i);
      if (j == -1) {
        break;
      }
      i += j;
    }
    if (i != paramInt) {
      throw new IOException("Expected " + paramInt + " bytes, read " + i + " bytes");
    }
    return arrayOfByte;
  }
  
  static int zzb(InputStream paramInputStream)
    throws IOException
  {
    return 0x0 | zza(paramInputStream) << 0 | zza(paramInputStream) << 8 | zza(paramInputStream) << 16 | zza(paramInputStream) << 24;
  }
  
  static long zzc(InputStream paramInputStream)
    throws IOException
  {
    return 0L | (0xFF & zza(paramInputStream)) << 0 | (0xFF & zza(paramInputStream)) << 8 | (0xFF & zza(paramInputStream)) << 16 | (0xFF & zza(paramInputStream)) << 24 | (0xFF & zza(paramInputStream)) << 32 | (0xFF & zza(paramInputStream)) << 40 | (0xFF & zza(paramInputStream)) << 48 | (0xFF & zza(paramInputStream)) << 56;
  }
  
  static String zzd(InputStream paramInputStream)
    throws IOException
  {
    return new String(zza(paramInputStream, (int)zzc(paramInputStream)), "UTF-8");
  }
  
  private static String zze(String paramString)
  {
    int i = paramString.length() / 2;
    String str = String.valueOf(paramString.substring(0, i).hashCode());
    return str + String.valueOf(paramString.substring(i).hashCode());
  }
  
  static Map<String, String> zze(InputStream paramInputStream)
    throws IOException
  {
    int i = zzb(paramInputStream);
    if (i == 0) {}
    for (Object localObject = Collections.emptyMap();; localObject = new HashMap(i)) {
      for (int j = 0; j < i; j++) {
        ((Map)localObject).put(zzd(paramInputStream).intern(), zzd(paramInputStream).intern());
      }
    }
    return localObject;
  }
  
  private File zzf(String paramString)
  {
    return new File(this.zzay, zze(paramString));
  }
  
  /* Error */
  public final zzb.zza zza(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 28	com/google/android/gms/internal/zzv:zzaw	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 56 2 0
    //   12: checkcast 58	com/google/android/gms/internal/zzv$zza
    //   15: astore_3
    //   16: aload_3
    //   17: ifnonnull +11 -> 28
    //   20: aconst_null
    //   21: astore 10
    //   23: aload_0
    //   24: monitorexit
    //   25: aload 10
    //   27: areturn
    //   28: aload_0
    //   29: aload_1
    //   30: invokespecial 44	com/google/android/gms/internal/zzv:zzf	(Ljava/lang/String;)Ljava/io/File;
    //   33: astore 4
    //   35: new 201	com/google/android/gms/internal/zzv$zzb
    //   38: dup
    //   39: new 203	java/io/FileInputStream
    //   42: dup
    //   43: aload 4
    //   45: invokespecial 204	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   48: iconst_0
    //   49: invokespecial 207	com/google/android/gms/internal/zzv$zzb:<init>	(Ljava/io/InputStream;B)V
    //   52: astore 5
    //   54: aload 5
    //   56: invokestatic 210	com/google/android/gms/internal/zzv$zza:zzf	(Ljava/io/InputStream;)Lcom/google/android/gms/internal/zzv$zza;
    //   59: pop
    //   60: aload 5
    //   62: aload 4
    //   64: invokevirtual 213	java/io/File:length	()J
    //   67: aload 5
    //   69: invokestatic 216	com/google/android/gms/internal/zzv$zzb:zza	(Lcom/google/android/gms/internal/zzv$zzb;)I
    //   72: i2l
    //   73: lsub
    //   74: l2i
    //   75: invokestatic 157	com/google/android/gms/internal/zzv:zza	(Ljava/io/InputStream;I)[B
    //   78: astore 14
    //   80: new 218	com/google/android/gms/internal/zzb$zza
    //   83: dup
    //   84: invokespecial 219	com/google/android/gms/internal/zzb$zza:<init>	()V
    //   87: astore 15
    //   89: aload 15
    //   91: aload 14
    //   93: putfield 223	com/google/android/gms/internal/zzb$zza:data	[B
    //   96: aload 15
    //   98: aload_3
    //   99: getfield 226	com/google/android/gms/internal/zzv$zza:zzb	Ljava/lang/String;
    //   102: putfield 227	com/google/android/gms/internal/zzb$zza:zzb	Ljava/lang/String;
    //   105: aload 15
    //   107: aload_3
    //   108: getfield 229	com/google/android/gms/internal/zzv$zza:zzc	J
    //   111: putfield 230	com/google/android/gms/internal/zzb$zza:zzc	J
    //   114: aload 15
    //   116: aload_3
    //   117: getfield 232	com/google/android/gms/internal/zzv$zza:zzd	J
    //   120: putfield 233	com/google/android/gms/internal/zzb$zza:zzd	J
    //   123: aload 15
    //   125: aload_3
    //   126: getfield 235	com/google/android/gms/internal/zzv$zza:zze	J
    //   129: putfield 236	com/google/android/gms/internal/zzb$zza:zze	J
    //   132: aload 15
    //   134: aload_3
    //   135: getfield 238	com/google/android/gms/internal/zzv$zza:zzf	J
    //   138: putfield 239	com/google/android/gms/internal/zzb$zza:zzf	J
    //   141: aload 15
    //   143: aload_3
    //   144: getfield 242	com/google/android/gms/internal/zzv$zza:zzg	Ljava/util/Map;
    //   147: putfield 243	com/google/android/gms/internal/zzb$zza:zzg	Ljava/util/Map;
    //   150: aload 5
    //   152: invokevirtual 246	com/google/android/gms/internal/zzv$zzb:close	()V
    //   155: aload 15
    //   157: astore 10
    //   159: goto -136 -> 23
    //   162: astore 16
    //   164: aconst_null
    //   165: astore 10
    //   167: goto -144 -> 23
    //   170: astore 6
    //   172: aconst_null
    //   173: astore 7
    //   175: iconst_2
    //   176: anewarray 4	java/lang/Object
    //   179: astore 11
    //   181: aload 11
    //   183: iconst_0
    //   184: aload 4
    //   186: invokevirtual 249	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   189: aastore
    //   190: aload 11
    //   192: iconst_1
    //   193: aload 6
    //   195: invokevirtual 250	java/io/IOException:toString	()Ljava/lang/String;
    //   198: aastore
    //   199: ldc 252
    //   201: aload 11
    //   203: invokestatic 75	com/google/android/gms/internal/zzs:zzb	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   206: aload_0
    //   207: aload_1
    //   208: invokespecial 254	com/google/android/gms/internal/zzv:remove	(Ljava/lang/String;)V
    //   211: aload 7
    //   213: ifnull +8 -> 221
    //   216: aload 7
    //   218: invokevirtual 246	com/google/android/gms/internal/zzv$zzb:close	()V
    //   221: aconst_null
    //   222: astore 10
    //   224: goto -201 -> 23
    //   227: astore 12
    //   229: aconst_null
    //   230: astore 10
    //   232: goto -209 -> 23
    //   235: astore 8
    //   237: aconst_null
    //   238: astore 5
    //   240: aload 5
    //   242: ifnull +8 -> 250
    //   245: aload 5
    //   247: invokevirtual 246	com/google/android/gms/internal/zzv$zzb:close	()V
    //   250: aload 8
    //   252: athrow
    //   253: astore_2
    //   254: aload_0
    //   255: monitorexit
    //   256: aload_2
    //   257: athrow
    //   258: astore 9
    //   260: aconst_null
    //   261: astore 10
    //   263: goto -240 -> 23
    //   266: astore 8
    //   268: goto -28 -> 240
    //   271: astore 8
    //   273: aload 7
    //   275: astore 5
    //   277: goto -37 -> 240
    //   280: astore 6
    //   282: aload 5
    //   284: astore 7
    //   286: goto -111 -> 175
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	289	0	this	zzv
    //   0	289	1	paramString	String
    //   253	4	2	localObject1	Object
    //   15	129	3	localzza	zza
    //   33	152	4	localFile	File
    //   52	231	5	localObject2	Object
    //   170	24	6	localIOException1	IOException
    //   280	1	6	localIOException2	IOException
    //   173	112	7	localObject3	Object
    //   235	16	8	localObject4	Object
    //   266	1	8	localObject5	Object
    //   271	1	8	localObject6	Object
    //   258	1	9	localIOException3	IOException
    //   21	241	10	localObject7	Object
    //   179	23	11	arrayOfObject	Object[]
    //   227	1	12	localIOException4	IOException
    //   78	14	14	arrayOfByte	byte[]
    //   87	69	15	localzza1	zzb.zza
    //   162	1	16	localIOException5	IOException
    // Exception table:
    //   from	to	target	type
    //   150	155	162	java/io/IOException
    //   35	54	170	java/io/IOException
    //   216	221	227	java/io/IOException
    //   35	54	235	finally
    //   2	16	253	finally
    //   28	35	253	finally
    //   150	155	253	finally
    //   216	221	253	finally
    //   245	250	253	finally
    //   250	253	253	finally
    //   245	250	258	java/io/IOException
    //   54	150	266	finally
    //   175	211	271	finally
    //   54	150	280	java/io/IOException
  }
  
  /* Error */
  public final void zza()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 32	com/google/android/gms/internal/zzv:zzay	Ljava/io/File;
    //   6: invokevirtual 257	java/io/File:exists	()Z
    //   9: ifne +41 -> 50
    //   12: aload_0
    //   13: getfield 32	com/google/android/gms/internal/zzv:zzay	Ljava/io/File;
    //   16: invokevirtual 260	java/io/File:mkdirs	()Z
    //   19: ifne +28 -> 47
    //   22: iconst_1
    //   23: anewarray 4	java/lang/Object
    //   26: astore 17
    //   28: aload 17
    //   30: iconst_0
    //   31: aload_0
    //   32: getfield 32	com/google/android/gms/internal/zzv:zzay	Ljava/io/File;
    //   35: invokevirtual 249	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   38: aastore
    //   39: ldc_w 262
    //   42: aload 17
    //   44: invokestatic 264	com/google/android/gms/internal/zzs:zzc	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   47: aload_0
    //   48: monitorexit
    //   49: return
    //   50: aload_0
    //   51: getfield 32	com/google/android/gms/internal/zzv:zzay	Ljava/io/File;
    //   54: invokevirtual 268	java/io/File:listFiles	()[Ljava/io/File;
    //   57: astore_2
    //   58: aload_2
    //   59: ifnull -12 -> 47
    //   62: aload_2
    //   63: arraylength
    //   64: istore_3
    //   65: iconst_0
    //   66: istore 4
    //   68: iload 4
    //   70: iload_3
    //   71: if_icmpge -24 -> 47
    //   74: aload_2
    //   75: iload 4
    //   77: aaload
    //   78: astore 5
    //   80: aconst_null
    //   81: astore 6
    //   83: new 270	java/io/BufferedInputStream
    //   86: dup
    //   87: new 203	java/io/FileInputStream
    //   90: dup
    //   91: aload 5
    //   93: invokespecial 204	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   96: invokespecial 273	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   99: astore 7
    //   101: aload 7
    //   103: invokestatic 210	com/google/android/gms/internal/zzv$zza:zzf	(Ljava/io/InputStream;)Lcom/google/android/gms/internal/zzv$zza;
    //   106: astore 14
    //   108: aload 14
    //   110: aload 5
    //   112: invokevirtual 213	java/io/File:length	()J
    //   115: putfield 61	com/google/android/gms/internal/zzv$zza:zzaA	J
    //   118: aload_0
    //   119: aload 14
    //   121: getfield 276	com/google/android/gms/internal/zzv$zza:key	Ljava/lang/String;
    //   124: aload 14
    //   126: invokespecial 278	com/google/android/gms/internal/zzv:zza	(Ljava/lang/String;Lcom/google/android/gms/internal/zzv$zza;)V
    //   129: aload 7
    //   131: invokevirtual 279	java/io/BufferedInputStream:close	()V
    //   134: iinc 4 1
    //   137: goto -69 -> 68
    //   140: astore 16
    //   142: aconst_null
    //   143: astore 7
    //   145: aload 5
    //   147: ifnull +9 -> 156
    //   150: aload 5
    //   152: invokevirtual 50	java/io/File:delete	()Z
    //   155: pop
    //   156: aload 7
    //   158: ifnull -24 -> 134
    //   161: aload 7
    //   163: invokevirtual 279	java/io/BufferedInputStream:close	()V
    //   166: goto -32 -> 134
    //   169: astore 9
    //   171: goto -37 -> 134
    //   174: astore 11
    //   176: aload 6
    //   178: ifnull +8 -> 186
    //   181: aload 6
    //   183: invokevirtual 279	java/io/BufferedInputStream:close	()V
    //   186: aload 11
    //   188: athrow
    //   189: astore_1
    //   190: aload_0
    //   191: monitorexit
    //   192: aload_1
    //   193: athrow
    //   194: astore 15
    //   196: goto -62 -> 134
    //   199: astore 12
    //   201: goto -15 -> 186
    //   204: astore 10
    //   206: aload 7
    //   208: astore 6
    //   210: aload 10
    //   212: astore 11
    //   214: goto -38 -> 176
    //   217: astore 8
    //   219: goto -74 -> 145
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	222	0	this	zzv
    //   189	4	1	localObject1	Object
    //   57	18	2	arrayOfFile	File[]
    //   64	8	3	i	int
    //   66	69	4	j	int
    //   78	73	5	localFile	File
    //   81	128	6	localObject2	Object
    //   99	108	7	localBufferedInputStream	java.io.BufferedInputStream
    //   217	1	8	localIOException1	IOException
    //   169	1	9	localIOException2	IOException
    //   204	7	10	localObject3	Object
    //   174	13	11	localObject4	Object
    //   212	1	11	localObject5	Object
    //   199	1	12	localIOException3	IOException
    //   106	19	14	localzza	zza
    //   194	1	15	localIOException4	IOException
    //   140	1	16	localIOException5	IOException
    //   26	17	17	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   83	101	140	java/io/IOException
    //   161	166	169	java/io/IOException
    //   83	101	174	finally
    //   2	47	189	finally
    //   50	58	189	finally
    //   62	65	189	finally
    //   74	80	189	finally
    //   129	134	189	finally
    //   161	166	189	finally
    //   181	186	189	finally
    //   186	189	189	finally
    //   129	134	194	java/io/IOException
    //   181	186	199	java/io/IOException
    //   101	129	204	finally
    //   150	156	204	finally
    //   101	129	217	java/io/IOException
  }
  
  public final void zza(String paramString, zzb.zza paramzza)
  {
    int i = 0;
    for (;;)
    {
      FileOutputStream localFileOutputStream;
      zza localzza1;
      try
      {
        int j = paramzza.data.length;
        zza localzza2;
        if (this.zzax + j >= this.zzaz)
        {
          if (zzs.DEBUG) {
            zzs.zza("Pruning old cache entries.", new Object[0]);
          }
          long l1 = this.zzax;
          long l2 = SystemClock.elapsedRealtime();
          Iterator localIterator = this.zzaw.entrySet().iterator();
          if (!localIterator.hasNext()) {
            break label405;
          }
          localzza2 = (zza)((Map.Entry)localIterator.next()).getValue();
          if (!zzf(localzza2.key).delete()) {
            continue;
          }
          this.zzax -= localzza2.zzaA;
          localIterator.remove();
          k = i + 1;
          if ((float)(this.zzax + j) >= 0.9F * this.zzaz) {
            break label399;
          }
          if (zzs.DEBUG)
          {
            Object[] arrayOfObject3 = new Object[3];
            arrayOfObject3[0] = Integer.valueOf(k);
            arrayOfObject3[1] = Long.valueOf(this.zzax - l1);
            arrayOfObject3[2] = Long.valueOf(SystemClock.elapsedRealtime() - l2);
            zzs.zza("pruned %d files, %d bytes, %d ms", arrayOfObject3);
          }
        }
        File localFile = zzf(paramString);
        try
        {
          localFileOutputStream = new FileOutputStream(localFile);
          localzza1 = new zza(paramString, paramzza);
          if (localzza1.zza(localFileOutputStream)) {
            break label375;
          }
          localFileOutputStream.close();
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localFile.getAbsolutePath();
          zzs.zzb("Failed to write header for %s", arrayOfObject2);
          throw new IOException();
        }
        catch (IOException localIOException)
        {
          if (!localFile.delete())
          {
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = localFile.getAbsolutePath();
            zzs.zzb("Could not clean up file %s", arrayOfObject1);
          }
        }
        return;
        Object[] arrayOfObject4 = new Object[2];
        arrayOfObject4[0] = localzza2.key;
        arrayOfObject4[1] = zze(localzza2.key);
        zzs.zzb("Could not delete cache entry for key=%s, filename=%s", arrayOfObject4);
        continue;
        localFileOutputStream.write(paramzza.data);
      }
      finally {}
      label375:
      localFileOutputStream.close();
      zza(paramString, localzza1);
      continue;
      label399:
      i = k;
      continue;
      label405:
      int k = i;
    }
  }
  
  static final class zza
  {
    public String key;
    public long zzaA;
    public String zzb;
    public long zzc;
    public long zzd;
    public long zze;
    public long zzf;
    public Map<String, String> zzg;
    
    private zza() {}
    
    public zza(String paramString, zzb.zza paramzza)
    {
      this.key = paramString;
      this.zzaA = paramzza.data.length;
      this.zzb = paramzza.zzb;
      this.zzc = paramzza.zzc;
      this.zzd = paramzza.zzd;
      this.zze = paramzza.zze;
      this.zzf = paramzza.zzf;
      this.zzg = paramzza.zzg;
    }
    
    public static zza zzf(InputStream paramInputStream)
      throws IOException
    {
      zza localzza = new zza();
      if (zzv.zzb(paramInputStream) != 538247942) {
        throw new IOException();
      }
      localzza.key = zzv.zzd(paramInputStream);
      localzza.zzb = zzv.zzd(paramInputStream);
      if (localzza.zzb.equals("")) {
        localzza.zzb = null;
      }
      localzza.zzc = zzv.zzc(paramInputStream);
      localzza.zzd = zzv.zzc(paramInputStream);
      localzza.zze = zzv.zzc(paramInputStream);
      localzza.zzf = zzv.zzc(paramInputStream);
      localzza.zzg = zzv.zze(paramInputStream);
      return localzza;
    }
    
    public final boolean zza(OutputStream paramOutputStream)
    {
      for (;;)
      {
        try
        {
          zzv.zza(paramOutputStream, 538247942);
          zzv.zza(paramOutputStream, this.key);
          if (this.zzb == null)
          {
            str = "";
            zzv.zza(paramOutputStream, str);
            zzv.zza(paramOutputStream, this.zzc);
            zzv.zza(paramOutputStream, this.zzd);
            zzv.zza(paramOutputStream, this.zze);
            zzv.zza(paramOutputStream, this.zzf);
            Map localMap = this.zzg;
            if (localMap == null) {
              break;
            }
            zzv.zza(paramOutputStream, localMap.size());
            Iterator localIterator = localMap.entrySet().iterator();
            if (!localIterator.hasNext()) {
              break label187;
            }
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            zzv.zza(paramOutputStream, (String)localEntry.getKey());
            zzv.zza(paramOutputStream, (String)localEntry.getValue());
            continue;
          }
          Object[] arrayOfObject;
          String str = this.zzb;
        }
        catch (IOException localIOException)
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = localIOException.toString();
          zzs.zzb("%s", arrayOfObject);
          return false;
        }
      }
      zzv.zza(paramOutputStream, 0);
      label187:
      paramOutputStream.flush();
      return true;
    }
  }
  
  private static final class zzb
    extends FilterInputStream
  {
    private int zzaB = 0;
    
    private zzb(InputStream paramInputStream)
    {
      super();
    }
    
    public final int read()
      throws IOException
    {
      int i = super.read();
      if (i != -1) {
        this.zzaB = (1 + this.zzaB);
      }
      return i;
    }
    
    public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i != -1) {
        this.zzaB = (i + this.zzaB);
      }
      return i;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzv
 * JD-Core Version:    0.7.0.1
 */