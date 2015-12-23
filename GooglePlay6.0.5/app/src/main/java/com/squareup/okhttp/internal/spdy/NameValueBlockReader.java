package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.InflaterSource;
import okio.Okio;
import okio.Source;

final class NameValueBlockReader
{
  int compressedLimit;
  private final InflaterSource inflaterSource = new InflaterSource(new ForwardingSource(paramBufferedSource)new Inflater
  {
    public final long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
      throws IOException
    {
      if (NameValueBlockReader.this.compressedLimit == 0) {
        return -1L;
      }
      long l = super.read(paramAnonymousBuffer, Math.min(paramAnonymousLong, NameValueBlockReader.this.compressedLimit));
      if (l == -1L) {
        return -1L;
      }
      NameValueBlockReader localNameValueBlockReader = NameValueBlockReader.this;
      localNameValueBlockReader.compressedLimit = ((int)(localNameValueBlockReader.compressedLimit - l));
      return l;
    }
  }, new Inflater()
  {
    public final int inflate(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      throws DataFormatException
    {
      int i = super.inflate(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      if ((i == 0) && (needsDictionary()))
      {
        setDictionary(Spdy3.DICTIONARY);
        i = super.inflate(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      return i;
    }
  });
  final BufferedSource source = Okio.buffer(this.inflaterSource);
  
  public NameValueBlockReader(BufferedSource paramBufferedSource) {}
  
  private ByteString readByteString()
    throws IOException
  {
    int i = this.source.readInt();
    return this.source.readByteString(i);
  }
  
  public final List<Header> readNameValueBlock(int paramInt)
    throws IOException
  {
    this.compressedLimit = (paramInt + this.compressedLimit);
    int i = this.source.readInt();
    if (i < 0) {
      throw new IOException("numberOfPairs < 0: " + i);
    }
    if (i > 1024) {
      throw new IOException("numberOfPairs > 1024: " + i);
    }
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
    {
      ByteString localByteString1 = readByteString().toAsciiLowercase();
      ByteString localByteString2 = readByteString();
      if (localByteString1.data.length == 0) {
        throw new IOException("name.size == 0");
      }
      localArrayList.add(new Header(localByteString1, localByteString2));
    }
    if (this.compressedLimit > 0)
    {
      this.inflaterSource.refill();
      if (this.compressedLimit != 0) {
        throw new IOException("compressedLimit > 0: " + this.compressedLimit);
      }
    }
    return localArrayList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.NameValueBlockReader
 * JD-Core Version:    0.7.0.1
 */