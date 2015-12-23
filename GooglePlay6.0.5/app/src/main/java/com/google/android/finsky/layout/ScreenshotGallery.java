package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.FrameLayout;
import com.google.android.finsky.adapters.ImageStripAdapter;
import com.google.android.finsky.adapters.ImageStripAdapter.OnImageChildViewTapListener;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ScreenshotGallery
  extends FrameLayout
  implements ImageStripAdapter.OnImageChildViewTapListener, DetailsSectionStack.NoBottomSeparator, DetailsSectionStack.NoTopSeparator, LayoutSwitcher.RetryButtonListener
{
  public BitmapLoader mBitmapLoader;
  private List<Common.Image> mCombinedImagesToLoad;
  public Document mDocument;
  private final Handler mHandler = new Handler(Looper.myLooper());
  public boolean mHasDetailsLoaded;
  private AsyncTask<Void, Integer, Void> mImageLoadTask;
  public HorizontalStrip mImageStrip;
  private ImageStripAdapter mImageStripAdapter;
  private SparseArray<List<Common.Image>> mImageUrls = new SparseArray();
  private final List<BitmapLoader.BitmapContainer> mInFlightRequests = new ArrayList();
  private final Runnable mInvalidateRunnable = new Runnable()
  {
    public final void run()
    {
      ScreenshotGallery.this.mImageStripAdapter.mDataSetObservable.notifyInvalidated();
    }
  };
  private int mLastRequestedHeight;
  private LayoutSwitcher mLayoutSwitcher;
  public NavigationManager mNavigationManager;
  private int mNumImagesFailed;
  private Resources mResources;
  
  public ScreenshotGallery(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ScreenshotGallery(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mImageUrls.put(1, Collections.emptyList());
    this.mImageUrls.put(3, Collections.emptyList());
    this.mImageUrls.put(13, Collections.emptyList());
    this.mResources = paramContext.getResources();
  }
  
  protected void onDetachedFromWindow()
  {
    if (this.mImageLoadTask != null) {
      this.mImageLoadTask.cancel(true);
    }
    if (this.mImageStripAdapter != null) {
      this.mImageStripAdapter.mDataSetObservable.unregisterAll();
    }
    this.mHandler.removeCallbacks(this.mInvalidateRunnable);
    this.mImageStrip.setAdapter(null);
    this.mImageStripAdapter = null;
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mImageStrip = ((HorizontalStrip)findViewById(2131755393));
    this.mLayoutSwitcher = new LayoutSwitcher(this, 2131755393, this);
    this.mLayoutSwitcher.switchToLoadingDelayed(500);
  }
  
  public final void onImageChildViewTap(int paramInt)
  {
    this.mNavigationManager.goToScreenshots(this.mDocument, paramInt, false);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    startImageLoadingTaskIfNecessary();
  }
  
  public final void onRetry()
  {
    this.mLastRequestedHeight = 0;
    startImageLoadingTaskIfNecessary();
  }
  
  public final void startImageLoadingTaskIfNecessary()
  {
    int i = getHeight();
    if ((i == 0) || (i == this.mLastRequestedHeight) || (this.mBitmapLoader == null) || (getVisibility() == 8)) {}
    for (;;)
    {
      return;
      if (this.mDocument.hasScreenshots()) {}
      for (List localList1 = this.mDocument.getImages(1);; localList1 = Collections.emptyList())
      {
        if (!localList1.isEmpty()) {
          break label83;
        }
        if (!this.mHasDetailsLoaded) {
          break;
        }
        setVisibility(8);
        return;
      }
      label83:
      List localList2;
      int m;
      if ((this.mDocument != null) && (this.mDocument.mDocument.docid.equals(this.mDocument.mDocument.docid)) && (getVisibility() == 0))
      {
        localList2 = (List)this.mImageUrls.get(1);
        if (localList1.size() == localList2.size()) {
          break label306;
        }
        m = 0;
      }
      while (m == 0)
      {
        this.mImageUrls.clear();
        this.mImageUrls.put(1, localList1);
        this.mCombinedImagesToLoad = Lists.newArrayList(localList1);
        if (this.mImageStripAdapter != null) {
          this.mImageStripAdapter.mDataSetObservable.unregisterAll();
        }
        int j = localList1.size();
        int[] arrayOfInt = new int[j];
        Arrays.fill(arrayOfInt, 2);
        this.mImageStrip.setAppScreenshotStates(arrayOfInt);
        this.mImageStripAdapter = new ImageStripAdapter(j, this);
        this.mImageStrip.setAdapter(this.mImageStripAdapter);
        this.mLastRequestedHeight = getHeight();
        Iterator localIterator = this.mInFlightRequests.iterator();
        while (localIterator.hasNext())
        {
          BitmapLoader.BitmapContainer localBitmapContainer = (BitmapLoader.BitmapContainer)localIterator.next();
          if (localBitmapContainer != null) {
            localBitmapContainer.cancelRequest();
          }
        }
        label306:
        for (int k = 0;; k++)
        {
          if (k >= localList1.size()) {
            break label367;
          }
          if (!((Common.Image)localList1.get(k)).imageUrl.equals(((Common.Image)localList2.get(k)).imageUrl))
          {
            m = 0;
            break;
          }
        }
        label367:
        m = 1;
      }
    }
    this.mInFlightRequests.clear();
    if (this.mImageLoadTask != null) {
      this.mImageLoadTask.cancel(true);
    }
    this.mImageLoadTask = new AsyncTask()
    {
      private Void doInBackground$10299ca()
      {
        int i = 0;
        while ((!isCancelled()) && (ScreenshotGallery.this.mImageStripAdapter != null))
        {
          Integer[] arrayOfInteger = new Integer[1];
          arrayOfInteger[0] = Integer.valueOf(i);
          publishProgress(arrayOfInteger);
          i++;
          if (i >= ScreenshotGallery.this.mCombinedImagesToLoad.size()) {
            break;
          }
          try
          {
            Thread.sleep(400L);
          }
          catch (InterruptedException localInterruptedException) {}
        }
        return null;
      }
    };
    Utils.executeMultiThreaded(this.mImageLoadTask, new Void[0]);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ScreenshotGallery
 * JD-Core Version:    0.7.0.1
 */