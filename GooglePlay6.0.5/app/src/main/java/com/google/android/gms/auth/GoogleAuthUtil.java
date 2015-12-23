package com.google.android.gms.auth;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.UserRecoverableException;
import com.google.android.gms.common.internal.zzx;
import java.io.IOException;

public final class GoogleAuthUtil
{
  public static final String KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
  public static final String KEY_CALLER_UID = "callerUid";
  private static final ComponentName zzVl = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
  private static final ComponentName zzVm = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");
  
  public static PendingIntent getRecoveryIfSuggested$12f08959(Context paramContext, String paramString)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    zzx.zzcy("Calling this from your main thread can lead to deadlock");
    try
    {
      GooglePlayServicesUtil.zzae(paramContext.getApplicationContext());
      final Bundle localBundle = new Bundle();
      localBundle.putString(KEY_ANDROID_PACKAGE_NAME, paramContext.getPackageName());
      zza local3 = new zza() {};
      RecoveryDecision localRecoveryDecision = (RecoveryDecision)zza(paramContext, zzVm, local3);
      if ((localRecoveryDecision.showRecoveryInterstitial) && (localRecoveryDecision.isRecoveryInterstitialAllowed)) {
        return localRecoveryDecision.recoveryIntent;
      }
    }
    catch (GooglePlayServicesRepairableException localGooglePlayServicesRepairableException)
    {
      throw new GooglePlayServicesAvailabilityException(localGooglePlayServicesRepairableException.zzVA, localGooglePlayServicesRepairableException.getMessage(), new Intent(localGooglePlayServicesRepairableException.mIntent));
    }
    catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
    {
      throw new GoogleAuthException(localGooglePlayServicesNotAvailableException.getMessage());
    }
    return null;
  }
  
  /* Error */
  private static <T> T zza(Context paramContext, ComponentName paramComponentName, zza<T> paramzza)
    throws IOException, GoogleAuthException
  {
    // Byte code:
    //   0: new 138	com/google/android/gms/common/zza
    //   3: dup
    //   4: invokespecial 139	com/google/android/gms/common/zza:<init>	()V
    //   7: astore_3
    //   8: aload_0
    //   9: invokestatic 145	com/google/android/gms/common/internal/zzl:zzav	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzl;
    //   12: astore 4
    //   14: aload 4
    //   16: aload_1
    //   17: aload_3
    //   18: ldc 147
    //   20: invokevirtual 150	com/google/android/gms/common/internal/zzl:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   23: ifeq +65 -> 88
    //   26: aload_2
    //   27: aload_3
    //   28: invokevirtual 154	com/google/android/gms/common/zza:zzoj	()Landroid/os/IBinder;
    //   31: invokeinterface 160 2 0
    //   36: astore 8
    //   38: aload 4
    //   40: aload_1
    //   41: aload_3
    //   42: ldc 147
    //   44: invokevirtual 164	com/google/android/gms/common/internal/zzl:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   47: aload 8
    //   49: areturn
    //   50: astore 5
    //   52: ldc 147
    //   54: ldc 166
    //   56: aload 5
    //   58: invokestatic 172	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   61: pop
    //   62: new 46	java/io/IOException
    //   65: dup
    //   66: ldc 166
    //   68: aload 5
    //   70: invokespecial 175	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   73: athrow
    //   74: astore 6
    //   76: aload 4
    //   78: aload_1
    //   79: aload_3
    //   80: ldc 147
    //   82: invokevirtual 164	com/google/android/gms/common/internal/zzl:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   85: aload 6
    //   87: athrow
    //   88: new 46	java/io/IOException
    //   91: dup
    //   92: ldc 177
    //   94: invokespecial 178	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   97: athrow
    //   98: astore 5
    //   100: goto -48 -> 52
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	103	0	paramContext	Context
    //   0	103	1	paramComponentName	ComponentName
    //   0	103	2	paramzza	zza<T>
    //   7	73	3	localzza	com.google.android.gms.common.zza
    //   12	65	4	localzzl	com.google.android.gms.common.internal.zzl
    //   50	19	5	localInterruptedException	java.lang.InterruptedException
    //   98	1	5	localRemoteException	RemoteException
    //   74	12	6	localObject1	Object
    //   36	12	8	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   26	38	50	java/lang/InterruptedException
    //   26	38	74	finally
    //   52	74	74	finally
    //   26	38	98	android/os/RemoteException
  }
  
  private static abstract interface zza<T>
  {
    public abstract T exec(IBinder paramIBinder)
      throws RemoteException, IOException, GoogleAuthException;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.GoogleAuthUtil
 * JD-Core Version:    0.7.0.1
 */