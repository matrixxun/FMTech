package com.google.android.finsky.detailspage;

import android.graphics.Bitmap;

public abstract interface ThumbnailTransitionParticipant
{
  public abstract void bindThumbnailAtTransitionEnd();
  
  public abstract void bindThumbnailAtTransitionStart(Bitmap paramBitmap);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ThumbnailTransitionParticipant
 * JD-Core Version:    0.7.0.1
 */