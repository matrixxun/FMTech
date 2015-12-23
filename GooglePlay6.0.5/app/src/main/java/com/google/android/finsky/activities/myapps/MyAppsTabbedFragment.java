package com.google.android.finsky.activities.myapps;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.activities.MultiInstallActivity;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeBulkDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.billing.ProgressDialogFragment;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.ModifyLibrary.ModifyLibraryResponse;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.transition.PageFade;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.headerlist.PlayHeaderListTabStrip;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MyAppsTabbedFragment
  extends PageFragment
  implements ViewPager.OnPageChangeListener, SimpleAlertDialog.Listener
{
  private String mBreadcrumb;
  private ObjectMap mFragmentObjectMap = new ObjectMap();
  Installer mInstaller;
  ProgressDialogFragment mProgressDialog;
  MyAppsTabbedAdapter mTabbedAdapter;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(9);
  private ViewPager mViewPager;
  
  private void clearState()
  {
    if (this.mDataView != null) {
      ((PlayHeaderListLayout)this.mDataView).setOnPageChangeListener(null);
    }
    if (this.mViewPager != null)
    {
      this.mViewPager.setAdapter(null);
      this.mViewPager = null;
    }
    this.mTabbedAdapter = null;
  }
  
  private void dismissProgressDialog()
  {
    if (this.mProgressDialog != null)
    {
      this.mProgressDialog.dismissInternal(false);
      this.mProgressDialog = null;
    }
  }
  
  static ArrayList<String> getDocIdList(List<Document> paramList)
  {
    ArrayList localArrayList = Lists.newArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((Document)localIterator.next()).mDocument.docid);
    }
    return localArrayList;
  }
  
  public static MyAppsTabbedFragment newInstance(DfeToc paramDfeToc, boolean paramBoolean)
  {
    MyAppsTabbedFragment localMyAppsTabbedFragment = new MyAppsTabbedFragment();
    localMyAppsTabbedFragment.setArgument("finsky.PageFragment.toc", paramDfeToc);
    localMyAppsTabbedFragment.setArgument("trigger_update_all", paramBoolean);
    return localMyAppsTabbedFragment;
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  public final int getActionBarColor()
  {
    return CorpusResourceUtils.getPrimaryColor(getActivity(), 3);
  }
  
  @TargetApi(22)
  protected final Transition getCustomExitTransition()
  {
    return new PageFade(3);
  }
  
  protected final int getLayoutRes()
  {
    return 2130968781;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    String str = CorpusResourceUtils.getCorpusMyCollectionDescription(3);
    if (TextUtils.isEmpty(str)) {
      str = this.mContext.getString(2131362354);
    }
    this.mBreadcrumb = str;
    this.mInstaller = FinskyApp.get().mInstaller;
    FinskyApp.get().mNotificationHelper.hideUpdatesAvailableMessage();
    rebindViews();
    this.mActionBarController.enableActionBarOverlay();
  }
  
  public final boolean onBackPressed()
  {
    return this.mTabbedAdapter.finishActiveMode();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContentFrame localContentFrame = (ContentFrame)super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    FinskyHeaderListLayout localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mDataView;
    localFinskyHeaderListLayout.configure(new FinskyHeaderListLayout.FinskyConfigurator(localFinskyHeaderListLayout.getContext())
    {
      protected final void addContentView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousLayoutInflater.inflate(2130968782, paramAnonymousViewGroup);
      }
      
      protected final int getHeaderHeight()
      {
        return FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(this.mContext, 0);
      }
      
      protected final int getHeaderMode()
      {
        return 1;
      }
      
      protected final int getListViewId()
      {
        return 2131755738;
      }
      
      protected final int getTabMode()
      {
        return 0;
      }
      
      protected final int getTabPaddingMode()
      {
        return 1;
      }
      
      protected final int getViewPagerId()
      {
        return 2131755589;
      }
      
      protected final boolean hasViewPager()
      {
        return true;
      }
    });
    localFinskyHeaderListLayout.setContentViewId(2131755589);
    return localContentFrame;
  }
  
  public final void onDestroyView()
  {
    if (this.mTabbedAdapter != null) {
      this.mTabbedAdapter.onSaveInstanceState(this.mFragmentObjectMap);
    }
    if (this.mViewPager != null)
    {
      int i = BidiPagingHelper.getLogicalPosition(this.mTabbedAdapter, this.mViewPager.getCurrentItem());
      this.mFragmentObjectMap.putInt("MyAppsTabbedAdapter.CurrentTabType", i);
    }
    this.mTabbedAdapter.finishActiveMode();
    clearState();
    if ((this.mDataView instanceof PlayHeaderListLayout)) {
      ((PlayHeaderListLayout)this.mDataView).detachIfNeeded();
    }
    SimpleAlertDialog localSimpleAlertDialog = (SimpleAlertDialog)this.mFragmentManager.findFragmentByTag("archive_confirm");
    if (localSimpleAlertDialog != null) {
      localSimpleAlertDialog.dismissInternal(true);
    }
    super.onDestroyView();
  }
  
  public final void onEnterActionBarSearchMode() {}
  
  public final void onExitActionBarSearchMode() {}
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPageScrollStateChanged(int paramInt) {}
  
  public final void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
  
  public final void onPageSelected(int paramInt)
  {
    int i = BidiPagingHelper.getLogicalPosition(this.mTabbedAdapter, paramInt);
    this.mTabbedAdapter.onPageSelected(i);
    String str = this.mTabbedAdapter.getPageTitle(i);
    if (!TextUtils.isEmpty(str)) {
      UiUtils.sendAccessibilityEventWithText(this.mContext, this.mContext.getString(2131361816, new Object[] { str }), this.mViewPager);
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Unexpected requestCode %d", arrayOfObject);
      return;
    }
    final ArrayList localArrayList = paramBundle.getStringArrayList("docid_list");
    this.mTabbedAdapter.finishActiveMode();
    FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      localFinskyEventLog.logBackgroundEvent(506, (String)localArrayList.get(j), null, 0, null, null);
    }
    this.mProgressDialog = ProgressDialogFragment.newInstance(2131361857);
    this.mProgressDialog.show(this.mFragmentManager, "progress_dialog");
    this.mDfeApi.archiveFromLibrary(localArrayList, AccountLibrary.LIBRARY_ID_APPS, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        if (MyAppsTabbedFragment.this.canChangeFragmentManagerState())
        {
          MyAppsTabbedFragment.this.dismissProgressDialog();
          ErrorDialog.show(MyAppsTabbedFragment.this.mFragmentManager, null, ErrorStrings.get(MyAppsTabbedFragment.this.getActivity(), paramAnonymousVolleyError), false);
        }
      }
    });
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mProgressDialog = ((ProgressDialogFragment)this.mFragmentManager.findFragmentByTag("progress_dialog"));
    dismissProgressDialog();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mBreadcrumb);
    this.mPageFragmentHost.updateCurrentBackendId(3, true);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  public final void rebindViews()
  {
    this.mLayoutSwitcher.switchToDataMode();
    rebindActionBar();
    if ((this.mViewPager != null) && (this.mTabbedAdapter != null)) {
      return;
    }
    boolean bool = FinskyApp.get().mLibraries.hasSubscriptions();
    this.mTabbedAdapter = new MyAppsTabbedAdapter((AuthenticatedActivity)getActivity(), this.mNavigationManager, this.mDfeApi, this.mDfeToc, this.mBitmapLoader, bool, this.mFragmentObjectMap, this, this.mArguments.getBoolean("trigger_update_all"), this);
    this.mViewPager = ((ViewPager)this.mDataView.findViewById(2131755589));
    if (this.mViewPager != null)
    {
      this.mViewPager.setAdapter(this.mTabbedAdapter);
      this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(2131493528));
      PlayHeaderListLayout localPlayHeaderListLayout = (PlayHeaderListLayout)this.mDataView;
      localPlayHeaderListLayout.mTabStrip.notifyPagerAdapterChanged();
      localPlayHeaderListLayout.setOnPageChangeListener(this);
      localPlayHeaderListLayout.setFloatingControlsBackground(new ColorDrawable(CorpusResourceUtils.getPrimaryColor(getActivity(), 3)));
    }
    int i;
    if (this.mFragmentObjectMap.containsKey("MyAppsTabbedAdapter.CurrentTabType")) {
      i = this.mFragmentObjectMap.getInt("MyAppsTabbedAdapter.CurrentTabType");
    }
    for (int j = 0;; j++)
    {
      int k = this.mTabbedAdapter.getCount();
      int m = 0;
      if (j < k)
      {
        if (this.mTabbedAdapter.getTabType(j) == i) {
          m = j;
        }
      }
      else
      {
        int n = BidiPagingHelper.getVisualPosition(this.mTabbedAdapter, m);
        this.mViewPager.setCurrentItem(n, false);
        return;
        i = 1;
        break;
      }
    }
  }
  
  protected final void requestData()
  {
    clearState();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsTabbedFragment
 * JD-Core Version:    0.7.0.1
 */