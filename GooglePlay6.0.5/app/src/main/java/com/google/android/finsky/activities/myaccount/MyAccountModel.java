package com.google.android.finsky.activities.myaccount;

import android.support.v4.util.ArrayMap;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.MyAccountResponse;
import com.google.android.finsky.protos.SectionMetadata;
import java.util.Map;

public final class MyAccountModel
{
  private DfeApi mDfeApi;
  public Map<Integer, MyAccountCardData> mMyAccountCardDataMap;
  
  public MyAccountModel(MyAccountResponse paramMyAccountResponse, DfeApi paramDfeApi)
  {
    this.mDfeApi = paramDfeApi;
    this.mMyAccountCardDataMap = new ArrayMap();
    if (paramMyAccountResponse.subscriptionsMetadata != null) {
      addMyAccountCardData(1, new DfeList(this.mDfeApi, paramMyAccountResponse.subscriptionsMetadata.listUrl, true), paramMyAccountResponse.subscriptionsMetadata.header);
    }
    if (paramMyAccountResponse.rewardsMetadata != null) {
      addMyAccountCardData(2, new DfeList(this.mDfeApi, paramMyAccountResponse.rewardsMetadata.listUrl, true), paramMyAccountResponse.rewardsMetadata.header);
    }
    if (paramMyAccountResponse.purchaseHistoryMetadata != null) {
      addMyAccountCardData(0, new DfeList(this.mDfeApi, paramMyAccountResponse.purchaseHistoryMetadata.listUrl, true), paramMyAccountResponse.purchaseHistoryMetadata.header);
    }
  }
  
  private void addMyAccountCardData(int paramInt, DfeList paramDfeList, String paramString)
  {
    MyAccountCardData localMyAccountCardData = new MyAccountCardData((byte)0);
    localMyAccountCardData.mHeader = paramString;
    localMyAccountCardData.mDfeList = paramDfeList;
    this.mMyAccountCardDataMap.put(Integer.valueOf(paramInt), localMyAccountCardData);
  }
  
  public final DfeList getDfeList(int paramInt)
  {
    return ((MyAccountCardData)this.mMyAccountCardDataMap.get(Integer.valueOf(paramInt))).mDfeList;
  }
  
  public final String getHeader(int paramInt)
  {
    return ((MyAccountCardData)this.mMyAccountCardDataMap.get(Integer.valueOf(paramInt))).mHeader;
  }
  
  public final boolean isCardTypeEnabled(int paramInt)
  {
    if (paramInt == 1) {
      return (this.mMyAccountCardDataMap.containsKey(Integer.valueOf(paramInt))) && (FinskyApp.get().getExperiments(this.mDfeApi.getAccountName()).isEnabled(12603131L));
    }
    return this.mMyAccountCardDataMap.containsKey(Integer.valueOf(paramInt));
  }
  
  private final class MyAccountCardData
  {
    public DfeList mDfeList;
    public Response.ErrorListener mErrorListener;
    public boolean mFailedToLoad;
    public String mHeader;
    public OnDataChangedListener mOnDataChangedListener;
    
    private MyAccountCardData() {}
    
    public final void removeDataChangedListener()
    {
      if (this.mOnDataChangedListener != null)
      {
        this.mDfeList.removeDataChangedListener(this.mOnDataChangedListener);
        this.mOnDataChangedListener = null;
      }
      if (this.mErrorListener != null)
      {
        this.mDfeList.removeErrorListener(this.mErrorListener);
        this.mErrorListener = null;
      }
    }
    
    public final void startLoad(OnDataChangedListener paramOnDataChangedListener, boolean paramBoolean)
    {
      removeDataChangedListener();
      if (paramBoolean) {
        this.mDfeList.retryLoadItems();
      }
      for (;;)
      {
        this.mFailedToLoad = false;
        this.mOnDataChangedListener = paramOnDataChangedListener;
        this.mDfeList.addDataChangedListener(this.mOnDataChangedListener);
        this.mErrorListener = new Response.ErrorListener()
        {
          public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
          {
            MyAccountModel.MyAccountCardData.this.mFailedToLoad = true;
            MyAccountModel.MyAccountCardData.this.mOnDataChangedListener.onDataChanged();
          }
        };
        this.mDfeList.addErrorListener(this.mErrorListener);
        return;
        this.mDfeList.startLoadItems();
      }
    }
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.finsky.activities.myaccount.MyAccountModel

 * JD-Core Version:    0.7.0.1

 */