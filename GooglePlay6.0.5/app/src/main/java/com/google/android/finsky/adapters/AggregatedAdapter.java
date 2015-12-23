package com.google.android.finsky.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;

public final class AggregatedAdapter<T extends BaseAdapter>
  extends BaseAdapter
{
  public T[] mAdapters;
  private boolean mCachedAllItemsEnabled = true;
  private int mCachedCount = 0;
  private boolean mCachedHasStableIds = true;
  private ArrayList<IndexTranslation> mCachedTranslations;
  
  public AggregatedAdapter(T[] paramArrayOfT)
  {
    this.mAdapters = paramArrayOfT;
  }
  
  private void refreshCachedData()
  {
    for (;;)
    {
      int k;
      try
      {
        int i = this.mAdapters.length;
        int j = 0;
        this.mCachedAllItemsEnabled = true;
        this.mCachedHasStableIds = true;
        this.mCachedTranslations = new ArrayList(i * 3);
        k = 0;
        if (k < i)
        {
          BaseAdapter localBaseAdapter = this.mAdapters[k];
          int m = localBaseAdapter.getCount();
          j += m;
          if ((this.mCachedAllItemsEnabled) && (localBaseAdapter.areAllItemsEnabled()))
          {
            bool1 = true;
            this.mCachedAllItemsEnabled = bool1;
            if ((!this.mCachedHasStableIds) || (!localBaseAdapter.hasStableIds())) {
              break label177;
            }
            bool2 = true;
            this.mCachedHasStableIds = bool2;
            int n = 0;
            if (n >= m) {
              break label183;
            }
            IndexTranslation localIndexTranslation = new IndexTranslation(localBaseAdapter, n, (byte)0);
            this.mCachedTranslations.add(localIndexTranslation);
            n++;
            continue;
          }
        }
        else
        {
          this.mCachedCount = j;
          return;
        }
      }
      finally {}
      boolean bool1 = false;
      continue;
      label177:
      boolean bool2 = false;
      continue;
      label183:
      k++;
    }
  }
  
  private IndexTranslation translate(int paramInt)
  {
    return (IndexTranslation)this.mCachedTranslations.get(paramInt);
  }
  
  public final boolean areAllItemsEnabled()
  {
    return this.mCachedAllItemsEnabled;
  }
  
  public final void dumpState()
  {
    FinskyLog.d("****** AGGREGATED ADAPTER START ******", new Object[0]);
    StringBuilder localStringBuilder1 = new StringBuilder("Total items: ");
    localStringBuilder1.append(getCount());
    localStringBuilder1.append(" [ ");
    BaseAdapter[] arrayOfBaseAdapter = this.mAdapters;
    int i = arrayOfBaseAdapter.length;
    for (int j = 0; j < i; j++)
    {
      localStringBuilder1.append(arrayOfBaseAdapter[j].getCount());
      localStringBuilder1.append(" ");
    }
    localStringBuilder1.append("]");
    FinskyLog.d(localStringBuilder1.toString(), new Object[0]);
    StringBuilder localStringBuilder2 = new StringBuilder("Index translation: ");
    for (int k = 0; k < getCount(); k++)
    {
      localStringBuilder2.append(k);
      localStringBuilder2.append(":");
      localStringBuilder2.append(getItemViewType(k));
      localStringBuilder2.append(" ");
    }
    FinskyLog.d(localStringBuilder2.toString(), new Object[0]);
    FinskyLog.d("****** AGGREGATED ADAPTER  END  ******", new Object[0]);
  }
  
  public final int getCount()
  {
    return this.mCachedCount;
  }
  
  public final Object getItem(int paramInt)
  {
    IndexTranslation localIndexTranslation = translate(paramInt);
    return localIndexTranslation.targetAdapter.getItem(localIndexTranslation.translatedIndex);
  }
  
  public final long getItemId(int paramInt)
  {
    IndexTranslation localIndexTranslation = translate(paramInt);
    return localIndexTranslation.targetAdapter.getItemId(localIndexTranslation.translatedIndex);
  }
  
  public final int getItemViewType(int paramInt)
  {
    IndexTranslation localIndexTranslation = translate(paramInt);
    return localIndexTranslation.targetAdapter.getItemViewType(localIndexTranslation.translatedIndex);
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    IndexTranslation localIndexTranslation = translate(paramInt);
    return localIndexTranslation.targetAdapter.getView(localIndexTranslation.translatedIndex, paramView, paramViewGroup);
  }
  
  public final boolean hasStableIds()
  {
    return this.mCachedHasStableIds;
  }
  
  public final boolean isEmpty()
  {
    return this.mCachedCount == 0;
  }
  
  public final boolean isEnabled(int paramInt)
  {
    IndexTranslation localIndexTranslation = translate(paramInt);
    return localIndexTranslation.targetAdapter.isEnabled(localIndexTranslation.translatedIndex);
  }
  
  public final void notifyDataSetChanged()
  {
    refreshCachedData();
  }
  
  public final void notifyDataSetInvalidated()
  {
    refreshCachedData();
  }
  
  private static final class IndexTranslation
  {
    ListAdapter targetAdapter;
    int translatedIndex;
    
    private IndexTranslation(ListAdapter paramListAdapter, int paramInt)
    {
      this.targetAdapter = paramListAdapter;
      this.translatedIndex = paramInt;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.AggregatedAdapter
 * JD-Core Version:    0.7.0.1
 */