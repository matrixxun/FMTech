package com.google.android.finsky.activities.myapps;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.ViewPagerTab;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeModel;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.layout.AccountSelectorView;
import com.google.android.finsky.layout.play.PlayCardViewMyApps;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.play.utils.config.GservicesValue;

public abstract class MyAppsTab<T extends DfeModel>
  implements View.OnClickListener, Response.ErrorListener, ViewPagerTab, OnDataChangedListener, InstallerListener, Libraries.Listener
{
  protected final AuthenticatedActivity mAuthenticatedActivity;
  protected final DfeApi mDfeApi;
  protected T mDfeModel;
  protected final DfeToc mDfeToc;
  protected final Installer mInstaller;
  private boolean mIsSelectedTab = false;
  private VolleyError mLastVolleyError;
  protected final LayoutInflater mLayoutInflater;
  protected final Libraries mLibraries;
  protected final NavigationManager mNavigationManager;
  
  protected MyAppsTab(AuthenticatedActivity paramAuthenticatedActivity, DfeApi paramDfeApi, DfeToc paramDfeToc, NavigationManager paramNavigationManager)
  {
    this.mAuthenticatedActivity = paramAuthenticatedActivity;
    this.mLayoutInflater = LayoutInflater.from(this.mAuthenticatedActivity);
    this.mDfeApi = paramDfeApi;
    this.mDfeToc = paramDfeToc;
    this.mNavigationManager = paramNavigationManager;
    this.mInstaller = FinskyApp.get().mInstaller;
    this.mInstaller.addListener(this);
    this.mLibraries = FinskyApp.get().mLibraries;
    this.mLibraries.addListener(this);
  }
  
  private boolean isDataReady()
  {
    return (this.mDfeModel != null) && (this.mDfeModel.isReady());
  }
  
  protected final void clearState()
  {
    if (this.mDfeModel != null)
    {
      this.mDfeModel.removeDataChangedListener(this);
      this.mDfeModel.removeErrorListener(this);
      this.mDfeModel = null;
    }
  }
  
  protected final void configureEmptyUI(boolean paramBoolean, int paramInt)
  {
    MyAppsEmptyView localMyAppsEmptyView = (MyAppsEmptyView)getFullView().findViewById(2131755730);
    DfeToc localDfeToc;
    NavigationManager localNavigationManager;
    Toc.CorpusMetadata localCorpusMetadata;
    label56:
    label70:
    String str;
    if (localMyAppsEmptyView != null)
    {
      localDfeToc = this.mDfeToc;
      localNavigationManager = this.mNavigationManager;
      if (!paramBoolean) {
        break label112;
      }
      localMyAppsEmptyView.mAccountNameView.configure();
      localMyAppsEmptyView.mDescriptionView.setText(paramInt);
      if (localDfeToc != null) {
        break label124;
      }
      localCorpusMetadata = null;
      if (localCorpusMetadata != null) {
        break label135;
      }
      localMyAppsEmptyView.mAppBrowsing.setVisibility(8);
      str = (String)G.gamesBrowseUrl.get();
      if ((localDfeToc != null) && (!TextUtils.isEmpty(str))) {
        break label159;
      }
      localMyAppsEmptyView.mGamesBrowsing.setVisibility(8);
    }
    for (;;)
    {
      getListView().setEmptyView(localMyAppsEmptyView);
      return;
      label112:
      localMyAppsEmptyView.mAccountNameView.setVisibility(8);
      break;
      label124:
      localCorpusMetadata = localDfeToc.getCorpus(3);
      break label56;
      label135:
      localMyAppsEmptyView.mAppBrowsing.setOnClickListener(new MyAppsEmptyView.1(localMyAppsEmptyView, localNavigationManager, localCorpusMetadata, localDfeToc));
      break label70;
      label159:
      localMyAppsEmptyView.mGamesBrowsing.setOnClickListener(new MyAppsEmptyView.2(localMyAppsEmptyView, localNavigationManager, str, localDfeToc));
    }
  }
  
  protected boolean finishActiveMode()
  {
    return false;
  }
  
  protected abstract MyAppsListAdapter getAdapter();
  
  protected abstract Document getDocumentForView(View paramView);
  
  protected abstract View getFullView();
  
  protected abstract ListView getListView();
  
  protected final int getPositionForView(View paramView)
  {
    if (paramView.getId() == 2131755258) {
      paramView = (View)paramView.getParent();
    }
    if (getDocumentForView(paramView) == null) {}
    ListView localListView;
    label65:
    for (;;)
    {
      return -1;
      View localView = paramView;
      localListView = getListView();
      for (;;)
      {
        if (localView == null) {
          break label65;
        }
        ViewParent localViewParent = localView.getParent();
        if (localViewParent == localListView) {
          break label67;
        }
        if (!(localViewParent instanceof View)) {
          break;
        }
        localView = (View)localViewParent;
      }
    }
    label67:
    return localListView.getPositionForView(paramView);
  }
  
  final void loadData()
  {
    requestData();
    if (isDataReady()) {
      onDataChanged();
    }
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public void onClick(View paramView)
  {
    int i = getPositionForView(paramView);
    Document localDocument = getAdapter().getDocument(i);
    PlayStoreUiElementNode localPlayStoreUiElementNode = (PlayStoreUiElementNode)((PlayCardViewMyApps)paramView).getLoggingData();
    FinskyApp.get().getEventLogger().logClickEvent(localPlayStoreUiElementNode);
    if (NavigationManager.areTransitionsEnabled()) {}
    for (View localView = paramView.findViewById(2131755457);; localView = null)
    {
      this.mNavigationManager.getClickListener(localDocument, null, null, -1, localView).onClick(paramView);
      return;
    }
  }
  
  public void onDataChanged()
  {
    this.mLastVolleyError = null;
  }
  
  public final void onDestroy()
  {
    clearState();
    this.mInstaller.removeListener(this);
    this.mLibraries.removeListener(this);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    this.mLastVolleyError = paramVolleyError;
    syncViewToState();
  }
  
  protected abstract void requestData();
  
  protected abstract void retryFromError();
  
  public void setTabSelected(boolean paramBoolean)
  {
    this.mIsSelectedTab = paramBoolean;
  }
  
  protected final void syncViewToState()
  {
    View localView1 = getFullView();
    View localView2 = localView1.findViewById(2131755697);
    View localView3 = localView1.findViewById(2131755806);
    ListView localListView = (ListView)localView1.findViewById(2131755738);
    if (this.mLastVolleyError != null)
    {
      localView3.setVisibility(0);
      ((TextView)localView3.findViewById(2131755274)).setText(ErrorStrings.get(FinskyApp.get(), this.mLastVolleyError));
      localView3.findViewById(2131755482).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          MyAppsTab.this.retryFromError();
          MyAppsTab.this.syncViewToState();
        }
      });
      localView2.setVisibility(8);
      localListView.setVisibility(8);
      return;
    }
    if (isDataReady())
    {
      localListView.setVisibility(0);
      localView3.setVisibility(8);
      localView2.setVisibility(8);
      return;
    }
    localView2.setVisibility(0);
    localView3.setVisibility(8);
    localListView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsTab
 * JD-Core Version:    0.7.0.1
 */