package com.google.android.finsky.services;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IMarketCatalogService
  extends IInterface
{
  public abstract boolean isBackendEnabled(String paramString, int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IMarketCatalogService
  {
    public Stub()
    {
      attachInterface(this, "com.google.android.finsky.services.IMarketCatalogService");
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
        paramParcel2.writeString("com.google.android.finsky.services.IMarketCatalogService");
        return true;
      }
      paramParcel1.enforceInterface("com.google.android.finsky.services.IMarketCatalogService");
      boolean bool = isBackendEnabled(paramParcel1.readString(), paramParcel1.readInt());
      paramParcel2.writeNoException();
      if (bool) {}
      for (int i = 1;; i = 0)
      {
        paramParcel2.writeInt(i);
        return true;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.IMarketCatalogService
 * JD-Core Version:    0.7.0.1
 */