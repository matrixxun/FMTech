package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAPrivateKeySpec;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.KeyType;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.interfaces.Stream;
import org.keyczar.interfaces.VerifyingStream;
import org.keyczar.util.Base64Coder;
import org.keyczar.util.Util;

public class DsaPrivateKey
  extends KeyczarKey
{
  private DSAPrivateKey jcePrivateKey;
  @Expose
  private final DsaPublicKey publicKey = null;
  @Expose
  private final String x = null;
  
  private DsaPrivateKey()
  {
    super(0);
  }
  
  public static KeyType getType()
  {
    return DefaultKeyType.DSA_PRIV;
  }
  
  private DsaPrivateKey initFromJson()
    throws KeyczarException
  {
    this.publicKey.initFromJson();
    BigInteger localBigInteger1 = new BigInteger(Base64Coder.decodeWebSafe(this.x));
    BigInteger localBigInteger2 = new BigInteger(Base64Coder.decodeWebSafe(this.publicKey.p));
    BigInteger localBigInteger3 = new BigInteger(Base64Coder.decodeWebSafe(this.publicKey.q));
    BigInteger localBigInteger4 = new BigInteger(Base64Coder.decodeWebSafe(this.publicKey.g));
    try
    {
      this.jcePrivateKey = ((DSAPrivateKey)KeyFactory.getInstance("DSA").generatePrivate(new DSAPrivateKeySpec(localBigInteger1, localBigInteger2, localBigInteger3, localBigInteger4)));
      return this;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new KeyczarException(localGeneralSecurityException);
    }
  }
  
  static DsaPrivateKey read(String paramString)
    throws KeyczarException
  {
    return ((DsaPrivateKey)Util.gson().fromJson(paramString, DsaPrivateKey.class)).initFromJson();
  }
  
  protected final Stream getStream()
    throws KeyczarException
  {
    return new DsaSigningStream();
  }
  
  protected final byte[] hash()
  {
    return this.publicKey.hash();
  }
  
  private final class DsaSigningStream
    implements SigningStream, VerifyingStream
  {
    private Signature signature;
    private VerifyingStream verifyingStream;
    
    public DsaSigningStream()
      throws KeyczarException
    {
      try
      {
        this.signature = Signature.getInstance("SHA1withDSA");
        this.verifyingStream = ((VerifyingStream)DsaPrivateKey.this.publicKey.getStream());
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final int digestSize()
    {
      return DsaPrivateKey.getType().getOutputSize();
    }
    
    public final void initSign()
      throws KeyczarException
    {
      try
      {
        this.signature.initSign(DsaPrivateKey.this.jcePrivateKey);
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
 * Qualified Name:     org.keyczar.DsaPrivateKey
 * JD-Core Version:    0.7.0.1
 */