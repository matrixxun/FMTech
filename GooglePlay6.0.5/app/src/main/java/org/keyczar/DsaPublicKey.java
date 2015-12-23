package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.Stream;
import org.keyczar.interfaces.VerifyingStream;
import org.keyczar.util.Base64Coder;
import org.keyczar.util.Util;

public class DsaPublicKey
  extends KeyczarPublicKey
{
  @Expose
  final String g = null;
  private final byte[] hash = new byte[4];
  private DSAPublicKey jcePublicKey = null;
  @Expose
  final String p = null;
  @Expose
  final String q = null;
  @Expose
  final String y = null;
  
  static DsaPublicKey read(String paramString)
    throws KeyczarException
  {
    DsaPublicKey localDsaPublicKey = (DsaPublicKey)Util.gson().fromJson(paramString, DsaPublicKey.class);
    localDsaPublicKey.initFromJson();
    return localDsaPublicKey;
  }
  
  protected final Stream getStream()
    throws KeyczarException
  {
    return new DsaVerifyingStream();
  }
  
  public final byte[] hash()
  {
    return this.hash;
  }
  
  final void initFromJson()
    throws KeyczarException
  {
    BigInteger localBigInteger1 = new BigInteger(Base64Coder.decodeWebSafe(this.y));
    BigInteger localBigInteger2 = new BigInteger(Base64Coder.decodeWebSafe(this.p));
    BigInteger localBigInteger3 = new BigInteger(Base64Coder.decodeWebSafe(this.q));
    BigInteger localBigInteger4 = new BigInteger(Base64Coder.decodeWebSafe(this.g));
    try
    {
      this.jcePublicKey = ((DSAPublicKey)KeyFactory.getInstance("DSA").generatePublic(new DSAPublicKeySpec(localBigInteger1, localBigInteger2, localBigInteger3, localBigInteger4)));
      DSAParams localDSAParams = this.jcePublicKey.getParams();
      byte[][] arrayOfByte = new byte[4][];
      arrayOfByte[0] = Util.stripLeadingZeros(localDSAParams.getP().toByteArray());
      arrayOfByte[1] = Util.stripLeadingZeros(localDSAParams.getQ().toByteArray());
      arrayOfByte[2] = Util.stripLeadingZeros(localDSAParams.getG().toByteArray());
      arrayOfByte[3] = Util.stripLeadingZeros(this.jcePublicKey.getY().toByteArray());
      System.arraycopy(Util.prefixHash(arrayOfByte), 0, this.hash, 0, this.hash.length);
      return;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new KeyczarException(localGeneralSecurityException);
    }
  }
  
  private final class DsaVerifyingStream
    implements VerifyingStream
  {
    private Signature signature;
    
    public DsaVerifyingStream()
      throws KeyczarException
    {
      try
      {
        this.signature = Signature.getInstance("SHA1withDSA");
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
      try
      {
        this.signature.initVerify(DsaPublicKey.this.jcePublicKey);
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
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
 * Qualified Name:     org.keyczar.DsaPublicKey
 * JD-Core Version:    0.7.0.1
 */