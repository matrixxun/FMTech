package com.google.android.finsky.layout;

import com.google.android.finsky.placesapi.PlaceAutocompletePrediction;
import java.util.List;

public abstract interface AddressSuggestionProvider
{
  public abstract List<PlaceAutocompletePrediction> getSuggestions(CharSequence paramCharSequence);
  
  public abstract void onSuggestionAccepted(PlaceAutocompletePrediction paramPlaceAutocompletePrediction);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AddressSuggestionProvider
 * JD-Core Version:    0.7.0.1
 */