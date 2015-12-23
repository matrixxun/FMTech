package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc
  implements Parcelable.Creator<GoogleSignInAccount>
{
  static void zza(GoogleSignInAccount paramGoogleSignInAccount, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzH(paramParcel, 20293);
    zzb.zzc(paramParcel, 1, paramGoogleSignInAccount.versionCode);
    zzb.zza$2cfb68bf(paramParcel, 2, paramGoogleSignInAccount.zzyx);
    zzb.zza$2cfb68bf(paramParcel, 3, paramGoogleSignInAccount.zzWy);
    zzb.zza$2cfb68bf(paramParcel, 4, paramGoogleSignInAccount.zzXc);
    zzb.zza$2cfb68bf(paramParcel, 5, paramGoogleSignInAccount.zzUe);
    zzb.zza$377a007(paramParcel, 6, paramGoogleSignInAccount.zzXd, paramInt);
    zzb.zza$2cfb68bf(paramParcel, 7, paramGoogleSignInAccount.zzXe);
    zzb.zza(paramParcel, 8, paramGoogleSignInAccount.zzXf);
    zzb.zza$2cfb68bf(paramParcel, 9, paramGoogleSignInAccount.zzXg);
    zzb.zzc$62107c48(paramParcel, 10, paramGoogleSignInAccount.zzVF);
    zzb.zzI(paramParcel, i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.api.signin.zzc
 * JD-Core Version:    0.7.0.1
 */