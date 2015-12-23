package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.people.Graph;
import com.google.android.gms.people.Graph.LoadCirclesOptions;
import com.google.android.gms.people.Graph.LoadCirclesResult;
import com.google.android.gms.people.Graph.LoadPeopleOptions;
import com.google.android.gms.people.Graph.LoadPeopleResult;
import com.google.android.gms.people.People.zza;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.model.CircleBuffer;
import com.google.android.gms.people.model.PersonBuffer;

public final class zztn
  implements Graph
{
  public final PendingResult<Graph.LoadCirclesResult> loadCircles$3c0ce7d1(GoogleApiClient paramGoogleApiClient, final String paramString, final Graph.LoadCirclesOptions paramLoadCirclesOptions)
  {
    if (Log.isLoggable("PeopleClientCall", 3)) {
      zzl.zzi("loadCircles", new Object[] { paramString, null, paramLoadCirclesOptions });
    }
    paramGoogleApiClient.zza(new People.zza(paramGoogleApiClient) {});
  }
  
  public final PendingResult<Graph.LoadPeopleResult> loadPeople$7acb640d(GoogleApiClient paramGoogleApiClient, final String paramString, final Graph.LoadPeopleOptions paramLoadPeopleOptions)
  {
    if (Log.isLoggable("PeopleClientCall", 3)) {
      zzl.zzi("loadPeople", new Object[] { paramString, null, paramLoadPeopleOptions });
    }
    paramGoogleApiClient.zza(new People.zza(paramGoogleApiClient) {});
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zztn
 * JD-Core Version:    0.7.0.1
 */