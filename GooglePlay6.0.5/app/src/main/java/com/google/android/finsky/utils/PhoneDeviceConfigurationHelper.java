package com.google.android.finsky.utils;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto;
import java.util.ArrayList;
import java.util.List;

public final class PhoneDeviceConfigurationHelper
  extends DeviceConfigurationHelper
{
  private DeviceConfiguration.DeviceConfigurationProto mDeviceConfiguration;
  
  private static void customizeDeviceConfiguration(Context paramContext, DeviceConfiguration.DeviceConfigurationProto paramDeviceConfigurationProto)
  {
    FeatureInfo[] arrayOfFeatureInfo = paramContext.getPackageManager().getSystemAvailableFeatures();
    if (arrayOfFeatureInfo != null)
    {
      ArrayList localArrayList = new ArrayList();
      int i = arrayOfFeatureInfo.length;
      for (int j = 0; j < i; j++)
      {
        FeatureInfo localFeatureInfo = arrayOfFeatureInfo[j];
        if (localFeatureInfo.name != null) {
          localArrayList.add(localFeatureInfo.name);
        }
      }
      paramDeviceConfigurationProto.systemAvailableFeature = ((String[])localArrayList.toArray(new String[localArrayList.size()]));
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramDeviceConfigurationProto.nativePlatform = Build.SUPPORTED_ABIS;
      return;
    }
    if (Build.CPU_ABI2.equals("unknown"))
    {
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = Build.CPU_ABI;
      paramDeviceConfigurationProto.nativePlatform = arrayOfString2;
      return;
    }
    String[] arrayOfString1 = new String[2];
    arrayOfString1[0] = Build.CPU_ABI;
    arrayOfString1[1] = Build.CPU_ABI2;
    paramDeviceConfigurationProto.nativePlatform = arrayOfString1;
  }
  
  /* Error */
  public final DeviceConfiguration.DeviceConfigurationProto getDeviceConfiguration()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   6: ifnonnull +470 -> 476
    //   9: aload_0
    //   10: new 53	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto
    //   13: dup
    //   14: invokespecial 86	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:<init>	()V
    //   17: putfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   20: invokestatic 92	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   23: astore_3
    //   24: aload_3
    //   25: ldc 94
    //   27: invokevirtual 98	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   30: checkcast 100	android/app/ActivityManager
    //   33: astore 4
    //   35: aload 4
    //   37: invokevirtual 104	android/app/ActivityManager:getDeviceConfigurationInfo	()Landroid/content/pm/ConfigurationInfo;
    //   40: astore 5
    //   42: aload_3
    //   43: invokestatic 110	com/google/android/finsky/utils/UiUtils:getPortraitScreenDimensions	(Landroid/content/Context;)Landroid/util/Pair;
    //   46: astore 6
    //   48: aload_3
    //   49: ldc 112
    //   51: invokevirtual 98	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   54: checkcast 114	android/view/WindowManager
    //   57: astore 7
    //   59: new 116	android/util/DisplayMetrics
    //   62: dup
    //   63: invokespecial 117	android/util/DisplayMetrics:<init>	()V
    //   66: astore 8
    //   68: aload 7
    //   70: invokeinterface 121 1 0
    //   75: aload 8
    //   77: invokevirtual 127	android/view/Display:getMetrics	(Landroid/util/DisplayMetrics;)V
    //   80: aload_0
    //   81: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   84: aload 5
    //   86: getfield 132	android/content/pm/ConfigurationInfo:reqTouchScreen	I
    //   89: invokestatic 136	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:getTouchScreenId	(I)I
    //   92: putfield 139	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:touchScreen	I
    //   95: aload_0
    //   96: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   99: iconst_1
    //   100: putfield 143	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasTouchScreen	Z
    //   103: aload_0
    //   104: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   107: aload 5
    //   109: getfield 146	android/content/pm/ConfigurationInfo:reqKeyboardType	I
    //   112: invokestatic 149	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:getKeyboardConfigId	(I)I
    //   115: putfield 152	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:keyboard	I
    //   118: aload_0
    //   119: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   122: iconst_1
    //   123: putfield 155	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasKeyboard	Z
    //   126: aload_0
    //   127: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   130: aload 5
    //   132: getfield 158	android/content/pm/ConfigurationInfo:reqNavigation	I
    //   135: invokestatic 161	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:getNavigationId	(I)I
    //   138: putfield 164	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:navigation	I
    //   141: aload_0
    //   142: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   145: iconst_1
    //   146: putfield 167	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasNavigation	Z
    //   149: aload_0
    //   150: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   153: aload 5
    //   155: getfield 170	android/content/pm/ConfigurationInfo:reqGlEsVersion	I
    //   158: putfield 173	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:glEsVersion	I
    //   161: aload_0
    //   162: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   165: iconst_1
    //   166: putfield 176	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasGlEsVersion	Z
    //   169: aload_0
    //   170: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   173: aload 6
    //   175: getfield 182	android/util/Pair:first	Ljava/lang/Object;
    //   178: checkcast 184	java/lang/Integer
    //   181: invokevirtual 187	java/lang/Integer:intValue	()I
    //   184: putfield 190	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:screenWidth	I
    //   187: aload_0
    //   188: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   191: iconst_1
    //   192: putfield 193	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasScreenWidth	Z
    //   195: aload_0
    //   196: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   199: aload 6
    //   201: getfield 196	android/util/Pair:second	Ljava/lang/Object;
    //   204: checkcast 184	java/lang/Integer
    //   207: invokevirtual 187	java/lang/Integer:intValue	()I
    //   210: putfield 199	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:screenHeight	I
    //   213: aload_0
    //   214: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   217: iconst_1
    //   218: putfield 202	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasScreenHeight	Z
    //   221: aload_0
    //   222: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   225: aload 8
    //   227: getfield 205	android/util/DisplayMetrics:densityDpi	I
    //   230: putfield 208	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:screenDensity	I
    //   233: aload_0
    //   234: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   237: iconst_1
    //   238: putfield 211	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasScreenDensity	Z
    //   241: aload_0
    //   242: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   245: astore 9
    //   247: iconst_1
    //   248: aload 5
    //   250: getfield 214	android/content/pm/ConfigurationInfo:reqInputFeatures	I
    //   253: iand
    //   254: ifle +231 -> 485
    //   257: iconst_1
    //   258: istore 10
    //   260: aload 9
    //   262: iload 10
    //   264: putfield 217	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasHardKeyboard	Z
    //   267: aload_0
    //   268: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   271: iconst_1
    //   272: putfield 220	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasHasHardKeyboard	Z
    //   275: aload_0
    //   276: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   279: astore 11
    //   281: iconst_2
    //   282: aload 5
    //   284: getfield 214	android/content/pm/ConfigurationInfo:reqInputFeatures	I
    //   287: iand
    //   288: ifle +203 -> 491
    //   291: iconst_1
    //   292: istore 12
    //   294: aload 11
    //   296: iload 12
    //   298: putfield 223	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasFiveWayNavigation	Z
    //   301: aload_0
    //   302: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   305: iconst_1
    //   306: putfield 226	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasHasFiveWayNavigation	Z
    //   309: aload_3
    //   310: invokevirtual 230	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   313: invokevirtual 236	android/content/res/Resources:getConfiguration	()Landroid/content/res/Configuration;
    //   316: astore 13
    //   318: aload_0
    //   319: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   322: aload 13
    //   324: getfield 241	android/content/res/Configuration:screenLayout	I
    //   327: invokestatic 244	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:getScreenLayoutSizeId	(I)I
    //   330: putfield 245	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:screenLayout	I
    //   333: aload_0
    //   334: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   337: iconst_1
    //   338: putfield 248	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasScreenLayout	Z
    //   341: getstatic 62	android/os/Build$VERSION:SDK_INT	I
    //   344: bipush 13
    //   346: if_icmplt +25 -> 371
    //   349: aload_0
    //   350: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   353: astore 15
    //   355: aload 15
    //   357: aload 13
    //   359: getfield 251	android/content/res/Configuration:smallestScreenWidthDp	I
    //   362: putfield 252	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:smallestScreenWidthDp	I
    //   365: aload 15
    //   367: iconst_1
    //   368: putfield 255	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasSmallestScreenWidthDp	Z
    //   371: aload_0
    //   372: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   375: aload_3
    //   376: invokevirtual 18	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   379: invokevirtual 259	android/content/pm/PackageManager:getSystemSharedLibraryNames	()[Ljava/lang/String;
    //   382: putfield 262	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:systemSharedLibrary	[Ljava/lang/String;
    //   385: aload_0
    //   386: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   389: invokestatic 92	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   392: invokevirtual 266	com/google/android/finsky/FinskyApp:getAssets	()Landroid/content/res/AssetManager;
    //   395: invokevirtual 271	android/content/res/AssetManager:getLocales	()[Ljava/lang/String;
    //   398: putfield 274	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:systemSupportedLocale	[Ljava/lang/String;
    //   401: new 276	com/google/android/finsky/utils/GlExtensionReader
    //   404: dup
    //   405: invokespecial 277	com/google/android/finsky/utils/GlExtensionReader:<init>	()V
    //   408: invokevirtual 281	com/google/android/finsky/utils/GlExtensionReader:getGlExtensions	()Ljava/util/List;
    //   411: astore 14
    //   413: aload_0
    //   414: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   417: aload 14
    //   419: aload 14
    //   421: invokeinterface 43 1 0
    //   426: anewarray 45	java/lang/String
    //   429: invokeinterface 49 2 0
    //   434: checkcast 51	[Ljava/lang/String;
    //   437: putfield 284	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:glExtension	[Ljava/lang/String;
    //   440: getstatic 62	android/os/Build$VERSION:SDK_INT	I
    //   443: bipush 19
    //   445: if_icmplt +23 -> 468
    //   448: aload_0
    //   449: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   452: aload 4
    //   454: invokevirtual 288	android/app/ActivityManager:isLowRamDevice	()Z
    //   457: putfield 291	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:lowRamDevice	Z
    //   460: aload_0
    //   461: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   464: iconst_1
    //   465: putfield 294	com/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto:hasLowRamDevice	Z
    //   468: aload_3
    //   469: aload_0
    //   470: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   473: invokestatic 296	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:customizeDeviceConfiguration	(Landroid/content/Context;Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;)V
    //   476: aload_0
    //   477: getfield 85	com/google/android/finsky/utils/PhoneDeviceConfigurationHelper:mDeviceConfiguration	Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   480: astore_2
    //   481: aload_0
    //   482: monitorexit
    //   483: aload_2
    //   484: areturn
    //   485: iconst_0
    //   486: istore 10
    //   488: goto -228 -> 260
    //   491: iconst_0
    //   492: istore 12
    //   494: goto -200 -> 294
    //   497: astore_1
    //   498: aload_0
    //   499: monitorexit
    //   500: aload_1
    //   501: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	502	0	this	PhoneDeviceConfigurationHelper
    //   497	4	1	localObject	java.lang.Object
    //   480	4	2	localDeviceConfigurationProto1	DeviceConfiguration.DeviceConfigurationProto
    //   23	446	3	localFinskyApp	com.google.android.finsky.FinskyApp
    //   33	420	4	localActivityManager	android.app.ActivityManager
    //   40	243	5	localConfigurationInfo	android.content.pm.ConfigurationInfo
    //   46	154	6	localPair	android.util.Pair
    //   57	12	7	localWindowManager	android.view.WindowManager
    //   66	160	8	localDisplayMetrics	android.util.DisplayMetrics
    //   245	16	9	localDeviceConfigurationProto2	DeviceConfiguration.DeviceConfigurationProto
    //   258	229	10	bool1	boolean
    //   279	16	11	localDeviceConfigurationProto3	DeviceConfiguration.DeviceConfigurationProto
    //   292	201	12	bool2	boolean
    //   316	42	13	localConfiguration	android.content.res.Configuration
    //   411	9	14	localList	List
    //   353	13	15	localDeviceConfigurationProto4	DeviceConfiguration.DeviceConfigurationProto
    // Exception table:
    //   from	to	target	type
    //   2	257	497	finally
    //   260	291	497	finally
    //   294	371	497	finally
    //   371	468	497	finally
    //   468	476	497	finally
    //   476	481	497	finally
  }
  
  public final String getToken()
  {
    return (String)FinskyPreferences.deviceConfigToken.get();
  }
  
  public final void invalidateToken()
  {
    FinskyPreferences.deviceConfigToken.remove();
  }
  
  protected final void setToken(String paramString)
  {
    FinskyPreferences.deviceConfigToken.put(paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PhoneDeviceConfigurationHelper
 * JD-Core Version:    0.7.0.1
 */