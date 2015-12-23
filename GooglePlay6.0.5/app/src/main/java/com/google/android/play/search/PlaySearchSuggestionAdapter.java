package com.google.android.play.search;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.List;

public final class PlaySearchSuggestionAdapter
  extends RecyclerView.Adapter<ViewHolder>
{
  BitmapLoader mBitmapLoader;
  PlaySearchController mController;
  private final List<PlaySearchSuggestionModel> mItems = new ArrayList();
  
  public final int getItemCount()
  {
    return this.mItems.size();
  }
  
  public final void updateData(List<? extends PlaySearchSuggestionModel> paramList)
  {
    this.mItems.clear();
    this.mItems.addAll(paramList);
    this.mObservable.notifyChanged();
  }
  
  public static final class ViewHolder
    extends RecyclerView.ViewHolder
  {
    public PlaySearchOneSuggestion oneSuggestionView;
    
    public ViewHolder(PlaySearchOneSuggestion paramPlaySearchOneSuggestion)
    {
      super();
      this.oneSuggestionView = paramPlaySearchOneSuggestion;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchSuggestionAdapter
 * JD-Core Version:    0.7.0.1
 */