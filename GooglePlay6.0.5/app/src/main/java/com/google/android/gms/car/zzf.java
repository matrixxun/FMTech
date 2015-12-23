package com.google.android.gms.car;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.zza;
import com.google.android.gms.common.internal.zzj;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class zzf
  extends zzj<zzx>
{
  private zzx zzabR;
  private CarRetailModeManager zzadA;
  private CarNavigationStatusManager zzadB;
  private CarMediaManager zzadC;
  private CarCallManager zzadD;
  private final HashMap<String, Object> zzadE = new HashMap();
  private CarMessageManager zzadF;
  private CarBluetoothConnectionManager zzadG;
  private CarRadioManager zzadH;
  private final AtomicBoolean zzadI = new AtomicBoolean(false);
  private final List<Car.CarConnectionListener> zzadJ = new ArrayList();
  private final zzd zzadK = new zzd(this);
  private zzc zzadL;
  private volatile Car.CarActivityStartListener zzadM;
  private zzb zzadN;
  private final Object zzadx = new Object();
  private CarAudioManager zzady;
  private CarSensorManager zzadz;
  
  public zzf(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zzf paramzzf, Car.CarConnectionListener paramCarConnectionListener, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 13, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
    if (paramCarConnectionListener != null) {
      for (;;)
      {
        zzf localzzf;
        synchronized (this.zzadK)
        {
          if (this.zzadJ.contains(paramCarConnectionListener)) {
            break label240;
          }
          this.zzadJ.add(paramCarConnectionListener);
          zzd localzzd2 = this.zzadK;
          localzzf = (zzf)localzzd2.zzadQ.get();
          if (localzzf == null)
          {
            Log.i("CAR.CLIENT", "Null carClient in ICarConnectionListenerImpl.onCCLAL");
            return;
          }
          boolean bool = localzzf.zzlG();
          localObject2 = null;
          if (bool)
          {
            if (localzzd2.zzadR)
            {
              localObject2 = Collections.singletonList(paramCarConnectionListener);
              localzzd2.zzadR = true;
            }
          }
          else
          {
            if (localObject2 == null) {
              continue;
            }
            try
            {
              localzzd2.zza(localzzf, (List)localObject2, localzzf.zzlH());
            }
            catch (CarNotConnectedException localCarNotConnectedException)
            {
              localzzd2.zzadR = false;
            }
          }
        }
        Object localObject2 = new ArrayList(localzzf.zzadJ);
        continue;
        label240:
        if (CarLog.isLoggable("CAR.CLIENT", 3)) {
          Log.d("CAR.CLIENT", "registerCarConnectionListener(): " + paramCarConnectionListener + " already registered.");
        }
      }
    }
  }
  
  private void zzb(RemoteException paramRemoteException)
  {
    if (CarLog.isLoggable("CAR.CLIENT", 4)) {
      Log.i("CAR.CLIENT", "Remote exception from car service:" + paramRemoteException.getMessage());
    }
    if (this.zzadI.getAndSet(true)) {
      if (CarLog.isLoggable("CAR.CLIENT", 3)) {
        Log.d("CAR.CLIENT", "Already handling a remote exception, ignoring");
      }
    }
    do
    {
      return;
      this.zzadK.onDisconnected();
    } while (!isConnected());
    disconnect();
  }
  
  /* Error */
  private void zzlZ()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: getfield 57	com/google/android/gms/car/zzf:zzadx	Ljava/lang/Object;
    //   6: astore_2
    //   7: aload_2
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 211	com/google/android/gms/car/zzf:zzady	Lcom/google/android/gms/car/CarAudioManager;
    //   13: ifnull +159 -> 172
    //   16: aload_0
    //   17: getfield 211	com/google/android/gms/car/zzf:zzady	Lcom/google/android/gms/car/CarAudioManager;
    //   20: astore 40
    //   22: ldc 213
    //   24: iconst_3
    //   25: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   28: ifeq +11 -> 39
    //   31: ldc 213
    //   33: ldc 215
    //   35: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   38: pop
    //   39: aload 40
    //   41: iconst_0
    //   42: putfield 220	com/google/android/gms/car/CarAudioManager:zzabZ	Z
    //   45: aload 40
    //   47: getfield 224	com/google/android/gms/car/CarAudioManager:zzacD	[Lcom/google/android/gms/car/CarAudioTrack;
    //   50: astore 41
    //   52: aload 41
    //   54: monitorenter
    //   55: iload_1
    //   56: aload 40
    //   58: getfield 224	com/google/android/gms/car/CarAudioManager:zzacD	[Lcom/google/android/gms/car/CarAudioTrack;
    //   61: arraylength
    //   62: if_icmpge +24 -> 86
    //   65: aload 40
    //   67: getfield 224	com/google/android/gms/car/CarAudioManager:zzacD	[Lcom/google/android/gms/car/CarAudioTrack;
    //   70: iload_1
    //   71: aaload
    //   72: ifnull +716 -> 788
    //   75: aload 40
    //   77: getfield 224	com/google/android/gms/car/CarAudioManager:zzacD	[Lcom/google/android/gms/car/CarAudioTrack;
    //   80: iload_1
    //   81: aconst_null
    //   82: aastore
    //   83: goto +705 -> 788
    //   86: aload 41
    //   88: monitorexit
    //   89: aload 40
    //   91: getfield 227	com/google/android/gms/car/CarAudioManager:zzacE	Ljava/lang/Object;
    //   94: astore 43
    //   96: aload 43
    //   98: monitorenter
    //   99: aload 40
    //   101: getfield 231	com/google/android/gms/car/CarAudioManager:zzacF	Ljava/util/LinkedList;
    //   104: invokevirtual 237	java/util/LinkedList:iterator	()Ljava/util/Iterator;
    //   107: astore 45
    //   109: aload 45
    //   111: invokeinterface 242 1 0
    //   116: ifeq +40 -> 156
    //   119: aload 45
    //   121: invokeinterface 245 1 0
    //   126: checkcast 247	com/google/android/gms/car/CarAudioRecord
    //   129: invokevirtual 250	com/google/android/gms/car/CarAudioRecord:zzv$1385ff	()V
    //   132: goto -23 -> 109
    //   135: astore 44
    //   137: aload 43
    //   139: monitorexit
    //   140: aload 44
    //   142: athrow
    //   143: astore_3
    //   144: aload_2
    //   145: monitorexit
    //   146: aload_3
    //   147: athrow
    //   148: astore 42
    //   150: aload 41
    //   152: monitorexit
    //   153: aload 42
    //   155: athrow
    //   156: aload 40
    //   158: getfield 231	com/google/android/gms/car/CarAudioManager:zzacF	Ljava/util/LinkedList;
    //   161: invokevirtual 253	java/util/LinkedList:clear	()V
    //   164: aload 43
    //   166: monitorexit
    //   167: aload_0
    //   168: aconst_null
    //   169: putfield 211	com/google/android/gms/car/zzf:zzady	Lcom/google/android/gms/car/CarAudioManager;
    //   172: aload_0
    //   173: getfield 255	com/google/android/gms/car/zzf:zzadz	Lcom/google/android/gms/car/CarSensorManager;
    //   176: ifnull +60 -> 236
    //   179: aload_0
    //   180: getfield 255	com/google/android/gms/car/zzf:zzadz	Lcom/google/android/gms/car/CarSensorManager;
    //   183: astore 36
    //   185: ldc_w 257
    //   188: iconst_3
    //   189: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   192: ifeq +12 -> 204
    //   195: ldc_w 257
    //   198: ldc 215
    //   200: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   203: pop
    //   204: aload 36
    //   206: getfield 262	com/google/android/gms/car/CarSensorManager:zzaeI	Ljava/util/HashMap;
    //   209: astore 37
    //   211: aload 37
    //   213: monitorenter
    //   214: aload 36
    //   216: getfield 262	com/google/android/gms/car/CarSensorManager:zzaeI	Ljava/util/HashMap;
    //   219: invokevirtual 263	java/util/HashMap:clear	()V
    //   222: aload 36
    //   224: aconst_null
    //   225: putfield 267	com/google/android/gms/car/CarSensorManager:zzaeH	Lcom/google/android/gms/car/CarSensorManager$zza;
    //   228: aload 37
    //   230: monitorexit
    //   231: aload_0
    //   232: aconst_null
    //   233: putfield 255	com/google/android/gms/car/zzf:zzadz	Lcom/google/android/gms/car/CarSensorManager;
    //   236: aload_0
    //   237: getfield 269	com/google/android/gms/car/zzf:zzadF	Lcom/google/android/gms/car/CarMessageManager;
    //   240: ifnull +54 -> 294
    //   243: aload_0
    //   244: getfield 269	com/google/android/gms/car/zzf:zzadF	Lcom/google/android/gms/car/CarMessageManager;
    //   247: astore 32
    //   249: ldc_w 271
    //   252: iconst_3
    //   253: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   256: ifeq +12 -> 268
    //   259: ldc_w 271
    //   262: ldc 215
    //   264: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   267: pop
    //   268: aload 32
    //   270: getfield 277	com/google/android/gms/car/CarMessageManager:zzaek	Lcom/google/android/gms/car/zzap;
    //   273: aload 32
    //   275: getfield 281	com/google/android/gms/car/CarMessageManager:zzael	Lcom/google/android/gms/car/CarMessageManager$zza;
    //   278: invokeinterface 286 2 0
    //   283: aload 32
    //   285: aconst_null
    //   286: putfield 290	com/google/android/gms/car/CarMessageManager:zzaem	Lcom/google/android/gms/car/CarMessageManager$CarMessageListener;
    //   289: aload_0
    //   290: aconst_null
    //   291: putfield 269	com/google/android/gms/car/zzf:zzadF	Lcom/google/android/gms/car/CarMessageManager;
    //   294: aload_0
    //   295: getfield 292	com/google/android/gms/car/zzf:zzadG	Lcom/google/android/gms/car/CarBluetoothConnectionManager;
    //   298: ifnull +79 -> 377
    //   301: aload_0
    //   302: getfield 292	com/google/android/gms/car/zzf:zzadG	Lcom/google/android/gms/car/CarBluetoothConnectionManager;
    //   305: astore 27
    //   307: ldc_w 294
    //   310: iconst_3
    //   311: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   314: ifeq +12 -> 326
    //   317: ldc_w 294
    //   320: ldc 215
    //   322: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   325: pop
    //   326: aload 27
    //   328: getfield 300	com/google/android/gms/car/CarBluetoothConnectionManager:zzacZ	Lcom/google/android/gms/car/CarBluetoothConnectionManager$zza;
    //   331: astore 28
    //   333: ldc_w 302
    //   336: iconst_3
    //   337: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   340: ifeq +13 -> 353
    //   343: ldc_w 302
    //   346: ldc_w 304
    //   349: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   352: pop
    //   353: aload 28
    //   355: getfield 310	com/google/android/gms/car/CarBluetoothConnectionManager$zza:mHandler	Landroid/os/Handler;
    //   358: aload 28
    //   360: getfield 310	com/google/android/gms/car/CarBluetoothConnectionManager$zza:mHandler	Landroid/os/Handler;
    //   363: bipush 7
    //   365: invokevirtual 316	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
    //   368: invokevirtual 320	android/os/Handler:sendMessageAtFrontOfQueue	(Landroid/os/Message;)Z
    //   371: pop
    //   372: aload_0
    //   373: aconst_null
    //   374: putfield 292	com/google/android/gms/car/zzf:zzadG	Lcom/google/android/gms/car/CarBluetoothConnectionManager;
    //   377: aload_0
    //   378: getfield 322	com/google/android/gms/car/zzf:zzadB	Lcom/google/android/gms/car/CarNavigationStatusManager;
    //   381: ifnull +55 -> 436
    //   384: aload_0
    //   385: getfield 322	com/google/android/gms/car/zzf:zzadB	Lcom/google/android/gms/car/CarNavigationStatusManager;
    //   388: astore 21
    //   390: ldc_w 257
    //   393: iconst_3
    //   394: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   397: ifeq +12 -> 409
    //   400: ldc_w 257
    //   403: ldc 215
    //   405: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   408: pop
    //   409: aload 21
    //   411: getfield 328	com/google/android/gms/car/CarNavigationStatusManager:zzaep	Lcom/google/android/gms/car/zzar;
    //   414: aload 21
    //   416: getfield 332	com/google/android/gms/car/CarNavigationStatusManager:zzaeq	Lcom/google/android/gms/car/CarNavigationStatusManager$zza;
    //   419: invokeinterface 337 2 0
    //   424: pop
    //   425: aload 21
    //   427: aconst_null
    //   428: putfield 341	com/google/android/gms/car/CarNavigationStatusManager:zzaer	Lcom/google/android/gms/car/CarNavigationStatusManager$CarNavigationStatusListener;
    //   431: aload_0
    //   432: aconst_null
    //   433: putfield 322	com/google/android/gms/car/zzf:zzadB	Lcom/google/android/gms/car/CarNavigationStatusManager;
    //   436: aload_0
    //   437: getfield 343	com/google/android/gms/car/zzf:zzadC	Lcom/google/android/gms/car/CarMediaManager;
    //   440: ifnull +71 -> 511
    //   443: aload_0
    //   444: getfield 343	com/google/android/gms/car/zzf:zzadC	Lcom/google/android/gms/car/CarMediaManager;
    //   447: astore 15
    //   449: ldc_w 345
    //   452: iconst_3
    //   453: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   456: ifeq +12 -> 468
    //   459: ldc_w 345
    //   462: ldc 215
    //   464: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   467: pop
    //   468: aload 15
    //   470: aconst_null
    //   471: putfield 351	com/google/android/gms/car/CarMediaManager:zzaeg	Lcom/google/android/gms/car/CarMediaManager$CarMediaListener;
    //   474: aload 15
    //   476: getfield 355	com/google/android/gms/car/CarMediaManager:zzaee	Lcom/google/android/gms/car/zzal;
    //   479: aload 15
    //   481: getfield 359	com/google/android/gms/car/CarMediaManager:zzaef	Lcom/google/android/gms/car/CarMediaManager$zza;
    //   484: invokeinterface 364 2 0
    //   489: pop
    //   490: aload 15
    //   492: getfield 368	com/google/android/gms/car/CarMediaManager:zzaeh	Lcom/google/android/gms/car/zzan;
    //   495: aload 15
    //   497: getfield 372	com/google/android/gms/car/CarMediaManager:zzaei	Lcom/google/android/gms/car/CarMediaManager$zzb;
    //   500: invokeinterface 377 2 0
    //   505: pop
    //   506: aload_0
    //   507: aconst_null
    //   508: putfield 343	com/google/android/gms/car/zzf:zzadC	Lcom/google/android/gms/car/CarMediaManager;
    //   511: aload_0
    //   512: getfield 379	com/google/android/gms/car/zzf:zzadD	Lcom/google/android/gms/car/CarCallManager;
    //   515: ifnull +64 -> 579
    //   518: aload_0
    //   519: getfield 379	com/google/android/gms/car/zzf:zzadD	Lcom/google/android/gms/car/CarCallManager;
    //   522: astore 7
    //   524: ldc_w 381
    //   527: ldc_w 383
    //   530: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   533: pop
    //   534: aload 7
    //   536: getfield 389	com/google/android/gms/car/CarCallManager:zzadg	Lcom/google/android/gms/car/zzat;
    //   539: ifnull +19 -> 558
    //   542: aload 7
    //   544: getfield 389	com/google/android/gms/car/CarCallManager:zzadg	Lcom/google/android/gms/car/zzat;
    //   547: aload 7
    //   549: getfield 393	com/google/android/gms/car/CarCallManager:zzadi	Lcom/google/android/gms/car/CarCallManager$zzb;
    //   552: invokeinterface 398 2 0
    //   557: pop
    //   558: aload 7
    //   560: getfield 402	com/google/android/gms/car/CarCallManager:zzadf	Lcom/google/android/gms/car/zzah;
    //   563: aload 7
    //   565: getfield 406	com/google/android/gms/car/CarCallManager:zzadh	Lcom/google/android/gms/car/CarCallManager$zza;
    //   568: invokeinterface 411 2 0
    //   573: pop
    //   574: aload_0
    //   575: aconst_null
    //   576: putfield 379	com/google/android/gms/car/zzf:zzadD	Lcom/google/android/gms/car/CarCallManager;
    //   579: aload_0
    //   580: getfield 413	com/google/android/gms/car/zzf:zzadH	Lcom/google/android/gms/car/CarRadioManager;
    //   583: ifnull +15 -> 598
    //   586: aload_0
    //   587: getfield 413	com/google/android/gms/car/zzf:zzadH	Lcom/google/android/gms/car/CarRadioManager;
    //   590: invokevirtual 418	com/google/android/gms/car/CarRadioManager:zzlz	()V
    //   593: aload_0
    //   594: aconst_null
    //   595: putfield 413	com/google/android/gms/car/zzf:zzadH	Lcom/google/android/gms/car/CarRadioManager;
    //   598: aload_0
    //   599: getfield 420	com/google/android/gms/car/zzf:zzadA	Lcom/google/android/gms/car/CarRetailModeManager;
    //   602: ifnull +63 -> 665
    //   605: aload_0
    //   606: getfield 420	com/google/android/gms/car/zzf:zzadA	Lcom/google/android/gms/car/CarRetailModeManager;
    //   609: astore 4
    //   611: ldc_w 422
    //   614: iconst_3
    //   615: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   618: ifeq +13 -> 631
    //   621: ldc_w 422
    //   624: ldc_w 424
    //   627: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   630: pop
    //   631: aload 4
    //   633: getfield 430	com/google/android/gms/car/CarRetailModeManager:zzaeC	Lcom/google/android/gms/car/CarRetailModeManager$zza;
    //   636: ifnull +24 -> 660
    //   639: aload 4
    //   641: getfield 434	com/google/android/gms/car/CarRetailModeManager:zzaeB	Lcom/google/android/gms/car/ICarRetailMode;
    //   644: aload 4
    //   646: getfield 430	com/google/android/gms/car/CarRetailModeManager:zzaeC	Lcom/google/android/gms/car/CarRetailModeManager$zza;
    //   649: invokeinterface 440 2 0
    //   654: aload 4
    //   656: aconst_null
    //   657: putfield 430	com/google/android/gms/car/CarRetailModeManager:zzaeC	Lcom/google/android/gms/car/CarRetailModeManager$zza;
    //   660: aload_0
    //   661: aconst_null
    //   662: putfield 420	com/google/android/gms/car/zzf:zzadA	Lcom/google/android/gms/car/CarRetailModeManager;
    //   665: aload_0
    //   666: getfield 62	com/google/android/gms/car/zzf:zzadE	Ljava/util/HashMap;
    //   669: invokevirtual 263	java/util/HashMap:clear	()V
    //   672: aload_2
    //   673: monitorexit
    //   674: return
    //   675: astore 38
    //   677: aload 37
    //   679: monitorexit
    //   680: aload 38
    //   682: athrow
    //   683: astore 23
    //   685: ldc_w 257
    //   688: iconst_4
    //   689: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   692: ifeq -267 -> 425
    //   695: ldc_w 257
    //   698: new 143	java/lang/StringBuilder
    //   701: dup
    //   702: ldc_w 442
    //   705: invokespecial 148	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   708: aload 23
    //   710: invokevirtual 185	android/os/RemoteException:getMessage	()Ljava/lang/String;
    //   713: invokevirtual 157	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   716: invokevirtual 161	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   719: invokestatic 110	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   722: pop
    //   723: goto -298 -> 425
    //   726: astore 9
    //   728: ldc_w 381
    //   731: ldc_w 444
    //   734: aload 9
    //   736: invokestatic 447	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   739: pop
    //   740: goto -182 -> 558
    //   743: astore 11
    //   745: ldc_w 381
    //   748: ldc_w 449
    //   751: invokestatic 110	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   754: pop
    //   755: goto -181 -> 574
    //   758: astore 5
    //   760: goto -100 -> 660
    //   763: astore 17
    //   765: goto -259 -> 506
    //   768: astore 16
    //   770: goto -280 -> 490
    //   773: astore 22
    //   775: goto -350 -> 425
    //   778: astore 34
    //   780: goto -497 -> 283
    //   783: astore 33
    //   785: goto -502 -> 283
    //   788: iinc 1 1
    //   791: goto -736 -> 55
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	794	0	this	zzf
    //   1	788	1	i	int
    //   6	667	2	localObject1	Object
    //   143	4	3	localObject2	Object
    //   609	46	4	localCarRetailModeManager	CarRetailModeManager
    //   758	1	5	localRemoteException1	RemoteException
    //   522	42	7	localCarCallManager	CarCallManager
    //   726	9	9	localRemoteException2	RemoteException
    //   743	1	11	localRemoteException3	RemoteException
    //   447	49	15	localCarMediaManager	CarMediaManager
    //   768	1	16	localException1	java.lang.Exception
    //   763	1	17	localException2	java.lang.Exception
    //   388	38	21	localCarNavigationStatusManager	CarNavigationStatusManager
    //   773	1	22	localIllegalStateException1	IllegalStateException
    //   683	26	23	localRemoteException4	RemoteException
    //   305	22	27	localCarBluetoothConnectionManager	CarBluetoothConnectionManager
    //   331	28	28	localzza	CarBluetoothConnectionManager.zza
    //   247	37	32	localCarMessageManager	CarMessageManager
    //   783	1	33	localIllegalStateException2	IllegalStateException
    //   778	1	34	localRemoteException5	RemoteException
    //   183	40	36	localCarSensorManager	CarSensorManager
    //   209	469	37	localHashMap	HashMap
    //   675	6	38	localObject3	Object
    //   20	137	40	localCarAudioManager	CarAudioManager
    //   50	101	41	arrayOfCarAudioTrack	CarAudioTrack[]
    //   148	6	42	localObject4	Object
    //   94	71	43	localObject5	Object
    //   135	6	44	localObject6	Object
    //   107	13	45	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   99	109	135	finally
    //   109	132	135	finally
    //   137	140	135	finally
    //   156	167	135	finally
    //   9	39	143	finally
    //   39	55	143	finally
    //   89	99	143	finally
    //   140	143	143	finally
    //   144	146	143	finally
    //   153	156	143	finally
    //   167	172	143	finally
    //   172	204	143	finally
    //   204	214	143	finally
    //   231	236	143	finally
    //   236	268	143	finally
    //   268	283	143	finally
    //   283	294	143	finally
    //   294	326	143	finally
    //   326	353	143	finally
    //   353	377	143	finally
    //   377	409	143	finally
    //   409	425	143	finally
    //   425	436	143	finally
    //   436	468	143	finally
    //   468	474	143	finally
    //   474	490	143	finally
    //   490	506	143	finally
    //   506	511	143	finally
    //   511	534	143	finally
    //   534	558	143	finally
    //   558	574	143	finally
    //   574	579	143	finally
    //   579	598	143	finally
    //   598	631	143	finally
    //   631	660	143	finally
    //   660	665	143	finally
    //   665	674	143	finally
    //   680	683	143	finally
    //   685	723	143	finally
    //   728	740	143	finally
    //   745	755	143	finally
    //   55	83	148	finally
    //   86	89	148	finally
    //   150	153	148	finally
    //   214	231	675	finally
    //   677	680	675	finally
    //   409	425	683	android/os/RemoteException
    //   534	558	726	android/os/RemoteException
    //   558	574	743	android/os/RemoteException
    //   631	660	758	android/os/RemoteException
    //   490	506	763	java/lang/Exception
    //   474	490	768	java/lang/Exception
    //   409	425	773	java/lang/IllegalStateException
    //   268	283	778	android/os/RemoteException
    //   268	283	783	java/lang/IllegalStateException
  }
  
  /* Error */
  private void zzma()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   6: ifnonnull +45 -> 51
    //   9: aload_0
    //   10: new 454	com/google/android/gms/car/zzf$zzb
    //   13: dup
    //   14: aload_0
    //   15: invokespecial 455	com/google/android/gms/car/zzf$zzb:<init>	(Lcom/google/android/gms/car/zzf;)V
    //   18: putfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   21: aload_0
    //   22: aload_0
    //   23: invokevirtual 459	com/google/android/gms/car/zzf:zzqn	()Landroid/os/IInterface;
    //   26: checkcast 461	com/google/android/gms/car/zzx
    //   29: putfield 463	com/google/android/gms/car/zzf:zzabR	Lcom/google/android/gms/car/zzx;
    //   32: aload_0
    //   33: getfield 463	com/google/android/gms/car/zzf:zzabR	Lcom/google/android/gms/car/zzx;
    //   36: invokeinterface 467 1 0
    //   41: aload_0
    //   42: getfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   45: iconst_0
    //   46: invokeinterface 473 3 0
    //   51: aload_0
    //   52: monitorexit
    //   53: return
    //   54: astore_2
    //   55: ldc 102
    //   57: ldc_w 475
    //   60: invokestatic 169	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   63: pop
    //   64: aload_0
    //   65: aconst_null
    //   66: putfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   69: aload_0
    //   70: aconst_null
    //   71: putfield 463	com/google/android/gms/car/zzf:zzabR	Lcom/google/android/gms/car/zzx;
    //   74: goto -23 -> 51
    //   77: astore_1
    //   78: aload_0
    //   79: monitorexit
    //   80: aload_1
    //   81: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	zzf
    //   77	4	1	localObject	Object
    //   54	1	2	localRemoteException	RemoteException
    // Exception table:
    //   from	to	target	type
    //   21	51	54	android/os/RemoteException
    //   2	21	77	finally
    //   21	51	77	finally
    //   55	74	77	finally
  }
  
  /* Error */
  private void zzmb()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   6: ifnull +42 -> 48
    //   9: aload_0
    //   10: getfield 463	com/google/android/gms/car/zzf:zzabR	Lcom/google/android/gms/car/zzx;
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull +33 -> 48
    //   18: aload_0
    //   19: getfield 463	com/google/android/gms/car/zzf:zzabR	Lcom/google/android/gms/car/zzx;
    //   22: invokeinterface 467 1 0
    //   27: aload_0
    //   28: getfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   31: iconst_0
    //   32: invokeinterface 481 3 0
    //   37: pop
    //   38: aload_0
    //   39: aconst_null
    //   40: putfield 452	com/google/android/gms/car/zzf:zzadN	Lcom/google/android/gms/car/zzf$zzb;
    //   43: aload_0
    //   44: aconst_null
    //   45: putfield 463	com/google/android/gms/car/zzf:zzabR	Lcom/google/android/gms/car/zzx;
    //   48: aload_0
    //   49: monitorexit
    //   50: return
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    //   56: astore_3
    //   57: goto -19 -> 38
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	60	0	this	zzf
    //   51	4	1	localObject	Object
    //   13	2	2	localzzx	zzx
    //   56	1	3	localNoSuchElementException	java.util.NoSuchElementException
    // Exception table:
    //   from	to	target	type
    //   2	14	51	finally
    //   18	38	51	finally
    //   38	48	51	finally
    //   18	38	56	java/util/NoSuchElementException
  }
  
  /* Error */
  public final void disconnect()
  {
    // Byte code:
    //   0: ldc 102
    //   2: iconst_3
    //   3: invokestatic 141	com/google/android/gms/car/CarLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +12 -> 18
    //   9: ldc 102
    //   11: ldc_w 484
    //   14: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   17: pop
    //   18: aload_0
    //   19: invokespecial 175	com/google/android/gms/car/zzf:zzmb	()V
    //   22: aload_0
    //   23: invokespecial 201	com/google/android/gms/car/zzf:zzlZ	()V
    //   26: aload_0
    //   27: invokevirtual 459	com/google/android/gms/car/zzf:zzqn	()Landroid/os/IInterface;
    //   30: checkcast 461	com/google/android/gms/car/zzx
    //   33: astore_2
    //   34: aload_2
    //   35: ifnull +40 -> 75
    //   38: aload_2
    //   39: aload_0
    //   40: getfield 81	com/google/android/gms/car/zzf:zzadK	Lcom/google/android/gms/car/zzf$zzd;
    //   43: invokeinterface 487 2 0
    //   48: aload_0
    //   49: getfield 489	com/google/android/gms/car/zzf:zzadL	Lcom/google/android/gms/car/zzf$zzc;
    //   52: ifnull +23 -> 75
    //   55: aload_2
    //   56: aload_0
    //   57: getfield 489	com/google/android/gms/car/zzf:zzadL	Lcom/google/android/gms/car/zzf$zzc;
    //   60: invokeinterface 492 2 0
    //   65: aload_0
    //   66: aconst_null
    //   67: putfield 205	com/google/android/gms/car/zzf:zzadM	Lcom/google/android/gms/car/Car$CarActivityStartListener;
    //   70: aload_0
    //   71: aconst_null
    //   72: putfield 489	com/google/android/gms/car/zzf:zzadL	Lcom/google/android/gms/car/zzf$zzc;
    //   75: aload_0
    //   76: invokespecial 493	com/google/android/gms/common/internal/zzj:disconnect	()V
    //   79: return
    //   80: astore 5
    //   82: aconst_null
    //   83: astore_2
    //   84: goto -50 -> 34
    //   87: astore 4
    //   89: goto -14 -> 75
    //   92: astore_3
    //   93: goto -45 -> 48
    //   96: astore_1
    //   97: goto -15 -> 82
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	100	0	this	zzf
    //   96	1	1	localIllegalStateException	IllegalStateException
    //   33	51	2	localzzx	zzx
    //   92	1	3	localRemoteException1	RemoteException
    //   87	1	4	localRemoteException2	RemoteException
    //   80	1	5	localDeadObjectException	android.os.DeadObjectException
    // Exception table:
    //   from	to	target	type
    //   26	34	80	android/os/DeadObjectException
    //   55	75	87	android/os/RemoteException
    //   38	48	92	android/os/RemoteException
    //   26	34	96	java/lang/IllegalStateException
  }
  
  public final void onConnectionSuspended(int paramInt)
  {
    super.onConnectionSuspended(paramInt);
    if (CarLog.isLoggable("CAR.CLIENT", 3)) {
      Log.d("CAR.CLIENT", "onConnectionSuspended");
    }
  }
  
  public final void zza(GoogleApiClient.zza paramzza)
  {
    if (CarLog.isLoggable("CAR.CLIENT", 3)) {
      Log.d("CAR.CLIENT", "connect");
    }
    super.zza(paramzza);
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.car.service.START";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.car.ICar";
  }
  
  protected final Bundle zzjM()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("client_name", "car-1-0");
    return localBundle;
  }
  
  public final void zzlF()
  {
    super.zzlF();
    if (CarLog.isLoggable("CAR.CLIENT", 2)) {
      Log.v("CAR.CLIENT", "onConnectedLocked");
    }
    try
    {
      ((zzx)zzqn()).zza(this.zzadK);
      zzma();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (!CarLog.isLoggable("CAR.CLIENT", 5)) {}
      Log.w("CAR.CLIENT", "service disconnected while onConnectedLocked is called");
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb(localRemoteException);
    }
  }
  
  public final boolean zzlG()
  {
    if (!isConnected()) {
      return false;
    }
    try
    {
      boolean bool = ((zzx)zzqn()).zzlG();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      zzb(localRemoteException);
    }
    return false;
  }
  
  public final int zzlH()
    throws CarNotConnectedException
  {
    zzqm();
    try
    {
      int i = ((zzx)zzqn()).zzlH();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      zzb(localRemoteException);
      throw new CarNotConnectedException();
    }
    catch (IllegalStateException localIllegalStateException)
    {
      if (localIllegalStateException.getMessage().equals("CarNotConnected")) {
        throw new CarNotConnectedException();
      }
      throw localIllegalStateException;
    }
  }
  
  private static final class zzb
    implements IBinder.DeathRecipient
  {
    private final WeakReference<zzf> zzadQ;
    
    public zzb(zzf paramzzf)
    {
      this.zzadQ = new WeakReference(paramzzf);
    }
    
    public final void binderDied()
    {
      zzf localzzf = (zzf)this.zzadQ.get();
      if (localzzf != null) {
        zzf.zza(localzzf);
      }
    }
  }
  
  private static final class zzc
    extends zzy.zza
  {
    private final WeakReference<zzf> zzpl;
    
    public final void onActivityStarted(Intent paramIntent)
    {
      zzf localzzf = (zzf)this.zzpl.get();
      if (localzzf != null) {
        zzf.zzd(localzzf);
      }
    }
    
    public final void onNewActivityRequest(Intent paramIntent)
    {
      zzf localzzf = (zzf)this.zzpl.get();
      if (localzzf != null) {
        zzf.zzd(localzzf);
      }
    }
  }
  
  private static final class zzd
    extends zzak.zza
  {
    final WeakReference<zzf> zzadQ;
    volatile boolean zzadR = false;
    
    public zzd(zzf paramzzf)
    {
      this.zzadQ = new WeakReference(paramzzf);
    }
    
    public final void onConnected(int paramInt)
    {
      zzf localzzf = (zzf)this.zzadQ.get();
      if (localzzf == null) {
        Log.i("CAR.CLIENT", "Null carClient in ICarConnectionListenerImpl.onConnected");
      }
      for (;;)
      {
        return;
        ArrayList localArrayList = new ArrayList(zzf.zzb(localzzf).size());
        try
        {
          if (!this.zzadR)
          {
            localArrayList.addAll(zzf.zzb(localzzf));
            this.zzadR = true;
          }
          if (!localArrayList.isEmpty()) {
            zza(localzzf, localArrayList, paramInt);
          }
          if ((!localArrayList.isEmpty()) || (!CarLog.isLoggable("CAR.CLIENT", 4))) {
            continue;
          }
          Log.i("CAR.CLIENT", "Not notifying car connection [listeners=" + zzf.zzb(localzzf) + ", mConnectionNotified=" + this.zzadR + "]");
          return;
        }
        finally {}
      }
    }
    
    public final void onDisconnected()
    {
      final zzf localzzf = (zzf)this.zzadQ.get();
      if (localzzf == null)
      {
        Log.i("CAR.CLIENT", "Null carClient in ICarConnectionListenerImpl.onDisconnected");
        return;
      }
      final ArrayList localArrayList = new ArrayList(zzf.zzb(localzzf).size());
      try
      {
        if (this.zzadR)
        {
          localArrayList.addAll(zzf.zzb(localzzf));
          this.zzadR = false;
        }
        if (!localArrayList.isEmpty()) {
          zzbf.zza(localzzf.zzoD, new Runnable()
          {
            public final void run()
            {
              Iterator localIterator = localArrayList.iterator();
              while (localIterator.hasNext())
              {
                Car.CarConnectionListener localCarConnectionListener = (Car.CarConnectionListener)localIterator.next();
                if (localzzf.isConnected()) {
                  synchronized (zzf.zzd.this)
                  {
                    boolean bool = zzf.zzb(localzzf).contains(localCarConnectionListener);
                    if (bool) {
                      localCarConnectionListener.onDisconnected();
                    }
                  }
                }
              }
            }
          });
        }
        zzf.zzc(localzzf);
        return;
      }
      finally {}
    }
    
    final void zza(final zzf paramzzf, final List<Car.CarConnectionListener> paramList, final int paramInt)
    {
      zzbf.zza(paramzzf.zzoD, new Runnable()
      {
        public final void run()
        {
          Iterator localIterator = paramList.iterator();
          while (localIterator.hasNext())
          {
            Car.CarConnectionListener localCarConnectionListener = (Car.CarConnectionListener)localIterator.next();
            if (paramzzf.isConnected()) {
              synchronized (zzf.zzd.this)
              {
                boolean bool = zzf.zzb(paramzzf).contains(localCarConnectionListener);
                if (bool) {
                  localCarConnectionListener.onConnected$13462e();
                }
              }
            }
          }
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzf
 * JD-Core Version:    0.7.0.1
 */