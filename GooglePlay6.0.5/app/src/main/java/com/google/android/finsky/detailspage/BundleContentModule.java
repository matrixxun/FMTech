package com.google.android.finsky.detailspage;

import android.text.TextUtils;
import android.view.View;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.protos.DocV2;

public class BundleContentModule
  extends SimpleDfeListModule<SimpleDfeListModule.Data>
  implements Libraries.Listener
{
  private boolean mNeedsRefresh;
  
  private void refreshModule()
  {
    if (readyForDisplay())
    {
      this.mNeedsRefresh = true;
      this.mFinskyModuleController.refreshModule(this, false);
    }
  }
  
  public final void bindView(View paramView)
  {
    BundleCardClusterModuleLayout localBundleCardClusterModuleLayout = (BundleCardClusterModuleLayout)paramView;
    if ((!localBundleCardClusterModuleLayout.mIsBound) || (this.mNeedsRefresh))
    {
      this.mNeedsRefresh = false;
      localBundleCardClusterModuleLayout.bind(((SimpleDfeListModule.Data)this.mModuleData).dfeList, this.mBitmapLoader, this.mNavigationManager, this.mParentNode, 17);
    }
  }
  
  protected final SimpleDfeListModule.Data createModuleDataWithListUrl(Document paramDocument)
  {
    if ((paramDocument.mDocument.docType != 6) || (!paramDocument.isVideoBundle()) || (TextUtils.isEmpty(paramDocument.getVideoBundleContentListUrl()))) {
      return null;
    }
    SimpleDfeListModule.Data localData = new SimpleDfeListModule.Data();
    localData.listUrl = paramDocument.getVideoBundleContentListUrl();
    return localData;
  }
  
  public final int getLayoutResId()
  {
    return 2130968647;
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onDataChanged()
  {
    refreshModule();
  }
  
  public final void onDestroyModule()
  {
    this.mLibraries.removeListener(this);
    super.onDestroyModule();
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    refreshModule();
  }
  
  public final boolean readyForDisplay()
  {
    return (super.readyForDisplay()) && (((SimpleDfeListModule.Data)this.mModuleData).dfeList.getCount() != 0);
  }
  
  protected final void startModule()
  {
    this.mLibraries.addListener(this);
    super.startModule();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BundleContentModule
 * JD-Core Version:    0.7.0.1
 */