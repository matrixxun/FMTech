package org.keyczar.exceptions;

import org.keyczar.i18n.Messages;

public final class NoPrimaryKeyException
  extends KeyNotFoundException
{
  public NoPrimaryKeyException()
  {
    super(Messages.getString("NoPrimaryKeyFound", new Object[0]));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.exceptions.NoPrimaryKeyException
 * JD-Core Version:    0.7.0.1
 */