package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzag
  extends IInterface
{
  public abstract void onCarDelayedPairing()
    throws RemoteException;
  
  public abstract void onDisabled()
    throws RemoteException;
  
  public abstract void onEnabled()
    throws RemoteException;
  
  public abstract void onHfpConnected()
    throws RemoteException;
  
  public abstract void onHfpDisconnected()
    throws RemoteException;
  
  public abstract void onPaired()
    throws RemoteException;
  
  public abstract void onUnpaired()
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzag
  {
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
        paramParcel2.writeString("com.google.android.gms.car.ICarBluetoothClient");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
        onEnabled();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
        onDisabled();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
        onCarDelayedPairing();
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
        onPaired();
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
        onUnpaired();
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
        onHfpConnected();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarBluetoothClient");
      onHfpDisconnected();
      return true;
    }
    
    private static final class zza
      implements zzag
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
      
      public final void onCarDelayedPairing()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(3, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onDisabled()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onEnabled()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onHfpConnected()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(6, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onHfpDisconnected()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(7, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onPaired()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(4, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onUnpaired()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarBluetoothClient");
          this.zzop.transact(5, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.car.zzag
 * JD-Core Version:    0.7.0.1
 */