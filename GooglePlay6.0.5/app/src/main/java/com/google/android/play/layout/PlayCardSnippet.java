package com.google.android.play.layout;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.play.R.dimen;
import com.google.android.play.R.id;
import com.google.android.play.utils.PlayUtils;

public class PlayCardSnippet
  extends PlaySeparatorLayout
{
  private final int mAvatarLargeSize;
  private int mAvatarMarginLeft;
  private final int mAvatarRegularSize;
  private int mAvatarSize;
  private int mMode;
  private ImageView mSnippetAvatar;
  private TextView mSnippetText;
  private final int mTextLargeSize;
  private int mTextOnlyMarginLeft;
  private final int mTextRegularSize;
  
  public PlayCardSnippet(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardSnippet(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mAvatarRegularSize = localResources.getDimensionPixelSize(R.dimen.play_card_snippet_avatar_size);
    this.mAvatarLargeSize = localResources.getDimensionPixelSize(R.dimen.play_card_snippet_avatar_large_size);
    this.mTextRegularSize = localResources.getDimensionPixelSize(R.dimen.play_snippet_regular_size);
    this.mTextLargeSize = localResources.getDimensionPixelSize(R.dimen.play_snippet_large_size);
    this.mMode = 0;
  }
  
  private void syncWithCurrentSizeMode()
  {
    int i;
    TextView localTextView;
    if (this.mMode == 0)
    {
      i = this.mAvatarRegularSize;
      this.mAvatarSize = i;
      localTextView = this.mSnippetText;
      if (this.mMode != 0) {
        break label50;
      }
    }
    label50:
    for (float f = this.mTextRegularSize;; f = this.mTextLargeSize)
    {
      localTextView.setTextSize(0, f);
      return;
      i = this.mAvatarLargeSize;
      break;
    }
  }
  
  public ImageView getImageView()
  {
    return this.mSnippetAvatar;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSnippetText = ((TextView)findViewById(R.id.li_snippet_text));
    this.mSnippetAvatar = ((ImageView)findViewById(R.id.li_snippet_avatar));
    syncWithCurrentSizeMode();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    int i;
    int j;
    int k;
    int m;
    int n;
    for (boolean bool = true;; bool = false)
    {
      i = getHeight();
      j = getWidth();
      k = getPaddingTop();
      m = this.mSnippetText.getMeasuredHeight();
      n = this.mSnippetText.getMeasuredWidth();
      if (this.mSnippetAvatar.getVisibility() != 8) {
        break;
      }
      int i10 = k + (i - m - k) / 2;
      int i11 = PlayUtils.getViewLeftFromParentStart(j, n, bool, this.mTextOnlyMarginLeft);
      this.mSnippetText.layout(i11, i10, i11 + n, i10 + m);
      return;
    }
    int i1 = this.mSnippetAvatar.getMeasuredHeight();
    int i2 = this.mSnippetAvatar.getMeasuredWidth();
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mSnippetAvatar.getLayoutParams();
    int i3 = PlayUtils.getViewLeftFromParentStart(j, i2, bool, this.mAvatarMarginLeft);
    int i4 = i2 + this.mAvatarMarginLeft + MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams);
    if (i1 > m)
    {
      int i7 = k + (i - i1 - k) / 2;
      this.mSnippetAvatar.layout(i3, i7, i3 + i2, i7 + i1);
      int i8 = k + (i - m - k) / 2;
      int i9 = PlayUtils.getViewLeftFromParentStart(j, n, bool, i4);
      this.mSnippetText.layout(i9, i8, i9 + n, i8 + m);
      return;
    }
    int i5 = k + (i - m - k) / 2;
    this.mSnippetAvatar.layout(i3, i5, i3 + i2, i5 + i1);
    int i6 = PlayUtils.getViewLeftFromParentStart(j, n, bool, i4);
    this.mSnippetText.layout(i6, i5, i6 + n, i5 + m);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - getPaddingLeft() - getPaddingRight();
    int k;
    if (this.mSnippetAvatar.getVisibility() == 8)
    {
      k = 1;
      if (k == 0) {
        break label98;
      }
    }
    label98:
    ViewGroup.MarginLayoutParams localMarginLayoutParams;
    for (int n = j - this.mTextOnlyMarginLeft;; n = j - (this.mAvatarSize + localMarginLayoutParams.rightMargin + this.mAvatarMarginLeft))
    {
      this.mSnippetText.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), 0);
      setMeasuredDimension(i, Math.max(this.mAvatarSize, this.mSnippetText.getMeasuredHeight()) + getPaddingTop() + getPaddingBottom());
      return;
      k = 0;
      break;
      localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mSnippetAvatar.getLayoutParams();
      int m = View.MeasureSpec.makeMeasureSpec(this.mAvatarSize, 1073741824);
      this.mSnippetAvatar.measure(m, m);
    }
  }
  
  public void setSizeMode(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("Unsupported size mode: " + paramInt);
    }
    if (this.mMode != paramInt)
    {
      this.mMode = paramInt;
      syncWithCurrentSizeMode();
      requestLayout();
      invalidate();
    }
  }
  
  public final void setSnippetText(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    this.mSnippetText.setText(paramCharSequence);
    this.mTextOnlyMarginLeft = paramInt1;
    this.mAvatarMarginLeft = paramInt2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardSnippet
 * JD-Core Version:    0.7.0.1
 */