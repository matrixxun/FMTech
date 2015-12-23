package com.google.android.finsky.detailspage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.view.View;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.DiscoveryBar;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.List;

public class CombinedTitleModule
  extends FinskyModule<CombinedTitleModuleData>
  implements DisplayDuringTransition, FinskyModule.FinskyModuleController, ThumbnailTransitionParticipant
{
  private DiscoveryBarModule mDiscoveryBarModule;
  private boolean mHasDiscoveryBarModuleBindedView;
  private boolean mHasWarningMessageModuleBindedView;
  private List<FinskyModule> mNestedModules;
  private TitleModule mTitleModule;
  private WarningMessageModule mWarningMessageModule;
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    for (int i = 0; i < this.mNestedModules.size(); i++) {
      ((FinskyModule)this.mNestedModules.get(i)).bindModule(paramBoolean, paramDocument1, paramDfeDetails1, paramDocument2, paramDfeDetails2);
    }
  }
  
  public final void bindThumbnailAtTransitionEnd()
  {
    this.mTitleModule.bindThumbnailAtTransitionEnd();
  }
  
  public final void bindThumbnailAtTransitionStart(Bitmap paramBitmap)
  {
    this.mTitleModule.bindThumbnailAtTransitionStart(paramBitmap);
  }
  
  public final void bindView(View paramView)
  {
    CombinedTitleModuleLayout localCombinedTitleModuleLayout = (CombinedTitleModuleLayout)paramView;
    this.mTitleModule.bindView(localCombinedTitleModuleLayout.getTitleModuleLayout());
    DiscoveryBar localDiscoveryBar = localCombinedTitleModuleLayout.getDiscoveryBarModuleLayout();
    if (this.mDiscoveryBarModule.readyForDisplay())
    {
      this.mDiscoveryBarModule.bindView(localDiscoveryBar);
      this.mHasDiscoveryBarModuleBindedView = true;
      localDiscoveryBar.setVisibility(0);
    }
    WarningMessageModuleLayout localWarningMessageModuleLayout;
    for (;;)
    {
      localWarningMessageModuleLayout = localCombinedTitleModuleLayout.getWarningMessageModuleLayout();
      if (!this.mWarningMessageModule.readyForDisplay()) {
        break;
      }
      this.mWarningMessageModule.bindView(localWarningMessageModuleLayout);
      this.mHasWarningMessageModuleBindedView = true;
      localWarningMessageModuleLayout.setVisibility(0);
      return;
      localDiscoveryBar.setVisibility(8);
    }
    localWarningMessageModuleLayout.setVisibility(8);
  }
  
  public final void broadcastData(String paramString, Object paramObject)
  {
    for (int i = 0; i < this.mNestedModules.size(); i++) {
      ((FinskyModule)this.mNestedModules.get(i)).onReceiveBroadcastData(paramString, paramObject);
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968664;
  }
  
  public final void init(Context paramContext, FinskyModule.FinskyModuleController paramFinskyModuleController, DfeToc paramDfeToc, DfeApi paramDfeApi1, DfeApi paramDfeApi2, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PageFragment paramPageFragment, String paramString1, String paramString2, Libraries paramLibraries, boolean paramBoolean1, String paramString3, boolean paramBoolean2, RecyclerView.RecycledViewPool paramRecycledViewPool, ClusterContentConfiguratorRepository paramClusterContentConfiguratorRepository, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.init(paramContext, paramFinskyModuleController, paramDfeToc, paramDfeApi1, paramDfeApi2, paramBitmapLoader, paramNavigationManager, paramPageFragment, paramString1, paramString2, paramLibraries, paramBoolean1, paramString3, paramBoolean2, paramRecycledViewPool, paramClusterContentConfiguratorRepository, paramPlayStoreUiElementNode);
    this.mTitleModule = new TitleModule();
    this.mDiscoveryBarModule = new DiscoveryBarModule();
    this.mWarningMessageModule = new WarningMessageModule();
    this.mNestedModules = new ArrayList();
    this.mNestedModules.add(this.mTitleModule);
    this.mNestedModules.add(this.mDiscoveryBarModule);
    this.mNestedModules.add(this.mWarningMessageModule);
    for (int i = 0;; i++)
    {
      int j = this.mNestedModules.size();
      if (i >= j) {
        break;
      }
      ((FinskyModule)this.mNestedModules.get(i)).init(paramContext, this, paramDfeToc, paramDfeApi1, paramDfeApi2, paramBitmapLoader, paramNavigationManager, paramPageFragment, paramString1, paramString2, paramLibraries, paramBoolean1, paramString3, paramBoolean2, paramRecycledViewPool, paramClusterContentConfiguratorRepository, paramPlayStoreUiElementNode);
    }
  }
  
  public final void onDestroyModule()
  {
    for (int i = 0; i < this.mNestedModules.size(); i++) {
      ((FinskyModule)this.mNestedModules.get(i)).onDestroyModule();
    }
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    this.mTitleModule.onNegativeClick(paramInt, paramBundle);
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    this.mTitleModule.onPositiveClick(paramInt, paramBundle);
  }
  
  public final void onRecycleView(View paramView)
  {
    CombinedTitleModuleLayout localCombinedTitleModuleLayout = (CombinedTitleModuleLayout)paramView;
    this.mTitleModule.onRecycleView(localCombinedTitleModuleLayout.getTitleModuleLayout());
    if (this.mHasDiscoveryBarModuleBindedView) {
      this.mDiscoveryBarModule.onRecycleView(localCombinedTitleModuleLayout.getDiscoveryBarModuleLayout());
    }
    if (this.mHasWarningMessageModuleBindedView) {
      this.mWarningMessageModule.onRecycleView(localCombinedTitleModuleLayout.getWarningMessageModuleLayout());
    }
    super.onRecycleView(paramView);
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null)
    {
      this.mTitleModule.onRestoreModuleData(((CombinedTitleModuleData)this.mModuleData).titleModuleData);
      this.mDiscoveryBarModule.onRestoreModuleData(((CombinedTitleModuleData)this.mModuleData).discoveryBarModuleData);
      this.mWarningMessageModule.onRestoreModuleData(((CombinedTitleModuleData)this.mModuleData).warningMessageModuleData);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return this.mTitleModule.readyForDisplay();
  }
  
  public final void refreshModule(FinskyModule paramFinskyModule, boolean paramBoolean)
  {
    if (this.mTitleModule.readyForDisplay()) {
      this.mFinskyModuleController.refreshModule(this, false);
    }
  }
  
  public final void removeModule(FinskyModule paramFinskyModule)
  {
    if (this.mTitleModule.readyForDisplay()) {
      this.mFinskyModuleController.refreshModule(this, false);
    }
  }
  
  protected static final class CombinedTitleModuleData
    extends FinskyModule.ModuleData
  {
    DiscoveryBarModule.DiscoveryBarModuleData discoveryBarModuleData;
    TitleModule.TitleModuleData titleModuleData;
    WarningMessageModule.WarningMessageModuleData warningMessageModuleData;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.CombinedTitleModule
 * JD-Core Version:    0.7.0.1
 */