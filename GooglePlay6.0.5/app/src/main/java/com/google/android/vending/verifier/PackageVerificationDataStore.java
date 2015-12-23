package com.google.android.vending.verifier;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.google.android.finsky.utils.Utils;
import java.util.HashMap;
import java.util.Map;

public final class PackageVerificationDataStore
{
  private static final String[] FULL_PROJECTION = { "package_name", "cache_fingerprint", "sha256_digest", "length", "forward_locked", "suppress_user_warning" };
  private SQLiteDatabase mDb;
  private final Helper mHelper;
  
  public PackageVerificationDataStore(Context paramContext)
  {
    this.mHelper = new Helper(paramContext);
  }
  
  private void close()
  {
    Utils.ensureNotOnMainThread();
    this.mDb.close();
  }
  
  private static PackageVerificationData packageVerificationDataFromCursor(Cursor paramCursor)
  {
    String str = paramCursor.getString(0);
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    long l1 = paramCursor.getLong(1);
    byte[] arrayOfByte = paramCursor.getBlob(2);
    long l2 = paramCursor.getLong(3);
    boolean bool1;
    if (paramCursor.getInt(4) == 1)
    {
      bool1 = true;
      if (paramCursor.getInt(5) != 1) {
        break label95;
      }
    }
    label95:
    for (boolean bool2 = true;; bool2 = false)
    {
      return new PackageVerificationData(str, l1, arrayOfByte, l2, bool1, bool2);
      bool1 = false;
      break;
    }
  }
  
  private void reopen()
  {
    Utils.ensureNotOnMainThread();
    this.mDb = this.mHelper.getWritableDatabase();
  }
  
  /* Error */
  public final PackageVerificationData get(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 91	com/google/android/vending/verifier/PackageVerificationDataStore:reopen	()V
    //   6: aload_0
    //   7: getfield 47	com/google/android/vending/verifier/PackageVerificationDataStore:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   10: ldc 93
    //   12: getstatic 28	com/google/android/vending/verifier/PackageVerificationDataStore:FULL_PROJECTION	[Ljava/lang/String;
    //   15: ldc 95
    //   17: iconst_1
    //   18: anewarray 14	java/lang/String
    //   21: dup
    //   22: iconst_0
    //   23: aload_1
    //   24: aastore
    //   25: aconst_null
    //   26: aconst_null
    //   27: aconst_null
    //   28: invokevirtual 99	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   31: astore_3
    //   32: aload_3
    //   33: invokeinterface 103 1 0
    //   38: istore 5
    //   40: iload 5
    //   42: iconst_1
    //   43: if_icmpeq +21 -> 64
    //   46: aload_3
    //   47: invokeinterface 104 1 0
    //   52: aload_0
    //   53: invokespecial 105	com/google/android/vending/verifier/PackageVerificationDataStore:close	()V
    //   56: aconst_null
    //   57: astore 8
    //   59: aload_0
    //   60: monitorexit
    //   61: aload 8
    //   63: areturn
    //   64: aload_3
    //   65: invokeinterface 109 1 0
    //   70: pop
    //   71: aload_3
    //   72: invokestatic 111	com/google/android/vending/verifier/PackageVerificationDataStore:packageVerificationDataFromCursor	(Landroid/database/Cursor;)Lcom/google/android/vending/verifier/PackageVerificationData;
    //   75: astore 7
    //   77: aload 7
    //   79: astore 8
    //   81: aload_3
    //   82: invokeinterface 104 1 0
    //   87: aload_0
    //   88: invokespecial 105	com/google/android/vending/verifier/PackageVerificationDataStore:close	()V
    //   91: goto -32 -> 59
    //   94: astore_2
    //   95: aload_0
    //   96: monitorexit
    //   97: aload_2
    //   98: athrow
    //   99: astore 4
    //   101: aload_3
    //   102: invokeinterface 104 1 0
    //   107: aload_0
    //   108: invokespecial 105	com/google/android/vending/verifier/PackageVerificationDataStore:close	()V
    //   111: aload 4
    //   113: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	114	0	this	PackageVerificationDataStore
    //   0	114	1	paramString	String
    //   94	4	2	localObject1	Object
    //   31	71	3	localCursor	Cursor
    //   99	13	4	localObject2	Object
    //   38	6	5	i	int
    //   75	3	7	localPackageVerificationData	PackageVerificationData
    //   57	23	8	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   2	32	94	finally
    //   46	56	94	finally
    //   81	91	94	finally
    //   101	114	94	finally
    //   32	40	99	finally
    //   64	77	99	finally
  }
  
  public final Map<String, PackageVerificationData> getAll()
  {
    HashMap localHashMap;
    try
    {
      reopen();
      Cursor localCursor = this.mDb.query("verification_cache", FULL_PROJECTION, null, null, null, null, null);
      localHashMap = new HashMap();
      while (localCursor.moveToNext())
      {
        PackageVerificationData localPackageVerificationData = packageVerificationDataFromCursor(localCursor);
        localHashMap.put(localPackageVerificationData.mPackageName, localPackageVerificationData);
      }
      localCursor.close();
    }
    finally {}
    close();
    return localHashMap;
  }
  
