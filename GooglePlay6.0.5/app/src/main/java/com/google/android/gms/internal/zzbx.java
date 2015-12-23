package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@zzhb
public final class zzbx
{
  public static int zzC(String paramString)
  {
    byte[] arrayOfByte1;
    try
    {
      byte[] arrayOfByte2 = paramString.getBytes("UTF-8");
      arrayOfByte1 = arrayOfByte2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        int k;
        int i9;
        arrayOfByte1 = paramString.getBytes();
      }
      n = i & 0x3;
      i1 = 0;
      switch (n)
      {
      default: 
        i3 = m;
      }
    }
    int i = arrayOfByte1.length;
    int j = 0 + (i & 0xFFFFFFFC);
    k = 0;
    for (int m = 0; k < j; m = i9)
    {
      int i7 = -862048943 * (0xFF & arrayOfByte1[k] | (0xFF & arrayOfByte1[(k + 1)]) << 8 | (0xFF & arrayOfByte1[(k + 2)]) << 16 | arrayOfByte1[(k + 3)] << 24);
      int i8 = m ^ 461845907 * (i7 << 15 | i7 >>> 17);
      i9 = -430675100 + 5 * (i8 << 13 | i8 >>> 19);
      k += 4;
    }
    for (;;)
    {
      int n;
      int i4 = i3 ^ i;
      int i5 = -2048144789 * (i4 ^ i4 >>> 16);
      int i6 = -1028477387 * (i5 ^ i5 >>> 13);
      return i6 ^ i6 >>> 16;
      int i1 = (0xFF & arrayOfByte1[(j + 2)]) << 16;
      i1 |= (0xFF & arrayOfByte1[(j + 1)]) << 8;
      int i2 = -862048943 * (i1 | 0xFF & arrayOfByte1[j]);
      int i3 = m ^ 461845907 * (i2 << 15 | i2 >>> 17);
    }
  }
  
  public static String[] zzD(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    char[] arrayOfChar = paramString.toCharArray();
    int i = paramString.length();
    int j = 0;
    int k = 0;
    int m = 0;
    int n;
    int i1;
    int i7;
    label184:
    int i8;
    label220:
    int i2;
    label228:
    int i3;
    int i4;
    if (m < i)
    {
      n = Character.codePointAt(arrayOfChar, m);
      i1 = Character.charCount(n);
      if (Character.isLetter(n))
      {
        Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(n);
        if ((localUnicodeBlock == Character.UnicodeBlock.BOPOMOFO) || (localUnicodeBlock == Character.UnicodeBlock.BOPOMOFO_EXTENDED) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) || (localUnicodeBlock == Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS) || (localUnicodeBlock == Character.UnicodeBlock.HANGUL_JAMO) || (localUnicodeBlock == Character.UnicodeBlock.HANGUL_SYLLABLES) || (localUnicodeBlock == Character.UnicodeBlock.HIRAGANA) || (localUnicodeBlock == Character.UnicodeBlock.KATAKANA) || (localUnicodeBlock == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS))
        {
          i7 = 1;
          if (i7 == 0)
          {
            if (((n < 65382) || (n > 65437)) && ((n < 65441) || (n > 65500))) {
              break label310;
            }
            i8 = 1;
            if (i8 == 0) {
              break label316;
            }
          }
          i2 = 1;
          if (i2 == 0) {
            break label322;
          }
          if (j != 0) {
            localArrayList.add(new String(arrayOfChar, k, m - k));
          }
          localArrayList.add(new String(arrayOfChar, m, i1));
          i3 = k;
          i4 = 0;
        }
      }
    }
    for (;;)
    {
      m += i1;
      int i5 = i4;
      k = i3;
      j = i5;
      break;
      i7 = 0;
      break label184;
      label310:
      i8 = 0;
      break label220;
      label316:
      i2 = 0;
      break label228;
      label322:
      if ((Character.isLetterOrDigit(n)) || (Character.getType(n) == 6) || (Character.getType(n) == 8))
      {
        if (j == 0) {
          k = m;
        }
        i3 = k;
        i4 = 1;
      }
      else if (j != 0)
      {
        localArrayList.add(new String(arrayOfChar, k, m - k));
        i3 = k;
        i4 = 0;
        continue;
        if (j != 0) {
          localArrayList.add(new String(arrayOfChar, k, m - k));
        }
        return (String[])localArrayList.toArray(new String[localArrayList.size()]);
      }
      else
      {
        int i6 = j;
        i3 = k;
        i4 = i6;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbx
 * JD-Core Version:    0.7.0.1
 */