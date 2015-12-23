package com.google.android.play.search;

import android.content.Context;
import android.text.Spannable;
import android.text.style.TextAppearanceSpan;

public abstract class SuggestionFormatter
{
  private final Context mContext;
  
  protected SuggestionFormatter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  protected final void applyTextStyle(int paramInt1, Spannable paramSpannable, int paramInt2, int paramInt3)
  {
    if (paramInt2 != paramInt3) {
      paramSpannable.setSpan(new TextAppearanceSpan(this.mContext, paramInt1), paramInt2, paramInt3, 0);
    }
  }
  
  protected abstract CharSequence formatSuggestion(String paramString1, String paramString2, int paramInt1, int paramInt2);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.SuggestionFormatter
 * JD-Core Version:    0.7.0.1
 */