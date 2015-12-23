package com.google.android.finsky.api.model;

import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.protos.DocV2;
import java.util.List;

public abstract class ContainerList<T>
  extends PaginatedList<T, Document>
{
  public Document mContainerDocument;
  protected DfeApi mDfeApi;
  
  protected ContainerList(DfeApi paramDfeApi, String paramString)
  {
    super(paramString);
    this.mDfeApi = paramDfeApi;
  }
  
  protected ContainerList(DfeApi paramDfeApi, String paramString, boolean paramBoolean)
  {
    super(paramString, paramBoolean);
    this.mDfeApi = paramDfeApi;
  }
  
  protected ContainerList(DfeApi paramDfeApi, List<Document> paramList, String paramString, boolean paramBoolean)
  {
    super(paramList, paramString, paramBoolean);
    this.mDfeApi = paramDfeApi;
  }
  
  public int getBackendId()
  {
    if (this.mContainerDocument != null) {
      return this.mContainerDocument.mDocument.backendId;
    }
    return 0;
  }
  
  public final void setDfeApi(DfeApi paramDfeApi)
  {
    this.mDfeApi = paramDfeApi;
  }
  
  protected final Document[] updateContainerAndGetItems(DocV2 paramDocV2)
  {
    if (paramDocV2 != null)
    {
      this.mContainerDocument = new Document(paramDocV2);
      int i = paramDocV2.child.length;
      arrayOfDocument = new Document[i];
      for (int j = 0; j < i; j++) {
        arrayOfDocument[j] = new Document(paramDocV2.child[j]);
      }
    }
    Document[] arrayOfDocument = new Document[0];
    return arrayOfDocument;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.ContainerList
 * JD-Core Version:    0.7.0.1
 */