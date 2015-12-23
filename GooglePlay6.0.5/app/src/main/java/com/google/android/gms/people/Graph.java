package com.google.android.gms.people;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.model.CircleBuffer;
import com.google.android.gms.people.model.PersonBuffer;
import java.util.Collection;

public abstract interface Graph
{
  public abstract PendingResult<LoadCirclesResult> loadCircles$3c0ce7d1(GoogleApiClient paramGoogleApiClient, String paramString, LoadCirclesOptions paramLoadCirclesOptions);
  
  public abstract PendingResult<LoadPeopleResult> loadPeople$7acb640d(GoogleApiClient paramGoogleApiClient, String paramString, LoadPeopleOptions paramLoadPeopleOptions);
  
  public static final class LoadCirclesOptions
  {
    public static final LoadCirclesOptions zzbsA = new LoadCirclesOptions();
    public int zzbsB = -999;
    public String zzbsC;
    public String zzbsD;
    public boolean zzbsE;
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[8];
      arrayOfObject[0] = "mFilterCircleType";
      arrayOfObject[1] = Integer.valueOf(this.zzbsB);
      arrayOfObject[2] = "mFilterCircleId";
      arrayOfObject[3] = this.zzbsC;
      arrayOfObject[4] = "mFilterCircleNamePrefix";
      arrayOfObject[5] = this.zzbsD;
      arrayOfObject[6] = "mGetVisibility";
      arrayOfObject[7] = Boolean.valueOf(this.zzbsE);
      return zzl.zzd(arrayOfObject);
    }
  }
  
  public static abstract interface LoadCirclesResult
    extends People.ReleasableResult
  {
    public abstract CircleBuffer getCircles();
  }
  
  public static final class LoadPeopleOptions
  {
    public static final LoadPeopleOptions zzbsJ = new LoadPeopleOptions();
    public String zzNL;
    public String zzauT;
    public Collection<String> zzbsK;
    public long zzbsL;
    public boolean zzbss;
    public int zzbst = 2097151;
    public int zzbsu;
    public int zzbsx = 7;
    public int zzbsz = 0;
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[18];
      arrayOfObject[0] = "mCircleId";
      arrayOfObject[1] = this.zzauT;
      arrayOfObject[2] = "mQualifiedIds";
      arrayOfObject[3] = this.zzbsK;
      arrayOfObject[4] = "mProjection";
      arrayOfObject[5] = Integer.valueOf(this.zzbst);
      arrayOfObject[6] = "mPeopleOnly";
      arrayOfObject[7] = Boolean.valueOf(this.zzbss);
      arrayOfObject[8] = "mChangedSince";
      arrayOfObject[9] = Long.valueOf(this.zzbsL);
      arrayOfObject[10] = "mQuery";
      arrayOfObject[11] = this.zzNL;
      arrayOfObject[12] = "mSearchFields";
      arrayOfObject[13] = Integer.valueOf(this.zzbsx);
      arrayOfObject[14] = "mSortOrder";
      arrayOfObject[15] = Integer.valueOf(this.zzbsz);
      arrayOfObject[16] = "mExtraColumns";
      arrayOfObject[17] = Integer.valueOf(this.zzbsu);
      return zzl.zzd(arrayOfObject);
    }
  }
  
  public static abstract interface LoadPeopleResult
    extends People.ReleasableResult
  {
    public abstract PersonBuffer getPeople();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.Graph
 * JD-Core Version:    0.7.0.1
 */