package com.google.android.finsky.library;

import android.accounts.Account;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryUpdate;

public abstract interface LibraryReplicators
{
  public abstract void addListener(Listener paramListener);
  
  public abstract void applyLibraryUpdates(Account paramAccount, String paramString, Runnable paramRunnable, LibraryUpdateProto.LibraryUpdate... paramVarArgs);
  
  public abstract void dumpState();
  
  public abstract void reinitialize();
  
  public abstract void replicateAccount(Account paramAccount, String[] paramArrayOfString, Runnable paramRunnable, String paramString);
  
  public abstract void replicateAllAccounts(Runnable paramRunnable, String paramString);
  
  public static abstract interface Listener
  {
    public abstract void onMutationsApplied$12348bc5(String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryReplicators
 * JD-Core Version:    0.7.0.1
 */