package com.google.android.finsky.widget.consumption;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.ParcelFileDescriptor;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.config.GservicesValue;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@TargetApi(16)
public final class BatchedImageLoader
{
  private static final ExecutorService sFetchThread = Executors.newSingleThreadExecutor(new BackgroundThreadFactory());
  private final BitmapLoader mBitmapLoader;
  private final Context mContext;
  volatile Map<ImageBatch.ImageSpec, Bitmap> mPreviousBitmaps;
  private final Thread mProcessingThread;
  private LinkedBlockingQueue<ImageBatch> mQueue = new LinkedBlockingQueue();
  private int mTotalBitmapMemory = 0;
  private final Semaphore mWaitLock = new Semaphore(0);
  private final Cache mWidgetCache;
  
  public BatchedImageLoader(Context paramContext, Cache paramCache)
  {
    this.mWidgetCache = paramCache;
    this.mContext = paramContext;
    this.mBitmapLoader = FinskyApp.get().mBitmapLoader;
    this.mProcessingThread = BackgroundThreadFactory.createThread("BatchedImageLoader.mProcessingThread", new Runnable()
    {
      public final void run()
      {
        try
        {
          for (;;)
          {
            BatchedImageLoader.access$100(BatchedImageLoader.this, (ImageBatch)BatchedImageLoader.this.mQueue.take());
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          FinskyLog.e("Interrupted while trying to load images!", new Object[0]);
        }
      }
    });
    this.mProcessingThread.start();
  }
  
  private static int findBestSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    double d = Math.min(paramInt1 / paramInt3, paramInt2 / paramInt4);
    for (float f = 1.0F; 2.0F * f <= d; f *= 2.0F) {}
    return (int)f;
  }
  
  private byte[] loadFileFromUri(ImageBatch.ImageSpec paramImageSpec)
  {
    int i = 1;
    byte[] arrayOfByte = null;
    while ((i < 3) && (arrayOfByte == null)) {
      try
      {
        Uri localUri = paramImageSpec.uri.buildUpon().appendQueryParameter("w", String.valueOf(paramImageSpec.width)).appendQueryParameter("h", String.valueOf(paramImageSpec.height)).build();
        ParcelFileDescriptor localParcelFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(localUri, "r");
        if (localParcelFileDescriptor != null)
        {
          arrayOfByte = Utils.readBytes(new FileInputStream(localParcelFileDescriptor.getFileDescriptor()));
          localParcelFileDescriptor.close();
        }
        i++;
      }
      catch (IOException localIOException)
      {
        localIOException = localIOException;
        FinskyLog.d("IOException parsing [%s]", new Object[] { paramImageSpec });
        i++;
      }
      finally {}
    }
    return arrayOfByte;
  }
  
  private Bitmap loadFromProvider(final ImageBatch.ImageSpec paramImageSpec)
  {
    Cache.Entry localEntry1 = this.mWidgetCache.get(paramImageSpec.toString());
    byte[] arrayOfByte = null;
    int i;
    int j;
    Object localObject;
    if (localEntry1 != null)
    {
      arrayOfByte = localEntry1.data;
      if (arrayOfByte == null) {
        break label488;
      }
      i = paramImageSpec.width;
      j = paramImageSpec.height;
      if ((i != 0) && (j != 0)) {
        break label260;
      }
      localObject = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
    }
    for (;;)
    {
      for (;;)
      {
        if (localObject == null) {
          break label463;
        }
        this.mTotalBitmapMemory += ((Bitmap)localObject).getByteCount();
        return localObject;
        FutureTask localFutureTask = new FutureTask(new Callable() {});
        try
        {
          sFetchThread.execute(localFutureTask);
          arrayOfByte = (byte[])localFutureTask.get(((Long)G.consumptionAppImageTimeoutMs.get()).longValue(), TimeUnit.MILLISECONDS);
          if (arrayOfByte == null) {
            break;
          }
          Cache.Entry localEntry2 = new Cache.Entry();
          localEntry2.data = arrayOfByte;
          localEntry2.ttl = 9223372036854775807L;
          this.mWidgetCache.put(paramImageSpec.toString(), localEntry2);
        }
        catch (TimeoutException localTimeoutException)
        {
          Object[] arrayOfObject5 = new Object[1];
          arrayOfObject5[0] = paramImageSpec.uri;
          FinskyLog.e("Timed out while waiting for %s", arrayOfObject5);
        }
        catch (InterruptedException localInterruptedException)
        {
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = paramImageSpec.uri;
          FinskyLog.e("Interrupted while loading %s", arrayOfObject4);
        }
        catch (ExecutionException localExecutionException)
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = paramImageSpec.uri;
          FinskyLog.e("ExecutionException while loading %s", arrayOfObject1);
        }
      }
      break;
      label260:
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
      int k = localOptions.outWidth;
      int m = localOptions.outHeight;
      if ((k < i) || (m < j))
      {
        localObject = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
      }
      else
      {
        float f = k / m;
        int n = (int)(i / f);
        int i1 = (int)(f * j);
        localOptions.inJustDecodeBounds = false;
        int i2;
        label369:
        Bitmap localBitmap1;
        Bitmap localBitmap2;
        if (n > j)
        {
          i2 = i;
          localOptions.inSampleSize = findBestSampleSize(k, m, i2, n);
          localBitmap1 = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
          if ((localBitmap1 == null) || ((localBitmap1.getWidth() == i2) && (localBitmap1.getHeight() == n))) {
            break label456;
          }
          localBitmap2 = Bitmap.createScaledBitmap(localBitmap1, i2, n, true);
          localBitmap1.recycle();
        }
        for (;;)
        {
          localObject = localBitmap2;
          break;
          n = j;
          i2 = i1;
          break label369;
          label456:
          localBitmap2 = localBitmap1;
        }
      }
    }
    label463:
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = paramImageSpec.uri;
    FinskyLog.e("Failed to decode bitmap %s", arrayOfObject3);
    return localObject;
    label488:
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = paramImageSpec.uri;
    FinskyLog.e("File was not loaded for uri=[%s]", arrayOfObject2);
    return null;
  }
  
  public final void enqueue(ImageBatch paramImageBatch)
  {
    this.mQueue.add(paramImageBatch);
  }
  
  public static abstract interface BatchedImageCallback
  {
    public abstract void onLoaded(Map<ImageBatch.ImageSpec, Bitmap> paramMap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.BatchedImageLoader
 * JD-Core Version:    0.7.0.1
 */