package com.squareup.okhttp.internal.spdy;

import okio.BufferedSink;
import okio.BufferedSource;

public abstract interface Variant
{
  public abstract FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean);
  
  public abstract FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Variant
 * JD-Core Version:    0.7.0.1
 */