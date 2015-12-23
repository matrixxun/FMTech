package com.google.android.finsky.api.model;

import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import java.util.ArrayList;
import java.util.List;

public class MultiDfeBulkDetails
  extends DfeModel
  implements OnDataChangedListener
{
  private VolleyError mLastVolleyError;
  protected final List<DfeBulkDetails> mRequests = new ArrayList();
  
  public final void addRequest(DfeApi paramDfeApi, List<String> paramList, boolean paramBoolean)
  {
    DfeBulkDetails localDfeBulkDetails = new DfeBulkDetails(paramDfeApi, paramList, paramBoolean);
    localDfeBulkDetails.addDataChangedListener(this);
    localDfeBulkDetails.addErrorListener(this);
    this.mRequests.add(localDfeBulkDetails);
  }
  
  protected final void clearErrors()
  {
    throw new UnsupportedOperationException();
  }
  
  protected void collateResponses() {}
  
  public final VolleyError getVolleyError()
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean inErrorState()
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean isReady()
  {
    for (int i = 0; i < this.mRequests.size(); i++) {
      if (!((DfeBulkDetails)this.mRequests.get(i)).isReady()) {
        return false;
      }
    }
    return true;
  }
  
  public final void onDataChanged()
  {
    for (int i = 0; i < this.mRequests.size(); i++)
    {
      DfeBulkDetails localDfeBulkDetails = (DfeBulkDetails)this.mRequests.get(i);
      if (localDfeBulkDetails.inErrorState()) {}
      while (!localDfeBulkDetails.isReady()) {
        return;
      }
    }
    collateResponses();
    notifyDataSetChanged();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    if (this.mLastVolleyError == null)
    {
      notifyErrorOccured(paramVolleyError);
      this.mLastVolleyError = paramVolleyError;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.MultiDfeBulkDetails
 * JD-Core Version:    0.7.0.1
 */