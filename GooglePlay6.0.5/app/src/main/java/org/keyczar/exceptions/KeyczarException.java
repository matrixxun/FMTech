package org.keyczar.exceptions;

public class KeyczarException
  extends Exception
{
  public KeyczarException(String paramString)
  {
    super(paramString);
  }
  
  public KeyczarException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public KeyczarException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.exceptions.KeyczarException
 * JD-Core Version:    0.7.0.1
 */