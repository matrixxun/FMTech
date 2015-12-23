package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IMarketBillingService
  extends IInterface
{
  public abstract Bundle sendBillingRequest(Bundle paramBundle)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IMarketBillingService
  {
    public Stub()
    {
      attachInterface(this, "com.android.vending.billing.IMarketBillingService");
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
        paramParcel2.writeString("com.android.vending.billing.IMarketBillingService");
        return true;
      }
      paramParcel1.enforceInterface("com.android.vending.billing.IMarketBillingService");
      if (paramParcel1.readInt() != 0) {}
      for (Bundle localBundle1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);; localBundle1 = null)
      {
        Bundle localBundle2 = sendBillingRequest(localBundle1);
        paramParcel2.writeNoException();
        if (localBundle2 == null) {
          break;
        }
        paramParcel2.writeInt(1);
        localBundle2.writeToParcel(paramParcel2, 1);
        return true;
      }
      paramParcel2.writeInt(0);
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.billing.IMarketBillingService
 * JD-Core Version:    0.7.0.1
 */