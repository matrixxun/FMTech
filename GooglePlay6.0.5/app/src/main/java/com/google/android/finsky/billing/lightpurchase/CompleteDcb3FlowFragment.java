package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.carrierbilling.flow.CompleteDcb3Flow;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;

public final class CompleteDcb3FlowFragment
  extends LegacyFlowWrapperFragment
{
  protected final BillingFlow getLegacyPurchaseFlow()
  {
    Bundle localBundle1 = this.mArguments;
    Account localAccount1 = (Account)localBundle1.getParcelable("CompleteDcb3Flow.account");
    Bundle localBundle2 = new Bundle();
    localBundle2.putString("authAccount", localAccount1.name);
    Instrument localInstrument = (Instrument)ParcelableProto.getProtoFromBundle(localBundle1, "CompleteDcb3Flow.instrument");
    String str = localBundle2.getString("authAccount");
    localBundle2.putParcelable("dcb_instrument", ParcelableProto.forProto(localInstrument));
    Account localAccount2 = AccountHandler.findAccount(str, FinskyApp.get());
    if (localAccount2 == null)
    {
      FinskyLog.e("Invalid account passed in parameters.", new Object[0]);
      return null;
    }
    return new CompleteDcb3Flow(this, FinskyApp.get().getDfeApi(localAccount2.name), this, FinskyApp.get().getEventLogger(), localBundle2);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    startOrResumeLegacyFlow(paramBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.CompleteDcb3FlowFragment
 * JD-Core Version:    0.7.0.1
 */