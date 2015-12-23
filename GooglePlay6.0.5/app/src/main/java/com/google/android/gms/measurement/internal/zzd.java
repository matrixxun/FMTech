package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.zzaic;
import com.google.android.gms.internal.zzso.zzd;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class zzd
  extends zzw
{
  private static final Map<String, String> zzbmi;
  private final zza zzbmj;
  private final zzaa zzbmk = new zzaa(getClock());
  
  static
  {
    ArrayMap localArrayMap = new ArrayMap(5);
    zzbmi = localArrayMap;
    localArrayMap.put("app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;");
    zzbmi.put("app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;");
    zzbmi.put("gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;");
    zzbmi.put("dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;");
    zzbmi.put("measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;");
  }
  
  zzd(zzt paramzzt)
  {
    super(paramzzt);
    String str = zziQ();
    this.zzbmj = new zza(getContext(), str);
  }
  
  private SQLiteDatabase getWritableDatabase()
  {
    checkOnWorkerThread();
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.zzbmj.getWritableDatabase();
      return localSQLiteDatabase;
    }
    catch (SQLiteException localSQLiteException)
    {
      zzBh().zzbmZ.zzm("Error opening database", localSQLiteException);
      throw localSQLiteException;
    }
  }
  
  private boolean zzCf()
  {
    return getContext().getDatabasePath(zziQ()).exists();
  }
  
  private String zziQ()
  {
    if (!zzc.isPackageSide()) {
      return zzc.getStoreDatabaseFileName();
    }
    if (zzCa().isMainProcess()) {
      return zzc.getStoreDatabaseFileName();
    }
    zzBh().zzbna.zzeB("Using secondary database");
    return zzc.getStoreDatabaseSecondaryFileName();
  }
  
  public final void beginTransaction()
  {
    zziL();
    getWritableDatabase().beginTransaction();
  }
  
  public final void endTransaction()
  {
    zziL();
    getWritableDatabase().endTransaction();
  }
  
  protected final void onInitialize() {}
  
  public final void setTransactionSuccessful()
  {
    zziL();
    getWritableDatabase().setTransactionSuccessful();
  }
  
  /* Error */
  public final String zzCb()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 154	com/google/android/gms/measurement/internal/zzd:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore_1
    //   5: aload_1
    //   6: ldc 168
    //   8: aconst_null
    //   9: invokevirtual 172	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   12: astore 7
    //   14: aload 7
    //   16: astore_3
    //   17: aload_3
    //   18: invokeinterface 177 1 0
    //   23: ifeq +29 -> 52
    //   26: aload_3
    //   27: iconst_0
    //   28: invokeinterface 181 2 0
    //   33: astore 8
    //   35: aload 8
    //   37: astore 6
    //   39: aload_3
    //   40: ifnull +9 -> 49
    //   43: aload_3
    //   44: invokeinterface 184 1 0
    //   49: aload 6
    //   51: areturn
    //   52: aconst_null
    //   53: astore 6
    //   55: aload_3
    //   56: ifnull -7 -> 49
    //   59: aload_3
    //   60: invokeinterface 184 1 0
    //   65: aconst_null
    //   66: areturn
    //   67: astore 5
    //   69: aconst_null
    //   70: astore_3
    //   71: aload_0
    //   72: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   75: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   78: ldc 189
    //   80: aload 5
    //   82: invokevirtual 103	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
    //   85: aconst_null
    //   86: astore 6
    //   88: aload_3
    //   89: ifnull -40 -> 49
    //   92: aload_3
    //   93: invokeinterface 184 1 0
    //   98: aconst_null
    //   99: areturn
    //   100: astore_2
    //   101: aconst_null
    //   102: astore_3
    //   103: aload_2
    //   104: astore 4
    //   106: aload_3
    //   107: ifnull +9 -> 116
    //   110: aload_3
    //   111: invokeinterface 184 1 0
    //   116: aload 4
    //   118: athrow
    //   119: astore 4
    //   121: goto -15 -> 106
    //   124: astore 5
    //   126: goto -55 -> 71
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	this	zzd
    //   4	2	1	localSQLiteDatabase	SQLiteDatabase
    //   100	4	2	localObject1	Object
    //   16	95	3	localCursor1	Cursor
    //   104	13	4	localObject2	Object
    //   119	1	4	localObject3	Object
    //   67	14	5	localSQLiteException1	SQLiteException
    //   124	1	5	localSQLiteException2	SQLiteException
    //   37	50	6	str1	String
    //   12	3	7	localCursor2	Cursor
    //   33	3	8	str2	String
    // Exception table:
    //   from	to	target	type
    //   5	14	67	android/database/sqlite/SQLiteException
    //   5	14	100	finally
    //   17	35	119	finally
    //   71	85	119	finally
    //   17	35	124	android/database/sqlite/SQLiteException
  }
  
  final void zzCc()
  {
    checkOnWorkerThread();
    zziL();
    if (!zzCf()) {}
    int i;
    do
    {
      do
      {
        long l1;
        long l2;
        do
        {
          return;
          l1 = zzBZ().zzbnw.get();
          l2 = getClock().elapsedRealtime();
        } while (Math.abs(l2 - l1) <= zzc.zzBJ());
        zzBZ().zzbnw.set(l2);
        checkOnWorkerThread();
        zziL();
      } while (!zzCf());
      SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(getClock().currentTimeMillis());
      arrayOfString[1] = String.valueOf(zzc.zzBI());
      i = localSQLiteDatabase.delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", arrayOfString);
    } while (i <= 0);
    zzBh().zzbne.zzm("Deleted stale rows. rowsDeleted", Integer.valueOf(i));
  }
  
  /* Error */
  public final zzh zzW(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_1
    //   3: invokestatic 264	com/google/android/gms/common/internal/zzx:zzcG	(Ljava/lang/String;)Ljava/lang/String;
    //   6: pop
    //   7: aload_2
    //   8: invokestatic 264	com/google/android/gms/common/internal/zzx:zzcG	(Ljava/lang/String;)Ljava/lang/String;
    //   11: pop
    //   12: aload_0
    //   13: invokevirtual 83	com/google/android/gms/measurement/internal/zzd:checkOnWorkerThread	()V
    //   16: aload_0
    //   17: invokevirtual 153	com/google/android/gms/measurement/internal/zzd:zziL	()V
    //   20: aload_0
    //   21: invokespecial 154	com/google/android/gms/measurement/internal/zzd:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   24: ldc_w 266
    //   27: iconst_3
    //   28: anewarray 228	java/lang/String
    //   31: dup
    //   32: iconst_0
    //   33: ldc_w 268
    //   36: aastore
    //   37: dup
    //   38: iconst_1
    //   39: ldc_w 270
    //   42: aastore
    //   43: dup
    //   44: iconst_2
    //   45: ldc_w 272
    //   48: aastore
    //   49: ldc_w 274
    //   52: iconst_2
    //   53: anewarray 228	java/lang/String
    //   56: dup
    //   57: iconst_0
    //   58: aload_1
    //   59: aastore
    //   60: dup
    //   61: iconst_1
    //   62: aload_2
    //   63: aastore
    //   64: aconst_null
    //   65: aconst_null
    //   66: aconst_null
    //   67: invokevirtual 278	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   70: astore 9
    //   72: aload 9
    //   74: invokeinterface 177 1 0
    //   79: istore 10
    //   81: iload 10
    //   83: ifne +21 -> 104
    //   86: aload 9
    //   88: ifnull +10 -> 98
    //   91: aload 9
    //   93: invokeinterface 184 1 0
    //   98: aconst_null
    //   99: astore 11
    //   101: aload 11
    //   103: areturn
    //   104: new 280	com/google/android/gms/measurement/internal/zzh
    //   107: dup
    //   108: aload_1
    //   109: aload_2
    //   110: aload 9
    //   112: iconst_0
    //   113: invokeinterface 284 2 0
    //   118: aload 9
    //   120: iconst_1
    //   121: invokeinterface 284 2 0
    //   126: aload 9
    //   128: iconst_2
    //   129: invokeinterface 284 2 0
    //   134: invokespecial 287	com/google/android/gms/measurement/internal/zzh:<init>	(Ljava/lang/String;Ljava/lang/String;JJJ)V
    //   137: astore 11
    //   139: aload 9
    //   141: invokeinterface 290 1 0
    //   146: ifeq +16 -> 162
    //   149: aload_0
    //   150: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   153: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   156: ldc_w 292
    //   159: invokevirtual 146	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   162: aload 9
    //   164: ifnull -63 -> 101
    //   167: aload 9
    //   169: invokeinterface 184 1 0
    //   174: aload 11
    //   176: areturn
    //   177: astore 7
    //   179: aconst_null
    //   180: astore 8
    //   182: aload_0
    //   183: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   186: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   189: ldc_w 294
    //   192: aload_1
    //   193: aload_2
    //   194: aload 7
    //   196: invokevirtual 298	com/google/android/gms/measurement/internal/zzo$zza:zzd	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   199: aload 8
    //   201: ifnull +10 -> 211
    //   204: aload 8
    //   206: invokeinterface 184 1 0
    //   211: aconst_null
    //   212: areturn
    //   213: astore 6
    //   215: aload_3
    //   216: ifnull +9 -> 225
    //   219: aload_3
    //   220: invokeinterface 184 1 0
    //   225: aload 6
    //   227: athrow
    //   228: astore 6
    //   230: aload 9
    //   232: astore_3
    //   233: goto -18 -> 215
    //   236: astore 6
    //   238: aload 8
    //   240: astore_3
    //   241: goto -26 -> 215
    //   244: astore 7
    //   246: aload 9
    //   248: astore 8
    //   250: goto -68 -> 182
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	253	0	this	zzd
    //   0	253	1	paramString1	String
    //   0	253	2	paramString2	String
    //   1	240	3	localObject1	Object
    //   213	13	6	localObject2	Object
    //   228	1	6	localObject3	Object
    //   236	1	6	localObject4	Object
    //   177	18	7	localSQLiteException1	SQLiteException
    //   244	1	7	localSQLiteException2	SQLiteException
    //   180	69	8	localCursor1	Cursor
    //   70	177	9	localCursor2	Cursor
    //   79	3	10	bool	boolean
    //   99	76	11	localzzh	zzh
    // Exception table:
    //   from	to	target	type
    //   20	72	177	android/database/sqlite/SQLiteException
    //   20	72	213	finally
    //   72	81	228	finally
    //   104	162	228	finally
    //   182	199	236	finally
    //   72	81	244	android/database/sqlite/SQLiteException
    //   104	162	244	android/database/sqlite/SQLiteException
  }
  
  public final void zzX(String paramString1, String paramString2)
  {
    zzx.zzcG(paramString1);
    zzx.zzcG(paramString2);
    checkOnWorkerThread();
    zziL();
    try
    {
      int i = getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[] { paramString1, paramString2 });
      zzBh().zzbne.zzm("Deleted user attribute rows:", Integer.valueOf(i));
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      zzBh().zzbmW.zzd("Error deleting user attribute", paramString1, paramString2, localSQLiteException);
    }
  }
  
  public final void zza(zzso.zzd paramzzd)
  {
    checkOnWorkerThread();
    zziL();
    zzx.zzC(paramzzd);
    zzx.zzcG(paramzzd.appId);
    zzx.zzC(paramzzd.zzbpi);
    zzCc();
    long l = getClock().currentTimeMillis();
    if ((paramzzd.zzbpi.longValue() < l - zzc.zzBI()) || (paramzzd.zzbpi.longValue() > l + zzc.zzBI())) {
      zzBh().zzbmZ.zze("Storing bundle outside of the max uploading time span. now, timestamp", Long.valueOf(l), paramzzd.zzbpi);
    }
    try
    {
      byte[] arrayOfByte1 = new byte[paramzzd.getSerializedSize()];
      zzaic localzzaic = zzaic.zzb(arrayOfByte1, 0, arrayOfByte1.length);
      paramzzd.writeTo(localzzaic);
      localzzaic.zzOW();
      byte[] arrayOfByte2 = zzBX().zzg(arrayOfByte1);
      zzBh().zzbne.zzm("Saving bundle, size", Integer.valueOf(arrayOfByte2.length));
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("app_id", paramzzd.appId);
      localContentValues.put("bundle_end_timestamp", paramzzd.zzbpi);
      localContentValues.put("data", arrayOfByte2);
      return;
    }
    catch (IOException localIOException)
    {
      try
      {
        if (getWritableDatabase().insert("queue", null, localContentValues) == -1L) {
          zzBh().zzbmW.zzeB("Failed to insert bundle (got -1)");
        }
        return;
      }
      catch (SQLiteException localSQLiteException)
      {
        zzBh().zzbmW.zzm("Error storing bundle", localSQLiteException);
      }
      localIOException = localIOException;
      zzBh().zzbmW.zzm("Data loss. Failed to serialize bundle", localIOException);
      return;
    }
  }
  
  public final void zza(zza paramzza)
  {
    zzx.zzC(paramzza);
    checkOnWorkerThread();
    zziL();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("app_id", paramzza.zzbkS);
    localContentValues.put("app_instance_id", paramzza.zzblS);
    localContentValues.put("gmp_app_id", paramzza.zzblT);
    localContentValues.put("resettable_device_id_hash", paramzza.zzblU);
    localContentValues.put("last_bundle_index", Long.valueOf(paramzza.zzblV));
    localContentValues.put("last_bundle_end_timestamp", Long.valueOf(paramzza.zzblW));
    localContentValues.put("app_version", paramzza.mAppVersion);
    localContentValues.put("app_store", paramzza.zzblX);
    localContentValues.put("gmp_version", Long.valueOf(paramzza.zzblY));
    localContentValues.put("dev_cert_hash", Long.valueOf(paramzza.zzblZ));
    localContentValues.put("measurement_enabled", Boolean.valueOf(paramzza.zzbma));
    try
    {
      if (getWritableDatabase().insertWithOnConflict("apps", null, localContentValues, 5) == -1L) {
        zzBh().zzbmW.zzeB("Failed to insert/update app (got -1)");
      }
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      zzBh().zzbmW.zzm("Error storing app", localSQLiteException);
    }
  }
  
  public final void zza(zzac paramzzac)
  {
    zzx.zzC(paramzzac);
    checkOnWorkerThread();
    zziL();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("app_id", paramzzac.zzbkS);
    localContentValues.put("name", paramzzac.mName);
    localContentValues.put("set_timestamp", Long.valueOf(paramzzac.zzboR));
    Object localObject = paramzzac.zzMb;
    zzx.zzcG("value");
    zzx.zzC(localObject);
    if ((localObject instanceof String)) {
      localContentValues.put("value", (String)localObject);
    }
    for (;;)
    {
      try
      {
        if (getWritableDatabase().insertWithOnConflict("user_attributes", null, localContentValues, 5) == -1L) {
          zzBh().zzbmW.zzeB("Failed to insert/update user attribute (got -1)");
        }
        return;
      }
      catch (SQLiteException localSQLiteException)
      {
        zzBh().zzbmW.zzm("Error storing user attribute", localSQLiteException);
      }
      if ((localObject instanceof Long)) {
        localContentValues.put("value", (Long)localObject);
      } else if ((localObject instanceof Float)) {
        localContentValues.put("value", (Float)localObject);
      } else {
        throw new IllegalArgumentException("Invalid value type");
      }
    }
  }
  
  public final void zza(zzh paramzzh)
  {
    zzx.zzC(paramzzh);
    checkOnWorkerThread();
    zziL();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("app_id", paramzzh.zzbkS);
    localContentValues.put("name", paramzzh.mName);
    localContentValues.put("lifetime_count", Long.valueOf(paramzzh.zzbmt));
    localContentValues.put("current_bundle_count", Long.valueOf(paramzzh.zzbmu));
    localContentValues.put("last_fire_timestamp", Long.valueOf(paramzzh.zzbmv));
    try
    {
      if (getWritableDatabase().insertWithOnConflict("events", null, localContentValues, 5) == -1L) {
        zzBh().zzbmW.zzeB("Failed to insert/update event aggregates (got -1)");
      }
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      zzBh().zzbmW.zzm("Error storing event aggregates", localSQLiteException);
    }
  }
  
  /* Error */
  final long zza$6e791f8(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: invokespecial 154	com/google/android/gms/measurement/internal/zzd:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore_3
    //   7: aload_3
    //   8: aload_1
    //   9: aconst_null
    //   10: invokevirtual 172	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   13: astore 6
    //   15: aload 6
    //   17: invokeinterface 177 1 0
    //   22: ifeq +28 -> 50
    //   25: aload 6
    //   27: iconst_0
    //   28: invokeinterface 284 2 0
    //   33: lstore 7
    //   35: aload 6
    //   37: ifnull +10 -> 47
    //   40: aload 6
    //   42: invokeinterface 184 1 0
    //   47: lload 7
    //   49: lreturn
    //   50: aload 6
    //   52: ifnull +10 -> 62
    //   55: aload 6
    //   57: invokeinterface 184 1 0
    //   62: lconst_0
    //   63: lreturn
    //   64: astore 5
    //   66: aload_0
    //   67: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   70: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   73: ldc_w 518
    //   76: aload_1
    //   77: aload 5
    //   79: invokevirtual 339	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   82: aload 5
    //   84: athrow
    //   85: astore 4
    //   87: aload_2
    //   88: ifnull +9 -> 97
    //   91: aload_2
    //   92: invokeinterface 184 1 0
    //   97: aload 4
    //   99: athrow
    //   100: astore 4
    //   102: aload 6
    //   104: astore_2
    //   105: goto -18 -> 87
    //   108: astore 5
    //   110: aload 6
    //   112: astore_2
    //   113: goto -47 -> 66
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	zzd
    //   0	116	1	paramString	String
    //   1	112	2	localObject1	Object
    //   6	2	3	localSQLiteDatabase	SQLiteDatabase
    //   85	13	4	localObject2	Object
    //   100	1	4	localObject3	Object
    //   64	19	5	localSQLiteException1	SQLiteException
    //   108	1	5	localSQLiteException2	SQLiteException
    //   13	98	6	localCursor	Cursor
    //   33	15	7	l	long
    // Exception table:
    //   from	to	target	type
    //   7	15	64	android/database/sqlite/SQLiteException
    //   7	15	85	finally
    //   66	85	85	finally
    //   15	35	100	finally
    //   15	35	108	android/database/sqlite/SQLiteException
  }
  
  public final void zzaj(long paramLong)
  {
    checkOnWorkerThread();
    zziL();
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    if (localSQLiteDatabase.delete("queue", "rowid=?", arrayOfString) != 1) {
      zzBh().zzbmW.zzeB("Deleted fewer rows from queue than expected");
    }
  }
  
  /* Error */
  public final java.util.List<zzac> zzex(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 264	com/google/android/gms/common/internal/zzx:zzcG	(Ljava/lang/String;)Ljava/lang/String;
    //   4: pop
    //   5: aload_0
    //   6: invokevirtual 83	com/google/android/gms/measurement/internal/zzd:checkOnWorkerThread	()V
    //   9: aload_0
    //   10: invokevirtual 153	com/google/android/gms/measurement/internal/zzd:zziL	()V
    //   13: new 527	java/util/ArrayList
    //   16: dup
    //   17: invokespecial 528	java/util/ArrayList:<init>	()V
    //   20: astore_3
    //   21: aload_0
    //   22: invokespecial 154	com/google/android/gms/measurement/internal/zzd:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   25: astore 7
    //   27: iconst_3
    //   28: anewarray 228	java/lang/String
    //   31: dup
    //   32: iconst_0
    //   33: ldc_w 469
    //   36: aastore
    //   37: dup
    //   38: iconst_1
    //   39: ldc_w 474
    //   42: aastore
    //   43: dup
    //   44: iconst_2
    //   45: ldc_w 483
    //   48: aastore
    //   49: astore 8
    //   51: iconst_1
    //   52: anewarray 228	java/lang/String
    //   55: dup
    //   56: iconst_0
    //   57: aload_1
    //   58: aastore
    //   59: astore 9
    //   61: invokestatic 531	com/google/android/gms/measurement/internal/zzc:zzBC	()I
    //   64: pop
    //   65: aload 7
    //   67: ldc_w 302
    //   70: aload 8
    //   72: ldc_w 533
    //   75: aload 9
    //   77: aconst_null
    //   78: aconst_null
    //   79: ldc_w 535
    //   82: ldc_w 537
    //   85: invokevirtual 540	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   88: astore 11
    //   90: aload 11
    //   92: astore 5
    //   94: aload 5
    //   96: invokeinterface 177 1 0
    //   101: istore 12
    //   103: iload 12
    //   105: ifne +17 -> 122
    //   108: aload 5
    //   110: ifnull +10 -> 120
    //   113: aload 5
    //   115: invokeinterface 184 1 0
    //   120: aload_3
    //   121: areturn
    //   122: aload 5
    //   124: iconst_0
    //   125: invokeinterface 181 2 0
    //   130: astore 13
    //   132: aload 5
    //   134: iconst_1
    //   135: invokeinterface 284 2 0
    //   140: lstore 14
    //   142: getstatic 546	android/os/Build$VERSION:SDK_INT	I
    //   145: bipush 11
    //   147: if_icmplt +114 -> 261
    //   150: aload 5
    //   152: iconst_2
    //   153: invokeinterface 550 2 0
    //   158: istore 18
    //   160: goto +377 -> 537
    //   163: aload_0
    //   164: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   167: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   170: ldc_w 552
    //   173: iload 18
    //   175: invokestatic 256	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   178: invokevirtual 103	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
    //   181: aconst_null
    //   182: astore 19
    //   184: aload 19
    //   186: ifnonnull +262 -> 448
    //   189: aload_0
    //   190: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   193: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   196: ldc_w 554
    //   199: invokevirtual 146	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   202: aload 5
    //   204: invokeinterface 290 1 0
    //   209: ifne -87 -> 122
    //   212: aload_3
    //   213: invokeinterface 559 1 0
    //   218: invokestatic 531	com/google/android/gms/measurement/internal/zzc:zzBC	()I
    //   221: if_icmple +26 -> 247
    //   224: aload_0
    //   225: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   228: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   231: ldc_w 561
    //   234: invokevirtual 146	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   237: aload_3
    //   238: invokestatic 531	com/google/android/gms/measurement/internal/zzc:zzBC	()I
    //   241: invokeinterface 565 2 0
    //   246: pop
    //   247: aload 5
    //   249: ifnull +10 -> 259
    //   252: aload 5
    //   254: invokeinterface 184 1 0
    //   259: aload_3
    //   260: areturn
    //   261: aload 5
    //   263: checkcast 567	android/database/sqlite/SQLiteCursor
    //   266: invokevirtual 571	android/database/sqlite/SQLiteCursor:getWindow	()Landroid/database/CursorWindow;
    //   269: astore 16
    //   271: aload 5
    //   273: invokeinterface 574 1 0
    //   278: istore 17
    //   280: aload 16
    //   282: iload 17
    //   284: iconst_2
    //   285: invokevirtual 580	android/database/CursorWindow:isNull	(II)Z
    //   288: ifeq +9 -> 297
    //   291: iconst_0
    //   292: istore 18
    //   294: goto +243 -> 537
    //   297: aload 16
    //   299: iload 17
    //   301: iconst_2
    //   302: invokevirtual 583	android/database/CursorWindow:isLong	(II)Z
    //   305: ifeq +9 -> 314
    //   308: iconst_1
    //   309: istore 18
    //   311: goto +226 -> 537
    //   314: aload 16
    //   316: iload 17
    //   318: iconst_2
    //   319: invokevirtual 586	android/database/CursorWindow:isFloat	(II)Z
    //   322: ifeq +9 -> 331
    //   325: iconst_2
    //   326: istore 18
    //   328: goto +209 -> 537
    //   331: aload 16
    //   333: iload 17
    //   335: iconst_2
    //   336: invokevirtual 589	android/database/CursorWindow:isString	(II)Z
    //   339: ifeq +9 -> 348
    //   342: iconst_3
    //   343: istore 18
    //   345: goto +192 -> 537
    //   348: aload 16
    //   350: iload 17
    //   352: iconst_2
    //   353: invokevirtual 592	android/database/CursorWindow:isBlob	(II)Z
    //   356: ifeq +216 -> 572
    //   359: iconst_4
    //   360: istore 18
    //   362: goto +175 -> 537
    //   365: aload_0
    //   366: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   369: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   372: ldc_w 594
    //   375: invokevirtual 146	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   378: aconst_null
    //   379: astore 19
    //   381: goto -197 -> 184
    //   384: aload 5
    //   386: iconst_2
    //   387: invokeinterface 284 2 0
    //   392: invokestatic 335	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   395: astore 19
    //   397: goto -213 -> 184
    //   400: aload 5
    //   402: iconst_2
    //   403: invokeinterface 598 2 0
    //   408: invokestatic 601	java/lang/Float:valueOf	(F)Ljava/lang/Float;
    //   411: astore 19
    //   413: goto -229 -> 184
    //   416: aload 5
    //   418: iconst_2
    //   419: invokeinterface 181 2 0
    //   424: astore 19
    //   426: goto -242 -> 184
    //   429: aload_0
    //   430: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   433: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   436: ldc_w 603
    //   439: invokevirtual 146	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   442: aconst_null
    //   443: astore 19
    //   445: goto -261 -> 184
    //   448: aload_3
    //   449: new 466	com/google/android/gms/measurement/internal/zzac
    //   452: dup
    //   453: aload_1
    //   454: aload 13
    //   456: lload 14
    //   458: aload 19
    //   460: invokespecial 606	com/google/android/gms/measurement/internal/zzac:<init>	(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V
    //   463: invokeinterface 610 2 0
    //   468: pop
    //   469: goto -267 -> 202
    //   472: astore 4
    //   474: aload_0
    //   475: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   478: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   481: ldc_w 612
    //   484: aload_1
    //   485: aload 4
    //   487: invokevirtual 339	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   490: aload 5
    //   492: ifnull +10 -> 502
    //   495: aload 5
    //   497: invokeinterface 184 1 0
    //   502: aconst_null
    //   503: areturn
    //   504: astore 6
    //   506: aconst_null
    //   507: astore 5
    //   509: aload 5
    //   511: ifnull +10 -> 521
    //   514: aload 5
    //   516: invokeinterface 184 1 0
    //   521: aload 6
    //   523: athrow
    //   524: astore 6
    //   526: goto -17 -> 509
    //   529: astore 4
    //   531: aconst_null
    //   532: astore 5
    //   534: goto -60 -> 474
    //   537: iload 18
    //   539: tableswitch	default:+-376 -> 163, 0:+-174->365, 1:+-155->384, 2:+-139->400, 3:+-123->416, 4:+-110->429
    //   573: istore 18
    //   575: goto -38 -> 537
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	578	0	this	zzd
    //   0	578	1	paramString	String
    //   20	429	3	localArrayList	java.util.ArrayList
    //   472	14	4	localSQLiteException1	SQLiteException
    //   529	1	4	localSQLiteException2	SQLiteException
    //   92	441	5	localCursor1	Cursor
    //   504	18	6	localObject1	Object
    //   524	1	6	localObject2	Object
    //   25	41	7	localSQLiteDatabase	SQLiteDatabase
    //   49	22	8	arrayOfString1	String[]
    //   59	17	9	arrayOfString2	String[]
    //   88	3	11	localCursor2	Cursor
    //   101	3	12	bool	boolean
    //   130	325	13	str	String
    //   140	317	14	l	long
    //   269	80	16	localCursorWindow	android.database.CursorWindow
    //   278	73	17	i	int
    //   158	416	18	j	int
    //   182	277	19	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   94	103	472	android/database/sqlite/SQLiteException
    //   122	160	472	android/database/sqlite/SQLiteException
    //   163	181	472	android/database/sqlite/SQLiteException
    //   189	202	472	android/database/sqlite/SQLiteException
    //   202	247	472	android/database/sqlite/SQLiteException
    //   261	291	472	android/database/sqlite/SQLiteException
    //   297	308	472	android/database/sqlite/SQLiteException
    //   314	325	472	android/database/sqlite/SQLiteException
    //   331	342	472	android/database/sqlite/SQLiteException
    //   348	359	472	android/database/sqlite/SQLiteException
    //   365	378	472	android/database/sqlite/SQLiteException
    //   384	397	472	android/database/sqlite/SQLiteException
    //   400	413	472	android/database/sqlite/SQLiteException
    //   416	426	472	android/database/sqlite/SQLiteException
    //   429	442	472	android/database/sqlite/SQLiteException
    //   448	469	472	android/database/sqlite/SQLiteException
    //   21	90	504	finally
    //   94	103	524	finally
    //   122	160	524	finally
    //   163	181	524	finally
    //   189	202	524	finally
    //   202	247	524	finally
    //   261	291	524	finally
    //   297	308	524	finally
    //   314	325	524	finally
    //   331	342	524	finally
    //   348	359	524	finally
    //   365	378	524	finally
    //   384	397	524	finally
    //   400	413	524	finally
    //   416	426	524	finally
    //   429	442	524	finally
    //   448	469	524	finally
    //   474	490	524	finally
    //   21	90	529	android/database/sqlite/SQLiteException
  }
  
  /* Error */
  public final zza zzey(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 264	com/google/android/gms/common/internal/zzx:zzcG	(Ljava/lang/String;)Ljava/lang/String;
    //   4: pop
    //   5: aload_0
    //   6: invokevirtual 83	com/google/android/gms/measurement/internal/zzd:checkOnWorkerThread	()V
    //   9: aload_0
    //   10: invokevirtual 153	com/google/android/gms/measurement/internal/zzd:zziL	()V
    //   13: aconst_null
    //   14: astore_3
    //   15: aload_0
    //   16: invokespecial 154	com/google/android/gms/measurement/internal/zzd:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   19: ldc_w 455
    //   22: bipush 10
    //   24: anewarray 228	java/lang/String
    //   27: dup
    //   28: iconst_0
    //   29: ldc_w 405
    //   32: aastore
    //   33: dup
    //   34: iconst_1
    //   35: ldc_w 410
    //   38: aastore
    //   39: dup
    //   40: iconst_2
    //   41: ldc_w 415
    //   44: aastore
    //   45: dup
    //   46: iconst_3
    //   47: ldc_w 420
    //   50: aastore
    //   51: dup
    //   52: iconst_4
    //   53: ldc_w 426
    //   56: aastore
    //   57: dup
    //   58: iconst_5
    //   59: ldc 23
    //   61: aastore
    //   62: dup
    //   63: bipush 6
    //   65: ldc 33
    //   67: aastore
    //   68: dup
    //   69: bipush 7
    //   71: ldc 37
    //   73: aastore
    //   74: dup
    //   75: bipush 8
    //   77: ldc 41
    //   79: aastore
    //   80: dup
    //   81: bipush 9
    //   83: ldc 45
    //   85: aastore
    //   86: ldc_w 533
    //   89: iconst_1
    //   90: anewarray 228	java/lang/String
    //   93: dup
    //   94: iconst_0
    //   95: aload_1
    //   96: aastore
    //   97: aconst_null
    //   98: aconst_null
    //   99: aconst_null
    //   100: invokevirtual 278	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   103: astore 7
    //   105: aload 7
    //   107: invokeinterface 177 1 0
    //   112: istore 8
    //   114: iload 8
    //   116: ifne +21 -> 137
    //   119: aload 7
    //   121: ifnull +10 -> 131
    //   124: aload 7
    //   126: invokeinterface 184 1 0
    //   131: aconst_null
    //   132: astore 24
    //   134: aload 24
    //   136: areturn
    //   137: aload 7
    //   139: iconst_0
    //   140: invokeinterface 181 2 0
    //   145: astore 9
    //   147: aload 7
    //   149: iconst_1
    //   150: invokeinterface 181 2 0
    //   155: astore 10
    //   157: aload 7
    //   159: iconst_2
    //   160: invokeinterface 181 2 0
    //   165: astore 11
    //   167: aload 7
    //   169: iconst_3
    //   170: invokeinterface 284 2 0
    //   175: lstore 12
    //   177: aload 7
    //   179: iconst_4
    //   180: invokeinterface 284 2 0
    //   185: lstore 14
    //   187: aload 7
    //   189: iconst_5
    //   190: invokeinterface 181 2 0
    //   195: astore 16
    //   197: aload 7
    //   199: bipush 6
    //   201: invokeinterface 181 2 0
    //   206: astore 17
    //   208: aload 7
    //   210: bipush 7
    //   212: invokeinterface 284 2 0
    //   217: lstore 18
    //   219: aload 7
    //   221: bipush 8
    //   223: invokeinterface 284 2 0
    //   228: lstore 20
    //   230: aload 7
    //   232: bipush 9
    //   234: invokeinterface 617 2 0
    //   239: ifeq +82 -> 321
    //   242: iconst_1
    //   243: istore 22
    //   245: iload 22
    //   247: ifeq +92 -> 339
    //   250: iconst_1
    //   251: istore 23
    //   253: new 400	com/google/android/gms/measurement/internal/zza
    //   256: dup
    //   257: aload_1
    //   258: aload 9
    //   260: aload 10
    //   262: aload 11
    //   264: lload 12
    //   266: lload 14
    //   268: aload 16
    //   270: aload 17
    //   272: lload 18
    //   274: lload 20
    //   276: iload 23
    //   278: invokespecial 620	com/google/android/gms/measurement/internal/zza:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;Ljava/lang/String;JJZ)V
    //   281: astore 24
    //   283: aload 7
    //   285: invokeinterface 290 1 0
    //   290: ifeq +16 -> 306
    //   293: aload_0
    //   294: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   297: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   300: ldc_w 622
    //   303: invokevirtual 146	com/google/android/gms/measurement/internal/zzo$zza:zzeB	(Ljava/lang/String;)V
    //   306: aload 7
    //   308: ifnull -174 -> 134
    //   311: aload 7
    //   313: invokeinterface 184 1 0
    //   318: aload 24
    //   320: areturn
    //   321: aload 7
    //   323: bipush 9
    //   325: invokeinterface 625 2 0
    //   330: istore 25
    //   332: iload 25
    //   334: istore 22
    //   336: goto -91 -> 245
    //   339: iconst_0
    //   340: istore 23
    //   342: goto -89 -> 253
    //   345: astore 5
    //   347: aconst_null
    //   348: astore 6
    //   350: aload_0
    //   351: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   354: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   357: ldc_w 627
    //   360: aload_1
    //   361: aload 5
    //   363: invokevirtual 339	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   366: aload 6
    //   368: ifnull +10 -> 378
    //   371: aload 6
    //   373: invokeinterface 184 1 0
    //   378: aconst_null
    //   379: areturn
    //   380: astore 4
    //   382: aload_3
    //   383: ifnull +9 -> 392
    //   386: aload_3
    //   387: invokeinterface 184 1 0
    //   392: aload 4
    //   394: athrow
    //   395: astore 4
    //   397: aload 7
    //   399: astore_3
    //   400: goto -18 -> 382
    //   403: astore 4
    //   405: aload 6
    //   407: astore_3
    //   408: goto -26 -> 382
    //   411: astore 5
    //   413: aload 7
    //   415: astore 6
    //   417: goto -67 -> 350
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	420	0	this	zzd
    //   0	420	1	paramString	String
    //   14	394	3	localObject1	Object
    //   380	13	4	localObject2	Object
    //   395	1	4	localObject3	Object
    //   403	1	4	localObject4	Object
    //   345	17	5	localSQLiteException1	SQLiteException
    //   411	1	5	localSQLiteException2	SQLiteException
    //   348	68	6	localCursor1	Cursor
    //   103	311	7	localCursor2	Cursor
    //   112	3	8	bool1	boolean
    //   145	114	9	str1	String
    //   155	106	10	str2	String
    //   165	98	11	str3	String
    //   175	90	12	l1	long
    //   185	82	14	l2	long
    //   195	74	16	str4	String
    //   206	65	17	str5	String
    //   217	56	18	l3	long
    //   228	47	20	l4	long
    //   243	92	22	i	int
    //   251	90	23	bool2	boolean
    //   132	187	24	localzza	zza
    //   330	3	25	j	int
    // Exception table:
    //   from	to	target	type
    //   15	105	345	android/database/sqlite/SQLiteException
    //   15	105	380	finally
    //   105	114	395	finally
    //   137	242	395	finally
    //   253	306	395	finally
    //   321	332	395	finally
    //   350	366	403	finally
    //   105	114	411	android/database/sqlite/SQLiteException
    //   137	242	411	android/database/sqlite/SQLiteException
    //   253	306	411	android/database/sqlite/SQLiteException
    //   321	332	411	android/database/sqlite/SQLiteException
  }
  
  /* Error */
  public final java.util.List<android.util.Pair<zzso.zzd, Long>> zzf(String paramString, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore 4
    //   3: aload_0
    //   4: invokevirtual 83	com/google/android/gms/measurement/internal/zzd:checkOnWorkerThread	()V
    //   7: aload_0
    //   8: invokevirtual 153	com/google/android/gms/measurement/internal/zzd:zziL	()V
    //   11: iload_2
    //   12: ifle +111 -> 123
    //   15: iload 4
    //   17: istore 5
    //   19: iload 5
    //   21: invokestatic 633	com/google/android/gms/common/internal/zzx:zzab	(Z)V
    //   24: iload_3
    //   25: ifle +104 -> 129
    //   28: iload 4
    //   30: invokestatic 633	com/google/android/gms/common/internal/zzx:zzab	(Z)V
    //   33: aload_1
    //   34: invokestatic 264	com/google/android/gms/common/internal/zzx:zzcG	(Ljava/lang/String;)Ljava/lang/String;
    //   37: pop
    //   38: aload_0
    //   39: invokespecial 154	com/google/android/gms/measurement/internal/zzd:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   42: ldc 240
    //   44: iconst_2
    //   45: anewarray 228	java/lang/String
    //   48: dup
    //   49: iconst_0
    //   50: ldc_w 535
    //   53: aastore
    //   54: dup
    //   55: iconst_1
    //   56: ldc_w 382
    //   59: aastore
    //   60: ldc_w 533
    //   63: iconst_1
    //   64: anewarray 228	java/lang/String
    //   67: dup
    //   68: iconst_0
    //   69: aload_1
    //   70: aastore
    //   71: aconst_null
    //   72: aconst_null
    //   73: ldc_w 535
    //   76: iload_2
    //   77: invokestatic 635	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   80: invokevirtual 540	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   83: astore 13
    //   85: aload 13
    //   87: astore 8
    //   89: aload 8
    //   91: invokeinterface 177 1 0
    //   96: ifne +39 -> 135
    //   99: invokestatic 641	java/util/Collections:emptyList	()Ljava/util/List;
    //   102: astore 27
    //   104: aload 27
    //   106: astore 12
    //   108: aload 8
    //   110: ifnull +10 -> 120
    //   113: aload 8
    //   115: invokeinterface 184 1 0
    //   120: aload 12
    //   122: areturn
    //   123: iconst_0
    //   124: istore 5
    //   126: goto -107 -> 19
    //   129: iconst_0
    //   130: istore 4
    //   132: goto -104 -> 28
    //   135: new 527	java/util/ArrayList
    //   138: dup
    //   139: invokespecial 528	java/util/ArrayList:<init>	()V
    //   142: astore 12
    //   144: iconst_0
    //   145: istore 14
    //   147: aload 8
    //   149: iconst_0
    //   150: invokeinterface 284 2 0
    //   155: lstore 15
    //   157: aload 8
    //   159: iconst_1
    //   160: invokeinterface 645 2 0
    //   165: astore 20
    //   167: aload_0
    //   168: invokevirtual 359	com/google/android/gms/measurement/internal/zzd:zzBX	()Lcom/google/android/gms/measurement/internal/zzae;
    //   171: aload 20
    //   173: invokevirtual 648	com/google/android/gms/measurement/internal/zzae:zzA	([B)[B
    //   176: astore 21
    //   178: aload 12
    //   180: invokeinterface 651 1 0
    //   185: ifne +13 -> 198
    //   188: iload 14
    //   190: aload 21
    //   192: arraylength
    //   193: iadd
    //   194: iload_3
    //   195: if_icmpgt +77 -> 272
    //   198: aload 21
    //   200: iconst_0
    //   201: aload 21
    //   203: arraylength
    //   204: invokestatic 656	com/google/android/gms/internal/zzaib:zza	([BII)Lcom/google/android/gms/internal/zzaib;
    //   207: astore 22
    //   209: new 315	com/google/android/gms/internal/zzso$zzd
    //   212: dup
    //   213: invokespecial 657	com/google/android/gms/internal/zzso$zzd:<init>	()V
    //   216: astore 23
    //   218: aload 23
    //   220: aload 22
    //   222: invokevirtual 661	com/google/android/gms/internal/zzso$zzd:zzY	(Lcom/google/android/gms/internal/zzaib;)Lcom/google/android/gms/internal/zzso$zzd;
    //   225: pop
    //   226: iload 14
    //   228: aload 21
    //   230: arraylength
    //   231: iadd
    //   232: istore 18
    //   234: aload 12
    //   236: aload 23
    //   238: lload 15
    //   240: invokestatic 335	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   243: invokestatic 667	android/util/Pair:create	(Ljava/lang/Object;Ljava/lang/Object;)Landroid/util/Pair;
    //   246: invokeinterface 610 2 0
    //   251: pop
    //   252: aload 8
    //   254: invokeinterface 290 1 0
    //   259: istore 19
    //   261: iload 19
    //   263: ifeq +9 -> 272
    //   266: iload 18
    //   268: iload_3
    //   269: if_icmple +156 -> 425
    //   272: aload 8
    //   274: ifnull -154 -> 120
    //   277: aload 8
    //   279: invokeinterface 184 1 0
    //   284: aload 12
    //   286: areturn
    //   287: astore 17
    //   289: aload_0
    //   290: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   293: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   296: ldc_w 669
    //   299: aload_1
    //   300: aload 17
    //   302: invokevirtual 339	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   305: iload 14
    //   307: istore 18
    //   309: goto -57 -> 252
    //   312: astore 24
    //   314: aload_0
    //   315: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   318: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   321: ldc_w 671
    //   324: aload_1
    //   325: aload 24
    //   327: invokevirtual 339	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   330: iload 14
    //   332: istore 18
    //   334: goto -82 -> 252
    //   337: astore 9
    //   339: aconst_null
    //   340: astore 10
    //   342: aload_0
    //   343: invokevirtual 89	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   346: getfield 187	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   349: ldc_w 673
    //   352: aload_1
    //   353: aload 9
    //   355: invokevirtual 339	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   358: invokestatic 641	java/util/Collections:emptyList	()Ljava/util/List;
    //   361: astore 11
    //   363: aload 11
    //   365: astore 12
    //   367: aload 10
    //   369: ifnull -249 -> 120
    //   372: aload 10
    //   374: invokeinterface 184 1 0
    //   379: aload 12
    //   381: areturn
    //   382: astore 7
    //   384: aconst_null
    //   385: astore 8
    //   387: aload 8
    //   389: ifnull +10 -> 399
    //   392: aload 8
    //   394: invokeinterface 184 1 0
    //   399: aload 7
    //   401: athrow
    //   402: astore 7
    //   404: goto -17 -> 387
    //   407: astore 7
    //   409: aload 10
    //   411: astore 8
    //   413: goto -26 -> 387
    //   416: astore 9
    //   418: aload 8
    //   420: astore 10
    //   422: goto -80 -> 342
    //   425: iload 18
    //   427: istore 14
    //   429: goto -282 -> 147
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	432	0	this	zzd
    //   0	432	1	paramString	String
    //   0	432	2	paramInt1	int
    //   0	432	3	paramInt2	int
    //   1	130	4	bool1	boolean
    //   17	108	5	bool2	boolean
    //   382	18	7	localObject1	Object
    //   402	1	7	localObject2	Object
    //   407	1	7	localObject3	Object
    //   87	332	8	localObject4	Object
    //   337	17	9	localSQLiteException1	SQLiteException
    //   416	1	9	localSQLiteException2	SQLiteException
    //   340	81	10	localObject5	Object
    //   361	3	11	localList1	java.util.List
    //   106	274	12	localObject6	Object
    //   83	3	13	localCursor	Cursor
    //   145	283	14	i	int
    //   155	84	15	l	long
    //   287	14	17	localIOException1	IOException
    //   232	194	18	j	int
    //   259	3	19	bool3	boolean
    //   165	7	20	arrayOfByte1	byte[]
    //   176	53	21	arrayOfByte2	byte[]
    //   207	14	22	localzzaib	com.google.android.gms.internal.zzaib
    //   216	21	23	localzzd	zzso.zzd
    //   312	14	24	localIOException2	IOException
    //   102	3	27	localList2	java.util.List
    // Exception table:
    //   from	to	target	type
    //   157	178	287	java/io/IOException
    //   218	226	312	java/io/IOException
    //   38	85	337	android/database/sqlite/SQLiteException
    //   38	85	382	finally
    //   89	104	402	finally
    //   135	144	402	finally
    //   147	157	402	finally
    //   157	178	402	finally
    //   178	198	402	finally
    //   198	218	402	finally
    //   218	226	402	finally
    //   226	252	402	finally
    //   252	261	402	finally
    //   289	305	402	finally
    //   314	330	402	finally
    //   342	363	407	finally
    //   89	104	416	android/database/sqlite/SQLiteException
    //   135	144	416	android/database/sqlite/SQLiteException
    //   147	157	416	android/database/sqlite/SQLiteException
    //   157	178	416	android/database/sqlite/SQLiteException
    //   178	198	416	android/database/sqlite/SQLiteException
    //   198	218	416	android/database/sqlite/SQLiteException
    //   218	226	416	android/database/sqlite/SQLiteException
    //   226	252	416	android/database/sqlite/SQLiteException
    //   252	261	416	android/database/sqlite/SQLiteException
    //   289	305	416	android/database/sqlite/SQLiteException
    //   314	330	416	android/database/sqlite/SQLiteException
  }
  
  private final class zza
    extends SQLiteOpenHelper
  {
    zza(Context paramContext, String paramString)
    {
      super(paramString, null, 1);
    }
    
    private void zza(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, Map<String, String> paramMap)
      throws SQLiteException
    {
      if (!zza(paramSQLiteDatabase, paramString1)) {
        paramSQLiteDatabase.execSQL(paramString2);
      }
      Set localSet;
      for (;;)
      {
        int j;
        try
        {
          localSet = zzb(paramSQLiteDatabase, paramString1);
          String[] arrayOfString = paramString3.split(",");
          int i = arrayOfString.length;
          j = 0;
          if (j >= i) {
            break;
          }
          String str = arrayOfString[j];
          if (!localSet.remove(str)) {
            throw new SQLiteException("Database " + paramString1 + " is missing required column: " + str);
          }
        }
        catch (SQLiteException localSQLiteException)
        {
          zzd.this.zzBh().zzbmW.zzm("Failed to verify columns on table that was just created", paramString1);
          throw localSQLiteException;
        }
        j++;
      }
      if (paramMap != null)
      {
        Iterator localIterator = paramMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          if (!localSet.remove(localEntry.getKey())) {
            paramSQLiteDatabase.execSQL((String)localEntry.getValue());
          }
        }
      }
      if (!localSet.isEmpty()) {
        throw new SQLiteException("Database " + paramString1 + " table has extra columns");
      }
    }
    
    /* Error */
    private boolean zza(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: aload_1
      //   3: ldc 116
      //   5: iconst_1
      //   6: anewarray 34	java/lang/String
      //   9: dup
      //   10: iconst_0
      //   11: ldc 118
      //   13: aastore
      //   14: ldc 120
      //   16: iconst_1
      //   17: anewarray 34	java/lang/String
      //   20: dup
      //   21: iconst_0
      //   22: aload_2
      //   23: aastore
      //   24: aconst_null
      //   25: aconst_null
      //   26: aconst_null
      //   27: invokevirtual 124	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   30: astore 7
      //   32: aload 7
      //   34: astore 6
      //   36: aload 6
      //   38: invokeinterface 129 1 0
      //   43: istore 8
      //   45: aload 6
      //   47: ifnull +10 -> 57
      //   50: aload 6
      //   52: invokeinterface 133 1 0
      //   57: iload 8
      //   59: ireturn
      //   60: astore 5
      //   62: aconst_null
      //   63: astore 6
      //   65: aload_0
      //   66: getfield 10	com/google/android/gms/measurement/internal/zzd$zza:zzbml	Lcom/google/android/gms/measurement/internal/zzd;
      //   69: invokevirtual 67	com/google/android/gms/measurement/internal/zzd:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
      //   72: getfield 136	com/google/android/gms/measurement/internal/zzo:zzbmZ	Lcom/google/android/gms/measurement/internal/zzo$zza;
      //   75: ldc 138
      //   77: aload_2
      //   78: aload 5
      //   80: invokevirtual 142	com/google/android/gms/measurement/internal/zzo$zza:zze	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
      //   83: aload 6
      //   85: ifnull +10 -> 95
      //   88: aload 6
      //   90: invokeinterface 133 1 0
      //   95: iconst_0
      //   96: ireturn
      //   97: astore 4
      //   99: aload_3
      //   100: ifnull +9 -> 109
      //   103: aload_3
      //   104: invokeinterface 133 1 0
      //   109: aload 4
      //   111: athrow
      //   112: astore 4
      //   114: aload 6
      //   116: astore_3
      //   117: goto -18 -> 99
      //   120: astore 5
      //   122: goto -57 -> 65
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	125	0	this	zza
      //   0	125	1	paramSQLiteDatabase	SQLiteDatabase
      //   0	125	2	paramString	String
      //   1	116	3	localObject1	Object
      //   97	13	4	localObject2	Object
      //   112	1	4	localObject3	Object
      //   60	19	5	localSQLiteException1	SQLiteException
      //   120	1	5	localSQLiteException2	SQLiteException
      //   34	81	6	localCursor1	Cursor
      //   30	3	7	localCursor2	Cursor
      //   43	15	8	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   2	32	60	android/database/sqlite/SQLiteException
      //   2	32	97	finally
      //   36	45	112	finally
      //   65	83	112	finally
      //   36	45	120	android/database/sqlite/SQLiteException
    }
    
    private static Set<String> zzb(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
      HashSet localHashSet = new HashSet();
      Cursor localCursor = paramSQLiteDatabase.rawQuery("SELECT * FROM " + paramString + " LIMIT 0", null);
      try
      {
        Collections.addAll(localHashSet, localCursor.getColumnNames());
        return localHashSet;
      }
      finally
      {
        localCursor.close();
      }
    }
    
    public final SQLiteDatabase getWritableDatabase()
    {
      int i = 1;
      zzaa localzzaa = zzd.zza(zzd.this);
      zzc.zzBD();
      if (localzzaa.zzBV == 0L) {}
      while (i == 0)
      {
        throw new SQLiteException("Database open failed");
        if (localzzaa.zzri.elapsedRealtime() - localzzaa.zzBV < 3600000L) {
          i = 0;
        }
      }
      try
      {
        SQLiteDatabase localSQLiteDatabase2 = super.getWritableDatabase();
        return localSQLiteDatabase2;
      }
      catch (SQLiteException localSQLiteException1)
      {
        zzd.zza(zzd.this).start();
        zzd.this.zzBh().zzbmW.zzeB("Opening the database failed, dropping and recreating it");
        String str = zzd.zzb(zzd.this);
        zzd.this.getContext().getDatabasePath(str).delete();
        try
        {
          SQLiteDatabase localSQLiteDatabase1 = super.getWritableDatabase();
          zzd.zza(zzd.this).zzBV = 0L;
          return localSQLiteDatabase1;
        }
        catch (SQLiteException localSQLiteException2)
        {
          zzd.this.zzBh().zzbmW.zzm("Failed to open freshly created database", localSQLiteException2);
          throw localSQLiteException2;
        }
      }
    }
    
    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      if (Build.VERSION.SDK_INT >= 9)
      {
        File localFile = new File(paramSQLiteDatabase.getPath());
        localFile.setReadable(false, false);
        localFile.setWritable(false, false);
        localFile.setReadable(true, true);
        localFile.setWritable(true, true);
      }
    }
    
    public final void onOpen(SQLiteDatabase paramSQLiteDatabase)
    {
      Cursor localCursor;
      if (Build.VERSION.SDK_INT < 15) {
        localCursor = paramSQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
      }
      try
      {
        localCursor.moveToFirst();
        localCursor.close();
        zza(paramSQLiteDatabase, "events", "CREATE TABLE IF NOT EXISTS events ( app_id TEXT NOT NULL, name TEXT NOT NULL, lifetime_count INTEGER NOT NULL, current_bundle_count INTEGER NOT NULL, last_fire_timestamp INTEGER NOT NULL, PRIMARY KEY (app_id, name)) ;", "app_id,name,lifetime_count,current_bundle_count,last_fire_timestamp", null);
        zza(paramSQLiteDatabase, "user_attributes", "CREATE TABLE IF NOT EXISTS user_attributes ( app_id TEXT NOT NULL, name TEXT NOT NULL, set_timestamp INTEGER NOT NULL, value BLOB NOT NULL, PRIMARY KEY (app_id, name)) ;", "app_id,name,set_timestamp,value", null);
        zza(paramSQLiteDatabase, "apps", "CREATE TABLE IF NOT EXISTS apps ( app_id TEXT NOT NULL, app_instance_id TEXT, gmp_app_id TEXT, resettable_device_id_hash TEXT, last_bundle_index INTEGER NOT NULL, last_bundle_end_timestamp INTEGER NOT NULL, PRIMARY KEY (app_id)) ;", "app_id,app_instance_id,gmp_app_id,resettable_device_id_hash,last_bundle_index,last_bundle_end_timestamp", zzd.zzCg());
        zza(paramSQLiteDatabase, "queue", "CREATE TABLE IF NOT EXISTS queue ( app_id TEXT NOT NULL, bundle_end_timestamp INTEGER NOT NULL, data BLOB NOT NULL);", "app_id,bundle_end_timestamp,data", null);
        return;
      }
      finally
      {
        localCursor.close();
      }
    }
    
    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzd
 * JD-Core Version:    0.7.0.1
 */