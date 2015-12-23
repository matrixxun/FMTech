package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.config.GservicesValue;

@TargetApi(23)
public final class FingerprintUiHelper
  extends FingerprintManager.AuthenticationCallback
{
  final String mAccountName;
  final Callback mCallback;
  CancellationSignal mCancellationSignal;
  private int mFailedCount;
  final FingerprintManager mFingerprintManager;
  final ImageView mIcon;
  private final int mMaxFailedAttempt = ((Integer)G.passwordMaxFailedAttempts.get()).intValue();
  boolean mSelfCancelled;
  final TextView mStatusTextView;
  private Runnable mSuccessRunnable = new Runnable()
  {
    public final void run()
    {
      FingerprintUiHelper.this.mCallback.onAuthenticated();
    }
  };
  private Runnable mUnrecoverableErrorRunnable = new Runnable()
  {
    public final void run()
    {
      FingerprintUiHelper.this.mCallback.onUnrecoverableError();
    }
  };
  
  public FingerprintUiHelper(Context paramContext, ImageView paramImageView, TextView paramTextView, String paramString, Callback paramCallback)
  {
    this.mFingerprintManager = ((FingerprintManager)paramContext.getSystemService(FingerprintManager.class));
    this.mIcon = paramImageView;
    this.mStatusTextView = paramTextView;
    this.mAccountName = paramString;
    this.mCallback = paramCallback;
  }
  
  private void showError(CharSequence paramCharSequence)
  {
    this.mIcon.setImageResource(2130837765);
    updateAndAnnounceTextView(paramCharSequence.toString());
    this.mStatusTextView.setTextColor(this.mStatusTextView.getResources().getColor(2131689544, null));
    UiUtils.playShakeAnimationIfPossible(this.mStatusTextView.getContext(), this.mStatusTextView);
  }
  
  private void updateAndAnnounceTextView(String paramString)
  {
    UiUtils.sendAccessibilityEventWithText(this.mStatusTextView.getContext(), paramString, this.mStatusTextView);
    this.mStatusTextView.setText(paramString);
  }
  
  public final void onAuthenticationError(int paramInt, CharSequence paramCharSequence)
  {
    if (!this.mSelfCancelled)
    {
      showError(paramCharSequence);
      this.mStatusTextView.postDelayed(this.mUnrecoverableErrorRunnable, 1600L);
    }
  }
  
  public final void onAuthenticationFailed()
  {
    this.mFailedCount = (1 + this.mFailedCount);
    int i = 2131362154;
    if (this.mFailedCount == 1) {
      i = 2131362150;
    }
    do
    {
      for (;;)
      {
        showError(this.mStatusTextView.getResources().getString(i));
        return;
        if (this.mFailedCount != -1 + this.mMaxFailedAttempt) {
          break;
        }
        i = 2131362151;
      }
    } while (this.mFailedCount < this.mMaxFailedAttempt);
    stopListening();
    this.mCallback.onUnrecoverableError();
  }
  
  public final void onAuthenticationHelp(int paramInt, CharSequence paramCharSequence)
  {
    showError(paramCharSequence);
  }
  
  public final void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult paramAuthenticationResult)
  {
    this.mIcon.setImageResource(2130837767);
    this.mStatusTextView.setTextColor(this.mStatusTextView.getResources().getColor(2131689543, null));
    updateAndAnnounceTextView(this.mStatusTextView.getResources().getString(2131362156));
    this.mStatusTextView.postDelayed(this.mSuccessRunnable, 300L);
  }
  
  public final void stopListening()
  {
    if (this.mCancellationSignal != null)
    {
      this.mSelfCancelled = true;
      this.mCancellationSignal.cancel();
      this.mCancellationSignal = null;
      this.mStatusTextView.removeCallbacks(this.mUnrecoverableErrorRunnable);
      this.mStatusTextView.removeCallbacks(this.mSuccessRunnable);
    }
  }
  
  public static abstract interface Callback
  {
    public abstract void onAuthenticated();
    
    public abstract void onUnrecoverableError();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.FingerprintUiHelper
 * JD-Core Version:    0.7.0.1
 */