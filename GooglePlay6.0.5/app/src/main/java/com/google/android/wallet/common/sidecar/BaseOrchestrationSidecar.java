package com.google.android.wallet.common.sidecar;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.api.WalletRequestQueue;
import com.google.android.wallet.common.api.http.ApiContext;
import com.google.android.wallet.common.api.http.AuthHandlingRetryPolicy;
import com.google.android.wallet.common.api.http.BackgroundEventRequest;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.config.G;
import com.google.android.wallet.config.G.dcb;
import com.google.commerce.payments.orchestration.proto.ui.common.ResponseContextOuterClass.ResponseContext;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.UiError;
import com.google.moneta.orchestration.ui.common.ClientEnvironmentConfig.AndroidEnvironmentConfig;
import java.util.ArrayList;
import java.util.Locale;

public abstract class BaseOrchestrationSidecar
  extends SidecarFragment
{
  public ApiContext mApiContext;
  boolean mAttemptedToHandleAuth = false;
  int mAttempts = 1;
  public ArrayList<Request<?>> mQueuedRequests;
  public RequestQueue mRequestQueue;
  
  public static Bundle createArgs(Account paramAccount, ClientEnvironmentConfig.AndroidEnvironmentConfig paramAndroidEnvironmentConfig)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("account", paramAccount);
    localBundle.putParcelable("androidConfig", ParcelableProto.forProto(paramAndroidEnvironmentConfig));
    return localBundle;
  }
  
  public abstract void clearPreviousResponses();
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 100)
    {
      if (paramInt2 == -1)
      {
        this.mAttemptedToHandleAuth = true;
        setState(1, 0);
        if (this.mQueuedRequests != null)
        {
          int i = this.mQueuedRequests.size();
          for (int j = 0; j < i; j++) {
            sendRequest((Request)this.mQueuedRequests.get(j), true);
          }
          this.mQueuedRequests.clear();
        }
        return;
      }
      setState(3, 1);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    ClientEnvironmentConfig.AndroidEnvironmentConfig localAndroidEnvironmentConfig = (ClientEnvironmentConfig.AndroidEnvironmentConfig)ParcelableProto.getProtoFromBundle(localBundle, "androidConfig");
    Account localAccount = (Account)localBundle.getParcelable("account");
    FragmentActivity localFragmentActivity = getActivity();
    Context localContext = localFragmentActivity.getApplicationContext();
    String str1 = localAccount.name;
    String str2 = localAndroidEnvironmentConfig.accountName;
    if (GooglePlayServicesUtil.isSidewinderDevice(localFragmentActivity))
    {
      boolean bool1 = str1.contains("@");
      boolean bool2 = str2.contains("@");
      if (bool1 != bool2)
      {
        if (bool1) {
          str1 = str1.substring(0, str1.indexOf('@'));
        }
        if (bool2) {
          str2 = str2.substring(0, str2.indexOf('@'));
        }
      }
    }
    if ((!localAccount.name.equalsIgnoreCase(localAndroidEnvironmentConfig.accountName)) && (!str1.equalsIgnoreCase(str2)))
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localAccount.name;
      arrayOfObject[1] = localAndroidEnvironmentConfig.accountName;
      throw new IllegalArgumentException(String.format(localLocale, "Account specified by integrator=%s must match AndroidEnvironmentConfig=%s", arrayOfObject));
    }
    this.mApiContext = new ApiContext(localContext, localAndroidEnvironmentConfig, new AndroidAuthenticator(localContext, localAccount, localAndroidEnvironmentConfig.authTokenType));
    this.mRequestQueue = WalletRequestQueue.getApiRequestQueue(getActivity().getApplicationContext());
    super.onCreate(paramBundle);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("attemptedToHandleAuth", this.mAttemptedToHandleAuth);
    paramBundle.putInt("attempts", this.mAttempts);
  }
  
  public void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    this.mAttemptedToHandleAuth = paramBundle.getBoolean("attemptedToHandleAuth");
    this.mAttempts = paramBundle.getInt("attempts");
    super.restoreFromSavedInstanceState(paramBundle);
  }
  
  public void sendRequest(Request<?> paramRequest, boolean paramBoolean)
  {
    clearPreviousResponses();
    if (paramBoolean) {
      this.mAttempts = 1;
    }
    if ((this.mState == 1) && (this.mSubstate == 1)) {}
    for (int i = 1; i != 0; i = 0)
    {
      if (this.mQueuedRequests == null) {
        this.mQueuedRequests = new ArrayList(2);
      }
      this.mQueuedRequests.add(paramRequest);
      return;
    }
    ApiContext localApiContext = this.mApiContext;
    paramRequest.mRetryPolicy = new AuthHandlingRetryPolicy(((Integer)G.volleyApiRequestDefaultTimeoutMs.get()).intValue(), localApiContext);
    this.mRequestQueue.add(paramRequest);
    setState(1, 0);
  }
  
  public boolean shouldRetryOnNoConnectionErrors(int paramInt)
  {
    return false;
  }
  
  public final void updateStateAndSendAnalyticsEvent(BackgroundEventRequest<?> paramBackgroundEventRequest, ResponseContextOuterClass.ResponseContext paramResponseContext, UiErrorOuterClass.UiError paramUiError)
  {
    int i;
    if (paramUiError == null)
    {
      setState(2, 0);
      i = 0;
    }
    for (;;)
    {
      AnalyticsUtil.createAndSendResponseReceivedBackgroundEvent(paramBackgroundEventRequest.getBackgroundEventReceivedType(), i, null, paramBackgroundEventRequest.getClientLatencyMs(), paramResponseContext.responseTimeMillis, paramResponseContext.logToken);
      return;
      if (!TextUtils.isEmpty(paramUiError.internalDetails)) {
        Log.e("BaseOrchSidecar", paramUiError.internalDetails);
      }
      if (!TextUtils.isEmpty(paramUiError.message))
      {
        setState(3, 5);
        i = 3;
      }
      else
      {
        if ((paramUiError.action != 1) || (paramUiError.formFieldMessage.length <= 0)) {
          break;
        }
        setState(3, 4);
        i = 2;
      }
    }
    throw new IllegalArgumentException("No error found in error response");
  }
  
  public class OrchestrationErrorListener
    implements Response.ErrorListener
  {
    private final byte[] mPreviousResponseLogToken;
    public BackgroundEventRequest<?> mRequest;
    
    public OrchestrationErrorListener(byte[] paramArrayOfByte)
    {
      this.mPreviousResponseLogToken = paramArrayOfByte;
    }
    
    public void onErrorResponse(VolleyError paramVolleyError)
    {
      if (((paramVolleyError instanceof AuthFailureError)) && (!BaseOrchestrationSidecar.this.mAttemptedToHandleAuth))
      {
        Intent localIntent = ((AuthFailureError)paramVolleyError).mResolutionIntent;
        if (localIntent != null)
        {
          localIntent.setFlags(0xEFFFFFFF & localIntent.getFlags());
          BaseOrchestrationSidecar.this.startActivityForResult(localIntent, 100);
          BaseOrchestrationSidecar.this.setState(1, 1);
          BaseOrchestrationSidecar.this.sendRequest(this.mRequest, true);
          return;
        }
        BaseOrchestrationSidecar.this.setState(3, 1);
      }
      for (;;)
      {
        AnalyticsUtil.createAndSendResponseReceivedBackgroundEvent(this.mRequest.getBackgroundEventReceivedType(), 1, paramVolleyError.getClass().getSimpleName(), this.mRequest.getClientLatencyMs(), -1L, this.mPreviousResponseLogToken);
        return;
        if (((paramVolleyError instanceof NoConnectionError)) && (BaseOrchestrationSidecar.this.shouldRetryOnNoConnectionErrors(BaseOrchestrationSidecar.this.mAttempts)))
        {
          BaseOrchestrationSidecar localBaseOrchestrationSidecar = BaseOrchestrationSidecar.this;
          localBaseOrchestrationSidecar.mAttempts = (1 + localBaseOrchestrationSidecar.mAttempts);
          AnalyticsUtil.createAndSendRequestSentBackgroundEvent(this.mRequest.getBackgroundEventSentType(), BaseOrchestrationSidecar.this.mAttempts, this.mPreviousResponseLogToken);
          new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
          {
            public final void run()
            {
              BaseOrchestrationSidecar.access$100$483d39e9(BaseOrchestrationSidecar.this, BaseOrchestrationSidecar.OrchestrationErrorListener.this.mRequest);
            }
          }, ((Integer)G.dcb.verifyAssociationRetryDelayMs.get()).intValue());
          return;
        }
        if ((paramVolleyError instanceof ServerError))
        {
          BaseOrchestrationSidecar.this.setState(3, 3);
        }
        else if (((paramVolleyError instanceof NetworkError)) || ((paramVolleyError instanceof TimeoutError)))
        {
          BaseOrchestrationSidecar.this.setState(3, 2);
        }
        else if ((paramVolleyError instanceof AuthFailureError))
        {
          BaseOrchestrationSidecar.this.setState(3, 1);
        }
        else
        {
          Log.i("BaseOrchSidecar", "Unexpected error returned from Volley", paramVolleyError);
          BaseOrchestrationSidecar.this.setState(3, 3);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.sidecar.BaseOrchestrationSidecar
 * JD-Core Version:    0.7.0.1
 */