package com.google.android.play.utils;

import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;

public final class DocV2Utils
{
  public static Common.Image getFirstImageOfType(DocV2 paramDocV2, int paramInt)
  {
    if ((paramDocV2 == null) || (paramDocV2.image == null)) {}
    for (;;)
    {
      return null;
      Common.Image[] arrayOfImage = paramDocV2.image;
      for (int i = 0; i < arrayOfImage.length; i++) {
        if (arrayOfImage[i].imageType == paramInt) {
          return arrayOfImage[i];
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.DocV2Utils
 * JD-Core Version:    0.7.0.1
 */