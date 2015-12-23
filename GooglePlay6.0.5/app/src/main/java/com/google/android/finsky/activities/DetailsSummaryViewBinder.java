package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.layout.DetailsSummary;
import com.google.android.finsky.layout.DetailsSummaryDynamic;
import com.google.android.finsky.layout.DetailsSummaryWishlistView;
import com.google.android.finsky.layout.DetailsTitleCreatorBlock;
import com.google.android.finsky.layout.DetailsTitleCreatorBlock.1;
import com.google.android.finsky.layout.DocImageView;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.WishlistPlayActionButton;
import com.google.android.finsky.layout.WishlistPlayActionButton.1;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.RevokeListenerWrapper;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AlbumDetails;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.BookDetails;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MusicDetails;
import com.google.android.finsky.protos.VideoDetails;
import com.google.android.finsky.protos.Warning;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.PurchaseButtonHelper;
import com.google.android.finsky.utils.PurchaseButtonHelper.DocumentAction;
import com.google.android.finsky.utils.PurchaseButtonHelper.DocumentActions;
import com.google.android.finsky.utils.PurchaseButtonHelper.TextStyle;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.WishlistHelper;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.layout.PlayCardThumbnail;
import java.text.ParseException;
import java.util.List;

public class DetailsSummaryViewBinder
{
  protected boolean mBindingDynamicSection;
  private BitmapLoader mBitmapLoader;
  protected ViewGroup mButtonSection;
  protected PageFragment mContainerFragment;
  protected Context mContext;
  protected String mContinueUrl;
  protected final Account mDetailsAccount;
  protected DfeApi mDfeApi;
  protected DfeToc mDfeToc;
  protected Document mDoc;
  protected DetailsSummaryDynamic mDynamicSection;
  protected final FinskyEventLog mEventLogger;
  protected boolean mHideDynamicFeatures;
  protected boolean mIsBinderDestroyed;
  boolean mIsCancelingPreorder;
  boolean mIsCompactMode;
  protected boolean mIsPendingRefund;
  private boolean mIsRevealTransitionRunning;
  private View[] mLayouts;
  protected NavigationManager mNavigationManager;
  protected PlayStoreUiElementNode mParentNode;
  private String mRevealTransitionCoverName;
  
  public DetailsSummaryViewBinder(DfeToc paramDfeToc, Account paramAccount)
  {
    this.mDetailsAccount = paramAccount;
    this.mDfeToc = paramDfeToc;
    this.mDfeApi = FinskyApp.get().getDfeApi(paramAccount.name);
    this.mEventLogger = FinskyApp.get().getEventLogger(paramAccount);
  }
  
  private static void addByline(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, CharSequence paramCharSequence)
  {
    TextView localTextView = (TextView)paramLayoutInflater.inflate(2130968695, paramViewGroup, false);
    localTextView.setText(paramCharSequence);
    paramViewGroup.addView(localTextView);
  }
  
  private void addExtraLabel(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, CharSequence paramCharSequence)
  {
    TextView localTextView = (TextView)paramLayoutInflater.inflate(2130968697, paramViewGroup, false);
    localTextView.setText(paramCharSequence);
    localTextView.setTextColor(CorpusResourceUtils.getPrimaryColor(this.mContext, this.mDoc.mDocument.backendId));
    paramViewGroup.addView(localTextView);
  }
  
  protected static void addExtraLabelBottom(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, CharSequence paramCharSequence)
  {
    TextView localTextView = (TextView)paramLayoutInflater.inflate(2130968698, paramViewGroup, false);
    localTextView.setText(paramCharSequence);
    paramViewGroup.addView(localTextView);
  }
  
