package com.google.android.gms.wearable.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import java.util.List;

public abstract interface zzba
  extends IInterface
{
  public abstract void onConnectedNodes(List<NodeParcelable> paramList)
    throws RemoteException;
  
  public abstract void zza(AmsEntityUpdateParcelable paramAmsEntityUpdateParcelable)
    throws RemoteException;
  
  public abstract void zza(AncsNotificationParcelable paramAncsNotificationParcelable)
    throws RemoteException;
  
  public abstract void zza(CapabilityInfoParcelable paramCapabilityInfoParcelable)
    throws RemoteException;
  
  public abstract void zza(ChannelEventParcelable paramChannelEventParcelable)
    throws RemoteException;
  
  public abstract void zza(LargeAssetQueueStateChangeParcelable paramLargeAssetQueueStateChangeParcelable)
    throws RemoteException;
  
  public abstract void zza(LargeAssetSyncRequestPayload paramLargeAssetSyncRequestPayload, zzay paramzzay)
    throws RemoteException;
  
  public abstract void zza(MessageEventParcelable paramMessageEventParcelable)
    throws RemoteException;
  
  public abstract void zza(NodeParcelable paramNodeParcelable)
    throws RemoteException;
  
  public abstract void zza(zzax paramzzax, String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void zzar(DataHolder paramDataHolder)
    throws RemoteException;
  
  public abstract void zzb(NodeParcelable paramNodeParcelable)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzba
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.wearable.internal.IWearableListener");
    }
    
    public static zzba zzib(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableListener");
      if ((localIInterface != null) && ((localIInterface instanceof zzba))) {
        return (zzba)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.wearable.internal.IWearableListener");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (DataHolder localDataHolder = DataHolderCreator.createFromParcel(paramParcel1);; localDataHolder = null)
        {
          zzar(localDataHolder);
          return true;
        }
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (MessageEventParcelable localMessageEventParcelable = (MessageEventParcelable)MessageEventParcelable.CREATOR.createFromParcel(paramParcel1);; localMessageEventParcelable = null)
        {
          zza(localMessageEventParcelable);
          return true;
        }
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (NodeParcelable localNodeParcelable2 = (NodeParcelable)NodeParcelable.CREATOR.createFromParcel(paramParcel1);; localNodeParcelable2 = null)
        {
          zza(localNodeParcelable2);
          return true;
        }
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (NodeParcelable localNodeParcelable1 = (NodeParcelable)NodeParcelable.CREATOR.createFromParcel(paramParcel1);; localNodeParcelable1 = null)
        {
          zzb(localNodeParcelable1);
          return true;
        }
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        onConnectedNodes(paramParcel1.createTypedArrayList(NodeParcelable.CREATOR));
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (AncsNotificationParcelable localAncsNotificationParcelable = (AncsNotificationParcelable)AncsNotificationParcelable.CREATOR.createFromParcel(paramParcel1);; localAncsNotificationParcelable = null)
        {
          zza(localAncsNotificationParcelable);
          return true;
        }
      case 7: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (ChannelEventParcelable localChannelEventParcelable = (ChannelEventParcelable)ChannelEventParcelable.CREATOR.createFromParcel(paramParcel1);; localChannelEventParcelable = null)
        {
          zza(localChannelEventParcelable);
          return true;
        }
      case 8: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (CapabilityInfoParcelable localCapabilityInfoParcelable = (CapabilityInfoParcelable)CapabilityInfoParcelable.CREATOR.createFromParcel(paramParcel1);; localCapabilityInfoParcelable = null)
        {
          zza(localCapabilityInfoParcelable);
          return true;
        }
      case 9: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (AmsEntityUpdateParcelable localAmsEntityUpdateParcelable = (AmsEntityUpdateParcelable)AmsEntityUpdateParcelable.CREATOR.createFromParcel(paramParcel1);; localAmsEntityUpdateParcelable = null)
        {
          zza(localAmsEntityUpdateParcelable);
          return true;
        }
      case 10: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        if (paramParcel1.readInt() != 0) {}
        for (LargeAssetQueueStateChangeParcelable localLargeAssetQueueStateChangeParcelable = (LargeAssetQueueStateChangeParcelable)LargeAssetQueueStateChangeParcelable.CREATOR.createFromParcel(paramParcel1);; localLargeAssetQueueStateChangeParcelable = null)
        {
          zza(localLargeAssetQueueStateChangeParcelable);
          return true;
        }
      case 11: 
        paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
        IBinder localIBinder2 = paramParcel1.readStrongBinder();
        Object localObject2 = null;
        if (localIBinder2 == null) {}
        for (;;)
        {
          zza((zzax)localObject2, paramParcel1.readString(), paramParcel1.readInt());
          return true;
          IInterface localIInterface2 = localIBinder2.queryLocalInterface("com.google.android.gms.wearable.internal.IFileDescriptorReceiver");
          if ((localIInterface2 != null) && ((localIInterface2 instanceof zzax))) {
            localObject2 = (zzax)localIInterface2;
          } else {
            localObject2 = new zzax.zza.zza(localIBinder2);
          }
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
      LargeAssetSyncRequestPayload localLargeAssetSyncRequestPayload;
      IBinder localIBinder1;
      Object localObject1;
      if (paramParcel1.readInt() != 0)
      {
        localLargeAssetSyncRequestPayload = (LargeAssetSyncRequestPayload)LargeAssetSyncRequestPayload.CREATOR.createFromParcel(paramParcel1);
        localIBinder1 = paramParcel1.readStrongBinder();
        localObject1 = null;
        if (localIBinder1 != null) {
          break label659;
        }
      }
      for (;;)
      {
        zza(localLargeAssetSyncRequestPayload, (zzay)localObject1);
        return true;
        localLargeAssetSyncRequestPayload = null;
        break;
        label659:
        IInterface localIInterface1 = localIBinder1.queryLocalInterface("com.google.android.gms.wearable.internal.ILargeAssetSyncRequestResponder");
        if ((localIInterface1 != null) && ((localIInterface1 instanceof zzay))) {
          localObject1 = (zzay)localIInterface1;
        } else {
          localObject1 = new zzay.zza.zza(localIBinder1);
        }
      }
    }
    
    private static final class zza
      implements zzba
    {
      private IBinder zzop;
      
      zza(IBinder paramIBinder)
      {
        this.zzop = paramIBinder;
      }
      
      public final IBinder asBinder()
      {
        return this.zzop;
      }
      
      public final void onConnectedNodes(List<NodeParcelable> paramList)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
          localParcel.writeTypedList(paramList);
          this.zzop.transact(5, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public final void zza(AmsEntityUpdateParcelable paramAmsEntityUpdateParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 57	com/google/android/gms/wearable/internal/AmsEntityUpdateParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 9
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 42 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 45	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_3
        //   54: aload_2
        //   55: invokevirtual 45	android/os/Parcel:recycle	()V
        //   58: aload_3
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	zza
        //   0	60	1	paramAmsEntityUpdateParcelable	AmsEntityUpdateParcelable
        //   3	52	2	localParcel	Parcel
        //   53	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public final void zza(AncsNotificationParcelable paramAncsNotificationParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 61	com/google/android/gms/wearable/internal/AncsNotificationParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 6
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 42 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 45	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_3
        //   54: aload_2
        //   55: invokevirtual 45	android/os/Parcel:recycle	()V
        //   58: aload_3
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	zza
        //   0	60	1	paramAncsNotificationParcelable	AncsNotificationParcelable
        //   3	52	2	localParcel	Parcel
        //   53	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public final void zza(CapabilityInfoParcelable paramCapabilityInfoParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 65	com/google/android/gms/wearable/internal/CapabilityInfoParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 8
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 42 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 45	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_3
        //   54: aload_2
        //   55: invokevirtual 45	android/os/Parcel:recycle	()V
        //   58: aload_3
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	zza
        //   0	60	1	paramCapabilityInfoParcelable	CapabilityInfoParcelable
        //   3	52	2	localParcel	Parcel
        //   53	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public final void zza(ChannelEventParcelable paramChannelEventParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 69	com/google/android/gms/wearable/internal/ChannelEventParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 7
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 42 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 45	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_3
        //   54: aload_2
        //   55: invokevirtual 45	android/os/Parcel:recycle	()V
        //   58: aload_3
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	zza
        //   0	60	1	paramChannelEventParcelable	ChannelEventParcelable
        //   3	52	2	localParcel	Parcel
        //   53	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public final void zza(LargeAssetQueueStateChangeParcelable paramLargeAssetQueueStateChangeParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +34 -> 45
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 73	com/google/android/gms/wearable/internal/LargeAssetQueueStateChangeParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: bipush 10
        //   31: aload_2
        //   32: aconst_null
        //   33: iconst_1
        //   34: invokeinterface 42 5 0
        //   39: pop
        //   40: aload_2
        //   41: invokevirtual 45	android/os/Parcel:recycle	()V
        //   44: return
        //   45: aload_2
        //   46: iconst_0
        //   47: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   50: goto -25 -> 25
        //   53: astore_3
        //   54: aload_2
        //   55: invokevirtual 45	android/os/Parcel:recycle	()V
        //   58: aload_3
        //   59: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	60	0	this	zza
        //   0	60	1	paramLargeAssetQueueStateChangeParcelable	LargeAssetQueueStateChangeParcelable
        //   3	52	2	localParcel	Parcel
        //   53	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	53	finally
        //   14	25	53	finally
        //   25	40	53	finally
        //   45	50	53	finally
      }
      
      /* Error */
      public final void zza(LargeAssetSyncRequestPayload paramLargeAssetSyncRequestPayload, zzay paramzzay)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: aload_3
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +55 -> 66
        //   14: aload_3
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_3
        //   21: iconst_0
        //   22: invokevirtual 77	com/google/android/gms/wearable/internal/LargeAssetSyncRequestPayload:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aconst_null
        //   26: astore 5
        //   28: aload_2
        //   29: ifnull +11 -> 40
        //   32: aload_2
        //   33: invokeinterface 81 1 0
        //   38: astore 5
        //   40: aload_3
        //   41: aload 5
        //   43: invokevirtual 84	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   46: aload_0
        //   47: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   50: bipush 12
        //   52: aload_3
        //   53: aconst_null
        //   54: iconst_1
        //   55: invokeinterface 42 5 0
        //   60: pop
        //   61: aload_3
        //   62: invokevirtual 45	android/os/Parcel:recycle	()V
        //   65: return
        //   66: aload_3
        //   67: iconst_0
        //   68: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   71: goto -46 -> 25
        //   74: astore 4
        //   76: aload_3
        //   77: invokevirtual 45	android/os/Parcel:recycle	()V
        //   80: aload 4
        //   82: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	83	0	this	zza
        //   0	83	1	paramLargeAssetSyncRequestPayload	LargeAssetSyncRequestPayload
        //   0	83	2	paramzzay	zzay
        //   3	74	3	localParcel	Parcel
        //   74	7	4	localObject	Object
        //   26	16	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   4	10	74	finally
        //   14	25	74	finally
        //   32	40	74	finally
        //   40	61	74	finally
        //   66	71	74	finally
      }
      
      /* Error */
      public final void zza(MessageEventParcelable paramMessageEventParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 88	com/google/android/gms/wearable/internal/MessageEventParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_2
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 42 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 45	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 45	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramMessageEventParcelable	MessageEventParcelable
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
      
      /* Error */
      public final void zza(NodeParcelable paramNodeParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 92	com/google/android/gms/wearable/internal/NodeParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_3
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 42 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 45	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 45	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramNodeParcelable	NodeParcelable
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
      
      public final void zza(zzax paramzzax, String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.wearable.internal.IWearableListener");
          IBinder localIBinder = null;
          if (paramzzax != null) {
            localIBinder = paramzzax.asBinder();
          }
          localParcel.writeStrongBinder(localIBinder);
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          this.zzop.transact(11, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      /* Error */
      public final void zzar(DataHolder paramDataHolder)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 104	com/google/android/gms/common/data/DataHolder:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_1
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 42 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 45	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 45	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramDataHolder	DataHolder
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
      
      /* Error */
      public final void zzb(NodeParcelable paramNodeParcelable)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: aload_2
        //   5: ldc 29
        //   7: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   10: aload_1
        //   11: ifnull +33 -> 44
        //   14: aload_2
        //   15: iconst_1
        //   16: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokevirtual 92	com/google/android/gms/wearable/internal/NodeParcelable:writeToParcel	(Landroid/os/Parcel;I)V
        //   25: aload_0
        //   26: getfield 15	com/google/android/gms/wearable/internal/zzba$zza$zza:zzop	Landroid/os/IBinder;
        //   29: iconst_4
        //   30: aload_2
        //   31: aconst_null
        //   32: iconst_1
        //   33: invokeinterface 42 5 0
        //   38: pop
        //   39: aload_2
        //   40: invokevirtual 45	android/os/Parcel:recycle	()V
        //   43: return
        //   44: aload_2
        //   45: iconst_0
        //   46: invokevirtual 51	android/os/Parcel:writeInt	(I)V
        //   49: goto -24 -> 25
        //   52: astore_3
        //   53: aload_2
        //   54: invokevirtual 45	android/os/Parcel:recycle	()V
        //   57: aload_3
        //   58: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	zza
        //   0	59	1	paramNodeParcelable	NodeParcelable
        //   3	51	2	localParcel	Parcel
        //   52	6	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   4	10	52	finally
        //   14	25	52	finally
        //   25	39	52	finally
        //   44	49	52	finally
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzba
 * JD-Core Version:    0.7.0.1
 */