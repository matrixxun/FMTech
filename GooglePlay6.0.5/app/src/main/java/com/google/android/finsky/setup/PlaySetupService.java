package com.google.android.finsky.setup;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import com.android.vending.setup.IPlaySetupService.Stub;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreBackgroundActionEvent;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.protos.Restore.BackupDeviceInfo;
import com.google.android.finsky.protos.Restore.GetBackupDeviceChoicesResponse;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.DisplayMessageError;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

public class PlaySetupService
  extends Service
  implements InstallerListener
{
  private Semaphore mEarlyUpdateLock = null;
  private String mEarlyUpdatePackage;
  private boolean mEarlyUpdatePackageIsCritical;
  private Bundle mLastEarlyUpdateResponse;
  private boolean mListenerAdded;
  private int mServiceStartId;
  private int mStartupRefCount = 0;
  
  private boolean doCancelEarlyUpdate()
  {
    if (!((Boolean)G.setupWizardEarlyUpdateEnable.get()).booleanValue())
    {
      FinskyLog.wtf("Canceled early-update when disabled", new Object[0]);
      return true;
    }
    FutureTask localFutureTask = new FutureTask(new Callable() {});
    new Handler(getMainLooper()).post(localFutureTask);
    try
    {
      boolean bool = ((Boolean)localFutureTask.get()).booleanValue();
      return bool;
    }
    catch (InterruptedException localInterruptedException)
    {
      FinskyLog.wtf(localInterruptedException, "Canceler interrupted", new Object[0]);
      return true;
    }
    catch (ExecutionException localExecutionException)
    {
      FinskyLog.wtf(localExecutionException, "Canceler crashed", new Object[0]);
    }
    return true;
  }
  
  /* Error */
  private Bundle doGetEarlyUpdate()
  {
    // Byte code:
    //   0: getstatic 40	com/google/android/finsky/config/G:setupWizardEarlyUpdateEnable	Lcom/google/android/play/utils/config/GservicesValue;
    //   3: invokevirtual 46	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   6: checkcast 48	java/lang/Boolean
    //   9: invokevirtual 52	java/lang/Boolean:booleanValue	()Z
    //   12: ifne +5 -> 17
    //   15: aconst_null
    //   16: areturn
    //   17: invokestatic 350	com/google/android/finsky/utils/Utils:ensureNotOnMainThread	()V
    //   20: getstatic 143	com/google/android/finsky/api/DfeApiConfig:androidId	Lcom/google/android/play/utils/config/GservicesValue;
    //   23: invokevirtual 46	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   26: checkcast 145	java/lang/Long
    //   29: invokevirtual 149	java/lang/Long:longValue	()J
    //   32: lconst_0
    //   33: lcmp
    //   34: ifne +12 -> 46
    //   37: ldc 151
    //   39: iconst_0
    //   40: anewarray 56	java/lang/Object
    //   43: invokestatic 154	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   46: aload_0
    //   47: getfield 26	com/google/android/finsky/setup/PlaySetupService:mEarlyUpdateLock	Ljava/util/concurrent/Semaphore;
    //   50: getstatic 353	com/google/android/finsky/config/G:setupWizardEarlyUpdateDeadlockMaxMs	Lcom/google/android/play/utils/config/GservicesValue;
    //   53: invokevirtual 46	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   56: checkcast 145	java/lang/Long
    //   59: invokevirtual 149	java/lang/Long:longValue	()J
    //   62: getstatic 359	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   65: invokevirtual 365	java/util/concurrent/Semaphore:tryAcquire	(JLjava/util/concurrent/TimeUnit;)Z
    //   68: pop
    //   69: aload_0
    //   70: getfield 26	com/google/android/finsky/setup/PlaySetupService:mEarlyUpdateLock	Ljava/util/concurrent/Semaphore;
    //   73: invokevirtual 368	java/util/concurrent/Semaphore:release	()V
    //   76: aload_0
    //   77: monitorenter
    //   78: aload_0
    //   79: aconst_null
    //   80: putfield 64	com/google/android/finsky/setup/PlaySetupService:mLastEarlyUpdateResponse	Landroid/os/Bundle;
    //   83: aload_0
    //   84: monitorexit
    //   85: invokestatic 373	com/google/android/finsky/utils/DeviceConfigurationHelper:get	()Lcom/google/android/finsky/utils/DeviceConfigurationHelper;
    //   88: invokevirtual 377	com/google/android/finsky/utils/DeviceConfigurationHelper:getDeviceConfiguration	()Lcom/google/android/finsky/protos/DeviceConfiguration$DeviceConfigurationProto;
    //   91: astore 25
    //   93: aload 25
    //   95: astore 4
    //   97: invokestatic 173	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   100: invokevirtual 381	com/google/android/finsky/FinskyApp:getDfeApiNonAuthenticated	()Lcom/google/android/finsky/api/DfeApi;
    //   103: astore 5
    //   105: invokestatic 387	com/android/volley/toolbox/RequestFuture:newFuture	()Lcom/android/volley/toolbox/RequestFuture;
    //   108: astore 6
    //   110: aload 5
    //   112: aload 4
    //   114: aload 6
    //   116: aload 6
    //   118: invokeinterface 393 4 0
    //   123: pop
    //   124: aload 6
    //   126: invokevirtual 394	com/android/volley/toolbox/RequestFuture:get	()Ljava/lang/Object;
    //   129: checkcast 396	com/google/android/finsky/protos/EarlyUpdateResponse
    //   132: astore 12
    //   134: iconst_1
    //   135: anewarray 56	java/lang/Object
    //   138: astore 13
    //   140: aload 13
    //   142: iconst_0
    //   143: aload 12
    //   145: getfield 400	com/google/android/finsky/protos/EarlyUpdateResponse:earlyDocumentInfo	[Lcom/google/android/finsky/protos/EarlyDocumentInfo;
    //   148: arraylength
    //   149: invokestatic 405	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   152: aastore
    //   153: ldc_w 407
    //   156: aload 13
    //   158: invokestatic 205	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   161: iconst_0
    //   162: istore 14
    //   164: aconst_null
    //   165: astore 15
    //   167: invokestatic 173	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   170: invokevirtual 411	com/google/android/finsky/FinskyApp:getPackageManager	()Landroid/content/pm/PackageManager;
    //   173: astore 16
    //   175: aload 12
    //   177: getfield 400	com/google/android/finsky/protos/EarlyUpdateResponse:earlyDocumentInfo	[Lcom/google/android/finsky/protos/EarlyDocumentInfo;
    //   180: astore 17
    //   182: aload 17
    //   184: arraylength
    //   185: istore 18
    //   187: iconst_0
    //   188: istore 19
    //   190: iload 19
    //   192: iload 18
    //   194: if_icmpge +218 -> 412
    //   197: aload 17
    //   199: iload 19
    //   201: aaload
    //   202: astore 21
    //   204: aload 21
    //   206: getfield 417	com/google/android/finsky/protos/EarlyDocumentInfo:docid	Lcom/google/android/finsky/protos/Common$Docid;
    //   209: getfield 422	com/google/android/finsky/protos/Common$Docid:backendDocid	Ljava/lang/String;
    //   212: astore 22
    //   214: getstatic 426	com/google/android/finsky/utils/FinskyPreferences:earlyUpdateSkipPackage	Lcom/google/android/finsky/config/PreferenceFile$PrefixSharedPreference;
    //   217: aload 22
    //   219: invokevirtual 431	com/google/android/finsky/config/PreferenceFile$PrefixSharedPreference:get	(Ljava/lang/String;)Lcom/google/android/finsky/config/PreferenceFile$SharedPreference;
    //   222: invokevirtual 432	com/google/android/finsky/config/PreferenceFile$SharedPreference:get	()Ljava/lang/Object;
    //   225: checkcast 48	java/lang/Boolean
    //   228: invokevirtual 52	java/lang/Boolean:booleanValue	()Z
    //   231: ifne +88 -> 319
    //   234: aload 16
    //   236: aload 22
    //   238: iconst_0
    //   239: invokevirtual 438	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   242: getfield 443	android/content/pm/PackageInfo:versionCode	I
    //   245: istore 24
    //   247: iload 24
    //   249: aload 21
    //   251: getfield 444	com/google/android/finsky/protos/EarlyDocumentInfo:versionCode	I
    //   254: if_icmpge +65 -> 319
    //   257: iinc 14 1
    //   260: aload 15
    //   262: ifnonnull +57 -> 319
    //   265: new 70	android/os/Bundle
    //   268: dup
    //   269: invokespecial 193	android/os/Bundle:<init>	()V
    //   272: astore 15
    //   274: aload 15
    //   276: ldc 68
    //   278: aload 22
    //   280: invokevirtual 448	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   283: aload 15
    //   285: ldc 76
    //   287: aload 21
    //   289: getfield 444	com/google/android/finsky/protos/EarlyDocumentInfo:versionCode	I
    //   292: invokevirtual 452	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   295: aload 15
    //   297: ldc 82
    //   299: aload 21
    //   301: getfield 454	com/google/android/finsky/protos/EarlyDocumentInfo:title	Ljava/lang/String;
    //   304: invokevirtual 448	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   307: aload 15
    //   309: ldc 84
    //   311: aload 21
    //   313: getfield 456	com/google/android/finsky/protos/EarlyDocumentInfo:critical	Z
    //   316: invokevirtual 460	android/os/Bundle:putBoolean	(Ljava/lang/String;Z)V
    //   319: iinc 19 1
    //   322: goto -132 -> 190
    //   325: astore_1
    //   326: ldc_w 462
    //   329: iconst_0
    //   330: anewarray 56	java/lang/Object
    //   333: invokestatic 62	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   336: goto -260 -> 76
    //   339: astore_2
    //   340: aload_0
    //   341: monitorexit
    //   342: aload_2
    //   343: athrow
    //   344: astore_3
    //   345: aload_3
    //   346: ldc_w 464
    //   349: iconst_0
    //   350: anewarray 56	java/lang/Object
    //   353: invokestatic 339	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   356: aconst_null
    //   357: astore 4
    //   359: goto -262 -> 97
    //   362: astore 11
    //   364: aload 11
    //   366: ldc_w 466
    //   369: iconst_0
    //   370: anewarray 56	java/lang/Object
    //   373: invokestatic 468	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   376: aconst_null
    //   377: areturn
    //   378: astore 8
    //   380: aload 8
    //   382: invokevirtual 472	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   385: astore 9
    //   387: iconst_1
    //   388: anewarray 56	java/lang/Object
    //   391: astore 10
    //   393: aload 10
    //   395: iconst_0
    //   396: aload 9
    //   398: invokevirtual 478	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   401: aastore
    //   402: ldc_w 480
    //   405: aload 10
    //   407: invokestatic 168	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   410: aconst_null
    //   411: areturn
    //   412: aload 15
    //   414: ifnull +13 -> 427
    //   417: aload 15
    //   419: ldc_w 482
    //   422: iload 14
    //   424: invokevirtual 452	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   427: aload_0
    //   428: monitorenter
    //   429: aload_0
    //   430: aload 15
    //   432: putfield 64	com/google/android/finsky/setup/PlaySetupService:mLastEarlyUpdateResponse	Landroid/os/Bundle;
    //   435: aload_0
    //   436: monitorexit
    //   437: aload 15
    //   439: areturn
    //   440: astore 20
    //   442: aload_0
    //   443: monitorexit
    //   444: aload 20
    //   446: athrow
    //   447: astore 23
    //   449: iconst_0
    //   450: istore 24
    //   452: goto -205 -> 247
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	455	0	this	PlaySetupService
    //   325	1	1	localInterruptedException1	InterruptedException
    //   339	4	2	localObject1	Object
    //   344	2	3	localException	java.lang.Exception
    //   95	263	4	localDeviceConfigurationProto1	com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto
    //   103	8	5	localDfeApi	DfeApi
    //   108	17	6	localRequestFuture	RequestFuture
    //   378	3	8	localExecutionException	ExecutionException
    //   385	12	9	localThrowable	Throwable
    //   391	15	10	arrayOfObject1	Object[]
    //   362	3	11	localInterruptedException2	InterruptedException
    //   132	44	12	localEarlyUpdateResponse	com.google.android.finsky.protos.EarlyUpdateResponse
    //   138	19	13	arrayOfObject2	Object[]
    //   162	261	14	i	int
    //   165	273	15	localBundle	Bundle
    //   173	62	16	localPackageManager	android.content.pm.PackageManager
    //   180	18	17	arrayOfEarlyDocumentInfo	com.google.android.finsky.protos.EarlyDocumentInfo[]
    //   185	10	18	j	int
    //   188	132	19	k	int
    //   440	5	20	localObject2	Object
    //   202	110	21	localEarlyDocumentInfo	com.google.android.finsky.protos.EarlyDocumentInfo
    //   212	67	22	str	String
    //   447	1	23	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   245	206	24	m	int
    //   91	3	25	localDeviceConfigurationProto2	com.google.android.finsky.protos.DeviceConfiguration.DeviceConfigurationProto
    // Exception table:
    //   from	to	target	type
    //   46	76	325	java/lang/InterruptedException
    //   78	85	339	finally
    //   340	342	339	finally
    //   85	93	344	java/lang/Exception
    //   124	134	362	java/lang/InterruptedException
    //   124	134	378	java/util/concurrent/ExecutionException
    //   429	437	440	finally
    //   442	444	440	finally
    //   234	247	447	android/content/pm/PackageManager$NameNotFoundException
  }
  
  private static Restore.BackupDeviceInfo[] getBackupDevices(DfeApi paramDfeApi)
  {
    RequestFuture localRequestFuture = RequestFuture.newFuture();
    long l = SystemClock.elapsedRealtime();
    paramDfeApi.getBackupDeviceChoices(localRequestFuture, localRequestFuture);
    Throwable localThrowable;
    try
    {
      Restore.BackupDeviceInfo[] arrayOfBackupDeviceInfo = ((Restore.GetBackupDeviceChoicesResponse)localRequestFuture.get()).backupDeviceInfo;
      logBackgroundEvent(-1, null, paramDfeApi, l);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(arrayOfBackupDeviceInfo.length);
      FinskyLog.d("getBackupDeviceChoices returned with %d devices", arrayOfObject2);
      return arrayOfBackupDeviceInfo;
    }
    catch (InterruptedException localInterruptedException)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localInterruptedException.getMessage();
      FinskyLog.wtf("Unable to fetch backup devices, InterruptedException: %s", arrayOfObject1);
      logBackgroundEvent(1, localInterruptedException, paramDfeApi, l);
      return null;
    }
    catch (ExecutionException localExecutionException)
    {
      localThrowable = localExecutionException.getCause();
      if (!(localThrowable instanceof DisplayMessageError)) {}
    }
    for (String str = ((DisplayMessageError)localThrowable).mDisplayErrorHtml;; str = null)
    {
      FinskyLog.e("Unable to fetch backup devices: %s (%s)", new Object[] { localThrowable, str });
      logBackgroundEvent(1, localThrowable, paramDfeApi, l);
      return null;
    }
  }
  
  private static void logBackgroundEvent(int paramInt, Throwable paramThrowable, DfeApi paramDfeApi, long paramLong)
  {
    long l = SystemClock.elapsedRealtime() - paramLong;
    PlayStore.PlayStoreBackgroundActionEvent localPlayStoreBackgroundActionEvent = FinskyEventLog.obtainPlayStoreBackgroundActionEvent();
    localPlayStoreBackgroundActionEvent.type = 125;
    localPlayStoreBackgroundActionEvent.hasType = true;
    if (paramInt != -1)
    {
      localPlayStoreBackgroundActionEvent.errorCode = paramInt;
      localPlayStoreBackgroundActionEvent.hasErrorCode = true;
    }
    if (paramThrowable != null)
    {
      localPlayStoreBackgroundActionEvent.exceptionType = paramThrowable.getClass().getSimpleName();
      localPlayStoreBackgroundActionEvent.hasExceptionType = true;
    }
    if (paramLong != 0L)
    {
      localPlayStoreBackgroundActionEvent.clientLatencyMs = l;
      localPlayStoreBackgroundActionEvent.hasClientLatencyMs = true;
    }
    FinskyApp.get().getEventLogger(paramDfeApi.getAccountName()).sendBackgroundEventToSinks(localPlayStoreBackgroundActionEvent);
  }
  
  private void startDeferredInstalls()
  {
    new Handler(getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        FinskyApp.get().mInstaller.startDeferredInstalls();
      }
    });
  }
  
  private void stopServiceIfDone()
  {
    
    if ((this.mStartupRefCount == 0) && (this.mEarlyUpdatePackage == null))
    {
      if (this.mListenerAdded)
      {
        FinskyApp.get().mInstaller.removeListener(this);
        this.mListenerAdded = false;
      }
      stopSelf(this.mServiceStartId);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    new IPlaySetupService.Stub()
    {
      public final boolean cancelEarlyUpdate()
      {
        return PlaySetupService.this.doCancelEarlyUpdate();
      }
      
      public final Bundle getEarlyUpdate()
      {
        return PlaySetupService.this.doGetEarlyUpdate();
      }
      
      public final Bundle getFinalHoldFlow()
      {
        return PlaySetupService.access$500(PlaySetupService.this);
      }
      
      public final Bundle getRestoreFlow(String paramAnonymousString)
      {
        return PlaySetupService.access$300(PlaySetupService.this, paramAnonymousString);
      }
      
      public final void startDownloads()
      {
        PlaySetupService.access$600(PlaySetupService.this);
      }
      
      public final void startEarlyUpdate()
      {
        PlaySetupService.access$100(PlaySetupService.this);
      }
      
      public final void startVpa() {}
    };
  }
  
  public void onCreate()
  {
    this.mEarlyUpdateLock = new Semaphore(1);
  }
  
  public void onDestroy()
  {
    if (this.mListenerAdded)
    {
      FinskyApp.get().mInstaller.removeListener(this);
      this.mListenerAdded = false;
    }
    this.mEarlyUpdateLock = null;
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    if ((this.mEarlyUpdatePackage == null) || (!this.mEarlyUpdatePackage.equals(paramString))) {
      return;
    }
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = paramString;
    arrayOfObject1[1] = Integer.valueOf(paramInt1);
    FinskyLog.d("EarlyUpdate %s: %s", arrayOfObject1);
    int i = 0;
    int j = 0;
    switch (paramInt1)
    {
    }
    for (;;)
    {
      if (i != 0) {
        FinskyPreferences.earlyUpdateSkipPackage.get(paramString).put(Boolean.valueOf(true));
      }
      if (j == 0) {
        break;
      }
      this.mEarlyUpdatePackage = null;
      this.mEarlyUpdateLock.release();
      stopServiceIfDone();
      return;
      boolean bool2 = this.mEarlyUpdateLock.tryAcquire();
      i = 0;
      j = 0;
      if (!bool2)
      {
        FinskyLog.wtf("Couldn't acquire mutex for pending %s", new Object[] { paramString });
        i = 0;
        j = 0;
        continue;
        j = 1;
        i = 0;
        continue;
        try
        {
          boolean bool1 = this.mEarlyUpdatePackageIsCritical;
          i = 0;
          if (!bool1) {
            i = 1;
          }
          j = 1;
          continue;
        }
        finally {}
        i = 1;
        j = 1;
        continue;
        j = 1;
        i = 0;
        continue;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = paramString;
        arrayOfObject2[1] = Integer.valueOf(paramInt1);
        FinskyLog.wtf("EarlyUpdate %s: unexpected %d", arrayOfObject2);
        i = 0;
        j = 0;
      }
    }
  }
  
  public int onStartCommand(final Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.mServiceStartId = paramInt2;
    this.mStartupRefCount = (1 + this.mStartupRefCount);
    FinskyApp.get().mAppStates.load(new Runnable()
    {
      public final void run()
      {
        PlaySetupService.access$710(PlaySetupService.this);
        if (!PlaySetupService.access$800(PlaySetupService.this, paramIntent)) {
          PlaySetupService.this.stopServiceIfDone();
        }
      }
    });
    return 3;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.PlaySetupService
 * JD-Core Version:    0.7.0.1
 */