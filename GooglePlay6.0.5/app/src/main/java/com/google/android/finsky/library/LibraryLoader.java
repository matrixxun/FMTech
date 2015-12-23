package com.google.android.finsky.library;

import android.accounts.Account;
import android.os.Handler;
import android.util.Base64;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class LibraryLoader
{
  final AccountLibrary mAccountLibrary;
  private final Handler mBackgroundHandler;
  final List<Runnable> mLoadingCallbacks = new ArrayList();
  final Handler mNotificationHandler;
  final SQLiteLibrary mSQLiteLibrary;
  int mState = 0;
  
  public LibraryLoader(SQLiteLibrary paramSQLiteLibrary, AccountLibrary paramAccountLibrary, Handler paramHandler1, Handler paramHandler2)
  {
    this.mSQLiteLibrary = paramSQLiteLibrary;
    this.mAccountLibrary = paramAccountLibrary;
    this.mBackgroundHandler = paramHandler2;
    this.mNotificationHandler = paramHandler1;
  }
  
  public final void load(Runnable paramRunnable)
  {
    try
    {
      this.mLoadingCallbacks.add(paramRunnable);
      this.mBackgroundHandler.post(new Runnable()
      {
        public final void run()
        {
          LibraryLoader localLibraryLoader = LibraryLoader.this;
          if (localLibraryLoader.mState == 0)
          {
            localLibraryLoader.mAccountLibrary.disableListeners();
            localLibraryLoader.mSQLiteLibrary.reopen();
            String str1 = localLibraryLoader.mAccountLibrary.mAccount.name;
            Iterator localIterator2 = localLibraryLoader.mSQLiteLibrary.iterator();
            while (localIterator2.hasNext())
            {
              LibraryEntry localLibraryEntry = (LibraryEntry)localIterator2.next();
              if (str1.equals(localLibraryEntry.mAccountName)) {
                localLibraryLoader.mAccountLibrary.add(localLibraryEntry);
              }
            }
            String[] arrayOfString1 = AccountLibrary.LIBRARY_IDS;
            int i = arrayOfString1.length;
            int j = 0;
            if (j < i)
            {
              String str2 = arrayOfString1[j];
              String str3 = (String)FinskyPreferences.getLibraryServerToken(str2).get(str1).get();
              if (str3 != null) {}
              for (byte[] arrayOfByte = Base64.decode(str3, 0);; arrayOfByte = null)
              {
                localLibraryLoader.mAccountLibrary.setServerToken(str2, arrayOfByte);
                j++;
                break;
              }
            }
            String[] arrayOfString2 = Utils.commaUnpackStrings((String)FinskyPreferences.autoAcquireTags.get(str1).get());
            localLibraryLoader.mAccountLibrary.setAutoAcquireTags(arrayOfString2);
            localLibraryLoader.mState = 2;
            localLibraryLoader.mSQLiteLibrary.close();
            localLibraryLoader.mAccountLibrary.enableListeners();
          }
          try
          {
            Iterator localIterator1 = localLibraryLoader.mLoadingCallbacks.iterator();
            while (localIterator1.hasNext())
            {
              Runnable localRunnable = (Runnable)localIterator1.next();
              localLibraryLoader.mNotificationHandler.post(localRunnable);
            }
            localLibraryLoader.mLoadingCallbacks.clear();
          }
          finally {}
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryLoader
 * JD-Core Version:    0.7.0.1
 */