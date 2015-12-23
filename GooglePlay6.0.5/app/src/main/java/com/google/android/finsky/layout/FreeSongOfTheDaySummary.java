package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.previews.PreviewController;
import com.google.android.finsky.previews.SongSnippetStatusListener;
import com.google.android.finsky.previews.StatusListener;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.play.layout.PlayActionButton;

public class FreeSongOfTheDaySummary
  extends RelativeLayout
{
  public PlayActionButton mBuyButton;
  private final PreviewController mConnection = new PreviewController(this.mStatusListener);
  public TextView mCreator;
  public TextView mPlaybackLegend;
  public SongIndex mSongIndex;
  private final StatusListener mStatusListener = new SongSnippetStatusListener()
  {
    protected final void update(int paramAnonymousInt, boolean paramAnonymousBoolean)
    {
      FreeSongOfTheDaySummary.this.mSongIndex.setState(paramAnonymousInt);
      FreeSongOfTheDaySummary.this.setHighlighted(paramAnonymousBoolean);
      switch (paramAnonymousInt)
      {
      default: 
        FreeSongOfTheDaySummary.this.mPlaybackLegend.setText(null);
        return;
      case 2: 
        FreeSongOfTheDaySummary.this.mPlaybackLegend.setText(2131362801);
        return;
      }
      FreeSongOfTheDaySummary.this.mPlaybackLegend.setText(2131362802);
    }
  };
  public TextView mTitle;
  
  public FreeSongOfTheDaySummary(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FreeSongOfTheDaySummary(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setHighlighted(boolean paramBoolean)
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = ViewCompat.getPaddingStart(this);
    arrayOfInt[1] = getPaddingTop();
    arrayOfInt[2] = ViewCompat.getPaddingEnd(this);
    arrayOfInt[3] = getPaddingBottom();
    Resources localResources = getContext().getResources();
    if (paramBoolean) {
      setBackgroundColor(localResources.getColor(2131689722));
    }
    for (;;)
    {
      ViewCompat.setPaddingRelative(this, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
      return;
      setBackgroundResource(2130837960);
    }
  }
  
  protected void onDetachedFromWindow()
  {
    this.mConnection.unbind();
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitle = ((TextView)findViewById(2131755173));
    this.mCreator = ((TextView)findViewById(2131755568));
    this.mBuyButton = ((PlayActionButton)findViewById(2131755378));
    this.mSongIndex = ((SongIndex)findViewById(2131755569));
    this.mPlaybackLegend = ((TextView)findViewById(2131755572));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.FreeSongOfTheDaySummary
 * JD-Core Version:    0.7.0.1
 */