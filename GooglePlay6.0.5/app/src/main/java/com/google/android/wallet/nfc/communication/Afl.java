package com.google.android.wallet.nfc.communication;

public final class Afl
{
  public final byte endRecord;
  public final byte numRecords;
  public final byte sfi;
  public final byte startRecord;
  
  public Afl(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4)
  {
    this.startRecord = paramByte1;
    this.endRecord = paramByte2;
    this.sfi = paramByte3;
    this.numRecords = paramByte4;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.Afl
 * JD-Core Version:    0.7.0.1
 */