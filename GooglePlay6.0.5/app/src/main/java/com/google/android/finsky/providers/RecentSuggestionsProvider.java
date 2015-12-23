package com.google.android.finsky.providers;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSuggestionsProvider
  extends SearchRecentSuggestionsProvider
{
  public RecentSuggestionsProvider()
  {
    setupSuggestions("com.google.android.finsky.RecentSuggestionsProvider", 1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.providers.RecentSuggestionsProvider
 * JD-Core Version:    0.7.0.1
 */