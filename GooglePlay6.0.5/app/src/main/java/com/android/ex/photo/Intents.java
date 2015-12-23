package com.android.ex.photo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class Intents
{
  public static final class PhotoViewIntentBuilder
  {
    public boolean mActionBarHiddenInitially;
    public boolean mDisplayFullScreenThumbs;
    private boolean mEnableTimerLightsOut;
    private String mInitialPhotoUri;
    private final Intent mIntent;
    private Float mMaxInitialScale;
    public Integer mPhotoIndex;
    private String mPhotosUri;
    public String[] mProjection;
    public String mResolvedPhotoUri;
    private boolean mScaleAnimation;
    private int mStartHeight;
    private int mStartWidth;
    private int mStartX;
    private int mStartY;
    public String mThumbnailUri;
    private boolean mWatchNetwork;
    
    private PhotoViewIntentBuilder(Context paramContext, Class<?> paramClass)
    {
      this.mIntent = new Intent(paramContext, paramClass);
      this.mScaleAnimation = false;
      this.mActionBarHiddenInitially = false;
      this.mDisplayFullScreenThumbs = false;
      this.mEnableTimerLightsOut = true;
    }
    
    public final Intent build()
    {
      this.mIntent.setAction("android.intent.action.VIEW");
      this.mIntent.setFlags(67633152);
      if (this.mPhotoIndex != null) {
        this.mIntent.putExtra("photo_index", this.mPhotoIndex.intValue());
      }
      if (this.mInitialPhotoUri != null) {
        this.mIntent.putExtra("initial_photo_uri", this.mInitialPhotoUri);
      }
      if ((this.mInitialPhotoUri != null) && (this.mPhotoIndex != null)) {
        throw new IllegalStateException("specified both photo index and photo uri");
      }
      if (this.mPhotosUri != null)
      {
        this.mIntent.putExtra("photos_uri", this.mPhotosUri);
        this.mIntent.setData(Uri.parse(this.mPhotosUri));
      }
      if (this.mResolvedPhotoUri != null) {
        this.mIntent.putExtra("resolved_photo_uri", this.mResolvedPhotoUri);
      }
      if (this.mProjection != null) {
        this.mIntent.putExtra("projection", this.mProjection);
      }
      if (this.mThumbnailUri != null) {
        this.mIntent.putExtra("thumbnail_uri", this.mThumbnailUri);
      }
      if (this.mMaxInitialScale != null) {
        this.mIntent.putExtra("max_scale", this.mMaxInitialScale);
      }
      this.mIntent.putExtra("watch_network", this.mWatchNetwork);
      this.mIntent.putExtra("scale_up_animation", this.mScaleAnimation);
      if (this.mScaleAnimation)
      {
        this.mIntent.putExtra("start_x_extra", this.mStartX);
        this.mIntent.putExtra("start_y_extra", this.mStartY);
        this.mIntent.putExtra("start_width_extra", this.mStartWidth);
        this.mIntent.putExtra("start_height_extra", this.mStartHeight);
      }
      this.mIntent.putExtra("action_bar_hidden_initially", this.mActionBarHiddenInitially);
      this.mIntent.putExtra("display_thumbs_fullscreen", this.mDisplayFullScreenThumbs);
      this.mIntent.putExtra("enable_timer_lights_out", this.mEnableTimerLightsOut);
      return this.mIntent;
    }
    
    public final PhotoViewIntentBuilder setMaxInitialScale(float paramFloat)
    {
      this.mMaxInitialScale = Float.valueOf(paramFloat);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.Intents
 * JD-Core Version:    0.7.0.1
 */