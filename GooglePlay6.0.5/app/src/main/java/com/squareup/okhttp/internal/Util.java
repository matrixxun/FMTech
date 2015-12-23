package com.squareup.okhttp.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Util
{
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final Charset US_ASCII = Charset.forName("US-ASCII");
  public static final Charset UTF_8 = Charset.forName("UTF-8");
  
  public static void checkOffsetAndCount$487762af(long paramLong1, long paramLong2)
  {
    if (((0L | paramLong2) < 0L) || (0L > paramLong1) || (paramLong1 - 0L < paramLong2)) {
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  
  public static void closeAll(Closeable paramCloseable1, Closeable paramCloseable2)
    throws IOException
  {
    Object localObject1 = null;
    try
    {
      paramCloseable1.close();
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        for (;;)
        {
          paramCloseable2.close();
          if (localObject1 != null) {
            break;
          }
          return;
          localThrowable1 = localThrowable1;
        }
      }
      catch (Throwable localThrowable2)
      {
        Object localObject2;
        for (;;)
        {
          if (localThrowable1 == null) {
            localObject2 = localThrowable2;
          }
        }
        if ((localObject2 instanceof IOException)) {
          throw ((IOException)localObject2);
        }
        if ((localObject2 instanceof RuntimeException)) {
          throw ((RuntimeException)localObject2);
        }
        if ((localObject2 instanceof Error)) {
          throw ((Error)localObject2);
        }
        throw new AssertionError(localObject2);
      }
    }
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException) {}
  }
  
  public static void closeQuietly(Socket paramSocket)
  {
    if (paramSocket != null) {}
    try
    {
      paramSocket.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException) {}
  }
  
  public static boolean discard$1a4e8ddd(Source paramSource, TimeUnit paramTimeUnit)
  {
    try
    {
      boolean bool = skipAll(paramSource, 100, paramTimeUnit);
      return bool;
    }
    catch (IOException localIOException) {}
    return false;
  }
  
  public static boolean equal(Object paramObject1, Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }
  
  public static int getDefaultPort(String paramString)
  {
    if ("http".equals(paramString)) {
      return 80;
    }
    if ("https".equals(paramString)) {
      return 443;
    }
    return -1;
  }
  
  private static int getEffectivePort(String paramString, int paramInt)
  {
    if (paramInt != -1) {
      return paramInt;
    }
    return getDefaultPort(paramString);
  }
  
  public static int getEffectivePort(URI paramURI)
  {
    return getEffectivePort(paramURI.getScheme(), paramURI.getPort());
  }
  
  public static int getEffectivePort(URL paramURL)
  {
    return getEffectivePort(paramURL.getProtocol(), paramURL.getPort());
  }
  
  public static <T> List<T> immutableList(List<T> paramList)
  {
    return Collections.unmodifiableList(new ArrayList(paramList));
  }
  
  public static <T> List<T> immutableList(T... paramVarArgs)
  {
    return Collections.unmodifiableList(Arrays.asList((Object[])paramVarArgs.clone()));
  }
  
  public static <K, V> Map<K, V> immutableMap(Map<K, V> paramMap)
  {
    return Collections.unmodifiableMap(new LinkedHashMap(paramMap));
  }
  
  public static <T> List<T> intersect(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramArrayOfT1.length;
    int j = 0;
    if (j < i)
    {
      T ? = paramArrayOfT1[j];
      int k = paramArrayOfT2.length;
      for (int m = 0;; m++) {
        if (m < k)
        {
          T ? = paramArrayOfT2[m];
          if (?.equals(?)) {
            localArrayList.add(?);
          }
        }
        else
        {
          j++;
          break;
        }
      }
    }
    return localArrayList;
  }
  
  public static ByteString sha1(ByteString paramByteString)
  {
    try
    {
      ByteString localByteString = ByteString.of(MessageDigest.getInstance("SHA-1").digest(paramByteString.toByteArray()));
      return localByteString;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError(localNoSuchAlgorithmException);
    }
  }
  
  public static boolean skipAll(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
    throws IOException
  {
    long l1 = System.nanoTime();
    long l2;
    if (paramSource.timeout().hasDeadline) {
      l2 = paramSource.timeout().deadlineNanoTime() - l1;
    }
    for (;;)
    {
      paramSource.timeout().deadlineNanoTime(l1 + Math.min(l2, paramTimeUnit.toNanos(paramInt)));
      try
      {
        Buffer localBuffer = new Buffer();
        while (paramSource.read(localBuffer, 2048L) != -1L) {
          localBuffer.clear();
        }
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        if (l2 == 9223372036854775807L)
        {
          paramSource.timeout().hasDeadline = false;
          return false;
          l2 = 9223372036854775807L;
          continue;
          if (l2 == 9223372036854775807L) {
            paramSource.timeout().hasDeadline = false;
          }
          for (;;)
          {
            return true;
            paramSource.timeout().deadlineNanoTime(l1 + l2);
          }
        }
        else
        {
          paramSource.timeout().deadlineNanoTime(l1 + l2);
          return false;
        }
      }
      finally
      {
        if (l2 != 9223372036854775807L) {
          break label196;
        }
      }
    }
    paramSource.timeout().hasDeadline = false;
    for (;;)
    {
      throw localObject;
      label196:
      paramSource.timeout().deadlineNanoTime(l1 + l2);
    }
  }
  
  public static ThreadFactory threadFactory$4b642d48(String paramString)
  {
    new ThreadFactory()
    {
      public final Thread newThread(Runnable paramAnonymousRunnable)
      {
        Thread localThread = new Thread(paramAnonymousRunnable, this.val$name);
        localThread.setDaemon(this.val$daemon);
        return localThread;
      }
    };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.Util
 * JD-Core Version:    0.7.0.1
 */