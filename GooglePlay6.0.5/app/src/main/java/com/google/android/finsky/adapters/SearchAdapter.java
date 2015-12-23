package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.provider.SearchRecentSuggestions;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SearchFragment;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeSearch;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.SearchResultCorrectionLayout;
import com.google.android.finsky.layout.SuggestionBarLayout;
import com.google.android.finsky.layout.play.Identifiable;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Search.RelatedSearch;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.image.BitmapLoader;

public final class SearchAdapter
  extends CardRecyclerViewAdapterV2<DfeSearch>
{
  private final boolean mHasSuggestedQuery;
  private boolean mIsFamilySafeSearchModeEnabled;
  
  public SearchAdapter(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, MultiDfeList<DfeSearch> paramMultiDfeList, boolean paramBoolean1, boolean paramBoolean2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramContext, paramDfeApi, paramNavigationManager, paramBitmapLoader, paramDfeToc, paramClientMutationCache, paramMultiDfeList, null, null, paramBoolean1, false, 2, paramPlayStoreUiElementNode, null);
    this.mIsFamilySafeSearchModeEnabled = paramBoolean2;
    if (!TextUtils.isEmpty(((DfeSearch)this.mMultiDfeList.mContainerList).mSuggestedQuery)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mHasSuggestedQuery = bool;
      if ((this.mFooterMode == 0) && (hasFamilySafeSearchResults())) {
        this.mFooterMode = 3;
      }
      return;
    }
  }
  
  private void bindSearchCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    int i = 1;
    Document localDocument = this.mMultiDfeList.getTopLevelItem(paramInt);
    int j;
    int k;
    if ((this.mUseMiniCards) && (FinskyApp.get().getExperiments().isEnabled(12604267L)))
    {
      j = i;
      if (j == 0) {
        break label79;
      }
      k = 2130968939;
      label46:
      if (localDocument.getChildCount() != i) {
        break label86;
      }
    }
    for (;;)
    {
      if (i != 0) {
        k = 2130968935;
      }
      bindCluster(localDocument, paramViewHolder, k);
      return;
      j = 0;
      break;
      label79:
      k = 2130968954;
      break label46;
      label86:
      i = 0;
    }
  }
  
  private boolean hasFamilySafeSearchResults()
  {
    DfeSearch localDfeSearch = (DfeSearch)this.mMultiDfeList.mContainerList;
    return (this.mIsFamilySafeSearchModeEnabled) && (localDfeSearch != null) && (localDfeSearch.getCount() > 0);
  }
  
  protected final void bindGenericCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindGenericCluster(paramInt, paramViewHolder);
      return;
    }
    bindSearchCluster(paramInt, paramViewHolder);
  }
  
  protected final void bindOrderedCluster(int paramInt, RecyclerView.ViewHolder paramViewHolder)
  {
    if (!this.mIsUsingScrollableClusters)
    {
      super.bindOrderedCluster(paramInt, paramViewHolder);
      return;
    }
    bindSearchCluster(paramInt, paramViewHolder);
  }
  
  protected final void bindSecondaryHeader(View paramView)
  {
    int i = paramView.getResources().getDimensionPixelSize(2131493300) + this.mCardContentPadding;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    localMarginLayoutParams.leftMargin = i;
    localMarginLayoutParams.rightMargin = i;
  }
  
  protected final void bindSpinnerData(Identifiable paramIdentifiable, final Spinner paramSpinner, View paramView)
  {
    paramIdentifiable.setIdentifier("corpus_selector");
    final Search.RelatedSearch[] arrayOfRelatedSearch = ((DfeSearch)this.mMultiDfeList.mContainerList).getRelatedSearches();
    String[] arrayOfString = new String[arrayOfRelatedSearch.length];
    int i = 0;
    int j = 3;
    for (int k = 0; k < arrayOfString.length; k++)
    {
      Search.RelatedSearch localRelatedSearch = arrayOfRelatedSearch[k];
      arrayOfString[k] = localRelatedSearch.header;
      if ((i == 0) && (localRelatedSearch.current))
      {
        i = k;
        j = localRelatedSearch.backendId;
      }
    }
    paramSpinner.setBackgroundResource(CorpusResourceUtils.getCorpusSpinnerDrawable(j));
    paramView.setBackgroundColor(CorpusResourceUtils.getPrimaryColor(this.mContext, j));
    int m = paramSpinner.getResources().getDimensionPixelSize(2131493505) + this.mCardContentPadding;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramSpinner.getLayoutParams();
    localMarginLayoutParams.leftMargin = m;
    localMarginLayoutParams.rightMargin = m;
    paramSpinner.setLayoutParams(localMarginLayoutParams);
    paramSpinner.setAdapter(new StringBasedSpinnerAdapter(this.mContext, arrayOfString));
    paramSpinner.setSelection(i);
    paramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Search.RelatedSearch localRelatedSearch = arrayOfRelatedSearch[paramAnonymousInt];
        if ((SearchAdapter.this.mNavigationManager.getCurrentPageType() == 7) && (paramSpinner.getVisibility() == 0) && (!localRelatedSearch.current)) {
          SearchAdapter.this.mNavigationManager.goToSearch(localRelatedSearch.searchUrl, ((DfeSearch)SearchAdapter.this.mMultiDfeList.mContainerList).mQuery, localRelatedSearch.backendId, SearchAdapter.this.mParentNode);
        }
      }
      
      public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
    });
  }
  
  protected final View createSecondaryHeader(ViewGroup paramViewGroup)
  {
    return inflate(2130968744, paramViewGroup, false);
  }
  
  protected final View.OnClickListener getClusterClickListener(Document paramDocument, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    boolean bool = NavigationManager.hasClickListener(paramDocument);
    View.OnClickListener localOnClickListener = null;
    if (bool) {
      localOnClickListener = this.mNavigationManager.getClickListener(paramDocument, paramPlayStoreUiElementNode, ((DfeSearch)this.mMultiDfeList.mContainerList).mQuery, paramDocument.mDocument.backendId, null);
    }
    return localOnClickListener;
  }
  
  protected final int getDefaultFooterMode()
  {
    if (hasFamilySafeSearchResults()) {
      return 3;
    }
    return 0;
  }
  
  protected final int getDisplayIndex(int paramInt)
  {
    return -1;
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (this.mHasSuggestedQuery)
    {
      if (paramInt == 1) {
        return 40;
      }
      if (paramInt > 1) {
        paramInt--;
      }
    }
    return super.getItemViewType(paramInt);
  }
  
  protected final int getPrependedRowsCount()
  {
    int i = super.getPrependedRowsCount();
    if (this.mHasSuggestedQuery) {}
    for (int j = 1;; j = 0) {
      return j + i;
    }
  }
  
  protected final boolean hasFilters()
  {
    if (this.mH2oIsEnabled) {
      return false;
    }
    return ((DfeSearch)this.mMultiDfeList.mContainerList).getRelatedSearches().length > 0;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    if (paramViewHolder.mItemViewType == 40)
    {
      SearchResultCorrectionLayout localSearchResultCorrectionLayout = (SearchResultCorrectionLayout)paramViewHolder.itemView;
      DfeSearch localDfeSearch = (DfeSearch)this.mMultiDfeList.mContainerList;
      final String str1 = localDfeSearch.mQuery;
      final String str2 = localDfeSearch.mSuggestedQuery;
      boolean bool = localDfeSearch.mFullPageReplaced;
      localSearchResultCorrectionLayout.mFullPageReplaced = bool;
      if (localSearchResultCorrectionLayout.mFullPageReplaced)
      {
        localSearchResultCorrectionLayout.mSuggestionLine.setDisplayLine(localSearchResultCorrectionLayout.getContext().getString(2131362182), str2);
        localSearchResultCorrectionLayout.mReplacedLine.setDisplayLine(localSearchResultCorrectionLayout.getContext().getString(2131362714), str1);
        localSearchResultCorrectionLayout.mSuggestionLine.setVisibility(0);
        localSearchResultCorrectionLayout.mSuggestionLine.setVisibility(0);
        if (!bool) {
          break label193;
        }
        localSearchResultCorrectionLayout.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().mRecentSuggestions.saveRecentQuery(str1, null);
            NavigationManager localNavigationManager = SearchAdapter.this.mNavigationManager;
            String str1 = str1;
            int i = ((DfeSearch)SearchAdapter.this.mMultiDfeList.mContainerList).getBackendId();
            if (localNavigationManager.canNavigate())
            {
              String str2 = DfeUtils.formSearchUrlWithFprDisabled(str1, i);
              localNavigationManager.showPage(7, str2, SearchFragment.newInstance(str1, str2, i), false, new View[0]);
            }
          }
        });
        localSearchResultCorrectionLayout.setClickable(true);
        localSearchResultCorrectionLayout.setFocusable(true);
      }
      for (;;)
      {
        localSearchResultCorrectionLayout.setIdentifier("suggestion_header");
        return;
        localSearchResultCorrectionLayout.mSuggestionLine.setDisplayLine(localSearchResultCorrectionLayout.getContext().getString(2131362779), str2);
        localSearchResultCorrectionLayout.mSuggestionLine.setVisibility(0);
        localSearchResultCorrectionLayout.mReplacedLine.setVisibility(8);
        break;
        label193:
        localSearchResultCorrectionLayout.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().mRecentSuggestions.saveRecentQuery(str2, null);
            NavigationManager localNavigationManager = SearchAdapter.this.mNavigationManager;
            String str1 = str2;
            int i = ((DfeSearch)SearchAdapter.this.mMultiDfeList.mContainerList).getBackendId();
            if (localNavigationManager.canNavigate())
            {
              String str2 = DfeUtils.formSearchUrl(str1, i);
              localNavigationManager.showPage(7, str2, SearchFragment.newInstance(str1, str2, i), false, new View[0]);
            }
          }
        });
        localSearchResultCorrectionLayout.setClickable(true);
        localSearchResultCorrectionLayout.setFocusable(true);
      }
    }
    super.onBindViewHolder(paramViewHolder, paramInt);
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramInt == 40) {
      return new PlayRecyclerView.ViewHolder(inflate(2130969091, paramViewGroup, false));
    }
    return super.onCreateViewHolder(paramViewGroup, paramInt);
  }
  
  protected final void setFooterMode(int paramInt)
  {
    if ((paramInt == 0) && (hasFamilySafeSearchResults())) {
      paramInt = 3;
    }
    super.setFooterMode(paramInt);
  }
  
  protected final boolean shouldShowSecondaryHeader()
  {
    return hasFamilySafeSearchResults();
  }
  
  private static final class StringBasedSpinnerAdapter
    extends ArrayAdapter<String>
  {
    public StringBasedSpinnerAdapter(Context paramContext, String[] paramArrayOfString)
    {
      super(17367049, paramArrayOfString);
    }
    
    public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = LayoutInflater.from(getContext()).inflate(17367049, paramViewGroup, false);
      }
      ((TextView)paramView.findViewById(16908308)).setText((CharSequence)getItem(paramInt));
      return paramView;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = LayoutInflater.from(getContext()).inflate(2130969093, paramViewGroup, false);
      ((TextView)localView).setText((CharSequence)getItem(paramInt));
      return localView;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.SearchAdapter
 * JD-Core Version:    0.7.0.1
 */