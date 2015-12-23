package com.google.android.finsky.previews;

public abstract class SongSnippetStatusListener
  extends StatusListener
{
  public final void completed()
  {
    update(5, false);
  }
  
  public final void error()
  {
    update(4, false);
  }
  
  public final void paused()
  {
    update(3, true);
  }
  
  public final void playing()
  {
    update(2, true);
  }
  
  public final void prepared()
  {
    update(2, true);
  }
  
  public final void preparing()
  {
    update(1, true);
  }
  
  public final void unrolling()
  {
    update(1, true);
  }
  
  public abstract void update(int paramInt, boolean paramBoolean);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.previews.SongSnippetStatusListener
 * JD-Core Version:    0.7.0.1
 */