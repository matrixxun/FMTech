package com.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ILicensingService
  extends IInterface
{
  public abstract void checkLicense(long paramLong, String paramString, ILicenseResultListener paramILicenseResultListener)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements ILicensingService
  {
    public Stub()
    {
      attachInterface(this, "com.android.vending.licensing.ILicensingService");
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
        paramParcel2.writeString("com.android.vending.licensing.ILicensingService");
        return true;
      }
      paramParcel1.enforceInterface("com.android.vending.licensing.ILicensingService");
      long l = paramParcel1.readLong();
      String str = paramParcel1.readString();
      IBinder localIBinder = paramParcel1.readStrongBinder();
      Object localObject;
      if (localIBinder == null) {
        localObject = null;
      }
      for (;;)
      {
        checkLicense(l, str, (ILicenseResultListener)localObject);
        return true;
        IInterface localIInterface = localIBinder.queryLocalInterface("com.android.vending.licensing.ILicenseResultListener");
        if ((localIInterface != null) && ((localIInterface instanceof ILicenseResultListener))) {
          localObject = (ILicenseResultListener)localIInterface;
        } else {
          localObject = new ILicenseResultListener.Stub.Proxy(localIBinder);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.licensing.ILicensingService
 * JD-Core Version:    0.7.0.1
 */