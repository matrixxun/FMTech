package com.google.android.finsky.billing.carrierbilling.flow;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.utils.AndroidKeyczarReader;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.InstrumentFlow;
import com.google.android.finsky.billing.carrierbilling.flow.association.AssociationAction;
import com.google.android.finsky.billing.carrierbilling.flow.association.CarrierOutAssociation;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingPasswordDialogFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierTosDialogFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierTosDialogFragment.CarrierTosResultListener;
import com.google.android.finsky.billing.carrierbilling.fragment.VerifyAssociationDialogFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.VerifyAssociationDialogFragment.VerifyAssociationListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.BuyInstruments.UpdateInstrumentRequest;
import com.google.android.finsky.protos.BuyInstruments.UpdateInstrumentResponse;
import com.google.android.finsky.protos.CarrierBilling.VerifyAssociationResponse;
import com.google.android.finsky.protos.CarrierBillingInstrument;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;
import com.google.android.finsky.protos.CarrierTos;
import com.google.android.finsky.protos.CarrierTosEntry;
import com.google.android.finsky.protos.DeviceAssociation;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.protos.PasswordPrompt;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FixBrokenCipherSpiProvider;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;
import org.keyczar.AesKey;
import org.keyczar.Crypter;
import org.keyczar.DefaultKeyType;
import org.keyczar.Encrypter;
import org.keyczar.ImportedKeyReader;
import org.keyczar.SessionMaterial;
import org.keyczar.SignedSessionEncrypter;
import org.keyczar.Signer;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.KeyType;
import org.keyczar.util.Base64Coder;
import org.keyczar.util.Util;

