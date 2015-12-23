package org.keyczar;

import java.nio.ByteBuffer;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.exceptions.BadVersionException;
import org.keyczar.exceptions.KeyNotFoundException;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.exceptions.ShortSignatureException;
import org.keyczar.i18n.Messages;
import org.keyczar.interfaces.KeyczarReader;
import org.keyczar.interfaces.VerifyingStream;

public class Verifier
  extends Keyczar
{
  private static final Logger LOG = Logger.getLogger(Verifier.class);
  private final StreamCache<VerifyingStream> VERIFY_CACHE = new StreamCache();
  
  public Verifier(KeyczarReader paramKeyczarReader)
    throws KeyczarException
  {
    super(paramKeyczarReader);
  }
  
  boolean isAcceptablePurpose(KeyPurpose paramKeyPurpose)
  {
    return (paramKeyPurpose == KeyPurpose.VERIFY) || (paramKeyPurpose == KeyPurpose.SIGN_AND_VERIFY);
  }
  
  public final boolean verify$36a85960(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    throws KeyczarException
  {
    Logger localLogger = LOG;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramByteBuffer1.remaining());
    localLogger.debug(Messages.getString("Verifier.Verifying", arrayOfObject));
    if (paramByteBuffer2.remaining() < 5) {
      throw new ShortSignatureException(paramByteBuffer2.remaining());
    }
    byte b = paramByteBuffer2.get();
    if (b != 0) {
      throw new BadVersionException(b);
    }
    byte[] arrayOfByte = new byte[4];
    paramByteBuffer2.get(arrayOfByte);
    KeyczarKey localKeyczarKey = (KeyczarKey)this.hashMap.get(new Keyczar.KeyHash(this, arrayOfByte, (byte)0));
    if (localKeyczarKey == null) {
      throw new KeyNotFoundException(arrayOfByte);
    }
    if (localKeyczarKey == null) {
      throw new KeyNotFoundException(arrayOfByte);
    }
    VerifyingStream localVerifyingStream = (VerifyingStream)this.VERIFY_CACHE.getQueue(localKeyczarKey).poll();
    if (localVerifyingStream == null) {
      localVerifyingStream = (VerifyingStream)localKeyczarKey.getStream();
    }
    localVerifyingStream.initVerify();
    localVerifyingStream.updateVerify(paramByteBuffer1);
    localVerifyingStream.updateVerify(ByteBuffer.wrap(FORMAT_BYTES));
    boolean bool = localVerifyingStream.verify(paramByteBuffer2);
    this.VERIFY_CACHE.getQueue(localKeyczarKey).add(localVerifyingStream);
    return bool;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.Verifier
 * JD-Core Version:    0.7.0.1
 */