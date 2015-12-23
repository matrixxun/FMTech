package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ICarRetailModeListener
  extends IInterface
{
  public abstract void onShowcaseActivated()
    throws RemoteException;
  
  public abstract void onShowcaseDeactivated()
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements ICarRetailModeListener
  {
    public static ICarRetailModeListener zzbF(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarRetailModeListener");
      if ((localIInterface != null) && ((localIInterface instanceof ICarRetailModeListener))) {
        return (ICarRetailModeListener)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarRetailModeListener");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarRetailModeListener");
        onShowcaseActivated();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarRetailModeListener");
      onShowcaseDeactivated();
      return true;
    }
    
    private static final class zza
      implements ICarRetailModeListener
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
      
      public final void onShowcaseActivated()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRetailModeListener");
          this.zzop.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void onShowcaseDeactivated()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarRetailModeListener");
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
 * Qualified Name:     com.google.android.gms.car.ICarRetailModeListener
 * JD-Core Version:    0.7.0.1
 */