  /* Error */
  public final void put(PackageVerificationData paramPackageVerificationData)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: invokespecial 91	com/google/android/vending/verifier/PackageVerificationDataStore:reopen	()V
    //   8: new 129	android/content/ContentValues
    //   11: dup
    //   12: invokespecial 130	android/content/ContentValues:<init>	()V
    //   15: astore 4
    //   17: aload 4
    //   19: ldc 16
    //   21: aload_1
    //   22: getfield 120	com/google/android/vending/verifier/PackageVerificationData:mPackageName	Ljava/lang/String;
    //   25: invokevirtual 133	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   28: aload 4
    //   30: ldc 18
    //   32: aload_1
    //   33: getfield 137	com/google/android/vending/verifier/PackageVerificationData:mCacheFingerprint	J
    //   36: invokestatic 143	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   39: invokevirtual 146	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   42: aload 4
    //   44: ldc 20
    //   46: aload_1
    //   47: getfield 150	com/google/android/vending/verifier/PackageVerificationData:mSha256Digest	[B
    //   50: invokevirtual 153	android/content/ContentValues:put	(Ljava/lang/String;[B)V
    //   53: aload 4
    //   55: ldc 22
    //   57: aload_1
    //   58: getfield 156	com/google/android/vending/verifier/PackageVerificationData:mApkLength	J
    //   61: invokestatic 143	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   64: invokevirtual 146	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   67: aload_1
    //   68: getfield 160	com/google/android/vending/verifier/PackageVerificationData:mForwardLocked	Z
    //   71: ifeq +56 -> 127
    //   74: iload_2
    //   75: istore 5
    //   77: aload 4
    //   79: ldc 24
    //   81: iload 5
    //   83: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   86: invokevirtual 168	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   89: aload_1
    //   90: getfield 171	com/google/android/vending/verifier/PackageVerificationData:mSuppressUserWarning	Z
    //   93: ifeq +40 -> 133
    //   96: aload 4
    //   98: ldc 26
    //   100: iload_2
    //   101: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   104: invokevirtual 168	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   107: aload_0
    //   108: getfield 47	com/google/android/vending/verifier/PackageVerificationDataStore:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   111: ldc 93
    //   113: aconst_null
    //   114: aload 4
    //   116: invokevirtual 175	android/database/sqlite/SQLiteDatabase:replace	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   119: pop2
    //   120: aload_0
    //   121: invokespecial 105	com/google/android/vending/verifier/PackageVerificationDataStore:close	()V
    //   124: aload_0
    //   125: monitorexit
    //   126: return
    //   127: iconst_0
    //   128: istore 5
    //   130: goto -53 -> 77
    //   133: iconst_0
    //   134: istore_2
    //   135: goto -39 -> 96
    //   138: astore_3
    //   139: aload_0
    //   140: monitorexit
    //   141: aload_3
    //   142: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	143	0	this	PackageVerificationDataStore
    //   0	143	1	paramPackageVerificationData	PackageVerificationData
    //   1	134	2	i	int
    //   138	4	3	localObject	Object
    //   15	100	4	localContentValues	android.content.ContentValues
    //   75	54	5	j	int
    // Exception table:
    //   from	to	target	type
    //   4	74	138	finally
    //   77	96	138	finally
    //   96	124	138	finally
  }
  
  public final void remove(String paramString)
  {
    try
    {
      reopen();
      this.mDb.delete("verification_cache", "package_name=?", new String[] { paramString });
      close();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final void setSuppressUserWarning(String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: invokespecial 91	com/google/android/vending/verifier/PackageVerificationDataStore:reopen	()V
    //   8: new 129	android/content/ContentValues
    //   11: dup
    //   12: invokespecial 130	android/content/ContentValues:<init>	()V
    //   15: astore 5
    //   17: iload_2
    //   18: ifeq +43 -> 61
    //   21: aload 5
    //   23: ldc 26
    //   25: iload_3
    //   26: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   29: invokevirtual 168	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   32: aload_0
    //   33: getfield 47	com/google/android/vending/verifier/PackageVerificationDataStore:mDb	Landroid/database/sqlite/SQLiteDatabase;
    //   36: ldc 93
    //   38: aload 5
    //   40: ldc 95
    //   42: iconst_1
    //   43: anewarray 14	java/lang/String
    //   46: dup
    //   47: iconst_0
    //   48: aload_1
    //   49: aastore
    //   50: invokevirtual 187	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   53: pop
    //   54: aload_0
    //   55: invokespecial 105	com/google/android/vending/verifier/PackageVerificationDataStore:close	()V
    //   58: aload_0
    //   59: monitorexit
    //   60: return
    //   61: iconst_0
    //   62: istore_3
    //   63: goto -42 -> 21
    //   66: astore 4
    //   68: aload_0
    //   69: monitorexit
    //   70: aload 4
    //   72: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	73	0	this	PackageVerificationDataStore
    //   0	73	1	paramString	String
    //   0	73	2	paramBoolean	boolean
    //   1	62	3	i	int
    //   66	5	4	localObject	Object
    //   15	24	5	localContentValues	android.content.ContentValues
    // Exception table:
    //   from	to	target	type
    //   4	17	66	finally
    //   21	58	66	finally
  }
  
  private final class Helper
    extends SQLiteOpenHelper
  {
    public Helper(Context paramContext)
    {
      super("package_verification.db", null, 1);
    }
    
    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE verification_cache (package_name STRING PRIMARY KEY, cache_fingerprint INTEGER, sha256_digest BLOB, length INTEGER, forward_locked INTEGER, suppress_user_warning INTEGER);");
    }
    
    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      try
      {
        paramSQLiteDatabase.execSQL("DROP TABLE verification_cache");
        label6:
        onCreate(paramSQLiteDatabase);
        return;
      }
      catch (SQLException localSQLException)
      {
        break label6;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageVerificationDataStore
 * JD-Core Version:    0.7.0.1
 */