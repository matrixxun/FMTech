package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzaq
  extends IInterface
{
  public abstract void onIntegerMessage(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
  
  public abstract void onOwnershipLost(int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzaq
  {
    public static zzaq zzbw(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarMessageCallback");
      if ((localIInterface != null) && ((localIInterface instanceof zzaq))) {
        return (zzaq)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarMessageCallback");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessageCallback");
        onIntegerMessage(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarMessageCallback");
      onOwnershipLost(paramParcel1.readInt());
      return true;
    }
    
    private static final class zza
      implements zzaq
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
      
      public final void onIntegerMessage(int paramInt1, int paramInt2, int paramInt3)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarMessageCallback");
          localParcel.writeInt(paramInt1);
          localParcel.writeInt(paramInt2);
          localParcel.writeInt(paramInt3);
          this.zzop.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onOwnershipLost(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarMessageCallback");
          localParcel.writeInt(paramInt);
          this.zzop.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.zzaq
 * JD-Core Version:    0.7.0.1
 */