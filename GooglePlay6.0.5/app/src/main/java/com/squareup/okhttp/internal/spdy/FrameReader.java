package com.squareup.okhttp.internal.spdy;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okio.BufferedSource;
import okio.ByteString;

public abstract interface FrameReader
  extends Closeable
{
  public abstract boolean nextFrame(Handler paramHandler)
    throws IOException;
  
  public abstract void readConnectionPreface()
    throws IOException;
  
  public static abstract interface Handler
  {
    public abstract void data(boolean paramBoolean, int paramInt1, BufferedSource paramBufferedSource, int paramInt2)
      throws IOException;
    
    public abstract void goAway$44df1550(int paramInt, ByteString paramByteString);
    
    public abstract void headers$6fea9721(boolean paramBoolean1, boolean paramBoolean2, int paramInt, List<Header> paramList, HeadersMode paramHeadersMode);
    
    public abstract void ping(boolean paramBoolean, int paramInt1, int paramInt2);
    
    public abstract void pushPromise$16014a7a(int paramInt, List<Header> paramList)
      throws IOException;
    
    public abstract void rstStream(int paramInt, ErrorCode paramErrorCode);
    
    public abstract void settings(boolean paramBoolean, Settings paramSettings);
    
    public abstract void windowUpdate(int paramInt, long paramLong);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.FrameReader
 * JD-Core Version:    0.7.0.1
 */