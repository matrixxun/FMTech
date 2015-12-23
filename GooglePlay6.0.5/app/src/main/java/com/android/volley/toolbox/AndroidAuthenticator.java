package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.AuthFailureError;

public final class AndroidAuthenticator
{
  public final Account mAccount;
  private final AccountManager mAccountManager;
  private final String mAuthTokenType;
  private final boolean mNotifyAuthFailure;
  
  private AndroidAuthenticator(AccountManager paramAccountManager, Account paramAccount, String paramString, boolean paramBoolean)
  {
    this.mAccountManager = paramAccountManager;
    this.mAccount = paramAccount;
    this.mAuthTokenType = paramString;
    this.mNotifyAuthFailure = false;
  }
  
  public AndroidAuthenticator(Context paramContext, Account paramAccount, String paramString)
  {
    this(paramContext, paramAccount, paramString, (byte)0);
  }
  
  public AndroidAuthenticator(Context paramContext, Account paramAccount, String paramString, byte paramByte)
  {
    this(AccountManager.get(paramContext), paramAccount, paramString, false);
  }
  
  public final String getAuthToken()
    throws AuthFailureError
  {
    AccountManagerFuture localAccountManagerFuture = this.mAccountManager.getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
    Bundle localBundle;
    try
    {
      localBundle = (Bundle)localAccountManagerFuture.getResult();
      boolean bool1 = localAccountManagerFuture.isDone();
      str = null;
      if (!bool1) {
        break label110;
      }
      boolean bool2 = localAccountManagerFuture.isCancelled();
      str = null;
      if (bool2) {
        break label110;
      }
      if (localBundle.containsKey("intent")) {
        throw new AuthFailureError((Intent)localBundle.getParcelable("intent"));
      }
    }
    catch (Exception localException)
    {
      throw new AuthFailureError("Error while retrieving auth token", localException);
    }
    String str = localBundle.getString("authtoken");
    label110:
    if (str == null) {
      throw new AuthFailureError("Got null auth token for type: " + this.mAuthTokenType);
    }
    return str;
  }
  
  public final void invalidateAuthToken(String paramString)
  {
    this.mAccountManager.invalidateAuthToken(this.mAccount.type, paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.AndroidAuthenticator
 * JD-Core Version:    0.7.0.1
 */