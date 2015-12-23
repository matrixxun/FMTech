package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PersonAvatarView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.layout.PlayPopupMenu;
import com.google.android.play.layout.PlayPopupMenu.OnPopupActionSelectedListener;
import com.google.android.play.utils.PlayTouchDelegate;

public class ReviewItemLayout
  extends RelativeLayout
  implements PlayPopupMenu.OnPopupActionSelectedListener
{
  private View mActionContainer;
  private ImageView mActionImage;
  private ImageView mActionOverflow;
  private TextView mActionText;
  private TextView mAuthor;
  private TextView mBody;
  private boolean mCanFeedback;
  private ReviewItemHeaderLayout mHeader;
  private boolean mIsYourReview;
  private TextView mMainAction;
  private TextView mMetadata;
  private final Rect mOverflowExtendArea = new Rect();
  private PlayStoreUiElementNode mParentNode;
  private PersonAvatarView mProfilePicture;
  private ReviewFeedbackActionListener mReviewFeedbackActionListener;
  private ReviewReplyLayout mReviewReplyLayout;
  private ViewStub mReviewReplyStub;
  private TextView mTitle;
  private final int mTouchExtendSize;
  private boolean mUseFeedbackV2;
  private TextView mYourReviewLabel;
  
  public ReviewItemLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewItemLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mTouchExtendSize = (2 * paramContext.getResources().getDimensionPixelSize(2131493389));
  }
  
  public final void onActionSelected(int paramInt)
  {
    if (this.mReviewFeedbackActionListener == null) {
      return;
    }
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Unknown item selected on ReviewItemLayout overflow menu: %d", arrayOfObject);
      return;
    }
    this.mReviewFeedbackActionListener.onMarkAsSpamClick(this);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mAuthor = ((TextView)findViewById(2131756052));
    this.mHeader = ((ReviewItemHeaderLayout)findViewById(2131756054));
    this.mTitle = ((TextView)findViewById(2131755989));
    this.mBody = ((TextView)findViewById(2131755999));
    this.mMetadata = ((TextView)findViewById(2131756058));
    this.mProfilePicture = ((PersonAvatarView)findViewById(2131755993));
    this.mActionContainer = findViewById(2131756046);
    this.mMainAction = ((TextView)findViewById(2131756048));
    this.mActionImage = ((ImageView)findViewById(2131756047));
    this.mActionText = ((TextView)findViewById(2131755892));
    this.mActionOverflow = ((ImageView)findViewById(2131756049));
    this.mYourReviewLabel = ((TextView)findViewById(2131756053));
    this.mReviewReplyStub = ((ViewStub)findViewById(2131756000));
    this.mReviewReplyLayout = ((ReviewReplyLayout)findViewById(2131755739));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.mUseFeedbackV2) && (this.mOverflowExtendArea.isEmpty()))
    {
      this.mMainAction.getHitRect(this.mOverflowExtendArea);
      this.mOverflowExtendArea.inset(-this.mTouchExtendSize, -this.mTouchExtendSize);
      ((ViewGroup)this.mMainAction.getParent()).setTouchDelegate(new PlayTouchDelegate(this.mOverflowExtendArea, this.mMainAction));
    }
  }
  
  public void setActionClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mActionContainer.setOnClickListener(paramOnClickListener);
  }
  
  public void setReviewFeedbackActionListener(ReviewFeedbackActionListener paramReviewFeedbackActionListener)
  {
    this.mReviewFeedbackActionListener = paramReviewFeedbackActionListener;
  }
  
  public final void setReviewInfo(Document paramDocument, Review paramReview, int paramInt, NavigationManager paramNavigationManager, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, final boolean paramBoolean4, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mUseFeedbackV2 = paramBoolean2;
    Context localContext1 = getContext();
    Resources localResources = getResources();
    this.mIsYourReview = paramBoolean1;
    String str1 = paramReview.commentId;
    if ((!this.mIsYourReview) && (!TextUtils.isEmpty(str1))) {}
    ImageView localImageView;
    for (boolean bool = true;; bool = false)
    {
      this.mCanFeedback = bool;
      if (!this.mIsYourReview) {
        break;
      }
      localImageView = this.mActionImage;
      int i4 = paramDocument.mDocument.backendId;
      switch (i4)
      {
      case 5: 
      default: 
        throw new IllegalArgumentException("Unsupported backend ID (" + i4 + ")");
      }
    }
    int i5 = 2130837754;
    localImageView.setImageDrawable(ContextCompat.getDrawable(localContext1, i5));
    this.mActionText.setText(localResources.getString(2131362106).toUpperCase());
    this.mActionText.setTextColor(CorpusResourceUtils.getPrimaryColor(localContext1, paramDocument.mDocument.backendId));
    this.mActionText.setVisibility(0);
    this.mActionContainer.setContentDescription(this.mActionText.getText());
    this.mYourReviewLabel.setText(localResources.getString(2131362938).toUpperCase(localResources.getConfiguration().locale));
    this.mYourReviewLabel.setVisibility(0);
    label270:
    label312:
    label352:
    Object localObject;
    if ((paramReview.author != null) && (!TextUtils.isEmpty(paramReview.author.title)))
    {
      this.mAuthor.setText(paramReview.author.title);
      this.mAuthor.setVisibility(0);
      this.mHeader.setReviewInfo(paramReview);
      if (TextUtils.isEmpty(paramReview.title)) {
        break label1212;
      }
      this.mTitle.setVisibility(0);
      this.mTitle.setText(Html.fromHtml(paramReview.title));
      if (TextUtils.isEmpty(paramReview.comment)) {
        break label1224;
      }
      this.mBody.setVisibility(0);
      this.mBody.setText(Html.fromHtml(paramReview.comment));
      this.mBody.setMaxLines(paramInt);
      label392:
      if (paramDocument.mDocument.backendId == 3) {
        break label1236;
      }
      localObject = null;
      label406:
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        break label1396;
      }
      this.mMetadata.setVisibility(0);
      this.mMetadata.setText((CharSequence)localObject);
      label431:
      DocV2 localDocV2 = paramReview.author;
      if (localDocV2 == null) {
        break label1408;
      }
      GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(279, localDocV2.serverLogsCookie, null, this.mParentNode);
      this.mProfilePicture.bindView(localDocV2, paramNavigationManager, FinskyApp.get().mBitmapLoader, localGenericUiElementNode);
      this.mProfilePicture.setVisibility(0);
      label491:
      if (!paramReview.hasReplyText) {
        break label1494;
      }
      if (this.mReviewReplyLayout == null) {
        this.mReviewReplyLayout = ((ReviewReplyLayout)this.mReviewReplyStub.inflate());
      }
      localReviewReplyLayout = this.mReviewReplyLayout;
      if (!paramReview.hasReplyText) {
        localReviewReplyLayout.setVisibility(8);
      }
      localContext3 = localReviewReplyLayout.getContext();
      str3 = paramDocument.mDocument.creator;
      if (!paramReview.hasReplyTimestampMsec) {
        break label1461;
      }
      l1 = paramReview.replyTimestampMsec;
      str4 = DateUtils.formatShortDisplayDate(l1);
      if ((!paramReview.hasTimestampMsec) || (paramReview.timestampMsec <= l1)) {
        break label1420;
      }
      localReviewReplyLayout.mIsExpanded = false;
      localReviewReplyLayout.mReplyToggle.setVisibility(0);
      localReviewReplyLayout.mReplyText.setVisibility(8);
      localReviewReplyLayout.setOnClickListener(new ReviewReplyLayout.1(localReviewReplyLayout));
      localReviewReplyLayout.showMoreIndicator();
      localReviewReplyLayout.mReplyHeader.setText(Html.fromHtml(localContext3.getString(2131362694, new Object[] { str3, str4 })));
      localReviewReplyLayout.mReplyText.setText(paramReview.replyText);
      localReviewReplyLayout.setVisibility(0);
    }
    label750:
    label1396:
    while (this.mReviewReplyLayout == null) {
      for (;;)
      {
        ReviewReplyLayout localReviewReplyLayout;
        Context localContext3;
        String str3;
        long l1;
        String str4;
        return;
        i5 = 2130837756;
        break;
        i5 = 2130837755;
        break;
        i5 = 2130837757;
        break;
        if (CorpusResourceUtils.isEnterpriseTheme())
        {
          i5 = 2130837753;
          break;
        }
        i5 = 2130837752;
        break;
        View localView = this.mActionContainer;
        int i;
        int n;
        long l2;
        String str5;
        int i2;
        if (this.mCanFeedback)
        {
          i = 0;
          localView.setVisibility(i);
          this.mActionText.setVisibility(8);
          this.mYourReviewLabel.setVisibility(8);
          if (!this.mUseFeedbackV2) {
            break label1133;
          }
          this.mActionImage.setVisibility(8);
          if (!this.mCanFeedback) {
            break label270;
          }
          this.mMainAction.setVisibility(0);
          this.mActionContainer.setFocusable(false);
          this.mActionContainer.setBackgroundDrawable(null);
          this.mMainAction.setFocusable(true);
          n = CorpusResourceUtils.getPrimaryColor(getContext(), paramDocument.mDocument.backendId);
          if (!paramBoolean3) {
            break label1052;
          }
          l2 = 1L + paramReview.thumbsUpCount;
          int i3 = (int)l2;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Long.valueOf(l2);
          str5 = localResources.getQuantityString(2131296271, i3, arrayOfObject2);
          i2 = n;
          if (l2 <= 0L) {
            break label1105;
          }
          this.mMainAction.setText(String.valueOf(l2));
          this.mMainAction.setContentDescription(str5);
          this.mMainAction.setTextColor(i2);
        }
        for (;;)
        {
          Drawable localDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(localContext1, 2130837890));
          if (paramBoolean3) {
            DrawableCompat.setTint(localDrawable.mutate(), n);
          }
          ViewCompat.setPaddingRelative(this.mActionContainer, this.mActionContainer.getPaddingLeft(), this.mActionContainer.getPaddingTop(), 0, this.mActionContainer.getPaddingBottom());
          TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds$16207aff(this.mMainAction, null, localDrawable, null);
          this.mActionOverflow.setVisibility(0);
          this.mActionOverflow.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              PlayPopupMenu localPlayPopupMenu = new PlayPopupMenu(ReviewItemLayout.this.getContext(), ReviewItemLayout.this.mActionOverflow);
              Resources localResources = ReviewItemLayout.this.getContext().getResources();
              FinskyApp.get().getEventLogger().logClickEvent(238, null, ReviewItemLayout.this.mParentNode);
              if (paramBoolean4) {}
              for (int i = 2131362683;; i = 2131362682)
              {
                localPlayPopupMenu.addMenuItem(1, localResources.getString(i), true, ReviewItemLayout.this);
                ReviewItemLayout.this.mActionOverflow.setImageResource(2130838073);
                localPlayPopupMenu.mOnPopupDismissListener = new PopupWindow.OnDismissListener()
                {
                  public final void onDismiss()
                  {
                    ReviewItemLayout.this.mActionOverflow.setImageResource(2130838071);
                  }
                };
                localPlayPopupMenu.show();
                return;
              }
            }
          });
          this.mMainAction.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              if (ReviewItemLayout.this.mReviewFeedbackActionListener != null) {
                ReviewItemLayout.this.mReviewFeedbackActionListener.onMarkAsHelpfulClick(ReviewItemLayout.this);
              }
            }
          });
          break;
          i = 8;
          break label750;
          l2 = paramReview.thumbsUpCount;
          int i1 = (int)l2;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Long.valueOf(l2);
          str5 = localResources.getQuantityString(2131296270, i1, arrayOfObject1);
          i2 = localResources.getColor(2131689625);
          break label897;
          this.mMainAction.setText("");
          this.mMainAction.setContentDescription(localResources.getString(2131362687));
        }
        this.mActionImage.setVisibility(0);
        this.mActionImage.setImageDrawable(ContextCompat.getDrawable(localContext1, 2130837871));
        this.mActionContainer.setFocusable(true);
        this.mActionContainer.setContentDescription(localResources.getString(2131362686));
        this.mMainAction.setVisibility(8);
        this.mActionOverflow.setVisibility(8);
        break label270;
        this.mAuthor.setVisibility(8);
        break label312;
        this.mTitle.setVisibility(8);
        break label352;
        this.mBody.setVisibility(8);
        break label392;
        String str2 = paramReview.documentVersion;
        localObject = paramReview.deviceName;
        int j;
        if (!TextUtils.isEmpty(str2))
        {
          j = 1;
          if (TextUtils.isEmpty((CharSequence)localObject)) {
            break label1292;
          }
        }
        for (int k = 1;; k = 0)
        {
          if (j != 0) {
            break label1298;
          }
          if (k != 0) {
            break;
          }
          localObject = null;
          break;
          j = 0;
          break label1259;
        }
        AppDetails localAppDetails = paramDocument.getAppDetails();
        if ((localAppDetails.hasVersionString) && (str2.equals(localAppDetails.versionString))) {}
        for (int m = 1;; m = 0)
        {
          if (m == 0) {
            break label1350;
          }
          if (k != 0) {
            break;
          }
          localObject = null;
          break;
        }
        Context localContext2 = getContext();
        if (k != 0)
        {
          localObject = localContext2.getString(2131362692, new Object[] { localObject });
          break label406;
        }
        localObject = localContext2.getString(2131362691);
        break label406;
        this.mMetadata.setVisibility(8);
        break label431;
        this.mProfilePicture.setVisibility(8);
        break label491;
        localReviewReplyLayout.disableToggle();
        localReviewReplyLayout.mReplyHeader.setText(Html.fromHtml(localContext3.getString(2131362697, new Object[] { str3, str4 })));
        continue;
        localReviewReplyLayout.disableToggle();
        localReviewReplyLayout.mReplyHeader.setText(localContext3.getString(2131362696, new Object[] { str3 }));
      }
    }
    label897:
    label1052:
    label1105:
    label1133:
    this.mReviewReplyLayout.setVisibility(8);
    label1212:
    label1224:
    label1236:
    label1259:
    return;
  }
  
  public static abstract interface ReviewFeedbackActionListener
  {
    public abstract void onMarkAsHelpfulClick(ReviewItemLayout paramReviewItemLayout);
    
    public abstract void onMarkAsSpamClick(ReviewItemLayout paramReviewItemLayout);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ReviewItemLayout
 * JD-Core Version:    0.7.0.1
 */