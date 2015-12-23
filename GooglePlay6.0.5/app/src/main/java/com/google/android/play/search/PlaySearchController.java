package com.google.android.play.search;

import android.view.View.OnClickListener;
import java.util.ArrayList;

public final class PlaySearchController
{
  String mCurrentQuery = "";
  int mCurrentSearchMode;
  final ArrayList<PlaySearchListener> mListeners = new ArrayList();
  View.OnClickListener mNavigationClickListener;
  int mSteadyStateMode = 1;
  
  public final void addPlaySearchListener(PlaySearchListener paramPlaySearchListener)
  {
    this.mListeners.add(paramPlaySearchListener);
  }
  
  public final boolean isInSteadyStateMode()
  {
    return this.mCurrentSearchMode == this.mSteadyStateMode;
  }
  
  final void notifyQueryChangeInternal(boolean paramBoolean)
  {
    for (int i = -1 + this.mListeners.size(); i >= 0; i--) {
      ((PlaySearchListener)this.mListeners.get(i)).onQueryChanged(this.mCurrentQuery, paramBoolean);
    }
  }
  
  public final void onSearch()
  {
    for (int i = -1 + this.mListeners.size(); i >= 0; i--) {
      ((PlaySearchListener)this.mListeners.get(i)).onSearch(this.mCurrentQuery);
    }
  }
  
  public final void removePlaySearchListener(PlaySearchListener paramPlaySearchListener)
  {
    this.mListeners.remove(paramPlaySearchListener);
  }
  
  public final void setMode(int paramInt)
  {
    if (this.mCurrentSearchMode == paramInt) {}
    for (;;)
    {
      return;
      this.mCurrentSearchMode = paramInt;
      for (int i = -1 + this.mListeners.size(); i >= 0; i--) {
        ((PlaySearchListener)this.mListeners.get(i)).onModeChanged(paramInt);
      }
    }
  }
  
  final void setQueryInternal(String paramString, boolean paramBoolean)
  {
    if (this.mCurrentQuery.equals(paramString)) {
      return;
    }
    this.mCurrentQuery = paramString;
    notifyQueryChangeInternal(paramBoolean);
  }
  
  public final void switchToSteadyStateMode()
  {
    setMode(this.mSteadyStateMode);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchController
 * JD-Core Version:    0.7.0.1
 */