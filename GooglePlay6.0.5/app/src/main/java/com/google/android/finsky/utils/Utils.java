package com.google.android.finsky.utils;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Looper;
import com.google.android.finsky.activities.DebugActivity;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.G;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.utils.PlayUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Pattern;

public final class Utils
{
  private static Pattern COMMA_PATTERN = Pattern.compile(",");
  private static long[] EMPTY_LONG_ARRAY = new long[0];
  private static String[] EMPTY_STRING_ARRAY = new String[0];
  
  public static void checkUrlIsSecure(String paramString)
  {
    boolean bool = PlayUtils.isTestDevice();
    Uri localUri = Uri.parse(paramString);
    if ((localUri.getScheme().equals("https")) || ((bool) && ((localUri.getHost().toLowerCase().endsWith("corp.google.com")) || (localUri.getHost().startsWith("192.168.0")) || (localUri.getHost().startsWith("127.0.0"))))) {
      return;
    }
    throw new RuntimeException("Insecure URL: " + paramString);
  }
  
  public static String commaPackLongs(long[] paramArrayOfLong)
  {
    if ((paramArrayOfLong == null) || (paramArrayOfLong.length == 0)) {
      return "";
    }
    if (paramArrayOfLong.length == 1) {
      return String.valueOf(paramArrayOfLong[0]);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramArrayOfLong.length; i++)
    {
      if (i != 0) {
        localStringBuilder.append(',');
      }
      localStringBuilder.append(paramArrayOfLong[i]);
    }
    return localStringBuilder.toString();
  }
  
