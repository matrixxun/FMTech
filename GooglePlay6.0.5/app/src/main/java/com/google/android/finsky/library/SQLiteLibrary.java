package com.google.android.finsky.library;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import java.util.Collection;
import java.util.Iterator;

public final class SQLiteLibrary
  implements Library
{
  private static final String[] FULL_PROJECTION = { "account", "library_id", "backend", "doc_id", "doc_type", "offer_type", "document_hash", "subs_valid_until_time", "app_certificate_hash", "app_refund_pre_delivery_endtime_ms", "app_refund_post_delivery_window_ms", "subs_auto_renewing", "subs_initiation_time", "subs_trial_until_time", "inapp_purchase_data", "inapp_signature", "preordered", "owned_via_license", "shared_by_me", "sharer_person_doc_id" };
  private Context mContext;
  SQLiteDatabase mDb;
  private SQLiteStatement mQueryContains;
  private SQLiteStatement mQueryRemove;
  private SQLiteStatement mQueryResetAccountLibraryId;
  private SQLiteStatement mQuerySize;
  
  public SQLiteLibrary(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void add(LibraryEntry paramLibraryEntry)
  {
    for (;;)
    {
      ContentValues localContentValues;
      try
      {
        localContentValues = new ContentValues();
        localContentValues.put("account", paramLibraryEntry.mAccountName);
        localContentValues.put("library_id", paramLibraryEntry.mLibraryId);
        localContentValues.put("backend", Integer.valueOf(paramLibraryEntry.mBackendId));
        localContentValues.put("doc_id", paramLibraryEntry.mDocId);
        localContentValues.put("doc_type", Integer.valueOf(paramLibraryEntry.mDocType));
        localContentValues.put("offer_type", Integer.valueOf(paramLibraryEntry.mOfferType));
        localContentValues.put("document_hash", Long.valueOf(paramLibraryEntry.mDocumentHash));
        localContentValues.put("preordered", Boolean.valueOf(paramLibraryEntry.mPreordered));
        localContentValues.put("shared_by_me", Boolean.valueOf(paramLibraryEntry.mSharedByMe));
        localContentValues.put("sharer_person_doc_id", paramLibraryEntry.mSharerPersonDocid);
        if (paramLibraryEntry.mValidUntilTimestampMs != 9223372036854775807L)
        {
          localContentValues.put("subs_valid_until_time", Long.valueOf(paramLibraryEntry.mValidUntilTimestampMs));
          if ((paramLibraryEntry instanceof LibraryAppEntry))
          {
            LibraryAppEntry localLibraryAppEntry = (LibraryAppEntry)paramLibraryEntry;
            localContentValues.put("app_certificate_hash", Utils.commaPackStrings(localLibraryAppEntry.certificateHashes));
            localContentValues.put("app_refund_pre_delivery_endtime_ms", Long.valueOf(localLibraryAppEntry.refundPreDeliveryEndtimeMs));
            localContentValues.put("app_refund_post_delivery_window_ms", Long.valueOf(localLibraryAppEntry.refundPostDeliveryWindowMs));
            localContentValues.put("owned_via_license", Boolean.valueOf(localLibraryAppEntry.isOwnedViaLicense));
            this.mDb.replace("ownership", null, localContentValues);
          }
        }
        else
        {
          localContentValues.putNull("subs_valid_until_time");
          continue;
        }
        if (!(paramLibraryEntry instanceof LibraryInAppSubscriptionEntry)) {
          break label330;
        }
      }
      finally {}
      LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry = (LibraryInAppSubscriptionEntry)paramLibraryEntry;
      localContentValues.put("subs_auto_renewing", Boolean.valueOf(localLibraryInAppSubscriptionEntry.isAutoRenewing));
      localContentValues.put("subs_initiation_time", Long.valueOf(localLibraryInAppSubscriptionEntry.initiationTimestampMs));
      localContentValues.put("subs_trial_until_time", Long.valueOf(localLibraryInAppSubscriptionEntry.trialUntilTimestampMs));
      localContentValues.put("inapp_purchase_data", localLibraryInAppSubscriptionEntry.signedPurchaseData);
      localContentValues.put("inapp_signature", localLibraryInAppSubscriptionEntry.signature);
      continue;
      label330:
      if ((paramLibraryEntry instanceof LibrarySubscriptionEntry))
      {
        LibrarySubscriptionEntry localLibrarySubscriptionEntry = (LibrarySubscriptionEntry)paramLibraryEntry;
        localContentValues.put("subs_auto_renewing", Boolean.valueOf(localLibrarySubscriptionEntry.isAutoRenewing));
        localContentValues.put("subs_initiation_time", Long.valueOf(localLibrarySubscriptionEntry.initiationTimestampMs));
        localContentValues.put("subs_trial_until_time", Long.valueOf(localLibrarySubscriptionEntry.trialUntilTimestampMs));
      }
      else if ((paramLibraryEntry instanceof LibraryInAppEntry))
      {
        LibraryInAppEntry localLibraryInAppEntry = (LibraryInAppEntry)paramLibraryEntry;
        localContentValues.put("inapp_purchase_data", localLibraryInAppEntry.signedPurchaseData);
        localContentValues.put("inapp_signature", localLibraryInAppEntry.signature);
      }
    }
  }
  
  private static void bindPartialStatement(SQLiteStatement paramSQLiteStatement, LibraryEntry paramLibraryEntry)
  {
    paramSQLiteStatement.bindString(1, paramLibraryEntry.mAccountName);
    paramSQLiteStatement.bindString(2, paramLibraryEntry.mLibraryId);
    paramSQLiteStatement.bindLong(3, paramLibraryEntry.mBackendId);
    paramSQLiteStatement.bindString(4, paramLibraryEntry.mDocId);
    paramSQLiteStatement.bindLong(5, paramLibraryEntry.mDocType);
    paramSQLiteStatement.bindLong(6, paramLibraryEntry.mOfferType);
  }
  
  public static int getVersion()
  {
    return 10;
  }
  
  public final void addAll(Collection<? extends LibraryEntry> paramCollection)
  {
    try
    {
      this.mDb.beginTransaction();
      try
      {
        Iterator localIterator = paramCollection.iterator();
        while (localIterator.hasNext())
        {
          add((LibraryEntry)localIterator.next());
          continue;
          localObject1 = finally;
        }
      }
      finally
      {
        this.mDb.endTransaction();
      }
      this.mDb.setTransactionSuccessful();
    }
    finally {}
    this.mDb.endTransaction();
  }
  
  public final void close()
  {
    this.mQueryContains.close();
    this.mQueryRemove.close();
    this.mQueryResetAccountLibraryId.close();
    this.mQuerySize.close();
    this.mDb.close();
  }
  
  /* Error */
  public final boolean contains(LibraryEntry paramLibraryEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 250	com/google/android/finsky/library/SQLiteLibrary:mQueryContains	Landroid/database/sqlite/SQLiteStatement;
    //   6: aload_1
    //   7: invokestatic 263	com/google/android/finsky/library/SQLiteLibrary:bindPartialStatement	(Landroid/database/sqlite/SQLiteStatement;Lcom/google/android/finsky/library/LibraryEntry;)V
    //   10: aload_0
    //   11: getfield 250	com/google/android/finsky/library/SQLiteLibrary:mQueryContains	Landroid/database/sqlite/SQLiteStatement;
    //   14: invokevirtual 267	android/database/sqlite/SQLiteStatement:simpleQueryForLong	()J
    //   17: lstore_3
    //   18: lload_3
    //   19: lconst_1
    //   20: lcmp
    //   21: ifne +11 -> 32
    //   24: iconst_1
    //   25: istore 5
    //   27: aload_0
    //   28: monitorexit
    //   29: iload 5
    //   31: ireturn
    //   32: iconst_0
    //   33: istore 5
    //   35: goto -8 -> 27
    //   38: astore_2
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_2
    //   42: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	43	0	this	SQLiteLibrary
    //   0	43	1	paramLibraryEntry	LibraryEntry
    //   38	4	2	localObject	Object
    //   17	2	3	l	long
    //   25	9	5	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	18	38	finally
  }
  
  public final LibraryEntry get(LibraryEntry paramLibraryEntry)
  {
    try
    {
      throw new UnsupportedOperationException("getEntry not supported.");
    }
    finally {}
  }
  
  public final Iterator<LibraryEntry> iterator()
  {
    try
    {
      Iterator local1 = new Iterator()
      {
        protected final void finalize()
          throws Throwable
        {
          if (!this.val$all.isClosed()) {
            this.val$all.close();
          }
          super.finalize();
        }
        
        public final boolean hasNext()
        {
          if ((this.val$all.isAfterLast()) || (this.val$all.isLast())) {
            this.val$all.close();
          }
          while (this.val$all.isClosed()) {
            return false;
          }
          return true;
        }
        
        public final void remove()
        {
          throw new UnsupportedOperationException();
        }
      };
      return local1;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void remove(LibraryEntry paramLibraryEntry)
  {
    try
    {
      bindPartialStatement(this.mQueryRemove, paramLibraryEntry);
      this.mQueryRemove.execute();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void reopen()
  {
    this.mDb = new Helper(this.mContext).getWritableDatabase();
    this.mQueryContains = this.mDb.compileStatement("SELECT COUNT(*) FROM ownership WHERE account=? AND library_id=? AND backend=? AND doc_id=? AND doc_type=? AND offer_type=?");
    this.mQueryRemove = this.mDb.compileStatement("DELETE FROM ownership WHERE account=? AND library_id=? AND backend=? AND doc_id=? AND doc_type=? AND offer_type=?");
    this.mQueryResetAccountLibraryId = this.mDb.compileStatement("DELETE FROM ownership WHERE account=? AND library_id=?");
    this.mQuerySize = this.mDb.compileStatement("SELECT COUNT(*) FROM ownership");
  }
  
  public final void resetAccountLibrary(Account paramAccount, String paramString)
  {
    try
    {
      this.mQueryResetAccountLibraryId.bindString(1, paramAccount.name);
      this.mQueryResetAccountLibraryId.bindString(2, paramString);
      this.mQueryResetAccountLibraryId.execute();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final int size()
  {
    return (int)this.mQuerySize.simpleQueryForLong();
  }
  
  private static final class Helper
    extends SQLiteOpenHelper
  {
    public Helper(Context paramContext)
    {
      super("library.db", null, 10);
    }
    
    private void recreateDatabase(SQLiteDatabase paramSQLiteDatabase)
    {
      try
      {
        paramSQLiteDatabase.execSQL("DROP TABLE ownership");
        label6:
        onCreate(paramSQLiteDatabase);
        return;
      }
      catch (SQLException localSQLException)
      {
        break label6;
      }
    }
    
    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE ownership (account STRING, library_id STRING, backend INTEGER, doc_id STRING, doc_type INTEGER, offer_type INTEGER, document_hash INTEGER, subs_valid_until_time INTEGER, app_certificate_hash STRING, app_refund_pre_delivery_endtime_ms INTEGER, app_refund_post_delivery_window_ms INTEGER, subs_auto_renewing INTEGER, subs_initiation_time INTEGER, subs_trial_until_time INTEGER, inapp_purchase_data STRING, inapp_signature STRING, preordered INTEGER, owned_via_license INTEGER, shared_by_me INTEGER, sharer_person_doc_id STRING, PRIMARY KEY (account, library_id, backend, doc_id, doc_type, offer_type))");
    }
    
    public final void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      FinskyLog.d("Downgrading Library from %d to %d", arrayOfObject);
      recreateDatabase(paramSQLiteDatabase);
    }
    
    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      switch (paramInt1)
      {
      default: 
        recreateDatabase(paramSQLiteDatabase);
        return;
      case 5: 
        paramSQLiteDatabase.execSQL("ALTER TABLE ownership ADD COLUMN inapp_purchase_data STRING");
        paramSQLiteDatabase.execSQL("ALTER TABLE ownership ADD COLUMN inapp_signature STRING");
      case 6: 
      case 7: 
        paramSQLiteDatabase.execSQL("ALTER TABLE ownership ADD COLUMN preordered INTEGER");
      case 8: 
        paramSQLiteDatabase.execSQL("ALTER TABLE ownership ADD COLUMN owned_via_license INTEGER");
      }
      paramSQLiteDatabase.execSQL("ALTER TABLE ownership ADD COLUMN shared_by_me INTEGER");
      paramSQLiteDatabase.execSQL("ALTER TABLE ownership ADD COLUMN sharer_person_doc_id STRING");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.SQLiteLibrary
 * JD-Core Version:    0.7.0.1
 */