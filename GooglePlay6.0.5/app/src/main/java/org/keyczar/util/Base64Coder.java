package org.keyczar.util;

import org.keyczar.exceptions.Base64DecodingException;
import org.keyczar.i18n.Messages;

public final class Base64Coder
{
  private static final char[] ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  private static final byte[] DECODE = new byte[''];
  private static final char[] WHITESPACE = { 9, 10, 13, 32, 12 };
  
  static
  {
    for (int i = 0; i < DECODE.length; i++) {
      DECODE[i] = -1;
    }
    for (int j = 0; j < WHITESPACE.length; j++) {
      DECODE[WHITESPACE[j]] = -2;
    }
    for (int k = 0; k < ALPHABET.length; k++) {
      DECODE[ALPHABET[k]] = ((byte)k);
    }
  }
  
  public static byte[] decodeWebSafe(String paramString)
    throws Base64DecodingException
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    if (arrayOfChar[(i - 1)] == '=') {
      i--;
    }
    if (arrayOfChar[(i - 1)] == '=') {
      i--;
    }
    int j = 0;
    int k = arrayOfChar.length;
    for (int m = 0; m < k; m++) {
      if (isWhiteSpace(arrayOfChar[m])) {
        j++;
      }
    }
    int n = i - j;
    int i1 = n / 4;
    int i2 = n % 4;
    int i3 = i1 * 3;
    switch (i2)
    {
    }
    byte[] arrayOfByte;
    int i5;
    int i6;
    int i7;
    int i12;
    int i13;
    for (;;)
    {
      arrayOfByte = new byte[i3];
      i4 = 0;
      i5 = 0;
      i6 = 0;
      i7 = 0;
      if (i6 >= n + j) {
        break label348;
      }
      if (isWhiteSpace(arrayOfChar[i6])) {
        break label278;
      }
      i12 = i4 << 6;
      i13 = arrayOfChar[i6];
      if ((i13 >= 0) && (i13 <= 127) && (DECODE[i13] != -1)) {
        break;
      }
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i13);
      throw new Base64DecodingException(Messages.getString("Base64Coder.IllegalCharacter", arrayOfObject1));
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(n);
      throw new Base64DecodingException(Messages.getString("Base64Coder.IllegalLength", arrayOfObject2));
      i3++;
      continue;
      i3 += 2;
    }
    int i4 = i12 | DECODE[i13];
    i5++;
    label278:
    int i9;
    if (i5 == 4)
    {
      int i10 = i7 + 1;
      arrayOfByte[i7] = ((byte)(i4 >> 16));
      int i11 = i10 + 1;
      arrayOfByte[i10] = ((byte)(i4 >> 8));
      i9 = i11 + 1;
      arrayOfByte[i11] = ((byte)i4);
      i4 = 0;
      i5 = 0;
    }
    for (;;)
    {
      i6++;
      i7 = i9;
      break;
      switch (i5)
      {
      default: 
        return arrayOfByte;
      case 2: 
        label348:
        arrayOfByte[i7] = ((byte)(i4 >> 4));
        return arrayOfByte;
      }
      int i8 = i7 + 1;
      arrayOfByte[i7] = ((byte)(i4 >> 10));
      arrayOfByte[i8] = ((byte)(i4 >> 2));
      return arrayOfByte;
      i9 = i7;
    }
  }
  
  public static String encodeWebSafe(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length / 3;
    int j = paramArrayOfByte.length % 3;
    int k = i * 4;
    switch (j)
    {
    }
    char[] arrayOfChar;
    int n;
    int i1;
    for (;;)
    {
      arrayOfChar = new char[k];
      int m = 0;
      n = 0;
      i1 = 0;
      while (m < i)
      {
        int i6 = n + 1;
        int i7 = (0xFF & paramArrayOfByte[n]) << 16;
        int i8 = i6 + 1;
        int i9 = i7 | (0xFF & paramArrayOfByte[i6]) << 8;
        int i10 = i8 + 1;
        int i11 = i9 | 0xFF & paramArrayOfByte[i8];
        int i12 = i1 + 1;
        arrayOfChar[i1] = ALPHABET[(0x3F & i11 >> 18)];
        int i13 = i12 + 1;
        arrayOfChar[i12] = ALPHABET[(0x3F & i11 >> 12)];
        int i14 = i13 + 1;
        arrayOfChar[i13] = ALPHABET[(0x3F & i11 >> 6)];
        i1 = i14 + 1;
        arrayOfChar[i14] = ALPHABET[(i11 & 0x3F)];
        m++;
        n = i10;
      }
      k += 2;
      continue;
      k += 3;
    }
    if (j > 0)
    {
      int i2 = n + 1;
      int i3 = (0xFF & paramArrayOfByte[n]) << 16;
      if (j == 2) {
        i3 |= (0xFF & paramArrayOfByte[i2]) << 8;
      }
      int i4 = i1 + 1;
      arrayOfChar[i1] = ALPHABET[(0x3F & i3 >> 18)];
      int i5 = i4 + 1;
      arrayOfChar[i4] = ALPHABET[(0x3F & i3 >> 12)];
      if (j == 2) {
        arrayOfChar[i5] = ALPHABET[(0x3F & i3 >> 6)];
      }
    }
    for (;;)
    {
      return new String(arrayOfChar);
    }
  }
  
  private static boolean isWhiteSpace(int paramInt)
  {
    return DECODE[paramInt] == -2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.util.Base64Coder
 * JD-Core Version:    0.7.0.1
 */