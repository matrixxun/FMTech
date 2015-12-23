package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpConnection
{
  private static final byte[] CRLF = { 13, 10 };
  private static final byte[] FINAL_CHUNK = { 48, 13, 10, 13, 10 };
  private static final byte[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  final Connection connection;
  int onIdle = 0;
  final ConnectionPool pool;
  final BufferedSink sink;
  private final Socket socket;
  public final BufferedSource source;
  int state = 0;
  
  public HttpConnection(ConnectionPool paramConnectionPool, Connection paramConnection, Socket paramSocket)
    throws IOException
  {
    this.pool = paramConnectionPool;
    this.connection = paramConnection;
    this.socket = paramSocket;
    this.source = Okio.buffer(Okio.source(paramSocket));
    this.sink = Okio.buffer(Okio.sink(paramSocket));
  }
  
  public final void flush()
    throws IOException
  {
    this.sink.flush();
  }
  
  public final boolean isReadable()
  {
    try
    {
      int i = this.socket.getSoTimeout();
      try
      {
        this.socket.setSoTimeout(1);
        boolean bool = this.source.exhausted();
        return !bool;
      }
      finally
      {
        this.socket.setSoTimeout(i);
      }
      return false;
    }
    catch (SocketTimeoutException localSocketTimeoutException)
    {
      return true;
    }
    catch (IOException localIOException) {}
  }
  
  public final Source newFixedLengthSource(long paramLong)
    throws IOException
  {
    if (this.state != 4) {
      throw new IllegalStateException("state: " + this.state);
    }
    this.state = 5;
    return new FixedLengthSource(paramLong);
  }
  
  public final void readHeaders(Headers.Builder paramBuilder)
    throws IOException
  {
    for (;;)
    {
      String str = this.source.readUtf8LineStrict();
      if (str.length() == 0) {
        break;
      }
      Internal.instance.addLine(paramBuilder, str);
    }
  }
  
  public final Response.Builder readResponse()
    throws IOException
  {
    if ((this.state != 1) && (this.state != 3)) {
      throw new IllegalStateException("state: " + this.state);
    }
    StatusLine localStatusLine;
    Response.Builder localBuilder;
    do
    {
      localStatusLine = StatusLine.parse(this.source.readUtf8LineStrict());
      localBuilder = new Response.Builder();
      localBuilder.protocol = localStatusLine.protocol;
      localBuilder.code = localStatusLine.code;
      localBuilder.message = localStatusLine.message;
      Headers.Builder localBuilder1 = new Headers.Builder();
      readHeaders(localBuilder1);
      localBuilder1.add(OkHeaders.SELECTED_PROTOCOL, localStatusLine.protocol.toString());
      localBuilder.headers(localBuilder1.build());
    } while (localStatusLine.code == 100);
    this.state = 4;
    return localBuilder;
  }
  
  public final void setTimeouts(int paramInt1, int paramInt2)
  {
    if (paramInt1 != 0) {
      this.source.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
    }
    if (paramInt2 != 0) {
      this.sink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
    }
  }
  
  public final void writeRequest(Headers paramHeaders, String paramString)
    throws IOException
  {
    if (this.state != 0) {
      throw new IllegalStateException("state: " + this.state);
    }
    this.sink.writeUtf8(paramString).writeUtf8("\r\n");
    int i = 0;
    int j = paramHeaders.namesAndValues.length / 2;
    while (i < j)
    {
      this.sink.writeUtf8(paramHeaders.name(i)).writeUtf8(": ").writeUtf8(paramHeaders.value(i)).writeUtf8("\r\n");
      i++;
    }
    this.sink.writeUtf8("\r\n");
    this.state = 1;
  }
  
  private abstract class AbstractSource
    implements Source
  {
    protected boolean closed;
    
    private AbstractSource() {}
    
    protected final void endOfInput(boolean paramBoolean)
      throws IOException
    {
      if (HttpConnection.this.state != 5) {
        throw new IllegalStateException("state: " + HttpConnection.this.state);
      }
      HttpConnection.access$402(HttpConnection.this, 0);
      if ((paramBoolean) && (HttpConnection.this.onIdle == 1))
      {
        HttpConnection.access$902$76e0d70f(HttpConnection.this);
        Internal.instance.recycle(HttpConnection.this.pool, HttpConnection.this.connection);
      }
      while (HttpConnection.this.onIdle != 2) {
        return;
      }
      HttpConnection.access$402(HttpConnection.this, 6);
      HttpConnection.this.connection.socket.close();
    }
    
    public final Timeout timeout()
    {
      return HttpConnection.this.source.timeout();
    }
    
    protected final void unexpectedEndOfInput()
    {
      Util.closeQuietly(HttpConnection.this.connection.socket);
      HttpConnection.access$402(HttpConnection.this, 6);
    }
  }
  
  private final class ChunkedSink
    implements Sink
  {
    private boolean closed;
    private final byte[] hex = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 10 };
    
    private ChunkedSink() {}
    
    private void writeHex(long paramLong)
      throws IOException
    {
      int i = 16;
      do
      {
        byte[] arrayOfByte = this.hex;
        i--;
        arrayOfByte[i] = HttpConnection.HEX_DIGITS[((int)(0xF & paramLong))];
        paramLong >>>= 4;
      } while (paramLong != 0L);
      HttpConnection.this.sink.write(this.hex, i, this.hex.length - i);
    }
    
    /* Error */
    public final void close()
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 52	com/squareup/okhttp/internal/http/HttpConnection$ChunkedSink:closed	Z
      //   6: istore_2
      //   7: iload_2
      //   8: ifeq +6 -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: iconst_1
      //   16: putfield 52	com/squareup/okhttp/internal/http/HttpConnection$ChunkedSink:closed	Z
      //   19: aload_0
      //   20: getfield 16	com/squareup/okhttp/internal/http/HttpConnection$ChunkedSink:this$0	Lcom/squareup/okhttp/internal/http/HttpConnection;
      //   23: invokestatic 43	com/squareup/okhttp/internal/http/HttpConnection:access$300	(Lcom/squareup/okhttp/internal/http/HttpConnection;)Lokio/BufferedSink;
      //   26: invokestatic 55	com/squareup/okhttp/internal/http/HttpConnection:access$600	()[B
      //   29: invokeinterface 58 2 0
      //   34: pop
      //   35: aload_0
      //   36: getfield 16	com/squareup/okhttp/internal/http/HttpConnection$ChunkedSink:this$0	Lcom/squareup/okhttp/internal/http/HttpConnection;
      //   39: iconst_3
      //   40: invokestatic 62	com/squareup/okhttp/internal/http/HttpConnection:access$402	(Lcom/squareup/okhttp/internal/http/HttpConnection;I)I
      //   43: pop
      //   44: goto -33 -> 11
      //   47: astore_1
      //   48: aload_0
      //   49: monitorexit
      //   50: aload_1
      //   51: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	52	0	this	ChunkedSink
      //   47	4	1	localObject	Object
      //   6	2	2	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   2	7	47	finally
      //   14	44	47	finally
    }
    
    /* Error */
    public final void flush()
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 52	com/squareup/okhttp/internal/http/HttpConnection$ChunkedSink:closed	Z
      //   6: istore_2
      //   7: iload_2
      //   8: ifeq +6 -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: getfield 16	com/squareup/okhttp/internal/http/HttpConnection$ChunkedSink:this$0	Lcom/squareup/okhttp/internal/http/HttpConnection;
      //   18: invokestatic 43	com/squareup/okhttp/internal/http/HttpConnection:access$300	(Lcom/squareup/okhttp/internal/http/HttpConnection;)Lokio/BufferedSink;
      //   21: invokeinterface 65 1 0
      //   26: goto -15 -> 11
      //   29: astore_1
      //   30: aload_0
      //   31: monitorexit
      //   32: aload_1
      //   33: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	34	0	this	ChunkedSink
      //   29	4	1	localObject	Object
      //   6	2	2	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   2	7	29	finally
      //   14	26	29	finally
    }
    
    public final Timeout timeout()
    {
      return HttpConnection.this.sink.timeout();
    }
    
    public final void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (this.closed) {
        throw new IllegalStateException("closed");
      }
      if (paramLong == 0L) {
        return;
      }
      writeHex(paramLong);
      HttpConnection.this.sink.write(paramBuffer, paramLong);
      HttpConnection.this.sink.write(HttpConnection.CRLF);
    }
  }
  
  private final class ChunkedSource
    extends HttpConnection.AbstractSource
  {
    private int bytesRemainingInChunk = -1;
    private boolean hasMoreChunks = true;
    private final HttpEngine httpEngine;
    
    ChunkedSource(HttpEngine paramHttpEngine)
      throws IOException
    {
      super((byte)0);
      this.httpEngine = paramHttpEngine;
    }
    
    public final void close()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      if ((this.hasMoreChunks) && (!Util.discard$1a4e8ddd(this, TimeUnit.MILLISECONDS))) {
        unexpectedEndOfInput();
      }
      this.closed = true;
    }
    
    public final long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      }
      if (this.closed) {
        throw new IllegalStateException("closed");
      }
      if (!this.hasMoreChunks) {
        return -1L;
      }
      if ((this.bytesRemainingInChunk == 0) || (this.bytesRemainingInChunk == -1))
      {
        if (this.bytesRemainingInChunk != -1) {
          HttpConnection.this.source.readUtf8LineStrict();
        }
        String str = HttpConnection.this.source.readUtf8LineStrict();
        int i = str.indexOf(";");
        if (i != -1) {
          str = str.substring(0, i);
        }
        try
        {
          this.bytesRemainingInChunk = Integer.parseInt(str.trim(), 16);
          if (this.bytesRemainingInChunk == 0)
          {
            this.hasMoreChunks = false;
            Headers.Builder localBuilder = new Headers.Builder();
            HttpConnection.this.readHeaders(localBuilder);
            this.httpEngine.receiveHeaders(localBuilder.build());
            endOfInput(true);
          }
          if (!this.hasMoreChunks) {
            return -1L;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new ProtocolException("Expected a hex chunk size but was " + str);
        }
      }
      long l = HttpConnection.this.source.read(paramBuffer, Math.min(paramLong, this.bytesRemainingInChunk));
      if (l == -1L)
      {
        unexpectedEndOfInput();
        throw new IOException("unexpected end of stream");
      }
      this.bytesRemainingInChunk = ((int)(this.bytesRemainingInChunk - l));
      return l;
    }
  }
  
  private final class FixedLengthSink
    implements Sink
  {
    private long bytesRemaining;
    private boolean closed;
    
    private FixedLengthSink(long paramLong)
    {
      this.bytesRemaining = paramLong;
    }
    
    public final void close()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      this.closed = true;
      if (this.bytesRemaining > 0L) {
        throw new ProtocolException("unexpected end of stream");
      }
      HttpConnection.access$402(HttpConnection.this, 3);
    }
    
    public final void flush()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      HttpConnection.this.sink.flush();
    }
    
    public final Timeout timeout()
    {
      return HttpConnection.this.sink.timeout();
    }
    
    public final void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (this.closed) {
        throw new IllegalStateException("closed");
      }
      Util.checkOffsetAndCount$487762af(paramBuffer.size, paramLong);
      if (paramLong > this.bytesRemaining) {
        throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + paramLong);
      }
      HttpConnection.this.sink.write(paramBuffer, paramLong);
      this.bytesRemaining -= paramLong;
    }
  }
  
  private final class FixedLengthSource
    extends HttpConnection.AbstractSource
  {
    private long bytesRemaining;
    
    public FixedLengthSource(long paramLong)
      throws IOException
    {
      super((byte)0);
      this.bytesRemaining = paramLong;
      if (this.bytesRemaining == 0L) {
        endOfInput(true);
      }
    }
    
    public final void close()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      if ((this.bytesRemaining != 0L) && (!Util.discard$1a4e8ddd(this, TimeUnit.MILLISECONDS))) {
        unexpectedEndOfInput();
      }
      this.closed = true;
    }
    
    public final long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      }
      if (this.closed) {
        throw new IllegalStateException("closed");
      }
      long l;
      if (this.bytesRemaining == 0L) {
        l = -1L;
      }
      do
      {
        return l;
        l = HttpConnection.this.source.read(paramBuffer, Math.min(this.bytesRemaining, paramLong));
        if (l == -1L)
        {
          unexpectedEndOfInput();
          throw new ProtocolException("unexpected end of stream");
        }
        this.bytesRemaining -= l;
      } while (this.bytesRemaining != 0L);
      endOfInput(true);
      return l;
    }
  }
  
  private final class UnknownLengthSource
    extends HttpConnection.AbstractSource
  {
    private boolean inputExhausted;
    
    private UnknownLengthSource()
    {
      super((byte)0);
    }
    
    public final void close()
      throws IOException
    {
      if (this.closed) {
        return;
      }
      if (!this.inputExhausted) {
        unexpectedEndOfInput();
      }
      this.closed = true;
    }
    
    public final long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      }
      if (this.closed) {
        throw new IllegalStateException("closed");
      }
      long l;
      if (this.inputExhausted) {
        l = -1L;
      }
      do
      {
        return l;
        l = HttpConnection.this.source.read(paramBuffer, paramLong);
      } while (l != -1L);
      this.inputExhausted = true;
      endOfInput(false);
      return -1L;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.HttpConnection
 * JD-Core Version:    0.7.0.1
 */