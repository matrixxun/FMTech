package com.google.android.finsky.layout.play;

import android.util.SparseArray;
import android.widget.ImageView;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayCardSnippet;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import java.util.List;

public final class PlayCardHeap
{
  final SparseArray<List<PlayCardViewBase>> mHeap = new SparseArray();
  
  public final void recycle(PlayCardClusterView paramPlayCardClusterView)
  {
    
    if ((paramPlayCardClusterView == null) || (!paramPlayCardClusterView.hasCards())) {
      return;
    }
    PlayCardClusterMetadata localPlayCardClusterMetadata = paramPlayCardClusterView.getMetadata();
    int i = localPlayCardClusterMetadata.getTileCount();
    for (int j = 0; j < i; j++)
    {
      PlayCardViewBase localPlayCardViewBase = paramPlayCardClusterView.getCardChildAt(j);
      PlayCardClusterMetadata.CardMetadata localCardMetadata = localPlayCardClusterMetadata.getTileMetadata(j).mCardMetadata;
      if (localPlayCardViewBase != null)
      {
        ImageView localImageView1 = localPlayCardViewBase.getThumbnail().getImageView();
        if ((localImageView1 instanceof FifeImageView)) {
          ((FifeImageView)localImageView1).clearImage();
        }
        PlayCardSnippet localPlayCardSnippet1 = localPlayCardViewBase.getSnippet1();
        if (localPlayCardSnippet1 != null)
        {
          ImageView localImageView3 = localPlayCardSnippet1.getImageView();
          if ((localImageView1 instanceof FifeImageView)) {
            ((FifeImageView)localImageView3).clearImage();
          }
        }
        PlayCardSnippet localPlayCardSnippet2 = localPlayCardViewBase.getSnippet2();
        if (localPlayCardSnippet2 != null)
        {
          ImageView localImageView2 = localPlayCardSnippet2.getImageView();
          if ((localImageView1 instanceof FifeImageView)) {
            ((FifeImageView)localImageView2).clearImage();
          }
        }
        PlayCardUtils.recycleLoggingData(localPlayCardViewBase);
        int k = localCardMetadata.mLayoutId;
        ((List)this.mHeap.get(k)).add(localPlayCardViewBase);
      }
    }
    paramPlayCardClusterView.removeAllCards();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardHeap
 * JD-Core Version:    0.7.0.1
 */