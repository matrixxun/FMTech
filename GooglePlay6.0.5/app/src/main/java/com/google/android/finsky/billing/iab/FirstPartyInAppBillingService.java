package com.google.android.finsky.billing.iab;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IFirstPartyInAppBillingService.Stub;
import java.util.List;

public class FirstPartyInAppBillingService
  extends Service
{
  private final Stub mBinder = new Stub((byte)0);
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  private final class Stub
    extends IFirstPartyInAppBillingService.Stub
  {
    private Stub() {}
    
    public final int consumePurchase(String paramString1, int paramInt, String paramString2, String paramString3)
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return localResponseCode.responseCode;
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).consumePurchase(paramInt, paramString2, paramString3);
    }
    
    public final Bundle getBuyIntent(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5)
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return FirstPartyInAppBillingService.access$300$4a359201(localResponseCode);
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).getBuyIntent(paramInt, paramString2, paramString3, paramString4, paramString5);
    }
    
    public final Bundle getBuyIntentToReplaceSkus(String paramString1, int paramInt, String paramString2, List<String> paramList, String paramString3, String paramString4, String paramString5)
      throws RemoteException
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return FirstPartyInAppBillingService.access$300$4a359201(localResponseCode);
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).getBuyIntentToReplaceSkus(paramInt, paramString2, paramList, paramString3, paramString4, paramString5);
    }
    
    public final Bundle getPurchases(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4)
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return FirstPartyInAppBillingService.access$300$4a359201(localResponseCode);
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).getPurchases(paramInt, paramString2, paramString3, paramString4);
    }
    
    public final Bundle getSkuDetails(String paramString1, int paramInt, String paramString2, String paramString3, Bundle paramBundle)
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return FirstPartyInAppBillingService.access$300$4a359201(localResponseCode);
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).getSkuDetails(paramInt, paramString2, paramString3, paramBundle);
    }
    
    public final int isBillingSupported(String paramString1, int paramInt, String paramString2, String paramString3)
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return localResponseCode.responseCode;
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).isBillingSupported$6ef37c35(paramInt, paramString3);
    }
    
    public final int isPromoEligible(String paramString1, int paramInt, String paramString2, String paramString3)
      throws RemoteException
    {
      InAppBillingUtils.ResponseCode localResponseCode = FirstPartyInAppBillingService.access$100(FirstPartyInAppBillingService.this, paramString1, paramString2);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return localResponseCode.responseCode;
      }
      return FirstPartyInAppBillingService.access$200(FirstPartyInAppBillingService.this, paramString1).isPromoEligible(paramInt, paramString2, paramString3);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.iab.FirstPartyInAppBillingService
 * JD-Core Version:    0.7.0.1
 */