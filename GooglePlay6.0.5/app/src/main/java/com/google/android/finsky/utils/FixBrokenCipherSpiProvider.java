package com.google.android.finsky.utils;

import android.os.Build.VERSION;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

public final class FixBrokenCipherSpiProvider
  extends Provider
{
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  
  public FixBrokenCipherSpiProvider()
  {
    super("FixBrokenCipherSpiProvider", 1.0D, "Workaround for bug in pre-ICS Harmony");
    if (Build.VERSION.SDK_INT < 14)
    {
      put("Cipher.AES", FixBrokenCipherSpiProvider.FixBrokenCipherSpi.AES.class.getName());
      Security.insertProviderAt(this, 1);
    }
  }
  
  public static void insertIfNeeded() {}
  
  public static class FixBrokenCipherSpi
    extends CipherSpi
  {
    private String mAlgorithm;
    private Cipher mInstance;
    private String mMode;
    private String mPadding;
    
    public FixBrokenCipherSpi(String paramString)
    {
      this.mAlgorithm = paramString;
    }
    
    private Cipher getInstance()
    {
      if (this.mInstance != null) {
        return this.mInstance;
      }
      String str1 = "Cipher." + this.mAlgorithm;
      if ((this.mMode != null) && (this.mPadding != null)) {}
      for (String str2 = this.mAlgorithm + "/" + this.mMode + "/" + this.mPadding;; str2 = this.mAlgorithm) {
        for (Provider localProvider : Security.getProviders(str1)) {
          if (!(localProvider instanceof FixBrokenCipherSpiProvider)) {
            try
            {
              Cipher localCipher = Cipher.getInstance(str2, localProvider);
              this.mInstance = localCipher;
              return localCipher;
            }
            catch (GeneralSecurityException localGeneralSecurityException) {}
          }
        }
      }
      throw new RuntimeException("No other providers offer " + str2);
    }
    
    protected int engineDoFinal(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
      throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
    {
      return getInstance().doFinal(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3);
    }
    
    protected byte[] engineDoFinal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IllegalBlockSizeException, BadPaddingException
    {
      return getInstance().doFinal(paramArrayOfByte, paramInt1, paramInt2);
    }
    
    protected int engineGetBlockSize()
    {
      return getInstance().getBlockSize();
    }
    
    protected byte[] engineGetIV()
    {
      return getInstance().getIV();
    }
    
    protected int engineGetOutputSize(int paramInt)
    {
      return getInstance().getOutputSize(paramInt);
    }
    
    protected AlgorithmParameters engineGetParameters()
    {
      return getInstance().getParameters();
    }
    
    protected void engineInit(int paramInt, Key paramKey, AlgorithmParameters paramAlgorithmParameters, SecureRandom paramSecureRandom)
      throws InvalidKeyException, InvalidAlgorithmParameterException
    {
      getInstance().init(paramInt, paramKey, paramAlgorithmParameters, paramSecureRandom);
    }
    
    protected void engineInit(int paramInt, Key paramKey, SecureRandom paramSecureRandom)
      throws InvalidKeyException
    {
      getInstance().init(paramInt, paramKey, paramSecureRandom);
    }
    
    protected void engineInit(int paramInt, Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
      throws InvalidKeyException, InvalidAlgorithmParameterException
    {
      getInstance().init(paramInt, paramKey, paramAlgorithmParameterSpec, paramSecureRandom);
    }
    
    protected void engineSetMode(String paramString)
    {
      this.mMode = paramString;
    }
    
    protected void engineSetPadding(String paramString)
    {
      this.mPadding = paramString;
    }
    
    protected int engineUpdate(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
      throws ShortBufferException
    {
      return getInstance().update(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3);
    }
    
    protected byte[] engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      byte[] arrayOfByte = getInstance().update(paramArrayOfByte, paramInt1, paramInt2);
      if (arrayOfByte == null) {
        arrayOfByte = FixBrokenCipherSpiProvider.EMPTY_BYTE_ARRAY;
      }
      return arrayOfByte;
    }
    
    public static class AES
      extends FixBrokenCipherSpiProvider.FixBrokenCipherSpi
    {
      public AES()
      {
        super();
      }
    }
  }
  
  private static final class Holder
  {
    private static FixBrokenCipherSpiProvider INSTANCE = new FixBrokenCipherSpiProvider();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.FixBrokenCipherSpiProvider
 * JD-Core Version:    0.7.0.1
 */