package org.keyczar;

import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.exceptions.NoPrimaryKeyException;
import org.keyczar.interfaces.KeyczarReader;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.util.Util;

public class Signer
  extends Verifier
{
  private static final Logger LOG = Logger.getLogger(Signer.class);
  private final StreamQueue<SigningStream> SIGN_QUEUE = new StreamQueue();
  
  public Signer(KeyczarReader paramKeyczarReader)
    throws KeyczarException
  {
    super(paramKeyczarReader);
  }
  
  public final byte[] attachedSign(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws KeyczarException
  {
    KeyczarKey localKeyczarKey1 = getPrimaryKey();
    if (localKeyczarKey1 == null) {
      throw new NoPrimaryKeyException();
    }
    SigningStream localSigningStream = (SigningStream)this.SIGN_QUEUE.poll();
    if (localSigningStream == null) {
      localSigningStream = (SigningStream)localKeyczarKey1.getStream();
    }
    localSigningStream.initSign();
    byte[] arrayOfByte1 = Util.fromInt(0);
    if (paramArrayOfByte2.length > 0) {
      arrayOfByte1 = Util.lenPrefix(paramArrayOfByte2);
    }
    localSigningStream.updateSign(ByteBuffer.wrap(paramArrayOfByte1));
    localSigningStream.updateSign(ByteBuffer.wrap(arrayOfByte1));
    localSigningStream.updateSign(ByteBuffer.wrap(FORMAT_BYTES));
    KeyczarKey localKeyczarKey2 = getPrimaryKey();
    if (localKeyczarKey2 == null) {
      throw new NoPrimaryKeyException();
    }
    ByteBuffer localByteBuffer = ByteBuffer.allocate(5 + ((SigningStream)localKeyczarKey2.getStream()).digestSize());
    localByteBuffer.mark();
    localSigningStream.sign(localByteBuffer);
    localByteBuffer.limit(localByteBuffer.position());
    byte[][] arrayOfByte = new byte[4][];
    arrayOfByte[0] = FORMAT_BYTES;
    arrayOfByte[1] = localKeyczarKey1.hash();
    arrayOfByte[2] = Util.lenPrefix(paramArrayOfByte1);
    arrayOfByte[3] = localByteBuffer.array();
    byte[] arrayOfByte2 = Util.cat(arrayOfByte);
    this.SIGN_QUEUE.add(localSigningStream);
    return arrayOfByte2;
  }
  
  final boolean isAcceptablePurpose(KeyPurpose paramKeyPurpose)
  {
    return paramKeyPurpose == KeyPurpose.SIGN_AND_VERIFY;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.Signer
 * JD-Core Version:    0.7.0.1
 */