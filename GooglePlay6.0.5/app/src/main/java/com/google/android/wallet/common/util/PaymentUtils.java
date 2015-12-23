package com.google.android.wallet.common.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.api.WalletRequestQueue;
import com.google.android.wallet.config.G;
import com.google.android.wallet.config.G.images;
import com.google.commerce.payments.orchestration.proto.common.ContextOuterClass.NativeClientContext;
import com.google.commerce.payments.orchestration.proto.ui.common.RequestContextOuterClass.RequestContext;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.PanCategory;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CardType;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageSetOuterClass.LegalMessageByCountry;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageSetOuterClass.LegalMessageSet;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.moneta.api2.common.ExperimentProto.ExperimentContextPb;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import paymentfraud.mobile.DeviceFingerprinting.Parsed;
import paymentfraud.mobile.DeviceFingerprinting.Parsed.Properties;
import paymentfraud.mobile.DeviceFingerprinting.Parsed.State;
import paymentfraud.mobile.DeviceFingerprinting.Parsed.State.Location;
import paymentfraud.mobile.DeviceFingerprinting.Parsed.State.PackageInfo;

public final class PaymentUtils
{
  private static final Pattern NON_NUMERIC_PATTERN = Pattern.compile("[^\\d]");
  private static ImageLoader sImageLoader;
  
  public static boolean cardTypeEquals(CreditCard.CardType paramCardType1, CreditCard.CardType paramCardType2)
  {
    if (paramCardType1 == paramCardType2) {
      return true;
    }
    if ((paramCardType1 == null) || (paramCardType2 == null)) {
      return false;
    }
    return Arrays.equals(paramCardType1.typeToken, paramCardType2.typeToken);
  }
  
