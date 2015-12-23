package com.google.android.finsky.api.model;

import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PaginatedList<T, D>
  extends DfeModel
  implements Response.Listener<T>
{
  protected final boolean mAutoLoadNextPage;
  private int mCurrentOffset;
  public Request<?> mCurrentRequest;
  protected final List<D> mItems = new ArrayList();
  private boolean mItemsRemoved;
  private int mItemsUntilEndCount = 4;
  private int mLastPositionRequested;
  protected T mLastResponse;
  public boolean mMoreAvailable;
  protected List<UrlOffsetPair> mUrlOffsetList = new ArrayList();
  public int mWindowDistance = 12;
  
  protected PaginatedList(String paramString)
  {
    this(paramString, true);
  }
  
  protected PaginatedList(String paramString, boolean paramBoolean)
  {
    this.mUrlOffsetList.add(new UrlOffsetPair(0, paramString));
    this.mMoreAvailable = true;
    this.mAutoLoadNextPage = paramBoolean;
  }
  
  protected PaginatedList(List<D> paramList, String paramString, boolean paramBoolean)
  {
    this.mItems.addAll(paramList);
    this.mUrlOffsetList.add(new UrlOffsetPair(this.mItems.size(), paramString));
    if (!TextUtils.isEmpty(paramString)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mMoreAvailable = bool;
      this.mAutoLoadNextPage = paramBoolean;
      return;
    }
  }
  
  private void requestMoreItemsIfNoRequestExists(UrlOffsetPair paramUrlOffsetPair)
  {
    if (inErrorState()) {}
    do
    {
      return;
      if ((this.mCurrentRequest == null) || (this.mCurrentRequest.isCanceled())) {
        break;
      }
    } while (this.mCurrentRequest.getUrl().endsWith(paramUrlOffsetPair.url));
    this.mCurrentRequest.cancel();
    this.mCurrentOffset = paramUrlOffsetPair.offset;
    this.mCurrentRequest = makeRequest(paramUrlOffsetPair.url);
  }
  
  public void clearDataAndReplaceInitialUrl(String paramString)
  {
    this.mUrlOffsetList.clear();
    this.mUrlOffsetList.add(new UrlOffsetPair(0, paramString));
    resetItems();
  }
  
  protected abstract void clearDiskCache();
  
  public final void flushAllPages()
  {
    for (int i = 0; i < this.mItems.size(); i++) {
      this.mItems.set(i, null);
    }
  }
  
  public final void flushUnusedPages()
  {
    if (FinskyApp.get().getExperiments().isEnabled(12603103L)) {}
    for (;;)
    {
      return;
      if (this.mLastPositionRequested >= 0) {
        for (int i = 0; i < this.mItems.size(); i++) {
          if ((i < -1 + (this.mLastPositionRequested - this.mWindowDistance)) || (i >= this.mLastPositionRequested + this.mWindowDistance)) {
            this.mItems.set(i, null);
          }
        }
      }
    }
  }
  
  public final int getCount()
  {
    return this.mItems.size();
  }
  
  public final D getItem(int paramInt)
  {
    return getItem(paramInt, true);
  }
  
  public final D getItem(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.mLastPositionRequested = paramInt;
    }
    if (paramInt < 0) {
      throw new IllegalArgumentException("Can't return an item with a negative index: " + paramInt);
    }
    int i = getCount();
    Object localObject1 = null;
    if (paramInt < i)
    {
      localObject1 = this.mItems.get(paramInt);
      if ((this.mAutoLoadNextPage) && (this.mMoreAvailable) && (paramInt >= getCount() - this.mItemsUntilEndCount))
      {
        if (this.mItemsRemoved) {
          for (int j = 0; j < this.mUrlOffsetList.size(); j++) {
            if (((UrlOffsetPair)this.mUrlOffsetList.get(j)).offset > this.mItems.size())
            {
              int k = Math.max(1, j);
              while (this.mUrlOffsetList.size() > k) {
                this.mUrlOffsetList.remove(-1 + this.mUrlOffsetList.size());
              }
              UrlOffsetPair localUrlOffsetPair3 = (UrlOffsetPair)this.mUrlOffsetList.get(-1 + this.mUrlOffsetList.size());
              if (paramBoolean) {
                requestMoreItemsIfNoRequestExists(localUrlOffsetPair3);
              }
            }
          }
        }
        UrlOffsetPair localUrlOffsetPair2 = (UrlOffsetPair)this.mUrlOffsetList.get(-1 + this.mUrlOffsetList.size());
        if (paramBoolean) {
          requestMoreItemsIfNoRequestExists(localUrlOffsetPair2);
        }
      }
      if (localObject1 == null)
      {
        Object localObject2 = null;
        Iterator localIterator = this.mUrlOffsetList.iterator();
        while (localIterator.hasNext())
        {
          UrlOffsetPair localUrlOffsetPair1 = (UrlOffsetPair)localIterator.next();
          if (localUrlOffsetPair1.offset > paramInt) {
            break;
          }
          localObject2 = localUrlOffsetPair1;
        }
        requestMoreItemsIfNoRequestExists(localObject2);
      }
    }
    return localObject1;
  }
  
  protected abstract D[] getItemsFromResponse(T paramT);
  
  public final List<String> getListPageUrls()
  {
    ArrayList localArrayList = new ArrayList(this.mUrlOffsetList.size());
    Iterator localIterator = this.mUrlOffsetList.iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((UrlOffsetPair)localIterator.next()).url);
    }
    return localArrayList;
  }
  
  protected abstract String getNextPageUrl(T paramT);
  
  public final boolean hasItem(int paramInt)
  {
    return paramInt < getCount();
  }
  
  public final boolean hasUnflushedItem(int paramInt)
  {
    return (hasItem(paramInt)) && (this.mItems.get(paramInt) != null);
  }
  
  public final boolean isInTransit()
  {
    return (this.mCurrentRequest != null) && (!this.mCurrentRequest.isCanceled());
  }
  
  public final boolean isReady()
  {
    return (this.mLastResponse != null) || (this.mItems.size() > 0);
  }
  
  protected abstract Request<?> makeRequest(String paramString);
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    this.mCurrentRequest = null;
    super.onErrorResponse(paramVolleyError);
  }
  
  public final void onResponse(T paramT)
  {
    clearErrors();
    this.mLastResponse = paramT;
    int i = this.mItems.size();
    Object[] arrayOfObject = getItemsFromResponse(paramT);
    int j = arrayOfObject.length;
    int k;
    if (this.mItemsUntilEndCount <= 0)
    {
      this.mItemsUntilEndCount = 4;
      k = 0;
      label44:
      if (k >= arrayOfObject.length) {
        break label129;
      }
      if (k + this.mCurrentOffset >= this.mItems.size()) {
        break label112;
      }
      this.mItems.set(k + this.mCurrentOffset, arrayOfObject[k]);
    }
    for (;;)
    {
      k++;
      break label44;
      this.mItemsUntilEndCount = Math.max(1, j / 4);
      break;
      label112:
      this.mItems.add(arrayOfObject[k]);
    }
    label129:
    String str = getNextPageUrl(paramT);
    if ((!TextUtils.isEmpty(str)) && ((this.mCurrentOffset == i) || (this.mItemsRemoved))) {
      this.mUrlOffsetList.add(new UrlOffsetPair(this.mItems.size(), str));
    }
    if (this.mItemsRemoved) {
      this.mItemsRemoved = false;
    }
    int m = this.mItems.size();
    int n = ((UrlOffsetPair)this.mUrlOffsetList.get(-1 + this.mUrlOffsetList.size())).offset;
    int i1 = 0;
    if (m == n)
    {
      int i2 = arrayOfObject.length;
      i1 = 0;
      if (i2 > 0) {
        i1 = 1;
      }
    }
    if ((i1 != 0) && (this.mAutoLoadNextPage)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mMoreAvailable = bool;
      this.mCurrentRequest = null;
      notifyDataSetChanged();
      return;
    }
  }
  
  public final void removeItem(int paramInt)
  {
    this.mItems.remove(paramInt);
    this.mItemsRemoved = true;
    if ((this.mCurrentRequest != null) && (!this.mCurrentRequest.isCanceled())) {
      this.mCurrentRequest.cancel();
    }
    clearDiskCache();
  }
  
  public void resetItems()
  {
    this.mMoreAvailable = true;
    this.mItems.clear();
    notifyDataSetChanged();
  }
  
  public final void retryLoadItems()
  {
    if (inErrorState())
    {
      this.mCurrentRequest = null;
      clearErrors();
      int i = this.mCurrentOffset;
      Object localObject = null;
      if (i != -1)
      {
        Iterator localIterator = this.mUrlOffsetList.iterator();
        UrlOffsetPair localUrlOffsetPair;
        do
        {
          boolean bool = localIterator.hasNext();
          localObject = null;
          if (!bool) {
            break;
          }
          localUrlOffsetPair = (UrlOffsetPair)localIterator.next();
        } while (this.mCurrentOffset != localUrlOffsetPair.offset);
        localObject = localUrlOffsetPair;
      }
      if (localObject == null) {
        localObject = (UrlOffsetPair)this.mUrlOffsetList.get(-1 + this.mUrlOffsetList.size());
      }
      requestMoreItemsIfNoRequestExists((UrlOffsetPair)localObject);
    }
  }
  
  public final void startLoadItems()
  {
    if ((this.mMoreAvailable) && (getCount() == 0))
    {
      clearErrors();
      requestMoreItemsIfNoRequestExists((UrlOffsetPair)this.mUrlOffsetList.get(0));
    }
  }
  
  protected static final class UrlOffsetPair
  {
    public final int offset;
    public final String url;
    
    public UrlOffsetPair(int paramInt, String paramString)
    {
      this.offset = paramInt;
      this.url = paramString;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.PaginatedList
 * JD-Core Version:    0.7.0.1
 */