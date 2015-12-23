package com.google.android.finsky.installer;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IMultiUserCoordinatorServiceListener
  extends IInterface
{
  public abstract void packageAcquired(String paramString)
    throws RemoteException;
  
  public abstract void packageReleased(String paramString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IMultiUserCoordinatorServiceListener
  {
    public Stub()
    {
      attachInterface(this, "com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
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
        paramParcel2.writeString("com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
        packageAcquired(paramParcel1.readString());
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
      packageReleased(paramParcel1.readString());
      return true;
    }
    
    private static final class Proxy
      implements IMultiUserCoordinatorServiceListener
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public final IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public final void packageAcquired(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
          localParcel.writeString(paramString);
          this.mRemote.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public final void packageReleased(String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener");
          localParcel.writeString(paramString);
          this.mRemote.transact(2, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.finsky.installer.IMultiUserCoordinatorServiceListener
 * JD-Core Version:    0.7.0.1
 */