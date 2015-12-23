package com.squareup.okhttp.internal.spdy;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyStream
{
  long bytesLeftInWriteWindow;
  final SpdyConnection connection;
  private ErrorCode errorCode = null;
  final int id;
  public final SpdyTimeout readTimeout = new SpdyTimeout();
  private long readTimeoutMillis = 0L;
  private final List<Header> requestHeaders;
  List<Header> responseHeaders;
  final SpdyDataSink sink;
  public final SpdyDataSource source;
  long unacknowledgedBytesRead = 0L;
  private final SpdyTimeout writeTimeout = new SpdyTimeout();
  
  static
  {
    if (!SpdyStream.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  SpdyStream(int paramInt, SpdyConnection paramSpdyConnection, boolean paramBoolean1, boolean paramBoolean2, List<Header> paramList)
  {
    if (paramSpdyConnection == null) {
      throw new NullPointerException("connection == null");
    }
    if (paramList == null) {
      throw new NullPointerException("requestHeaders == null");
    }
    this.id = paramInt;
    this.connection = paramSpdyConnection;
    this.bytesLeftInWriteWindow = paramSpdyConnection.peerSettings.getInitialWindowSize$134621();
    this.source = new SpdyDataSource(paramSpdyConnection.okHttpSettings.getInitialWindowSize$134621(), (byte)0);
    this.sink = new SpdyDataSink();
    SpdyDataSource.access$102(this.source, paramBoolean2);
    SpdyDataSink.access$202(this.sink, paramBoolean1);
    this.requestHeaders = paramList;
  }
  
  private boolean closeInternal(ErrorCode paramErrorCode)
  {
    assert (!Thread.holdsLock(this));
    try
    {
      if (this.errorCode != null) {
        return false;
      }
      if ((this.source.finished) && (this.sink.finished)) {
        return false;
      }
    }
    finally {}
    this.errorCode = paramErrorCode;
    notifyAll();
    this.connection.removeStream(this.id);
    return true;
  }
  
  private void waitForIo()
    throws InterruptedIOException
  {
    try
    {
      wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new InterruptedIOException();
    }
  }
  
  final void addBytesToWriteWindow(long paramLong)
  {
    this.bytesLeftInWriteWindow = (paramLong + this.bytesLeftInWriteWindow);
    if (paramLong > 0L) {
      notifyAll();
    }
  }
  
  public final void close(ErrorCode paramErrorCode)
    throws IOException
  {
    if (!closeInternal(paramErrorCode)) {
      return;
    }
    this.connection.writeSynReset(this.id, paramErrorCode);
  }
  
  public final void closeLater(ErrorCode paramErrorCode)
  {
    if (!closeInternal(paramErrorCode)) {
      return;
    }
    this.connection.writeSynResetLater(this.id, paramErrorCode);
  }
  
  public final List<Header> getResponseHeaders()
    throws IOException
  {
    try
    {
      this.readTimeout.enter();
    }
    finally
    {
      try
      {
        while ((this.responseHeaders == null) && (this.errorCode == null)) {
          waitForIo();
        }
      }
      finally
      {
        this.readTimeout.exitAndThrowIfTimedOut();
      }
    }
    this.readTimeout.exitAndThrowIfTimedOut();
    if (this.responseHeaders != null)
    {
      List localList = this.responseHeaders;
      return localList;
    }
    throw new IOException("stream was reset: " + this.errorCode);
  }
  
  public final Sink getSink()
  {
    try
    {
      if ((this.responseHeaders == null) && (!isLocallyInitiated())) {
        throw new IllegalStateException("reply before requesting the sink");
      }
    }
    finally {}
    return this.sink;
  }
  
  public final boolean isLocallyInitiated()
  {
    if ((0x1 & this.id) == 1) {}
    for (int i = 1; this.connection.client == i; i = 0) {
      return true;
    }
    return false;
  }
  
  /* Error */
  public final boolean isOpen()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 56	com/squareup/okhttp/internal/spdy/SpdyStream:errorCode	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
    //   6: astore_2
    //   7: iconst_0
    //   8: istore_3
    //   9: aload_2
    //   10: ifnull +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_3
    //   16: ireturn
    //   17: aload_0
    //   18: getfield 93	com/squareup/okhttp/internal/spdy/SpdyStream:source	Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSource;
    //   21: invokestatic 124	com/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSource:access$100	(Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSource;)Z
    //   24: ifne +13 -> 37
    //   27: aload_0
    //   28: getfield 93	com/squareup/okhttp/internal/spdy/SpdyStream:source	Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSource;
    //   31: invokestatic 127	com/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSource:access$300	(Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSource;)Z
    //   34: ifeq +36 -> 70
    //   37: aload_0
    //   38: getfield 98	com/squareup/okhttp/internal/spdy/SpdyStream:sink	Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSink;
    //   41: invokestatic 131	com/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSink:access$200	(Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSink;)Z
    //   44: ifne +13 -> 57
    //   47: aload_0
    //   48: getfield 98	com/squareup/okhttp/internal/spdy/SpdyStream:sink	Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSink;
    //   51: invokestatic 134	com/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSink:access$400	(Lcom/squareup/okhttp/internal/spdy/SpdyStream$SpdyDataSink;)Z
    //   54: ifeq +16 -> 70
    //   57: aload_0
    //   58: getfield 214	com/squareup/okhttp/internal/spdy/SpdyStream:responseHeaders	Ljava/util/List;
    //   61: astore 4
    //   63: iconst_0
    //   64: istore_3
    //   65: aload 4
    //   67: ifnonnull -54 -> 13
    //   70: iconst_1
    //   71: istore_3
    //   72: goto -59 -> 13
    //   75: astore_1
    //   76: aload_0
    //   77: monitorexit
    //   78: aload_1
    //   79: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	this	SpdyStream
    //   75	4	1	localObject	Object
    //   6	4	2	localErrorCode	ErrorCode
    //   8	64	3	bool	boolean
    //   61	5	4	localList	List
    // Exception table:
    //   from	to	target	type
    //   2	7	75	finally
    //   17	37	75	finally
    //   37	57	75	finally
    //   57	63	75	finally
  }
  
  final void receiveFin()
  {
    assert (!Thread.holdsLock(this));
    try
    {
      SpdyDataSource.access$102(this.source, true);
      boolean bool = isOpen();
      notifyAll();
      if (!bool) {
        this.connection.removeStream(this.id);
      }
      return;
    }
    finally {}
  }
  
  final void receiveRstStream(ErrorCode paramErrorCode)
  {
    try
    {
      if (this.errorCode == null)
      {
        this.errorCode = paramErrorCode;
        notifyAll();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final class SpdyDataSink
    implements Sink
  {
    private boolean closed;
    private boolean finished;
    
    static
    {
      if (!SpdyStream.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    SpdyDataSink() {}
    
    public final void close()
      throws IOException
    {
      assert (!Thread.holdsLock(SpdyStream.this));
      synchronized (SpdyStream.this)
      {
        if (this.closed) {
          return;
        }
        if (!SpdyStream.this.sink.finished) {
          SpdyStream.this.connection.writeData(SpdyStream.this.id, true, null, 0L);
        }
      }
      synchronized (SpdyStream.this)
      {
        this.closed = true;
        SpdyStream.this.connection.flush();
        SpdyStream.access$1000(SpdyStream.this);
        return;
        localObject1 = finally;
        throw localObject1;
      }
    }
    
    public final void flush()
      throws IOException
    {
      assert (!Thread.holdsLock(SpdyStream.this));
      synchronized (SpdyStream.this)
      {
        SpdyStream.access$1200(SpdyStream.this);
        SpdyStream.this.connection.flush();
        return;
      }
    }
    
    public final Timeout timeout()
    {
      return SpdyStream.this.writeTimeout;
    }
    
    public final void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      assert (!Thread.holdsLock(SpdyStream.this));
      SpdyStream localSpdyStream1;
      try
      {
        SpdyStream.this.writeTimeout.exitAndThrowIfTimedOut();
        SpdyStream.access$1200(SpdyStream.this);
        long l = Math.min(SpdyStream.this.bytesLeftInWriteWindow, paramLong);
        SpdyStream localSpdyStream2 = SpdyStream.this;
        localSpdyStream2.bytesLeftInWriteWindow -= l;
        paramLong -= l;
        SpdyStream.this.connection.writeData(SpdyStream.this.id, false, paramBuffer, l);
        if (paramLong > 0L)
        {
          localSpdyStream1 = SpdyStream.this;
          SpdyStream.this.writeTimeout.enter();
        }
      }
      finally
      {
        try
        {
          for (;;)
          {
            if ((SpdyStream.this.bytesLeftInWriteWindow <= 0L) && (!this.finished) && (!this.closed) && (SpdyStream.this.errorCode == null)) {
              SpdyStream.this.waitForIo();
            }
          }
        }
        finally
        {
          SpdyStream.this.writeTimeout.exitAndThrowIfTimedOut();
        }
      }
    }
  }
  
  private final class SpdyDataSource
    implements Source
  {
    private boolean closed;
    private boolean finished;
    private final long maxByteCount;
    private final Buffer readBuffer = new Buffer();
    private final Buffer receiveBuffer = new Buffer();
    
    static
    {
      if (!SpdyStream.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    private SpdyDataSource(long paramLong)
    {
      this.maxByteCount = paramLong;
    }
    
    private void waitUntilReadable()
      throws IOException
    {
      SpdyStream.this.readTimeout.enter();
      try
      {
        if (this.readBuffer.size == 0L) {
          if ((!this.finished) && (!this.closed) && (SpdyStream.this.errorCode == null)) {
            SpdyStream.this.waitForIo();
          }
        }
        return;
      }
      finally
      {
        SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
      }
    }
    
    public final void close()
      throws IOException
    {
      synchronized (SpdyStream.this)
      {
        this.closed = true;
        this.readBuffer.clear();
        SpdyStream.this.notifyAll();
        SpdyStream.access$1000(SpdyStream.this);
        return;
      }
    }
    
    public final long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      }
      synchronized (SpdyStream.this)
      {
        waitUntilReadable();
        if (this.closed) {
          throw new IOException("stream closed");
        }
      }
      if (SpdyStream.this.errorCode != null) {
        throw new IOException("stream was reset: " + SpdyStream.this.errorCode);
      }
      if (this.readBuffer.size == 0L) {
        return -1L;
      }
      long l = this.readBuffer.read(paramBuffer, Math.min(paramLong, this.readBuffer.size));
      SpdyStream localSpdyStream2 = SpdyStream.this;
      localSpdyStream2.unacknowledgedBytesRead = (l + localSpdyStream2.unacknowledgedBytesRead);
      if (SpdyStream.this.unacknowledgedBytesRead >= SpdyStream.this.connection.okHttpSettings.getInitialWindowSize$134621() / 2)
      {
        SpdyStream.this.connection.writeWindowUpdateLater(SpdyStream.this.id, SpdyStream.this.unacknowledgedBytesRead);
        SpdyStream.this.unacknowledgedBytesRead = 0L;
      }
      synchronized (SpdyStream.this.connection)
      {
        SpdyConnection localSpdyConnection2 = SpdyStream.this.connection;
        localSpdyConnection2.unacknowledgedBytesRead = (l + localSpdyConnection2.unacknowledgedBytesRead);
        if (SpdyStream.this.connection.unacknowledgedBytesRead >= SpdyStream.this.connection.okHttpSettings.getInitialWindowSize$134621() / 2)
        {
          SpdyStream.this.connection.writeWindowUpdateLater(0, SpdyStream.this.connection.unacknowledgedBytesRead);
          SpdyStream.this.connection.unacknowledgedBytesRead = 0L;
        }
        return l;
      }
    }
    
    final void receive(BufferedSource paramBufferedSource, long paramLong)
      throws IOException
    {
      if ((!$assertionsDisabled) && (Thread.holdsLock(SpdyStream.this))) {
        throw new AssertionError();
      }
      for (;;)
      {
        Object localObject2;
        paramLong -= localObject2;
        synchronized (SpdyStream.this)
        {
          if (this.readBuffer.size == 0L)
          {
            j = 1;
            this.readBuffer.writeAll(this.receiveBuffer);
            if (j != 0) {
              SpdyStream.this.notifyAll();
            }
            if (paramLong > 0L) {}
            boolean bool;
            synchronized (SpdyStream.this)
            {
              bool = this.finished;
              int i;
              if (paramLong + this.readBuffer.size > this.maxByteCount)
              {
                i = 1;
                if (i != 0)
                {
                  paramBufferedSource.skip(paramLong);
                  SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                }
              }
              else
              {
                i = 0;
              }
            }
            if (bool)
            {
              paramBufferedSource.skip(paramLong);
              return;
            }
            long l = paramBufferedSource.read(this.receiveBuffer, paramLong);
            if (l != -1L) {
              continue;
            }
            throw new EOFException();
          }
          int j = 0;
        }
      }
    }
    
    public final Timeout timeout()
    {
      return SpdyStream.this.readTimeout;
    }
  }
  
  final class SpdyTimeout
    extends AsyncTimeout
  {
    SpdyTimeout() {}
    
    public final void exitAndThrowIfTimedOut()
      throws InterruptedIOException
    {
      if (exit()) {
        throw new InterruptedIOException("timeout");
      }
    }
    
    protected final void timedOut()
    {
      SpdyStream.this.closeLater(ErrorCode.CANCEL);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.SpdyStream
 * JD-Core Version:    0.7.0.1
 */