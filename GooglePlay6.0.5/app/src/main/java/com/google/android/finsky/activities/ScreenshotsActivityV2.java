package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import com.android.ex.photo.ActionBarInterface;
import com.android.ex.photo.Intents.PhotoViewIntentBuilder;
import com.android.ex.photo.PhotoViewActivity;
import com.android.ex.photo.PhotoViewController;
import com.android.ex.photo.PhotoViewController.ActivityInterface;
import com.android.ex.photo.provider.PhotoContract.PhotoQuery;
import com.google.android.finsky.api.model.Document;

public class ScreenshotsActivityV2
  extends PhotoViewActivity
  implements PhotoViewController.ActivityInterface
{
  public static void show(Context paramContext, Document paramDocument, int paramInt1, int paramInt2)
  {
    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = new Intents.PhotoViewIntentBuilder(paramContext, ScreenshotsActivityV2.class, (byte)0);
    localPhotoViewIntentBuilder.mPhotoIndex = Integer.valueOf(paramInt1);
    localPhotoViewIntentBuilder.mProjection = PhotoContract.PhotoQuery.PROJECTION;
    localPhotoViewIntentBuilder.mActionBarHiddenInitially = true;
    localPhotoViewIntentBuilder.setMaxInitialScale(10.0F).mDisplayFullScreenThumbs = true;
    Intent localIntent = localPhotoViewIntentBuilder.build();
    localIntent.putExtra("document", paramDocument);
    localIntent.putExtra("imageType", paramInt2);
    paramContext.startActivity(localIntent);
  }
  
  public final PhotoViewController createController()
  {
    return new ScreenshotsViewController(this, ((Document)getIntent().getParcelableExtra("document")).getImages(getIntent().getIntExtra("imageType", 1)));
  }
  
  public final ActionBarInterface getActionBarInterface()
  {
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ScreenshotsActivityV2
 * JD-Core Version:    0.7.0.1
 */