package com.google.android.play.search;

import android.graphics.drawable.Drawable;

public class PlaySearchSuggestionModel
{
  public final Drawable defaultIconDrawable;
  public final String displayText;
  public final String docId;
  public final Drawable iconBackgroundDrawable;
  public final String iconUrl;
  public final boolean iconUrlSupportsFifeOptions;
  
  public PlaySearchSuggestionModel(String paramString1, String paramString2, Drawable paramDrawable1, String paramString3, boolean paramBoolean, Drawable paramDrawable2)
  {
    this.displayText = paramString1;
    this.docId = paramString2;
    this.defaultIconDrawable = paramDrawable1;
    this.iconUrl = paramString3;
    this.iconUrlSupportsFifeOptions = paramBoolean;
    this.iconBackgroundDrawable = paramDrawable2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchSuggestionModel
 * JD-Core Version:    0.7.0.1
 */