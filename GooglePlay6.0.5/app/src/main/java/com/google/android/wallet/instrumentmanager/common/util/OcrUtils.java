package com.google.android.wallet.instrumentmanager.common.util;

public final class OcrUtils
{
  public static int ocrResultCodeToExitReason(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0;
    case -1: 
      return 1;
    case 0: 
    case 10007: 
      return 3;
    case 10001: 
    case 10004: 
      return 2;
    }
    return 4;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.common.util.OcrUtils
 * JD-Core Version:    0.7.0.1
 */