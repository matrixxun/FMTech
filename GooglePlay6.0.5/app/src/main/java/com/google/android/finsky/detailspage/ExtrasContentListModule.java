package com.google.android.finsky.detailspage;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.ExtrasItemSnippet;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;

public class ExtrasContentListModule
  extends SimpleDfeListModule<Data>
  implements ExtrasContentListModuleLayout.ExtrasItemSelectionListener, Libraries.Listener
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
    ExtrasContentListModuleLayout localExtrasContentListModuleLayout = (ExtrasContentListModuleLayout)paramView;
    BitmapLoader localBitmapLoader;
    PlayStoreUiElementNode localPlayStoreUiElementNode;
    NavigationManager localNavigationManager;
    Document localDocument1;
    Object localObject1;
    int k;
    int m;
    Object localObject2;
    Document localDocument2;
    ExtrasItemSnippet localExtrasItemSnippet3;
    Object localObject5;
    label253:
    int i2;
    label300:
    Object localObject3;
    ExtrasItemSnippet localExtrasItemSnippet2;
    if ((!localExtrasContentListModuleLayout.mHasBinded) || (this.mNeedsRefresh))
    {
      this.mNeedsRefresh = false;
      localBitmapLoader = this.mBitmapLoader;
      localPlayStoreUiElementNode = this.mParentNode;
      localNavigationManager = this.mNavigationManager;
      DfeList localDfeList = ((Data)this.mModuleData).dfeList;
      localDocument1 = ((Data)this.mModuleData).selectedItem;
      localExtrasContentListModuleLayout.mHasBinded = true;
      localExtrasContentListModuleLayout.mExtrasItemSelectionListener = this;
      String str1 = localDfeList.mContainerDocument.mDocument.title;
      String str2 = localDfeList.mContainerDocument.mDocument.subtitle;
      boolean bool1 = TextUtils.isEmpty(str1);
      boolean bool2 = TextUtils.isEmpty(str2);
      int n;
      if ((bool1) && (bool2))
      {
        localExtrasContentListModuleLayout.mHeader.setVisibility(8);
        localObject1 = null;
        k = localDfeList.getCount();
        m = -1 + localExtrasContentListModuleLayout.getChildCount();
        localObject2 = null;
        n = 0;
      }
      while (n < k)
      {
        localDocument2 = (Document)localDfeList.getItem(n);
        if (n < m)
        {
          localExtrasItemSnippet3 = (ExtrasItemSnippet)localExtrasContentListModuleLayout.getChildAt(n + 1);
          if (localExtrasItemSnippet3.getDocument() != localDocument2) {
            break label515;
          }
          localObject5 = localObject2;
          n++;
          localObject2 = localObject5;
          continue;
          localExtrasContentListModuleLayout.mHeader.setVisibility(0);
          localExtrasContentListModuleLayout.mTitle.setText(str1);
          TextView localTextView1 = localExtrasContentListModuleLayout.mTitle;
          int i;
          TextView localTextView2;
          if (bool1)
          {
            i = 8;
            localTextView1.setVisibility(i);
            localExtrasContentListModuleLayout.mSubtitle.setText(str2);
            localTextView2 = localExtrasContentListModuleLayout.mSubtitle;
            if (!bool2) {
              break label300;
            }
          }
          for (int j = 8;; j = 0)
          {
            localTextView2.setVisibility(j);
            break;
            i = 0;
            break label253;
          }
        }
        else
        {
          if (localObject2 == null) {
            localObject2 = LayoutInflater.from(localExtrasContentListModuleLayout.getContext());
          }
          ExtrasItemSnippet localExtrasItemSnippet1 = (ExtrasItemSnippet)((LayoutInflater)localObject2).inflate(2130968737, localExtrasContentListModuleLayout, false);
          i2 = 1;
          localObject3 = localObject2;
          localExtrasItemSnippet2 = localExtrasItemSnippet1;
        }
      }
    }
    for (;;)
    {
      if (localDocument2 == localDocument1) {}
      for (Object localObject4 = localExtrasItemSnippet2;; localObject4 = localObject1)
      {
        localExtrasItemSnippet2.mDocument = localDocument2;
        localExtrasItemSnippet2.mNavigationManager = localNavigationManager;
        localExtrasItemSnippet2.mBitmapLoader = localBitmapLoader;
        localExtrasItemSnippet2.mCollapsedStateChangedListener = localExtrasContentListModuleLayout;
        localExtrasItemSnippet2.mParentNode = localPlayStoreUiElementNode;
        FinskyEventLog.setServerLogCookie(localExtrasItemSnippet2.mUiElementProto, localDocument2.mDocument.serverLogsCookie);
        localExtrasItemSnippet2.mParentNode.childImpression(localExtrasItemSnippet2);
        if (i2 != 0) {
          localExtrasContentListModuleLayout.addView(localExtrasItemSnippet2);
        }
        for (;;)
        {
          localExtrasItemSnippet2.setVisibility(0);
          localObject5 = localObject3;
          localObject1 = localObject4;
          break;
          localExtrasItemSnippet2.updateContentView();
        }
        for (int i1 = k; i1 < m; i1++) {
          localExtrasContentListModuleLayout.getChildAt(i1 + 1).setVisibility(8);
        }
        if ((localObject1 != null) && (!localObject1.isExpanded())) {
          localObject1.setExpandedContentVisibility(0);
        }
        localExtrasContentListModuleLayout.refreshDrawableState();
        return;
      }
      label515:
      localObject3 = localObject2;
      localExtrasItemSnippet2 = localExtrasItemSnippet3;
      i2 = 0;
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968736;
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
  
  public final void onExtrasItemSelected(Document paramDocument)
  {
    ((Data)this.mModuleData).selectedItem = paramDocument;
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    refreshModule();
  }
  
  public final boolean readyForDisplay()
  {
    return (super.readyForDisplay()) && (((Data)this.mModuleData).dfeList.getCount() != 0);
  }
  
  protected final void startModule()
  {
    this.mLibraries.addListener(this);
    super.startModule();
  }
  
  protected static final class Data
    extends SimpleDfeListModule.Data
  {
    Document selectedItem;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ExtrasContentListModule
 * JD-Core Version:    0.7.0.1
 */