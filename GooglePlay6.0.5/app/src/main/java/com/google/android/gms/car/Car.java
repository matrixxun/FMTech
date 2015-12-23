package com.google.android.gms.car;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public final class Car
{
  public static final Api<CarOptions> API = new Api("Car.API", zzTR, zzTQ);
  public static final CarApi CarApi = new zza((byte)0);
  public static final CarFirstPartyApi CarFirstPartyApi = new zzb((byte)0);
  static final Api.zzc<zzf> zzTQ = new Api.zzc();
  private static final Api.zza<zzf, CarOptions> zzTR = new Api.zza() {};
  
  public static GoogleApiClient buildGoogleApiClientForCar(Context paramContext, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, CarConnectionListener paramCarConnectionListener)
  {
    return new GoogleApiClient.Builder(paramContext).addApi(API, new CarOptions(new Car.CarOptions.Builder(paramCarConnectionListener, (byte)0), (byte)0)).addConnectionCallbacks(paramConnectionCallbacks).addOnConnectionFailedListener(paramOnConnectionFailedListener).build();
  }
  
  public static abstract interface CarActivityStartListener {}
  
  public static abstract interface CarApi
    extends Car.CarApiHideFirstParty
  {}
  
  public static abstract interface CarApiHideFirstParty
  {
    public abstract boolean isConnectedToCar(GoogleApiClient paramGoogleApiClient)
      throws IllegalStateException;
  }
  
  public static abstract interface CarConnectionListener
  {
    public abstract void onConnected$13462e();
    
    public abstract void onDisconnected();
  }
  
  public static abstract interface CarFirstPartyApi {}
  
  public static final class CarOptions
    implements Api.ApiOptions.HasOptions
  {
    final Car.CarConnectionListener zzabE;
    
    private CarOptions(Builder paramBuilder)
    {
      this.zzabE = paramBuilder.zzabF;
    }
    
    public static final class Builder
    {
      final Car.CarConnectionListener zzabF;
      
      private Builder(Car.CarConnectionListener paramCarConnectionListener)
      {
        this.zzabF = paramCarConnectionListener;
      }
    }
  }
  
  private static final class zza
    implements Car.CarApi
  {
    public final boolean isConnectedToCar(GoogleApiClient paramGoogleApiClient)
      throws IllegalStateException
    {
      Car.zze(paramGoogleApiClient);
      return ((zzf)paramGoogleApiClient.zza(Car.zzTQ)).zzlG();
    }
  }
  
  private static final class zzb
    implements Car.CarFirstPartyApi
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.car.Car
 * JD-Core Version:    0.7.0.1
 */