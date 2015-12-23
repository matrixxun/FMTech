package com.google.android.finsky.detailspage;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;

public abstract class SimpleDfeListModule<D extends Data>
  extends FinskyModule<D>
  implements Response.ErrorListener, OnDataChangedListener
{
  public void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mModuleData == null)
    {
      this.mModuleData = createModuleDataWithListUrl(paramDocument1);
      if (this.mModuleData != null) {
        startModule();
      }
    }
  }
  
  protected abstract D createModuleDataWithListUrl(Document paramDocument);
  
  public void onDestroyModule()
  {
    if ((this.mModuleData != null) && (((Data)this.mModuleData).dfeList != null))
    {
      ((Data)this.mModuleData).dfeList.removeDataChangedListener(this);
      ((Data)this.mModuleData).dfeList.removeErrorListener(this);
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError) {}
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null) {
      startModule();
    }
  }
  
  public boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).dfeList != null) && (((Data)this.mModuleData).dfeList.isReady());
  }
  
  protected void startModule()
  {
    DfeList localDfeList = ((Data)this.mModuleData).dfeList;
    if (localDfeList == null)
    {
      localDfeList = new DfeList(this.mDfeApi, ((Data)this.mModuleData).listUrl, false);
      ((Data)this.mModuleData).dfeList = localDfeList;
    }
    localDfeList.addDataChangedListener(this);
    localDfeList.addErrorListener(this);
    if (!localDfeList.isReady()) {
      localDfeList.startLoadItems();
    }
  }
  
  protected static class Data
    extends FinskyModule.ModuleData
  {
    DfeList dfeList;
    String listUrl;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SimpleDfeListModule
 * JD-Core Version:    0.7.0.1
 */