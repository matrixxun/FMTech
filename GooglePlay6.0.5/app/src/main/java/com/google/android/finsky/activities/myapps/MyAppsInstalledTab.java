package com.google.android.finsky.activities.myapps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiWayUpdateController;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.layout.play.PlayCardViewMyAppsDownloading;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.Installer.InstallerProgressReport;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MyAppsInstalledTab
  extends MyAppsTab<MultiWayUpdateController>
  implements MyAppsInstalledAdapter.BucketsChangedListener
{
  private static String KEY_ALREADY_SHOWN_UPDATE_ALL_PROMPT = "already_shown_update_all_prompt";
  private MyAppsInstalledAdapter mAdapter;
  private Map<String, List<String>> mDocIdsByAccount = new HashMap();
  private ViewGroup mInstalledView;
  private boolean mListInitialized;
  private ListView mMyAppsList;
  private ObjectMap mRestoredInstanceState = ObjectMap.EMPTY;
  private boolean mShowUpdateAllPrompt;
  private boolean mUpdateAllPromptShown = false;
  
  public MyAppsInstalledTab(AuthenticatedActivity paramAuthenticatedActivity, DfeApi paramDfeApi, DfeToc paramDfeToc, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramAuthenticatedActivity, paramDfeApi, paramDfeToc, paramNavigationManager);
    this.mAdapter = new MyAppsInstalledAdapter(paramAuthenticatedActivity, this.mInstaller, FinskyApp.get().mInstallPolicies, FinskyApp.get().mAppStates, paramBitmapLoader, this, this, paramPlayStoreUiElementNode);
    this.mShowUpdateAllPrompt = paramBoolean;
  }
  
  protected final MyAppsListAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  protected final Document getDocumentForView(View paramView)
  {
    return MyAppsInstalledAdapter.getViewDoc(paramView);
  }
  
  protected final View getFullView()
  {
    return this.mInstalledView;
  }
  
  protected final ListView getListView()
  {
    return this.mMyAppsList;
  }
  
  public final View getView(int paramInt)
  {
    if (this.mInstalledView == null) {
      this.mInstalledView = ((ViewGroup)this.mLayoutInflater.inflate(2130968842, null));
    }
    return this.mInstalledView;
  }
  
  public final void onDataChanged()
  {
    super.onDataChanged();
    List localList = ((MultiWayUpdateController)this.mDfeModel).mCollatedDocuments;
    if (localList != null)
    {
      MyAppsInstalledAdapter localMyAppsInstalledAdapter = this.mAdapter;
      localMyAppsInstalledAdapter.mUnsortedDocuments.clear();
      localMyAppsInstalledAdapter.mUnsortedDocuments.addAll(localList);
      localMyAppsInstalledAdapter.notifyDataSetChanged();
      InstallPolicies localInstallPolicies = FinskyApp.get().mInstallPolicies;
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        localInstallPolicies.captureEverExternallyHosted((Document)localIterator.next());
      }
    }
    if (!this.mListInitialized)
    {
      this.mInstalledView.findViewById(2131755697).setVisibility(8);
      this.mMyAppsList = ((ListView)this.mInstalledView.findViewById(2131755738));
      int i = UiUtils.getGridHorizontalPadding(this.mMyAppsList.getResources());
      ViewCompat.setPaddingRelative(this.mMyAppsList, i, this.mMyAppsList.getPaddingTop(), i, this.mMyAppsList.getPaddingBottom());
      this.mMyAppsList.setAdapter(this.mAdapter);
      this.mMyAppsList.setItemsCanFocus(true);
      this.mListInitialized = true;
      if (this.mRestoredInstanceState.containsKey("MyAppsTab.KeyListParcel")) {
        this.mMyAppsList.onRestoreInstanceState((Parcelable)this.mRestoredInstanceState.get("MyAppsTab.KeyListParcel"));
      }
      configureEmptyUI(false, 2131362112);
      this.mMyAppsList.setRecyclerListener(this.mAdapter);
    }
    syncViewToState();
    if ((this.mShowUpdateAllPrompt) && (!this.mUpdateAllPromptShown))
    {
      this.mAdapter.updateAllDocs();
      this.mUpdateAllPromptShown = true;
    }
  }
  
  public final ObjectMap onDestroyView()
  {
    ObjectMap localObjectMap = new ObjectMap();
    if (this.mMyAppsList != null) {
      localObjectMap.put("MyAppsTab.KeyListParcel", this.mMyAppsList.onSaveInstanceState());
    }
    String str = KEY_ALREADY_SHOWN_UPDATE_ALL_PROMPT;
    boolean bool = this.mUpdateAllPromptShown;
    localObjectMap.mHashmap.put(str, Boolean.valueOf(bool));
    return localObjectMap;
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    MyAppsInstalledAdapter localMyAppsInstalledAdapter;
    ListView localListView;
    int j;
    Document localDocument2;
    if ((paramInt1 == 1) || (paramInt1 == 4))
    {
      localMyAppsInstalledAdapter = this.mAdapter;
      localListView = this.mMyAppsList;
      if (localListView != null)
      {
        int i = localMyAppsInstalledAdapter.mDownloadingSectionAdapter.getCount();
        j = 1;
        if (j >= i) {
          break label183;
        }
        localDocument2 = (Document)localMyAppsInstalledAdapter.mDownloadingSectionAdapter.getItem(j);
        if (!paramString.equals(localDocument2.getAppDetails().packageName)) {
          break label166;
        }
      }
    }
    label166:
    label183:
    for (Document localDocument1 = localDocument2;; localDocument1 = null)
    {
      int k;
      int m;
      if (localDocument1 != null)
      {
        k = localListView.getFirstVisiblePosition();
        m = localListView.getLastVisiblePosition();
      }
      for (int n = k;; n++) {
        if (n <= m)
        {
          if (localDocument1 == localListView.getItemAtPosition(n))
          {
            View localView = localListView.getChildAt(n - k);
            Installer.InstallerProgressReport localInstallerProgressReport = localMyAppsInstalledAdapter.mInstaller.getProgress(localDocument1.getAppDetails().packageName);
            ((PlayCardViewMyAppsDownloading)localView).bindProgress(localInstallerProgressReport);
          }
        }
        else
        {
          return;
          j++;
          break;
        }
      }
      requestData();
      return;
    }
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    requestData();
  }
  
  public final void onRestoreInstanceState(ObjectMap paramObjectMap)
  {
    ObjectMap localObjectMap;
    String str;
    if (paramObjectMap != null)
    {
      this.mRestoredInstanceState = paramObjectMap;
      localObjectMap = this.mRestoredInstanceState;
      str = KEY_ALREADY_SHOWN_UPDATE_ALL_PROMPT;
      if (!localObjectMap.mHashmap.containsKey(str)) {
        break label56;
      }
    }
    label56:
    for (boolean bool = ((Boolean)localObjectMap.mHashmap.get(str)).booleanValue();; bool = localObjectMap.mBundle.getBoolean(str))
    {
      this.mUpdateAllPromptShown = bool;
      return;
    }
  }
  
  protected final void requestData()
  {
    new AsyncTask() {}.execute(new Void[0]);
  }
  
  protected final void retryFromError()
  {
    clearState();
    requestData();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsInstalledTab
 * JD-Core Version:    0.7.0.1
 */