  private void configureActionButton$eb2e118(PlayActionButton paramPlayActionButton, PurchaseButtonHelper.DocumentAction paramDocumentAction, int paramInt)
  {
    paramPlayActionButton.setVisibility(0);
    PurchaseButtonHelper.TextStyle localTextStyle = new PurchaseButtonHelper.TextStyle();
    View.OnClickListener localOnClickListener;
    if (this.mContext.getResources().getBoolean(2131427334))
    {
      PurchaseButtonHelper.getActionStyleLong(paramDocumentAction, paramInt, localTextStyle);
      if (!PurchaseButtonHelper.canCreateClickListener(paramDocumentAction)) {
        break label92;
      }
      localOnClickListener = PurchaseButtonHelper.getActionClickListener(paramDocumentAction, paramInt, this.mNavigationManager, this.mContinueUrl, null, this.mContext);
    }
    for (;;)
    {
      paramPlayActionButton.configure(paramInt, localTextStyle.getString(this.mContext), localOnClickListener);
      return;
      PurchaseButtonHelper.getActionStyleForFormat(paramDocumentAction, paramInt, false, true, localTextStyle);
      break;
      label92:
      if (paramDocumentAction.actionType == 9)
      {
        localOnClickListener = getCancelPreorderClickListener(paramDocumentAction.document, paramDocumentAction.account);
      }
      else if (paramDocumentAction.actionType == 12)
      {
        localOnClickListener = getDownloadClickListener(paramDocumentAction.document, paramDocumentAction.account);
      }
      else if (paramDocumentAction.actionType == 16)
      {
        localOnClickListener = getViewBundleClickListener$1f5226f(paramDocumentAction.document);
      }
      else
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramDocumentAction.actionType);
        FinskyLog.wtf("Can't create a click listener for action %d", arrayOfObject);
        localOnClickListener = null;
      }
    }
  }
  
  private View.OnClickListener getCancelPreorderClickListener(final Document paramDocument, final Account paramAccount)
  {
    new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        FragmentManagerImpl localFragmentManagerImpl = DetailsSummaryViewBinder.this.mContainerFragment.mFragmentManager;
        if (localFragmentManagerImpl.findFragmentByTag("DetailsSummaryViewBinder.confirm_cancel_dialog") != null) {
          return;
        }
        DetailsSummaryViewBinder.this.mEventLogger.logClickEvent(235, null, DetailsSummaryViewBinder.this.mParentNode);
        Resources localResources = DetailsSummaryViewBinder.this.mContext.getResources();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramDocument.mDocument.title;
        String str = localResources.getString(2131361965, arrayOfObject);
        SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
        localBuilder.setMessage(str).setPositiveId(2131362937).setNegativeId(2131362370).setEventLog(305, paramDocument.mDocument.serverLogsCookie, 245, 246, null);
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("DetailsSummaryViewBinder.doc", paramDocument);
        localBundle.putString("DetailsSummaryViewBinder.ownerAccountName", paramAccount.name);
        localBuilder.setCallback(DetailsSummaryViewBinder.this.mContainerFragment, 7, localBundle);
        localBuilder.build().show(localFragmentManagerImpl, "DetailsSummaryViewBinder.confirm_cancel_dialog");
      }
    };
  }
  
  public static boolean supportsThumbnailPeekingOnNonWideLayout(int paramInt)
  {
    return (paramInt == 6) || (paramInt == 16) || (paramInt == 17) || (paramInt == 44);
  }
  
  public void bind(Document paramDocument, boolean paramBoolean, View... paramVarArgs)
  {
    this.mLayouts = paramVarArgs;
    this.mDoc = paramDocument;
    this.mBindingDynamicSection = paramBoolean;
    this.mDynamicSection = ((DetailsSummaryDynamic)findViewById(2131755405));
    this.mButtonSection = ((ViewGroup)findViewById(2131755406));
    int i = this.mDoc.mDocument.docType;
    TextView localTextView1 = (TextView)findViewById(2131755262);
    if (localTextView1 != null) {
      localTextView1.setText(this.mDoc.mDocument.title);
    }
    ViewGroup localViewGroup1 = (ViewGroup)findViewById(2131755395);
    DecoratedTextView localDecoratedTextView1 = (DecoratedTextView)localViewGroup1.findViewById(2131755396);
    ViewGroup localViewGroup2 = (ViewGroup)findViewById(2131755398);
    DetailsTitleCreatorBlock localDetailsTitleCreatorBlock;
    Document localDocument1;
    NavigationManager localNavigationManager;
    BitmapLoader localBitmapLoader;
    FinskyEventLog localFinskyEventLog;
    PlayStoreUiElementNode localPlayStoreUiElementNode;
    int i9;
    boolean bool4;
    DetailsSummaryWishlistView localDetailsSummaryWishlistView;
    label319:
    Resources localResources1;
    PlayCardThumbnail localPlayCardThumbnail;
    boolean bool1;
    int j;
    label443:
    int k;
    label482:
    ViewGroup localViewGroup3;
    LayoutInflater localLayoutInflater1;
    label556:
    int m;
    label567:
    ViewGroup localViewGroup4;
    LayoutInflater localLayoutInflater2;
    String str4;
    label631:
    Libraries localLibraries;
    int i8;
    label750:
    String str3;
    label795:
    label851:
    int n;
    label862:
    ViewGroup localViewGroup5;
    label895:
    ViewGroup localViewGroup8;
    int i2;
    if (localDecoratedTextView1 != null)
    {
      if ((i == 3) || (i == 2) || (i == 4) || (i == 5) || (i == 30))
      {
        localViewGroup1.setVisibility(8);
        localViewGroup2.setVisibility(8);
      }
    }
    else
    {
      DecoratedTextView localDecoratedTextView2 = (DecoratedTextView)findViewById(2131755402);
      BadgeUtils.configureTipperSticker(this.mDoc, localDecoratedTextView2);
      localDetailsTitleCreatorBlock = (DetailsTitleCreatorBlock)findViewById(2131755427);
      if (localDetailsTitleCreatorBlock != null)
      {
        localDocument1 = this.mDoc;
        localNavigationManager = this.mNavigationManager;
        localBitmapLoader = this.mBitmapLoader;
        localFinskyEventLog = this.mEventLogger;
        localPlayStoreUiElementNode = this.mParentNode;
        i9 = localDocument1.mDocument.docType;
        bool4 = localDocument1.hasCreatorDoc();
        if (((i9 == 2) || (i9 == 4) || (i9 == 5) || (bool4)) && (i9 != 1)) {
          break label1101;
        }
        localDetailsTitleCreatorBlock.setVisibility(8);
      }
      localDetailsSummaryWishlistView = (DetailsSummaryWishlistView)findViewById(2131755394);
      if (localDetailsSummaryWishlistView != null)
      {
        if (!this.mIsCompactMode) {
          break label1500;
        }
        localDetailsSummaryWishlistView.setVisibility(8);
      }
      localResources1 = this.mContext.getResources();
      localPlayCardThumbnail = (PlayCardThumbnail)findViewById(2131755412);
      bool1 = localResources1.getBoolean(2131427334);
      switch (i)
      {
      default: 
        j = 0;
        DetailsSummary localDetailsSummary = (DetailsSummary)findViewById(2131755333);
        if ((!this.mIsCompactMode) && (j == 0))
        {
          localPlayCardThumbnail.setVisibility(8);
          if (bool1)
          {
            k = 0;
            localDetailsSummary.setThumbnailMode(k);
            if (!this.mIsCompactMode)
            {
              localViewGroup3 = (ViewGroup)findViewById(2131755403);
              localViewGroup3.removeAllViews();
              localLayoutInflater1 = LayoutInflater.from(this.mContext);
            }
            switch (this.mDoc.mDocument.docType)
            {
            default: 
              if (localViewGroup3.getChildCount() > 0)
              {
                m = 0;
                localViewGroup3.setVisibility(m);
                localViewGroup4 = (ViewGroup)findViewById(2131755404);
                localViewGroup4.removeAllViews();
                localLayoutInflater2 = LayoutInflater.from(this.mContext);
                if (shouldDisplayOfferNote())
                {
                  Annotations localAnnotations = this.mDoc.mDocument.annotations;
                  if (localAnnotations == null) {
                    break label1909;
                  }
                  str4 = localAnnotations.offerNote;
                  if (!TextUtils.isEmpty(str4)) {
                    addExtraLabel(localLayoutInflater2, localViewGroup4, str4);
                  }
                }
                localLibraries = FinskyApp.get().mLibraries;
                Account localAccount = LibraryUtils.getOwnerWithCurrentAccount(this.mDoc, localLibraries, this.mDetailsAccount);
                if (localAccount != null)
                {
                  AccountLibrary localAccountLibrary4 = localLibraries.getAccountLibrary(this.mDetailsAccount);
                  if (LibraryUtils.isPreordered(this.mDoc, localAccountLibrary4))
                  {
                    int i7 = LibraryUtils.getOwnedPurchaseOfferType(this.mDoc, localAccountLibrary4);
                    Common.Offer localOffer2 = this.mDoc.getOffer(i7);
                    if (localOffer2 == null) {
                      break label1923;
                    }
                    if ((!localOffer2.hasPreorderFulfillmentDisplayDate) || (localOffer2.preorderFulfillmentDisplayDate <= System.currentTimeMillis())) {
                      break label1917;
                    }
                    i8 = 0;
                    if (i8 == 0) {
                      break label1923;
                    }
                    long l1 = localOffer2.onSaleDate;
                    Context localContext = this.mContext;
                    Object[] arrayOfObject = new Object[1];
                    arrayOfObject[0] = DateUtils.formatShortDisplayDateUtc(l1);
                    str3 = localContext.getString(2131362467, arrayOfObject);
                    addExtraLabel(localLayoutInflater2, localViewGroup4, str3);
                  }
                }
                if (localAccount == null)
                {
                  AccountLibrary localAccountLibrary2 = localLibraries.getAccountLibrary(this.mDetailsAccount);
                  if (!LibraryUtils.isPreorderedThroughBundle(this.mDoc, localAccountLibrary2)) {
                    break label1938;
                  }
                  addExtraLabel(localLayoutInflater2, localViewGroup4, this.mContext.getString(2131362334));
                }
                if (localViewGroup4.getChildCount() <= 0) {
                  break label2228;
                }
                n = 0;
                localViewGroup4.setVisibility(n);
                localViewGroup5 = (ViewGroup)findViewById(2131755409);
                if (this.mDoc.mDocument.docType == 1) {
                  break label2235;
                }
                localViewGroup8 = localViewGroup5;
                i2 = 8;
                localViewGroup8.setVisibility(i2);
                if (!paramBoolean) {
                  break label2526;
                }
                syncDynamicSection();
                updateButtonActionStyle();
              }
              break;
            }
          }
        }
        break;
      }
    }
    for (;;)
    {
      UiUtils.syncContainerVisibility(this.mDynamicSection, 8);
      return;
      if (i == 6)
      {
        localViewGroup1.setVisibility(8);
        BadgeUtils.configureContentRatingBadge(this.mDoc, this.mBitmapLoader, localViewGroup2);
        break;
      }
      localViewGroup1.setVisibility(0);
      localDecoratedTextView1.setText(PlayCardUtils.getDocDisplaySubtitle(this.mDoc));
      BadgeUtils.configureCreatorBadge(this.mDoc, this.mBitmapLoader, localDecoratedTextView1);
      BadgeUtils.configureContentRatingBadge(this.mDoc, this.mBitmapLoader, localViewGroup2);
      FinskyApp localFinskyApp = FinskyApp.get();
      if ((!GooglePlayServicesUtil.isSidewinderDevice(localFinskyApp)) && (!localFinskyApp.getExperiments().isEnabled(12603301L))) {
        break;
      }
      long l2 = this.mDoc.getInstallationSize();
      if (l2 <= 0L) {
        break;
      }
      TextView localTextView2 = (TextView)findViewById(2131755401);
      String str6 = Formatter.formatShortFileSize(this.mContext, l2);
      if ((localTextView2 == null) || (TextUtils.isEmpty(str6))) {
        break;
      }
      localTextView2.setText(str6);
      localTextView2.setVisibility(0);
      break;
      label1101:
      label1132:
      String str5;
      if (!bool4)
      {
        localDetailsTitleCreatorBlock.mCreatorTitle.setText(localDocument1.mDocument.creator);
        localDetailsTitleCreatorBlock.mCreatorImage.setVisibility(8);
        if (i9 == 1) {
          BadgeUtils.configureCreatorBadge(localDocument1, localBitmapLoader, localDetailsTitleCreatorBlock.mCreatorTitle);
        }
        if ((i9 == 2) || (i9 == 4) || (i9 == 5))
        {
          if ((i9 != 2) && (i9 != 4)) {
            break label1451;
          }
          str5 = localDocument1.getAlbumDetails().details.originalReleaseDate;
          label1193:
          if ((localDocument1.hasPreorderOffer()) || (TextUtils.isEmpty(str5))) {
            break label1487;
          }
        }
      }
      for (;;)
      {
        try
        {
          localDetailsTitleCreatorBlock.mCreatorDate.setText(DateUtils.formatIso8601Date(str5));
          localDetailsTitleCreatorBlock.mCreatorDate.setVisibility(0);
          localDetailsTitleCreatorBlock.setVisibility(0);
        }
        catch (ParseException localParseException)
        {
          Document localDocument2;
          List localList;
          Common.Image localImage;
          StringBuilder localStringBuilder;
          label1451:
          FinskyLog.e(localParseException, "Cannot parse ISO 8601 date", new Object[0]);
        }
        localDocument2 = localDocument1.getCreatorDoc();
        localDetailsTitleCreatorBlock.mCreatorTitle.setText(localDocument2.mDocument.title);
        localList = localDocument2.getImages(0);
        if ((localList == null) || (localList.size() == 0))
        {
          localDetailsTitleCreatorBlock.mCreatorImage.setVisibility(8);
          if ((TextUtils.isEmpty(localDocument2.mDocument.detailsUrl)) || (localNavigationManager == null)) {
            continue;
          }
          localDetailsTitleCreatorBlock.setFocusable(true);
          localDetailsTitleCreatorBlock.setOnClickListener(new DetailsTitleCreatorBlock.1(localDetailsTitleCreatorBlock, localFinskyEventLog, localPlayStoreUiElementNode, localNavigationManager, localDocument2));
          break label1132;
        }
        localImage = (Common.Image)localList.get(0);
        localDetailsTitleCreatorBlock.mCreatorImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
        localDetailsTitleCreatorBlock.mCreatorImage.setVisibility(0);
        if (NavigationManager.areTransitionsEnabled())
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("transition_generic_circle:");
          localStringBuilder.append(":");
          localStringBuilder.append(localDocument2.mDocument.docid);
          localDetailsTitleCreatorBlock.mCreatorImage.setTransitionName(localStringBuilder.toString());
          continue;
          break label1132;
          str5 = null;
          if (i9 != 5) {
            break label1193;
          }
          str5 = localDocument1.getBookDetails().publicationDate;
          break label1193;
          label1487:
          localDetailsTitleCreatorBlock.mCreatorDate.setVisibility(8);
        }
      }
      label1500:
      localDetailsSummaryWishlistView.configure(this.mDoc, this.mNavigationManager);
      break label319;
      j = 1;
      break label443;
      j = bool1;
      break label443;
      k = 2;
      break label482;
      localPlayCardThumbnail.setVisibility(0);
      localPlayCardThumbnail.updateThumbnailPadding(this.mDoc.mDocument.backendId);
      ViewGroup.LayoutParams localLayoutParams = localPlayCardThumbnail.getLayoutParams();
      localLayoutParams.width = CorpusResourceUtils.getRegularDetailsIconWidth(this.mContext, i);
      localLayoutParams.height = CorpusResourceUtils.getRegularDetailsIconHeight(this.mContext, i);
      DocImageView localDocImageView = (DocImageView)localPlayCardThumbnail.getImageView();
      localDocImageView.setScaleType(ImageView.ScaleType.FIT_START);
      if (NavigationManager.areTransitionsEnabled()) {
        localDocImageView.setTransitionName(this.mRevealTransitionCoverName);
      }
      if (!this.mIsRevealTransitionRunning) {
        localDocImageView.bind(this.mDoc, this.mBitmapLoader, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
      }
      localDocImageView.setFocusable(this.mBindingDynamicSection);
      localDocImageView.setContentDescription(CorpusResourceUtils.getItemThumbnailContentDescription(this.mDoc, localResources1));
      if (this.mBindingDynamicSection)
      {
        localDocImageView.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            if (DetailsSummaryViewBinder.this.mDoc.getImages(4) != null) {
              DetailsSummaryViewBinder.this.mNavigationManager.goToImagesLightbox(DetailsSummaryViewBinder.this.mDoc, 0, 4, false);
            }
            while (DetailsSummaryViewBinder.this.mDoc.getImages(0) == null) {
              return;
            }
            DetailsSummaryViewBinder.this.mNavigationManager.goToImagesLightbox(DetailsSummaryViewBinder.this.mDoc, 0, 0, false);
          }
        });
        localDocImageView.setForeground(ContextCompat.getDrawable(this.mContext, 2130837958));
      }
      if (bool1)
      {
        k = 0;
        break label482;
      }
      if (supportsThumbnailPeekingOnNonWideLayout(i))
      {
        if (HeroGraphicView.getDetailsHeroGraphic(this.mDoc, bool1) != null)
        {
          k = 1;
          break label482;
        }
        k = 2;
        break label482;
      }
      k = 2;
      break label482;
      VideoDetails localVideoDetails = this.mDoc.getVideoDetails();
      if ((!this.mDoc.hasPreorderOffer()) && (!TextUtils.isEmpty(localVideoDetails.releaseDate))) {
        addByline(localLayoutInflater1, localViewGroup3, localVideoDetails.releaseDate);
      }
      if (this.mDoc.getBadgeForContentRating() == null)
      {
        if (TextUtils.isEmpty(localVideoDetails.contentRating)) {
          break label1855;
        }
        addByline(localLayoutInflater1, localViewGroup3, localVideoDetails.contentRating);
      }
      while (!TextUtils.isEmpty(localVideoDetails.duration))
      {
        addByline(localLayoutInflater1, localViewGroup3, localVideoDetails.duration);
        break;
        label1855:
        addByline(localLayoutInflater1, localViewGroup3, this.mContext.getString(2131362378));
      }
      if (!this.mDoc.hasSeriesLine()) {
        break label556;
      }
      addByline(localLayoutInflater1, localViewGroup3, this.mDoc.getSeriesLine());
      break label556;
      m = 8;
      break label567;
      label1909:
      str4 = "";
      break label631;
      label1917:
      i8 = 1;
      break label750;
      label1923:
      str3 = this.mContext.getString(2131362468);
      break label795;
      label1938:
      if ((FinskyApp.get().getExperiments().isEnabled(12603136L)) || (this.mDoc.mDocument.backendId == 6)) {
        break label851;
      }
      AccountLibrary localAccountLibrary3 = localLibraries.getAccountLibrary(this.mDetailsAccount);
      Common.Offer localOffer1 = DocUtils.getOfferWithLargestDiscountIfAny(this.mDoc, this.mDfeToc, localAccountLibrary3);
      Object localObject;
      if (localOffer1 == null)
      {
        localObject = null;
        if (TextUtils.isEmpty((CharSequence)localObject)) {
          break label851;
        }
        addExtraLabel(localLayoutInflater2, localViewGroup4, (CharSequence)localObject);
        break label851;
      }
      int i3 = this.mDoc.mDocument.docType;
      int i4 = localOffer1.offerType;
      if (i3 == 6) {}
      int i5;
      switch (i4)
      {
      case 2: 
      case 5: 
      case 6: 
      default: 
        if ((i3 == 5) && (i4 == 3)) {
          i5 = 2131362301;
        }
        break;
      }
      for (;;)
      {
        String str1 = localOffer1.formattedFullAmount;
        String str2 = this.mContext.getString(i5, new Object[] { str1 });
        localObject = new SpannableStringBuilder(str2);
        int i6 = str2.indexOf(str1);
        if (i6 < 0) {
          break;
        }
        ((SpannableStringBuilder)localObject).setSpan(new StrikethroughSpan(), i6, i6 + str1.length(), 17);
        break;
        i5 = 2131362300;
        continue;
        i5 = 2131362304;
        continue;
        i5 = 2131362302;
        continue;
        i5 = 2131362303;
        continue;
        i5 = 2131362299;
      }
      label2228:
      n = 8;
      break label862;
      label2235:
      Resources localResources2 = localViewGroup5.getContext().getResources();
      ViewGroup localViewGroup6 = (ViewGroup)localViewGroup5.findViewById(2131755410);
      ViewGroup localViewGroup7 = (ViewGroup)localViewGroup5.findViewById(2131755411);
      localViewGroup6.removeAllViews();
      localViewGroup7.removeAllViews();
      LayoutInflater localLayoutInflater3 = LayoutInflater.from(this.mContext);
      AppDetails localAppDetails = this.mDoc.getAppDetails();
      boolean bool2 = FinskyApp.get().getExperiments().isEnabled(12602049L);
      int i1 = 0;
      if (!bool2)
      {
        boolean bool3 = TextUtils.isEmpty(localAppDetails.numDownloads);
        i1 = 0;
        if (!bool3)
        {
          addExtraLabelBottom(localLayoutInflater3, localViewGroup6, localAppDetails.numDownloads);
          i1 = 1;
        }
      }
      if (this.mDoc.hasOptimalDeviceClassWarning())
      {
        addExtraLabelBottom(localLayoutInflater3, localViewGroup6, this.mDoc.getOptimalDeviceClassWarning().localizedMessage);
        i1 = 1;
      }
      if (this.mDoc.isPreregistration())
      {
        AccountLibrary localAccountLibrary1 = FinskyApp.get().mLibraries.getAccountLibrary(this.mDetailsAccount);
        if (LibraryUtils.isAvailable(this.mDoc, this.mDfeToc, localAccountLibrary1))
        {
          addExtraLabelBottom(localLayoutInflater3, localViewGroup7, localResources2.getString(2131362596));
          i1 = 1;
        }
      }
      for (;;)
      {
        if (i1 == 0) {
          break label2524;
        }
        localViewGroup8 = localViewGroup5;
        i2 = 0;
        break;
        if (localAppDetails != null)
        {
          if ((localAppDetails.hasInstallNotes) && (!TextUtils.isEmpty(localAppDetails.installNotes)))
          {
            addExtraLabelBottom(localLayoutInflater3, localViewGroup7, localAppDetails.installNotes);
            i1 = 1;
          }
          if (localAppDetails.declaresIab)
          {
            addExtraLabelBottom(localLayoutInflater3, localViewGroup7, localResources2.getString(2131362221));
            i1 = 1;
          }
        }
      }
      label2524:
      break label895;
      label2526:
      this.mButtonSection.setVisibility(8);
    }
  }
  
  public final void bindThumbnailAtTransitionEnd()
  {
    PlayCardThumbnail localPlayCardThumbnail = (PlayCardThumbnail)findViewById(2131755412);
    if (localPlayCardThumbnail != null) {
      ((DocImageView)localPlayCardThumbnail.getImageView()).bind(this.mDoc, this.mBitmapLoader, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
    }
    this.mIsRevealTransitionRunning = false;
  }
  
  public final void bindThumbnailAtTransitionStart(Bitmap paramBitmap)
  {
    PlayCardThumbnail localPlayCardThumbnail = (PlayCardThumbnail)findViewById(2131755412);
    if (localPlayCardThumbnail != null) {
      ((DocImageView)localPlayCardThumbnail.getImageView()).setImageBitmap(paramBitmap);
    }
  }
  
  protected boolean displayActionButtonsIfNecessary(PlayActionButton paramPlayActionButton1, PlayActionButton paramPlayActionButton2, PlayActionButton paramPlayActionButton3, PlayActionButton paramPlayActionButton4, PlayActionButton paramPlayActionButton5, WishlistPlayActionButton paramWishlistPlayActionButton)
  {
    Libraries localLibraries = FinskyApp.get().mLibraries;
    AccountLibrary localAccountLibrary = localLibraries.getAccountLibrary(this.mDetailsAccount);
    Account localAccount1 = LibraryUtils.getOwnerWithCurrentAccount(this.mDoc, localLibraries, this.mDetailsAccount);
    if (localAccount1 != null)
    {
      if (LibraryUtils.isPreordered(this.mDoc, localAccountLibrary))
      {
        Document localDocument2 = this.mDoc;
        paramPlayActionButton1.setVisibility(0);
        if (!this.mIsCancelingPreorder) {}
        for (boolean bool = true;; bool = false)
        {
          paramPlayActionButton1.setEnabled(bool);
          paramPlayActionButton1.configure(localDocument2.mDocument.backendId, 2131361916, getCancelPreorderClickListener(localDocument2, localAccount1));
          return true;
        }
      }
      Document localDocument1 = this.mDoc;
      paramPlayActionButton1.setVisibility(0);
      paramPlayActionButton1.setEnabled(true);
      int n = localDocument1.mDocument.backendId;
      int i1;
      switch (localDocument1.mDocument.backendId)
      {
      case 3: 
      case 5: 
      default: 
        i1 = 2131362438;
      }
      for (;;)
      {
        paramPlayActionButton1.configure(n, i1, this.mNavigationManager.getOpenClickListener(localDocument1, localAccount1, this.mContainerFragment));
        break;
        i1 = 2131362550;
        continue;
        i1 = 2131362305;
        continue;
        i1 = 2131362633;
      }
    }
    if (!LibraryUtils.isAvailable(this.mDoc, this.mDfeToc, localAccountLibrary)) {
      return false;
    }
    Account localAccount2 = this.mDetailsAccount;
    Common.Offer localOffer = this.mDoc.getOffer(1);
    int m;
    Object localObject;
    int j;
    label393:
    int k;
    if (localOffer != null)
    {
      paramPlayActionButton2.setVisibility(0);
      if (this.mDoc.hasPreorderOffer())
      {
        Context localContext = this.mContext;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localOffer.formattedAmount;
        String str2 = localContext.getString(2131362586, arrayOfObject);
        m = 234;
        localObject = str2;
        paramPlayActionButton2.configure(this.mDoc.mDocument.backendId, (String)localObject, this.mNavigationManager.getBuyImmediateClickListener(localAccount2, this.mDoc, localOffer.offerType, null, this.mContinueUrl, m, null));
      }
    }
    else
    {
      Common.Offer[] arrayOfOffer = this.mDoc.mDocument.offer;
      int i = arrayOfOffer.length;
      j = 0;
      if (j >= i) {
        break label510;
      }
      if (arrayOfOffer[j].offerType != 2) {
        break label504;
      }
      k = 1;
      label415:
      if ((k != 0) && (paramPlayActionButton4 != null))
      {
        paramPlayActionButton4.setVisibility(0);
        if (!LibraryUtils.isOfferOwned(this.mDoc, localAccountLibrary, 2)) {
          break label516;
        }
        paramPlayActionButton4.configure(this.mDoc.mDocument.backendId, 2131362708, this.mNavigationManager.getOpenClickListener(this.mDoc, this.mDetailsAccount, this.mContainerFragment));
      }
    }
    for (;;)
    {
      return true;
      String str1 = getBuyButtonString$3da8d3c4(false);
      m = getBuyButtonLoggingElementType$25dacd7(false);
      localObject = str1;
      break;
      label504:
      j++;
      break label393;
      label510:
      k = 0;
      break label415;
      label516:
      paramPlayActionButton4.configure(this.mDoc.mDocument.backendId, 2131362708, this.mNavigationManager.getBuyImmediateClickListener(this.mDetailsAccount, this.mDoc, 2, null, this.mContinueUrl, 222, null));
    }
  }
  
  protected final boolean displayActionButtonsIfNecessaryNew(PlayActionButton paramPlayActionButton1, PlayActionButton paramPlayActionButton2, PlayActionButton paramPlayActionButton3, PlayActionButton paramPlayActionButton4, PlayActionButton paramPlayActionButton5, WishlistPlayActionButton paramWishlistPlayActionButton)
  {
    Libraries localLibraries = FinskyApp.get().mLibraries;
    int i = 0;
    int j = 0;
    PurchaseButtonHelper.DocumentActions localDocumentActions = new PurchaseButtonHelper.DocumentActions();
    PurchaseButtonHelper.getDocumentActions(this.mDetailsAccount, null, localLibraries, null, this.mDfeToc, 3, this.mDoc, localDocumentActions);
    int k = 0;
    Document localDocument;
    NavigationManager localNavigationManager;
    if (k < localDocumentActions.actionCount)
    {
      if (j >= 2)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(2);
        FinskyLog.w("Not supposed to have more than %d actions available", arrayOfObject);
      }
    }
    else
    {
      if ((localDocumentActions.hasAction()) || (!this.mDoc.mDocument.useWishlistAsPrimaryAction)) {
        break label372;
      }
      localDocument = this.mDoc;
      localNavigationManager = this.mNavigationManager;
      if (!WishlistHelper.shouldHideWishlistAction(localDocument, FinskyApp.get().getDfeApi(null))) {
        break label302;
      }
      paramWishlistPlayActionButton.setVisibility(8);
    }
    for (;;)
    {
      return true;
      PurchaseButtonHelper.DocumentAction localDocumentAction = localDocumentActions.getAction(k);
      if ((localDocumentAction.actionType == 6) || (localDocumentAction.actionType == 9))
      {
        configureActionButton$eb2e118(paramPlayActionButton1, localDocumentAction, localDocumentActions.backend);
        j++;
      }
      for (;;)
      {
        k++;
        break;
        if ((localDocumentAction.actionType == 11) || (localDocumentAction.actionType == 10))
        {
          configureActionButton$eb2e118(paramPlayActionButton4, localDocumentAction, localDocumentActions.backend);
          j++;
        }
        else
        {
          if (localDocumentAction.actionType != 12) {
            break label261;
          }
          configureActionButton$eb2e118(paramPlayActionButton5, localDocumentAction, localDocumentActions.backend);
          j++;
        }
      }
      label261:
      if (i == 0) {
        configureActionButton$eb2e118(paramPlayActionButton2, localDocumentAction, localDocumentActions.backend);
      }
      for (;;)
      {
        i++;
        j++;
        break;
        configureActionButton$eb2e118(paramPlayActionButton3, localDocumentAction, localDocumentActions.backend);
      }
      label302:
      paramWishlistPlayActionButton.mDoc = localDocument;
      paramWishlistPlayActionButton.setVisibility(0);
      Account localAccount = FinskyApp.get().getCurrentAccount();
      paramWishlistPlayActionButton.syncVisuals(WishlistHelper.isInWishlist(localDocument, localAccount), localDocument.mDocument.backendId);
      paramWishlistPlayActionButton.mOnWishlistClickListener = new WishlistPlayActionButton.1(paramWishlistPlayActionButton, localDocument, localAccount, localNavigationManager);
      paramWishlistPlayActionButton.mIsConfigured = true;
    }
    label372:
    return localDocumentActions.hasAction();
  }
  
  protected final View findViewById(int paramInt)
  {
    View localView2;
    if (this.mLayouts == null)
    {
      localView2 = null;
      return localView2;
    }
    View[] arrayOfView = this.mLayouts;
    int i = arrayOfView.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label60;
      }
      View localView1 = arrayOfView[j];
      if (localView1 != null)
      {
        localView2 = localView1.findViewById(paramInt);
        if (localView2 != null) {
          break;
        }
      }
    }
    label60:
    return null;
  }
  
  protected final int getBuyButtonLoggingElementType$25dacd7(boolean paramBoolean)
  {
    if (paramBoolean) {}
    do
    {
      return 221;
      if (this.mDoc.needsCheckoutFlow(1)) {
        break;
      }
    } while (this.mDoc.mDocument.backendId == 3);
    if (this.mDoc.mDocument.backendId == 1) {
      return 225;
    }
    return 200;
  }
  
  protected final String getBuyButtonString$3da8d3c4(boolean paramBoolean)
  {
    String str;
    if (paramBoolean) {
      str = this.mContext.getString(2131362224);
    }
    boolean bool1;
    boolean bool2;
    do
    {
      return str;
      bool1 = this.mDoc.needsCheckoutFlow(1);
      if (!bool1)
      {
        if (this.mDoc.mDocument.backendId == 3) {
          return this.mContext.getString(2131362224);
        }
        if (this.mDoc.mDocument.backendId == 1) {
          return this.mContext.getString(2131362438);
        }
      }
      bool2 = FinskyApp.get().getExperiments().isEnabled(12602328L);
      Common.Offer localOffer = this.mDoc.getOffer(1);
      if ((localOffer == null) || (!localOffer.hasFormattedAmount)) {
        break;
      }
      str = localOffer.formattedAmount;
    } while ((!bool2) || (!bool1));
    return this.mContext.getString(2131361909, new Object[] { str });
    return "";
  }
  
  protected View.OnClickListener getDownloadClickListener(Document paramDocument, Account paramAccount)
  {
    return null;
  }
  
  protected View.OnClickListener getViewBundleClickListener$1f5226f(Document paramDocument)
  {
    return null;
  }
  
  public void init(Context paramContext, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PageFragment paramPageFragment, boolean paramBoolean1, String paramString1, String paramString2, boolean paramBoolean2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mContext = paramContext;
    this.mNavigationManager = paramNavigationManager;
    this.mBitmapLoader = paramBitmapLoader;
    this.mContainerFragment = paramPageFragment;
    this.mContinueUrl = paramString1;
    this.mRevealTransitionCoverName = paramString2;
    this.mIsRevealTransitionRunning = paramBoolean2;
    this.mParentNode = paramPlayStoreUiElementNode;
  }
  
  public void onDestroyView()
  {
    this.mIsBinderDestroyed = true;
    if (this.mButtonSection != null)
    {
      int i = this.mButtonSection.getChildCount();
      for (int j = 0; j < i; j++)
      {
        View localView = this.mButtonSection.getChildAt(j);
        if ((localView instanceof PlayActionButton)) {
          ((PlayActionButton)localView).resetClickListener();
        }
      }
    }
  }
  
  public void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 7)
    {
      Document localDocument = (Document)paramBundle.getParcelable("DetailsSummaryViewBinder.doc");
      String str = paramBundle.getString("DetailsSummaryViewBinder.ownerAccountName");
      DfeApi localDfeApi = FinskyApp.get().getDfeApi(str);
      RevokeListenerWrapper localRevokeListenerWrapper = new RevokeListenerWrapper(FinskyApp.get().mLibraryReplicators, localDfeApi.getAccount(), new Runnable()
      {
        public final void run()
        {
          Toast.makeText(DetailsSummaryViewBinder.this.mContext, 2131361918, 0).show();
        }
      });
      Response.ErrorListener local4 = new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          DetailsSummaryViewBinder.this.mIsCancelingPreorder = false;
          DetailsSummaryViewBinder.this.refresh();
          Toast.makeText(DetailsSummaryViewBinder.this.mContext, 2131361917, 0).show();
        }
      };
      this.mIsCancelingPreorder = true;
      refresh();
      localDfeApi.revoke$b40b8a6(localDocument.mDocument.docid, localRevokeListenerWrapper, local4);
    }
  }
  
  public final void refresh()
  {
    if (!this.mIsBinderDestroyed) {
      bind(this.mDoc, this.mBindingDynamicSection, this.mLayouts);
    }
  }
  
  public final void setDynamicFeaturesVisibility(boolean paramBoolean)
  {
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      this.mHideDynamicFeatures = bool;
      return;
    }
  }
  
  protected void setupActionButtons(boolean paramBoolean)
  {
    PlayActionButton localPlayActionButton1 = (PlayActionButton)findViewById(2131755375);
    PlayActionButton localPlayActionButton2 = (PlayActionButton)findViewById(2131755378);
    PlayActionButton localPlayActionButton3 = (PlayActionButton)findViewById(2131755377);
    PlayActionButton localPlayActionButton4 = (PlayActionButton)findViewById(2131755374);
    PlayActionButton localPlayActionButton5 = (PlayActionButton)findViewById(2131755371);
    WishlistPlayActionButton localWishlistPlayActionButton = (WishlistPlayActionButton)findViewById(2131755379);
    localPlayActionButton4.setVisibility(8);
    if (localPlayActionButton1 != null) {
      localPlayActionButton1.setVisibility(8);
    }
    localPlayActionButton2.setVisibility(8);
    if (localPlayActionButton3 != null) {
      localPlayActionButton3.setVisibility(8);
    }
    if (localPlayActionButton5 != null) {
      localPlayActionButton5.setVisibility(8);
    }
    if (localWishlistPlayActionButton != null) {
      localWishlistPlayActionButton.setVisibility(8);
    }
    if (this.mHideDynamicFeatures) {}
    while ((paramBoolean) || (!displayActionButtonsIfNecessary(localPlayActionButton4, localPlayActionButton2, localPlayActionButton3, localPlayActionButton1, localPlayActionButton5, localWishlistPlayActionButton))) {
      return;
    }
    syncButtonSectionVisibility();
  }
  
  protected boolean shouldDisplayOfferNote()
  {
    Libraries localLibraries = FinskyApp.get().mLibraries;
    return LibraryUtils.getOwnerWithCurrentAccount(this.mDoc, localLibraries, this.mDetailsAccount) == null;
  }
  
  protected void showDynamicStatus(int paramInt)
  {
    TextView localTextView = (TextView)this.mDynamicSection.findViewById(2131755408);
    this.mButtonSection.setVisibility(8);
    localTextView.setVisibility(0);
    localTextView.setText(this.mContext.getResources().getString(paramInt));
  }
  
  protected final void syncButtonSectionVisibility()
  {
    UiUtils.syncContainerVisibility(this.mButtonSection, 4);
  }
  
  protected void syncDynamicSection()
  {
    AccountLibrary localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(this.mDetailsAccount);
    setDynamicFeaturesVisibility(LibraryUtils.isAvailable(this.mDoc, this.mDfeToc, localAccountLibrary));
    this.mButtonSection.setVisibility(4);
    if (this.mHideDynamicFeatures) {
      return;
    }
    if (this.mIsPendingRefund)
    {
      showDynamicStatus(2131362651);
      return;
    }
    setupActionButtons(false);
  }
  
  protected void updateButtonActionStyle()
  {
    int i = 2147483647;
    for (int j = 0; j < this.mButtonSection.getChildCount(); j++)
    {
      PlayActionButton localPlayActionButton2 = (PlayActionButton)this.mButtonSection.getChildAt(j);
      if ((localPlayActionButton2.getVisibility() == 0) && (localPlayActionButton2.getPriority() < i)) {
        i = localPlayActionButton2.getPriority();
      }
    }
    int k = 0;
    if (k < this.mButtonSection.getChildCount())
    {
      PlayActionButton localPlayActionButton1 = (PlayActionButton)this.mButtonSection.getChildAt(k);
      if (localPlayActionButton1.getVisibility() == 0)
      {
        if (localPlayActionButton1.getPriority() != i) {
          break label114;
        }
        localPlayActionButton1.setActionStyle(0);
      }
      for (;;)
      {
        k++;
        break;
        label114:
        localPlayActionButton1.setActionStyle(2);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsSummaryViewBinder
 * JD-Core Version:    0.7.0.1
 */