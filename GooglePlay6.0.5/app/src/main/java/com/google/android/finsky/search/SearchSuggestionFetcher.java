package com.google.android.finsky.search;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.SearchSuggest.SearchSuggestResponse;
import com.google.android.play.utils.config.GservicesValue;

public final class SearchSuggestionFetcher
  extends SuggestionFetcher
{
  private static Boolean sIsGoogleTV = null;
  private final int mBackendId;
  private final int mIconSize;
  private final SearchLogger mLogger;
  private boolean mRequestNavigationalSuggestions;
  private final SuggestionHandler mSuggestionHandler;
  private final boolean mZeroQueryDisabled;
  
  public SearchSuggestionFetcher(Context paramContext, int paramInt, String paramString, SuggestionHandler paramSuggestionHandler, boolean paramBoolean, SearchLogger paramSearchLogger)
  {
    super(paramString, paramContext);
    this.mBackendId = paramInt;
    this.mSuggestionHandler = paramSuggestionHandler;
    this.mZeroQueryDisabled = paramBoolean;
    this.mIconSize = paramContext.getResources().getDimensionPixelSize(2131493465);
    this.mLogger = paramSearchLogger;
    if (sIsGoogleTV == null) {
      sIsGoogleTV = Boolean.valueOf(this.mContext.getPackageManager().hasSystemFeature("com.google.android.tv"));
    }
    if ((!sIsGoogleTV.booleanValue()) || (((Boolean)G.gtvNavigationalSuggestEnabled.get()).booleanValue())) {}
    for (boolean bool = true;; bool = false)
    {
      this.mRequestNavigationalSuggestions = bool;
      return;
    }
  }
  
  protected final void makeRequest(final SuggestionFetcher.OnCompleteListener paramOnCompleteListener)
  {
    if ((this.mZeroQueryDisabled) && (TextUtils.isEmpty(this.mQuery)))
    {
      paramOnCompleteListener.onComplete();
      return;
    }
    FinskyApp.get().getDfeApi(null).searchSuggest$1c9e90a1(this.mQuery, this.mBackendId, this.mIconSize, this.mRequestNavigationalSuggestions, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        SearchLogger.logSearchVolleyError(510, paramAnonymousVolleyError);
        paramOnCompleteListener.onComplete();
      }
    });
    startRequestLatencyTimer();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.SearchSuggestionFetcher
 * JD-Core Version:    0.7.0.1
 */