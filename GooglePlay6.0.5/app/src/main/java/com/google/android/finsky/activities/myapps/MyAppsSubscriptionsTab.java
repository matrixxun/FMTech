package com.google.android.finsky.activities.myapps;

import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeBulkDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.SelectableUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibraryInAppSubscriptionEntry;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class MyAppsSubscriptionsTab
  extends MyAppsTab<DfeBulkDetails>
{
  private MyAppsSubscriptionsAdapter mAdapter;
  private boolean mAdapterInitialized = false;
  private ObjectMap mRestoredInstanceState = ObjectMap.EMPTY;
  Map<String, LibraryInAppSubscriptionEntry> mSubscriptionsInLibrary = new HashMap();
  private ListView mSubscriptionsListView;
  private ViewGroup mSubscriptionsView;
  
  public MyAppsSubscriptionsTab(AuthenticatedActivity paramAuthenticatedActivity, DfeApi paramDfeApi, DfeToc paramDfeToc, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, SelectableUiElementNode paramSelectableUiElementNode)
  {
    super(paramAuthenticatedActivity, paramDfeApi, paramDfeToc, paramNavigationManager);
    this.mAdapter = new MyAppsSubscriptionsAdapter(paramAuthenticatedActivity, this.mLayoutInflater, paramBitmapLoader, this, paramSelectableUiElementNode);
  }
  
  protected final MyAppsListAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  protected final Document getDocumentForView(View paramView)
  {
    return (Document)paramView.getTag();
  }
  
  protected final View getFullView()
  {
    return this.mSubscriptionsView;
  }
  
  protected final ListView getListView()
  {
    return this.mSubscriptionsListView;
  }
  
  public final View getView(int paramInt)
  {
    if (this.mSubscriptionsView == null) {
      this.mSubscriptionsView = ((ViewGroup)this.mLayoutInflater.inflate(2130968846, null));
    }
    return this.mSubscriptionsView;
  }
  
  public final void onDataChanged()
  {
    super.onDataChanged();
    List localList = ((DfeBulkDetails)this.mDfeModel).getDocuments();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    Iterator localIterator1 = localList.iterator();
    while (localIterator1.hasNext())
    {
      Document localDocument3 = (Document)localIterator1.next();
      switch (localDocument3.mDocument.docType)
      {
      default: 
        break;
      case 1: 
        localHashMap2.put(localDocument3.mDocument.docid, localDocument3);
        break;
      case 15: 
        localHashMap1.put(localDocument3.mDocument.docid, localDocument3);
      }
    }
    Iterator localIterator2 = this.mSubscriptionsInLibrary.entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator2.next();
      String str1 = (String)localEntry.getKey();
      Document localDocument1 = (Document)localHashMap1.get(str1);
      String str2 = DocUtils.getPackageNameForSubscription(str1);
      Document localDocument2 = (Document)localHashMap2.get(str2);
      if (localDocument1 == null)
      {
        FinskyLog.w("Subscription %s is unavailable, ignoring this entry", new Object[] { str1 });
      }
      else if (localDocument2 == null)
      {
        FinskyLog.w("Parent app %s of subscription %s is unavailable, ignoring this entry", new Object[] { str2, str1 });
      }
      else
      {
        MyAppsSubscriptionsAdapter localMyAppsSubscriptionsAdapter = this.mAdapter;
        LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry = (LibraryInAppSubscriptionEntry)localEntry.getValue();
        localMyAppsSubscriptionsAdapter.mSubscriptions.add(new MyAppsSubscriptionsAdapter.MyAppsSubscriptionEntry(localMyAppsSubscriptionsAdapter, localDocument1, localDocument2, localLibraryInAppSubscriptionEntry));
        localMyAppsSubscriptionsAdapter.notifyDataSetChanged();
      }
    }
    Collections.sort(this.mAdapter.mSubscriptions, MyAppsSubscriptionsAdapter.sSubscriptionAbcSorter);
    if (!this.mAdapterInitialized)
    {
      this.mSubscriptionsListView = ((ListView)this.mSubscriptionsView.findViewById(2131755738));
      int i = UiUtils.getGridHorizontalPadding(this.mSubscriptionsListView.getResources());
      ViewCompat.setPaddingRelative(this.mSubscriptionsListView, i, this.mSubscriptionsListView.getPaddingTop(), i, this.mSubscriptionsListView.getPaddingBottom());
      this.mSubscriptionsListView.setAdapter(this.mAdapter);
      this.mSubscriptionsListView.setItemsCanFocus(true);
      if (this.mRestoredInstanceState.containsKey("MyAppsTab.KeyListParcel")) {
        this.mSubscriptionsListView.onRestoreInstanceState((Parcelable)this.mRestoredInstanceState.get("MyAppsTab.KeyListParcel"));
      }
      this.mAdapterInitialized = true;
      this.mSubscriptionsListView.setRecyclerListener(this.mAdapter);
    }
    syncViewToState();
  }
  
  public final ObjectMap onDestroyView()
  {
    ObjectMap localObjectMap = new ObjectMap();
    if (this.mSubscriptionsListView != null)
    {
      localObjectMap.put("MyAppsTab.KeyListParcel", this.mSubscriptionsListView.onSaveInstanceState());
      this.mSubscriptionsListView.setRecyclerListener(null);
    }
    return localObjectMap;
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 6) || (paramInt1 == 8)) {
      this.mAdapter.notifyDataSetChanged();
    }
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    requestData();
  }
  
  public final void onRestoreInstanceState(ObjectMap paramObjectMap)
  {
    if (paramObjectMap != null) {
      this.mRestoredInstanceState = paramObjectMap;
    }
  }
  
  protected final void requestData()
  {
    clearState();
    this.mSubscriptionsInLibrary.clear();
    MyAppsSubscriptionsAdapter localMyAppsSubscriptionsAdapter = this.mAdapter;
    localMyAppsSubscriptionsAdapter.mSubscriptions.clear();
    localMyAppsSubscriptionsAdapter.notifyDataSetChanged();
    Libraries localLibraries = FinskyApp.get().mLibraries;
    HashSet localHashSet = new HashSet();
    AccountLibrary localAccountLibrary1 = localLibraries.getAccountLibrary(this.mDfeApi.getAccount());
    Iterator localIterator1 = localLibraries.getAccountLibraries().iterator();
    while (localIterator1.hasNext())
    {
      AccountLibrary localAccountLibrary2 = (AccountLibrary)localIterator1.next();
      if (localAccountLibrary2 != localAccountLibrary1)
      {
        Iterator localIterator3 = localAccountLibrary2.getInAppSubscriptionsList().iterator();
        while (localIterator3.hasNext())
        {
          LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry2 = (LibraryInAppSubscriptionEntry)localIterator3.next();
          String str2 = localLibraryInAppSubscriptionEntry2.mDocId;
          this.mSubscriptionsInLibrary.put(str2, localLibraryInAppSubscriptionEntry2);
          localHashSet.add(str2);
          localHashSet.add(DocUtils.getPackageNameForSubscription(str2));
        }
      }
    }
    Iterator localIterator2 = localAccountLibrary1.getInAppSubscriptionsList().iterator();
    while (localIterator2.hasNext())
    {
      LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry1 = (LibraryInAppSubscriptionEntry)localIterator2.next();
      String str1 = localLibraryInAppSubscriptionEntry1.mDocId;
      this.mSubscriptionsInLibrary.put(str1, localLibraryInAppSubscriptionEntry1);
      localHashSet.add(str1);
      localHashSet.add(DocUtils.getPackageNameForSubscription(str1));
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(localHashSet);
    this.mDfeModel = new DfeBulkDetails(this.mDfeApi, localArrayList, false);
    ((DfeBulkDetails)this.mDfeModel).addDataChangedListener(this);
    ((DfeBulkDetails)this.mDfeModel).addErrorListener(this);
  }
  
  protected final void retryFromError()
  {
    requestData();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsSubscriptionsTab
 * JD-Core Version:    0.7.0.1
 */