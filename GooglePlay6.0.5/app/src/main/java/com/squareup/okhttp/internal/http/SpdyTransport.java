package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Timeout;

public final class SpdyTransport
  implements Transport
{
  private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS;
  private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS;
  private final HttpEngine httpEngine;
  private final SpdyConnection spdyConnection;
  private SpdyStream stream;
  
  static
  {
    ByteString[] arrayOfByteString1 = new ByteString[5];
    arrayOfByteString1[0] = ByteString.encodeUtf8("connection");
    arrayOfByteString1[1] = ByteString.encodeUtf8("host");
    arrayOfByteString1[2] = ByteString.encodeUtf8("keep-alive");
    arrayOfByteString1[3] = ByteString.encodeUtf8("proxy-connection");
    arrayOfByteString1[4] = ByteString.encodeUtf8("transfer-encoding");
    SPDY_3_PROHIBITED_HEADERS = Util.immutableList(arrayOfByteString1);
    ByteString[] arrayOfByteString2 = new ByteString[8];
    arrayOfByteString2[0] = ByteString.encodeUtf8("connection");
    arrayOfByteString2[1] = ByteString.encodeUtf8("host");
    arrayOfByteString2[2] = ByteString.encodeUtf8("keep-alive");
    arrayOfByteString2[3] = ByteString.encodeUtf8("proxy-connection");
    arrayOfByteString2[4] = ByteString.encodeUtf8("te");
    arrayOfByteString2[5] = ByteString.encodeUtf8("transfer-encoding");
    arrayOfByteString2[6] = ByteString.encodeUtf8("encoding");
    arrayOfByteString2[7] = ByteString.encodeUtf8("upgrade");
    HTTP_2_PROHIBITED_HEADERS = Util.immutableList(arrayOfByteString2);
  }
  
  public SpdyTransport(HttpEngine paramHttpEngine, SpdyConnection paramSpdyConnection)
  {
    this.httpEngine = paramHttpEngine;
    this.spdyConnection = paramSpdyConnection;
  }
  
  private static boolean isProhibitedHeader(Protocol paramProtocol, ByteString paramByteString)
  {
    if (paramProtocol == Protocol.SPDY_3) {
      return SPDY_3_PROHIBITED_HEADERS.contains(paramByteString);
    }
    if (paramProtocol == Protocol.HTTP_2) {
      return HTTP_2_PROHIBITED_HEADERS.contains(paramByteString);
    }
    throw new AssertionError(paramProtocol);
  }
  
  public final boolean canReuseConnection()
  {
    return true;
  }
  
  public final Sink createRequestBody(Request paramRequest, long paramLong)
    throws IOException
  {
    return this.stream.getSink();
  }
  
  public final void finishRequest()
    throws IOException
  {
    this.stream.getSink().close();
  }
  
  public final ResponseBody openResponseBody(Response paramResponse)
    throws IOException
  {
    return new RealResponseBody(paramResponse.headers, Okio.buffer(this.stream.source));
  }
  
  public final Response.Builder readResponseHeaders()
    throws IOException
  {
    List localList = this.stream.getResponseHeaders();
    Protocol localProtocol = this.spdyConnection.protocol;
    Object localObject1 = null;
    Object localObject2 = "HTTP/1.1";
    Headers.Builder localBuilder = new Headers.Builder();
    localBuilder.set(OkHeaders.SELECTED_PROTOCOL, localProtocol.toString());
    int i = localList.size();
    int j = 0;
    while (j < i)
    {
      ByteString localByteString = ((Header)localList.get(j)).name;
      String str = ((Header)localList.get(j)).value.utf8();
      Object localObject3 = localObject2;
      int k = 0;
      if (k < str.length())
      {
        int m = str.indexOf(0, k);
        if (m == -1) {
          m = str.length();
        }
        Object localObject4 = str.substring(k, m);
        if (localByteString.equals(Header.RESPONSE_STATUS)) {}
        for (;;)
        {
          int n = m + 1;
          localObject1 = localObject4;
          k = n;
          break;
          if (localByteString.equals(Header.VERSION))
          {
            localObject3 = localObject4;
            localObject4 = localObject1;
          }
          else
          {
            if (!isProhibitedHeader(localProtocol, localByteString)) {
              localBuilder.add(localByteString.utf8(), (String)localObject4);
            }
            localObject4 = localObject1;
          }
        }
      }
      j++;
      localObject2 = localObject3;
    }
    if (localObject1 == null) {
      throw new ProtocolException("Expected ':status' header not present");
    }
    StatusLine localStatusLine = StatusLine.parse((String)localObject2 + " " + localObject1);
    Response.Builder localBuilder1 = new Response.Builder();
    localBuilder1.protocol = localProtocol;
    localBuilder1.code = localStatusLine.code;
    localBuilder1.message = localStatusLine.message;
    return localBuilder1.headers(localBuilder.build());
  }
  
  public final void releaseConnectionOnIdle() {}
  
  public final void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException
  {
    paramRetryableSink.writeToSocket(this.stream.getSink());
  }
  
  public final void writeRequestHeaders(Request paramRequest)
    throws IOException
  {
    if (this.stream != null) {
      return;
    }
    this.httpEngine.writingRequestHeaders();
    boolean bool = this.httpEngine.permitsRequestBody();
    String str1 = RequestLine.version(this.httpEngine.connection.protocol);
    SpdyConnection localSpdyConnection = this.spdyConnection;
    Protocol localProtocol = this.spdyConnection.protocol;
    Headers localHeaders = paramRequest.headers;
    ArrayList localArrayList = new ArrayList(10 + localHeaders.namesAndValues.length / 2);
    localArrayList.add(new Header(Header.TARGET_METHOD, paramRequest.method));
    localArrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(paramRequest.url())));
    String str2 = HttpEngine.hostHeader(paramRequest.url());
    int j;
    label228:
    ByteString localByteString;
    String str3;
    if (Protocol.SPDY_3 == localProtocol)
    {
      localArrayList.add(new Header(Header.VERSION, str1));
      localArrayList.add(new Header(Header.TARGET_HOST, str2));
      localArrayList.add(new Header(Header.TARGET_SCHEME, paramRequest.url().getProtocol()));
      LinkedHashSet localLinkedHashSet = new LinkedHashSet();
      int i = localHeaders.namesAndValues.length / 2;
      j = 0;
      if (j >= i) {
        break label517;
      }
      localByteString = ByteString.encodeUtf8(localHeaders.name(j).toLowerCase(Locale.US));
      str3 = localHeaders.value(j);
      if ((!isProhibitedHeader(localProtocol, localByteString)) && (!localByteString.equals(Header.TARGET_METHOD)) && (!localByteString.equals(Header.TARGET_PATH)) && (!localByteString.equals(Header.TARGET_SCHEME)) && (!localByteString.equals(Header.TARGET_AUTHORITY)) && (!localByteString.equals(Header.TARGET_HOST)) && (!localByteString.equals(Header.VERSION)))
      {
        if (!localLinkedHashSet.add(localByteString)) {
          break label414;
        }
        localArrayList.add(new Header(localByteString, str3));
      }
    }
    label515:
    for (;;)
    {
      j++;
      break label228;
      if (Protocol.HTTP_2 == localProtocol)
      {
        localArrayList.add(new Header(Header.TARGET_AUTHORITY, str2));
        break;
      }
      throw new AssertionError();
      label414:
      for (int k = 0;; k++)
      {
        if (k >= localArrayList.size()) {
          break label515;
        }
        if (((Header)localArrayList.get(k)).name.equals(localByteString))
        {
          localArrayList.set(k, new Header(localByteString, ((Header)localArrayList.get(k)).value.utf8() + '\000' + str3));
          break;
        }
      }
    }
    label517:
    this.stream = localSpdyConnection.newStream$6c06c739(localArrayList, bool, true);
    this.stream.readTimeout.timeout(this.httpEngine.client.readTimeout, TimeUnit.MILLISECONDS);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.SpdyTransport
 * JD-Core Version:    0.7.0.1
 */