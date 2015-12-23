package com.squareup.okhttp.internal;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.CacheRequest;
import java.io.IOException;

public abstract interface InternalCache
{
  public abstract Response get$7633b7c3()
    throws IOException;
  
  public abstract CacheRequest put$3be241a0()
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.InternalCache
 * JD-Core Version:    0.7.0.1
 */