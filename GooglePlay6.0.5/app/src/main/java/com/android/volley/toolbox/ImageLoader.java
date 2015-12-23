package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public final class ImageLoader
{
  private int mBatchResponseDelayMs = 100;
  final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap();
  final ImageCache mCache;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap();
  final RequestQueue mRequestQueue;
  Runnable mRunnable;
  
  public ImageLoader(RequestQueue paramRequestQueue, ImageCache paramImageCache)
  {
    this.mRequestQueue = paramRequestQueue;
    this.mCache = paramImageCache;
  }
  
  final void batchResponse(String paramString, BatchedImageRequest paramBatchedImageRequest)
  {
    this.mBatchedResponses.put(paramString, paramBatchedImageRequest);
    if (this.mRunnable == null)
    {
      this.mRunnable = new Runnable()
      {
        public final void run()
        {
          Iterator localIterator1 = ImageLoader.this.mBatchedResponses.values().iterator();
          while (localIterator1.hasNext())
          {
            ImageLoader.BatchedImageRequest localBatchedImageRequest = (ImageLoader.BatchedImageRequest)localIterator1.next();
            Iterator localIterator2 = localBatchedImageRequest.mContainers.iterator();
            while (localIterator2.hasNext())
            {
              ImageLoader.ImageContainer localImageContainer = (ImageLoader.ImageContainer)localIterator2.next();
              if (localImageContainer.mListener != null) {
                if (localBatchedImageRequest.mError == null)
                {
                  localImageContainer.mBitmap = localBatchedImageRequest.mResponseBitmap;
                  localImageContainer.mListener.onResponse(localImageContainer, false);
                }
                else
                {
                  localImageContainer.mListener.onErrorResponse(localBatchedImageRequest.mError);
                }
              }
            }
          }
          ImageLoader.this.mBatchedResponses.clear();
          ImageLoader.this.mRunnable = null;
        }
      };
      this.mHandler.postDelayed(this.mRunnable, this.mBatchResponseDelayMs);
    }
  }
  
  private final class BatchedImageRequest
  {
    final LinkedList<ImageLoader.ImageContainer> mContainers = new LinkedList();
    VolleyError mError;
    private final Request<?> mRequest;
    Bitmap mResponseBitmap;
    
    public BatchedImageRequest(ImageLoader.ImageContainer paramImageContainer)
    {
      this.mRequest = paramImageContainer;
      Object localObject;
      this.mContainers.add(localObject);
    }
    
    public final boolean removeContainerAndCancelIfNecessary(ImageLoader.ImageContainer paramImageContainer)
    {
      this.mContainers.remove(paramImageContainer);
      if (this.mContainers.size() == 0)
      {
        this.mRequest.cancel();
        return true;
      }
      return false;
    }
  }
  
  public static abstract interface ImageCache
  {
    public abstract Bitmap getBitmap(String paramString);
    
    public abstract void putBitmap(String paramString, Bitmap paramBitmap);
  }
  
  public final class ImageContainer
  {
    Bitmap mBitmap;
    private final String mCacheKey;
    final ImageLoader.ImageListener mListener;
    final String mRequestUrl;
    
    public ImageContainer(Bitmap paramBitmap, String paramString1, String paramString2, ImageLoader.ImageListener paramImageListener)
    {
      this.mBitmap = paramBitmap;
      this.mRequestUrl = paramString1;
      this.mCacheKey = paramString2;
      this.mListener = paramImageListener;
    }
    
    public final void cancelRequest()
    {
      if (this.mListener == null) {}
      ImageLoader.BatchedImageRequest localBatchedImageRequest2;
      do
      {
        do
        {
          ImageLoader.BatchedImageRequest localBatchedImageRequest1;
          do
          {
            return;
            localBatchedImageRequest1 = (ImageLoader.BatchedImageRequest)ImageLoader.this.mInFlightRequests.get(this.mCacheKey);
            if (localBatchedImageRequest1 == null) {
              break;
            }
          } while (!localBatchedImageRequest1.removeContainerAndCancelIfNecessary(this));
          ImageLoader.this.mInFlightRequests.remove(this.mCacheKey);
          return;
          localBatchedImageRequest2 = (ImageLoader.BatchedImageRequest)ImageLoader.this.mBatchedResponses.get(this.mCacheKey);
        } while (localBatchedImageRequest2 == null);
        localBatchedImageRequest2.removeContainerAndCancelIfNecessary(this);
      } while (localBatchedImageRequest2.mContainers.size() != 0);
      ImageLoader.this.mBatchedResponses.remove(this.mCacheKey);
    }
  }
  
  public static abstract interface ImageListener
    extends Response.ErrorListener
  {
    public abstract void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.ImageLoader
 * JD-Core Version:    0.7.0.1
 */