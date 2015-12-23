package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.ContentFiltersUtils;
import com.google.android.finsky.config.ContentFiltersUtils.ContentFilterSelection;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.ContentFilters.FilterChoice;
import com.google.android.finsky.protos.ContentFilters.FilterRange;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.FifeImageView;

public final class ContentFilterChoiceAdapter
  extends BaseAdapter
{
  private final Context mContext;
  private final ContentFilters.FilterRange mFilterRange;
  private final LayoutInflater mInflater;
  private final ContentFiltersUtils.ContentFilterSelection[] mSelections;
  
  public ContentFilterChoiceAdapter(Context paramContext, ContentFiltersUtils.ContentFilterSelection[] paramArrayOfContentFilterSelection, ContentFilters.FilterRange paramFilterRange)
  {
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mSelections = paramArrayOfContentFilterSelection;
    this.mFilterRange = paramFilterRange;
  }
  
  public final int getCount()
  {
    return this.mFilterRange.filterChoice.length;
  }
  
  public final Object getItem(int paramInt)
  {
    return this.mFilterRange.filterChoice[paramInt];
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ContentFilters.FilterChoice localFilterChoice = this.mFilterRange.filterChoice[paramInt];
    View localView = paramView;
    if (paramView == null) {
      localView = this.mInflater.inflate(2130968668, paramViewGroup, false);
    }
    TextView localTextView = (TextView)localView.findViewById(2131755351);
    localTextView.setText(localFilterChoice.label);
    FifeImageView localFifeImageView = (FifeImageView)localView.findViewById(2131755352);
    Common.Image localImage = localFilterChoice.imageFife;
    int i;
    if (localImage != null)
    {
      ThumbnailUtils.adjustWidthFromImageMetadata(localFifeImageView, localImage);
      localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      localFifeImageView.setVisibility(0);
      i = ContentFiltersUtils.findSelection(this.mFilterRange, this.mSelections);
      if (i < 0) {
        break label213;
      }
    }
    label213:
    for (ContentFiltersUtils.ContentFilterSelection localContentFilterSelection = this.mSelections[i];; localContentFilterSelection = null)
    {
      if (!ContentFiltersUtils.isFilterChoiceSelected(localFilterChoice, localContentFilterSelection)) {
        break label219;
      }
      localView.setBackgroundColor(this.mContext.getResources().getColor(2131689486));
      Context localContext = this.mContext;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localFilterChoice.label;
      localTextView.setContentDescription(localContext.getString(2131361816, arrayOfObject));
      return localView;
      localFifeImageView.setVisibility(8);
      break;
    }
    label219:
    localView.setBackgroundColor(0);
    localTextView.setContentDescription(localFilterChoice.label);
    return localView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.ContentFilterChoiceAdapter
 * JD-Core Version:    0.7.0.1
 */