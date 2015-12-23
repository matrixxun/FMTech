package com.google.android.finsky.utils;

import com.google.android.play.layout.PlayCardViewBase;

public final class PlayCardCustomizerRepository<T extends PlayCardViewBase>
{
  private static PlayCardCustomizerRepository<PlayCardViewBase> sInstance = new PlayCardCustomizerRepository();
  PlayCardCustomizer<? extends T>[] mCustomizers = new PlayCardCustomizer[22];
  final PlayCardCustomizer<? extends T> mDefaultCustomizer = new PlayCardCustomizer();
  
  public static PlayCardCustomizerRepository<PlayCardViewBase> getInstance()
  {
    return sInstance;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PlayCardCustomizerRepository
 * JD-Core Version:    0.7.0.1
 */