package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.play.image.BitmapLoader;

public class PlayCardRateClusterViewContent
  extends PlayCardClusterViewContent
{
  int[] mTileIndexToDocumentIndexMapping;
  
  public PlayCardRateClusterViewContent(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardRateClusterViewContent(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void createContent(DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PlayCardDismissListener paramPlayCardDismissListener, PlayCardHeap paramPlayCardHeap, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    syncIndexMapping();
    super.createContent(paramDfeApi, paramNavigationManager, paramBitmapLoader, paramPlayCardDismissListener, paramPlayCardHeap, paramPlayStoreUiElementNode);
  }
  
  public final boolean hasItemsToRate()
  {
    int i = this.mTileIndexToDocumentIndexMapping[0];
    boolean bool = false;
    if (i >= 0) {
      bool = true;
    }
    return bool;
  }
  
  public final void syncIndexMapping()
  {
    int i = getDocCount();
    if ((i == 0) || (this.mMetadata == null)) {}
    for (;;)
    {
      return;
      int j = this.mMetadata.getTileCount();
      if (this.mTileIndexToDocumentIndexMapping == null) {
        this.mTileIndexToDocumentIndexMapping = new int[j];
      }
      for (int k = 0; k < j; k++) {
        this.mTileIndexToDocumentIndexMapping[k] = -1;
      }
      int m = 0;
      int n = 0;
      while ((m < i) && (n < j))
      {
        String str = getDoc(m).mDocument.docid;
        if (this.mClientMutationCache.getCachedReview(str, null) != null) {}
        for (int i1 = 1;; i1 = 0)
        {
          boolean bool = this.mClientMutationCache.isDismissedRecommendation(str);
          if ((i1 == 0) && (!bool)) {
            break label140;
          }
          m++;
          break;
        }
        label140:
        int[] arrayOfInt = this.mTileIndexToDocumentIndexMapping;
        int i2 = n + 1;
        int i3 = m + 1;
        arrayOfInt[n] = m;
        m = i3;
        n = i2;
      }
    }
  }
  
  protected final int tileIndexToDocumentIndex(int paramInt)
  {
    return this.mTileIndexToDocumentIndexMapping[paramInt];
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardRateClusterViewContent
 * JD-Core Version:    0.7.0.1
 */