package com.google.android.gms.internal;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Environment;
import com.google.android.gms.ads.internal.zzp;
import java.util.Map;

@zzhb
public final class zzfp
  extends zzfs
{
  final Context mContext;
  final Map<String, String> zzxC;
  
  public zzfp(zzjo paramzzjo, Map<String, String> paramMap)
  {
    super(paramzzjo, "storePicture");
    this.zzxC = paramMap;
    this.mContext = paramzzjo.zzhF();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfp
 * JD-Core Version:    0.7.0.1
 */