package com.google.android.finsky.widget.recommendation;

import com.google.android.finsky.widget.BaseWidgetProvider;
import com.google.android.finsky.widget.WidgetTrampolineActivity;

public class RecommendedTrampoline
  extends WidgetTrampolineActivity
{
  protected final boolean enableMultiCorpus()
  {
    return true;
  }
  
  protected final String getCorpusName(int paramInt)
  {
    if (paramInt == 0) {
      return getString(2131362352);
    }
    return super.getCorpusName(paramInt);
  }
  
  protected final int getDialogTitle()
  {
    return 2131362929;
  }
  
  protected final Class<? extends BaseWidgetProvider> getWidgetClass()
  {
    return RecommendedWidgetProvider.class;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.RecommendedTrampoline
 * JD-Core Version:    0.7.0.1
 */