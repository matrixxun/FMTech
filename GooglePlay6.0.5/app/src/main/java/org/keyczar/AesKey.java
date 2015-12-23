package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.keyczar.enums.CipherMode;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.DecryptingStream;
import org.keyczar.interfaces.EncryptingStream;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.interfaces.Stream;
import org.keyczar.util.Base64Coder;
import org.keyczar.util.Util;

public class AesKey
  extends KeyczarKey
{
  private static final CipherMode DEFAULT_MODE = CipherMode.CBC;
  private SecretKey aesKey;
  @Expose
  private final String aesKeyString;
  private final byte[] hash = new byte[4];
  @Expose
  private final HmacKey hmacKey;
  @Expose
  private final CipherMode mode;
  
  private AesKey()
  {
    super(0);
    this.aesKeyString = null;
    this.hmacKey = null;
    this.mode = null;
  }
  
  private AesKey(byte[] paramArrayOfByte, HmacKey paramHmacKey)
    throws KeyczarException
  {
    super(8 * paramArrayOfByte.length);
    this.aesKeyString = Base64Coder.encodeWebSafe(paramArrayOfByte);
    this.mode = DEFAULT_MODE;
    this.hmacKey = paramHmacKey;
    initJceKey(paramArrayOfByte);
  }
  
  public static AesKey generate(int paramInt)
    throws KeyczarException
  {
    return new AesKey(Util.rand(paramInt / 8), HmacKey.generate());
  }
  
  private void initJceKey(byte[] paramArrayOfByte)
    throws KeyczarException
  {
    this.aesKey = new SecretKeySpec(paramArrayOfByte, "AES");
    byte[][] arrayOfByte = new byte[3][];
    arrayOfByte[0] = Util.fromInt(16);
    arrayOfByte[1] = paramArrayOfByte;
    arrayOfByte[2] = this.hmacKey.hmacKey.getEncoded();
    System.arraycopy(Util.hash(arrayOfByte), 0, this.hash, 0, this.hash.length);
  }
  
  static AesKey read(String paramString)
    throws KeyczarException
  {
    AesKey localAesKey = (AesKey)Util.gson().fromJson(paramString, AesKey.class);
    localAesKey.hmacKey.initFromJson();
    localAesKey.initJceKey(Base64Coder.decodeWebSafe(localAesKey.aesKeyString));
    return localAesKey;
  }
  
  protected final Stream getStream()
    throws KeyczarException
  {
    return new AesStream();
  }
  
  protected final byte[] hash()
  {
    return this.hash;
  }
  
  private final class AesStream
    implements DecryptingStream, EncryptingStream
  {
    private final Cipher decryptingCipher;
    private final Cipher encryptingCipher;
    boolean ivRead = false;
    private final SigningStream signStream;
    
    public AesStream()
      throws KeyczarException
    {
      IvParameterSpec localIvParameterSpec = new IvParameterSpec(new byte[16]);
      try
      {
        this.encryptingCipher = Cipher.getInstance(AesKey.this.mode.jceMode);
        this.encryptingCipher.init(1, AesKey.this.aesKey, localIvParameterSpec);
        this.decryptingCipher = Cipher.getInstance(AesKey.this.mode.jceMode);
        this.decryptingCipher.init(2, AesKey.this.aesKey, localIvParameterSpec);
        this.signStream = ((SigningStream)AesKey.this.hmacKey.getStream());
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final int doFinalEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
      throws KeyczarException
    {
      try
      {
        int i = this.encryptingCipher.doFinal(paramByteBuffer1, paramByteBuffer2);
        return i;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final SigningStream getSigningStream()
    {
      return this.signStream;
    }
    
    public final int initEncrypt(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      byte[] arrayOfByte = new byte[16];
      Util.rand(arrayOfByte);
      try
      {
        int i = this.encryptingCipher.update(ByteBuffer.wrap(arrayOfByte), paramByteBuffer);
        return i;
      }
      catch (javax.crypto.ShortBufferException localShortBufferException)
      {
        throw new org.keyczar.exceptions.ShortBufferException(localShortBufferException);
      }
    }
    
    public final int maxOutputSize(int paramInt)
    {
      CipherMode localCipherMode = AesKey.this.mode;
      if (localCipherMode == CipherMode.CBC) {
        return 16 * (2 + paramInt / 16);
      }
      if (localCipherMode == CipherMode.ECB) {
        return 16;
      }
      if (localCipherMode == CipherMode.CTR) {
        return paramInt + 8;
      }
      if (localCipherMode == CipherMode.DET_CBC) {
        return 16 * (1 + paramInt / 16);
      }
      return 0;
    }
    
    public final int updateEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
      throws KeyczarException
    {
      try
      {
        int i = this.encryptingCipher.update(paramByteBuffer1, paramByteBuffer2);
        return i;
      }
      catch (javax.crypto.ShortBufferException localShortBufferException)
      {
        throw new org.keyczar.exceptions.ShortBufferException(localShortBufferException);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.AesKey
 * JD-Core Version:    0.7.0.1
 */