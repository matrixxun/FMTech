package com.google.android.finsky.layout.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Checkable;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.play.layout.PlayCardLabelView;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;

public class PlayCardViewMyApps
  extends PlayCardViewBase
  implements Checkable, Identifiable
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private TextView mAppSize;
  private final Rect mArchiveArea = new Rect();
  private View mArchiveView;
  private boolean mChecked = false;
  private String mIdentifier;
  private final Rect mOldArchiveArea = new Rect();
  private View mRatingContainer;
  
  public PlayCardViewMyApps(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewMyApps(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mThumbnailAspectRatio = 1.0F;
  }
  
  public TextView getAppSize()
  {
    return this.mAppSize;
  }
  
  public int getCardType()
  {
    return 9;
  }
  
  public String getIdentifier()
  {
    return this.mIdentifier;
  }
  
  public boolean isChecked()
  {
    return this.mChecked;
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (this.mChecked) {
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mArchiveView = findViewById(2131755868);
    this.mAppSize = ((TextView)findViewById(2131755870));
    this.mRatingContainer = findViewById(2131755884);
  }
  
  @SuppressLint({"DrawAllocation"})
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.mAppSize != null) && (this.mAppSize.getVisibility() != 8))
    {
      int i = this.mAppSize.getMeasuredWidth();
      int j = this.mAppSize.getMeasuredHeight();
      int k = this.mRatingContainer.getTop() + Math.max(this.mRatingBar.getHeight(), this.mItemBadge.getHeight()) / 2;
      int m = this.mLabel.getRight() - i;
      int n = k - j / 2;
      int i1 = this.mLabel.getRight();
      int i2 = n + j;
      this.mAppSize.layout(m, n, i1, i2);
    }
    if ((this.mArchiveView == null) || (this.mArchiveView.getVisibility() == 8)) {}
    do
    {
      return;
      this.mArchiveView.getHitRect(this.mArchiveArea);
      Rect localRect1 = this.mArchiveArea;
      localRect1.bottom += this.mArchiveArea.height() / 2;
      Rect localRect2 = this.mArchiveArea;
      localRect2.top -= this.mArchiveView.getTop();
      Rect localRect3 = this.mArchiveArea;
      localRect3.left -= this.mArchiveArea.width() / 2;
      Rect localRect4 = this.mArchiveArea;
      localRect4.right += getWidth() - this.mArchiveView.getRight();
    } while (this.mArchiveArea.equals(this.mOldArchiveArea));
    setTouchDelegate(new TouchDelegate(this.mArchiveArea, this.mArchiveView));
    this.mOldArchiveArea.set(this.mArchiveArea);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    measureThumbnailSpanningHeight(paramInt2);
    int i = Math.min(View.MeasureSpec.makeMeasureSpec(((ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams()).height, 1073741824), paramInt2);
    this.mThumbnail.measure(i, i);
    super.onMeasure(paramInt1, paramInt2);
    if ((this.mAppSize != null) && (this.mAppSize.getVisibility() != 8))
    {
      int j = getPaddingLeft();
      int k = getPaddingRight();
      int m = View.MeasureSpec.getSize(paramInt1) - j - k;
      this.mAppSize.measure(View.MeasureSpec.makeMeasureSpec(m, -2147483648), 0);
    }
  }
  
  public final void setArchivable(boolean paramBoolean, final OnArchiveActionListener paramOnArchiveActionListener)
  {
    if (!paramBoolean)
    {
      this.mArchiveView.setVisibility(8);
      setNextFocusRightId(-1);
      this.mArchiveView.setOnClickListener(null);
      this.mArchiveView.setClickable(false);
      return;
    }
    this.mArchiveView.setVisibility(0);
    this.mArchiveView.setFocusable(true);
    this.mArchiveView.setNextFocusLeftId(2131755258);
    setNextFocusRightId(this.mArchiveView.getId());
    this.mArchiveView.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        Document localDocument = (Document)PlayCardViewMyApps.this.getData();
        paramOnArchiveActionListener.onArchiveAction(localDocument);
      }
    });
  }
  
  public void setChecked(boolean paramBoolean)
  {
    if (this.mChecked == paramBoolean) {
      return;
    }
    this.mChecked = paramBoolean;
    refreshDrawableState();
  }
  
  public void setIdentifier(String paramString)
  {
    this.mIdentifier = paramString;
  }
  
  public void toggle()
  {
    if (!this.mChecked) {}
    for (boolean bool = true;; bool = false)
    {
      setChecked(bool);
      return;
    }
  }
  
  public static abstract interface OnArchiveActionListener
  {
    public abstract void onArchiveAction(Document paramDocument);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewMyApps
 * JD-Core Version:    0.7.0.1
 */