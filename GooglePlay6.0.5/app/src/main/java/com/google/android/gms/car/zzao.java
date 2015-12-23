package com.google.android.gms.car;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzao
  extends IInterface
{
  public abstract void zzbE(int paramInt)
    throws RemoteException;
  
  public static abstract class zza
    extends Binder
    implements zzao
  {
    public static zzao zzbu(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.car.ICarMediaPlaybackStatusEventListener");
      if ((localIInterface != null) && ((localIInterface instanceof zzao))) {
        return (zzao)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.car.ICarMediaPlaybackStatusEventListener");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.gms.car.ICarMediaPlaybackStatusEventListener");
      zzbE(paramParcel1.readInt());
      return true;
    }
    
    private static final class zza
      implements zzao
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
      
      public final void zzbE(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.car.ICarMediaPlaybackStatusEventListener");
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
 * Qualified Name:     com.google.android.gms.car.zzao
 * JD-Core Version:    0.7.0.1
 */