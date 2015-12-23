package com.google.android.gms.internal;

import android.app.ApplicationErrorReport.CrashInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.feedback.ErrorReport;
import com.google.android.gms.feedback.FeedbackOptions;
import com.google.android.gms.feedback.FileTeleporter;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class zzpq
  extends zzj<zzpr>
{
  public static ErrorReport zza(FeedbackOptions paramFeedbackOptions, File paramFile)
  {
    ErrorReport localErrorReport = new ErrorReport();
    if (paramFeedbackOptions != null)
    {
      if ((paramFeedbackOptions.mPsdBundle != null) && (paramFeedbackOptions.mPsdBundle.size() > 0)) {
        localErrorReport.psdBundle = paramFeedbackOptions.mPsdBundle;
      }
      if (!TextUtils.isEmpty(paramFeedbackOptions.mAccountInUse)) {
        localErrorReport.account = paramFeedbackOptions.mAccountInUse;
      }
      if (!TextUtils.isEmpty(paramFeedbackOptions.mDescription)) {
        localErrorReport.description = paramFeedbackOptions.mDescription;
      }
      if (paramFeedbackOptions.getCrashInfo() != null)
      {
        localErrorReport.throwMethodName = paramFeedbackOptions.getCrashInfo().throwMethodName;
        localErrorReport.throwLineNumber = paramFeedbackOptions.getCrashInfo().throwLineNumber;
        localErrorReport.throwClassName = paramFeedbackOptions.getCrashInfo().throwClassName;
        localErrorReport.stackTrace = paramFeedbackOptions.getCrashInfo().stackTrace;
        localErrorReport.exceptionClassName = paramFeedbackOptions.getCrashInfo().exceptionClassName;
        localErrorReport.exceptionMessage = paramFeedbackOptions.getCrashInfo().exceptionMessage;
        localErrorReport.throwFileName = paramFeedbackOptions.getCrashInfo().throwFileName;
      }
      if (paramFeedbackOptions.mThemeSettings != null) {
        localErrorReport.themeSettings = paramFeedbackOptions.mThemeSettings;
      }
      if (!TextUtils.isEmpty(paramFeedbackOptions.mCategoryTag)) {
        localErrorReport.categoryTag = paramFeedbackOptions.mCategoryTag;
      }
      if (!TextUtils.isEmpty(paramFeedbackOptions.mPackageName)) {
        localErrorReport.applicationErrorReport.packageName = paramFeedbackOptions.mPackageName;
      }
      if ((paramFeedbackOptions.mBitmapTeleporter != null) && (paramFile != null))
      {
        localErrorReport.bitmapTeleporter = paramFeedbackOptions.mBitmapTeleporter;
        BitmapTeleporter localBitmapTeleporter = localErrorReport.bitmapTeleporter;
        if (paramFile == null) {
          throw new NullPointerException("Cannot set null temp directory");
        }
        localBitmapTeleporter.zzaro = paramFile;
      }
      if ((paramFeedbackOptions.mFileTeleporters != null) && (paramFeedbackOptions.mFileTeleporters.size() != 0) && (paramFile != null))
      {
        Iterator localIterator = paramFeedbackOptions.mFileTeleporters.iterator();
        while (localIterator.hasNext())
        {
          FileTeleporter localFileTeleporter = (FileTeleporter)localIterator.next();
          if (paramFile == null) {
            throw new NullPointerException("Cannot set null temp directory");
          }
          localFileTeleporter.zzaro = paramFile;
        }
        localErrorReport.fileTeleporterList = ((FileTeleporter[])paramFeedbackOptions.mFileTeleporters.toArray(new FileTeleporter[paramFeedbackOptions.mFileTeleporters.size()]));
      }
      if (paramFeedbackOptions.mLogOptions != null) {
        localErrorReport.logOptions = paramFeedbackOptions.mLogOptions;
      }
      localErrorReport.excludePii = paramFeedbackOptions.mExcludePii;
    }
    return localErrorReport;
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.feedback.internal.IFeedbackService";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.feedback.internal.IFeedbackService";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzpq
 * JD-Core Version:    0.7.0.1
 */