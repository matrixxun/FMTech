package com.google.android.finsky.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.android.volley.Request;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.auth.AuthState;
import com.google.android.finsky.billing.lightpurchase.AuthChallengeSidecar;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;

public final class GaiaAuthFragment
  extends Fragment
  implements View.OnClickListener, SimpleAlertDialog.Listener, SidecarFragment.Listener
{
  private String mAccountName;
  private Request<?> mAuthRequest = null;
  private AuthState mAuthState;
  private FinskyEventLog mEventLogger;
  private int mFailedCount;
  private EditText mInput;
  public Listener mListener;
  private View mMainView;
  private PlayStoreUiElementNode mNode = new GenericUiElementNode(314, null, null, null);
  private boolean mShowWarning;
  private AuthChallengeSidecar mSidecar;
  
  private void failure()
  {
    this.mFailedCount = (1 + this.mFailedCount);
    logBackgroundEvent$2563266(false);
    if (this.mFailedCount >= ((Integer)G.passwordMaxFailedAttempts.get()).intValue())
    {
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      int i = this.mAuthState.getMaxAttemptExceededResourceId();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mAuthState.getRecoveryUrl(this.mAccountName);
      localBuilder.setMessageHtml(getString(i, arrayOfObject)).setPositiveId(2131362418).setCallback(this, 1, null);
      localBuilder.build().show(this.mFragmentManager, "GaiaAuthFragment.errorDialog");
      return;
    }
    this.mInput.setText("");
    this.mInput.setEnabled(true);
    UiUtils.showKeyboard(getActivity(), this.mInput);
    EditText localEditText = this.mInput;
    AuthState localAuthState = this.mAuthState;
    if (localAuthState.mUseFingerprintAuth) {
      throw new IllegalStateException("Call for fingerprint is not supported");
    }
    if (localAuthState.mUsePinBasedAuth) {}
    for (int j = 2131362194;; j = 2131362193)
    {
      UiUtils.setErrorOnTextView(localEditText, getString(j), this.mSidecar.mErrorMessage);
      return;
    }
  }
  
  private void logBackgroundEvent$2563266(boolean paramBoolean)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(502).setOperationSuccess(paramBoolean).setAuthContext(this.mAuthState.getAuthContextForLogging());
    this.mEventLogger.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  private void logClick(int paramInt, boolean paramBoolean)
  {
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
    if (paramBoolean)
    {
      localPlayStoreUiElementInfo.isImeAction = true;
      localPlayStoreUiElementInfo.hasIsImeAction = true;
    }
    localPlayStoreUiElementInfo.authContext = this.mAuthState.getAuthContextForLogging();
    this.mEventLogger.logClickEventWithClientCookie(paramInt, localPlayStoreUiElementInfo, this.mNode);
  }
  
  private void logClickAndVerifyInput(boolean paramBoolean)
  {
    logClick(265, paramBoolean);
    String str = this.mInput.getText().toString();
    this.mInput.setEnabled(false);
    this.mSidecar.submitResponse(this.mAccountName, str, null);
  }
  
  public static GaiaAuthFragment newInstance(String paramString, boolean paramBoolean, AuthState paramAuthState)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("GaiaAuthFragment_authState", paramAuthState);
    localBundle.putBoolean("GaiaAuthFragment_showWarning", paramBoolean);
    GaiaAuthFragment localGaiaAuthFragment = new GaiaAuthFragment();
    localGaiaAuthFragment.setArguments(localBundle);
    return localGaiaAuthFragment;
  }
  
  private void success()
  {
    this.mInput.setEnabled(true);
    this.mInput.setError(null);
    if (this.mListener != null) {
      this.mListener.onSuccess$255f295();
    }
    logBackgroundEvent$2563266(true);
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 100)
    {
      if (paramInt2 == -1)
      {
        success();
        return;
      }
      failure();
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public final void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    do
    {
      return;
      logClickAndVerifyInput(false);
      return;
      logClick(266, false);
    } while (this.mListener == null);
    this.mListener.onCancel();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccountName = this.mArguments.getString("authAccount");
    this.mAuthState = ((AuthState)this.mArguments.getParcelable("GaiaAuthFragment_authState"));
    this.mShowWarning = this.mArguments.getBoolean("GaiaAuthFragment_showWarning");
    if (paramBundle != null) {
      this.mFailedCount = paramBundle.getInt("GaiaAuthFragment_retryCount");
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130968776, paramViewGroup, false);
    this.mMainView = localView;
    this.mEventLogger = FinskyApp.get().getEventLogger(this.mAccountName);
    if (paramBundle == null)
    {
      this.mNode.getPlayStoreUiElement().clientLogsCookie = new PlayStore.PlayStoreUiElementInfo();
      this.mNode.getPlayStoreUiElement().clientLogsCookie.authContext = this.mAuthState.getAuthContextForLogging();
      this.mEventLogger.logPathImpression(0L, this.mNode);
    }
    ((TextView)localView.findViewById(2131755271)).setText(Utils.getDisplayAccountName(this.mAccountName, getContext()));
    TextView localTextView1 = (TextView)localView.findViewById(2131755270);
    int i;
    TextView localTextView2;
    if (this.mAuthState.getAuthMethod() == 2)
    {
      i = 2131755101;
      this.mInput = ((EditText)localView.findViewById(i));
      localTextView1.setText(this.mAuthState.getTitleResourceId());
      this.mInput.setVisibility(0);
      this.mInput.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousInt == 6)
          {
            GaiaAuthFragment.access$000$c2990c9(GaiaAuthFragment.this);
            return true;
          }
          return false;
        }
      });
      localTextView2 = (TextView)localView.findViewById(2131755576);
      int j = this.mAuthState.getRecoveryMessageResourceId();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mAuthState.getRecoveryUrl(this.mAccountName);
      Spanned localSpanned = Html.fromHtml(getString(j, arrayOfObject1));
      if (localSpanned == null) {
        break label404;
      }
      localTextView2.setText(localSpanned);
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView2.setVisibility(0);
    }
    for (;;)
    {
      if (this.mShowWarning)
      {
        TextView localTextView3 = (TextView)localView.findViewById(2131755575);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = G.gaiaOptOutLearnMoreLink.get();
        localTextView3.setText(Html.fromHtml(getString(2131361867, arrayOfObject2)));
        localTextView3.setMovementMethod(LinkMovementMethod.getInstance());
        localTextView3.setVisibility(0);
      }
      Button localButton1 = (Button)localView.findViewById(2131755302);
      localButton1.setText(2131362418);
      localButton1.setOnClickListener(this);
      Button localButton2 = (Button)localView.findViewById(2131755301);
      localButton2.setText(2131361915);
      localButton2.setOnClickListener(this);
      return localView;
      i = 2131755057;
      break;
      label404:
      localTextView2.setVisibility(8);
    }
  }
  
  public final void onDestroyView()
  {
    if (this.mAuthRequest != null) {
      this.mAuthRequest.cancel();
    }
    super.onDestroyView();
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1) {
      this.mListener.onCancel();
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1) {
      this.mListener.onCancel();
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), getString(this.mAuthState.getTitleResourceId()), this.mMainView);
    UiUtils.showKeyboard(getActivity(), this.mInput);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("GaiaAuthFragment_retryCount", this.mFailedCount);
  }
  
  public final void onStart()
  {
    this.mSidecar = ((AuthChallengeSidecar)getActivity().getSupportFragmentManager().findFragmentByTag("AuthChallengeStep.sidecar"));
    if (this.mSidecar == null)
    {
      this.mSidecar = AuthChallengeSidecar.newInstance(this.mAccountName, this.mAuthState);
      getActivity().getSupportFragmentManager().beginTransaction().add(this.mSidecar, "AuthChallengeStep.sidecar").commit();
    }
    this.mSidecar.setListener(this);
    super.onStart();
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    int i = paramSidecarFragment.mStateInstance;
    if (FinskyLog.DEBUG)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramSidecarFragment.mState);
      arrayOfObject[1] = Integer.valueOf(i);
      FinskyLog.v("Received state change: state=%d, stateInstance=%d", arrayOfObject);
    }
    switch (this.mSidecar.mState)
    {
    default: 
      return;
    case 2: 
      success();
      return;
    }
    if (this.mSidecar.mSubstate == 1)
    {
      this.mSidecar.confirmCredentials(this.mAccountName, this.mInput.getText().toString());
      return;
    }
    failure();
  }
  
  public final void onStop()
  {
    if (this.mSidecar != null) {
      this.mSidecar.setListener(null);
    }
    super.onStop();
  }
  
  public static abstract interface Listener
  {
    public abstract void onCancel();
    
    public abstract void onSuccess$255f295();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.GaiaAuthFragment
 * JD-Core Version:    0.7.0.1
 */