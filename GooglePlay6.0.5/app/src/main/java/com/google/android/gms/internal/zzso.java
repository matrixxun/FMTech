package com.google.android.gms.internal;

import java.io.IOException;

public abstract interface zzso
{
  public static final class zza
    extends zzaik
  {
    private static volatile zza[] zzboV;
    public Integer count = null;
    public String name = null;
    public zzso.zzb[] zzboW = zzso.zzb.zzDf();
    public Long zzboX = null;
    public Long zzboY = null;
    
    public zza()
    {
      this.zzcjk = -1;
    }
    
    public static zza[] zzDd()
    {
      if (zzboV == null) {}
      synchronized (zzaii.zzcjj)
      {
        if (zzboV == null) {
          zzboV = new zza[0];
        }
        return zzboV;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.zzboW != null) && (this.zzboW.length > 0)) {
        for (int j = 0; j < this.zzboW.length; j++)
        {
          zzso.zzb localzzb = this.zzboW[j];
          if (localzzb != null) {
            i += zzaic.zzc(1, localzzb);
          }
        }
      }
      if (this.name != null) {
        i += zzaic.zzq(2, this.name);
      }
      if (this.zzboX != null) {
        i += zzaic.zzi(3, this.zzboX.longValue());
      }
      if (this.zzboY != null) {
        i += zzaic.zzi(4, this.zzboY.longValue());
      }
      if (this.count != null) {
        i += zzaic.zzY(5, this.count.intValue());
      }
      return i;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      zza localzza;
      do
      {
        do
        {
          return true;
          if (!(paramObject instanceof zza)) {
            return false;
          }
          localzza = (zza)paramObject;
          if (!zzaii.equals(this.zzboW, localzza.zzboW)) {
            return false;
          }
          if (this.name == null)
          {
            if (localzza.name != null) {
              return false;
            }
          }
          else if (!this.name.equals(localzza.name)) {
            return false;
          }
          if (this.zzboX == null)
          {
            if (localzza.zzboX != null) {
              return false;
            }
          }
          else if (!this.zzboX.equals(localzza.zzboX)) {
            return false;
          }
          if (this.zzboY == null)
          {
            if (localzza.zzboY != null) {
              return false;
            }
          }
          else if (!this.zzboY.equals(localzza.zzboY)) {
            return false;
          }
          if (this.count != null) {
            break;
          }
        } while (localzza.count == null);
        return false;
      } while (this.count.equals(localzza.count));
      return false;
    }
    
