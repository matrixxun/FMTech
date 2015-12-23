package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool
{
  private static final ConnectionPool systemDefault;
  private final LinkedList<Connection> connections = new LinkedList();
  private final Runnable connectionsCleanupRunnable = new Runnable()
  {
    public final void run()
    {
      ConnectionPool.access$000(ConnectionPool.this);
    }
  };
  private Executor executor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory$4b642d48("OkHttp ConnectionPool"));
  private final long keepAliveDurationNs;
  private final int maxIdleConnections;
  
  static
  {
    String str1 = System.getProperty("http.keepAlive");
    String str2 = System.getProperty("http.keepAliveDuration");
    String str3 = System.getProperty("http.maxConnections");
    if (str2 != null) {}
    for (long l = Long.parseLong(str2); (str1 != null) && (!Boolean.parseBoolean(str1)); l = 300000L)
    {
      systemDefault = new ConnectionPool(0, l);
      return;
    }
    if (str3 != null)
    {
      systemDefault = new ConnectionPool(Integer.parseInt(str3), l);
      return;
    }
    systemDefault = new ConnectionPool(5, l);
  }
  
  private ConnectionPool(int paramInt, long paramLong)
  {
    this.maxIdleConnections = paramInt;
    this.keepAliveDurationNs = (1000L * (paramLong * 1000L));
  }
  
  public static ConnectionPool getDefault()
  {
    return systemDefault;
  }
  
  private boolean performCleanup()
  {
    ArrayList localArrayList;
    int i;
    long l2;
    for (;;)
    {
      Connection localConnection2;
      long l4;
      try
      {
        if (this.connections.isEmpty()) {
          return false;
        }
        localArrayList = new ArrayList();
        i = 0;
        long l1 = System.nanoTime();
        l2 = this.keepAliveDurationNs;
        ListIterator localListIterator1 = this.connections.listIterator(this.connections.size());
        if (!localListIterator1.hasPrevious()) {
          break;
        }
        localConnection2 = (Connection)localListIterator1.previous();
        if (localConnection2.getIdleStartTimeNs() == 9223372036854775807L)
        {
          l4 = 9223372036854775807L;
          if ((l4 > 0L) && (localConnection2.isAlive())) {
            break label149;
          }
          localListIterator1.remove();
          localArrayList.add(localConnection2);
          continue;
        }
        l4 = localConnection2.getIdleStartTimeNs() - l1 + this.keepAliveDurationNs;
      }
      finally {}
      continue;
      label149:
      if (localConnection2.isIdle())
      {
        i++;
        l2 = Math.min(l2, l4);
      }
    }
    ListIterator localListIterator2 = this.connections.listIterator(this.connections.size());
    while ((localListIterator2.hasPrevious()) && (i > this.maxIdleConnections))
    {
      Connection localConnection1 = (Connection)localListIterator2.previous();
      if (localConnection1.isIdle())
      {
        localArrayList.add(localConnection1);
        localListIterator2.remove();
        i--;
      }
    }
    boolean bool = localArrayList.isEmpty();
    if (bool) {}
    try
    {
      long l3 = l2 / 1000000L;
      wait(l3, (int)(l2 - 1000000L * l3));
      return true;
    }
    catch (InterruptedException localInterruptedException)
    {
      label289:
      int j;
      int k;
      break label289;
    }
    j = 0;
    k = localArrayList.size();
    while (j < k)
    {
      Util.closeQuietly(((Connection)localArrayList.get(j)).socket);
      j++;
    }
    return true;
  }
  
  final void addConnection(Connection paramConnection)
  {
    boolean bool = this.connections.isEmpty();
    this.connections.addFirst(paramConnection);
    if (bool)
    {
      this.executor.execute(this.connectionsCleanupRunnable);
      return;
    }
    notifyAll();
  }
  
  /* Error */
  public final Connection get(Address paramAddress)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 64	com/squareup/okhttp/ConnectionPool:connections	Ljava/util/LinkedList;
    //   6: aload_0
    //   7: getfield 64	com/squareup/okhttp/ConnectionPool:connections	Ljava/util/LinkedList;
    //   10: invokevirtual 126	java/util/LinkedList:size	()I
    //   13: invokevirtual 130	java/util/LinkedList:listIterator	(I)Ljava/util/ListIterator;
    //   16: astore_3
    //   17: aload_3
    //   18: invokeinterface 135 1 0
    //   23: istore 4
    //   25: aconst_null
    //   26: astore 5
    //   28: iload 4
    //   30: ifeq +87 -> 117
    //   33: aload_3
    //   34: invokeinterface 139 1 0
    //   39: checkcast 141	com/squareup/okhttp/Connection
    //   42: astore 6
    //   44: aload 6
    //   46: getfield 209	com/squareup/okhttp/Connection:route	Lcom/squareup/okhttp/Route;
    //   49: getfield 215	com/squareup/okhttp/Route:address	Lcom/squareup/okhttp/Address;
    //   52: aload_1
    //   53: invokevirtual 220	com/squareup/okhttp/Address:equals	(Ljava/lang/Object;)Z
    //   56: ifeq -39 -> 17
    //   59: aload 6
    //   61: invokevirtual 149	com/squareup/okhttp/Connection:isAlive	()Z
    //   64: ifeq -47 -> 17
    //   67: invokestatic 122	java/lang/System:nanoTime	()J
    //   70: aload 6
    //   72: invokevirtual 144	com/squareup/okhttp/Connection:getIdleStartTimeNs	()J
    //   75: lsub
    //   76: aload_0
    //   77: getfield 103	com/squareup/okhttp/ConnectionPool:keepAliveDurationNs	J
    //   80: lcmp
    //   81: ifge -64 -> 17
    //   84: aload_3
    //   85: invokeinterface 152 1 0
    //   90: aload 6
    //   92: invokevirtual 223	com/squareup/okhttp/Connection:isSpdy	()Z
    //   95: istore 7
    //   97: iload 7
    //   99: ifne +14 -> 113
    //   102: invokestatic 228	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   105: aload 6
    //   107: getfield 183	com/squareup/okhttp/Connection:socket	Ljava/net/Socket;
    //   110: invokevirtual 231	com/squareup/okhttp/internal/Platform:tagSocket	(Ljava/net/Socket;)V
    //   113: aload 6
    //   115: astore 5
    //   117: aload 5
    //   119: ifnull +20 -> 139
    //   122: aload 5
    //   124: invokevirtual 223	com/squareup/okhttp/Connection:isSpdy	()Z
    //   127: ifeq +12 -> 139
    //   130: aload_0
    //   131: getfield 64	com/squareup/okhttp/ConnectionPool:connections	Ljava/util/LinkedList;
    //   134: aload 5
    //   136: invokevirtual 193	java/util/LinkedList:addFirst	(Ljava/lang/Object;)V
    //   139: aload_0
    //   140: monitorexit
    //   141: aload 5
    //   143: areturn
    //   144: astore 8
    //   146: aload 6
    //   148: getfield 183	com/squareup/okhttp/Connection:socket	Ljava/net/Socket;
    //   151: invokestatic 187	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   154: invokestatic 228	com/squareup/okhttp/internal/Platform:get	()Lcom/squareup/okhttp/internal/Platform;
    //   157: pop
    //   158: new 233	java/lang/StringBuilder
    //   161: dup
    //   162: ldc 235
    //   164: invokespecial 238	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   167: aload 8
    //   169: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   172: invokevirtual 246	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: invokestatic 249	com/squareup/okhttp/internal/Platform:logW	(Ljava/lang/String;)V
    //   178: goto -161 -> 17
    //   181: astore_2
    //   182: aload_0
    //   183: monitorexit
    //   184: aload_2
    //   185: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	186	0	this	ConnectionPool
    //   0	186	1	paramAddress	Address
    //   181	4	2	localObject1	Object
    //   16	69	3	localListIterator	ListIterator
    //   23	6	4	bool1	boolean
    //   26	116	5	localObject2	Object
    //   42	105	6	localConnection	Connection
    //   95	3	7	bool2	boolean
    //   144	24	8	localSocketException	java.net.SocketException
    // Exception table:
    //   from	to	target	type
    //   102	113	144	java/net/SocketException
    //   2	17	181	finally
    //   17	25	181	finally
    //   33	97	181	finally
    //   102	113	181	finally
    //   122	139	181	finally
    //   146	178	181	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.ConnectionPool
 * JD-Core Version:    0.7.0.1
 */