  @TargetApi(18)
  private static RequestContextOuterClass.RequestContext createRequestContext$12d149f4(Context paramContext, byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
  {
    ContextOuterClass.NativeClientContext localNativeClientContext = new ContextOuterClass.NativeClientContext();
    localNativeClientContext.osVersion = Integer.toString(Build.VERSION.SDK_INT);
    localNativeClientContext.device = Build.DEVICE;
    localNativeClientContext.marketClientId = ((String)G.marketClientId.get());
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    boolean bool = PermissionDelegate.selfHasPermission(paramContext, "android.permission.READ_PHONE_STATE");
    int i;
    if (bool) {
      i = 1;
    }
    for (;;)
    {
      localNativeClientContext.readPhoneStatePermissionState = i;
      String str5;
      if (localTelephonyManager != null)
      {
        if (!bool) {
          break label748;
        }
        str5 = localTelephonyManager.getSubscriberId();
        label93:
        if (!TextUtils.isEmpty(str5)) {
          localNativeClientContext.imsiHash = sha1HashAsBase64(str5);
        }
      }
      try
      {
        localNativeClientContext.roundedImsi = (1000L * (Long.parseLong(str5) / 1000L));
        label129:
        final String str6 = (String)G.mccMncOverride.get();
        label187:
        String str8;
        label229:
        PackageManager localPackageManager;
        PackageInfo localPackageInfo;
        if (!TextUtils.isEmpty(str6))
        {
          Handler localHandler = new Handler(Looper.getMainLooper());
          Runnable local1 = new Runnable()
          {
            public final void run()
            {
              Context localContext = this.val$appContext;
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = str6;
              Toast.makeText(localContext, String.format("Overriding MCC/MNC with %s", arrayOfObject), 0).show();
            }
          };
          localHandler.post(local1);
          localNativeClientContext.mccMnc = str6;
          String str7 = localTelephonyManager.getSimOperatorName();
          if (!TextUtils.isEmpty(str7)) {
            localNativeClientContext.simOperatorName = str7;
          }
          if (Build.VERSION.SDK_INT >= 18)
          {
            if (!bool) {
              break label779;
            }
            str8 = localTelephonyManager.getGroupIdLevel1();
            if (!TextUtils.isEmpty(str8)) {
              localNativeClientContext.gid1 = str8;
            }
          }
          DisplayMetrics localDisplayMetrics = AndroidUtils.getDisplayMetrics(paramContext);
          localNativeClientContext.screenWidthPx = localDisplayMetrics.widthPixels;
          localNativeClientContext.screenHeightPx = localDisplayMetrics.heightPixels;
          localNativeClientContext.screenXDpi = localDisplayMetrics.xdpi;
          localNativeClientContext.screenYDpi = localDisplayMetrics.ydpi;
          localNativeClientContext.androidClientSubtype = 1;
          localPackageManager = paramContext.getPackageManager();
          localPackageInfo = null;
        }
        try
        {
          localPackageInfo = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0);
          localNativeClientContext.packageName = localPackageInfo.packageName;
          localNativeClientContext.packageVersionCode = Integer.toString(localPackageInfo.versionCode);
          localNativeClientContext.packageVersionName = localPackageInfo.versionName;
          label350:
          DeviceFingerprinting.Parsed localParsed = new DeviceFingerprinting.Parsed();
          DeviceFingerprinting.Parsed.Properties localProperties = new DeviceFingerprinting.Parsed.Properties();
          localProperties.operatingSystem = 0;
          String str3;
          if ((localTelephonyManager != null) && (bool))
          {
            str3 = localTelephonyManager.getDeviceId();
            if (!TextUtils.isEmpty(str3)) {
              switch (localTelephonyManager.getPhoneType())
              {
              }
            }
          }
          for (;;)
          {
            String str4 = localTelephonyManager.getLine1Number();
            if (!TextUtils.isEmpty(str4)) {
              localProperties.phoneNumber = str4;
            }
            localProperties.androidId = ((Long)G.androidId.get()).longValue();
            localProperties.deviceName = Build.DEVICE;
            localProperties.productName = Build.PRODUCT;
            localProperties.modelName = Build.MODEL;
            localProperties.manufacturer = Build.MANUFACTURER;
            localProperties.buildFingerprint = Build.FINGERPRINT;
            localProperties.osVersion = Build.VERSION.RELEASE;
            localProperties.androidBuildBrand = Build.BRAND;
            localParsed.properties = localProperties;
            localParsed.state = getState(paramContext, localPackageInfo, null, localTelephonyManager);
            localNativeClientContext.riskData = localParsed;
            RequestContextOuterClass.RequestContext localRequestContext = new RequestContextOuterClass.RequestContext();
            if (paramArrayOfByte != null) {
              localRequestContext.sessionData = paramArrayOfByte;
            }
            localRequestContext.nativeContext = localNativeClientContext;
            Locale localLocale = paramContext.getResources().getConfiguration().locale;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(localLocale.getLanguage());
            String str1 = localLocale.getCountry();
            String str2 = localLocale.getVariant();
            if (!TextUtils.isEmpty(str1)) {
              localStringBuilder.append('-').append(str1);
            }
            if (!TextUtils.isEmpty(str2)) {
              localStringBuilder.append('-').append(str2);
            }
            localRequestContext.languageCode = localStringBuilder.toString();
            localRequestContext.clientType = 2;
            localRequestContext.clientVersion = 11403000L;
            localRequestContext.requestId = Long.toString(UUID.randomUUID().getLeastSignificantBits());
            localRequestContext.isPrefetchRequest = false;
            if ((paramArrayOfInt != null) && (paramArrayOfInt.length > 0))
            {
              localRequestContext.experimentContext = new ExperimentProto.ExperimentContextPb();
              localRequestContext.experimentContext.experimentId = paramArrayOfInt;
            }
            return localRequestContext;
            i = 2;
            break;
            label748:
            str5 = null;
            break label93;
            String str9 = localTelephonyManager.getSimOperator();
            if (TextUtils.isEmpty(str9)) {
              break label187;
            }
            localNativeClientContext.mccMnc = str9;
            break label187;
            label779:
            str8 = null;
            break label229;
            if (str3.length() <= 8)
            {
              localProperties.esn = str3;
            }
            else
            {
              localProperties.meid = str3;
              continue;
              localProperties.imei = str3;
            }
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          break label350;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        break label129;
      }
    }
  }
  
  public static RequestContextOuterClass.RequestContext createRequestContextForSharedLibrary$793b517b(Context paramContext, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return createRequestContext$12d149f4(paramContext.getApplicationContext(), paramArrayOfByte, 11403000, null);
  }
  
  public static LegalMessageOuterClass.LegalMessage findLegalMessageByCountry(LegalMessageSetOuterClass.LegalMessageSet paramLegalMessageSet, String paramString)
  {
    if (paramLegalMessageSet == null) {
      return null;
    }
    int i = 0;
    int j = paramLegalMessageSet.messageByCountry.length;
    while (i < j)
    {
      if (Objects.equals(paramString, paramLegalMessageSet.messageByCountry[i].country)) {
        return paramLegalMessageSet.messageByCountry[i].message;
      }
      i++;
    }
    return paramLegalMessageSet.defaultMessage;
  }
  
  public static int getEmbeddedImageId(String paramString)
  {
    return Integer.parseInt(paramString.substring(9));
  }
  
  public static ImageLoader getImageLoader(Context paramContext)
  {
    return getImageLoader(paramContext, ((Integer)G.images.inMemoryCacheSizeDp.get()).intValue());
  }
  
  private static ImageLoader getImageLoader(Context paramContext, int paramInt)
  {
    try
    {
      if (sImageLoader == null) {
        sImageLoader = new ImageLoader(WalletRequestQueue.getImageRequestQueue(paramContext), new BitmapLruCache(paramContext, paramInt));
      }
      ImageLoader localImageLoader = sImageLoader;
      return localImageLoader;
    }
    finally {}
  }
  
  @TargetApi(17)
  private static DeviceFingerprinting.Parsed.State getState(Context paramContext, PackageInfo paramPackageInfo1, PackageInfo paramPackageInfo2, TelephonyManager paramTelephonyManager)
  {
    DeviceFingerprinting.Parsed.State localState = new DeviceFingerprinting.Parsed.State();
    if (paramPackageInfo1 != null)
    {
      DeviceFingerprinting.Parsed.State.PackageInfo localPackageInfo = new DeviceFingerprinting.Parsed.State.PackageInfo();
      if (!TextUtils.isEmpty(paramPackageInfo1.packageName)) {
        localPackageInfo.name = paramPackageInfo1.packageName;
      }
      localPackageInfo.versionCode = Integer.toString(paramPackageInfo1.versionCode);
      localPackageInfo.firstInstallTime = paramPackageInfo1.firstInstallTime;
      localPackageInfo.lastUpdateTime = paramPackageInfo1.lastUpdateTime;
      ApplicationInfo localApplicationInfo = paramPackageInfo1.applicationInfo;
      if ((localApplicationInfo != null) && (!TextUtils.isEmpty(localApplicationInfo.sourceDir))) {
        localPackageInfo.installLocation = localApplicationInfo.sourceDir;
      }
      localState.installedPackages = ((DeviceFingerprinting.Parsed.State.PackageInfo[])ArrayUtils.appendToArray(localState.installedPackages, localPackageInfo));
    }
    if (PermissionDelegate.selfHasAnyLocationPermission(paramContext))
    {
      Location localLocation = ((LocationManager)paramContext.getSystemService("location")).getLastKnownLocation("network");
      if (localLocation != null)
      {
        DeviceFingerprinting.Parsed.State.Location localLocation1 = new DeviceFingerprinting.Parsed.State.Location();
        localLocation1.latitude = localLocation.getLatitude();
        localLocation1.longitude = localLocation.getLongitude();
        localLocation1.timeInMs = localLocation.getTime();
        localLocation1.altitude = localLocation.getAltitude();
        localLocation1.accuracy = localLocation.getAccuracy();
        localState.lastGpsLocation = localLocation1;
      }
    }
    Intent localIntent = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    if (localIntent != null)
    {
      int k = localIntent.getIntExtra("level", -1);
      int m = localIntent.getIntExtra("scale", -1);
      if (m > 0) {
        localState.percentBattery = (k * 100 / m);
      }
    }
    localState.gmtOffsetMillis = TimeZone.getDefault().getRawOffset();
    ContentResolver localContentResolver = paramContext.getContentResolver();
    boolean bool4;
    if (Build.VERSION.SDK_INT < 17) {
      if (1 == Settings.Secure.getInt(localContentResolver, "adb_enabled", 0))
      {
        bool4 = true;
        localState.devModeOn = bool4;
        if ((Build.VERSION.SDK_INT >= 17) && (Build.VERSION.SDK_INT < 21)) {
          break label506;
        }
        if (1 != Settings.Secure.getInt(localContentResolver, "install_non_market_apps", 0)) {
          break label500;
        }
      }
    }
    label500:
    for (boolean bool2 = true;; bool2 = false)
    {
      localState.nonPlayInstallAllowed = bool2;
      Locale localLocale = paramContext.getResources().getConfiguration().locale;
      localState.language = localLocale.getISO3Language();
      localState.locale = localLocale.toString();
      ArrayList localArrayList = NetUtils.getNonLoopbackInetAddresses();
      localState.ipAddr = new String[localArrayList.size()];
      int i = 0;
      int j = localArrayList.size();
      while (i < j)
      {
        localState.ipAddr[i] = ((InetAddress)localArrayList.get(i)).getHostAddress();
        i++;
      }
      bool4 = false;
      break;
      if (1 == Settings.Global.getInt(localContentResolver, "adb_enabled", 0)) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        localState.devModeOn = bool1;
        break;
      }
    }
    label506:
    if (1 == Settings.Global.getInt(localContentResolver, "install_non_market_apps", 0)) {}
    for (boolean bool3 = true;; bool3 = false)
    {
      localState.nonPlayInstallAllowed = bool3;
      break;
    }
    if (paramTelephonyManager != null)
    {
      String str1 = paramTelephonyManager.getNetworkOperator();
      if (!TextUtils.isEmpty(str1)) {
        localState.cellOperator = str1;
      }
      String str2 = paramTelephonyManager.getSimOperator();
      if (!TextUtils.isEmpty(str2)) {
        localState.simOperator = str2;
      }
    }
    return localState;
  }
  
  public static Map<String, String> headerArrayToMap(String[] paramArrayOfString)
  {
    Object localObject;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      int i = paramArrayOfString.length;
      if (i % 2 != 0) {
        throw new IllegalArgumentException("Header arrays must have matching names and values");
      }
      localObject = new ArrayMap(i / 2);
      for (int j = 0; j < i; j += 2)
      {
        if (TextUtils.isEmpty(paramArrayOfString[j])) {
          throw new IllegalArgumentException("Header names must be non empty.");
        }
        ((ArrayMap)localObject).put(paramArrayOfString[j], paramArrayOfString[(j + 1)]);
      }
    }
  }
  
