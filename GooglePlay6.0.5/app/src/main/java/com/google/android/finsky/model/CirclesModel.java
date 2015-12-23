package com.google.android.finsky.model;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.GPlusUtils;
import com.google.android.finsky.utils.GPlusUtils.1;
import com.google.android.finsky.utils.GPlusUtils.2;
import com.google.android.finsky.utils.GPlusUtils.3;
import com.google.android.finsky.utils.GPlusUtils.CirclePickerListener;
import com.google.android.finsky.utils.GPlusUtils.CirclesLoader;
import com.google.android.finsky.utils.GPlusUtils.GetCirclesListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.people.data.AudienceMember;
import com.google.android.play.dfe.api.PlayDfeApi;
import java.util.ArrayList;
import java.util.List;

public final class CirclesModel
  implements GPlusUtils.CirclePickerListener
{
  public ArrayList<AudienceMember> mCircles;
  public CirclesModelListener mCirclesModelListener;
  private boolean mHasRequestedCircles;
  public final String mOwnerAccountName;
  public final Document mTargetPersonDoc;
  
  public CirclesModel(Document paramDocument, String paramString)
  {
    this.mTargetPersonDoc = paramDocument;
    this.mOwnerAccountName = paramString;
  }
  
  final void invokeListener()
  {
    if (this.mCirclesModelListener != null) {
      this.mCirclesModelListener.onCirclesUpdate(this.mCircles);
    }
  }
  
  public final void launchCirclePicker(FragmentActivity paramFragmentActivity)
  {
    if (this.mCircles == null) {
      return;
    }
    String str = this.mTargetPersonDoc.mDocument.backendDocid;
    ArrayList localArrayList = this.mCircles;
    FinskyApp.get().getPlayDfeApi(null).getPlusProfile(new GPlusUtils.1(paramFragmentActivity, str, localArrayList, this), new GPlusUtils.2(), true);
  }
  
  public final void loadCircles(Context paramContext, GoogleApiClient paramGoogleApiClient)
  {
    boolean bool = true;
    if (this.mHasRequestedCircles) {
      return;
    }
    this.mHasRequestedCircles = bool;
    String str1 = this.mTargetPersonDoc.mDocument.backendDocid;
    GPlusUtils.GetCirclesListener local1 = new GPlusUtils.GetCirclesListener()
    {
      public final void onCirclesLoaded(ArrayList<AudienceMember> paramAnonymousArrayList)
      {
        CirclesModel.this.mCircles = paramAnonymousArrayList;
        CirclesModel.this.invokeListener();
      }
      
      public final void onCirclesLoadedFailed()
      {
        CirclesModel.this.mCircles = new ArrayList();
        CirclesModel.this.invokeListener();
      }
    };
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext) == 0)
    {
      label44:
      if (!bool) {
        break label154;
      }
      GPlusUtils.CirclesLoader localCirclesLoader = new GPlusUtils.CirclesLoader(paramGoogleApiClient, FinskyApp.get().getCurrentAccountName(), str1, local1);
      localCirclesLoader.mPeopleClient.registerConnectionCallbacks(localCirclesLoader);
      localCirclesLoader.mPeopleClient.registerConnectionFailedListener(localCirclesLoader);
      if ((!localCirclesLoader.mPeopleClient.isConnected()) && (!localCirclesLoader.mPeopleClient.isConnecting())) {
        localCirclesLoader.mPeopleClient.connect();
      }
    }
    for (;;)
    {
      String str2 = this.mTargetPersonDoc.mDocument.backendDocid;
      if ((!GPlusUtils.sIsCirclePickerActive) || (str2 != GPlusUtils.sLastUserToAddObfuscatedId)) {
        break;
      }
      GPlusUtils.sLastCirclePickerListener = this;
      return;
      bool = false;
      break label44;
      label154:
      new Handler().post(new GPlusUtils.3(local1));
    }
  }
  
  public final void onCirclesSelected(ArrayList<AudienceMember> paramArrayList)
  {
    this.mCircles = paramArrayList;
    invokeListener();
  }
  
  public static abstract interface CirclesModelListener
  {
    public abstract void onCirclesUpdate(List<AudienceMember> paramList);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.model.CirclesModel
 * JD-Core Version:    0.7.0.1
 */