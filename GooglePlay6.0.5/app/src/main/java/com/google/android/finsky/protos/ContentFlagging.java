package com.google.android.finsky.protos;

import com.google.protobuf.nano.MessageNano;

public abstract interface ContentFlagging
{
  public static final class FlagContentResponse
    extends MessageNano
  {
    public FlagContentResponse()
    {
      this.cachedSize = -1;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.ContentFlagging
 * JD-Core Version:    0.7.0.1
 */