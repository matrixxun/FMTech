package com.google.android.gms.people.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.internal.zzmu.zzb;
import com.google.android.gms.internal.zznl;
import com.google.android.gms.internal.zznl.zzb;
import com.google.android.gms.people.Graph.LoadCirclesResult;
import com.google.android.gms.people.Graph.LoadPeopleResult;
import com.google.android.gms.people.internal.agg.zzd;
import com.google.android.gms.people.model.CircleBuffer;
import com.google.android.gms.people.model.PersonBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class zzn
  extends zzj<zzg>
{
  private static volatile Bundle zzbxi;
  private static volatile Bundle zzbxj;
  public final Context mContext;
  public final String zzUb;
  public final String zzbxg;
  private final HashMap<Object, zzr> zzbxh = new HashMap();
  private Long zzbxk = null;
  
  public zzn(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zzf paramzzf)
  {
    super(paramContext.getApplicationContext(), paramLooper, 5, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
    this.mContext = paramContext;
    this.zzbxg = paramString;
    this.zzUb = paramzzf.zzUb;
  }
  
  private void zzQ(Bundle paramBundle)
  {
    if (paramBundle == null) {}
    for (;;)
    {
      return;
      try
      {
        zzd.zzaR(paramBundle.getBoolean("use_contactables_api", true));
        zzm.zzbxd.zza(paramBundle.getStringArray("config.url_uncompress.patterns"), paramBundle.getStringArray("config.url_uncompress.replacements"));
        zzbxi = paramBundle.getBundle("config.email_type_map");
        zzbxj = paramBundle.getBundle("config.phone_type_map");
      }
      finally {}
    }
  }
  
  public final void disconnect()
  {
    for (;;)
    {
      zzr localzzr;
      synchronized (this.zzbxh)
      {
        if (!isConnected()) {
          break;
        }
        Iterator localIterator = this.zzbxh.values().iterator();
        if (!localIterator.hasNext()) {
          break;
        }
        localzzr = (zzr)localIterator.next();
        localzzr.zzbxv.mListener = null;
      }
      try
      {
        ((zzg)super.zzqn()).zza(localzzr, false, null, null, 0);
      }
      catch (RemoteException localRemoteException)
      {
        zzo.zzb("PeopleClient", "Failed to unregister listener", localRemoteException);
        continue;
        localObject = finally;
        throw localObject;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        zzo.zzb("PeopleClient", "PeopleService is in unexpected state", localIllegalStateException);
      }
    }
    this.zzbxh.clear();
    super.disconnect();
  }
  
  protected final void zza(int paramInt1, IBinder paramIBinder, Bundle paramBundle, int paramInt2)
  {
    if ((paramInt1 == 0) && (paramBundle != null)) {
      zzQ(paramBundle.getBundle("post_init_configuration"));
    }
    if (paramBundle == null) {}
    for (Bundle localBundle = null;; localBundle = paramBundle.getBundle("post_init_resolution"))
    {
      super.zza(paramInt1, paramIBinder, localBundle, paramInt2);
      return;
    }
  }
  
  public final void zza(zzmu.zzb<Graph.LoadCirclesResult> paramzzb, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, boolean paramBoolean)
  {
    super.zzqm();
    zzp localzzp = new zzp(paramzzb);
    try
    {
      ((zzg)super.zzqn()).zza(localzzp, paramString1, paramString2, paramString3, paramInt, paramString4, paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localzzp.zza(8, null, null);
    }
  }
  
  public final void zza(zzmu.zzb<Graph.LoadPeopleResult> paramzzb, String paramString1, String paramString2, String paramString3, Collection<String> paramCollection, int paramInt1, boolean paramBoolean, long paramLong, String paramString4, int paramInt2, int paramInt3, int paramInt4)
  {
    super.zzqm();
    zzaa localzzaa = new zzaa(paramzzb);
    try
    {
      zzg localzzg = (zzg)super.zzqn();
      if (paramCollection == null) {}
      for (Object localObject = null;; localObject = new ArrayList(paramCollection))
      {
        localzzg.zza(localzzaa, paramString1, paramString2, paramString3, (List)localObject, paramInt1, paramBoolean, paramLong, paramString4, paramInt2, paramInt3, paramInt4);
        return;
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localzzaa.zza(8, null, null);
    }
  }
  
  protected final String zzgp()
  {
    return "com.google.android.gms.people.service.START";
  }
  
  protected final String zzgq()
  {
    return "com.google.android.gms.people.internal.IPeopleService";
  }
  
  protected final Bundle zzjM()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("social_client_application_id", this.zzbxg);
    localBundle.putString("real_client_package_name", this.zzUb);
    localBundle.putBoolean("support_new_image_callback", true);
    return localBundle;
  }
  
  private static final class zzaa
    extends zza
  {
    private final zzmu.zzb<Graph.LoadPeopleResult> zzbeT;
    
    public zzaa(zzmu.zzb<Graph.LoadPeopleResult> paramzzb)
    {
      this.zzbeT = paramzzb;
    }
    
    public final void zza(int paramInt, Bundle paramBundle, DataHolder paramDataHolder)
    {
      if (zzo.zzdj(3)) {
        zzo.zzH("PeopleClient", "People callback: status=" + paramInt + "\nresolution=" + paramBundle + "\nholder=" + paramDataHolder);
      }
      Status localStatus = zzn.zzb$3fda7968(paramInt, paramBundle);
      PersonBuffer localPersonBuffer = zzn.zzak(paramDataHolder);
      this.zzbeT.zzu(new zzn.zzal(localStatus, localPersonBuffer));
    }
  }
  
  private static final class zzal
    implements Graph.LoadPeopleResult
  {
    private final Status zzUc;
    private final PersonBuffer zzbxF;
    
    public zzal(Status paramStatus, PersonBuffer paramPersonBuffer)
    {
      this.zzUc = paramStatus;
      this.zzbxF = paramPersonBuffer;
    }
    
    public final PersonBuffer getPeople()
    {
      return this.zzbxF;
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
    
    public final void release()
    {
      if (this.zzbxF != null) {
        this.zzbxF.release();
      }
    }
  }
  
  private static final class zzc
    implements Graph.LoadCirclesResult
  {
    private final Status zzUc;
    private final CircleBuffer zzbxm;
    
    public zzc(Status paramStatus, CircleBuffer paramCircleBuffer)
    {
      this.zzUc = paramStatus;
      this.zzbxm = paramCircleBuffer;
    }
    
    public final CircleBuffer getCircles()
    {
      return this.zzbxm;
    }
    
    public final Status getStatus()
    {
      return this.zzUc;
    }
    
    public final void release()
    {
      if (this.zzbxm != null) {
        this.zzbxm.release();
      }
    }
  }
  
  private static final class zze
    implements zznl.zzb<Object>
  {
    private final String mAccount;
    private final String zzbsk;
    private final int zzbxo;
    
    public zze(String paramString1, String paramString2, int paramInt)
    {
      this.mAccount = paramString1;
      this.zzbsk = paramString2;
      this.zzbxo = paramInt;
    }
    
    public final void zzoR() {}
  }
  
  private static final class zzp
    extends zza
  {
    private final zzmu.zzb<Graph.LoadCirclesResult> zzbeT;
    
    public zzp(zzmu.zzb<Graph.LoadCirclesResult> paramzzb)
    {
      this.zzbeT = paramzzb;
    }
    
    public final void zza(int paramInt, Bundle paramBundle, DataHolder paramDataHolder)
    {
      if (zzo.zzdj(3)) {
        zzo.zzH("PeopleClient", "Circles callback: status=" + paramInt + "\nresolution=" + paramBundle + "\nholder=" + paramDataHolder);
      }
      Status localStatus = zzn.zzb$3fda7968(paramInt, paramBundle);
      if (paramDataHolder == null) {}
      for (CircleBuffer localCircleBuffer = null;; localCircleBuffer = new CircleBuffer(paramDataHolder))
      {
        this.zzbeT.zzu(new zzn.zzc(localStatus, localCircleBuffer));
        return;
      }
    }
  }
  
  private static final class zzr
    extends zza
  {
    final zznl<Object> zzbxv;
    
    public final void zza(int paramInt, Bundle paramBundle1, Bundle paramBundle2)
    {
      if (zzo.zzdj(3)) {
        zzo.zzH("PeopleClient", "Bundle callback: status=" + paramInt + "\nresolution=" + paramBundle1 + "\nbundle=" + paramBundle2);
      }
      if (paramInt != 0)
      {
        if (zzo.zzdj(5)) {
          Log.w("PeopleClient", "Non-success data changed callback received.");
        }
        return;
      }
      this.zzbxv.zza(new zzn.zze(paramBundle2.getString("account"), paramBundle2.getString("pagegaiaid"), paramBundle2.getInt("scope")));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzn
 * JD-Core Version:    0.7.0.1
 */