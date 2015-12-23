package com.google.android.finsky.previews;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.net.Uri.Builder;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.SkyjamJsonObjectRequest;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public final class PreviewPlayer
{
  private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
  {
    public final void onAudioFocusChange(int paramAnonymousInt)
    {
      PreviewPlayer.this.mAudioFocusState = paramAnonymousInt;
    }
  };
  int mAudioFocusState = -1;
  AudioManager mAudioManager = null;
  final MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener()
  {
    public final void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
    {
      PreviewPlayer.this.mListenerProxy.completed();
      PreviewPlayer.this.mStatusListeners.remove(PreviewPlayer.this.mCurrentTrack);
      if (PreviewPlayer.this.playNextTrack()) {
        PreviewPlayer.this.mPlayer.mCurrentState = 10;
      }
    }
  };
  SongDetails mCurrentTrack = null;
  JsonObjectRequest mCurrentUnrollRequest = null;
  private final Response.Listener<JSONObject> mJsonListener = new Response.Listener() {};
  final StatusListener mListenerProxy = new StatusListener()
  {
    public final void completed()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).completed();
      }
    }
    
    public final void error()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).error();
      }
    }
    
    public final void paused()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).paused();
      }
    }
    
    public final void playing()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).playing();
      }
    }
    
    public final void prepared()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).prepared();
      }
    }
    
    public final void preparing()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).preparing();
      }
    }
    
    public final void queueChanged(int paramAnonymousInt)
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).queueChanged(paramAnonymousInt);
      }
    }
    
    public final void reset()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).reset();
      }
    }
    
    public final void unrolling()
    {
      Iterator localIterator = PreviewPlayer.this.mStatusListeners.iterator();
      while (localIterator.hasNext()) {
        ((StatusListener)localIterator.next()).unrolling();
      }
    }
  };
  final MediaPlayerWrapper mPlayer = new MediaPlayerWrapper(this.mListenerProxy);
  final MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener()
  {
    public final void onPrepared(MediaPlayer paramAnonymousMediaPlayer)
    {
      PreviewPlayer.this.mPlayer.start();
    }
  };
  final Queue<SongDetails> mQueue = new LinkedList();
  RequestQueue mRequestQueue = null;
  final List<StatusListener> mStatusListeners = new ArrayList();
  private final Response.ErrorListener mUnrollErrorListener = new Response.ErrorListener()
  {
    public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramAnonymousVolleyError.getCause();
      FinskyLog.e("Unable to load JSON: %s", arrayOfObject);
      PreviewPlayer.this.mCurrentUnrollRequest = null;
      PreviewPlayer.access$200(PreviewPlayer.this);
    }
  };
  
  static String setModeToStreaming(String paramString)
  {
    ArrayList localArrayList = Lists.newArrayList(URLEncodedUtils.parse(URI.create(paramString), "UTF-8"));
    NameValuePair localNameValuePair;
    for (int i = 0;; i++)
    {
      int j = localArrayList.size();
      localNameValuePair = null;
      if (i >= j) {
        break;
      }
      localNameValuePair = (NameValuePair)localArrayList.get(i);
      if ("mode".equals(localNameValuePair.getName())) {
        break;
      }
    }
    if (localNameValuePair != null) {
      localArrayList.remove(localNameValuePair);
    }
    localArrayList.add(new BasicNameValuePair("mode", "streaming"));
    String str = URLEncodedUtils.format(localArrayList, "UTF-8");
    return Uri.parse(paramString).buildUpon().encodedQuery(str).build().toString();
  }
  
  final void notifyQueueChanged()
  {
    this.mListenerProxy.queueChanged(this.mQueue.size());
  }
  
  final boolean playNextTrack()
  {
    Utils.ensureOnMainThread();
    boolean bool = this.mQueue.isEmpty();
    if ((!bool) && (this.mAudioFocusState != 1)) {
      this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 1);
    }
    for (this.mAudioFocusState = 1;; this.mAudioFocusState = -1)
    {
      do
      {
        this.mPlayer.resetWhileStayingAwake();
        if (!this.mQueue.isEmpty()) {
          break;
        }
        this.mCurrentTrack = null;
        return false;
      } while ((!bool) || (this.mAudioFocusState == -1));
      this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
    }
    this.mListenerProxy.completed();
    if (this.mCurrentUnrollRequest != null) {
      this.mCurrentUnrollRequest.cancel();
    }
    this.mCurrentTrack = ((SongDetails)this.mQueue.remove());
    notifyQueueChanged();
    this.mCurrentUnrollRequest = new SkyjamJsonObjectRequest(setModeToStreaming(this.mCurrentTrack.previewUrl), this.mJsonListener, this.mUnrollErrorListener);
    FinskyApp.drain(this.mRequestQueue);
    this.mRequestQueue.add(this.mCurrentUnrollRequest);
    this.mListenerProxy.unrolling();
    return true;
  }
  
  public final void reset()
  {
    this.mListenerProxy.reset();
    this.mPlayer.reset();
    this.mCurrentTrack = null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.previews.PreviewPlayer
 * JD-Core Version:    0.7.0.1
 */