  public static String commaPackStrings(List<String> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return "";
    }
    int i = paramList.size();
    if (i == 1) {
      return (String)paramList.get(0);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int j = 0; j < i; j++)
    {
      if (j != 0) {
        localStringBuilder.append(',');
      }
      localStringBuilder.append((String)paramList.get(j));
    }
    return localStringBuilder.toString();
  }
  
  public static String commaPackStrings(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return "";
    }
    if (paramArrayOfString.length == 1) {
      return paramArrayOfString[0];
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (i != 0) {
        localStringBuilder.append(',');
      }
      localStringBuilder.append(paramArrayOfString[i]);
    }
    return localStringBuilder.toString();
  }
  
  public static long[] commaUnpackLongs(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      arrayOfLong = EMPTY_LONG_ARRAY;
      return arrayOfLong;
    }
    if (paramString.indexOf(',') == -1) {
      try
      {
        long l2 = Long.parseLong(paramString);
        return new long[] { l2 };
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        FinskyLog.wtf("Error parsing a string as long - %s", new Object[] { paramString });
        return EMPTY_LONG_ARRAY;
      }
    }
    String[] arrayOfString = COMMA_PATTERN.split(paramString);
    long[] arrayOfLong = new long[arrayOfString.length];
    int i = 0;
    while (i < arrayOfString.length)
    {
      String str = arrayOfString[i].trim();
      try
      {
        long l1 = Long.parseLong(str);
        arrayOfLong[i] = l1;
        i++;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        FinskyLog.wtf("Error parsing a string as long - %s", new Object[] { str });
      }
    }
    return EMPTY_LONG_ARRAY;
  }
  
  public static String[] commaUnpackStrings(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return EMPTY_STRING_ARRAY;
    }
    if (paramString.indexOf(',') == -1) {
      return new String[] { paramString };
    }
    return COMMA_PATTERN.split(paramString);
  }
  
  public static void copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[8192];
    try
    {
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        paramOutputStream.write(arrayOfByte, 0, i);
      }
    }
    finally
    {
      paramInputStream.close();
    }
  }
  
  public static void ensureNotOnMainThread()
  {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      return;
    }
    throw new IllegalStateException("This method cannot be called from the UI thread.");
  }
  
  public static void ensureOnMainThread()
  {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      return;
    }
    throw new IllegalStateException("This method must be called from the UI thread.");
  }
  
  @TargetApi(11)
  public static <P> void executeMultiThreaded(AsyncTask<P, ?, ?> paramAsyncTask, P... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      paramAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramVarArgs);
      return;
    }
    paramAsyncTask.execute(paramVarArgs);
  }
  
  public static String getDisplayAccountName(String paramString, Context paramContext)
  {
    if ((paramString == null) || (!GooglePlayServicesUtil.isSidewinderDevice(paramContext))) {
      return paramString;
    }
    return getDisplayAccountName$17065f8(AccountHandler.findAccount(paramString, paramContext));
  }
  
  public static String getDisplayAccountName$17065f8(Account paramAccount)
  {
    if (paramAccount == null) {
      return null;
    }
    if ("cn.google".equals(paramAccount.type)) {
      return paramAccount.name.substring(0, paramAccount.name.indexOf("@"));
    }
    return paramAccount.name;
  }
  
  public static String getItalicSafeString(String paramString)
  {
    return paramString + " ";
  }
  
  public static String getPreferenceKey(String paramString1, String paramString2)
  {
    return paramString2 + ":" + paramString1;
  }
  
  public static String getSysProperty(String paramString)
  {
    try
    {
      String str = (String)Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[] { String.class }).invoke(null, new Object[] { paramString });
      return str;
    }
    catch (Exception localException)
    {
      FinskyLog.wtf("Can't get system properties: %s", new Object[] { localException });
    }
    return "";
  }
  
  @SuppressLint({"NewApi"})
  public static boolean isBackgroundDataEnabled(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (Build.VERSION.SDK_INT < 14) {
      return localConnectivityManager.getBackgroundDataSetting();
    }
    for (NetworkInfo localNetworkInfo : localConnectivityManager.getAllNetworkInfo()) {
      if ((localNetworkInfo != null) && (localNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.BLOCKED)) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean isDownloadingOrInstalling(int paramInt)
  {
    return (paramInt == 1) || (paramInt == 2) || (paramInt == 3);
  }
  
  public static boolean isEmptyOrSpaces(CharSequence paramCharSequence)
  {
    boolean bool;
    if ((paramCharSequence != null) && (paramCharSequence.length() != 0))
    {
      if (paramCharSequence != null) {
        break label38;
      }
      paramCharSequence = null;
      int m = paramCharSequence.length();
      bool = false;
      if (m != 0) {}
    }
    else
    {
      bool = true;
    }
    return bool;
    label38:
    int i = -1 + paramCharSequence.length();
    for (int j = 0; (j <= i) && (paramCharSequence.charAt(j) <= ' '); j++) {}
    for (;;)
    {
      int k;
      if ((k >= j) && (paramCharSequence.charAt(k) <= ' '))
      {
        k--;
      }
      else
      {
        if ((j == 0) && (k == i)) {
          break;
        }
        paramCharSequence = paramCharSequence.subSequence(j, k + 1);
        break;
        k = i;
      }
    }
  }
  
  public static byte[] readBytes(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      copy(paramInputStream, localByteArrayOutputStream);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      return arrayOfByte;
    }
    finally
    {
      localByteArrayOutputStream.close();
    }
  }
  
  public static void safeClose(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException) {}
  }
  
  public static void syncDebugActivityStatus(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    if (((Boolean)G.debugOptionsEnabled.get()).booleanValue()) {}
    for (int i = 1;; i = 2)
    {
      localPackageManager.setComponentEnabledSetting(new ComponentName(paramContext, DebugActivity.class), i, 1);
      return;
    }
  }
  
  public static String urlDecode(String paramString)
  {
    try
    {
      String str = URLDecoder.decode(paramString, "UTF-8");
      return str;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = localIllegalArgumentException.getMessage();
      FinskyLog.d("Unable to parse %s - %s", arrayOfObject);
      return null;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      FinskyLog.wtf("%s", new Object[] { localUnsupportedEncodingException });
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }
  
  public static String urlEncode(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      FinskyLog.wtf("%s", new Object[] { localUnsupportedEncodingException });
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Utils
 * JD-Core Version:    0.7.0.1
 */