package com.google.android.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public final class GCMRegistrar
{
  private static GCMBroadcastReceiver sRetryReceiver;
  static String sRetryReceiverClassName;
  
  private static int getAppVersion(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException("Coult not get package name: " + localNameNotFoundException);
    }
  }
  
  static String getFlatSenderIds(String... paramVarArgs)
  {
    if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
      throw new IllegalArgumentException("No senderIds");
    }
    StringBuilder localStringBuilder = new StringBuilder(paramVarArgs[0]);
    for (int i = 1; i < paramVarArgs.length; i++) {
      localStringBuilder.append(',').append(paramVarArgs[i]);
    }
    return localStringBuilder.toString();
  }
  
  public static String getRegistrationId(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.google.android.gcm", 0);
    String str = localSharedPreferences.getString("regId", "");
    int i = localSharedPreferences.getInt("appVersion", -2147483648);
    int j = getAppVersion(paramContext);
    if ((i != -2147483648) && (i != j))
    {
      Log.v("GCMRegistrar", "App version changed from " + i + " to " + j + "; resetting registration id");
      setRegistrationId(paramContext, "");
      str = "";
    }
    return str;
  }
  
  public static void internalRegister(Context paramContext, String... paramVarArgs)
  {
    String str = getFlatSenderIds(paramVarArgs);
    Log.v("GCMRegistrar", "Registering app " + paramContext.getPackageName() + " of senders " + str);
    Intent localIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
    localIntent.setPackage("com.google.android.gsf");
    localIntent.putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    localIntent.putExtra("sender", str);
    paramContext.startService(localIntent);
  }
  
  public static void resetBackoff(Context paramContext)
  {
    Log.d("GCMRegistrar", "resetting backoff for " + paramContext.getPackageName());
    setBackoff(paramContext, 3000);
  }
  
  static void setBackoff(Context paramContext, int paramInt)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.google.android.gcm", 0).edit();
    localEditor.putInt("backoff_ms", paramInt);
    localEditor.commit();
  }
  
  static String setRegistrationId(Context paramContext, String paramString)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.google.android.gcm", 0);
    String str = localSharedPreferences.getString("regId", "");
    int i = getAppVersion(paramContext);
    Log.v("GCMRegistrar", "Saving regId on app version " + i);
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putString("regId", paramString);
    localEditor.putInt("appVersion", i);
    localEditor.commit();
    return str;
  }
  
  /* Error */
  static void setRetryBroadcastReceiver(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 194	com/google/android/gcm/GCMRegistrar:sRetryReceiver	Lcom/google/android/gcm/GCMBroadcastReceiver;
    //   6: ifnonnull +94 -> 100
    //   9: getstatic 196	com/google/android/gcm/GCMRegistrar:sRetryReceiverClassName	Ljava/lang/String;
    //   12: ifnonnull +92 -> 104
    //   15: ldc 94
    //   17: ldc 198
    //   19: invokestatic 201	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   22: pop
    //   23: new 203	com/google/android/gcm/GCMBroadcastReceiver
    //   26: dup
    //   27: invokespecial 204	com/google/android/gcm/GCMBroadcastReceiver:<init>	()V
    //   30: putstatic 194	com/google/android/gcm/GCMRegistrar:sRetryReceiver	Lcom/google/android/gcm/GCMBroadcastReceiver;
    //   33: aload_0
    //   34: invokevirtual 22	android/content/Context:getPackageName	()Ljava/lang/String;
    //   37: astore 4
    //   39: new 206	android/content/IntentFilter
    //   42: dup
    //   43: ldc 208
    //   45: invokespecial 209	android/content/IntentFilter:<init>	(Ljava/lang/String;)V
    //   48: astore 5
    //   50: aload 5
    //   52: aload 4
    //   54: invokevirtual 212	android/content/IntentFilter:addCategory	(Ljava/lang/String;)V
    //   57: new 38	java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial 213	java/lang/StringBuilder:<init>	()V
    //   64: aload 4
    //   66: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: ldc 215
    //   71: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   77: astore 6
    //   79: ldc 94
    //   81: ldc 217
    //   83: invokestatic 109	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   86: pop
    //   87: aload_0
    //   88: getstatic 194	com/google/android/gcm/GCMRegistrar:sRetryReceiver	Lcom/google/android/gcm/GCMBroadcastReceiver;
    //   91: aload 5
    //   93: aload 6
    //   95: aconst_null
    //   96: invokevirtual 221	android/content/Context:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;Ljava/lang/String;Landroid/os/Handler;)Landroid/content/Intent;
    //   99: pop
    //   100: ldc 2
    //   102: monitorexit
    //   103: return
    //   104: getstatic 196	com/google/android/gcm/GCMRegistrar:sRetryReceiverClassName	Ljava/lang/String;
    //   107: invokestatic 227	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   110: invokevirtual 231	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   113: checkcast 203	com/google/android/gcm/GCMBroadcastReceiver
    //   116: putstatic 194	com/google/android/gcm/GCMRegistrar:sRetryReceiver	Lcom/google/android/gcm/GCMBroadcastReceiver;
    //   119: goto -86 -> 33
    //   122: astore_2
    //   123: ldc 94
    //   125: new 38	java/lang/StringBuilder
    //   128: dup
    //   129: ldc 233
    //   131: invokespecial 44	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   134: getstatic 196	com/google/android/gcm/GCMRegistrar:sRetryReceiverClassName	Ljava/lang/String;
    //   137: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: ldc 235
    //   142: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: ldc 203
    //   147: invokevirtual 238	java/lang/Class:getName	()Ljava/lang/String;
    //   150: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: ldc 240
    //   155: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   161: invokestatic 201	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   164: pop
    //   165: new 203	com/google/android/gcm/GCMBroadcastReceiver
    //   168: dup
    //   169: invokespecial 204	com/google/android/gcm/GCMBroadcastReceiver:<init>	()V
    //   172: putstatic 194	com/google/android/gcm/GCMRegistrar:sRetryReceiver	Lcom/google/android/gcm/GCMBroadcastReceiver;
    //   175: goto -142 -> 33
    //   178: astore_1
    //   179: ldc 2
    //   181: monitorexit
    //   182: aload_1
    //   183: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	paramContext	Context
    //   178	5	1	localObject	Object
    //   122	1	2	localException	java.lang.Exception
    //   37	28	4	str1	String
    //   48	44	5	localIntentFilter	android.content.IntentFilter
    //   77	17	6	str2	String
    // Exception table:
    //   from	to	target	type
    //   104	119	122	java/lang/Exception
    //   3	33	178	finally
    //   33	100	178	finally
    //   104	119	178	finally
    //   123	175	178	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gcm.GCMRegistrar
 * JD-Core Version:    0.7.0.1
 */