package com.google.android.finsky.utils;

import android.os.Bundle;

public final class ExpandableUtils
{
  public static int getSavedExpansionState$1c580cd(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {}
    String str;
    do
    {
      return 1;
      str = "expansion_state:" + paramString;
    } while (!paramBundle.containsKey(str));
    return paramBundle.getInt(str);
  }
  
  public static void saveExpansionState(Bundle paramBundle, String paramString, int paramInt)
  {
    paramBundle.putInt("expansion_state:" + paramString, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ExpandableUtils
 * JD-Core Version:    0.7.0.1
 */