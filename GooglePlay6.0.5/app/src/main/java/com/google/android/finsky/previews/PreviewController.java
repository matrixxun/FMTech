package com.google.android.finsky.previews;

import android.media.AudioManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.text.TextUtils;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.finsky.utils.Utils;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public final class PreviewController
{
  private static final PreviewPlayer mPlayer = new PreviewPlayer();
  private final StatusListener mStatusListener;
  
  public PreviewController(StatusListener paramStatusListener)
  {
    PreviewPlayer localPreviewPlayer = mPlayer;
    if (localPreviewPlayer.mRequestQueue == null) {
      localPreviewPlayer.mRequestQueue = FinskyApp.get().mRequestQueue;
    }
    if (localPreviewPlayer.mAudioManager == null) {
      localPreviewPlayer.mAudioManager = ((AudioManager)FinskyApp.get().getSystemService("audio"));
    }
    localPreviewPlayer.mPlayer.setOnPreparedListener(localPreviewPlayer.mPreparedListener);
    localPreviewPlayer.mPlayer.setOnCompletionListener(localPreviewPlayer.mCompletionListener);
    this.mStatusListener = paramStatusListener;
    mPlayer.mStatusListeners.add(paramStatusListener);
  }
  
  public static int getCurrentQueueSize()
  {
    return mPlayer.mQueue.size();
  }
  
  public static SongDetails getCurrentTrack()
  {
    return mPlayer.mCurrentTrack;
  }
  
  public static void getStatusUpdate(StatusListener paramStatusListener)
  {
    switch (mPlayer.mPlayer.mCurrentState)
    {
    case 6: 
    default: 
      return;
    case 2: 
      paramStatusListener.preparing();
      return;
    case 3: 
      paramStatusListener.prepared();
      return;
    case 4: 
      paramStatusListener.playing();
      return;
    case 5: 
      paramStatusListener.paused();
      return;
    case 7: 
      paramStatusListener.completed();
      return;
    }
    paramStatusListener.error();
  }
  
  public static void play(Collection<Document> paramCollection)
  {
    PreviewPlayer localPreviewPlayer = mPlayer;
    Utils.ensureOnMainThread();
    localPreviewPlayer.mQueue.clear();
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      SongDetails localSongDetails = ((Document)localIterator.next()).getSongDetails();
      if ((localSongDetails != null) && (localSongDetails.hasPreviewUrl) && (!TextUtils.isEmpty(localSongDetails.previewUrl))) {
        localPreviewPlayer.mQueue.add(localSongDetails);
      }
    }
    localPreviewPlayer.notifyQueueChanged();
    localPreviewPlayer.playNextTrack();
  }
  
  public static void reset()
  {
    mPlayer.reset();
  }
  
  public static void setupOnBackStackChangedListener(NavigationManager paramNavigationManager)
  {
    FragmentManager.OnBackStackChangedListener local1 = new FragmentManager.OnBackStackChangedListener()
    {
      public final void onBackStackChanged()
      {
        PreviewPlayer localPreviewPlayer1 = PreviewController.mPlayer;
        Utils.ensureOnMainThread();
        localPreviewPlayer1.mQueue.clear();
        PreviewPlayer localPreviewPlayer2 = PreviewController.mPlayer;
        switch (localPreviewPlayer2.mPlayer.mCurrentState)
        {
        }
        for (;;)
        {
          PreviewController.mPlayer.reset();
          this.val$navigationManager.removeOnBackStackChangedListener(this);
          return;
          localPreviewPlayer2.mPlayer.stop();
        }
      }
    };
    paramNavigationManager.removeOnBackStackChangedListener(local1);
    paramNavigationManager.addOnBackStackChangedListener(local1);
  }
  
  public static void skip()
  {
    mPlayer.playNextTrack();
  }
  
  public static void togglePlayback(SongDetails paramSongDetails)
  {
    PreviewPlayer localPreviewPlayer = mPlayer;
    if (localPreviewPlayer.mCurrentUnrollRequest != null) {
      localPreviewPlayer.mCurrentUnrollRequest.cancel();
    }
    int i = localPreviewPlayer.mPlayer.mCurrentState;
    boolean bool;
    if ((localPreviewPlayer.mCurrentTrack == null) && (paramSongDetails == null))
    {
      bool = true;
      if (!bool) {
        break label120;
      }
      if (i != 4) {
        break label90;
      }
      localPreviewPlayer.mPlayer.pause();
    }
    label90:
    do
    {
      return;
      if ((localPreviewPlayer.mCurrentTrack == null) || (paramSongDetails == null))
      {
        bool = false;
        break;
      }
      bool = paramSongDetails.previewUrl.equals(localPreviewPlayer.mCurrentTrack.previewUrl);
      break;
      if (i == 5)
      {
        localPreviewPlayer.mPlayer.start();
        return;
      }
    } while (i != 2);
    localPreviewPlayer.mListenerProxy.completed();
    localPreviewPlayer.reset();
    return;
    label120:
    Utils.ensureOnMainThread();
    localPreviewPlayer.mQueue.clear();
    localPreviewPlayer.mQueue.add(paramSongDetails);
    localPreviewPlayer.notifyQueueChanged();
    localPreviewPlayer.playNextTrack();
  }
  
  public final void unbind()
  {
    PreviewPlayer localPreviewPlayer = mPlayer;
    StatusListener localStatusListener = this.mStatusListener;
    localPreviewPlayer.mStatusListeners.remove(localStatusListener);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.previews.PreviewController
 * JD-Core Version:    0.7.0.1
 */