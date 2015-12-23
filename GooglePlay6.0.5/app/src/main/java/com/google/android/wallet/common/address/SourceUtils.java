package com.google.android.wallet.common.address;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Pair;
import java.util.List;

public final class SourceUtils
{
  public static SpannableString createSpannableForMatchedSubstrings(List<Pair<Integer, Integer>> paramList, CharSequence paramCharSequence)
  {
    SpannableString localSpannableString = new SpannableString(paramCharSequence);
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      Pair localPair = (Pair)paramList.get(i);
      if (((Integer)localPair.second).intValue() != 0) {
        localSpannableString.setSpan(new StyleSpan(1), ((Integer)localPair.first).intValue(), ((Integer)localPair.first).intValue() + ((Integer)localPair.second).intValue(), 0);
      }
      i++;
    }
    return localSpannableString;
  }
  
  public static int startsWithOrContainsWordPrefixIndex(String paramString, CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramString)) {
      return -1;
    }
    if (TextUtils.isEmpty(paramCharSequence)) {
      return 0;
    }
    return (" " + paramString.toLowerCase()).indexOf((" " + paramCharSequence).toLowerCase());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.SourceUtils
 * JD-Core Version:    0.7.0.1
 */