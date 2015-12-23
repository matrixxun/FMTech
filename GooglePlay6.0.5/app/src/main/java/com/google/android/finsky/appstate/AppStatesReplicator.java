package com.google.android.finsky.appstate;

import android.accounts.Account;
import android.os.Handler;
import android.text.TextUtils;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.library.AccountsProvider;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.local.AssetUtils;
import com.google.android.finsky.protos.VendingProtos.ContentSyncRequestProto;
import com.google.android.finsky.protos.VendingProtos.ContentSyncRequestProto.AssetInstallState;
import com.google.android.finsky.protos.VendingProtos.ContentSyncRequestProto.SystemApp;
import com.google.android.finsky.protos.VendingProtos.ContentSyncResponseProto;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Lists;
import com.google.android.gms.ads.identifier.AdIdProvider;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingApiFactory;
import com.google.android.vending.remoting.api.VendingRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AppStatesReplicator
{
  final AccountsProvider mAccounts;
  private final AdIdProvider mAdIdProvider;
  private final Handler mBackgroundHandler;
  final String mDeviceFingerprint;
  final Libraries mLibraries;
  final Handler mNotificationHandler;
  final PackageStateRepository mPackageStates;
  final String mPreferencesSuffix;
  private final List<Listener> mReplicationListeners = new ArrayList();
  private final VendingApiFactory mVendingApiFactory;
  
  public AppStatesReplicator(AccountsProvider paramAccountsProvider, Libraries paramLibraries, PackageStateRepository paramPackageStateRepository, VendingApiFactory paramVendingApiFactory, Handler paramHandler1, Handler paramHandler2, AdIdProvider paramAdIdProvider, String paramString1, String paramString2)
  {
    this.mAccounts = paramAccountsProvider;
    this.mLibraries = paramLibraries;
    this.mPackageStates = paramPackageStateRepository;
    this.mVendingApiFactory = paramVendingApiFactory;
    this.mNotificationHandler = paramHandler1;
    this.mBackgroundHandler = paramHandler2;
    this.mAdIdProvider = paramAdIdProvider;
    this.mDeviceFingerprint = paramString1;
    this.mPreferencesSuffix = paramString2;
  }
  
  static int computeHash(Collection<PackageStateRepository.PackageState> paramCollection, int paramInt)
  {
    return paramInt ^ paramCollection.hashCode();
  }
  
  static VendingProtos.ContentSyncRequestProto makeContentSyncRequest(int paramInt1, int paramInt2, List<PackageStateRepository.PackageState> paramList1, int paramInt3, int paramInt4, List<PackageStateRepository.PackageState> paramList2, int paramInt5, long paramLong)
  {
    int i;
    int j;
    if (paramInt1 != paramInt2)
    {
      i = 1;
      if ((i == 0) && (paramInt3 == paramInt4)) {
        break label205;
      }
      j = 1;
    }
    for (;;)
    {
      label22:
      if (j != 0)
      {
        VendingProtos.ContentSyncRequestProto localContentSyncRequestProto = new VendingProtos.ContentSyncRequestProto();
        if (paramList2 != null)
        {
          int n = paramList2.size();
          if (n > 0)
          {
            localContentSyncRequestProto.assetInstallState = new VendingProtos.ContentSyncRequestProto.AssetInstallState[n];
            int i1 = 0;
            for (;;)
            {
              if (i1 < n)
              {
                PackageStateRepository.PackageState localPackageState2 = (PackageStateRepository.PackageState)paramList2.get(i1);
                VendingProtos.ContentSyncRequestProto.AssetInstallState[] arrayOfAssetInstallState = localContentSyncRequestProto.assetInstallState;
                VendingProtos.ContentSyncRequestProto.AssetInstallState localAssetInstallState = new VendingProtos.ContentSyncRequestProto.AssetInstallState();
                localAssetInstallState.assetId = AssetUtils.makeAssetId(localPackageState2.packageName, localPackageState2.installedVersion);
                localAssetInstallState.hasAssetId = true;
                localAssetInstallState.assetState = 2;
                localAssetInstallState.hasAssetState = true;
                localAssetInstallState.installTime = paramLong;
                localAssetInstallState.hasInstallTime = true;
                localAssetInstallState.packageName = localPackageState2.packageName;
                localAssetInstallState.hasPackageName = true;
                localAssetInstallState.versionCode = localPackageState2.installedVersion;
                localAssetInstallState.hasVersionCode = true;
                arrayOfAssetInstallState[i1] = localAssetInstallState;
                i1++;
                continue;
                i = 0;
                break;
                label205:
                j = 0;
                break label22;
              }
            }
          }
        }
        if (i != 0)
        {
          int k = paramList1.size();
          if (k > 0)
          {
            localContentSyncRequestProto.systemApp = new VendingProtos.ContentSyncRequestProto.SystemApp[paramList1.size()];
            for (int m = 0; m < k; m++)
            {
              PackageStateRepository.PackageState localPackageState1 = (PackageStateRepository.PackageState)paramList1.get(m);
              VendingProtos.ContentSyncRequestProto.SystemApp[] arrayOfSystemApp = localContentSyncRequestProto.systemApp;
              VendingProtos.ContentSyncRequestProto.SystemApp localSystemApp = new VendingProtos.ContentSyncRequestProto.SystemApp();
              localSystemApp.packageName = localPackageState1.packageName;
              localSystemApp.hasPackageName = true;
              localSystemApp.versionCode = localPackageState1.installedVersion;
              localSystemApp.hasVersionCode = true;
              ArrayList localArrayList = Lists.newArrayList(localPackageState1.certificateHashes);
              Collections.sort(localArrayList);
              localSystemApp.certificateHash = ((String[])localArrayList.toArray(new String[localArrayList.size()]));
              arrayOfSystemApp[m] = localSystemApp;
            }
          }
        }
        localContentSyncRequestProto.sideloadedAppCount = paramInt5;
        localContentSyncRequestProto.hasSideloadedAppCount = true;
        return localContentSyncRequestProto;
      }
    }
    return null;
  }
  
  final void handleContentSyncResponse(final int paramInt1, int paramInt2, final int paramInt3)
  {
    if (paramInt2 == paramInt1) {
      try
      {
        Iterator localIterator = this.mReplicationListeners.iterator();
        while (localIterator.hasNext())
        {
          final Listener localListener = (Listener)localIterator.next();
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramInt2);
          arrayOfObject[1] = Integer.valueOf(paramInt3);
          FinskyLog.d("Completed %d account content syncs with %d successful.", arrayOfObject);
          if (localListener != null) {
            this.mNotificationHandler.post(new Runnable()
            {
              public final void run()
              {
                AppStatesReplicator.Listener localListener = localListener;
                if (paramInt3 == paramInt1) {}
                for (boolean bool = true;; bool = false)
                {
                  localListener.onFinished(bool);
                  return;
                }
              }
            });
          }
        }
        this.mReplicationListeners.clear();
      }
      finally {}
    }
  }
  
  public final void load(final Runnable paramRunnable)
  {
    this.mLibraries.load(new Runnable()
    {
      public final void run()
      {
        if (paramRunnable != null) {
          AppStatesReplicator.this.mNotificationHandler.post(paramRunnable);
        }
      }
    });
  }
  
  final void performRequests(final List<Account> paramList, final int[] paramArrayOfInt1, final int[] paramArrayOfInt2, List<VendingProtos.ContentSyncRequestProto> paramList1, List<Response.Listener<VendingProtos.ContentSyncResponseProto>> paramList2)
  {
    if (paramList.size() == 0)
    {
      handleContentSyncResponse(0, 0, 0);
      return;
    }
    int i = 0;
    label20:
    VendingProtos.ContentSyncRequestProto localContentSyncRequestProto;
    Response.Listener localListener;
    VendingApi localVendingApi;
    AdIdProvider localAdIdProvider;
    int j;
    if (i < paramList.size())
    {
      Account localAccount = (Account)paramList.get(i);
      localContentSyncRequestProto = (VendingProtos.ContentSyncRequestProto)paramList1.get(i);
      localListener = (Response.Listener)paramList2.get(i);
      localVendingApi = this.mVendingApiFactory.getApi(localAccount);
      localAdIdProvider = this.mAdIdProvider;
      Account[] arrayOfAccount = AccountHandler.getAccounts(FinskyApp.get());
      j = 0;
      label100:
      if (j >= arrayOfAccount.length) {
        break label264;
      }
      if (!arrayOfAccount[j].equals(localAccount)) {
        break label258;
      }
    }
    label258:
    label264:
    for (String str = Integer.toString(j);; str = null)
    {
      Response.ErrorListener local4 = new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          int[] arrayOfInt = paramArrayOfInt1;
          arrayOfInt[0] = (1 + arrayOfInt[0]);
          AppStatesReplicator.this.handleContentSyncResponse(paramList.size(), paramArrayOfInt1[0], paramArrayOfInt2[0]);
        }
      };
      VendingRequest localVendingRequest = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.ContentSyncRequestProto.class, localContentSyncRequestProto, VendingProtos.ContentSyncResponseProto.class, localListener, localVendingApi.mApiContext, local4);
      if (localAdIdProvider != null)
      {
        if (!TextUtils.isEmpty(localAdIdProvider.getPublicAndroidId())) {
          localVendingRequest.addExtraHeader("X-Public-Android-Id", localAdIdProvider.getPublicAndroidId());
        }
        if (VendingApi.SEND_AD_ID_FOR_CONTENT_SYNC)
        {
          localVendingRequest.mAdIdProvider = localAdIdProvider;
          localVendingRequest.mIncludeAdIdAsHeader = true;
        }
      }
      if (!TextUtils.isEmpty(str)) {
        localVendingRequest.addExtraHeader("X-Account-Ordinal", str);
      }
      localVendingRequest.mAvoidBulkCancel = true;
      localVendingApi.mRequestQueue.add(localVendingRequest);
      i++;
      break label20;
      break;
      j++;
      break label100;
    }
  }
  
  /* Error */
  public final void replicate(Listener paramListener)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 33	com/google/android/finsky/appstate/AppStatesReplicator:mReplicationListeners	Ljava/util/List;
    //   6: aload_1
    //   7: invokeinterface 323 2 0
    //   12: pop
    //   13: aload_0
    //   14: getfield 33	com/google/android/finsky/appstate/AppStatesReplicator:mReplicationListeners	Ljava/util/List;
    //   17: invokeinterface 69 1 0
    //   22: istore 4
    //   24: iload 4
    //   26: iconst_1
    //   27: if_icmple +6 -> 33
    //   30: aload_0
    //   31: monitorexit
    //   32: return
    //   33: aload_0
    //   34: getfield 45	com/google/android/finsky/appstate/AppStatesReplicator:mBackgroundHandler	Landroid/os/Handler;
    //   37: new 325	com/google/android/finsky/appstate/AppStatesReplicator$2
    //   40: dup
    //   41: aload_0
    //   42: invokespecial 328	com/google/android/finsky/appstate/AppStatesReplicator$2:<init>	(Lcom/google/android/finsky/appstate/AppStatesReplicator;)V
    //   45: invokevirtual 211	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   48: pop
    //   49: goto -19 -> 30
    //   52: astore_2
    //   53: aload_0
    //   54: monitorexit
    //   55: aload_2
    //   56: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	AppStatesReplicator
    //   0	57	1	paramListener	Listener
    //   52	4	2	localObject	Object
    //   22	6	4	i	int
    // Exception table:
    //   from	to	target	type
    //   2	24	52	finally
    //   33	49	52	finally
  }
  
  public static abstract interface Listener
  {
    public abstract void onFinished(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.AppStatesReplicator
 * JD-Core Version:    0.7.0.1
 */