package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView.ScaleType;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;

public class ImageRequest
  extends Request<Bitmap>
{
  private static final Object sDecodeLock = new Object();
  private final Bitmap.Config mDecodeConfig;
  private final Response.Listener<Bitmap> mListener;
  private final int mMaxHeight;
  private final int mMaxWidth;
  private ImageView.ScaleType mScaleType;
  
  public ImageRequest(String paramString, Response.Listener<Bitmap> paramListener, int paramInt1, int paramInt2, ImageView.ScaleType paramScaleType, Bitmap.Config paramConfig, Response.ErrorListener paramErrorListener)
  {
    super(0, paramString, paramErrorListener);
    this.mRetryPolicy = new DefaultRetryPolicy(1000, 2, 2.0F);
    this.mListener = paramListener;
    this.mDecodeConfig = paramConfig;
    this.mMaxWidth = paramInt1;
    this.mMaxHeight = paramInt2;
    this.mScaleType = paramScaleType;
  }
  
  private static int findBestSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    double d = Math.min(paramInt1 / paramInt3, paramInt2 / paramInt4);
    for (float f = 1.0F; 2.0F * f <= d; f *= 2.0F) {}
    return (int)f;
  }
  
  private static int getResizedDimension(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageView.ScaleType paramScaleType)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0)) {}
    do
    {
      return paramInt3;
      if (paramScaleType != ImageView.ScaleType.FIT_XY) {
        break;
      }
    } while (paramInt1 == 0);
    return paramInt1;
    if (paramInt1 == 0) {
      return (int)(paramInt2 / paramInt4 * paramInt3);
    }
    if (paramInt2 == 0) {
      return paramInt1;
    }
    double d = paramInt4 / paramInt3;
    int i = paramInt1;
    if (paramScaleType == ImageView.ScaleType.CENTER_CROP)
    {
      if (d * i < paramInt2) {
        i = (int)(paramInt2 / d);
      }
      return i;
    }
    if (d * i > paramInt2) {
      i = (int)(paramInt2 / d);
    }
    return i;
  }
  
  public void deliverResponse(Bitmap paramBitmap)
  {
    this.mListener.onResponse(paramBitmap);
  }
  
  public final Request.Priority getPriority()
  {
    return Request.Priority.LOW;
  }
  
  public Response<Bitmap> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    for (;;)
    {
      Bitmap localBitmap;
      synchronized (sDecodeLock)
      {
        try
        {
          byte[] arrayOfByte = paramNetworkResponse.data;
          BitmapFactory.Options localOptions = new BitmapFactory.Options();
          if ((this.mMaxWidth == 0) && (this.mMaxHeight == 0))
          {
            localOptions.inPreferredConfig = this.mDecodeConfig;
            localObject3 = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
            if (localObject3 != null) {
              break label304;
            }
            Response localResponse2 = Response.error(new ParseError(paramNetworkResponse));
            localObject4 = localResponse2;
            return localObject4;
          }
          localOptions.inJustDecodeBounds = true;
          BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
          int i = localOptions.outWidth;
          int j = localOptions.outHeight;
          int k = getResizedDimension(this.mMaxWidth, this.mMaxHeight, i, j, this.mScaleType);
          int m = getResizedDimension(this.mMaxHeight, this.mMaxWidth, j, i, this.mScaleType);
          localOptions.inJustDecodeBounds = false;
          localOptions.inSampleSize = findBestSampleSize(i, j, k, m);
          localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
          if ((localBitmap != null) && ((localBitmap.getWidth() > k) || (localBitmap.getHeight() > m)))
          {
            localObject3 = Bitmap.createScaledBitmap(localBitmap, k, m, true);
            localBitmap.recycle();
            continue;
            Object[] arrayOfObject;
            Response localResponse1;
            localObject2 = finally;
          }
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramNetworkResponse.data.length);
          arrayOfObject[1] = getUrl();
          VolleyLog.e("Caught OOM for %d byte image, url=%s", arrayOfObject);
          localResponse1 = Response.error(new ParseError(localOutOfMemoryError));
          return localResponse1;
        }
      }
      Object localObject3 = localBitmap;
      continue;
      label304:
      Response localResponse3 = Response.success(localObject3, HttpHeaderParser.parseCacheHeaders(paramNetworkResponse));
      Object localObject4 = localResponse3;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.ImageRequest
 * JD-Core Version:    0.7.0.1
 */