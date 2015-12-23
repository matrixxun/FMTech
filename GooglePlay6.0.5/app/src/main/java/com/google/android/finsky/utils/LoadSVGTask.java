package com.google.android.finsky.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

public final class LoadSVGTask
  extends AsyncTask<Integer, Integer, Bitmap>
{
  private final Context mContext;
  private final int mHeight;
  private final ImageView mImageView;
  private final int mWidth;
  
  public LoadSVGTask(Context paramContext, int paramInt1, int paramInt2, ImageView paramImageView)
  {
    this.mContext = paramContext;
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
    this.mImageView = paramImageView;
  }
  
  private Bitmap doInBackground(Integer... paramVarArgs)
  {
    try
    {
      SVG localSVG = SVG.getFromResource(this.mContext, paramVarArgs[0].intValue());
      Bitmap localBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
      localSVG.renderToCanvas(new Canvas(localBitmap));
      return localBitmap;
    }
    catch (SVGParseException localSVGParseException)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramVarArgs[0];
      arrayOfObject[1] = localSVGParseException.getMessage();
      FinskyLog.e("Error loading SVG resource 0x%x: %s", arrayOfObject);
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.LoadSVGTask
 * JD-Core Version:    0.7.0.1
 */