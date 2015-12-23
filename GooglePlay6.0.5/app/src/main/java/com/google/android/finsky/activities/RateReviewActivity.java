package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.RateReviewEditor;
import com.google.android.finsky.layout.RateReviewEditor.ReviewChangeListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.RateReviewHelper;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public class RateReviewActivity
  extends AppCompatActivity
  implements RateReviewEditor.ReviewChangeListener, PlayStoreUiElementNode
{
  private String mAccountName;
  private Document mAuthor;
  private int mBackend;
  private ButtonBar mButtonBar;
  private boolean mClickDebounce = false;
  private String mDocDetailsUrl;
  private String mDocId;
  private FinskyEventLog mEventLogger;
  private long mImpressionId;
  private boolean mIsAnonymousRating;
  private boolean mIsExternalRequest;
  private RateReviewEditor mReviewEditor;
  private int mReviewMode;
  private Bundle mSavedInstanceState;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(1203);
  
  public static Intent createIntent(String paramString, Document paramDocument1, Document paramDocument2, Review paramReview, int paramInt, boolean paramBoolean1, boolean paramBoolean2, Context paramContext)
  {
    Intent localIntent = new Intent(FinskyApp.get(), RateReviewActivity.class);
    localIntent.putExtra("account_name", paramString);
    localIntent.putExtra("doc_id", paramDocument1.mDocument.docid);
    localIntent.putExtra("doc_details_url", paramDocument1.mDocument.detailsUrl);
    localIntent.putExtra("doc_title", paramDocument1.mDocument.title);
    localIntent.putExtra("author", paramDocument2);
    localIntent.putExtra("backend", paramDocument1.mDocument.backendId);
    localIntent.putExtra("previous_rating", paramInt);
    if (paramReview != null)
    {
      localIntent.putExtra("previous_title", paramReview.title);
      localIntent.putExtra("previous_comment", paramReview.comment);
      if (paramReview.author != null) {
        localIntent.putExtra("previous_author", ParcelableProto.forProto(paramReview.author));
      }
    }
    localIntent.putExtra("server_logs_cookie", paramDocument1.mDocument.serverLogsCookie);
    localIntent.putExtra("impression_id", FinskyEventLog.getNextImpressionId());
    localIntent.putExtra("is_external_request", paramBoolean1);
    localIntent.putExtra("is_anonymous_rating", paramBoolean2);
    if (paramBoolean1)
    {
      localIntent.putExtra("doc_creator", paramDocument1.mDocument.creator);
      Common.Image localImage = ThumbnailUtils.getImageFromDocument(paramDocument1, 0, paramContext.getResources().getDimensionPixelSize(2131493245), new int[] { 4, 0 });
      if (localImage != null) {
        localIntent.putExtra("doc_thumbnail_url", localImage.imageUrl);
      }
      localIntent.putExtra("doc_thumbnail_is_fife", localImage.supportsFifeUrlOptions);
    }
    return localIntent;
  }
  
  private void finishAsCanceled()
  {
    this.mEventLogger.logClickEvent(1207, null, this);
    Intent localIntent = new Intent();
    localIntent.putExtra("doc_id", this.mDocId);
    setResult(3, localIntent);
    finish();
  }
  
  private void syncButtonEnabledState()
  {
    boolean bool1;
    int i;
    label35:
    int j;
    label51:
    boolean bool2;
    if (this.mReviewEditor.getUserRating() > 0)
    {
      bool1 = true;
      if (this.mReviewMode != 3) {
        break label96;
      }
      if (TextUtils.isEmpty(this.mReviewEditor.getUserTitle())) {
        break label80;
      }
      i = 1;
      if (TextUtils.isEmpty(this.mReviewEditor.getUserComment())) {
        break label85;
      }
      j = 1;
      if ((i == 0) || (j == 0) || (!bool1)) {
        break label91;
      }
      bool2 = true;
    }
    for (;;)
    {
      this.mButtonBar.setPositiveButtonEnabled(bool2);
      return;
      bool1 = false;
      break;
      label80:
      i = 0;
      break label35;
      label85:
      j = 0;
      break label51;
      label91:
      bool2 = false;
      continue;
      label96:
      bool2 = bool1;
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public void onBackPressed()
  {
    this.mEventLogger.logClickEvent(1207, null, this);
    Intent localIntent = new Intent();
    localIntent.putExtra("doc_id", this.mDocId);
    setResult(3, localIntent);
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mSavedInstanceState = paramBundle;
    super.onCreate(paramBundle);
    setContentView(2130969035);
    Intent localIntent = getIntent();
    this.mAccountName = localIntent.getStringExtra("account_name");
    this.mIsExternalRequest = localIntent.getBooleanExtra("is_external_request", true);
    this.mIsAnonymousRating = localIntent.getBooleanExtra("is_anonymous_rating", false);
    this.mDocId = localIntent.getStringExtra("doc_id");
    this.mDocDetailsUrl = localIntent.getStringExtra("doc_details_url");
    String str1 = localIntent.getStringExtra("doc_title");
    this.mBackend = localIntent.getIntExtra("backend", 0);
    DocV2 localDocV2 = (DocV2)ParcelableProto.getProtoFromIntent(localIntent, "previous_author");
    Document localDocument1;
    Document localDocument2;
    int i;
    String str2;
    String str3;
    label170:
    int j;
    label254:
    final boolean bool;
    label332:
    int k;
    if (localDocV2 != null)
    {
      localDocument1 = new Document(localDocV2);
      localDocument2 = (Document)localIntent.getParcelableExtra("author");
      if (this.mSavedInstanceState == null) {
        break label556;
      }
      i = this.mSavedInstanceState.getInt("previous_rating");
      str2 = this.mSavedInstanceState.getString("previous_title");
      str3 = this.mSavedInstanceState.getString("previous_comment");
      byte[] arrayOfByte = localIntent.getByteArrayExtra("server_logs_cookie");
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, arrayOfByte);
      this.mEventLogger = FinskyApp.get().getEventLogger(this.mAccountName);
      this.mImpressionId = localIntent.getLongExtra("impression_id", 0L);
      if (paramBundle == null) {
        this.mEventLogger.logPathImpression(this.mImpressionId, this);
      }
      if ((!((Boolean)G.enableReviewComments.get()).booleanValue()) || (this.mIsAnonymousRating)) {
        break label584;
      }
      j = 2;
      this.mReviewMode = j;
      View localView = findViewById(2131755984);
      this.mReviewEditor = ((RateReviewEditor)findViewById(2131755985));
      this.mReviewEditor.configure(this.mReviewMode, this.mBackend, str1, this.mIsExternalRequest, i, str2, str3);
      this.mReviewEditor.setReviewChangeListener(this);
      if ((localDocV2 == null) && (!this.mIsAnonymousRating)) {
        break label590;
      }
      bool = true;
      this.mButtonBar = ((ButtonBar)localView.findViewById(2131755300));
      this.mButtonBar.setPositiveButtonEnabled(true);
      ButtonBar localButtonBar = this.mButtonBar;
      if (!bool) {
        break label596;
      }
      k = 2131362710;
      label371:
      localButtonBar.setPositiveButtonTitle(k);
      this.mButtonBar.setNegativeButtonVisible(bool);
      this.mButtonBar.setNegativeButtonTitle(2131362078);
      this.mButtonBar.setClickListener(new ButtonBar.ClickListener()
      {
        public final void onNegativeButtonClick()
        {
          if (RateReviewActivity.this.mClickDebounce) {
            return;
          }
          RateReviewActivity.access$002$24bc3a7c(RateReviewActivity.this);
          RateReviewActivity.this.mEventLogger.logClickEvent(1206, null, RateReviewActivity.this);
          if (RateReviewActivity.this.mIsExternalRequest) {
            RateReviewHelper.deleteReview(RateReviewActivity.this.mAccountName, RateReviewActivity.this.mDocId, RateReviewActivity.this.mDocDetailsUrl, RateReviewActivity.this, null);
          }
          Intent localIntent = new Intent();
          localIntent.putExtra("doc_id", RateReviewActivity.this.mDocId);
          RateReviewActivity.this.setResult(2, localIntent);
          RateReviewActivity.this.finish();
        }
        
        public final void onPositiveButtonClick()
        {
          if (RateReviewActivity.this.mClickDebounce) {
            return;
          }
          RateReviewActivity.access$002$24bc3a7c(RateReviewActivity.this);
          if (bool) {}
          for (int i = 1205;; i = 1204)
          {
            RateReviewActivity.this.mEventLogger.logClickEvent(i, null, RateReviewActivity.this);
            if (RateReviewActivity.this.mIsExternalRequest) {
              RateReviewHelper.updateReview(RateReviewActivity.this.mAccountName, RateReviewActivity.this.mDocId, RateReviewActivity.this.mDocDetailsUrl, RateReviewActivity.this.mReviewEditor.getUserRating(), RateReviewActivity.this.mReviewEditor.getUserTitle(), RateReviewActivity.this.mReviewEditor.getUserComment(), RateReviewActivity.this.mAuthor, RateReviewActivity.this, null, 1203);
            }
            Intent localIntent = new Intent();
            localIntent.putExtra("doc_id", RateReviewActivity.this.mDocId);
            localIntent.putExtra("rating", RateReviewActivity.this.mReviewEditor.getUserRating());
            localIntent.putExtra("review_title", RateReviewActivity.this.mReviewEditor.getUserTitle());
            localIntent.putExtra("review_comment", RateReviewActivity.this.mReviewEditor.getUserComment());
            if (!RateReviewActivity.this.mIsExternalRequest) {
              localIntent.putExtra("author", RateReviewActivity.this.mAuthor);
            }
            if (!RateReviewActivity.this.mIsAnonymousRating)
            {
              localIntent.putExtra("author_title", RateReviewActivity.this.mAuthor.mDocument.title);
              localIntent.putExtra("author_profile_image_url", ((Common.Image)RateReviewActivity.this.mAuthor.getImages(4).get(0)).imageUrl);
            }
            RateReviewActivity.this.setResult(1, localIntent);
            RateReviewActivity.this.finish();
            return;
          }
        }
      });
      if (localDocument1 == null) {
        break label604;
      }
    }
    TextView localTextView;
    FifeImageView localFifeImageView1;
    label556:
    label584:
    label590:
    label596:
    label604:
    for (this.mAuthor = localDocument1;; this.mAuthor = localDocument2)
    {
      localTextView = (TextView)findViewById(2131756043);
      localFifeImageView1 = (FifeImageView)findViewById(2131755993);
      if (this.mAuthor == null) {
        break label613;
      }
      Resources localResources = getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mAuthor.mDocument.title;
      localTextView.setText(localResources.getString(2131362666, arrayOfObject));
      FifeImageView localFifeImageView2 = (FifeImageView)findViewById(2131755993);
      Common.Image localImage = (Common.Image)this.mAuthor.getImages(4).get(0);
      localFifeImageView2.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      return;
      localDocument1 = null;
      break;
      i = localIntent.getIntExtra("previous_rating", 0);
      str2 = localIntent.getStringExtra("previous_title");
      str3 = localIntent.getStringExtra("previous_comment");
      break label170;
      j = 1;
      break label254;
      bool = false;
      break label332;
      k = 2131362770;
      break label371;
    }
    label613:
    localTextView.setVisibility(8);
    localFifeImageView1.setVisibility(8);
  }
  
  public final void onRatingChanged()
  {
    syncButtonEnabledState();
  }
  
  public final void onReviewChanged()
  {
    syncButtonEnabledState();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("previous_rating", this.mReviewEditor.getUserRating());
    paramBundle.putString("previous_title", this.mReviewEditor.getUserTitle());
    paramBundle.putString("previous_comment", this.mReviewEditor.getUserComment());
  }
  
  public void onStart()
  {
    super.onStart();
    syncButtonEnabledState();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    int j = (int)paramMotionEvent.getX();
    int k = (int)paramMotionEvent.getY();
    View localView = getWindow().getDecorView();
    if ((i == 0) && ((j < 0) || (j >= localView.getWidth()) || (k < 0) || (k >= localView.getHeight())))
    {
      finishAsCanceled();
      return true;
    }
    if (i == 4)
    {
      finishAsCanceled();
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.RateReviewActivity
 * JD-Core Version:    0.7.0.1
 */