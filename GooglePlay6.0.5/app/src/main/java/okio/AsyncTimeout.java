package okio;

import java.io.IOException;
import java.io.InterruptedIOException;

public class AsyncTimeout
  extends Timeout
{
  private static AsyncTimeout head;
  private boolean inQueue;
  private AsyncTimeout next;
  private long timeoutAt;
  
  /* Error */
  private static AsyncTimeout awaitTimeout()
    throws InterruptedException
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 24	okio/AsyncTimeout:head	Lokio/AsyncTimeout;
    //   6: getfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   9: astore_1
    //   10: aload_1
    //   11: ifnonnull +15 -> 26
    //   14: ldc 2
    //   16: invokevirtual 31	java/lang/Object:wait	()V
    //   19: aconst_null
    //   20: astore_1
    //   21: ldc 2
    //   23: monitorexit
    //   24: aload_1
    //   25: areturn
    //   26: invokestatic 37	java/lang/System:nanoTime	()J
    //   29: lstore_2
    //   30: aload_1
    //   31: getfield 39	okio/AsyncTimeout:timeoutAt	J
    //   34: lload_2
    //   35: lsub
    //   36: lstore 4
    //   38: lload 4
    //   40: lconst_0
    //   41: lcmp
    //   42: ifle +33 -> 75
    //   45: lload 4
    //   47: ldc2_w 40
    //   50: ldiv
    //   51: lstore 6
    //   53: ldc 2
    //   55: lload 6
    //   57: lload 4
    //   59: lload 6
    //   61: ldc2_w 40
    //   64: lmul
    //   65: lsub
    //   66: l2i
    //   67: invokevirtual 44	java/lang/Object:wait	(JI)V
    //   70: aconst_null
    //   71: astore_1
    //   72: goto -51 -> 21
    //   75: getstatic 24	okio/AsyncTimeout:head	Lokio/AsyncTimeout;
    //   78: aload_1
    //   79: getfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   82: putfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   85: aload_1
    //   86: aconst_null
    //   87: putfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   90: goto -69 -> 21
    //   93: astore_0
    //   94: ldc 2
    //   96: monitorexit
    //   97: aload_0
    //   98: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   93	5	0	localObject	Object
    //   9	77	1	localAsyncTimeout	AsyncTimeout
    //   29	6	2	l1	long
    //   36	22	4	l2	long
    //   51	9	6	l3	long
    // Exception table:
    //   from	to	target	type
    //   3	10	93	finally
    //   14	19	93	finally
    //   26	38	93	finally
    //   45	70	93	finally
    //   75	90	93	finally
  }
  
  /* Error */
  private static boolean cancelScheduledTimeout(AsyncTimeout paramAsyncTimeout)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 24	okio/AsyncTimeout:head	Lokio/AsyncTimeout;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull +39 -> 47
    //   11: aload_2
    //   12: getfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   15: aload_0
    //   16: if_acmpne +23 -> 39
    //   19: aload_2
    //   20: aload_0
    //   21: getfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   24: putfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   27: aload_0
    //   28: aconst_null
    //   29: putfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   32: iconst_0
    //   33: istore_3
    //   34: ldc 2
    //   36: monitorexit
    //   37: iload_3
    //   38: ireturn
    //   39: aload_2
    //   40: getfield 26	okio/AsyncTimeout:next	Lokio/AsyncTimeout;
    //   43: astore_2
    //   44: goto -37 -> 7
    //   47: iconst_1
    //   48: istore_3
    //   49: goto -15 -> 34
    //   52: astore_1
    //   53: ldc 2
    //   55: monitorexit
    //   56: aload_1
    //   57: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	paramAsyncTimeout	AsyncTimeout
    //   52	5	1	localObject	Object
    //   6	38	2	localAsyncTimeout	AsyncTimeout
    //   33	16	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   3	7	52	finally
    //   11	32	52	finally
    //   39	44	52	finally
  }
  
  private static void scheduleTimeout(AsyncTimeout paramAsyncTimeout, long paramLong, boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        if (head == null)
        {
          head = new AsyncTimeout();
          new Watchdog().start();
        }
        long l1 = System.nanoTime();
        if ((paramLong != 0L) && (paramBoolean))
        {
          paramAsyncTimeout.timeoutAt = (l1 + Math.min(paramLong, paramAsyncTimeout.deadlineNanoTime() - l1));
          long l2 = paramAsyncTimeout.timeoutAt - l1;
          localAsyncTimeout = head;
          if ((localAsyncTimeout.next != null) && (l2 >= localAsyncTimeout.next.timeoutAt - l1)) {
            break label185;
          }
          paramAsyncTimeout.next = localAsyncTimeout.next;
          localAsyncTimeout.next = paramAsyncTimeout;
          if (localAsyncTimeout == head) {
            AsyncTimeout.class.notify();
          }
          return;
        }
        if (paramLong != 0L)
        {
          long l3 = l1 + paramLong;
          paramAsyncTimeout.timeoutAt = l3;
          continue;
        }
        if (!paramBoolean) {
          break label177;
        }
      }
      finally {}
      paramAsyncTimeout.timeoutAt = paramAsyncTimeout.deadlineNanoTime();
      continue;
      label177:
      throw new AssertionError();
      label185:
      AsyncTimeout localAsyncTimeout = localAsyncTimeout.next;
    }
  }
  
  public final void enter()
  {
    if (this.inQueue) {
      throw new IllegalStateException("Unbalanced enter/exit");
    }
    long l = this.timeoutNanos;
    boolean bool = this.hasDeadline;
    if ((l == 0L) && (!bool)) {
      return;
    }
    this.inQueue = true;
    scheduleTimeout(this, l, bool);
  }
  
  final IOException exit(IOException paramIOException)
    throws IOException
  {
    if (!exit()) {
      return paramIOException;
    }
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    localInterruptedIOException.initCause(paramIOException);
    return localInterruptedIOException;
  }
  
  final void exit(boolean paramBoolean)
    throws IOException
  {
    if ((exit()) && (paramBoolean)) {
      throw new InterruptedIOException("timeout");
    }
  }
  
  public final boolean exit()
  {
    if (!this.inQueue) {
      return false;
    }
    this.inQueue = false;
    return cancelScheduledTimeout(this);
  }
  
  public void timedOut() {}
  
  private static final class Watchdog
    extends Thread
  {
    public Watchdog()
    {
      super();
      setDaemon(true);
    }
    
    public final void run()
    {
      try
      {
        for (;;)
        {
          AsyncTimeout localAsyncTimeout = AsyncTimeout.access$000();
          if (localAsyncTimeout != null) {
            localAsyncTimeout.timedOut();
          }
        }
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.AsyncTimeout
 * JD-Core Version:    0.7.0.1
 */