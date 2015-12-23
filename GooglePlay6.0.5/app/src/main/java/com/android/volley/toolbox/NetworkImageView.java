package com.android.volley.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import java.util.HashMap;
import java.util.LinkedList;

public class NetworkImageView
  extends ImageView
{
  private int mDefaultImageId;
  private int mErrorImageId;
  private ImageLoader.ImageContainer mImageContainer;
  private ImageLoader mImageLoader;
  private String mUrl;
  
  public NetworkImageView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NetworkImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NetworkImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void loadImageIfNecessary(final boolean paramBoolean)
  {
    int i = getWidth();
    int j = getHeight();
    ImageView.ScaleType localScaleType = getScaleType();
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    int k = 0;
    int m = 0;
    label63:
    int n;
    if (localLayoutParams != null)
    {
      if (getLayoutParams().width == -2)
      {
        m = 1;
        if (getLayoutParams().height != -2) {
          break label96;
        }
        k = 1;
      }
    }
    else
    {
      if ((m == 0) || (k == 0)) {
        break label102;
      }
      n = 1;
      label76:
      if ((i != 0) || (j != 0) || (n != 0)) {
        break label108;
      }
    }
    label96:
    label102:
    label108:
    do
    {
      return;
      m = 0;
      break;
      k = 0;
      break label63;
      n = 0;
      break label76;
      if (TextUtils.isEmpty(this.mUrl))
      {
        if (this.mImageContainer != null)
        {
          this.mImageContainer.cancelRequest();
          this.mImageContainer = null;
        }
        setDefaultImageOrNull();
        return;
      }
      if ((this.mImageContainer == null) || (this.mImageContainer.mRequestUrl == null)) {
        break label187;
      }
    } while (this.mImageContainer.mRequestUrl.equals(this.mUrl));
    this.mImageContainer.cancelRequest();
    setDefaultImageOrNull();
    label187:
    int i1;
    if (m != 0)
    {
      i1 = 0;
      if (k == 0) {
        break label251;
      }
    }
    ImageLoader localImageLoader;
    String str1;
    ImageLoader.ImageListener local1;
    label251:
    for (int i2 = 0;; i2 = j)
    {
      localImageLoader = this.mImageLoader;
      str1 = this.mUrl;
      local1 = new ImageLoader.ImageListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          if (NetworkImageView.this.mErrorImageId != 0) {
            NetworkImageView.this.setImageResource(NetworkImageView.this.mErrorImageId);
          }
        }
        
        public final void onResponse(final ImageLoader.ImageContainer paramAnonymousImageContainer, boolean paramAnonymousBoolean)
        {
          if ((paramAnonymousBoolean) && (paramBoolean)) {
            NetworkImageView.this.post(new Runnable()
            {
              public final void run()
              {
                NetworkImageView.1.this.onResponse(paramAnonymousImageContainer, false);
              }
            });
          }
          do
          {
            return;
            if (paramAnonymousImageContainer.mBitmap != null)
            {
              NetworkImageView.this.setImageBitmap(paramAnonymousImageContainer.mBitmap);
              return;
            }
          } while (NetworkImageView.this.mDefaultImageId == 0);
          NetworkImageView.this.setImageResource(NetworkImageView.this.mDefaultImageId);
        }
      };
      if (Looper.myLooper() == Looper.getMainLooper()) {
        break label257;
      }
      throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
      i1 = i;
      break;
    }
    label257:
    String str2 = 12 + str1.length() + "#W" + i1 + "#H" + i2 + "#S" + localScaleType.ordinal() + str1;
    Bitmap localBitmap = localImageLoader.mCache.getBitmap(str2);
    Object localObject;
    if (localBitmap != null)
    {
      ImageLoader.ImageContainer localImageContainer1 = new ImageLoader.ImageContainer(localImageLoader, localBitmap, str1, null, null);
      local1.onResponse(localImageContainer1, true);
      localObject = localImageContainer1;
    }
    for (;;)
    {
      this.mImageContainer = ((ImageLoader.ImageContainer)localObject);
      return;
      ImageLoader.ImageContainer localImageContainer2 = new ImageLoader.ImageContainer(localImageLoader, null, str1, str2, local1);
      local1.onResponse(localImageContainer2, true);
      ImageLoader.BatchedImageRequest localBatchedImageRequest = (ImageLoader.BatchedImageRequest)localImageLoader.mInFlightRequests.get(str2);
      if (localBatchedImageRequest != null)
      {
        localBatchedImageRequest.mContainers.add(localImageContainer2);
        localObject = localImageContainer2;
      }
      else
      {
        ImageRequest localImageRequest = new ImageRequest(str1, new ImageLoader.2(localImageLoader, str2), i1, i2, localScaleType, Bitmap.Config.RGB_565, new ImageLoader.3(localImageLoader, str2));
        localImageLoader.mRequestQueue.add(localImageRequest);
        localImageLoader.mInFlightRequests.put(str2, new ImageLoader.BatchedImageRequest(localImageLoader, localImageRequest, localImageContainer2));
        localObject = localImageContainer2;
      }
    }
  }
  
  private void setDefaultImageOrNull()
  {
    if (this.mDefaultImageId != 0)
    {
      setImageResource(this.mDefaultImageId);
      return;
    }
    setImageBitmap(null);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    invalidate();
  }
  
  protected void onDetachedFromWindow()
  {
    if (this.mImageContainer != null)
    {
      this.mImageContainer.cancelRequest();
      setImageBitmap(null);
      this.mImageContainer = null;
    }
    super.onDetachedFromWindow();
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    loadImageIfNecessary(true);
  }
  
  public void setDefaultImageResId(int paramInt)
  {
    this.mDefaultImageId = paramInt;
  }
  
  public void setErrorImageResId(int paramInt)
  {
    this.mErrorImageId = paramInt;
  }
  
  public void setImageUrl(String paramString, ImageLoader paramImageLoader)
  {
    this.mUrl = paramString;
    this.mImageLoader = paramImageLoader;
    loadImageIfNecessary(false);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.NetworkImageView
 * JD-Core Version:    0.7.0.1
 */