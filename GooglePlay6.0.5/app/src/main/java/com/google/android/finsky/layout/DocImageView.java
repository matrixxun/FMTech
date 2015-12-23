package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.Arrays;

public class DocImageView
  extends FifeImageView
{
  private Document mDoc;
  private int[] mImageTypes;
  
  public DocImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DocImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void bind(Document paramDocument, BitmapLoader paramBitmapLoader, int... paramVarArgs)
  {
    int i;
    int j;
    int k;
    if ((this.mDoc != null) && (this.mDoc == paramDocument) && (Arrays.equals(this.mImageTypes, paramVarArgs)))
    {
      i = 1;
      if (i == 0)
      {
        this.mDoc = paramDocument;
        this.mImageTypes = paramVarArgs;
        j = getWidth();
        k = getHeight();
        if (k <= 0) {
          break label104;
        }
      }
    }
    label104:
    for (Common.Image localImage = ThumbnailUtils.getImageFromDocument(this.mDoc, 0, k, this.mImageTypes);; localImage = ThumbnailUtils.getImageFromDocument(this.mDoc, j, 0, this.mImageTypes))
    {
      if (localImage == null) {
        break label123;
      }
      setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
      return;
      i = 0;
      break;
    }
    label123:
    clearImage();
  }
  
  public final void clearImage()
  {
    super.clearImage();
    this.mDoc = null;
    this.mImageTypes = null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DocImageView
 * JD-Core Version:    0.7.0.1
 */