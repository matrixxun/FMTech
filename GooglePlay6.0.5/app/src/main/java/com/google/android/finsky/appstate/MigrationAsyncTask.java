package com.google.android.finsky.appstate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.FinskyLog;
import java.io.File;
import java.util.Map;

public final class MigrationAsyncTask
  extends AsyncTask<Void, Void, Map<String, Integer>>
{
  private final Boolean FINSKY_AUTOUPDATE_IS_STRINGS = Boolean.valueOf(true);
  private final String FINSKY_COLUMN_AUTO_UPDATE = "AUTO_UPDATE";
  private final String FINSKY_COLUMN_PACKAGE_NAME = "PACKAGE";
  private final String FINSKY_DATABASE_NAME = "market_assets.db";
  private final String FINSKY_TABLE_NAME = "assets";
  private final Boolean GRANOLA_AUTOUPDATE_IS_STRINGS = Boolean.valueOf(false);
  private final String GRANOLA_COLUMN_AUTO_UPDATE = "auto_update";
  private final String GRANOLA_COLUMN_PACKAGE_NAME = "package_name";
  private final String GRANOLA_DATABASE_NAME = "assets14.db";
  private final String GRANOLA_TABLE_NAME = "assets10";
  private final AppStates mAppStates;
  private final Context mContext;
  
  public MigrationAsyncTask(Context paramContext)
  {
    this.mContext = paramContext;
    this.mAppStates = FinskyApp.get().mAppStates;
  }
  
  private void collectLegacyData(Map<String, Integer> paramMap, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    File localFile = this.mContext.getDatabasePath(paramString1);
    if (!localFile.exists()) {
      return;
    }
    for (;;)
    {
      SQLiteDatabase localSQLiteDatabase;
      Cursor localCursor;
      String str1;
      try
      {
        localSQLiteDatabase = SQLiteDatabase.openDatabase(localFile.getAbsolutePath(), null, 1);
        FinskyLog.d("Reading from legacy database %s", new Object[] { paramString1 });
        localCursor = localSQLiteDatabase.query(paramString2, new String[] { paramString3, paramString4 }, null, null, null, null, null);
        if (localCursor == null) {
          break label252;
        }
        String str2;
        try
        {
          if (!localCursor.moveToNext()) {
            break label245;
          }
          str1 = localCursor.getString(0);
          if (TextUtils.isEmpty(str1)) {
            continue;
          }
          if (!paramBoolean) {
            break label216;
          }
          str2 = localCursor.getString(1);
          if (!"DISABLED".equals(str2)) {
            break label193;
          }
          i = 1;
        }
        finally
        {
          localCursor.close();
        }
        paramMap.put(str1, Integer.valueOf(2));
        continue;
        Object[] arrayOfObject;
        bool = "ENABLED".equals(str2);
      }
      catch (SQLiteException localSQLiteException)
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString1;
        arrayOfObject[1] = localSQLiteException.toString();
        FinskyLog.e("Unable to open %s because %s", arrayOfObject);
        return;
      }
      label193:
      boolean bool;
      int i = 0;
      if (bool)
      {
        i = 2;
        break label258;
        label216:
        i = localCursor.getInt(1);
        break label258;
        paramMap.put(str1, Integer.valueOf(1));
        continue;
        label245:
        localCursor.close();
        label252:
        localSQLiteDatabase.close();
        return;
      }
      label258:
      switch (i)
      {
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.MigrationAsyncTask
 * JD-Core Version:    0.7.0.1
 */