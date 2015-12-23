package com.google.android.gms.googlehelp;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.feedback.ErrorReport;
import com.google.android.gms.feedback.ThemeSettings;
import com.google.android.gms.googlehelp.internal.common.OverflowMenuItem;
import com.google.android.gms.googlehelp.internal.common.TogglingData;
import java.util.ArrayList;
import java.util.List;

public final class GoogleHelp
  implements SafeParcelable
{
  public static final Parcelable.Creator<GoogleHelp> CREATOR = new zzb();
  Bundle mPsdBundle;
  ThemeSettings mThemeSettings;
  final int mVersionCode;
  String zzbaA;
  public Account zzbaB;
  String zzbaC;
  int zzbaD;
  int zzbaE;
  boolean zzbaF;
  boolean zzbaG;
  List<String> zzbaH;
  @Deprecated
  Bundle zzbaI;
  @Deprecated
  Bitmap zzbaJ;
  @Deprecated
  byte[] zzbaK;
  @Deprecated
  int zzbaL;
  @Deprecated
  int zzbaM;
  String zzbaN;
  public Uri zzbaO;
  List<OverflowMenuItem> zzbaP;
  @Deprecated
  int zzbaQ;
  List<OfflineSuggestion> zzbaR;
  boolean zzbaS;
  public ErrorReport zzbaT = new ErrorReport();
  public TogglingData zzbaU;
  int zzbaV;
  PendingIntent zzbaW;
  
  GoogleHelp(int paramInt1, String paramString1, Account paramAccount, Bundle paramBundle1, String paramString2, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, List<String> paramList, Bundle paramBundle2, Bitmap paramBitmap, byte[] paramArrayOfByte, int paramInt4, int paramInt5, String paramString3, Uri paramUri, List<OverflowMenuItem> paramList1, int paramInt6, ThemeSettings paramThemeSettings, List<OfflineSuggestion> paramList2, boolean paramBoolean3, ErrorReport paramErrorReport, TogglingData paramTogglingData, int paramInt7, PendingIntent paramPendingIntent)
  {
    this.mVersionCode = paramInt1;
    this.zzbaA = paramString1;
    this.zzbaB = paramAccount;
    this.mPsdBundle = paramBundle1;
    this.zzbaC = paramString2;
    this.zzbaD = paramInt2;
    this.zzbaE = paramInt3;
    this.zzbaF = paramBoolean1;
    this.zzbaG = paramBoolean2;
    this.zzbaH = paramList;
    this.zzbaW = paramPendingIntent;
    this.zzbaI = paramBundle2;
    this.zzbaJ = paramBitmap;
    this.zzbaK = paramArrayOfByte;
    this.zzbaL = paramInt4;
    this.zzbaM = paramInt5;
    this.zzbaN = paramString3;
    this.zzbaO = paramUri;
    this.zzbaP = paramList1;
    GoogleHelp localGoogleHelp;
    if (this.mVersionCode < 4)
    {
      paramThemeSettings = new ThemeSettings();
      paramThemeSettings.zzaLn = paramInt6;
      localGoogleHelp = this;
    }
    for (;;)
    {
      localGoogleHelp.mThemeSettings = paramThemeSettings;
      this.zzbaR = paramList2;
      this.zzbaS = paramBoolean3;
      this.zzbaT = paramErrorReport;
      if (this.zzbaT != null) {
        this.zzbaT.launcher = "GoogleHelp";
      }
      this.zzbaU = paramTogglingData;
      this.zzbaV = paramInt7;
      return;
      if (paramThemeSettings == null)
      {
        paramThemeSettings = new ThemeSettings();
        localGoogleHelp = this;
      }
      else
      {
        localGoogleHelp = this;
      }
    }
  }
  
  public GoogleHelp(String paramString)
  {
    this(7, paramString, null, null, null, 0, 0, true, true, new ArrayList(), null, null, null, 0, 0, null, null, new ArrayList(), 0, null, new ArrayList(), false, new ErrorReport(), null, 0, null);
  }
  
  public static Bitmap getScreenshot(Activity paramActivity)
  {
    try
    {
      View localView = paramActivity.getWindow().getDecorView().getRootView();
      Bitmap localBitmap = Bitmap.createBitmap(localView.getWidth(), localView.getHeight(), Bitmap.Config.ARGB_8888);
      localView.draw(new Canvas(localBitmap));
      return localBitmap;
    }
    catch (Error localError)
    {
      Log.w("GOOGLEHELP_GoogleHelp", "Get screenshot failed!", localError);
      return null;
    }
    catch (Exception localException)
    {
      label44:
      break label44;
    }
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.GoogleHelp
 * JD-Core Version:    0.7.0.1
 */