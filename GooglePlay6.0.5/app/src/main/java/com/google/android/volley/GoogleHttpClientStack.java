package com.google.android.volley;

import android.content.Context;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpClientStack;

public final class GoogleHttpClientStack
  extends HttpClientStack
{
  private final GoogleHttpClient mGoogleHttpClient;
  
  public GoogleHttpClientStack(Context paramContext, boolean paramBoolean)
  {
    this(new GoogleHttpClient(paramContext, "unused/0"), paramBoolean);
  }
  
  private GoogleHttpClientStack(GoogleHttpClient paramGoogleHttpClient, boolean paramBoolean)
  {
    super(paramGoogleHttpClient);
    this.mGoogleHttpClient = paramGoogleHttpClient;
    if (paramBoolean)
    {
      String str = VolleyLog.TAG;
      AndroidHttpClient localAndroidHttpClient = paramGoogleHttpClient.mClient;
      if (str == null) {
        throw new NullPointerException("name");
      }
      localAndroidHttpClient.curlConfiguration = new AndroidHttpClient.LoggingConfiguration(str, 2, (byte)0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.GoogleHttpClientStack
 * JD-Core Version:    0.7.0.1
 */