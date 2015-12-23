package com.google.android.play.dfe.api;

public abstract interface DfeResponseVerifier
{
  public abstract String getSignatureRequest()
    throws DfeResponseVerifier.DfeResponseVerifierException;
  
  public abstract void verify(byte[] paramArrayOfByte, String paramString)
    throws DfeResponseVerifier.DfeResponseVerifierException;
  
  public static final class DfeResponseVerifierException
    extends Exception
  {
    public DfeResponseVerifierException(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.dfe.api.DfeResponseVerifier
 * JD-Core Version:    0.7.0.1
 */