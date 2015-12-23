package com.google.android.finsky.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MatrixCursor.RowBuilder;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.android.ex.photo.PhotoViewController;
import com.android.ex.photo.PhotoViewController.ActivityInterface;
import com.android.ex.photo.loaders.PhotoBitmapLoader;
import com.android.ex.photo.loaders.PhotoBitmapLoaderInterface.BitmapResult;
import com.android.ex.photo.provider.PhotoContract.PhotoQuery;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.image.FifeUrlUtils;
import java.util.Iterator;
import java.util.List;

public final class ScreenshotsViewController
  extends PhotoViewController
{
  private List<Common.Image> mImages;
  
  public ScreenshotsViewController(PhotoViewController.ActivityInterface paramActivityInterface, List<Common.Image> paramList)
  {
    super(paramActivityInterface);
    this.mImages = paramList;
  }
  
  public final void hideActionBar() {}
  
  public final Loader<PhotoBitmapLoaderInterface.BitmapResult> onCreateBitmapLoader$21c6b1c7(int paramInt, String paramString)
  {
    return new ScreenshotsBitmapLoader(this.mActivity.getContext(), paramString);
  }
  
  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new ScreenshotsPagerLoader(this.mActivity.getContext(), this.mImages);
  }
  
  public final void showActionBar() {}
  
  public static final class ScreenshotsBitmapLoader
    extends PhotoBitmapLoader
    implements BitmapLoader.BitmapLoadedHandler
  {
    private float mScalingFactor = 1.0F;
    private int mScreenshotsRequestSize;
    private String mUri;
    
    public ScreenshotsBitmapLoader(Context paramContext, String paramString)
    {
      super(paramString);
      this.mUri = paramString;
      float f = FifeUrlUtils.getNetworkScaleFactor(paramContext);
      if (Build.VERSION.SDK_INT <= 10) {
        this.mScalingFactor = 0.5F;
      }
      this.mScalingFactor = Math.min(this.mScalingFactor, f);
      WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      int i = Math.max(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels);
      this.mScreenshotsRequestSize = Math.round(Math.min(localDisplayMetrics.densityDpi, 250) * (i / localDisplayMetrics.densityDpi) * this.mScalingFactor);
    }
    
    public final PhotoBitmapLoaderInterface.BitmapResult loadInBackground()
    {
      PhotoBitmapLoaderInterface.BitmapResult localBitmapResult = new PhotoBitmapLoaderInterface.BitmapResult();
      BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
      if (this.mId == 2) {}
      for (BitmapLoader.BitmapContainer localBitmapContainer = localBitmapLoader.get(this.mUri, this.mScreenshotsRequestSize, this.mScreenshotsRequestSize, true, this, true);; localBitmapContainer = localBitmapLoader.get(this.mUri, this.mScreenshotsRequestSize, this.mScreenshotsRequestSize, false, this, true))
      {
        localBitmapResult.bitmap = localBitmapContainer.mBitmap;
        if ((localBitmapResult.bitmap != null) && (localBitmapResult.bitmap.isRecycled()))
        {
          localBitmapResult.bitmap = null;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(this.mId);
          FinskyLog.wtf("Using recycled bitmap for Id:%d", arrayOfObject);
        }
        localBitmapResult.status = 0;
        return localBitmapResult;
      }
    }
    
    protected final void onReleaseResources(Bitmap paramBitmap)
    {
      if ((paramBitmap != null) && (this.mId != 2) && (!paramBitmap.isRecycled())) {
        paramBitmap.recycle();
      }
    }
    
    public final void onResponse(BitmapLoader.BitmapContainer paramBitmapContainer)
    {
      PhotoBitmapLoaderInterface.BitmapResult localBitmapResult = new PhotoBitmapLoaderInterface.BitmapResult();
      if (paramBitmapContainer != null)
      {
        localBitmapResult.bitmap = paramBitmapContainer.mBitmap;
        localBitmapResult.status = 0;
        if ((localBitmapResult.bitmap != null) && (localBitmapResult.bitmap.isRecycled()))
        {
          localBitmapResult.bitmap = null;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(this.mId);
          FinskyLog.e("Using recycled bitmap for Id:%d", arrayOfObject);
        }
      }
      else
      {
        localBitmapResult.status = 1;
      }
      deliverResult(localBitmapResult);
    }
  }
  
  public static final class ScreenshotsPagerLoader
    extends CursorLoader
  {
    private List<Common.Image> mImages;
    
    public ScreenshotsPagerLoader(Context paramContext, List<Common.Image> paramList)
    {
      super();
      this.mImages = paramList;
    }
    
    public final Cursor loadInBackground()
    {
      MatrixCursor localMatrixCursor = new MatrixCursor(PhotoContract.PhotoQuery.PROJECTION);
      Iterator localIterator = this.mImages.iterator();
      while (localIterator.hasNext())
      {
        Common.Image localImage = (Common.Image)localIterator.next();
        localMatrixCursor.newRow().add(localImage.imageUrl).add("").add(localImage.imageUrl).add(localImage.imageUrl).add("image/WebP");
      }
      return localMatrixCursor;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ScreenshotsViewController
 * JD-Core Version:    0.7.0.1
 */