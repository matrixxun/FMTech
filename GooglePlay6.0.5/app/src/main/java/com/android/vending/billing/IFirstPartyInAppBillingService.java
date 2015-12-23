package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import java.util.List;

public abstract interface IFirstPartyInAppBillingService
  extends IInterface
{
  public abstract int consumePurchase(String paramString1, int paramInt, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract Bundle getBuyIntent(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5)
    throws RemoteException;
  
  public abstract Bundle getBuyIntentToReplaceSkus(String paramString1, int paramInt, String paramString2, List<String> paramList, String paramString3, String paramString4, String paramString5)
    throws RemoteException;
  
  public abstract Bundle getPurchases(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4)
    throws RemoteException;
  
  public abstract Bundle getSkuDetails(String paramString1, int paramInt, String paramString2, String paramString3, Bundle paramBundle)
    throws RemoteException;
  
  public abstract int isBillingSupported(String paramString1, int paramInt, String paramString2, String paramString3)
    throws RemoteException;
  
  public abstract int isPromoEligible(String paramString1, int paramInt, String paramString2, String paramString3)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IFirstPartyInAppBillingService
  {
    public Stub()
    {
      attachInterface(this, "com.android.vending.billing.IFirstPartyInAppBillingService");
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
        paramParcel2.writeString("com.android.vending.billing.IFirstPartyInAppBillingService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
        int m = isBillingSupported(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
        String str1 = paramParcel1.readString();
        int k = paramParcel1.readInt();
        String str2 = paramParcel1.readString();
        String str3 = paramParcel1.readString();
        Bundle localBundle4;
        if (paramParcel1.readInt() != 0)
        {
          localBundle4 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          Bundle localBundle5 = getSkuDetails(str1, k, str2, str3, localBundle4);
          paramParcel2.writeNoException();
          if (localBundle5 == null) {
            break label230;
          }
          paramParcel2.writeInt(1);
          localBundle5.writeToParcel(paramParcel2, 1);
        }
        for (;;)
        {
          return true;
          localBundle4 = null;
          break;
          paramParcel2.writeInt(0);
        }
      case 3: 
        paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
        Bundle localBundle3 = getBuyIntent(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localBundle3 != null)
        {
          paramParcel2.writeInt(1);
          localBundle3.writeToParcel(paramParcel2, 1);
        }
        for (;;)
        {
          return true;
          paramParcel2.writeInt(0);
        }
      case 4: 
        paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
        Bundle localBundle2 = getPurchases(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localBundle2 != null)
        {
          paramParcel2.writeInt(1);
          localBundle2.writeToParcel(paramParcel2, 1);
        }
        for (;;)
        {
          return true;
          paramParcel2.writeInt(0);
        }
      case 5: 
        paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
        int j = consumePurchase(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 6: 
        label230:
        paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
        int i = isPromoEligible(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i);
        return true;
      }
      paramParcel1.enforceInterface("com.android.vending.billing.IFirstPartyInAppBillingService");
      Bundle localBundle1 = getBuyIntentToReplaceSkus(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.createStringArrayList(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
      paramParcel2.writeNoException();
      if (localBundle1 != null)
      {
        paramParcel2.writeInt(1);
        localBundle1.writeToParcel(paramParcel2, 1);
      }
      for (;;)
      {
        return true;
        paramParcel2.writeInt(0);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.billing.IFirstPartyInAppBillingService
 * JD-Core Version:    0.7.0.1
 */