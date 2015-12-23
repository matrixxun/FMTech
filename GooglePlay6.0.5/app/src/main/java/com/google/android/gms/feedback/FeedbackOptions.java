package com.google.android.gms.feedback;

import android.app.ApplicationErrorReport;
import android.app.ApplicationErrorReport.CrashInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;

public class FeedbackOptions
  implements SafeParcelable
{
  public static final Parcelable.Creator<FeedbackOptions> CREATOR = new zzb();
  public String mAccountInUse;
  public ApplicationErrorReport mApplicationErrorReport;
  public BitmapTeleporter mBitmapTeleporter;
  public String mCategoryTag;
  public String mDescription;
  public boolean mExcludePii;
  public ArrayList<FileTeleporter> mFileTeleporters;
  public LogOptions mLogOptions;
  public String mPackageName;
  public Bundle mPsdBundle;
  public ThemeSettings mThemeSettings;
  public final int mVersionCode;
  
  private FeedbackOptions()
  {
    this(3, null, null, null, new ApplicationErrorReport(), null, null, null, null, true, null, null);
  }
  
  FeedbackOptions(int paramInt, String paramString1, Bundle paramBundle, String paramString2, ApplicationErrorReport paramApplicationErrorReport, String paramString3, BitmapTeleporter paramBitmapTeleporter, String paramString4, ArrayList<FileTeleporter> paramArrayList, boolean paramBoolean, ThemeSettings paramThemeSettings, LogOptions paramLogOptions)
  {
    this.mVersionCode = paramInt;
    this.mAccountInUse = paramString1;
    this.mPsdBundle = paramBundle;
    this.mDescription = paramString2;
    this.mApplicationErrorReport = paramApplicationErrorReport;
    this.mCategoryTag = paramString3;
    this.mBitmapTeleporter = paramBitmapTeleporter;
    this.mPackageName = paramString4;
    this.mFileTeleporters = paramArrayList;
    this.mExcludePii = paramBoolean;
    this.mThemeSettings = paramThemeSettings;
    this.mLogOptions = paramLogOptions;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final ApplicationErrorReport.CrashInfo getCrashInfo()
  {
    if (this.mApplicationErrorReport == null) {
      return null;
    }
    return this.mApplicationErrorReport.crashInfo;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
  
  public static final class Builder
  {
    public String mAccountInUse;
    public String mCategoryTag;
    public String mDescription;
    public boolean mExcludePii;
    public ArrayList<FileTeleporter> mFileTeleporters = new ArrayList();
    public LogOptions mLogOptions;
    public Bundle mPsdBundle = new Bundle();
    public ThemeSettings mThemeSettings;
    public Bitmap zzaLj;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.FeedbackOptions
 * JD-Core Version:    0.7.0.1
 */