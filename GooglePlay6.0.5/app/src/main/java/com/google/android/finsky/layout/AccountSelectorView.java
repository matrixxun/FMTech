package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.Utils;

public class AccountSelectorView
  extends IdentifiableTextView
{
  public AccountSelectorView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AccountSelectorView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configure()
  {
    Account localAccount = FinskyApp.get().getCurrentAccount();
    getContext();
    setText(Utils.getDisplayAccountName$17065f8(localAccount));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AccountSelectorView
 * JD-Core Version:    0.7.0.1
 */