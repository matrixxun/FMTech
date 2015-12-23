package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@zzhb
public final class zzit
{
  final Context mContext;
  private int mState = 0;
  private final float zzDb;
  private String zzLm;
  private float zzLn;
  private float zzLo;
  private float zzLp;
  
  private zzit(Context paramContext)
  {
    this.mContext = paramContext;
    this.zzDb = paramContext.getResources().getDisplayMetrics().density;
  }
  
  public zzit(Context paramContext, String paramString)
  {
    this(paramContext);
    this.zzLm = paramString;
  }
  
  public final void zza(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 0)
    {
      this.mState = 0;
      this.zzLn = paramFloat1;
      this.zzLo = paramFloat2;
      this.zzLp = paramFloat2;
    }
    label24:
    label224:
    do
    {
      do
      {
        break label24;
        do
        {
          return;
        } while (this.mState == -1);
        if (paramInt != 2) {
          break;
        }
        if (paramFloat2 > this.zzLo) {
          this.zzLo = paramFloat2;
        }
        while (this.zzLo - this.zzLp > 30.0F * this.zzDb)
        {
          this.mState = -1;
          return;
          if (paramFloat2 < this.zzLp) {
            this.zzLp = paramFloat2;
          }
        }
        if ((this.mState == 0) || (this.mState == 2)) {
          if (paramFloat1 - this.zzLn >= 50.0F * this.zzDb) {
            this.zzLn = paramFloat1;
          }
        }
        for (this.mState = (1 + this.mState);; this.mState = (1 + this.mState))
        {
          do
          {
            if ((this.mState != 1) && (this.mState != 3)) {
              break label224;
            }
            if (paramFloat1 <= this.zzLn) {
              break;
            }
            this.zzLn = paramFloat1;
            return;
          } while (((this.mState != 1) && (this.mState != 3)) || (paramFloat1 - this.zzLn > -50.0F * this.zzDb));
          this.zzLn = paramFloat1;
        }
      } while ((this.mState != 2) || (paramFloat1 >= this.zzLn));
      this.zzLn = paramFloat1;
      return;
    } while ((paramInt != 1) || (this.mState != 4));
    if (!(this.mContext instanceof Activity))
    {
      zzb.i("Can not create dialog without Activity Context");
      return;
    }
    String str1 = this.zzLm;
    final String str2;
    if (!TextUtils.isEmpty(str1))
    {
      String str3 = str1.replaceAll("\\+", "%20");
      Uri localUri = new Uri.Builder().encodedQuery(str3).build();
      StringBuilder localStringBuilder = new StringBuilder();
      zzp.zzbI();
      Map localMap = zziq.zze(localUri);
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        localStringBuilder.append(str4).append(" = ").append((String)localMap.get(str4)).append("\n\n");
      }
      str2 = localStringBuilder.toString().trim();
      if (TextUtils.isEmpty(str2)) {}
    }
    for (;;)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mContext);
      localBuilder.setMessage(str2);
      localBuilder.setTitle("Ad Information");
      localBuilder.setPositiveButton("Share", new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          zzp.zzbI();
          zziq.zzb(zzit.this.mContext, Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", str2), "Share via"));
        }
      });
      localBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
      });
      localBuilder.create().show();
      return;
      str2 = "No debug information";
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzit
 * JD-Core Version:    0.7.0.1
 */