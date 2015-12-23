package com.google.android.play.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.id;
import com.google.android.play.R.styleable;
import com.google.android.play.cardview.CardViewGroupDelegate;
import com.google.android.play.cardview.CardViewGroupDelegates;
import com.google.android.play.utils.PlayTouchDelegate;

public abstract class PlayCardViewBase
  extends ForegroundRelativeLayout
{
  public static final boolean DISABLE_NESTED_FOCUS_TRAVERSAL;
  public TextView mAdCreative;
  public TextView mAdLabel;
  protected final int mAvatarSnippetMarginLeft;
  public int mBackendId;
  private final int mCardInset;
  public Object mData;
  protected PlayTextView mDescription;
  private Drawable mDisabledDrawable;
  public boolean mIsItemOwned;
  public PlayTextView mItemBadge;
  public PlayCardLabelView mLabel;
  public View mLoadingIndicator;
  protected Object mLoggingData;
  private final Rect mOldOverflowArea;
  public ImageView mOverflow;
  private final Rect mOverflowArea;
  private final int mOverflowTouchExtend;
  private final int mOwnershipRenderingType;
  public StarRatingBar mRatingBar;
  public final boolean mShowInlineCreatorBadge;
  public PlayCardSnippet mSnippet1;
  public PlayCardSnippet mSnippet2;
  public PlayTextView mSubtitle;
  public PlayTextView mSubtitle2;
  public boolean mSupportsSubtitleAndRating;
  protected final int mTextOnlySnippetMarginLeft;
  public PlayCardThumbnail mThumbnail;
  public float mThumbnailAspectRatio;
  public TextView mTitle;
  private boolean mToDisplayAsDisabled;
  
  static
  {
    if (Build.VERSION.SDK_INT <= 13) {}
    for (boolean bool = true;; bool = false)
    {
      DISABLE_NESTED_FOCUS_TRAVERSAL = bool;
      return;
    }
  }
  
  public PlayCardViewBase(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PlayCardViewBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayCardViewBase(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mOverflowTouchExtend = paramContext.getResources().getDimensionPixelSize(R.dimen.play_card_overflow_touch_extend);
    this.mOverflowArea = new Rect();
    this.mOldOverflowArea = new Rect();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayCardBaseView);
    this.mShowInlineCreatorBadge = localTypedArray.getBoolean(R.styleable.PlayCardBaseView_card_show_inline_creator_badge, false);
    this.mSupportsSubtitleAndRating = localTypedArray.getBoolean(R.styleable.PlayCardBaseView_card_supports_subtitle_and_rating, false);
    this.mTextOnlySnippetMarginLeft = localTypedArray.getDimensionPixelSize(R.styleable.PlayCardBaseView_card_text_only_snippet_margin_left, paramContext.getResources().getDimensionPixelSize(R.dimen.play_card_snippet_text_extra_margin_left));
    this.mAvatarSnippetMarginLeft = localTypedArray.getDimensionPixelSize(R.styleable.PlayCardBaseView_card_avatar_snippet_margin_left, 0);
    this.mOwnershipRenderingType = localTypedArray.getInt(R.styleable.PlayCardBaseView_card_owned_status_rendering_type, 1);
    localTypedArray.recycle();
    this.mCardInset = paramContext.getResources().getDimensionPixelSize(R.dimen.play_card_default_inset);
    setForegroundPadding(this.mCardInset, this.mCardInset, this.mCardInset, this.mCardInset);
    getCardViewGroupDelegate().initialize(this, paramContext, paramAttributeSet, paramInt);
  }
  
  public void bindLoading()
  {
    setOnClickListener(null);
    setClickable(false);
    setContentDescription(null);
    this.mLoadingIndicator.setVisibility(0);
    this.mTitle.setVisibility(8);
    if (this.mSubtitle != null) {
      this.mSubtitle.setVisibility(8);
    }
    if (this.mSubtitle2 != null) {
      this.mSubtitle2.setVisibility(8);
    }
    this.mThumbnail.setVisibility(8);
    if (this.mLabel != null) {
      this.mLabel.setVisibility(8);
    }
    if (this.mRatingBar != null) {
      this.mRatingBar.setVisibility(8);
    }
    if (this.mItemBadge != null) {
      this.mItemBadge.setVisibility(8);
    }
    if (this.mDescription != null) {
      this.mDescription.setVisibility(8);
    }
    if (this.mOverflow != null) {
      this.mOverflow.setVisibility(8);
    }
    if (this.mAdLabel != null) {
      this.mAdLabel.setVisibility(8);
    }
    if (this.mAdCreative != null) {
      this.mAdCreative.setVisibility(8);
    }
    setVisibility(0);
  }
  
  public final void clearCardState()
  {
    this.mThumbnail.setVisibility(8);
    setVisibility(4);
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    boolean bool = super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    if ((this.mToDisplayAsDisabled) && (paramAccessibilityEvent.getEventType() == 8)) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0)
      {
        paramAccessibilityEvent.setEnabled(false);
        bool = true;
      }
      return bool;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    if (this.mToDisplayAsDisabled)
    {
      if (this.mDisabledDrawable == null) {
        this.mDisabledDrawable = new PaintDrawable(getResources().getColor(R.color.play_dismissed_overlay));
      }
      this.mDisabledDrawable.setBounds(0, 0, i, j);
      this.mDisabledDrawable.draw(paramCanvas);
    }
  }
  
  public TextView getAdCreative()
  {
    return this.mAdCreative;
  }
  
  public TextView getAdLabel()
  {
    return this.mAdLabel;
  }
  
  public int getAvatarSnippetMarginLeft()
  {
    return this.mAvatarSnippetMarginLeft;
  }
  
  public abstract int getCardType();
  
  public CardViewGroupDelegate getCardViewGroupDelegate()
  {
    return CardViewGroupDelegates.IMPL;
  }
  
  public Object getData()
  {
    return this.mData;
  }
  
  public PlayTextView getDescription()
  {
    return this.mDescription;
  }
  
  public PlayTextView getItemBadge()
  {
    return this.mItemBadge;
  }
  
  public PlayCardLabelView getLabel()
  {
    return this.mLabel;
  }
  
  public View getLoadingIndicator()
  {
    return this.mLoadingIndicator;
  }
  
  public Object getLoggingData()
  {
    return this.mLoggingData;
  }
  
  public ImageView getOverflow()
  {
    return this.mOverflow;
  }
  
  public int getOwnershipRenderingType()
  {
    return this.mOwnershipRenderingType;
  }
  
  public StarRatingBar getRatingBar()
  {
    return this.mRatingBar;
  }
  
  public PlayCardSnippet getSnippet1()
  {
    return this.mSnippet1;
  }
  
  public PlayCardSnippet getSnippet2()
  {
    return this.mSnippet2;
  }
  
  public PlayTextView getSubtitle()
  {
    return this.mSubtitle;
  }
  
  public PlayTextView getSubtitle2()
  {
    return this.mSubtitle2;
  }
  
  public int getTextOnlySnippetMarginLeft()
  {
    return this.mTextOnlySnippetMarginLeft;
  }
  
  public PlayCardThumbnail getThumbnail()
  {
    return this.mThumbnail;
  }
  
  public TextView getTitle()
  {
    return this.mTitle;
  }
  
  public final void measureThumbnailSpanningHeight(int paramInt)
  {
    int i = View.MeasureSpec.getSize(paramInt);
    int j = getPaddingTop();
    int k = getPaddingBottom();
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    if (this.mThumbnail.getVisibility() != 8)
    {
      localMarginLayoutParams.width = ((int)((i - j - k - localMarginLayoutParams.topMargin - localMarginLayoutParams.bottomMargin) / this.mThumbnailAspectRatio));
      return;
    }
    localMarginLayoutParams.width = 0;
  }
  
  public final void measureThumbnailSpanningWidth(int paramInt)
  {
    int i = View.MeasureSpec.getSize(paramInt);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)this.mThumbnail.getLayoutParams();
    if (this.mThumbnail.getVisibility() != 8)
    {
      int m = i - j - k - localMarginLayoutParams.leftMargin - localMarginLayoutParams.rightMargin;
      localMarginLayoutParams.height = ((int)(this.mThumbnailAspectRatio * m));
      return;
    }
    localMarginLayoutParams.height = 0;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    PlayCardWindowLifecycleTracker.getInstance().cardAttachedToWindow(this);
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    PlayCardWindowLifecycleTracker.getInstance().cardDetachedFromWindow(this);
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mThumbnail = ((PlayCardThumbnail)findViewById(R.id.li_thumbnail_frame));
    this.mTitle = ((TextView)findViewById(R.id.li_title));
    this.mSubtitle = ((PlayTextView)findViewById(R.id.li_subtitle));
    this.mSubtitle2 = ((PlayTextView)findViewById(R.id.li_subtitle_2));
    this.mRatingBar = ((StarRatingBar)findViewById(R.id.li_rating));
    this.mItemBadge = ((PlayTextView)findViewById(R.id.li_badge));
    this.mDescription = ((PlayTextView)findViewById(R.id.li_description));
    this.mOverflow = ((ImageView)findViewById(R.id.li_overflow));
    this.mLabel = ((PlayCardLabelView)findViewById(R.id.li_label));
    this.mSnippet1 = ((PlayCardSnippet)findViewById(R.id.li_snippet_1));
    this.mSnippet2 = ((PlayCardSnippet)findViewById(R.id.li_snippet_2));
    this.mLoadingIndicator = findViewById(R.id.loading_progress_bar);
    this.mAdLabel = ((TextView)findViewById(R.id.li_ad_label));
    this.mAdCreative = ((TextView)findViewById(R.id.li_ad_creative));
    if (DISABLE_NESTED_FOCUS_TRAVERSAL)
    {
      setNextFocusRightId(-1);
      if (this.mOverflow != null)
      {
        this.mOverflow.setFocusable(false);
        this.mOverflow.setNextFocusLeftId(-1);
      }
    }
  }
  
  @TargetApi(14)
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    if (!this.mToDisplayAsDisabled) {}
    for (boolean bool = true;; bool = false)
    {
      paramAccessibilityNodeInfo.setEnabled(bool);
      return;
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mToDisplayAsDisabled) {
      return true;
    }
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    recomputeOverflowAreaIfNeeded();
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i;
    Layout localLayout;
    if ((this.mDescription != null) && (this.mDescription.getVisibility() == 0) && (!TextUtils.isEmpty(this.mDescription.getText())))
    {
      i = this.mDescription.getMeasuredHeight();
      localLayout = this.mDescription.getLayout();
      if (localLayout != null) {
        break label59;
      }
    }
    for (;;)
    {
      return;
      label59:
      for (int j = 0; j < localLayout.getLineCount(); j++) {
        if (localLayout.getLineBottom(j) > i)
        {
          PlayTextView localPlayTextView = this.mDescription;
          if (j >= 2) {}
          for (int k = 0;; k = 4)
          {
            localPlayTextView.setVisibility(k);
            return;
          }
        }
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mToDisplayAsDisabled) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public final void recomputeOverflowAreaIfNeeded()
  {
    if ((this.mOverflow == null) || (this.mOverflow.getVisibility() == 8)) {}
    do
    {
      return;
      this.mOverflow.getHitRect(this.mOverflowArea);
      Rect localRect1 = this.mOverflowArea;
      localRect1.top -= this.mOverflowTouchExtend;
      Rect localRect2 = this.mOverflowArea;
      localRect2.bottom += this.mOverflowTouchExtend;
      Rect localRect3 = this.mOverflowArea;
      localRect3.left -= this.mOverflowTouchExtend;
      Rect localRect4 = this.mOverflowArea;
      localRect4.right += this.mOverflowTouchExtend;
    } while ((this.mOverflowArea.top == this.mOldOverflowArea.top) && (this.mOverflowArea.bottom == this.mOldOverflowArea.bottom) && (this.mOverflowArea.left == this.mOldOverflowArea.left) && (this.mOverflowArea.right == this.mOldOverflowArea.right));
    setTouchDelegate(new PlayTouchDelegate(this.mOverflowArea, this.mOverflow));
    this.mOldOverflowArea.set(this.mOverflowArea);
  }
  
  public void setBackgroundColor(int paramInt)
  {
    getCardViewGroupDelegate().setBackgroundColor(this, paramInt);
  }
  
  public void setBackgroundResource(int paramInt)
  {
    getCardViewGroupDelegate().setBackgroundResource(this, paramInt);
  }
  
  public void setData(Object paramObject, int paramInt)
  {
    this.mData = paramObject;
    this.mBackendId = paramInt;
  }
  
  public void setDisplayAsDisabled(boolean paramBoolean)
  {
    if (this.mToDisplayAsDisabled == paramBoolean) {
      return;
    }
    this.mToDisplayAsDisabled = paramBoolean;
    if (this.mToDisplayAsDisabled) {}
    for (int i = 393216;; i = 131072)
    {
      setDescendantFocusability(i);
      invalidate();
      return;
    }
  }
  
  public void setItemOwned(boolean paramBoolean)
  {
    this.mIsItemOwned = paramBoolean;
  }
  
  public void setLoggingData(Object paramObject)
  {
    this.mLoggingData = paramObject;
  }
  
  public void setThumbnailAspectRatio(float paramFloat)
  {
    if (this.mThumbnailAspectRatio != paramFloat)
    {
      this.mThumbnailAspectRatio = paramFloat;
      requestLayout();
    }
  }
  
  public boolean supportsSubtitleAndRating()
  {
    return this.mSupportsSubtitleAndRating;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayCardViewBase
 * JD-Core Version:    0.7.0.1
 */