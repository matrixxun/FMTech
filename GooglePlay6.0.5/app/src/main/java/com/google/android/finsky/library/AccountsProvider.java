package com.google.android.finsky.library;

import android.accounts.Account;
import java.util.List;

public abstract interface AccountsProvider
{
  public abstract Account getAccount(String paramString);
  
  public abstract List<Account> getAccounts();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.AccountsProvider
 * JD-Core Version:    0.7.0.1
 */