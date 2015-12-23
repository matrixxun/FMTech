package org.keyczar.exceptions;

import org.keyczar.i18n.Messages;
import org.keyczar.interfaces.KeyType;

public final class UnsupportedTypeException
  extends KeyczarException
{
  public UnsupportedTypeException(KeyType paramKeyType)
  {
    super(Messages.getString("InvalidTypeInInput", new Object[] { paramKeyType }));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.exceptions.UnsupportedTypeException
 * JD-Core Version:    0.7.0.1
 */