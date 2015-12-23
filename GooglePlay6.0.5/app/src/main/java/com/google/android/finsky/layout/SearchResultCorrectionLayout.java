package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;

public class SearchResultCorrectionLayout
  extends IdentifiableLinearLayout
{
  public boolean mFullPageReplaced;
  public SuggestionBarLayout mReplacedLine;
  public SuggestionBarLayout mSuggestionLine;
  
  public SearchResultCorrectionLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public SearchResultCorrectionLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSuggestionLine = ((SuggestionBarLayout)findViewById(2131756092));
    this.mReplacedLine = ((SuggestionBarLayout)findViewById(2131756093));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SearchResultCorrectionLayout
 * JD-Core Version:    0.7.0.1
 */