package com.android.vending.details;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IDetailsService
  extends IInterface
{
  public abstract Bundle getAppDetails(String paramString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IDetailsService
  {
    public Stub()
    {
      attachInterface(this, "com.android.vending.details.IDetailsService");
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
        paramParcel2.writeString("com.android.vending.details.IDetailsService");
        return true;
      }
      paramParcel1.enforceInterface("com.android.vending.details.IDetailsService");
      Bundle localBundle = getAppDetails(paramParcel1.readString());
      paramParcel2.writeNoException();
      if (localBundle != null)
      {
        paramParcel2.writeInt(1);
        localBundle.writeToParcel(paramParcel2, 1);
        return true;
      }
      paramParcel2.writeInt(0);
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.details.IDetailsService
 * JD-Core Version:    0.7.0.1
 */