package com.google.android.finsky.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.common.people.data.AudienceMember;
import com.google.android.gms.people.Graph;
import com.google.android.gms.people.Graph.LoadCirclesOptions;
import com.google.android.gms.people.Graph.LoadCirclesResult;
import com.google.android.gms.people.Graph.LoadPeopleOptions;
import com.google.android.gms.people.Graph.LoadPeopleResult;
import com.google.android.gms.people.People;
import java.util.ArrayList;
import java.util.List;

public final class GPlusUtils
{
  public static boolean sIsCirclePickerActive;
  public static CirclePickerListener sLastCirclePickerListener;
  public static String sLastUserToAddObfuscatedId;
  
  static boolean checkGooglePlayServicesShowErrorDialogs(Activity paramActivity)
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramActivity);
    if (i == 0) {
      return true;
    }
    GooglePlayServicesUtil.getErrorDialog$65f13a54(i, paramActivity).show();
    return false;
  }
  
  static String gaiaIdToPeopleQualifiedId(String paramString)
  {
    return "g:" + paramString;
  }
  
  public static String getCirclesString(List<AudienceMember> paramList, Resources paramResources)
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return paramResources.getString(2131361928);
    }
    if (paramList.size() == 1) {
      return ((AudienceMember)paramList.get(0)).zzUe;
    }
    int i = paramList.size();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramList.size());
    return paramResources.getQuantityString(2131296256, i, arrayOfObject);
  }
  
  public static void launchGPlusSignUp(Activity paramActivity)
  {
    if (checkGooglePlayServicesShowErrorDialogs(paramActivity)) {
      paramActivity.startActivityForResult(SignUp.newSignUpIntent$d24fdd6(FinskyApp.get().getCurrentAccountName(), paramActivity.getResources().getString(2131362200)), 35);
    }
  }
  
  public static abstract interface CirclePickerListener
  {
    public abstract void onCirclesSelected(ArrayList<AudienceMember> paramArrayList);
  }
  
  private static final class CirclesLoader
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    String[] mBelongingCircleIds;
    ArrayList<AudienceMember> mCircles;
    boolean mCirclesLoaded;
    private final String mCurrentAccountName;
    final GPlusUtils.GetCirclesListener mGetCirclesListener;
    public final GoogleApiClient mPeopleClient;
    boolean mPeopleLoaded;
    private final String mUserToLookUpGaiaObfId;
    
    public CirclesLoader(GoogleApiClient paramGoogleApiClient, String paramString1, String paramString2, GPlusUtils.GetCirclesListener paramGetCirclesListener)
    {
      this.mPeopleClient = paramGoogleApiClient;
      this.mCurrentAccountName = paramString1;
      this.mUserToLookUpGaiaObfId = paramString2;
      this.mGetCirclesListener = paramGetCirclesListener;
    }
    
    final void computeBelongingCircles()
    {
      if ((!this.mCirclesLoaded) || (!this.mPeopleLoaded)) {
        return;
      }
      ArrayList localArrayList = new ArrayList();
      if (this.mBelongingCircleIds != null)
      {
        int i = 0;
        int j = this.mBelongingCircleIds.length;
        while (i < j)
        {
          String str = this.mBelongingCircleIds[i];
          int k = 0;
          int m = this.mCircles.size();
          while (k < m)
          {
            AudienceMember localAudienceMember = (AudienceMember)this.mCircles.get(k);
            if (str.equals(localAudienceMember.zzauT)) {
              localArrayList.add(localAudienceMember);
            }
            k++;
          }
          i++;
        }
      }
      this.mPeopleClient.unregisterConnectionCallbacks(this);
      this.mPeopleClient.unregisterConnectionFailedListener(this);
      this.mGetCirclesListener.onCirclesLoaded(localArrayList);
    }
    
    public final void onConnected(Bundle paramBundle)
    {
      Graph.LoadCirclesOptions localLoadCirclesOptions = new Graph.LoadCirclesOptions();
      localLoadCirclesOptions.zzbsB = -1;
      People.GraphApi.loadCircles$3c0ce7d1(this.mPeopleClient, this.mCurrentAccountName, localLoadCirclesOptions).setResultCallback(new ResultCallback() {});
      Graph.LoadPeopleOptions localLoadPeopleOptions = new Graph.LoadPeopleOptions();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(GPlusUtils.gaiaIdToPeopleQualifiedId(this.mUserToLookUpGaiaObfId));
      localLoadPeopleOptions.zzbsK = localArrayList;
      People.GraphApi.loadPeople$7acb640d(this.mPeopleClient, this.mCurrentAccountName, localLoadPeopleOptions).setResultCallback(new ResultCallback() {});
    }
    
    public final void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      Utils.ensureOnMainThread();
      this.mPeopleClient.unregisterConnectionCallbacks(this);
      this.mPeopleClient.unregisterConnectionFailedListener(this);
      this.mGetCirclesListener.onCirclesLoadedFailed();
    }
    
    public final void onConnectionSuspended(int paramInt)
    {
      this.mPeopleClient.unregisterConnectionCallbacks(this);
      this.mPeopleClient.unregisterConnectionFailedListener(this);
    }
  }
  
  public static abstract interface GetCirclesListener
  {
    public abstract void onCirclesLoaded(ArrayList<AudienceMember> paramArrayList);
    
    public abstract void onCirclesLoadedFailed();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GPlusUtils
 * JD-Core Version:    0.7.0.1
 */