package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.challenge.AddressChallengeFlow;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.protos.ChallengeProto.AddressChallenge;
import com.google.android.finsky.utils.ParcelableProto;

public class AddressChallengeActivity
  extends ChallengeActivity
{
  private BillingFlow mFlow;
  private final FakeNavigationManager mNavigationManager = new FakeNavigationManager(this);
  
  public static Intent getIntent(int paramInt, ChallengeProto.AddressChallenge paramAddressChallenge, Bundle paramBundle)
  {
    Intent localIntent = new Intent(FinskyApp.get(), AddressChallengeActivity.class);
    localIntent.putExtra("backend", paramInt);
    localIntent.putExtra("challenge", ParcelableProto.forProto(paramAddressChallenge));
    localIntent.putExtra("extra_parameters", paramBundle);
    return localIntent;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968629);
    ActionBarHelper localActionBarHelper = new ActionBarHelper(this.mNavigationManager, this);
    localActionBarHelper.updateCurrentBackendId(getIntent().getIntExtra("backend", 0), false);
    localActionBarHelper.updateActionBarMode(false, -1);
    this.mFlow = new AddressChallengeFlow(this, this, (ChallengeProto.AddressChallenge)ParcelableProto.getProtoFromIntent(getIntent(), "challenge"), getIntent().getBundleExtra("extra_parameters"));
    if (paramBundle != null)
    {
      this.mFlow.resumeFromSavedState(paramBundle);
      return;
    }
    this.mFlow.start();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
    {
      this.mNavigationManager.goUp();
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mFlow != null) {
      this.mFlow.saveState(paramBundle);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AddressChallengeActivity
 * JD-Core Version:    0.7.0.1
 */