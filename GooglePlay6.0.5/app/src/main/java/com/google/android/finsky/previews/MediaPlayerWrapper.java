package com.google.android.finsky.previews;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.android.finsky.FinskyApp;
import java.io.IOException;

public final class MediaPlayerWrapper
  extends MediaPlayer
  implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener
{
  int mCurrentState = 0;
  private MediaPlayer.OnCompletionListener mOnCompletionListener = null;
  private MediaPlayer.OnErrorListener mOnErrorListener = null;
  private MediaPlayer.OnPreparedListener mOnPreparedListener = null;
  private final StatusListener mStatusListener;
  private final PowerManager.WakeLock mWakeLock;
  
  public MediaPlayerWrapper(StatusListener paramStatusListener)
  {
    super.setOnPreparedListener(this);
    super.setOnCompletionListener(this);
    super.setOnErrorListener(this);
    this.mStatusListener = paramStatusListener;
    this.mWakeLock = ((PowerManager)FinskyApp.get().getSystemService("power")).newWakeLock(6, getClass().getSimpleName());
  }
  
  private void releaseWakeLock()
  {
    if (this.mWakeLock.isHeld()) {
      this.mWakeLock.release();
    }
  }
  
  public final void onCompletion(MediaPlayer paramMediaPlayer)
  {
    this.mCurrentState = 7;
    this.mStatusListener.completed();
    if (this.mOnCompletionListener != null) {
      this.mOnCompletionListener.onCompletion(paramMediaPlayer);
    }
    if (this.mCurrentState != 10) {
      releaseWakeLock();
    }
  }
  
  public final boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    this.mCurrentState = 8;
    this.mStatusListener.error();
    if (this.mOnErrorListener != null) {
      return this.mOnErrorListener.onError(paramMediaPlayer, paramInt1, paramInt2);
    }
    return false;
  }
  
  public final void onPrepared(MediaPlayer paramMediaPlayer)
  {
    this.mCurrentState = 3;
    this.mStatusListener.prepared();
    if (this.mOnPreparedListener != null) {
      this.mOnPreparedListener.onPrepared(paramMediaPlayer);
    }
  }
  
  public final void pause()
  {
    super.pause();
    this.mCurrentState = 5;
    this.mStatusListener.paused();
    releaseWakeLock();
  }
  
  public final void prepare()
    throws IOException
  {
    super.prepare();
    this.mCurrentState = 3;
    this.mStatusListener.prepared();
  }
  
  public final void prepareAsync()
  {
    super.prepareAsync();
    this.mCurrentState = 2;
    this.mStatusListener.preparing();
  }
  
  public final void release()
  {
    super.release();
    this.mCurrentState = 9;
    releaseWakeLock();
  }
  
  public final void reset()
  {
    super.reset();
    this.mCurrentState = 0;
    this.mStatusListener.reset();
    releaseWakeLock();
  }
  
  public final void resetWhileStayingAwake()
  {
    super.reset();
    this.mCurrentState = 0;
    this.mStatusListener.reset();
  }
  
  public final void setDataSource(String paramString)
    throws IOException
  {
    super.setDataSource(paramString);
    this.mCurrentState = 1;
  }
  
  public final void setOnCompletionListener(MediaPlayer.OnCompletionListener paramOnCompletionListener)
  {
    this.mOnCompletionListener = paramOnCompletionListener;
  }
  
  public final void setOnPreparedListener(MediaPlayer.OnPreparedListener paramOnPreparedListener)
  {
    this.mOnPreparedListener = paramOnPreparedListener;
  }
  
  public final void start()
  {
    super.start();
    this.mCurrentState = 4;
    this.mStatusListener.playing();
    if (!this.mWakeLock.isHeld()) {
      this.mWakeLock.acquire();
    }
  }
  
  public final void stop()
  {
    super.stop();
    this.mCurrentState = 6;
    releaseWakeLock();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.previews.MediaPlayerWrapper
 * JD-Core Version:    0.7.0.1
 */