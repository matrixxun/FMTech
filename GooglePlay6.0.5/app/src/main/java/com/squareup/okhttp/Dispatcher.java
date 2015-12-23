package com.squareup.okhttp;

import java.util.ArrayDeque;
import java.util.Deque;

public final class Dispatcher
{
  private final Deque<Call> executedCalls = new ArrayDeque();
  private int maxRequests = 64;
  private int maxRequestsPerHost = 5;
  private final Deque<Object> readyCalls = new ArrayDeque();
  private final Deque<Object> runningCalls = new ArrayDeque();
  
  final void executed(Call paramCall)
  {
    try
    {
      this.executedCalls.add(paramCall);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void finished(Call paramCall)
  {
    try
    {
      if (!this.executedCalls.remove(paramCall)) {
        throw new AssertionError("Call wasn't in-flight!");
      }
    }
    finally {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Dispatcher
 * JD-Core Version:    0.7.0.1
 */