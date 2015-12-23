package com.google.android.finsky.widget.consumption;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.widget.BaseWidgetProvider;
import com.google.android.finsky.widget.WidgetTrampolineActivity;

public class NowPlayingTrampoline
  extends WidgetTrampolineActivity
{
  protected final boolean enableMultiCorpus()
  {
    return true;
  }
  
  protected final String getCorpusName(int paramInt)
  {
    DfeToc localDfeToc = FinskyApp.get().mToc;
    if (localDfeToc != null)
    {
      Toc.CorpusMetadata localCorpusMetadata = localDfeToc.getCorpus(paramInt);
      if (localCorpusMetadata != null) {
        return localCorpusMetadata.libraryName;
      }
    }
    return getString(2131362927);
  }
  
  protected final int getDialogTitle()
  {
    return 2131362928;
  }
  
  protected final Class<? extends BaseWidgetProvider> getWidgetClass()
  {
    return NowPlayingWidgetProvider.class;
  }
  
  protected final boolean isBackendEnabled(int paramInt)
  {
    return paramInt != 3;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.NowPlayingTrampoline
 * JD-Core Version:    0.7.0.1
 */