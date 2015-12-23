package com.google.ads.afma.nano;

import com.google.android.gms.internal.zzaic;
import com.google.android.gms.internal.zzaik;
import java.io.IOException;

public abstract interface NanoAdshieldEvent
{
  public static final class AdShieldEvent
    extends zzaik
  {
    public String appId = "";
    
    public AdShieldEvent()
    {
      this.zzcjk = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.appId.equals("")) {
        i += zzaic.zzq(1, this.appId);
      }
      return i;
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      if (!this.appId.equals("")) {
        paramzzaic.zzb(1, this.appId);
      }
      super.writeTo(paramzzaic);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.ads.afma.nano.NanoAdshieldEvent
 * JD-Core Version:    0.7.0.1
 */