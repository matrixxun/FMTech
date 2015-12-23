package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class zzahj
  extends zzaid<zzahj>
{
  public zza[] zzchW = zza.zzOl();
  
  public zzahj()
  {
    this.zzcja = null;
    this.zzcjk = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.zzchW != null) && (this.zzchW.length > 0)) {
      for (int j = 0; j < this.zzchW.length; j++)
      {
        zza localzza = this.zzchW[j];
        if (localzza != null) {
          i += zzaic.zzc(1, localzza);
        }
      }
    }
    return i;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    zzahj localzzahj;
    do
    {
      return true;
      if (!(paramObject instanceof zzahj)) {
        return false;
      }
      localzzahj = (zzahj)paramObject;
      if (!zzaii.equals(this.zzchW, localzzahj.zzchW)) {
        return false;
      }
      if ((this.zzcja != null) && (!this.zzcja.isEmpty())) {
        break;
      }
    } while ((localzzahj.zzcja == null) || (localzzahj.zzcja.isEmpty()));
    return false;
    return this.zzcja.equals(localzzahj.zzcja);
  }
  
  public final int hashCode()
  {
    int i = 31 * (31 * (527 + getClass().getName().hashCode()) + zzaii.hashCode(this.zzchW));
    if ((this.zzcja == null) || (this.zzcja.isEmpty())) {}
    for (int j = 0;; j = this.zzcja.hashCode()) {
      return j + i;
    }
  }
  
  public final void writeTo(zzaic paramzzaic)
    throws IOException
  {
    if ((this.zzchW != null) && (this.zzchW.length > 0)) {
      for (int i = 0; i < this.zzchW.length; i++)
      {
        zza localzza = this.zzchW[i];
        if (localzza != null) {
          paramzzaic.zza(1, localzza);
        }
      }
    }
    super.writeTo(paramzzaic);
  }
  
  public static final class zza
    extends zzaid<zza>
  {
    private static volatile zza[] zzchX;
    public String name = "";
    public zza zzchY = null;
    
    public zza()
    {
      this.zzcja = null;
      this.zzcjk = -1;
    }
    
    public static zza[] zzOl()
    {
      if (zzchX == null) {}
      synchronized (zzaii.zzcjj)
      {
        if (zzchX == null) {
          zzchX = new zza[0];
        }
        return zzchX;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize() + zzaic.zzq(1, this.name);
      if (this.zzchY != null) {
        i += zzaic.zzc(2, this.zzchY);
      }
      return i;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (paramObject == this) {}
      zza localzza;
      do
      {
        return true;
        if (!(paramObject instanceof zza)) {
          return false;
        }
        localzza = (zza)paramObject;
        if (this.name == null)
        {
          if (localzza.name != null) {
            return false;
          }
        }
        else if (!this.name.equals(localzza.name)) {
          return false;
        }
        if (this.zzchY == null)
        {
          if (localzza.zzchY != null) {
            return false;
          }
        }
        else if (!this.zzchY.equals(localzza.zzchY)) {
          return false;
        }
        if ((this.zzcja != null) && (!this.zzcja.isEmpty())) {
          break;
        }
      } while ((localzza.zzcja == null) || (localzza.zzcja.isEmpty()));
      return false;
      return this.zzcja.equals(localzza.zzcja);
    }
    
    public final int hashCode()
    {
      int i = 31 * (527 + getClass().getName().hashCode());
      int j;
      int m;
      label44:
      int n;
      int i1;
      if (this.name == null)
      {
        j = 0;
        int k = 31 * (j + i);
        if (this.zzchY != null) {
          break label101;
        }
        m = 0;
        n = 31 * (m + k);
        zzaig localzzaig = this.zzcja;
        i1 = 0;
        if (localzzaig != null)
        {
          boolean bool = this.zzcja.isEmpty();
          i1 = 0;
          if (!bool) {
            break label113;
          }
        }
      }
      for (;;)
      {
        return n + i1;
        j = this.name.hashCode();
        break;
        label101:
        m = this.zzchY.hashCode();
        break label44;
        label113:
        i1 = this.zzcja.hashCode();
      }
    }
    
    public final void writeTo(zzaic paramzzaic)
      throws IOException
    {
      paramzzaic.zzb(1, this.name);
      if (this.zzchY != null) {
        paramzzaic.zza(2, this.zzchY);
      }
      super.writeTo(paramzzaic);
    }
    
    public static final class zza
      extends zzaid<zza>
    {
      private static volatile zza[] zzchZ;
      public int type = 1;
      public zza zzcia = null;
      
      public zza()
      {
        this.zzcja = null;
        this.zzcjk = -1;
      }
      
      public static zza[] zzOn()
      {
        if (zzchZ == null) {}
        synchronized (zzaii.zzcjj)
        {
          if (zzchZ == null) {
            zzchZ = new zza[0];
          }
          return zzchZ;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize() + zzaic.zzY(1, this.type);
        if (this.zzcia != null) {
          i += zzaic.zzc(2, this.zzcia);
        }
        return i;
      }
      
      public final boolean equals(Object paramObject)
      {
        if (paramObject == this) {}
        zza localzza;
        do
        {
          return true;
          if (!(paramObject instanceof zza)) {
            return false;
          }
          localzza = (zza)paramObject;
          if (this.type != localzza.type) {
            return false;
          }
          if (this.zzcia == null)
          {
            if (localzza.zzcia != null) {
              return false;
            }
          }
          else if (!this.zzcia.equals(localzza.zzcia)) {
            return false;
          }
          if ((this.zzcja != null) && (!this.zzcja.isEmpty())) {
            break;
          }
        } while ((localzza.zzcja == null) || (localzza.zzcja.isEmpty()));
        return false;
        return this.zzcja.equals(localzza.zzcja);
      }
      
      public final int hashCode()
      {
        int i = 31 * (31 * (527 + getClass().getName().hashCode()) + this.type);
        int j;
        int k;
        int m;
        if (this.zzcia == null)
        {
          j = 0;
          k = 31 * (j + i);
          zzaig localzzaig = this.zzcja;
          m = 0;
          if (localzzaig != null)
          {
            boolean bool = this.zzcja.isEmpty();
            m = 0;
            if (!bool) {
              break label89;
            }
          }
        }
        for (;;)
        {
          return k + m;
          j = this.zzcia.hashCode();
          break;
          label89:
          m = this.zzcja.hashCode();
        }
      }
      
      public final void writeTo(zzaic paramzzaic)
        throws IOException
      {
        paramzzaic.zzW(1, this.type);
        if (this.zzcia != null) {
          paramzzaic.zza(2, this.zzcia);
        }
        super.writeTo(paramzzaic);
      }
      
      public static final class zza
        extends zzaid<zza>
      {
        public byte[] zzcib = zzain.zzcjr;
        public String zzcic = "";
        public double zzcid = 0.0D;
        public float zzcie = 0.0F;
        public long zzcif = 0L;
        public int zzcig = 0;
        public int zzcih = 0;
        public boolean zzcii = false;
        public zzahj.zza[] zzcij = zzahj.zza.zzOl();
        public zzahj.zza.zza[] zzcik = zzahj.zza.zza.zzOn();
        public String[] zzcil = zzain.zzcjp;
        public long[] zzcim = zzain.zzchq;
        public float[] zzcin = zzain.zzcjm;
        public long zzcio = 0L;
        
        public zza()
        {
          this.zzcja = null;
          this.zzcjk = -1;
        }
        
        protected final int computeSerializedSize()
        {
          int i = 0;
          int j = super.computeSerializedSize();
          if (!Arrays.equals(this.zzcib, zzain.zzcjr)) {
            j += zzaic.zzb(1, this.zzcib);
          }
          if (!this.zzcic.equals("")) {
            j += zzaic.zzq(2, this.zzcic);
          }
          if (Double.doubleToLongBits(this.zzcid) != Double.doubleToLongBits(0.0D)) {
            j += 8 + zzaic.zzsv(3);
          }
          if (Float.floatToIntBits(this.zzcie) != Float.floatToIntBits(0.0F)) {
            j += 4 + zzaic.zzsv(4);
          }
          if (this.zzcif != 0L) {
            j += zzaic.zzi(5, this.zzcif);
          }
          if (this.zzcig != 0) {
            j += zzaic.zzY(6, this.zzcig);
          }
          if (this.zzcih != 0)
          {
            int i6 = this.zzcih;
            j += zzaic.zzsv(7) + zzaic.zzsx(zzaic.zzsz(i6));
          }
          if (this.zzcii) {
            j += 1 + zzaic.zzsv(8);
          }
          if ((this.zzcij != null) && (this.zzcij.length > 0))
          {
            int i4 = j;
            for (int i5 = 0; i5 < this.zzcij.length; i5++)
            {
              zzahj.zza localzza1 = this.zzcij[i5];
              if (localzza1 != null) {
                i4 += zzaic.zzc(9, localzza1);
              }
            }
            j = i4;
          }
          if ((this.zzcik != null) && (this.zzcik.length > 0))
          {
            int i2 = j;
            for (int i3 = 0; i3 < this.zzcik.length; i3++)
            {
              zzahj.zza.zza localzza = this.zzcik[i3];
              if (localzza != null) {
                i2 += zzaic.zzc(10, localzza);
              }
            }
            j = i2;
          }
          if ((this.zzcil != null) && (this.zzcil.length > 0))
          {
            int m = 0;
            int n = 0;
            int i1 = 0;
            while (m < this.zzcil.length)
            {
              String str = this.zzcil[m];
              if (str != null)
              {
                i1++;
                n += zzaic.zzjw(str);
              }
              m++;
            }
            j = j + n + i1 * 1;
          }
          if ((this.zzcim != null) && (this.zzcim.length > 0))
          {
            int k = 0;
            while (i < this.zzcim.length)
            {
              k += zzaic.zzaI(this.zzcim[i]);
              i++;
            }
            j = j + k + 1 * this.zzcim.length;
          }
          if (this.zzcio != 0L) {
            j += zzaic.zzi(13, this.zzcio);
          }
          if ((this.zzcin != null) && (this.zzcin.length > 0)) {
            j = j + 4 * this.zzcin.length + 1 * this.zzcin.length;
          }
          return j;
        }
        
        public final boolean equals(Object paramObject)
        {
          if (paramObject == this) {}
          zza localzza;
          do
          {
            return true;
            if (!(paramObject instanceof zza)) {
              return false;
            }
            localzza = (zza)paramObject;
            if (!Arrays.equals(this.zzcib, localzza.zzcib)) {
              return false;
            }
            if (this.zzcic == null)
            {
              if (localzza.zzcic != null) {
                return false;
              }
            }
            else if (!this.zzcic.equals(localzza.zzcic)) {
              return false;
            }
            if (Double.doubleToLongBits(this.zzcid) != Double.doubleToLongBits(localzza.zzcid)) {
              return false;
            }
            if (Float.floatToIntBits(this.zzcie) != Float.floatToIntBits(localzza.zzcie)) {
              return false;
            }
            if (this.zzcif != localzza.zzcif) {
              return false;
            }
            if (this.zzcig != localzza.zzcig) {
              return false;
            }
            if (this.zzcih != localzza.zzcih) {
              return false;
            }
            if (this.zzcii != localzza.zzcii) {
              return false;
            }
            if (!zzaii.equals(this.zzcij, localzza.zzcij)) {
              return false;
            }
            if (!zzaii.equals(this.zzcik, localzza.zzcik)) {
              return false;
            }
            if (!zzaii.equals(this.zzcil, localzza.zzcil)) {
              return false;
            }
            if (!zzaii.equals(this.zzcim, localzza.zzcim)) {
              return false;
            }
            if (!zzaii.equals(this.zzcin, localzza.zzcin)) {
              return false;
            }
            if (this.zzcio != localzza.zzcio) {
              return false;
            }
            if ((this.zzcja != null) && (!this.zzcja.isEmpty())) {
              break;
            }
          } while ((localzza.zzcja == null) || (localzza.zzcja.isEmpty()));
          return false;
          return this.zzcja.equals(localzza.zzcja);
        }
        
        public final int hashCode()
        {
          int i = 31 * (31 * (527 + getClass().getName().hashCode()) + Arrays.hashCode(this.zzcib));
          int j;
          int n;
          label126:
          int i1;
          int i2;
          if (this.zzcic == null)
          {
            j = 0;
            int k = j + i;
            long l = Double.doubleToLongBits(this.zzcid);
            int m = 31 * (31 * (31 * (31 * (31 * (k * 31 + (int)(l ^ l >>> 32)) + Float.floatToIntBits(this.zzcie)) + (int)(this.zzcif ^ this.zzcif >>> 32)) + this.zzcig) + this.zzcih);
            if (!this.zzcii) {
              break label256;
            }
            n = 1231;
            i1 = 31 * (31 * (31 * (31 * (31 * (31 * (31 * (n + m) + zzaii.hashCode(this.zzcij)) + zzaii.hashCode(this.zzcik)) + zzaii.hashCode(this.zzcil)) + zzaii.hashCode(this.zzcim)) + zzaii.hashCode(this.zzcin)) + (int)(this.zzcio ^ this.zzcio >>> 32));
            zzaig localzzaig = this.zzcja;
            i2 = 0;
            if (localzzaig != null)
            {
              boolean bool = this.zzcja.isEmpty();
              i2 = 0;
              if (!bool) {
                break label264;
              }
            }
          }
          for (;;)
          {
            return i1 + i2;
            j = this.zzcic.hashCode();
            break;
            label256:
            n = 1237;
            break label126;
            label264:
            i2 = this.zzcja.hashCode();
          }
        }
        
        public final void writeTo(zzaic paramzzaic)
          throws IOException
        {
          if (!Arrays.equals(this.zzcib, zzain.zzcjr)) {
            paramzzaic.zza(1, this.zzcib);
          }
          if (!this.zzcic.equals("")) {
            paramzzaic.zzb(2, this.zzcic);
          }
          if (Double.doubleToLongBits(this.zzcid) != Double.doubleToLongBits(0.0D))
          {
            double d = this.zzcid;
            paramzzaic.zzaa(3, 1);
            long l = Double.doubleToLongBits(d);
            if (paramzzaic.zzciZ.remaining() < 8) {
              throw new zzaic.zza(paramzzaic.zzciZ.position(), paramzzaic.zzciZ.limit());
            }
            paramzzaic.zzciZ.putLong(l);
          }
          if (Float.floatToIntBits(this.zzcie) != Float.floatToIntBits(0.0F)) {
            paramzzaic.zzb(4, this.zzcie);
          }
          if (this.zzcif != 0L) {
            paramzzaic.zzb(5, this.zzcif);
          }
          if (this.zzcig != 0) {
            paramzzaic.zzW(6, this.zzcig);
          }
          if (this.zzcih != 0)
          {
            int i2 = this.zzcih;
            paramzzaic.zzaa(7, 0);
            paramzzaic.zzsw(zzaic.zzsz(i2));
          }
          if (this.zzcii) {
            paramzzaic.zze(8, this.zzcii);
          }
          if ((this.zzcij != null) && (this.zzcij.length > 0)) {
            for (int i1 = 0; i1 < this.zzcij.length; i1++)
            {
              zzahj.zza localzza1 = this.zzcij[i1];
              if (localzza1 != null) {
                paramzzaic.zza(9, localzza1);
              }
            }
          }
          if ((this.zzcik != null) && (this.zzcik.length > 0)) {
            for (int n = 0; n < this.zzcik.length; n++)
            {
              zzahj.zza.zza localzza = this.zzcik[n];
              if (localzza != null) {
                paramzzaic.zza(10, localzza);
              }
            }
          }
          if ((this.zzcil != null) && (this.zzcil.length > 0)) {
            for (int m = 0; m < this.zzcil.length; m++)
            {
              String str = this.zzcil[m];
              if (str != null) {
                paramzzaic.zzb(11, str);
              }
            }
          }
          if ((this.zzcim != null) && (this.zzcim.length > 0)) {
            for (int k = 0; k < this.zzcim.length; k++) {
              paramzzaic.zzb(12, this.zzcim[k]);
            }
          }
          if (this.zzcio != 0L) {
            paramzzaic.zzb(13, this.zzcio);
          }
          if (this.zzcin != null)
          {
            int i = this.zzcin.length;
            int j = 0;
            if (i > 0) {
              while (j < this.zzcin.length)
              {
                paramzzaic.zzb(14, this.zzcin[j]);
                j++;
              }
            }
          }
          super.writeTo(paramzzaic);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzahj
 * JD-Core Version:    0.7.0.1
 */