package com.google.android.wallet.nfc.exceptions;

public final class TagReadTimeoutException
  extends Exception
{
  public TagReadTimeoutException(Exception paramException)
  {
    super("Timeout when trying to read credit card information from NFC tag.", paramException);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.exceptions.TagReadTimeoutException
 * JD-Core Version:    0.7.0.1
 */