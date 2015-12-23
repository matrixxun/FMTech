package com.google.android.finsky.widget.consumption;

import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.widget.TrampolineActivity;
import com.google.android.finsky.widget.WidgetUtils;

public class MyLibraryTrampoline
  extends TrampolineActivity
{
  protected final boolean enableMultiCorpus()
  {
    return false;
  }
  
  public final void finish(int paramInt, String paramString)
  {
    int i;
    if (paramInt == -1)
    {
      i = WidgetUtils.translate(paramString);
      Intent localIntent1 = getIntent();
      if ((localIntent1 == null) || (!localIntent1.hasExtra("appWidgetId"))) {
        break label91;
      }
      int j = localIntent1.getIntExtra("appWidgetId", -1);
      Intent localIntent2 = new Intent(this, NowPlayingWidgetProvider.class);
      localIntent2.setAction("NowPlayingWidgetProvider.WarmWelcome");
      localIntent2.putExtra("appWidgetId", j);
      localIntent2.putExtra("backendId", i);
      sendBroadcast(localIntent2);
    }
    for (;;)
    {
      finish();
      return;
      label91:
      startActivity(IntentUtils.buildConsumptionAppLaunchIntent(this, i, FinskyApp.get().getCurrentAccountName()));
    }
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
    return 2131362927;
  }
  
  protected final boolean isBackendEnabled(int paramInt)
  {
    return (paramInt != 3) && (IntentUtils.isConsumptionAppInstalled(getPackageManager(), paramInt)) && (!IntentUtils.isConsumptionAppDisabled(FinskyApp.get().mPackageStateRepository, paramInt));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.MyLibraryTrampoline
 * JD-Core Version:    0.7.0.1
 */