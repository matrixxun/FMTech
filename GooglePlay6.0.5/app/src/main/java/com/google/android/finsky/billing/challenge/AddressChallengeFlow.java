package com.google.android.finsky.billing.challenge;

import android.os.Bundle;
import android.util.Base64;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.protos.Address;
import com.google.android.finsky.protos.ChallengeProto.AddressChallenge;
import com.google.android.finsky.protos.ChallengeProto.FormCheckbox;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;

public final class AddressChallengeFlow
  extends BillingFlow
  implements AddressChallengeFragment.AddressChallengeResultListener
{
  private ChallengeProto.AddressChallenge mAddressChallenge;
  private AddressChallengeFragment mAddressChallengeFragment;
  private boolean mFinishOnSwitchCountry;
  private int mResultFormat = 0;
  
  public AddressChallengeFlow(BillingFlowContext paramBillingFlowContext, BillingFlowListener paramBillingFlowListener, ChallengeProto.AddressChallenge paramAddressChallenge, Bundle paramBundle)
  {
    super(paramBillingFlowContext, paramBillingFlowListener, paramBundle);
    this.mAddressChallenge = paramAddressChallenge;
    boolean bool1 = false;
    if (paramBundle != null)
    {
      boolean bool2 = paramBundle.getBoolean("AddressChallengeFlow.finishOnSwitchCountry");
      bool1 = false;
      if (bool2) {
        bool1 = true;
      }
    }
    this.mFinishOnSwitchCountry = bool1;
    if ((paramBundle != null) && (paramBundle.containsKey("AddressChallengeFlow.resultFormat"))) {
      this.mResultFormat = paramBundle.getInt("AddressChallengeFlow.resultFormat");
    }
  }
  
  public final void onAddressChallengeResult(int paramInt, Address paramAddress, boolean[] paramArrayOfBoolean)
  {
    if (paramInt == 2) {
      cancel();
    }
    do
    {
      return;
      if (paramInt == 0)
      {
        Bundle localBundle = new Bundle();
        if (this.mResultFormat == 0)
        {
          String str = Base64.encodeToString(MessageNano.toByteArray(paramAddress), 8);
          localBundle.putString(this.mAddressChallenge.responseAddressParam, str);
        }
        StringBuilder localStringBuilder;
        ArrayList localArrayList;
        for (;;)
        {
          localStringBuilder = new StringBuilder();
          localArrayList = new ArrayList();
          for (int i = 0; i < paramArrayOfBoolean.length; i++)
          {
            if (i > 0) {
              localStringBuilder.append(',');
            }
            localStringBuilder.append(String.valueOf(paramArrayOfBoolean[i]));
            if (paramArrayOfBoolean[i] != 0) {
              localArrayList.add(this.mAddressChallenge.checkbox[i].id);
            }
          }
          if (this.mResultFormat == 1) {
            localBundle.putParcelable("AddressChallengeFlow.address", ParcelableProto.forProto(paramAddress));
          }
        }
        if (this.mResultFormat == 0) {
          localBundle.putString(this.mAddressChallenge.responseCheckboxesParam, localStringBuilder.toString());
        }
        for (;;)
        {
          finish(localBundle);
          return;
          localBundle.putStringArrayList("AddressChallengeFlow.checkedCheckboxes", localArrayList);
        }
      }
    } while (paramInt != 1);
    fail(null);
  }
  
  public final void onCountryChanged(String paramString, Bundle paramBundle)
  {
    if (this.mFinishOnSwitchCountry)
    {
      FinskyLog.v("Finishing due to country switch.", new Object[0]);
      Bundle localBundle = new Bundle();
      localBundle.putString("AddressChallengeFlow.switchCountry", paramString);
      localBundle.putBundle("AddressChallengeFlow.currentState", paramBundle);
      finish(localBundle);
    }
  }
  
  public final void onInitialized()
  {
    this.mBillingFlowContext.hideProgress();
  }
  
  public final void onInitializing()
  {
    this.mBillingFlowContext.showProgress(2131362306);
  }
  
  public final void resumeFromSavedState(Bundle paramBundle)
  {
    if (paramBundle.containsKey("address_widget"))
    {
      this.mAddressChallengeFragment = ((AddressChallengeFragment)this.mBillingFlowContext.restoreFragment(paramBundle, "address_widget"));
      if (this.mAddressChallengeFragment != null) {
        this.mAddressChallengeFragment.mListener = this;
      }
    }
  }
  
  public final void saveState(Bundle paramBundle)
  {
    if (this.mAddressChallengeFragment != null) {
      this.mBillingFlowContext.persistFragment(paramBundle, "address_widget", this.mAddressChallengeFragment);
    }
  }
  
  public final void start()
  {
    this.mAddressChallengeFragment = AddressChallengeFragment.newInstance(this.mParameters.getString("authAccount"), this.mAddressChallenge, this.mParameters.getBundle("AddressChallengeFlow.previousState"));
    this.mAddressChallengeFragment.mListener = this;
    this.mBillingFlowContext.showFragment$41b27b4d(this.mAddressChallengeFragment, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.challenge.AddressChallengeFlow
 * JD-Core Version:    0.7.0.1
 */