package android.support.v4.util;

import java.io.PrintWriter;

public final class TimeUtils
{
  private static char[] sFormatStr = new char[24];
  private static final Object sFormatSync = new Object();
  
  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter)
  {
    if (paramLong1 == 0L)
    {
      paramPrintWriter.print("--");
      return;
    }
    formatDuration$112769eb(paramLong1 - paramLong2, paramPrintWriter);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter)
  {
    formatDuration$112769eb(paramLong, paramPrintWriter);
  }
  
  private static void formatDuration$112769eb(long paramLong, PrintWriter paramPrintWriter)
  {
    int i = 1;
    for (;;)
    {
      int m;
      int j;
      synchronized (sFormatSync)
      {
        if (sFormatStr.length < 0) {
          sFormatStr = new char[0];
        }
        char[] arrayOfChar = sFormatStr;
        if (paramLong != 0L) {
          break label318;
        }
        arrayOfChar[0] = '0';
        paramPrintWriter.print(new String(sFormatStr, 0, i));
        return;
        int k = (int)(paramLong % 1000L);
        m = (int)Math.floor(paramLong / 1000L);
        if (m <= 86400) {
          break label312;
        }
        n = m / 86400;
        m -= n * 86400;
        if (m <= 3600) {
          break label306;
        }
        int i1 = m / 3600;
        m -= i1 * 3600;
        i2 = i1;
        if (m > 60)
        {
          int i3 = m / 60;
          int i4 = m - i3 * 60;
          i5 = i3;
          i6 = i4;
          arrayOfChar[0] = j;
          int i7 = printField(arrayOfChar, n, 'd', 1, false, 0);
          if (i7 == i) {
            break label341;
          }
          int i8 = i;
          int i10 = printField(arrayOfChar, i2, 'h', i7, i8, 0);
          if (i10 == i) {
            break label347;
          }
          int i11 = i;
          int i13 = printField(arrayOfChar, i5, 'm', i10, i11, 0);
          if (i13 == i) {
            break label353;
          }
          int i14 = i;
          int i16 = printField(arrayOfChar, k, 'm', printField(arrayOfChar, i6, 's', i13, i14, 0), true, 0);
          arrayOfChar[i16] = 's';
          i = i16 + 1;
        }
      }
      int i6 = m;
      int i5 = 0;
      continue;
      label306:
      int i2 = 0;
      continue;
      label312:
      int n = 0;
      continue;
      label318:
      if (paramLong > 0L)
      {
        j = 43;
      }
      else
      {
        paramLong = -paramLong;
        j = 45;
        continue;
        label341:
        int i9 = 0;
        continue;
        label347:
        int i12 = 0;
        continue;
        label353:
        int i15 = 0;
      }
    }
  }
  
  private static int printField(char[] paramArrayOfChar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if ((paramBoolean) || (paramInt1 > 0))
    {
      int i = paramInt2;
      if (paramInt1 > 99)
      {
        int m = paramInt1 / 100;
        paramArrayOfChar[paramInt2] = ((char)(m + 48));
        paramInt2++;
        paramInt1 -= m * 100;
      }
      if ((paramInt1 > 9) || (i != paramInt2))
      {
        int j = paramInt1 / 10;
        paramArrayOfChar[paramInt2] = ((char)(j + 48));
        paramInt2++;
        paramInt1 -= j * 10;
      }
      paramArrayOfChar[paramInt2] = ((char)(paramInt1 + 48));
      int k = paramInt2 + 1;
      paramArrayOfChar[k] = paramChar;
      paramInt2 = k + 1;
    }
    return paramInt2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.util.TimeUtils
 * JD-Core Version:    0.7.0.1
 */