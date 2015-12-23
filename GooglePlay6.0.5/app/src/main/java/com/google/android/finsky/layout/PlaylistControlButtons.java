package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.previews.PreviewController;
import com.google.android.finsky.previews.StatusListener;
import com.google.android.play.layout.PlayActionButton;
import java.util.ArrayList;
import java.util.Collection;

public class PlaylistControlButtons
  extends PlayActionButton
  implements View.OnClickListener
{
  private final PreviewController mConnection = new PreviewController(this.mStatusListener);
  public Collection<Document> mDocs = new ArrayList();
  private boolean mIsPlaying = false;
  private final StatusListener mStatusListener = new StatusListener()
  {
    public final void queueChanged(int paramAnonymousInt)
    {
      PlaylistControlButtons localPlaylistControlButtons = PlaylistControlButtons.this;
      if (paramAnonymousInt > 0) {}
      for (boolean bool = true;; bool = false)
      {
        localPlaylistControlButtons.setIsPlaying(bool);
        return;
      }
    }
  };
  
  public PlaylistControlButtons(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaylistControlButtons(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setIsPlaying(boolean paramBoolean)
  {
    this.mIsPlaying = paramBoolean;
    if (this.mIsPlaying) {}
    for (int i = 2131362765;; i = 2131362556)
    {
      configure(2, i, null);
      return;
    }
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (PreviewController.getCurrentQueueSize() > 0) {}
    for (boolean bool = true;; bool = false)
    {
      setIsPlaying(bool);
      return;
    }
  }
  
  public void onClick(View paramView)
  {
    if (!this.mIsPlaying)
    {
      PreviewController.play(this.mDocs);
      setIsPlaying(true);
      return;
    }
    PreviewController.skip();
  }
  
  protected void onDetachedFromWindow()
  {
    this.mConnection.unbind();
    super.onDetachedFromWindow();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.PlaylistControlButtons
 * JD-Core Version:    0.7.0.1
 */