package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzb
  implements Parcelable.Creator<FeedbackOptions>
{
  static void zza(FeedbackOptions paramFeedbackOptions, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, 20293);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramFeedbackOptions.mVersionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 2, paramFeedbackOptions.mAccountInUse);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$f7bef55(paramParcel, 3, paramFeedbackOptions.mPsdBundle);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 5, paramFeedbackOptions.mDescription);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$377a007(paramParcel, 6, paramFeedbackOptions.mApplicationErrorReport, paramInt);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 7, paramFeedbackOptions.mCategoryTag);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$377a007(paramParcel, 8, paramFeedbackOptions.mBitmapTeleporter, paramInt);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$2cfb68bf(paramParcel, 9, paramFeedbackOptions.mPackageName);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc$62107c48(paramParcel, 10, paramFeedbackOptions.mFileTeleporters);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 11, paramFeedbackOptions.mExcludePii);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$377a007(paramParcel, 12, paramFeedbackOptions.mThemeSettings, paramInt);
    com.google.android.gms.common.internal.safeparcel.zzb.zza$377a007(paramParcel, 13, paramFeedbackOptions.mLogOptions, paramInt);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.zzb
 * JD-Core Version:    0.7.0.1
 */