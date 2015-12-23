package org.keyczar.exceptions;

import org.keyczar.i18n.Messages;

public final class BadVersionException
  extends KeyczarException
{
  public BadVersionException(byte paramByte)
  {
    super(Messages.getString("BadVersionNumber", arrayOfObject));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.exceptions.BadVersionException
 * JD-Core Version:    0.7.0.1
 */