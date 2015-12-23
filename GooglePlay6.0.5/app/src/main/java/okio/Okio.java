package okio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Okio
{
  private static final Logger logger = Logger.getLogger(Okio.class.getName());
  
  public static BufferedSink buffer(Sink paramSink)
  {
    if (paramSink == null) {
      throw new IllegalArgumentException("sink == null");
    }
    return new RealBufferedSink(paramSink);
  }
  
  public static BufferedSource buffer(Source paramSource)
  {
    if (paramSource == null) {
      throw new IllegalArgumentException("source == null");
    }
    return new RealBufferedSource(paramSource);
  }
  
  public static Sink sink(Socket paramSocket)
    throws IOException
  {
    if (paramSocket == null) {
      throw new IllegalArgumentException("socket == null");
    }
    AsyncTimeout localAsyncTimeout = timeout(paramSocket);
    final OutputStream localOutputStream = paramSocket.getOutputStream();
    if (localOutputStream == null) {
      throw new IllegalArgumentException("out == null");
    }
    new AsyncTimeout.1(localAsyncTimeout, new Sink()
    {
      public final void close()
        throws IOException
      {
        localOutputStream.close();
      }
      
      public final void flush()
        throws IOException
      {
        localOutputStream.flush();
      }
      
      public final Timeout timeout()
      {
        return this.val$timeout;
      }
      
      public final String toString()
      {
        return "sink(" + localOutputStream + ")";
      }
      
      public final void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        Util.checkOffsetAndCount(paramAnonymousBuffer.size, 0L, paramAnonymousLong);
        while (paramAnonymousLong > 0L)
        {
          this.val$timeout.throwIfReached();
          Segment localSegment = paramAnonymousBuffer.head;
          int i = (int)Math.min(paramAnonymousLong, localSegment.limit - localSegment.pos);
          localOutputStream.write(localSegment.data, localSegment.pos, i);
          localSegment.pos = (i + localSegment.pos);
          paramAnonymousLong -= i;
          paramAnonymousBuffer.size -= i;
          if (localSegment.pos == localSegment.limit)
          {
            paramAnonymousBuffer.head = localSegment.pop();
            SegmentPool.INSTANCE.recycle(localSegment);
          }
        }
      }
    });
  }
  
  public static Source source(Socket paramSocket)
    throws IOException
  {
    if (paramSocket == null) {
      throw new IllegalArgumentException("socket == null");
    }
    AsyncTimeout localAsyncTimeout = timeout(paramSocket);
    final InputStream localInputStream = paramSocket.getInputStream();
    if (localInputStream == null) {
      throw new IllegalArgumentException("in == null");
    }
    new AsyncTimeout.2(localAsyncTimeout, new Source()
    {
      public final void close()
        throws IOException
      {
        localInputStream.close();
      }
      
      public final long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        if (paramAnonymousLong < 0L) {
          throw new IllegalArgumentException("byteCount < 0: " + paramAnonymousLong);
        }
        this.val$timeout.throwIfReached();
        Segment localSegment = paramAnonymousBuffer.writableSegment(1);
        int i = (int)Math.min(paramAnonymousLong, 2048 - localSegment.limit);
        int j = localInputStream.read(localSegment.data, localSegment.limit, i);
        if (j == -1) {
          return -1L;
        }
        localSegment.limit = (j + localSegment.limit);
        paramAnonymousBuffer.size += j;
        return j;
      }
      
      public final Timeout timeout()
      {
        return this.val$timeout;
      }
      
      public final String toString()
      {
        return "source(" + localInputStream + ")";
      }
    });
  }
  
  private static AsyncTimeout timeout(Socket paramSocket)
  {
    new AsyncTimeout()
    {
      protected final void timedOut()
      {
        try
        {
          this.val$socket.close();
          return;
        }
        catch (Exception localException)
        {
          Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, localException);
        }
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.Okio
 * JD-Core Version:    0.7.0.1
 */