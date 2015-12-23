package com.google.android.finsky.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.analytics.PlayStore.AuthContext;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;

public class AuthState
  implements Parcelable
{
  public static final Parcelable.Creator<AuthState> CREATOR = new Parcelable.Creator() {};
  private final String mAccountName;
  private final String mPinRecoveryUrl;
  private final boolean mUseDelegatedAuth;
  public boolean mUseFingerprintAuth;
  public final boolean mUsePinBasedAuth;
  
  public AuthState(boolean paramBoolean1, String paramString1, boolean paramBoolean2, boolean paramBoolean3, String paramString2)
  {
    this.mUsePinBasedAuth = paramBoolean1;
    this.mPinRecoveryUrl = paramString1;
    if ((paramBoolean2) && (AuthApi.isFingerprintAvailable(paramString2))) {}
    for (boolean bool = true;; bool = false)
    {
      this.mUseFingerprintAuth = bool;
      this.mUseDelegatedAuth = paramBoolean3;
      this.mAccountName = paramString2;
      return;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final PlayStore.AuthContext getAuthContextForLogging()
  {
    int i;
    if (this.mUseFingerprintAuth) {
      i = 3;
    }
    for (;;)
    {
      PlayStore.AuthContext localAuthContext = new PlayStore.AuthContext();
      localAuthContext.mode = i;
      localAuthContext.hasMode = true;
      if (this.mUseDelegatedAuth) {
        localAuthContext.delegationReason = 1;
      }
      return localAuthContext;
      if (this.mUsePinBasedAuth) {
        i = 2;
      } else {
        i = 1;
      }
    }
  }
  
  public final int getAuthMethod()
  {
    if (this.mUseFingerprintAuth) {
      return 3;
    }
    if (this.mUsePinBasedAuth) {
      return 2;
    }
    return 1;
  }
  
  public final int getMaxAttemptExceededResourceId()
  {
    if (this.mUseFingerprintAuth) {
      throw new IllegalStateException("Call for fingerprint is not supported");
    }
    if (this.mUsePinBasedAuth) {
      return 2131362258;
    }
    return 2131362256;
  }
  
  public final int getRecoveryMessageResourceId()
  {
    if (this.mUseFingerprintAuth) {
      throw new IllegalStateException("Call for fingerprint is not supported");
    }
    if (this.mUsePinBasedAuth) {
      return 2131362636;
    }
    return 2131362487;
  }
  
  public final String getRecoveryUrl(String paramString)
  {
    if (this.mUseFingerprintAuth) {
      throw new IllegalStateException("Call for fingerprint is not supported");
    }
    if (this.mUsePinBasedAuth) {
      return this.mPinRecoveryUrl;
    }
    return ((String)G.passwordRecoveryUrl.get()).replace("%email%", paramString);
  }
  
  public final int getTitleResourceId()
  {
    if (this.mUseFingerprintAuth) {
      return 2131362158;
    }
    if (this.mUsePinBasedAuth) {
      return 2131362542;
    }
    return 2131362486;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    int j;
    int k;
    if (this.mUsePinBasedAuth)
    {
      j = i;
      paramParcel.writeInt(j);
      paramParcel.writeString(this.mPinRecoveryUrl);
      if (!this.mUseFingerprintAuth) {
        break label69;
      }
      k = i;
      label36:
      paramParcel.writeInt(k);
      if (!this.mUseDelegatedAuth) {
        break label75;
      }
    }
    for (;;)
    {
      paramParcel.writeInt(i);
      paramParcel.writeString(this.mAccountName);
      return;
      j = 0;
      break;
      label69:
      k = 0;
      break label36;
      label75:
      i = 0;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.AuthState
 * JD-Core Version:    0.7.0.1
 */