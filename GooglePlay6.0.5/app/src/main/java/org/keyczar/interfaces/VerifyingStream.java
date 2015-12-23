package org.keyczar.interfaces;

import java.nio.ByteBuffer;
import org.keyczar.exceptions.KeyczarException;

public abstract interface VerifyingStream
  extends Stream
{
  public abstract void initVerify()
    throws KeyczarException;
  
  public abstract void updateVerify(ByteBuffer paramByteBuffer)
    throws KeyczarException;
  
  public abstract boolean verify(ByteBuffer paramByteBuffer)
    throws KeyczarException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.interfaces.VerifyingStream
 * JD-Core Version:    0.7.0.1
 */