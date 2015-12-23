package com.google.android.finsky.api.model;

import com.google.android.finsky.protos.DocV2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class MultiDfeList<T extends ContainerList<?>>
{
  public final Map<String, DfeList> mClusterData;
  public T mContainerList;
  
  public MultiDfeList(T paramT)
  {
    this.mContainerList = paramT;
    this.mClusterData = new HashMap();
  }
  
  public final void flushData()
  {
    this.mContainerList.flushUnusedPages();
    Iterator localIterator = this.mClusterData.entrySet().iterator();
    label147:
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      DfeList localDfeList = (DfeList)localEntry.getValue();
      String str = (String)localEntry.getKey();
      int i = this.mContainerList.getCount();
      for (int j = 0;; j++)
      {
        if (j >= i) {
          break label147;
        }
        Document localDocument = (Document)this.mContainerList.getItem(j, false);
        if ((localDocument != null) && (str.equals(localDocument.mDocument.docid)))
        {
          if (this.mContainerList.hasUnflushedItem(j))
          {
            localDfeList.flushUnusedPages();
            break;
          }
          localDfeList.flushAllPages();
          break;
        }
      }
    }
    this.mContainerList.mCurrentRequest = null;
  }
  
  public final DfeList getSecondaryList(String paramString)
  {
    return (DfeList)this.mClusterData.get(paramString);
  }
  
  public final Document getTopLevelItem(int paramInt)
  {
    return (Document)this.mContainerList.getItem(paramInt);
  }
  
  public final void setSecondaryList(String paramString, DfeList paramDfeList)
  {
    this.mClusterData.put(paramString, paramDfeList);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.MultiDfeList
 * JD-Core Version:    0.7.0.1
 */