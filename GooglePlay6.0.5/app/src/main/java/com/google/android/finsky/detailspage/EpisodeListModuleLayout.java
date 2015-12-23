package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.EpisodeSnippet;
import com.google.android.finsky.layout.EpisodeSnippet.OnCollapsedStateChanged;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;
import java.util.List;

public class EpisodeListModuleLayout
  extends LinearLayout
  implements EpisodeSnippet.OnCollapsedStateChanged
{
  BitmapLoader mBitmapLoader;
  PlayActionButton mBuyButton;
  Document mCurrentSeason;
  EpisodeSelectionListener mEpisodeSelectionListener;
  LinearLayout mEpisodesContainer;
  boolean mHasBinded;
  View mInProgressSnippet;
  View mLoadingOverlay;
  NavigationManager mNavigationManager;
  PlayStoreUiElementNode mPlayStoreUiElementNode;
  Spinner mSeasonSpinner;
  List<Document> mSeasons;
  
  public EpisodeListModuleLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public EpisodeListModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public EpisodeListModuleLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void onCollapsedStateChanged$25edfb42(EpisodeSnippet paramEpisodeSnippet)
  {
    int i = this.mEpisodesContainer.getChildCount();
    for (int j = 0; j < i; j++)
    {
      EpisodeSnippet localEpisodeSnippet = (EpisodeSnippet)this.mEpisodesContainer.getChildAt(j);
      if (localEpisodeSnippet != paramEpisodeSnippet) {
        localEpisodeSnippet.collapseWithoutNotifyingListeners();
      }
    }
    if (paramEpisodeSnippet.isExpanded())
    {
      this.mEpisodeSelectionListener.onEpisodeSelected(paramEpisodeSnippet.getEpisode());
      return;
    }
    this.mEpisodeSelectionListener.onEpisodeSelected(null);
  }
  
  public void onFinishInflate()
  {
    this.mEpisodesContainer = ((LinearLayout)findViewById(2131755469));
    this.mLoadingOverlay = findViewById(2131755470);
    this.mBuyButton = ((PlayActionButton)findViewById(2131755378));
    this.mInProgressSnippet = findViewById(2131755466);
    this.mSeasonSpinner = ((Spinner)findViewById(2131755468));
    this.mSeasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        EpisodeListModuleLayout.this.mEpisodeSelectionListener.onSeasonSelected((Document)paramAnonymousAdapterView.getAdapter().getItem(paramAnonymousInt));
      }
      
      public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
    });
  }
  
  public void setSelectedSeasonIndex(int paramInt)
  {
    this.mSeasonSpinner.setSelection(paramInt);
  }
  
  public static abstract interface EpisodeSelectionListener
  {
    public abstract void onEpisodeSelected(Document paramDocument);
    
    public abstract void onSeasonSelected(Document paramDocument);
  }
  
  public final class SeasonListAdapter
    extends ArrayAdapter<Document>
  {
    public SeasonListAdapter(List<Document> paramList)
    {
      super(2130969138, localObject.toArray(new Document[localObject.size()]));
    }
    
    public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = LayoutInflater.from(getContext()).inflate(2130969138, paramViewGroup, false);
      }
      Document localDocument = (Document)getItem(paramInt);
      TextView localTextView = (TextView)paramView.findViewById(2131756164);
      localTextView.setText(((Document)getItem(paramInt)).mDocument.title.toUpperCase());
      Resources localResources = getContext().getResources();
      if (localDocument == EpisodeListModuleLayout.this.mCurrentSeason)
      {
        paramView.setBackgroundColor(localResources.getColor(2131689639));
        localTextView.setTextColor(localResources.getColor(2131689753));
        return paramView;
      }
      paramView.setBackgroundResource(2130837960);
      localTextView.setTextColor(localResources.getColor(2131689624));
      return paramView;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = LayoutInflater.from(getContext()).inflate(2130969139, paramViewGroup, false);
      }
      Document localDocument = (Document)getItem(paramInt);
      ((TextView)paramView).setText(localDocument.mDocument.title.toUpperCase());
      return paramView;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.EpisodeListModuleLayout
 * JD-Core Version:    0.7.0.1
 */