package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.protos.Common.Docid;

public class RedeemCodeActivity
  extends RedeemCodeBaseActivity
{
  public static Intent createBuyFlowIntent(String paramString, int paramInt1, Common.Docid paramDocid, int paramInt2)
  {
    Intent localIntent = new Intent(FinskyApp.get(), RedeemCodeActivity.class);
    putIntentExtras(localIntent, paramString, 1, paramInt1, paramDocid, paramInt2, null, null);
    return localIntent;
  }
  
  public static Intent createIntent(String paramString, int paramInt)
  {
    return createIntent(paramString, paramInt, null, null);
  }
  
  public static Intent createIntent(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(FinskyApp.get(), RedeemCodeActivity.class);
    putIntentExtras(localIntent, paramString1, paramInt, 3, null, 0, paramString2, paramString3);
    return localIntent;
  }
  
  protected final int getActivityLayout()
  {
    return 2130969060;
  }
  
  protected final int getBillingUiMode()
  {
    return 0;
  }
  
  protected final int getInstrumentManagerThemeResourceId()
  {
    return BillingUtils.getInstrumentManagerThemeResourceId();
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 880;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeActivity
 * JD-Core Version:    0.7.0.1
 */