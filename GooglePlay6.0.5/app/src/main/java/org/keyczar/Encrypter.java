package org.keyczar;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.exceptions.NoPrimaryKeyException;
import org.keyczar.i18n.Messages;
import org.keyczar.interfaces.EncryptingStream;
import org.keyczar.interfaces.KeyczarReader;
import org.keyczar.interfaces.SigningStream;
import org.keyczar.util.Base64Coder;

public class Encrypter
  extends Keyczar
{
  private static final Logger LOG = Logger.getLogger(Encrypter.class);
  private final StreamQueue<EncryptingStream> ENCRYPT_QUEUE = new StreamQueue();
  
  public Encrypter(KeyczarReader paramKeyczarReader)
    throws KeyczarException
  {
    super(paramKeyczarReader);
  }
  
  public final String encrypt(String paramString)
    throws KeyczarException
  {
    try
    {
      String str = Base64Coder.encodeWebSafe(encrypt(paramString.getBytes("UTF-8")));
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new KeyczarException(localUnsupportedEncodingException);
    }
  }
  
  public final byte[] encrypt(byte[] paramArrayOfByte)
    throws KeyczarException
  {
    int i = paramArrayOfByte.length;
    EncryptingStream localEncryptingStream1 = (EncryptingStream)this.ENCRYPT_QUEUE.poll();
    if (localEncryptingStream1 == null)
    {
      KeyczarKey localKeyczarKey2 = getPrimaryKey();
      if (localKeyczarKey2 == null) {
        throw new NoPrimaryKeyException();
      }
      localEncryptingStream1 = (EncryptingStream)localKeyczarKey2.getStream();
    }
    SigningStream localSigningStream1 = localEncryptingStream1.getSigningStream();
    int j = 5 + localEncryptingStream1.maxOutputSize(i) + localSigningStream1.digestSize();
    this.ENCRYPT_QUEUE.add(localEncryptingStream1);
    ByteBuffer localByteBuffer1 = ByteBuffer.allocate(j);
    ByteBuffer localByteBuffer2 = ByteBuffer.wrap(paramArrayOfByte);
    Logger localLogger = LOG;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localByteBuffer2.remaining());
    localLogger.debug(Messages.getString("Encrypter.Encrypting", arrayOfObject));
    KeyczarKey localKeyczarKey1 = getPrimaryKey();
    if (localKeyczarKey1 == null) {
      throw new NoPrimaryKeyException();
    }
    EncryptingStream localEncryptingStream2 = (EncryptingStream)this.ENCRYPT_QUEUE.poll();
    if (localEncryptingStream2 == null) {
      localEncryptingStream2 = (EncryptingStream)localKeyczarKey1.getStream();
    }
    SigningStream localSigningStream2 = localEncryptingStream2.getSigningStream();
    localSigningStream2.initSign();
    localByteBuffer1.mark();
    ByteBuffer localByteBuffer3 = localByteBuffer1.asReadOnlyBuffer();
    localKeyczarKey1.copyHeader(localByteBuffer1);
    localEncryptingStream2.initEncrypt(localByteBuffer1);
    ByteBuffer localByteBuffer4 = localByteBuffer2.asReadOnlyBuffer();
    while (localByteBuffer4.remaining() > 1024)
    {
      ByteBuffer localByteBuffer5 = localByteBuffer4.slice();
      localByteBuffer5.limit(1024);
      localEncryptingStream2.updateEncrypt(localByteBuffer5, localByteBuffer1);
      localByteBuffer4.position(1024 + localByteBuffer4.position());
      localByteBuffer3.limit(localByteBuffer1.position());
      localSigningStream2.updateSign(localByteBuffer3);
      localByteBuffer3.position(localByteBuffer1.position());
    }
    localEncryptingStream2.doFinalEncrypt(localByteBuffer4, localByteBuffer1);
    localByteBuffer1.limit(localByteBuffer1.position() + localSigningStream2.digestSize());
    localByteBuffer3.limit(localByteBuffer1.position());
    localSigningStream2.updateSign(localByteBuffer3);
    localSigningStream2.sign(localByteBuffer1);
    this.ENCRYPT_QUEUE.add(localEncryptingStream2);
    localByteBuffer1.reset();
    byte[] arrayOfByte = new byte[localByteBuffer1.remaining()];
    localByteBuffer1.get(arrayOfByte);
    return arrayOfByte;
  }
  
  boolean isAcceptablePurpose(KeyPurpose paramKeyPurpose)
  {
    return (paramKeyPurpose == KeyPurpose.ENCRYPT) || (paramKeyPurpose == KeyPurpose.DECRYPT_AND_ENCRYPT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.Encrypter
 * JD-Core Version:    0.7.0.1
 */