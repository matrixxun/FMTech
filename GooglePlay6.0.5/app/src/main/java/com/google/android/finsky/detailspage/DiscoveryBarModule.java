package com.google.android.finsky.detailspage;

import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.DiscoveryBar;
import com.google.android.finsky.layout.play.DiscoveryBadgeBase;
import com.google.android.finsky.layout.play.DiscoveryBadgeGeneric;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Details.DetailsResponse;
import com.google.android.finsky.protos.Details.DiscoveryBadge;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;

public class DiscoveryBarModule
  extends FinskyModule<DiscoveryBarModuleData>
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (paramDocument1.isPreregistration()) {
      this.mFinskyModuleController.removeModule(this);
    }
    do
    {
      do
      {
        do
        {
          return;
        } while (paramDocument1.mDocument.docType == 3);
        if (this.mModuleData == null) {
          this.mModuleData = new DiscoveryBarModuleData();
        }
      } while (((DiscoveryBarModuleData)this.mModuleData).discoveryBadges != null);
      ((DiscoveryBarModuleData)this.mModuleData).detailsDoc = paramDocument1;
      ((DiscoveryBarModuleData)this.mModuleData).socialDetailsDoc = paramDocument2;
    } while (!paramBoolean);
    DiscoveryBarModuleData localDiscoveryBarModuleData = (DiscoveryBarModuleData)this.mModuleData;
    if (paramDfeDetails2.mDetailsResponse == null) {}
    for (Details.DiscoveryBadge[] arrayOfDiscoveryBadge = null;; arrayOfDiscoveryBadge = paramDfeDetails2.mDetailsResponse.discoveryBadge)
    {
      localDiscoveryBarModuleData.discoveryBadges = arrayOfDiscoveryBadge;
      if ((((DiscoveryBarModuleData)this.mModuleData).discoveryBadges == null) || (((DiscoveryBarModuleData)this.mModuleData).discoveryBadges.length <= 0)) {
        break;
      }
      this.mFinskyModuleController.refreshModule(this, true);
      return;
    }
    this.mFinskyModuleController.removeModule(this);
  }
  
  public final void bindView(View paramView)
  {
    DiscoveryBar localDiscoveryBar = (DiscoveryBar)paramView;
    if (((DiscoveryBarModuleData)this.mModuleData).discoveryBadges == null)
    {
      localDiscoveryBar.mBadgeContainer.removeAllViews();
      localDiscoveryBadgeGeneric = (DiscoveryBadgeGeneric)LayoutInflater.from(localDiscoveryBar.getContext()).inflate(2130968714, localDiscoveryBar.mBadgeContainer, false);
      localDiscoveryBadgeGeneric.setVisibility(4);
      localDiscoveryBar.mBadgeContainer.addView(localDiscoveryBadgeGeneric);
    }
    while (localDiscoveryBar.mHasConfigured)
    {
      DiscoveryBadgeGeneric localDiscoveryBadgeGeneric;
      return;
    }
    NavigationManager localNavigationManager = this.mNavigationManager;
    BitmapLoader localBitmapLoader = this.mBitmapLoader;
    Document localDocument = ((DiscoveryBarModuleData)this.mModuleData).socialDetailsDoc;
    Details.DiscoveryBadge[] arrayOfDiscoveryBadge1 = ((DiscoveryBarModuleData)this.mModuleData).discoveryBadges;
    DfeToc localDfeToc = FinskyApp.get().mToc;
    PackageManager localPackageManager = FinskyApp.get().getPackageManager();
    boolean bool = ((DiscoveryBarModuleData)this.mModuleData).hasSavedScrollPosition;
    int i = ((DiscoveryBarModuleData)this.mModuleData).savedScrollPosition;
    PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
    if ((arrayOfDiscoveryBadge1 == null) || (arrayOfDiscoveryBadge1.length == 0))
    {
      localDiscoveryBar.setVisibility(8);
      ((DiscoveryBarModuleData)this.mModuleData).hasSavedScrollPosition = false;
      return;
    }
    localDiscoveryBar.setVisibility(0);
    localDiscoveryBar.mHasConfigured = true;
    localDiscoveryBar.mDoc = localDocument;
    localDiscoveryBar.mDiscoveryBadges = arrayOfDiscoveryBadge1;
    localDiscoveryBar.mBitmapLoader = localBitmapLoader;
    localDiscoveryBar.mNavigationManager = localNavigationManager;
    localDiscoveryBar.mDfeToc = localDfeToc;
    localDiscoveryBar.mPackageManager = localPackageManager;
    localDiscoveryBar.mParentNode = localPlayStoreUiElementNode;
    localDiscoveryBar.mNeedsToRestoreScrollPosition = bool;
    localDiscoveryBar.mRestoreScrollPosition = i;
    LayoutInflater localLayoutInflater = LayoutInflater.from(localDiscoveryBar.getContext());
    localDiscoveryBar.mBadgeContainer.removeAllViews();
    Details.DiscoveryBadge[] arrayOfDiscoveryBadge2 = localDiscoveryBar.mDiscoveryBadges;
    int j = arrayOfDiscoveryBadge2.length;
    int k = 0;
    label273:
    Details.DiscoveryBadge localDiscoveryBadge;
    DiscoveryBadgeBase localDiscoveryBadgeBase;
    if (k < j)
    {
      localDiscoveryBadge = arrayOfDiscoveryBadge2[k];
      if (!localDiscoveryBadge.hasAggregateRating) {
        break label355;
      }
      localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968715, localDiscoveryBar.mBadgeContainer, false);
    }
    for (;;)
    {
      localDiscoveryBadgeBase.bind(localDiscoveryBadge, localDiscoveryBar.mBitmapLoader, localDiscoveryBar.mNavigationManager, localDiscoveryBar.mDoc, localDiscoveryBar.mDfeToc, localDiscoveryBar.mPackageManager, localDiscoveryBar);
      localDiscoveryBar.mBadgeContainer.addView(localDiscoveryBadgeBase);
      k++;
      break label273;
      break;
      label355:
      if (localDiscoveryBadge.isPlusOne) {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968717, localDiscoveryBar.mBadgeContainer, false);
      } else if (localDiscoveryBadge.hasUserStarRating) {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968718, localDiscoveryBar.mBadgeContainer, false);
      } else if (localDiscoveryBadge.hasDownloadCount) {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968711, localDiscoveryBar.mBadgeContainer, false);
      } else if (localDiscoveryBadge.playerBadge != null) {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968716, localDiscoveryBar.mBadgeContainer, false);
      } else if (localDiscoveryBadge.familyAgeRangeBadge != null) {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968712, localDiscoveryBar.mBadgeContainer, false);
      } else if (localDiscoveryBadge.familyCategoryBadge != null) {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968713, localDiscoveryBar.mBadgeContainer, false);
      } else {
        localDiscoveryBadgeBase = (DiscoveryBadgeBase)localLayoutInflater.inflate(2130968714, localDiscoveryBar.mBadgeContainer, false);
      }
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968719;
  }
  
  public final void onRecycleView(View paramView)
  {
    DiscoveryBar localDiscoveryBar = (DiscoveryBar)paramView;
    if (this.mModuleData != null)
    {
      ((DiscoveryBarModuleData)this.mModuleData).hasSavedScrollPosition = true;
      ((DiscoveryBarModuleData)this.mModuleData).savedScrollPosition = localDiscoveryBar.getScrollPosition();
    }
  }
  
  public final boolean readyForDisplay()
  {
    if (this.mModuleData == null) {
      return false;
    }
    if (((DiscoveryBarModuleData)this.mModuleData).detailsDoc.isPreregistration()) {
      return false;
    }
    if (((DiscoveryBarModuleData)this.mModuleData).discoveryBadges == null)
    {
      switch (((DiscoveryBarModuleData)this.mModuleData).detailsDoc.mDocument.docType)
      {
      default: 
        return false;
      }
      return true;
    }
    return ((DiscoveryBarModuleData)this.mModuleData).discoveryBadges.length > 0;
  }
  
  protected static final class DiscoveryBarModuleData
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
    Details.DiscoveryBadge[] discoveryBadges;
    boolean hasSavedScrollPosition;
    int savedScrollPosition;
    Document socialDetailsDoc;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.DiscoveryBarModule
 * JD-Core Version:    0.7.0.1
 */