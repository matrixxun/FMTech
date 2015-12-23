package com.google.android.finsky.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.google.android.finsky.api.utils.AndroidKeyczarReader;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.dfe.api.DfeResponseVerifier;
import com.google.android.play.dfe.api.DfeResponseVerifier.DfeResponseVerifierException;
import java.io.File;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.keyczar.KeyczarFileReader;
import org.keyczar.Verifier;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.KeyczarReader;

public final class DfeResponseVerifierImpl
  implements DfeResponseVerifier
{
  private static final String FALLBACK_KEYS_FILES_SUBDIR;
  private static final String PROD_KEYS_ASSETS_SUBDIR = "keys" + File.separator + "dfe-response-auth";
  private static final SecureRandom SECURE_RANDOM;
  private static KeyczarReader sFallbackReader;
  private static boolean sFallbackReaderInitialized;
  private static KeyczarReader sProdReader;
  private final Context mContext;
  private byte[] mNonce = new byte[256];
  private boolean mNonceInitialized;
  
  static
  {
    FALLBACK_KEYS_FILES_SUBDIR = "keys" + File.separator + "dfe-response-auth-dev";
    try
    {
      SecureRandom localSecureRandom2 = SecureRandom.getInstance("SHA1PRNG");
      localSecureRandom1 = localSecureRandom2;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;)
      {
        FinskyLog.e("Could not initialize SecureRandom, SHA1PRNG not supported. %s", new Object[] { localNoSuchAlgorithmException });
        SecureRandom localSecureRandom1 = null;
      }
    }
    SECURE_RANDOM = localSecureRandom1;
  }
  
  public DfeResponseVerifierImpl(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private static KeyczarReader getFallbackReader(Context paramContext)
  {
    try
    {
      if (!sFallbackReaderInitialized)
      {
        File localFile = new File(paramContext.getFilesDir(), FALLBACK_KEYS_FILES_SUBDIR);
        if (localFile.exists()) {
          sFallbackReader = new KeyczarFileReader(localFile.getAbsolutePath());
        }
        sFallbackReaderInitialized = true;
      }
      KeyczarReader localKeyczarReader = sFallbackReader;
      return localKeyczarReader;
    }
    finally {}
  }
  
  private static KeyczarReader getProdReader(Context paramContext)
  {
    try
    {
      if (sProdReader == null) {
        sProdReader = new AndroidKeyczarReader(paramContext.getResources(), PROD_KEYS_ASSETS_SUBDIR);
      }
      KeyczarReader localKeyczarReader = sProdReader;
      return localKeyczarReader;
    }
    finally {}
  }
  
  private static boolean internalVerify(KeyczarReader paramKeyczarReader, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
    throws DfeResponseVerifier.DfeResponseVerifierException
  {
    Verifier localVerifier;
    byte[] arrayOfByte1;
    try
    {
      localVerifier = new Verifier(paramKeyczarReader);
      arrayOfByte1 = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
      System.arraycopy(paramArrayOfByte1, 0, arrayOfByte1, 0, paramArrayOfByte1.length);
      System.arraycopy(paramArrayOfByte2, 0, arrayOfByte1, paramArrayOfByte1.length, paramArrayOfByte2.length);
      if (TextUtils.isEmpty(paramString))
      {
        FinskyLog.e("No signing response found.", new Object[0]);
        throw new DfeResponseVerifier.DfeResponseVerifierException("No signing response found.");
      }
    }
    catch (KeyczarException localKeyczarException)
    {
      FinskyLog.d("Keyczar exception during signature verification: %s", new Object[] { localKeyczarException });
      return false;
    }
    String[] arrayOfString = paramString.split(";");
    int i = arrayOfString.length;
    for (int j = 0;; j++) {
      if (j < i)
      {
        String str = arrayOfString[j].trim();
        if (str.startsWith("signature="))
        {
          byte[] arrayOfByte2 = Base64.decode(str.substring(10), 11);
          return localVerifier.verify$36a85960(ByteBuffer.wrap(arrayOfByte1), ByteBuffer.wrap(arrayOfByte2));
        }
      }
      else
      {
        throw new DfeResponseVerifier.DfeResponseVerifierException("Signature not found in response: " + paramString);
      }
    }
  }
  
  public final String getSignatureRequest()
    throws DfeResponseVerifier.DfeResponseVerifierException
  {
    try
    {
      if (SECURE_RANDOM == null) {
        throw new DfeResponseVerifier.DfeResponseVerifierException("Uninitialized SecureRandom.");
      }
    }
    finally {}
    if (!this.mNonceInitialized)
    {
      SECURE_RANDOM.nextBytes(this.mNonce);
      this.mNonceInitialized = true;
    }
    String str = "nonce=" + Base64.encodeToString(this.mNonce, 11);
    return str;
  }
  
  public final void verify(byte[] paramArrayOfByte, String paramString)
    throws DfeResponseVerifier.DfeResponseVerifierException
  {
    boolean bool = internalVerify(getProdReader(this.mContext), this.mNonce, paramArrayOfByte, paramString);
    if (!bool)
    {
      KeyczarReader localKeyczarReader = getFallbackReader(this.mContext);
      if (localKeyczarReader != null)
      {
        FinskyLog.d("Retry verification using fallback keys.", new Object[0]);
        bool = internalVerify(localKeyczarReader, this.mNonce, paramArrayOfByte, paramString);
      }
    }
    if ((!bool) || (FinskyLog.DEBUG))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(bool);
      FinskyLog.d("Response signature verified: %b", arrayOfObject);
    }
    if (!bool) {
      throw new DfeResponseVerifier.DfeResponseVerifierException("Response signature mismatch.");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.DfeResponseVerifierImpl
 * JD-Core Version:    0.7.0.1
 */