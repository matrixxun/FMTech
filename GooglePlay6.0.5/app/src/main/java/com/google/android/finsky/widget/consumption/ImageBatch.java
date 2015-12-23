package com.google.android.finsky.widget.consumption;

import android.net.Uri;
import java.util.List;

public final class ImageBatch
{
  int backendId;
  BatchedImageLoader.BatchedImageCallback callback;
  List<ImageSpec> urisToLoad;
  
  public ImageBatch(int paramInt, List<ImageSpec> paramList, BatchedImageLoader.BatchedImageCallback paramBatchedImageCallback)
  {
    this.backendId = paramInt;
    this.urisToLoad = paramList;
    this.callback = paramBatchedImageCallback;
  }
  
  public final boolean equals(Object paramObject)
  {
    return ((paramObject instanceof ImageBatch)) && (((ImageBatch)paramObject).backendId == this.backendId);
  }
  
  public static final class ImageSpec
  {
    public final int height;
    public final Uri uri;
    public final int width;
    
    public ImageSpec(Uri paramUri, int paramInt1, int paramInt2)
    {
      this.uri = paramUri;
      this.width = paramInt1;
      this.height = paramInt2;
    }
    
    public final boolean satisfies(Uri paramUri, int paramInt1, int paramInt2)
    {
      return (this.uri.equals(paramUri)) && (this.width >= paramInt1) && (this.height >= paramInt2);
    }
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this.uri;
      arrayOfObject[1] = Integer.valueOf(this.width);
      arrayOfObject[2] = Integer.valueOf(this.height);
      return String.format("uri=[%s], [%s x %s]", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.ImageBatch
 * JD-Core Version:    0.7.0.1
 */