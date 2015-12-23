package com.google.android.volley;

import com.android.volley.VolleyError;

public abstract class DisplayMessageError
  extends VolleyError
{
  public String mDisplayErrorHtml;
  
  public DisplayMessageError() {}
  
  public DisplayMessageError(String paramString)
  {
    this.mDisplayErrorHtml = paramString;
  }
  
  public String toString()
  {
    return "DisplayErrorMessage[" + this.mDisplayErrorHtml + "]";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.DisplayMessageError
 * JD-Core Version:    0.7.0.1
 */