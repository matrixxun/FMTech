package org.keyczar.exceptions;

import org.keyczar.i18n.Messages;

public final class ShortSignatureException
  extends KeyczarException
{
  public ShortSignatureException(int paramInt)
  {
    super(Messages.getString("SignatureTooShort", arrayOfObject));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.exceptions.ShortSignatureException
 * JD-Core Version:    0.7.0.1
 */