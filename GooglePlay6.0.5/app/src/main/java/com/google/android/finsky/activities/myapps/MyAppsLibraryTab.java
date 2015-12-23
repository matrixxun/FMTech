package com.google.android.finsky.activities.myapps;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.Checkable;
import android.widget.ListView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeBulkDetails;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.ProgressDialogFragment;
import com.google.android.finsky.layout.play.PlayCardViewMyApps.OnArchiveActionListener;
import com.google.android.finsky.layout.play.PlayListView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;
import java.util.ArrayList;
import java.util.List;

public final class MyAppsLibraryTab
  extends MyAppsTab<DfeList>
  implements View.OnLongClickListener, PlayStoreUiElementNode
{
  public static final boolean SUPPORTS_MULTI_CHOICE;
  private final AccountLibrary mAccountLibrary;
  final MyAppsLibraryAdapter mAdapter;
  private ActionMode mCurrentActionMode;
  private final MyAppsTabbedFragment mFragment;
  private ViewGroup mLibraryView;
  private boolean mListInitialized = false;
  PlayListView mListView;
  private PlayStoreUiElementNode mParentNode;
  private ObjectMap mRestoredInstanceState = ObjectMap.EMPTY;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(408);
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_MULTI_CHOICE = bool;
      return;
    }
  }
  
  public MyAppsLibraryTab(AuthenticatedActivity paramAuthenticatedActivity, DfeApi paramDfeApi, DfeToc paramDfeToc, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, MyAppsTabbedFragment paramMyAppsTabbedFragment, AccountLibrary paramAccountLibrary, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramAuthenticatedActivity, paramDfeApi, paramDfeToc, paramNavigationManager);
    this.mFragment = paramMyAppsTabbedFragment;
    this.mAccountLibrary = paramAccountLibrary;
    PlayCardViewMyApps.OnArchiveActionListener local1 = new PlayCardViewMyApps.OnArchiveActionListener()
    {
      public final void onArchiveAction(Document paramAnonymousDocument)
      {
        MyAppsTabbedFragment localMyAppsTabbedFragment = MyAppsLibraryTab.this.mFragment;
        ArrayList localArrayList1 = Lists.newArrayList(new Document[] { paramAnonymousDocument });
        Object[] arrayOfObject;
        if (localMyAppsTabbedFragment.mFragmentManager.findFragmentByTag("archive_confirm") == null)
        {
          if (localArrayList1.size() != 1) {
            break label172;
          }
          arrayOfObject = new Object[1];
          arrayOfObject[0] = ((Document)localArrayList1.get(0)).mDocument.title;
        }
        label172:
        for (String str = localMyAppsTabbedFragment.getString(2131361858, arrayOfObject);; str = localMyAppsTabbedFragment.getString(2131361859))
        {
          SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
          localBuilder.setMessage(str).setPositiveId(2131362418).setNegativeId(2131361915);
          ArrayList localArrayList2 = MyAppsTabbedFragment.getDocIdList(localArrayList1);
          Bundle localBundle = new Bundle();
          localBundle.putStringArrayList("docid_list", localArrayList2);
          localBuilder.setCallback(localMyAppsTabbedFragment, 6, localBundle);
          localBuilder.setEventLog(317, null, 269, 270, null);
          localBuilder.build().show(localMyAppsTabbedFragment.mFragmentManager, "archive_confirm");
          return;
        }
      }
    };
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mAdapter = new MyAppsLibraryAdapter(paramAuthenticatedActivity, paramNavigationManager, FinskyApp.get().mToc, this.mLibraries, FinskyApp.get().mPackageStateRepository, this.mInstaller, paramBitmapLoader, this, local1, this, this);
    MyAppsLibraryAdapter localMyAppsLibraryAdapter = this.mAdapter;
    localMyAppsLibraryAdapter.mHasAccountSwitcher = true;
    localMyAppsLibraryAdapter.notifyDataSetChanged();
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  @TargetApi(11)
  protected final boolean finishActiveMode()
  {
    if (this.mCurrentActionMode != null)
    {
      this.mCurrentActionMode.finish();
      return true;
    }
    return false;
  }
  
  protected final MyAppsListAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  protected final Document getDocumentForView(View paramView)
  {
    return MyAppsLibraryAdapter.getViewDoc(paramView);
  }
  
  protected final View getFullView()
  {
    return this.mLibraryView;
  }
  
  protected final ListView getListView()
  {
    return this.mListView;
  }
  
  public final PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final View getView(int paramInt)
  {
    if (this.mLibraryView == null) {
      this.mLibraryView = ((ViewGroup)this.mLayoutInflater.inflate(2130968843, null));
    }
    return this.mLibraryView;
  }
  
  public final void onClick(View paramView)
  {
    if (this.mListView.getChoiceMode() != 3)
    {
      super.onClick(paramView);
      return;
    }
    int i = getPositionForView(paramView);
    PlayListView localPlayListView = this.mListView;
    if (!this.mListView.isItemChecked(i)) {}
    for (boolean bool = true;; bool = false)
    {
      localPlayListView.setItemChecked(i, bool);
      return;
    }
  }
  
  @TargetApi(11)
  public final void onDataChanged()
  {
    super.onDataChanged();
    if (!this.mListInitialized)
    {
      this.mListInitialized = true;
      this.mListView = ((PlayListView)this.mLibraryView.findViewById(2131755738));
      int i = UiUtils.getGridHorizontalPadding(this.mListView.getResources());
      ViewCompat.setPaddingRelative(this.mListView, i, this.mListView.getPaddingTop(), i, this.mListView.getPaddingBottom());
      this.mListView.setAnimateChanges(true);
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setItemsCanFocus(true);
      if (SUPPORTS_MULTI_CHOICE)
      {
        this.mListView.setMultiChoiceModeListener(new MultiChoiceListener(this, (byte)0));
        if ((this.mRestoredInstanceState.containsKey("MyAppsLibraryTab.ChoiceMode")) && (this.mRestoredInstanceState.getInt("MyAppsLibraryTab.ChoiceMode") == 3))
        {
          this.mAdapter.setMultiChoiceMode(true);
          this.mListView.setChoiceMode(3);
        }
      }
      this.mListView.setRecyclerListener(this.mAdapter);
      if (this.mRestoredInstanceState.containsKey("MyAppsTab.KeyListParcel")) {
        this.mListView.onRestoreInstanceState((Parcelable)this.mRestoredInstanceState.get("MyAppsTab.KeyListParcel"));
      }
      configureEmptyUI(true, 2131362111);
    }
    syncViewToState();
    this.mAdapter.onDataChanged();
    Document localDocument = ((DfeList)this.mDfeModel).mContainerDocument;
    if (localDocument != null) {
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, localDocument.mDocument.serverLogsCookie);
    }
  }
  
  public final ObjectMap onDestroyView()
  {
    ObjectMap localObjectMap = new ObjectMap();
    if ((this.mDfeModel != null) && (((DfeList)this.mDfeModel).isReady())) {
      localObjectMap.put("MyAppsLibraryTab.ListData", this.mDfeModel);
    }
    if (this.mListView != null)
    {
      localObjectMap.put("MyAppsTab.KeyListParcel", this.mListView.onSaveInstanceState());
      localObjectMap.put("MyAppsLibraryTab.ChoiceMode", Integer.valueOf(this.mListView.getChoiceMode()));
    }
    return localObjectMap;
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 6) || (paramInt1 == 8)) {
      this.mAdapter.notifyDataSetChanged();
    }
  }
  
  public final void onLibraryContentsChanged$40bdb4b1() {}
  
  public final boolean onLongClick(View paramView)
  {
    if (SUPPORTS_MULTI_CHOICE)
    {
      if (this.mListView.getChoiceMode() != 3)
      {
        this.mAdapter.setMultiChoiceMode(true);
        this.mListView.setChoiceMode(3);
      }
      int i = getPositionForView(paramView);
      if (i != -1)
      {
        PlayListView localPlayListView = this.mListView;
        boolean bool1 = this.mListView.isItemChecked(i);
        boolean bool2 = false;
        if (!bool1) {
          bool2 = true;
        }
        localPlayListView.setItemChecked(i, bool2);
      }
      return true;
    }
    return false;
  }
  
  public final void onRestoreInstanceState(ObjectMap paramObjectMap)
  {
    if (paramObjectMap != null) {
      this.mRestoredInstanceState = paramObjectMap;
    }
  }
  
  public final void removeItems(List<String> paramList)
  {
    int i = 0;
    if (i < paramList.size()) {
      for (int j = 0;; j++) {
        if (j < ((DfeList)this.mDfeModel).getCount())
        {
          if (((String)paramList.get(i)).equals(((Document)((DfeList)this.mDfeModel).getItem(j)).mDocument.docid)) {
            ((DfeList)this.mDfeModel).removeItem(j);
          }
        }
        else
        {
          i++;
          break;
        }
      }
    }
    this.mAdapter.notifyDataSetChanged();
  }
  
  protected final void requestData()
  {
    clearState();
    byte[] arrayOfByte = this.mAccountLibrary.getServerToken(AccountLibrary.LIBRARY_ID_APPS);
    String str = this.mDfeApi.getLibraryUrl(3, AccountLibrary.LIBRARY_ID_APPS, 1, arrayOfByte);
    DfeList localDfeList;
    if ((this.mRestoredInstanceState != null) && (this.mRestoredInstanceState.containsKey("MyAppsLibraryTab.ListData")))
    {
      localDfeList = (DfeList)this.mRestoredInstanceState.get("MyAppsLibraryTab.ListData");
      if (str.equals(localDfeList.mInitialListUrl)) {
        localDfeList.setDfeApi(this.mDfeApi);
      }
    }
    for (;;)
    {
      this.mDfeModel = localDfeList;
      ((DfeList)this.mDfeModel).addDataChangedListener(this);
      ((DfeList)this.mDfeModel).addErrorListener(this);
      ((DfeList)this.mDfeModel).startLoadItems();
      MyAppsLibraryAdapter localMyAppsLibraryAdapter = this.mAdapter;
      localMyAppsLibraryAdapter.mList = ((DfeList)this.mDfeModel);
      localMyAppsLibraryAdapter.notifyDataSetChanged();
      return;
      localDfeList = new DfeList(this.mDfeApi, str, true);
      localDfeList.mFilteredDocId = "com.google.android.gms";
    }
  }
  
  protected final void retryFromError()
  {
    ((DfeList)this.mDfeModel).resetItems();
    ((DfeList)this.mDfeModel).mCurrentRequest = null;
    ((DfeList)this.mDfeModel).startLoadItems();
  }
  
  public final void setTabSelected(boolean paramBoolean)
  {
    super.setTabSelected(paramBoolean);
    if (!paramBoolean) {
      finishActiveMode();
    }
  }
  
  @TargetApi(11)
  private static final class MultiChoiceListener
    implements AbsListView.MultiChoiceModeListener
  {
    private final MyAppsLibraryTab mTab;
    
    private MultiChoiceListener(MyAppsLibraryTab paramMyAppsLibraryTab)
    {
      this.mTab = paramMyAppsLibraryTab;
    }
    
    private List<Document> getCheckedDocuments()
    {
      SparseBooleanArray localSparseBooleanArray = this.mTab.mListView.getCheckedItemPositions();
      ArrayList localArrayList = Lists.newArrayList(localSparseBooleanArray.size());
      for (int i = 0; i < localSparseBooleanArray.size(); i++)
      {
        int j = localSparseBooleanArray.keyAt(i);
        if (localSparseBooleanArray.get(j)) {
          localArrayList.add(this.mTab.mAdapter.getDocument(j));
        }
      }
      return localArrayList;
    }
    
    public final boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      MyAppsTabbedFragment localMyAppsTabbedFragment = this.mTab.mFragment;
      List localList = getCheckedDocuments();
      switch (paramMenuItem.getItemId())
      {
      }
      for (int i = 0; i != 0; i = 1)
      {
        return true;
        if (localMyAppsTabbedFragment.mFragmentManager.findFragmentByTag("progress_dialog") == null)
        {
          localMyAppsTabbedFragment.mTabbedAdapter.finishActiveMode();
          localMyAppsTabbedFragment.mProgressDialog = ProgressDialogFragment.newInstance$1c1250dc();
          localMyAppsTabbedFragment.mProgressDialog.show(localMyAppsTabbedFragment.mFragmentManager, "progress_dialog");
          ArrayList localArrayList = MyAppsTabbedFragment.getDocIdList(localList);
          String str = FinskyApp.get().getCurrentAccountName();
          DfeBulkDetails localDfeBulkDetails = new DfeBulkDetails(localMyAppsTabbedFragment.mDfeApi, localArrayList, false);
          localDfeBulkDetails.addDataChangedListener(new MyAppsTabbedFragment.4(localMyAppsTabbedFragment, localDfeBulkDetails, str));
          localDfeBulkDetails.addErrorListener(new MyAppsTabbedFragment.5(localMyAppsTabbedFragment));
        }
      }
      return false;
    }
    
    public final boolean onCreateActionMode(final ActionMode paramActionMode, Menu paramMenu)
    {
      paramActionMode.getMenuInflater().inflate(2131820547, paramMenu);
      MyAppsLibraryTab.access$202(this.mTab, paramActionMode);
      final MenuItem localMenuItem = paramMenu.findItem(2131755842);
      View localView = this.mTab.mAuthenticatedActivity.getLayoutInflater().inflate(2130968840, null);
      PlayActionButton localPlayActionButton = (PlayActionButton)localView.findViewById(2131755729);
      localPlayActionButton.setActionStyle(1);
      localPlayActionButton.configure(3, 2131362224, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          MyAppsLibraryTab.MultiChoiceListener.this.onActionItemClicked(paramActionMode, localMenuItem);
        }
      });
      localMenuItem.setActionView(localView);
      return true;
    }
    
    public final void onDestroyActionMode(ActionMode paramActionMode)
    {
      MyAppsLibraryTab.access$202(this.mTab, null);
      this.mTab.mListView.post(new Runnable()
      {
        public final void run()
        {
          PlayListView localPlayListView = MyAppsLibraryTab.this.mListView;
          MyAppsLibraryTab.this.mAdapter.setMultiChoiceMode(false);
          localPlayListView.clearChoices();
          for (int i = 0; i < localPlayListView.getChildCount(); i++)
          {
            View localView = localPlayListView.getChildAt(i);
            if ((localView instanceof Checkable)) {
              ((Checkable)localView).setChecked(false);
            }
          }
          localPlayListView.setChoiceMode(0);
        }
      });
    }
    
    public final void onItemCheckedStateChanged(ActionMode paramActionMode, int paramInt, long paramLong, boolean paramBoolean)
    {
      int i = getCheckedDocuments().size();
      ActionMode localActionMode = this.mTab.mCurrentActionMode;
      Resources localResources = this.mTab.mAuthenticatedActivity.getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      localActionMode.setTitle(localResources.getQuantityString(2131296261, i, arrayOfObject));
    }
    
    public final boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsLibraryTab
 * JD-Core Version:    0.7.0.1
 */