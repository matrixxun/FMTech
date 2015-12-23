package com.google.android.finsky.billing.iab;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService.Stub;
import java.util.List;

public class InAppBillingService
  extends Service
{
  private final Stub mBinder = new Stub((byte)0);
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  private final class Stub
    extends IInAppBillingService.Stub
  {
    private Stub() {}
    
    public final int consumePurchase(int paramInt, String paramString1, String paramString2)
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return localResponseCode.responseCode;
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE.responseCode;
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).consumePurchase(paramInt, paramString1, paramString2);
    }
    
    public final Bundle getBuyIntent(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return InAppBillingService.access$400$12b40437(localResponseCode);
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingService.access$400$12b40437(InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).getBuyIntent(paramInt, paramString1, paramString2, paramString3, paramString4);
    }
    
    public final Bundle getBuyIntentToReplaceSkus(int paramInt, String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
      throws RemoteException
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return InAppBillingService.access$400$12b40437(localResponseCode);
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingService.access$400$12b40437(InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).getBuyIntentToReplaceSkus(paramInt, paramString1, paramList, paramString2, paramString3, paramString4);
    }
    
    public final Bundle getPurchases(int paramInt, String paramString1, String paramString2, String paramString3)
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return InAppBillingService.access$400$12b40437(localResponseCode);
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingService.access$400$12b40437(InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).getPurchases(paramInt, paramString1, paramString2, paramString3);
    }
    
    public final Bundle getSkuDetails(int paramInt, String paramString1, String paramString2, Bundle paramBundle)
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return InAppBillingService.access$400$12b40437(localResponseCode);
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingService.access$400$12b40437(InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).getSkuDetails(paramInt, paramString1, paramString2, paramBundle);
    }
    
    public final int isBillingSupported(int paramInt, String paramString1, String paramString2)
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return localResponseCode.responseCode;
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE.responseCode;
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).isBillingSupported$6ef37c35(paramInt, paramString2);
    }
    
    public final int isPromoEligible(int paramInt, String paramString1, String paramString2)
      throws RemoteException
    {
      InAppBillingUtils.ResponseCode localResponseCode = InAppBillingService.validatePackageName(InAppBillingService.this, paramString1);
      if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
        return localResponseCode.responseCode;
      }
      String str = InAppBillingService.access$200(InAppBillingService.this, paramString1);
      if (str == null) {
        return InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE.responseCode;
      }
      return InAppBillingService.access$300(InAppBillingService.this, str).isPromoEligible(paramInt, paramString1, paramString2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.iab.InAppBillingService
 * JD-Core Version:    0.7.0.1
 */