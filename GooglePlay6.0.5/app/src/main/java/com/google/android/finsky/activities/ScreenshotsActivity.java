package com.google.android.finsky.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.ScreenshotView;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.play.image.BitmapLoader;
import java.util.List;

public class ScreenshotsActivity
  extends Activity
{
  private Document mDocument;
  private ViewPager mPager;
  
  public static void show(Context paramContext, Document paramDocument, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, ScreenshotsActivity.class);
    localIntent.putExtra("document", paramDocument);
    localIntent.putExtra("index", paramInt1);
    localIntent.putExtra("imageType", paramInt2);
    localIntent.putExtra("enableTapToLoadScreenshots", paramBoolean);
    paramContext.startActivity(localIntent);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mDocument = ((Document)getIntent().getParcelableExtra("document"));
    int i = getIntent().getIntExtra("imageType", 1);
    boolean bool = getIntent().getBooleanExtra("enableTapToLoadScreenshots", false);
    List localList = this.mDocument.getImages(i);
    if (bool)
    {
      setContentView(2130969088);
      ((ScreenshotView)findViewById(2131756088)).setImage((Common.Image)localList.get(getIntent().getIntExtra("index", 0)), FinskyApp.get().mBitmapLoader);
    }
    for (;;)
    {
      if (ActivityManager.isUserAMonkey()) {
        finish();
      }
      return;
      setContentView(2130969089);
      this.mPager = ((ViewPager)findViewById(2131756091));
      this.mPager.setAdapter(new ImagePagerAdapter(localList, this, FinskyApp.get().mBitmapLoader));
      if (paramBundle == null) {
        this.mPager.setCurrentItem(getIntent().getIntExtra("index", 0));
      }
    }
  }
  
  private static final class ImagePagerAdapter
    extends PagerAdapter
  {
    private final BitmapLoader mBitmapLoader;
    private final List<Common.Image> mImages;
    private final LayoutInflater mInflater;
    
    public ImagePagerAdapter(List<Common.Image> paramList, Context paramContext, BitmapLoader paramBitmapLoader)
    {
      this.mImages = paramList;
      this.mInflater = LayoutInflater.from(paramContext);
      this.mBitmapLoader = paramBitmapLoader;
    }
    
    public final void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
    {
      paramViewGroup.removeView((ScreenshotView)paramObject);
    }
    
    public final int getCount()
    {
      return this.mImages.size();
    }
    
    public final Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
    {
      ScreenshotView localScreenshotView = (ScreenshotView)this.mInflater.inflate(2130969088, paramViewGroup, false);
      localScreenshotView.setImage((Common.Image)this.mImages.get(paramInt), this.mBitmapLoader);
      paramViewGroup.addView(localScreenshotView);
      return localScreenshotView;
    }
    
    public final boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ScreenshotsActivity
 * JD-Core Version:    0.7.0.1
 */