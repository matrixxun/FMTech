package com.google.android.finsky.activities;

import android.accounts.Account;
import android.view.View;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.WishlistPlayActionButton;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.play.layout.PlayActionButton;
import java.util.List;

public final class DetailsSummaryMagazinesViewBinder
  extends DetailsSummaryViewBinder
{
  private List<Document> mSubscriptions;
  
  public DetailsSummaryMagazinesViewBinder(DfeToc paramDfeToc, Account paramAccount)
  {
    super(paramDfeToc, paramAccount);
  }
  
  public final void bind(Document paramDocument, boolean paramBoolean, View... paramVarArgs)
  {
    this.mSubscriptions = DocUtils.getSubscriptions(paramDocument, this.mDfeToc, FinskyApp.get().mLibraries.getAccountLibrary(this.mDetailsAccount));
    super.bind(paramDocument, paramBoolean, paramVarArgs);
  }
  
  protected final boolean displayActionButtonsIfNecessary(PlayActionButton paramPlayActionButton1, PlayActionButton paramPlayActionButton2, PlayActionButton paramPlayActionButton3, PlayActionButton paramPlayActionButton4, PlayActionButton paramPlayActionButton5, WishlistPlayActionButton paramWishlistPlayActionButton)
  {
    return displayActionButtonsIfNecessaryNew(paramPlayActionButton1, paramPlayActionButton2, paramPlayActionButton3, paramPlayActionButton4, paramPlayActionButton5, paramWishlistPlayActionButton);
  }
  
  protected final void setupActionButtons(boolean paramBoolean)
  {
    super.setupActionButtons(paramBoolean);
    findViewById(2131755371).setVisibility(8);
    syncButtonSectionVisibility();
  }
  
  protected final boolean shouldDisplayOfferNote()
  {
    Libraries localLibraries = FinskyApp.get().mLibraries;
    return LibraryUtils.getOwnerWithCurrentAccount(this.mSubscriptions, localLibraries, this.mDetailsAccount) == null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsSummaryMagazinesViewBinder
 * JD-Core Version:    0.7.0.1
 */