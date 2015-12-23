package com.google.android.finsky.search;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.SearchSuggestionReport;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.search.PlaySearch;
import com.google.android.play.search.PlaySearchController;
import com.google.android.play.search.PlaySearchSuggestionModel;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public class FinskySearch
  extends PlaySearch
{
  private final Context mContext;
  private int mCurrentBackendId;
  private final SearchLogger mLogger;
  private NavigationManager mNavigationManager;
  private long mNextSuggestRequestTimeMs;
  private String mQueryForLastSuggestRequest;
  private final boolean mShouldThrottleSuggestions;
  private final int mSuggestRequestDelay;
  private AsyncTask<Void, Void, List<PlaySearchSuggestionModel>> mSuggestionsFetcher;
  private final boolean mZeroQueryDisabled;
  
  public FinskySearch(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FinskySearch(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    if (!FinskyApp.get().getExperiments().isEnabled(12603102L)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mZeroQueryDisabled = bool;
      this.mLogger = new SearchLogger();
      this.mSuggestRequestDelay = ((Integer)G.searchSuggestThrottleIntervalMs.get()).intValue();
      this.mShouldThrottleSuggestions = FinskyApp.get().getExperiments().isEnabled(12603101L);
      return;
    }
  }
  
  private void resetSuggestionsThrottleTime()
  {
    try
    {
      this.mNextSuggestRequestTimeMs = 0L;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void triggerSearch(String paramString)
  {
    resetSuggestionsThrottleTime();
    FinskyApp.get().mRecentSuggestions.saveRecentQuery(paramString, null);
    if (this.mNavigationManager != null) {
      this.mNavigationManager.goToSearch$36098d51(paramString, this.mCurrentBackendId);
    }
  }
  
  public final void onModeChanged(int paramInt)
  {
    super.onModeChanged(paramInt);
    if ((paramInt == 3) || (paramInt == 4)) {
      resetSuggestionsThrottleTime();
    }
  }
  
  public final void onQueryChanged(final String paramString, boolean paramBoolean)
  {
    super.onQueryChanged(paramString, paramBoolean);
    if ((this.mController.isInSteadyStateMode()) || (!paramBoolean)) {
      return;
    }
    if (this.mSuggestionsFetcher != null) {
      this.mSuggestionsFetcher.cancel(true);
    }
    this.mSuggestionsFetcher = new AsyncTask()
    {
      private List<PlaySearchSuggestionModel> doInBackground$68cf9880()
      {
        long l2;
        synchronized (FinskySearch.this)
        {
          long l1 = FinskySearch.this.mNextSuggestRequestTimeMs;
          if ((FinskySearch.this.mShouldThrottleSuggestions) && (l1 > 0L))
          {
            l2 = l1 - System.currentTimeMillis();
            if (l2 <= 0L) {}
          }
        }
        try
        {
          Thread.sleep(l2);
          if (isCancelled())
          {
            return null;
            localObject1 = finally;
            throw localObject1;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            cancel(false);
          }
        }
        for (;;)
        {
          SuggestionHandler localSuggestionHandler;
          synchronized (FinskySearch.this)
          {
            FinskySearch.access$002(FinskySearch.this, System.currentTimeMillis() + FinskySearch.this.mSuggestRequestDelay);
            localSuggestionHandler = new SuggestionHandler(FinskySearch.this.mContext);
            if ((FinskySearch.this.mCurrentBackendId == 2) && (!FinskyApp.get().getExperiments().isEnabled(12603044L)))
            {
              localObject3 = new MusicSearchSuggestionFetcher(paramString, FinskySearch.this.getContext(), localSuggestionHandler, FinskySearch.this.mLogger);
              FinskySearch.access$702(FinskySearch.this, paramString);
              new RecentSearchSuggestionFetcher(paramString, FinskySearch.this.mContext, localSuggestionHandler).gatherSuggestions();
              ((SuggestionFetcher)localObject3).gatherSuggestions();
              return localSuggestionHandler.mModels;
            }
          }
          Object localObject3 = new SearchSuggestionFetcher(FinskySearch.this.mContext, FinskySearch.this.mCurrentBackendId, paramString, localSuggestionHandler, FinskySearch.this.mZeroQueryDisabled, FinskySearch.this.mLogger);
        }
      }
    };
    Utils.executeMultiThreaded(this.mSuggestionsFetcher, new Void[0]);
  }
  
  public final void onSearch(String paramString)
  {
    super.onSearch(paramString);
    switchToMode(2);
    triggerSearch(paramString);
  }
  
  public final void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel)
  {
    FinskySearchSuggestionModel localFinskySearchSuggestionModel1 = (FinskySearchSuggestionModel)paramPlaySearchSuggestionModel;
    super.onSuggestionClicked(paramPlaySearchSuggestionModel);
    String str1 = paramPlaySearchSuggestionModel.displayText;
    if (localFinskySearchSuggestionModel1.isRecentSearchSuggestion)
    {
      String str3 = this.mQueryForLastSuggestRequest;
      PlayStore.SearchSuggestionReport localSearchSuggestionReport2 = FinskyEventLog.obtainPlayStoreSearchSuggestionReport();
      localSearchSuggestionReport2.source = 4;
      localSearchSuggestionReport2.hasSource = true;
      localSearchSuggestionReport2.query = str3;
      localSearchSuggestionReport2.hasQuery = true;
      localSearchSuggestionReport2.suggestedQuery = str1;
      localSearchSuggestionReport2.hasSuggestedQuery = true;
      BackgroundEventBuilder localBackgroundEventBuilder2 = new BackgroundEventBuilder(511).setSearchSuggestionReport(localSearchSuggestionReport2);
      FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder2.event);
    }
    while (localFinskySearchSuggestionModel1.link == null)
    {
      triggerSearch(str1);
      return;
      SearchLogger localSearchLogger = this.mLogger;
      FinskySearchSuggestionModel localFinskySearchSuggestionModel2 = (FinskySearchSuggestionModel)paramPlaySearchSuggestionModel;
      if ((localSearchLogger.mGenericSuggestionReport != null) && (!localFinskySearchSuggestionModel2.isRecentSearchSuggestion))
      {
        PlayStore.SearchSuggestionReport localSearchSuggestionReport1 = FinskyEventLog.obtainPlayStoreSearchSuggestionReport();
        SearchLogger.copySuggestionReportFields(localSearchLogger.mGenericSuggestionReport, localSearchSuggestionReport1);
        if ((localFinskySearchSuggestionModel2.serverLogsCookie != null) && (localFinskySearchSuggestionModel2.serverLogsCookie.length > 0))
        {
          localSearchSuggestionReport1.suggestionServerLogsCookie = localFinskySearchSuggestionModel2.serverLogsCookie;
          localSearchSuggestionReport1.hasSuggestionServerLogsCookie = true;
        }
        if (TextUtils.isEmpty(paramPlaySearchSuggestionModel.docId)) {}
        for (String str2 = str1;; str2 = paramPlaySearchSuggestionModel.docId)
        {
          localSearchSuggestionReport1.suggestedQuery = str2;
          localSearchSuggestionReport1.hasSuggestedQuery = true;
          BackgroundEventBuilder localBackgroundEventBuilder1 = new BackgroundEventBuilder(511).setSearchSuggestionReport(localSearchSuggestionReport1);
          FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder1.event);
          break;
        }
      }
    }
    this.mNavigationManager.resolveLink(localFinskySearchSuggestionModel1.link, FinskyApp.get().mToc, this.mContext.getPackageManager());
  }
  
  public void setCurrentBackendId(int paramInt)
  {
    this.mCurrentBackendId = paramInt;
  }
  
  public void setNavigationManager(NavigationManager paramNavigationManager)
  {
    this.mNavigationManager = paramNavigationManager;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.FinskySearch
 * JD-Core Version:    0.7.0.1
 */