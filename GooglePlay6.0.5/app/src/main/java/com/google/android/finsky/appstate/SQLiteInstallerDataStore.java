package com.google.android.finsky.appstate;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.finsky.protos.AndroidAppDeliveryData;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.Collection;

public final class SQLiteInstallerDataStore
  implements InstallerDataStore
{
  private static final String[] EMPTY_NODES_RESULT = new String[0];
  private static final String[] FULL_PROJECTION = { "package_name", "auto_update", "desired_version", "download_uri", "delivery_data", "delivery_data_timestamp_ms", "installer_state", "first_download_ms", "referrer", "account", "title", "flags", "continue_url", "last_notified_version", "last_update_timestamp_ms", "account_for_update", "auto_acquire_tags", "external_referrer_timestamp_ms", "persistent_flags", "permissions_version", "delivery_token", "completed_split_ids", "active_split_id", "request_id" };
  private SQLiteDatabase mDb;
  private final Helper mHelper;
  
  public SQLiteInstallerDataStore(Context paramContext, String paramString)
  {
    this.mHelper = new Helper(paramContext, paramString);
  }
  
  private void close()
  {
    Utils.ensureNotOnMainThread();
    this.mDb.close();
  }
  
  public static void deleteDatabaseNode(Context paramContext, String paramString)
  {
    paramContext.deleteDatabase(getDatabaseName(paramString));
  }
  
  public static String getDatabaseName(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return "localappstate.db";
    }
    return "node-appstate-" + Uri.encode(paramString) + ".db";
  }
  
  public static String[] getDatabaseNodes(Context paramContext)
  {
    String[] arrayOfString = paramContext.databaseList();
    ArrayList localArrayList = null;
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfString[j];
      if ((str.startsWith("node-appstate-")) && (str.endsWith(".db")))
      {
        if (localArrayList == null) {
          localArrayList = new ArrayList();
        }
        localArrayList.add(Uri.decode(str.substring(14, -3 + str.length())));
      }
    }
    if (localArrayList == null) {
      return EMPTY_NODES_RESULT;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  private static InstallerDataStore.InstallerData localAppStateFromCursor(Cursor paramCursor)
  {
    String str1 = paramCursor.getString(0);
    int i;
    if (paramCursor.isNull(1)) {
      i = 0;
    }
    for (;;)
    {
      int j;
      label32:
      int k;
      AndroidAppDeliveryData localAndroidAppDeliveryData;
      if (paramCursor.isNull(2))
      {
        j = -1;
        if (!paramCursor.isNull(13)) {
          break label523;
        }
        k = -1;
        boolean bool1 = paramCursor.isNull(4);
        localAndroidAppDeliveryData = null;
        if (bool1) {}
      }
      try
      {
        byte[] arrayOfByte = paramCursor.getBlob(4);
        localAndroidAppDeliveryData = (AndroidAppDeliveryData)MessageNano.mergeFrom$1ec43da(new AndroidAppDeliveryData(), arrayOfByte, arrayOfByte.length);
        long l1 = paramCursor.getLong(5);
        int m = paramCursor.getInt(6);
        String str2 = paramCursor.getString(3);
        long l2 = paramCursor.getLong(7);
        String str3 = paramCursor.getString(8);
        String str4 = paramCursor.getString(9);
        String str5 = paramCursor.getString(10);
        int n = paramCursor.getInt(11);
        String str6 = paramCursor.getString(12);
        long l3 = paramCursor.getLong(14);
        String str7 = paramCursor.getString(15);
        boolean bool2 = paramCursor.isNull(16);
        String str8 = null;
        if (!bool2) {
          str8 = paramCursor.getString(16);
        }
        String[] arrayOfString = Utils.commaUnpackStrings(str8);
        long l4 = paramCursor.getLong(17);
        int i1 = paramCursor.getInt(18);
        int i2 = paramCursor.getInt(19);
        String str9 = paramCursor.getString(20);
        boolean bool3 = paramCursor.isNull(21);
        String str10 = null;
        if (!bool3) {
          str10 = paramCursor.getString(21);
        }
        InstallerDataStore.InstallerData localInstallerData = new InstallerDataStore.InstallerData(str1, i, j, k, localAndroidAppDeliveryData, l1, m, str2, l2, str3, str6, str4, str5, n, l3, str7, arrayOfString, l4, i1, i2, str9, Utils.commaUnpackStrings(str10), paramCursor.getString(22), paramCursor.getString(23));
        i3 = localInstallerData.flags;
        i4 = localInstallerData.persistentFlags;
        int i5 = localInstallerData.permissionsVersion;
        if ((i3 & 0x40) != 0)
        {
          int i9 = i3 & 0xFFFFFFBF;
          int i10 = i4 | 0x1;
          i6 = i9;
          i8 = 1;
          i7 = i10;
          if ((0x100 & i6) != 0)
          {
            i8 = 1;
            i6 = 0xFFFFFEFF & i6;
            i5 = 1;
          }
          if (i8 != 0)
          {
            InstallerDataStore.InstallerData.Builder localBuilder = new InstallerDataStore.InstallerData.Builder(localInstallerData.packageName);
            localBuilder.setFlags(i6);
            localBuilder.setPersistentFlags(i7);
            localBuilder.setPermissionsVersion(i5);
            localInstallerData = localBuilder.mInstance;
          }
          return localInstallerData;
          i = paramCursor.getInt(1);
          continue;
          j = paramCursor.getInt(2);
          break label32;
          label523:
          k = paramCursor.getInt(13);
        }
      }
      catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
      {
        for (;;)
        {
          int i3;
          int i4;
          FinskyLog.w("Couldn't parse blob as AndroidAppDeliveryData", new Object[] { localInvalidProtocolBufferNanoException });
          localAndroidAppDeliveryData = null;
          continue;
          int i6 = i3;
          int i7 = i4;
          int i8 = 0;
        }
      }
    }
  }
  
  private void reopen()
  {
    Utils.ensureNotOnMainThread();
    this.mDb = this.mHelper.getWritableDatabase();
  }
  
  /* Error */
  public final InstallerDataStore.InstallerData get(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 262	com/google/android/finsky/appstate/SQLiteInstallerDataStore:reopen	()V
    //   6: aload_0
    //   7: getfield 88	com/google/android/finsky/appstate/SQLiteInstallerDataStore:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   10: ldc_w 264
    //   13: getstatic 67	com/google/android/finsky/appstate/SQLiteInstallerDataStore:FULL_PROJECTION	[Ljava/lang/String;
    //   16: ldc_w 266
    //   19: iconst_1
    //   20: anewarray 17	java/lang/String
    //   23: dup
    //   24: iconst_0
    //   25: aload_1
    //   26: aastore
    //   27: aconst_null
    //   28: aconst_null
    //   29: aconst_null
    //   30: invokevirtual 270	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   33: astore_3
    //   34: aload_3
    //   35: invokeinterface 273 1 0
    //   40: istore 5
    //   42: iload 5
    //   44: iconst_1
    //   45: if_icmpeq +21 -> 66
    //   48: aload_3
    //   49: invokeinterface 274 1 0
    //   54: aload_0
    //   55: invokespecial 275	com/google/android/finsky/appstate/SQLiteInstallerDataStore:close	()V
    //   58: aconst_null
    //   59: astore 8
    //   61: aload_0
    //   62: monitorexit
    //   63: aload 8
    //   65: areturn
    //   66: aload_3
    //   67: invokeinterface 279 1 0
    //   72: pop
    //   73: aload_3
    //   74: invokestatic 281	com/google/android/finsky/appstate/SQLiteInstallerDataStore:localAppStateFromCursor	(Landroid/database/Cursor;)Lcom/google/android/finsky/appstate/InstallerDataStore$InstallerData;
    //   77: astore 7
    //   79: aload 7
    //   81: astore 8
    //   83: aload_3
    //   84: invokeinterface 274 1 0
    //   89: aload_0
    //   90: invokespecial 275	com/google/android/finsky/appstate/SQLiteInstallerDataStore:close	()V
    //   93: goto -32 -> 61
    //   96: astore_2
    //   97: aload_0
    //   98: monitorexit
    //   99: aload_2
    //   100: athrow
    //   101: astore 4
    //   103: aload_3
    //   104: invokeinterface 274 1 0
    //   109: aload_0
    //   110: invokespecial 275	com/google/android/finsky/appstate/SQLiteInstallerDataStore:close	()V
    //   113: aload 4
    //   115: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	SQLiteInstallerDataStore
    //   0	116	1	paramString	String
    //   96	4	2	localObject1	Object
    //   33	71	3	localCursor	Cursor
    //   101	13	4	localObject2	Object
    //   40	6	5	i	int
    //   77	3	7	localInstallerData	InstallerDataStore.InstallerData
    //   59	23	8	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   2	34	96	finally
    //   48	58	96	finally
    //   83	93	96	finally
    //   103	116	96	finally
    //   34	42	101	finally
    //   66	79	101	finally
  }
  
  public final Collection<InstallerDataStore.InstallerData> getAll()
  {
    Cursor localCursor;
    ArrayList localArrayList;
    try
    {
      reopen();
      localCursor = this.mDb.query("appstate", FULL_PROJECTION, null, null, null, null, null);
      localArrayList = new ArrayList(localCursor.getCount());
    }
    finally
    {
      try
      {
        while (localCursor.moveToNext())
        {
          localArrayList.add(localAppStateFromCursor(localCursor));
          continue;
          throw localObject1;
        }
      }
      finally
      {
        localCursor.close();
        close();
      }
    }
    localCursor.close();
    close();
    return localArrayList;
  }
  
  /* Error */
  public final void put(InstallerDataStore.InstallerData paramInstallerData)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 262	com/google/android/finsky/appstate/SQLiteInstallerDataStore:reopen	()V
    //   6: new 293	android/content/ContentValues
    //   9: dup
    //   10: invokespecial 294	android/content/ContentValues:<init>	()V
    //   13: astore_3
    //   14: aload_3
    //   15: ldc 19
    //   17: aload_1
    //   18: getfield 230	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:packageName	Ljava/lang/String;
    //   21: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   24: aload_3
    //   25: ldc 21
    //   27: aload_1
    //   28: getfield 300	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:autoUpdate	I
    //   31: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   34: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   37: aload_3
    //   38: ldc 23
    //   40: aload_1
    //   41: getfield 312	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:desiredVersion	I
    //   44: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   47: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   50: aload_3
    //   51: ldc 25
    //   53: aload_1
    //   54: getfield 315	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:downloadUri	Ljava/lang/String;
    //   57: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   60: aload_1
    //   61: getfield 319	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:deliveryData	Lcom/google/android/finsky/protos/AndroidAppDeliveryData;
    //   64: ifnull +259 -> 323
    //   67: aload_3
    //   68: ldc 27
    //   70: aload_1
    //   71: getfield 319	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:deliveryData	Lcom/google/android/finsky/protos/AndroidAppDeliveryData;
    //   74: invokestatic 323	com/google/protobuf/nano/MessageNano:toByteArray	(Lcom/google/protobuf/nano/MessageNano;)[B
    //   77: invokevirtual 326	android/content/ContentValues:put	(Ljava/lang/String;[B)V
    //   80: aload_3
    //   81: ldc 29
    //   83: aload_1
    //   84: getfield 330	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:deliveryDataTimestampMs	J
    //   87: invokestatic 335	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   90: invokevirtual 338	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   93: aload_3
    //   94: ldc 31
    //   96: aload_1
    //   97: getfield 341	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:installerState	I
    //   100: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   103: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   106: aload_3
    //   107: ldc 33
    //   109: aload_1
    //   110: getfield 344	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:firstDownloadMs	J
    //   113: invokestatic 335	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   116: invokevirtual 338	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   119: aload_3
    //   120: ldc 35
    //   122: aload_1
    //   123: getfield 347	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:externalReferrer	Ljava/lang/String;
    //   126: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   129: aload_3
    //   130: ldc 37
    //   132: aload_1
    //   133: getfield 350	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:accountName	Ljava/lang/String;
    //   136: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   139: aload_3
    //   140: ldc 39
    //   142: aload_1
    //   143: getfield 352	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:title	Ljava/lang/String;
    //   146: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   149: aload_3
    //   150: ldc 41
    //   152: aload_1
    //   153: getfield 218	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:flags	I
    //   156: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   159: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   162: aload_3
    //   163: ldc 43
    //   165: aload_1
    //   166: getfield 355	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:continueUrl	Ljava/lang/String;
    //   169: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   172: aload_3
    //   173: ldc 45
    //   175: aload_1
    //   176: getfield 358	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:lastNotifiedVersion	I
    //   179: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   182: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   185: aload_3
    //   186: ldc 47
    //   188: aload_1
    //   189: getfield 361	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:lastUpdateTimestampMs	J
    //   192: invokestatic 335	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   195: invokevirtual 338	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   198: aload_3
    //   199: ldc 49
    //   201: aload_1
    //   202: getfield 364	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:accountForUpdate	Ljava/lang/String;
    //   205: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   208: aload_3
    //   209: ldc 51
    //   211: aload_1
    //   212: invokevirtual 367	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:getAutoAcquireTags	()[Ljava/lang/String;
    //   215: invokestatic 371	com/google/android/finsky/utils/Utils:commaPackStrings	([Ljava/lang/String;)Ljava/lang/String;
    //   218: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   221: aload_3
    //   222: ldc 53
    //   224: aload_1
    //   225: getfield 374	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:externalReferrerTimestampMs	J
    //   228: invokestatic 335	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   231: invokevirtual 338	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   234: aload_3
    //   235: ldc 55
    //   237: aload_1
    //   238: getfield 221	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:persistentFlags	I
    //   241: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   244: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   247: aload_3
    //   248: ldc 57
    //   250: aload_1
    //   251: getfield 224	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:permissionsVersion	I
    //   254: invokestatic 306	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   257: invokevirtual 309	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   260: aload_3
    //   261: ldc 59
    //   263: aload_1
    //   264: getfield 377	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:deliveryToken	Ljava/lang/String;
    //   267: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   270: aload_3
    //   271: ldc 61
    //   273: aload_1
    //   274: invokevirtual 380	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:getCompletedSplitIds	()[Ljava/lang/String;
    //   277: invokestatic 371	com/google/android/finsky/utils/Utils:commaPackStrings	([Ljava/lang/String;)Ljava/lang/String;
    //   280: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   283: aload_3
    //   284: ldc 63
    //   286: aload_1
    //   287: getfield 383	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:activeSplitId	Ljava/lang/String;
    //   290: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   293: aload_3
    //   294: ldc 65
    //   296: aload_1
    //   297: getfield 386	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:requestId	Ljava/lang/String;
    //   300: invokevirtual 297	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   303: aload_0
    //   304: getfield 88	com/google/android/finsky/appstate/SQLiteInstallerDataStore:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   307: ldc_w 264
    //   310: aconst_null
    //   311: aload_3
    //   312: invokevirtual 390	android/database/sqlite/SQLiteDatabase:replace	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   315: pop2
    //   316: aload_0
    //   317: invokespecial 275	com/google/android/finsky/appstate/SQLiteInstallerDataStore:close	()V
    //   320: aload_0
    //   321: monitorexit
    //   322: return
    //   323: aload_3
    //   324: ldc 27
    //   326: invokevirtual 393	android/content/ContentValues:putNull	(Ljava/lang/String;)V
    //   329: goto -249 -> 80
    //   332: astore_2
    //   333: aload_0
    //   334: monitorexit
    //   335: aload_2
    //   336: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	337	0	this	SQLiteInstallerDataStore
    //   0	337	1	paramInstallerData	InstallerDataStore.InstallerData
    //   332	4	2	localObject	Object
    //   13	311	3	localContentValues	android.content.ContentValues
    // Exception table:
    //   from	to	target	type
    //   2	80	332	finally
    //   80	320	332	finally
    //   323	329	332	finally
  }
  
  public final void setAccountForUpdate(String paramString1, String paramString2)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString1), paramString1).setAccountForUpdate(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setActiveSplitId(String paramString1, String paramString2)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString1), paramString1).setActiveSplitId(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAutoAcquireTags(String paramString, String[] paramArrayOfString)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setAutoAcquireTags(paramArrayOfString).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setAutoUpdate(String paramString, int paramInt)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setAutoUpdate(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setCompletedSplitIds(String paramString, String[] paramArrayOfString)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setCompletedSplitIds(paramArrayOfString).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setContinueUrl(String paramString1, String paramString2)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString1), paramString1).setContinueUrl(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setDeliveryData(String paramString, AndroidAppDeliveryData paramAndroidAppDeliveryData, long paramLong)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setDeliveryData(paramAndroidAppDeliveryData, paramLong).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setDeliveryToken(String paramString1, String paramString2)
  {
    put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString1), paramString1).setDeliveryToken(paramString2).mInstance);
  }
  
  public final void setDesiredVersion(String paramString, int paramInt)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setDesiredVersion(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setExternalReferrer(String paramString1, String paramString2)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString1), paramString1).setExternalReferrer(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setExternalReferrerTimestampMs(String paramString, long paramLong)
  {
    put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setExternalReferrerTimestampMs(paramLong).mInstance);
  }
  
  public final void setFirstDownloadMs(String paramString, long paramLong)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setFirstDownloadMs(paramLong).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setFlags(String paramString, int paramInt)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setFlags(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setInstallerState(String paramString1, int paramInt, String paramString2)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString1), paramString1).setInstallerState(paramInt).setDownloadUri(paramString2).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setLastNotifiedVersion(String paramString, int paramInt)
  {
    try
    {
      put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setLastNotifiedVersion(paramInt).mInstance);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setLastUpdateTimestampMs(String paramString, long paramLong)
  {
    put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setLastUpdateTimestampMs(paramLong).mInstance);
  }
  
  public final void setPermissionsVersion(String paramString, int paramInt)
  {
    put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setPermissionsVersion(paramInt).mInstance);
  }
  
  public final void setPersistentFlags(String paramString, int paramInt)
  {
    put(InstallerDataStore.InstallerData.Builder.buildUpon(get(paramString), paramString).setPersistentFlags(paramInt).mInstance);
  }
  
  private final class Helper
    extends SQLiteOpenHelper
  {
    public Helper(Context paramContext, String paramString)
    {
      super(SQLiteInstallerDataStore.getDatabaseName(paramString), null, 18);
    }
    
    private void recreateDatabase(SQLiteDatabase paramSQLiteDatabase)
    {
      try
      {
        paramSQLiteDatabase.execSQL("DROP TABLE appstate");
        onCreate(paramSQLiteDatabase);
        return;
      }
      catch (SQLException localSQLException)
      {
        for (;;)
        {
          FinskyLog.w("Unable to drop table: %s", new Object[] { localSQLException });
        }
      }
    }
    
    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE appstate (package_name STRING, auto_update INTEGER, desired_version INTEGER, download_uri STRING, delivery_data BLOB, delivery_data_timestamp_ms INTEGER,installer_state INTEGER, first_download_ms INTEGER, referrer STRING, account STRING, title STRING,flags INTEGER, continue_url STRING, last_notified_version INTEGER, last_update_timestamp_ms INTEGER, account_for_update STRING, auto_acquire_tags STRING, external_referrer_timestamp_ms INTEGER, persistent_flags INTEGER, permissions_version INTEGER, delivery_token STRING,completed_split_ids STRING, active_split_id STRING, request_id STRING, PRIMARY KEY (package_name))");
    }
    
    public final void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      FinskyLog.d("Downgrading InstallerDataStore from %d to %d", arrayOfObject);
      recreateDatabase(paramSQLiteDatabase);
    }
    
    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      switch (paramInt1)
      {
      default: 
        recreateDatabase(paramSQLiteDatabase);
        return;
      case 7: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN continue_url STRING");
      case 8: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN delivery_data_timestamp_ms INTEGER");
      case 9: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN last_notified_version INTEGER");
      case 10: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN last_update_timestamp_ms INTEGER");
      case 11: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN account_for_update STRING");
      case 12: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN auto_acquire_tags STRING");
      case 13: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN external_referrer_timestamp_ms INTEGER");
      case 14: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN persistent_flags INTEGER");
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN permissions_version INTEGER");
      case 15: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN delivery_token STRING");
      case 16: 
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN completed_split_ids STRING");
        paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN active_split_id STRING");
      }
      paramSQLiteDatabase.execSQL("ALTER TABLE appstate ADD COLUMN request_id STRING");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.SQLiteInstallerDataStore
 * JD-Core Version:    0.7.0.1
 */