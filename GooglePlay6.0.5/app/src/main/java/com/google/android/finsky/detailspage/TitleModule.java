package com.google.android.finsky.detailspage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import com.google.android.finsky.activities.BinderFactory;
import com.google.android.finsky.activities.DetailsSummaryViewBinder;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.protos.DocV2;

public class TitleModule
  extends FinskyModule<TitleModuleData>
  implements DisplayDuringTransition, ThumbnailTransitionParticipant, Libraries.Listener
{
  private DetailsSummaryViewBinder mSummaryViewBinder;
  
  private void setUpSummaryViewBinder()
  {
    if (this.mSummaryViewBinder != null) {
      this.mSummaryViewBinder.onDestroyView();
    }
    this.mSummaryViewBinder = BinderFactory.getSummaryViewBinder(this.mDfeToc, ((TitleModuleData)this.mModuleData).document.mDocument.backendId, this.mDfeApi.getAccount());
    this.mSummaryViewBinder.init(this.mContext, this.mNavigationManager, this.mBitmapLoader, this.mContainerFragment, true, this.mContinueUrl, this.mRevealTransitionCoverName, this.mIsRevealTransitionRunning, this.mParentNode);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    int i;
    switch (paramDocument1.mDocument.docType)
    {
    default: 
      i = 0;
      if (i != 0) {
        break;
      }
    }
    do
    {
      return;
      i = 1;
      break;
      if (this.mModuleData == null)
      {
        this.mModuleData = new TitleModuleData();
        ((TitleModuleData)this.mModuleData).document = paramDocument1;
        setUpSummaryViewBinder();
        this.mLibraries.addListener(this);
        return;
      }
    } while (!paramBoolean);
    ((TitleModuleData)this.mModuleData).document = paramDocument1;
    this.mFinskyModuleController.refreshModule(this, false);
  }
  
  public final void bindThumbnailAtTransitionEnd()
  {
    this.mSummaryViewBinder.bindThumbnailAtTransitionEnd();
  }
  
  public final void bindThumbnailAtTransitionStart(Bitmap paramBitmap)
  {
    this.mSummaryViewBinder.bindThumbnailAtTransitionStart(paramBitmap);
  }
  
  public final void bindView(View paramView)
  {
    this.mSummaryViewBinder.bind(((TitleModuleData)this.mModuleData).document, true, new View[] { paramView });
  }
  
  public final int getLayoutResId()
  {
    return 2130969135;
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onDestroyModule()
  {
    if (this.mSummaryViewBinder != null) {
      this.mSummaryViewBinder.onDestroyView();
    }
    this.mLibraries.removeListener(this);
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    this.mFinskyModuleController.refreshModule(this, false);
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    case 3: 
    case 6: 
    default: 
      return;
    case 1: 
    case 2: 
    case 4: 
    case 7: 
      this.mSummaryViewBinder.onPositiveClick(paramInt, paramBundle);
      return;
    }
    Intent localIntent = new Intent("android.settings.WIFI_SETTINGS");
    localIntent.setFlags(537526272);
    this.mContainerFragment.startActivity(localIntent);
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null)
    {
      setUpSummaryViewBinder();
      this.mLibraries.addListener(this);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected static final class TitleModuleData
    extends FinskyModule.ModuleData
  {
    Document document;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.TitleModule
 * JD-Core Version:    0.7.0.1
 */