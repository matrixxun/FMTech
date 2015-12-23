package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import java.io.IOException;

public final class IOUtils
{
  public static void closeQuietly(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (paramParcelFileDescriptor != null) {}
    try
    {
      paramParcelFileDescriptor.close();
      return;
    }
    catch (IOException localIOException) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.util.IOUtils
 * JD-Core Version:    0.7.0.1
 */