  public static boolean imageWithCaptionEquals(ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption1, ImageWithCaptionOuterClass.ImageWithCaption paramImageWithCaption2)
  {
    if (paramImageWithCaption1 == paramImageWithCaption2) {
      return true;
    }
    if ((paramImageWithCaption1 == null) || (paramImageWithCaption2 == null)) {
      return false;
    }
    return Objects.equals(paramImageWithCaption1.imageUri, paramImageWithCaption2.imageUri);
  }
  
  public static boolean isEmbeddedImageUri(String paramString)
  {
    return paramString.startsWith("embedded:");
  }
  
  public static boolean isMonthComplete(String paramString)
  {
    if (paramString == null) {}
    do
    {
      int i;
      do
      {
        return false;
        if (paramString.length() != 1) {
          break;
        }
        i = paramString.charAt(0);
      } while ((i == 48) || (i == 49));
      return true;
    } while (paramString.length() != 2);
    return true;
  }
  
  public static boolean panCategoryEquals(CardFormOuterClass.PanCategory paramPanCategory1, CardFormOuterClass.PanCategory paramPanCategory2)
  {
    if (paramPanCategory1 == paramPanCategory2) {
      return true;
    }
    if ((paramPanCategory1 == null) || (paramPanCategory2 == null)) {
      return false;
    }
    return Arrays.equals(paramPanCategory1.panCategoryToken, paramPanCategory2.panCategoryToken);
  }
  
