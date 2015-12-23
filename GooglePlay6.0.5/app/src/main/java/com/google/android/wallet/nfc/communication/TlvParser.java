package com.google.android.wallet.nfc.communication;

import com.google.android.wallet.common.util.DataTypeConversionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class TlvParser
{
  static List<TlObject> parseDol(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramInt >= paramArrayOfByte.length) {
      return new LinkedList();
    }
    if ((0x1F & 1 + paramArrayOfByte[paramInt]) == 0) {}
    for (int i = paramInt + 2;; i = paramInt + 1)
    {
      byte[] arrayOfByte = Arrays.copyOfRange(paramArrayOfByte, paramInt, i);
      byte b = paramArrayOfByte[i];
      List localList = parseDol(paramArrayOfByte, i + 1);
      localList.add(0, new TlObject(arrayOfByte, b));
      return localList;
    }
  }
  
  public static TlvDatum parseTlv(ResponseApdu paramResponseApdu)
  {
    if (paramResponseApdu.message.length == 0) {
      return null;
    }
    return (TlvDatum)parseTlv(paramResponseApdu.message, 0).get(0);
  }
  
  private static List<TlvDatum> parseTlv(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramInt >= paramArrayOfByte.length) {
      return new LinkedList();
    }
    int i = paramArrayOfByte[paramInt];
    int j;
    int k;
    label41:
    byte[] arrayOfByte1;
    int m;
    int n;
    label75:
    byte[] arrayOfByte2;
    int i1;
    label101:
    byte[] arrayOfByte3;
    List localList;
    if ((i & 0x20) != 0)
    {
      j = 1;
      if ((0x1F & i + 1) != 0) {
        break label166;
      }
      k = paramInt + 2;
      arrayOfByte1 = Arrays.copyOfRange(paramArrayOfByte, paramInt, k);
      m = paramArrayOfByte[k];
      n = k + 1;
      if (m != -127) {
        break label174;
      }
      n = k + 2;
      arrayOfByte2 = Arrays.copyOfRange(paramArrayOfByte, k, n);
      if (arrayOfByte2.length != 1) {
        break label190;
      }
      i1 = DataTypeConversionUtil.unsignedByteToInt(arrayOfByte2[0]);
      arrayOfByte3 = Arrays.copyOfRange(paramArrayOfByte, n, n + i1);
      localList = parseTlv(paramArrayOfByte, n + i1);
      if (j == 0) {
        break label232;
      }
    }
    label166:
    label174:
    label190:
    label232:
    for (Object localObject = parseTlv(arrayOfByte3, 0);; localObject = new ArrayList())
    {
      localList.add(0, new TlvDatum(arrayOfByte1, arrayOfByte3, (List)localObject));
      return localList;
      j = 0;
      break;
      k = paramInt + 1;
      break label41;
      if (m != -126) {
        break label75;
      }
      n = k + 3;
      break label75;
      if (arrayOfByte2.length == 2)
      {
        i1 = DataTypeConversionUtil.unsignedByteToInt(arrayOfByte2[1]);
        break label101;
      }
      i1 = DataTypeConversionUtil.unsignedByteToInt(arrayOfByte2[1]) << 8 | DataTypeConversionUtil.unsignedByteToInt(arrayOfByte2[2]);
      break label101;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.TlvParser
 * JD-Core Version:    0.7.0.1
 */