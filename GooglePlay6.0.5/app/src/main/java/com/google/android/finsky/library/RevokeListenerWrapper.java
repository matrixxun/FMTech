package com.google.android.finsky.library;

import android.accounts.Account;
import com.android.volley.Response.Listener;
import com.google.android.finsky.protos.RevokeResponse;

public final class RevokeListenerWrapper
  implements Response.Listener<RevokeResponse>
{
  private final Account mAccount;
  private final Runnable mPostLibraryUpdateCallback;
  private final LibraryReplicators mReplicators;
  
  public RevokeListenerWrapper(LibraryReplicators paramLibraryReplicators, Account paramAccount, Runnable paramRunnable)
  {
    this.mReplicators = paramLibraryReplicators;
    this.mAccount = paramAccount;
    this.mPostLibraryUpdateCallback = paramRunnable;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.RevokeListenerWrapper
 * JD-Core Version:    0.7.0.1
 */