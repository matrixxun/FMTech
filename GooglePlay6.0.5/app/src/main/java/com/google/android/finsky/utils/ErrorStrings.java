package com.google.android.finsky.utils;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.android.volley.DisplayMessageError;

public final class ErrorStrings
{
  public static String get(Context paramContext, VolleyError paramVolleyError)
  {
    if ((paramVolleyError instanceof DisplayMessageError)) {
      return ((DisplayMessageError)paramVolleyError).mDisplayErrorHtml;
    }
    if ((paramVolleyError instanceof AuthFailureError)) {
      return paramContext.getString(2131361869);
    }
    if ((paramVolleyError instanceof ServerError)) {
      return paramContext.getString(2131362721);
    }
    if ((paramVolleyError instanceof TimeoutError)) {
      return paramContext.getString(2131362787);
    }
    if ((paramVolleyError instanceof NetworkError)) {
      return paramContext.getString(2131362362);
    }
    FinskyLog.d("No specific error message for: %s", new Object[] { paramVolleyError });
    return paramContext.getString(2131362362);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ErrorStrings
 * JD-Core Version:    0.7.0.1
 */