package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.iab.InAppBillingUtils;
import com.google.android.finsky.billing.iab.InAppBillingUtils.ResponseCode;
import com.google.android.finsky.utils.FinskyLog;

public class IabV3Activity
  extends PurchaseActivity
{
  public static Intent createIntent(Account paramAccount, PurchaseParams paramPurchaseParams)
  {
    Intent localIntent = PurchaseActivity.createIntent(paramAccount, paramPurchaseParams, null, null);
    localIntent.setClass(FinskyApp.get(), IabV3Activity.class);
    return localIntent;
  }
  
  protected final void handleAccessRestriction()
  {
    setResult(0, new Intent().putExtra("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_USER_CANCELED.ordinal()));
  }
  
  protected final void onFinish(PurchaseFragment paramPurchaseFragment)
  {
    boolean bool = paramPurchaseFragment.mSucceeded;
    PurchaseFragment.PurchaseError localPurchaseError = paramPurchaseFragment.mError;
    Object localObject;
    Bundle localBundle;
    label44:
    Intent localIntent;
    if (bool)
    {
      localObject = InAppBillingUtils.ResponseCode.RESULT_OK;
      Context localContext = getApplicationContext();
      PurchaseParams localPurchaseParams = this.mParams;
      if (paramPurchaseFragment.mExtraPurchaseData == null) {
        break label269;
      }
      localBundle = paramPurchaseFragment.mExtraPurchaseData;
      localIntent = InAppBillingUtils.createResultIntent(localContext, localPurchaseParams, localBundle, (InAppBillingUtils.ResponseCode)localObject);
      if (localObject != InAppBillingUtils.ResponseCode.RESULT_OK) {
        break label281;
      }
    }
    label269:
    label281:
    for (int i = -1;; i = 0)
    {
      setResult(i, localIntent);
      return;
      if (localPurchaseError != null)
      {
        label168:
        InAppBillingUtils.ResponseCode localResponseCode;
        if (localPurchaseError.errorType == 3) {
          switch (localPurchaseError.errorSubtype)
          {
          default: 
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(localPurchaseError.errorSubtype);
            FinskyLog.wtf("Unexpected PurchasePermissionResponse: %d", arrayOfObject);
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ERROR;
          }
        }
        for (;;)
        {
          localObject = localResponseCode;
          break;
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR;
          continue;
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ITEM_UNAVAILABLE;
          continue;
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ITEM_ALREADY_OWNED;
          continue;
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ERROR;
          continue;
          FinskyLog.w("Unexpected INSTALL_OK response.", new Object[0]);
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_OK;
          continue;
          if (localPurchaseError.errorType == 1)
          {
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ITEM_ALREADY_OWNED;
          }
          else
          {
            if (localPurchaseError.errorType != 2) {
              break label168;
            }
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_SERVICE_UNAVAILABLE;
          }
        }
      }
      localObject = InAppBillingUtils.ResponseCode.RESULT_USER_CANCELED;
      break;
      localBundle = paramPurchaseFragment.mSidecar.mExtraPurchaseData;
      break label44;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.IabV3Activity
 * JD-Core Version:    0.7.0.1
 */