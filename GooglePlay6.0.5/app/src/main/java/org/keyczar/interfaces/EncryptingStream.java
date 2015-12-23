package org.keyczar.interfaces;

import java.nio.ByteBuffer;
import org.keyczar.exceptions.KeyczarException;

public abstract interface EncryptingStream
  extends Stream
{
  public abstract int doFinalEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    throws KeyczarException;
  
  public abstract SigningStream getSigningStream()
    throws KeyczarException;
  
  public abstract int initEncrypt(ByteBuffer paramByteBuffer)
    throws KeyczarException;
  
  public abstract int maxOutputSize(int paramInt);
  
  public abstract int updateEncrypt(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    throws KeyczarException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.interfaces.EncryptingStream
 * JD-Core Version:    0.7.0.1
 */