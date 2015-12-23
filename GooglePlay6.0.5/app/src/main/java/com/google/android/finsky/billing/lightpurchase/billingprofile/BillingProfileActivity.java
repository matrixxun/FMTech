package com.google.android.finsky.billing.lightpurchase.billingprofile;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.billing.BillingProfileBaseFragment;
import com.google.android.finsky.billing.BillingProfileFragment;
import com.google.android.finsky.billing.BillingProfileFragment.Listener;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.receivers.FlushLogsReceiver;
import com.google.android.finsky.utils.ParcelableProto;

public class BillingProfileActivity
  extends AppCompatActivity
  implements BillingProfileFragment.Listener
{
  private Account mAccount;
  private String mPurchaseContextToken;
  
  public static Intent createIntent(Account paramAccount, String paramString, Common.Docid paramDocid, int paramInt)
  {
    Intent localIntent = new Intent(FinskyApp.get(), BillingProfileActivity.class);
    localIntent.putExtra("BillingProfileActivity.account", paramAccount);
    localIntent.putExtra("BillingProfileActivity.purchaseContextToken", paramString);
    localIntent.putExtra("BillingProfileActivity.docid", ParcelableProto.forProto(paramDocid));
    localIntent.putExtra("BillingProfileActivity.offerType", paramInt);
    return localIntent;
  }
  
  public void finish()
  {
    BillingProfileBaseFragment localBillingProfileBaseFragment = (BillingProfileBaseFragment)getSupportFragmentManager().findFragmentByTag("BillingProfileActivity.fragment");
    if ((localBillingProfileBaseFragment != null) && (this.mAccount != null)) {
      FinskyApp.get().getEventLogger(this.mAccount).logPathImpression$7d139cbf(603, localBillingProfileBaseFragment);
    }
    super.finish();
  }
  
  public final void onCancel()
  {
    setResult(0);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968633);
    ((TextView)findViewById(2131755173)).setText(2131362492);
    this.mAccount = ((Account)getIntent().getParcelableExtra("BillingProfileActivity.account"));
    this.mPurchaseContextToken = getIntent().getStringExtra("BillingProfileActivity.purchaseContextToken");
    TextView localTextView = (TextView)findViewById(2131755621);
    localTextView.setText(this.mAccount.name);
    localTextView.setVisibility(0);
    if (getSupportFragmentManager().findFragmentByTag("BillingProfileActivity.fragment") == null)
    {
      Intent localIntent = getIntent();
      Common.Docid localDocid = (Common.Docid)ParcelableProto.getProtoFromIntent(localIntent, "BillingProfileActivity.docid");
      BillingProfileFragment localBillingProfileFragment = BillingProfileFragment.newInstance(this.mAccount, this.mPurchaseContextToken, localDocid, localIntent.getIntExtra("BillingProfileActivity.offerType", 0));
      getSupportFragmentManager().beginTransaction().add(2131755234, localBillingProfileFragment, "BillingProfileActivity.fragment").commit();
    }
  }
  
  public final void onInstrumentSelected(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("BillingProfileActivity.selectedInstrumentId", paramString);
    setResult(-1, localIntent);
    finish();
  }
  
  protected void onPause()
  {
    super.onPause();
    FlushLogsReceiver.scheduleLogsFlushOnAppExit();
  }
  
  public final void onPromoCodeRedeemed(RedeemCodeResult paramRedeemCodeResult)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("BillingProfileActivity.redeemPromoCodeResult", paramRedeemCodeResult);
    setResult(-1, localIntent);
    finish();
  }
  
  protected void onResume()
  {
    super.onResume();
    FlushLogsReceiver.cancelLogsFlush();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileActivity
 * JD-Core Version:    0.7.0.1
 */