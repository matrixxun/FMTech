package com.google.android.finsky.activities.myaccount;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.adapters.EmptyRecyclerViewAdapter;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.refund.RequestRefundActivity;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.OrderHistoryRowView.OnRefundActionListener;
import com.google.android.finsky.layout.SubscriptionView.CancelListener;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.AppSupport;
import com.google.android.finsky.utils.AppSupport.RefundListener;
import com.google.android.finsky.utils.CancelSubscriptionDialog;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.MyAccountHelper;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.headerlist.PlayHeaderListLayout;

public final class MyAccountListPageFragment
  extends PageFragment
  implements SimpleAlertDialog.Listener, OrderHistoryRowView.OnRefundActionListener, SubscriptionView.CancelListener, Libraries.Listener
{
  private MyAccountListPageAdapter mAdapter;
  private String mBreadcrumb;
  private DfeList mDfeList;
  private boolean mIsOrderHistoryPage;
  private long mLastRequestTimeMs;
  private Libraries mLibraries;
  private PlayRecyclerView mRecyclerView;
  private Bundle mRecyclerViewRestoreBundle = new Bundle();
  private PlayStore.PlayStoreUiElement mUiElementProto;
  
  private void clearDataSource()
  {
    if (this.mDfeList != null)
    {
      this.mDfeList.removeDataChangedListener(this);
      this.mDfeList.removeErrorListener(this);
      this.mDfeList = null;
    }
  }
  
  public static MyAccountListPageFragment newInstance(String paramString1, String paramString2, DfeToc paramDfeToc, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("list_url", paramString1);
    localBundle.putString("title", paramString2);
    localBundle.putBoolean("is_order_history_page", paramBoolean);
    MyAccountListPageFragment localMyAccountListPageFragment = new MyAccountListPageFragment();
    localMyAccountListPageFragment.setArguments(localBundle);
    localMyAccountListPageFragment.setArgument("finsky.PageFragment.toc", paramDfeToc);
    return localMyAccountListPageFragment;
  }
  
  private void rebindAdapter()
  {
    if (this.mRecyclerView == null) {
      FinskyLog.w("Recycler view null, ignoring.", new Object[0]);
    }
    do
    {
      return;
      if (this.mAdapter != null) {
        break;
      }
      this.mAdapter = new MyAccountListPageAdapter(this.mDfeApi.getAccount(), this.mContext, this.mDfeList, this.mBitmapLoader, this.mNavigationManager, this, this, this, this.mDfeToc);
      this.mRecyclerView.setAdapter(this.mAdapter);
    } while (this.mRecyclerViewRestoreBundle.isEmpty());
    MyAccountListPageAdapter localMyAccountListPageAdapter2 = this.mAdapter;
    PlayRecyclerView localPlayRecyclerView = this.mRecyclerView;
    Bundle localBundle = this.mRecyclerViewRestoreBundle;
    Parcelable localParcelable = localBundle.getParcelable("recycler_view_parcel");
    if (localParcelable != null) {
      localPlayRecyclerView.restoreInstanceState(localParcelable);
    }
    localMyAccountListPageAdapter2.mSelectionListener.mSelectedPosition = localBundle.getInt("selected_position");
    this.mRecyclerViewRestoreBundle.clear();
    return;
    MyAccountListPageAdapter localMyAccountListPageAdapter1 = this.mAdapter;
    DfeList localDfeList = this.mDfeList;
    localMyAccountListPageAdapter1.mDfeList.removeDataChangedListener(localMyAccountListPageAdapter1);
    localMyAccountListPageAdapter1.mDfeList = localDfeList;
    localMyAccountListPageAdapter1.mDfeList.addDataChangedListener(localMyAccountListPageAdapter1);
    localMyAccountListPageAdapter1.mObservable.notifyChanged();
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  protected final int getDefaultHeaderShadowMode()
  {
    return 3;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968781;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    if (this.mUiElementProto == null) {
      this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(11);
    }
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    rebindActionBar();
    boolean bool1;
    if (this.mIsOrderHistoryPage)
    {
      bool1 = MyAccountHelper.hasMutationOccurredSince(this.mLastRequestTimeMs);
      if (bool1) {
        clearDataSource();
      }
      DfeList localDfeList = this.mDfeList;
      int i = 0;
      if (localDfeList != null)
      {
        boolean bool2 = this.mDfeList.isReady();
        i = 0;
        if (bool2) {
          i = 1;
        }
      }
      if (i == 0) {
        break label122;
      }
      Document localDocument = this.mDfeList.mContainerDocument;
      if (localDocument != null)
      {
        byte[] arrayOfByte = localDocument.mDocument.serverLogsCookie;
        FinskyEventLog.setServerLogCookie(getPlayStoreUiElement(), arrayOfByte);
      }
      rebindAdapter();
    }
    for (;;)
    {
      this.mActionBarController.enableActionBarOverlay();
      return;
      bool1 = false;
      break;
      label122:
      requestData();
      switchToLoading();
    }
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onCancel(Document paramDocument, LibrarySubscriptionEntry paramLibrarySubscriptionEntry)
  {
    CancelSubscriptionDialog.show(this.mFragmentManager, paramDocument, paramLibrarySubscriptionEntry);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mIsOrderHistoryPage = this.mArguments.getBoolean("is_order_history_page");
    this.mBreadcrumb = this.mArguments.getString("title");
    FinskyHeaderListLayout localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mDataView;
    localFinskyHeaderListLayout.configure(new FinskyHeaderListLayout.FinskyConfigurator(localFinskyHeaderListLayout.getContext())
    {
      protected final void addContentView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousLayoutInflater.inflate(2130968783, paramAnonymousViewGroup);
      }
      
      protected final int getListViewId()
      {
        return 2131755590;
      }
    });
    localFinskyHeaderListLayout.setContentViewId(2131755590);
    this.mRecyclerView = ((PlayRecyclerView)this.mDataView.findViewById(2131755590));
    this.mRecyclerView.setSaveEnabled(false);
    Resources localResources = this.mRecyclerView.getResources();
    int i = UiUtils.getGridHorizontalPadding(localResources) + localResources.getDimensionPixelSize(2131493068);
    ViewCompat.setPaddingRelative(this.mRecyclerView, i, this.mRecyclerView.getPaddingTop(), i, this.mRecyclerView.getPaddingBottom());
    LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager();
    this.mRecyclerView.setLayoutManager(localLinearLayoutManager);
    this.mRecyclerView.setAdapter(new EmptyRecyclerViewAdapter());
    this.mLibraries = FinskyApp.get().mLibraries;
    this.mLibraries.addListener(this);
    return localView;
  }
  
  public final void onDestroyView()
  {
    if ((this.mRecyclerView != null) && (this.mAdapter != null))
    {
      MyAccountListPageAdapter localMyAccountListPageAdapter2 = this.mAdapter;
      PlayRecyclerView localPlayRecyclerView = this.mRecyclerView;
      Bundle localBundle = this.mRecyclerViewRestoreBundle;
      localBundle.putParcelable("recycler_view_parcel", localPlayRecyclerView.saveInstanceState());
      localBundle.putInt("selected_position", localMyAccountListPageAdapter2.mSelectionListener.mSelectedPosition);
    }
    this.mRecyclerView = null;
    if (this.mAdapter != null)
    {
      MyAccountListPageAdapter localMyAccountListPageAdapter1 = this.mAdapter;
      localMyAccountListPageAdapter1.mDfeList.removeDataChangedListener(localMyAccountListPageAdapter1);
      this.mAdapter = null;
    }
    if ((this.mDataView instanceof PlayHeaderListLayout)) {
      ((PlayHeaderListLayout)this.mDataView).detachIfNeeded();
    }
    this.mLibraries.removeListener(this);
    super.onDestroyView();
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    if (this.mAdapter != null) {
      this.mAdapter.mObservable.notifyChanged();
    }
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1)
    {
      String str1 = paramBundle.getString("package_name");
      String str2 = paramBundle.getString("refund_account_name");
      AppSupport.silentRefund(this.mFragmentManager, str1, str2, true, new AppSupport.RefundListener()
      {
        public final void onRefundComplete(boolean paramAnonymousBoolean)
        {
          if ((paramAnonymousBoolean) && (MyAccountListPageFragment.this.mAdapter != null)) {
            MyAccountListPageFragment.this.mAdapter.mObservable.notifyChanged();
          }
        }
        
        public final void onRefundStart()
        {
          Toast.makeText(MyAccountListPageFragment.this.mContext, 2131362651, 1).show();
        }
      });
    }
  }
  
  public final void onRefundAction(String paramString1, String paramString2)
  {
    FragmentManagerImpl localFragmentManagerImpl = this.mFragmentManager;
    if (localFragmentManagerImpl.findFragmentByTag("refund_confirm") != null) {
      return;
    }
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageId(2131362812).setPositiveId(2131362937).setNegativeId(2131362370);
    Bundle localBundle = new Bundle();
    localBundle.putString("package_name", paramString1);
    localBundle.putString("refund_account_name", paramString2);
    localBuilder.setCallback(this, 1, localBundle);
    localBuilder.build().show(localFragmentManagerImpl, "refund_confirm");
  }
  
  public final void onRequestRefundAction(Document paramDocument)
  {
    startActivity(RequestRefundActivity.createIntent(this.mDfeApi.getAccountName(), paramDocument));
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mBreadcrumb);
    this.mPageFragmentHost.updateCurrentBackendId(0, true);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews()
  {
    rebindAdapter();
  }
  
  protected final void requestData()
  {
    clearDataSource();
    String str = this.mArguments.getString("list_url");
    this.mDfeList = new DfeList(this.mDfeApi, str, true);
    this.mDfeList.addDataChangedListener(this);
    this.mDfeList.addErrorListener(this);
    this.mDfeList.startLoadItems();
    this.mLastRequestTimeMs = System.currentTimeMillis();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myaccount.MyAccountListPageFragment
 * JD-Core Version:    0.7.0.1
 */