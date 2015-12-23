package com.google.android.finsky;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.util.Locale;

final class CrashDetector
  implements Thread.UncaughtExceptionHandler
{
  private final Context mContext;
  private int mCrashCount;
  private final File mCrashFile;
  private long mCrashTimestampMs;
  private volatile boolean mCrashing;
  Thread.UncaughtExceptionHandler mDefaultHandler;
  
  /* Error */
  public CrashDetector(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 25	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: aload_1
    //   6: putfield 27	com/google/android/finsky/CrashDetector:mContext	Landroid/content/Context;
    //   9: aload_1
    //   10: invokevirtual 33	android/content/Context:getCacheDir	()Ljava/io/File;
    //   13: astore_2
    //   14: getstatic 39	java/util/Locale:US	Ljava/util/Locale;
    //   17: astore_3
    //   18: iconst_1
    //   19: anewarray 4	java/lang/Object
    //   22: astore 4
    //   24: aload 4
    //   26: iconst_0
    //   27: ldc 40
    //   29: invokestatic 46	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   32: aastore
    //   33: aload_0
    //   34: new 48	java/io/File
    //   37: dup
    //   38: aload_2
    //   39: aload_3
    //   40: ldc 50
    //   42: aload 4
    //   44: invokestatic 56	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   47: invokespecial 59	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   50: putfield 61	com/google/android/finsky/CrashDetector:mCrashFile	Ljava/io/File;
    //   53: aload_0
    //   54: getfield 61	com/google/android/finsky/CrashDetector:mCrashFile	Ljava/io/File;
    //   57: invokevirtual 65	java/io/File:exists	()Z
    //   60: ifne +176 -> 236
    //   63: aload_0
    //   64: invokespecial 68	com/google/android/finsky/CrashDetector:resetCrashInfo	()V
    //   67: aload_0
    //   68: getfield 70	com/google/android/finsky/CrashDetector:mCrashCount	I
    //   71: ifle +164 -> 235
    //   74: aload_0
    //   75: getstatic 76	com/google/android/finsky/config/G:minCrashCountToPurgeCache	Lcom/google/android/play/utils/config/GservicesValue;
    //   78: invokevirtual 82	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   81: checkcast 42	java/lang/Integer
    //   84: invokevirtual 86	java/lang/Integer:intValue	()I
    //   87: invokespecial 90	com/google/android/finsky/CrashDetector:checkCrashCountThreshold	(I)Z
    //   90: ifeq +49 -> 139
    //   93: aload_0
    //   94: ldc 92
    //   96: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   99: aload_0
    //   100: new 48	java/io/File
    //   103: dup
    //   104: aload_0
    //   105: getfield 27	com/google/android/finsky/CrashDetector:mContext	Landroid/content/Context;
    //   108: invokevirtual 33	android/content/Context:getCacheDir	()Ljava/io/File;
    //   111: ldc 98
    //   113: invokespecial 59	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   116: invokespecial 102	com/google/android/finsky/CrashDetector:safeDelete	(Ljava/io/File;)V
    //   119: aload_0
    //   120: new 48	java/io/File
    //   123: dup
    //   124: aload_0
    //   125: getfield 27	com/google/android/finsky/CrashDetector:mContext	Landroid/content/Context;
    //   128: invokevirtual 33	android/content/Context:getCacheDir	()Ljava/io/File;
    //   131: ldc 104
    //   133: invokespecial 59	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   136: invokespecial 102	com/google/android/finsky/CrashDetector:safeDelete	(Ljava/io/File;)V
    //   139: aload_0
    //   140: getstatic 107	com/google/android/finsky/config/G:minCrashCountToPurgeIntermediateData	Lcom/google/android/play/utils/config/GservicesValue;
    //   143: invokevirtual 82	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   146: checkcast 42	java/lang/Integer
    //   149: invokevirtual 86	java/lang/Integer:intValue	()I
    //   152: invokespecial 90	com/google/android/finsky/CrashDetector:checkCrashCountThreshold	(I)Z
    //   155: ifeq +36 -> 191
    //   158: aload_0
    //   159: ldc 109
    //   161: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   164: aload_0
    //   165: ldc 111
    //   167: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   170: aload_0
    //   171: getfield 27	com/google/android/finsky/CrashDetector:mContext	Landroid/content/Context;
    //   174: invokestatic 116	com/google/android/finsky/services/RestoreService:deleteStores	(Landroid/content/Context;)V
    //   177: aload_0
    //   178: ldc 118
    //   180: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   183: aload_0
    //   184: getfield 27	com/google/android/finsky/CrashDetector:mContext	Landroid/content/Context;
    //   187: aconst_null
    //   188: invokestatic 124	com/google/android/finsky/appstate/SQLiteInstallerDataStore:deleteDatabaseNode	(Landroid/content/Context;Ljava/lang/String;)V
    //   191: aload_0
    //   192: getstatic 127	com/google/android/finsky/config/G:minCrashCountToPurgeUserPreferences	Lcom/google/android/play/utils/config/GservicesValue;
    //   195: invokevirtual 82	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   198: checkcast 42	java/lang/Integer
    //   201: invokevirtual 86	java/lang/Integer:intValue	()I
    //   204: invokespecial 90	com/google/android/finsky/CrashDetector:checkCrashCountThreshold	(I)Z
    //   207: ifeq +15 -> 222
    //   210: aload_0
    //   211: ldc 129
    //   213: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   216: invokestatic 134	com/google/android/finsky/utils/FinskyPreferences:clear	()V
    //   219: invokestatic 137	com/google/android/finsky/utils/VendingPreferences:clear	()V
    //   222: aload_0
    //   223: aload_0
    //   224: getfield 70	com/google/android/finsky/CrashDetector:mCrashCount	I
    //   227: aload_0
    //   228: getfield 139	com/google/android/finsky/CrashDetector:mCrashTimestampMs	J
    //   231: iconst_1
    //   232: invokespecial 143	com/google/android/finsky/CrashDetector:safeWriteCrashFile	(IJZ)V
    //   235: return
    //   236: new 145	java/io/FileInputStream
    //   239: dup
    //   240: aload_0
    //   241: getfield 61	com/google/android/finsky/CrashDetector:mCrashFile	Ljava/io/File;
    //   244: invokespecial 147	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   247: astore 10
    //   249: new 149	java/io/ObjectInputStream
    //   252: dup
    //   253: aload 10
    //   255: invokespecial 152	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   258: astore 11
    //   260: aload_0
    //   261: aload 11
    //   263: invokevirtual 155	java/io/ObjectInputStream:readInt	()I
    //   266: putfield 70	com/google/android/finsky/CrashDetector:mCrashCount	I
    //   269: aload_0
    //   270: aload 11
    //   272: invokevirtual 159	java/io/ObjectInputStream:readLong	()J
    //   275: putfield 139	com/google/android/finsky/CrashDetector:mCrashTimestampMs	J
    //   278: aload 11
    //   280: invokevirtual 162	java/io/ObjectInputStream:readBoolean	()Z
    //   283: istore 14
    //   285: aload 11
    //   287: invokevirtual 165	java/io/ObjectInputStream:close	()V
    //   290: aload 10
    //   292: invokevirtual 166	java/io/FileInputStream:close	()V
    //   295: aload_0
    //   296: ldc 168
    //   298: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   301: aload_0
    //   302: getfield 70	com/google/android/finsky/CrashDetector:mCrashCount	I
    //   305: getstatic 171	com/google/android/finsky/config/G:maxCrashCount	Lcom/google/android/play/utils/config/GservicesValue;
    //   308: invokevirtual 82	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   311: checkcast 42	java/lang/Integer
    //   314: invokevirtual 86	java/lang/Integer:intValue	()I
    //   317: if_icmple +111 -> 428
    //   320: iconst_1
    //   321: istore 15
    //   323: aload_0
    //   324: getfield 139	com/google/android/finsky/CrashDetector:mCrashTimestampMs	J
    //   327: lconst_0
    //   328: lcmp
    //   329: ifle +105 -> 434
    //   332: invokestatic 176	java/lang/System:currentTimeMillis	()J
    //   335: aload_0
    //   336: getfield 139	com/google/android/finsky/CrashDetector:mCrashTimestampMs	J
    //   339: lsub
    //   340: getstatic 179	com/google/android/finsky/config/G:crashFileExpiredTimeoutMs	Lcom/google/android/play/utils/config/GservicesValue;
    //   343: invokevirtual 82	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   346: checkcast 181	java/lang/Long
    //   349: invokevirtual 184	java/lang/Long:longValue	()J
    //   352: lcmp
    //   353: ifle +81 -> 434
    //   356: iconst_1
    //   357: istore 16
    //   359: goto +133 -> 492
    //   362: aload_0
    //   363: ldc 186
    //   365: invokespecial 96	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;)V
    //   368: aload_0
    //   369: invokespecial 68	com/google/android/finsky/CrashDetector:resetCrashInfo	()V
    //   372: aload_0
    //   373: aload_0
    //   374: getfield 61	com/google/android/finsky/CrashDetector:mCrashFile	Ljava/io/File;
    //   377: invokespecial 102	com/google/android/finsky/CrashDetector:safeDelete	(Ljava/io/File;)V
    //   380: goto -313 -> 67
    //   383: astore 5
    //   385: aload_0
    //   386: ldc 188
    //   388: aload 5
    //   390: invokespecial 191	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   393: aload_0
    //   394: invokespecial 68	com/google/android/finsky/CrashDetector:resetCrashInfo	()V
    //   397: aload_0
    //   398: aload_0
    //   399: getfield 61	com/google/android/finsky/CrashDetector:mCrashFile	Ljava/io/File;
    //   402: invokespecial 102	com/google/android/finsky/CrashDetector:safeDelete	(Ljava/io/File;)V
    //   405: goto -338 -> 67
    //   408: astore 12
    //   410: aload 11
    //   412: invokevirtual 165	java/io/ObjectInputStream:close	()V
    //   415: aload 12
    //   417: athrow
    //   418: astore 13
    //   420: aload 10
    //   422: invokevirtual 166	java/io/FileInputStream:close	()V
    //   425: aload 13
    //   427: athrow
    //   428: iconst_0
    //   429: istore 15
    //   431: goto -108 -> 323
    //   434: iconst_0
    //   435: istore 16
    //   437: goto +55 -> 492
    //   440: astore 9
    //   442: aload_0
    //   443: ldc 193
    //   445: aload 9
    //   447: invokespecial 191	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   450: goto -311 -> 139
    //   453: astore 7
    //   455: aload_0
    //   456: ldc 195
    //   458: aload 7
    //   460: invokespecial 191	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   463: goto -286 -> 177
    //   466: astore 8
    //   468: aload_0
    //   469: ldc 197
    //   471: aload 8
    //   473: invokespecial 191	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   476: goto -285 -> 191
    //   479: astore 6
    //   481: aload_0
    //   482: ldc 199
    //   484: aload 6
    //   486: invokespecial 191	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   489: goto -267 -> 222
    //   492: iload 16
    //   494: ifne -132 -> 362
    //   497: iload 14
    //   499: ifne -137 -> 362
    //   502: iload 15
    //   504: ifeq -437 -> 67
    //   507: goto -145 -> 362
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	510	0	this	CrashDetector
    //   0	510	1	paramContext	Context
    //   13	26	2	localFile	File
    //   17	23	3	localLocale	Locale
    //   22	21	4	arrayOfObject	Object[]
    //   383	6	5	localThrowable1	Throwable
    //   479	6	6	localThrowable2	Throwable
    //   453	6	7	localThrowable3	Throwable
    //   466	6	8	localThrowable4	Throwable
    //   440	6	9	localThrowable5	Throwable
    //   247	174	10	localFileInputStream	java.io.FileInputStream
    //   258	153	11	localObjectInputStream	java.io.ObjectInputStream
    //   408	8	12	localObject1	Object
    //   418	8	13	localObject2	Object
    //   283	215	14	bool	boolean
    //   321	182	15	i	int
    //   357	136	16	j	int
    // Exception table:
    //   from	to	target	type
    //   53	67	383	java/lang/Throwable
    //   236	249	383	java/lang/Throwable
    //   290	320	383	java/lang/Throwable
    //   323	356	383	java/lang/Throwable
    //   362	380	383	java/lang/Throwable
    //   420	428	383	java/lang/Throwable
    //   260	285	408	finally
    //   249	260	418	finally
    //   285	290	418	finally
    //   410	418	418	finally
    //   99	139	440	java/lang/Throwable
    //   170	177	453	java/lang/Throwable
    //   183	191	466	java/lang/Throwable
    //   216	222	479	java/lang/Throwable
  }
  
  private boolean checkCrashCountThreshold(int paramInt)
  {
    return (paramInt > 0) && (this.mCrashCount >= paramInt);
  }
  
  private void resetCrashInfo()
  {
    this.mCrashCount = 0;
    this.mCrashTimestampMs = 0L;
  }
  
  private void safeDelete(File paramFile)
  {
    try
    {
      if (paramFile.isDirectory())
      {
        File[] arrayOfFile = paramFile.listFiles();
        if (arrayOfFile != null)
        {
          int i = arrayOfFile.length;
          for (int j = 0; j < i; j++) {
            safeDelete(arrayOfFile[j]);
          }
        }
      }
      if (!paramFile.delete()) {
        safeLog("Failed to delete file: " + paramFile);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      safeLog("Failed to delete file: " + paramFile, localThrowable);
    }
  }
  
  private void safeLog(String paramString)
  {
    try
    {
      Log.w("Finsky.CrashDetector", String.format(Locale.US, "%s %s.", new Object[] { paramString, this }));
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  private void safeLog(String paramString, Throwable paramThrowable)
  {
    try
    {
      Log.w("Finsky.CrashDetector", String.format(Locale.US, "%s %s.", new Object[] { paramString, this }), paramThrowable);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  /* Error */
  private void safeWriteCrashFile(int paramInt, long paramLong, boolean paramBoolean)
  {
    // Byte code:
    //   0: new 238	java/io/FileOutputStream
    //   3: dup
    //   4: aload_0
    //   5: getfield 61	com/google/android/finsky/CrashDetector:mCrashFile	Ljava/io/File;
    //   8: invokespecial 239	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   11: astore 5
    //   13: new 241	java/io/ObjectOutputStream
    //   16: dup
    //   17: aload 5
    //   19: invokespecial 244	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   22: astore 6
    //   24: aload 6
    //   26: iload_1
    //   27: invokevirtual 248	java/io/ObjectOutputStream:writeInt	(I)V
    //   30: aload 6
    //   32: lload_2
    //   33: invokevirtual 252	java/io/ObjectOutputStream:writeLong	(J)V
    //   36: aload 6
    //   38: iload 4
    //   40: invokevirtual 256	java/io/ObjectOutputStream:writeBoolean	(Z)V
    //   43: aload 6
    //   45: invokevirtual 259	java/io/ObjectOutputStream:flush	()V
    //   48: aload 5
    //   50: invokevirtual 263	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   53: invokevirtual 268	java/io/FileDescriptor:sync	()V
    //   56: aload 6
    //   58: invokevirtual 269	java/io/ObjectOutputStream:close	()V
    //   61: aload 5
    //   63: invokevirtual 270	java/io/FileOutputStream:close	()V
    //   66: return
    //   67: astore 7
    //   69: aload 6
    //   71: invokevirtual 269	java/io/ObjectOutputStream:close	()V
    //   74: aload 7
    //   76: athrow
    //   77: astore 8
    //   79: aload 5
    //   81: invokevirtual 270	java/io/FileOutputStream:close	()V
    //   84: aload 8
    //   86: athrow
    //   87: astore 9
    //   89: aload_0
    //   90: ldc_w 272
    //   93: aload 9
    //   95: invokespecial 191	com/google/android/finsky/CrashDetector:safeLog	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	99	0	this	CrashDetector
    //   0	99	1	paramInt	int
    //   0	99	2	paramLong	long
    //   0	99	4	paramBoolean	boolean
    //   11	69	5	localFileOutputStream	java.io.FileOutputStream
    //   22	48	6	localObjectOutputStream	java.io.ObjectOutputStream
    //   67	8	7	localObject1	Object
    //   77	8	8	localObject2	Object
    //   87	7	9	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   24	56	67	finally
    //   13	24	77	finally
    //   56	61	77	finally
    //   69	77	77	finally
    //   0	13	87	java/lang/Throwable
    //   61	66	87	java/lang/Throwable
    //   79	87	87	java/lang/Throwable
  }
  
  public final String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.mCrashCount);
    arrayOfObject[1] = Long.valueOf(this.mCrashTimestampMs);
    return String.format(localLocale, "cnt=%d, ts=%d", arrayOfObject);
  }
  
  public final void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    if (this.mCrashing) {}
    do
    {
      return;
      this.mCrashing = true;
      safeWriteCrashFile(1 + this.mCrashCount, System.currentTimeMillis(), false);
    } while (this.mDefaultHandler == null);
    this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.CrashDetector
 * JD-Core Version:    0.7.0.1
 */