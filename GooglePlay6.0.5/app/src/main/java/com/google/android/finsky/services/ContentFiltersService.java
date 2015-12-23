package com.google.android.finsky.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.vending.contentfilters.IContentFiltersService.Stub;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ContentFiltersActivity2;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeContentFilters;
import com.google.android.finsky.config.ContentFiltersUtils;
import com.google.android.finsky.config.ContentFiltersUtils.ContentFilterSelection;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.ContentFilters.ContentFilterSettingsResponse;
import com.google.android.finsky.protos.ContentFilters.FilterChoice;
import com.google.android.finsky.protos.ContentFilters.FilterRange;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.SignatureUtils;

public class ContentFiltersService
  extends Service
{
  private final IContentFiltersService.Stub mBinder = new IContentFiltersService.Stub()
  {
    public final Bundle getContentFiltersAndIntent(int[] paramAnonymousArrayOfInt)
      throws RemoteException
    {
      FinskyApp localFinskyApp = FinskyApp.get();
      Bundle localBundle1;
      if (!localFinskyApp.getExperiments().isEnabled(12602392L)) {
        localBundle1 = null;
      }
      for (;;)
      {
        return localBundle1;
        FinskyEventLog localFinskyEventLog = localFinskyApp.getEventLogger();
        String str1 = SignatureUtils.getCallingAppIfAuthorized(ContentFiltersService.this, null, null, localFinskyEventLog, 519);
        if (str1 == null) {
          return null;
        }
        FinskyLog.d("Received content filters request from %s", new Object[] { str1 });
        DfeContentFilters localDfeContentFilters = new DfeContentFilters(localFinskyApp.getDfeApi(null), ContentFiltersService.this);
        boolean bool = localDfeContentFilters.isCacheStale();
        ContentFilters.ContentFilterSettingsResponse localContentFilterSettingsResponse = null;
        if (bool) {
          localContentFilterSettingsResponse = localDfeContentFilters.fetchOverNetwork$6f1d50b6();
        }
        if (localContentFilterSettingsResponse == null) {
          localContentFilterSettingsResponse = localDfeContentFilters.fetchFromCache();
        }
        if (localContentFilterSettingsResponse == null) {
          return null;
        }
        if (!ContentFiltersService.access$000(localContentFilterSettingsResponse.filterRange, paramAnonymousArrayOfInt)) {
          return null;
        }
        localBundle1 = new Bundle();
        Intent localIntent = new Intent(localFinskyApp, ContentFiltersActivity2.class);
        PendingIntent localPendingIntent = PendingIntent.getActivity(ContentFiltersService.this, 0, localIntent, 1073741824);
        localBundle1.putParcelable("content_filters_intent", localPendingIntent);
        localBundle1.putInt("content_filters_request_code", 44);
        if (!TextUtils.isEmpty((CharSequence)FinskyPreferences.contentFilters2.get()))
        {
          ContentFiltersUtils.ContentFilterSelection[] arrayOfContentFilterSelection = ContentFiltersUtils.getSelectionsFromPref(FinskyPreferences.contentFilters2Selections);
          for (ContentFilters.FilterRange localFilterRange : localContentFilterSettingsResponse.filterRange)
          {
            ContentFilters.FilterChoice localFilterChoice = ContentFiltersUtils.getFilterChoice(localFilterRange, arrayOfContentFilterSelection);
            if (localFilterChoice != null)
            {
              int k = paramAnonymousArrayOfInt.length;
              int m = 0;
              if (m < k)
              {
                int n = paramAnonymousArrayOfInt[m];
                int[] arrayOfInt = localFilterRange.documentType;
                int i1 = arrayOfInt.length;
                for (int i2 = 0;; i2++) {
                  if (i2 < i1)
                  {
                    if (arrayOfInt[i2] == n)
                    {
                      String str2 = String.valueOf(n);
                      Bundle localBundle2 = ContentFiltersUtils.createBundleForFilterChoice(localFilterRange, localFilterChoice);
                      localBundle1.putBundle(str2, localBundle2);
                    }
                  }
                  else
                  {
                    m++;
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }
  };
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.ContentFiltersService
 * JD-Core Version:    0.7.0.1
 */