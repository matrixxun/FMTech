package com.google.android.finsky.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.WishlistHelper.WishlistStatusListener;
import com.google.android.play.image.BitmapLoader;
import java.util.HashSet;
import java.util.Set;

public final class MyWishlistRecyclerViewAdapter<T extends ContainerList<?>>
  extends CardRecyclerViewAdapter<T>
  implements WishlistHelper.WishlistStatusListener
{
  private Set<String> mDismissedDocIds = new HashSet();
  
  public MyWishlistRecyclerViewAdapter(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, MultiDfeList<T> paramMultiDfeList, PlayStoreUiElementNode paramPlayStoreUiElementNode, boolean paramBoolean)
  {
    super(paramContext, paramDfeApi, paramNavigationManager, paramBitmapLoader, paramDfeToc, paramClientMutationCache, paramMultiDfeList, null, null, paramBoolean, false, 2, paramPlayStoreUiElementNode, null);
  }
  
  protected final boolean isDismissed(Document paramDocument)
  {
    return this.mDismissedDocIds.contains(paramDocument.mDocument.docid);
  }
  
  public final void onDataChanged()
  {
    this.mDismissedDocIds.clear();
    super.onDataChanged();
  }
  
  public final void onWishlistStatusChanged(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!paramBoolean2) {
      if (paramBoolean1)
      {
        this.mDismissedDocIds.remove(paramString);
        this.mObservable.notifyChanged();
      }
    }
    while (paramBoolean1) {
      for (;;)
      {
        return;
        this.mDismissedDocIds.add(paramString);
      }
    }
    this.mDismissedDocIds.remove(paramString);
    ContainerList localContainerList = this.mMultiDfeList.mContainerList;
    for (int i = 0;; i++) {
      if (i < localContainerList.getCount())
      {
        if (paramString.equals(((Document)localContainerList.getItem(i)).mDocument.docid)) {
          localContainerList.removeItem(i);
        }
      }
      else
      {
        updateAdapterData(localContainerList);
        return;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.MyWishlistRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */