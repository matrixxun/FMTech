package com.google.android.gms.feedback;

import android.app.ApplicationErrorReport;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class ErrorReport
  implements SafeParcelable
{
  public static final Parcelable.Creator<ErrorReport> CREATOR = new zza();
  public String account;
  public String anrStackTraces;
  public ApplicationErrorReport applicationErrorReport = new ApplicationErrorReport();
  public BitmapTeleporter bitmapTeleporter;
  public String board;
  public String brand;
  public String buildFingerprint;
  public String buildId;
  public String buildType;
  public String categoryTag;
  public Bundle classificationSignals;
  public String codename;
  @Deprecated
  public String color;
  public String description;
  public String device;
  public String[] eventLog;
  public String exceptionClassName;
  public String exceptionMessage;
  public boolean excludePii;
  public FileTeleporter[] fileTeleporterList;
  public List<RectF> highlightBounds;
  public String incremental;
  public boolean isCtlAllowed;
  public boolean isSilentSend;
  public String launcher;
  public String localeString;
  public LogOptions logOptions;
  public String model;
  public int networkMcc;
  public int networkMnc;
  public String networkName;
  public int networkType;
  public int packageVersion;
  public String packageVersionName;
  public int phoneType;
  public String product;
  public Bundle psdBundle;
  public String[] psdFilePaths;
  public String release;
  public String[] runningApplications;
  public String screenshot;
  public byte[] screenshotBytes;
  public int screenshotHeight;
  public String screenshotPath;
  public int screenshotWidth;
  public int sdk_int;
  public String stackTrace;
  public String submittingPackageName;
  public String suggestionSessionId;
  public boolean suggestionShown;
  public String[] systemLog;
  public ThemeSettings themeSettings;
  public String throwClassName;
  public String throwFileName;
  public int throwLineNumber;
  public String throwMethodName;
  public final int versionCode;
  
  public ErrorReport()
  {
    this.versionCode = 9;
  }
  
  ErrorReport(int paramInt1, ApplicationErrorReport paramApplicationErrorReport, String paramString1, int paramInt2, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt3, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString14, String paramString15, byte[] paramArrayOfByte, int paramInt4, int paramInt5, int paramInt6, int paramInt7, String paramString16, String paramString17, String paramString18, Bundle paramBundle1, boolean paramBoolean1, int paramInt8, int paramInt9, boolean paramBoolean2, String paramString19, String paramString20, int paramInt10, String paramString21, String paramString22, String paramString23, String paramString24, String paramString25, String paramString26, String paramString27, BitmapTeleporter paramBitmapTeleporter, String paramString28, FileTeleporter[] paramArrayOfFileTeleporter, String[] paramArrayOfString4, boolean paramBoolean3, String paramString29, ThemeSettings paramThemeSettings, LogOptions paramLogOptions, String paramString30, boolean paramBoolean4, Bundle paramBundle2, List<RectF> paramList)
  {
    this.versionCode = paramInt1;
    this.applicationErrorReport = paramApplicationErrorReport;
    this.description = paramString1;
    this.packageVersion = paramInt2;
    this.packageVersionName = paramString2;
    this.device = paramString3;
    this.buildId = paramString4;
    this.buildType = paramString5;
    this.model = paramString6;
    this.product = paramString7;
    this.buildFingerprint = paramString8;
    this.sdk_int = paramInt3;
    this.release = paramString9;
    this.incremental = paramString10;
    this.codename = paramString11;
    this.board = paramString12;
    this.brand = paramString13;
    this.runningApplications = paramArrayOfString1;
    this.systemLog = paramArrayOfString2;
    this.eventLog = paramArrayOfString3;
    this.anrStackTraces = paramString14;
    this.screenshot = paramString15;
    this.screenshotBytes = paramArrayOfByte;
    this.screenshotHeight = paramInt4;
    this.screenshotWidth = paramInt5;
    this.phoneType = paramInt6;
    this.networkType = paramInt7;
    this.networkName = paramString16;
    this.account = paramString17;
    this.localeString = paramString18;
    this.psdBundle = paramBundle1;
    this.isSilentSend = paramBoolean1;
    this.networkMcc = paramInt8;
    this.networkMnc = paramInt9;
    this.isCtlAllowed = paramBoolean2;
    this.exceptionClassName = paramString19;
    this.throwFileName = paramString20;
    this.throwLineNumber = paramInt10;
    this.throwClassName = paramString21;
    this.throwMethodName = paramString22;
    this.stackTrace = paramString23;
    this.exceptionMessage = paramString24;
    this.categoryTag = paramString25;
    this.color = paramString26;
    this.submittingPackageName = paramString27;
    this.bitmapTeleporter = paramBitmapTeleporter;
    this.screenshotPath = paramString28;
    this.fileTeleporterList = paramArrayOfFileTeleporter;
    this.psdFilePaths = paramArrayOfString4;
    this.excludePii = paramBoolean3;
    this.launcher = paramString29;
    this.themeSettings = paramThemeSettings;
    this.logOptions = paramLogOptions;
    this.suggestionSessionId = paramString30;
    this.suggestionShown = paramBoolean4;
    this.classificationSignals = paramBundle2;
    this.highlightBounds = paramList;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.ErrorReport
 * JD-Core Version:    0.7.0.1
 */