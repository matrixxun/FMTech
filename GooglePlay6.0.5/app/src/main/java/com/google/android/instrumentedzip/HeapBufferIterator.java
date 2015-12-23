package com.google.android.instrumentedzip;

public final class HeapBufferIterator
{
  private final byte[] buffer;
  private final int offset;
  int position;
  
  HeapBufferIterator(byte[] paramArrayOfByte)
  {
    this.buffer = paramArrayOfByte;
    this.offset = 0;
  }
  
  public final int readInt()
  {
    int i = this.offset + this.position;
    this.position = (4 + this.position);
    byte[] arrayOfByte1 = this.buffer;
    int j = i + 1;
    int k = (0xFF & arrayOfByte1[i]) << 0;
    byte[] arrayOfByte2 = this.buffer;
    int m = j + 1;
    int n = k | (0xFF & arrayOfByte2[j]) << 8;
    byte[] arrayOfByte3 = this.buffer;
    int i1 = m + 1;
    return n | (0xFF & arrayOfByte3[m]) << 16 | (0xFF & this.buffer[i1]) << 24;
  }
  
  public final short readShort()
  {
    int i = this.offset + this.position;
    this.position = (2 + this.position);
    byte[] arrayOfByte = this.buffer;
    int j = i + 1;
    return (short)((0xFF & arrayOfByte[i]) << 0 | (0xFF & this.buffer[j]) << 8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.instrumentedzip.HeapBufferIterator
 * JD-Core Version:    0.7.0.1
 */