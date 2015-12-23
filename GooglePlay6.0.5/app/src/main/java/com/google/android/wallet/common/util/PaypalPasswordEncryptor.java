package com.google.android.wallet.common.util;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class PaypalPasswordEncryptor
{
  public final String mHashedDeviceId;
  
  public PaypalPasswordEncryptor(Context paramContext, String paramString)
  {
    this.mHashedDeviceId = getHashedDeviceId(Settings.Secure.getString(paramContext.getContentResolver(), "android_id"), paramString);
  }
  
  public static byte[] createInnerMessage(long paramLong, String paramString1, String paramString2)
  {
    int i = (int)(paramLong / 1000L);
    byte[] arrayOfByte1;
    int j;
    try
    {
      arrayOfByte1 = paramString1.getBytes("UTF-8");
      j = arrayOfByte1.length;
      if (j != paramString1.length()) {
        throw new IllegalArgumentException("Hashed device ID not 8 bits per char: " + j + " " + paramString1.length());
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException1)
    {
      throw new RuntimeException(localUnsupportedEncodingException1);
    }
    if (j > 255) {
      throw new IllegalArgumentException("Hashed device ID too long: " + j);
    }
    byte[] arrayOfByte2;
    int k;
    try
    {
      arrayOfByte2 = paramString2.getBytes("UTF-16LE");
      k = paramString2.length();
      if (k > 255) {
        throw new IllegalArgumentException("Password too long: " + k);
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException2)
    {
      throw new RuntimeException(localUnsupportedEncodingException2);
    }
    ByteBuffer localByteBuffer = ByteBuffer.allocate(1 + (j + 5) + arrayOfByte2.length);
    localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    localByteBuffer.putInt(i);
    localByteBuffer.put((byte)j);
    localByteBuffer.put(arrayOfByte1);
    localByteBuffer.put((byte)k);
    localByteBuffer.put(arrayOfByte2);
    return localByteBuffer.array();
  }
  
  public static SecretKey createSessionKey()
  {
    try
    {
      KeyGenerator localKeyGenerator = KeyGenerator.getInstance("AES");
      localKeyGenerator.init(256);
      SecretKey localSecretKey = localKeyGenerator.generateKey();
      return localSecretKey;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException(localNoSuchAlgorithmException);
    }
  }
  
  /* Error */
  public static byte[] encryptExchangeKey(java.security.cert.Certificate paramCertificate, SecretKey paramSecretKey)
    throws java.security.cert.CertificateException
  {
    // Byte code:
    //   0: ldc 146
    //   2: invokestatic 151	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   5: astore 4
    //   7: aload 4
    //   9: iconst_1
    //   10: aload_0
    //   11: invokevirtual 157	java/security/cert/Certificate:getPublicKey	()Ljava/security/PublicKey;
    //   14: invokevirtual 160	javax/crypto/Cipher:init	(ILjava/security/Key;)V
    //   17: aload 4
    //   19: aload_1
    //   20: invokeinterface 165 1 0
    //   25: invokevirtual 169	javax/crypto/Cipher:doFinal	([B)[B
    //   28: astore 8
    //   30: aload 8
    //   32: invokestatic 173	com/google/android/wallet/common/util/PaypalPasswordEncryptor:reverseArray	([B)V
    //   35: aload 8
    //   37: areturn
    //   38: astore_3
    //   39: new 74	java/lang/RuntimeException
    //   42: dup
    //   43: aload_3
    //   44: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   47: athrow
    //   48: astore_2
    //   49: new 74	java/lang/RuntimeException
    //   52: dup
    //   53: aload_2
    //   54: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   57: athrow
    //   58: astore 5
    //   60: new 136	java/security/cert/CertificateException
    //   63: dup
    //   64: aload 5
    //   66: invokespecial 174	java/security/cert/CertificateException:<init>	(Ljava/lang/Throwable;)V
    //   69: athrow
    //   70: astore 7
    //   72: new 74	java/lang/RuntimeException
    //   75: dup
    //   76: aload 7
    //   78: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   81: athrow
    //   82: astore 6
    //   84: new 74	java/lang/RuntimeException
    //   87: dup
    //   88: aload 6
    //   90: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   93: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	paramCertificate	java.security.cert.Certificate
    //   0	94	1	paramSecretKey	SecretKey
    //   48	6	2	localNoSuchPaddingException	javax.crypto.NoSuchPaddingException
    //   38	6	3	localNoSuchAlgorithmException	NoSuchAlgorithmException
    //   5	13	4	localCipher	javax.crypto.Cipher
    //   58	7	5	localInvalidKeyException	java.security.InvalidKeyException
    //   82	7	6	localBadPaddingException	javax.crypto.BadPaddingException
    //   70	7	7	localIllegalBlockSizeException	javax.crypto.IllegalBlockSizeException
    //   28	8	8	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   0	7	38	java/security/NoSuchAlgorithmException
    //   0	7	48	javax/crypto/NoSuchPaddingException
    //   7	17	58	java/security/InvalidKeyException
    //   17	30	70	javax/crypto/IllegalBlockSizeException
    //   17	30	82	javax/crypto/BadPaddingException
  }
  
  /* Error */
  public static byte[] encryptInnerMessage(SecretKey paramSecretKey, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: ldc 180
    //   2: invokestatic 151	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   5: astore 4
    //   7: new 182	javax/crypto/spec/IvParameterSpec
    //   10: dup
    //   11: bipush 16
    //   13: newarray byte
    //   15: invokespecial 184	javax/crypto/spec/IvParameterSpec:<init>	([B)V
    //   18: astore 5
    //   20: aload 4
    //   22: iconst_1
    //   23: aload_0
    //   24: aload 5
    //   26: invokevirtual 187	javax/crypto/Cipher:init	(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   29: aload 4
    //   31: aload_1
    //   32: invokevirtual 169	javax/crypto/Cipher:doFinal	([B)[B
    //   35: astore 10
    //   37: aload 10
    //   39: areturn
    //   40: astore_3
    //   41: new 74	java/lang/RuntimeException
    //   44: dup
    //   45: aload_3
    //   46: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   49: athrow
    //   50: astore_2
    //   51: new 74	java/lang/RuntimeException
    //   54: dup
    //   55: aload_2
    //   56: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   59: athrow
    //   60: astore 7
    //   62: new 74	java/lang/RuntimeException
    //   65: dup
    //   66: aload 7
    //   68: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   71: athrow
    //   72: astore 6
    //   74: new 74	java/lang/RuntimeException
    //   77: dup
    //   78: aload 6
    //   80: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   83: athrow
    //   84: astore 9
    //   86: new 74	java/lang/RuntimeException
    //   89: dup
    //   90: aload 9
    //   92: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   95: athrow
    //   96: astore 8
    //   98: new 74	java/lang/RuntimeException
    //   101: dup
    //   102: aload 8
    //   104: invokespecial 77	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   107: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	108	0	paramSecretKey	SecretKey
    //   0	108	1	paramArrayOfByte	byte[]
    //   50	6	2	localNoSuchPaddingException	javax.crypto.NoSuchPaddingException
    //   40	6	3	localNoSuchAlgorithmException	NoSuchAlgorithmException
    //   5	25	4	localCipher	javax.crypto.Cipher
    //   18	7	5	localIvParameterSpec	javax.crypto.spec.IvParameterSpec
    //   72	7	6	localInvalidAlgorithmParameterException	java.security.InvalidAlgorithmParameterException
    //   60	7	7	localInvalidKeyException	java.security.InvalidKeyException
    //   96	7	8	localBadPaddingException	javax.crypto.BadPaddingException
    //   84	7	9	localIllegalBlockSizeException	javax.crypto.IllegalBlockSizeException
    //   35	3	10	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   0	7	40	java/security/NoSuchAlgorithmException
    //   0	7	50	javax/crypto/NoSuchPaddingException
    //   20	29	60	java/security/InvalidKeyException
    //   20	29	72	java/security/InvalidAlgorithmParameterException
    //   29	37	84	javax/crypto/IllegalBlockSizeException
    //   29	37	96	javax/crypto/BadPaddingException
  }
  
  private static String getHashedDeviceId(String paramString1, String paramString2)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
      localMessageDigest.update(paramString2.getBytes("UTF-8"));
      localMessageDigest.update(paramString1.getBytes("UTF-8"));
      String str = Base64.encodeToString(localMessageDigest.digest(), 2);
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException(localNoSuchAlgorithmException);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }
  
  private static void reverseArray(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < paramArrayOfByte.length / 2; i++)
    {
      int j = -1 + paramArrayOfByte.length - i;
      int k = paramArrayOfByte[i];
      paramArrayOfByte[i] = paramArrayOfByte[j];
      paramArrayOfByte[j] = k;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.PaypalPasswordEncryptor
 * JD-Core Version:    0.7.0.1
 */