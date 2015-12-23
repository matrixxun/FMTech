package com.google.android.play.search;

import android.content.Context;

public final class LevenshteinSuggestionFormatter
  extends SuggestionFormatter
{
  public LevenshteinSuggestionFormatter(Context paramContext)
  {
    super(paramContext);
  }
  
  private static LevenshteinDistance.Token[] tokenize(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    char[] arrayOfChar = paramString.toCharArray();
    LevenshteinDistance.Token[] arrayOfToken1 = new LevenshteinDistance.Token[j];
    int k = 0;
    int i1;
    if (i < j)
    {
      while ((i < j) && ((arrayOfChar[i] == ' ') || (arrayOfChar[i] == '\t') || (arrayOfChar[i] == '"'))) {
        i++;
      }
      int m = i;
      while ((i < j) && (arrayOfChar[i] != ' ') && (arrayOfChar[i] != '\t') && (arrayOfChar[i] != '"')) {
        i++;
      }
      int n = i;
      if (m == n) {
        break label160;
      }
      i1 = k + 1;
      arrayOfToken1[k] = new LevenshteinDistance.Token(arrayOfChar, m, n);
    }
    for (;;)
    {
      k = i1;
      break;
      LevenshteinDistance.Token[] arrayOfToken2 = new LevenshteinDistance.Token[k];
      System.arraycopy(arrayOfToken1, 0, arrayOfToken2, 0, k);
      return arrayOfToken2;
      label160:
      i1 = k;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.LevenshteinSuggestionFormatter
 * JD-Core Version:    0.7.0.1
 */