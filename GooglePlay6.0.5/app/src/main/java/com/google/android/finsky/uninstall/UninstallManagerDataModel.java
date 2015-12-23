package com.google.android.finsky.uninstall;

import android.content.pm.IPackageStatsObserver.Stub;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class UninstallManagerDataModel
{
  boolean mAppDisplayDataReady = false;
  long mAppNeededBytes;
  boolean mAppSizeDataReady = false;
  boolean mAvailableSpaceDataReady = false;
  OnDataChangedListener mDataListener;
  List<UninstallManagerSizedDoc> mDocList = null;
  Map<String, UninstallManagerSizedDoc> mDocMap = null;
  boolean mFreeSpaceNeededDataReady = false;
  Document mInstallingDoc;
  long mLastStorageUpdateTime;
  long mPartitionAvailableBytes;
  long mPartitionTotalBytes;
  
  public UninstallManagerDataModel(Document paramDocument)
  {
    this.mInstallingDoc = paramDocument;
  }
  
  public final boolean isDataReady()
  {
    return (this.mAppSizeDataReady) && (this.mAppDisplayDataReady) && (this.mAvailableSpaceDataReady) && (this.mFreeSpaceNeededDataReady);
  }
  
  final void notifyIfReady()
  {
    if (isDataReady())
    {
      this.mDocList = Lists.newArrayList(this.mDocMap.values());
      if (this.mDataListener != null) {
        this.mDataListener.onDataChanged();
      }
    }
  }
  
  private final class ComputeAvailableSpaceTask
    extends AsyncTask<Void, Void, Long>
  {
    private long mTimestamp;
    
    private ComputeAvailableSpaceTask() {}
  }
  
  private final class ComputeFreeSpaceNeededTask
    extends AsyncTask<Void, Void, Void>
  {
    private long mMinNeededBytes;
    private long mTotalBytes;
    
    private ComputeFreeSpaceNeededTask() {}
  }
  
  private final class RequestInstalledAppsTask
    extends AsyncTask<Void, Void, Map<String, List<String>>>
  {
    private AppStates mAppStates;
    private Libraries mLibraries;
    private PackageManager mPackageManager;
    
    public RequestInstalledAppsTask(PackageManager paramPackageManager)
    {
      this.mPackageManager = paramPackageManager;
      this.mAppStates = FinskyApp.get().mAppStates;
      this.mLibraries = FinskyApp.get().mLibraries;
    }
  }
  
  public static final class UninstallManagerSizedDoc
  {
    public long bytes;
    public final String packageName;
    public String title;
    
    public UninstallManagerSizedDoc(String paramString)
    {
      this.packageName = paramString;
      this.bytes = -1L;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.uninstall.UninstallManagerDataModel
 * JD-Core Version:    0.7.0.1
 */