  public static boolean passesChecksum(String paramString, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      Log.d("PaymentUtils", "Unexpected checksum type=" + paramInt);
    }
    int k;
    do
    {
      return true;
      int i = -1 + paramString.length();
      int j = 0;
      k = 0;
      if (i >= 0)
      {
        int m = Integer.parseInt(paramString.substring(i, i + 1));
        if (j != 0)
        {
          m *= 2;
          if (m > 9) {
            m -= 9;
          }
        }
        k += m;
        if (j == 0) {}
        for (int n = 1;; n = 0)
        {
          i--;
          j = n;
          break;
        }
      }
    } while (k % 10 == 0);
    return false;
  }
  
  public static String removeNonNumericDigits(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return NON_NUMERIC_PATTERN.matcher(paramString).replaceAll("");
  }
  
  /* Error */
  private static String sha1HashAsBase64(String paramString)
  {
    // Byte code:
    //   0: ldc_w 785
    //   3: invokestatic 791	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   6: astore_2
    //   7: aload_0
    //   8: ldc_w 793
    //   11: invokevirtual 797	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   14: astore 4
    //   16: aload_2
    //   17: aload 4
    //   19: invokevirtual 801	java/security/MessageDigest:update	([B)V
    //   22: aload_2
    //   23: invokevirtual 805	java/security/MessageDigest:digest	()[B
    //   26: bipush 11
    //   28: invokestatic 811	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   31: areturn
    //   32: astore_1
    //   33: new 813	java/lang/RuntimeException
    //   36: dup
    //   37: aload_1
    //   38: invokespecial 816	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   41: athrow
    //   42: astore_3
    //   43: new 813	java/lang/RuntimeException
    //   46: dup
    //   47: aload_3
    //   48: invokespecial 816	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   51: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	paramString	String
    //   32	6	1	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
    //   6	17	2	localMessageDigest	java.security.MessageDigest
    //   42	6	3	localUnsupportedEncodingException	java.io.UnsupportedEncodingException
    //   14	4	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   0	7	32	java/security/NoSuchAlgorithmException
    //   7	16	42	java/io/UnsupportedEncodingException
  }
  
  public static boolean shouldAutoCompleteBeEnabled(Context paramContext)
  {
    AccessibilityManager localAccessibilityManager = (AccessibilityManager)paramContext.getSystemService("accessibility");
    if (Build.VERSION.SDK_INT >= 14) {}
    for (boolean bool = localAccessibilityManager.isTouchExplorationEnabled(); (!bool) || (Build.VERSION.SDK_INT >= ((Integer)G.minApiLevelToShowAutocompleteForAccessibility.get()).intValue()); bool = localAccessibilityManager.isEnabled()) {
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.PaymentUtils
 * JD-Core Version:    0.7.0.1
 */