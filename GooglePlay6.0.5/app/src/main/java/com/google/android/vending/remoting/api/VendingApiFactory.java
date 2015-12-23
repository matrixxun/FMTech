package com.google.android.vending.remoting.api;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.android.volley.RequestQueue;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.play.utils.config.GservicesValue;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VendingApiFactory
{
  private Context mContext;
  private RequestQueue mQueue;
  private final Map<String, VendingApi> mVendingApiMap = new HashMap();
  
  public VendingApiFactory(Context paramContext, RequestQueue paramRequestQueue)
  {
    this.mContext = paramContext;
    this.mQueue = paramRequestQueue;
  }
  
  private VendingApiContext getApiContext(Account paramAccount)
  {
    try
    {
      PackageInfo localPackageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
      int i = localPackageInfo.versionCode;
      String str1 = localPackageInfo.versionName;
      TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
      Context localContext = this.mContext;
      Locale localLocale = Locale.getDefault();
      String str2 = Long.toHexString(((Long)DfeApiConfig.androidId.get()).longValue());
      String str3 = localTelephonyManager.getNetworkOperatorName();
      String str4 = localTelephonyManager.getSimOperatorName();
      String str5 = localTelephonyManager.getNetworkOperator();
      String str6 = localTelephonyManager.getSimOperator();
      String str7 = Build.DEVICE;
      String str8 = Build.VERSION.SDK;
      String str9 = (String)DfeApiConfig.clientId.get();
      String str10 = (String)DfeApiConfig.loggingId.get();
      boolean bool = localContext.getResources().getBoolean(2131427334);
      VendingApiContext localVendingApiContext = new VendingApiContext(localContext, paramAccount, localLocale, str2, i, str3, str4, str5, str6, str7, str8, str9, str10, VendingApiContext.makeUserAgentString(str1, i, Build.VERSION.SDK_INT, Build.DEVICE, Build.HARDWARE, Build.PRODUCT, Build.VERSION.RELEASE, Build.MODEL, Build.ID, bool));
      return localVendingApiContext;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException("Can't find our own package", localNameNotFoundException);
    }
  }
  
  public VendingApi getApi(Account paramAccount)
  {
    synchronized (this.mVendingApiMap)
    {
      VendingApi localVendingApi = (VendingApi)this.mVendingApiMap.get(paramAccount.name);
      if (localVendingApi == null)
      {
        localVendingApi = new VendingApi(this.mQueue, getApiContext(paramAccount));
        this.mVendingApiMap.put(paramAccount.name, localVendingApi);
      }
      return localVendingApi;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.remoting.api.VendingApiFactory
 * JD-Core Version:    0.7.0.1
 */