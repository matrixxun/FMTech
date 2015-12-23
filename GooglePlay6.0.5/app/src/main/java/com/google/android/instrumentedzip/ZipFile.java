package com.google.android.instrumentedzip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

public final class ZipFile
  implements Closeable
{
  public final List<ZipEntry> entryList = new ArrayList();
  private final LinkedHashMap<String, ZipEntry> entryMap = new LinkedHashMap();
  private RandomAccessFile raf;
  
  public ZipFile(File paramFile)
    throws ZipException, IOException
  {
    this.raf = new RandomAccessFile(paramFile.getPath(), "r");
    readCentralDir();
  }
  
  private void readCentralDir()
    throws IOException
  {
    long l1 = this.raf.length() - 22L;
    if (l1 < 0L) {
      throw new ZipException("File too short to be a zip file: " + this.raf.length());
    }
    this.raf.seek(0L);
    if (Integer.reverseBytes(this.raf.readInt()) != 67324752L) {
      throw new ZipException("Not a zip archive");
    }
    long l2 = l1 - 65536L;
    if (l2 < 0L) {
      l2 = 0L;
    }
    do
    {
      this.raf.seek(l1);
      if (Integer.reverseBytes(this.raf.readInt()) == 101010256L) {
        break;
      }
      l1 -= 1L;
    } while (l1 >= l2);
    throw new ZipException("End Of Central Directory signature not found");
    byte[] arrayOfByte1 = new byte[18];
    this.raf.readFully(arrayOfByte1);
    HeapBufferIterator localHeapBufferIterator = new HeapBufferIterator(arrayOfByte1);
    int i = 0xFFFF & localHeapBufferIterator.readShort();
    int j = 0xFFFF & localHeapBufferIterator.readShort();
    int k = 0xFFFF & localHeapBufferIterator.readShort();
    int m = 0xFFFF & localHeapBufferIterator.readShort();
    localHeapBufferIterator.position = (4 + localHeapBufferIterator.position);
    long l3 = 0xFFFFFFFF & localHeapBufferIterator.readInt();
    if ((k != m) || (i != 0) || (j != 0)) {
      throw new ZipException("Spanned archives not supported");
    }
    RAFStream localRAFStream = new RAFStream(this.raf, l3);
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(localRAFStream, 4096);
    byte[] arrayOfByte2 = new byte[46];
    for (int n = 0; n < k; n++)
    {
      ZipEntry localZipEntry1 = new ZipEntry(arrayOfByte2, localBufferedInputStream);
      if (localZipEntry1.localHeaderRelOffset >= l3) {
        throw new ZipException("Local file header offset is after central directory");
      }
      String str = localZipEntry1.name;
      ZipEntry localZipEntry2 = (ZipEntry)this.entryMap.put(str, localZipEntry1);
      if (localZipEntry2 != null)
      {
        localZipEntry2.verificationErrors = (0x20 | localZipEntry2.verificationErrors);
        localZipEntry1.verificationErrors = (0x20 | localZipEntry1.verificationErrors);
      }
      this.entryList.add(localZipEntry1);
    }
  }
  
  static void throwZipException(String paramString, int paramInt)
    throws ZipException
  {
    StringBuilder localStringBuilder = new StringBuilder().append(paramString).append(" signature not found; was ");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    throw new ZipException(String.format("%08x", arrayOfObject));
  }
  
  public final void close()
    throws IOException
  {
    RandomAccessFile localRandomAccessFile = this.raf;
    if (localRandomAccessFile != null) {
      try
      {
        this.raf = null;
        localRandomAccessFile.close();
        return;
      }
      finally {}
    }
  }
  
  public final InputStream verifyAndGetInputStream(ZipEntry paramZipEntry, boolean paramBoolean)
    throws IOException
  {
    String str = paramZipEntry.name;
    if (this.raf == null) {
      throw new IllegalStateException("Zip file closed");
    }
    if (str == null) {
      throw new NullPointerException("entryName == null");
    }
    ZipEntry localZipEntry1 = (ZipEntry)this.entryMap.get(str);
    if (localZipEntry1 == null) {}
    for (ZipEntry localZipEntry2 = (ZipEntry)this.entryMap.get(str + "/");; localZipEntry2 = localZipEntry1)
    {
      if (localZipEntry2 == null) {
        return null;
      }
      RAFStream localRAFStream;
      DataInputStream localDataInputStream;
      synchronized (this.raf)
      {
        localRAFStream = new RAFStream(???, localZipEntry2.localHeaderRelOffset);
        localDataInputStream = new DataInputStream(localRAFStream);
        int i = Integer.reverseBytes(localDataInputStream.readInt());
        if (i != 67324752L) {
          throwZipException("Local File Header", i);
        }
        localDataInputStream.skipBytes(2);
        int j = 0xFFFF & Short.reverseBytes(localDataInputStream.readShort());
        if ((j & 0x1) != 0) {
          throw new ZipException("Invalid General Purpose Bit Flag: " + j);
        }
      }
      localDataInputStream.skipBytes(18);
      int k = 0xFFFF & Short.reverseBytes(localDataInputStream.readShort());
      int m = 0xFFFF & Short.reverseBytes(localDataInputStream.readShort());
      localDataInputStream.close();
      if (k != localZipEntry2.nameLength) {
        localZipEntry2.verificationErrors = (0x8 | localZipEntry2.verificationErrors);
      }
      if (m >= 32768) {
        localZipEntry2.verificationErrors = (0x4 | localZipEntry2.verificationErrors);
      }
      if (paramBoolean) {
        return null;
      }
      localRAFStream.skip(k + m);
      if (localZipEntry2.compressionMethod == 0)
      {
        RAFStream.access$102(localRAFStream, localRAFStream.offset + localZipEntry2.size);
        return localRAFStream;
      }
      RAFStream.access$102(localRAFStream, localRAFStream.offset + localZipEntry2.compressedSize);
      int n = Math.max(1024, (int)Math.min(localZipEntry2.size, 4096L));
      ZipInflaterInputStream localZipInflaterInputStream = new ZipInflaterInputStream(localRAFStream, new Inflater(true), n, localZipEntry2);
      return localZipInflaterInputStream;
    }
  }
  
  static final class RAFStream
    extends InputStream
  {
    private long endOffset;
    private long offset;
    private final RandomAccessFile sharedRaf;
    
    public RAFStream(RandomAccessFile paramRandomAccessFile, long paramLong)
      throws IOException
    {
      this.sharedRaf = paramRandomAccessFile;
      this.offset = paramLong;
      this.endOffset = paramRandomAccessFile.length();
    }
    
    public final int available()
      throws IOException
    {
      if (this.offset < this.endOffset) {
        return 1;
      }
      return 0;
    }
    
    public final int read()
      throws IOException
    {
      synchronized (this.sharedRaf)
      {
        this.sharedRaf.seek(this.offset);
        int i = this.sharedRaf.read();
        if (i != -1) {
          this.offset = (1L + this.offset);
        }
        return i;
      }
    }
    
    public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      synchronized (this.sharedRaf)
      {
        long l = this.endOffset - this.offset;
        if (paramInt2 > l) {
          paramInt2 = (int)l;
        }
        this.sharedRaf.seek(this.offset);
        int i = this.sharedRaf.read(paramArrayOfByte, paramInt1, paramInt2);
        if (i > 0)
        {
          this.offset += i;
          return i;
        }
        return -1;
      }
    }
    
    public final long skip(long paramLong)
      throws IOException
    {
      if (paramLong > this.endOffset - this.offset) {
        paramLong = this.endOffset - this.offset;
      }
      this.offset = (paramLong + this.offset);
      return paramLong;
    }
  }
  
  static final class ZipInflaterInputStream
    extends InflaterInputStream
  {
    private long bytesRead = 0L;
    private final ZipEntry entry;
    
    public ZipInflaterInputStream(InputStream paramInputStream, Inflater paramInflater, int paramInt, ZipEntry paramZipEntry)
    {
      super(paramInflater, paramInt);
      this.entry = paramZipEntry;
    }
    
    public final int available()
      throws IOException
    {
      if (super.available() == 0) {
        return 0;
      }
      return (int)(this.entry.size - this.bytesRead);
    }
    
    public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i;
      try
      {
        i = super.read(paramArrayOfByte, paramInt1, paramInt2);
        if (i == -1)
        {
          if (this.entry.size == this.bytesRead) {
            break label128;
          }
          throw new IOException("Size mismatch on inflated file: " + this.bytesRead + " vs " + this.entry.size);
        }
      }
      catch (IOException localIOException)
      {
        throw new IOException("Error reading data for " + this.entry.name + " near offset " + this.bytesRead);
      }
      this.bytesRead += i;
      label128:
      return i;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.instrumentedzip.ZipFile
 * JD-Core Version:    0.7.0.1
 */