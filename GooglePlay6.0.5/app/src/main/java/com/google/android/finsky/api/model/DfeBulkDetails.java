package com.google.android.finsky.api.model;

import com.android.volley.Response.Listener;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.Details.BulkDetailsEntry;
import com.google.android.finsky.protos.Details.BulkDetailsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DfeBulkDetails
  extends DfeModel
  implements Response.Listener<Details.BulkDetailsResponse>
{
  private Details.BulkDetailsResponse mBulkDetailsResponse;
  final DfeApi mDfeApi;
  private final List<String> mDocids;
  
  public DfeBulkDetails(DfeApi paramDfeApi, List<String> paramList, boolean paramBoolean)
  {
    this.mDfeApi = paramDfeApi;
    this.mDocids = paramList;
    paramDfeApi.getBulkDetails(this.mDocids, paramBoolean, this, this);
  }
  
  public final List<Document> getDocuments()
  {
    if (this.mBulkDetailsResponse == null)
    {
      localObject = null;
      return localObject;
    }
    Object localObject = new ArrayList();
    int i = 0;
    label21:
    DocV2 localDocV2;
    if (i < this.mBulkDetailsResponse.entry.length)
    {
      localDocV2 = this.mBulkDetailsResponse.entry[i].doc;
      if (localDocV2 != null) {
        break label89;
      }
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.mDocids.get(i);
        FinskyLog.v("Null document for requested docid: %s ", arrayOfObject);
      }
    }
    for (;;)
    {
      i++;
      break label21;
      break;
      label89:
      ((List)localObject).add(new Document(localDocV2));
    }
  }
  
  public final List<String> getMissingDocIds()
  {
    Object localObject;
    if (this.mBulkDetailsResponse == null) {
      localObject = Collections.emptyList();
    }
    for (;;)
    {
      return localObject;
      int i = 0;
      int j = this.mBulkDetailsResponse.entry.length;
      for (;;)
      {
        int k = 0;
        if (i < j)
        {
          if (this.mBulkDetailsResponse.entry[i].doc == null) {
            k = 1;
          }
        }
        else
        {
          if (k != 0) {
            break;
          }
          return Collections.emptyList();
        }
        i++;
      }
      localObject = Lists.newArrayList(this.mDocids);
      int m = 0;
      int n = this.mBulkDetailsResponse.entry.length;
      while (m < n)
      {
        DocV2 localDocV2 = this.mBulkDetailsResponse.entry[m].doc;
        if (localDocV2 != null) {
          ((List)localObject).remove(localDocV2.docid);
        }
        m++;
      }
    }
  }
  
  public final boolean isReady()
  {
    return this.mBulkDetailsResponse != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeBulkDetails
 * JD-Core Version:    0.7.0.1
 */