package com.google.android.play.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.play.R.id;
import com.google.android.play.R.styleable;

public class PlayCardThumbnail
  extends FrameLayout
{
  private final int mAppThumbnailPadding;
  private final int mOriginalPaddingBottom;
  private final int mOriginalPaddingEnd;
  private final int mOriginalPaddingStart;
  private final int mOriginalPaddingTop;
  private ImageView mThumbnail;
  private final int mThumbnailId;
  
  public PlayCardThumbnail(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardThumbnail(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardThumbnail);
    this.mThumbnailId = localTypedArray.getResourceId(R.styleable.PlayCardThumbnail_thumbnail_id, R.id.li_thumbnail);
    this.mAppThumbnailPadding = localTypedArray.getDimensionPixelSize(R.styleable.PlayCardThumbnail_app_thumbnail_padding, 0);
    localTypedArray.recycle();
    this.mOriginalPaddingStart = ViewCompat.getPaddingStart(this);
    this.mOriginalPaddingTop = getPaddingTop();
    this.mOriginalPaddingEnd = ViewCompat.getPaddingEnd(this);
    this.mOriginalPaddingBottom = getPaddingBottom();
  }
  
  public int getBaseline()
  {
    return getPaddingTop() + this.mThumbnail.getMeasuredHeight();
  }
  
  public ImageView getImageView()
  {
    return this.mThumbnail;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mThumbnail = ((ImageView)findViewById(this.mThumbnailId));
  }
  
  public final void updateThumbnailPadding(int paramInt)
  {
    int i;
    int j;
    int k;
    int m;
    if (paramInt == 3)
    {
      i = this.mAppThumbnailPadding;
      j = this.mAppThumbnailPadding;
      k = this.mAppThumbnailPadding;
      m = this.mAppThumbnailPadding;
      if ((i == getPaddingLeft()) && (j == getPaddingTop()) && (k == getPaddingRight()) && (m == getPaddingBottom())) {
        break label109;
      }
    }
    label109:
    for (int n = 1;; n = 0)
    {
      if (n != 0)
      {
        ViewCompat.setPaddingRelative(this, i, j, k, m);
        requestLayout();
      }
      return;
      i = this.mOriginalPaddingStart;
      j = this.mOriginalPaddingTop;
      k = this.mOriginalPaddingEnd;
      m = this.mOriginalPaddingBottom;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardThumbnail
 * JD-Core Version:    0.7.0.1
 */