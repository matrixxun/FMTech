package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import org.keyczar.enums.RsaPadding;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.exceptions.UnsupportedTypeException;
import org.keyczar.interfaces.EncryptingStream;
import org.keyczar.interfaces.KeyType;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.interfaces.Stream;
import org.keyczar.interfaces.VerifyingStream;
import org.keyczar.util.Util;

public class RsaPublicKey
  extends KeyczarPublicKey
{
  private final byte[] hash = new byte[4];
  private RSAPublicKey jcePublicKey;
  @Expose
  final String modulus = null;
  @Expose
  final RsaPadding padding = null;
  @Expose
  final String publicExponent = null;
  
  public static KeyType getType()
  {
    return DefaultKeyType.RSA_PUB;
  }
  
  static RsaPublicKey read(String paramString)
    throws KeyczarException
  {
    RsaPublicKey localRsaPublicKey = (RsaPublicKey)Util.gson().fromJson(paramString, RsaPublicKey.class);
    if (DefaultKeyType.RSA_PUB != DefaultKeyType.RSA_PUB) {
      throw new UnsupportedTypeException(DefaultKeyType.RSA_PUB);
    }
    return localRsaPublicKey.initFromJson();
  }
  
  public final RsaPadding getPadding()
  {
    if ((this.padding == null) || (this.padding == RsaPadding.OAEP)) {
      return RsaPadding.OAEP;
    }
    return RsaPadding.PKCS;
  }
  
  protected final Stream getStream()
    throws KeyczarException
  {
    return new RsaStream();
  }
  
  public final byte[] hash()
  {
    return this.hash;
  }
  
  final RsaPublicKey initFromJson()
    throws KeyczarException
  {
    BigInteger localBigInteger1 = Util.decodeBigInteger(this.modulus);
    BigInteger localBigInteger2 = Util.decodeBigInteger(this.publicExponent);
    RSAPublicKey localRSAPublicKey;
    try
    {
      RSAPublicKeySpec localRSAPublicKeySpec = new RSAPublicKeySpec(localBigInteger1, localBigInteger2);
      this.jcePublicKey = ((RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(localRSAPublicKeySpec));
      RsaPadding localRsaPadding = getPadding();
      localRSAPublicKey = this.jcePublicKey;
      switch (org.keyczar.enums.RsaPadding.1.$SwitchMap$org$keyczar$enums$RsaPadding[localRsaPadding.ordinal()])
      {
      default: 
        throw new KeyczarException("Bug! Unknown padding type");
      }
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new KeyczarException(localGeneralSecurityException);
    }
    byte[][] arrayOfByte2 = new byte[2][];
    arrayOfByte2[0] = Util.stripLeadingZeros(localRSAPublicKey.getModulus().toByteArray());
    arrayOfByte2[1] = Util.stripLeadingZeros(localRSAPublicKey.getPublicExponent().toByteArray());
    byte[][] arrayOfByte1;
    for (byte[] arrayOfByte = Util.prefixHash(arrayOfByte2);; arrayOfByte = Util.prefixHash(arrayOfByte1))
    {
      System.arraycopy(arrayOfByte, 0, this.hash, 0, this.hash.length);
      return this;
      arrayOfByte1 = new byte[2][];
      arrayOfByte1[0] = localRSAPublicKey.getModulus().toByteArray();
      arrayOfByte1[1] = localRSAPublicKey.getPublicExponent().toByteArray();
    }
  }
  
  private final class RsaStream
    implements EncryptingStream, VerifyingStream
  {
    private Cipher cipher;
    private Signature signature;
    
    RsaStream()
      throws KeyczarException
    {
      try
      {
        this.signature = Signature.getInstance("SHA1withRSA");
        this.cipher = Cipher.getInstance(RsaPublicKey.this.getPadding().cryptAlgorithm);
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
      int i;
      int j;
      try
      {
        i = this.cipher.getOutputSize(paramByteBuffer1.limit());
        j = paramByteBuffer2.limit() - paramByteBuffer2.position();
        ByteBuffer localByteBuffer = ByteBuffer.allocate(i);
        this.cipher.doFinal(paramByteBuffer1, localByteBuffer);
        if (i == j)
        {
          paramByteBuffer2.put(localByteBuffer.array());
          return j;
        }
        if ((i == j + 1) && (localByteBuffer.array()[(i - 1)] == 0))
        {
          paramByteBuffer2.put(localByteBuffer.array(), 0, j);
          return j;
        }
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
      throw new KeyczarException("Expected " + j + " bytes from encryption operation but got " + i);
    }
    
    public final SigningStream getSigningStream()
    {
      new SigningStream()
      {
        public final int digestSize()
        {
          return 0;
        }
        
        public final void initSign() {}
        
        public final void sign(ByteBuffer paramAnonymousByteBuffer) {}
        
        public final void updateSign(ByteBuffer paramAnonymousByteBuffer) {}
      };
    }
    
    public final int initEncrypt(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      try
      {
        this.cipher.init(1, RsaPublicKey.this.jcePublicKey);
        return 0;
      }
      catch (InvalidKeyException localInvalidKeyException)
      {
        throw new KeyczarException(localInvalidKeyException);
      }
    }
    
    public final void initVerify()
      throws KeyczarException
    {
      try
      {
        this.signature.initVerify(RsaPublicKey.this.jcePublicKey);
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final int maxOutputSize(int paramInt)
    {
      return RsaPublicKey.getType().getOutputSize(RsaPublicKey.this.size);
    }
    
    public final int updateEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
      throws KeyczarException
    {
      try
      {
        int i = this.cipher.update(paramByteBuffer1, paramByteBuffer2);
        return i;
      }
      catch (ShortBufferException localShortBufferException)
      {
        throw new KeyczarException(localShortBufferException);
      }
    }
    
    public final void updateVerify(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      try
      {
        this.signature.update(paramByteBuffer);
        return;
      }
      catch (SignatureException localSignatureException)
      {
        throw new KeyczarException(localSignatureException);
      }
    }
    
    public final boolean verify(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      try
      {
        boolean bool = this.signature.verify(paramByteBuffer.array(), paramByteBuffer.position(), paramByteBuffer.limit() - paramByteBuffer.position());
        return bool;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.RsaPublicKey
 * JD-Core Version:    0.7.0.1
 */