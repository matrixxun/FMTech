package com.android.vending.setup;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IPlaySetupService
  extends IInterface
{
  public abstract boolean cancelEarlyUpdate()
    throws RemoteException;
  
  public abstract Bundle getEarlyUpdate()
    throws RemoteException;
  
  public abstract Bundle getFinalHoldFlow()
    throws RemoteException;
  
  public abstract Bundle getRestoreFlow(String paramString)
    throws RemoteException;
  
  public abstract void startDownloads()
    throws RemoteException;
  
  public abstract void startEarlyUpdate()
    throws RemoteException;
  
  public abstract void startVpa()
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IPlaySetupService
  {
    public Stub()
    {
      attachInterface(this, "com.android.vending.setup.IPlaySetupService");
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
        paramParcel2.writeString("com.android.vending.setup.IPlaySetupService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
        Bundle localBundle3 = getEarlyUpdate();
        paramParcel2.writeNoException();
        if (localBundle3 != null)
        {
          paramParcel2.writeInt(1);
          localBundle3.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
        startEarlyUpdate();
        paramParcel2.writeNoException();
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
        boolean bool = cancelEarlyUpdate();
        paramParcel2.writeNoException();
        int i = 0;
        if (bool) {
          i = 1;
        }
        paramParcel2.writeInt(i);
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
        Bundle localBundle2 = getRestoreFlow(paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localBundle2 != null)
        {
          paramParcel2.writeInt(1);
          localBundle2.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
        startVpa();
        paramParcel2.writeNoException();
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
        Bundle localBundle1 = getFinalHoldFlow();
        paramParcel2.writeNoException();
        if (localBundle1 != null)
        {
          paramParcel2.writeInt(1);
          localBundle1.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      }
      paramParcel1.enforceInterface("com.android.vending.setup.IPlaySetupService");
      startDownloads();
      paramParcel2.writeNoException();
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.setup.IPlaySetupService
 * JD-Core Version:    0.7.0.1
 */