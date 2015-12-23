package com.google.android.play.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView.ScaleType;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.play.utils.PlayCommonLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BitmapLoader
{
  private static int MIN_CACHE_SIZE_BYTES = 3145728;
  private static int MIN_NUM_IMAGES_IN_CACHE = 6;
  private final HashMap<String, RequestListenerWrapper> mBatchedResponses = new HashMap();
  private final BitmapCache mCachedRemoteImages;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  public final HashMap<String, RequestListenerWrapper> mInFlightRequests = new HashMap();
  private boolean mIsDeliveringBatchResponses;
  private final int mMaxImageSizeToCacheInBytes;
  private final RequestQueue mRequestQueue;
  public RequestTimeoutProvider mRequestTimeoutProvider;
  private Runnable mRunnable;
  private TentativeGcRunner mTentativeGcRunner;
  
  public BitmapLoader(RequestQueue paramRequestQueue, int paramInt1, int paramInt2, TentativeGcRunner paramTentativeGcRunner)
  {
    this.mRequestQueue = paramRequestQueue;
    int i = ((Integer)PlayG.bitmapLoaderCacheSizeOverrideMb.get()).intValue();
    if (i == -1) {}
    for (int j = Math.max(MIN_CACHE_SIZE_BYTES, (int)(4 * (paramInt1 * paramInt2) * ((Float)PlayG.bitmapLoaderCacheSizeRatioToScreen.get()).floatValue()));; j = 1024 * (i * 1024))
    {
      this.mMaxImageSizeToCacheInBytes = Math.max(((Integer)PlayG.minImageSizeLimitInLRUCacheBytes.get()).intValue(), j / MIN_NUM_IMAGES_IN_CACHE);
      this.mCachedRemoteImages = new BitmapCache(j);
      this.mTentativeGcRunner = paramTentativeGcRunner;
      return;
    }
  }
  
  private void batchResponse(String paramString, RequestListenerWrapper paramRequestListenerWrapper)
  {
    this.mBatchedResponses.put(paramString, paramRequestListenerWrapper);
    if (this.mRunnable == null)
    {
      this.mRunnable = new Runnable()
      {
        public final void run()
        {
          BitmapLoader.access$602(BitmapLoader.this, true);
          Iterator localIterator = BitmapLoader.this.mBatchedResponses.values().iterator();
          while (localIterator.hasNext())
          {
            BitmapLoader.RequestListenerWrapper localRequestListenerWrapper = (BitmapLoader.RequestListenerWrapper)localIterator.next();
            List localList = localRequestListenerWrapper.handlers;
            int i = localList.size();
            for (int j = 0; j < i; j++)
            {
              BitmapLoader.BitmapContainer localBitmapContainer = (BitmapLoader.BitmapContainer)localList.get(j);
              localBitmapContainer.mBitmap = localRequestListenerWrapper.responseBitmap;
              if (localBitmapContainer.mBitmapLoaded != null) {
                localBitmapContainer.mBitmapLoaded.onResponse(localBitmapContainer);
              }
            }
          }
          BitmapLoader.this.mBatchedResponses.clear();
          BitmapLoader.access$1202$2c88b07b(BitmapLoader.this);
          BitmapLoader.access$602(BitmapLoader.this, false);
        }
      };
      this.mHandler.postDelayed(this.mRunnable, 100L);
    }
  }
  
  public DebugImageRequest createImageRequest(String paramString, int paramInt1, int paramInt2, Bitmap.Config paramConfig, Response.Listener<Bitmap> paramListener, Response.ErrorListener paramErrorListener)
  {
    return new DebugImageRequest(paramString, paramInt1, paramInt2, paramConfig, paramListener, paramErrorListener);
  }
  
  public final BitmapContainer get(String paramString, int paramInt1, int paramInt2, BitmapLoadedHandler paramBitmapLoadedHandler)
  {
    return get(paramString, paramInt1, paramInt2, true, paramBitmapLoadedHandler, false);
  }
  
  public final BitmapContainer get(final String paramString, final int paramInt1, final int paramInt2, boolean paramBoolean1, BitmapLoadedHandler paramBitmapLoadedHandler, final boolean paramBoolean2)
  {
    String str1 = paramString;
    if ((paramInt1 > 0) || (paramInt2 > 0)) {
      str1 = FifeUrlUtils.buildFifeUrl(paramString, paramInt1, paramInt2);
    }
    final String str2 = str1;
    RemoteRequestCreator local1 = new RemoteRequestCreator()
    {
      public final Request<?> create()
      {
        TentativeGcRunner localTentativeGcRunner = BitmapLoader.this.mTentativeGcRunner;
        int i = 2 * (paramInt1 * paramInt2);
        if (localTentativeGcRunner.mEnabled)
        {
          localTentativeGcRunner.mAllocatedSinceLastRun = (i + localTentativeGcRunner.mAllocatedSinceLastRun);
          if ((i > 81920) && (localTentativeGcRunner.mAllocatedSinceLastRun > 524288))
          {
            localTentativeGcRunner.mHandler.post(localTentativeGcRunner.mGcRunnable);
            localTentativeGcRunner.mAllocatedSinceLastRun = 0;
          }
        }
        BitmapLoader.DebugImageRequest localDebugImageRequest = BitmapLoader.this.createImageRequest(str2, paramInt1, paramInt2, Bitmap.Config.RGB_565, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
        {
          public final void onErrorResponse(VolleyError paramAnonymous2VolleyError)
          {
            BitmapLoader.access$300(BitmapLoader.this, BitmapLoader.1.this.val$finalModifiedUrl);
          }
        });
        if (BitmapLoader.this.mRequestTimeoutProvider != null) {
          localDebugImageRequest.mRetryPolicy = new DefaultRetryPolicy(BitmapLoader.this.mRequestTimeoutProvider.getRequestTimeoutMs(), 2, 2.0F);
        }
        return localDebugImageRequest;
      }
    };
    if (TextUtils.isEmpty(paramString)) {
      return new BitmapContainer(null, null, null, paramInt1, paramInt2, null);
    }
    List localList = (List)this.mCachedRemoteImages.mLruCache.get(paramString);
    Object localObject2;
    if (localList == null) {
      localObject2 = null;
    }
    int i;
    label136:
    int j;
    label143:
    Object localObject1;
    int k;
    for (;;)
    {
      if ((localObject2 != null) && (((BitmapCache.BitmapCacheEntry)localObject2).requestedWidth == paramInt1) && (((BitmapCache.BitmapCacheEntry)localObject2).requestedHeight == paramInt2))
      {
        return new BitmapContainer(((BitmapCache.BitmapCacheEntry)localObject2).bitmap, paramString, str2, paramInt1, paramInt2, null);
        if (paramInt1 != 0)
        {
          i = 1;
          if (paramInt2 == 0) {
            break label248;
          }
          j = 1;
          localObject1 = null;
          k = 0;
          label149:
          if (k >= localList.size()) {
            break label254;
          }
          localObject2 = (BitmapCache.BitmapCacheEntry)localList.get(k);
          if ((((BitmapCache.BitmapCacheEntry)localObject2).requestedWidth == paramInt1) && (((BitmapCache.BitmapCacheEntry)localObject2).requestedHeight == paramInt2)) {
            continue;
          }
          if ((localObject1 != null) || ((i != 0) && (((BitmapCache.BitmapCacheEntry)localObject2).bitmap.getWidth() < paramInt1)) || ((j != 0) && (((BitmapCache.BitmapCacheEntry)localObject2).bitmap.getHeight() < paramInt2))) {
            break label411;
          }
        }
      }
    }
    for (;;)
    {
      k++;
      localObject1 = localObject2;
      break label149;
      i = 0;
      break label136;
      label248:
      j = 0;
      break label143;
      label254:
      if (localObject1 != null)
      {
        localObject2 = localObject1;
        break;
      }
      localObject2 = (BitmapCache.BitmapCacheEntry)localList.get(-1 + localList.size());
      break;
      Bitmap localBitmap = null;
      if (paramBoolean1)
      {
        localBitmap = null;
        if (localObject2 != null) {
          localBitmap = ((BitmapCache.BitmapCacheEntry)localObject2).bitmap;
        }
      }
      BitmapContainer localBitmapContainer = new BitmapContainer(localBitmap, paramString, str2, paramInt1, paramInt2, paramBitmapLoadedHandler);
      RequestListenerWrapper localRequestListenerWrapper = (RequestListenerWrapper)this.mInFlightRequests.get(str2);
      if (localRequestListenerWrapper != null)
      {
        localRequestListenerWrapper.handlers.add(localBitmapContainer);
        return localBitmapContainer;
      }
      Request localRequest = local1.create();
      this.mRequestQueue.add(localRequest);
      this.mInFlightRequests.put(str2, new RequestListenerWrapper(localRequest, localBitmapContainer));
      return localBitmapContainer;
      label411:
      localObject2 = localObject1;
    }
  }
  
  public final BitmapContainer get$6721551b(String paramString, int paramInt1, int paramInt2, BitmapLoadedHandler paramBitmapLoadedHandler)
  {
    return get(paramString, paramInt1, paramInt2, false, paramBitmapLoadedHandler, false);
  }
  
  public final class BitmapContainer
  {
    public Bitmap mBitmap;
    BitmapLoader.BitmapLoadedHandler mBitmapLoaded;
    private final String mModifiedUrl;
    final int mRequestHeight;
    final String mRequestUrl;
    final int mRequestWidth;
    
    public BitmapContainer(Bitmap paramBitmap, String paramString1, String paramString2, int paramInt1, int paramInt2, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler)
    {
      this.mBitmap = paramBitmap;
      this.mRequestUrl = paramString1;
      this.mModifiedUrl = paramString2;
      this.mRequestWidth = paramInt1;
      this.mRequestHeight = paramInt2;
      this.mBitmapLoaded = paramBitmapLoadedHandler;
    }
    
    public final void cancelRequest()
    {
      if (this.mBitmapLoaded == null) {}
      BitmapLoader.RequestListenerWrapper localRequestListenerWrapper2;
      do
      {
        BitmapLoader.RequestListenerWrapper localRequestListenerWrapper1;
        do
        {
          return;
          if (BitmapLoader.this.mIsDeliveringBatchResponses)
          {
            PlayCommonLog.wtf("Attempt to cancel a bitmap request from BitmapLoadedHandler.onResponse", new Object[0]);
            return;
          }
          localRequestListenerWrapper1 = (BitmapLoader.RequestListenerWrapper)BitmapLoader.this.mInFlightRequests.get(this.mModifiedUrl);
          if (localRequestListenerWrapper1 == null) {
            break;
          }
        } while (!localRequestListenerWrapper1.removeHandlerAndCancelIfNecessary(this));
        BitmapLoader.this.mInFlightRequests.remove(this.mModifiedUrl);
        return;
        localRequestListenerWrapper2 = (BitmapLoader.RequestListenerWrapper)BitmapLoader.this.mBatchedResponses.get(this.mModifiedUrl);
      } while ((localRequestListenerWrapper2 == null) || (!localRequestListenerWrapper2.removeHandlerAndCancelIfNecessary(this)));
      BitmapLoader.this.mBatchedResponses.remove(this.mModifiedUrl);
    }
  }
  
  public static abstract interface BitmapLoadedHandler
    extends Response.Listener<BitmapLoader.BitmapContainer>
  {
    public abstract void onResponse(BitmapLoader.BitmapContainer paramBitmapContainer);
  }
  
  public static class DebugImageRequest
    extends ImageRequest
  {
    private static final Matrix IDENTITY = new Matrix();
    private boolean mResponseDelivered;
    
    public DebugImageRequest(String paramString, int paramInt1, int paramInt2, Bitmap.Config paramConfig, Response.Listener<Bitmap> paramListener, Response.ErrorListener paramErrorListener)
    {
      super(paramListener, paramInt1, paramInt2, ImageView.ScaleType.CENTER_INSIDE, paramConfig, paramErrorListener);
    }
    
    public void deliverResponse(Bitmap paramBitmap)
    {
      if (this.mResponseDelivered) {
        return;
      }
      this.mResponseDelivered = true;
      super.deliverResponse(paramBitmap);
    }
    
    public Response<Bitmap> parseNetworkResponse(NetworkResponse paramNetworkResponse)
    {
      Response localResponse = super.parseNetworkResponse(paramNetworkResponse);
      if ((!localResponse.isSuccess()) || (!((Boolean)PlayG.debugImageSizes.get()).booleanValue())) {
        return localResponse;
      }
      Bitmap localBitmap1 = (Bitmap)localResponse.result;
      int i = paramNetworkResponse.data.length / 1024;
      Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1.getWidth(), localBitmap1.getHeight(), localBitmap1.getConfig());
      Canvas localCanvas = new Canvas(localBitmap2);
      localCanvas.drawBitmap(localBitmap1, IDENTITY, null);
      Paint localPaint = new Paint(8);
      if (getUrl().contains("ggpht.com")) {}
      String str1;
      String str2;
      String str3;
      float f1;
      for (int j = -16711681;; j = -65281)
      {
        localPaint.setColor(j);
        localPaint.setStrokeWidth(3.0F);
        localPaint.setTextAlign(Paint.Align.LEFT);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        str1 = String.format("%dk", arrayOfObject1);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(localBitmap1.getHeight());
        str2 = String.format("%dh", arrayOfObject2);
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(localBitmap1.getWidth());
        str3 = String.format("%dw", arrayOfObject3);
        for (f1 = 40.0F;; f1 = (float)(0.8D * f1))
        {
          localPaint.setTextSize(f1);
          if ((3.1D * f1 <= localCanvas.getHeight()) && (1.1D * Math.max(Math.max(localPaint.measureText(str2), localPaint.measureText(str3)), localPaint.measureText(str1)) < localCanvas.getWidth())) {
            break;
          }
        }
      }
      float f2 = localCanvas.getHeight() / 2 - f1;
      localCanvas.drawText(str1, 4.0F, f2, localPaint);
      float f3 = f2 + (5.0F + f1);
      localCanvas.drawText(str2, 4.0F, f3, localPaint);
      localCanvas.drawText(str3, 4.0F, f3 + (f1 + 5.0F), localPaint);
      localBitmap1.recycle();
      return Response.success(localBitmap2, localResponse.cacheEntry);
    }
  }
  
  private static abstract interface RemoteRequestCreator
  {
    public abstract Request<?> create();
  }
  
  private final class RequestListenerWrapper
  {
    List<BitmapLoader.BitmapContainer> handlers = new ArrayList();
    public Request<?> request;
    Bitmap responseBitmap;
    
    public RequestListenerWrapper(BitmapLoader.BitmapContainer paramBitmapContainer)
    {
      this.request = paramBitmapContainer;
      Object localObject;
      this.handlers.add(localObject);
    }
    
    public final boolean removeHandlerAndCancelIfNecessary(BitmapLoader.BitmapContainer paramBitmapContainer)
    {
      this.handlers.remove(paramBitmapContainer);
      if (this.handlers.size() == 0)
      {
        this.request.cancel();
        return true;
      }
      return false;
    }
  }
  
  public static abstract interface RequestTimeoutProvider
  {
    public abstract int getRequestTimeoutMs();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.image.BitmapLoader
 * JD-Core Version:    0.7.0.1
 */