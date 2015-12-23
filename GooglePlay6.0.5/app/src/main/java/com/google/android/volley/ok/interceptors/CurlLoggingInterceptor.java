package com.google.android.volley.ok.interceptors;

import android.util.Base64;
import android.util.Log;
import com.android.volley.VolleyLog;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import okio.Buffer;

public final class CurlLoggingInterceptor
  implements Interceptor
{
  private static boolean isBinaryContent(Headers paramHeaders, MediaType paramMediaType)
  {
    List localList = paramHeaders.values("Content-Encoding");
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext()) {
        if (((String)localIterator.next()).equalsIgnoreCase("gzip")) {
          return true;
        }
      }
    }
    String str1 = paramMediaType.type;
    String str2 = paramMediaType.subtype;
    if (str1 != null)
    {
      if (str1.equals("text")) {
        return false;
      }
      if ((str1.equals("application")) && (str2 != null)) {
        return (!str2.equals("xml")) && (!str2.equals("json"));
      }
    }
    return true;
  }
  
  public final Response intercept(Interceptor.Chain paramChain)
    throws IOException
  {
    Request localRequest = paramChain.request();
    String str1 = VolleyLog.TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("curl ");
    localStringBuilder.append("-X ");
    localStringBuilder.append(localRequest.method);
    localStringBuilder.append(" ");
    Iterator localIterator = localRequest.headers.names().iterator();
    while (localIterator.hasNext())
    {
      String str4 = (String)localIterator.next();
      localStringBuilder.append("--header \"");
      localStringBuilder.append(str4);
      localStringBuilder.append(": ");
      List localList = localRequest.headers.values(str4);
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        localStringBuilder.append((String)localList.get(j));
        if (j < i - 1) {
          localStringBuilder.append(", ");
        }
      }
      localStringBuilder.append("\" ");
    }
    URI localURI = localRequest.uri();
    localStringBuilder.append("\"");
    localStringBuilder.append(localURI);
    localStringBuilder.append("\"");
    ByteArrayOutputStream localByteArrayOutputStream;
    if (localRequest.body != null)
    {
      RequestBody localRequestBody = localRequest.body;
      if (localRequestBody != null)
      {
        if (localRequestBody.contentLength() >= 1024L) {
          break label417;
        }
        localByteArrayOutputStream = new ByteArrayOutputStream();
        Buffer localBuffer = new Buffer();
        localRequestBody.writeTo(localBuffer);
        localBuffer.writeTo(localByteArrayOutputStream, localBuffer.size);
        localBuffer.copyTo(localByteArrayOutputStream, 0L, localBuffer.size);
        if (!isBinaryContent(localRequest.headers, localRequestBody.contentType())) {
          break label389;
        }
        String str3 = Base64.encodeToString(localByteArrayOutputStream.toByteArray(), 2);
        localStringBuilder.insert(0, "echo '" + str3 + "' | base64 -d > /tmp/$$.bin; ");
        localStringBuilder.append(" --data-binary @/tmp/$$.bin");
      }
    }
    for (;;)
    {
      Log.v(str1, localStringBuilder.toString());
      return paramChain.proceed(localRequest);
      label389:
      String str2 = localByteArrayOutputStream.toString();
      localStringBuilder.append(" --data-ascii \"").append(str2).append("\"");
      continue;
      label417:
      localStringBuilder.append(" [TOO MUCH DATA TO INCLUDE]");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.ok.interceptors.CurlLoggingInterceptor
 * JD-Core Version:    0.7.0.1
 */