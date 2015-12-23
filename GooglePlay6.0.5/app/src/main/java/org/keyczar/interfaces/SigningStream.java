package org.keyczar.interfaces;

import java.nio.ByteBuffer;
import org.keyczar.exceptions.KeyczarException;

public abstract interface SigningStream
  extends Stream
{
  public abstract int digestSize();
  
  public abstract void initSign()
    throws KeyczarException;
  
  public abstract void sign(ByteBuffer paramByteBuffer)
    throws KeyczarException;
  
  public abstract void updateSign(ByteBuffer paramByteBuffer)
    throws KeyczarException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.interfaces.SigningStream
 * JD-Core Version:    0.7.0.1
 */