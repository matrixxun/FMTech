package com.google.android.finsky.utils;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Iterator;
import java.util.List;

public final class DeviceManagementHelper
{
  private static DeviceManagementHelper sInstance = null;
  private boolean cachedValuesStable = false;
  private final ContentResolver contentResolver;
  private final DevicePolicyManager devicePolicyManager;
  private boolean isDeviceOwner = false;
  private boolean isProfileOwner = false;
  
  private DeviceManagementHelper(Context paramContext)
  {
    this.devicePolicyManager = ((DevicePolicyManager)paramContext.getSystemService("device_policy"));
    this.contentResolver = paramContext.getContentResolver();
  }
  
  public static DeviceManagementHelper getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null) {
        sInstance = new DeviceManagementHelper(paramContext);
      }
      DeviceManagementHelper localDeviceManagementHelper = sInstance;
      return localDeviceManagementHelper;
    }
    finally {}
  }
  
  @TargetApi(21)
  private void updateCachedValues()
  {
    for (;;)
    {
      try
      {
        boolean bool1 = this.cachedValuesStable;
        if (bool1) {
          return;
        }
        if (this.devicePolicyManager == null)
        {
          localList = null;
          if (localList == null) {
            break;
          }
          Iterator localIterator = localList.iterator();
          if (!localIterator.hasNext()) {
            break;
          }
          String str = ((ComponentName)localIterator.next()).getPackageName();
          this.isDeviceOwner |= this.devicePolicyManager.isDeviceOwnerApp(str);
          this.isProfileOwner |= this.devicePolicyManager.isProfileOwnerApp(str);
          continue;
        }
        List localList = this.devicePolicyManager.getActiveAdmins();
      }
      finally {}
    }
    if (!this.isProfileOwner) {
      if (Settings.Global.getInt(this.contentResolver, "device_provisioned", 0) != 0) {
        break label153;
      }
    }
    for (;;)
    {
      this.cachedValuesStable = bool2;
      break;
      boolean bool2 = false;
      continue;
      label153:
      bool2 = true;
    }
  }
  
  public final boolean isManaged(Account paramAccount)
  {
    if (!((Boolean)G.deviceManagementHelperEnabled.get()).booleanValue()) {
      return false;
    }
    if ((paramAccount != null) && (paramAccount.type.equals("com.google.work"))) {
      return true;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return false;
    }
    updateCachedValues();
    return (this.isDeviceOwner) || (this.isProfileOwner);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DeviceManagementHelper
 * JD-Core Version:    0.7.0.1
 */