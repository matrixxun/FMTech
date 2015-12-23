package com.google.android.play.search;

public abstract interface PlaySearchListener
{
  public abstract void onModeChanged(int paramInt);
  
  public abstract void onQueryChanged(String paramString, boolean paramBoolean);
  
  public abstract void onSearch(String paramString);
  
  public abstract void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchListener
 * JD-Core Version:    0.7.0.1
 */