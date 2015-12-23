package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.finsky.layout.play.CardTextView;
import com.google.android.finsky.layout.play.CircularBackgroundView;
import com.google.android.finsky.layout.play.PlayCardClusterViewHeader;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.PlayUtils;

public class DetailsAvatarClusterHeader
  extends PlayCardClusterViewHeader
{
  private final int mAvatarSize;
  private FifeImageView mAvatarView;
  private final int mBackgroundHeight;
  private final int mRegularHeaderXPadding;
  
  public DetailsAvatarClusterHeader(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsAvatarClusterHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mBackgroundHeight = localResources.getDimensionPixelSize(2131493025);
    this.mAvatarSize = localResources.getDimensionPixelSize(2131492936);
    this.mRegularHeaderXPadding = localResources.getDimensionPixelSize(2131493080);
  }
  
  public int getBackgroundImageHeight()
  {
    return this.mBackgroundHeight;
  }
  
  public int getBackgroundImageTopMargin()
  {
    if (this.mAvatarView.getVisibility() != 8) {
      return ((ViewGroup.MarginLayoutParams)this.mHeaderImage.getLayoutParams()).topMargin;
    }
    return 0;
  }
  
  public int getContentBottom()
  {
    return this.mMoreView.getBottom();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAvatarView = ((FifeImageView)findViewById(2131755382));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    int i;
    int j;
    int k;
    for (boolean bool = true;; bool = false)
    {
      i = getWidth();
      j = getHeight();
      k = ViewCompat.getPaddingStart(this);
      int m = getPaddingTop();
      this.mHeaderImage.layout(k, m + getBackgroundImageTopMargin(), k + this.mHeaderImage.getMeasuredWidth(), m + getBackgroundImageTopMargin() + this.mHeaderImage.getMeasuredHeight());
      int n = i / 2;
      if (this.mAvatarView.getVisibility() == 8) {
        break;
      }
      int i9 = this.mAvatarView.getMeasuredWidth();
      int i10 = PlayUtils.getViewLeftFromParentStart(i, i9, bool, n - this.mAvatarView.getMeasuredWidth() / 2);
      this.mAvatarView.layout(i10, m, i10 + i9, m + this.mAvatarView.getMeasuredHeight());
      int i11 = this.mAvatarView.getBottom();
      int i12 = this.mTitleGroup.getMeasuredWidth();
      int i13 = PlayUtils.getViewLeftFromParentStart(i, i12, bool, n - this.mTitleGroup.getMeasuredWidth() / 2);
      this.mTitleGroup.layout(i13, i11, i13 + i12, i11 + this.mTitleGroup.getMeasuredHeight());
      int i14 = this.mMoreView.getMeasuredWidth();
      int i15 = PlayUtils.getViewLeftFromParentStart(i, i14, bool, n - this.mMoreView.getMeasuredWidth() / 2);
      this.mMoreView.layout(i15, this.mTitleGroup.getBottom(), i15 + i14, this.mTitleGroup.getBottom() + this.mMoreView.getMeasuredHeight());
      return;
    }
    int i1 = j * 2 / 3;
    int i2 = this.mTitleGroup.getMeasuredWidth();
    int i3 = this.mTitleGroup.getMeasuredHeight();
    int i4 = PlayUtils.getViewLeftFromParentStart(i, i2, bool, k + this.mRegularHeaderXPadding);
    this.mTitleGroup.layout(i4, i1 - i3, i4 + i2, i1);
    int i5 = this.mMoreView.getMeasuredWidth();
    int i6 = this.mMoreView.getMeasuredHeight();
    int i7 = i1 - (i3 - i6) / 2;
    int i8 = PlayUtils.getViewLeftFromParentEnd(i, i5, bool, ViewCompat.getPaddingEnd(this) + this.mRegularHeaderXPadding);
    this.mMoreView.layout(i8, i7 - i6, i8 + i5, i7);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    int n = View.MeasureSpec.makeMeasureSpec(this.mBackgroundHeight, 1073741824);
    this.mHeaderImage.measure(m, n);
    if (this.mAvatarView.getVisibility() != 8)
    {
      int i2 = View.MeasureSpec.makeMeasureSpec(this.mAvatarSize, 1073741824);
      this.mAvatarView.measure(i2, i2);
    }
    this.mTitleGroup.measure(0, 0);
    this.mMoreView.measure(0, 0);
    int i1 = this.mBackgroundHeight + getPaddingTop() + getPaddingBottom() + getBackgroundImageTopMargin();
    if (k == 1073741824) {
      i1 = j;
    }
    for (;;)
    {
      setMeasuredDimension(i, i1);
      return;
      if (k == -2147483648) {
        i1 = Math.min(i1, j);
      }
    }
  }
  
  public final void setContent(BitmapLoader paramBitmapLoader, int paramInt, Common.Image paramImage1, Common.Image paramImage2, Badge paramBadge, String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener)
  {
    Resources localResources = getContext().getResources();
    this.mHeaderImage.setBackgroundColor(localResources.getColor(2131689623));
    super.setContent(paramInt, paramString1, null, paramString3, paramOnClickListener, paramBitmapLoader, paramImage1, 0, null, null);
    BadgeUtils.configureBadge(paramBadge, paramBitmapLoader, (DecoratedTextView)this.mTitleMain);
    int i = localResources.getColor(2131689624);
    this.mHeaderImage.setColorFilter(Color.argb(51, Color.red(i), Color.green(i), Color.blue(i)));
    if (paramImage2 != null)
    {
      this.mAvatarView.setImage(paramImage2.imageUrl, paramImage2.supportsFifeUrlOptions, paramBitmapLoader);
      this.mAvatarView.setVisibility(0);
      return;
    }
    this.mAvatarView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsAvatarClusterHeader
 * JD-Core Version:    0.7.0.1
 */