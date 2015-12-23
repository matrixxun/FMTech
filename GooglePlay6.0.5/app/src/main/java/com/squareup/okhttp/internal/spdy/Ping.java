package com.squareup.okhttp.internal.spdy;

import java.util.concurrent.CountDownLatch;

public final class Ping
{
  final CountDownLatch latch = new CountDownLatch(1);
  long received = -1L;
  long sent = -1L;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Ping
 * JD-Core Version:    0.7.0.1
 */