package com.google.android.finsky.widget;

import com.android.vending.MarketWidgetProvider;
import com.google.android.finsky.config.G;
import com.google.android.play.utils.config.GservicesValue;

public class MarketWidgetTrampolineActivity
  extends WidgetTrampolineActivity
{
  protected final boolean enableMultiCorpus()
  {
    return false;
  }
  
  protected final Class<? extends BaseWidgetProvider> getWidgetClass()
  {
    return MarketWidgetProvider.class;
  }
  
  protected final boolean shouldAllowConfiguration()
  {
    return ((Boolean)G.enableCorpusWidgets.get()).booleanValue();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.MarketWidgetTrampolineActivity
 * JD-Core Version:    0.7.0.1
 */