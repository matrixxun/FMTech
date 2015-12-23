package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.android.volley.RequestQueue;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.auth.AuthApi;
import com.google.android.finsky.auth.AuthApi.1;
import com.google.android.finsky.auth.AuthResponseListener;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.billing.challenge.ClientLoginApi;
import com.google.android.finsky.billing.challenge.ClientLoginApi.1;
import com.google.android.finsky.billing.challenge.ClientLoginApi.2;
import com.google.android.finsky.billing.challenge.ClientLoginApi.ClientLoginRequest;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.ChallengeProto.FamilyWalletAuthChallenge.Approver;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.utils.config.GservicesValue;
import java.io.IOException;

public final class AuthChallengeSidecar
  extends SidecarFragment
  implements AuthResponseListener
{
  private String mAccountName;
  private AuthState mAuthState;
  public String mErrorMessage;
  private FinskyEventLog mEventLog;
  private int mFailedCount;
  private final int mMaxFailedAttempt = ((Integer)G.passwordMaxFailedAttempts.get()).intValue();
  
  private void fail(int paramInt1, int paramInt2)
  {
    logBackgroundEvent$2563266(false);
    this.mErrorMessage = getString(paramInt1);
    if (paramInt2 != 1)
    {
      this.mFailedCount = (1 + this.mFailedCount);
      if (this.mFailedCount >= this.mMaxFailedAttempt) {
        paramInt2 = 4;
      }
    }
    setState(3, paramInt2);
  }
  
  private void logBackgroundEvent$2563266(boolean paramBoolean)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(508).setOperationSuccess(paramBoolean).setAuthContext(this.mAuthState.getAuthContextForLogging());
    this.mEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  public static AuthChallengeSidecar newInstance(String paramString, AuthState paramAuthState)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("AuthChallengeSidecar.authState", paramAuthState);
    AuthChallengeSidecar localAuthChallengeSidecar = new AuthChallengeSidecar();
    localAuthChallengeSidecar.setArguments(localBundle);
    return localAuthChallengeSidecar;
  }
  
  public final void confirmCredentials(String paramString1, String paramString2)
  {
    Account localAccount = AccountHandler.findAccount(paramString1, FinskyApp.get());
    AccountManager localAccountManager = AccountManager.get(FinskyApp.get());
    Bundle localBundle = new Bundle();
    localBundle.putString("password", paramString2);
    localAccountManager.confirmCredentials(localAccount, localBundle, getActivity(), new AccountManagerCallback()
    {
      public final void run(AccountManagerFuture<Bundle> paramAnonymousAccountManagerFuture)
      {
        try
        {
          if (((Bundle)paramAnonymousAccountManagerFuture.getResult()).getBoolean("booleanResult"))
          {
            AuthChallengeSidecar.this.succeed();
            return;
          }
          AuthChallengeSidecar.this.fail();
          return;
        }
        catch (OperationCanceledException localOperationCanceledException)
        {
          FinskyLog.w("OperationCanceledException: %s", new Object[] { localOperationCanceledException });
          AuthChallengeSidecar.this.fail();
          return;
        }
        catch (IOException localIOException)
        {
          FinskyLog.w("IOException: %s", new Object[] { localIOException });
          AuthChallengeSidecar.this.fail();
          return;
        }
        catch (AuthenticatorException localAuthenticatorException)
        {
          FinskyLog.w("AuthenticatorException: %s", new Object[] { localAuthenticatorException });
          AuthChallengeSidecar.this.fail();
        }
      }
    }, null);
  }
  
  public final void onAuthFailure(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      fail(2131362187, 0);
      return;
    case 1200: 
    case 1202: 
      succeed();
      return;
    case 1100: 
      fail(2131362257, 2);
      return;
    case 1003: 
      fail(2131362259, 3);
      return;
    }
    fail(2131362187, 1);
  }
  
  public final void onAuthSuccess()
  {
    succeed();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.mAuthState = ((AuthState)this.mArguments.getParcelable("AuthChallengeSidecar.authState"));
    this.mAccountName = this.mArguments.getString("authAccount");
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccountName);
    this.mFailedCount = 0;
    super.onCreate(paramBundle);
  }
  
  public final void submitResponse(String paramString1, String paramString2, ChallengeProto.FamilyWalletAuthChallenge.Approver paramApprover)
  {
    boolean bool1 = true;
    Account localAccount = AccountHandler.findAccount(paramString1, FinskyApp.get());
    if (paramApprover != null) {}
    AuthApi localAuthApi;
    for (boolean bool2 = bool1;; bool2 = false)
    {
      localAuthApi = new AuthApi(localAccount, bool2);
      if (!TextUtils.isEmpty(paramString2)) {
        break label73;
      }
      if (this.mAuthState.getAuthMethod() != 2) {
        break;
      }
      fail(2131361905, 3);
      return;
    }
    fail(2131361904, 2);
    return;
    label73:
    boolean bool3;
    if (this.mAuthState.getAuthMethod() == 2)
    {
      bool3 = bool1;
      if (!bool3)
      {
        if ((localAuthApi.mReauthClient != null) && (AuthApi.shouldUseReauthApi(localAuthApi.mUseDelegatedAuth, localAuthApi.mAccount.name))) {
          break label164;
        }
        bool1 = false;
        label123:
        if (!bool1) {
          break label239;
        }
      }
      if (paramApprover != null) {
        break label230;
      }
    }
    label164:
    label230:
    for (String str1 = null;; str1 = paramApprover.obfuscatedGaiaId)
    {
      Utils.executeMultiThreaded(new AuthApi.1(localAuthApi, paramString2, bool3, str1, this), new Void[0]);
      return;
      bool3 = false;
      break;
      FinskyApp localFinskyApp = FinskyApp.get();
      if (GooglePlayServicesUtil.isSidewinderDevice(localFinskyApp)) {
        break label123;
      }
      FinskyExperiments localFinskyExperiments = localFinskyApp.getExperiments(localAuthApi.mAccount.name);
      if (localAuthApi.mUseDelegatedAuth)
      {
        if (!localFinskyExperiments.isEnabled(12604372L)) {
          break label123;
        }
        bool1 = false;
        break label123;
      }
      bool1 = localFinskyExperiments.isEnabled(12603111L);
      break label123;
    }
    label239:
    if (paramApprover == null) {}
    for (String str2 = localAuthApi.mAccount.name;; str2 = paramApprover.emailAddress)
    {
      ClientLoginApi localClientLoginApi = localAuthApi.mClientLoginApi;
      ClientLoginApi.ClientLoginRequest localClientLoginRequest = new ClientLoginApi.ClientLoginRequest(localClientLoginApi, ClientLoginApi.CLIENT_LOGIN_URI, str2, paramString2, new ClientLoginApi.1(localClientLoginApi, this), new ClientLoginApi.2(localClientLoginApi, this));
      localClientLoginApi.mQueue.add(localClientLoginRequest);
      return;
    }
  }
  
  void succeed()
  {
    logBackgroundEvent$2563266(true);
    this.mErrorMessage = "";
    AuthApi.recordFingerprintKey(this.mAccountName);
    setState(2, 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.AuthChallengeSidecar
 * JD-Core Version:    0.7.0.1
 */