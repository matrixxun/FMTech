package com.google.android.volley.ok;

import android.content.ContentResolver;
import android.content.Context;
import com.google.android.volley.guava.UrlRules;
import com.google.android.volley.guava.UrlRules.Rule;

public final class GoogleUrlRewriter
  implements UrlRewriter
{
  private ContentResolver mContentResolver;
  
  public GoogleUrlRewriter(Context paramContext)
  {
    this.mContentResolver = paramContext.getContentResolver();
  }
  
  public final String rewriteUrl(String paramString)
    throws UrlRewriter.BlockedRequestException
  {
    UrlRules.Rule localRule = UrlRules.getRules(this.mContentResolver).matchRule(paramString);
    String str = localRule.apply(paramString);
    if (str == null) {
      throw new UrlRewriter.BlockedRequestException(localRule.mName);
    }
    return str;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.ok.GoogleUrlRewriter
 * JD-Core Version:    0.7.0.1
 */