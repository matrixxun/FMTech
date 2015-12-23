package com.google.android.finsky.uninstall;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.Formatter;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.EmptyRecyclerViewAdapter;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class UninstallManagerFragment
  extends PageFragment
  implements ButtonBar.ClickListener, PackageMonitorReceiver.PackageStatusListener, UninstallManagerRecyclerViewAdapter.RowCheckedListener
{
  private UninstallManagerRecyclerViewAdapter mAdapter;
  private ButtonBar mButtonBar;
  private TextView mCheckedTextView;
  private LinearLayout mContentLayout;
  private Installer mInstaller = FinskyApp.get().mInstaller;
  private Document mInstallingDoc;
  private UninstallManagerContinueListener mListener;
  private UninstallManagerDataModel mModel;
  private ProgressBar mProgressBar;
  private PlayRecyclerView mRecyclerView;
  private ObjectMap mRecyclerViewRestoreObjectMap = new ObjectMap();
  private TextView mStorageTextView;
  private long mUninstallProgressMade;
  private long mUninstallProgressPending;
  private long mUninstallProgressPotential;
  private List<UninstallManagerDataModel.UninstallManagerSizedDoc> mUninstallingDocs = new ArrayList();
  
  public static UninstallManagerFragment newInstance(Document paramDocument)
  {
    UninstallManagerFragment localUninstallManagerFragment = new UninstallManagerFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("uninstall_manager_fragment_installing_doc", paramDocument);
    localUninstallManagerFragment.setArguments(localBundle);
    localUninstallManagerFragment.setArgument("finsky.PageFragment.toc", FinskyApp.get().mToc);
    return localUninstallManagerFragment;
  }
  
  private void rebindCheckedText()
  {
    String str1 = Formatter.formatFileSize(this.mContext, this.mUninstallProgressPotential);
    String str2 = getResources().getString(2131363038, new Object[] { str1 });
    this.mCheckedTextView.setText(str2);
  }
  
  private void rebindProgressBar()
  {
    long l = this.mModel.mAppNeededBytes - this.mModel.mPartitionAvailableBytes + this.mUninstallProgressMade;
    if (l > 0L)
    {
      int i = (int)((float)(this.mUninstallProgressMade + this.mUninstallProgressPending) / (float)l * this.mProgressBar.getMax());
      this.mProgressBar.setProgress(i);
      int j = (int)((float)this.mUninstallProgressPotential / (float)l * this.mProgressBar.getMax());
      this.mProgressBar.setSecondaryProgress(j + i);
      return;
    }
    this.mProgressBar.setProgress(this.mProgressBar.getMax());
  }
  
  protected final int getLayoutRes()
  {
    return 2130969141;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return null;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    try
    {
      this.mListener = ((UninstallManagerContinueListener)this.mPageFragmentHost);
      this.mContentLayout = ((LinearLayout)this.mDataView.findViewById(2131756172));
      this.mButtonBar = ((ButtonBar)this.mDataView.findViewById(2131755300));
      this.mStorageTextView = ((TextView)this.mDataView.findViewById(2131756173));
      this.mCheckedTextView = ((TextView)this.mDataView.findViewById(2131756175));
      this.mRecyclerView = ((PlayRecyclerView)this.mDataView.findViewById(2131756176));
      this.mProgressBar = ((ProgressBar)this.mDataView.findViewById(2131756174));
      this.mRecyclerView.setLayoutManager(new LinearLayoutManager());
      this.mRecyclerView.setAdapter(new EmptyRecyclerViewAdapter());
      FinskyApp.get().mPackageMonitorReceiver.attach(this);
      if ((this.mModel != null) && (this.mModel.isDataReady()))
      {
        i = 1;
        if (i != 0) {
          break label201;
        }
        switchToLoading();
        requestData();
      }
    }
    catch (ClassCastException localClassCastException)
    {
      for (;;)
      {
        FinskyLog.wtf(localClassCastException, "Uninstall Wizard host activity doesn't implement the callback interface", new Object[0]);
        continue;
        int i = 0;
      }
      label201:
      rebindViews();
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    this.mInstallingDoc = ((Document)this.mArguments.getParcelable("uninstall_manager_fragment_installing_doc"));
  }
  
  public final void onDestroyView()
  {
    FinskyApp.get().mPackageMonitorReceiver.detach(this);
    if ((this.mRecyclerView != null) && (this.mRecyclerView.getVisibility() == 0) && (this.mAdapter != null))
    {
      UninstallManagerRecyclerViewAdapter localUninstallManagerRecyclerViewAdapter = this.mAdapter;
      ObjectMap localObjectMap = this.mRecyclerViewRestoreObjectMap;
      localObjectMap.put("uninstall_manager__adapter_docs", localUninstallManagerRecyclerViewAdapter.mDocs);
      localObjectMap.put("uninstall_manager__adapter_checked", localUninstallManagerRecyclerViewAdapter.mChecked);
      localObjectMap.put("uninstall_manager__adapter_uninstalling", localUninstallManagerRecyclerViewAdapter.mUninstalling);
      localObjectMap.putInt("uninstall_manager_adapter_selection", localUninstallManagerRecyclerViewAdapter.mCurrentSortType);
    }
    this.mRecyclerView = null;
    if (this.mAdapter != null)
    {
      this.mAdapter.mRowCheckedListener = null;
      this.mAdapter = null;
    }
    this.mButtonBar = null;
    this.mStorageTextView = null;
    this.mProgressBar = null;
    this.mContentLayout = null;
    super.onDestroyView();
  }
  
  public final void onNegativeButtonClick()
  {
    this.mUninstallProgressPending += this.mUninstallProgressPotential;
    this.mUninstallProgressPotential = 0L;
    List localList = this.mUninstallingDocs;
    UninstallManagerRecyclerViewAdapter localUninstallManagerRecyclerViewAdapter = this.mAdapter;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < localUninstallManagerRecyclerViewAdapter.mChecked.size(); i++) {
      if (((Boolean)localUninstallManagerRecyclerViewAdapter.mChecked.get(i)).booleanValue())
      {
        UninstallManagerDataModel.UninstallManagerSizedDoc localUninstallManagerSizedDoc2 = (UninstallManagerDataModel.UninstallManagerSizedDoc)localUninstallManagerRecyclerViewAdapter.mDocs.get(i);
        localUninstallManagerRecyclerViewAdapter.mUninstalling.set(i, Boolean.valueOf(true));
        localUninstallManagerRecyclerViewAdapter.setItemChecked(i, false);
        localUninstallManagerRecyclerViewAdapter.notifyItemChanged(i + 1);
        localArrayList.add(localUninstallManagerSizedDoc2);
      }
    }
    localList.addAll(localArrayList);
    Iterator localIterator = this.mUninstallingDocs.iterator();
    while (localIterator.hasNext())
    {
      UninstallManagerDataModel.UninstallManagerSizedDoc localUninstallManagerSizedDoc1 = (UninstallManagerDataModel.UninstallManagerSizedDoc)localIterator.next();
      this.mInstaller.uninstallAssetSilently(localUninstallManagerSizedDoc1.packageName, false);
    }
  }
  
  public final void onPackageAdded(String paramString)
  {
    requestData();
  }
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
  
  public final void onPackageChanged(String paramString) {}
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      Iterator localIterator = this.mUninstallingDocs.iterator();
      while (localIterator.hasNext())
      {
        UninstallManagerDataModel.UninstallManagerSizedDoc localUninstallManagerSizedDoc = (UninstallManagerDataModel.UninstallManagerSizedDoc)localIterator.next();
        if (localUninstallManagerSizedDoc.packageName.equals(paramString)) {
          this.mUninstallingDocs.remove(localUninstallManagerSizedDoc);
        }
      }
      if (this.mUninstallingDocs.isEmpty())
      {
        this.mUninstallProgressMade += this.mUninstallProgressPending;
        this.mUninstallProgressPending = 0L;
        requestData();
      }
    }
  }
  
  public final void onPositiveButtonClick()
  {
    if (this.mListener != null) {
      this.mListener.onUninstallManagerContinue();
    }
  }
  
  public final void onRowChecked(boolean paramBoolean, long paramLong)
  {
    this.mButtonBar.setNegativeButtonEnabled(this.mAdapter.isAnyItemChecked());
    if (paramBoolean) {}
    for (this.mUninstallProgressPotential = (paramLong + this.mUninstallProgressPotential);; this.mUninstallProgressPotential -= paramLong)
    {
      if (this.mUninstallProgressPotential < 0L) {
        this.mUninstallProgressPotential = 0L;
      }
      rebindProgressBar();
      rebindCheckedText();
      return;
    }
  }
  
  protected final void rebindViews()
  {
    this.mContentLayout.setVisibility(0);
    if (this.mModel != null)
    {
      Resources localResources;
      if (this.mRecyclerView == null)
      {
        FinskyLog.w("Recycler view null, ignoring.", new Object[0]);
        localResources = getResources();
        long l = this.mModel.mAppNeededBytes - this.mModel.mPartitionAvailableBytes;
        if (l <= 0L) {
          break label430;
        }
        String str = localResources.getString(2131363042, new Object[] { Formatter.formatFileSize(this.mContext, l) });
        this.mStorageTextView.setText(str);
        label95:
        rebindCheckedText();
        rebindProgressBar();
        this.mButtonBar.setPositiveButtonTitle(2131362062);
        this.mButtonBar.setNegativeButtonTitle(2131362807);
        this.mButtonBar.setClickListener(this);
        if (this.mAdapter == null) {
          break label447;
        }
        this.mButtonBar.setNegativeButtonEnabled(this.mAdapter.isAnyItemChecked());
      }
      for (;;)
      {
        if (this.mModel.mAppNeededBytes - this.mModel.mPartitionAvailableBytes <= 0L) {
          break label458;
        }
        this.mButtonBar.setPositiveButtonEnabled(false);
        return;
        ObjectMap localObjectMap1 = this.mRecyclerViewRestoreObjectMap;
        int i;
        label202:
        UninstallManagerRecyclerViewAdapter localUninstallManagerRecyclerViewAdapter;
        if ((localObjectMap1 != null) && (localObjectMap1.containsKey("uninstall_manager__adapter_docs")))
        {
          i = 1;
          if (this.mAdapter != null) {
            break label413;
          }
          this.mAdapter = new UninstallManagerRecyclerViewAdapter(this.mContext);
          this.mRecyclerView.setAdapter(this.mAdapter);
          this.mAdapter.mRowCheckedListener = this;
          if (i == 0) {
            break label396;
          }
          localUninstallManagerRecyclerViewAdapter = this.mAdapter;
          ObjectMap localObjectMap2 = this.mRecyclerViewRestoreObjectMap;
          localUninstallManagerRecyclerViewAdapter.mDocs = ((ArrayList)localObjectMap2.get("uninstall_manager__adapter_docs"));
          localUninstallManagerRecyclerViewAdapter.mChecked = ((ArrayList)localObjectMap2.get("uninstall_manager__adapter_checked"));
          localUninstallManagerRecyclerViewAdapter.mUninstalling = ((ArrayList)localObjectMap2.get("uninstall_manager__adapter_uninstalling"));
          if (localObjectMap2.getInt("uninstall_manager_adapter_selection") != 1) {
            break label387;
          }
          localUninstallManagerRecyclerViewAdapter.mCurrentSortType = 1;
          label325:
          localUninstallManagerRecyclerViewAdapter.mCurrentComparator = UninstallManagerRecyclerViewAdapter.createComparator(localUninstallManagerRecyclerViewAdapter.mCurrentSortType);
          ObjectMap localObjectMap3 = this.mRecyclerViewRestoreObjectMap;
          localObjectMap3.mHashmap.clear();
          localObjectMap3.mBundle.clear();
        }
        for (;;)
        {
          this.mRecyclerView.setEmptyView(this.mDataView.findViewById(2131755730));
          break;
          i = 0;
          break label202;
          label387:
          localUninstallManagerRecyclerViewAdapter.mCurrentSortType = 0;
          break label325;
          label396:
          this.mAdapter.setDocs(this.mModel.mDocList);
        }
        label413:
        this.mAdapter.setDocs(this.mModel.mDocList);
        break;
        label430:
        this.mStorageTextView.setText(localResources.getString(2131363036));
        break label95;
        label447:
        this.mButtonBar.setNegativeButtonEnabled(false);
      }
      label458:
      this.mButtonBar.setPositiveButtonEnabled(true);
      return;
    }
    FinskyLog.wtf("Tried binding views with null data model", new Object[0]);
  }
  
  protected final void requestData()
  {
    if (this.mModel == null)
    {
      this.mModel = new UninstallManagerDataModel(this.mInstallingDoc);
      this.mModel.mDataListener = this;
    }
    UninstallManagerDataModel localUninstallManagerDataModel = this.mModel;
    FragmentActivity localFragmentActivity = getActivity();
    localUninstallManagerDataModel.mAppDisplayDataReady = false;
    localUninstallManagerDataModel.mAppSizeDataReady = false;
    localUninstallManagerDataModel.mAvailableSpaceDataReady = false;
    if (localUninstallManagerDataModel.mDocMap != null) {
      localUninstallManagerDataModel.mDocMap.clear();
    }
    PackageManager localPackageManager = localFragmentActivity.getPackageManager();
    if (!localUninstallManagerDataModel.mFreeSpaceNeededDataReady) {
      Utils.executeMultiThreaded(new UninstallManagerDataModel.ComputeFreeSpaceNeededTask(localUninstallManagerDataModel, (byte)0), new Void[0]);
    }
    new UninstallManagerDataModel.RequestInstalledAppsTask(localUninstallManagerDataModel, localPackageManager).execute(new Void[0]);
    Utils.executeMultiThreaded(new UninstallManagerDataModel.ComputeAvailableSpaceTask(localUninstallManagerDataModel, (byte)0), new Void[0]);
  }
  
  public static abstract interface UninstallManagerContinueListener
  {
    public abstract void onUninstallManagerContinue();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.uninstall.UninstallManagerFragment
 * JD-Core Version:    0.7.0.1
 */