package com.squareup.okhttp.internal.http;

import java.io.IOException;
import okio.Sink;

public abstract interface CacheRequest
{
  public abstract Sink body()
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.CacheRequest
 * JD-Core Version:    0.7.0.1
 */