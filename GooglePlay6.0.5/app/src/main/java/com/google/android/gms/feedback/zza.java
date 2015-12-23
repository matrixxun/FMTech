package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza
  implements Parcelable.Creator<ErrorReport>
{
  static void zza(ErrorReport paramErrorReport, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramErrorReport.versionCode);
    zzb.zza$377a007(paramParcel, 2, paramErrorReport.applicationErrorReport, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 3, paramErrorReport.description);
    zzb.zzc(paramParcel, 4, paramErrorReport.packageVersion);
    zzb.zza$2cfb68bf(paramParcel, 5, paramErrorReport.packageVersionName);
    zzb.zza$2cfb68bf(paramParcel, 6, paramErrorReport.device);
    zzb.zza$2cfb68bf(paramParcel, 7, paramErrorReport.buildId);
    zzb.zza$2cfb68bf(paramParcel, 8, paramErrorReport.buildType);
    zzb.zza$2cfb68bf(paramParcel, 9, paramErrorReport.model);
    zzb.zza$2cfb68bf(paramParcel, 10, paramErrorReport.product);
    zzb.zza$2cfb68bf(paramParcel, 11, paramErrorReport.buildFingerprint);
    zzb.zzc(paramParcel, 12, paramErrorReport.sdk_int);
    zzb.zza$2cfb68bf(paramParcel, 13, paramErrorReport.release);
    zzb.zza$2cfb68bf(paramParcel, 14, paramErrorReport.incremental);
    zzb.zza$2cfb68bf(paramParcel, 15, paramErrorReport.codename);
    zzb.zza$2cfb68bf(paramParcel, 17, paramErrorReport.brand);
    zzb.zza$2cfb68bf(paramParcel, 16, paramErrorReport.board);
    zzb.zza$41b439c0(paramParcel, 19, paramErrorReport.systemLog);
    zzb.zza$41b439c0(paramParcel, 18, paramErrorReport.runningApplications);
    zzb.zza$2cfb68bf(paramParcel, 21, paramErrorReport.anrStackTraces);
    zzb.zza$41b439c0(paramParcel, 20, paramErrorReport.eventLog);
    zzb.zza$52910762(paramParcel, 23, paramErrorReport.screenshotBytes);
    zzb.zza$2cfb68bf(paramParcel, 22, paramErrorReport.screenshot);
    zzb.zzc(paramParcel, 25, paramErrorReport.screenshotWidth);
    zzb.zzc(paramParcel, 24, paramErrorReport.screenshotHeight);
    zzb.zzc(paramParcel, 27, paramErrorReport.networkType);
    zzb.zzc(paramParcel, 26, paramErrorReport.phoneType);
    zzb.zza$2cfb68bf(paramParcel, 29, paramErrorReport.account);
    zzb.zza$2cfb68bf(paramParcel, 28, paramErrorReport.networkName);
    zzb.zza$f7bef55(paramParcel, 31, paramErrorReport.psdBundle);
    zzb.zza$2cfb68bf(paramParcel, 30, paramErrorReport.localeString);
    zzb.zzc(paramParcel, 34, paramErrorReport.networkMnc);
    zzb.zza(paramParcel, 35, paramErrorReport.isCtlAllowed);
    zzb.zza(paramParcel, 32, paramErrorReport.isSilentSend);
    zzb.zzc(paramParcel, 33, paramErrorReport.networkMcc);
    zzb.zzc(paramParcel, 38, paramErrorReport.throwLineNumber);
    zzb.zza$2cfb68bf(paramParcel, 39, paramErrorReport.throwClassName);
    zzb.zza$2cfb68bf(paramParcel, 36, paramErrorReport.exceptionClassName);
    zzb.zza$2cfb68bf(paramParcel, 37, paramErrorReport.throwFileName);
    zzb.zza$2cfb68bf(paramParcel, 42, paramErrorReport.exceptionMessage);
    zzb.zza$2cfb68bf(paramParcel, 43, paramErrorReport.categoryTag);
    zzb.zza$2cfb68bf(paramParcel, 40, paramErrorReport.throwMethodName);
    zzb.zza$2cfb68bf(paramParcel, 41, paramErrorReport.stackTrace);
    zzb.zza$377a007(paramParcel, 46, paramErrorReport.bitmapTeleporter, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 47, paramErrorReport.screenshotPath);
    zzb.zza$2cfb68bf(paramParcel, 44, paramErrorReport.color);
    zzb.zza$2cfb68bf(paramParcel, 45, paramErrorReport.submittingPackageName);
    zzb.zza$2cfb68bf(paramParcel, 51, paramErrorReport.launcher);
    zzb.zza(paramParcel, 50, paramErrorReport.excludePii);
    zzb.zza$41b439c0(paramParcel, 49, paramErrorReport.psdFilePaths);
    zzb.zza$2d7953c6(paramParcel, 48, paramErrorReport.fileTeleporterList, paramInt);
    zzb.zza(paramParcel, 55, paramErrorReport.suggestionShown);
    zzb.zza$2cfb68bf(paramParcel, 54, paramErrorReport.suggestionSessionId);
    zzb.zza$377a007(paramParcel, 53, paramErrorReport.logOptions, paramInt);
    zzb.zza$377a007(paramParcel, 52, paramErrorReport.themeSettings, paramInt);
    zzb.zzc$62107c48(paramParcel, 57, paramErrorReport.highlightBounds);
    zzb.zza$f7bef55(paramParcel, 56, paramErrorReport.classificationSignals);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.zza
 * JD-Core Version:    0.7.0.1
 */