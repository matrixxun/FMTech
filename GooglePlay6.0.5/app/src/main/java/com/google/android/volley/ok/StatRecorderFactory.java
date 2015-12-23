package com.google.android.volley.ok;

import org.apache.http.HttpEntity;

public abstract interface StatRecorderFactory
{
  public abstract StatRecorder createStatRecorder$6c5cba1d();
  
  public static abstract interface StatRecorder
  {
    public abstract HttpEntity recordStats$58b453ee(String paramString, HttpEntity paramHttpEntity);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.volley.ok.StatRecorderFactory
 * JD-Core Version:    0.7.0.1
 */