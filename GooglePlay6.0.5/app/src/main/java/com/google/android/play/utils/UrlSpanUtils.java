package com.google.android.play.utils;

import android.text.Spannable;
import android.text.style.URLSpan;
import android.view.View;

public final class UrlSpanUtils
{
  public static void selfishifyUrlSpans(CharSequence paramCharSequence1, CharSequence paramCharSequence2, Listener paramListener)
  {
    if (paramListener == null) {
      throw new IllegalStateException("listener should not be null");
    }
    if (!(paramCharSequence1 instanceof Spannable)) {}
    for (;;)
    {
      return;
      Spannable localSpannable = (Spannable)paramCharSequence1;
      for (URLSpan localURLSpan : (URLSpan[])localSpannable.getSpans(0, localSpannable.length(), URLSpan.class))
      {
        String str = localURLSpan.getURL();
        if ((paramCharSequence2 == null) || (paramCharSequence2.equals(str)))
        {
          int k = localSpannable.getSpanStart(localURLSpan);
          int m = localSpannable.getSpanEnd(localURLSpan);
          localSpannable.removeSpan(localURLSpan);
          localSpannable.setSpan(new SelfishUrlSpan(str, paramListener), k, m, 0);
        }
      }
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onClick(View paramView, String paramString);
  }
  
  private static class SelfishUrlSpan
    extends URLSpan
  {
    private final UrlSpanUtils.Listener listener;
    
    public SelfishUrlSpan(String paramString, UrlSpanUtils.Listener paramListener)
    {
      super();
      this.listener = paramListener;
    }
    
    public void onClick(View paramView)
    {
      this.listener.onClick(paramView, getURL());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.UrlSpanUtils
 * JD-Core Version:    0.7.0.1
 */