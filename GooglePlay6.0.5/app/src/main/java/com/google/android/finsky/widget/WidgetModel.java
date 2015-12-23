package com.google.android.finsky.widget;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.Lists;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import java.util.List;

public final class WidgetModel
  implements Response.ErrorListener, OnDataChangedListener
{
  private final BitmapLoader mBitmapLoader;
  private final int[] mDocumentTypes;
  final int mImageHeightResource;
  private final ImageSelector mImageSelector;
  final List<PromotionalItem> mItems = Lists.newArrayList(10);
  DfeList mList;
  RefreshListener mListener;
  private int mLoadedImagesSoFar = 0;
  int mMaxHeight;
  private final int mMaxItems = 10;
  private int mSize;
  boolean mUpdatePending;
  
  public WidgetModel(BitmapLoader paramBitmapLoader, int[] paramArrayOfInt, ImageSelector paramImageSelector)
  {
    this.mBitmapLoader = paramBitmapLoader;
    this.mDocumentTypes = paramArrayOfInt;
    this.mImageSelector = paramImageSelector;
    this.mImageHeightResource = 2131493559;
  }
  
  private void loadViewsIfDone()
  {
    if (this.mLoadedImagesSoFar == this.mSize) {
      this.mListener.onData();
    }
  }
  
  final void bitmapLoaded(Document paramDocument, BitmapLoader.BitmapContainer paramBitmapContainer, int paramInt)
  {
    this.mLoadedImagesSoFar = (1 + this.mLoadedImagesSoFar);
    if (paramBitmapContainer.mBitmap != null) {
      this.mItems.add(new PromotionalItem(paramBitmapContainer.mBitmap, paramInt, paramDocument.mDocument.title, paramDocument.mDocument.creator, paramDocument));
    }
    loadViewsIfDone();
  }
  
  public final void onDataChanged()
  {
    this.mItems.clear();
    this.mUpdatePending = false;
    this.mLoadedImagesSoFar = 0;
    int i = Math.min(this.mList.getCount(), this.mMaxItems);
    this.mSize = i;
    int j = 0;
    if (j < i)
    {
      final Document localDocument = (Document)this.mList.getItem(j);
      Common.Image localImage = this.mImageSelector.getImage(localDocument, this.mMaxHeight);
      int k;
      if (this.mDocumentTypes == null) {
        k = 1;
      }
      for (;;)
      {
        if ((k != 0) && (localImage != null) && (!TextUtils.isEmpty(localImage.imageUrl))) {
          break label193;
        }
        this.mSize = (-1 + this.mSize);
        loadViewsIfDone();
        j++;
        break;
        if (localDocument.mDocument.hasDocType)
        {
          int n = localDocument.mDocument.docType;
          int[] arrayOfInt = this.mDocumentTypes;
          int i1 = arrayOfInt.length;
          for (int i2 = 0;; i2++)
          {
            if (i2 >= i1) {
              break label187;
            }
            if (arrayOfInt[i2] == n)
            {
              k = 1;
              break;
            }
          }
        }
        label187:
        k = 0;
      }
      label193:
      if (localImage.supportsFifeUrlOptions) {}
      for (int m = this.mMaxHeight;; m = 0)
      {
        BitmapLoader.BitmapContainer localBitmapContainer = this.mBitmapLoader.get(localImage.imageUrl, 0, m, new BitmapLoader.BitmapLoadedHandler()
        {
          public final void onResponse(BitmapLoader.BitmapContainer paramAnonymousBitmapContainer)
          {
            WidgetModel.this.bitmapLoaded(localDocument, paramAnonymousBitmapContainer, this.val$imageType);
          }
        });
        if (localBitmapContainer.mBitmap == null) {
          break;
        }
        bitmapLoaded(localDocument, localBitmapContainer, 2);
        break;
      }
    }
    loadViewsIfDone();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    this.mUpdatePending = false;
    this.mListener.onError(ErrorStrings.get(FinskyApp.get(), paramVolleyError));
  }
  
  public static abstract interface ImageSelector
  {
    public abstract Common.Image getImage(Document paramDocument, int paramInt);
  }
  
  public static final class PromotionalItem
  {
    public final String developer;
    public final Document doc;
    public final Bitmap image;
    public final int imageType;
    public final String title;
    
    public PromotionalItem(Bitmap paramBitmap, int paramInt, String paramString1, String paramString2, Document paramDocument)
    {
      this.image = paramBitmap;
      this.imageType = paramInt;
      this.title = paramString1;
      this.developer = paramString2;
      this.doc = paramDocument;
    }
  }
  
  public static abstract interface RefreshListener
  {
    public abstract void onData();
    
    public abstract void onError(String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.WidgetModel
 * JD-Core Version:    0.7.0.1
 */