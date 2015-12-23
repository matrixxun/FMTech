package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.view.View;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.ImageStripAdapter;
import com.google.android.finsky.adapters.Recyclable;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.HorizontalStrip;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Image.Dimension;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.NetworkInfoUtil;
import java.util.Arrays;
import java.util.List;

public class ScreenshotsModule
  extends FinskyModule<Data>
  implements ScreenshotsModuleLayout.ScreenshotsClickListener, ScreenshotsModuleLayout.ScreenshotsLoader
{
  private int[] mAppScreenshotStates;
  private Common.Image.Dimension[] mImageDimensions;
  private Drawable[] mImageDrawables;
  private Common.Image[] mImagesToLoad;
  private BitmapLoader.BitmapContainer[] mInFlightRequests;
  private int mNumImagesFailed;
  private int mNumRequestedImages = 0;
  private LoadImagesTask mRunningLoadImagesTask;
  private Boolean mShouldEnableTapToLoadScreenshots;
  private int mState;
  private LoadImagesTask mWaitingLoadImagesTask;
  
  private void cancelLoadImagesTasks()
  {
    if (this.mRunningLoadImagesTask != null) {
      this.mRunningLoadImagesTask.cancel(true);
    }
    if (this.mWaitingLoadImagesTask != null) {
      this.mWaitingLoadImagesTask.cancel(true);
    }
    this.mRunningLoadImagesTask = null;
    this.mWaitingLoadImagesTask = null;
  }
  
  private TransitionDrawable createFadeInDrawable(Bitmap paramBitmap)
  {
    Drawable[] arrayOfDrawable = new Drawable[2];
    arrayOfDrawable[0] = new ColorDrawable(0);
    arrayOfDrawable[1] = new BitmapDrawable(this.mContext.getResources(), paramBitmap);
    TransitionDrawable localTransitionDrawable = new TransitionDrawable(arrayOfDrawable);
    localTransitionDrawable.setCrossFadeEnabled(true);
    localTransitionDrawable.startTransition(250);
    return localTransitionDrawable;
  }
  
  private void executeLoadImagesTask(LoadImagesTask paramLoadImagesTask)
  {
    if (this.mWaitingLoadImagesTask != null) {
      FinskyLog.wtf("Tried to queue another LoadImageTask when another task was waiting.", new Object[0]);
    }
    do
    {
      return;
      this.mWaitingLoadImagesTask = paramLoadImagesTask;
    } while (this.mRunningLoadImagesTask != null);
    runWaitingLoadImagesTask();
  }
  
  private void loadInitialSetOfImages()
  {
    this.mState = 0;
    this.mImagesToLoad = ((Common.Image[])((Data)this.mModuleData).detailsDoc.getImages(1).toArray(new Common.Image[0]));
    int i = this.mImagesToLoad.length;
    this.mInFlightRequests = new BitmapLoader.BitmapContainer[i];
    this.mImageDimensions = new Common.Image.Dimension[i];
    for (int j = 0; j < i; j++) {
      this.mImageDimensions[j] = this.mImagesToLoad[j].dimension;
    }
    this.mImageDrawables = new Drawable[i];
    this.mNumImagesFailed = 0;
    cancelLoadImagesTasks();
    this.mNumRequestedImages = 0;
    if (FinskyApp.get().getExperiments().isEnabled(12602981L)) {}
    for (int k = Math.min(1 + ScreenshotsModuleLayout.getMaxNumImagesShown(this.mContext.getResources()), i);; k = i)
    {
      this.mAppScreenshotStates = new int[i];
      if (!shouldEnableTapToLoadScreenshots(this.mContext)) {
        break;
      }
      Arrays.fill(this.mAppScreenshotStates, 0);
      this.mState = 1;
      return;
    }
    Arrays.fill(this.mAppScreenshotStates, 2);
    executeLoadImagesTask(new LoadImagesTask(0, k - 1));
  }
  
  private void loadNextImage(final int paramInt)
  {
    final int i = this.mImagesToLoad.length;
    Common.Image localImage = this.mImagesToLoad[paramInt];
    if (localImage == null) {
      return;
    }
    boolean bool = localImage.supportsFifeUrlOptions;
    int j = 0;
    if (bool) {
      j = (int)this.mContext.getResources().getDimension(2131492967);
    }
    BitmapLoader.BitmapContainer localBitmapContainer = this.mBitmapLoader.get$6721551b(localImage.imageUrl, 0, j, new BitmapLoader.BitmapLoadedHandler()
    {
      public final void onResponse(BitmapLoader.BitmapContainer paramAnonymousBitmapContainer)
      {
        if (ScreenshotsModule.this.mState == 2) {}
        Bitmap localBitmap;
        do
        {
          return;
          ScreenshotsModule.this.mInFlightRequests[paramInt] = null;
          localBitmap = paramAnonymousBitmapContainer.mBitmap;
          if (localBitmap != null) {
            break;
          }
          if (ScreenshotsModule.this.shouldEnableTapToLoadScreenshots(ScreenshotsModule.this.mContext))
          {
            ScreenshotsModule.this.mAppScreenshotStates[paramInt] = 0;
            ScreenshotsModule.this.mFinskyModuleController.refreshModule(ScreenshotsModule.this, false);
            return;
          }
        } while (ScreenshotsModule.access$404(ScreenshotsModule.this) != i);
        ScreenshotsModule.access$002(ScreenshotsModule.this, 3);
        ScreenshotsModule.this.mFinskyModuleController.refreshModule(ScreenshotsModule.this, false);
        return;
        ScreenshotsModule.this.mAppScreenshotStates[paramInt] = 2;
        ScreenshotsModule.access$002(ScreenshotsModule.this, 1);
        ScreenshotsModule.this.mImageDrawables[paramInt] = ScreenshotsModule.this.createFadeInDrawable(localBitmap);
        ScreenshotsModule.this.mFinskyModuleController.refreshModule(ScreenshotsModule.this, false);
      }
    });
    Bitmap localBitmap = localBitmapContainer.mBitmap;
    if (localBitmap != null)
    {
      this.mAppScreenshotStates[paramInt] = 2;
      this.mState = 1;
      this.mImageDrawables[paramInt] = createFadeInDrawable(localBitmap);
      this.mFinskyModuleController.refreshModule(this, false);
      return;
    }
    this.mInFlightRequests[paramInt] = localBitmapContainer;
  }
  
  private void runWaitingLoadImagesTask()
  {
    this.mRunningLoadImagesTask = this.mWaitingLoadImagesTask;
    this.mWaitingLoadImagesTask = null;
    if (this.mRunningLoadImagesTask == null) {
      return;
    }
    int i = this.mNumRequestedImages;
    LoadImagesTask localLoadImagesTask = this.mRunningLoadImagesTask;
    this.mNumRequestedImages = (i + (1 + (localLoadImagesTask.mIndexOfLastImageToLoad - localLoadImagesTask.mIndexOfFirstImageToLoad)));
    Utils.executeMultiThreaded(this.mRunningLoadImagesTask, new Void[0]);
  }
  
  private boolean shouldEnableTapToLoadScreenshots(Context paramContext)
  {
    if (this.mShouldEnableTapToLoadScreenshots == null)
    {
      this.mShouldEnableTapToLoadScreenshots = Boolean.valueOf(false);
      if (FinskyApp.get().getExperiments().isEnabled(12603286L))
      {
        int i = NetworkInfoUtil.getNetworkType(paramContext);
        boolean bool;
        if (i != 1)
        {
          bool = false;
          if (i != 2) {}
        }
        else
        {
          bool = true;
        }
        this.mShouldEnableTapToLoadScreenshots = Boolean.valueOf(bool);
      }
    }
    return this.mShouldEnableTapToLoadScreenshots.booleanValue();
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if ((this.mModuleData != null) || (!paramDocument1.hasScreenshots())) {
      return;
    }
    this.mModuleData = new Data();
    ((Data)this.mModuleData).detailsDoc = paramDocument1;
    loadInitialSetOfImages();
  }
  
  public final void bindView(View paramView)
  {
    int i;
    if (this.mState == 0)
    {
      i = 1;
      if (this.mState != 3) {
        break label83;
      }
    }
    ScreenshotsModuleLayout localScreenshotsModuleLayout;
    Common.Image.Dimension[] arrayOfDimension;
    Drawable[] arrayOfDrawable;
    int[] arrayOfInt;
    boolean bool;
    label83:
    for (int j = 1;; j = 0)
    {
      localScreenshotsModuleLayout = (ScreenshotsModuleLayout)paramView;
      arrayOfDimension = this.mImageDimensions;
      arrayOfDrawable = this.mImageDrawables;
      arrayOfInt = this.mAppScreenshotStates;
      bool = shouldEnableTapToLoadScreenshots(this.mContext);
      localScreenshotsModuleLayout.mClickListener = this;
      localScreenshotsModuleLayout.mScreenshotsLoader = this;
      if (i == 0) {
        break label88;
      }
      localScreenshotsModuleLayout.mLayoutSwitcher.switchToLoadingMode();
      return;
      i = 0;
      break;
    }
    label88:
    if (j != 0)
    {
      localScreenshotsModuleLayout.mLayoutSwitcher.switchToErrorMode(localScreenshotsModuleLayout.getContext().getString(2131362712));
      return;
    }
    localScreenshotsModuleLayout.mLayoutSwitcher.switchToDataMode();
    if (localScreenshotsModuleLayout.mImageStripAdapter == null)
    {
      Drawable localDrawable = localScreenshotsModuleLayout.getPlaceholderDrawable();
      int i1 = arrayOfDrawable.length;
      for (int i2 = 0; i2 < i1; i2++) {
        if (arrayOfInt[i2] == 0) {
          arrayOfDrawable[i2] = localDrawable;
        }
      }
      localScreenshotsModuleLayout.mImageStripAdapter = new ImageStripAdapter(arrayOfDrawable.length, localScreenshotsModuleLayout);
      localScreenshotsModuleLayout.mImageStrip.setAppScreenshotStates(arrayOfInt);
      localScreenshotsModuleLayout.mImageStrip.setAdapter(localScreenshotsModuleLayout.mImageStripAdapter);
      if (!bool) {
        localScreenshotsModuleLayout.mImageStrip.setLoadAllScreenshotsListener(localScreenshotsModuleLayout);
      }
    }
    ImageStripAdapter localImageStripAdapter = localScreenshotsModuleLayout.mImageStripAdapter;
    int k = arrayOfDrawable.length;
    int m = localImageStripAdapter.mImageCount;
    int n = 0;
    if (k != m)
    {
      FinskyLog.wtf("Number of images don't match", new Object[0]);
      return;
    }
    while (n < localImageStripAdapter.mImageCount)
    {
      localImageStripAdapter.mImages[n] = arrayOfDrawable[n];
      localImageStripAdapter.mImageDimensions[n] = arrayOfDimension[n];
      n++;
    }
    localImageStripAdapter.mDataSetObservable.notifyChanged();
  }
  
  public final int getLayoutResId()
  {
    return 2130969090;
  }
  
  public final void loadAllScreenshots()
  {
    if ((this.mNumRequestedImages < this.mImagesToLoad.length) && (this.mWaitingLoadImagesTask == null)) {
      executeLoadImagesTask(new LoadImagesTask(this.mNumRequestedImages, -1 + this.mImagesToLoad.length));
    }
  }
  
  public final void onDestroyModule()
  {
    cancelLoadImagesTasks();
    if (this.mInFlightRequests != null) {
      for (int i = 0; i < this.mInFlightRequests.length; i++)
      {
        BitmapLoader.BitmapContainer localBitmapContainer = this.mInFlightRequests[i];
        if (localBitmapContainer != null) {
          localBitmapContainer.cancelRequest();
        }
      }
    }
    this.mState = 2;
  }
  
  public final void onImageClick(int paramInt)
  {
    if (this.mAppScreenshotStates[paramInt] == 0)
    {
      this.mAppScreenshotStates[paramInt] = 1;
      this.mFinskyModuleController.refreshModule(this, false);
      loadNextImage(paramInt);
      return;
    }
    this.mNavigationManager.goToScreenshots(((Data)this.mModuleData).detailsDoc, paramInt, shouldEnableTapToLoadScreenshots(this.mContext));
  }
  
  public final void onRecycleView(View paramView)
  {
    ((Recyclable)paramView).onRecycle();
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (paramModuleData != null) {
      loadInitialSetOfImages();
    }
  }
  
  public final void onRetryClick()
  {
    loadInitialSetOfImages();
    this.mFinskyModuleController.refreshModule(this, false);
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
  }
  
  private final class LoadImagesTask
    extends AsyncTask<Void, Integer, Void>
  {
    final int mIndexOfFirstImageToLoad;
    final int mIndexOfLastImageToLoad;
    
    public LoadImagesTask(int paramInt1, int paramInt2)
    {
      this.mIndexOfFirstImageToLoad = paramInt1;
      this.mIndexOfLastImageToLoad = paramInt2;
    }
    
    private Void doInBackground$10299ca()
    {
      int i = this.mIndexOfFirstImageToLoad;
      while (!isCancelled())
      {
        Integer[] arrayOfInteger = new Integer[1];
        arrayOfInteger[0] = Integer.valueOf(i);
        publishProgress(arrayOfInteger);
        i++;
        if (i > this.mIndexOfLastImageToLoad) {
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
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ScreenshotsModule
 * JD-Core Version:    0.7.0.1
 */