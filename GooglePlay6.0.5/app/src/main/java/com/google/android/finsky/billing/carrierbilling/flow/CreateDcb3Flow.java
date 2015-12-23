package com.google.android.finsky.billing.carrierbilling.flow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.InstrumentFlow;
import com.google.android.finsky.billing.carrierbilling.PhoneCarrierBillingUtils;
import com.google.android.finsky.billing.carrierbilling.flow.association.AssociationAction;
import com.google.android.finsky.billing.carrierbilling.flow.association.CarrierOutAssociation;
import com.google.android.finsky.billing.carrierbilling.fragment.AddCarrierBillingFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.AddCarrierBillingFragment.AddCarrierBillingResultListener;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingErrorDialog;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingErrorDialog.CarrierBillingErrorListener;
import com.google.android.finsky.billing.carrierbilling.fragment.EditCarrierBillingFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.EditCarrierBillingFragment.EditCarrierBillingResultListener;
import com.google.android.finsky.billing.carrierbilling.fragment.SetupWizardVerifyAssociationFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.VerifyAssociationDialogFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.VerifyAssociationDialogFragment.VerifyAssociationListener;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.Address;
import com.google.android.finsky.protos.BuyInstruments.UpdateInstrumentRequest;
import com.google.android.finsky.protos.BuyInstruments.UpdateInstrumentResponse;
import com.google.android.finsky.protos.CarrierBilling.VerifyAssociationResponse;
import com.google.android.finsky.protos.CarrierBillingInstrument;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;
import com.google.android.finsky.protos.CarrierTos;
import com.google.android.finsky.protos.CarrierTosEntry;
import com.google.android.finsky.protos.ChallengeProto.InputValidationError;
import com.google.android.finsky.protos.DeviceAssociation;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public final class CreateDcb3Flow
  extends InstrumentFlow
  implements Response.ErrorListener, Response.Listener<BuyInstruments.UpdateInstrumentResponse>, AddCarrierBillingFragment.AddCarrierBillingResultListener, CarrierBillingErrorDialog.CarrierBillingErrorListener, EditCarrierBillingFragment.EditCarrierBillingResultListener, VerifyAssociationDialogFragment.VerifyAssociationListener
{
  private final String mAccountName;
  private BuyInstruments.UpdateInstrumentResponse mAddCarrierBillingResponse;
  private AddCarrierBillingFragment mAddFragment;
  private boolean mAddFragmentShown;
  private int mAddResult = -1;
  int mAddressMode = 0;
  private AssociationAction mAssociationAction;
  private String mAssociationAddress;
  private AssociationListener mAssociationListener;
  private String mAssociationPrefix;
  private boolean mAssociationRequired;
  int mBillingUiMode;
  private String mCarrierName;
  final BillingFlowContext mContext;
  String mDcbTosUrl;
  String mDcbTosVersion;
  private final DfeApi mDfeApi;
  private EditCarrierBillingFragment mEditFragment;
  private CarrierBillingErrorDialog mErrorFragment;
  private final FinskyEventLog mEventLog;
  private String mPiiTosUrl;
  private String mPiiTosVersion;
  private int mState = 0;
  Address mSubscriberAddress;
  private String mTitle;
  private SubscriberInfo mUserProvidedAddress;
  Fragment mVerifyFragment;
  
  public CreateDcb3Flow(BillingFlowContext paramBillingFlowContext, BillingFlowListener paramBillingFlowListener, DfeApi paramDfeApi, CarrierBillingInstrumentStatus paramCarrierBillingInstrumentStatus, int paramInt)
  {
    super(paramBillingFlowContext, paramBillingFlowListener, null);
    this.mContext = paramBillingFlowContext;
    this.mDfeApi = paramDfeApi;
    this.mBillingUiMode = paramInt;
    int i;
    if (((Boolean)G.enableDcbReducedBillingAddress.get()).booleanValue())
    {
      List localList = BillingLocator.getBillingCountries();
      if (localList != null)
      {
        VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry = BillingUtils.findCountry(BillingUtils.getDefaultCountry(FinskyApp.get(), null), localList);
        if (localCountry != null)
        {
          if (!localCountry.allowsReducedBillingAddress) {
            break label297;
          }
          i = 1;
          this.mAddressMode = i;
        }
      }
    }
    this.mAccountName = paramDfeApi.getAccountName();
    this.mEventLog = FinskyApp.get().getEventLogger(paramDfeApi.getAccount());
    if (paramCarrierBillingInstrumentStatus.deviceAssociation != null)
    {
      this.mAssociationAddress = paramCarrierBillingInstrumentStatus.deviceAssociation.userTokenRequestAddress;
      this.mAssociationPrefix = paramCarrierBillingInstrumentStatus.deviceAssociation.userTokenRequestMessage;
    }
    if (paramCarrierBillingInstrumentStatus.hasName)
    {
      this.mCarrierName = paramCarrierBillingInstrumentStatus.name;
      FinskyApp localFinskyApp = FinskyApp.get();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mCarrierName;
      this.mTitle = localFinskyApp.getString(2131362072, arrayOfObject);
    }
    for (;;)
    {
      if (paramCarrierBillingInstrumentStatus.carrierTos != null)
      {
        CarrierTos localCarrierTos = paramCarrierBillingInstrumentStatus.carrierTos;
        if (localCarrierTos.dcbTos != null)
        {
          CarrierTosEntry localCarrierTosEntry2 = localCarrierTos.dcbTos;
          this.mDcbTosUrl = localCarrierTosEntry2.url;
          this.mDcbTosVersion = localCarrierTosEntry2.version;
        }
        if (localCarrierTos.piiTos != null)
        {
          CarrierTosEntry localCarrierTosEntry1 = localCarrierTos.piiTos;
          this.mPiiTosUrl = localCarrierTosEntry1.url;
          this.mPiiTosVersion = localCarrierTosEntry1.version;
        }
      }
      this.mAssociationRequired = paramCarrierBillingInstrumentStatus.associationRequired;
      return;
      label297:
      i = 0;
      break;
      FinskyLog.wtf("No carrier name available in status.", new Object[0]);
    }
  }
  
  private boolean initParams()
  {
    if ((this.mCarrierName == null) || (this.mDfeApi == null))
    {
      FinskyLog.d("Cannot run this BillingFlow since carrier name or DFE api is null.", new Object[0]);
      fail(FinskyApp.get().getString(2131362187));
      return false;
    }
    return true;
  }
  
  private void logDcbAddError(int paramInt, String paramString)
  {
    this.mEventLog.logBackgroundEvent(342, null, null, paramInt, paramString, null);
  }
  
  private void showEditAddressFragment(ArrayList<Integer> paramArrayList)
  {
    if (this.mUserProvidedAddress != null) {}
    for (SubscriberInfo localSubscriberInfo = this.mUserProvidedAddress;; localSubscriberInfo = PhoneCarrierBillingUtils.getSubscriberInfo(this.mSubscriberAddress))
    {
      this.mEditFragment = EditCarrierBillingFragment.newInstance(this.mAccountName, this.mAddressMode, localSubscriberInfo, paramArrayList, this.mBillingUiMode);
      this.mEditFragment.mListener = this;
      this.mContext.showFragment$41b27b4d(this.mEditFragment, this.mTitle);
      return;
    }
  }
  
  private void showErrorDialog(String paramString, boolean paramBoolean)
  {
    this.mErrorFragment = CarrierBillingErrorDialog.newInstance(paramString, paramBoolean);
    this.mErrorFragment.mListener = this;
    this.mContext.showDialogFragment(this.mErrorFragment, "error");
  }
  
  private void showGenericError(String paramString1, String paramString2)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mCarrierName;
    showError$78a511a1(paramString1, localFinskyApp.getString(2131361822, arrayOfObject), false);
  }
  
  private void showProgress()
  {
    this.mContext.showProgress(2131362711);
    if (this.mAddFragment != null) {
      this.mAddFragment.enableUi(false);
    }
  }
  
  private void showTosFragment(SubscriberInfo paramSubscriberInfo, String paramString1, String paramString2)
  {
    int i;
    if (TextUtils.isEmpty(paramString1)) {
      if (paramSubscriberInfo != null) {
        i = 2;
      }
    }
    for (;;)
    {
      this.mAddFragment = AddCarrierBillingFragment.newInstance(i, paramSubscriberInfo, paramString1, paramString2, this.mCarrierName, this.mAccountName, this.mBillingUiMode);
      this.mAddFragment.mListener = this;
      this.mAddFragmentShown = true;
      this.mContext.showFragment$41b27b4d(this.mAddFragment, this.mTitle);
      return;
      if (!TextUtils.isEmpty(paramString2))
      {
        i = 0;
      }
      else
      {
        FinskyLog.w("showTosFragment has no address and tos. wrong fragment.", new Object[0]);
        return;
        if (paramSubscriberInfo != null) {
          i = 3;
        } else if (!TextUtils.isEmpty(paramString2)) {
          i = 1;
        } else {
          i = 6;
        }
      }
    }
  }
  
  public final void back()
  {
    super.cancel();
  }
  
  public final boolean canGoBack()
  {
    return true;
  }
  
  public final void cancel()
  {
    super.cancel();
  }
  
  final void hideProgress()
  {
    this.mContext.hideProgress();
    if (this.mAddFragment != null) {
      this.mAddFragment.enableUi(true);
    }
  }
  
  public final void onActivityResume()
  {
    if ((this.mAssociationAction != null) && (this.mAssociationListener != null)) {
      this.mAssociationAction.setListener(this.mAssociationListener, this);
    }
  }
  
  public final void onAddCarrierBillingResult(int paramInt)
  {
    this.mAddResult = paramInt;
    if (this.mAddFragment != null)
    {
      this.mContext.hideFragment$4b1b4969(this.mAddFragment);
      this.mAddFragment = null;
    }
    if (paramInt == 0)
    {
      performNext();
      return;
    }
    if (paramInt == 2)
    {
      performNext();
      return;
    }
    if (paramInt == 1)
    {
      super.cancel();
      return;
    }
    showGenericError("Invalid error code.", "UNKNOWN");
  }
  
  public final void onEditCarrierBillingResult(SubscriberInfo paramSubscriberInfo)
  {
    if (this.mEditFragment != null)
    {
      this.mContext.hideFragment$4b1b4969(this.mEditFragment);
      this.mEditFragment = null;
    }
    if (paramSubscriberInfo != null)
    {
      this.mUserProvidedAddress = paramSubscriberInfo;
      performNext();
      return;
    }
    super.cancel();
  }
  
  public final void onErrorDismiss(boolean paramBoolean)
  {
    if (this.mAssociationAction != null) {
      this.mAssociationAction.cancel();
    }
    if (paramBoolean)
    {
      fail(FinskyApp.get().getString(2131362187));
      return;
    }
    super.cancel();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    hideProgress();
    FinskyLog.w("Error received: %s", new Object[] { paramVolleyError });
    logDcbAddError(1, paramVolleyError.getClass().getCanonicalName());
    showErrorDialog(ErrorStrings.get(FinskyApp.get(), paramVolleyError), false);
  }
  
  public final void onVerifyCancel()
  {
    if (this.mAssociationAction != null) {
      this.mAssociationAction.cancel();
    }
    super.cancel();
  }
  
  final void performNext()
  {
    do
    {
      switch (this.mState)
      {
      default: 
        throw new IllegalStateException("Unexpected state: " + this.mState);
      case 0: 
        this.mState = 1;
      }
    } while (TextUtils.isEmpty(this.mPiiTosUrl));
    showTosFragment(null, this.mPiiTosUrl, null);
    return;
    if (this.mAssociationRequired)
    {
      this.mState = 2;
      if (this.mBillingUiMode == 0)
      {
        this.mVerifyFragment = VerifyAssociationDialogFragment.newInstance(this.mAccountName);
        ((VerifyAssociationDialogFragment)this.mVerifyFragment).mListener = this;
        this.mContext.showDialogFragment((VerifyAssociationDialogFragment)this.mVerifyFragment, "verifying pin");
      }
      for (;;)
      {
        this.mAssociationAction = new CarrierOutAssociation(this.mDfeApi, this.mAssociationAddress, this.mAssociationPrefix, this.mPiiTosVersion, true);
        this.mAssociationListener = new AssociationListener();
        this.mAssociationAction.start(this.mAssociationListener, this);
        return;
        this.mVerifyFragment = SetupWizardVerifyAssociationFragment.newInstance(this.mAccountName);
        ((SetupWizardVerifyAssociationFragment)this.mVerifyFragment).mListener = this;
        this.mContext.showFragment$41b27b4d(this.mVerifyFragment, FinskyApp.get().getString(2131362835));
        showProgress();
      }
    }
    this.mState = 3;
    showEditAddressFragment(null);
    return;
    Address localAddress2 = this.mSubscriberAddress;
    int n = 0;
    if (localAddress2 != null)
    {
      boolean bool1 = TextUtils.isEmpty(localAddress2.postalCode);
      n = 0;
      if (!bool1)
      {
        boolean bool2 = TextUtils.isEmpty(localAddress2.name);
        n = 0;
        if (!bool2)
        {
          boolean bool3 = TextUtils.isEmpty(localAddress2.phoneNumber);
          n = 0;
          if (!bool3) {
            break label359;
          }
        }
      }
    }
    while (n == 0)
    {
      this.mState = 3;
      showEditAddressFragment(null);
      return;
      label359:
      if (this.mAddressMode == 0)
      {
        boolean bool4 = TextUtils.isEmpty(localAddress2.addressLine1);
        n = 0;
        if (!bool4)
        {
          boolean bool5 = TextUtils.isEmpty(localAddress2.city);
          n = 0;
          if (bool5) {}
        }
      }
      else
      {
        n = 1;
      }
    }
    this.mState = 4;
    if (TextUtils.isEmpty(this.mSubscriberAddress.addressLine1))
    {
      showTosFragment(PhoneCarrierBillingUtils.getSubscriberInfo(this.mSubscriberAddress), this.mDcbTosUrl, null);
      return;
    }
    showTosFragment(null, this.mDcbTosUrl, this.mSubscriberAddress.addressLine1);
    return;
    this.mState = 4;
    showTosFragment(this.mUserProvidedAddress, this.mDcbTosUrl, null);
    return;
    if (this.mAddResult == 2)
    {
      this.mState = 3;
      showEditAddressFragment(null);
      return;
    }
    this.mState = 5;
    showProgress();
    this.mEventLog.logBackgroundEvent(340, null, null, 0, null, null);
    BuyInstruments.UpdateInstrumentRequest localUpdateInstrumentRequest = new BuyInstruments.UpdateInstrumentRequest();
    CarrierBillingInstrument localCarrierBillingInstrument = new CarrierBillingInstrument();
    localCarrierBillingInstrument.instrumentKey = BillingLocator.getSimIdentifier();
    localCarrierBillingInstrument.hasInstrumentKey = true;
    CarrierTos localCarrierTos = new CarrierTos();
    if (!TextUtils.isEmpty(this.mDcbTosVersion))
    {
      localCarrierTos.dcbTos = new CarrierTosEntry();
      localCarrierTos.dcbTos.version = this.mDcbTosVersion;
      localCarrierTos.dcbTos.hasVersion = true;
    }
    if (!TextUtils.isEmpty(this.mPiiTosVersion))
    {
      localCarrierTos.piiTos = new CarrierTosEntry();
      localCarrierTos.piiTos.version = this.mPiiTosVersion;
      localCarrierTos.piiTos.hasVersion = true;
    }
    localCarrierBillingInstrument.acceptedCarrierTos = localCarrierTos;
    Instrument localInstrument = new Instrument();
    Address localAddress1;
    if (this.mUserProvidedAddress != null)
    {
      SubscriberInfo localSubscriberInfo = this.mUserProvidedAddress;
      int m = this.mAddressMode;
      localAddress1 = new Address();
      if (!TextUtils.isEmpty(localSubscriberInfo.mName))
      {
        localAddress1.name = localSubscriberInfo.mName;
        localAddress1.hasName = true;
      }
      if (!TextUtils.isEmpty(localSubscriberInfo.mIdentifier))
      {
        localAddress1.phoneNumber = localSubscriberInfo.mIdentifier;
        localAddress1.hasPhoneNumber = true;
      }
      if (m == 0)
      {
        if (!TextUtils.isEmpty(localSubscriberInfo.mAddress1))
        {
          localAddress1.addressLine1 = localSubscriberInfo.mAddress1;
          localAddress1.hasAddressLine1 = true;
        }
        if (!TextUtils.isEmpty(localSubscriberInfo.mAddress2))
        {
          localAddress1.addressLine2 = localSubscriberInfo.mAddress2;
          localAddress1.hasAddressLine2 = true;
        }
        if (!TextUtils.isEmpty(localSubscriberInfo.mCity))
        {
          localAddress1.city = localSubscriberInfo.mCity;
          localAddress1.hasCity = true;
        }
        if (!TextUtils.isEmpty(localSubscriberInfo.mState))
        {
          localAddress1.state = localSubscriberInfo.mState;
          localAddress1.hasState = true;
        }
        localAddress1.deprecatedIsReduced = false;
        localAddress1.hasDeprecatedIsReduced = true;
        if (!TextUtils.isEmpty(localSubscriberInfo.mPostalCode))
        {
          localAddress1.postalCode = localSubscriberInfo.mPostalCode;
          localAddress1.hasPostalCode = true;
        }
        if (!TextUtils.isEmpty(localSubscriberInfo.mCountry))
        {
          localAddress1.postalCountry = localSubscriberInfo.mCountry;
          localAddress1.hasPostalCountry = true;
        }
        localInstrument.billingAddress = localAddress1;
      }
    }
    for (;;)
    {
      localInstrument.carrierBilling = localCarrierBillingInstrument;
      localUpdateInstrumentRequest.instrument = localInstrument;
      this.mDfeApi.updateInstrument(localUpdateInstrumentRequest, this, this);
      return;
      localAddress1.deprecatedIsReduced = true;
      break;
      if (this.mSubscriberAddress == null) {
        FinskyLog.wtf("No Subscriber address available.", new Object[0]);
      } else {
        localInstrument.billingAddress = this.mSubscriberAddress;
      }
    }
    if (this.mAddCarrierBillingResponse == null)
    {
      FinskyLog.wtf("Update instrument response is null.", new Object[0]);
      showGenericError("Update instrument response is null.", "UNKNOWN");
      return;
    }
    if (this.mAddCarrierBillingResponse.result == 0)
    {
      this.mEventLog.logBackgroundEvent(341, null, null, 0, null, null);
      this.mState = 6;
      BuyInstruments.UpdateInstrumentResponse localUpdateInstrumentResponse2 = this.mAddCarrierBillingResponse;
      Bundle localBundle = new Bundle();
      if (localUpdateInstrumentResponse2.hasInstrumentId) {
        localBundle.putString("instrument_id", localUpdateInstrumentResponse2.instrumentId);
      }
      finish(localBundle);
      return;
    }
    BuyInstruments.UpdateInstrumentResponse localUpdateInstrumentResponse1 = this.mAddCarrierBillingResponse;
    ArrayList localArrayList;
    if (localUpdateInstrumentResponse1.result == 2)
    {
      ChallengeProto.InputValidationError[] arrayOfInputValidationError = localUpdateInstrumentResponse1.errorInputField;
      localArrayList = new ArrayList();
      int i = arrayOfInputValidationError.length;
      int j = 0;
      if (j < i)
      {
        ChallengeProto.InputValidationError localInputValidationError = arrayOfInputValidationError[j];
        int k = localInputValidationError.inputField;
        switch (k)
        {
        case 10: 
        case 11: 
        case 12: 
        default: 
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(k);
          arrayOfObject[1] = localInputValidationError.errorMessage;
          FinskyLog.d("InputValidationError that can't be edited: type=%d, message=%s", arrayOfObject);
        }
        for (;;)
        {
          j++;
          break;
          localArrayList.add(Integer.valueOf(k));
        }
      }
      if (localArrayList.isEmpty()) {}
    }
    while (localArrayList != null)
    {
      logDcbAddError(2, null);
      this.mState = 3;
      showEditAddressFragment(localArrayList);
      return;
      localArrayList = null;
    }
    if (this.mAddCarrierBillingResponse.hasUserMessageHtml)
    {
      logDcbAddError(3, null);
      showError$78a511a1("Update carrier billing instrument had error", this.mAddCarrierBillingResponse.userMessageHtml, false);
      return;
    }
    logDcbAddError(0, null);
    showGenericError("Could not add carrier billing instrument.", "UNKNOWN");
  }
  
  public final void resumeFromSavedState(Bundle paramBundle)
  {
    if (!initParams()) {}
    do
    {
      do
      {
        return;
        if (this.mState != 0) {
          throw new IllegalStateException();
        }
        this.mState = paramBundle.getInt("state");
        if (this.mState == 5) {
          finish(null);
        }
        this.mAddFragmentShown = paramBundle.getBoolean("add_fragment_shown");
        this.mUserProvidedAddress = ((SubscriberInfo)paramBundle.getParcelable("user_provided_address"));
        if (paramBundle.containsKey("dcb_tos_url")) {
          this.mDcbTosUrl = paramBundle.getString("dcb_tos_url");
        }
        if (paramBundle.containsKey("dcb_tos_version")) {
          this.mDcbTosVersion = paramBundle.getString("dcb_tos_version");
        }
        if (paramBundle.containsKey("pii_tos_url")) {
          this.mPiiTosUrl = paramBundle.getString("pii_tos_url");
        }
        if (paramBundle.containsKey("pii_tos_version")) {
          this.mPiiTosVersion = paramBundle.getString("pii_tos_version");
        }
        if (paramBundle.containsKey("error_dialog"))
        {
          this.mErrorFragment = ((CarrierBillingErrorDialog)this.mContext.restoreFragment(paramBundle, "error_dialog"));
          this.mErrorFragment.mListener = this;
        }
        if (paramBundle.containsKey("add_fragment"))
        {
          this.mAddFragment = ((AddCarrierBillingFragment)this.mContext.restoreFragment(paramBundle, "add_fragment"));
          this.mAddFragment.mListener = this;
        }
        if (paramBundle.containsKey("edit_fragment"))
        {
          this.mEditFragment = ((EditCarrierBillingFragment)this.mContext.restoreFragment(paramBundle, "edit_fragment"));
          this.mEditFragment.mListener = this;
        }
      } while (!paramBundle.containsKey("verify_dialog"));
      this.mAssociationAction = new CarrierOutAssociation(this.mDfeApi, this.mAssociationAddress, this.mAssociationPrefix, this.mPiiTosVersion, true);
      this.mAssociationListener = new AssociationListener();
      this.mAssociationAction.resumeState(paramBundle, this.mAssociationListener, this);
      this.mVerifyFragment = this.mContext.restoreFragment(paramBundle, "verify_dialog");
      if ((this.mVerifyFragment instanceof VerifyAssociationDialogFragment))
      {
        ((VerifyAssociationDialogFragment)this.mVerifyFragment).mListener = this;
        return;
      }
    } while (!(this.mVerifyFragment instanceof SetupWizardVerifyAssociationFragment));
    ((SetupWizardVerifyAssociationFragment)this.mVerifyFragment).mListener = this;
  }
  
  public final void saveState(Bundle paramBundle)
  {
    paramBundle.putInt("state", this.mState);
    paramBundle.putBoolean("add_fragment_shown", this.mAddFragmentShown);
    if (this.mErrorFragment != null) {
      this.mContext.persistFragment(paramBundle, "error_dialog", this.mErrorFragment);
    }
    if (this.mAddFragment != null) {
      this.mContext.persistFragment(paramBundle, "add_fragment", this.mAddFragment);
    }
    if (this.mEditFragment != null) {
      this.mContext.persistFragment(paramBundle, "edit_fragment", this.mEditFragment);
    }
    if (this.mVerifyFragment != null)
    {
      this.mContext.persistFragment(paramBundle, "verify_dialog", this.mVerifyFragment);
      if (this.mAssociationAction != null) {
        this.mAssociationAction.saveState(paramBundle);
      }
    }
    if (this.mUserProvidedAddress != null) {
      paramBundle.putParcelable("user_provided_address", this.mUserProvidedAddress);
    }
    if (!TextUtils.isEmpty(this.mDcbTosUrl))
    {
      paramBundle.putString("dcb_tos_url", this.mDcbTosUrl);
      paramBundle.putString("dcb_tos_version", this.mDcbTosVersion);
    }
    if (!TextUtils.isEmpty(this.mPiiTosUrl))
    {
      paramBundle.putString("pii_tos_version", this.mPiiTosVersion);
      paramBundle.putString("pii_tos_url", this.mPiiTosUrl);
    }
  }
  
  final void showError$78a511a1(String paramString1, String paramString2, boolean paramBoolean)
  {
    FinskyLog.w(paramString1, new Object[0]);
    showErrorDialog(paramString2, paramBoolean);
  }
  
  public final void start()
  {
    if (!initParams()) {
      return;
    }
    performNext();
  }
  
  final class AssociationListener
    implements Response.Listener<CarrierBilling.VerifyAssociationResponse>
  {
    AssociationListener() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.flow.CreateDcb3Flow
 * JD-Core Version:    0.7.0.1
 */