package com.squareup.okhttp;

import java.io.IOException;
import java.net.Proxy;

public abstract interface Authenticator
{
  public abstract Request authenticate(Proxy paramProxy, Response paramResponse)
    throws IOException;
  
  public abstract Request authenticateProxy(Proxy paramProxy, Response paramResponse)
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Authenticator
 * JD-Core Version:    0.7.0.1
 */