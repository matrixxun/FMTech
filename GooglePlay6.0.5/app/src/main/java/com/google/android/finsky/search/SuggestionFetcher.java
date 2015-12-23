package com.google.android.finsky.search;

import android.content.Context;
import java.util.concurrent.Semaphore;

public abstract class SuggestionFetcher
{
  protected final Context mContext;
  protected final String mQuery;
  protected long mStartTimeMs;
  
  public SuggestionFetcher(String paramString, Context paramContext)
  {
    this.mQuery = paramString;
    this.mContext = paramContext;
  }
  
  public final void gatherSuggestions()
  {
    final Semaphore localSemaphore = new Semaphore(0);
    makeRequest(new OnCompleteListener()
    {
      public final void onComplete()
      {
        localSemaphore.release();
      }
    });
    try
    {
      localSemaphore.acquire();
      return;
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  protected abstract void makeRequest(OnCompleteListener paramOnCompleteListener);
  
  public final void startRequestLatencyTimer()
  {
    this.mStartTimeMs = System.currentTimeMillis();
  }
  
  protected static abstract interface OnCompleteListener
  {
    public abstract void onComplete();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.SuggestionFetcher
 * JD-Core Version:    0.7.0.1
 */