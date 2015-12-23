package com.android.ex.photo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.ex.photo.PhotoViewCallbacks;
import com.android.ex.photo.PhotoViewCallbacks.CursorChangedListener;
import com.android.ex.photo.PhotoViewCallbacks.OnScreenListener;
import com.android.ex.photo.PhotoViewController.ActivityInterface;
import com.android.ex.photo.R.id;
import com.android.ex.photo.R.layout;
import com.android.ex.photo.adapters.PhotoPagerAdapter;
import com.android.ex.photo.loaders.PhotoBitmapLoaderInterface;
import com.android.ex.photo.loaders.PhotoBitmapLoaderInterface.BitmapResult;
import com.android.ex.photo.views.PhotoView;
import com.android.ex.photo.views.PhotoView.RotateRunnable;
import com.android.ex.photo.views.PhotoView.ScaleRunnable;
import com.android.ex.photo.views.PhotoView.SnapRunnable;
import com.android.ex.photo.views.PhotoView.TranslateRunnable;
import com.android.ex.photo.views.ProgressBarWrapper;

public class PhotoViewFragment
  extends Fragment
  implements LoaderManager.LoaderCallbacks<PhotoBitmapLoaderInterface.BitmapResult>, View.OnClickListener, PhotoViewCallbacks.CursorChangedListener, PhotoViewCallbacks.OnScreenListener
{
  protected PhotoPagerAdapter mAdapter;
  protected PhotoViewCallbacks mCallback;
  protected boolean mConnected;
  protected boolean mDisplayThumbsFullScreen;
  protected TextView mEmptyText;
  protected boolean mFullScreen;
  protected Intent mIntent;
  protected BroadcastReceiver mInternetStateReceiver;
  protected boolean mOnlyShowSpinner;
  protected View mPhotoPreviewAndProgress;
  protected ImageView mPhotoPreviewImage;
  protected ProgressBarWrapper mPhotoProgressBar;
  protected PhotoView mPhotoView;
  protected int mPosition;
  protected boolean mProgressBarNeeded = true;
  protected String mResolvedPhotoUri;
  protected ImageView mRetryButton;
  protected boolean mThumbnailShown;
  protected String mThumbnailUri;
  protected boolean mWatchNetworkState;
  
  private void enableImageTransforms(boolean paramBoolean)
  {
    this.mPhotoView.enableImageTransforms(paramBoolean);
  }
  
  public static PhotoViewFragment newInstance(Intent paramIntent, int paramInt, boolean paramBoolean)
  {
    PhotoViewFragment localPhotoViewFragment = new PhotoViewFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("arg-intent", paramIntent);
    localBundle.putInt("arg-position", paramInt);
    localBundle.putBoolean("arg-show-spinner", paramBoolean);
    localPhotoViewFragment.setArguments(localBundle);
    return localPhotoViewFragment;
  }
  
  private void resetViews()
  {
    if (this.mPhotoView != null) {
      this.mPhotoView.resetTransformations();
    }
  }
  
  private void setViewVisibility()
  {
    if (this.mCallback == null) {}
    for (boolean bool = false;; bool = this.mCallback.isFragmentFullScreen(this))
    {
      this.mFullScreen = bool;
      return;
    }
  }
  
  public final String getPhotoUri()
  {
    return this.mResolvedPhotoUri;
  }
  
  public final boolean isPhotoBound()
  {
    if (this.mPhotoView != null)
    {
      if (this.mPhotoView.mDrawable != null) {}
      for (int i = 1; i != 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mCallback = ((PhotoViewController.ActivityInterface)getActivity()).getController();
    if (this.mCallback == null) {
      throw new IllegalArgumentException("Activity must be a derived class of PhotoViewActivity");
    }
    this.mAdapter = this.mCallback.getAdapter();
    if (this.mAdapter == null) {
      throw new IllegalStateException("Callback reported null adapter");
    }
    setViewVisibility();
  }
  
  public void onClick(View paramView)
  {
    this.mCallback.toggleFullScreen();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle1 = this.mArguments;
    if (localBundle1 == null) {}
    do
    {
      return;
      this.mIntent = ((Intent)localBundle1.getParcelable("arg-intent"));
      this.mDisplayThumbsFullScreen = this.mIntent.getBooleanExtra("display_thumbs_fullscreen", false);
      this.mPosition = localBundle1.getInt("arg-position");
      this.mOnlyShowSpinner = localBundle1.getBoolean("arg-show-spinner");
      this.mProgressBarNeeded = true;
      if (paramBundle != null)
      {
        Bundle localBundle2 = paramBundle.getBundle("com.android.mail.photo.fragments.PhotoViewFragment.INTENT");
        if (localBundle2 != null) {
          this.mIntent = new Intent().putExtras(localBundle2);
        }
      }
    } while (this.mIntent == null);
    this.mResolvedPhotoUri = this.mIntent.getStringExtra("resolved_photo_uri");
    this.mThumbnailUri = this.mIntent.getStringExtra("thumbnail_uri");
    this.mWatchNetworkState = this.mIntent.getBooleanExtra("watch_network", false);
  }
  
  public final Loader<PhotoBitmapLoaderInterface.BitmapResult> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (this.mOnlyShowSpinner) {
      return null;
    }
    String str = null;
    switch (paramInt)
    {
    }
    for (;;)
    {
      return this.mCallback.onCreateBitmapLoader$21c6b1c7(paramInt, str);
      str = this.mThumbnailUri;
      continue;
      str = this.mResolvedPhotoUri;
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.photo_fragment_view, paramViewGroup, false);
    this.mPhotoView = ((PhotoView)localView.findViewById(R.id.photo_view));
    this.mPhotoView.setMaxInitialScale(this.mIntent.getFloatExtra("max_scale", 1.0F));
    this.mPhotoView.setOnClickListener(this);
    PhotoView localPhotoView = this.mPhotoView;
    boolean bool = this.mFullScreen;
    if (bool != localPhotoView.mFullScreen)
    {
      localPhotoView.mFullScreen = bool;
      localPhotoView.requestLayout();
      localPhotoView.invalidate();
    }
    this.mPhotoView.enableImageTransforms(false);
    this.mPhotoPreviewAndProgress = localView.findViewById(R.id.photo_preview);
    this.mPhotoPreviewImage = ((ImageView)localView.findViewById(R.id.photo_preview_image));
    this.mThumbnailShown = false;
    ProgressBar localProgressBar = (ProgressBar)localView.findViewById(R.id.indeterminate_progress);
    this.mPhotoProgressBar = new ProgressBarWrapper((ProgressBar)localView.findViewById(R.id.determinate_progress), localProgressBar);
    this.mEmptyText = ((TextView)localView.findViewById(R.id.empty_text));
    this.mRetryButton = ((ImageView)localView.findViewById(R.id.retry_button));
    setViewVisibility();
    return localView;
  }
  
  public final void onCursorChanged(Cursor paramCursor)
  {
    if (this.mAdapter == null) {}
    Loader localLoader2;
    do
    {
      LoaderManager localLoaderManager;
      do
      {
        do
        {
          return;
        } while ((!paramCursor.moveToPosition(this.mPosition)) || (isPhotoBound()));
        localLoaderManager = getLoaderManager();
        Loader localLoader1 = localLoaderManager.getLoader(3);
        if (localLoader1 != null)
        {
          PhotoBitmapLoaderInterface localPhotoBitmapLoaderInterface2 = (PhotoBitmapLoaderInterface)localLoader1;
          this.mResolvedPhotoUri = this.mAdapter.getPhotoUri(paramCursor);
          localPhotoBitmapLoaderInterface2.setPhotoUri(this.mResolvedPhotoUri);
          localPhotoBitmapLoaderInterface2.forceLoad();
        }
      } while (this.mThumbnailShown);
      localLoader2 = localLoaderManager.getLoader(2);
    } while (localLoader2 == null);
    PhotoBitmapLoaderInterface localPhotoBitmapLoaderInterface1 = (PhotoBitmapLoaderInterface)localLoader2;
    this.mThumbnailUri = this.mAdapter.getThumbnailUri(paramCursor);
    localPhotoBitmapLoaderInterface1.setPhotoUri(this.mThumbnailUri);
    localPhotoBitmapLoaderInterface1.forceLoad();
  }
  
  public final void onDestroyView()
  {
    if (this.mPhotoView != null)
    {
      PhotoView localPhotoView = this.mPhotoView;
      localPhotoView.mGestureDetector = null;
      localPhotoView.mScaleGetureDetector = null;
      localPhotoView.mDrawable = null;
      localPhotoView.mScaleRunnable.stop();
      localPhotoView.mScaleRunnable = null;
      localPhotoView.mTranslateRunnable.stop();
      localPhotoView.mTranslateRunnable = null;
      localPhotoView.mSnapRunnable.stop();
      localPhotoView.mSnapRunnable = null;
      localPhotoView.mRotateRunnable.stop();
      localPhotoView.mRotateRunnable = null;
      localPhotoView.setOnClickListener(null);
      localPhotoView.mExternalClickListener = null;
      localPhotoView.mDoubleTapOccurred = false;
      this.mPhotoView = null;
    }
    super.onDestroyView();
  }
  
  public final void onDetach()
  {
    this.mCallback = null;
    super.onDetach();
  }
  
  public final void onFullScreenChanged$1385ff()
  {
    setViewVisibility();
  }
  
  public final boolean onInterceptMoveLeft$2548a39()
  {
    if (!this.mCallback.isFragmentActive(this)) {}
    for (;;)
    {
      return false;
      if (this.mPhotoView != null)
      {
        PhotoView localPhotoView = this.mPhotoView;
        int i;
        if (!localPhotoView.mTransformsEnabled) {
          i = 0;
        }
        while (i != 0)
        {
          return true;
          if (!PhotoView.TranslateRunnable.access$000(localPhotoView.mTranslateRunnable))
          {
            localPhotoView.mMatrix.getValues(localPhotoView.mValues);
            localPhotoView.mTranslateRect.set(localPhotoView.mTempSrc);
            localPhotoView.mMatrix.mapRect(localPhotoView.mTranslateRect);
            float f1 = localPhotoView.getWidth();
            float f2 = localPhotoView.mValues[2];
            float f3 = localPhotoView.mTranslateRect.right - localPhotoView.mTranslateRect.left;
            if ((!localPhotoView.mTransformsEnabled) || (f3 <= f1))
            {
              i = 0;
              continue;
            }
            if (f2 == 0.0F)
            {
              i = 0;
              continue;
            }
          }
          i = 1;
        }
      }
    }
  }
  
  public final boolean onInterceptMoveRight$2548a39()
  {
    if (!this.mCallback.isFragmentActive(this)) {}
    for (;;)
    {
      return false;
      if (this.mPhotoView != null)
      {
        PhotoView localPhotoView = this.mPhotoView;
        int i;
        if (!localPhotoView.mTransformsEnabled) {
          i = 0;
        }
        while (i != 0)
        {
          return true;
          if (!PhotoView.TranslateRunnable.access$000(localPhotoView.mTranslateRunnable))
          {
            localPhotoView.mMatrix.getValues(localPhotoView.mValues);
            localPhotoView.mTranslateRect.set(localPhotoView.mTempSrc);
            localPhotoView.mMatrix.mapRect(localPhotoView.mTranslateRect);
            float f1 = localPhotoView.getWidth();
            float f2 = localPhotoView.mValues[2];
            float f3 = localPhotoView.mTranslateRect.right - localPhotoView.mTranslateRect.left;
            if ((!localPhotoView.mTransformsEnabled) || (f3 <= f1))
            {
              i = 0;
              continue;
            }
            if ((f2 != 0.0F) && (f1 >= f3 + f2))
            {
              i = 0;
              continue;
            }
          }
          i = 1;
        }
      }
    }
  }
  
  public final void onLoaderReset$5dda1f52() {}
  
  public final void onPause()
  {
    if (this.mWatchNetworkState) {
      getActivity().unregisterReceiver(this.mInternetStateReceiver);
    }
    this.mCallback.removeCursorListener(this);
    this.mCallback.removeScreenListener(this.mPosition);
    PhotoView localPhotoView;
    boolean bool1;
    if (this.mPhotoView != null)
    {
      localPhotoView = this.mPhotoView;
      bool1 = localPhotoView.mDrawable instanceof BitmapDrawable;
      if (bool1) {
        break label117;
      }
    }
    label117:
    for (boolean bool2 = true;; bool2 = false)
    {
      if ((localPhotoView.mDrawable != null) && (bool1))
      {
        if (((BitmapDrawable)localPhotoView.mDrawable).getBitmap() != null)
        {
          localPhotoView.mMinScale = 0.0F;
          localPhotoView.mDrawable = null;
          bool2 = false;
        }
      }
      else
      {
        localPhotoView.configureBounds(bool2);
        localPhotoView.invalidate();
      }
      super.onPause();
      return;
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    this.mCallback.addScreenListener(this.mPosition, this);
    this.mCallback.addCursorListener(this);
    NetworkInfo localNetworkInfo;
    if (this.mWatchNetworkState)
    {
      if (this.mInternetStateReceiver == null) {
        this.mInternetStateReceiver = new InternetStateBroadcastReceiver((byte)0);
      }
      getActivity().registerReceiver(this.mInternetStateReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
      localNetworkInfo = ((ConnectivityManager)getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo == null) {
        break label152;
      }
    }
    label152:
    for (this.mConnected = localNetworkInfo.isConnected();; this.mConnected = false)
    {
      if (!isPhotoBound())
      {
        this.mProgressBarNeeded = true;
        this.mPhotoPreviewAndProgress.setVisibility(0);
        getLoaderManager().initLoader(2, null, this);
        getLoaderManager().initLoader(3, null, this);
      }
      return;
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mIntent != null) {
      paramBundle.putParcelable("com.android.mail.photo.fragments.PhotoViewFragment.INTENT", this.mIntent.getExtras());
    }
  }
  
  public final void onViewActivated()
  {
    if (!this.mCallback.isFragmentActive(this)) {
      resetViews();
    }
    while (isPhotoBound()) {
      return;
    }
    getLoaderManager().restartLoader$71be8de6(2, this);
  }
  
  public final void onViewUpNext()
  {
    resetViews();
  }
  
  private final class InternetStateBroadcastReceiver
    extends BroadcastReceiver
  {
    private InternetStateBroadcastReceiver() {}
    
    public final void onReceive(Context paramContext, Intent paramIntent)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected())) {
        PhotoViewFragment.this.mConnected = false;
      }
      while ((PhotoViewFragment.this.mConnected) || (PhotoViewFragment.this.isPhotoBound())) {
        return;
      }
      if (!PhotoViewFragment.this.mThumbnailShown) {
        PhotoViewFragment.this.getLoaderManager().restartLoader$71be8de6(2, PhotoViewFragment.this);
      }
      PhotoViewFragment.this.getLoaderManager().restartLoader$71be8de6(3, PhotoViewFragment.this);
      PhotoViewFragment.this.mConnected = true;
      PhotoViewFragment.this.mPhotoProgressBar.setVisibility(0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.fragments.PhotoViewFragment
 * JD-Core Version:    0.7.0.1
 */