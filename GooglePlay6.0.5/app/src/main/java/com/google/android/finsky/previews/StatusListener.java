package com.google.android.finsky.previews;

public abstract class StatusListener
{
  public void completed() {}
  
  public void error() {}
  
  public void paused() {}
  
  public void playing() {}
  
  public void prepared() {}
  
  public void preparing() {}
  
  public void queueChanged(int paramInt) {}
  
  public void reset() {}
  
  public void unrolling() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.previews.StatusListener
 * JD-Core Version:    0.7.0.1
 */