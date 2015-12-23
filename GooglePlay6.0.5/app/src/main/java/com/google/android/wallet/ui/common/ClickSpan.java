package com.google.android.wallet.ui.common;

import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

public class ClickSpan
  extends URLSpan
{
  private final OnClickListener mListener;
  
  private ClickSpan(String paramString, OnClickListener paramOnClickListener)
  {
    super(paramString);
    if (paramOnClickListener == null) {
      throw new IllegalStateException("listener should not be null");
    }
    this.mListener = paramOnClickListener;
  }
  
  public static void clickify(TextView paramTextView, String paramString, OnClickListener paramOnClickListener)
  {
    SpannableString localSpannableString = new SpannableString(Html.fromHtml(paramString));
    for (URLSpan localURLSpan : (URLSpan[])localSpannableString.getSpans(0, localSpannableString.length(), URLSpan.class))
    {
      String str = localURLSpan.getURL();
      int k = localSpannableString.getSpanStart(localURLSpan);
      int m = localSpannableString.getSpanEnd(localURLSpan);
      int n = localSpannableString.getSpanFlags(localURLSpan);
      localSpannableString.removeSpan(localURLSpan);
      localSpannableString.setSpan(new ClickSpan(str, paramOnClickListener), k, m, n);
    }
    paramTextView.setText(localSpannableString);
    if (!(paramTextView.getMovementMethod() instanceof LinkMovementMethod)) {
      paramTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }
  
  public void onClick(View paramView)
  {
    this.mListener.onClick(paramView, getURL());
  }
  
  public static abstract interface OnClickListener
  {
    public abstract void onClick(View paramView, String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.ClickSpan
 * JD-Core Version:    0.7.0.1
 */