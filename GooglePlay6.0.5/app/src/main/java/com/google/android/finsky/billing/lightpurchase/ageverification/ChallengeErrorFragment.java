package com.google.android.finsky.billing.lightpurchase.ageverification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.protos.ChallengeProto.Challenge;
import com.google.android.finsky.protos.ChallengeProto.ChallengeError;
import com.google.android.finsky.protos.ChallengeProto.FormButton;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.layout.PlayActionButton;

public final class ChallengeErrorFragment
  extends LoggingFragment
  implements View.OnClickListener
{
  private PlayActionButton mCancelButton;
  private ChallengeProto.ChallengeError mChallengeError;
  private View mMainView;
  private PlayActionButton mSubmitButton;
  
  private Listener getListener()
  {
    if ((this.mTarget instanceof Listener)) {
      return (Listener)this.mTarget;
    }
    if ((this.mParentFragment instanceof Listener)) {
      return (Listener)this.mParentFragment;
    }
    if ((getActivity() instanceof Listener)) {
      return (Listener)getActivity();
    }
    throw new IllegalStateException("No listener registered.");
  }
  
  public static ChallengeErrorFragment newInstance(String paramString, int paramInt, ChallengeProto.ChallengeError paramChallengeError)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putInt("ChallengeErrorFragment.backend", paramInt);
    localBundle.putParcelable("ChallengeErrorFragment.challenge", ParcelableProto.forProto(paramChallengeError));
    ChallengeErrorFragment localChallengeErrorFragment = new ChallengeErrorFragment();
    localChallengeErrorFragment.setArguments(localBundle);
    return localChallengeErrorFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1406;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mSubmitButton)
    {
      logClickEvent(1407);
      if (this.mChallengeError.submitButton.actionFailChallenge) {
        getListener().onFail();
      }
    }
    while (paramView != this.mCancelButton)
    {
      return;
      if (this.mChallengeError.submitButton.actionChallenge.length == 1)
      {
        ChallengeProto.Challenge localChallenge = this.mChallengeError.submitButton.actionChallenge[0];
        getListener().onStartChallenge(localChallenge);
        return;
      }
      throw new IllegalStateException("Unexpected submit button action.");
    }
    logClickEvent(1409);
    if (this.mChallengeError.cancelButton.actionFailChallenge)
    {
      getListener().onFail();
      return;
    }
    throw new IllegalStateException("Unexpected cancel button action.");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallengeError = ((ChallengeProto.ChallengeError)ParcelableProto.getProtoFromBundle(this.mArguments, "ChallengeErrorFragment.challenge"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968612, paramViewGroup, false);
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
    TextView localTextView2;
    if (!TextUtils.isEmpty(this.mChallengeError.title))
    {
      localTextView1.setText(this.mChallengeError.title);
      localTextView2 = (TextView)this.mMainView.findViewById(2131755233);
      if (TextUtils.isEmpty(this.mChallengeError.descriptionHtml)) {
        break label243;
      }
      localTextView2.setText(Html.fromHtml(this.mChallengeError.descriptionHtml));
      int i = this.mArguments.getInt("ChallengeErrorFragment.backend");
      this.mSubmitButton = ((PlayActionButton)this.mMainView.findViewById(2131755302));
      if ((this.mChallengeError.submitButton == null) || (TextUtils.isEmpty(this.mChallengeError.submitButton.label))) {
        break label253;
      }
      this.mSubmitButton.configure(i, this.mChallengeError.submitButton.label, this);
      this.mCancelButton = ((PlayActionButton)this.mMainView.findViewById(2131755301));
      if ((this.mChallengeError.cancelButton == null) || (TextUtils.isEmpty(this.mChallengeError.cancelButton.label))) {
        break label263;
      }
      this.mCancelButton.configure(i, this.mChallengeError.cancelButton.label, this);
    }
    for (;;)
    {
      return this.mMainView;
      throw new IllegalStateException("No title returned.");
      label243:
      localTextView2.setVisibility(8);
      break;
      label253:
      throw new IllegalStateException("No submit button returned.");
      label263:
      this.mCancelButton.setVisibility(8);
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), this.mChallengeError.title, this.mMainView);
  }
  
  public static abstract interface Listener
  {
    public abstract void onFail();
    
    public abstract void onStartChallenge(ChallengeProto.Challenge paramChallenge);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.ageverification.ChallengeErrorFragment
 * JD-Core Version:    0.7.0.1
 */