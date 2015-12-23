package org.keyczar.interfaces;

import org.keyczar.exceptions.KeyczarException;

public abstract interface KeyczarReader
{
  public abstract String getKey(int paramInt)
    throws KeyczarException;
  
  public abstract String getMetadata()
    throws KeyczarException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.interfaces.KeyczarReader
 * JD-Core Version:    0.7.0.1
 */