package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Http20Draft16
  implements Variant
{
  private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
  private static final Logger logger = Logger.getLogger(FrameLogger.class.getName());
  
  private static IOException ioException(String paramString, Object... paramVarArgs)
    throws IOException
  {
    throw new IOException(String.format(paramString, paramVarArgs));
  }
  
  public final FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean)
  {
    return new Reader(paramBufferedSource, paramBoolean);
  }
  
  public final FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    return new Writer(paramBufferedSink, paramBoolean);
  }
  
  static final class ContinuationSource
    implements Source
  {
    byte flags;
    int left;
    int length;
    short padding;
    private final BufferedSource source;
    int streamId;
    
    public ContinuationSource(BufferedSource paramBufferedSource)
    {
      this.source = paramBufferedSource;
    }
    
    public final void close()
      throws IOException
    {}
    
    public final long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      while (this.left == 0)
      {
        this.source.skip(this.padding);
        this.padding = 0;
        if ((0x4 & this.flags) != 0) {
          return -1L;
        }
        int i = this.streamId;
        int j = Http20Draft16.access$300(this.source);
        this.left = j;
        this.length = j;
        byte b = (byte)(0xFF & this.source.readByte());
        this.flags = ((byte)(0xFF & this.source.readByte()));
        if (Http20Draft16.logger.isLoggable(Level.FINE)) {
          Http20Draft16.logger.fine(Http20Draft16.FrameLogger.formatHeader(true, this.streamId, this.length, b, this.flags));
        }
        this.streamId = (0x7FFFFFFF & this.source.readInt());
        if (b != 9)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Byte.valueOf(b);
          throw Http20Draft16.ioException("%s != TYPE_CONTINUATION", arrayOfObject);
        }
        if (this.streamId != i) {
          throw Http20Draft16.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
        }
      }
      long l = this.source.read(paramBuffer, Math.min(paramLong, this.left));
      if (l == -1L) {
        return -1L;
      }
      this.left = ((int)(this.left - l));
      return l;
    }
    
    public final Timeout timeout()
    {
      return this.source.timeout();
    }
  }
  
  static final class FrameLogger
  {
    private static final String[] BINARY;
    private static final String[] FLAGS;
    private static final String[] TYPES = { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };
    
    static
    {
      FLAGS = new String[64];
      BINARY = new String[256];
      for (int i = 0; i < BINARY.length; i++)
      {
        String[] arrayOfString = BINARY;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.toBinaryString(i);
        arrayOfString[i] = String.format("%8s", arrayOfObject).replace(' ', '0');
      }
      FLAGS[0] = "";
      FLAGS[1] = "END_STREAM";
      int[] arrayOfInt1 = { 1 };
      FLAGS[8] = "PADDED";
      for (int j = 0; j <= 0; j++)
      {
        int i3 = arrayOfInt1[j];
        FLAGS[(i3 | 0x8)] = (FLAGS[i3] + "|PADDED");
      }
      FLAGS[4] = "END_HEADERS";
      FLAGS[32] = "PRIORITY";
      FLAGS[36] = "END_HEADERS|PRIORITY";
      int[] arrayOfInt2 = { 4, 32, 36 };
      for (int k = 0; k < 3; k++)
      {
        int n = arrayOfInt2[k];
        for (int i1 = 0; i1 <= 0; i1++)
        {
          int i2 = arrayOfInt1[i1];
          FLAGS[(i2 | n)] = (FLAGS[i2] + '|' + FLAGS[n]);
          FLAGS[(0x8 | i2 | n)] = (FLAGS[i2] + '|' + FLAGS[n] + "|PADDED");
        }
      }
      for (int m = 0; m < FLAGS.length; m++) {
        if (FLAGS[m] == null) {
          FLAGS[m] = BINARY[m];
        }
      }
    }
    
    static String formatHeader(boolean paramBoolean, int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
    {
      String str1;
      String str2;
      label24:
      Object[] arrayOfObject2;
      if (paramByte1 < TYPES.length)
      {
        str1 = TYPES[paramByte1];
        if (paramByte2 != 0) {
          break label106;
        }
        str2 = "";
        arrayOfObject2 = new Object[5];
        if (!paramBoolean) {
          break label263;
        }
      }
      label263:
      for (String str3 = "<<";; str3 = ">>")
      {
        arrayOfObject2[0] = str3;
        arrayOfObject2[1] = Integer.valueOf(paramInt1);
        arrayOfObject2[2] = Integer.valueOf(paramInt2);
        arrayOfObject2[3] = str1;
        arrayOfObject2[4] = str2;
        return String.format("%s 0x%08x %5d %-13s %s", arrayOfObject2);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Byte.valueOf(paramByte1);
        str1 = String.format("0x%02x", arrayOfObject1);
        break;
        switch (paramByte1)
        {
        case 5: 
        default: 
          label106:
          if (paramByte2 >= FLAGS.length) {
            break;
          }
        }
        for (str2 = FLAGS[paramByte2];; str2 = BINARY[paramByte2])
        {
          if ((paramByte1 != 5) || ((paramByte2 & 0x4) == 0)) {
            break label237;
          }
          str2 = str2.replace("HEADERS", "PUSH_PROMISE");
          break;
          if (paramByte2 == 1)
          {
            str2 = "ACK";
            break;
          }
          str2 = BINARY[paramByte2];
          break;
          str2 = BINARY[paramByte2];
          break;
        }
        label237:
        if ((paramByte1 != 0) || ((paramByte2 & 0x20) == 0)) {
          break label24;
        }
        str2 = str2.replace("PRIORITY", "COMPRESSED");
        break label24;
      }
    }
  }
  
  static final class Reader
    implements FrameReader
  {
    private final boolean client;
    private final Http20Draft16.ContinuationSource continuation;
    final HpackDraft10.Reader hpackReader;
    private final BufferedSource source;
    
    Reader(BufferedSource paramBufferedSource, boolean paramBoolean)
    {
      this.source = paramBufferedSource;
      this.client = paramBoolean;
      this.continuation = new Http20Draft16.ContinuationSource(this.source);
      this.hpackReader = new HpackDraft10.Reader(4096, this.continuation);
    }
    
    private List<Header> readHeaderBlock(int paramInt1, short paramShort, byte paramByte, int paramInt2)
      throws IOException
    {
      Http20Draft16.ContinuationSource localContinuationSource = this.continuation;
      this.continuation.left = paramInt1;
      localContinuationSource.length = paramInt1;
      this.continuation.padding = paramShort;
      this.continuation.flags = paramByte;
      this.continuation.streamId = paramInt2;
      HpackDraft10.Reader localReader1 = this.hpackReader;
      while (!localReader1.source.exhausted())
      {
        int i = 0xFF & localReader1.source.readByte();
        if (i == 128) {
          throw new IOException("index == 0");
        }
        if ((i & 0x80) == 128)
        {
          int j = -1 + localReader1.readInt(i, 127);
          if (HpackDraft10.Reader.isStaticHeader(j))
          {
            Header localHeader = HpackDraft10.access$000()[j];
            localReader1.headerList.add(localHeader);
          }
          else
          {
            int k = localReader1.dynamicTableIndex(j - HpackDraft10.access$000().length);
            if ((k < 0) || (k > -1 + localReader1.dynamicTable.length)) {
              throw new IOException("Header index too large " + (j + 1));
            }
            localReader1.headerList.add(localReader1.dynamicTable[k]);
          }
        }
        else if (i == 64)
        {
          localReader1.insertIntoDynamicTable$2acf0583(new Header(HpackDraft10.access$100(localReader1.readByteString()), localReader1.readByteString()));
        }
        else if ((i & 0x40) == 64)
        {
          localReader1.insertIntoDynamicTable$2acf0583(new Header(localReader1.getName(-1 + localReader1.readInt(i, 63)), localReader1.readByteString()));
        }
        else if ((i & 0x20) == 32)
        {
          localReader1.maxDynamicTableByteCount = localReader1.readInt(i, 31);
          if ((localReader1.maxDynamicTableByteCount < 0) || (localReader1.maxDynamicTableByteCount > localReader1.headerTableSizeSetting)) {
            throw new IOException("Invalid dynamic table size update " + localReader1.maxDynamicTableByteCount);
          }
          localReader1.adjustDynamicTableByteCount();
        }
        else if ((i == 16) || (i == 0))
        {
          ByteString localByteString1 = HpackDraft10.access$100(localReader1.readByteString());
          ByteString localByteString2 = localReader1.readByteString();
          localReader1.headerList.add(new Header(localByteString1, localByteString2));
        }
        else
        {
          ByteString localByteString3 = localReader1.getName(-1 + localReader1.readInt(i, 15));
          ByteString localByteString4 = localReader1.readByteString();
          localReader1.headerList.add(new Header(localByteString3, localByteString4));
        }
      }
      HpackDraft10.Reader localReader2 = this.hpackReader;
      ArrayList localArrayList = new ArrayList(localReader2.headerList);
      localReader2.headerList.clear();
      return localArrayList;
    }
    
    private void readPriority$36b9b000()
      throws IOException
    {
      this.source.readInt();
      this.source.readByte();
    }
    
    public final void close()
      throws IOException
    {
      this.source.close();
    }
    
    public final boolean nextFrame(FrameReader.Handler paramHandler)
      throws IOException
    {
      int i = 1;
      int j;
      byte b2;
      int k;
      label248:
      label254:
      Settings localSettings;
      label431:
      label630:
      do
      {
        do
        {
          try
          {
            this.source.require(9L);
            j = Http20Draft16.access$300(this.source);
            if ((j < 0) || (j > 16384))
            {
              Object[] arrayOfObject1 = new Object[i];
              arrayOfObject1[0] = Integer.valueOf(j);
              throw Http20Draft16.ioException("FRAME_SIZE_ERROR: %s", arrayOfObject1);
            }
          }
          catch (IOException localIOException)
          {
            i = 0;
            return i;
          }
          byte b1 = (byte)(0xFF & this.source.readByte());
          b2 = (byte)(0xFF & this.source.readByte());
          k = 0x7FFFFFFF & this.source.readInt();
          if (Http20Draft16.logger.isLoggable(Level.FINE)) {
            Http20Draft16.logger.fine(Http20Draft16.FrameLogger.formatHeader(i, k, j, b1, b2));
          }
          switch (b1)
          {
          default: 
            this.source.skip(j);
            return i;
          case 0: 
            boolean bool3;
            if ((b2 & 0x1) != 0)
            {
              bool3 = i;
              if ((b2 & 0x20) == 0) {
                break label248;
              }
            }
            for (boolean bool4 = i;; bool4 = false)
            {
              if (!bool4) {
                break label254;
              }
              throw Http20Draft16.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
              bool3 = false;
              break;
            }
            int i10 = b2 & 0x8;
            short s4 = 0;
            if (i10 != 0) {
              s4 = (short)(0xFF & this.source.readByte());
            }
            int i11 = Http20Draft16.access$400(j, b2, s4);
            paramHandler.data(bool3, k, this.source, i11);
            this.source.skip(s4);
            return i;
          case 1: 
            if (k == 0) {
              throw Http20Draft16.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean bool2;
            if ((b2 & 0x1) != 0)
            {
              bool2 = i;
              if ((b2 & 0x8) == 0) {
                break label431;
              }
            }
            for (short s3 = (short)(0xFF & this.source.readByte());; s3 = 0)
            {
              if ((b2 & 0x20) != 0)
              {
                readPriority$36b9b000();
                j -= 5;
              }
              paramHandler.headers$6fea9721(false, bool2, k, readHeaderBlock(Http20Draft16.access$400(j, b2, s3), s3, b2, k), HeadersMode.HTTP_20_HEADERS);
              return i;
              bool2 = false;
              break;
            }
          case 2: 
            if (j != 5)
            {
              Object[] arrayOfObject12 = new Object[i];
              arrayOfObject12[0] = Integer.valueOf(j);
              throw Http20Draft16.ioException("TYPE_PRIORITY length: %d != 5", arrayOfObject12);
            }
            if (k == 0) {
              throw Http20Draft16.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
            readPriority$36b9b000();
            return i;
          case 3: 
            if (j != 4)
            {
              Object[] arrayOfObject11 = new Object[i];
              arrayOfObject11[0] = Integer.valueOf(j);
              throw Http20Draft16.ioException("TYPE_RST_STREAM length: %d != 4", arrayOfObject11);
            }
            if (k == 0) {
              throw Http20Draft16.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
            int i9 = this.source.readInt();
            ErrorCode localErrorCode = ErrorCode.fromHttp2(i9);
            if (localErrorCode == null)
            {
              Object[] arrayOfObject10 = new Object[i];
              arrayOfObject10[0] = Integer.valueOf(i9);
              throw Http20Draft16.ioException("TYPE_RST_STREAM unexpected error code: %d", arrayOfObject10);
            }
            paramHandler.rstStream(k, localErrorCode);
            return i;
          case 4: 
            if (k != 0) {
              throw Http20Draft16.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            }
            if ((b2 & 0x1) == 0) {
              break label630;
            }
          }
        } while (j == 0);
        throw Http20Draft16.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
        if (j % 6 != 0)
        {
          Object[] arrayOfObject9 = new Object[i];
          arrayOfObject9[0] = Integer.valueOf(j);
          throw Http20Draft16.ioException("TYPE_SETTINGS length %% 6 != 0: %s", arrayOfObject9);
        }
        localSettings = new Settings();
        int i6 = 0;
        if (i6 < j)
        {
          short s2 = this.source.readShort();
          int i8 = this.source.readInt();
          switch (s2)
          {
          default: 
            Object[] arrayOfObject8 = new Object[i];
            arrayOfObject8[0] = Short.valueOf(s2);
            throw Http20Draft16.ioException("PROTOCOL_ERROR invalid settings id: %s", arrayOfObject8);
          case 2: 
            if ((i8 != 0) && (i8 != i)) {
              throw Http20Draft16.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
            }
            break;
          case 3: 
            s2 = 4;
          }
          do
          {
            do
            {
              localSettings.set(s2, 0, i8);
              i6 += 6;
              break;
              s2 = 7;
            } while (i8 >= 0);
            throw Http20Draft16.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
          } while ((i8 >= 16384) && (i8 <= 16777215));
          Object[] arrayOfObject7 = new Object[i];
          arrayOfObject7[0] = Integer.valueOf(i8);
          throw Http20Draft16.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", arrayOfObject7);
        }
        paramHandler.settings(false, localSettings);
      } while (localSettings.getHeaderTableSize() < 0);
      HpackDraft10.Reader localReader = this.hpackReader;
      int i7 = localSettings.getHeaderTableSize();
      localReader.headerTableSizeSetting = i7;
      localReader.maxDynamicTableByteCount = i7;
      localReader.adjustDynamicTableByteCount();
      return i;
      if (k == 0) {
        throw Http20Draft16.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
      }
      int i5 = b2 & 0x8;
      short s1 = 0;
      if (i5 != 0) {
        s1 = (short)(0xFF & this.source.readByte());
      }
      paramHandler.pushPromise$16014a7a(0x7FFFFFFF & this.source.readInt(), readHeaderBlock(Http20Draft16.access$400(j - 4, b2, s1), s1, b2, k));
      return i;
      if (j != 8)
      {
        Object[] arrayOfObject6 = new Object[i];
        arrayOfObject6[0] = Integer.valueOf(j);
        throw Http20Draft16.ioException("TYPE_PING length != 8: %s", arrayOfObject6);
      }
      if (k != 0) {
        throw Http20Draft16.ioException("TYPE_PING streamId != 0", new Object[0]);
      }
      int i2 = this.source.readInt();
      int i3 = this.source.readInt();
      int i4 = b2 & 0x1;
      boolean bool1 = false;
      if (i4 != 0) {
        bool1 = i;
      }
      paramHandler.ping(bool1, i2, i3);
      return i;
      if (j < 8)
      {
        Object[] arrayOfObject5 = new Object[i];
        arrayOfObject5[0] = Integer.valueOf(j);
        throw Http20Draft16.ioException("TYPE_GOAWAY length < 8: %s", arrayOfObject5);
      }
      if (k != 0) {
        throw Http20Draft16.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
      }
      int m = this.source.readInt();
      int n = this.source.readInt();
      int i1 = j - 8;
      if (ErrorCode.fromHttp2(n) == null)
      {
        Object[] arrayOfObject4 = new Object[i];
        arrayOfObject4[0] = Integer.valueOf(n);
        throw Http20Draft16.ioException("TYPE_GOAWAY unexpected error code: %d", arrayOfObject4);
      }
      ByteString localByteString = ByteString.EMPTY;
      if (i1 > 0) {
        localByteString = this.source.readByteString(i1);
      }
      paramHandler.goAway$44df1550(m, localByteString);
      return i;
      if (j != 4)
      {
        Object[] arrayOfObject3 = new Object[i];
        arrayOfObject3[0] = Integer.valueOf(j);
        throw Http20Draft16.ioException("TYPE_WINDOW_UPDATE length !=4: %s", arrayOfObject3);
      }
      long l = 0x7FFFFFFF & this.source.readInt();
      if (l == 0L)
      {
        Object[] arrayOfObject2 = new Object[i];
        arrayOfObject2[0] = Long.valueOf(l);
        throw Http20Draft16.ioException("windowSizeIncrement was 0", arrayOfObject2);
      }
      paramHandler.windowUpdate(k, l);
      return i;
    }
    
    public final void readConnectionPreface()
      throws IOException
    {
      if (this.client) {}
      ByteString localByteString;
      do
      {
        return;
        localByteString = this.source.readByteString(Http20Draft16.CONNECTION_PREFACE.data.length);
        if (Http20Draft16.logger.isLoggable(Level.FINE))
        {
          Logger localLogger = Http20Draft16.logger;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localByteString.hex();
          localLogger.fine(String.format("<< CONNECTION %s", arrayOfObject2));
        }
      } while (Http20Draft16.CONNECTION_PREFACE.equals(localByteString));
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localByteString.utf8();
      throw Http20Draft16.ioException("Expected a connection header but was %s", arrayOfObject1);
    }
  }
  
  static final class Writer
    implements FrameWriter
  {
    private final boolean client;
    private boolean closed;
    private final Buffer hpackBuffer;
    private final HpackDraft10.Writer hpackWriter;
    private int maxFrameSize;
    private final BufferedSink sink;
    
    Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
    {
      this.sink = paramBufferedSink;
      this.client = paramBoolean;
      this.hpackBuffer = new Buffer();
      this.hpackWriter = new HpackDraft10.Writer(this.hpackBuffer);
      this.maxFrameSize = 16384;
    }
    
    private void frameHeader(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
      throws IOException
    {
      if (Http20Draft16.logger.isLoggable(Level.FINE)) {
        Http20Draft16.logger.fine(Http20Draft16.FrameLogger.formatHeader(false, paramInt1, paramInt2, paramByte1, paramByte2));
      }
      if (paramInt2 > this.maxFrameSize)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(this.maxFrameSize);
        arrayOfObject2[1] = Integer.valueOf(paramInt2);
        throw Http20Draft16.access$500("FRAME_SIZE_ERROR length > %d: %d", arrayOfObject2);
      }
      if ((0x80000000 & paramInt1) != 0)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(paramInt1);
        throw Http20Draft16.access$500("reserved bit set: %s", arrayOfObject1);
      }
      Http20Draft16.access$600(this.sink, paramInt2);
      this.sink.writeByte(paramByte1 & 0xFF);
      this.sink.writeByte(paramByte2 & 0xFF);
      this.sink.writeInt(0x7FFFFFFF & paramInt1);
    }
    
    private void writeContinuationFrames(int paramInt, long paramLong)
      throws IOException
    {
      if (paramLong > 0L)
      {
        int i = (int)Math.min(this.maxFrameSize, paramLong);
        paramLong -= i;
        if (paramLong == 0L) {}
        for (byte b = 4;; b = 0)
        {
          frameHeader(paramInt, i, (byte)9, b);
          this.sink.write(this.hpackBuffer, i);
          break;
        }
      }
    }
    
    public final void ackSettings(Settings paramSettings)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      int i = this.maxFrameSize;
      if ((0x20 & paramSettings.set) != 0) {
        i = paramSettings.values[5];
      }
      this.maxFrameSize = i;
      frameHeader(0, 0, (byte)4, (byte)1);
      this.sink.flush();
    }
    
    public final void close()
      throws IOException
    {
      try
      {
        this.closed = true;
        this.sink.close();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public final void connectionPreface()
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      boolean bool = this.client;
      if (!bool) {}
      for (;;)
      {
        return;
        if (Http20Draft16.logger.isLoggable(Level.FINE))
        {
          Logger localLogger = Http20Draft16.logger;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Http20Draft16.CONNECTION_PREFACE.hex();
          localLogger.fine(String.format(">> CONNECTION %s", arrayOfObject));
        }
        this.sink.write(Http20Draft16.CONNECTION_PREFACE.toByteArray());
        this.sink.flush();
      }
    }
    
    public final void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      byte b = 0;
      if (paramBoolean) {
        b = 1;
      }
      frameHeader(paramInt1, paramInt2, (byte)0, b);
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
      if (paramErrorCode.httpCode == -1) {
        throw Http20Draft16.access$500("errorCode.httpCode == -1", new Object[0]);
      }
      frameHeader(0, 8 + paramArrayOfByte.length, (byte)7, (byte)0);
      this.sink.writeInt(paramInt);
      this.sink.writeInt(paramErrorCode.httpCode);
      if (paramArrayOfByte.length > 0) {
        this.sink.write(paramArrayOfByte);
      }
      this.sink.flush();
    }
    
    public final int maxDataLength()
    {
      return this.maxFrameSize;
    }
    
    public final void ping(boolean paramBoolean, int paramInt1, int paramInt2)
      throws IOException
    {
      try
      {
        if (this.closed) {
          throw new IOException("closed");
        }
      }
      finally {}
      byte b = 0;
      if (paramBoolean) {
        b = 1;
      }
      frameHeader(0, 8, (byte)6, b);
      this.sink.writeInt(paramInt1);
      this.sink.writeInt(paramInt2);
      this.sink.flush();
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
      frameHeader(paramInt, 4, (byte)3, (byte)0);
      this.sink.writeInt(paramErrorCode.httpCode);
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
      frameHeader(0, 6 * Integer.bitCount(paramSettings.set), (byte)4, (byte)0);
      int i = 0;
      int j;
      if (i < 10)
      {
        if (!paramSettings.isSet(i)) {
          break label111;
        }
        j = i;
        if (i != 4) {
          break label117;
        }
        j = 3;
      }
      for (;;)
      {
        this.sink.writeShort(j);
        this.sink.writeInt(paramSettings.values[i]);
        break label111;
        this.sink.flush();
        return;
        label111:
        i++;
        break;
        label117:
        if (j == 7) {
          j = 4;
        }
      }
    }
    
    public final void synStream(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, List<Header> paramList)
      throws IOException
    {
      if (paramBoolean2) {
        try
        {
          throw new UnsupportedOperationException();
        }
        finally {}
      }
      if (this.closed) {
        throw new IOException("closed");
      }
      if (this.closed) {
        throw new IOException("closed");
      }
      if (this.hpackBuffer.size != 0L) {
        throw new IllegalStateException();
      }
      this.hpackWriter.writeHeaders(paramList);
      long l = this.hpackBuffer.size;
      int i = (int)Math.min(this.maxFrameSize, l);
      byte b;
      if (l == i) {
        b = 4;
      }
      for (;;)
      {
        frameHeader(paramInt1, i, (byte)1, b);
        this.sink.write(this.hpackBuffer, i);
        if (l > i) {
          writeContinuationFrames(paramInt1, l - i);
        }
        return;
        b = 0;
        if (paramBoolean1) {
          b = (byte)(b | 0x1);
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
      if ((paramLong == 0L) || (paramLong > 2147483647L))
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(paramLong);
        throw Http20Draft16.access$500("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", arrayOfObject);
      }
      frameHeader(paramInt, 4, (byte)8, (byte)0);
      this.sink.writeInt((int)paramLong);
      this.sink.flush();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Http20Draft16
 * JD-Core Version:    0.7.0.1
 */