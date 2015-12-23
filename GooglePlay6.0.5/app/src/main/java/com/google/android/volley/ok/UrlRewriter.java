package com.google.android.volley.ok;

import java.io.IOException;

public abstract interface UrlRewriter
{
  public abstract String rewriteUrl(String paramString)
    throws UrlRewriter.BlockedRequestException;
  
  public static final class BlockedRequestException
    extends IOException
  {
    BlockedRequestException(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.ok.UrlRewriter
 * JD-Core Version:    0.7.0.1
 */