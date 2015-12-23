package com.google.android.finsky.wear;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.InMemoryInstallerDataStore;
import com.google.android.finsky.appstate.SQLiteInstallerDataStore;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.HashMap;
import java.util.Map;

public final class WearAppStatesFactory
{
  private final Handler mBackgroundHandler;
  private final Context mContext;
  private final GoogleApiClient mGoogleApiClient;
  private Map<String, WriteThroughInstallerDataStore> mInstallerDataStores;
  private final Handler mNotificationHandler;
  
  public WearAppStatesFactory(Context paramContext, GoogleApiClient paramGoogleApiClient, Handler paramHandler1, Handler paramHandler2)
  {
    this.mContext = paramContext;
    this.mGoogleApiClient = paramGoogleApiClient;
    this.mNotificationHandler = paramHandler1;
    this.mBackgroundHandler = paramHandler2;
    this.mInstallerDataStores = new HashMap();
  }
  
  public final AppStates createAppStates(String paramString)
  {
    return new AppStates(paramString, getInstallerDataStore(paramString), WearPackageStateRepository.get(this.mContext, paramString, this.mGoogleApiClient));
  }
  
  public final WriteThroughInstallerDataStore getInstallerDataStore(String paramString)
  {
    try
    {
      WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = (WriteThroughInstallerDataStore)this.mInstallerDataStores.get(paramString);
      if (localWriteThroughInstallerDataStore == null)
      {
        localWriteThroughInstallerDataStore = new WriteThroughInstallerDataStore(new InMemoryInstallerDataStore(), new SQLiteInstallerDataStore(this.mContext, paramString), this.mBackgroundHandler, this.mNotificationHandler);
        this.mInstallerDataStores.put(paramString, localWriteThroughInstallerDataStore);
      }
      return localWriteThroughInstallerDataStore;
    }
    finally {}
  }
  
  public final void load(String paramString, final Runnable paramRunnable)
  {
    Runnable local1 = new Runnable()
    {
      private int mLoaded;
      
      public final void run()
      {
        this.mLoaded = (1 + this.mLoaded);
        if (this.mLoaded == 2) {
          paramRunnable.run();
        }
      }
    };
    getInstallerDataStore(paramString).load(local1);
    WearPackageStateRepository.get(this.mContext, paramString, this.mGoogleApiClient).load(local1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearAppStatesFactory
 * JD-Core Version:    0.7.0.1
 */