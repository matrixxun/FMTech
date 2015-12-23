package com.google.android.finsky.search;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.finsky.FinskyApp;
import java.util.Locale;
import org.json.JSONArray;

final class MusicSearchSuggestionFetcher
  extends SuggestionFetcher
{
  static final Uri BASE_URI = Uri.parse("http://www.google.com/complete/search");
  private final SearchLogger mLogger;
  private String mRequestUrl = BASE_URI.buildUpon().appendQueryParameter("q", this.mQuery).appendQueryParameter("json", "1").appendQueryParameter("hl", Locale.getDefault().getLanguage()).appendQueryParameter("gl", Locale.getDefault().getCountry()).appendQueryParameter("ds", "cse").appendQueryParameter("client", "partner").appendQueryParameter("partnerid", "skyjam-store").build().toString();
  protected final SuggestionHandler mSuggestionHandler;
  
  public MusicSearchSuggestionFetcher(String paramString, Context paramContext, SuggestionHandler paramSuggestionHandler, SearchLogger paramSearchLogger)
  {
    super(paramString, paramContext);
    this.mSuggestionHandler = paramSuggestionHandler;
    this.mLogger = paramSearchLogger;
  }
  
  protected final void makeRequest(final SuggestionFetcher.OnCompleteListener paramOnCompleteListener)
  {
    if (TextUtils.isEmpty(this.mQuery))
    {
      paramOnCompleteListener.onComplete();
      return;
    }
    JsonArrayRequest localJsonArrayRequest = new JsonArrayRequest(this.mRequestUrl, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        SearchLogger.logSearchVolleyError(510, paramAnonymousVolleyError);
        paramOnCompleteListener.onComplete();
      }
    });
    FinskyApp.get().mRequestQueue.add(localJsonArrayRequest);
    startRequestLatencyTimer();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.MusicSearchSuggestionFetcher
 * JD-Core Version:    0.7.0.1
 */