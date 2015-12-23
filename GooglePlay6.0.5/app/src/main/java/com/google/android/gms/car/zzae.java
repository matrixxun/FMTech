package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzae
  extends IInterface
{
  public abstract void zzbo(int paramInt)
    throws RemoteException;
  
  public abstract void zzlD()
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzae
  {
    public static zzae zzbk(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarAudioTrackCallback");
      if ((localIInterface != null) && ((localIInterface instanceof zzae))) {
        return (zzae)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarAudioTrackCallback");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudioTrackCallback");
        zzlD();
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarAudioTrackCallback");
      zzbo(paramParcel1.readInt());
      return true;
    }
    
    private static final class zza
      implements zzae
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
      
      public final void zzbo(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarAudioTrackCallback");
          localParcel.writeInt(paramInt);
          this.zzop.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void zzlD()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarAudioTrackCallback");
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
 * Qualified Name:     com.google.android.gms.car.zzae
 * JD-Core Version:    0.7.0.1
 */