package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import com.google.android.finsky.activities.ReviewFeedbackListener;
import com.google.android.finsky.activities.ReviewFeedbackListener.ReviewFeedbackRating;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.play.image.BitmapLoader;

public abstract class FinskyModule<T extends ModuleData>
  extends ModulesAdapter.Module
  implements ReviewFeedbackListener, SimpleAlertDialog.Listener
{
  protected BitmapLoader mBitmapLoader;
  protected ClusterContentConfiguratorRepository mClusterConfiguratorRepository;
  protected PageFragment mContainerFragment;
  protected Context mContext;
  protected String mContinueUrl;
  protected String mDetailsUrl;
  protected DfeApi mDfeApi;
  protected DfeToc mDfeToc;
  protected FinskyModuleController mFinskyModuleController;
  protected boolean mIsRevealTransitionRunning;
  protected Libraries mLibraries;
  protected T mModuleData;
  protected NavigationManager mNavigationManager;
  protected PlayStoreUiElementNode mParentNode;
  protected RecyclerView.RecycledViewPool mRecycledViewPool;
  protected String mRevealTransitionCoverName;
  protected DfeApi mSocialDfeApi;
  protected boolean mUseWideLayout;
  
  public abstract void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2);
  
  public void init(Context paramContext, FinskyModuleController paramFinskyModuleController, DfeToc paramDfeToc, DfeApi paramDfeApi1, DfeApi paramDfeApi2, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PageFragment paramPageFragment, String paramString1, String paramString2, Libraries paramLibraries, boolean paramBoolean1, String paramString3, boolean paramBoolean2, RecyclerView.RecycledViewPool paramRecycledViewPool, ClusterContentConfiguratorRepository paramClusterContentConfiguratorRepository, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mContext = paramContext;
    this.mFinskyModuleController = paramFinskyModuleController;
    this.mDfeToc = paramDfeToc;
    this.mDfeApi = paramDfeApi1;
    this.mSocialDfeApi = paramDfeApi2;
    this.mBitmapLoader = paramBitmapLoader;
    this.mNavigationManager = paramNavigationManager;
    this.mContainerFragment = paramPageFragment;
    this.mDetailsUrl = paramString1;
    this.mContinueUrl = paramString2;
    this.mLibraries = paramLibraries;
    this.mUseWideLayout = paramBoolean1;
    this.mRevealTransitionCoverName = paramString3;
    this.mIsRevealTransitionRunning = paramBoolean2;
    this.mRecycledViewPool = paramRecycledViewPool;
    this.mClusterConfiguratorRepository = paramClusterContentConfiguratorRepository;
    this.mParentNode = paramPlayStoreUiElementNode;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {}
  
  public void onDestroyModule() {}
  
  public void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public void onPositiveClick(int paramInt, Bundle paramBundle) {}
  
  protected void onReceiveBroadcastData(String paramString, Object paramObject) {}
  
  public void onRestoreModuleData(ModuleData paramModuleData)
  {
    this.mModuleData = paramModuleData;
  }
  
  public void onReviewFeedback(String paramString1, String paramString2, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating) {}
  
  public T onSaveModuleData()
  {
    return this.mModuleData;
  }
  
  public abstract boolean readyForDisplay();
  
  public static abstract interface FinskyModuleController
  {
    public abstract void broadcastData(String paramString, Object paramObject);
    
    public abstract void refreshModule(FinskyModule paramFinskyModule, boolean paramBoolean);
    
    public abstract void removeModule(FinskyModule paramFinskyModule);
  }
  
  public static class ModuleData {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.FinskyModule
 * JD-Core Version:    0.7.0.1
 */