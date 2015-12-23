package com.google.android.wallet.nfc.communication;

public final class CommandApdu
{
  public final byte[] command;
  
  private CommandApdu(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte[] paramArrayOfByte, byte paramByte5, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i;
    if (paramBoolean1)
    {
      i = paramArrayOfByte.length;
      if (!paramBoolean1) {
        break label121;
      }
    }
    label121:
    for (int j = i + 5;; j = 4)
    {
      if (paramBoolean2) {
        j++;
      }
      this.command = new byte[j];
      this.command[0] = paramByte1;
      this.command[1] = paramByte2;
      this.command[2] = paramByte3;
      this.command[3] = paramByte4;
      if (paramBoolean1)
      {
        this.command[4] = ((byte)i);
        System.arraycopy(paramArrayOfByte, 0, this.command, 5, paramArrayOfByte.length);
      }
      if (paramBoolean2) {
        this.command[(j - 1)] = paramByte5;
      }
      return;
      i = 0;
      break;
    }
  }
  
  public static final class Builder
  {
    byte mCla = 0;
    private byte[] mData;
    private boolean mDataIsSet;
    private byte mIns;
    private byte mLe;
    private boolean mLeIsSet;
    byte mP1 = 0;
    byte mP2 = 0;
    
    public Builder(byte paramByte)
    {
      this.mIns = paramByte;
    }
    
    public final CommandApdu build()
    {
      return new CommandApdu(this.mCla, this.mIns, this.mP1, this.mP2, this.mData, this.mLe, this.mDataIsSet, this.mLeIsSet, (byte)0);
    }
    
    public final Builder setData(byte[] paramArrayOfByte)
    {
      if (paramArrayOfByte != null) {}
      for (boolean bool = true;; bool = false)
      {
        this.mDataIsSet = bool;
        this.mData = paramArrayOfByte;
        return this;
      }
    }
    
    public final Builder setLe(byte paramByte)
    {
      this.mLeIsSet = true;
      this.mLe = paramByte;
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.CommandApdu
 * JD-Core Version:    0.7.0.1
 */