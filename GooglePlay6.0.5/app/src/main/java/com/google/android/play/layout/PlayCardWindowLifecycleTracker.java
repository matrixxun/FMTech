package com.google.android.play.layout;

import java.util.ArrayList;
import java.util.List;

public final class PlayCardWindowLifecycleTracker
{
  private static PlayCardWindowLifecycleTracker INSTANCE = new PlayCardWindowLifecycleTracker();
  private List<CardLifecycleListener> mListenerList = new ArrayList();
  
  public static PlayCardWindowLifecycleTracker getInstance()
  {
    return INSTANCE;
  }
  
  final void cardAttachedToWindow(PlayCardViewBase paramPlayCardViewBase)
  {
    try
    {
      for (int i = -1 + this.mListenerList.size(); i >= 0; i--) {
        ((CardLifecycleListener)this.mListenerList.get(i)).onCardAttachedToWindow(paramPlayCardViewBase);
      }
      return;
    }
    finally {}
  }
  
  final void cardDetachedFromWindow(PlayCardViewBase paramPlayCardViewBase)
  {
    try
    {
      for (int i = -1 + this.mListenerList.size(); i >= 0; i--) {
        ((CardLifecycleListener)this.mListenerList.get(i)).onCardDetachedFromWindow(paramPlayCardViewBase);
      }
      return;
    }
    finally {}
  }
  
  public final void registerListener(CardLifecycleListener paramCardLifecycleListener)
  {
    try
    {
      this.mListenerList.add(paramCardLifecycleListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static abstract interface CardLifecycleListener
  {
    public abstract void onCardAttachedToWindow(PlayCardViewBase paramPlayCardViewBase);
    
    public abstract void onCardDetachedFromWindow(PlayCardViewBase paramPlayCardViewBase);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardWindowLifecycleTracker
 * JD-Core Version:    0.7.0.1
 */