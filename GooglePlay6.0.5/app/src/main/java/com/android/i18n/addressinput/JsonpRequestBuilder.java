package com.android.i18n.addressinput;

import android.os.Process;
import com.google.android.finsky.utils.Utils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

final class JsonpRequestBuilder
{
  private static String encodeUrl(String paramString)
  {
    int i = paramString.length();
    StringBuilder localStringBuilder = new StringBuilder(i);
    int j = 0;
    int k;
    char c;
    if (j < i)
    {
      k = j;
      c = '\000';
      while (k < i)
      {
        c = paramString.charAt(k);
        if ((c == ':') || (c == '/')) {
          break;
        }
        k++;
      }
      if (k == i) {
        localStringBuilder.append(Utils.urlEncode(paramString.substring(j)));
      }
    }
    else
    {
      return localStringBuilder.toString();
    }
    if (k > j)
    {
      localStringBuilder.append(Utils.urlEncode(paramString.substring(j, k)));
      localStringBuilder.append(c);
      j = k;
    }
    for (;;)
    {
      j++;
      break;
      localStringBuilder.append(c);
    }
  }
  
  static void requestObject(String paramString, AsyncCallback<JsoMap> paramAsyncCallback)
  {
    new AsyncHttp(new HttpGet(encodeUrl(paramString)), paramAsyncCallback).start();
  }
  
  static abstract interface AsyncCallback<T>
  {
    public abstract void onFailure$786b7c60();
    
    public abstract void onSuccess(T paramT);
  }
  
  private static final class AsyncHttp
    extends Thread
  {
    private static final HttpClient CLIENT = new DefaultHttpClient();
    private JsonpRequestBuilder.AsyncCallback<JsoMap> mCallback;
    private HttpUriRequest mRequest;
    
    protected AsyncHttp(HttpUriRequest paramHttpUriRequest, JsonpRequestBuilder.AsyncCallback<JsoMap> paramAsyncCallback)
    {
      this.mRequest = paramHttpUriRequest;
      this.mCallback = paramAsyncCallback;
    }
    
    public final void run()
    {
      Process.setThreadPriority(10);
      try
      {
        synchronized (CLIENT)
        {
          String str = (String)CLIENT.execute(this.mRequest, new BasicResponseHandler());
          this.mCallback.onSuccess(JsoMap.buildJsoMap(str));
          return;
        }
        return;
      }
      catch (Exception localException)
      {
        this.mCallback.onFailure$786b7c60();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.JsonpRequestBuilder
 * JD-Core Version:    0.7.0.1
 */