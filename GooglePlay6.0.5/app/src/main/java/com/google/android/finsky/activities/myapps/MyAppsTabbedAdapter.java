package com.google.android.finsky.activities.myapps;

import android.database.DataSetObservable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.ViewPagerTab;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.SelectableUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.libraries.bind.bidi.BidiAwarePagerAdapter;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.utils.PlayUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MyAppsTabbedAdapter
  extends PagerAdapter
  implements BidiAwarePagerAdapter
{
  private final AuthenticatedActivity mAuthenticatedActivity;
  private final BitmapLoader mBitmapLoader;
  private int mCurrentlySelectedLogicalTab;
  private final DfeApi mDfeApi;
  private final DfeToc mDfeToc;
  private final MyAppsTabbedFragment mFragment;
  private final boolean mHasSubscriptions;
  private boolean mIsRtl;
  private final NavigationManager mNavigationManager;
  private PlayStoreUiElementNode mParentNode;
  private final boolean mShowUpdateAllPrompt;
  protected final List<TabData> mTabDataList = new ArrayList();
  private final List<String> mTabTitles;
  
  public MyAppsTabbedAdapter(AuthenticatedActivity paramAuthenticatedActivity, NavigationManager paramNavigationManager, DfeApi paramDfeApi, DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, boolean paramBoolean1, ObjectMap paramObjectMap, MyAppsTabbedFragment paramMyAppsTabbedFragment, boolean paramBoolean2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mAuthenticatedActivity = paramAuthenticatedActivity;
    this.mNavigationManager = paramNavigationManager;
    this.mBitmapLoader = paramBitmapLoader;
    this.mDfeApi = paramDfeApi;
    this.mDfeToc = paramDfeToc;
    this.mHasSubscriptions = paramBoolean1;
    this.mFragment = paramMyAppsTabbedFragment;
    this.mShowUpdateAllPrompt = paramBoolean2;
    this.mCurrentlySelectedLogicalTab = 0;
    this.mParentNode = paramPlayStoreUiElementNode;
    if ((paramObjectMap != null) && (paramObjectMap.containsKey("MyAppsTabbedAdapter.TabBundles"))) {}
    for (List localList = paramObjectMap.getList("MyAppsTabbedAdapter.TabBundles");; localList = null)
    {
      this.mTabDataList.clear();
      if (this.mHasSubscriptions) {
        this.mTabDataList.add(new TabData(0, this.mParentNode, 404));
      }
      this.mTabDataList.add(new TabData(1, this.mParentNode, 405));
      this.mTabDataList.add(new TabData(2, this.mParentNode, 406));
      if ((localList != null) && (localList.size() == this.mTabDataList.size())) {}
      for (int i = 1;; i = 0) {
        for (int j = 0; j < this.mTabDataList.size(); j++) {
          if (i != 0) {
            ((TabData)this.mTabDataList.get(j)).tabBundle = ((ObjectMap)localList.get(j));
          }
        }
      }
      ArrayList localArrayList = new ArrayList();
      if (this.mHasSubscriptions) {
        localArrayList.add(this.mAuthenticatedActivity.getString(2131362353).toUpperCase());
      }
      localArrayList.add(this.mAuthenticatedActivity.getString(2131362351).toUpperCase());
      localArrayList.add(this.mAuthenticatedActivity.getString(2131362352).toUpperCase());
      this.mTabTitles = localArrayList;
      if (!PlayUtils.useLtr(paramAuthenticatedActivity)) {}
      for (boolean bool = true;; bool = false)
      {
        this.mIsRtl = bool;
        return;
      }
    }
  }
  
  private void updatePageSelectionAt(int paramInt)
  {
    TabData localTabData = (TabData)this.mTabDataList.get(paramInt);
    if (localTabData.slidingPanelTab != null) {
      if (this.mCurrentlySelectedLogicalTab != paramInt) {
        break label72;
      }
    }
    label72:
    for (boolean bool = true;; bool = false)
    {
      localTabData.elementNode.setNodeSelected(bool);
      localTabData.slidingPanelTab.setTabSelected(bool);
      if (bool)
      {
        FinskyEventLog.startNewImpression(localTabData.elementNode);
        FinskyEventLog.requestImpressions((ViewGroup)localTabData.slidingPanelTab.getFullView());
      }
      return;
    }
  }
  
  public final void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    int i = BidiPagingHelper.getLogicalPosition(this, paramInt);
    ViewPagerTab localViewPagerTab = (ViewPagerTab)paramObject;
    ((ViewPager)paramViewGroup).removeView(localViewPagerTab.getView(3));
    TabData localTabData = (TabData)this.mTabDataList.get(i);
    localTabData.tabBundle = localTabData.slidingPanelTab.onDestroyView();
    localTabData.slidingPanelTab = null;
    localViewPagerTab.onDestroy();
  }
  
  final boolean finishActiveMode()
  {
    boolean bool = false;
    Iterator localIterator = this.mTabDataList.iterator();
    while (localIterator.hasNext())
    {
      MyAppsTab localMyAppsTab = ((TabData)localIterator.next()).slidingPanelTab;
      if ((localMyAppsTab != null) && (localMyAppsTab.finishActiveMode())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public final void finishUpdate$52bc874c() {}
  
  public final int getCount()
  {
    return this.mTabDataList.size();
  }
  
  public final String getPageTitle(int paramInt)
  {
    return (String)this.mTabTitles.get(paramInt);
  }
  
  public final int getTabType(int paramInt)
  {
    return ((TabData)this.mTabDataList.get(paramInt)).type;
  }
  
  public final Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    int i = BidiPagingHelper.getLogicalPosition(this, paramInt);
    TabData localTabData = (TabData)this.mTabDataList.get(i);
    Object localObject = localTabData.slidingPanelTab;
    if (localObject == null) {
      switch (localTabData.type)
      {
      }
    }
    for (;;)
    {
      localTabData.slidingPanelTab = ((MyAppsTab)localObject);
      paramViewGroup.addView(((MyAppsTab)localObject).getView(3));
      ((MyAppsTab)localObject).onRestoreInstanceState(localTabData.tabBundle);
      if (i == this.mCurrentlySelectedLogicalTab) {
        updatePageSelectionAt(i);
      }
      ((MyAppsTab)localObject).loadData();
      return localObject;
      localObject = new MyAppsInstalledTab(this.mAuthenticatedActivity, this.mDfeApi, this.mDfeToc, this.mNavigationManager, this.mBitmapLoader, this.mShowUpdateAllPrompt, localTabData.elementNode);
      continue;
      AccountLibrary localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(this.mDfeApi.getAccount());
      localObject = new MyAppsLibraryTab(this.mAuthenticatedActivity, this.mDfeApi, this.mDfeToc, this.mNavigationManager, this.mBitmapLoader, this.mFragment, localAccountLibrary, localTabData.elementNode);
      continue;
      localObject = new MyAppsSubscriptionsTab(this.mAuthenticatedActivity, this.mDfeApi, this.mDfeToc, this.mNavigationManager, this.mBitmapLoader, localTabData.elementNode);
    }
  }
  
  public final boolean isRtl()
  {
    return this.mIsRtl;
  }
  
  public final boolean isViewFromObject(View paramView, Object paramObject)
  {
    return ((ViewPagerTab)paramObject).getView(3) == paramView;
  }
  
  public final void onPageSelected(int paramInt)
  {
    this.mCurrentlySelectedLogicalTab = paramInt;
    for (int i = 0; i < this.mTabDataList.size(); i++) {
      updatePageSelectionAt(i);
    }
  }
  
  public final void onSaveInstanceState(ObjectMap paramObjectMap)
  {
    if ((this.mTabDataList == null) || (this.mTabDataList.isEmpty())) {}
    ArrayList localArrayList;
    for (Object localObject = null;; localObject = localArrayList)
    {
      paramObjectMap.put("MyAppsTabbedAdapter.TabBundles", localObject);
      return;
      localArrayList = new ArrayList();
      Iterator localIterator = this.mTabDataList.iterator();
      while (localIterator.hasNext())
      {
        TabData localTabData = (TabData)localIterator.next();
        if (localTabData.slidingPanelTab != null) {
          localArrayList.add(localTabData.slidingPanelTab.onDestroyView());
        } else {
          localArrayList.add(localTabData.tabBundle);
        }
      }
    }
  }
  
  public final void removeLibraryItems(List<String> paramList)
  {
    for (int i = 0; i < this.mTabDataList.size(); i++)
    {
      TabData localTabData = (TabData)this.mTabDataList.get(i);
      if (localTabData.type == 2) {
        ((MyAppsLibraryTab)localTabData.slidingPanelTab).removeItems(paramList);
      }
    }
  }
  
  public final void setRtl(boolean paramBoolean)
  {
    if (this.mIsRtl != paramBoolean)
    {
      this.mIsRtl = paramBoolean;
      this.mObservable.notifyChanged();
    }
  }
  
  public static final class TabData
  {
    public SelectableUiElementNode elementNode;
    public MyAppsTab<?> slidingPanelTab;
    ObjectMap tabBundle;
    public final int type;
    
    public TabData(int paramInt1, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt2)
    {
      this.type = paramInt1;
      this.elementNode = new SelectableUiElementNode(paramInt2, null, paramPlayStoreUiElementNode);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsTabbedAdapter
 * JD-Core Version:    0.7.0.1
 */