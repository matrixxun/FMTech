package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.KeyType;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.interfaces.Stream;
import org.keyczar.interfaces.VerifyingStream;
import org.keyczar.util.Base64Coder;
import org.keyczar.util.Util;

public class HmacKey
  extends KeyczarKey
{
  private final byte[] hash = new byte[4];
  SecretKey hmacKey;
  @Expose
  private final String hmacKeyString;
  
  private HmacKey()
  {
    super(0);
    this.hmacKeyString = null;
  }
  
  private HmacKey(byte[] paramArrayOfByte)
    throws KeyczarException
  {
    super(8 * paramArrayOfByte.length);
    this.hmacKeyString = Base64Coder.encodeWebSafe(paramArrayOfByte);
    initJceKey(paramArrayOfByte);
  }
  
  static HmacKey generate()
    throws KeyczarException
  {
    return new HmacKey(Util.rand(DefaultKeyType.HMAC_SHA1.defaultSize() / 8));
  }
  
  public static KeyType getType()
  {
    return DefaultKeyType.HMAC_SHA1;
  }
  
  private void initJceKey(byte[] paramArrayOfByte)
    throws KeyczarException
  {
    this.hmacKey = new SecretKeySpec(paramArrayOfByte, "HMACSHA1");
    System.arraycopy(Util.hash(new byte[][] { paramArrayOfByte }), 0, this.hash, 0, this.hash.length);
  }
  
  static HmacKey read(String paramString)
    throws KeyczarException
  {
    HmacKey localHmacKey = (HmacKey)Util.gson().fromJson(paramString, HmacKey.class);
    localHmacKey.initFromJson();
    return localHmacKey;
  }
  
  protected final Stream getStream()
    throws KeyczarException
  {
    return new HmacStream();
  }
  
  protected final byte[] hash()
  {
    return this.hash;
  }
  
  final void initFromJson()
    throws KeyczarException
  {
    initJceKey(Base64Coder.decodeWebSafe(this.hmacKeyString));
  }
  
  private final class HmacStream
    implements SigningStream, VerifyingStream
  {
    private final Mac hmac;
    
    public HmacStream()
      throws KeyczarException
    {
      try
      {
        this.hmac = Mac.getInstance("HMACSHA1");
        return;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new KeyczarException(localGeneralSecurityException);
      }
    }
    
    public final int digestSize()
    {
      return HmacKey.getType().getOutputSize();
    }
    
    public final void initSign()
      throws KeyczarException
    {
      try
      {
        this.hmac.init(HmacKey.this.hmacKey);
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
      initSign();
    }
    
    public final void sign(ByteBuffer paramByteBuffer)
    {
      paramByteBuffer.put(this.hmac.doFinal());
    }
    
    public final void updateSign(ByteBuffer paramByteBuffer)
    {
      this.hmac.update(paramByteBuffer);
    }
    
    public final void updateVerify(ByteBuffer paramByteBuffer)
    {
      updateSign(paramByteBuffer);
    }
    
    public final boolean verify(ByteBuffer paramByteBuffer)
    {
      byte[] arrayOfByte = new byte[paramByteBuffer.remaining()];
      paramByteBuffer.get(arrayOfByte);
      return Util.safeArrayEquals(this.hmac.doFinal(), arrayOfByte);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.HmacKey
 * JD-Core Version:    0.7.0.1
 */