package com.google.android.wallet.nfc.communication;

public final class TlObject
{
  public final byte length;
  public final byte[] tag;
  
  public TlObject(byte[] paramArrayOfByte, byte paramByte)
  {
    this.tag = paramArrayOfByte;
    this.length = paramByte;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.TlObject
 * JD-Core Version:    0.7.0.1
 */