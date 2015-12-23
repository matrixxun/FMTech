package com.google.android.finsky.services;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.text.TextUtils;
import com.android.vending.licensing.ILicenseResultListener;
import com.android.vending.licensing.ILicensingService.Stub;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.VendingProtos.CheckLicenseRequestProto;
import com.google.android.finsky.protos.VendingProtos.CheckLicenseResponseProto;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingRequest;
import java.util.List;

public class LicensingService
  extends Service
{
  private final ILicensingService.Stub mBinder = new ILicensingService.Stub()
  {
    private void checkLicense(long paramAnonymousLong, String paramAnonymousString1, final ILicenseResultListener paramAnonymousILicenseResultListener, int paramAnonymousInt, String paramAnonymousString2)
    {
      VendingProtos.CheckLicenseRequestProto localCheckLicenseRequestProto = new VendingProtos.CheckLicenseRequestProto();
      localCheckLicenseRequestProto.packageName = paramAnonymousString1;
      localCheckLicenseRequestProto.hasPackageName = true;
      localCheckLicenseRequestProto.versionCode = paramAnonymousInt;
      localCheckLicenseRequestProto.hasVersionCode = true;
      localCheckLicenseRequestProto.nonce = paramAnonymousLong;
      localCheckLicenseRequestProto.hasNonce = true;
      VendingApi localVendingApi = FinskyApp.get().getVendingApi(paramAnonymousString2);
      Response.Listener local1 = new Response.Listener() {};
      Response.ErrorListener local2 = new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymous2VolleyError)
        {
          LicensingService.access$000(paramAnonymousILicenseResultListener, 257, null, null);
        }
      };
      VendingRequest localVendingRequest = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.CheckLicenseRequestProto.class, localCheckLicenseRequestProto, VendingProtos.CheckLicenseResponseProto.class, local1, localVendingApi.mApiContext, local2);
      localVendingApi.mRequestQueue.add(localVendingRequest);
    }
    
    public final void checkLicense(long paramAnonymousLong, String paramAnonymousString, ILicenseResultListener paramAnonymousILicenseResultListener)
    {
      int i;
      AppStates.AppState localAppState;
      try
      {
        PackageInfo localPackageInfo = LicensingService.this.getPackageManager().getPackageInfo(paramAnonymousString, 0);
        if (localPackageInfo.applicationInfo.uid != getCallingUid())
        {
          LicensingService.access$000(paramAnonymousILicenseResultListener, 259, null, null);
          return;
        }
        i = localPackageInfo.versionCode;
        AppStates localAppStates = FinskyApp.get().mAppStates;
        localAppStates.mStateStore.load();
        localAppState = localAppStates.getApp(paramAnonymousString);
        if (localAppState == null)
        {
          FinskyLog.wtf("Unexpected null appState for %s", new Object[] { paramAnonymousString });
          LicensingService.access$000(paramAnonymousILicenseResultListener, 258, null, null);
          return;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        LicensingService.access$000(paramAnonymousILicenseResultListener, 258, null, null);
        return;
      }
      if (localAppState.installerData != null)
      {
        String str = localAppState.installerData.accountName;
        if (!TextUtils.isEmpty(str))
        {
          checkLicense(paramAnonymousLong, paramAnonymousString, paramAnonymousILicenseResultListener, i, str);
          return;
        }
      }
      Libraries localLibraries = FinskyApp.get().mLibraries;
      localLibraries.blockingLoad();
      List localList = localLibraries.getAppOwners(paramAnonymousString, localAppState.packageManagerState.certificateHashes);
      if (!localList.isEmpty())
      {
        checkLicense(paramAnonymousLong, paramAnonymousString, paramAnonymousILicenseResultListener, i, ((Account)localList.get(0)).name);
        return;
      }
      Account localAccount = AccountHandler.getFirstAccount(LicensingService.this);
      if (localAccount != null)
      {
        checkLicense(paramAnonymousLong, paramAnonymousString, paramAnonymousILicenseResultListener, i, localAccount.name);
        return;
      }
      LicensingService.access$000(paramAnonymousILicenseResultListener, 1, null, null);
    }
  };
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.LicensingService
 * JD-Core Version:    0.7.0.1
 */