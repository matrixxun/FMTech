package com.google.android.finsky.search;

import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.SearchSuggestionReport;

public final class SearchLogger
{
  final PlayStore.SearchSuggestionReport mGenericSuggestionReport = FinskyEventLog.obtainPlayStoreSearchSuggestionReport();
  
  public static void copySuggestionReportFields(PlayStore.SearchSuggestionReport paramSearchSuggestionReport1, PlayStore.SearchSuggestionReport paramSearchSuggestionReport2)
  {
    copySuggestionReportFields(paramSearchSuggestionReport1.query, paramSearchSuggestionReport1.source, paramSearchSuggestionReport1.resultCount, paramSearchSuggestionReport1.responseServerLogsCookie, paramSearchSuggestionReport1.clientLatencyMs, paramSearchSuggestionReport2);
  }
  
  private static void copySuggestionReportFields(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong, PlayStore.SearchSuggestionReport paramSearchSuggestionReport)
  {
    paramSearchSuggestionReport.query = paramString;
    paramSearchSuggestionReport.hasQuery = true;
    paramSearchSuggestionReport.clientLatencyMs = paramLong;
    paramSearchSuggestionReport.hasClientLatencyMs = true;
    paramSearchSuggestionReport.source = paramInt1;
    paramSearchSuggestionReport.hasSource = true;
    paramSearchSuggestionReport.resultCount = paramInt2;
    paramSearchSuggestionReport.hasResultCount = true;
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      paramSearchSuggestionReport.responseServerLogsCookie = paramArrayOfByte;
      paramSearchSuggestionReport.hasResponseServerLogsCookie = true;
    }
  }
  
  public static void logSearchVolleyError(int paramInt, VolleyError paramVolleyError)
  {
    FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
    if (paramVolleyError == null) {}
    for (String str = null;; str = paramVolleyError.getClass().getSimpleName())
    {
      localFinskyEventLog.logBackgroundEvent(paramInt, null, null, 1, str, null);
      return;
    }
  }
  
  public final void logSuggestionsReceived(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte, long paramLong)
  {
    long l = System.currentTimeMillis() - paramLong;
    PlayStore.SearchSuggestionReport localSearchSuggestionReport = FinskyEventLog.obtainPlayStoreSearchSuggestionReport();
    copySuggestionReportFields(paramString, paramInt1, paramInt2, paramArrayOfByte, l, localSearchSuggestionReport);
    this.mGenericSuggestionReport.clear();
    copySuggestionReportFields(localSearchSuggestionReport, this.mGenericSuggestionReport);
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(510).setSearchSuggestionReport(localSearchSuggestionReport);
    FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.search.SearchLogger
 * JD-Core Version:    0.7.0.1
 */