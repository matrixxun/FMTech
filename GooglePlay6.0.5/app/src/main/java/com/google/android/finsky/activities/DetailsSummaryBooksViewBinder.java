package com.google.android.finsky.activities;

import android.accounts.Account;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.WishlistPlayActionButton;
import com.google.android.play.layout.PlayActionButton;

public final class DetailsSummaryBooksViewBinder
  extends DetailsSummaryViewBinder
{
  public DetailsSummaryBooksViewBinder(DfeToc paramDfeToc, Account paramAccount)
  {
    super(paramDfeToc, paramAccount);
  }
  
  protected final boolean displayActionButtonsIfNecessary(PlayActionButton paramPlayActionButton1, PlayActionButton paramPlayActionButton2, PlayActionButton paramPlayActionButton3, PlayActionButton paramPlayActionButton4, PlayActionButton paramPlayActionButton5, WishlistPlayActionButton paramWishlistPlayActionButton)
  {
    return displayActionButtonsIfNecessaryNew(paramPlayActionButton1, paramPlayActionButton2, paramPlayActionButton3, paramPlayActionButton4, paramPlayActionButton5, paramWishlistPlayActionButton);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsSummaryBooksViewBinder
 * JD-Core Version:    0.7.0.1
 */