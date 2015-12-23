package com.google.android.gms.measurement.internal;

abstract class zzw
  extends zzv
{
  private boolean zzPl;
  boolean zzPm;
  private boolean zzpv;
  
  zzw(zzt paramzzt)
  {
    super(paramzzt);
    zzt localzzt = this.zzbkM;
    localzzt.zzbom = (1 + localzzt.zzbom);
  }
  
  final boolean isInitialized()
  {
    return (this.zzPl) && (!this.zzpv);
  }
  
  protected abstract void onInitialize();
  
  public final void zza()
  {
    if (this.zzPl) {
      throw new IllegalStateException("Can't initialize twice");
    }
    onInitialize();
    zzt localzzt = this.zzbkM;
    localzzt.zzbon = (1 + localzzt.zzbon);
    this.zzPl = true;
  }
  
  protected final void zziL()
  {
    if (!isInitialized()) {
      throw new IllegalStateException("Not initialized");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzw
 * JD-Core Version:    0.7.0.1
 */