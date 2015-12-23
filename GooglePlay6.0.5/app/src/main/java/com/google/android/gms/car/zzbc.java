package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzbc
  extends IInterface
{
  public abstract void onConnected()
    throws RemoteException;
  
  public abstract void onDisconnected()
    throws RemoteException;
  
  public abstract void zzbO(int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzbc
  {
    public static zzbc zzbJ(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarVendorExtensionClient");
      if ((localIInterface != null) && ((localIInterface instanceof zzbc))) {
        return (zzbc)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarVendorExtensionClient");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarVendorExtensionClient");
        zzbO(paramParcel1.readInt());
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarVendorExtensionClient");
        onConnected();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarVendorExtensionClient");
      onDisconnected();
      return true;
    }
    
    private static final class zza
      implements zzbc
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
      
      public final void onConnected()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarVendorExtensionClient");
          this.zzop.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onDisconnected()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarVendorExtensionClient");
          this.zzop.transact(3, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void zzbO(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarVendorExtensionClient");
          localParcel.writeInt(paramInt);
          this.zzop.transact(1, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.car.zzbc
 * JD-Core Version:    0.7.0.1
 */