package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class SpdyConnection
  implements Closeable
{
  private static final ExecutorService executor;
  long bytesLeftInWriteWindow;
  final boolean client;
  private final Set<Integer> currentPushRequests = new LinkedHashSet();
  public final FrameWriter frameWriter;
  private final IncomingStreamHandler handler;
  private final String hostName;
  private long idleStartTimeNs = System.nanoTime();
  private int lastGoodStreamId;
  private int nextPingId;
  private int nextStreamId;
  public final Settings okHttpSettings = new Settings();
  final Settings peerSettings = new Settings();
  private Map<Integer, Ping> pings;
  public final Protocol protocol;
  private final ExecutorService pushExecutor;
  private final PushObserver pushObserver;
  final Reader readerRunnable;
  private boolean receivedInitialPeerSettings = false;
  private boolean shutdown;
  final Socket socket;
  private final Map<Integer, SpdyStream> streams = new HashMap();
  long unacknowledgedBytesRead = 0L;
  final Variant variant;
  
  static
  {
    if (!SpdyConnection.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      executor = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory$4b642d48("OkHttp SpdyConnection"));
      return;
    }
  }
  
  private SpdyConnection(Builder paramBuilder)
    throws IOException
  {
    this.protocol = paramBuilder.protocol;
    this.pushObserver = paramBuilder.pushObserver;
    this.client = paramBuilder.client;
    this.handler = paramBuilder.handler;
    int j;
    if (paramBuilder.client)
    {
      j = 1;
      this.nextStreamId = j;
      if ((paramBuilder.client) && (this.protocol == Protocol.HTTP_2)) {
        this.nextStreamId = (2 + this.nextStreamId);
      }
      if (paramBuilder.client) {
        i = 1;
      }
      this.nextPingId = i;
      if (paramBuilder.client) {
        this.okHttpSettings.set(7, 0, 16777216);
      }
      this.hostName = paramBuilder.hostName;
      if (this.protocol != Protocol.HTTP_2) {
        break label367;
      }
      this.variant = new Http20Draft16();
      TimeUnit localTimeUnit = TimeUnit.SECONDS;
      LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.hostName;
      this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, localTimeUnit, localLinkedBlockingQueue, Util.threadFactory$4b642d48(String.format("OkHttp %s Push Observer", arrayOfObject)));
      this.peerSettings.set(7, 0, 65535);
      this.peerSettings.set(5, 0, 16384);
    }
    for (;;)
    {
      this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize$134621();
      this.socket = paramBuilder.socket;
      this.frameWriter = this.variant.newWriter(Okio.buffer(Okio.sink(paramBuilder.socket)), this.client);
      this.readerRunnable = new Reader((byte)0);
      new Thread(this.readerRunnable).start();
      return;
      j = i;
      break;
      label367:
      if (this.protocol != Protocol.SPDY_3) {
        break label396;
      }
      this.variant = new Spdy3();
      this.pushExecutor = null;
    }
    label396:
    throw new AssertionError(this.protocol);
  }
  
  /* Error */
  private void close(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2)
    throws IOException
  {
    // Byte code:
    //   0: getstatic 59	com/squareup/okhttp/internal/spdy/SpdyConnection:$assertionsDisabled	Z
    //   3: ifne +18 -> 21
    //   6: aload_0
    //   7: invokestatic 402	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   10: ifeq +11 -> 21
    //   13: new 221	java/lang/AssertionError
    //   16: dup
    //   17: invokespecial 403	java/lang/AssertionError:<init>	()V
    //   20: athrow
    //   21: aconst_null
    //   22: astore_3
    //   23: aload_0
    //   24: getfield 198	com/squareup/okhttp/internal/spdy/SpdyConnection:frameWriter	Lcom/squareup/okhttp/internal/spdy/FrameWriter;
    //   27: astore 19
    //   29: aload 19
    //   31: monitorenter
    //   32: aload_0
    //   33: monitorenter
    //   34: aload_0
    //   35: getfield 307	com/squareup/okhttp/internal/spdy/SpdyConnection:shutdown	Z
    //   38: ifeq +169 -> 207
    //   41: aload_0
    //   42: monitorexit
    //   43: aload 19
    //   45: monitorexit
    //   46: aload_0
    //   47: monitorenter
    //   48: aload_0
    //   49: getfield 97	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   52: invokeinterface 408 1 0
    //   57: istore 5
    //   59: aconst_null
    //   60: astore 6
    //   62: iload 5
    //   64: ifne +48 -> 112
    //   67: aload_0
    //   68: getfield 97	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   71: invokeinterface 412 1 0
    //   76: aload_0
    //   77: getfield 97	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   80: invokeinterface 414 1 0
    //   85: anewarray 416	com/squareup/okhttp/internal/spdy/SpdyStream
    //   88: invokeinterface 422 2 0
    //   93: checkcast 424	[Lcom/squareup/okhttp/internal/spdy/SpdyStream;
    //   96: astore 6
    //   98: aload_0
    //   99: getfield 97	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   102: invokeinterface 427 1 0
    //   107: aload_0
    //   108: iconst_0
    //   109: invokespecial 431	com/squareup/okhttp/internal/spdy/SpdyConnection:setIdle	(Z)V
    //   112: aload_0
    //   113: getfield 433	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   116: astore 7
    //   118: aconst_null
    //   119: astore 8
    //   121: aload 7
    //   123: ifnull +39 -> 162
    //   126: aload_0
    //   127: getfield 433	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   130: invokeinterface 412 1 0
    //   135: aload_0
    //   136: getfield 433	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   139: invokeinterface 414 1 0
    //   144: anewarray 383	com/squareup/okhttp/internal/spdy/Ping
    //   147: invokeinterface 422 2 0
    //   152: checkcast 435	[Lcom/squareup/okhttp/internal/spdy/Ping;
    //   155: astore 8
    //   157: aload_0
    //   158: aconst_null
    //   159: putfield 433	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   162: aload_0
    //   163: monitorexit
    //   164: aload 6
    //   166: ifnull +115 -> 281
    //   169: aload 6
    //   171: astore 14
    //   173: aload 6
    //   175: arraylength
    //   176: istore 15
    //   178: iconst_0
    //   179: istore 16
    //   181: iload 16
    //   183: iload 15
    //   185: if_icmpge +96 -> 281
    //   188: aload 14
    //   190: iload 16
    //   192: aaload
    //   193: astore 17
    //   195: aload 17
    //   197: aload_2
    //   198: invokevirtual 438	com/squareup/okhttp/internal/spdy/SpdyStream:close	(Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
    //   201: iinc 16 1
    //   204: goto -23 -> 181
    //   207: aload_0
    //   208: iconst_1
    //   209: putfield 307	com/squareup/okhttp/internal/spdy/SpdyConnection:shutdown	Z
    //   212: aload_0
    //   213: getfield 312	com/squareup/okhttp/internal/spdy/SpdyConnection:lastGoodStreamId	I
    //   216: istore 22
    //   218: aload_0
    //   219: monitorexit
    //   220: aload_0
    //   221: getfield 198	com/squareup/okhttp/internal/spdy/SpdyConnection:frameWriter	Lcom/squareup/okhttp/internal/spdy/FrameWriter;
    //   224: iload 22
    //   226: aload_1
    //   227: getstatic 442	com/squareup/okhttp/internal/Util:EMPTY_BYTE_ARRAY	[B
    //   230: invokeinterface 446 4 0
    //   235: aload 19
    //   237: monitorexit
    //   238: aconst_null
    //   239: astore_3
    //   240: goto -194 -> 46
    //   243: astore 20
    //   245: aload 19
    //   247: monitorexit
    //   248: aload 20
    //   250: athrow
    //   251: astore_3
    //   252: goto -206 -> 46
    //   255: astore 21
    //   257: aload_0
    //   258: monitorexit
    //   259: aload 21
    //   261: athrow
    //   262: astore 4
    //   264: aload_0
    //   265: monitorexit
    //   266: aload 4
    //   268: athrow
    //   269: astore 18
    //   271: aload_3
    //   272: ifnull -71 -> 201
    //   275: aload 18
    //   277: astore_3
    //   278: goto -77 -> 201
    //   281: aload 8
    //   283: ifnull +87 -> 370
    //   286: aload 8
    //   288: astore 10
    //   290: aload 8
    //   292: arraylength
    //   293: istore 11
    //   295: iconst_0
    //   296: istore 12
    //   298: iload 12
    //   300: iload 11
    //   302: if_icmpge +68 -> 370
    //   305: aload 10
    //   307: iload 12
    //   309: aaload
    //   310: astore 13
    //   312: aload 13
    //   314: getfield 449	com/squareup/okhttp/internal/spdy/Ping:received	J
    //   317: ldc2_w 387
    //   320: lcmp
    //   321: ifne +15 -> 336
    //   324: aload 13
    //   326: getfield 386	com/squareup/okhttp/internal/spdy/Ping:sent	J
    //   329: ldc2_w 387
    //   332: lcmp
    //   333: ifne +11 -> 344
    //   336: new 390	java/lang/IllegalStateException
    //   339: dup
    //   340: invokespecial 391	java/lang/IllegalStateException:<init>	()V
    //   343: athrow
    //   344: aload 13
    //   346: aload 13
    //   348: getfield 386	com/squareup/okhttp/internal/spdy/Ping:sent	J
    //   351: lconst_1
    //   352: lsub
    //   353: putfield 449	com/squareup/okhttp/internal/spdy/Ping:received	J
    //   356: aload 13
    //   358: getfield 453	com/squareup/okhttp/internal/spdy/Ping:latch	Ljava/util/concurrent/CountDownLatch;
    //   361: invokevirtual 458	java/util/concurrent/CountDownLatch:countDown	()V
    //   364: iinc 12 1
    //   367: goto -69 -> 298
    //   370: aload_0
    //   371: getfield 198	com/squareup/okhttp/internal/spdy/SpdyConnection:frameWriter	Lcom/squareup/okhttp/internal/spdy/FrameWriter;
    //   374: invokeinterface 460 1 0
    //   379: aload_0
    //   380: getfield 180	com/squareup/okhttp/internal/spdy/SpdyConnection:socket	Ljava/net/Socket;
    //   383: invokevirtual 463	java/net/Socket:close	()V
    //   386: aload_3
    //   387: ifnull +21 -> 408
    //   390: aload_3
    //   391: athrow
    //   392: astore 9
    //   394: aload_3
    //   395: ifnonnull -16 -> 379
    //   398: aload 9
    //   400: astore_3
    //   401: goto -22 -> 379
    //   404: astore_3
    //   405: goto -19 -> 386
    //   408: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	409	0	this	SpdyConnection
    //   0	409	1	paramErrorCode1	ErrorCode
    //   0	409	2	paramErrorCode2	ErrorCode
    //   22	218	3	localObject1	Object
    //   251	21	3	localIOException1	IOException
    //   277	124	3	localObject2	Object
    //   404	1	3	localIOException2	IOException
    //   262	5	4	localObject3	Object
    //   57	6	5	bool	boolean
    //   60	114	6	arrayOfSpdyStream1	SpdyStream[]
    //   116	6	7	localMap	Map
    //   119	172	8	arrayOfPing1	Ping[]
    //   392	7	9	localIOException3	IOException
    //   288	18	10	arrayOfPing2	Ping[]
    //   293	10	11	i	int
    //   296	69	12	j	int
    //   310	47	13	localPing	Ping
    //   171	18	14	arrayOfSpdyStream2	SpdyStream[]
    //   176	10	15	k	int
    //   179	23	16	m	int
    //   193	3	17	localSpdyStream	SpdyStream
    //   269	7	18	localIOException4	IOException
    //   27	219	19	localFrameWriter	FrameWriter
    //   243	6	20	localObject4	Object
    //   255	5	21	localObject5	Object
    //   216	9	22	n	int
    // Exception table:
    //   from	to	target	type
    //   32	34	243	finally
    //   43	46	243	finally
    //   220	238	243	finally
    //   245	248	243	finally
    //   259	262	243	finally
    //   23	32	251	java/io/IOException
    //   248	251	251	java/io/IOException
    //   34	43	255	finally
    //   207	220	255	finally
    //   257	259	255	finally
    //   48	59	262	finally
    //   67	112	262	finally
    //   112	118	262	finally
    //   126	162	262	finally
    //   162	164	262	finally
    //   264	266	262	finally
    //   195	201	269	java/io/IOException
    //   370	379	392	java/io/IOException
    //   379	386	404	java/io/IOException
  }
  
  /* Error */
  private Ping removePing(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 433	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   6: ifnull +24 -> 30
    //   9: aload_0
    //   10: getfield 433	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   13: iload_1
    //   14: invokestatic 281	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   17: invokeinterface 467 2 0
    //   22: checkcast 383	com/squareup/okhttp/internal/spdy/Ping
    //   25: astore_3
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_3
    //   29: areturn
    //   30: aconst_null
    //   31: astore_3
    //   32: goto -6 -> 26
    //   35: astore_2
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_2
    //   39: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	SpdyConnection
    //   0	40	1	paramInt	int
    //   35	4	2	localObject	Object
    //   25	7	3	localPing	Ping
    // Exception table:
    //   from	to	target	type
    //   2	26	35	finally
  }
  
  private void setIdle(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (;;)
    {
      try
      {
        l = System.nanoTime();
        this.idleStartTimeNs = l;
        return;
      }
      finally {}
      long l = 9223372036854775807L;
    }
  }
  
  public final void close()
    throws IOException
  {
    close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
  }
  
  public final void flush()
    throws IOException
  {
    this.frameWriter.flush();
  }
  
  public final long getIdleStartTimeNs()
  {
    try
    {
      long l = this.idleStartTimeNs;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final SpdyStream getStream(int paramInt)
  {
    try
    {
      SpdyStream localSpdyStream = (SpdyStream)this.streams.get(Integer.valueOf(paramInt));
      return localSpdyStream;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final boolean isIdle()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 105	com/squareup/okhttp/internal/spdy/SpdyConnection:idleStartTimeNs	J
    //   6: lstore_2
    //   7: lload_2
    //   8: ldc2_w 468
    //   11: lcmp
    //   12: ifeq +11 -> 23
    //   15: iconst_1
    //   16: istore 4
    //   18: aload_0
    //   19: monitorexit
    //   20: iload 4
    //   22: ireturn
    //   23: iconst_0
    //   24: istore 4
    //   26: goto -8 -> 18
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	34	0	this	SpdyConnection
    //   29	4	1	localObject	Object
    //   6	2	2	l	long
    //   16	9	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	7	29	finally
  }
  
  public final SpdyStream newStream$6c06c739(List<Header> paramList, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    boolean bool = false;
    if (!paramBoolean1) {
      bool = true;
    }
    synchronized (this.frameWriter)
    {
      try
      {
        if (this.shutdown) {
          throw new IOException("shutdown");
        }
      }
      finally {}
    }
    int i = this.nextStreamId;
    this.nextStreamId = (2 + this.nextStreamId);
    SpdyStream localSpdyStream = new SpdyStream(i, this, bool, false, paramList);
    if (localSpdyStream.isOpen())
    {
      this.streams.put(Integer.valueOf(i), localSpdyStream);
      setIdle(false);
    }
    this.frameWriter.synStream(bool, false, i, 0, paramList);
    if (!paramBoolean1) {
      this.frameWriter.flush();
    }
    return localSpdyStream;
  }
  
  final SpdyStream removeStream(int paramInt)
  {
    try
    {
      SpdyStream localSpdyStream = (SpdyStream)this.streams.remove(Integer.valueOf(paramInt));
      if ((localSpdyStream != null) && (this.streams.isEmpty())) {
        setIdle(true);
      }
      return localSpdyStream;
    }
    finally {}
  }
  
  public final void writeData(int paramInt, boolean paramBoolean, Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramLong == 0L)
    {
      this.frameWriter.data(paramBoolean, paramInt, paramBuffer, 0);
      return;
    }
    for (;;)
    {
      try
      {
        int i = Math.min((int)Math.min(paramLong, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
        this.bytesLeftInWriteWindow -= i;
        paramLong -= i;
        FrameWriter localFrameWriter = this.frameWriter;
        if ((paramBoolean) && (paramLong == 0L))
        {
          bool = true;
          localFrameWriter.data(bool, paramInt, paramBuffer, i);
          if (paramLong <= 0L) {
            break;
          }
          try
          {
            if (this.bytesLeftInWriteWindow > 0L) {
              continue;
            }
            wait();
            continue;
            localObject = finally;
          }
          catch (InterruptedException localInterruptedException)
          {
            throw new InterruptedIOException();
          }
        }
        boolean bool = false;
      }
      finally {}
    }
  }
  
  final void writeSynReset(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    this.frameWriter.rstStream(paramInt, paramErrorCode);
  }
  
  final void writeSynResetLater(final int paramInt, final ErrorCode paramErrorCode)
  {
    ExecutorService localExecutorService = executor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localExecutorService.submit(new NamedRunnable("OkHttp %s stream %d", arrayOfObject)
    {
      public final void execute()
      {
        try
        {
          SpdyConnection.this.writeSynReset(paramInt, paramErrorCode);
          return;
        }
        catch (IOException localIOException) {}
      }
    });
  }
  
  final void writeWindowUpdateLater(final int paramInt, final long paramLong)
  {
    ExecutorService localExecutorService = executor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localExecutorService.submit(new NamedRunnable("OkHttp Window Update %s stream %d", arrayOfObject)
    {
      public final void execute()
      {
        try
        {
          SpdyConnection.this.frameWriter.windowUpdate(paramInt, paramLong);
          return;
        }
        catch (IOException localIOException) {}
      }
    });
  }
  
  public static final class Builder
  {
    boolean client;
    IncomingStreamHandler handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
    String hostName;
    public Protocol protocol = Protocol.SPDY_3;
    PushObserver pushObserver = PushObserver.CANCEL;
    Socket socket;
    
    public Builder(String paramString, Socket paramSocket)
      throws IOException
    {
      this.hostName = paramString;
      this.client = true;
      this.socket = paramSocket;
    }
  }
  
  final class Reader
    extends NamedRunnable
    implements FrameReader.Handler
  {
    FrameReader frameReader;
    
    private Reader()
    {
      super(arrayOfObject);
    }
    
    public final void data(boolean paramBoolean, int paramInt1, BufferedSource paramBufferedSource, int paramInt2)
      throws IOException
    {
      if (SpdyConnection.access$1100(SpdyConnection.this, paramInt1)) {
        SpdyConnection.access$1200(SpdyConnection.this, paramInt1, paramBufferedSource, paramInt2, paramBoolean);
      }
      SpdyStream localSpdyStream;
      do
      {
        return;
        localSpdyStream = SpdyConnection.this.getStream(paramInt1);
        if (localSpdyStream == null)
        {
          SpdyConnection.this.writeSynResetLater(paramInt1, ErrorCode.INVALID_STREAM);
          paramBufferedSource.skip(paramInt2);
          return;
        }
        if ((!SpdyStream.$assertionsDisabled) && (Thread.holdsLock(localSpdyStream))) {
          throw new AssertionError();
        }
        localSpdyStream.source.receive(paramBufferedSource, paramInt2);
      } while (!paramBoolean);
      localSpdyStream.receiveFin();
    }
    
    /* Error */
    protected final void execute()
    {
      // Byte code:
      //   0: getstatic 96	com/squareup/okhttp/internal/spdy/ErrorCode:INTERNAL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   3: astore_1
      //   4: getstatic 96	com/squareup/okhttp/internal/spdy/ErrorCode:INTERNAL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   7: astore_2
      //   8: aload_0
      //   9: aload_0
      //   10: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   13: getfield 100	com/squareup/okhttp/internal/spdy/SpdyConnection:variant	Lcom/squareup/okhttp/internal/spdy/Variant;
      //   16: aload_0
      //   17: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   20: getfield 104	com/squareup/okhttp/internal/spdy/SpdyConnection:socket	Ljava/net/Socket;
      //   23: invokestatic 109	okio/Okio:source	(Ljava/net/Socket;)Lokio/Source;
      //   26: invokestatic 113	okio/Okio:buffer	(Lokio/Source;)Lokio/BufferedSource;
      //   29: aload_0
      //   30: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   33: getfield 116	com/squareup/okhttp/internal/spdy/SpdyConnection:client	Z
      //   36: invokeinterface 122 3 0
      //   41: putfield 124	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   44: aload_0
      //   45: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   48: getfield 116	com/squareup/okhttp/internal/spdy/SpdyConnection:client	Z
      //   51: ifne +12 -> 63
      //   54: aload_0
      //   55: getfield 124	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   58: invokeinterface 129 1 0
      //   63: aload_0
      //   64: getfield 124	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   67: aload_0
      //   68: invokeinterface 133 2 0
      //   73: ifne -10 -> 63
      //   76: getstatic 136	com/squareup/okhttp/internal/spdy/ErrorCode:NO_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   79: astore_1
      //   80: getstatic 139	com/squareup/okhttp/internal/spdy/ErrorCode:CANCEL	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   83: astore 8
      //   85: aload_0
      //   86: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   89: aload_1
      //   90: aload 8
      //   92: invokestatic 143	com/squareup/okhttp/internal/spdy/SpdyConnection:access$1000	(Lcom/squareup/okhttp/internal/spdy/SpdyConnection;Lcom/squareup/okhttp/internal/spdy/ErrorCode;Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
      //   95: aload_0
      //   96: getfield 124	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   99: invokestatic 149	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   102: return
      //   103: astore 5
      //   105: getstatic 152	com/squareup/okhttp/internal/spdy/ErrorCode:PROTOCOL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   108: astore_1
      //   109: getstatic 152	com/squareup/okhttp/internal/spdy/ErrorCode:PROTOCOL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   112: astore 6
      //   114: aload_0
      //   115: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   118: aload_1
      //   119: aload 6
      //   121: invokestatic 143	com/squareup/okhttp/internal/spdy/SpdyConnection:access$1000	(Lcom/squareup/okhttp/internal/spdy/SpdyConnection;Lcom/squareup/okhttp/internal/spdy/ErrorCode;Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
      //   124: aload_0
      //   125: getfield 124	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   128: invokestatic 149	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   131: return
      //   132: astore_3
      //   133: aload_0
      //   134: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   137: aload_1
      //   138: aload_2
      //   139: invokestatic 143	com/squareup/okhttp/internal/spdy/SpdyConnection:access$1000	(Lcom/squareup/okhttp/internal/spdy/SpdyConnection;Lcom/squareup/okhttp/internal/spdy/ErrorCode;Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
      //   142: aload_0
      //   143: getfield 124	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   146: invokestatic 149	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   149: aload_3
      //   150: athrow
      //   151: astore 4
      //   153: goto -11 -> 142
      //   156: astore 7
      //   158: goto -34 -> 124
      //   161: astore 9
      //   163: goto -68 -> 95
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	166	0	this	Reader
      //   3	135	1	localErrorCode1	ErrorCode
      //   7	132	2	localErrorCode2	ErrorCode
      //   132	18	3	localObject	Object
      //   151	1	4	localIOException1	IOException
      //   103	1	5	localIOException2	IOException
      //   112	8	6	localErrorCode3	ErrorCode
      //   156	1	7	localIOException3	IOException
      //   83	8	8	localErrorCode4	ErrorCode
      //   161	1	9	localIOException4	IOException
      // Exception table:
      //   from	to	target	type
      //   8	63	103	java/io/IOException
      //   63	85	103	java/io/IOException
      //   8	63	132	finally
      //   63	85	132	finally
      //   105	114	132	finally
      //   133	142	151	java/io/IOException
      //   114	124	156	java/io/IOException
      //   85	95	161	java/io/IOException
    }
    
    public final void goAway$44df1550(int paramInt, ByteString paramByteString)
    {
      synchronized (SpdyConnection.this)
      {
        SpdyStream[] arrayOfSpdyStream = (SpdyStream[])SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
        SpdyConnection.access$1402$27431131(SpdyConnection.this);
        int i = arrayOfSpdyStream.length;
        int j = 0;
        if (j < i)
        {
          SpdyStream localSpdyStream = arrayOfSpdyStream[j];
          if ((localSpdyStream.id > paramInt) && (localSpdyStream.isLocallyInitiated()))
          {
            localSpdyStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
            SpdyConnection.this.removeStream(localSpdyStream.id);
          }
          j++;
        }
      }
    }
    
    public final void headers$6fea9721(boolean paramBoolean1, boolean paramBoolean2, int paramInt, List<Header> paramList, HeadersMode paramHeadersMode)
    {
      boolean bool1 = true;
      if (SpdyConnection.access$1100(SpdyConnection.this, paramInt))
      {
        SpdyConnection.access$1300(SpdyConnection.this, paramInt, paramList, paramBoolean2);
        return;
      }
      synchronized (SpdyConnection.this)
      {
        if (SpdyConnection.this.shutdown) {
          return;
        }
      }
      SpdyStream localSpdyStream1 = SpdyConnection.this.getStream(paramInt);
      int i;
      if (localSpdyStream1 == null)
      {
        if (paramHeadersMode == HeadersMode.SPDY_REPLY) {
          break label517;
        }
        HeadersMode localHeadersMode1 = HeadersMode.SPDY_HEADERS;
        i = 0;
        if (paramHeadersMode == localHeadersMode1) {
          break label517;
        }
      }
      for (;;)
      {
        if (i != 0)
        {
          SpdyConnection.this.writeSynResetLater(paramInt, ErrorCode.INVALID_STREAM);
          return;
        }
        if (paramInt <= SpdyConnection.this.lastGoodStreamId) {
          return;
        }
        if (paramInt % 2 == SpdyConnection.this.nextStreamId % 2) {
          return;
        }
        final SpdyStream localSpdyStream2 = new SpdyStream(paramInt, SpdyConnection.this, paramBoolean1, paramBoolean2, paramList);
        SpdyConnection.access$1502(SpdyConnection.this, paramInt);
        SpdyConnection.this.streams.put(Integer.valueOf(paramInt), localSpdyStream2);
        ExecutorService localExecutorService = SpdyConnection.executor;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = SpdyConnection.this.hostName;
        arrayOfObject[1] = Integer.valueOf(paramInt);
        localExecutorService.submit(new NamedRunnable("OkHttp %s stream %d", arrayOfObject)
        {
          public final void execute()
          {
            try
            {
              SpdyConnection.this.handler.receive(localSpdyStream2);
              return;
            }
            catch (IOException localIOException)
            {
              throw new RuntimeException(localIOException);
            }
          }
        });
        return;
        if (paramHeadersMode == HeadersMode.SPDY_SYN_STREAM) {}
        for (boolean bool2 = bool1; bool2; bool2 = false)
        {
          localSpdyStream1.closeLater(ErrorCode.PROTOCOL_ERROR);
          SpdyConnection.this.removeStream(paramInt);
          return;
        }
        if ((!SpdyStream.$assertionsDisabled) && (Thread.holdsLock(localSpdyStream1))) {
          throw new AssertionError();
        }
        for (;;)
        {
          ErrorCode localErrorCode;
          try
          {
            if (localSpdyStream1.responseHeaders == null)
            {
              HeadersMode localHeadersMode3 = HeadersMode.SPDY_HEADERS;
              int k = 0;
              if (paramHeadersMode == localHeadersMode3) {
                k = bool1;
              }
              if (k != 0)
              {
                localErrorCode = ErrorCode.PROTOCOL_ERROR;
                if (localErrorCode == null) {
                  break label495;
                }
                localSpdyStream1.closeLater(localErrorCode);
                if (!paramBoolean2) {
                  break;
                }
                localSpdyStream1.receiveFin();
                return;
              }
              localSpdyStream1.responseHeaders = paramList;
              bool1 = localSpdyStream1.isOpen();
              localSpdyStream1.notifyAll();
              localErrorCode = null;
              continue;
            }
            localHeadersMode2 = HeadersMode.SPDY_REPLY;
          }
          finally {}
          HeadersMode localHeadersMode2;
          int j = 0;
          if (paramHeadersMode == localHeadersMode2) {
            j = bool1;
          }
          if (j != 0)
          {
            localErrorCode = ErrorCode.STREAM_IN_USE;
          }
          else
          {
            ArrayList localArrayList = new ArrayList();
            localArrayList.addAll(localSpdyStream1.responseHeaders);
            localArrayList.addAll(paramList);
            localSpdyStream1.responseHeaders = localArrayList;
            localErrorCode = null;
            continue;
            label495:
            if (!bool1) {
              localSpdyStream1.connection.removeStream(localSpdyStream1.id);
            }
          }
        }
        label517:
        i = bool1;
      }
    }
    
    public final void ping(boolean paramBoolean, int paramInt1, int paramInt2)
    {
      if (paramBoolean)
      {
        Ping localPing = SpdyConnection.this.removePing(paramInt1);
        if (localPing != null)
        {
          if ((localPing.received != -1L) || (localPing.sent == -1L)) {
            throw new IllegalStateException();
          }
          localPing.received = System.nanoTime();
          localPing.latch.countDown();
        }
        return;
      }
      SpdyConnection.access$2300$479a839d(SpdyConnection.this, paramInt1, paramInt2);
    }
    
    public final void pushPromise$16014a7a(int paramInt, List<Header> paramList)
    {
      SpdyConnection.access$2400(SpdyConnection.this, paramInt, paramList);
    }
    
    public final void rstStream(int paramInt, ErrorCode paramErrorCode)
    {
      if (SpdyConnection.access$1100(SpdyConnection.this, paramInt)) {
        SpdyConnection.access$2000(SpdyConnection.this, paramInt, paramErrorCode);
      }
      SpdyStream localSpdyStream;
      do
      {
        return;
        localSpdyStream = SpdyConnection.this.removeStream(paramInt);
      } while (localSpdyStream == null);
      localSpdyStream.receiveRstStream(paramErrorCode);
    }
    
    public final void settings(boolean paramBoolean, final Settings paramSettings)
    {
      long l = 0L;
      for (;;)
      {
        int j;
        SpdyStream[] arrayOfSpdyStream2;
        int n;
        synchronized (SpdyConnection.this)
        {
          int i = SpdyConnection.this.peerSettings.getInitialWindowSize$134621();
          if (paramBoolean)
          {
            Settings localSettings1 = SpdyConnection.this.peerSettings;
            localSettings1.persisted = 0;
            localSettings1.persistValue = 0;
            localSettings1.set = 0;
            Arrays.fill(localSettings1.values, 0);
          }
          Settings localSettings2 = SpdyConnection.this.peerSettings;
          j = 0;
          if (j < 10)
          {
            if (!paramSettings.isSet(j)) {
              break label391;
            }
            localSettings2.set(j, paramSettings.flags(j), paramSettings.values[j]);
            break label391;
          }
          if (SpdyConnection.this.protocol == Protocol.HTTP_2)
          {
            ExecutorService localExecutorService = SpdyConnection.executor;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = SpdyConnection.this.hostName;
            localExecutorService.submit(new NamedRunnable("OkHttp %s ACK Settings", arrayOfObject)
            {
              public final void execute()
              {
                try
                {
                  SpdyConnection.this.frameWriter.ackSettings(paramSettings);
                  return;
                }
                catch (IOException localIOException) {}
              }
            });
          }
          int k = SpdyConnection.this.peerSettings.getInitialWindowSize$134621();
          SpdyStream[] arrayOfSpdyStream1 = null;
          if (k != -1)
          {
            arrayOfSpdyStream1 = null;
            if (k != i)
            {
              l = k - i;
              if (!SpdyConnection.this.receivedInitialPeerSettings)
              {
                SpdyConnection localSpdyConnection2 = SpdyConnection.this;
                localSpdyConnection2.bytesLeftInWriteWindow = (l + localSpdyConnection2.bytesLeftInWriteWindow);
                if (l > 0L) {
                  localSpdyConnection2.notifyAll();
                }
                SpdyConnection.access$2102$27431131(SpdyConnection.this);
              }
              boolean bool = SpdyConnection.this.streams.isEmpty();
              arrayOfSpdyStream1 = null;
              if (!bool) {
                arrayOfSpdyStream1 = (SpdyStream[])SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
              }
            }
          }
          if ((arrayOfSpdyStream1 == null) || (l == 0L)) {
            break label390;
          }
          arrayOfSpdyStream2 = arrayOfSpdyStream1;
          int m = arrayOfSpdyStream1.length;
          n = 0;
          if (n >= m) {
            break label390;
          }
        }
        synchronized (arrayOfSpdyStream2[n])
        {
          ???.addBytesToWriteWindow(l);
          n++;
          continue;
          localObject1 = finally;
          throw localObject1;
        }
        label390:
        return;
        label391:
        j++;
      }
    }
    
    public final void windowUpdate(int paramInt, long paramLong)
    {
      if (paramInt == 0) {
        synchronized (SpdyConnection.this)
        {
          SpdyConnection localSpdyConnection2 = SpdyConnection.this;
          localSpdyConnection2.bytesLeftInWriteWindow = (paramLong + localSpdyConnection2.bytesLeftInWriteWindow);
          SpdyConnection.this.notifyAll();
          return;
        }
      }
      SpdyStream localSpdyStream = SpdyConnection.this.getStream(paramInt);
      if (localSpdyStream != null) {
        try
        {
          localSpdyStream.addBytesToWriteWindow(paramLong);
          return;
        }
        finally {}
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.SpdyConnection
 * JD-Core Version:    0.7.0.1
 */