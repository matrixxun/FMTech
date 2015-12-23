package com.google.android.gms.internal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.CalendarContract.Events;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzp;
import java.util.Map;

@zzhb
public final class zzfm
  extends zzfs
{
  final Context mContext;
  String zzCu;
  long zzCv;
  long zzCw;
  String zzCx;
  String zzCy;
  private final Map<String, String> zzxC;
  
  public zzfm(zzjo paramzzjo, Map<String, String> paramMap)
  {
    super(paramzzjo, "createCalendarEvent");
    this.zzxC = paramMap;
    this.mContext = paramzzjo.zzhF();
    this.zzCu = zzai("description");
    this.zzCx = zzai("summary");
    this.zzCv = zzaj("start_ticks");
    this.zzCw = zzaj("end_ticks");
    this.zzCy = zzai("location");
  }
  
  private String zzai(String paramString)
  {
    if (TextUtils.isEmpty((CharSequence)this.zzxC.get(paramString))) {
      return "";
    }
    return (String)this.zzxC.get(paramString);
  }
  
  private long zzaj(String paramString)
  {
    String str = (String)this.zzxC.get(paramString);
    if (str == null) {
      return -1L;
    }
    try
    {
      long l = Long.parseLong(str);
      return l;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return -1L;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfm
 * JD-Core Version:    0.7.0.1
 */