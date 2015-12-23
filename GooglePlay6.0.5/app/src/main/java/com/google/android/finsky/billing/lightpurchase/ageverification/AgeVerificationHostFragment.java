package com.google.android.finsky.billing.lightpurchase.ageverification;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.billing.ProgressSpinnerFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.protos.ChallengeProto.AgeChallenge;
import com.google.android.finsky.protos.ChallengeProto.Challenge;
import com.google.android.finsky.protos.ChallengeProto.ChallengeError;
import com.google.android.finsky.protos.ChallengeProto.FormButton;
import com.google.android.finsky.protos.ChallengeProto.SmsCodeChallenge;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Map;

public final class AgeVerificationHostFragment
  extends Fragment
  implements AgeChallengeFragment.Listener, ChallengeErrorFragment.Listener, SmsCodeFragment.Listener, SidecarFragment.Listener
{
  private String mAccountName;
  private int mBackend;
  private String mDocidStr;
  private int mLastStateInstance;
  private ProgressSpinnerFragment mProgressSpinnerFragment;
  private AgeVerificationSidecar mSidecar;
  
  private void handleSuccess(boolean paramBoolean)
  {
    if (paramBoolean) {
      FinskyApp.get().getClientMutationCache(this.mAccountName).mAgeVerificationRequired = Boolean.valueOf(false);
    }
    ((Listener)getActivity()).onFinished(true);
  }
  
  public static Fragment newInstance(String paramString1, int paramInt, String paramString2)
  {
    AgeVerificationHostFragment localAgeVerificationHostFragment = new AgeVerificationHostFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString1);
    localBundle.putInt("AgeVerificationHostFragment.backend", paramInt);
    localBundle.putString("AgeVerificationHostFragment.docid_str", paramString2);
    localAgeVerificationHostFragment.setArguments(localBundle);
    return localAgeVerificationHostFragment;
  }
  
  private void showFragment(Fragment paramFragment)
  {
    FragmentTransaction localFragmentTransaction = getChildFragmentManager().beginTransaction();
    localFragmentTransaction.replace(2131755234, paramFragment);
    localFragmentTransaction.setTransition$9d93138();
    localFragmentTransaction.commit();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccountName = this.mArguments.getString("authAccount");
    this.mBackend = this.mArguments.getInt("AgeVerificationHostFragment.backend");
    this.mDocidStr = this.mArguments.getString("AgeVerificationHostFragment.docid_str");
    if (paramBundle != null) {
      this.mLastStateInstance = paramBundle.getInt("AgeVerificationHostFragment.last_state_instance");
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130968613, paramViewGroup, false);
  }
  
  public final void onFail()
  {
    ((Listener)getActivity()).onFinished(false);
  }
  
  public final void onPause()
  {
    super.onPause();
    this.mSidecar.setListener(null);
  }
  
  public final void onResendSmsCode(String paramString)
  {
    AgeVerificationSidecar localAgeVerificationSidecar = this.mSidecar;
    localAgeVerificationSidecar.mDfeApi.resendAgeVerificationCode(paramString, localAgeVerificationSidecar, localAgeVerificationSidecar);
    localAgeVerificationSidecar.setState(1, 0);
  }
  
  public final void onResume()
  {
    super.onResume();
    this.mSidecar.setListener(this);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("AgeVerificationHostFragment.last_state_instance", this.mLastStateInstance);
  }
  
  public final void onStart()
  {
    super.onStart();
    this.mSidecar = ((AgeVerificationSidecar)this.mFragmentManager.findFragmentByTag("AgeVerificationHostFragment.sidecar"));
    if (this.mSidecar == null)
    {
      String str = this.mAccountName;
      AgeVerificationSidecar localAgeVerificationSidecar = new AgeVerificationSidecar();
      Bundle localBundle = new Bundle();
      localBundle.putString("authAccount", str);
      localAgeVerificationSidecar.setArguments(localBundle);
      this.mSidecar = localAgeVerificationSidecar;
      this.mFragmentManager.beginTransaction().add(this.mSidecar, "AgeVerificationHostFragment.sidecar").commit();
    }
  }
  
  public final void onStartChallenge(ChallengeProto.Challenge paramChallenge)
  {
    AgeVerificationSidecar localAgeVerificationSidecar = this.mSidecar;
    localAgeVerificationSidecar.mLastChallenge = paramChallenge;
    if (localAgeVerificationSidecar.mLastChallenge.ageChallenge != null)
    {
      localAgeVerificationSidecar.setState(5, 0);
      return;
    }
    if (localAgeVerificationSidecar.mLastChallenge.smsCodeChallenge != null)
    {
      localAgeVerificationSidecar.setState(6, 0);
      return;
    }
    throw new IllegalStateException("Received unknown challenge.");
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    if (this.mSidecar.mStateInstance <= this.mLastStateInstance)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mLastStateInstance);
      FinskyLog.d("Already received state instance %d, ignore.", arrayOfObject);
      return;
    }
    this.mLastStateInstance = this.mSidecar.mStateInstance;
    switch (this.mSidecar.mState)
    {
    default: 
      FinskyLog.wtf("Unexpected state: " + this.mSidecar.mState, new Object[0]);
      return;
    case 0: 
      AgeVerificationSidecar localAgeVerificationSidecar6 = this.mSidecar;
      String str2 = this.mDocidStr;
      if (str2 == null)
      {
        localAgeVerificationSidecar6.setState(4, 0);
        return;
      }
      localAgeVerificationSidecar6.mDfeDetails = new DfeDetails(localAgeVerificationSidecar6.mDfeApi, DfeUtils.createDetailsUrlFromId(str2));
      localAgeVerificationSidecar6.mDfeDetails.addDataChangedListener(localAgeVerificationSidecar6);
      localAgeVerificationSidecar6.mDfeDetails.addErrorListener(localAgeVerificationSidecar6);
      localAgeVerificationSidecar6.setState(1, 0);
      return;
    case 4: 
      AgeVerificationSidecar localAgeVerificationSidecar5 = this.mSidecar;
      localAgeVerificationSidecar5.mDfeApi.requestAgeVerificationForm(localAgeVerificationSidecar5, localAgeVerificationSidecar5);
      localAgeVerificationSidecar5.setState(1, 0);
      return;
    case 1: 
      if (this.mProgressSpinnerFragment == null) {
        this.mProgressSpinnerFragment = new ProgressSpinnerFragment();
      }
      showFragment(this.mProgressSpinnerFragment);
      return;
    case 5: 
      AgeVerificationSidecar localAgeVerificationSidecar4 = this.mSidecar;
      if (localAgeVerificationSidecar4.mState != 5) {
        throw new IllegalStateException("Invalid state: " + localAgeVerificationSidecar4.mState);
      }
      ChallengeProto.AgeChallenge localAgeChallenge = localAgeVerificationSidecar4.mLastChallenge.ageChallenge;
      showFragment(AgeChallengeFragment.newInstance(this.mAccountName, this.mBackend, localAgeChallenge));
      return;
    case 6: 
      AgeVerificationSidecar localAgeVerificationSidecar3 = this.mSidecar;
      if (localAgeVerificationSidecar3.mState != 6) {
        throw new IllegalStateException("Invalid state: " + localAgeVerificationSidecar3.mState);
      }
      ChallengeProto.SmsCodeChallenge localSmsCodeChallenge = localAgeVerificationSidecar3.mLastChallenge.smsCodeChallenge;
      showFragment(SmsCodeFragment.newInstance(this.mAccountName, this.mBackend, localSmsCodeChallenge));
      return;
    case 3: 
      ChallengeProto.ChallengeError localChallengeError;
      if (this.mSidecar.mSubstate == 1)
      {
        AgeVerificationSidecar localAgeVerificationSidecar2 = this.mSidecar;
        if ((localAgeVerificationSidecar2.mState != 3) || (localAgeVerificationSidecar2.mSubstate != 1)) {
          throw new IllegalStateException("Invalid state: " + localAgeVerificationSidecar2.mState + " with substate: " + localAgeVerificationSidecar2.mSubstate);
        }
        localChallengeError = localAgeVerificationSidecar2.mLastChallenge.error;
      }
      for (;;)
      {
        showFragment(ChallengeErrorFragment.newInstance(this.mAccountName, this.mBackend, localChallengeError));
        return;
        AgeVerificationSidecar localAgeVerificationSidecar1 = this.mSidecar;
        if ((localAgeVerificationSidecar1.mState != 3) || (localAgeVerificationSidecar1.mSubstate == 1)) {
          throw new IllegalStateException("Invalid state: " + localAgeVerificationSidecar1.mState + " with substate: " + localAgeVerificationSidecar1.mSubstate);
        }
        String str1 = localAgeVerificationSidecar1.mErrorHtml;
        Resources localResources = getActivity().getResources();
        ChallengeProto.FormButton localFormButton = new ChallengeProto.FormButton();
        localFormButton.label = localResources.getString(2131362418);
        localFormButton.actionFailChallenge = true;
        localChallengeError = new ChallengeProto.ChallengeError();
        localChallengeError.title = localResources.getString(2131362123);
        localChallengeError.descriptionHtml = str1;
        localChallengeError.submitButton = localFormButton;
      }
    case 2: 
      handleSuccess(true);
      return;
    }
    handleSuccess(false);
  }
  
  public final void onSubmit(String paramString, Map<String, String> paramMap)
  {
    AgeVerificationSidecar localAgeVerificationSidecar = this.mSidecar;
    localAgeVerificationSidecar.mDfeApi.verifyAge(paramString, paramMap, localAgeVerificationSidecar, localAgeVerificationSidecar);
    localAgeVerificationSidecar.setState(1, 0);
  }
  
  public final void onVerifySmsCode(String paramString1, String paramString2, String paramString3)
  {
    AgeVerificationSidecar localAgeVerificationSidecar = this.mSidecar;
    localAgeVerificationSidecar.mDfeApi.verifyAgeVerificationCode(paramString1, paramString2, paramString3, localAgeVerificationSidecar, localAgeVerificationSidecar);
    localAgeVerificationSidecar.setState(1, 0);
  }
  
  public static abstract interface Listener
  {
    public abstract void onFinished(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.ageverification.AgeVerificationHostFragment
 * JD-Core Version:    0.7.0.1
 */