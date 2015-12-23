package com.google.android.finsky.billing;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileSidecar;
import com.google.android.finsky.billing.lightpurchase.billingprofile.FopActionEntry;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfile;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfileOption;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.protos.StoredValueInstrument;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;
import java.util.ArrayList;
import java.util.List;

public abstract class BillingProfileBaseFragment
  extends Fragment
  implements PlayStoreUiElementNode
{
  public Account mAccount;
  private final BillingProfileListener mBillingProfileListener = new BillingProfileListener((byte)0);
  protected BillingProfileSidecar mBillingProfileSidecar;
  protected View mErrorIndicator;
  public FinskyEventLog mEventLog;
  private int mLastBillingProfileStateInstance = -1;
  private int mLastCarrierBillingStateInstance = -1;
  public BillingProfileProtos.BillingProfile mProfile;
  private boolean mProfileDirty = true;
  public View mProfileView;
  public View mProgressIndicator;
  private String mPurchaseContextToken;
  private String mStoredValueInstrumentId;
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  protected void clearInstrumentsAndActionViews() {}
  
  public abstract byte[] getBackgroundEventServerLogsCookie();
  
  public abstract int getBillingProfileRequestEnum();
  
  public abstract int getCreditCardEventType();
  
  public abstract int getDcbEventType();
  
  protected int getEditEventType()
  {
    return -1;
  }
  
  public abstract int getGenericInstrumentEventType();
  
  public abstract Intent getRedeemCodeIntent();
  
  public abstract int getRedeemEventType();
  
  public SetupWizardUtils.SetupWizardParams getSetupWizardParams()
  {
    return null;
  }
  
  public abstract int getTopupEventType();
  
  public abstract void logLoading();
  
  public abstract void logScreen();
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccount = ((Account)this.mArguments.getParcelable("BillingProfileFragment.account"));
    this.mPurchaseContextToken = this.mArguments.getString("BillingProfileFragment.purchaseContextToken");
    if (paramBundle != null)
    {
      this.mProfile = ((BillingProfileProtos.BillingProfile)ParcelableProto.getProtoFromBundle(paramBundle, "BillingProfileFragment.profile"));
      if (this.mProfile != null) {
        this.mProfileDirty = true;
      }
      this.mLastBillingProfileStateInstance = paramBundle.getInt("BillingProfileFragment.lastBillingProfileStateInstance", -1);
      this.mLastCarrierBillingStateInstance = paramBundle.getInt("BillingProfileFragment.lastCarrierBillingStateInstance", -1);
      this.mStoredValueInstrumentId = paramBundle.getString("PurchaseFlowBillingProfileFragment.storedValueInstrumentId");
    }
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccount);
  }
  
  public final void onDestroyView()
  {
    super.onDestroyView();
    this.mProfileDirty = true;
    this.mLastBillingProfileStateInstance = -1;
    this.mLastCarrierBillingStateInstance = -1;
  }
  
  public abstract void onFatalError(String paramString);
  
  public abstract void onInstrumentCreated(String paramString);
  
  public abstract void onInstrumentEdited(String paramString);
  
  public final void onPause()
  {
    this.mBillingProfileSidecar.setListener(null);
    super.onPause();
  }
  
  public abstract void onPromoCodeRedeemed(RedeemCodeResult paramRedeemCodeResult);
  
  public final void onResume()
  {
    super.onResume();
    renderProfile();
    this.mBillingProfileSidecar.setListener(this.mBillingProfileListener);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("BillingProfileFragment.profile", ParcelableProto.forProto(this.mProfile));
    paramBundle.putInt("BillingProfileFragment.lastBillingProfileStateInstance", this.mLastBillingProfileStateInstance);
    paramBundle.putInt("BillingProfileFragment.lastCarrierBillingStateInstance", this.mLastCarrierBillingStateInstance);
    paramBundle.putString("PurchaseFlowBillingProfileFragment.storedValueInstrumentId", this.mStoredValueInstrumentId);
  }
  
  public abstract void onStoredValueAdded(String paramString);
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (this.mProfileView == null) {
      throw new IllegalStateException("mProfileView not set up.");
    }
    if (this.mProgressIndicator == null) {
      throw new IllegalStateException("mProgressIndicator not set up.");
    }
    this.mBillingProfileSidecar = ((BillingProfileSidecar)this.mFragmentManager.findFragmentByTag("BillingProfileFragment.billingProfileSidecar"));
    if (this.mBillingProfileSidecar == null)
    {
      this.mBillingProfileSidecar = BillingProfileSidecar.newInstance(this.mAccount, this.mPurchaseContextToken, getSetupWizardParams(), getRedeemCodeIntent(), getBillingProfileRequestEnum(), getCreditCardEventType(), getDcbEventType(), getGenericInstrumentEventType(), getRedeemEventType(), getTopupEventType(), getEditEventType(), getBackgroundEventServerLogsCookie());
      this.mFragmentManager.beginTransaction().add(this.mBillingProfileSidecar, "BillingProfileFragment.billingProfileSidecar").commit();
    }
    renderProfile();
  }
  
  public abstract void renderActions(List<FopActionEntry> paramList);
  
  public abstract void renderInstruments(Instrument[] paramArrayOfInstrument, byte[] paramArrayOfByte);
  
  protected final void renderProfile()
  {
    if (!this.mProfileDirty) {}
    do
    {
      return;
      this.mProfileDirty = false;
    } while ((this.mProfile == null) || (!shouldRender(this.mProfile.instrument)));
    clearInstrumentsAndActionViews();
    for (Instrument localInstrument : this.mProfile.instrument) {
      if ((localInstrument.instrumentFamily == 7) && (localInstrument.storedValue != null) && (localInstrument.storedValue.type == 33)) {
        this.mStoredValueInstrumentId = localInstrument.externalInstrumentId;
      }
    }
    renderInstruments(this.mProfile.instrument, this.mProfile.paymentsIntegratorCommonToken);
    ArrayList localArrayList = Lists.newArrayList(this.mProfile.billingProfileOption.length);
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity.getPackageManager().checkPermission("android.permission.SEND_SMS", localFragmentActivity.getPackageName()) == 0) {}
    for (int k = 1;; k = 0) {
      for (BillingProfileProtos.BillingProfileOption localBillingProfileOption : this.mProfile.billingProfileOption) {
        if ((localBillingProfileOption.type != 2) || (k != 0))
        {
          FopActionEntry localFopActionEntry = this.mBillingProfileSidecar.billingProfileOptionToActionEntry(localBillingProfileOption, this.mProfile.paymentsIntegratorCommonToken, this);
          if (localFopActionEntry != null) {
            localArrayList.add(localFopActionEntry);
          }
        }
      }
    }
    renderActions(localArrayList);
    this.mProgressIndicator.setVisibility(8);
    if (this.mErrorIndicator != null) {
      this.mErrorIndicator.setVisibility(8);
    }
    this.mProfileView.setVisibility(0);
    this.mProfileView.requestFocus();
    logScreen();
  }
  
  protected final void requestBillingProfile()
  {
    this.mBillingProfileSidecar.requestBillingProfile();
  }
  
  public boolean shouldRender(Instrument[] paramArrayOfInstrument)
  {
    return true;
  }
  
  private final class BillingProfileListener
    implements SidecarFragment.Listener
  {
    private BillingProfileListener() {}
    
    public final void onStateChange(SidecarFragment paramSidecarFragment)
    {
      if (paramSidecarFragment.mStateInstance == BillingProfileBaseFragment.this.mLastBillingProfileStateInstance) {}
      for (;;)
      {
        return;
        BillingProfileBaseFragment.access$102(BillingProfileBaseFragment.this, paramSidecarFragment.mStateInstance);
        BillingProfileBaseFragment.this.mProgressIndicator.setVisibility(8);
        if (BillingProfileBaseFragment.this.mErrorIndicator != null) {
          BillingProfileBaseFragment.this.mErrorIndicator.setVisibility(8);
        }
        BillingProfileBaseFragment.this.mProfileView.setVisibility(8);
        int i = paramSidecarFragment.mState;
        boolean bool = false;
        switch (i)
        {
        }
        while (bool)
        {
          BillingProfileBaseFragment.this.logScreen();
          return;
          BillingProfileBaseFragment.this.requestBillingProfile();
          bool = false;
          continue;
          BillingProfileBaseFragment.access$200(BillingProfileBaseFragment.this);
          bool = false;
          continue;
          BillingProfileBaseFragment.access$300(BillingProfileBaseFragment.this, BillingProfileBaseFragment.this.mBillingProfileSidecar.getBillingProfile());
          bool = false;
          continue;
          BillingProfileBaseFragment.this.onInstrumentCreated(BillingProfileBaseFragment.this.mBillingProfileSidecar.mLastUpdatedInstrumentId);
          bool = BillingProfileBaseFragment.this.mBillingProfileSidecar.shouldLogScreen();
          continue;
          BillingProfileBaseFragment.this.onInstrumentEdited(BillingProfileBaseFragment.this.mBillingProfileSidecar.mLastUpdatedInstrumentId);
          bool = BillingProfileBaseFragment.this.mBillingProfileSidecar.shouldLogScreen();
          continue;
          BillingProfileBaseFragment.this.onPromoCodeRedeemed(BillingProfileBaseFragment.this.mBillingProfileSidecar.mRedeemCodeResult);
          bool = BillingProfileBaseFragment.this.mBillingProfileSidecar.shouldLogScreen();
          continue;
          int j = paramSidecarFragment.mSubstate;
          if (j == 1) {
            BillingProfileBaseFragment.access$402(BillingProfileBaseFragment.this, BillingProfileBaseFragment.this.mBillingProfileSidecar.mStoredValueInstrumentId);
          }
          while (j == 2)
          {
            BillingProfileBaseFragment.this.onStoredValueAdded(BillingProfileBaseFragment.this.mStoredValueInstrumentId);
            bool = BillingProfileBaseFragment.this.mBillingProfileSidecar.shouldLogScreen();
            break;
          }
          throw new IllegalStateException("Invalid substate for STATE_STORED_VALUE_ADDED: " + j);
          BillingProfileBaseFragment.access$500(BillingProfileBaseFragment.this);
          bool = false;
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingProfileBaseFragment
 * JD-Core Version:    0.7.0.1
 */