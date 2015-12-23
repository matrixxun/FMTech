package com.google.android.wallet.nfc.communication;

import java.util.List;

public final class TlvDatum
{
  public final List<TlvDatum> subTlvData;
  public final byte[] tag;
  public final byte[] value;
  
  public TlvDatum(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, List<TlvDatum> paramList)
  {
    this.tag = paramArrayOfByte1;
    this.value = paramArrayOfByte2;
    this.subTlvData = paramList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.TlvDatum
 * JD-Core Version:    0.7.0.1
 */