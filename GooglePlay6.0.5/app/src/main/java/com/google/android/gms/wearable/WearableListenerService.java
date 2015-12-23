package com.google.android.gms.wearable;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.wearable.internal.AmsEntityUpdateParcelable;
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.ChannelEventParcelable;
import com.google.android.gms.wearable.internal.LargeAssetQueueStateChangeParcelable;
import com.google.android.gms.wearable.internal.LargeAssetSyncRequestPayload;
import com.google.android.gms.wearable.internal.MessageEventParcelable;
import com.google.android.gms.wearable.internal.NodeParcelable;
import com.google.android.gms.wearable.internal.zzay;
import com.google.android.gms.wearable.internal.zzba.zza;
import com.google.android.gms.wearable.internal.zzbj;
import com.google.android.gms.wearable.internal.zzbt;
import java.util.List;

public abstract class WearableListenerService
  extends Service
  implements DataApi.DataListener
{
  private String mPackageName;
  private Handler zzbGH;
  private IBinder zzpt;
  private final Object zzpu = new Object();
  private boolean zzpv;
  
  public final IBinder onBind(Intent paramIntent)
  {
    if ("com.google.android.gms.wearable.BIND_LISTENER".equals(paramIntent.getAction())) {
      return this.zzpt;
    }
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    if (Log.isLoggable("WearableLS", 3)) {
      Log.d("WearableLS", "onCreate: " + getPackageName());
    }
    this.mPackageName = getPackageName();
    HandlerThread localHandlerThread = new HandlerThread("WearableListenerService");
    localHandlerThread.start();
    this.zzbGH = new Handler(localHandlerThread.getLooper());
    this.zzpt = new zza((byte)0);
  }
  
  public void onDataChanged(DataEventBuffer paramDataEventBuffer) {}
  
  public void onDestroy()
  {
    synchronized (this.zzpu)
    {
      this.zzpv = true;
      if (this.zzbGH == null) {
        throw new IllegalStateException("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()?");
      }
    }
    this.zzbGH.getLooper().quit();
    super.onDestroy();
  }
  
  private final class zza
    extends zzba.zza
  {
    private volatile int zzpr = -1;
    
    private zza() {}
    
    private boolean zza(Runnable paramRunnable, String paramString, Object paramObject)
    {
      if (!(WearableListenerService.this instanceof WearableListenerServiceFirstParty)) {
        return false;
      }
      return zzb(paramRunnable, paramString, paramObject);
    }
    
    private void zzaY()
      throws SecurityException
    {
      int i = Binder.getCallingUid();
      if (i == this.zzpr) {
        return;
      }
      if (GooglePlayServicesUtil.zze(WearableListenerService.this, i))
      {
        this.zzpr = i;
        return;
      }
      throw new SecurityException("Caller is not GooglePlayServices");
    }
    
    private boolean zzb(Runnable paramRunnable, String paramString, Object paramObject)
    {
      if (Log.isLoggable("WearableLS", 3))
      {
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = WearableListenerService.zzc(WearableListenerService.this);
        arrayOfObject[2] = paramObject;
        Log.d("WearableLS", String.format("%s: %s %s", arrayOfObject));
      }
      zzaY();
      synchronized (WearableListenerService.zza(WearableListenerService.this))
      {
        if (WearableListenerService.zzb(WearableListenerService.this)) {
          return false;
        }
        WearableListenerService.zzd(WearableListenerService.this).post(paramRunnable);
        return true;
      }
    }
    
    public final void onConnectedNodes(final List<NodeParcelable> paramList)
    {
      zzb(new Runnable()
      {
        public final void run() {}
      }, "onConnectedNodes", paramList);
    }
    
    public final void zza(final AmsEntityUpdateParcelable paramAmsEntityUpdateParcelable)
    {
      zza(new Runnable()
      {
        public final void run() {}
      }, "onEntityUpdate", paramAmsEntityUpdateParcelable);
    }
    
    public final void zza(final AncsNotificationParcelable paramAncsNotificationParcelable)
    {
      zza(new Runnable()
      {
        public final void run() {}
      }, "onNotificationReceived", paramAncsNotificationParcelable);
    }
    
    public final void zza(final CapabilityInfoParcelable paramCapabilityInfoParcelable)
    {
      zzb(new Runnable()
      {
        public final void run() {}
      }, "onConnectedCapabilityChanged", paramCapabilityInfoParcelable);
    }
    
    public final void zza(final ChannelEventParcelable paramChannelEventParcelable)
    {
      zzb(new Runnable()
      {
        public final void run()
        {
          paramChannelEventParcelable.zza$34052072();
        }
      }, "onChannelEvent", paramChannelEventParcelable);
    }
    
    public final void zza(final LargeAssetQueueStateChangeParcelable paramLargeAssetQueueStateChangeParcelable)
    {
      Runnable local2 = new Runnable()
      {
        public final void run()
        {
          paramLargeAssetQueueStateChangeParcelable.zzcgM.release();
        }
      };
      try
      {
        boolean bool = zza(local2, "onLargeAssetStateChanged", paramLargeAssetQueueStateChangeParcelable);
        if (!bool) {}
        return;
      }
      finally
      {
        paramLargeAssetQueueStateChangeParcelable.zzcgM.release();
      }
    }
    
    public final void zza(final LargeAssetSyncRequestPayload paramLargeAssetSyncRequestPayload, final zzay paramzzay)
    {
      zza(new Runnable()
      {
        public final void run()
        {
          zzbj localzzbj = new zzbj(paramLargeAssetSyncRequestPayload, paramzzay);
          WearableListenerServiceFirstParty.onLargeAssetRequest(localzzbj);
          for (;;)
          {
            try
            {
              zzx.zza(localzzbj.zzcgY, "Received onLargeAssetSyncRequest but didn't set a response.");
              if (localzzbj.zzcgZ != null) {
                localzzbj.zzcgX.zza(localzzbj.zzcgZ, localzzbj.zzcha);
              }
            }
            finally
            {
              if (localzzbj.zzcgZ != null) {
                IOUtils.closeQuietly(localzzbj.zzcgZ);
              }
            }
            try
            {
              if (localzzbj.zzcgZ != null) {
                IOUtils.closeQuietly(localzzbj.zzcgZ);
              }
              return;
            }
            catch (RemoteException localRemoteException)
            {
              Log.w("WearableLS", "Failed to respond to LargeAssetRequest", localRemoteException);
            }
            localzzbj.zzcgX.refuse(localzzbj.zzchb);
          }
        }
      }, "onLargeAssetSyncRequest", paramLargeAssetSyncRequestPayload);
    }
    
    public final void zza(final MessageEventParcelable paramMessageEventParcelable)
    {
      zzb(new Runnable()
      {
        public final void run() {}
      }, "onMessageReceived", paramMessageEventParcelable);
    }
    
    public final void zza(final NodeParcelable paramNodeParcelable)
    {
      zzb(new Runnable()
      {
        public final void run() {}
      }, "onPeerConnected", paramNodeParcelable);
    }
    
    /* Error */
    public final void zza(com.google.android.gms.wearable.internal.zzax paramzzax, String paramString, int paramInt)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 4
      //   3: aload_0
      //   4: getfield 12	com/google/android/gms/wearable/WearableListenerService$zza:zzceH	Lcom/google/android/gms/wearable/WearableListenerService;
      //   7: instanceof 24
      //   10: ifne +4 -> 14
      //   13: return
      //   14: ldc 49
      //   16: iconst_3
      //   17: invokestatic 55	android/util/Log:isLoggable	(Ljava/lang/String;I)Z
      //   20: ifeq +35 -> 55
      //   23: iconst_2
      //   24: anewarray 57	java/lang/Object
      //   27: astore 16
      //   29: aload 16
      //   31: iconst_0
      //   32: aload_2
      //   33: aastore
      //   34: aload 16
      //   36: iconst_1
      //   37: iload_3
      //   38: invokestatic 189	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   41: aastore
      //   42: ldc 49
      //   44: ldc 191
      //   46: aload 16
      //   48: invokestatic 71	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   51: invokestatic 75	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   54: pop
      //   55: aload_0
      //   56: invokespecial 77	com/google/android/gms/wearable/WearableListenerService$zza:zzaY	()V
      //   59: aload_0
      //   60: getfield 12	com/google/android/gms/wearable/WearableListenerService$zza:zzceH	Lcom/google/android/gms/wearable/WearableListenerService;
      //   63: invokestatic 80	com/google/android/gms/wearable/WearableListenerService:zza	(Lcom/google/android/gms/wearable/WearableListenerService;)Ljava/lang/Object;
      //   66: astore 5
      //   68: aload 5
      //   70: monitorenter
      //   71: aload_0
      //   72: getfield 12	com/google/android/gms/wearable/WearableListenerService$zza:zzceH	Lcom/google/android/gms/wearable/WearableListenerService;
      //   75: invokestatic 83	com/google/android/gms/wearable/WearableListenerService:zzb	(Lcom/google/android/gms/wearable/WearableListenerService;)Z
      //   78: ifeq +15 -> 93
      //   81: aload 5
      //   83: monitorexit
      //   84: return
      //   85: astore 6
      //   87: aload 5
      //   89: monitorexit
      //   90: aload 6
      //   92: athrow
      //   93: aload 5
      //   95: monitorexit
      //   96: new 193	java/io/File
      //   99: dup
      //   100: aload_2
      //   101: invokespecial 194	java/io/File:<init>	(Ljava/lang/String;)V
      //   104: invokevirtual 198	java/io/File:getParentFile	()Ljava/io/File;
      //   107: astore 10
      //   109: aload 10
      //   111: invokevirtual 202	java/io/File:exists	()Z
      //   114: istore 11
      //   116: aconst_null
      //   117: astore 4
      //   119: iload 11
      //   121: ifne +49 -> 170
      //   124: aload 10
      //   126: invokevirtual 205	java/io/File:mkdirs	()Z
      //   129: ifne +41 -> 170
      //   132: ldc 49
      //   134: new 207	java/lang/StringBuilder
      //   137: dup
      //   138: ldc 209
      //   140: invokespecial 210	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   143: aload 10
      //   145: invokevirtual 214	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   148: invokevirtual 218	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   151: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   154: invokestatic 224	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
      //   157: pop
      //   158: aload_1
      //   159: aconst_null
      //   160: invokeinterface 229 2 0
      //   165: aconst_null
      //   166: invokestatic 234	com/google/android/gms/common/util/IOUtils:closeQuietly	(Landroid/os/ParcelFileDescriptor;)V
      //   169: return
      //   170: new 193	java/io/File
      //   173: dup
      //   174: aload_2
      //   175: invokespecial 194	java/io/File:<init>	(Ljava/lang/String;)V
      //   178: ldc 235
      //   180: iload_3
      //   181: ior
      //   182: invokestatic 241	android/os/ParcelFileDescriptor:open	(Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;
      //   185: astore 14
      //   187: aload 14
      //   189: astore 4
      //   191: aload_1
      //   192: aload 4
      //   194: invokeinterface 229 2 0
      //   199: aload 4
      //   201: invokestatic 234	com/google/android/gms/common/util/IOUtils:closeQuietly	(Landroid/os/ParcelFileDescriptor;)V
      //   204: return
      //   205: astore 12
      //   207: ldc 49
      //   209: new 207	java/lang/StringBuilder
      //   212: dup
      //   213: ldc 243
      //   215: invokespecial 210	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   218: aload_2
      //   219: invokevirtual 218	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   222: invokevirtual 221	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   225: aload 12
      //   227: invokestatic 246	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   230: pop
      //   231: aload_1
      //   232: aconst_null
      //   233: invokeinterface 229 2 0
      //   238: aconst_null
      //   239: invokestatic 234	com/google/android/gms/common/util/IOUtils:closeQuietly	(Landroid/os/ParcelFileDescriptor;)V
      //   242: return
      //   243: astore 8
      //   245: ldc 49
      //   247: ldc 248
      //   249: aload 8
      //   251: invokestatic 246	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   254: pop
      //   255: aload 4
      //   257: invokestatic 234	com/google/android/gms/common/util/IOUtils:closeQuietly	(Landroid/os/ParcelFileDescriptor;)V
      //   260: return
      //   261: astore 7
      //   263: aload 4
      //   265: invokestatic 234	com/google/android/gms/common/util/IOUtils:closeQuietly	(Landroid/os/ParcelFileDescriptor;)V
      //   268: aload 7
      //   270: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	271	0	this	zza
      //   0	271	1	paramzzax	com.google.android.gms.wearable.internal.zzax
      //   0	271	2	paramString	String
      //   0	271	3	paramInt	int
      //   1	263	4	localObject1	Object
      //   66	28	5	localObject2	Object
      //   85	6	6	localObject3	Object
      //   261	8	7	localObject4	Object
      //   243	7	8	localRemoteException	RemoteException
      //   107	37	10	localFile	java.io.File
      //   114	6	11	bool	boolean
      //   205	21	12	localFileNotFoundException	java.io.FileNotFoundException
      //   185	3	14	localParcelFileDescriptor	android.os.ParcelFileDescriptor
      //   27	20	16	arrayOfObject	Object[]
      // Exception table:
      //   from	to	target	type
      //   71	84	85	finally
      //   87	90	85	finally
      //   93	96	85	finally
      //   170	187	205	java/io/FileNotFoundException
      //   96	116	243	android/os/RemoteException
      //   124	165	243	android/os/RemoteException
      //   170	187	243	android/os/RemoteException
      //   191	199	243	android/os/RemoteException
      //   207	238	243	android/os/RemoteException
      //   96	116	261	finally
      //   124	165	261	finally
      //   170	187	261	finally
      //   191	199	261	finally
      //   207	238	261	finally
      //   245	255	261	finally
    }
    
    public final void zzar(final DataHolder paramDataHolder)
    {
      Runnable local1 = new Runnable()
      {
        public final void run()
        {
          DataEventBuffer localDataEventBuffer = new DataEventBuffer(paramDataHolder);
          try
          {
            WearableListenerService.this.onDataChanged(localDataEventBuffer);
            return;
          }
          finally
          {
            localDataEventBuffer.release();
          }
        }
      };
      try
      {
        boolean bool = zzb(local1, "onDataItemChanged", paramDataHolder);
        if (!bool) {}
        return;
      }
      finally
      {
        paramDataHolder.close();
      }
    }
    
    public final void zzb(final NodeParcelable paramNodeParcelable)
    {
      zzb(new Runnable()
      {
        public final void run() {}
      }, "onPeerDisconnected", paramNodeParcelable);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.WearableListenerService
 * JD-Core Version:    0.7.0.1
 */