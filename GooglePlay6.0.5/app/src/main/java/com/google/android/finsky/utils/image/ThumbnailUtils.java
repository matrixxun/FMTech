package com.google.android.finsky.utils.image;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Image.Dimension;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.image.FifeUrlUtils;
import java.util.List;

public final class ThumbnailUtils
{
  public static void adjustWidthFromImageMetadata(FifeImageView paramFifeImageView, Common.Image paramImage)
  {
    if ((paramImage.dimension != null) && (paramImage.dimension.hasWidth) && (paramImage.dimension.hasHeight) && (paramImage.dimension.width > paramImage.dimension.height))
    {
      float f = paramImage.dimension.width / paramImage.dimension.height;
      ViewGroup.LayoutParams localLayoutParams = paramFifeImageView.getLayoutParams();
      localLayoutParams.width = ((int)(f * localLayoutParams.height));
      paramFifeImageView.requestLayout();
    }
  }
  
  public static Common.Image getBestImage(List<Common.Image> paramList, int paramInt1, int paramInt2)
  {
    Common.Image localImage2;
    if (paramList == null)
    {
      localImage2 = null;
      return localImage2;
    }
    int i = 2147483647;
    int j = 2147483647;
    Common.Image localImage1 = null;
    int k = paramList.size();
    for (int m = 0;; m++)
    {
      if (m >= k) {
        break label129;
      }
      localImage2 = (Common.Image)paramList.get(m);
      if (localImage2.supportsFifeUrlOptions) {
        break;
      }
      if (localImage2.dimension != null)
      {
        int n = localImage2.dimension.width;
        int i1 = localImage2.dimension.height;
        if ((n >= paramInt1) && (i1 >= paramInt2) && (i >= n) && (j >= i1))
        {
          i = n;
          j = i1;
          localImage1 = localImage2;
        }
      }
    }
    label129:
    if (localImage1 != null) {
      return localImage1;
    }
    if (paramList.size() > 0) {
      return (Common.Image)paramList.get(-1 + paramList.size());
    }
    return null;
  }
  
  public static Common.Image getImageFromDocument(Document paramDocument, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      Common.Image localImage = getBestImage(paramDocument.getImages(paramArrayOfInt[i]), paramInt1, paramInt2);
      if (localImage != null) {
        return localImage;
      }
    }
    return null;
  }
  
  public static BitmapLoader.BitmapContainer preloadCoverImage(Context paramContext, Document paramDocument, BitmapLoader paramBitmapLoader, int paramInt1, int paramInt2, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler, int[] paramArrayOfInt)
  {
    if (paramDocument == null) {
      return null;
    }
    return preloadCoverImage(paramContext, getImageFromDocument(paramDocument, paramInt1, 0, paramArrayOfInt), paramBitmapLoader, paramInt1, paramInt2, paramBitmapLoadedHandler);
  }
  
  public static BitmapLoader.BitmapContainer preloadCoverImage(Context paramContext, Common.Image paramImage, BitmapLoader paramBitmapLoader, int paramInt1, int paramInt2, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler)
  {
    if (paramImage == null) {
      return null;
    }
    int i = (int)(FifeUrlUtils.getNetworkScaleFactor(paramContext) * FifeUrlUtils.getDpiScaleFactor() * paramInt1);
    return paramBitmapLoader.get(paramImage.imageUrl, i, paramInt2, false, paramBitmapLoadedHandler, false);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.image.ThumbnailUtils
 * JD-Core Version:    0.7.0.1
 */