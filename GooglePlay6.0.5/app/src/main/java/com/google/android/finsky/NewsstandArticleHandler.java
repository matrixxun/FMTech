package com.google.android.finsky;

import android.net.Uri;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.play.article.NewsstandArticleLoader;
import com.google.android.play.article.NewsstandArticleLoader.Configurator;
import com.google.android.play.article.NewsstandArticleLoader.HttpRequestCallback;
import com.google.android.play.article.NewsstandArticleLoader.HttpRequestProxy;

public final class NewsstandArticleHandler
  implements NewsstandArticleLoader.HttpRequestProxy
{
  public final NewsstandArticleLoader mArticleLoader = new NewsstandArticleLoader(new NewsstandArticleLoader.Configurator()
  {
    public final NewsstandArticleLoader.HttpRequestProxy getHttpRequestProxy()
    {
      return NewsstandArticleHandler.this;
    }
  });
  
  public final void get(final Uri paramUri, final NewsstandArticleLoader.HttpRequestCallback paramHttpRequestCallback)
  {
    Response.Listener local2 = new Response.Listener() {};
    Response.ErrorListener local3 = new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        paramHttpRequestCallback.onError$757ab272();
      }
    };
    StringRequest localStringRequest = new StringRequest(0, paramUri.toString(), local2, local3);
    FinskyApp.get().mRequestQueue.add(localStringRequest);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.NewsstandArticleHandler
 * JD-Core Version:    0.7.0.1
 */