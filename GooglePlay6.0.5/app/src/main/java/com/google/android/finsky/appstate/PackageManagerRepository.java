package com.google.android.finsky.appstate;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class PackageManagerRepository
  implements PackageStateRepository, PackageMonitorReceiver.PackageStatusListener
{
  private static final PackageStateRepository.PackageState NOT_INSTALLED_MARKER = new PackageStateRepository.PackageState(null, null, false, false, false, false, -1, false);
  private final DevicePolicyManager mDevicePolicyManager;
  private final PackageManager mPackageManager;
  private final Map<String, PackageStateRepository.PackageState> mPackageStates = new HashMap();
  
  public PackageManagerRepository(PackageManager paramPackageManager, PackageMonitorReceiver paramPackageMonitorReceiver, DevicePolicyManager paramDevicePolicyManager)
  {
    this.mPackageManager = paramPackageManager;
    this.mDevicePolicyManager = paramDevicePolicyManager;
    if (paramPackageMonitorReceiver != null) {
      paramPackageMonitorReceiver.attach(this);
    }
  }
  
  private static String[] computeCertificateHashes(PackageInfo paramPackageInfo)
  {
    int i = paramPackageInfo.signatures.length;
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++) {
      arrayOfString[j] = Sha1Util.secureHash(paramPackageInfo.signatures[j].toByteArray());
    }
    return arrayOfString;
  }
  
  private PackageStateRepository.PackageState createPackageState(PackageInfo paramPackageInfo)
  {
    try
    {
      int i = paramPackageInfo.versionCode;
      boolean bool1;
      boolean bool2;
      label38:
      int j;
      boolean bool3;
      if ((0x1 & paramPackageInfo.applicationInfo.flags) != 0)
      {
        bool1 = true;
        if ((0x80 & paramPackageInfo.applicationInfo.flags) == 0) {
          break label116;
        }
        bool2 = true;
        j = this.mPackageManager.getApplicationEnabledSetting(paramPackageInfo.packageName);
        if (j != 0) {
          break label131;
        }
        bool3 = false;
        if (paramPackageInfo.applicationInfo.enabled) {
          break label122;
        }
        bool4 = true;
      }
      for (;;)
      {
        PackageStateRepository.PackageState localPackageState = new PackageStateRepository.PackageState(paramPackageInfo.packageName, computeCertificateHashes(paramPackageInfo), bool1, bool2, bool4, bool3, i, isActiveDeviceAdmin(paramPackageInfo.packageName));
        return localPackageState;
        bool1 = false;
        break;
        label116:
        bool2 = false;
        break label38;
        label122:
        bool4 = false;
        bool3 = false;
      }
      label131:
      if ((j == 3) || (j == 4))
      {
        bool3 = true;
        label146:
        if ((!bool3) && (j != 2)) {
          break label169;
        }
      }
      label169:
      for (boolean bool4 = true;; bool4 = false)
      {
        break;
        bool3 = false;
        break label146;
      }
      Object[] arrayOfObject;
      return NOT_INSTALLED_MARKER;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = paramPackageInfo.packageName;
      FinskyLog.w("Package %s not installed", arrayOfObject);
    }
  }
  
  private boolean isActiveDeviceAdmin(String paramString)
  {
    List localList = this.mDevicePolicyManager.getActiveAdmins();
    if (localList == null) {
      return false;
    }
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext()) {
      if (((ComponentName)localIterator.next()).getPackageName().equals(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  private PackageStateRepository.PackageState refreshEntry(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mPackageStates.put(paramString, NOT_INSTALLED_MARKER);
      return NOT_INSTALLED_MARKER;
    }
    try
    {
      PackageStateRepository.PackageState localPackageState = createPackageState(this.mPackageManager.getPackageInfo(paramString, 64));
      this.mPackageStates.put(paramString, localPackageState);
      return localPackageState;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      this.mPackageStates.put(paramString, NOT_INSTALLED_MARKER);
    }
    return NOT_INSTALLED_MARKER;
  }
  
  @TargetApi(21)
  public final boolean canLaunch(String paramString)
  {
    if (UiUtils.isAndroidTv()) {
      if (this.mPackageManager.getLeanbackLaunchIntentForPackage(paramString) == null) {}
    }
    while (this.mPackageManager.getLaunchIntentForPackage(paramString) != null)
    {
      return true;
      return false;
    }
    return false;
  }
  
  public final PackageStateRepository.PackageState get(String paramString)
  {
    try
    {
      PackageStateRepository.PackageState localPackageState1 = (PackageStateRepository.PackageState)this.mPackageStates.get(paramString);
      if (localPackageState1 == null) {
        localPackageState1 = refreshEntry(paramString, false);
      }
      PackageStateRepository.PackageState localPackageState2 = NOT_INSTALLED_MARKER;
      if (localPackageState1 == localPackageState2) {
        localPackageState1 = null;
      }
      return localPackageState1;
    }
    finally {}
  }
  
  public final Collection<PackageStateRepository.PackageState> getAllBlocking()
  {
    Utils.ensureNotOnMainThread();
    List localList = this.mPackageManager.getInstalledPackages(64);
    ArrayList localArrayList = Lists.newArrayList(localList.size());
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      PackageStateRepository.PackageState localPackageState = createPackageState((PackageInfo)localIterator.next());
      if (localPackageState != NOT_INSTALLED_MARKER) {
        localArrayList.add(localPackageState);
      }
    }
    return localArrayList;
  }
  
  public final int getVersionCode(String paramString)
  {
    try
    {
      int i = this.mPackageManager.getPackageInfo(paramString, 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return -1;
  }
  
  public final String getVersionName(String paramString)
  {
    try
    {
      String str = this.mPackageManager.getPackageInfo(paramString, 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public final void invalidate(String paramString)
  {
    try
    {
      this.mPackageStates.remove(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void onPackageAdded(String paramString)
  {
    refreshEntry(paramString, false);
  }
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++) {
      refreshEntry(paramArrayOfString[j], false);
    }
  }
  
  public final void onPackageChanged(String paramString)
  {
    refreshEntry(paramString, false);
  }
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      refreshEntry(paramString, bool);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.appstate.PackageManagerRepository
 * JD-Core Version:    0.7.0.1
 */