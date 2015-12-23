package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.google.android.finsky.adapters.PeopleDetailsStreamAdapter;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher.RetryButtonListener;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocDetails.PersonDetails;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.MyPeoplePageHelper;
import com.google.android.play.image.BitmapLoader;

public final class PeopleDetailsStreamViewBinder
  extends DetailsViewBinder
  implements OnDataChangedListener
{
  PeopleDetailsStreamAdapter mAdapter;
  private BitmapLoader mBitmapLoader;
  private ClientMutationCache mClientMutationCache;
  DfeList mDfeList;
  private DfeToc mDfeToc;
  boolean mIsAdapterSet;
  private long mLastRequestTimeMs;
  private PlayStoreUiElementNode mParentNode;
  PlayRecyclerView mRecyclerView;
  Bundle mRecyclerViewRestoreBundle = new Bundle();
  
  private void clearDfeList()
  {
    if (this.mDfeList != null)
    {
      this.mDfeList.removeDataChangedListener(this);
      this.mDfeList.removeErrorListener(this);
      this.mDfeList = null;
    }
  }
  
  private void rebindAdapter()
  {
    if (this.mRecyclerView == null) {
      FinskyLog.w("List view null, ignoring.", new Object[0]);
    }
    final boolean bool;
    do
    {
      return;
      bool = PeopleDetailsStreamAdapter.hasRestoreData(this.mRecyclerViewRestoreBundle);
    } while ((this.mDfeList == null) || (!this.mDfeList.isReady()));
    if (this.mAdapter == null) {
      this.mAdapter = new PeopleDetailsStreamAdapter(this.mDoc, this.mContext, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, this.mDfeToc, this.mClientMutationCache, new MultiDfeList(this.mDfeList), bool, this.mParentNode);
    }
    if (!this.mIsAdapterSet)
    {
      this.mIsAdapterSet = true;
      this.mRecyclerView.post(new Runnable()
      {
        public final void run()
        {
          if ((PeopleDetailsStreamViewBinder.this.mRecyclerView != null) && (PeopleDetailsStreamViewBinder.this.mAdapter != null))
          {
            PeopleDetailsStreamViewBinder.this.mRecyclerView.setAdapter(PeopleDetailsStreamViewBinder.this.mAdapter);
            if (bool)
            {
              PeopleDetailsStreamViewBinder.this.mAdapter.onRestoreInstanceState(PeopleDetailsStreamViewBinder.this.mRecyclerView, PeopleDetailsStreamViewBinder.this.mRecyclerViewRestoreBundle);
              PeopleDetailsStreamViewBinder.this.mRecyclerViewRestoreBundle.clear();
            }
          }
        }
      });
      return;
    }
    this.mAdapter.updateAdapterData(this.mDfeList);
  }
  
  public final void bind(View paramView, Document paramDocument)
  {
    super.bind$4d2badcf(paramView, paramDocument);
    if (this.mLayoutSwitcher == null) {
      this.mLayoutSwitcher = new LayoutSwitcher(paramView, 2131755814, new LayoutSwitcher.RetryButtonListener()
      {
        public final void onRetry()
        {
          PeopleDetailsStreamViewBinder.this.requestData();
        }
      });
    }
    DocDetails.PersonDetails localPersonDetails = this.mDoc.getPersonDetails();
    if ((localPersonDetails != null) && (localPersonDetails.personIsRequester)) {}
    for (int i = 1;; i = 0)
    {
      if ((i != 0) && (MyPeoplePageHelper.hasMutationOccurredSince(this.mLastRequestTimeMs)))
      {
        clearDfeList();
        this.mRecyclerViewRestoreBundle.clear();
      }
      if ((this.mDfeList != null) && (this.mDfeList.isReady())) {
        break;
      }
      this.mLayoutSwitcher.switchToLoadingMode();
      requestData();
      return;
    }
    this.mLayoutSwitcher.switchToDataMode();
    rebindAdapter();
  }
  
  public final void init(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.init(paramContext, paramDfeApi, paramNavigationManager);
    this.mBitmapLoader = paramBitmapLoader;
    this.mDfeToc = paramDfeToc;
    this.mClientMutationCache = paramClientMutationCache;
    this.mParentNode = paramPlayStoreUiElementNode;
  }
  
  public final void onDataChanged()
  {
    if (this.mLayoutSwitcher != null) {
      this.mLayoutSwitcher.switchToDataMode();
    }
    rebindAdapter();
  }
  
  void requestData()
  {
    clearDfeList();
    this.mDfeList = new DfeList(this.mDfeApi, this.mDoc.getCoreContentListUrl(), true);
    this.mDfeList.addDataChangedListener(this);
    this.mDfeList.addErrorListener(this);
    this.mDfeList.startLoadItems();
    this.mLastRequestTimeMs = System.currentTimeMillis();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.PeopleDetailsStreamViewBinder
 * JD-Core Version:    0.7.0.1
 */