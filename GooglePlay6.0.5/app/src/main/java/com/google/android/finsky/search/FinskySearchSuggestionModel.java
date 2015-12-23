package com.google.android.finsky.search;

import android.graphics.drawable.Drawable;
import com.google.android.finsky.protos.Link;
import com.google.android.play.search.PlaySearchSuggestionModel;

public final class FinskySearchSuggestionModel
  extends PlaySearchSuggestionModel
{
  public final boolean isRecentSearchSuggestion;
  public final Link link;
  public final byte[] serverLogsCookie;
  
  public FinskySearchSuggestionModel(String paramString1, String paramString2, Drawable paramDrawable1, String paramString3, boolean paramBoolean1, Drawable paramDrawable2, Link paramLink, byte[] paramArrayOfByte, boolean paramBoolean2)
  {
    super(paramString1, paramString2, paramDrawable1, paramString3, paramBoolean1, paramDrawable2);
    this.link = paramLink;
    this.serverLogsCookie = paramArrayOfByte;
    this.isRecentSearchSuggestion = paramBoolean2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.FinskySearchSuggestionModel
 * JD-Core Version:    0.7.0.1
 */