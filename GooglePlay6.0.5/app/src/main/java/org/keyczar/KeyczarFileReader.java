package org.keyczar;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.i18n.Messages;
import org.keyczar.interfaces.KeyczarReader;

public final class KeyczarFileReader
  implements KeyczarReader
{
  private String location;
  
  public KeyczarFileReader(String paramString)
  {
    if ((paramString != null) && (!paramString.endsWith(File.separator))) {
      paramString = paramString + File.separator;
    }
    this.location = paramString;
  }
  
  private static String readFile(String paramString)
    throws KeyczarException
  {
    try
    {
      RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString, "r");
      byte[] arrayOfByte = new byte[(int)localRandomAccessFile.length()];
      localRandomAccessFile.read(arrayOfByte);
      localRandomAccessFile.close();
      String str = new String(arrayOfByte);
      return str;
    }
    catch (IOException localIOException)
    {
      throw new KeyczarException(Messages.getString("KeyczarFileReader.FileError", new Object[] { paramString }), localIOException);
    }
  }
  
  public final String getKey(int paramInt)
    throws KeyczarException
  {
    return readFile(this.location + paramInt);
  }
  
  public final String getMetadata()
    throws KeyczarException
  {
    return readFile(this.location + "meta");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.KeyczarFileReader
 * JD-Core Version:    0.7.0.1
 */