public final class CompleteDcb3Flow
  extends InstrumentFlow
  implements Response.ErrorListener, Response.Listener<CarrierBilling.VerifyAssociationResponse>, CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener, CarrierTosDialogFragment.CarrierTosResultListener, VerifyAssociationDialogFragment.VerifyAssociationListener
{
  private static final String DCB_PIN_SIGNING_KEYS = "keys" + File.separator + "dcb-pin-sign";
  private static final String GOOGLE_ENCRYPTION_KEYS_V1 = "keys" + File.separator + "dcb-pin-encrypt-v1";
  private static final String GOOGLE_ENCRYPTION_KEYS_V2 = "keys" + File.separator + "dcb-pin-encrypt-v2";
  private AssociationAction mAssociationAction;
  private String mAssociationAddress;
  private String mAssociationPrefix;
  private boolean mAssociationRequired;
  private String mCarrierName;
  private final BillingFlowContext mContext;
  private boolean mDcbTosAcceptanceRequired;
  private String mDcbTosUrl;
  private String mDcbTosVersion;
  private final DfeApi mDfeApi;
  private String mEncryptedPassword;
  private final FinskyEventLog mEventLog;
  private Instrument mInstrument;
  private boolean mInstrumentUpdateRequired;
  private String mPasswordForgotUrl;
  private CarrierBillingPasswordDialogFragment mPasswordFragment;
  private String mPasswordPrompt;
  private boolean mPasswordRequired;
  private int mState;
  private CarrierTosDialogFragment mTosFragment;
  private int mTosNumber = 0;
  private BuyInstruments.UpdateInstrumentResponse mUpdateInstrumentResponse;
  private VerifyAssociationDialogFragment mVerifyAssociationFragment;
  
  static {}
  
  public CompleteDcb3Flow(BillingFlowContext paramBillingFlowContext, DfeApi paramDfeApi, BillingFlowListener paramBillingFlowListener, FinskyEventLog paramFinskyEventLog, Bundle paramBundle)
  {
    super(paramBillingFlowContext, paramBillingFlowListener, paramBundle);
    this.mContext = paramBillingFlowContext;
    this.mDfeApi = paramDfeApi;
    this.mEventLog = paramFinskyEventLog;
    this.mState = 0;
    this.mInstrument = ((Instrument)ParcelableProto.getProtoFromBundle(paramBundle, "dcb_instrument"));
    if (this.mInstrument.carrierBillingStatus != null)
    {
      CarrierBillingInstrumentStatus localCarrierBillingInstrumentStatus = this.mInstrument.carrierBillingStatus;
      this.mPasswordRequired = localCarrierBillingInstrumentStatus.passwordRequired;
      if (localCarrierBillingInstrumentStatus.carrierPasswordPrompt != null)
      {
        this.mPasswordPrompt = localCarrierBillingInstrumentStatus.carrierPasswordPrompt.prompt;
        this.mPasswordForgotUrl = localCarrierBillingInstrumentStatus.carrierPasswordPrompt.forgotPasswordUrl;
      }
      this.mAssociationRequired = localCarrierBillingInstrumentStatus.associationRequired;
      if (localCarrierBillingInstrumentStatus.deviceAssociation != null)
      {
        this.mAssociationAddress = localCarrierBillingInstrumentStatus.deviceAssociation.userTokenRequestAddress;
        this.mAssociationPrefix = localCarrierBillingInstrumentStatus.deviceAssociation.userTokenRequestMessage;
      }
      if (localCarrierBillingInstrumentStatus.carrierTos != null)
      {
        this.mDcbTosAcceptanceRequired = localCarrierBillingInstrumentStatus.carrierTos.needsDcbTosAcceptance;
        if (this.mDcbTosAcceptanceRequired)
        {
          this.mDcbTosUrl = localCarrierBillingInstrumentStatus.carrierTos.dcbTos.url;
          this.mDcbTosVersion = localCarrierBillingInstrumentStatus.carrierTos.dcbTos.version;
        }
      }
      if (localCarrierBillingInstrumentStatus.hasName) {
        this.mCarrierName = localCarrierBillingInstrumentStatus.name;
      }
      if ((this.mDcbTosAcceptanceRequired) && (TextUtils.isEmpty(this.mCarrierName)))
      {
        FinskyLog.wtf("Carrier name is empty. Can't proceed with the flow.", new Object[0]);
        fail(FinskyApp.get().getString(2131362186));
      }
    }
  }
  
  private void createAndShowPasswordFragment()
  {
    this.mPasswordFragment = CarrierBillingPasswordDialogFragment.newInstance(this.mDfeApi.getAccountName(), this.mPasswordPrompt, this.mPasswordForgotUrl);
    this.mPasswordFragment.mListener = this;
    this.mBillingFlowContext.showDialogFragment(this.mPasswordFragment, "PasswordDialog");
  }
  
  private void dissmissPasswordFragment()
  {
    if (this.mPasswordFragment != null)
    {
      this.mPasswordFragment.dismissInternal(false);
      this.mPasswordFragment = null;
    }
  }
  
  private void hideVerifyAssociationDialog()
  {
    if (this.mVerifyAssociationFragment != null)
    {
      this.mVerifyAssociationFragment.dismissInternal(false);
      this.mVerifyAssociationFragment = null;
    }
  }
  
  public final void onCarrierBillingPasswordResult(int paramInt, String paramString, Context paramContext)
  {
    if (paramInt != 0) {
      dissmissPasswordFragment();
    }
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("enum %d", arrayOfObject2);
      return;
    case 0: 
      for (;;)
      {
        SignedSessionEncrypter localSignedSessionEncrypter;
        int j;
        try
        {
          int i = ((Integer)G.dcb3PassphraseKeyVersion.get()).intValue();
          switch (i)
          {
          case 0: 
          default: 
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = Integer.valueOf(i);
            FinskyLog.d("Unrecognized passphrase key version %d, using default", arrayOfObject1);
          case -1: 
          case 2: 
            str1 = GOOGLE_ENCRYPTION_KEYS_V2;
            FinskyLog.d("Using passphrase key path: %s", new Object[] { str1 });
            Resources localResources = paramContext.getResources();
            localSignedSessionEncrypter = new SignedSessionEncrypter(new Encrypter(new AndroidKeyczarReader(localResources, str1)), new Signer(new AndroidKeyczarReader(localResources, DCB_PIN_SIGNING_KEYS)));
            j = ((Integer)Collections.unmodifiableList(DefaultKeyType.AES.acceptableSizes).get(0)).intValue();
            if (DefaultKeyType.AES.isAcceptableSize(j)) {
              break label298;
            }
            throw new KeyczarException("Unsupported key size requested for session");
          }
        }
        catch (Exception localException)
        {
          FinskyLog.w("Exception thrown: %s", new Object[] { localException });
          if (TextUtils.isEmpty(this.mEncryptedPassword)) {
            fail(FinskyApp.get().getString(2131362186));
          }
          performNext();
          return;
        }
        String str1 = GOOGLE_ENCRYPTION_KEYS_V1;
        continue;
        AesKey localAesKey = AesKey.generate(j);
        byte[] arrayOfByte1 = new byte[16];
        Util.rand(arrayOfByte1);
        String str2 = Base64Coder.encodeWebSafe(arrayOfByte1);
        localSignedSessionEncrypter.session.set(new SessionMaterial(localAesKey, str2));
        String str3 = localSignedSessionEncrypter.encrypter.encrypt(((SessionMaterial)localSignedSessionEncrypter.session.get()).toString());
        byte[] arrayOfByte2 = paramString.getBytes();
        if (localSignedSessionEncrypter.session.get() == null) {
          throw new KeyczarException("Session not initialized.");
        }
        SessionMaterial localSessionMaterial = (SessionMaterial)localSignedSessionEncrypter.session.get();
        if (localSessionMaterial.key == null) {
          throw new KeyczarException("Key has not been initialized");
        }
        byte[] arrayOfByte3 = new Crypter(new ImportedKeyReader(localSessionMaterial.key)).encrypt(arrayOfByte2);
        byte[] arrayOfByte4 = localSignedSessionEncrypter.signer.attachedSign(arrayOfByte3, Base64Coder.decodeWebSafe(localSessionMaterial.nonce));
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("efeData", str3);
        localJSONObject.put("fieldData", Base64Coder.encodeWebSafe(arrayOfByte4));
        this.mEncryptedPassword = localJSONObject.toString();
      }
    case 2: 
      label298:
      cancel();
      return;
    }
    FinskyLog.d("Getting password info failed.", new Object[0]);
    fail(FinskyApp.get().getString(2131362186));
  }
  
  public final void onCarrierTosResult(int paramInt)
  {
    if (this.mTosFragment != null)
    {
      this.mTosFragment.dismissInternal(false);
      this.mTosFragment = null;
    }
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("enum %d", arrayOfObject);
      return;
    case 0: 
      this.mInstrumentUpdateRequired = true;
      this.mDcbTosAcceptanceRequired = false;
      performNext();
      return;
    case 1: 
      FinskyLog.d("Showing TOS to user failed.", new Object[0]);
      fail(FinskyApp.get().getString(2131362186));
      return;
    }
    cancel();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    dissmissPasswordFragment();
    if (this.mAssociationAction != null) {
      this.mAssociationAction.cancel();
    }
    hideVerifyAssociationDialog();
    fail(FinskyApp.get().getString(2131362186));
  }
  
  public final void onVerifyCancel()
  {
    if (this.mAssociationAction != null) {
      this.mAssociationAction.cancel();
    }
    cancel();
  }
  
  protected final void performNext()
  {
    while (this.mState == 0)
    {
      if (this.mAssociationRequired)
      {
        this.mVerifyAssociationFragment = VerifyAssociationDialogFragment.newInstance(this.mDfeApi.getAccountName());
        this.mVerifyAssociationFragment.mListener = this;
        this.mContext.showDialogFragment(this.mVerifyAssociationFragment, "verifying pin");
        this.mAssociationAction = new CarrierOutAssociation(this.mDfeApi, this.mAssociationAddress, this.mAssociationPrefix, null, false);
        this.mAssociationAction.start(this, this);
        return;
      }
      this.mState = 1;
    }
    if (this.mState == 1)
    {
      if (this.mDcbTosAcceptanceRequired)
      {
        String str = this.mDcbTosUrl;
        this.mTosFragment = CarrierTosDialogFragment.newInstance(this.mDfeApi.getAccountName(), str, this.mCarrierName);
        this.mTosFragment.mListener = this;
        this.mBillingFlowContext.showDialogFragment(this.mTosFragment, "TosDialog" + this.mTosNumber);
        this.mTosNumber = (1 + this.mTosNumber);
        return;
      }
      if (this.mInstrumentUpdateRequired)
      {
        this.mState = 3;
        this.mContext.showProgress(2131362711);
        CarrierBillingInstrument localCarrierBillingInstrument = new CarrierBillingInstrument();
        localCarrierBillingInstrument.instrumentKey = BillingLocator.getSimIdentifier();
        localCarrierBillingInstrument.hasInstrumentKey = true;
        if (!TextUtils.isEmpty(this.mDcbTosVersion))
        {
          CarrierTos localCarrierTos = new CarrierTos();
          localCarrierTos.dcbTos = new CarrierTosEntry();
          localCarrierTos.dcbTos.version = this.mDcbTosVersion;
          localCarrierTos.dcbTos.hasVersion = true;
          localCarrierBillingInstrument.acceptedCarrierTos = localCarrierTos;
        }
        BuyInstruments.UpdateInstrumentRequest localUpdateInstrumentRequest = new BuyInstruments.UpdateInstrumentRequest();
        localUpdateInstrumentRequest.instrument = this.mInstrument;
        localUpdateInstrumentRequest.instrument.carrierBilling = localCarrierBillingInstrument;
        this.mDfeApi.updateInstrument(localUpdateInstrumentRequest, new Response.Listener() {}, this);
        return;
      }
      if (this.mPasswordRequired)
      {
        this.mState = 2;
        createAndShowPasswordFragment();
        return;
      }
      finish(null);
      return;
    }
    if (this.mState == 3)
    {
      this.mContext.hideProgress();
      if (this.mUpdateInstrumentResponse == null)
      {
        FinskyLog.w("Failed to get update instrument response.", new Object[0]);
        fail(FinskyApp.get().getString(2131362186));
        return;
      }
      if (this.mUpdateInstrumentResponse.result == 0)
      {
        if (this.mPasswordRequired)
        {
          this.mState = 2;
          createAndShowPasswordFragment();
          return;
        }
        finish(null);
        return;
      }
      FinskyLog.w("Updating DCB instrument failed.", new Object[0]);
      fail(FinskyApp.get().getString(2131362186));
      return;
    }
    if (this.mState == 2)
    {
      dissmissPasswordFragment();
      Bundle localBundle = new Bundle();
      localBundle.putString("encrypted_passphrase", this.mEncryptedPassword);
      finish(localBundle);
      return;
    }
    throw new IllegalStateException("Unexpected state: " + this.mState);
  }
  
  public final void resumeFromSavedState(Bundle paramBundle)
  {
    if (this.mState != 0) {
      throw new IllegalStateException();
    }
    this.mState = paramBundle.getInt("state");
    if (this.mState == 3)
    {
      if (!this.mPasswordRequired) {
        break label255;
      }
      this.mState = 2;
      createAndShowPasswordFragment();
    }
    for (;;)
    {
      this.mDcbTosAcceptanceRequired = paramBundle.getBoolean("tos_required");
      if (paramBundle.containsKey("tos_version")) {
        this.mDcbTosVersion = paramBundle.getString("tos_version");
      }
      if (paramBundle.containsKey("tos_url")) {
        this.mDcbTosUrl = paramBundle.getString("tos_url");
      }
      if (paramBundle.containsKey("tos_fragment"))
      {
        this.mTosFragment = ((CarrierTosDialogFragment)this.mContext.restoreFragment(paramBundle, "tos_fragment"));
        this.mTosFragment.mListener = this;
      }
      if (paramBundle.containsKey("password_fragment"))
      {
        this.mPasswordFragment = ((CarrierBillingPasswordDialogFragment)this.mContext.restoreFragment(paramBundle, "password_fragment"));
        this.mPasswordFragment.mListener = this;
      }
      if (paramBundle.containsKey("verify_association_dialog"))
      {
        this.mVerifyAssociationFragment = ((VerifyAssociationDialogFragment)this.mContext.restoreFragment(paramBundle, "verify_association_dialog"));
        this.mVerifyAssociationFragment.mListener = this;
        this.mAssociationAction = new CarrierOutAssociation(this.mDfeApi, this.mAssociationAddress, this.mAssociationPrefix, null, false);
        this.mAssociationAction.resumeState(paramBundle, this, this);
      }
      return;
      label255:
      finish(null);
    }
  }
  
  public final void saveState(Bundle paramBundle)
  {
    paramBundle.putInt("state", this.mState);
    if (this.mTosFragment != null) {
      this.mContext.persistFragment(paramBundle, "tos_fragment", this.mTosFragment);
    }
    if (this.mPasswordFragment != null) {
      this.mContext.persistFragment(paramBundle, "password_fragment", this.mPasswordFragment);
    }
    if (this.mVerifyAssociationFragment != null)
    {
      this.mContext.persistFragment(paramBundle, "verify_association_dialog", this.mVerifyAssociationFragment);
      if (this.mAssociationAction != null)
      {
        this.mAssociationAction.saveState(paramBundle);
        this.mAssociationAction.cancel();
      }
    }
    paramBundle.putBoolean("tos_required", this.mDcbTosAcceptanceRequired);
    if (this.mDcbTosAcceptanceRequired)
    {
      paramBundle.putString("tos_version", this.mDcbTosVersion);
      paramBundle.putString("tos_url", this.mDcbTosUrl);
    }
  }
  
  public final void start()
  {
    performNext();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.flow.CompleteDcb3Flow
 * JD-Core Version:    0.7.0.1
 */