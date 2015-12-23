package com.android.volley.toolbox;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class RequestFuture<T>
  implements Response.ErrorListener, Response.Listener<T>, Future<T>
{
  private VolleyError mException;
  public Request<?> mRequest;
  private T mResult;
  private boolean mResultReceived = false;
  
  private T doGet(Long paramLong)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    try
    {
      if (this.mException != null) {
        throw new ExecutionException(this.mException);
      }
    }
    finally {}
    if (this.mResultReceived) {}
    for (Object localObject2 = this.mResult;; localObject2 = this.mResult)
    {
      return localObject2;
      if (paramLong == null) {
        wait(0L);
      }
      while (this.mException != null)
      {
        throw new ExecutionException(this.mException);
        if (paramLong.longValue() > 0L) {
          wait(paramLong.longValue());
        }
      }
      if (!this.mResultReceived) {
        throw new TimeoutException();
      }
    }
  }
  
  public static <E> RequestFuture<E> newFuture()
  {
    return new RequestFuture();
  }
  
  /* Error */
  public final boolean cancel(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 60	com/android/volley/toolbox/RequestFuture:mRequest	Lcom/android/volley/Request;
    //   6: astore_3
    //   7: iconst_0
    //   8: istore 4
    //   10: aload_3
    //   11: ifnonnull +8 -> 19
    //   14: aload_0
    //   15: monitorexit
    //   16: iload 4
    //   18: ireturn
    //   19: aload_0
    //   20: invokevirtual 64	com/android/volley/toolbox/RequestFuture:isDone	()Z
    //   23: istore 5
    //   25: iconst_0
    //   26: istore 4
    //   28: iload 5
    //   30: ifne -16 -> 14
    //   33: aload_0
    //   34: getfield 60	com/android/volley/toolbox/RequestFuture:mRequest	Lcom/android/volley/Request;
    //   37: invokevirtual 68	com/android/volley/Request:cancel	()V
    //   40: iconst_1
    //   41: istore 4
    //   43: goto -29 -> 14
    //   46: astore_2
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_2
    //   50: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	this	RequestFuture
    //   0	51	1	paramBoolean	boolean
    //   46	4	2	localObject	Object
    //   6	5	3	localRequest	Request
    //   8	34	4	bool1	boolean
    //   23	6	5	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	7	46	finally
    //   19	25	46	finally
    //   33	40	46	finally
  }
  
  public final T get()
    throws InterruptedException, ExecutionException
  {
    try
    {
      Object localObject = doGet(null);
      return localObject;
    }
    catch (TimeoutException localTimeoutException)
    {
      throw new AssertionError(localTimeoutException);
    }
  }
  
  public final T get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    return doGet(Long.valueOf(TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit)));
  }
  
  public final boolean isCancelled()
  {
    if (this.mRequest == null) {
      return false;
    }
    return this.mRequest.isCanceled();
  }
  
  /* Error */
  public final boolean isDone()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 27	com/android/volley/toolbox/RequestFuture:mResultReceived	Z
    //   6: ifne +19 -> 25
    //   9: aload_0
    //   10: getfield 37	com/android/volley/toolbox/RequestFuture:mException	Lcom/android/volley/VolleyError;
    //   13: ifnonnull +12 -> 25
    //   16: aload_0
    //   17: invokevirtual 98	com/android/volley/toolbox/RequestFuture:isCancelled	()Z
    //   20: istore_3
    //   21: iload_3
    //   22: ifeq +9 -> 31
    //   25: iconst_1
    //   26: istore_2
    //   27: aload_0
    //   28: monitorexit
    //   29: iload_2
    //   30: ireturn
    //   31: iconst_0
    //   32: istore_2
    //   33: goto -6 -> 27
    //   36: astore_1
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	41	0	this	RequestFuture
    //   36	4	1	localObject	Object
    //   26	7	2	bool1	boolean
    //   20	2	3	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	21	36	finally
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    try
    {
      this.mException = paramVolleyError;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void onResponse(T paramT)
  {
    try
    {
      this.mResultReceived = true;
      this.mResult = paramT;
      notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.RequestFuture
 * JD-Core Version:    0.7.0.1
 */