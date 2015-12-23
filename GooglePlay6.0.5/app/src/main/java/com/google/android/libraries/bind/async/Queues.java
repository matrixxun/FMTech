package com.google.android.libraries.bind.async;

public class Queues
{
  public static final Queue BIND_CPU = new Queue("BIND_CPU");
  public static final Queue BIND_IMMEDIATE = new Queue("BIND_IMMEDIATE", AsyncUtil.immediateExecutor());
  public static final Queue BIND_MAIN = new Queue("BIND_MAIN", AsyncUtil.mainThreadExecutor());
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.async.Queues
 * JD-Core Version:    0.7.0.1
 */