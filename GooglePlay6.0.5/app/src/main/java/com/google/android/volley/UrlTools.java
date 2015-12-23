package com.google.android.volley;

import android.content.Context;
import com.google.android.common.http.UrlRules;
import com.google.android.common.http.UrlRules.Rule;

public final class UrlTools
{
  public static String rewrite(Context paramContext, String paramString)
  {
    return UrlRules.getRules(paramContext.getContentResolver()).matchRule(paramString).apply(paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.UrlTools
 * JD-Core Version:    0.7.0.1
 */