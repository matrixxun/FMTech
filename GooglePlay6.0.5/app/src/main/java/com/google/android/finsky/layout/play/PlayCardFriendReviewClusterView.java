package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.play.cardview.CardBubbleViewGroupDelegate;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import com.google.android.play.utils.PlayUtils;

public class PlayCardFriendReviewClusterView
  extends PlayClusterView
{
  private PersonAvatarView mAvatar;
  private boolean mIsWideLayout;
  private PlayCardFriendReviewHeaderView mPlayCardFriendReviewView;
  private TextView mReviewDate;
  private View mReviewer;
  private TextView mReviewerName;
  private final int mReviewerWideTopMargin;
  private StarRatingBar mStarRating;
  
  public PlayCardFriendReviewClusterView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardFriendReviewClusterView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mIsWideLayout = localResources.getBoolean(2131427335);
    this.mReviewerWideTopMargin = localResources.getDimensionPixelSize(2131493024);
  }
  
  public final void bindData(Document paramDocument, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode, PlayCardDismissListener paramPlayCardDismissListener)
  {
    configureLogging(paramDocument.mDocument.serverLogsCookie, paramPlayStoreUiElementNode);
    PlayCardFriendReviewHeaderView localPlayCardFriendReviewHeaderView = this.mPlayCardFriendReviewView;
    PlayStoreUiElementNode localPlayStoreUiElementNode = getParentOfChildren();
    PlayCardUtils.bindCard(localPlayCardFriendReviewHeaderView, paramDocument.getChildAt(0), paramDocument.mDocument.docid, paramBitmapLoader, paramNavigationManager, false, paramPlayCardDismissListener, localPlayStoreUiElementNode, true, -1, false);
    Review localReview1 = paramDocument.getFriendReview();
    localPlayCardFriendReviewHeaderView.mReview.setText(FastHtmlParser.fromHtml(localReview1.comment));
    Review localReview2 = paramDocument.getFriendReview();
    this.mStarRating.setRating(localReview2.starRating);
    TextView localTextView = this.mReviewDate;
    Context localContext = getContext();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = DateUtils.formatShortDisplayDate(localReview2.timestampMsec);
    localTextView.setText(localContext.getString(2131362560, arrayOfObject));
    DocV2 localDocV2 = localReview2.author;
    if (localDocV2 != null)
    {
      if (!TextUtils.isEmpty(localDocV2.title)) {
        this.mReviewerName.setText(localDocV2.title);
      }
      this.mAvatar.bindView(localDocV2, paramNavigationManager, paramBitmapLoader, null);
      return;
    }
    this.mAvatar.setVisibility(8);
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 424;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPlayCardFriendReviewView = ((PlayCardFriendReviewHeaderView)findViewById(2131755454));
    this.mStarRating = ((StarRatingBar)this.mPlayCardFriendReviewView.findViewById(2131755861));
    if (this.mIsWideLayout) {
      this.mPlayCardFriendReviewView.getCardViewGroupDelegate().setBubbleGravity(this.mPlayCardFriendReviewView, 8388611);
    }
    this.mReviewer = findViewById(2131755860);
    this.mAvatar = ((PersonAvatarView)this.mReviewer.findViewById(2131755382));
    this.mReviewerName = ((TextView)this.mReviewer.findViewById(2131755864));
    this.mReviewDate = ((TextView)this.mReviewer.findViewById(2131755865));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingTop();
    int j = ViewCompat.getPaddingStart(this);
    int k = getWidth();
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    int m;
    int n;
    for (boolean bool = true;; bool = false)
    {
      m = this.mPlayCardFriendReviewView.getMeasuredWidth();
      n = this.mReviewer.getMeasuredWidth();
      if (!this.mIsWideLayout) {
        break;
      }
      int i5 = PlayUtils.getViewLeftFromParentStart(k, n, bool, j);
      int i6 = i + this.mReviewerWideTopMargin;
      this.mReviewer.layout(i5, i6, i5 + n, i6 + this.mReviewer.getMeasuredHeight());
      int i7 = PlayUtils.getViewLeftFromParentStart(k, m, bool, j + n);
      this.mPlayCardFriendReviewView.layout(i7, i, i7 + m, i + this.mPlayCardFriendReviewView.getMeasuredHeight());
      return;
    }
    int i1 = this.mPlayCardFriendReviewView.getMeasuredHeight();
    int i2 = PlayUtils.getViewLeftFromParentStart(k, m, bool, j);
    this.mPlayCardFriendReviewView.layout(i2, i, i2 + m, i + i1);
    int i3 = i + i1;
    int i4 = PlayUtils.getViewLeftFromParentStart(k, n, bool, j);
    this.mReviewer.layout(i4, i3, i4 + n, i3 + this.mReviewer.getMeasuredHeight());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getPaddingTop();
    int j = getPaddingBottom();
    int k = ViewCompat.getPaddingStart(this);
    int m = ViewCompat.getPaddingEnd(this);
    int n = View.MeasureSpec.getSize(paramInt1);
    int i1 = n - k - m;
    if (this.mIsWideLayout)
    {
      this.mReviewer.measure(0, 0);
      int i3 = i1 - this.mReviewer.getMeasuredWidth();
      this.mPlayCardFriendReviewView.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), 0);
      setMeasuredDimension(n, j + (i + Math.max(this.mReviewer.getMeasuredHeight() + this.mReviewerWideTopMargin, this.mPlayCardFriendReviewView.getMeasuredHeight())));
      return;
    }
    int i2 = View.MeasureSpec.makeMeasureSpec(i1, 1073741824);
    this.mPlayCardFriendReviewView.measure(i2, 0);
    this.mReviewer.measure(i2, 0);
    setMeasuredDimension(n, j + (i + this.mPlayCardFriendReviewView.getMeasuredHeight() + this.mReviewer.getMeasuredHeight()));
  }
  
  public void setContentHorizontalPadding(int paramInt)
  {
    ViewCompat.setPaddingRelative(this, paramInt, getPaddingTop(), paramInt, getPaddingBottom());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardFriendReviewClusterView
 * JD-Core Version:    0.7.0.1
 */