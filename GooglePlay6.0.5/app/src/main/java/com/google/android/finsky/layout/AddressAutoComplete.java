package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import com.google.android.finsky.placesapi.PlaceAutocompletePrediction;
import java.util.List;

public class AddressAutoComplete
  extends AutoCompleteTextView
  implements AdapterView.OnItemClickListener
{
  private Adapter mAdapter;
  private boolean mBlockNextSuggestion;
  private AddressSuggestionProvider mSuggestionProvider;
  
  public AddressAutoComplete(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AddressAutoComplete(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AddressAutoComplete(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void blockNextSuggestion()
  {
    try
    {
      this.mBlockNextSuggestion = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAdapter = new Adapter(getContext());
    setAdapter(this.mAdapter);
    setOnItemClickListener(this);
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mSuggestionProvider != null) {
      this.mSuggestionProvider.onSuggestionAccepted((PlaceAutocompletePrediction)this.mAdapter.mPredictions.get(paramInt));
    }
  }
  
  protected void replaceText(CharSequence paramCharSequence) {}
  
  public void setSuggestionProvider(AddressSuggestionProvider paramAddressSuggestionProvider)
  {
    try
    {
      this.mSuggestionProvider = paramAddressSuggestionProvider;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private final class Adapter
    extends ArrayAdapter<String>
    implements Filterable
  {
    private List<PlaceAutocompletePrediction> mPredictions;
    
    public Adapter(Context paramContext)
    {
      super(2130968609);
    }
    
    public final int getCount()
    {
      if (this.mPredictions != null) {
        return this.mPredictions.size();
      }
      return 0;
    }
    
    public final Filter getFilter()
    {
      return new AddressAutoComplete.PlacesFilter(AddressAutoComplete.this, (byte)0);
    }
  }
  
  private final class PlacesFilter
    extends Filter
  {
    private PlacesFilter() {}
    
    protected final Filter.FilterResults performFiltering(CharSequence paramCharSequence)
    {
      synchronized (AddressAutoComplete.this)
      {
        boolean bool = AddressAutoComplete.this.mBlockNextSuggestion;
        AddressSuggestionProvider localAddressSuggestionProvider = AddressAutoComplete.this.mSuggestionProvider;
        AddressAutoComplete.access$202$396956e4(AddressAutoComplete.this);
        Filter.FilterResults localFilterResults = null;
        if (localAddressSuggestionProvider != null)
        {
          localFilterResults = null;
          if (!bool)
          {
            List localList = localAddressSuggestionProvider.getSuggestions(paramCharSequence);
            localFilterResults = null;
            if (localList != null)
            {
              localFilterResults = new Filter.FilterResults();
              localFilterResults.values = localList;
              localFilterResults.count = localList.size();
            }
          }
        }
        return localFilterResults;
      }
    }
    
    protected final void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
    {
      if ((paramFilterResults != null) && (paramFilterResults.count > 0))
      {
        AddressAutoComplete.Adapter.access$002(AddressAutoComplete.this.mAdapter, (List)paramFilterResults.values);
        AddressAutoComplete.this.mAdapter.notifyDataSetChanged();
        return;
      }
      AddressAutoComplete.this.mAdapter.notifyDataSetInvalidated();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AddressAutoComplete
 * JD-Core Version:    0.7.0.1
 */