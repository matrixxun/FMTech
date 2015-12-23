package com.google.android.gms.people;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzmu.zza;
import com.google.android.gms.internal.zztl;
import com.google.android.gms.internal.zztm;
import com.google.android.gms.internal.zztn;
import com.google.android.gms.internal.zzto;
import com.google.android.gms.internal.zztp;
import com.google.android.gms.internal.zztq;
import com.google.android.gms.internal.zztr;
import com.google.android.gms.internal.zzts;
import com.google.android.gms.internal.zztt;
import com.google.android.gms.people.identity.IdentityApi;
import com.google.android.gms.people.identity.internal.zzh;
import com.google.android.gms.people.internal.zzn;

public final class People
{
  public static final Api<PeopleOptions1p> API_1P;
  public static final Autocomplete AutocompleteApi;
  public static final ContactsSync ContactsSyncApi = new zztm();
  public static final Graph GraphApi;
  public static final GraphUpdate GraphUpdateApi;
  public static final IdentityApi IdentityApi;
  public static final Images ImageApi;
  public static final InteractionFeedback InteractionFeedbackApi;
  public static final InternalApi InternalApi;
  public static final Notifications NotificationApi = new zzts();
  public static final Sync SyncApi;
  static final Api.zza<zzn, PeopleOptions1p> zzaRb;
  public static final Api.zzc<zzn> zzbsQ = new Api.zzc();
  
  static
  {
    zzaRb = new Api.zza() {};
    API_1P = new Api("People.API_1P", zzaRb, zzbsQ);
    IdentityApi = new zzh();
    GraphApi = new zztn();
    GraphUpdateApi = new zzto();
    ImageApi = new zztp();
    SyncApi = new zztt();
    AutocompleteApi = new zztl();
    InteractionFeedbackApi = new zztq();
    InternalApi = new zztr();
  }
  
  public static final class PeopleOptions1p
    implements Api.ApiOptions.HasOptions
  {
    final int zzbsR;
    
    private PeopleOptions1p(Builder paramBuilder)
    {
      this.zzbsR = paramBuilder.zzbsR;
    }
    
    public static final class Builder
    {
      public int zzbsR = -1;
    }
  }
  
  public static abstract interface ReleasableResult
    extends Releasable, Result
  {}
  
  public static abstract class zza<R extends Result>
    extends zzmu.zza<R, zzn>
  {
    public zza(GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.People
 * JD-Core Version:    0.7.0.1
 */