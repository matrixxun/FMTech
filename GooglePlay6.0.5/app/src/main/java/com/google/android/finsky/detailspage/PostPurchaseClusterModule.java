package com.google.android.finsky.detailspage;

import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.utils.LibraryUtils;

public abstract class PostPurchaseClusterModule
  extends SimpleCardClusterModule
  implements Libraries.Listener
{
  private Document mDetailsDoc;
  private DfeDetails mDfeDetails;
  private boolean mHasDetailsLoaded;
  private Boolean mIsEnabled;
  private Document mSocialDetailsDoc;
  private DfeDetails mSocialDfeDetails;
  private Boolean mWasOwnedWhenPageLoaded;
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mIsEnabled == null) {
      this.mIsEnabled = Boolean.valueOf(shouldEnable(paramDocument1));
    }
    if (this.mIsEnabled.booleanValue())
    {
      if (this.mWasOwnedWhenPageLoaded == null)
      {
        this.mWasOwnedWhenPageLoaded = Boolean.valueOf(LibraryUtils.isOwned(paramDocument1, this.mLibraries));
        this.mLibraries.addListener(this);
      }
      this.mHasDetailsLoaded = paramBoolean;
      this.mDetailsDoc = paramDocument1;
      this.mDfeDetails = paramDfeDetails1;
      this.mSocialDetailsDoc = paramDocument2;
      this.mSocialDfeDetails = paramDfeDetails2;
    }
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onDestroyModule()
  {
    super.onDestroyModule();
    this.mLibraries.removeListener(this);
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    if ((!this.mWasOwnedWhenPageLoaded.booleanValue()) && (LibraryUtils.isOwned(this.mDetailsDoc, this.mLibraries))) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0)
      {
        super.bindModule(this.mHasDetailsLoaded, this.mDetailsDoc, this.mDfeDetails, this.mSocialDetailsDoc, this.mSocialDfeDetails);
        this.mWasOwnedWhenPageLoaded = Boolean.valueOf(true);
      }
      return;
    }
  }
  
  protected abstract boolean shouldEnable(Document paramDocument);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.PostPurchaseClusterModule
 * JD-Core Version:    0.7.0.1
 */