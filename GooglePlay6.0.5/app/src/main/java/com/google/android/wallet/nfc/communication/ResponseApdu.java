package com.google.android.wallet.nfc.communication;

import java.util.Arrays;

public final class ResponseApdu
{
  public static final byte[] SUCCESS_SW = { 9, 0, 0, 0 };
  public static final byte[] WRONG_LENGTH_LE = { 6, 12 };
  public final byte[] message;
  public final byte statusWord1;
  public final byte statusWord2;
  
  public ResponseApdu(byte[] paramArrayOfByte)
  {
    this.message = Arrays.copyOfRange(paramArrayOfByte, 0, -2 + paramArrayOfByte.length);
    this.statusWord1 = paramArrayOfByte[(-2 + paramArrayOfByte.length)];
    this.statusWord2 = paramArrayOfByte[(-1 + paramArrayOfByte.length)];
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.ResponseApdu
 * JD-Core Version:    0.7.0.1
 */