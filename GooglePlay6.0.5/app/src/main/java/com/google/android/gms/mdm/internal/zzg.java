package com.google.android.gms.mdm.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrs.zza.zza;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzrt.zza.zza;

public abstract interface zzg
  extends IInterface
{
  public abstract void zza(zzrs paramzzrs)
    throws RemoteException;
  
  public abstract void zza(zzrt paramzzrt)
    throws RemoteException;
  
  public abstract void zza(String paramString, Bundle paramBundle1, Bundle paramBundle2)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzg
  {
    public static zzg zzfr(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.mdm.internal.INetworkQualityService");
      if ((localIInterface != null) && ((localIInterface instanceof zzg))) {
        return (zzg)localIInterface;
      }
      return new zza(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.google.android.gms.mdm.internal.INetworkQualityService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.mdm.internal.INetworkQualityService");
        String str = paramParcel1.readString();
        Bundle localBundle1;
        if (paramParcel1.readInt() != 0)
        {
          localBundle1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0) {
            break label138;
          }
        }
        for (Bundle localBundle2 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; localBundle2 = null)
        {
          zza(str, localBundle1, localBundle2);
          paramParcel2.writeNoException();
          return true;
          localBundle1 = null;
          break;
        }
      case 2: 
        label138:
        paramParcel1.enforceInterface("com.google.android.gms.mdm.internal.INetworkQualityService");
        IBinder localIBinder2 = paramParcel1.readStrongBinder();
        Object localObject2 = null;
        if (localIBinder2 == null) {}
        for (;;)
        {
          zza((zzrs)localObject2);
          paramParcel2.writeNoException();
          return true;
          IInterface localIInterface2 = localIBinder2.queryLocalInterface("com.google.android.gms.herrevad.internal.IActiveNetworkQualityCallbacks");
          if ((localIInterface2 != null) && ((localIInterface2 instanceof zzrs))) {
            localObject2 = (zzrs)localIInterface2;
          } else {
            localObject2 = new zzrs.zza.zza(localIBinder2);
          }
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.mdm.internal.INetworkQualityService");
      IBinder localIBinder1 = paramParcel1.readStrongBinder();
      Object localObject1 = null;
      if (localIBinder1 == null) {}
      for (;;)
      {
        zza((zzrt)localObject1);
        paramParcel2.writeNoException();
        return true;
        IInterface localIInterface1 = localIBinder1.queryLocalInterface("com.google.android.gms.herrevad.internal.IConnectedNetworksQualityCallbacks");
        if ((localIInterface1 != null) && ((localIInterface1 instanceof zzrt))) {
          localObject1 = (zzrt)localIInterface1;
        } else {
          localObject1 = new zzrt.zza.zza(localIBinder1);
        }
      }
    }
    
    private static final class zza
      implements zzg
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
      
      /* Error */
      public final void zza(zzrs paramzzrs)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 37 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/mdm/internal/zzg$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_2
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 46 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 49	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 52	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 52	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 52	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 52	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramzzrs	zzrs
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        //   24	37	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	26	65	finally
        //   26	50	65	finally
      }
      
      /* Error */
      public final void zza(zzrt paramzzrt)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 56 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 40	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/google/android/gms/mdm/internal/zzg$zza$zza:zzop	Landroid/os/IBinder;
        //   36: iconst_3
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 46 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 49	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 52	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 52	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 52	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 52	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	zza
        //   0	78	1	paramzzrt	zzrt
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        //   24	37	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	26	65	finally
        //   26	50	65	finally
      }
      
      public final void zza(String paramString, Bundle paramBundle1, Bundle paramBundle2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.mdm.internal.INetworkQualityService");
            localParcel1.writeString(paramString);
            if (paramBundle1 != null)
            {
              localParcel1.writeInt(1);
              paramBundle1.writeToParcel(localParcel1, 0);
              if (paramBundle2 != null)
              {
                localParcel1.writeInt(1);
                paramBundle2.writeToParcel(localParcel1, 0);
                this.zzop.transact(1, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
            localParcel1.writeInt(0);
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.mdm.internal.zzg
 * JD-Core Version:    0.7.0.1
 */