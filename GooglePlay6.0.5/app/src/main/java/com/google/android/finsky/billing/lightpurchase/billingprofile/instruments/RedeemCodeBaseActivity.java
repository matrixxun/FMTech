package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment.Listener;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.utils.ParcelableProto;

public abstract class RedeemCodeBaseActivity
  extends LoggingFragmentActivity
  implements RedeemCodeFragment.Listener
{
  private RedeemCodeFragment mRedeemCodeFragment;
  
  protected static void putIntentExtras(Intent paramIntent, String paramString1, int paramInt1, int paramInt2, Common.Docid paramDocid, int paramInt3, String paramString2, String paramString3)
  {
    paramIntent.putExtra("authAccount", paramString1);
    paramIntent.putExtra("RedeemCodeBaseActivity.redemption_context", paramInt1);
    paramIntent.putExtra("RedeemCodeBaseActivity.backend", paramInt2);
    paramIntent.putExtra("RedeemCodeBaseActivity.docid", ParcelableProto.forProto(paramDocid));
    paramIntent.putExtra("RedeemCodeBaseActivity.offer_type", paramInt3);
    paramIntent.putExtra("RedeemCodeBaseActivity.prefill_code", paramString2);
    paramIntent.putExtra("RedeemCodeBaseActivity.partner_payload", paramString3);
  }
  
  public void finish()
  {
    Intent localIntent = new Intent();
    if (this.mRedeemCodeFragment != null)
    {
      RedeemCodeResult localRedeemCodeResult = this.mRedeemCodeFragment.mRedeemCodeResult;
      if (localRedeemCodeResult != null) {
        localIntent.putExtra("RedeemCodeBaseActivity.redeem_code_result", localRedeemCodeResult);
      }
    }
    setResult(-1, localIntent);
    super.finish();
  }
  
  protected abstract int getActivityLayout();
  
  protected abstract int getBillingUiMode();
  
  protected abstract int getInstrumentManagerThemeResourceId();
  
  protected abstract int getPlayStoreUiElementType();
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getActivityLayout());
    if (paramBundle == null)
    {
      Intent localIntent = getIntent();
      Common.Docid localDocid = (Common.Docid)ParcelableProto.getProtoFromIntent(localIntent, "RedeemCodeBaseActivity.docid");
      RedeemCodeFragment localRedeemCodeFragment = RedeemCodeFragment.newInstance(this.mAccountName, localIntent.getIntExtra("RedeemCodeBaseActivity.redemption_context", 0), localIntent.getIntExtra("RedeemCodeBaseActivity.backend", -1), getBillingUiMode(), localDocid, localIntent.getIntExtra("RedeemCodeBaseActivity.offer_type", 0), localIntent.getStringExtra("RedeemCodeBaseActivity.prefill_code"), getInstrumentManagerThemeResourceId(), localIntent.getStringExtra("RedeemCodeBaseActivity.partner_payload"));
      getSupportFragmentManager().beginTransaction().add(2131755234, localRedeemCodeFragment).commit();
    }
  }
  
  public final void onFinished()
  {
    finish();
  }
  
  protected void onStart()
  {
    super.onStart();
    this.mRedeemCodeFragment = ((RedeemCodeFragment)getSupportFragmentManager().findFragmentById(2131755234));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeBaseActivity
 * JD-Core Version:    0.7.0.1
 */