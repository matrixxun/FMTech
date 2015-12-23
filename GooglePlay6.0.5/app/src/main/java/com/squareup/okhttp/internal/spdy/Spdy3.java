package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.Deflater;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Okio;

public final class Spdy3
  implements Variant
{
  static final byte[] DICTIONARY;
  
  static
  {
    try
    {
      DICTIONARY = "".getBytes(Util.UTF_8.name());
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new AssertionError();
    }
  }
  
  public final FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean)
  {
    return new Reader(paramBufferedSource, paramBoolean);
  }
  
  public final FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    return new Writer(paramBufferedSink, paramBoolean);
  }
  
  static final class Reader
    implements FrameReader
  {
    private final boolean client;
    private final NameValueBlockReader headerBlockReader;
    private final BufferedSource source;
    
    Reader(BufferedSource paramBufferedSource, boolean paramBoolean)
    {
      this.source = paramBufferedSource;
      this.headerBlockReader = new NameValueBlockReader(this.source);
      this.client = paramBoolean;
    }
    
    private static IOException ioException(String paramString, Object... paramVarArgs)
      throws IOException
    {
      throw new IOException(String.format(paramString, paramVarArgs));
    }
    
    private void readSettings(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      boolean bool = true;
      int i = this.source.readInt();
      if (paramInt2 != 4 + i * 8)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramInt2);
        arrayOfObject[bool] = Integer.valueOf(i);
        throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", arrayOfObject);
      }
      Settings localSettings = new Settings();
      for (int j = 0; j < i; j++)
      {
        int k = this.source.readInt();
        int m = this.source.readInt();
        int n = (0xFF000000 & k) >>> 24;
        localSettings.set(k & 0xFFFFFF, n, m);
      }
      if ((paramInt1 & 0x1) != 0) {}
      for (;;)
      {
        paramHandler.settings(bool, localSettings);
        return;
        bool = false;
      }
    }
    
    public final void close()
      throws IOException
    {
      this.headerBlockReader.source.close();
    }
    
    public final boolean nextFrame(FrameReader.Handler paramHandler)
      throws IOException
    {
      int i;
      int m;
      int n;
      int i3;
      for (;;)
      {
        try
        {
          i = this.source.readInt();
          int j = this.source.readInt();
          if ((0x80000000 & i) != 0)
          {
            k = 1;
            m = (0xFF000000 & j) >>> 24;
            n = j & 0xFFFFFF;
            if (k == 0) {
              break label787;
            }
            int i2 = (0x7FFF0000 & i) >>> 16;
            i3 = i & 0xFFFF;
            if (i2 == 3) {
              break;
            }
            throw new ProtocolException("version != 3: " + i2);
          }
        }
        catch (IOException localIOException)
        {
          return false;
        }
        int k = 0;
      }
      switch (i3)
      {
      case 5: 
      default: 
        this.source.skip(n);
        return true;
      case 1: 
        int i13 = this.source.readInt();
        this.source.readInt();
        int i14 = i13 & 0x7FFFFFFF;
        this.source.readShort();
        List localList2 = this.headerBlockReader.readNameValueBlock(n - 10);
        boolean bool6;
        if ((m & 0x1) != 0)
        {
          bool6 = true;
          if ((m & 0x2) == 0) {
            break label271;
          }
        }
        for (boolean bool7 = true;; bool7 = false)
        {
          paramHandler.headers$6fea9721(bool7, bool6, i14, localList2, HeadersMode.SPDY_SYN_STREAM);
          return true;
          bool6 = false;
          break;
        }
      case 2: 
        int i12 = 0x7FFFFFFF & this.source.readInt();
        List localList1 = this.headerBlockReader.readNameValueBlock(n - 4);
        if ((m & 0x1) != 0) {}
        for (boolean bool5 = true;; bool5 = false)
        {
          paramHandler.headers$6fea9721(false, bool5, i12, localList1, HeadersMode.SPDY_REPLY);
          return true;
        }
      case 3: 
        if (n != 8)
        {
          Object[] arrayOfObject7 = new Object[1];
          arrayOfObject7[0] = Integer.valueOf(n);
          throw ioException("TYPE_RST_STREAM length: %d != 8", arrayOfObject7);
        }
        int i10 = 0x7FFFFFFF & this.source.readInt();
        int i11 = this.source.readInt();
        ErrorCode localErrorCode = ErrorCode.fromSpdy3Rst(i11);
        if (localErrorCode == null)
        {
          Object[] arrayOfObject6 = new Object[1];
          arrayOfObject6[0] = Integer.valueOf(i11);
          throw ioException("TYPE_RST_STREAM unexpected error code: %d", arrayOfObject6);
        }
        paramHandler.rstStream(i10, localErrorCode);
        return true;
      case 4: 
        readSettings(paramHandler, m, n);
        return true;
      case 6: 
        if (n != 4)
        {
          Object[] arrayOfObject5 = new Object[1];
          arrayOfObject5[0] = Integer.valueOf(n);
          throw ioException("TYPE_PING length: %d != 4", arrayOfObject5);
        }
        int i9 = this.source.readInt();
        boolean bool2 = this.client;
        boolean bool3;
        if ((i9 & 0x1) == 1)
        {
          bool3 = true;
          if (bool2 != bool3) {
            break label537;
          }
        }
        for (boolean bool4 = true;; bool4 = false)
        {
          paramHandler.ping(bool4, i9, 0);
          return true;
          bool3 = false;
          break;
        }
      case 7: 
        if (n != 8)
        {
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = Integer.valueOf(n);
          throw ioException("TYPE_GOAWAY length: %d != 8", arrayOfObject4);
        }
        int i7 = 0x7FFFFFFF & this.source.readInt();
        int i8 = this.source.readInt();
        if (ErrorCode.fromSpdyGoAway(i8) == null)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(i8);
          throw ioException("TYPE_GOAWAY unexpected error code: %d", arrayOfObject3);
        }
        paramHandler.goAway$44df1550(i7, ByteString.EMPTY);
        return true;
      case 8: 
        label271:
        label537:
        paramHandler.headers$6fea9721(false, false, 0x7FFFFFFF & this.source.readInt(), this.headerBlockReader.readNameValueBlock(n - 4), HeadersMode.SPDY_HEADERS);
        return true;
      }
      if (n != 8)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(n);
        throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", arrayOfObject2);
      }
      int i4 = this.source.readInt();
      int i5 = this.source.readInt();
      int i6 = i4 & 0x7FFFFFFF;
      long l = i5 & 0x7FFFFFFF;
      if (l == 0L)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Long.valueOf(l);
        throw ioException("windowSizeIncrement was 0", arrayOfObject1);
      }
      paramHandler.windowUpdate(i6, l);
      return true;
      label787:
      int i1 = i & 0x7FFFFFFF;
      if ((m & 0x1) != 0) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        paramHandler.data(bool1, i1, this.source, n);
        return true;
      }
    }
    
    public final void readConnectionPreface() {}
  }
  
  static final class Writer
    implements FrameWriter
  {
    private final boolean client;
    private boolean closed;
    private final Buffer headerBlockBuffer;
    private final BufferedSink headerBlockOut;
    private final BufferedSink sink;
    
    Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
    {
      this.sink = paramBufferedSink;
      this.client = paramBoolean;
      Deflater localDeflater = new Deflater();
      localDeflater.setDictionary(Spdy3.DICTIONARY);
      this.headerBlockBuffer = new Buffer();
      this.headerBlockOut = Okio.buffer(new DeflaterSink(this.headerBlockBuffer, localDeflater));
    }
    
    private void writeNameValueBlockToBuffer(List<Header> paramList)
      throws IOException
    {
      if (this.headerBlockBuffer.size != 0L) {
        throw new IllegalStateException();
      }
      this.headerBlockOut.writeInt(paramList.size());
      int i = 0;
      int j = paramList.size();
      while (i < j)
      {
        ByteString localByteString1 = ((Header)paramList.get(i)).name;
        this.headerBlockOut.writeInt(localByteString1.data.length);
        this.headerBlockOut.write(localByteString1);
        ByteString localByteString2 = ((Header)paramList.get(i)).value;
        this.headerBlockOut.writeInt(localByteString2.data.length);
        this.headerBlockOut.write(localByteString2);
        i++;
      }
      this.headerBlockOut.flush();
    }
    
    public final void ackSettings(Settings paramSettings) {}
    
    public final void close()
      throws IOException
    {
      try
      {
        this.closed = true;
        Util.closeAll(this.sink, this.headerBlockOut);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public final void connectionPreface() {}
    
    public final void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      if (paramBoolean) {}
      for (int i = 1;; i = 0) {
        try
        {
          if (!this.closed) {
            break;
          }
          throw new IOException("closed");
        }
        finally {}
      }
      if (paramInt2 > 16777215L) {
        throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + paramInt2);
      }
      this.sink.writeInt(0x7FFFFFFF & paramInt1);
      this.sink.writeInt((i & 0xFF) << 24 | 0xFFFFFF & paramInt2);
      if (paramInt2 > 0) {
        this.sink.write(paramBuffer, paramInt2);
      }
    }
    
    public final void flush()
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      this.sink.flush();
    }
    
    public final void goAway(int paramInt, ErrorCode paramErrorCode, byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      if (paramErrorCode.spdyGoAwayCode == -1) {
        throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
      }
      this.sink.writeInt(-2147287033);
      this.sink.writeInt(8);
      this.sink.writeInt(paramInt);
      this.sink.writeInt(paramErrorCode.spdyGoAwayCode);
      this.sink.flush();
    }
    
    public final int maxDataLength()
    {
      return 16383;
    }
    
    public final void ping(boolean paramBoolean, int paramInt1, int paramInt2)
      throws IOException
    {
      boolean bool1 = true;
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      boolean bool2 = this.client;
      boolean bool3;
      if ((paramInt1 & 0x1) == bool1) {
        bool3 = bool1;
      }
      for (;;)
      {
        if (paramBoolean != bool1) {
          throw new IllegalArgumentException("payload != reply");
        }
        this.sink.writeInt(-2147287034);
        this.sink.writeInt(4);
        this.sink.writeInt(paramInt1);
        this.sink.flush();
        return;
        for (;;)
        {
          if (bool2 == bool3) {
            break label128;
          }
          break;
          bool3 = false;
        }
        label128:
        bool1 = false;
      }
    }
    
    public final void rstStream(int paramInt, ErrorCode paramErrorCode)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      if (paramErrorCode.spdyRstCode == -1) {
        throw new IllegalArgumentException();
      }
      this.sink.writeInt(-2147287037);
      this.sink.writeInt(8);
      this.sink.writeInt(0x7FFFFFFF & paramInt);
      this.sink.writeInt(paramErrorCode.spdyRstCode);
      this.sink.flush();
    }
    
    public final void settings(Settings paramSettings)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      int i = Integer.bitCount(paramSettings.set);
      int j = 4 + i * 8;
      this.sink.writeInt(-2147287036);
      this.sink.writeInt(0x0 | j & 0xFFFFFF);
      this.sink.writeInt(i);
      for (int k = 0;; k++) {
        if (k <= 10)
        {
          if (paramSettings.isSet(k))
          {
            int m = paramSettings.flags(k);
            this.sink.writeInt((m & 0xFF) << 24 | k & 0xFFFFFF);
            this.sink.writeInt(paramSettings.values[k]);
          }
        }
        else
        {
          this.sink.flush();
          return;
        }
      }
    }
    
    public final void synStream(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, List<Header> paramList)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      writeNameValueBlockToBuffer(paramList);
      int i = (int)(10L + this.headerBlockBuffer.size);
      int j;
      if (paramBoolean1) {
        j = 1;
      }
      for (;;)
      {
        int m = j | k;
        this.sink.writeInt(-2147287039);
        this.sink.writeInt((m & 0xFF) << 24 | 0xFFFFFF & i);
        this.sink.writeInt(0x7FFFFFFF & paramInt1);
        this.sink.writeInt(0);
        this.sink.writeShort(0);
        this.sink.writeAll(this.headerBlockBuffer);
        this.sink.flush();
        return;
        j = 0;
        int k = 0;
        if (paramBoolean2) {
          k = 2;
        }
      }
    }
    
    public final void windowUpdate(int paramInt, long paramLong)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      if ((paramLong == 0L) || (paramLong > 2147483647L)) {
        throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + paramLong);
      }
      this.sink.writeInt(-2147287031);
      this.sink.writeInt(8);
      this.sink.writeInt(paramInt);
      this.sink.writeInt((int)paramLong);
      this.sink.flush();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Spdy3
 * JD-Core Version:    0.7.0.1
 */