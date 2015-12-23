package com.google.android.finsky.api.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.KeyczarReader;

public final class AndroidKeyczarReader
  implements KeyczarReader
{
  private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
  private final AssetManager mAssetManager;
  private final String mSubDirectory;
  
  public AndroidKeyczarReader(Resources paramResources, String paramString)
  {
    this.mAssetManager = paramResources.getAssets();
    this.mSubDirectory = paramString;
  }
  
  private String getFileContentAsString(String paramString)
    throws KeyczarException
  {
    StringBuilder localStringBuilder;
    for (;;)
    {
      try
      {
        localStringBuilder = new StringBuilder();
        char[] arrayOfChar = new char[1024];
        AssetManager localAssetManager = this.mAssetManager;
        if (this.mSubDirectory == null)
        {
          InputStreamReader localInputStreamReader = new InputStreamReader(localAssetManager.open(paramString), CHARSET_UTF8);
          int i = localInputStreamReader.read(arrayOfChar);
          if (i <= 0) {
            break;
          }
          localStringBuilder.append(arrayOfChar, 0, i);
          continue;
        }
        paramString = this.mSubDirectory + File.separator + paramString;
      }
      catch (IOException localIOException)
      {
        throw new KeyczarException("Couldn't read Keyczar 'meta' file from assets/", localIOException);
      }
    }
    String str = localStringBuilder.toString();
    return str;
  }
  
  public final String getKey(int paramInt)
    throws KeyczarException
  {
    return getFileContentAsString(String.valueOf(paramInt));
  }
  
  public final String getMetadata()
    throws KeyczarException
  {
    return getFileContentAsString("meta");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.utils.AndroidKeyczarReader
 * JD-Core Version:    0.7.0.1
 */