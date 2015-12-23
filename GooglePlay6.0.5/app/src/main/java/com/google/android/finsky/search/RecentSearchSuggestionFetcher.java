package com.google.android.finsky.search;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.v4.content.ContextCompat;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;

final class RecentSearchSuggestionFetcher
  extends SuggestionFetcher
{
  private static final Uri RECENT_SEARCH_URI = new Uri.Builder().scheme("content").authority("com.google.android.finsky.RecentSuggestionsProvider").appendPath("search_suggest_query").build();
  private Drawable mRecentIcon;
  private final SuggestionHandler mSuggestionHandler;
  
  public RecentSearchSuggestionFetcher(String paramString, Context paramContext, SuggestionHandler paramSuggestionHandler)
  {
    super(paramString, paramContext);
    this.mSuggestionHandler = paramSuggestionHandler;
    this.mRecentIcon = ContextCompat.getDrawable(this.mContext, 2130837882);
  }
  
  protected final void makeRequest(SuggestionFetcher.OnCompleteListener paramOnCompleteListener)
  {
    ContentResolver localContentResolver = this.mContext.getContentResolver();
    Uri localUri = RECENT_SEARCH_URI;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mQuery;
    Cursor localCursor = localContentResolver.query(localUri, null, " ?", arrayOfString, null);
    if (localCursor != null)
    {
      int i = localCursor.getColumnIndex("suggest_text_1");
      if (i >= 0)
      {
        int j = 0;
        FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
        int k;
        if (localFinskyExperiments.isEnabled(739L)) {
          k = 2;
        }
        for (;;)
        {
          localCursor.moveToPosition(0);
          while ((!localCursor.isAfterLast()) && (j < k))
          {
            String str = localCursor.getString(i);
            if (str.startsWith(this.mQuery))
            {
              j++;
              this.mSuggestionHandler.addRow(str, null, this.mRecentIcon, null, null, null, true);
            }
            localCursor.moveToNext();
          }
          if (localFinskyExperiments.isEnabled(12603135L)) {
            k = 3;
          } else {
            k = 2147483647;
          }
        }
        localCursor.close();
      }
    }
    paramOnCompleteListener.onComplete();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.RecentSearchSuggestionFetcher
 * JD-Core Version:    0.7.0.1
 */