package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.RateReviewActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.MyReviewReplyLayout;
import com.google.android.finsky.layout.MyReviewReplyLayout.1;
import com.google.android.finsky.layout.RateReviewEditor;
import com.google.android.finsky.layout.RateReviewEditor2;
import com.google.android.finsky.layout.ReviewItemLayout;
import com.google.android.finsky.layout.play.PersonAvatarView;
import com.google.android.finsky.layout.play.PlayRatingBar;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Details.DetailsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GPlusDialogsHelper;
import com.google.android.finsky.utils.RateReviewHelper;
import com.google.android.finsky.utils.RateReviewHelper.CheckAndConfirmGPlusListener;
import com.google.android.finsky.utils.RateReviewHelper.RateReviewListener;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.dfe.api.PlayDfeApi;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import com.google.android.play.utils.DocV2Utils;
import com.google.android.play.utils.PlayCorpusUtils;

public class RateReviewModule
  extends FinskyModule<Data>
  implements View.OnFocusChangeListener, RateReviewModuleLayout.RateReviewClickListener, RateReviewModuleV2Layout.RateReviewClickListener, PlayStoreUiElementNode, Libraries.Listener
{
  private ClientMutationCache mClientMutationCache = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName());
  private boolean mIsDestroyed;
  private boolean mIsReviewUpdateError;
  private boolean mNeedsRefresh;
  private boolean mRefreshAfterBindView;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(1200);
  private boolean mUserIsTyping;
  
  private void deleteReview(String paramString)
  {
    ((Data)this.mModuleData).isEditing = false;
    RateReviewHelper.deleteReview(paramString, ((Data)this.mModuleData).detailsDoc.mDocument.docid, ((Data)this.mModuleData).detailsDoc.mDocument.detailsUrl, this.mContext, new RateReviewHelper.RateReviewListener()
    {
      public final void onRateReviewCommitted$6ef37c42(int paramAnonymousInt)
      {
        if (RateReviewEditor.shouldUseReviewsEditorV2()) {
          ((RateReviewModule.Data)RateReviewModule.this.mModuleData).originalReview = null;
        }
        RateReviewModule.this.refreshReview();
      }
      
      public final void onRateReviewFailed()
      {
        if (RateReviewEditor.shouldUseReviewsEditorV2()) {
          ((RateReviewModule.Data)RateReviewModule.this.mModuleData).isEditing = false;
        }
        RateReviewModule.this.refreshReview();
      }
    });
  }
  
  private void loadPlusProfile()
  {
    FinskyApp.get().getPlayDfeApi(null).getPlusProfile(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
    }, true);
  }
  
  private void openLegacyReviewEditor(Document paramDocument, Review paramReview, int paramInt)
  {
    String str = FinskyApp.get().getCurrentAccountName();
    if (paramDocument == null) {}
    for (boolean bool = true;; bool = false)
    {
      Intent localIntent = RateReviewActivity.createIntent(str, ((Data)this.mModuleData).detailsDoc, paramDocument, paramReview, paramInt, false, bool, this.mContext);
      this.mContainerFragment.startActivityForResult(localIntent, 43);
      return;
    }
  }
  
  private void refreshReview()
  {
    reloadCurrentReview();
    if (!this.mIsDestroyed)
    {
      this.mNeedsRefresh = true;
      this.mFinskyModuleController.refreshModule(this, true);
    }
  }
  
  private void reloadCurrentReview()
  {
    ((Data)this.mModuleData).originalReview = ((Data)this.mModuleData).currentReview;
    ((Data)this.mModuleData).currentReview = this.mClientMutationCache.getCachedReview(((Data)this.mModuleData).detailsDoc.mDocument.docid, ((Data)this.mModuleData).originalReview);
  }
  
  private void updateReview(String paramString1, int paramInt1, String paramString2, String paramString3, Document paramDocument, int paramInt2, final boolean paramBoolean)
  {
    if ((((Data)this.mModuleData).mIsGplusSignUpEnabled) && (!GPlusDialogsHelper.hasUserAcceptedGPlusReviews())) {
      FinskyPreferences.acceptedPlusReviews.get(FinskyApp.get().getCurrentAccountName()).put(Boolean.valueOf(true));
    }
    RateReviewHelper.updateReview(paramString1, ((Data)this.mModuleData).detailsDoc.mDocument.docid, ((Data)this.mModuleData).detailsDoc.mDocument.detailsUrl, paramInt1, paramString2, paramString3, paramDocument, this.mContext, new RateReviewHelper.RateReviewListener()
    {
      public final void onRateReviewCommitted$6ef37c42(int paramAnonymousInt)
      {
        ((RateReviewModule.Data)RateReviewModule.this.mModuleData).isSaveInProgress = false;
        if (paramBoolean)
        {
          RateReviewModule.this.refreshReview();
          return;
        }
        RateReviewModule.this.reloadCurrentReview();
      }
      
      public final void onRateReviewFailed()
      {
        ((RateReviewModule.Data)RateReviewModule.this.mModuleData).isSaveInProgress = false;
        if (RateReviewModule.this.mUserIsTyping)
        {
          RateReviewModule.access$602$4f8c4e47(RateReviewModule.this);
          return;
        }
        RateReviewModule.this.handleError(false);
      }
    }, paramInt2);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    boolean bool1 = true;
    if (!paramBoolean) {
      break label7;
    }
    label7:
    while (this.mModuleData != null) {
      return;
    }
    this.mModuleData = new Data();
    ((Data)this.mModuleData).detailsDoc = paramDocument2;
    Data localData1 = (Data)this.mModuleData;
    boolean bool2;
    label58:
    label107:
    Data localData3;
    if (paramDfeDetails1.mDetailsResponse == null)
    {
      bool2 = bool1;
      localData1.isRateReviewEnabled = bool2;
      Data localData2 = (Data)this.mModuleData;
      if ((!DocUtils.canRate(this.mLibraries, ((Data)this.mModuleData).detailsDoc)) || (!((Data)this.mModuleData).isRateReviewEnabled)) {
        break label245;
      }
      localData2.canRate = bool1;
      localData3 = (Data)this.mModuleData;
      if ((paramDfeDetails2.mDetailsResponse != null) && (paramDfeDetails2.mDetailsResponse.userReview != null)) {
        break label251;
      }
    }
    label245:
    label251:
    for (Review localReview = null;; localReview = paramDfeDetails2.mDetailsResponse.userReview)
    {
      localData3.originalReview = localReview;
      ((Data)this.mModuleData).mIsGplusSignUpEnabled = this.mDfeToc.mToc.gplusSignupEnabled;
      ((Data)this.mModuleData).currentReview = this.mClientMutationCache.getCachedReview(paramDocument2.mDocument.docid, ((Data)this.mModuleData).originalReview);
      this.mLibraries.addListener(this);
      if (!((Data)this.mModuleData).canRate) {
        break;
      }
      loadPlusProfile();
      return;
      bool2 = paramDfeDetails1.mDetailsResponse.enableReviews;
      break label58;
      bool1 = false;
      break label107;
    }
  }
  
  public final void bindView(View paramView)
  {
    int i;
    RateReviewModuleLayout localRateReviewModuleLayout;
    Document localDocument3;
    Resources localResources3;
    int i3;
    label215:
    String str6;
    label262:
    label348:
    int i4;
    if ((((Data)this.mModuleData).mIsGplusSignUpEnabled) && (!GPlusDialogsHelper.hasUserAcceptedGPlusReviews()))
    {
      i = 1;
      if (!(paramView instanceof RateReviewModuleLayout)) {
        break label511;
      }
      localRateReviewModuleLayout = (RateReviewModuleLayout)paramView;
      if ((!localRateReviewModuleLayout.mHasBinded) || (this.mNeedsRefresh))
      {
        Review localReview3 = ((Data)this.mModuleData).currentReview;
        localDocument3 = ((Data)this.mModuleData).detailsDoc;
        DocV2 localDocV22 = ((Data)this.mModuleData).plusDocV2;
        NavigationManager localNavigationManager2 = this.mNavigationManager;
        localRateReviewModuleLayout.mRateReviewClickListener = this;
        localRateReviewModuleLayout.mMyRatingBar.setVerticalPadding(2131492996);
        localRateReviewModuleLayout.mMyRatingBar.configure(0, localDocument3.mDocument.backendId, new RateReviewModuleLayout.1(localRateReviewModuleLayout));
        localRateReviewModuleLayout.mReviewItemLayout.setActionClickListener(new RateReviewModuleLayout.2(localRateReviewModuleLayout));
        localResources3 = localRateReviewModuleLayout.getResources();
        if (localReview3 == null) {
          break label425;
        }
        int i5 = localReview3.starRating;
        localRateReviewModuleLayout.mReviewItemLayout.setReviewInfo(localDocument3, localReview3, 2147483647, localNavigationManager2, true, false, false, false, this);
        localRateReviewModuleLayout.mReviewItemLayout.setVisibility(0);
        localRateReviewModuleLayout.mRatingLayout.setVisibility(8);
        i3 = i5;
        if (i3 <= 0) {
          break label450;
        }
        str6 = localResources3.getString(2131362357);
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(i3);
        localRateReviewModuleLayout.setContentDescription(localResources3.getQuantityString(2131296257, i3, arrayOfObject3));
        if (localRateReviewModuleLayout.mRatingLayout.getVisibility() != 8)
        {
          localRateReviewModuleLayout.mMyRatingBar.setRating(i3);
          localRateReviewModuleLayout.mMyRatingText.setText(Utils.getItalicSafeString(str6));
        }
        if (localDocV22 == null) {
          break label481;
        }
        localRateReviewModuleLayout.mMyDisplayName.setText(localDocV22.title);
        Common.Image localImage = DocV2Utils.getFirstImageOfType(localDocV22, 4);
        localRateReviewModuleLayout.mMyAvatar.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
        if (localRateReviewModuleLayout.mDisclaimer != null)
        {
          View localView2 = localRateReviewModuleLayout.mDisclaimer;
          if (i == 0) {
            break label504;
          }
          i4 = 0;
          label370:
          localView2.setVisibility(i4);
        }
        localRateReviewModuleLayout.mHasBinded = true;
        this.mNeedsRefresh = false;
      }
    }
    label425:
    label450:
    label481:
    label504:
    label511:
    RateReviewModuleV2Layout localRateReviewModuleV2Layout;
    do
    {
      do
      {
        if (this.mRefreshAfterBindView)
        {
          this.mRefreshAfterBindView = false;
          new Handler().post(new Runnable()
          {
            public final void run()
            {
              RateReviewModule.this.refreshReview();
            }
          });
        }
        return;
        i = 0;
        break;
        localRateReviewModuleLayout.mReviewItemLayout.setVisibility(8);
        localRateReviewModuleLayout.mRatingLayout.setVisibility(0);
        i3 = 0;
        break label215;
        str6 = CorpusResourceUtils.getRateString(localResources3, localDocument3.mDocument.docType);
        localRateReviewModuleLayout.setContentDescription(localResources3.getString(2131362003));
        break label262;
        localRateReviewModuleLayout.mMyAvatar.setVisibility(8);
        localRateReviewModuleLayout.mMyDisplayName.setVisibility(8);
        break label348;
        i4 = 8;
        break label370;
      } while (!(paramView instanceof RateReviewModuleV2Layout));
      localRateReviewModuleV2Layout = (RateReviewModuleV2Layout)paramView;
    } while ((localRateReviewModuleV2Layout.mHasBinded) && (!this.mNeedsRefresh));
    Document localDocument1 = ((Data)this.mModuleData).detailsDoc;
    DocV2 localDocV21 = ((Data)this.mModuleData).plusDocV2;
    NavigationManager localNavigationManager1 = this.mNavigationManager;
    localRateReviewModuleV2Layout.mParentNode = this;
    localRateReviewModuleV2Layout.mRateReviewClickListener = this;
    localRateReviewModuleV2Layout.mNavigationManager = localNavigationManager1;
    localRateReviewModuleV2Layout.mDocument = localDocument1;
    localRateReviewModuleV2Layout.mAuthor = localDocV21;
    localRateReviewModuleV2Layout.mReviewEditor.setCommentFocusChangeListener(this);
    if (((Data)this.mModuleData).isEditing)
    {
      Review localReview2 = ((Data)this.mModuleData).currentReview;
      int m = ((Data)this.mModuleData).currentReview.starRating;
      boolean bool;
      label659:
      int n;
      label675:
      String str5;
      label760:
      RateReviewEditor2 localRateReviewEditor2;
      if (((Data)this.mModuleData).originalReview == null)
      {
        bool = true;
        if (((Data)this.mModuleData).mIsGplusSignUpEnabled) {
          break label920;
        }
        n = 1;
        localRateReviewModuleV2Layout.getResources();
        localRateReviewModuleV2Layout.mMyRatingBar.setVisibility(8);
        localRateReviewModuleV2Layout.mReviewText.setVisibility(8);
        localRateReviewModuleV2Layout.mMyRatingText.setVisibility(8);
        localRateReviewModuleV2Layout.mReviewRatingBar.setVisibility(8);
        localRateReviewModuleV2Layout.mBottomDivider.setVisibility(8);
        localRateReviewModuleV2Layout.mDisclaimer.setVisibility(8);
        localRateReviewModuleV2Layout.mMyAvatar.setToFadeInAfterLoad(false);
        localRateReviewModuleV2Layout.configureOverflow(true);
        if (localReview2.comment != null) {
          break label926;
        }
        str5 = "";
        localRateReviewEditor2 = localRateReviewModuleV2Layout.mReviewEditor;
        if (n == 0) {
          break label936;
        }
      }
      label920:
      label926:
      label936:
      for (int i1 = 1;; i1 = 2)
      {
        int i2 = localRateReviewModuleV2Layout.mDocument.mDocument.backendId;
        localRateReviewEditor2.configure$5fc451fa(i1, i2, m, localReview2.comment, bool);
        localRateReviewModuleV2Layout.mReviewEditor.setClickListener(new RateReviewModuleV2Layout.2(localRateReviewModuleV2Layout, str5));
        localRateReviewModuleV2Layout.mReviewEditor.setVisibility(0);
        localRateReviewModuleV2Layout.mReviewEditor.setReviewChangeListener(new RateReviewModuleV2Layout.3(localRateReviewModuleV2Layout));
        localRateReviewModuleV2Layout.bindHeader();
        localRateReviewModuleV2Layout.mHasBinded = true;
        if ((((Data)this.mModuleData).editorState != null) && (!this.mUserIsTyping)) {
          localRateReviewModuleV2Layout.restoreHierarchyState(((Data)this.mModuleData).editorState);
        }
        ((Data)this.mModuleData).editorState = null;
        this.mNeedsRefresh = false;
        break;
        bool = false;
        break label659;
        n = 0;
        break label675;
        str5 = localReview2.comment;
        break label760;
      }
    }
    if (((Data)this.mModuleData).currentReview != null)
    {
      Review localReview1 = ((Data)this.mModuleData).currentReview;
      Resources localResources2 = localRateReviewModuleV2Layout.getResources();
      int k = localReview1.starRating;
      localRateReviewModuleV2Layout.mReviewEditor.setVisibility(8);
      localRateReviewModuleV2Layout.mMyRatingBar.setRating(k);
      localRateReviewModuleV2Layout.mReviewRatingBar.setVisibility(8);
      localRateReviewModuleV2Layout.mMyRatingBar.setVisibility(0);
      localRateReviewModuleV2Layout.mDisclaimer.setVisibility(8);
      localRateReviewModuleV2Layout.configureOverflow(false);
      String str1 = RateReviewEditor2.formatComment(localReview1.title, localReview1.comment);
      label1073:
      MyReviewReplyLayout localMyReviewReplyLayout;
      Context localContext;
      String str4;
      if (!TextUtils.isEmpty(str1))
      {
        localRateReviewModuleV2Layout.mReviewText.setText(FastHtmlParser.fromHtml(str1));
        localRateReviewModuleV2Layout.mReviewText.setVisibility(0);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(k);
        localRateReviewModuleV2Layout.setContentDescription(localResources2.getQuantityString(2131296257, k, arrayOfObject1));
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = DateUtils.formatShortDisplayDate(localReview1.timestampMsec);
        String str2 = localResources2.getString(2131362356, arrayOfObject2);
        localRateReviewModuleV2Layout.mMyRatingText.setText(Utils.getItalicSafeString(str2));
        localRateReviewModuleV2Layout.mMyRatingText.setVisibility(0);
        localRateReviewModuleV2Layout.mBottomDivider.setVisibility(0);
        localRateReviewModuleV2Layout.bindHeader();
        if (!localReview1.hasReplyText) {
          break label1465;
        }
        if (localRateReviewModuleV2Layout.mReviewReplyLayout == null) {
          localRateReviewModuleV2Layout.mReviewReplyLayout = ((MyReviewReplyLayout)localRateReviewModuleV2Layout.mReviewReplyStub.inflate());
        }
        localMyReviewReplyLayout = localRateReviewModuleV2Layout.mReviewReplyLayout;
        Document localDocument2 = localRateReviewModuleV2Layout.mDocument;
        if (!localReview1.hasReplyText) {
          localMyReviewReplyLayout.setVisibility(8);
        }
        localContext = localMyReviewReplyLayout.getContext();
        String str3 = localDocument2.mDocument.creator;
        localMyReviewReplyLayout.mReplyDeveloperName.setText(str3);
        if (!localReview1.hasReplyTimestampMsec) {
          break label1452;
        }
        long l = localReview1.replyTimestampMsec;
        str4 = DateUtils.formatShortDisplayDate(l);
        localMyReviewReplyLayout.mReplySubtitle.setVisibility(0);
        if ((!localReview1.hasTimestampMsec) || (localReview1.timestampMsec <= l)) {
          break label1424;
        }
        localMyReviewReplyLayout.mReplySubtitle.setText(localContext.getString(2131362693, new Object[] { str4 }));
        label1324:
        localMyReviewReplyLayout.mReplyText.setText(localReview1.replyText);
        localMyReviewReplyLayout.mReplyText.setVisibility(8);
        ColorStateList localColorStateList = PlayCorpusUtils.getPrimaryTextColor(localContext, localDocument2.mDocument.backendId);
        localMyReviewReplyLayout.mReadMoreLabel.setTextColor(localColorStateList);
        localMyReviewReplyLayout.mReadMoreLabel.setOnClickListener(new MyReviewReplyLayout.1(localMyReviewReplyLayout));
        localMyReviewReplyLayout.mReadMoreLabel.setVisibility(0);
        localMyReviewReplyLayout.setVisibility(0);
      }
      for (;;)
      {
        localRateReviewModuleV2Layout.mHasBinded = true;
        break;
        localRateReviewModuleV2Layout.mReviewText.setVisibility(8);
        break label1073;
        label1424:
        localMyReviewReplyLayout.mReplySubtitle.setText(localContext.getString(2131362695, new Object[] { str4 }));
        break label1324;
        label1452:
        localMyReviewReplyLayout.mReplySubtitle.setVisibility(8);
        break label1324;
        label1465:
        if (localRateReviewModuleV2Layout.mReviewReplyLayout != null) {
          localRateReviewModuleV2Layout.mReviewReplyLayout.setVisibility(8);
        }
      }
    }
    localRateReviewModuleV2Layout.mReviewRatingBar.setVerticalPadding(2131492996);
    localRateReviewModuleV2Layout.mReviewRatingBar.configure(0, localRateReviewModuleV2Layout.mDocument.mDocument.backendId, new RateReviewModuleV2Layout.1(localRateReviewModuleV2Layout));
    Resources localResources1 = localRateReviewModuleV2Layout.getResources();
    localRateReviewModuleV2Layout.mReviewEditor.setVisibility(8);
    localRateReviewModuleV2Layout.mReviewRatingBar.setVisibility(0);
    localRateReviewModuleV2Layout.mMyRatingText.setText("");
    localRateReviewModuleV2Layout.mMyRatingText.setVisibility(4);
    localRateReviewModuleV2Layout.mMyRatingBar.setVisibility(8);
    localRateReviewModuleV2Layout.mRatingOverflow.setVisibility(8);
    localRateReviewModuleV2Layout.mReviewText.setVisibility(8);
    localRateReviewModuleV2Layout.mBottomDivider.setVisibility(8);
    localRateReviewModuleV2Layout.setContentDescription(localResources1.getString(2131362003));
    localRateReviewModuleV2Layout.bindHeader();
    View localView1;
    if (localRateReviewModuleV2Layout.mDisclaimer != null)
    {
      localView1 = localRateReviewModuleV2Layout.mDisclaimer;
      if (i == 0) {
        break label1648;
      }
    }
    label1648:
    for (int j = 0;; j = 8)
    {
      localView1.setVisibility(j);
      localRateReviewModuleV2Layout.mHasBinded = true;
      break;
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getLayoutResId()
  {
    if (RateReviewEditor.shouldUseReviewsEditorV2()) {
      return 2130969043;
    }
    return 2130969041;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  protected final void handleError(boolean paramBoolean)
  {
    if (paramBoolean) {
      Toast.makeText(this.mContext, 2131362680, 0).show();
    }
    ((Data)this.mModuleData).currentReview = ((Data)this.mModuleData).originalReview;
    ((Data)this.mModuleData).isEditing = false;
    this.mIsReviewUpdateError = false;
    refreshReview();
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 != 43) {
      return;
    }
    String str = FinskyApp.get().getCurrentAccountName();
    switch (paramInt2)
    {
    default: 
      return;
    case 1: 
      updateReview(str, paramIntent.getIntExtra("rating", 0), paramIntent.getStringExtra("review_title"), paramIntent.getStringExtra("review_comment"), (Document)paramIntent.getParcelableExtra("author"), 312, true);
      return;
    case 2: 
      deleteReview(str);
      return;
    }
    refreshReview();
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onCancelClicked()
  {
    if (this.mIsReviewUpdateError) {
      handleError(true);
    }
  }
  
  public final void onDeleteClicked()
  {
    FinskyApp.get().getEventLogger().logClickEvent(1209, null, this);
    if (((Data)this.mModuleData).isSaveInProgress)
    {
      Toast.makeText(this.mContext, 2131362667, 0).show();
      return;
    }
    deleteReview(FinskyApp.get().getCurrentAccountName());
  }
  
  public final void onDestroyModule()
  {
    this.mLibraries.removeListener(this);
    this.mIsDestroyed = true;
  }
  
  public final void onEditClicked()
  {
    if (((Data)this.mModuleData).currentReview != null)
    {
      FinskyApp.get().getEventLogger().logClickEvent(1202, null, this);
      if (RateReviewEditor.shouldUseReviewsEditorV2())
      {
        ((Data)this.mModuleData).isEditing = true;
        refreshReview();
      }
    }
    else
    {
      return;
    }
    boolean bool = ((Data)this.mModuleData).mIsGplusSignUpEnabled;
    Document localDocument = null;
    if (bool) {
      localDocument = new Document(((Data)this.mModuleData).plusDocV2);
    }
    openLegacyReviewEditor(localDocument, ((Data)this.mModuleData).currentReview, ((Data)this.mModuleData).currentReview.starRating);
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    this.mUserIsTyping = paramBoolean;
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    Data localData;
    if ((this.mModuleData != null) && (!((Data)this.mModuleData).canRate))
    {
      localData = (Data)this.mModuleData;
      if ((!DocUtils.canRate(this.mLibraries, ((Data)this.mModuleData).detailsDoc)) || (!((Data)this.mModuleData).isRateReviewEnabled)) {
        break label86;
      }
    }
    label86:
    for (boolean bool = true;; bool = false)
    {
      localData.canRate = bool;
      if (((Data)this.mModuleData).canRate) {
        loadPlusProfile();
      }
      return;
    }
  }
  
  public final void onRatingClicked(final int paramInt)
  {
    FinskyApp.get().getEventLogger().logClickEvent(1201, null, this);
    String str = FinskyApp.get().getCurrentAccountName();
    if (((Data)this.mModuleData).mIsGplusSignUpEnabled)
    {
      if (!((Data)this.mModuleData).isEditing)
      {
        PageFragment localPageFragment = this.mContainerFragment;
        final Review localReview = ((Data)this.mModuleData).currentReview;
        RateReviewHelper.checkAndConfirmGPlus(localPageFragment.getActivity(), new RateReviewHelper.CheckAndConfirmGPlusListener()
        {
          public final void onCheckAndConfirmGPlusFailed()
          {
            if (RateReviewModule.this.mIsDestroyed) {
              return;
            }
            RateReviewModule.this.refreshReview();
          }
          
          public final void onCheckAndConfirmGPlusPassed(Document paramAnonymousDocument)
          {
            if (RateReviewModule.this.mIsDestroyed) {
              return;
            }
            if (RateReviewEditor.shouldUseReviewsEditorV2())
            {
              ((RateReviewModule.Data)RateReviewModule.this.mModuleData).isEditing = true;
              ((RateReviewModule.Data)RateReviewModule.this.mModuleData).isSaveInProgress = true;
              RateReviewModule.access$200$71cf01cb(RateReviewModule.this, FinskyApp.get().getCurrentAccountName(), paramInt, "", "", paramAnonymousDocument);
              RateReviewModule.this.refreshReview();
              return;
            }
            RateReviewModule.this.openLegacyReviewEditor(paramAnonymousDocument, localReview, paramInt);
          }
        }, false);
        return;
      }
      updateReview(str, paramInt, "", ((Data)this.mModuleData).currentReview.comment, new Document(((Data)this.mModuleData).plusDocV2), 1201, false);
      return;
    }
    updateReview(str, paramInt, "", "", null, 1201, true);
  }
  
  public final void onRecycleView(View paramView)
  {
    super.onRecycleView(paramView);
    if (((paramView instanceof RateReviewModuleV2Layout)) && (this.mModuleData != null))
    {
      if (((Data)this.mModuleData).isEditing)
      {
        ((Data)this.mModuleData).editorState = new SparseArray();
        paramView.saveHierarchyState(((Data)this.mModuleData).editorState);
      }
    }
    else {
      return;
    }
    ((Data)this.mModuleData).editorState = null;
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null)
    {
      this.mLibraries.addListener(this);
      if ((((Data)this.mModuleData).hasLoadedDocV2) || (!((Data)this.mModuleData).canRate)) {
        break label51;
      }
      loadPlusProfile();
    }
    for (;;)
    {
      return;
      label51:
      Review localReview1 = this.mClientMutationCache.getCachedReview(((Data)this.mModuleData).detailsDoc.mDocument.docid, ((Data)this.mModuleData).currentReview);
      Review localReview2 = ((Data)this.mModuleData).currentReview;
      int i;
      if (localReview2 == localReview1) {
        i = 1;
      }
      while (i == 0)
      {
        this.mRefreshAfterBindView = true;
        ((Data)this.mModuleData).isEditing = false;
        return;
        if ((localReview2 != null) && (localReview1 != null) && (localReview2.timestampMsec == localReview1.timestampMsec)) {
          i = 1;
        } else {
          i = 0;
        }
      }
    }
  }
  
  public final void onSubmitClicked(int paramInt, String paramString)
  {
    if (this.mIsReviewUpdateError) {
      handleError(true);
    }
    while (TextUtils.isEmpty(paramString)) {
      return;
    }
    FinskyApp.get().getEventLogger().logClickEvent(1204, null, this);
    ((Data)this.mModuleData).isEditing = false;
    updateReview(FinskyApp.get().getCurrentAccountName(), paramInt, "", paramString, new Document(((Data)this.mModuleData).plusDocV2), 1200, false);
    refreshReview();
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).canRate) && (((Data)this.mModuleData).hasLoadedDocV2 == true);
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    boolean canRate;
    Review currentReview;
    Document detailsDoc;
    public SparseArray<Parcelable> editorState;
    boolean hasLoadedDocV2;
    boolean isEditing;
    boolean isRateReviewEnabled;
    boolean isSaveInProgress;
    boolean mIsGplusSignUpEnabled;
    Review originalReview;
    DocV2 plusDocV2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.RateReviewModule
 * JD-Core Version:    0.7.0.1
 */