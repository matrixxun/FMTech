package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.VolleyLog;
import java.io.BufferedOutputStream;
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

public final class DiskBasedCache
  implements Cache
{
  private final Map<String, CacheHeader> mEntries = new LinkedHashMap(16, 0.75F, true);
  private final int mMaxCacheSizeInBytes;
  private final File mRootDirectory;
  private long mTotalSize = 0L;
  
  public DiskBasedCache(File paramFile, int paramInt)
  {
    this.mRootDirectory = paramFile;
    this.mMaxCacheSizeInBytes = paramInt;
  }
  
  private File getFileForKey(String paramString)
  {
    return new File(this.mRootDirectory, getFilenameForKey(paramString));
  }
  
  private static String getFilenameForKey(String paramString)
  {
    int i = paramString.length() / 2;
    String str = String.valueOf(paramString.substring(0, i).hashCode());
    return str + String.valueOf(paramString.substring(i).hashCode());
  }
  
  private void putEntry(String paramString, CacheHeader paramCacheHeader)
  {
    if (!this.mEntries.containsKey(paramString)) {}
    CacheHeader localCacheHeader;
    for (this.mTotalSize += paramCacheHeader.size;; this.mTotalSize += paramCacheHeader.size - localCacheHeader.size)
    {
      this.mEntries.put(paramString, paramCacheHeader);
      return;
      localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    }
  }
  
  private static int read(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1) {
      throw new EOFException();
    }
    return i;
  }
  
  static int readInt(InputStream paramInputStream)
    throws IOException
  {
    return 0x0 | read(paramInputStream) << 0 | read(paramInputStream) << 8 | read(paramInputStream) << 16 | read(paramInputStream) << 24;
  }
  
  static long readLong(InputStream paramInputStream)
    throws IOException
  {
    return 0L | (0xFF & read(paramInputStream)) << 0 | (0xFF & read(paramInputStream)) << 8 | (0xFF & read(paramInputStream)) << 16 | (0xFF & read(paramInputStream)) << 24 | (0xFF & read(paramInputStream)) << 32 | (0xFF & read(paramInputStream)) << 40 | (0xFF & read(paramInputStream)) << 48 | (0xFF & read(paramInputStream)) << 56;
  }
  
  static String readString(InputStream paramInputStream)
    throws IOException
  {
    return new String(streamToBytes(paramInputStream, (int)readLong(paramInputStream)), "UTF-8");
  }
  
  static Map<String, String> readStringStringMap(InputStream paramInputStream)
    throws IOException
  {
    int i = readInt(paramInputStream);
    if (i == 0) {}
    for (Object localObject = Collections.emptyMap();; localObject = new HashMap(i)) {
      for (int j = 0; j < i; j++) {
        ((Map)localObject).put(readString(paramInputStream).intern(), readString(paramInputStream).intern());
      }
    }
    return localObject;
  }
  
  private void remove(String paramString)
  {
    try
    {
      boolean bool = getFileForKey(paramString).delete();
      CacheHeader localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
      if (localCacheHeader != null)
      {
        this.mTotalSize -= localCacheHeader.size;
        this.mEntries.remove(paramString);
      }
      if (!bool)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = getFilenameForKey(paramString);
        VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", arrayOfObject);
      }
      return;
    }
    finally {}
  }
  
  private static byte[] streamToBytes(InputStream paramInputStream, int paramInt)
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
  
  static void writeInt(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(0xFF & paramInt >> 0);
    paramOutputStream.write(0xFF & paramInt >> 8);
    paramOutputStream.write(0xFF & paramInt >> 16);
    paramOutputStream.write(0xFF & paramInt >> 24);
  }
  
  static void writeLong(OutputStream paramOutputStream, long paramLong)
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
  
  static void writeString(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    writeLong(paramOutputStream, arrayOfByte.length);
    paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public final void clear()
  {
    try
    {
      File[] arrayOfFile = this.mRootDirectory.listFiles();
      if (arrayOfFile != null)
      {
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++) {
          arrayOfFile[j].delete();
        }
      }
      this.mEntries.clear();
      this.mTotalSize = 0L;
      VolleyLog.d("Cache cleared.", new Object[0]);
      return;
    }
    finally {}
  }
  
  /* Error */
  public final Cache.Entry get(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 28	com/android/volley/toolbox/DiskBasedCache:mEntries	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 96 2 0
    //   12: checkcast 85	com/android/volley/toolbox/DiskBasedCache$CacheHeader
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: aload_3
    //   20: ifnonnull +8 -> 28
    //   23: aload_0
    //   24: monitorexit
    //   25: aload 4
    //   27: areturn
    //   28: aload_0
    //   29: aload_1
    //   30: invokespecial 151	com/android/volley/toolbox/DiskBasedCache:getFileForKey	(Ljava/lang/String;)Ljava/io/File;
    //   33: astore 5
    //   35: aconst_null
    //   36: astore 6
    //   38: new 212	com/android/volley/toolbox/DiskBasedCache$CountingInputStream
    //   41: dup
    //   42: new 214	java/io/BufferedInputStream
    //   45: dup
    //   46: new 216	java/io/FileInputStream
    //   49: dup
    //   50: aload 5
    //   52: invokespecial 219	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   55: invokespecial 222	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   58: iconst_0
    //   59: invokespecial 225	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:<init>	(Ljava/io/InputStream;B)V
    //   62: astore 7
    //   64: aload 7
    //   66: invokestatic 229	com/android/volley/toolbox/DiskBasedCache$CacheHeader:readHeader	(Ljava/io/InputStream;)Lcom/android/volley/toolbox/DiskBasedCache$CacheHeader;
    //   69: pop
    //   70: aload 7
    //   72: aload 5
    //   74: invokevirtual 232	java/io/File:length	()J
    //   77: aload 7
    //   79: invokestatic 236	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:access$100	(Lcom/android/volley/toolbox/DiskBasedCache$CountingInputStream;)I
    //   82: i2l
    //   83: lsub
    //   84: l2i
    //   85: invokestatic 122	com/android/volley/toolbox/DiskBasedCache:streamToBytes	(Ljava/io/InputStream;I)[B
    //   88: astore 14
    //   90: new 238	com/android/volley/Cache$Entry
    //   93: dup
    //   94: invokespecial 239	com/android/volley/Cache$Entry:<init>	()V
    //   97: astore 15
    //   99: aload 15
    //   101: aload 14
    //   103: putfield 243	com/android/volley/Cache$Entry:data	[B
    //   106: aload 15
    //   108: aload_3
    //   109: getfield 247	com/android/volley/toolbox/DiskBasedCache$CacheHeader:etag	Ljava/lang/String;
    //   112: putfield 248	com/android/volley/Cache$Entry:etag	Ljava/lang/String;
    //   115: aload 15
    //   117: aload_3
    //   118: getfield 251	com/android/volley/toolbox/DiskBasedCache$CacheHeader:serverDate	J
    //   121: putfield 252	com/android/volley/Cache$Entry:serverDate	J
    //   124: aload 15
    //   126: aload_3
    //   127: getfield 255	com/android/volley/toolbox/DiskBasedCache$CacheHeader:lastModified	J
    //   130: putfield 256	com/android/volley/Cache$Entry:lastModified	J
    //   133: aload 15
    //   135: aload_3
    //   136: getfield 259	com/android/volley/toolbox/DiskBasedCache$CacheHeader:ttl	J
    //   139: putfield 260	com/android/volley/Cache$Entry:ttl	J
    //   142: aload 15
    //   144: aload_3
    //   145: getfield 263	com/android/volley/toolbox/DiskBasedCache$CacheHeader:softTtl	J
    //   148: putfield 264	com/android/volley/Cache$Entry:softTtl	J
    //   151: aload 15
    //   153: aload_3
    //   154: getfield 267	com/android/volley/toolbox/DiskBasedCache$CacheHeader:responseHeaders	Ljava/util/Map;
    //   157: putfield 268	com/android/volley/Cache$Entry:responseHeaders	Ljava/util/Map;
    //   160: aload 7
    //   162: invokevirtual 271	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   165: aload 15
    //   167: astore 4
    //   169: goto -146 -> 23
    //   172: astore 8
    //   174: iconst_2
    //   175: anewarray 4	java/lang/Object
    //   178: astore 11
    //   180: aload 11
    //   182: iconst_0
    //   183: aload 5
    //   185: invokevirtual 274	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   188: aastore
    //   189: aload 11
    //   191: iconst_1
    //   192: aload 8
    //   194: invokevirtual 275	java/io/IOException:toString	()Ljava/lang/String;
    //   197: aastore
    //   198: ldc_w 277
    //   201: aload 11
    //   203: invokestatic 165	com/android/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   206: aload_0
    //   207: aload_1
    //   208: invokespecial 279	com/android/volley/toolbox/DiskBasedCache:remove	(Ljava/lang/String;)V
    //   211: aconst_null
    //   212: astore 4
    //   214: aload 6
    //   216: ifnull -193 -> 23
    //   219: aload 6
    //   221: invokevirtual 271	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   224: aconst_null
    //   225: astore 4
    //   227: goto -204 -> 23
    //   230: astore 12
    //   232: aconst_null
    //   233: astore 4
    //   235: goto -212 -> 23
    //   238: astore 9
    //   240: aload 6
    //   242: ifnull +8 -> 250
    //   245: aload 6
    //   247: invokevirtual 271	com/android/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   250: aload 9
    //   252: athrow
    //   253: astore_2
    //   254: aload_0
    //   255: monitorexit
    //   256: aload_2
    //   257: athrow
    //   258: astore 16
    //   260: aconst_null
    //   261: astore 4
    //   263: goto -240 -> 23
    //   266: astore 10
    //   268: aconst_null
    //   269: astore 4
    //   271: goto -248 -> 23
    //   274: astore 9
    //   276: aload 7
    //   278: astore 6
    //   280: goto -40 -> 240
    //   283: astore 8
    //   285: aload 7
    //   287: astore 6
    //   289: goto -115 -> 174
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	292	0	this	DiskBasedCache
    //   0	292	1	paramString	String
    //   253	4	2	localObject1	Object
    //   15	139	3	localCacheHeader	CacheHeader
    //   17	253	4	localObject2	Object
    //   33	151	5	localFile	File
    //   36	252	6	localObject3	Object
    //   62	224	7	localCountingInputStream	CountingInputStream
    //   172	21	8	localIOException1	IOException
    //   283	1	8	localIOException2	IOException
    //   238	13	9	localObject4	Object
    //   274	1	9	localObject5	Object
    //   266	1	10	localIOException3	IOException
    //   178	24	11	arrayOfObject	Object[]
    //   230	1	12	localIOException4	IOException
    //   88	14	14	arrayOfByte	byte[]
    //   97	69	15	localEntry	Cache.Entry
    //   258	1	16	localIOException5	IOException
    // Exception table:
    //   from	to	target	type
    //   38	64	172	java/io/IOException
    //   219	224	230	java/io/IOException
    //   38	64	238	finally
    //   174	211	238	finally
    //   2	16	253	finally
    //   28	35	253	finally
    //   160	165	253	finally
    //   219	224	253	finally
    //   245	250	253	finally
    //   250	253	253	finally
    //   160	165	258	java/io/IOException
    //   245	250	266	java/io/IOException
    //   64	160	274	finally
    //   64	160	283	java/io/IOException
  }
  
  /* Error */
  public final void initialize()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 32	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   6: invokevirtual 283	java/io/File:exists	()Z
    //   9: ifne +41 -> 50
    //   12: aload_0
    //   13: getfield 32	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   16: invokevirtual 286	java/io/File:mkdirs	()Z
    //   19: ifne +28 -> 47
    //   22: iconst_1
    //   23: anewarray 4	java/lang/Object
    //   26: astore 16
    //   28: aload 16
    //   30: iconst_0
    //   31: aload_0
    //   32: getfield 32	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   35: invokevirtual 274	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   38: aastore
    //   39: ldc_w 288
    //   42: aload 16
    //   44: invokestatic 291	com/android/volley/VolleyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   47: aload_0
    //   48: monitorexit
    //   49: return
    //   50: aload_0
    //   51: getfield 32	com/android/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   54: invokevirtual 205	java/io/File:listFiles	()[Ljava/io/File;
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
    //   83: new 214	java/io/BufferedInputStream
    //   86: dup
    //   87: new 216	java/io/FileInputStream
    //   90: dup
    //   91: aload 5
    //   93: invokespecial 219	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   96: invokespecial 222	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   99: astore 7
    //   101: aload 7
    //   103: invokestatic 229	com/android/volley/toolbox/DiskBasedCache$CacheHeader:readHeader	(Ljava/io/InputStream;)Lcom/android/volley/toolbox/DiskBasedCache$CacheHeader;
    //   106: astore 13
    //   108: aload 13
    //   110: aload 5
    //   112: invokevirtual 232	java/io/File:length	()J
    //   115: putfield 88	com/android/volley/toolbox/DiskBasedCache$CacheHeader:size	J
    //   118: aload_0
    //   119: aload 13
    //   121: getfield 294	com/android/volley/toolbox/DiskBasedCache$CacheHeader:key	Ljava/lang/String;
    //   124: aload 13
    //   126: invokespecial 296	com/android/volley/toolbox/DiskBasedCache:putEntry	(Ljava/lang/String;Lcom/android/volley/toolbox/DiskBasedCache$CacheHeader;)V
    //   129: aload 7
    //   131: invokevirtual 297	java/io/BufferedInputStream:close	()V
    //   134: iinc 4 1
    //   137: goto -69 -> 68
    //   140: astore 14
    //   142: goto -8 -> 134
    //   145: astore 15
    //   147: aload 5
    //   149: ifnull +9 -> 158
    //   152: aload 5
    //   154: invokevirtual 155	java/io/File:delete	()Z
    //   157: pop
    //   158: aload 6
    //   160: ifnull -26 -> 134
    //   163: aload 6
    //   165: invokevirtual 297	java/io/BufferedInputStream:close	()V
    //   168: goto -34 -> 134
    //   171: astore 9
    //   173: goto -39 -> 134
    //   176: astore 10
    //   178: aload 6
    //   180: ifnull +8 -> 188
    //   183: aload 6
    //   185: invokevirtual 297	java/io/BufferedInputStream:close	()V
    //   188: aload 10
    //   190: athrow
    //   191: astore_1
    //   192: aload_0
    //   193: monitorexit
    //   194: aload_1
    //   195: athrow
    //   196: astore 11
    //   198: goto -10 -> 188
    //   201: astore 10
    //   203: aload 7
    //   205: astore 6
    //   207: goto -29 -> 178
    //   210: astore 8
    //   212: aload 7
    //   214: astore 6
    //   216: goto -69 -> 147
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	this	DiskBasedCache
    //   191	4	1	localObject1	Object
    //   57	18	2	arrayOfFile	File[]
    //   64	8	3	i	int
    //   66	69	4	j	int
    //   78	75	5	localFile	File
    //   81	134	6	localObject2	Object
    //   99	114	7	localBufferedInputStream	java.io.BufferedInputStream
    //   210	1	8	localIOException1	IOException
    //   171	1	9	localIOException2	IOException
    //   176	13	10	localObject3	Object
    //   201	1	10	localObject4	Object
    //   196	1	11	localIOException3	IOException
    //   106	19	13	localCacheHeader	CacheHeader
    //   140	1	14	localIOException4	IOException
    //   145	1	15	localIOException5	IOException
    //   26	17	16	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   129	134	140	java/io/IOException
    //   83	101	145	java/io/IOException
    //   163	168	171	java/io/IOException
    //   83	101	176	finally
    //   152	158	176	finally
    //   2	47	191	finally
    //   50	58	191	finally
    //   62	65	191	finally
    //   74	80	191	finally
    //   129	134	191	finally
    //   163	168	191	finally
    //   183	188	191	finally
    //   188	191	191	finally
    //   183	188	196	java/io/IOException
    //   101	129	201	finally
    //   101	129	210	java/io/IOException
  }
  
  public final void invalidate(String paramString, boolean paramBoolean)
  {
    try
    {
      Cache.Entry localEntry = get(paramString);
      if (localEntry != null)
      {
        localEntry.softTtl = 0L;
        if (paramBoolean) {
          localEntry.ttl = 0L;
        }
        put(paramString, localEntry);
      }
      return;
    }
    finally {}
  }
  
  public final void put(String paramString, Cache.Entry paramEntry)
  {
    for (;;)
    {
      BufferedOutputStream localBufferedOutputStream;
      CacheHeader localCacheHeader1;
      try
      {
        int i = paramEntry.data.length;
        CacheHeader localCacheHeader2;
        if (this.mTotalSize + i >= this.mMaxCacheSizeInBytes)
        {
          if (VolleyLog.DEBUG) {
            VolleyLog.v("Pruning old cache entries.", new Object[0]);
          }
          long l1 = this.mTotalSize;
          j = 0;
          long l2 = SystemClock.elapsedRealtime();
          Iterator localIterator = this.mEntries.entrySet().iterator();
          if (!localIterator.hasNext()) {
            break label413;
          }
          localCacheHeader2 = (CacheHeader)((Map.Entry)localIterator.next()).getValue();
          if (!getFileForKey(localCacheHeader2.key).delete()) {
            continue;
          }
          this.mTotalSize -= localCacheHeader2.size;
          localIterator.remove();
          k = j + 1;
          if ((float)(this.mTotalSize + i) >= 0.9F * this.mMaxCacheSizeInBytes) {
            break label406;
          }
          if (VolleyLog.DEBUG)
          {
            Object[] arrayOfObject3 = new Object[3];
            arrayOfObject3[0] = Integer.valueOf(k);
            arrayOfObject3[1] = Long.valueOf(this.mTotalSize - l1);
            arrayOfObject3[2] = Long.valueOf(SystemClock.elapsedRealtime() - l2);
            VolleyLog.v("pruned %d files, %d bytes, %d ms", arrayOfObject3);
          }
        }
        File localFile = getFileForKey(paramString);
        try
        {
          localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));
          localCacheHeader1 = new CacheHeader(paramString, paramEntry);
          if (localCacheHeader1.writeHeader(localBufferedOutputStream)) {
            break label382;
          }
          localBufferedOutputStream.close();
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localFile.getAbsolutePath();
          VolleyLog.d("Failed to write header for %s", arrayOfObject2);
          throw new IOException();
        }
        catch (IOException localIOException)
        {
          if (!localFile.delete())
          {
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = localFile.getAbsolutePath();
            VolleyLog.d("Could not clean up file %s", arrayOfObject1);
          }
        }
        return;
        Object[] arrayOfObject4 = new Object[2];
        arrayOfObject4[0] = localCacheHeader2.key;
        arrayOfObject4[1] = getFilenameForKey(localCacheHeader2.key);
        VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", arrayOfObject4);
        continue;
        localBufferedOutputStream.write(paramEntry.data);
      }
      finally {}
      label382:
      localBufferedOutputStream.close();
      putEntry(paramString, localCacheHeader1);
      continue;
      label406:
      int j = k;
      continue;
      label413:
      int k = j;
    }
  }
  
  static final class CacheHeader
  {
    public String etag;
    public String key;
    public long lastModified;
    public Map<String, String> responseHeaders;
    public long serverDate;
    public long size;
    public long softTtl;
    public long ttl;
    
    private CacheHeader() {}
    
    public CacheHeader(String paramString, Cache.Entry paramEntry)
    {
      this.key = paramString;
      this.size = paramEntry.data.length;
      this.etag = paramEntry.etag;
      this.serverDate = paramEntry.serverDate;
      this.lastModified = paramEntry.lastModified;
      this.ttl = paramEntry.ttl;
      this.softTtl = paramEntry.softTtl;
      this.responseHeaders = paramEntry.responseHeaders;
    }
    
    public static CacheHeader readHeader(InputStream paramInputStream)
      throws IOException
    {
      CacheHeader localCacheHeader = new CacheHeader();
      if (DiskBasedCache.readInt(paramInputStream) != 538247942) {
        throw new IOException();
      }
      localCacheHeader.key = DiskBasedCache.readString(paramInputStream);
      localCacheHeader.etag = DiskBasedCache.readString(paramInputStream);
      if (localCacheHeader.etag.equals("")) {
        localCacheHeader.etag = null;
      }
      localCacheHeader.serverDate = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.lastModified = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.ttl = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.softTtl = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.responseHeaders = DiskBasedCache.readStringStringMap(paramInputStream);
      return localCacheHeader;
    }
    
    public final boolean writeHeader(OutputStream paramOutputStream)
    {
      for (;;)
      {
        try
        {
          DiskBasedCache.writeInt(paramOutputStream, 538247942);
          DiskBasedCache.writeString(paramOutputStream, this.key);
          if (this.etag == null)
          {
            str = "";
            DiskBasedCache.writeString(paramOutputStream, str);
            DiskBasedCache.writeLong(paramOutputStream, this.serverDate);
            DiskBasedCache.writeLong(paramOutputStream, this.lastModified);
            DiskBasedCache.writeLong(paramOutputStream, this.ttl);
            DiskBasedCache.writeLong(paramOutputStream, this.softTtl);
            Map localMap = this.responseHeaders;
            if (localMap == null) {
              break;
            }
            DiskBasedCache.writeInt(paramOutputStream, localMap.size());
            Iterator localIterator = localMap.entrySet().iterator();
            if (!localIterator.hasNext()) {
              break label187;
            }
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            DiskBasedCache.writeString(paramOutputStream, (String)localEntry.getKey());
            DiskBasedCache.writeString(paramOutputStream, (String)localEntry.getValue());
            continue;
          }
          Object[] arrayOfObject;
          String str = this.etag;
        }
        catch (IOException localIOException)
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = localIOException.toString();
          VolleyLog.d("%s", arrayOfObject);
          return false;
        }
      }
      DiskBasedCache.writeInt(paramOutputStream, 0);
      label187:
      paramOutputStream.flush();
      return true;
    }
  }
  
  private static final class CountingInputStream
    extends FilterInputStream
  {
    private int bytesRead = 0;
    
    private CountingInputStream(InputStream paramInputStream)
    {
      super();
    }
    
    public final int read()
      throws IOException
    {
      int i = super.read();
      if (i != -1) {
        this.bytesRead = (1 + this.bytesRead);
      }
      return i;
    }
    
    public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i != -1) {
        this.bytesRead = (i + this.bytesRead);
      }
      return i;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.DiskBasedCache
 * JD-Core Version:    0.7.0.1
 */