    public final int hashCode()
    {
      int i = 31 * (31 * (527 + getClass().getName().hashCode()) + zzaii.hashCode(this.zzboW));
      int j;
      int m;
      label55:
      int i1;
      label74:
      int i2;
      int i3;
      if (this.name == null)
      {
        j = 0;
        int k = 31 * (j + i);
        if (this.zzboX != null) {
          break label115;
        }
        m = 0;
        int n = 31 * (m + k);
        if (this.zzboY != null) {
          break label127;
        }
        i1 = 0;
        i2 = 31 * (i1 + n);
        Integer localInteger = this.count;
        i3 = 0;
        if (localInteger != null) {
          break label139;
        }
      }
      for (;;)
      {
        return i2 + i3;
        j = this.name.hashCode();
        break;
        label115:
        m = this.zzboX.hashCode();
        break label55;
        label127:
        i1 = this.zzboY.hashCode();
        break label74;
        label139:
        i3 = this.count.hashCode();
      }
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      if ((this.zzboW != null) && (this.zzboW.length > 0)) {
        for (int i = 0; i < this.zzboW.length; i++)
        {
          zzso.zzb localzzb = this.zzboW[i];
          if (localzzb != null) {
            paramzzaic.zza(1, localzzb);
          }
        }
      }
      if (this.name != null) {
        paramzzaic.zzb(2, this.name);
      }
      if (this.zzboX != null) {
        paramzzaic.zzb(3, this.zzboX.longValue());
      }
      if (this.zzboY != null) {
        paramzzaic.zzb(4, this.zzboY.longValue());
      }
      if (this.count != null) {
        paramzzaic.zzW(5, this.count.intValue());
      }
      super.writeTo(paramzzaic);
    }
  }
  
  public static final class zzb
    extends zzaik
  {
    private static volatile zzb[] zzboZ;
    public String name = null;
    public String stringValue = null;
    public Float zzboU = null;
    public Long zzbpa = null;
    
    public zzb()
    {
      this.zzcjk = -1;
    }
    
    public static zzb[] zzDf()
    {
      if (zzboZ == null) {}
      synchronized (zzaii.zzcjj)
      {
        if (zzboZ == null) {
          zzboZ = new zzb[0];
        }
        return zzboZ;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.name != null) {
        i += zzaic.zzq(1, this.name);
      }
      if (this.stringValue != null) {
        i += zzaic.zzq(2, this.stringValue);
      }
      if (this.zzbpa != null) {
        i += zzaic.zzi(3, this.zzbpa.longValue());
      }
      if (this.zzboU != null)
      {
        this.zzboU.floatValue();
        i += 4 + zzaic.zzsv(4);
      }
      return i;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      zzb localzzb;
      do
      {
        do
        {
          return true;
          if (!(paramObject instanceof zzb)) {
            return false;
          }
          localzzb = (zzb)paramObject;
          if (this.name == null)
          {
            if (localzzb.name != null) {
              return false;
            }
          }
          else if (!this.name.equals(localzzb.name)) {
            return false;
          }
          if (this.stringValue == null)
          {
            if (localzzb.stringValue != null) {
              return false;
            }
          }
          else if (!this.stringValue.equals(localzzb.stringValue)) {
            return false;
          }
          if (this.zzbpa == null)
          {
            if (localzzb.zzbpa != null) {
              return false;
            }
          }
          else if (!this.zzbpa.equals(localzzb.zzbpa)) {
            return false;
          }
          if (this.zzboU != null) {
            break;
          }
        } while (localzzb.zzboU == null);
        return false;
      } while (this.zzboU.equals(localzzb.zzboU));
      return false;
    }
    
    public final int hashCode()
    {
      int i = 31 * (527 + getClass().getName().hashCode());
      int j;
      int m;
      label44:
      int i1;
      label63:
      int i2;
      int i3;
      if (this.name == null)
      {
        j = 0;
        int k = 31 * (j + i);
        if (this.stringValue != null) {
          break label104;
        }
        m = 0;
        int n = 31 * (m + k);
        if (this.zzbpa != null) {
          break label116;
        }
        i1 = 0;
        i2 = 31 * (i1 + n);
        Float localFloat = this.zzboU;
        i3 = 0;
        if (localFloat != null) {
          break label128;
        }
      }
      for (;;)
      {
        return i2 + i3;
        j = this.name.hashCode();
        break;
        label104:
        m = this.stringValue.hashCode();
        break label44;
        label116:
        i1 = this.zzbpa.hashCode();
        break label63;
        label128:
        i3 = this.zzboU.hashCode();
      }
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      if (this.name != null) {
        paramzzaic.zzb(1, this.name);
      }
      if (this.stringValue != null) {
        paramzzaic.zzb(2, this.stringValue);
      }
      if (this.zzbpa != null) {
        paramzzaic.zzb(3, this.zzbpa.longValue());
      }
      if (this.zzboU != null) {
        paramzzaic.zzb(4, this.zzboU.floatValue());
      }
      super.writeTo(paramzzaic);
    }
  }
  
  public static final class zzc
    extends zzaik
  {
    public zzso.zzd[] zzbpb = zzso.zzd.zzDi();
    
    public zzc()
    {
      this.zzcjk = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.zzbpb != null) && (this.zzbpb.length > 0)) {
        for (int j = 0; j < this.zzbpb.length; j++)
        {
          zzso.zzd localzzd = this.zzbpb[j];
          if (localzzd != null) {
            i += zzaic.zzc(1, localzzd);
          }
        }
      }
      return i;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      zzc localzzc;
      do
      {
        return true;
        if (!(paramObject instanceof zzc)) {
          return false;
        }
        localzzc = (zzc)paramObject;
      } while (zzaii.equals(this.zzbpb, localzzc.zzbpb));
      return false;
    }
    
    public final int hashCode()
    {
      return 31 * (527 + getClass().getName().hashCode()) + zzaii.hashCode(this.zzbpb);
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      if ((this.zzbpb != null) && (this.zzbpb.length > 0)) {
        for (int i = 0; i < this.zzbpb.length; i++)
        {
          zzso.zzd localzzd = this.zzbpb[i];
          if (localzzd != null) {
            paramzzaic.zza(1, localzzd);
          }
        }
      }
      super.writeTo(paramzzaic);
    }
  }
  
  public static final class zzd
    extends zzaik
  {
    private static volatile zzd[] zzbpc;
    public String appId = null;
    public String osVersion = null;
    public String zzbcL = null;
    public String zzbmb = null;
    public String zzbmc = null;
    public String zzbmf = null;
    public Integer zzbpd = null;
    public zzso.zza[] zzbpe = zzso.zza.zzDd();
    public zzso.zze[] zzbpf = zzso.zze.zzDk();
    public Long zzbpg = null;
    public Long zzbph = null;
    public Long zzbpi = null;
    public Long zzbpj = null;
    public Long zzbpk = null;
    public String zzbpl = null;
    public String zzbpm = null;
    public String zzbpn = null;
    public Integer zzbpo = null;
    public Long zzbpp = null;
    public Long zzbpq = null;
    public String zzbpr = null;
    public Boolean zzbps = null;
    public String zzbpt = null;
    public Long zzbpu = null;
    public Integer zzbpv = null;
    public Boolean zzbpw = null;
    
    public zzd()
    {
      this.zzcjk = -1;
    }
    
    public static zzd[] zzDi()
    {
      if (zzbpc == null) {}
      synchronized (zzaii.zzcjj)
      {
        if (zzbpc == null) {
          zzbpc = new zzd[0];
        }
        return zzbpc;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.zzbpd != null) {
        i += zzaic.zzY(1, this.zzbpd.intValue());
      }
      if ((this.zzbpe != null) && (this.zzbpe.length > 0))
      {
        int m = i;
        for (int n = 0; n < this.zzbpe.length; n++)
        {
          zzso.zza localzza = this.zzbpe[n];
          if (localzza != null) {
            m += zzaic.zzc(2, localzza);
          }
        }
        i = m;
      }
      if (this.zzbpf != null)
      {
        int j = this.zzbpf.length;
        int k = 0;
        if (j > 0) {
          while (k < this.zzbpf.length)
          {
            zzso.zze localzze = this.zzbpf[k];
            if (localzze != null) {
              i += zzaic.zzc(3, localzze);
            }
            k++;
          }
        }
      }
      if (this.zzbpg != null) {
        i += zzaic.zzi(4, this.zzbpg.longValue());
      }
      if (this.zzbph != null) {
        i += zzaic.zzi(5, this.zzbph.longValue());
      }
      if (this.zzbpi != null) {
        i += zzaic.zzi(6, this.zzbpi.longValue());
      }
      if (this.zzbpk != null) {
        i += zzaic.zzi(7, this.zzbpk.longValue());
      }
      if (this.zzbpl != null) {
        i += zzaic.zzq(8, this.zzbpl);
      }
      if (this.osVersion != null) {
        i += zzaic.zzq(9, this.osVersion);
      }
      if (this.zzbpm != null) {
        i += zzaic.zzq(10, this.zzbpm);
      }
      if (this.zzbpn != null) {
        i += zzaic.zzq(11, this.zzbpn);
      }
      if (this.zzbpo != null) {
        i += zzaic.zzY(12, this.zzbpo.intValue());
      }
      if (this.zzbmc != null) {
        i += zzaic.zzq(13, this.zzbmc);
      }
      if (this.appId != null) {
        i += zzaic.zzq(14, this.appId);
      }
      if (this.zzbcL != null) {
        i += zzaic.zzq(16, this.zzbcL);
      }
      if (this.zzbpp != null) {
        i += zzaic.zzi(17, this.zzbpp.longValue());
      }
      if (this.zzbpq != null) {
        i += zzaic.zzi(18, this.zzbpq.longValue());
      }
      if (this.zzbpr != null) {
        i += zzaic.zzq(19, this.zzbpr);
      }
      if (this.zzbps != null)
      {
        this.zzbps.booleanValue();
        i += 1 + zzaic.zzsv(20);
      }
      if (this.zzbpt != null) {
        i += zzaic.zzq(21, this.zzbpt);
      }
      if (this.zzbpu != null) {
        i += zzaic.zzi(22, this.zzbpu.longValue());
      }
      if (this.zzbpv != null) {
        i += zzaic.zzY(23, this.zzbpv.intValue());
      }
      if (this.zzbmf != null) {
        i += zzaic.zzq(24, this.zzbmf);
      }
      if (this.zzbmb != null) {
        i += zzaic.zzq(25, this.zzbmb);
      }
      if (this.zzbpj != null) {
        i += zzaic.zzi(26, this.zzbpj.longValue());
      }
      if (this.zzbpw != null)
      {
        this.zzbpw.booleanValue();
        i += 1 + zzaic.zzsv(28);
      }
      return i;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      zzd localzzd;
      do
      {
        do
        {
          return true;
          if (!(paramObject instanceof zzd)) {
            return false;
          }
          localzzd = (zzd)paramObject;
          if (this.zzbpd == null)
          {
            if (localzzd.zzbpd != null) {
              return false;
            }
          }
          else if (!this.zzbpd.equals(localzzd.zzbpd)) {
            return false;
          }
          if (!zzaii.equals(this.zzbpe, localzzd.zzbpe)) {
            return false;
          }
          if (!zzaii.equals(this.zzbpf, localzzd.zzbpf)) {
            return false;
          }
          if (this.zzbpg == null)
          {
            if (localzzd.zzbpg != null) {
              return false;
            }
          }
          else if (!this.zzbpg.equals(localzzd.zzbpg)) {
            return false;
          }
          if (this.zzbph == null)
          {
            if (localzzd.zzbph != null) {
              return false;
            }
          }
          else if (!this.zzbph.equals(localzzd.zzbph)) {
            return false;
          }
          if (this.zzbpi == null)
          {
            if (localzzd.zzbpi != null) {
              return false;
            }
          }
          else if (!this.zzbpi.equals(localzzd.zzbpi)) {
            return false;
          }
          if (this.zzbpj == null)
          {
            if (localzzd.zzbpj != null) {
              return false;
            }
          }
          else if (!this.zzbpj.equals(localzzd.zzbpj)) {
            return false;
          }
          if (this.zzbpk == null)
          {
            if (localzzd.zzbpk != null) {
              return false;
            }
          }
          else if (!this.zzbpk.equals(localzzd.zzbpk)) {
            return false;
          }
          if (this.zzbpl == null)
          {
            if (localzzd.zzbpl != null) {
              return false;
            }
          }
          else if (!this.zzbpl.equals(localzzd.zzbpl)) {
            return false;
          }
          if (this.osVersion == null)
          {
            if (localzzd.osVersion != null) {
              return false;
            }
          }
          else if (!this.osVersion.equals(localzzd.osVersion)) {
            return false;
          }
          if (this.zzbpm == null)
          {
            if (localzzd.zzbpm != null) {
              return false;
            }
          }
          else if (!this.zzbpm.equals(localzzd.zzbpm)) {
            return false;
          }
          if (this.zzbpn == null)
          {
            if (localzzd.zzbpn != null) {
              return false;
            }
          }
          else if (!this.zzbpn.equals(localzzd.zzbpn)) {
            return false;
          }
          if (this.zzbpo == null)
          {
            if (localzzd.zzbpo != null) {
              return false;
            }
          }
          else if (!this.zzbpo.equals(localzzd.zzbpo)) {
            return false;
          }
          if (this.zzbmc == null)
          {
            if (localzzd.zzbmc != null) {
              return false;
            }
          }
          else if (!this.zzbmc.equals(localzzd.zzbmc)) {
            return false;
          }
          if (this.appId == null)
          {
            if (localzzd.appId != null) {
              return false;
            }
          }
          else if (!this.appId.equals(localzzd.appId)) {
            return false;
          }
          if (this.zzbcL == null)
          {
            if (localzzd.zzbcL != null) {
              return false;
            }
          }
          else if (!this.zzbcL.equals(localzzd.zzbcL)) {
            return false;
          }
          if (this.zzbpp == null)
          {
            if (localzzd.zzbpp != null) {
              return false;
            }
          }
          else if (!this.zzbpp.equals(localzzd.zzbpp)) {
            return false;
          }
          if (this.zzbpq == null)
          {
            if (localzzd.zzbpq != null) {
              return false;
            }
          }
          else if (!this.zzbpq.equals(localzzd.zzbpq)) {
            return false;
          }
          if (this.zzbpr == null)
          {
            if (localzzd.zzbpr != null) {
              return false;
            }
          }
          else if (!this.zzbpr.equals(localzzd.zzbpr)) {
            return false;
          }
          if (this.zzbps == null)
          {
            if (localzzd.zzbps != null) {
              return false;
            }
          }
          else if (!this.zzbps.equals(localzzd.zzbps)) {
            return false;
          }
          if (this.zzbpt == null)
          {
            if (localzzd.zzbpt != null) {
              return false;
            }
          }
          else if (!this.zzbpt.equals(localzzd.zzbpt)) {
            return false;
          }
          if (this.zzbpu == null)
          {
            if (localzzd.zzbpu != null) {
              return false;
            }
          }
          else if (!this.zzbpu.equals(localzzd.zzbpu)) {
            return false;
          }
          if (this.zzbpv == null)
          {
            if (localzzd.zzbpv != null) {
              return false;
            }
          }
          else if (!this.zzbpv.equals(localzzd.zzbpv)) {
            return false;
          }
          if (this.zzbmf == null)
          {
            if (localzzd.zzbmf != null) {
              return false;
            }
          }
          else if (!this.zzbmf.equals(localzzd.zzbmf)) {
            return false;
          }
          if (this.zzbmb == null)
          {
            if (localzzd.zzbmb != null) {
              return false;
            }
          }
          else if (!this.zzbmb.equals(localzzd.zzbmb)) {
            return false;
          }
          if (this.zzbpw != null) {
            break;
          }
        } while (localzzd.zzbpw == null);
        return false;
      } while (this.zzbpw.equals(localzzd.zzbpw));
      return false;
    }
    
    public final int hashCode()
    {
      int i = 31 * (527 + getClass().getName().hashCode());
      int j;
      int m;
      label66:
      int i1;
      label85:
      int i3;
      label105:
      int i5;
      label125:
      int i7;
      label145:
      int i9;
      label165:
      int i11;
      label185:
      int i13;
      label205:
      int i15;
      label225:
      int i17;
      label245:
      int i19;
      label265:
      int i21;
      label285:
      int i23;
      label305:
      int i25;
      label325:
      int i27;
      label345:
      int i29;
      label365:
      int i31;
      label385:
      int i33;
      label405:
      int i35;
      label425:
      int i37;
      label445:
      int i39;
      label465:
      int i41;
      label485:
      int i42;
      int i43;
      if (this.zzbpd == null)
      {
        j = 0;
        int k = 31 * (31 * (31 * (j + i) + zzaii.hashCode(this.zzbpe)) + zzaii.hashCode(this.zzbpf));
        if (this.zzbpg != null) {
          break label526;
        }
        m = 0;
        int n = 31 * (m + k);
        if (this.zzbph != null) {
          break label538;
        }
        i1 = 0;
        int i2 = 31 * (i1 + n);
        if (this.zzbpi != null) {
          break label550;
        }
        i3 = 0;
        int i4 = 31 * (i3 + i2);
        if (this.zzbpj != null) {
          break label562;
        }
        i5 = 0;
        int i6 = 31 * (i5 + i4);
        if (this.zzbpk != null) {
          break label574;
        }
        i7 = 0;
        int i8 = 31 * (i7 + i6);
        if (this.zzbpl != null) {
          break label586;
        }
        i9 = 0;
        int i10 = 31 * (i9 + i8);
        if (this.osVersion != null) {
          break label598;
        }
        i11 = 0;
        int i12 = 31 * (i11 + i10);
        if (this.zzbpm != null) {
          break label610;
        }
        i13 = 0;
        int i14 = 31 * (i13 + i12);
        if (this.zzbpn != null) {
          break label622;
        }
        i15 = 0;
        int i16 = 31 * (i15 + i14);
        if (this.zzbpo != null) {
          break label634;
        }
        i17 = 0;
        int i18 = 31 * (i17 + i16);
        if (this.zzbmc != null) {
          break label646;
        }
        i19 = 0;
        int i20 = 31 * (i19 + i18);
        if (this.appId != null) {
          break label658;
        }
        i21 = 0;
        int i22 = 31 * (i21 + i20);
        if (this.zzbcL != null) {
          break label670;
        }
        i23 = 0;
        int i24 = 31 * (i23 + i22);
        if (this.zzbpp != null) {
          break label682;
        }
        i25 = 0;
        int i26 = 31 * (i25 + i24);
        if (this.zzbpq != null) {
          break label694;
        }
        i27 = 0;
        int i28 = 31 * (i27 + i26);
        if (this.zzbpr != null) {
          break label706;
        }
        i29 = 0;
        int i30 = 31 * (i29 + i28);
        if (this.zzbps != null) {
          break label718;
        }
        i31 = 0;
        int i32 = 31 * (i31 + i30);
        if (this.zzbpt != null) {
          break label730;
        }
        i33 = 0;
        int i34 = 31 * (i33 + i32);
        if (this.zzbpu != null) {
          break label742;
        }
        i35 = 0;
        int i36 = 31 * (i35 + i34);
        if (this.zzbpv != null) {
          break label754;
        }
        i37 = 0;
        int i38 = 31 * (i37 + i36);
        if (this.zzbmf != null) {
          break label766;
        }
        i39 = 0;
        int i40 = 31 * (i39 + i38);
        if (this.zzbmb != null) {
          break label778;
        }
        i41 = 0;
        i42 = 31 * (i41 + i40);
        Boolean localBoolean = this.zzbpw;
        i43 = 0;
        if (localBoolean != null) {
          break label790;
        }
      }
      for (;;)
      {
        return i42 + i43;
        j = this.zzbpd.hashCode();
        break;
        label526:
        m = this.zzbpg.hashCode();
        break label66;
        label538:
        i1 = this.zzbph.hashCode();
        break label85;
        label550:
        i3 = this.zzbpi.hashCode();
        break label105;
        label562:
        i5 = this.zzbpj.hashCode();
        break label125;
        label574:
        i7 = this.zzbpk.hashCode();
        break label145;
        label586:
        i9 = this.zzbpl.hashCode();
        break label165;
        label598:
        i11 = this.osVersion.hashCode();
        break label185;
        label610:
        i13 = this.zzbpm.hashCode();
        break label205;
        label622:
        i15 = this.zzbpn.hashCode();
        break label225;
        label634:
        i17 = this.zzbpo.hashCode();
        break label245;
        label646:
        i19 = this.zzbmc.hashCode();
        break label265;
        label658:
        i21 = this.appId.hashCode();
        break label285;
        label670:
        i23 = this.zzbcL.hashCode();
        break label305;
        label682:
        i25 = this.zzbpp.hashCode();
        break label325;
        label694:
        i27 = this.zzbpq.hashCode();
        break label345;
        label706:
        i29 = this.zzbpr.hashCode();
        break label365;
        label718:
        i31 = this.zzbps.hashCode();
        break label385;
        label730:
        i33 = this.zzbpt.hashCode();
        break label405;
        label742:
        i35 = this.zzbpu.hashCode();
        break label425;
        label754:
        i37 = this.zzbpv.hashCode();
        break label445;
        label766:
        i39 = this.zzbmf.hashCode();
        break label465;
        label778:
        i41 = this.zzbmb.hashCode();
        break label485;
        label790:
        i43 = this.zzbpw.hashCode();
      }
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      if (this.zzbpd != null) {
        paramzzaic.zzW(1, this.zzbpd.intValue());
      }
      if ((this.zzbpe != null) && (this.zzbpe.length > 0)) {
        for (int k = 0; k < this.zzbpe.length; k++)
        {
          zzso.zza localzza = this.zzbpe[k];
          if (localzza != null) {
            paramzzaic.zza(2, localzza);
          }
        }
      }
      if (this.zzbpf != null)
      {
        int i = this.zzbpf.length;
        int j = 0;
        if (i > 0) {
          while (j < this.zzbpf.length)
          {
            zzso.zze localzze = this.zzbpf[j];
            if (localzze != null) {
              paramzzaic.zza(3, localzze);
            }
            j++;
          }
        }
      }
      if (this.zzbpg != null) {
        paramzzaic.zzb(4, this.zzbpg.longValue());
      }
      if (this.zzbph != null) {
        paramzzaic.zzb(5, this.zzbph.longValue());
      }
      if (this.zzbpi != null) {
        paramzzaic.zzb(6, this.zzbpi.longValue());
      }
      if (this.zzbpk != null) {
        paramzzaic.zzb(7, this.zzbpk.longValue());
      }
      if (this.zzbpl != null) {
        paramzzaic.zzb(8, this.zzbpl);
      }
      if (this.osVersion != null) {
        paramzzaic.zzb(9, this.osVersion);
      }
      if (this.zzbpm != null) {
        paramzzaic.zzb(10, this.zzbpm);
      }
      if (this.zzbpn != null) {
        paramzzaic.zzb(11, this.zzbpn);
      }
      if (this.zzbpo != null) {
        paramzzaic.zzW(12, this.zzbpo.intValue());
      }
      if (this.zzbmc != null) {
        paramzzaic.zzb(13, this.zzbmc);
      }
      if (this.appId != null) {
        paramzzaic.zzb(14, this.appId);
      }
      if (this.zzbcL != null) {
        paramzzaic.zzb(16, this.zzbcL);
      }
      if (this.zzbpp != null) {
        paramzzaic.zzb(17, this.zzbpp.longValue());
      }
      if (this.zzbpq != null) {
        paramzzaic.zzb(18, this.zzbpq.longValue());
      }
      if (this.zzbpr != null) {
        paramzzaic.zzb(19, this.zzbpr);
      }
      if (this.zzbps != null) {
        paramzzaic.zze(20, this.zzbps.booleanValue());
      }
      if (this.zzbpt != null) {
        paramzzaic.zzb(21, this.zzbpt);
      }
      if (this.zzbpu != null) {
        paramzzaic.zzb(22, this.zzbpu.longValue());
      }
      if (this.zzbpv != null) {
        paramzzaic.zzW(23, this.zzbpv.intValue());
      }
      if (this.zzbmf != null) {
        paramzzaic.zzb(24, this.zzbmf);
      }
      if (this.zzbmb != null) {
        paramzzaic.zzb(25, this.zzbmb);
      }
      if (this.zzbpj != null) {
        paramzzaic.zzb(26, this.zzbpj.longValue());
      }
      if (this.zzbpw != null) {
        paramzzaic.zze(28, this.zzbpw.booleanValue());
      }
      super.writeTo(paramzzaic);
    }
    
    public final zzd zzY(zzaib paramzzaib)
      throws IOException
    {
      for (;;)
      {
        int i = paramzzaib.zzOF();
        switch (i)
        {
        default: 
          if (zzain.zzb(paramzzaib, i)) {
            continue;
          }
        case 0: 
          return this;
        case 8: 
          this.zzbpd = Integer.valueOf(paramzzaib.zzON());
          break;
        case 18: 
          int m = zzain.zzc(paramzzaib, 18);
          if (this.zzbpe == null) {}
          zzso.zza[] arrayOfzza;
          for (int n = 0;; n = this.zzbpe.length)
          {
            arrayOfzza = new zzso.zza[m + n];
            if (n != 0) {
              System.arraycopy(this.zzbpe, 0, arrayOfzza, 0, n);
            }
            while (n < -1 + arrayOfzza.length)
            {
              arrayOfzza[n] = new zzso.zza();
              paramzzaib.zza(arrayOfzza[n]);
              paramzzaib.zzOF();
              n++;
            }
          }
          arrayOfzza[n] = new zzso.zza();
          paramzzaib.zza(arrayOfzza[n]);
          this.zzbpe = arrayOfzza;
          break;
        case 26: 
          int j = zzain.zzc(paramzzaib, 26);
          if (this.zzbpf == null) {}
          zzso.zze[] arrayOfzze;
          for (int k = 0;; k = this.zzbpf.length)
          {
            arrayOfzze = new zzso.zze[j + k];
            if (k != 0) {
              System.arraycopy(this.zzbpf, 0, arrayOfzze, 0, k);
            }
            while (k < -1 + arrayOfzze.length)
            {
              arrayOfzze[k] = new zzso.zze();
              paramzzaib.zza(arrayOfzze[k]);
              paramzzaib.zzOF();
              k++;
            }
          }
          arrayOfzze[k] = new zzso.zze();
          paramzzaib.zza(arrayOfzze[k]);
          this.zzbpf = arrayOfzze;
          break;
        case 32: 
          this.zzbpg = Long.valueOf(paramzzaib.zzOO());
          break;
        case 40: 
          this.zzbph = Long.valueOf(paramzzaib.zzOO());
          break;
        case 48: 
          this.zzbpi = Long.valueOf(paramzzaib.zzOO());
          break;
        case 56: 
          this.zzbpk = Long.valueOf(paramzzaib.zzOO());
          break;
        case 66: 
          this.zzbpl = paramzzaib.readString();
          break;
        case 74: 
          this.osVersion = paramzzaib.readString();
          break;
        case 82: 
          this.zzbpm = paramzzaib.readString();
          break;
        case 90: 
          this.zzbpn = paramzzaib.readString();
          break;
        case 96: 
          this.zzbpo = Integer.valueOf(paramzzaib.zzON());
          break;
        case 106: 
          this.zzbmc = paramzzaib.readString();
          break;
        case 114: 
          this.appId = paramzzaib.readString();
          break;
        case 130: 
          this.zzbcL = paramzzaib.readString();
          break;
        case 136: 
          this.zzbpp = Long.valueOf(paramzzaib.zzOO());
          break;
        case 144: 
          this.zzbpq = Long.valueOf(paramzzaib.zzOO());
          break;
        case 154: 
          this.zzbpr = paramzzaib.readString();
          break;
        case 160: 
          this.zzbps = Boolean.valueOf(paramzzaib.zzOK());
          break;
        case 170: 
          this.zzbpt = paramzzaib.readString();
          break;
        case 176: 
          this.zzbpu = Long.valueOf(paramzzaib.zzOO());
          break;
        case 184: 
          this.zzbpv = Integer.valueOf(paramzzaib.zzON());
          break;
        case 194: 
          this.zzbmf = paramzzaib.readString();
          break;
        case 202: 
          this.zzbmb = paramzzaib.readString();
          break;
        case 208: 
          this.zzbpj = Long.valueOf(paramzzaib.zzOO());
          break;
        }
        this.zzbpw = Boolean.valueOf(paramzzaib.zzOK());
      }
    }
  }
  
  public static final class zze
    extends zzaik
  {
    private static volatile zze[] zzbpx;
    public String name = null;
    public String stringValue = null;
    public Float zzboU = null;
    public Long zzbpa = null;
    public Long zzbpy = null;
    
    public zze()
    {
      this.zzcjk = -1;
    }
    
    public static zze[] zzDk()
    {
      if (zzbpx == null) {}
      synchronized (zzaii.zzcjj)
      {
        if (zzbpx == null) {
          zzbpx = new zze[0];
        }
        return zzbpx;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.zzbpy != null) {
        i += zzaic.zzi(1, this.zzbpy.longValue());
      }
      if (this.name != null) {
        i += zzaic.zzq(2, this.name);
      }
      if (this.stringValue != null) {
        i += zzaic.zzq(3, this.stringValue);
      }
      if (this.zzbpa != null) {
        i += zzaic.zzi(4, this.zzbpa.longValue());
      }
      if (this.zzboU != null)
      {
        this.zzboU.floatValue();
        i += 4 + zzaic.zzsv(5);
      }
      return i;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      zze localzze;
      do
      {
        do
        {
          return true;
          if (!(paramObject instanceof zze)) {
            return false;
          }
          localzze = (zze)paramObject;
          if (this.zzbpy == null)
          {
            if (localzze.zzbpy != null) {
              return false;
            }
          }
          else if (!this.zzbpy.equals(localzze.zzbpy)) {
            return false;
          }
          if (this.name == null)
          {
            if (localzze.name != null) {
              return false;
            }
          }
          else if (!this.name.equals(localzze.name)) {
            return false;
          }
          if (this.stringValue == null)
          {
            if (localzze.stringValue != null) {
              return false;
            }
          }
          else if (!this.stringValue.equals(localzze.stringValue)) {
            return false;
          }
          if (this.zzbpa == null)
          {
            if (localzze.zzbpa != null) {
              return false;
            }
          }
          else if (!this.zzbpa.equals(localzze.zzbpa)) {
            return false;
          }
          if (this.zzboU != null) {
            break;
          }
        } while (localzze.zzboU == null);
        return false;
      } while (this.zzboU.equals(localzze.zzboU));
      return false;
    }
    
    public final int hashCode()
    {
      int i = 31 * (527 + getClass().getName().hashCode());
      int j;
      int m;
      label44:
      int i1;
      label63:
      int i3;
      label83:
      int i4;
      int i5;
      if (this.zzbpy == null)
      {
        j = 0;
        int k = 31 * (j + i);
        if (this.name != null) {
          break label124;
        }
        m = 0;
        int n = 31 * (m + k);
        if (this.stringValue != null) {
          break label136;
        }
        i1 = 0;
        int i2 = 31 * (i1 + n);
        if (this.zzbpa != null) {
          break label148;
        }
        i3 = 0;
        i4 = 31 * (i3 + i2);
        Float localFloat = this.zzboU;
        i5 = 0;
        if (localFloat != null) {
          break label160;
        }
      }
      for (;;)
      {
        return i4 + i5;
        j = this.zzbpy.hashCode();
        break;
        label124:
        m = this.name.hashCode();
        break label44;
        label136:
        i1 = this.stringValue.hashCode();
        break label63;
        label148:
        i3 = this.zzbpa.hashCode();
        break label83;
        label160:
        i5 = this.zzboU.hashCode();
      }
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      if (this.zzbpy != null) {
        paramzzaic.zzb(1, this.zzbpy.longValue());
      }
      if (this.name != null) {
        paramzzaic.zzb(2, this.name);
      }
      if (this.stringValue != null) {
        paramzzaic.zzb(3, this.stringValue);
      }
      if (this.zzbpa != null) {
        paramzzaic.zzb(4, this.zzbpa.longValue());
      }
      if (this.zzboU != null) {
        paramzzaic.zzb(5, this.zzboU.floatValue());
      }
      super.writeTo(paramzzaic);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzso
 * JD-Core Version:    0.7.0.1
 */