package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzhb;
import java.util.Locale;

@zzhb
public final class zzh
{
  private static final String zzFr = String.format(Locale.US, "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER)", new Object[] { "InAppPurchase", "purchase_id", "product_id", "developer_payload", "record_time" });
  private static zzh zzFt;
  static final Object zzqp = new Object();
  private final zza zzFs;
  
  private zzh(Context paramContext)
  {
    this.zzFs = new zza(paramContext, "google_inapp_purchase.db");
  }
  
  public static zzh zzz(Context paramContext)
  {
    synchronized (zzqp)
    {
      if (zzFt == null) {
        zzFt = new zzh(paramContext);
      }
      zzh localzzh = zzFt;
      return localzzh;
    }
  }
  
  public final int getRecordCount()
  {
    localCursor = null;
    SQLiteDatabase localSQLiteDatabase;
    synchronized (zzqp)
    {
      localSQLiteDatabase = getWritableDatabase();
      if (localSQLiteDatabase == null) {
        return 0;
      }
    }
    try
    {
      localCursor = localSQLiteDatabase.rawQuery("select count(*) from InAppPurchase", null);
      if (localCursor.moveToFirst())
      {
        int i = localCursor.getInt(0);
        if (localCursor != null) {
          localCursor.close();
        }
        return i;
        localObject2 = finally;
        throw localObject2;
      }
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        zzb.w("Error getting record count" + localSQLiteException.getMessage());
        if (localCursor != null) {
          localCursor.close();
        }
      }
    }
    finally
    {
      if (localCursor == null) {
        break label134;
      }
      localCursor.close();
    }
    return 0;
  }
  
  public final SQLiteDatabase getWritableDatabase()
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.zzFs.getWritableDatabase();
      return localSQLiteDatabase;
    }
    catch (SQLiteException localSQLiteException)
    {
      zzb.w("Error opening writable conversion tracking database");
    }
    return null;
  }
  
  public final void zza(zzf paramzzf)
  {
    if (paramzzf == null) {
      return;
    }
    SQLiteDatabase localSQLiteDatabase;
    synchronized (zzqp)
    {
      localSQLiteDatabase = getWritableDatabase();
      if (localSQLiteDatabase == null) {
        return;
      }
    }
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = "purchase_id";
    arrayOfObject[1] = Long.valueOf(paramzzf.zzFl);
    localSQLiteDatabase.delete("InAppPurchase", String.format(localLocale, "%s = %d", arrayOfObject), null);
  }
  
  public final class zza
    extends SQLiteOpenHelper
  {
    public zza(Context paramContext, String paramString)
    {
      super(paramString, null, 4);
    }
    
    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL(zzh.zzfU());
    }
    
    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      zzb.i("Database updated from version " + paramInt1 + " to version " + paramInt2);
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS InAppPurchase");
      onCreate(paramSQLiteDatabase);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.purchase.zzh
 * JD-Core Version:    0.7.0.1
 */