package com.google.android.wallet.nfc.communication;

import com.google.android.wallet.common.util.DataTypeConversionUtil;

public final class ApduUtil
{
  public static final String[] SUPPORTED_AIDS = { "A0000000041010", "A00000000401", "A0000000042010", "A0000000042203", "A0000000043010", "A0000000044010", "A0000000045010", "A0000000043060", "A0000000050001", "A0000000101030", "A0000000031010", "A0000000032020", "A0000000032010", "A0000000033010", "A0000000034010", "A0000000035010", "A0000000038010", "A0000000039010", "A0000", "A000000025", "A00000006510" };
  public static final String[] SUPPORTED_PSE_AIDS = { "315041592E5359532E4444463031", "325041592E5359532E4444463031" };
  
  public static CommandApdu buildGpoCommand(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      if (!paramBoolean) {
        break label50;
      }
    }
    label50:
    for (String str = "830B0000000000000000000000";; str = "8300")
    {
      paramArrayOfByte = DataTypeConversionUtil.hexStringToByteArray(str);
      CommandApdu.Builder localBuilder = new CommandApdu.Builder((byte)-88);
      localBuilder.mCla = -128;
      return localBuilder.setData(paramArrayOfByte).setLe((byte)0).build();
    }
  }
  
  public static CommandApdu buildReadCommand(byte paramByte1, byte paramByte2)
  {
    return buildReadCommand(paramByte1, paramByte2, (byte)0);
  }
  
  public static CommandApdu buildReadCommand(byte paramByte1, byte paramByte2, byte paramByte3)
  {
    CommandApdu.Builder localBuilder = new CommandApdu.Builder((byte)-78);
    localBuilder.mP1 = paramByte1;
    localBuilder.mP2 = ((byte)(0x4 | paramByte2 << 3));
    return localBuilder.setLe(paramByte3).build();
  }
  
  public static CommandApdu buildSelectCommand(String paramString)
  {
    CommandApdu.Builder localBuilder = new CommandApdu.Builder((byte)-92);
    localBuilder.mP1 = 4;
    return localBuilder.setData(DataTypeConversionUtil.hexStringToByteArray(paramString)).setLe((byte)0).build();
  }
  
  public static boolean matchesStatus(ResponseApdu paramResponseApdu, byte[] paramArrayOfByte)
  {
    if ((0xF & paramResponseApdu.statusWord1 >>> 4) != paramArrayOfByte[0]) {}
    while (((paramArrayOfByte.length >= 2) && ((0xF & paramResponseApdu.statusWord1) != paramArrayOfByte[1])) || ((paramArrayOfByte.length >= 3) && ((0xF & paramResponseApdu.statusWord2 >>> 4) != paramArrayOfByte[2])) || ((paramArrayOfByte.length == 4) && ((0xF & paramResponseApdu.statusWord2) != paramArrayOfByte[3]))) {
      return false;
    }
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.ApduUtil
 * JD-Core Version:    0.7.0.1
 */