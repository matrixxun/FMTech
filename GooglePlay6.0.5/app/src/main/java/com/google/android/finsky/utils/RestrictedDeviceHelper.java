package com.google.android.finsky.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;

public class RestrictedDeviceHelper
{
  private static Boolean sIsEduDevice = null;
  private static Boolean sIsSchoolOwnedEduDevice = null;
  private static boolean sUserPinValid = false;
  
  @TargetApi(21)
  public static Intent getIntentForChallengeUIIfRequired(Activity paramActivity)
  {
    if (sUserPinValid) {
      return null;
    }
    Intent localIntent = ((RestrictionsManager)paramActivity.getSystemService("restrictions")).createLocalApprovalIntent();
    PersistableBundle localPersistableBundle = new PersistableBundle();
    localPersistableBundle.putString("android.request.mesg", paramActivity.getString(2131362662));
    localIntent.putExtra("android.content.extra.REQUEST_BUNDLE", localPersistableBundle);
    return localIntent;
  }
  
  /* Error */
  @TargetApi(18)
  private static boolean isEduDevice()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 13	com/google/android/finsky/utils/RestrictedDeviceHelper:sIsEduDevice	Ljava/lang/Boolean;
    //   6: ifnonnull +34 -> 40
    //   9: invokestatic 78	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   12: ldc 80
    //   14: invokevirtual 81	com/google/android/finsky/FinskyApp:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   17: checkcast 83	android/app/admin/DevicePolicyManager
    //   20: astore_2
    //   21: aload_2
    //   22: getstatic 89	com/google/android/finsky/config/G:eduDevicePolicyApp	Lcom/google/android/play/utils/config/GservicesValue;
    //   25: invokevirtual 94	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   28: checkcast 96	java/lang/String
    //   31: invokevirtual 100	android/app/admin/DevicePolicyManager:isDeviceOwnerApp	(Ljava/lang/String;)Z
    //   34: invokestatic 106	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   37: putstatic 13	com/google/android/finsky/utils/RestrictedDeviceHelper:sIsEduDevice	Ljava/lang/Boolean;
    //   40: getstatic 13	com/google/android/finsky/utils/RestrictedDeviceHelper:sIsEduDevice	Ljava/lang/Boolean;
    //   43: invokevirtual 109	java/lang/Boolean:booleanValue	()Z
    //   46: istore_1
    //   47: ldc 2
    //   49: monitorexit
    //   50: iload_1
    //   51: ireturn
    //   52: astore 4
    //   54: aload 4
    //   56: ldc 111
    //   58: iconst_0
    //   59: anewarray 4	java/lang/Object
    //   62: invokestatic 117	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   65: iconst_0
    //   66: invokestatic 106	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   69: putstatic 13	com/google/android/finsky/utils/RestrictedDeviceHelper:sIsEduDevice	Ljava/lang/Boolean;
    //   72: goto -32 -> 40
    //   75: astore_0
    //   76: ldc 2
    //   78: monitorexit
    //   79: aload_0
    //   80: athrow
    //   81: astore_3
    //   82: aload_3
    //   83: ldc 119
    //   85: iconst_0
    //   86: anewarray 4	java/lang/Object
    //   89: invokestatic 117	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   92: iconst_0
    //   93: invokestatic 106	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   96: putstatic 13	com/google/android/finsky/utils/RestrictedDeviceHelper:sIsEduDevice	Ljava/lang/Boolean;
    //   99: goto -59 -> 40
    // Local variable table:
    //   start	length	slot	name	signature
    //   75	5	0	localObject	Object
    //   46	5	1	bool	boolean
    //   20	2	2	localDevicePolicyManager	android.app.admin.DevicePolicyManager
    //   81	2	3	localRuntimeException	java.lang.RuntimeException
    //   52	3	4	localNoSuchMethodError	java.lang.NoSuchMethodError
    // Exception table:
    //   from	to	target	type
    //   21	40	52	java/lang/NoSuchMethodError
    //   3	21	75	finally
    //   21	40	75	finally
    //   40	47	75	finally
    //   54	72	75	finally
    //   82	99	75	finally
    //   21	40	81	java/lang/RuntimeException
  }
  
  public static void isLimitedOrSchoolEduUser(OnCompleteListener paramOnCompleteListener)
  {
    for (;;)
    {
      try
      {
        if (sIsSchoolOwnedEduDevice == null)
        {
          if ((FinskyApp.get().mUsers.mUserManagerFacade.isLimitedUser()) || (((Boolean)G.debugLimitedUserState.get()).booleanValue())) {
            sIsSchoolOwnedEduDevice = Boolean.valueOf(true);
          }
        }
        else
        {
          paramOnCompleteListener.onComplete(sIsSchoolOwnedEduDevice.booleanValue());
          return;
        }
        if ((Build.VERSION.SDK_INT >= 18) && (isEduDevice())) {
          isSchoolOwnedEduDevice(paramOnCompleteListener);
        } else {
          sIsSchoolOwnedEduDevice = Boolean.valueOf(false);
        }
      }
      finally {}
    }
  }
  
  private static void isSchoolOwnedEduDevice(OnCompleteListener paramOnCompleteListener)
  {
    try
    {
      FinskyApp localFinskyApp = FinskyApp.get();
      SchoolOwnershipServiceConnection localSchoolOwnershipServiceConnection = new SchoolOwnershipServiceConnection(localFinskyApp, paramOnCompleteListener, (byte)0);
      Intent localIntent = new Intent("com.google.android.nfcprovision.IOwnedService.BIND");
      localIntent.setComponent(new ComponentName("com.google.android.nfcprovision", "com.google.android.nfcprovision.SchoolOwnedService"));
      if (!localFinskyApp.bindService(localIntent, localSchoolOwnershipServiceConnection, 1))
      {
        sIsSchoolOwnedEduDevice = Boolean.valueOf(false);
        paramOnCompleteListener.onComplete(false);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static void setUserPinValid$1385ff()
  {
    sUserPinValid = true;
  }
  
  public static abstract interface OnCompleteListener
  {
    public abstract void onComplete(boolean paramBoolean);
  }
  
  private static final class SchoolOwnershipServiceConnection
    implements ServiceConnection
  {
    private final Context mContext;
    private final RestrictedDeviceHelper.OnCompleteListener mListener;
    
    private SchoolOwnershipServiceConnection(Context paramContext, RestrictedDeviceHelper.OnCompleteListener paramOnCompleteListener)
    {
      this.mContext = paramContext;
      this.mListener = paramOnCompleteListener;
    }
    
    /* Error */
    public final void onServiceConnected(ComponentName paramComponentName, android.os.IBinder paramIBinder)
    {
      // Byte code:
      //   0: ldc 28
      //   2: monitorenter
      //   3: aload_2
      //   4: invokestatic 34	com/google/android/nfcprovision/ISchoolOwnedService$Stub:asInterface	(Landroid/os/IBinder;)Lcom/google/android/nfcprovision/ISchoolOwnedService;
      //   7: astore 4
      //   9: aload 4
      //   11: invokeinterface 40 1 0
      //   16: invokestatic 46	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   19: invokestatic 50	com/google/android/finsky/utils/RestrictedDeviceHelper:access$102	(Ljava/lang/Boolean;)Ljava/lang/Boolean;
      //   22: pop
      //   23: aload_0
      //   24: getfield 17	com/google/android/finsky/utils/RestrictedDeviceHelper$SchoolOwnershipServiceConnection:mContext	Landroid/content/Context;
      //   27: aload_0
      //   28: invokevirtual 56	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
      //   31: aload_0
      //   32: getfield 19	com/google/android/finsky/utils/RestrictedDeviceHelper$SchoolOwnershipServiceConnection:mListener	Lcom/google/android/finsky/utils/RestrictedDeviceHelper$OnCompleteListener;
      //   35: invokestatic 60	com/google/android/finsky/utils/RestrictedDeviceHelper:access$100	()Ljava/lang/Boolean;
      //   38: invokevirtual 63	java/lang/Boolean:booleanValue	()Z
      //   41: invokeinterface 69 2 0
      //   46: ldc 28
      //   48: monitorexit
      //   49: return
      //   50: astore 6
      //   52: ldc 71
      //   54: iconst_0
      //   55: anewarray 4	java/lang/Object
      //   58: invokestatic 77	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   61: iconst_0
      //   62: invokestatic 46	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   65: invokestatic 50	com/google/android/finsky/utils/RestrictedDeviceHelper:access$102	(Ljava/lang/Boolean;)Ljava/lang/Boolean;
      //   68: pop
      //   69: aload_0
      //   70: getfield 17	com/google/android/finsky/utils/RestrictedDeviceHelper$SchoolOwnershipServiceConnection:mContext	Landroid/content/Context;
      //   73: aload_0
      //   74: invokevirtual 56	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
      //   77: aload_0
      //   78: getfield 19	com/google/android/finsky/utils/RestrictedDeviceHelper$SchoolOwnershipServiceConnection:mListener	Lcom/google/android/finsky/utils/RestrictedDeviceHelper$OnCompleteListener;
      //   81: invokestatic 60	com/google/android/finsky/utils/RestrictedDeviceHelper:access$100	()Ljava/lang/Boolean;
      //   84: invokevirtual 63	java/lang/Boolean:booleanValue	()Z
      //   87: invokeinterface 69 2 0
      //   92: goto -46 -> 46
      //   95: astore_3
      //   96: ldc 28
      //   98: monitorexit
      //   99: aload_3
      //   100: athrow
      //   101: astore 5
      //   103: aload_0
      //   104: getfield 17	com/google/android/finsky/utils/RestrictedDeviceHelper$SchoolOwnershipServiceConnection:mContext	Landroid/content/Context;
      //   107: aload_0
      //   108: invokevirtual 56	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
      //   111: aload_0
      //   112: getfield 19	com/google/android/finsky/utils/RestrictedDeviceHelper$SchoolOwnershipServiceConnection:mListener	Lcom/google/android/finsky/utils/RestrictedDeviceHelper$OnCompleteListener;
      //   115: invokestatic 60	com/google/android/finsky/utils/RestrictedDeviceHelper:access$100	()Ljava/lang/Boolean;
      //   118: invokevirtual 63	java/lang/Boolean:booleanValue	()Z
      //   121: invokeinterface 69 2 0
      //   126: aload 5
      //   128: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	129	0	this	SchoolOwnershipServiceConnection
      //   0	129	1	paramComponentName	ComponentName
      //   0	129	2	paramIBinder	android.os.IBinder
      //   95	5	3	localObject1	Object
      //   7	3	4	localISchoolOwnedService	com.google.android.nfcprovision.ISchoolOwnedService
      //   101	26	5	localObject2	Object
      //   50	1	6	localRemoteException	android.os.RemoteException
      // Exception table:
      //   from	to	target	type
      //   9	23	50	android/os/RemoteException
      //   3	9	95	finally
      //   23	46	95	finally
      //   46	49	95	finally
      //   69	92	95	finally
      //   96	99	95	finally
      //   103	129	95	finally
      //   9	23	101	finally
      //   52	69	101	finally
    }
    
    public final void onServiceDisconnected(ComponentName paramComponentName) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.RestrictedDeviceHelper
 * JD-Core Version:    0.7.0.1
 */