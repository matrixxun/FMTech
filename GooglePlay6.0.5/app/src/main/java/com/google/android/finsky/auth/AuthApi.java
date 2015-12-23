package com.google.android.finsky.auth;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.security.keystore.KeyGenParameterSpec.Builder;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.FingerprintManagerCompatImpl;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.challenge.ClientLoginApi;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class AuthApi
{
  public final Account mAccount;
  private AsyncTask<Void, Void, ReauthSettings> mAuthStateFetcher;
  public final ClientLoginApi mClientLoginApi;
  public final ReauthClient mReauthClient;
  public final boolean mUseDelegatedAuth;
  
  public AuthApi(Account paramAccount)
  {
    this(paramAccount, false);
  }
  
  public AuthApi(Account paramAccount, boolean paramBoolean)
  {
    this.mAccount = paramAccount;
    this.mClientLoginApi = new ClientLoginApi(FinskyApp.get().mRequestQueue);
    this.mUseDelegatedAuth = paramBoolean;
    if (shouldUseReauthApi(this.mUseDelegatedAuth, this.mAccount.name))
    {
      this.mReauthClient = new ReauthClient(FinskyApp.get());
      return;
    }
    this.mReauthClient = null;
  }
  
  public static boolean isFingerprintAvailable(String paramString)
  {
    FingerprintManagerCompat localFingerprintManagerCompat = FingerprintManagerCompat.from(FinskyApp.get());
    return (!FinskyApp.get().getExperiments(paramString).isEnabled(12603961L)) && (FingerprintManagerCompat.IMPL.isHardwareDetected(localFingerprintManagerCompat.mContext)) && (FingerprintManagerCompat.IMPL.hasEnrolledFingerprints(localFingerprintManagerCompat.mContext));
  }
  
  public static boolean isFingerprintEnabled(String paramString)
  {
    return (((Boolean)FinskyPreferences.useFingerprintForPurchase.get(paramString).get()).booleanValue()) && (isFingerprintKeyValid());
  }
  
  @TargetApi(23)
  public static boolean isFingerprintKeyValid()
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance("AndroidKeyStore");
      Cipher localCipher = Cipher.getInstance(TextUtils.join("/", new String[] { "AES", "CBC", "PKCS7Padding" }));
      localKeyStore.load(null);
      SecretKey localSecretKey = (SecretKey)localKeyStore.getKey("FingerprintKey", null);
      if (localSecretKey == null) {
        return true;
      }
      localCipher.init(1, localSecretKey);
      return true;
    }
    catch (Exception localException)
    {
      if ((Build.VERSION.SDK_INT >= 23) && ((localException instanceof KeyPermanentlyInvalidatedException))) {
        return false;
      }
      throw new RuntimeException(localException);
    }
  }
  
  public static void recordFingerprintKey(String paramString)
  {
    if (!isFingerprintAvailable(paramString)) {
      return;
    }
    try
    {
      KeyStore.getInstance("AndroidKeyStore").load(null);
      KeyGenerator localKeyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
      localKeyGenerator.init(new KeyGenParameterSpec.Builder("FingerprintKey", 3).setEncryptionPaddings(new String[] { "PKCS7Padding" }).setBlockModes(new String[] { "CBC" }).setUserAuthenticationRequired(true).build());
      localKeyGenerator.generateKey();
      return;
    }
    catch (KeyStoreException localKeyStoreException)
    {
      throw new RuntimeException(localKeyStoreException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      break label77;
    }
    catch (CertificateException localCertificateException)
    {
      break label77;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      break label77;
    }
    catch (IOException localIOException)
    {
      break label77;
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      break label77;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      label77:
      break label77;
    }
  }
  
  public static boolean shouldUseReauthApi(boolean paramBoolean, String paramString)
  {
    if (Build.VERSION.SDK_INT < 14) {}
    FinskyExperiments localFinskyExperiments;
    do
    {
      return false;
      FinskyApp localFinskyApp = FinskyApp.get();
      if (GooglePlayServicesUtil.isSidewinderDevice(localFinskyApp)) {
        return true;
      }
      localFinskyExperiments = localFinskyApp.getExperiments(paramString);
      if (!paramBoolean) {
        break;
      }
    } while (localFinskyExperiments.isEnabled(12604372L));
    return true;
    return localFinskyExperiments.isEnabled(12602373L);
  }
  
  public final void cancelAuthFetchRequest()
  {
    if (this.mAuthStateFetcher != null)
    {
      this.mAuthStateFetcher.cancel(true);
      this.mAuthStateFetcher = null;
    }
  }
  
  public final void fetchAuthState(final AuthStateFetchListener paramAuthStateFetchListener)
  {
    if (!shouldUseReauthApi(this.mUseDelegatedAuth, this.mAccount.name))
    {
      String str = this.mAccount.name;
      paramAuthStateFetchListener.onComplete(new AuthState(false, null, isFingerprintEnabled(str), false, str));
      return;
    }
    ReauthSettings localReauthSettings = this.mReauthClient.getReauthSettingsFromDevice(this.mAccount);
    if (localReauthSettings != null)
    {
      handleReauthSettingsResponse(paramAuthStateFetchListener, localReauthSettings);
      return;
    }
    this.mAuthStateFetcher = new AsyncTask() {};
    Utils.executeMultiThreaded(this.mAuthStateFetcher, new Void[0]);
    paramAuthStateFetchListener.onStart();
  }
  
  final void handleReauthSettingsResponse(AuthStateFetchListener paramAuthStateFetchListener, ReauthSettings paramReauthSettings)
  {
    boolean bool;
    if ((paramReauthSettings != null) && (paramReauthSettings.status == 0) && (paramReauthSettings.pin != null) && ("active".equalsIgnoreCase(paramReauthSettings.pin.status)) && ((paramReauthSettings.passwordStatus == null) || (!"active".equalsIgnoreCase(paramReauthSettings.passwordStatus))))
    {
      bool = true;
      if (!bool) {
        break label114;
      }
    }
    label114:
    for (String str = paramReauthSettings.pin.recoveryUrl;; str = null)
    {
      this.mAuthStateFetcher = null;
      paramAuthStateFetchListener.onComplete(new AuthState(bool, str, isFingerprintEnabled(this.mAccount.name), false, this.mAccount.name));
      return;
      bool = false;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.auth.AuthApi
 * JD-Core Version:    0.7.0.1
 */