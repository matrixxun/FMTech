package com.google.android.finsky.utils;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class OptionalPermissionsBucketer
{
  protected static final String[] CALENDAR_PERMISSIONS = { "android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR" };
  protected static final String[] CAMERA_PERMISSIONS = { "android.permission.CAMERA" };
  protected static final String[] CONTACTS_PERMISSIONS = { "android.permission.GET_ACCOUNTS", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS" };
  protected static final String[] LOCATION_PERMISSIONS = { "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "com.google.android.gms.permission.CAR_SPEED" };
  protected static final String[] MICROPHONE_PERMISSIONS = { "android.permission.RECORD_AUDIO" };
  protected static final String[] PHONE_PERMISSIONS = { "android.permission.CALL_PHONE", "android.permission.PROCESS_OUTGOING_CALLS", "android.permission.READ_CALL_LOG", "android.permission.READ_PHONE_STATE", "android.permission.USE_SIP", "android.permission.WRITE_CALL_LOG", "com.android.voicemail.permission.ADD_VOICEMAIL" };
  protected static final String[] SENSORS_PERMISSIONS = { "android.permission.BODY_SENSORS" };
  protected static final String[] SMS_PERMISSIONS = { "android.permission.READ_SMS", "android.permission.READ_CELL_BROADCASTS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_MMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.SEND_SMS" };
  protected static final String[] STORAGE_PERMISSIONS = { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" };
  private static Map<String, Integer> sCachedPermissionsMap = null;
  
  private static void bucketPermissions(Map<String, Integer> paramMap, Set<String> paramSet, boolean paramBoolean, PermissionBucket[] paramArrayOfPermissionBucket)
  {
    boolean bool = ((Boolean)G.otherOptionalPermissionsOnlyShowDangerous.get()).booleanValue();
    String[] arrayOfString = Utils.commaUnpackStrings((String)G.additionalRemovedPermissionsM.get());
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      try
      {
        PermissionInfo localPermissionInfo2 = FinskyApp.get().getPackageManager().getPermissionInfo(str, 0);
        localPermissionInfo1 = localPermissionInfo2;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          Integer localInteger;
          int i;
          int j;
          label256:
          int n;
          label336:
          int k;
          PermissionBucket localPermissionBucket;
          label720:
          label729:
          CharSequence localCharSequence;
          Object localObject;
          label981:
          PermissionInfo localPermissionInfo1 = null;
        }
      }
      if (localPermissionInfo1 != null)
      {
        localInteger = (Integer)paramMap.get(str);
        if (localInteger == null)
        {
          i = 0xF & localPermissionInfo1.protectionLevel;
          if (((bool) && (i != 1)) || (i == 2)) {
            continue;
          }
          j = -1;
          switch (str.hashCode())
          {
          default: 
            switch (j)
            {
            default: 
              int m = arrayOfString.length;
              n = 0;
              if (n < m) {
                if (arrayOfString[n].equals(str)) {
                  k = 1;
                }
              }
              break;
            }
            break;
          }
        }
        while (k == 0)
        {
          localInteger = Integer.valueOf(9);
          localPermissionBucket = paramArrayOfPermissionBucket[localInteger.intValue()];
          if (localPermissionBucket != null) {
            break label729;
          }
          switch (localInteger.intValue())
          {
          default: 
            throw new IllegalStateException("invalid permission bucket.");
            if (!str.equals("android.permission.READ_PROFILE")) {
              break label256;
            }
            j = 0;
            break label256;
            if (!str.equals("android.permission.WRITE_PROFILE")) {
              break label256;
            }
            j = 1;
            break label256;
            if (!str.equals("android.permission.READ_SOCIAL_STREAM")) {
              break label256;
            }
            j = 2;
            break label256;
            if (!str.equals("android.permission.WRITE_SOCIAL_STREAM")) {
              break label256;
            }
            j = 3;
            break label256;
            if (!str.equals("android.permission.READ_USER_DICTIONARY")) {
              break label256;
            }
            j = 4;
            break label256;
            if (!str.equals("android.permission.WRITE_USER_DICTIONARY")) {
              break label256;
            }
            j = 5;
            break label256;
            if (!str.equals("android.permission.WRITE_SMS")) {
              break label256;
            }
            j = 6;
            break label256;
            if (!str.equals("com.android.browser.permission.READ_HISTORY_BOOKMARKS")) {
              break label256;
            }
            j = 7;
            break label256;
            if (!str.equals("com.android.browser.permission.WRITE_HISTORY_BOOKMARKS")) {
              break label256;
            }
            j = 8;
            break label256;
            if (!str.equals("android.permission.AUTHENTICATE_ACCOUNTS")) {
              break label256;
            }
            j = 9;
            break label256;
            if (!str.equals("android.permission.MANAGE_ACCOUNTS")) {
              break label256;
            }
            j = 10;
            break label256;
            if (!str.equals("android.permission.USE_CREDENTIALS")) {
              break label256;
            }
            j = 11;
            break label256;
            if (!str.equals("android.permission.SUBSCRIBED_FEEDS_READ")) {
              break label256;
            }
            j = 12;
            break label256;
            if (!str.equals("android.permission.SUBSCRIBED_FEEDS_WRITE")) {
              break label256;
            }
            j = 13;
            break label256;
            k = 1;
            continue;
            n++;
            break label336;
            k = 0;
          }
        }
        localPermissionBucket = new PermissionBucket(0, 2130837839, 2131362440, 2131362439);
        paramArrayOfPermissionBucket[localInteger.intValue()] = localPermissionBucket;
        localCharSequence = localPermissionInfo1.loadLabel(FinskyApp.get().getPackageManager());
        if (localCharSequence == null) {}
        for (localObject = null; !TextUtils.isEmpty((CharSequence)localObject); localObject = localCharSequence.toString())
        {
          if (!paramBoolean) {
            break label981;
          }
          localPermissionBucket.mNewPermissionDescriptions.add(localObject);
          break;
          localPermissionBucket = new PermissionBucket(2, 2130837842, 2131362444, 2131362443);
          break label720;
          localPermissionBucket = new PermissionBucket(3, 2130837848, 2131362446, 2131362445);
          break label720;
          localPermissionBucket = new PermissionBucket(5, 2130837852, 2131362452, 2131362451);
          break label720;
          localPermissionBucket = new PermissionBucket(7, 2130837850, 2131362456, 2131362455);
          break label720;
          localPermissionBucket = new PermissionBucket(8, 2130837849, 2131362458, 2131362457);
          break label720;
          localPermissionBucket = new PermissionBucket(1, 2130837840, 2131362442, 2131362441);
          break label720;
          localPermissionBucket = new PermissionBucket(4, 2130837851, 2131362448, 2131362447);
          break label720;
          localPermissionBucket = new PermissionBucket(6, 2130837838, 2131362454, 2131362453);
          break label720;
          localPermissionBucket = new PermissionBucket(9, 2130837854, 2131362450, 2131362449);
          break label720;
        }
        localPermissionBucket.mExistingPermissionDescriptions.add(localObject);
      }
    }
  }
  
  public static PermissionData getPermissionBuckets(String[] paramArrayOfString, Set<String> paramSet)
  {
    PermissionBucket[] arrayOfPermissionBucket = new PermissionBucket[10];
    if (paramArrayOfString == null) {
      return new PermissionData(arrayOfPermissionBucket, 9, false);
    }
    Map localMap;
    if (sCachedPermissionsMap != null) {
      localMap = sCachedPermissionsMap;
    }
    for (;;)
    {
      Set localSet = Sets.newHashSet(paramArrayOfString);
      if (paramSet != null) {
        localSet.removeAll(paramSet);
      }
      bucketPermissions(localMap, localSet, true, arrayOfPermissionBucket);
      if (paramSet != null) {
        bucketPermissions(localMap, paramSet, false, arrayOfPermissionBucket);
      }
      return new PermissionData(arrayOfPermissionBucket, 9, false);
      HashMap localHashMap = new HashMap();
      String[] arrayOfString1 = CAMERA_PERMISSIONS;
      int i = arrayOfString1.length;
      for (int j = 0; j < i; j++) {
        localHashMap.put(arrayOfString1[j], Integer.valueOf(1));
      }
      String[] arrayOfString2 = CALENDAR_PERMISSIONS;
      int k = arrayOfString2.length;
      for (int m = 0; m < k; m++) {
        localHashMap.put(arrayOfString2[m], Integer.valueOf(0));
      }
      String[] arrayOfString3 = CONTACTS_PERMISSIONS;
      int n = arrayOfString3.length;
      for (int i1 = 0; i1 < n; i1++) {
        localHashMap.put(arrayOfString3[i1], Integer.valueOf(2));
      }
      String[] arrayOfString4 = LOCATION_PERMISSIONS;
      int i2 = arrayOfString4.length;
      for (int i3 = 0; i3 < i2; i3++) {
        localHashMap.put(arrayOfString4[i3], Integer.valueOf(3));
      }
      String[] arrayOfString5 = MICROPHONE_PERMISSIONS;
      int i4 = arrayOfString5.length;
      for (int i5 = 0; i5 < i4; i5++) {
        localHashMap.put(arrayOfString5[i5], Integer.valueOf(4));
      }
      String[] arrayOfString6 = PHONE_PERMISSIONS;
      int i6 = arrayOfString6.length;
      for (int i7 = 0; i7 < i6; i7++) {
        localHashMap.put(arrayOfString6[i7], Integer.valueOf(5));
      }
      String[] arrayOfString7 = SENSORS_PERMISSIONS;
      int i8 = arrayOfString7.length;
      for (int i9 = 0; i9 < i8; i9++) {
        localHashMap.put(arrayOfString7[i9], Integer.valueOf(6));
      }
      String[] arrayOfString8 = SMS_PERMISSIONS;
      int i10 = arrayOfString8.length;
      for (int i11 = 0; i11 < i10; i11++) {
        localHashMap.put(arrayOfString8[i11], Integer.valueOf(7));
      }
      String[] arrayOfString9 = STORAGE_PERMISSIONS;
      int i12 = arrayOfString9.length;
      for (int i13 = 0; i13 < i12; i13++) {
        localHashMap.put(arrayOfString9[i13], Integer.valueOf(8));
      }
      localMap = Collections.unmodifiableMap(localHashMap);
      sCachedPermissionsMap = localMap;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.OptionalPermissionsBucketer
 * JD-Core Version:    0.7.0.1
 */