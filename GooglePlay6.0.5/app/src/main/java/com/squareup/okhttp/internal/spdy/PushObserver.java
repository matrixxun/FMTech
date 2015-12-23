package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import okio.BufferedSource;

public abstract interface PushObserver
{
  public static final PushObserver CANCEL = new PushObserver()
  {
    public final boolean onData$749b27ff(BufferedSource paramAnonymousBufferedSource, int paramAnonymousInt)
      throws IOException
    {
      paramAnonymousBufferedSource.skip(paramAnonymousInt);
      return true;
    }
    
    public final boolean onHeaders$4ec42067()
    {
      return true;
    }
    
    public final boolean onRequest$163bb723()
    {
      return true;
    }
    
    public final void onReset$5226a6d5() {}
  };
  
  public abstract boolean onData$749b27ff(BufferedSource paramBufferedSource, int paramInt)
    throws IOException;
  
  public abstract boolean onHeaders$4ec42067();
  
  public abstract boolean onRequest$163bb723();
  
  public abstract void onReset$5226a6d5();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.PushObserver
 * JD-Core Version:    0.7.0.1
 */