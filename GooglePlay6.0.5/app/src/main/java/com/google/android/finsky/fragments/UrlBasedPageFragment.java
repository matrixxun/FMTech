package com.google.android.finsky.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.finsky.api.model.DfeToc;

public abstract class UrlBasedPageFragment
  extends PageFragment
{
  public String mUrl;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUrl = this.mArguments.getString("finsky.UrlBasedPageFragment.url");
  }
  
  public final void setDfeTocAndUrl(DfeToc paramDfeToc, String paramString)
  {
    setArgument("finsky.PageFragment.toc", paramDfeToc);
    setArgument("finsky.UrlBasedPageFragment.url", paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.UrlBasedPageFragment
 * JD-Core Version:    0.7.0.1
 */