package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.net.ProtocolException;

public final class StatusLine
{
  public final int code;
  public final String message;
  public final Protocol protocol;
  
  private StatusLine(Protocol paramProtocol, int paramInt, String paramString)
  {
    this.protocol = paramProtocol;
    this.code = paramInt;
    this.message = paramString;
  }
  
  public static StatusLine parse(String paramString)
    throws IOException
  {
    int m;
    int i;
    Protocol localProtocol;
    if (paramString.startsWith("HTTP/1."))
    {
      if ((paramString.length() < 9) || (paramString.charAt(8) != ' ')) {
        throw new ProtocolException("Unexpected status line: " + paramString);
      }
      m = '￐' + paramString.charAt(7);
      i = 9;
      if (m == 0) {
        localProtocol = Protocol.HTTP_1_0;
      }
    }
    while (paramString.length() < i + 3)
    {
      throw new ProtocolException("Unexpected status line: " + paramString);
      if (m == 1)
      {
        localProtocol = Protocol.HTTP_1_1;
      }
      else
      {
        throw new ProtocolException("Unexpected status line: " + paramString);
        if (paramString.startsWith("ICY "))
        {
          localProtocol = Protocol.HTTP_1_0;
          i = 4;
        }
        else
        {
          throw new ProtocolException("Unexpected status line: " + paramString);
        }
      }
    }
    int j = i + 3;
    int k;
    try
    {
      k = Integer.parseInt(paramString.substring(i, j));
      str = "";
      if (paramString.length() <= i + 3) {
        break label289;
      }
      if (paramString.charAt(i + 3) != ' ') {
        throw new ProtocolException("Unexpected status line: " + paramString);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProtocolException("Unexpected status line: " + paramString);
    }
    String str = paramString.substring(i + 4);
    label289:
    return new StatusLine(localProtocol, k, str);
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.protocol == Protocol.HTTP_1_0) {}
    for (String str = "HTTP/1.0";; str = "HTTP/1.1")
    {
      localStringBuilder.append(str);
      localStringBuilder.append(' ').append(this.code);
      if (this.message != null) {
        localStringBuilder.append(' ').append(this.message);
      }
      return localStringBuilder.toString();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.StatusLine
 * JD-Core Version:    0.7.0.1
 */