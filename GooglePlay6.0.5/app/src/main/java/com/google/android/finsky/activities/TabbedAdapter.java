package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.QuickLinkHelper.QuickLinkInfo;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.SelectableUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.BrowseTab;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.finsky.protos.UserContext;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.libraries.bind.bidi.BidiAwarePagerAdapter;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.utils.PlayUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class TabbedAdapter
  extends PagerAdapter
  implements BidiAwarePagerAdapter
{
  private final ActionBarController mActionBarController;
  private final int mBackendId;
  private final BitmapLoader mBitmapLoader;
  private final ClientMutationCache mClientMutationCache;
  private final Context mContext;
  private int mCurrentlySelectedLogicalTab;
  private final DfeApi mDfeApi;
  private final DfeToc mDfeToc;
  private final boolean mH2oStoreIsActive;
  public boolean mIsRtl;
  boolean mIsViewDestroyed;
  private final LayoutInflater mLayoutInflater;
  private final NavigationManager mNavigationManager;
  private final PlayStoreUiElementNode mParent;
  private final SpacerHeightProvider mSpacerHeightProvider;
  List<TabData> mTabDataList = new ArrayList();
  private final TabDataListener mTabDataListener;
  final TabSelectionTracker mTabSelectionTracker = new TabSelectionTracker((byte)0);
  private final UserContext mUserContext;
  
  public TabbedAdapter(Context paramContext, LayoutInflater paramLayoutInflater, NavigationManager paramNavigationManager, DfeToc paramDfeToc, DfeApi paramDfeApi, ClientMutationCache paramClientMutationCache, UserContext paramUserContext, BitmapLoader paramBitmapLoader, Browse.BrowseTab[] paramArrayOfBrowseTab, Browse.QuickLink[] paramArrayOfQuickLink, int paramInt1, int paramInt2, int paramInt3, ObjectMap paramObjectMap, PlayStoreUiElementNode paramPlayStoreUiElementNode, ActionBarController paramActionBarController, TabDataListener paramTabDataListener, SpacerHeightProvider paramSpacerHeightProvider)
  {
    this.mContext = paramContext;
    this.mLayoutInflater = paramLayoutInflater;
    this.mNavigationManager = paramNavigationManager;
    this.mDfeToc = paramDfeToc;
    this.mDfeApi = paramDfeApi;
    this.mUserContext = paramUserContext;
    this.mClientMutationCache = paramClientMutationCache;
    this.mBitmapLoader = paramBitmapLoader;
    this.mSpacerHeightProvider = paramSpacerHeightProvider;
    this.mBackendId = paramInt3;
    this.mParent = paramPlayStoreUiElementNode;
    this.mCurrentlySelectedLogicalTab = -1;
    this.mActionBarController = paramActionBarController;
    this.mTabDataListener = paramTabDataListener;
    this.mH2oStoreIsActive = FinskyApp.get().getExperiments().isH20StoreEnabled();
    this.mTabDataList.clear();
    int i = 0;
    if (i < paramArrayOfBrowseTab.length)
    {
      Browse.BrowseTab localBrowseTab2 = paramArrayOfBrowseTab[i];
      boolean bool2;
      label159:
      TabData localTabData5;
      if (localBrowseTab2.category.length > 0)
      {
        bool2 = true;
        localTabData5 = new TabData(localBrowseTab2, bool2);
        if (!bool2) {
          break label229;
        }
      }
      label229:
      for (int i11 = 401;; i11 = 403)
      {
        localTabData5.elementNode = new SelectableUiElementNode(i11, localBrowseTab2.serverLogsCookie, this.mParent);
        this.mTabDataList.add(localTabData5);
        i++;
        break;
        bool2 = false;
        break label159;
      }
    }
    if ((paramObjectMap != null) && (paramObjectMap.containsKey("TabbedAdapter.TabInstanceStates"))) {}
    for (List localList1 = paramObjectMap.getList("TabbedAdapter.TabInstanceStates");; localList1 = null)
    {
      List localList3;
      if ((paramObjectMap != null) && (paramObjectMap.containsKey("TabbedAdapter.TabDfeLists")))
      {
        localList3 = paramObjectMap.getList("TabbedAdapter.TabDfeLists");
        if (localList3 != null)
        {
          Iterator localIterator3 = localList3.iterator();
          while (localIterator3.hasNext())
          {
            MultiDfeList localMultiDfeList = (MultiDfeList)localIterator3.next();
            if (localMultiDfeList != null)
            {
              DfeApi localDfeApi = this.mDfeApi;
              if (localMultiDfeList.mContainerList != null) {
                localMultiDfeList.mContainerList.setDfeApi(localDfeApi);
              }
              Iterator localIterator4 = localMultiDfeList.mClusterData.values().iterator();
              while (localIterator4.hasNext())
              {
                DfeList localDfeList = (DfeList)localIterator4.next();
                if (localDfeList != null) {
                  localDfeList.setDfeApi(localDfeApi);
                }
              }
            }
          }
        }
      }
      for (List localList2 = localList3;; localList2 = null)
      {
        int j;
        if ((localList2 != null) && (localList2.size() == this.mTabDataList.size()))
        {
          j = 1;
          if ((localList1 == null) || (localList1.size() != this.mTabDataList.size())) {
            break label545;
          }
        }
        label545:
        for (int k = 1;; k = 0)
        {
          for (int m = 0; m < paramArrayOfBrowseTab.length; m++)
          {
            TabData localTabData4 = (TabData)this.mTabDataList.get(m);
            if (j != 0) {
              localTabData4.multiDfeList = ((MultiDfeList)localList2.get(m));
            }
            if (k != 0) {
              localTabData4.instanceState = ((ObjectMap)localList1.get(m));
            }
          }
          j = 0;
          break;
        }
        int i8;
        if ((this.mH2oStoreIsActive) && (paramArrayOfQuickLink.length == 0)) {
          i8 = 0;
        }
        ArrayList localArrayList4;
        for (;;)
        {
          if (i8 < paramArrayOfBrowseTab.length)
          {
            Browse.BrowseTab localBrowseTab1 = paramArrayOfBrowseTab[i8];
            TabData localTabData3 = (TabData)this.mTabDataList.get(i8);
            ArrayList localArrayList5 = new ArrayList();
            for (Browse.QuickLink localQuickLink2 : localBrowseTab1.quickLink) {
              localArrayList5.add(new QuickLinkHelper.QuickLinkInfo(localQuickLink2, localQuickLink2.backendId));
            }
            localTabData3.quickLinks = ((QuickLinkHelper.QuickLinkInfo[])localArrayList5.toArray(new QuickLinkHelper.QuickLinkInfo[localBrowseTab1.quickLink.length]));
            i8++;
            continue;
            if (!this.mTabDataList.isEmpty())
            {
              if ((paramArrayOfQuickLink == null) || (paramArrayOfQuickLink.length <= 0)) {
                break label1273;
              }
              localArrayList4 = new ArrayList();
              int i6 = paramArrayOfQuickLink.length;
              for (int i7 = 0; i7 < i6; i7++)
              {
                Browse.QuickLink localQuickLink1 = paramArrayOfQuickLink[i7];
                localArrayList4.add(new QuickLinkHelper.QuickLinkInfo(localQuickLink1, localQuickLink1.backendId));
              }
            }
          }
        }
        label1190:
        label1235:
        label1266:
        label1273:
        for (ArrayList localArrayList1 = localArrayList4;; localArrayList1 = null)
        {
          ArrayList localArrayList2;
          ArrayList localArrayList3;
          Context localContext;
          int i1;
          if ((localArrayList1 != null) && (localArrayList1.size() > 0))
          {
            localArrayList2 = new ArrayList();
            localArrayList3 = new ArrayList();
            localContext = this.mContext;
            Iterator localIterator1 = localArrayList1.iterator();
            i1 = 0;
            if (localIterator1.hasNext())
            {
              QuickLinkHelper.QuickLinkInfo localQuickLinkInfo2 = (QuickLinkHelper.QuickLinkInfo)localIterator1.next();
              if (!localQuickLinkInfo2.mQuickLink.displayRequired) {
                break label1266;
              }
              localArrayList2.add(localQuickLinkInfo2);
            }
          }
          for (int i5 = i1 + 1;; i5 = i1)
          {
            i1 = i5;
            break;
            int i2 = UiUtils.getStreamQuickLinkColumnCount(localContext.getResources(), i1, localArrayList1.size() - i1);
            int i3 = i2 * (int)Math.ceil(i1 / i2) - i1;
            Iterator localIterator2 = localArrayList1.iterator();
            int i4 = i3;
            while (localIterator2.hasNext())
            {
              QuickLinkHelper.QuickLinkInfo localQuickLinkInfo1 = (QuickLinkHelper.QuickLinkInfo)localIterator2.next();
              if (!localQuickLinkInfo1.mQuickLink.displayRequired) {
                if (i4 > 0)
                {
                  localArrayList2.add(localQuickLinkInfo1);
                  i4--;
                }
                else
                {
                  localArrayList3.add(localQuickLinkInfo1);
                }
              }
            }
            ((TabData)this.mTabDataList.get(paramInt1)).quickLinks = ((QuickLinkHelper.QuickLinkInfo[])localArrayList2.toArray(new QuickLinkHelper.QuickLinkInfo[localArrayList2.size()]));
            if (!this.mH2oStoreIsActive) {
              if (paramInt2 == -1) {
                break label1190;
              }
            }
            TabData localTabData1;
            QuickLinkHelper.QuickLinkInfo[] arrayOfQuickLinkInfo;
            for (TabData localTabData2 = (TabData)this.mTabDataList.get(paramInt2);; localTabData2 = null)
            {
              if (localTabData2 != null) {
                localTabData2.quickLinks = ((QuickLinkHelper.QuickLinkInfo[])localArrayList3.toArray(new QuickLinkHelper.QuickLinkInfo[localArrayList3.size()]));
              }
              if ((this.mTabDataList.size() != 0) || (paramArrayOfQuickLink == null) || (paramArrayOfQuickLink.length <= 0)) {
                break label1235;
              }
              localTabData1 = new TabData(new Browse.BrowseTab(), true);
              arrayOfQuickLinkInfo = new QuickLinkHelper.QuickLinkInfo[paramArrayOfQuickLink.length];
              for (int n = 0; n < paramArrayOfQuickLink.length; n++) {
                arrayOfQuickLinkInfo[n] = new QuickLinkHelper.QuickLinkInfo(paramArrayOfQuickLink[n], this.mBackendId);
              }
            }
            localTabData1.quickLinks = arrayOfQuickLinkInfo;
            localTabData1.elementNode = new SelectableUiElementNode(401, null, this.mParent);
            this.mTabDataList.add(localTabData1);
            if (!PlayUtils.useLtr(this.mContext)) {}
            for (boolean bool1 = true;; bool1 = false)
            {
              this.mIsRtl = bool1;
              this.mIsViewDestroyed = false;
              return;
            }
          }
        }
      }
    }
  }
  
  public final void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    int i = BidiPagingHelper.getLogicalPosition(this, paramInt);
    ViewPagerTab localViewPagerTab = (ViewPagerTab)paramObject;
    paramViewGroup.removeView(localViewPagerTab.getView(this.mBackendId));
    TabData localTabData = (TabData)this.mTabDataList.get(i);
    if (localTabData.multiDfeList != null) {
      localTabData.multiDfeList.flushData();
    }
    if (!this.mIsViewDestroyed) {
      localTabData.instanceState = localTabData.viewPagerTab.onDestroyView();
    }
    localTabData.viewPagerTab = null;
    localViewPagerTab.onDestroy();
  }
  
  public final void finishUpdate$52bc874c() {}
  
  public final int getCount()
  {
    return this.mTabDataList.size();
  }
  
  final ArrayList<MultiDfeList> getMultiDfeLists()
  {
    Object localObject;
    if ((this.mTabDataList == null) || (this.mTabDataList.isEmpty())) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = new ArrayList();
      Iterator localIterator = this.mTabDataList.iterator();
      while (localIterator.hasNext()) {
        ((ArrayList)localObject).add(((TabData)localIterator.next()).multiDfeList);
      }
    }
  }
  
  public final String getPageTitle(int paramInt)
  {
    if (paramInt >= this.mTabDataList.size()) {
      return "";
    }
    return ((TabData)this.mTabDataList.get(paramInt)).browseTab.title.toUpperCase();
  }
  
  public final float getPageWidth(int paramInt)
  {
    if ((paramInt == 0) && (this.mTabDataList.size() > 1) && (((TabData)this.mTabDataList.get(0)).isCategoryTab)) {
      return this.mContext.getResources().getInteger(2131623949) / 100.0F;
    }
    return 1.0F;
  }
  
  public final Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    int i = BidiPagingHelper.getLogicalPosition(this, paramInt);
    TabData localTabData = (TabData)this.mTabDataList.get(i);
    Browse.BrowseTab localBrowseTab = localTabData.browseTab;
    boolean bool1 = this.mTabSelectionTracker.mShouldDeferListTabDataDisplay;
    int j;
    Object localObject;
    if (getCount() > 1)
    {
      j = 0;
      if (!localTabData.isCategoryTab) {
        break label177;
      }
      localObject = new CategoryTab(this.mContext, this.mNavigationManager, this.mBitmapLoader, this.mLayoutInflater, localTabData, this.mDfeToc, j);
      label89:
      ((ViewPagerTab)localObject).onRestoreInstanceState(localTabData.instanceState);
      if (this.mCurrentlySelectedLogicalTab != i) {
        break label316;
      }
    }
    label177:
    label316:
    for (boolean bool2 = true;; bool2 = false)
    {
      ((ViewPagerTab)localObject).setTabSelected(bool2);
      localTabData.viewPagerTab = ((ViewPagerTab)localObject);
      paramViewGroup.addView(((ViewPagerTab)localObject).getView(this.mBackendId));
      if ((bool1) && ((localObject instanceof ListTab))) {
        TabSelectionTracker.access$200(this.mTabSelectionTracker, (ListTab)localObject);
      }
      return localObject;
      j = 2;
      break;
      if (localTabData.multiDfeList == null) {
        localTabData.multiDfeList = new MultiDfeList(new DfeList(this.mDfeApi, localBrowseTab.listUrl, true, this.mUserContext));
      }
      Context localContext = this.mContext;
      NavigationManager localNavigationManager = this.mNavigationManager;
      BitmapLoader localBitmapLoader = this.mBitmapLoader;
      DfeApi localDfeApi = this.mDfeApi;
      LayoutInflater localLayoutInflater = this.mLayoutInflater;
      DfeToc localDfeToc = this.mDfeToc;
      ClientMutationCache localClientMutationCache = this.mClientMutationCache;
      ActionBarController localActionBarController = this.mActionBarController;
      TabDataListener localTabDataListener = this.mTabDataListener;
      SpacerHeightProvider localSpacerHeightProvider = this.mSpacerHeightProvider;
      localObject = new ListTab(localContext, localNavigationManager, localBitmapLoader, localDfeApi, localLayoutInflater, localTabData, localDfeToc, localClientMutationCache, bool1, localActionBarController, j, localTabDataListener, localSpacerHeightProvider);
      break label89;
    }
  }
  
  public final boolean isRtl()
  {
    return this.mIsRtl;
  }
  
  public final boolean isViewFromObject(View paramView, Object paramObject)
  {
    return ((ViewPagerTab)paramObject).getView(this.mBackendId) == paramView;
  }
  
  public final void setPrimaryItem$30510aeb(int paramInt, Object paramObject)
  {
    for (int i = 0; i < this.mTabDataList.size(); i++)
    {
      TabData localTabData2 = (TabData)this.mTabDataList.get(i);
      if ((localTabData2.viewPagerTab != null) && (i != paramInt)) {
        localTabData2.viewPagerTab.setTabSelected(false);
      }
    }
    TabData localTabData1 = (TabData)this.mTabDataList.get(paramInt);
    if (localTabData1.viewPagerTab != null) {
      localTabData1.viewPagerTab.setTabSelected(true);
    }
    this.mCurrentlySelectedLogicalTab = paramInt;
  }
  
  public final void setRtl(boolean paramBoolean)
  {
    if (this.mIsRtl != paramBoolean)
    {
      this.mIsRtl = paramBoolean;
      this.mObservable.notifyChanged();
    }
  }
  
  static final class TabData
  {
    public final Browse.BrowseTab browseTab;
    public SelectableUiElementNode elementNode;
    ObjectMap instanceState;
    public final boolean isCategoryTab;
    public MultiDfeList multiDfeList;
    public QuickLinkHelper.QuickLinkInfo[] quickLinks;
    public ViewPagerTab viewPagerTab;
    
    public TabData(Browse.BrowseTab paramBrowseTab, boolean paramBoolean)
    {
      this.browseTab = paramBrowseTab;
      this.isCategoryTab = paramBoolean;
    }
  }
  
  public static abstract interface TabDataListener
  {
    public abstract void onTabDataReady(TabbedBrowseFragment.BackgroundViewConfigurator paramBackgroundViewConfigurator);
  }
  
  private static final class TabSelectionTracker
    implements Runnable
  {
    private int mCurrScrollState = 0;
    private List<ListTab> mDeferredTabs = new ArrayList();
    private Handler mHandler = new Handler();
    boolean mShouldDeferListTabDataDisplay;
    
    private void exitDeferredDataDisplayMode()
    {
      Utils.ensureOnMainThread();
      Iterator localIterator = this.mDeferredTabs.iterator();
      while (localIterator.hasNext())
      {
        ListTab localListTab = (ListTab)localIterator.next();
        localListTab.mShouldDeferDataDisplay = false;
        localListTab.syncViewToState();
      }
      this.mDeferredTabs.clear();
      this.mShouldDeferListTabDataDisplay = false;
    }
    
    public final void run()
    {
      if (this.mCurrScrollState == 0) {
        exitDeferredDataDisplayMode();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.TabbedAdapter
 * JD-Core Version:    0.7.0.1
 */