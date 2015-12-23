package com.google.android.gms.measurement.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.measurement.AppMeasurementReceiver;
import com.google.android.gms.measurement.AppMeasurementService;

public final class zzab
  extends zzw
{
  private final AlarmManager zzQA = (AlarmManager)super.getContext().getSystemService("alarm");
  private boolean zzQz;
  
  protected zzab(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  private PendingIntent zzjh()
  {
    Intent localIntent = new Intent(super.getContext(), AppMeasurementReceiver.class);
    localIntent.setAction("com.google.android.gms.measurement.UPLOAD");
    return PendingIntent.getBroadcast(super.getContext(), 0, localIntent, 0);
  }
  
  public final void cancel()
  {
    zziL();
    this.zzQz = false;
    this.zzQA.cancel(zzjh());
  }
  
  protected final void onInitialize()
  {
    this.zzQA.cancel(zzjh());
  }
  
  public final void zzr(long paramLong)
  {
    zziL();
    if (paramLong > 0L) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzab(bool);
      zzx.zza(AppMeasurementReceiver.zzac(super.getContext()), "Receiver not registered/enabled");
      zzx.zza(AppMeasurementService.zzad(super.getContext()), "Service not registered/enabled");
      cancel();
      long l = paramLong + super.getClock().elapsedRealtime();
      this.zzQz = true;
      this.zzQA.setInexactRepeating(2, l, Math.max(zzc.zzBQ(), paramLong), zzjh());
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzab
 * JD-Core Version:    0.7.0.1
 */