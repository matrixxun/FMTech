package com.google.android.vending.verifier;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings.Secure;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.safetynet.SafetyNetFirstPartyClient;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.verifier.api.PackageVerificationApi;
import com.google.android.vending.verifier.api.PackageVerificationApi.FileInfo;
import com.google.android.vending.verifier.api.PackageVerificationRequest;
import com.google.android.vending.verifier.api.PackageVerificationResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class PackageVerificationService
  extends Service
{
  private static PackageVerificationService sInstance;
  private int mLastStartId;
  private ArrayList<String> mRemovalRequests = Lists.newArrayList(0);
  private ArrayList<VerificationState> mVerifications = Lists.newArrayList(1);
  private VerifyInstalledPackagesTask mVerifyInstalledPackagesTask = null;
  
  private static boolean acceptedAntiMalwareConsent()
  {
    return (!((Boolean)G.antiMalwareConsentRequired.get()).booleanValue()) || (((Boolean)FinskyPreferences.acceptedAntiMalwareConsent.get()).booleanValue());
  }
  
  private static void cancelDialog(VerificationState paramVerificationState)
  {
    if (paramVerificationState.mDialog != null)
    {
      paramVerificationState.mDialog.finish();
      paramVerificationState.mDialog = null;
    }
  }
  
  public static Intent createRemovalRequestIntent$6548352b(Context paramContext, String paramString, byte[] paramArrayOfByte)
  {
    Intent localIntent1 = new Intent("com.google.android.vending.verifier.intent.action.REMOVAL_REQUEST_RESPONSE");
    localIntent1.putExtra("android.content.pm.extra.VERIFICATION_PACKAGE_NAME", paramString);
    localIntent1.putExtra("com.google.android.vending.verifier.intent.extra.UNINSTALL", true);
    localIntent1.putExtra("com.google.android.vending.verifier.intent.extra.DONT_WARN", false);
    localIntent1.putExtra("pressed_back_button", false);
    localIntent1.putExtra("pressed_uninstall_action", true);
    localIntent1.putExtra("com.google.android.vending.verifier.intent.extra.RESPONSE_TOKEN", paramArrayOfByte);
    Intent localIntent2 = new Intent(paramContext, PackageVerificationService.class);
    localIntent2.putExtra("broadcast_intent", localIntent1);
    localIntent2.setData(Uri.parse("verifyapps://removalrequest/" + paramString));
    return localIntent2;
  }
  
  private VerificationState findVerification(int paramInt)
  {
    Iterator localIterator = this.mVerifications.iterator();
    while (localIterator.hasNext())
    {
      VerificationState localVerificationState = (VerificationState)localIterator.next();
      if (localVerificationState.id == paramInt) {
        return localVerificationState;
      }
    }
    return null;
  }
  
  public static ApplicationInfo getApplicationInfo(String paramString)
  {
    try
    {
      ApplicationInfo localApplicationInfo = FinskyApp.get().getPackageManager().getApplicationInfo(paramString, 0);
      return localApplicationInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public static String getApplicationName(ApplicationInfo paramApplicationInfo)
  {
    CharSequence localCharSequence = FinskyApp.get().getPackageManager().getApplicationLabel(paramApplicationInfo);
    if (localCharSequence == null) {
      return paramApplicationInfo.packageName;
    }
    return localCharSequence.toString();
  }
  
  private static byte[][] getCertificateFingerprints(Signature[] paramArrayOfSignature)
  {
    byte[][] arrayOfByte;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA1");
      int i;
      if ((paramArrayOfSignature != null) && (paramArrayOfSignature.length > 0))
      {
        arrayOfByte = new byte[paramArrayOfSignature.length][];
        i = 0;
      }
      while (i < paramArrayOfSignature.length)
      {
        arrayOfByte[i] = localMessageDigest.digest(paramArrayOfSignature[i].toByteArray());
        i++;
        continue;
        arrayOfByte = null;
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException(localNoSuchAlgorithmException);
    }
    return arrayOfByte;
  }
  
  /* Error */
  private boolean getPackageInfo(VerificationState paramVerificationState)
  {
    // Byte code:
    //   0: aload_1
    //   1: getfield 639	com/google/android/vending/verifier/PackageVerificationService$VerificationState:dataUri	Landroid/net/Uri;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnonnull +30 -> 36
    //   9: iconst_1
    //   10: anewarray 316	java/lang/Object
    //   13: astore 20
    //   15: aload 20
    //   17: iconst_0
    //   18: aload_1
    //   19: getfield 599	com/google/android/vending/verifier/PackageVerificationService$VerificationState:id	I
    //   22: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   25: aastore
    //   26: ldc_w 641
    //   29: aload 20
    //   31: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   34: iconst_0
    //   35: ireturn
    //   36: ldc_w 643
    //   39: aload_2
    //   40: invokevirtual 646	android/net/Uri:getScheme	()Ljava/lang/String;
    //   43: invokevirtual 650	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   46: ifne +35 -> 81
    //   49: iconst_2
    //   50: anewarray 316	java/lang/Object
    //   53: astore 19
    //   55: aload 19
    //   57: iconst_0
    //   58: aload_2
    //   59: aastore
    //   60: aload 19
    //   62: iconst_1
    //   63: aload_1
    //   64: getfield 599	com/google/android/vending/verifier/PackageVerificationService$VerificationState:id	I
    //   67: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   70: aastore
    //   71: ldc_w 652
    //   74: aload 19
    //   76: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   79: iconst_0
    //   80: ireturn
    //   81: aload_2
    //   82: invokevirtual 655	android/net/Uri:getPath	()Ljava/lang/String;
    //   85: astore_3
    //   86: new 657	java/io/File
    //   89: dup
    //   90: aload_3
    //   91: invokespecial 658	java/io/File:<init>	(Ljava/lang/String;)V
    //   94: astore 4
    //   96: aload 4
    //   98: invokevirtual 661	java/io/File:exists	()Z
    //   101: ifne +35 -> 136
    //   104: iconst_2
    //   105: anewarray 316	java/lang/Object
    //   108: astore 18
    //   110: aload 18
    //   112: iconst_0
    //   113: aload_2
    //   114: aastore
    //   115: aload 18
    //   117: iconst_1
    //   118: aload_1
    //   119: getfield 599	com/google/android/vending/verifier/PackageVerificationService$VerificationState:id	I
    //   122: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   125: aastore
    //   126: ldc_w 663
    //   129: aload 18
    //   131: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   134: iconst_0
    //   135: ireturn
    //   136: aload 4
    //   138: invokevirtual 666	java/io/File:canRead	()Z
    //   141: ifne +35 -> 176
    //   144: iconst_2
    //   145: anewarray 316	java/lang/Object
    //   148: astore 17
    //   150: aload 17
    //   152: iconst_0
    //   153: aload_2
    //   154: aastore
    //   155: aload 17
    //   157: iconst_1
    //   158: aload_1
    //   159: getfield 599	com/google/android/vending/verifier/PackageVerificationService$VerificationState:id	I
    //   162: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   165: aastore
    //   166: ldc_w 668
    //   169: aload 17
    //   171: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   174: iconst_0
    //   175: ireturn
    //   176: aload_0
    //   177: invokevirtual 514	com/google/android/vending/verifier/PackageVerificationService:getPackageManager	()Landroid/content/pm/PackageManager;
    //   180: aload_3
    //   181: bipush 64
    //   183: invokevirtual 672	android/content/pm/PackageManager:getPackageArchiveInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   186: astore 7
    //   188: aload 7
    //   190: ifnonnull +75 -> 265
    //   193: iconst_2
    //   194: anewarray 316	java/lang/Object
    //   197: astore 16
    //   199: aload 16
    //   201: iconst_0
    //   202: aload_2
    //   203: aastore
    //   204: aload 16
    //   206: iconst_1
    //   207: aload_1
    //   208: getfield 599	com/google/android/vending/verifier/PackageVerificationService$VerificationState:id	I
    //   211: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   214: aastore
    //   215: ldc_w 674
    //   218: aload 16
    //   220: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   223: iconst_0
    //   224: ireturn
    //   225: astore 5
    //   227: iconst_3
    //   228: anewarray 316	java/lang/Object
    //   231: astore 6
    //   233: aload 6
    //   235: iconst_0
    //   236: aload_2
    //   237: aastore
    //   238: aload 6
    //   240: iconst_1
    //   241: aload_1
    //   242: getfield 599	com/google/android/vending/verifier/PackageVerificationService$VerificationState:id	I
    //   245: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   248: aastore
    //   249: aload 6
    //   251: iconst_2
    //   252: aload 5
    //   254: aastore
    //   255: ldc_w 676
    //   258: aload 6
    //   260: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   263: iconst_0
    //   264: ireturn
    //   265: aload_1
    //   266: aload 7
    //   268: getfield 679	android/content/pm/PackageInfo:packageName	Ljava/lang/String;
    //   271: putfield 341	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mPackageName	Ljava/lang/String;
    //   274: aload_1
    //   275: aload 7
    //   277: getfield 682	android/content/pm/PackageInfo:versionCode	I
    //   280: invokestatic 213	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   283: putfield 359	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mVersion	Ljava/lang/Integer;
    //   286: aload_1
    //   287: aload 4
    //   289: invokevirtual 684	java/io/File:length	()J
    //   292: putfield 367	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mLength	J
    //   295: aload_1
    //   296: aload 4
    //   298: invokestatic 536	com/google/android/vending/verifier/PackageVerificationService:getSha256Hash	(Ljava/io/File;)[B
    //   301: putfield 350	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mSha256	[B
    //   304: aload 7
    //   306: getfield 688	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   309: ifnull +129 -> 438
    //   312: aload_1
    //   313: aload 7
    //   315: getfield 688	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   318: invokestatic 69	com/google/android/vending/verifier/PackageVerificationService:getRawSignatures	([Landroid/content/pm/Signature;)[[B
    //   321: putfield 691	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mSignatures	[[B
    //   324: aload_1
    //   325: aload 4
    //   327: invokestatic 697	com/google/android/vending/verifier/ZipAnalyzer:analyzeZipFile	(Ljava/io/File;)[Lcom/google/android/vending/verifier/api/PackageVerificationApi$FileInfo;
    //   330: putfield 701	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mFileInfos	[Lcom/google/android/vending/verifier/api/PackageVerificationApi$FileInfo;
    //   333: aload 7
    //   335: getfield 705	android/content/pm/PackageInfo:applicationInfo	Landroid/content/pm/ApplicationInfo;
    //   338: aload_3
    //   339: putfield 708	android/content/pm/ApplicationInfo:publicSourceDir	Ljava/lang/String;
    //   342: aload 7
    //   344: getfield 705	android/content/pm/PackageInfo:applicationInfo	Landroid/content/pm/ApplicationInfo;
    //   347: aload_0
    //   348: invokevirtual 514	com/google/android/vending/verifier/PackageVerificationService:getPackageManager	()Landroid/content/pm/PackageManager;
    //   351: invokevirtual 712	android/content/pm/ApplicationInfo:loadLabel	(Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
    //   354: astore 12
    //   356: aload 12
    //   358: ifnull +14 -> 372
    //   361: aload_1
    //   362: aload 12
    //   364: invokeinterface 615 1 0
    //   369: putfield 715	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mLabel	Ljava/lang/String;
    //   372: iconst_2
    //   373: aload 7
    //   375: getfield 705	android/content/pm/PackageInfo:applicationInfo	Landroid/content/pm/ApplicationInfo;
    //   378: getfield 282	android/content/pm/ApplicationInfo:flags	I
    //   381: iand
    //   382: istore 13
    //   384: iconst_0
    //   385: istore 14
    //   387: iload 13
    //   389: ifeq +6 -> 395
    //   392: iconst_1
    //   393: istore 14
    //   395: aload_1
    //   396: iload 14
    //   398: putfield 718	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mDebuggable	Z
    //   401: iconst_1
    //   402: ireturn
    //   403: astore 9
    //   405: ldc_w 720
    //   408: iconst_2
    //   409: anewarray 316	java/lang/Object
    //   412: dup
    //   413: iconst_0
    //   414: aload_2
    //   415: aastore
    //   416: dup
    //   417: iconst_1
    //   418: aload 9
    //   420: aastore
    //   421: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   424: iconst_0
    //   425: ireturn
    //   426: astore 8
    //   428: new 277	java/lang/RuntimeException
    //   431: dup
    //   432: aload 8
    //   434: invokespecial 634	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   437: athrow
    //   438: aload_1
    //   439: aload_3
    //   440: invokestatic 726	com/google/android/vending/verifier/CertificateUtils:collectCertificates	(Ljava/lang/String;)[[B
    //   443: putfield 691	com/google/android/vending/verifier/PackageVerificationService$VerificationState:mSignatures	[[B
    //   446: goto -122 -> 324
    //   449: astore 15
    //   451: ldc_w 728
    //   454: iconst_2
    //   455: anewarray 316	java/lang/Object
    //   458: dup
    //   459: iconst_0
    //   460: aload_2
    //   461: aastore
    //   462: dup
    //   463: iconst_1
    //   464: aload 15
    //   466: aastore
    //   467: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   470: goto -137 -> 333
    //   473: astore 11
    //   475: ldc_w 728
    //   478: iconst_2
    //   479: anewarray 316	java/lang/Object
    //   482: dup
    //   483: iconst_0
    //   484: aload_2
    //   485: aastore
    //   486: dup
    //   487: iconst_1
    //   488: aload 11
    //   490: aastore
    //   491: invokestatic 417	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   494: goto -161 -> 333
    //   497: astore 10
    //   499: new 277	java/lang/RuntimeException
    //   502: dup
    //   503: aload 10
    //   505: invokespecial 634	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   508: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	509	0	this	PackageVerificationService
    //   0	509	1	paramVerificationState	VerificationState
    //   4	481	2	localUri	Uri
    //   85	355	3	str	String
    //   94	232	4	localFile	File
    //   225	28	5	localException	java.lang.Exception
    //   231	28	6	arrayOfObject1	Object[]
    //   186	188	7	localPackageInfo	PackageInfo
    //   426	7	8	localNoSuchAlgorithmException1	NoSuchAlgorithmException
    //   403	16	9	localIOException1	IOException
    //   497	7	10	localNoSuchAlgorithmException2	NoSuchAlgorithmException
    //   473	16	11	localRuntimeException	RuntimeException
    //   354	9	12	localCharSequence	CharSequence
    //   382	6	13	i	int
    //   385	12	14	bool	boolean
    //   449	16	15	localIOException2	IOException
    //   197	22	16	arrayOfObject2	Object[]
    //   148	22	17	arrayOfObject3	Object[]
    //   108	22	18	arrayOfObject4	Object[]
    //   53	22	19	arrayOfObject5	Object[]
    //   13	17	20	arrayOfObject6	Object[]
    // Exception table:
    //   from	to	target	type
    //   176	188	225	java/lang/Exception
    //   295	304	403	java/io/IOException
    //   295	304	426	java/security/NoSuchAlgorithmException
    //   324	333	449	java/io/IOException
    //   324	333	473	java/lang/RuntimeException
    //   324	333	497	java/security/NoSuchAlgorithmException
  }
  
  private static byte[][] getRawSignatures(Signature[] paramArrayOfSignature)
  {
    byte[][] arrayOfByte;
    int i;
    if ((paramArrayOfSignature != null) && (paramArrayOfSignature.length > 0))
    {
      arrayOfByte = new byte[paramArrayOfSignature.length][];
      i = 0;
    }
    while (i < paramArrayOfSignature.length)
    {
      arrayOfByte[i] = paramArrayOfSignature[i].toByteArray();
      i++;
      continue;
      arrayOfByte = null;
    }
    return arrayOfByte;
  }
  
  private static String getSafetyNetId(Context paramContext)
  {
    try
    {
      int i = ((Integer)G.verifyAppsMinimumGmsCoreVersionCodeForSharedId.get()).intValue();
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo("com.google.android.gms", 0);
      if (localPackageInfo != null)
      {
        int j = localPackageInfo.versionCode;
        if (j >= i) {}
      }
      else
      {
        return null;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      FinskyLog.e("Could not get GMS Core package info for verifier shared ID", new Object[0]);
      return null;
    }
    try
    {
      String str = SafetyNetFirstPartyClient.getId(paramContext);
      return str;
    }
    catch (RuntimeException localRuntimeException)
    {
      FinskyLog.e("Unable to fetch SafetyNet ID: %s", new Object[] { localRuntimeException });
    }
    return null;
  }
  
  private static byte[] getSha256Hash(File paramFile)
    throws IOException, NoSuchAlgorithmException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    try
    {
      byte[] arrayOfByte = getSha256Hash(localFileInputStream);
      return arrayOfByte;
    }
    finally
    {
      localFileInputStream.close();
    }
  }
  
  static byte[] getSha256Hash(InputStream paramInputStream)
    throws IOException, NoSuchAlgorithmException
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance("SHA256");
    byte[] arrayOfByte = new byte[16384];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i < 0) {
        break;
      }
      localMessageDigest.update(arrayOfByte, 0, i);
    }
    return localMessageDigest.digest();
  }
  
  @SuppressLint({"WorldReadableFiles"})
  public static void migrateAntiMalwareConsent(Context paramContext)
  {
    if (!acceptedAntiMalwareConsent()) {}
    SharedPreferences localSharedPreferences;
    do
    {
      ContentResolver localContentResolver;
      int j;
      do
      {
        return;
        if (Build.VERSION.SDK_INT < 19) {
          break;
        }
        localContentResolver = paramContext.getContentResolver();
        int i = Settings.Secure.getInt(localContentResolver, "package_verifier_user_consent", 0);
        j = 0;
        if (i == 1) {
          j = 1;
        }
      } while (j != 0);
      Settings.Secure.putInt(localContentResolver, "package_verifier_user_consent", 1);
      new File(new File(paramContext.getApplicationInfo().dataDir, "shared_prefs"), "package_verifer_public_preferences.xml").delete();
      return;
      localSharedPreferences = paramContext.getSharedPreferences("package_verifer_public_preferences", 1);
    } while (localSharedPreferences.getBoolean("accepted-anti-malware-consent", false));
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putBoolean("accepted-anti-malware-consent", true);
    localEditor.commit();
  }
  
  public static boolean registerDialog(int paramInt, Activity paramActivity)
  {
    
    if (sInstance == null) {}
    VerificationState localVerificationState;
    do
    {
      return false;
      localVerificationState = sInstance.findVerification(paramInt);
    } while (localVerificationState == null);
    localVerificationState.mDialog = paramActivity;
    return true;
  }
  
  private void reportAndCleanup(Context paramContext, VerificationState paramVerificationState)
  {
    Utils.ensureOnMainThread();
    reportResult(paramContext, paramVerificationState, paramVerificationState.mResult);
    this.mVerifications.remove(paramVerificationState);
    stopServiceIfIdle();
  }
  
  public static void reportConsentDialog(int paramInt, boolean paramBoolean)
  {
    Utils.ensureOnMainThread();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Boolean.valueOf(paramBoolean);
    arrayOfObject[1] = Integer.valueOf(paramInt);
    FinskyLog.d("User consent %b for id=%d", arrayOfObject);
    if (sInstance == null) {}
    VerificationState localVerificationState;
    do
    {
      return;
      localVerificationState = sInstance.findVerification(paramInt);
    } while (localVerificationState == null);
    if (paramBoolean)
    {
      FinskyPreferences.acceptedAntiMalwareConsent.put(Boolean.valueOf(true));
      migrateAntiMalwareConsent(sInstance);
      sInstance.sendVerificationRequest(localVerificationState);
      return;
    }
    localVerificationState.mResult = 1;
    sInstance.reportAndCleanup(sInstance, localVerificationState);
  }
  
  private void reportResult(Context paramContext, VerificationState paramVerificationState, int paramInt)
  {
    if (paramVerificationState.fromVerificationActivity)
    {
      if (paramInt == 1)
      {
        Intent localIntent = paramVerificationState.originalIntent;
        localIntent.setPackage("com.android.packageinstaller");
        localIntent.setComponent(new ComponentName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity"));
        localIntent.addFlags(268435456);
        startActivity(localIntent);
      }
      return;
    }
    int i = paramVerificationState.id;
    paramContext.getPackageManager().verifyPendingInstall(i, paramInt);
  }
  
  public static void reportUserChoice(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 1;
    Utils.ensureOnMainThread();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt2);
    arrayOfObject[i] = Integer.valueOf(paramInt1);
    FinskyLog.d("User selected %d for id=%d", arrayOfObject);
    if (sInstance == null) {}
    VerificationState localVerificationState;
    do
    {
      return;
      localVerificationState = sInstance.findVerification(paramInt1);
    } while (localVerificationState == null);
    if (localVerificationState.mResult != -1)
    {
      if (paramInt2 == i) {
        i = 0;
      }
      PackageVerificationApi.reportUserDecision(sInstance, localVerificationState.mPackageName, i, false, paramBoolean, false, localVerificationState.mToken);
    }
    localVerificationState.mResult = paramInt2;
    sInstance.reportAndCleanup(sInstance, localVerificationState);
  }
  
  public static void sendRemovalResponse(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte[] paramArrayOfByte)
  {
    Intent localIntent = new Intent("com.google.android.vending.verifier.intent.action.REMOVAL_REQUEST_RESPONSE");
    localIntent.putExtra("android.content.pm.extra.VERIFICATION_PACKAGE_NAME", paramString);
    localIntent.putExtra("com.google.android.vending.verifier.intent.extra.UNINSTALL", paramBoolean1);
    localIntent.putExtra("com.google.android.vending.verifier.intent.extra.DONT_WARN", paramBoolean2);
    localIntent.putExtra("pressed_back_button", paramBoolean3);
    localIntent.putExtra("com.google.android.vending.verifier.intent.extra.RESPONSE_TOKEN", paramArrayOfByte);
    paramContext.sendBroadcast(localIntent);
  }
  
  private void sendVerificationRequest(final VerificationState paramVerificationState)
  {
    String str1 = getResources().getConfiguration().locale.toString();
    long l1 = ((Long)DfeApiConfig.androidId.get()).longValue();
    byte[] arrayOfByte = paramVerificationState.mSha256;
    long l2 = paramVerificationState.mLength;
    String str2 = paramVerificationState.mPackageName;
    Integer localInteger = paramVerificationState.mVersion;
    byte[][] arrayOfByte1 = paramVerificationState.mSignatures;
    PackageVerificationApi.FileInfo[] arrayOfFileInfo = paramVerificationState.mFileInfos;
    Uri localUri1 = paramVerificationState.originatingUri;
    Uri localUri2 = paramVerificationState.referrerUri;
    InetAddress localInetAddress1 = paramVerificationState.originatingIp;
    InetAddress localInetAddress2 = paramVerificationState.referrerIp;
    String[] arrayOfString1 = paramVerificationState.originatingPackageNames;
    byte[][] arrayOfByte2 = paramVerificationState.originatingSignatures;
    String[] arrayOfString2 = paramVerificationState.installerPackageNames;
    byte[][] arrayOfByte3 = paramVerificationState.installerSignatures;
    String str3 = paramVerificationState.mSafetyNetId;
    if ((0x1 & paramVerificationState.flags) != 0) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      boolean bool2 = paramVerificationState.mDebuggable;
      PackageVerificationRequest localPackageVerificationRequest = new PackageVerificationRequest("https://safebrowsing.google.com/safebrowsing/clientreport/download", new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramVerificationState.id);
          arrayOfObject[1] = paramAnonymousVolleyError;
          FinskyLog.d("Verification id=%d error response %s", arrayOfObject);
          PackageVerificationService.this.reportAndCleanup(PackageVerificationService.this, paramVerificationState);
        }
      }, PackageVerificationApi.buildRequestForApp(arrayOfByte, l2, str2, localInteger, arrayOfByte1, null, arrayOfFileInfo, localUri1, localUri2, localInetAddress1, localInetAddress2, arrayOfString1, arrayOfByte2, arrayOfString2, arrayOfByte3, str1, Long.valueOf(l1), str3, null, false, bool1, false, false, false, bool2, false));
      FinskyApp.get().mRequestQueue.add(localPackageVerificationRequest);
      return;
    }
  }
  
  public static void start(Context paramContext, Intent paramIntent)
  {
    Intent localIntent = new Intent(paramContext, PackageVerificationService.class);
    localIntent.putExtra("broadcast_intent", paramIntent);
    paramContext.startService(localIntent);
  }
  
  private void stopServiceIfIdle()
  {
    
    if ((this.mVerifications.isEmpty()) && (this.mRemovalRequests.isEmpty()) && (this.mVerifyInstalledPackagesTask == null)) {
      stopSelf(this.mLastStartId);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    sInstance = this;
  }
  
  public void onDestroy()
  {
    
    while (!this.mVerifications.isEmpty())
    {
      VerificationState localVerificationState = (VerificationState)this.mVerifications.remove(0);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(localVerificationState.id);
      FinskyLog.w("Destroying orphaned verification id=%d", arrayOfObject);
      reportResult(this, localVerificationState, 1);
      cancelDialog(localVerificationState);
    }
    if (this.mVerifyInstalledPackagesTask != null)
    {
      this.mVerifyInstalledPackagesTask.cancel(true);
      this.mVerifyInstalledPackagesTask = null;
    }
    sInstance = null;
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Intent localIntent = (Intent)paramIntent.getParcelableExtra("broadcast_intent");
    String str1 = localIntent.getAction();
    if (("android.intent.action.PACKAGE_NEEDS_VERIFICATION".equals(str1)) || ("android.intent.action.VIEW".equals(str1)) || ("android.intent.action.INSTALL_PACKAGE".equals(str1)))
    {
      VerificationState localVerificationState1 = new VerificationState(localIntent);
      this.mVerifications.add(localVerificationState1);
      Utils.executeMultiThreaded(new WorkerTask(localVerificationState1), new Void[0]);
    }
    for (;;)
    {
      this.mLastStartId = paramInt2;
      stopServiceIfIdle();
      return 3;
      if ("com.google.android.vending.verifier.intent.action.VERIFY_INSTALLED_PACKAGES".equals(str1))
      {
        if (this.mVerifyInstalledPackagesTask == null)
        {
          this.mVerifyInstalledPackagesTask = new VerifyInstalledPackagesTask();
          Utils.executeMultiThreaded(this.mVerifyInstalledPackagesTask, new Void[0]);
        }
        else
        {
          FinskyLog.w("Verify installed packages is already running.", new Object[0]);
        }
      }
      else if ("com.google.android.vending.verifier.intent.action.REMOVAL_REQUEST_RESPONSE".equals(str1))
      {
        Bundle localBundle = localIntent.getExtras();
        String str2 = localBundle.getString("android.content.pm.extra.VERIFICATION_PACKAGE_NAME");
        if (str2 != null)
        {
          this.mRemovalRequests.add(str2);
          new CleanupRemovalRequestTask(str2, localBundle.getBoolean("com.google.android.vending.verifier.intent.extra.UNINSTALL"), localBundle.getBoolean("com.google.android.vending.verifier.intent.extra.DONT_WARN"), localBundle.getBoolean("pressed_back_button"), localBundle.getBoolean("pressed_uninstall_action", false), localBundle.getByteArray("com.google.android.vending.verifier.intent.extra.RESPONSE_TOKEN")).execute(new Void[0]);
        }
      }
      else if ("android.intent.action.PACKAGE_VERIFIED".equals(str1))
      {
        int i = localIntent.getExtras().getInt("android.content.pm.extra.VERIFICATION_ID");
        VerificationState localVerificationState2 = findVerification(i);
        if ((localVerificationState2 != null) && (localVerificationState2.mResult != -1))
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(i);
          FinskyLog.d("Cancel active verification id=%d", arrayOfObject);
          cancelDialog(localVerificationState2);
          this.mVerifications.remove(localVerificationState2);
        }
      }
    }
  }
  
  private final class CleanupRemovalRequestTask
    extends AsyncTask<Void, Void, Void>
  {
    private final Context mContext = PackageVerificationService.this;
    private final boolean mDontWarn;
    private final boolean mFromUninstallAction;
    private final String mPackageName;
    private final boolean mPressedBack;
    private final byte[] mResponseToken;
    private final boolean mUninstall;
    
    public CleanupRemovalRequestTask(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, byte[] paramArrayOfByte)
    {
      this.mPackageName = paramString;
      this.mUninstall = paramBoolean1;
      this.mDontWarn = paramBoolean2;
      this.mPressedBack = paramBoolean3;
      this.mFromUninstallAction = paramBoolean4;
      this.mResponseToken = paramArrayOfByte;
    }
  }
  
  private static final class VerificationState
  {
    public final Uri dataUri;
    public final int flags;
    public final boolean fromVerificationActivity;
    public final int id;
    public String[] installerPackageNames;
    public byte[][] installerSignatures;
    public final int installerUid;
    public int mAlternateLayoutVersion;
    public boolean mDebuggable;
    public String mDescription;
    public Activity mDialog;
    public PackageVerificationApi.FileInfo[] mFileInfos;
    public String mLabel;
    public long mLength;
    public String mPackageName;
    public int mResult = 1;
    public String mSafetyNetId;
    public byte[] mSha256;
    public byte[][] mSignatures;
    public byte[] mToken;
    public Integer mVersion;
    public final Intent originalIntent;
    public InetAddress originatingIp;
    public String[] originatingPackageNames;
    public byte[][] originatingSignatures;
    public final int originatingUid;
    public final Uri originatingUri;
    public InetAddress referrerIp;
    public final Uri referrerUri;
    
    public VerificationState(Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      this.id = localBundle.getInt("android.content.pm.extra.VERIFICATION_ID");
      this.dataUri = paramIntent.getData();
      this.flags = localBundle.getInt("android.content.pm.extra.VERIFICATION_INSTALL_FLAGS");
      this.originatingUri = ((Uri)localBundle.getParcelable("android.intent.extra.ORIGINATING_URI"));
      this.referrerUri = ((Uri)localBundle.getParcelable("android.intent.extra.REFERRER"));
      this.originatingUid = localBundle.getInt("android.intent.extra.ORIGINATING_UID", -1);
      this.installerUid = localBundle.getInt("android.content.pm.extra.VERIFICATION_INSTALLER_UID", -1);
      this.fromVerificationActivity = localBundle.getBoolean("com.google.android.vending.verifier.extra.FROM_VERIFICATION_ACTIVITY");
      this.originalIntent = paramIntent;
      this.mPackageName = localBundle.getString("android.content.pm.extra.VERIFICATION_PACKAGE_NAME");
      this.mVersion = ((Integer)localBundle.get("android.content.pm.extra.VERIFICATION_VERSION_CODE"));
    }
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(this.id);
      arrayOfObject[1] = this.dataUri;
      arrayOfObject[2] = Integer.valueOf(this.flags);
      arrayOfObject[3] = Boolean.valueOf(this.fromVerificationActivity);
      return String.format("id = %d, data=%s flags=%d fromVerificationActivity=%b", arrayOfObject);
    }
  }
  
  private final class VerifyInstalledPackagesTask
    extends AsyncTask<Void, Void, Map<String, PackageVerificationData>>
  {
    private final Context mContext = PackageVerificationService.this;
    private String mSafetyNetId;
    
    public VerifyInstalledPackagesTask() {}
    
    @TargetApi(9)
    private Map<String, PackageVerificationData> doInBackground$4f3371dc()
    {
      HashMap localHashMap = new HashMap();
      List localList = PackageVerificationService.this.getPackageManager().getInstalledPackages(64);
      PackageVerificationDataStore localPackageVerificationDataStore = new PackageVerificationDataStore(this.mContext);
      boolean bool1 = PackageVerificationService.access$300();
      boolean bool2 = ((Boolean)G.verifyInstalledPlayPackagesEnabled.get()).booleanValue();
      int i = Build.VERSION.SDK_INT;
      int j = 0;
      HashSet localHashSet;
      Iterator localIterator1;
      if (i >= 14)
      {
        if (new Random().nextFloat() < ((Float)G.verifySystemPackagesProbability.get()).floatValue()) {
          j = 1;
        }
      }
      else
      {
        Map localMap = localPackageVerificationDataStore.getAll();
        localHashSet = new HashSet(localMap.keySet());
        localIterator1 = localList.iterator();
      }
      for (;;)
      {
        if (localIterator1.hasNext())
        {
          PackageInfo localPackageInfo = (PackageInfo)localIterator1.next();
          if (isCancelled())
          {
            return null;
            j = 0;
            break;
          }
          String str1 = localPackageInfo.packageName;
          localHashSet.remove(str1);
          ApplicationInfo localApplicationInfo = localPackageInfo.applicationInfo;
          if (localApplicationInfo != null)
          {
            boolean bool3;
            label203:
            long l1;
            label236:
            boolean bool4;
            label258:
            PackageVerificationData localPackageVerificationData;
            long l2;
            Object localObject;
            String str2;
            File localFile;
            label348:
            boolean bool5;
            label372:
            boolean bool6;
            if ((0x1 & localApplicationInfo.flags) != 0)
            {
              bool3 = true;
              if ((bool3) && ((j == 0) || (!localApplicationInfo.enabled))) {
                break label503;
              }
              if (Build.VERSION.SDK_INT < 9) {
                break label505;
              }
              l1 = localPackageInfo.lastUpdateTime;
              if (FinskyApp.get().mLibraries.getAppOwners(str1).isEmpty()) {
                break label516;
              }
              bool4 = true;
              if (((!bool4) && (!bool1)) || ((bool4) && (!bool2))) {
                break label520;
              }
              localPackageVerificationData = localPackageVerificationDataStore.get(str1);
              if ((localPackageVerificationData == null) || (l1 != localPackageVerificationData.mCacheFingerprint))
              {
                l2 = 0L;
                localObject = null;
                str2 = localApplicationInfo.publicSourceDir;
                localFile = new File(str2);
                if (localFile.exists()) {
                  break label522;
                }
                FinskyLog.w("Cannot find file for %s", new Object[] { str2 });
                if (localObject == null) {
                  break label548;
                }
                if (localApplicationInfo.publicSourceDir.equals(localApplicationInfo.sourceDir)) {
                  break label611;
                }
                bool5 = true;
                localPackageVerificationData = new PackageVerificationData(str1, l1, (byte[])localObject, l2, bool5, false);
                localPackageVerificationDataStore.put(localPackageVerificationData);
              }
              localPackageVerificationData.mInstalledByPlay = bool4;
              if ((0x200000 & localApplicationInfo.flags) == 0) {
                break label617;
              }
              bool6 = true;
              label419:
              localPackageVerificationData.mInStoppedState = bool6;
              localPackageVerificationData.mSystemApp = bool3;
              if ((0x80 & localApplicationInfo.flags) == 0) {
                break label623;
              }
            }
            label516:
            label520:
            label522:
            label548:
            label611:
            label617:
            label623:
            for (boolean bool7 = true;; bool7 = false)
            {
              for (;;)
              {
                localPackageVerificationData.mUpdatedSystemApp = bool7;
                localPackageVerificationData.mCertFingerprints = PackageVerificationService.getCertificateFingerprints(localPackageInfo.signatures);
                localHashMap.put(localPackageVerificationData.mPackageName, localPackageVerificationData);
                FinskyLog.d("Adding %s for verification", new Object[] { str1 });
                break;
                bool3 = false;
                break label203;
                label503:
                break;
                label505:
                l1 = localPackageInfo.versionCode;
                break label236;
                bool4 = false;
                break label258;
                break;
                if (!localFile.canRead())
                {
                  FinskyLog.w("Cannot read file for %s", new Object[] { str2 });
                  localObject = null;
                  break label348;
                  break;
                }
                l2 = localFile.length();
                try
                {
                  byte[] arrayOfByte = PackageVerificationService.getSha256Hash(localFile);
                  localObject = arrayOfByte;
                }
                catch (IOException localIOException)
                {
                  FinskyLog.d("Error while calculating sha256 for file=%s, error=%s", new Object[] { str2, localIOException });
                  localObject = null;
                }
                catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
                {
                  throw new RuntimeException(localNoSuchAlgorithmException);
                }
              }
              bool5 = false;
              break label372;
              bool6 = false;
              break label419;
            }
          }
        }
      }
      Iterator localIterator2 = localHashSet.iterator();
      while (localIterator2.hasNext()) {
        localPackageVerificationDataStore.remove((String)localIterator2.next());
      }
      this.mSafetyNetId = PackageVerificationService.getSafetyNetId(this.mContext);
      return localHashMap;
    }
    
    protected final void onPreExecute()
    {
      FinskyLog.d("Verifying installed apps", new Object[0]);
    }
  }
  
  private final class WorkerTask
    extends AsyncTask<Void, Void, Boolean>
  {
    private final Context mContext;
    private final PackageVerificationService.VerificationState mState;
    
    public WorkerTask(PackageVerificationService.VerificationState paramVerificationState)
    {
      this.mState = paramVerificationState;
      this.mContext = PackageVerificationService.this;
    }
    
    private Boolean doInBackground$5f8445a4()
    {
      this.mState.mResult = 1;
      if (!PackageVerificationService.this.getPackageInfo(this.mState)) {
        return Boolean.valueOf(false);
      }
      PackageManager localPackageManager1;
      String[] arrayOfString1;
      if (this.mState.originatingUid != -1)
      {
        PackageManager localPackageManager2 = this.mContext.getPackageManager();
        String[] arrayOfString2 = localPackageManager2.getPackagesForUid(this.mState.originatingUid);
        this.mState.originatingPackageNames = arrayOfString2;
        if ((arrayOfString2 != null) && (arrayOfString2.length > 0)) {
          try
          {
            PackageInfo localPackageInfo2 = localPackageManager2.getPackageInfo(arrayOfString2[0], 64);
            this.mState.originatingSignatures = PackageVerificationService.getRawSignatures(localPackageInfo2.signatures);
            if ((PackageVerificationService.access$200$392ecb(this.mState.originatingSignatures, this.mState.mSignatures)) && (!PackageVerificationService.access$300()))
            {
              int i = arrayOfString2.length;
              for (int j = 0; j < i; j++) {
                if (localPackageManager2.checkPermission("android.permission.INSTALL_PACKAGES", arrayOfString2[j]) == 0)
                {
                  Object[] arrayOfObject3 = new Object[1];
                  arrayOfObject3[0] = Integer.valueOf(this.mState.id);
                  FinskyLog.d("Skipping verification for id=%d", arrayOfObject3);
                  Boolean localBoolean = Boolean.valueOf(false);
                  return localBoolean;
                }
              }
            }
            Object[] arrayOfObject2;
            if (this.mState.installerUid == -1) {
              break label365;
            }
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException2)
          {
            arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = arrayOfString2[0];
            FinskyLog.d("Could not retrieve signatures for package %s", arrayOfObject2);
          }
        }
        localPackageManager1 = this.mContext.getPackageManager();
        arrayOfString1 = localPackageManager1.getPackagesForUid(this.mState.installerUid);
        this.mState.installerPackageNames = arrayOfString1;
        if ((arrayOfString1 == null) || (arrayOfString1.length <= 0)) {}
      }
      for (;;)
      {
        try
        {
          PackageInfo localPackageInfo1 = localPackageManager1.getPackageInfo(arrayOfString1[0], 64);
          this.mState.installerSignatures = PackageVerificationService.getRawSignatures(localPackageInfo1.signatures);
          PackageVerificationService.access$400(this.mState);
          this.mState.mSafetyNetId = PackageVerificationService.getSafetyNetId(this.mContext);
          return Boolean.valueOf(true);
          this.mState.originatingPackageNames = null;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException1)
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = arrayOfString1[0];
          FinskyLog.d("Could not retrieve signatures for package %s", arrayOfObject1);
          continue;
        }
        label365:
        this.mState.installerPackageNames = null;
      }
    }
    
    protected final void onPreExecute()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mState;
      FinskyLog.d("Verification Requested for %s", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageVerificationService
 * JD-Core Version:    0.7.0.1
 */