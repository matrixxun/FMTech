package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Build.VERSION;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class zzf
  extends zzw
{
  private long zzbmo;
  private String zzbmp;
  
  zzf(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  protected final void onInitialize()
  {
    Calendar localCalendar = Calendar.getInstance();
    this.zzbmo = TimeUnit.MINUTES.convert(localCalendar.get(15) + localCalendar.get(16), TimeUnit.MILLISECONDS);
    Locale localLocale = Locale.getDefault();
    this.zzbmp = (localLocale.getLanguage().toLowerCase(Locale.ENGLISH) + "-" + localLocale.getCountry().toLowerCase(Locale.ENGLISH));
  }
  
  public final String zzCh()
  {
    zziL();
    return Build.VERSION.RELEASE;
  }
  
  public final long zzCi()
  {
    zziL();
    return this.zzbmo;
  }
  
  public final String zzCj()
  {
    zziL();
    return this.zzbmp;
  }
  
  public final String zzhj()
  {
    zziL();
    return Build.MODEL;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzf
 * JD-Core Version:    0.7.0.1
 */