package com.google.android.finsky.activities;

import android.accounts.Account;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;

public final class BinderFactory
{
  public static DetailsSummaryViewBinder getSummaryViewBinder(DfeToc paramDfeToc, int paramInt, Account paramAccount)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      return new DetailsSummaryViewBinder(paramDfeToc, paramAccount);
    case 3: 
      return new DetailsSummaryAppsViewBinder(paramDfeToc, paramAccount, FinskyApp.get().mPackageMonitorReceiver, FinskyApp.get().mInstaller, FinskyApp.get().mAppStates, FinskyApp.get().mLibraries);
    case 4: 
      return new DetailsSummaryMoviesViewBinder(paramDfeToc, paramAccount);
    case 2: 
      return new DetailsSummaryMusicViewBinder(paramDfeToc, paramAccount);
    case 6: 
      return new DetailsSummaryMagazinesViewBinder(paramDfeToc, paramAccount);
    }
    return new DetailsSummaryBooksViewBinder(paramDfeToc, paramAccount);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.BinderFactory
 * JD-Core Version:    0.7.0.1
 */