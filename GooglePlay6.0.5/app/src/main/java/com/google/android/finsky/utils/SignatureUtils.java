package com.google.android.finsky.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Binder;
import android.util.Base64;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.wallet.common.util.AndroidUtils;
import java.util.List;

public final class SignatureUtils
{
  private static List<Signature> sOtherSignatures;
  
  private static boolean containsFirstPartyPackage(Context paramContext, String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++) {
      if (isFirstPartyPackage(paramContext, paramArrayOfString[j])) {
        return true;
      }
    }
    return false;
  }
  
  /* Error */
  private static void faultInOtherSignatures(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 17	com/google/android/finsky/utils/SignatureUtils:sOtherSignatures	Ljava/util/List;
    //   6: ifnonnull +57 -> 63
    //   9: new 19	java/util/ArrayList
    //   12: dup
    //   13: invokespecial 23	java/util/ArrayList:<init>	()V
    //   16: astore_2
    //   17: aload_2
    //   18: putstatic 17	com/google/android/finsky/utils/SignatureUtils:sOtherSignatures	Ljava/util/List;
    //   21: aload_2
    //   22: ldc 25
    //   24: invokestatic 29	com/google/android/finsky/utils/SignatureUtils:makeSignature	(Ljava/lang/String;)Landroid/content/pm/Signature;
    //   27: invokeinterface 35 2 0
    //   32: pop
    //   33: aload_0
    //   34: invokestatic 39	com/google/android/finsky/utils/SignatureUtils:isFinskyWithDebugKeys	(Landroid/content/Context;)Z
    //   37: ifeq +30 -> 67
    //   40: ldc 41
    //   42: iconst_0
    //   43: anewarray 4	java/lang/Object
    //   46: invokestatic 47	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   49: getstatic 17	com/google/android/finsky/utils/SignatureUtils:sOtherSignatures	Ljava/util/List;
    //   52: ldc 49
    //   54: invokestatic 29	com/google/android/finsky/utils/SignatureUtils:makeSignature	(Ljava/lang/String;)Landroid/content/pm/Signature;
    //   57: invokeinterface 35 2 0
    //   62: pop
    //   63: ldc 2
    //   65: monitorexit
    //   66: return
    //   67: ldc 51
    //   69: iconst_0
    //   70: anewarray 4	java/lang/Object
    //   73: invokestatic 47	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   76: goto -13 -> 63
    //   79: astore_1
    //   80: ldc 2
    //   82: monitorexit
    //   83: aload_1
    //   84: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	paramContext	Context
    //   79	5	1	localObject	Object
    //   16	6	2	localArrayList	java.util.ArrayList
    // Exception table:
    //   from	to	target	type
    //   3	63	79	finally
    //   67	76	79	finally
  }
  
  public static String getCallingAppIfAuthorized(Context paramContext, String paramString, GservicesValue<Boolean> paramGservicesValue, FinskyEventLog paramFinskyEventLog, int paramInt)
  {
    int i = Binder.getCallingUid();
    PackageManager localPackageManager = paramContext.getPackageManager();
    String[] arrayOfString = localPackageManager.getPackagesForUid(i);
    String str1;
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i);
      FinskyLog.e("getPackagesForUid %d returned 0 packages", arrayOfObject1);
      logEvent(paramFinskyEventLog, paramInt, paramString, "no-packages-for-uid", null, null);
      str1 = null;
    }
    for (;;)
    {
      return str1;
      if (arrayOfString.length == 1) {}
      String str2;
      for (str1 = arrayOfString[0]; ((paramGservicesValue == null) || (!((Boolean)paramGservicesValue.get()).booleanValue())) && (!containsFirstPartyPackage(paramContext, arrayOfString)); str1 = "sharedUserId=" + str2)
      {
        FinskyLog.d("Unable to authorize API access for %s", new Object[] { str1 });
        logEvent(paramFinskyEventLog, paramInt, paramString, "auth-rejection", str1, null);
        return null;
        str2 = localPackageManager.getNameForUid(i);
        if (str2 == null)
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(i);
          FinskyLog.e("getNameForUid %d returned null", arrayOfObject2);
          logEvent(paramFinskyEventLog, paramInt, paramString, "no-name-for-uid", null, null);
          return null;
        }
      }
    }
  }
  
  public static String getCallingFirstPartyPackage(Activity paramActivity)
  {
    String str = AndroidUtils.getCallingPackage(paramActivity);
    if (str == null)
    {
      FinskyLog.d("Couldn't determine caller. Either use startActivityForResult or set FLAG_ACTIVITY_FORWARD_RESULT.", new Object[0]);
      str = null;
    }
    while ((str.equals(paramActivity.getPackageName())) || (isFirstPartyPackage(paramActivity, str))) {
      return str;
    }
    return null;
  }
  
  private static boolean isFinskyWithDebugKeys(Context paramContext)
  {
    for (;;)
    {
      int j;
      try
      {
        Signature[] arrayOfSignature = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures;
        Signature localSignature = makeSignature("MIIEqDCCA5CgAwIBAgIJANWFuGx90071MA0GCSqGSIb3DQEBBAUAMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbTAeFw0wODA0MTUyMzM2NTZaFw0zNTA5MDEyMzM2NTZaMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbTCCASAwDQYJKoZIhvcNAQEBBQADggENADCCAQgCggEBANbOLggKv+IxTdGNs8/TGFy0PTP6DHThvbbR24kT9ixcOd9W+EaBPWW+wPPKQmsHxajtWjmQwWfna8mZuSeJS48LIgAZlKkpFeVyxW0qMBujb8X8ETrWy550NaFtI6t9+u7hZeTfHwqNvacKhp1RbE6dBRGWynwMVX8XW8N1+UjFaq6GCJukT4qmpN2afb8sCjUigq0GuMwYXrFVee74bQgLHWGJwPmvmLHC69EH6kWr22ijx4OKXlSIx2xT1AsSHee70w5iDBiK4aph27yH3TxkXy9V89TDdexAcKk/cVHYNnDBapcavl7y0RiQ4biu8ymM8Ga/nmzhRKya6G0cGw8CAQOjgfwwgfkwHQYDVR0OBBYEFI0cxb6VTEM8YYY6FbBMvAPyT+CyMIHJBgNVHSMEgcEwgb6AFI0cxb6VTEM8YYY6FbBMvAPyT+CyoYGapIGXMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbYIJANWFuGx90071MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEEBQADggEBABnTDPEF+3iSP0wNfdIjIz1AlnrPzgAIHVvXxunW7SBrDhEglQZBbKJEk5kT0mtKoOD1JMrSu1xuTKEBahWRbqHsXclaXjoBADb0kkjVEJu/Lh5hgYZnOjvlba8Ld7HCKePCVePoTJBdI4fvugnL8TsgK05aIskyY0hKI9L8KfqfGTl1lzOv2KoWD0KWwtAWPoGChZxmQ+nBli+gwYMzM1vAkP+aayLe0a1EQimlOalO762r0GXO0ks+UeXde2Z4e+8S/pf7pITEI/tP+MxJTALw9QUWEv9lKTk+jkbqxbsh8nfBUapfKqYn0eidpwq2AzVp3juYl7//fKnaPhJD9gs=");
        int i = arrayOfSignature.length;
        j = 0;
        boolean bool = false;
        if (j < i)
        {
          if (localSignature.equals(arrayOfSignature[j])) {
            bool = true;
          }
        }
        else {
          return bool;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        FinskyLog.wtf(localNameNotFoundException, "Unable to look up our own PackageInfo", new Object[0]);
        return false;
      }
      j++;
    }
  }
  
  public static boolean isFirstPartyPackage(Context paramContext, String paramString)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    if (localPackageManager.checkSignatures(paramString, paramContext.getPackageName()) == 0) {}
    for (;;)
    {
      return true;
      faultInOtherSignatures(paramContext);
      try
      {
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramString, 64);
        if (paramString.equals("com.quickoffice.android"))
        {
          Signature localSignature2 = makeSignature("MIICVzCCAcCgAwIBAgIESVJ9+TANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJVUzELMAkGA1UECBMCVFgxDzANBgNVBAcTBkRhbGxhczEUMBIGA1UEChMLUXVpY2tvZmZpY2UxDzANBgNVBAsTBlFPLURFVjEbMBkGA1UEAxMSQWxleGFuZGVyIFN0ZXBhbm92MCAXDTA4MTIyNDE4MjI0OVoYDzIwNjMwOTI3MTgyMjQ5WjBvMQswCQYDVQQGEwJVUzELMAkGA1UECBMCVFgxDzANBgNVBAcTBkRhbGxhczEUMBIGA1UEChMLUXVpY2tvZmZpY2UxDzANBgNVBAsTBlFPLURFVjEbMBkGA1UEAxMSQWxleGFuZGVyIFN0ZXBhbm92MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChx/LvhUKzh2bdxAYelxtLR+EU7hWRFw/GmJHgTSvhAVKReWUZolS2bOk0xaybV499hHEAGutl2mX90KJ4EIZh3LZNk3qjy3terL8YDqoayWI+EZtSAifFEBbn3bPdhX6pqI/1v/SivKf/LQrw5VASStE2dylHtRAnLLJGbvOWeQIDAQABMA0GCSqGSIb3DQEBBQUAA4GBABU7YooXGdyniqWBK44g0XjN9NLTKTpXec856AVBQWv+PnqAvGS282KChu2tMYyJHRok0jxvTlYwgXZIa31Sw57372zJ6hDqgnBQdBdF7FnKDhyZZ+XPZHGEr+TXe/3jZwl5tZo7WmoWMAkWnMqda/CQ7TVgr+8gacujXGyhUsWv");
          Signature[] arrayOfSignature2 = localPackageInfo.signatures;
          int m = arrayOfSignature2.length;
          for (int n = 0;; n++)
          {
            if (n >= m) {
              break label105;
            }
            if (arrayOfSignature2[n].equals(localSignature2)) {
              break;
            }
          }
        }
        arrayOfSignature1 = localPackageInfo.signatures;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        FinskyLog.w("Couldn't get info for package %s", new Object[] { paramString });
        return false;
      }
    }
    label105:
    Signature[] arrayOfSignature1;
    int i = arrayOfSignature1.length;
    label174:
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label180;
      }
      Signature localSignature1 = arrayOfSignature1[j];
      for (int k = 0;; k++)
      {
        if (k >= sOtherSignatures.size()) {
          break label174;
        }
        if (localSignature1.equals(sOtherSignatures.get(k))) {
          break;
        }
      }
    }
    label180:
    return false;
  }
  
  public static void logEvent(FinskyEventLog paramFinskyEventLog, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt).setDocument(paramString1).setReason(paramString2);
    if (paramString2 == null) {}
    for (boolean bool = true;; bool = false)
    {
      paramFinskyEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.setOperationSuccess(bool).setCallingPackage(paramString3).setExceptionType(paramString4).event);
      return;
    }
  }
  
  private static Signature makeSignature(String paramString)
  {
    return new Signature(Base64.decode(paramString, 0));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.SignatureUtils
 * JD-Core Version:    0.7.0.1
 */