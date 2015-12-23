package com.google.android.finsky.api.model;

import com.android.volley.Request;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.ListResponse;
import com.google.android.finsky.protos.UserContext;
import java.util.List;

public final class DfeList
  extends ContainerList<ListResponse>
{
  public String mFilteredDocId = null;
  public String mInitialListUrl;
  private UserContext mUserContext;
  
  public DfeList(DfeApi paramDfeApi, String paramString, boolean paramBoolean)
  {
    this(paramDfeApi, paramString, paramBoolean, null);
  }
  
  public DfeList(DfeApi paramDfeApi, String paramString, boolean paramBoolean, UserContext paramUserContext)
  {
    super(paramDfeApi, paramString, paramBoolean);
    this.mInitialListUrl = paramString;
    this.mUserContext = paramUserContext;
  }
  
  public DfeList(DfeApi paramDfeApi, List<Document> paramList, String paramString, boolean paramBoolean)
  {
    super(paramDfeApi, paramList, paramString, paramBoolean);
    this.mInitialListUrl = paramString;
    this.mUserContext = null;
  }
  
  public final void clearDataAndReplaceInitialUrl(String paramString)
  {
    this.mInitialListUrl = paramString;
    super.clearDataAndReplaceInitialUrl(paramString);
  }
  
  protected final void clearDiskCache()
  {
    for (int i = 0; i < this.mUrlOffsetList.size(); i++) {
      this.mDfeApi.invalidateListCache$505cbf4b(((PaginatedList.UrlOffsetPair)this.mUrlOffsetList.get(i)).url);
    }
  }
  
  protected final Request<?> makeRequest(String paramString)
  {
    return this.mDfeApi.getList(paramString, this.mUserContext, this, this);
  }
  
  public final void setInitialDocs(Document[] paramArrayOfDocument)
  {
    if (paramArrayOfDocument == null) {}
    for (;;)
    {
      return;
      int i = Math.min(paramArrayOfDocument.length, this.mItems.size());
      for (int j = 0; j < i; j++) {
        this.mItems.set(j, paramArrayOfDocument[j]);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeList
 * JD-Core Version:    0.7.0.1
 */