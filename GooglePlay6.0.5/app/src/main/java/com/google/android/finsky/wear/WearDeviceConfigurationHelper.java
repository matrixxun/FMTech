package com.google.android.finsky.wear;

import android.os.Handler;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto;
import com.google.android.finsky.utils.DeviceConfigurationHelper;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.google.android.gms.wearable.Wearable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WearDeviceConfigurationHelper
  extends DeviceConfigurationHelper
{
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  private static boolean sIsLoaded;
  private static ArrayList<Runnable> sLoadedCallbacks;
  private static List<String> sNodeIds;
  private static Handler sNotificationHandler;
  private static Map<String, WearDeviceConfigurationHelper> sWearHelpers;
  public final String androidId;
  public final String buildDevice;
  public final String buildFingerprint;
  public final String buildHardware;
  public final String buildId;
  public final String buildModel;
  public final String buildProduct;
  public final String buildVersionRelease;
  public final int buildVersionSdkInt;
  public final DeviceConfiguration.DeviceConfigurationProto deviceConfigurationProto;
  public final String deviceDataVersionInfo;
  public final String loggingId;
  public final String networkOperator;
  public final String networkOperatorName;
  public final String nodeId;
  public final String simOperator;
  public final String simOperatorName;
  public final String version;
  public final int versionCode;
  
  private WearDeviceConfigurationHelper(String paramString, DataItem paramDataItem)
  {
    this.nodeId = paramString;
    DataMap localDataMap = DataMap.fromByteArray(paramDataItem.getData());
    DeviceConfiguration.DeviceConfigurationProto localDeviceConfigurationProto = new DeviceConfiguration.DeviceConfigurationProto();
    localDeviceConfigurationProto.touchScreen = getTouchScreenId(localDataMap.getInt$505cff29("touchScreen"));
    localDeviceConfigurationProto.hasTouchScreen = true;
    localDeviceConfigurationProto.keyboard = getKeyboardConfigId(localDataMap.getInt$505cff29("keyboard"));
    localDeviceConfigurationProto.hasKeyboard = true;
    localDeviceConfigurationProto.navigation = getNavigationId(localDataMap.getInt$505cff29("navigation"));
    localDeviceConfigurationProto.hasNavigation = true;
    localDeviceConfigurationProto.screenLayout = getScreenLayoutSizeId(localDataMap.getInt$505cff29("screenLayout"));
    localDeviceConfigurationProto.hasScreenLayout = true;
    localDeviceConfigurationProto.hasHardKeyboard = localDataMap.getBoolean$505cbf47("hasHardKeyboard");
    localDeviceConfigurationProto.hasHasHardKeyboard = true;
    localDeviceConfigurationProto.hasFiveWayNavigation = localDataMap.getBoolean$505cbf47("hasFiveWayNavigation");
    localDeviceConfigurationProto.hasHasFiveWayNavigation = true;
    localDeviceConfigurationProto.screenDensity = localDataMap.getInt$505cff29("screenDensity");
    localDeviceConfigurationProto.hasScreenDensity = true;
    localDeviceConfigurationProto.screenWidth = localDataMap.getInt$505cff29("screenWidth");
    localDeviceConfigurationProto.hasScreenWidth = true;
    localDeviceConfigurationProto.screenHeight = localDataMap.getInt$505cff29("screenHeight");
    localDeviceConfigurationProto.hasScreenHeight = true;
    localDeviceConfigurationProto.glEsVersion = localDataMap.getInt$505cff29("glEsVersion");
    localDeviceConfigurationProto.hasGlEsVersion = true;
    localDeviceConfigurationProto.systemSharedLibrary = localDataMap.getStringArray("systemSharedLibrary");
    localDeviceConfigurationProto.systemAvailableFeature = localDataMap.getStringArray("systemAvailableFeatures");
    localDeviceConfigurationProto.nativePlatform = localDataMap.getStringArray("nativePlatform");
    localDeviceConfigurationProto.systemSupportedLocale = localDataMap.getStringArray("systemSupportedLocales");
    localDeviceConfigurationProto.glExtension = localDataMap.getStringArray("glExtension");
    localDeviceConfigurationProto.smallestScreenWidthDp = localDataMap.getInt$505cff29("smallestScreenWidth");
    localDeviceConfigurationProto.hasSmallestScreenWidthDp = true;
    if (localDataMap.zzoF.containsKey("lowRamDevice"))
    {
      localDeviceConfigurationProto.lowRamDevice = localDataMap.getBoolean$505cbf47("lowRamDevice");
      localDeviceConfigurationProto.hasLowRamDevice = true;
    }
    this.deviceConfigurationProto = localDeviceConfigurationProto;
    this.versionCode = localDataMap.getInt$505cff29("wearskyVersionCode");
    this.version = localDataMap.getString("wearskyVersionName");
    this.androidId = localDataMap.getString("androidId");
    this.deviceDataVersionInfo = localDataMap.getString("deviceDataVersionInfo");
    this.loggingId = localDataMap.getString("loggingId");
    this.buildDevice = localDataMap.getString("buildDevice");
    this.buildFingerprint = localDataMap.getString("buildFingerprint");
    this.buildHardware = localDataMap.getString("buildHardware");
    this.buildId = localDataMap.getString("buildId");
    this.buildModel = localDataMap.getString("buildModel");
    this.buildProduct = localDataMap.getString("buildProduct");
    this.buildVersionRelease = localDataMap.getString("buildVersionRelease");
    this.buildVersionSdkInt = localDataMap.getInt$505cff29("buildVersionSdkInt");
    this.simOperator = localDataMap.getString("simOperator");
    this.simOperatorName = localDataMap.getString("simOperatorName");
    this.networkOperator = localDataMap.getString("networkOperator");
    this.networkOperatorName = localDataMap.getString("networkOperatorName");
  }
  
  /* Error */
  public static boolean checkKnownNodeId(String paramString)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 289	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sNodeIds	Ljava/util/List;
    //   6: invokeinterface 295 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface 300 1 0
    //   18: ifeq +30 -> 48
    //   21: aload_2
    //   22: invokeinterface 304 1 0
    //   27: checkcast 306	java/lang/String
    //   30: aload_0
    //   31: invokevirtual 309	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   34: istore 4
    //   36: iload 4
    //   38: ifeq -26 -> 12
    //   41: iconst_1
    //   42: istore_3
    //   43: ldc 2
    //   45: monitorexit
    //   46: iload_3
    //   47: ireturn
    //   48: iconst_0
    //   49: istore_3
    //   50: goto -7 -> 43
    //   53: astore_1
    //   54: ldc 2
    //   56: monitorexit
    //   57: aload_1
    //   58: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	59	0	paramString	String
    //   53	5	1	localObject	Object
    //   11	11	2	localIterator	java.util.Iterator
    //   42	8	3	bool1	boolean
    //   34	3	4	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   3	12	53	finally
    //   12	36	53	finally
  }
  
  public static WearDeviceConfigurationHelper get(String paramString)
  {
    try
    {
      WearDeviceConfigurationHelper localWearDeviceConfigurationHelper = (WearDeviceConfigurationHelper)sWearHelpers.get(paramString);
      return localWearDeviceConfigurationHelper;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static String[] getNodeIds()
  {
    try
    {
      String[] arrayOfString = (String[])sNodeIds.toArray(new String[sNodeIds.size()]);
      return arrayOfString;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static void handleDataItemsResult(GoogleApiClient paramGoogleApiClient, DataItemBuffer paramDataItemBuffer)
  {
    int i;
    String str;
    WearDeviceConfigurationHelper localWearDeviceConfigurationHelper;
    try
    {
      sNodeIds = Collections.emptyList();
      sWearHelpers = Collections.emptyMap();
      Status localStatus = paramDataItemBuffer.zzUc;
      if (!localStatus.isSuccess())
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(localStatus.zzakr);
        arrayOfObject2[1] = localStatus.zzanv;
        FinskyLog.w("Error %d getting data items. (%s)", arrayOfObject2);
      }
      int j;
      do
      {
        return;
        i = 0;
        j = paramDataItemBuffer.getCount();
      } while (i >= j);
      DataItem localDataItem = (DataItem)paramDataItemBuffer.get(i);
      str = WearskyUris.getnodeId(localDataItem.getUri());
      localWearDeviceConfigurationHelper = new WearDeviceConfigurationHelper(str, localDataItem);
      if (localWearDeviceConfigurationHelper.versionCode < 80400050)
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = str;
        arrayOfObject1[1] = Integer.valueOf(localWearDeviceConfigurationHelper.versionCode);
        FinskyLog.d("Discard wear node %s because v=%d", arrayOfObject1);
      }
      else if ("0".equals(localWearDeviceConfigurationHelper.androidId))
      {
        FinskyLog.d("Discard wear node %s because androidId is 0", new Object[] { str });
        Wearable.MessageApi.sendMessage(paramGoogleApiClient, str, "get_device_configuration", EMPTY_BYTE_ARRAY).setResultCallback(new ResultCallback() {});
      }
    }
    finally {}
    String[] arrayOfString = localWearDeviceConfigurationHelper.deviceConfigurationProto.systemAvailableFeature;
    int k = arrayOfString.length;
    for (int m = 0;; m++)
    {
      int n = 0;
      if (m < k)
      {
        if ("android.hardware.type.watch".equals(arrayOfString[m])) {
          n = 1;
        }
      }
      else
      {
        if (n == 0)
        {
          FinskyLog.d("Discard wear node %s because not watch", new Object[] { str });
        }
        else
        {
          FinskyLog.d("Found wear node %s", new Object[] { str });
          if (sNodeIds.size() == 0) {
            sNodeIds = new ArrayList();
          }
          sNodeIds.add(str);
          if (sWearHelpers.size() == 0) {
            sWearHelpers = new HashMap();
          }
          sWearHelpers.put(str, localWearDeviceConfigurationHelper);
        }
        i++;
        break;
      }
    }
  }
  
  public static void invalidate()
  {
    try
    {
      sIsLoaded = false;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public static void load(GoogleApiClient paramGoogleApiClient, Runnable paramRunnable)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 282	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sNotificationHandler	Landroid/os/Handler;
    //   6: ifnonnull +16 -> 22
    //   9: new 446	android/os/Handler
    //   12: dup
    //   13: invokestatic 452	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   16: invokespecial 455	android/os/Handler:<init>	(Landroid/os/Looper;)V
    //   19: putstatic 282	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sNotificationHandler	Landroid/os/Handler;
    //   22: getstatic 286	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sIsLoaded	Z
    //   25: ifeq +15 -> 40
    //   28: getstatic 282	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sNotificationHandler	Landroid/os/Handler;
    //   31: aload_1
    //   32: invokevirtual 459	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   35: pop
    //   36: ldc 2
    //   38: monitorexit
    //   39: return
    //   40: getstatic 277	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sLoadedCallbacks	Ljava/util/ArrayList;
    //   43: ifnonnull +13 -> 56
    //   46: new 431	java/util/ArrayList
    //   49: dup
    //   50: invokespecial 432	java/util/ArrayList:<init>	()V
    //   53: putstatic 277	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sLoadedCallbacks	Ljava/util/ArrayList;
    //   56: getstatic 277	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sLoadedCallbacks	Ljava/util/ArrayList;
    //   59: aload_1
    //   60: invokevirtual 460	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   63: pop
    //   64: getstatic 277	com/google/android/finsky/wear/WearDeviceConfigurationHelper:sLoadedCallbacks	Ljava/util/ArrayList;
    //   67: invokevirtual 461	java/util/ArrayList:size	()I
    //   70: iconst_1
    //   71: if_icmpne -35 -> 36
    //   74: getstatic 465	com/google/android/gms/wearable/Wearable:DataApi	Lcom/google/android/gms/wearable/DataApi;
    //   77: aload_0
    //   78: getstatic 469	com/google/android/finsky/wear/WearskyUris:URI_DEVICE_CONFIGURATION_ALL	Landroid/net/Uri;
    //   81: invokeinterface 475 3 0
    //   86: new 477	com/google/android/finsky/wear/WearDeviceConfigurationHelper$1
    //   89: dup
    //   90: aload_0
    //   91: invokespecial 480	com/google/android/finsky/wear/WearDeviceConfigurationHelper$1:<init>	(Lcom/google/android/gms/common/api/GoogleApiClient;)V
    //   94: invokevirtual 423	com/google/android/gms/common/api/PendingResult:setResultCallback	(Lcom/google/android/gms/common/api/ResultCallback;)V
    //   97: goto -61 -> 36
    //   100: astore_2
    //   101: ldc 2
    //   103: monitorexit
    //   104: aload_2
    //   105: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	106	0	paramGoogleApiClient	GoogleApiClient
    //   0	106	1	paramRunnable	Runnable
    //   100	5	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   3	22	100	finally
    //   22	36	100	finally
    //   40	56	100	finally
    //   56	97	100	finally
  }
  
  public final DeviceConfiguration.DeviceConfigurationProto getDeviceConfiguration()
  {
    return this.deviceConfigurationProto;
  }
  
  public final String getToken()
  {
    return (String)FinskyPreferences.wearDeviceConfigToken.get(this.nodeId).get();
  }
  
  public final void invalidateToken()
  {
    FinskyPreferences.wearDeviceConfigToken.get(this.nodeId).remove();
  }
  
  protected final void setToken(String paramString)
  {
    FinskyPreferences.wearDeviceConfigToken.get(this.nodeId).put(paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearDeviceConfigurationHelper
 * JD-Core Version:    0.7.0.1
 */