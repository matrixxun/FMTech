package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;

public abstract interface zzou
  extends IInterface
{
  public abstract void zza(DataHolder paramDataHolder)
    throws RemoteException;
  
  public abstract void zzee(int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzou
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.deviceconnection.internal.IDeviceConnectionCallbacks");
    }
    
    public static zzou zzcC(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.deviceconnection.internal.IDeviceConnectionCallbacks");
      if ((localIInterface != null) && ((localIInterface instanceof zzou))) {
        return (zzou)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.deviceconnection.internal.IDeviceConnectionCallbacks");
        return true;
      case 1001: 
        paramParcel1.enforceInterface("com.google.android.gms.deviceconnection.internal.IDeviceConnectionCallbacks");
        if (paramParcel1.readInt() != 0) {}
        for (DataHolder localDataHolder = DataHolderCreator.createFromParcel(paramParcel1);; localDataHolder = null)
        {
          zza(localDataHolder);
          paramParcel2.writeNoException();
          return true;
        }
      }
      paramParcel1.enforceInterface("com.google.android.gms.deviceconnection.internal.IDeviceConnectionCallbacks");
      zzee(paramParcel1.readInt());
      paramParcel2.writeNoException();
      return true;
    }
    
    private static final class zza
      implements zzou
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
      public final void zza(DataHolder paramDataHolder)
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
        //   15: ifnull +43 -> 58
        //   18: aload_2
        //   19: iconst_1
        //   20: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   23: aload_1
        //   24: aload_2
        //   25: iconst_0
        //   26: invokevirtual 43	com/google/android/gms/common/data/DataHolder:writeToParcel	(Landroid/os/Parcel;I)V
        //   29: aload_0
        //   30: getfield 15	com/google/android/gms/internal/zzou$zza$zza:zzop	Landroid/os/IBinder;
        //   33: sipush 1001
        //   36: aload_2
        //   37: aload_3
        //   38: iconst_0
        //   39: invokeinterface 49 5 0
        //   44: pop
        //   45: aload_3
        //   46: invokevirtual 52	android/os/Parcel:readException	()V
        //   49: aload_3
        //   50: invokevirtual 55	android/os/Parcel:recycle	()V
        //   53: aload_2
        //   54: invokevirtual 55	android/os/Parcel:recycle	()V
        //   57: return
        //   58: aload_2
        //   59: iconst_0
        //   60: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   63: goto -34 -> 29
        //   66: astore 4
        //   68: aload_3
        //   69: invokevirtual 55	android/os/Parcel:recycle	()V
        //   72: aload_2
        //   73: invokevirtual 55	android/os/Parcel:recycle	()V
        //   76: aload 4
        //   78: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	79	0	this	zza
        //   0	79	1	paramDataHolder	DataHolder
        //   3	70	2	localParcel1	Parcel
        //   7	62	3	localParcel2	Parcel
        //   66	11	4	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   8	14	66	finally
        //   18	29	66	finally
        //   29	49	66	finally
        //   58	63	66	finally
      }
      
      public final void zzee(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.deviceconnection.internal.IDeviceConnectionCallbacks");
          localParcel1.writeInt(paramInt);
          this.zzop.transact(1002, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
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


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzou
 * JD-Core Version:    0.7.0.1
 */