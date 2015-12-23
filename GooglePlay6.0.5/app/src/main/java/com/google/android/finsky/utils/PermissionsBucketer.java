package com.google.android.finsky.utils;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PermissionsBucketer
{
  protected static final String[] BILLING_PERMISSIONS_BUCKET = { "com.android.vending.BILLING" };
  protected static final String[] BLUETOOTH_CONNECTION_INFORMATION;
  protected static final String[] CALENDAR_PERMISSIONS;
  protected static final String[] CAMERA_PERMISSIONS;
  protected static final String[] CONTACTS_PERMISSIONS;
  protected static final String[] DEVICE_AND_APP_HISTORY_PERMISSIONS;
  protected static final String[] DEVICE_ID_AND_CALL_INFORMATION;
  protected static final String[] IDENTITY_PERMISSIONS = { "android.permission.GET_ACCOUNTS", "android.permission.MANAGE_ACCOUNTS", "android.permission.READ_PROFILE", "android.permission.WRITE_PROFILE" };
  protected static final String[] IGNORED_PERMISSIONS;
  protected static final String[] LOCATION_PERMISSIONS;
  protected static final String[] MIC_PERMISSIONS;
  protected static final String[] PHONE_PERMISSIONS;
  protected static final String[] PHOTOS_MEDIA_FILES_PERMISSIONS;
  protected static final String[] REDIRECT_INTERNET_TRAFFIC;
  protected static final String[] SMS_PERMISSIONS;
  protected static final String[] WEARABLE_ACTIVITY_PERMISSIONS;
  protected static final String[] WIFI_CONNECTION_INFORMATION;
  private static Set<String> sCachedIgnoredPermissionSet = null;
  private static Map<String, Integer> sCachedPermissionsMap;
  
  static
  {
    CALENDAR_PERMISSIONS = new String[] { "android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR" };
    CONTACTS_PERMISSIONS = new String[] { "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS" };
    LOCATION_PERMISSIONS = new String[] { "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", "android.permission.ACCESS_GPS", "com.google.android.gms.permission.CAR_SPEED" };
    SMS_PERMISSIONS = new String[] { "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.WRITE_SMS", "android.permission.SEND_SMS", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_WAP_PUSH" };
    PHONE_PERMISSIONS = new String[] { "android.permission.CALL_PHONE", "android.permission.WRITE_CALL_LOG", "android.permission.READ_CALL_LOG", "android.permission.CALL_PRIVILEGED", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.MODIFY_PHONE_STATE" };
    PHOTOS_MEDIA_FILES_PERMISSIONS = new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.MOUNT_FORMAT_FILESYSTEMS", "android.permission.MOUNT_UNMOUNT_FILESYSTEMS" };
    CAMERA_PERMISSIONS = new String[] { "android.permission.CAMERA", "android.permission.RECORD_VIDEO" };
    MIC_PERMISSIONS = new String[] { "android.permission.RECORD_AUDIO" };
    DEVICE_AND_APP_HISTORY_PERMISSIONS = new String[] { "android.permission.READ_LOGS", "android.permission.GET_TASKS", "android.permission.DUMP", "com.android.browser.permission.READ_HISTORY_BOOKMARKS" };
    REDIRECT_INTERNET_TRAFFIC = new String[] { "android.permission.WRITE_APN_SETTINGS" };
    WIFI_CONNECTION_INFORMATION = new String[] { "android.permission.ACCESS_WIFI_STATE" };
    BLUETOOTH_CONNECTION_INFORMATION = new String[] { "android.permission.BLUETOOTH_ADMIN" };
    DEVICE_ID_AND_CALL_INFORMATION = new String[] { "android.permission.READ_PHONE_STATE" };
    WEARABLE_ACTIVITY_PERMISSIONS = new String[] { "android.permission.BODY_SENSORS" };
    IGNORED_PERMISSIONS = new String[] { "android.permission.ACCESS_MOCK_LOCATION", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCOUNT_MANAGER", "android.permission.AUTHENTICATE_ACCOUNTS", "android.permission.BATTERY_STATS", "android.permission.BLUETOOTH", "android.permission.BROADCAST_STICKY", "android.permission.CHANGE_CONFIGURATION", "android.permission.CHANGE_NETWORK_STATE", "android.permission.CHANGE_WIFI_MULTICAST_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.CHANGE_WIMAX_STATE", "android.permission.CLEAR_APP_CACHE", "android.permission.DISABLE_KEYGUARD", "android.permission.EXPAND_STATUS_BAR", "android.permission.FLASHLIGHT", "android.permission.GET_PACKAGE_SIZE", "android.permission.INTERNET", "android.permission.KILL_BACKGROUND_PROCESSES", "android.permission.MODIFY_AUDIO_SETTINGS", "android.permission.NFC", "android.permission.PERSISTENT_ACTIVITY", "android.permission.READ_SYNC_SETTINGS", "android.permission.READ_USER_DICTIONARY", "android.permission.RECEIVE_BOOT_COMPLETED", "android.permission.REORDER_TASKS", "android.permission.SERIAL_PORT", "android.permission.SET_ALWAYS_FINISH", "android.permission.SET_ANIMATION_SCALE", "android.permission.SET_DEBUG_APP", "android.permission.SET_PREFERRED_APPLICATIONS", "android.permission.SET_PROCESS_LIMIT", "android.permission.SET_TIME_ZONE", "android.permission.SET_WALLPAPER", "android.permission.SIGNAL_PERSISTENT_PROCESSES", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.USE_CREDENTIALS", "android.permission.USE_SIP", "android.permission.VIBRATE", "android.permission.WAKE_LOCK", "android.permission.WRITE_SETTINGS", "android.permission.WRITE_SYNC_SETTINGS", "android.permission.WRITE_USER_DICTIONARY", "com.android.alarm.permission.SET_ALARM", "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS", "com.android.launcher.permission.INSTALL_SHORTCUT", "com.android.launcher.permission.UNINSTALL_SHORTCUT", "com.android.vending.CHECK_LICENSE", "com.google.android.providers.gsf.permission.READ_GSERVICES" };
    sCachedPermissionsMap = null;
  }
  
  private static void bucketPermissions(Map<String, Integer> paramMap, Set<String> paramSet, boolean paramBoolean1, boolean paramBoolean2, PermissionBucket[] paramArrayOfPermissionBucket)
  {
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      Integer localInteger = (Integer)paramMap.get(str1);
      if (localInteger == null)
      {
        if (sCachedIgnoredPermissionSet == null) {
          sCachedIgnoredPermissionSet = Sets.newHashSet(IGNORED_PERMISSIONS);
        }
        boolean bool = sCachedIgnoredPermissionSet.contains(str1);
        if (((bool) && (paramBoolean1)) || ((!bool) && (isDangerousPlatformDefinedPermission(str1)))) {
          localInteger = Integer.valueOf(16);
        }
      }
      else
      {
        PermissionBucket localPermissionBucket = paramArrayOfPermissionBucket[localInteger.intValue()];
        if (localPermissionBucket == null) {
          switch (localInteger.intValue())
          {
          default: 
            throw new IllegalStateException("invalid permission bucket.");
          case 0: 
            localPermissionBucket = new PermissionBucket(0, 2130837847, 2131362515, 2131362514);
          }
        }
        String str2;
        for (;;)
        {
          paramArrayOfPermissionBucket[localInteger.intValue()] = localPermissionBucket;
          str2 = getHumanReadablePermissionString(str1);
          if (TextUtils.isEmpty(str2)) {
            break;
          }
          if (!paramBoolean2) {
            break label646;
          }
          localPermissionBucket.mNewPermissionDescriptions.add(str2);
          break;
          localPermissionBucket = new PermissionBucket(3, 2130837846, 2131362513, 2131362512);
          continue;
          localPermissionBucket = new PermissionBucket(4, 2130837839, 2131362503, 2131362502);
          continue;
          localPermissionBucket = new PermissionBucket(5, 2130837842, 2131362507, 2131362506);
          continue;
          localPermissionBucket = new PermissionBucket(6, 2130837848, 2131362517, 2131362516);
          continue;
          localPermissionBucket = new PermissionBucket(8, 2130837852, 2131362523, 2131362522);
          continue;
          localPermissionBucket = new PermissionBucket(7, 2130837850, 2131362529, 2131362528);
          continue;
          localPermissionBucket = new PermissionBucket(9, 2130837849, 2131362525, 2131362524);
          continue;
          localPermissionBucket = new PermissionBucket(10, 2130837840, 2131362505, 2131362504);
          continue;
          localPermissionBucket = new PermissionBucket(11, 2130837851, 2131362519, 2131362518);
          continue;
          localPermissionBucket = new PermissionBucket(1, 2130837845, 2131362509, 2131362508);
          continue;
          localPermissionBucket = new PermissionBucket(2, 2130837843, 2131362527, 2131362526);
          continue;
          localPermissionBucket = new PermissionBucket(12, 2130837853, 2131362532, 2131362531);
          continue;
          localPermissionBucket = new PermissionBucket(13, 2130837837, 2131362501, 2131362500);
          continue;
          localPermissionBucket = new PermissionBucket(14, 2130837844, 2131362511, 2131362510);
          continue;
          localPermissionBucket = new PermissionBucket(15, 2130837838, 2131362530, 2131362534);
          continue;
          localPermissionBucket = new PermissionBucket(16, 2130837854, 2131362521, 2131362520);
        }
        label646:
        localPermissionBucket.mExistingPermissionDescriptions.add(str2);
      }
    }
  }
  
  private static String getHumanReadablePermissionString(String paramString)
  {
    try
    {
      PermissionInfo localPermissionInfo = FinskyApp.get().getPackageManager().getPermissionInfo(paramString, 0);
      if (localPermissionInfo != null)
      {
        CharSequence localCharSequence = localPermissionInfo.loadLabel(FinskyApp.get().getPackageManager());
        if (localCharSequence == null) {
          return null;
        }
        String str = localCharSequence.toString();
        return str;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public static PermissionData getPermissionBuckets(String[] paramArrayOfString, Set<String> paramSet, boolean paramBoolean)
  {
    return getPermissionBuckets(paramArrayOfString, paramSet, paramBoolean, false);
  }
  
  public static PermissionData getPermissionBuckets(String[] paramArrayOfString, Set<String> paramSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    PermissionBucket[] arrayOfPermissionBucket = new PermissionBucket[17];
    if (paramArrayOfString == null) {
      return new PermissionData(arrayOfPermissionBucket, 16, false);
    }
    Map localMap;
    boolean bool;
    Set localSet2;
    Set localSet3;
    int i28;
    if (sCachedPermissionsMap != null)
    {
      localMap = sCachedPermissionsMap;
      if (paramBoolean1) {
        break label966;
      }
      bool = false;
      if (paramSet != null)
      {
        localSet2 = Sets.newHashSet(paramArrayOfString);
        localSet2.removeAll(paramSet);
        localSet3 = localMap.keySet();
        Iterator localIterator1 = localSet2.iterator();
        while (localIterator1.hasNext()) {
          if (localSet3.contains((String)localIterator1.next())) {
            i28 = 1;
          }
        }
      }
    }
    else
    {
      for (;;)
      {
        if (i28 != 0) {
          break label916;
        }
        return new PermissionData(arrayOfPermissionBucket, 16, false);
        HashMap localHashMap = new HashMap();
        String[] arrayOfString1 = BILLING_PERMISSIONS_BUCKET;
        int i = arrayOfString1.length;
        for (int j = 0; j < i; j++) {
          localHashMap.put(arrayOfString1[j], Integer.valueOf(0));
        }
        String[] arrayOfString2 = IDENTITY_PERMISSIONS;
        int k = arrayOfString2.length;
        for (int m = 0; m < k; m++) {
          localHashMap.put(arrayOfString2[m], Integer.valueOf(3));
        }
        String[] arrayOfString3 = CONTACTS_PERMISSIONS;
        int n = arrayOfString3.length;
        for (int i1 = 0; i1 < n; i1++) {
          localHashMap.put(arrayOfString3[i1], Integer.valueOf(5));
        }
        String[] arrayOfString4 = CALENDAR_PERMISSIONS;
        int i2 = arrayOfString4.length;
        for (int i3 = 0; i3 < i2; i3++) {
          localHashMap.put(arrayOfString4[i3], Integer.valueOf(4));
        }
        String[] arrayOfString5 = LOCATION_PERMISSIONS;
        int i4 = arrayOfString5.length;
        for (int i5 = 0; i5 < i4; i5++) {
          localHashMap.put(arrayOfString5[i5], Integer.valueOf(6));
        }
        String[] arrayOfString6 = SMS_PERMISSIONS;
        int i6 = arrayOfString6.length;
        for (int i7 = 0; i7 < i6; i7++) {
          localHashMap.put(arrayOfString6[i7], Integer.valueOf(7));
        }
        String[] arrayOfString7 = PHONE_PERMISSIONS;
        int i8 = arrayOfString7.length;
        for (int i9 = 0; i9 < i8; i9++) {
          localHashMap.put(arrayOfString7[i9], Integer.valueOf(8));
        }
        String[] arrayOfString8 = PHOTOS_MEDIA_FILES_PERMISSIONS;
        int i10 = arrayOfString8.length;
        for (int i11 = 0; i11 < i10; i11++) {
          localHashMap.put(arrayOfString8[i11], Integer.valueOf(9));
        }
        String[] arrayOfString9 = CAMERA_PERMISSIONS;
        int i12 = arrayOfString9.length;
        for (int i13 = 0; i13 < i12; i13++) {
          localHashMap.put(arrayOfString9[i13], Integer.valueOf(10));
        }
        String[] arrayOfString10 = MIC_PERMISSIONS;
        int i14 = arrayOfString10.length;
        for (int i15 = 0; i15 < i14; i15++) {
          localHashMap.put(arrayOfString10[i15], Integer.valueOf(11));
        }
        String[] arrayOfString11 = DEVICE_AND_APP_HISTORY_PERMISSIONS;
        int i16 = arrayOfString11.length;
        for (int i17 = 0; i17 < i16; i17++) {
          localHashMap.put(arrayOfString11[i17], Integer.valueOf(1));
        }
        String[] arrayOfString12 = REDIRECT_INTERNET_TRAFFIC;
        int i18 = arrayOfString12.length;
        for (int i19 = 0; i19 < i18; i19++) {
          localHashMap.put(arrayOfString12[i19], Integer.valueOf(2));
        }
        String[] arrayOfString13 = WIFI_CONNECTION_INFORMATION;
        int i20 = arrayOfString13.length;
        for (int i21 = 0; i21 < i20; i21++) {
          localHashMap.put(arrayOfString13[i21], Integer.valueOf(12));
        }
        String[] arrayOfString14 = BLUETOOTH_CONNECTION_INFORMATION;
        int i22 = arrayOfString14.length;
        for (int i23 = 0; i23 < i22; i23++) {
          localHashMap.put(arrayOfString14[i23], Integer.valueOf(13));
        }
        String[] arrayOfString15 = DEVICE_ID_AND_CALL_INFORMATION;
        int i24 = arrayOfString15.length;
        for (int i25 = 0; i25 < i24; i25++) {
          localHashMap.put(arrayOfString15[i25], Integer.valueOf(14));
        }
        String[] arrayOfString16 = WEARABLE_ACTIVITY_PERMISSIONS;
        int i26 = arrayOfString16.length;
        for (int i27 = 0; i27 < i26; i27++) {
          localHashMap.put(arrayOfString16[i27], Integer.valueOf(15));
        }
        localMap = Collections.unmodifiableMap(localHashMap);
        sCachedPermissionsMap = localMap;
        break;
        HashSet localHashSet = Sets.newHashSet(localSet2);
        localHashSet.removeAll(localSet3);
        Iterator localIterator2 = localHashSet.iterator();
        for (;;)
        {
          if (localIterator2.hasNext()) {
            if (isDangerousPlatformDefinedPermission((String)localIterator2.next()))
            {
              i28 = 1;
              break;
            }
          }
        }
        i28 = 0;
      }
      label916:
      bool = true;
    }
    paramSet = null;
    Set localSet1 = Sets.newHashSet(paramArrayOfString);
    for (;;)
    {
      bucketPermissions(localMap, localSet1, paramBoolean2, true, arrayOfPermissionBucket);
      if (paramSet != null) {
        bucketPermissions(localMap, paramSet, paramBoolean2, false, arrayOfPermissionBucket);
      }
      return new PermissionData(arrayOfPermissionBucket, 16, bool);
      label966:
      localSet1 = Sets.newHashSet(paramArrayOfString);
      bool = false;
      if (paramSet != null)
      {
        localSet1.removeAll(paramSet);
        bool = false;
      }
    }
  }
  
  public static boolean hasAcceptedBuckets(InstallerDataStore paramInstallerDataStore, String paramString)
  {
    int i = 1;
    InstallerDataStore.InstallerData localInstallerData = paramInstallerDataStore.get(paramString);
    if ((localInstallerData != null) && (localInstallerData.permissionsVersion == i)) {}
    for (;;)
    {
      if (i == 0)
      {
        PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get(paramString);
        if (localPackageState != null) {
          bool = localPackageState.isSystemApp;
        }
      }
      return bool;
      boolean bool = false;
    }
  }
  
  private static boolean isDangerousPlatformDefinedPermission(String paramString)
  {
    try
    {
      PermissionInfo localPermissionInfo = FinskyApp.get().getPackageManager().getPermissionInfo(paramString, 0);
      if (localPermissionInfo != null)
      {
        int i = localPermissionInfo.protectionLevel;
        if (i == 1) {
          return true;
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
  
  public static void setAcceptedNewBuckets(InstallerDataStore paramInstallerDataStore, String paramString)
  {
    paramInstallerDataStore.setPermissionsVersion(paramString, 1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PermissionsBucketer
 * JD-Core Version:    0.7.0.1
 */