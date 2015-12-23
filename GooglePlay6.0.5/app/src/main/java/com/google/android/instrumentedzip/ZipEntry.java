package com.google.android.instrumentedzip;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipException;

public final class ZipEntry
{
  long compressedSize = -1L;
  int compressionMethod = -1;
  long localHeaderRelOffset = -1L;
  public String name;
  int nameLength = -1;
  long size = -1L;
  public int verificationErrors;
  
  ZipEntry(byte[] paramArrayOfByte, InputStream paramInputStream)
    throws IOException
  {
    Utils.readFully(paramInputStream, paramArrayOfByte, 0, 46);
    HeapBufferIterator localHeapBufferIterator = new HeapBufferIterator(paramArrayOfByte);
    int i = localHeapBufferIterator.readInt();
    if (i != 33639248L) {
      ZipFile.throwZipException("Central Directory Entry", i);
    }
    localHeapBufferIterator.position = 8;
    int j = 0xFFFF & localHeapBufferIterator.readShort();
    if ((j & 0x1) != 0) {
      throw new ZipException("Invalid General Purpose Bit Flag: " + j);
    }
    this.compressionMethod = (0xFFFF & localHeapBufferIterator.readShort());
    localHeapBufferIterator.readShort();
    localHeapBufferIterator.readShort();
    localHeapBufferIterator.readInt();
    this.compressedSize = (0xFFFFFFFF & localHeapBufferIterator.readInt());
    this.size = (0xFFFFFFFF & localHeapBufferIterator.readInt());
    this.nameLength = (0xFFFF & localHeapBufferIterator.readShort());
    int k = 0xFFFF & localHeapBufferIterator.readShort();
    int m = 0xFFFF & localHeapBufferIterator.readShort();
    if (k >= 32768) {
      this.verificationErrors = (0x1 | this.verificationErrors);
    }
    if (m >= 32768) {
      this.verificationErrors = (0x2 | this.verificationErrors);
    }
    localHeapBufferIterator.position = 42;
    this.localHeaderRelOffset = (0xFFFFFFFF & localHeapBufferIterator.readInt());
    byte[] arrayOfByte = new byte[this.nameLength];
    Utils.readFully(paramInputStream, arrayOfByte, 0, arrayOfByte.length);
    if (containsNulByte(arrayOfByte)) {
      this.verificationErrors = (0x10 | this.verificationErrors);
    }
    try
    {
      this.name = new String(arrayOfByte, 0, arrayOfByte.length, "UTF-8");
      if (k > 0) {
        Utils.skipFully(paramInputStream, k);
      }
      if (m > 0) {
        Utils.skipFully(paramInputStream, m);
      }
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException(localUnsupportedEncodingException);
    }
  }
  
  private static boolean containsNulByte(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfByte[j] == 0) {
        return true;
      }
    }
    return false;
  }
  
  public final int hashCode()
  {
    return this.name.hashCode();
  }
  
  public final String toString()
  {
    return this.name;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.instrumentedzip.ZipEntry
 * JD-Core Version:    0.7.0.1
 */