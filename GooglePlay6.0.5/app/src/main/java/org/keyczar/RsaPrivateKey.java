package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import javax.crypto.Cipher;
import org.keyczar.enums.RsaPadding;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.DecryptingStream;
import org.keyczar.interfaces.EncryptingStream;
import org.keyczar.interfaces.KeyType;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.interfaces.Stream;
import org.keyczar.interfaces.VerifyingStream;
import org.keyczar.util.Util;

public class RsaPrivateKey
  extends KeyczarKey
{
  @Expose
  private final String crtCoefficient = null;
  private RSAPrivateCrtKey jcePrivateKey = null;
  @Expose
  private final String primeExponentP = null;
  @Expose
  private final String primeExponentQ = null;
  @Expose
  private final String primeP = null;
  @Expose
  private final String primeQ = null;
  @Expose
  private final String privateExponent = null;
  @Expose
  private final RsaPublicKey publicKey = null;
  
  private RsaPrivateKey()
  {
    super(0);
  }
  
  public static KeyType getType()
  {
    return DefaultKeyType.RSA_PRIV;
  }
  
  private RsaPrivateKey initFromJson()
    throws KeyczarException
  {
    this.publicKey.initFromJson();
    try
    {
      this.jcePrivateKey = ((RSAPrivateCrtKey)KeyFactory.getInstance("RSA").generatePrivate(new RSAPrivateCrtKeySpec(Util.decodeBigInteger(this.publicKey.modulus), Util.decodeBigInteger(this.publicKey.publicExponent), Util.decodeBigInteger(this.privateExponent), Util.decodeBigInteger(this.primeP), Util.decodeBigInteger(this.primeQ), Util.decodeBigInteger(this.primeExponentP), Util.decodeBigInteger(this.primeExponentQ), Util.decodeBigInteger(this.crtCoefficient))));
      return this;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new KeyczarException(localGeneralSecurityException);
    }
  }
  
  static RsaPrivateKey read(String paramString)
    throws KeyczarException
  {
    return ((RsaPrivateKey)Util.gson().fromJson(paramString, RsaPrivateKey.class)).initFromJson();
  }
  
  protected final Stream getStream()
    throws KeyczarException
  {
    return new RsaPrivateStream();
  }
  
  protected final byte[] hash()
  {
    return this.publicKey.hash();
  }
  
  private final class RsaPrivateStream
    implements DecryptingStream, EncryptingStream, SigningStream, VerifyingStream
  {
    private Cipher cipher;
    private EncryptingStream encryptingStream;
    private Signature signature;
    private VerifyingStream verifyingStream;
    
    public RsaPrivateStream()
      throws KeyczarException
    {
      try
      {
        this.signature = Signature.getInstance("SHA1withRSA");
        this.verifyingStream = ((VerifyingStream)RsaPrivateKey.this.publicKey.getStream());
        this.cipher = Cipher.getInstance(RsaPrivateKey.this.publicKey.getPadding().cryptAlgorithm);
        this.encryptingStream = ((EncryptingStream)RsaPrivateKey.this.publicKey.getStream());
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final int digestSize()
    {
      return RsaPrivateKey.getType().getOutputSize();
    }
    
    public final int doFinalEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
      throws KeyczarException
    {
      return this.encryptingStream.doFinalEncrypt(paramByteBuffer1, paramByteBuffer2);
    }
    
    public final SigningStream getSigningStream()
      throws KeyczarException
    {
      return this.encryptingStream.getSigningStream();
    }
    
    public final int initEncrypt(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      return this.encryptingStream.initEncrypt(paramByteBuffer);
    }
    
    public final void initSign()
      throws KeyczarException
    {
      try
      {
        this.signature.initSign(RsaPrivateKey.this.jcePrivateKey);
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final void initVerify()
      throws KeyczarException
    {
      this.verifyingStream.initVerify();
    }
    
    public final int maxOutputSize(int paramInt)
    {
      return RsaPrivateKey.getType().getOutputSize(RsaPrivateKey.this.size);
    }
    
    public final void sign(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      try
      {
        paramByteBuffer.put(this.signature.sign());
        return;
      }
      catch (SignatureException localSignatureException)
      {
        throw new KeyczarException(localSignatureException);
      }
    }
    
    public final int updateEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
      throws KeyczarException
    {
      return this.encryptingStream.updateEncrypt(paramByteBuffer1, paramByteBuffer2);
    }
    
    public final void updateSign(ByteBuffer paramByteBuffer)
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
    
    public final void updateVerify(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      this.verifyingStream.updateVerify(paramByteBuffer);
    }
    
    public final boolean verify(ByteBuffer paramByteBuffer)
      throws KeyczarException
    {
      return this.verifyingStream.verify(paramByteBuffer);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.RsaPrivateKey
 * JD-Core Version:    0.7.0.1
 */