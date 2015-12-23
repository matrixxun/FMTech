package org.keyczar.exceptions;

import org.keyczar.i18n.Messages;

public class KeyNotFoundException
  extends KeyczarException
{
  KeyNotFoundException(String paramString)
  {
    super(paramString);
  }
  
  public KeyNotFoundException(byte[] paramArrayOfByte)
  {
    super(Messages.getString("KeyWithHashIdentifier", arrayOfObject));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.exceptions.KeyNotFoundException
 * JD-Core Version:    0.7.0.1
 */