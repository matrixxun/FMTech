package com.google.android.finsky.activities;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.layout.DetailsSummaryWishlistView;
import com.google.android.finsky.layout.DocImageView;
import com.google.android.finsky.layout.HorizontalStrip;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.ScreenshotGallery;
import com.google.android.finsky.layout.WarningMessageSection;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.ExternalReferrer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.VoucherUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.StarRatingBar;
import java.text.NumberFormat;
import java.util.List;

public final class InlineAppDetailsFragment
  extends PageFragment
  implements RootUiElementNode
{
  private DfeDetails mDfeDetails;
  private DfeToc mDfeToc;
  private Document mDoc;
  private String mDocId;
  protected GenericUiElementNode mDocumentUiElementNode = null;
  private boolean mHasBeenAuthenticated = false;
  private Handler mImpressionHandler;
  private long mImpressionId = FinskyEventLog.getNextImpressionId();
  private Libraries mLibraries;
  private String mReferrer;
  private PlayStore.PlayStoreUiElement mRootUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(14);
  private Bundle mSavedInstanceState;
  private boolean mSentImpression = false;
  
  public static InlineAppDetailsFragment newInstance(String paramString1, String paramString2, String paramString3)
  {
    InlineAppDetailsFragment localInlineAppDetailsFragment = new InlineAppDetailsFragment();
    localInlineAppDetailsFragment.mDocId = paramString1;
    localInlineAppDetailsFragment.mReferrer = paramString2;
    localInlineAppDetailsFragment.setDfeAccount(paramString3);
    return localInlineAppDetailsFragment;
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.rootImpression(this.mImpressionHandler, this.mImpressionId, this, paramPlayStoreUiElementNode);
  }
  
  public final void flushImpression()
  {
    FinskyEventLog.flushImpression(this.mImpressionHandler, this.mImpressionId, this);
  }
  
  protected final int getLayoutRes()
  {
    return 2130968791;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mRootUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mLibraries = FinskyApp.get().mLibraries;
    this.mDfeToc = FinskyApp.get().mToc;
    this.mSavedInstanceState = paramBundle;
    if (paramBundle != null)
    {
      this.mDocId = paramBundle.getString("docId");
      this.mReferrer = paramBundle.getString("referrer");
    }
    this.mLayoutSwitcher.switchToLoadingMode();
  }
  
  public final void onAttach(Activity paramActivity)
  {
    this.mImpressionHandler = new Handler(paramActivity.getMainLooper());
    super.onAttach(paramActivity);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (paramBundle == null) {
      return;
    }
    paramBundle.putString("docId", this.mDocId);
    paramBundle.putString("referrer", this.mReferrer);
  }
  
  protected final void rebindViews()
  {
    if ((this.mDfeDetails != null) && (this.mDfeDetails.isReady())) {}
    for (int i = 1; (i == 0) || (!this.mHasBeenAuthenticated); i = 0) {
      return;
    }
    this.mDoc = this.mDfeDetails.getDocument();
    if (this.mDoc.mDocument.backendId != 3)
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = this.mDocId;
      FinskyLog.e("Only apps are supported: %s", arrayOfObject3);
      getActivity().finish();
      return;
    }
    Document localDocument1;
    Resources localResources;
    int j;
    label325:
    int i8;
    label776:
    int i9;
    label786:
    Object localObject2;
    label813:
    WarningMessageSection localWarningMessageSection;
    Document localDocument2;
    DfeToc localDfeToc2;
    Libraries localLibraries1;
    Account localAccount2;
    boolean bool1;
    int k;
    label1137:
    boolean bool2;
    boolean bool3;
    int m;
    label1192:
    boolean bool4;
    Context localContext2;
    Spanned localSpanned;
    if (this.mDoc != null)
    {
      View localView = this.mView;
      localDocument1 = this.mDoc;
      TextView localTextView1 = (TextView)localView.findViewById(2131755262);
      if (localTextView1 != null)
      {
        localTextView1.setText(localDocument1.mDocument.title);
        localTextView1.setSelected(true);
      }
      ViewGroup localViewGroup1 = (ViewGroup)localView.findViewById(2131755395);
      DecoratedTextView localDecoratedTextView = (DecoratedTextView)localViewGroup1.findViewById(2131755396);
      if (localDecoratedTextView != null)
      {
        localViewGroup1.setVisibility(0);
        localDecoratedTextView.setText(PlayCardUtils.getDocDisplaySubtitle(localDocument1));
        BadgeUtils.configureCreatorBadge(localDocument1, this.mBitmapLoader, localDecoratedTextView);
      }
      ViewGroup localViewGroup2 = (ViewGroup)localView.findViewById(2131755398);
      if (localViewGroup2 != null)
      {
        localViewGroup2.setVisibility(0);
        BadgeUtils.configureContentRatingBadge(this.mDoc, this.mBitmapLoader, localViewGroup2);
      }
      DetailsSummaryWishlistView localDetailsSummaryWishlistView = (DetailsSummaryWishlistView)localView.findViewById(2131755394);
      if (localDetailsSummaryWishlistView != null) {
        localDetailsSummaryWishlistView.configure(localDocument1, this.mNavigationManager);
      }
      localResources = this.mContext.getResources();
      PlayCardThumbnail localPlayCardThumbnail = (PlayCardThumbnail)localView.findViewById(2131755412);
      localPlayCardThumbnail.setVisibility(0);
      localPlayCardThumbnail.updateThumbnailPadding(-1);
      ViewGroup.LayoutParams localLayoutParams = localPlayCardThumbnail.getLayoutParams();
      if (!FinskyApp.get().getExperiments().isEnabled(12602392L)) {
        break label1426;
      }
      j = localResources.getDimensionPixelSize(2131493034);
      localLayoutParams.width = j;
      localLayoutParams.height = j;
      DocImageView localDocImageView = (DocImageView)localPlayCardThumbnail.getImageView();
      localDocImageView.setScaleType(ImageView.ScaleType.FIT_START);
      localDocImageView.bind(localDocument1, this.mBitmapLoader, PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
      localDocImageView.setFocusable(false);
      localDocImageView.setContentDescription(CorpusResourceUtils.getItemThumbnailContentDescription(localDocument1, localResources));
      StarRatingBar localStarRatingBar = (StarRatingBar)localView.findViewById(2131755616);
      if ((localStarRatingBar != null) && (localDocument1.hasRating())) {
        localStarRatingBar.setRating(localDocument1.getStarRating());
      }
      TextView localTextView2 = (TextView)localView.findViewById(2131755617);
      if (localTextView2 != null)
      {
        float f = (float)localDocument1.getRatingCount();
        localTextView2.setText(NumberFormat.getIntegerInstance().format(f));
      }
      AppDetails localAppDetails = localDocument1.getAppDetails();
      if (localAppDetails != null)
      {
        if ((localAppDetails.hasInstallNotes) && (!TextUtils.isEmpty(localAppDetails.installNotes)))
        {
          TextView localTextView6 = (TextView)localView.findViewById(2131755607);
          localTextView6.setText(localAppDetails.installNotes);
          localTextView6.setVisibility(0);
        }
        if (localAppDetails.declaresIab)
        {
          TextView localTextView5 = (TextView)localView.findViewById(2131755608);
          localTextView5.setText(2131362221);
          localTextView5.setVisibility(0);
        }
      }
      DfeToc localDfeToc1 = FinskyApp.get().mToc;
      Account localAccount1 = this.mDfeApi.getAccount();
      if (LibraryUtils.isAvailable(localDocument1, localDfeToc1, FinskyApp.get().mLibraries.getAccountLibrary(localAccount1)))
      {
        PlayActionButton localPlayActionButton1 = (PlayActionButton)localView.findViewById(2131755378);
        PlayActionButton localPlayActionButton2 = (PlayActionButton)localView.findViewById(2131755374);
        localPlayActionButton2.setVisibility(8);
        localPlayActionButton1.setVisibility(8);
        Libraries localLibraries2 = FinskyApp.get().mLibraries;
        AppStates localAppStates = FinskyApp.get().mAppStates;
        AppActionAnalyzer localAppActionAnalyzer = new AppActionAnalyzer(localDocument1.getAppDetails().packageName, localAppStates, localLibraries2);
        if ((localAppActionAnalyzer.isLaunchable) && (!localAppActionAnalyzer.isContinueLaunch))
        {
          localPlayActionButton2.setVisibility(0);
          View.OnClickListener localOnClickListener = this.mNavigationManager.getOpenClickListener(this.mDoc, localAccount1, this);
          localPlayActionButton2.configure(localDocument1.mDocument.backendId, 2131362438, localOnClickListener);
        }
        if ((!localAppActionAnalyzer.isInstalled) && (LibraryUtils.isAvailable(localDocument1, localDfeToc1, localLibraries2)))
        {
          Account localAccount5 = LibraryUtils.getOwnerWithCurrentAccount(localDocument1, FinskyApp.get().mLibraries, localAccount1);
          localPlayActionButton1.setVisibility(0);
          if (localAccount5 == null) {
            break label1439;
          }
          i8 = 1;
          if (i8 == 0) {
            break label1445;
          }
          i9 = 221;
          int i10 = localDocument1.mDocument.backendId;
          if (i8 == 0) {
            break label1502;
          }
          localObject2 = this.mContext.getString(2131362224);
          localPlayActionButton1.configure(i10, (String)localObject2, new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              FragmentActivity localFragmentActivity = InlineAppDetailsFragment.this.getActivity();
              if ((localFragmentActivity instanceof InlineAppDetailsDialog))
              {
                InlineAppDetailsDialog localInlineAppDetailsDialog = (InlineAppDetailsDialog)localFragmentActivity;
                if (localInlineAppDetailsDialog.mDialog != null) {
                  localInlineAppDetailsDialog.mDialog.setVisibility(8);
                }
              }
              this.val$listener.onClick(paramAnonymousView);
            }
          });
        }
      }
      TextView localTextView3 = (TextView)localView.findViewById(2131755602);
      if (localTextView3 != null)
      {
        Object localObject1 = this.mDoc.mDocument.promotionalDescription;
        if (TextUtils.isEmpty((CharSequence)localObject1)) {
          localObject1 = this.mDoc.getDescription();
        }
        localTextView3.setText((CharSequence)localObject1);
      }
      ScreenshotGallery localScreenshotGallery = (ScreenshotGallery)localView.findViewById(2131755603);
      if (localScreenshotGallery != null)
      {
        int i7 = this.mContext.getResources().getDimensionPixelOffset(2131493503);
        localScreenshotGallery.mImageStrip.setMargins(0, i7);
        Document localDocument3 = this.mDoc;
        BitmapLoader localBitmapLoader = this.mBitmapLoader;
        NavigationManager localNavigationManager = this.mNavigationManager;
        localScreenshotGallery.setVisibility(0);
        localScreenshotGallery.mBitmapLoader = localBitmapLoader;
        localScreenshotGallery.mNavigationManager = localNavigationManager;
        localScreenshotGallery.mDocument = localDocument3;
        localScreenshotGallery.mHasDetailsLoaded = true;
        localScreenshotGallery.startImageLoadingTaskIfNecessary();
      }
      TextView localTextView4 = (TextView)localView.findViewById(2131755605);
      if (localTextView4 != null)
      {
        localTextView4.setText(getString(2131362328).toUpperCase());
        localTextView4.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setData(Uri.parse("market://details?id=" + InlineAppDetailsFragment.this.mDoc.getAppDetails().packageName));
            InlineAppDetailsFragment.this.startActivity(localIntent);
          }
        });
      }
      localWarningMessageSection = (WarningMessageSection)localView.findViewById(2131755431);
      if (localWarningMessageSection != null)
      {
        localDocument2 = this.mDoc;
        localDfeToc2 = this.mDfeToc;
        localLibraries1 = this.mLibraries;
        localAccount2 = this.mDfeApi.getAccount();
        localWarningMessageSection.mBinded = true;
        AccountLibrary localAccountLibrary = localLibraries1.getAccountLibrary(localAccount2);
        bool1 = LibraryUtils.isAvailable(localDocument2, localDfeToc2, localAccountLibrary);
        if ((!VoucherUtils.hasApplicableVouchers(localDocument2, localAccountLibrary)) || (!localDocument2.hasApplicableVoucherDescription())) {
          break label1660;
        }
        k = 1;
        bool2 = localDocument2.hasWarningMessage();
        bool3 = WarningMessageSection.shouldShowLicenseMessage(localDocument2, localAccountLibrary);
        if ((!LibraryUtils.isOwned(localDocument2, localAccountLibrary)) || (localDocument2.mDocument.docType != 1) || (!AppActionAnalyzer.isUninstallBlockedByAdmin(localDocument2.getAppDetails().packageName))) {
          break label1666;
        }
        m = 1;
        bool4 = WarningMessageSection.shouldShowExternallyHostedMessage(localDocument2);
        if ((localDocument2.mDocument.docType == 1) || (LibraryUtils.isOwned(localDocument2, localAccountLibrary))) {
          break label1781;
        }
        Account localAccount3 = LibraryUtils.getFirstOwner(localDocument2, localLibraries1);
        localContext2 = localWarningMessageSection.getContext();
        if (localAccount3 == null) {
          break label1672;
        }
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localAccount3.name;
        localSpanned = Html.fromHtml(localContext2.getString(2131362466, arrayOfObject2));
      }
    }
    for (;;)
    {
      if ((!bool1) || (bool3) || (bool2) || (k != 0) || (bool4) || (!TextUtils.isEmpty(localSpanned)) || (m != 0)) {
        break label1787;
      }
      localWarningMessageSection.setVisibility(8);
      if (this.mSavedInstanceState == null) {
        ExternalReferrer.saveExternalReferrer(this.mReferrer, this.mDoc.getFullDocid());
      }
      FinskyEventLog.startNewImpression(this);
      FinskyEventLog.setServerLogCookie(this.mRootUiElementProto, this.mDfeDetails.getServerLogsCookie());
      if (this.mDoc == null) {
        break;
      }
      if (this.mDocumentUiElementNode == null) {
        this.mDocumentUiElementNode = new GenericUiElementNode(209, null, null, this);
      }
      this.mDocumentUiElementNode.setServerLogsCookie(this.mDoc.mDocument.serverLogsCookie);
      if (this.mSentImpression) {
        break;
      }
      childImpression(this.mDocumentUiElementNode);
      this.mSentImpression = true;
      return;
      label1426:
      j = localResources.getDimensionPixelSize(2131493033);
      break label325;
      label1439:
      i8 = 0;
      break label776;
      label1445:
      if (!localDocument1.needsCheckoutFlow(1))
      {
        if (localDocument1.mDocument.backendId == 3)
        {
          i9 = 221;
          break label786;
        }
        if (localDocument1.mDocument.backendId == 1)
        {
          i9 = 225;
          break label786;
        }
      }
      i9 = 200;
      break label786;
      label1502:
      boolean bool5 = localDocument1.needsCheckoutFlow(1);
      if (!bool5)
      {
        if (localDocument1.mDocument.backendId == 3)
        {
          localObject2 = getActivity().getString(2131362224);
          break label813;
        }
        if (localDocument1.mDocument.backendId == 1)
        {
          localObject2 = this.mContext.getString(2131362438);
          break label813;
        }
      }
      boolean bool6 = FinskyApp.get().getExperiments().isEnabled(12602328L);
      Common.Offer localOffer = localDocument1.getOffer(1);
      if ((localOffer != null) && (localOffer.hasFormattedAmount))
      {
        String str = localOffer.formattedAmount;
        if ((bool6) && (bool5))
        {
          localObject2 = this.mContext.getString(2131361909, new Object[] { str });
          break label813;
        }
        localObject2 = str;
        break label813;
      }
      localObject2 = "";
      break label813;
      label1660:
      k = 0;
      break label1137;
      label1666:
      m = 0;
      break label1192;
      label1672:
      if (localDocument2.hasSubscriptions())
      {
        List localList = DocUtils.getSubscriptions(localDocument2, localDfeToc2, localLibraries1);
        if (LibraryUtils.getOwnerWithCurrentAccount(localList, localLibraries1, localAccount2) == null) {
          for (int i6 = 0;; i6++)
          {
            if (i6 >= localList.size()) {
              break label1781;
            }
            Account localAccount4 = LibraryUtils.getFirstOwner((Document)localList.get(i6), localLibraries1);
            if (localAccount4 != null)
            {
              Object[] arrayOfObject1 = new Object[1];
              arrayOfObject1[0] = localAccount4.name;
              localSpanned = Html.fromHtml(localContext2.getString(2131362466, arrayOfObject1));
              break;
            }
          }
        }
      }
      label1781:
      localSpanned = null;
    }
    label1787:
    localWarningMessageSection.setVisibility(0);
    localWarningMessageSection.mDetailsWarningInfoSecondLineText.setVisibility(8);
    int n = localDocument2.mDocument.backendId;
    if (!bool1)
    {
      localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(DocUtils.getAvailabilityRestrictionResourceId(localDocument2));
      localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(CorpusResourceUtils.getWarningDrawable(n));
    }
    for (;;)
    {
      Context localContext1 = localWarningMessageSection.getContext();
      ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(localContext1, n);
      localWarningMessageSection.mDetailsWarningInfoFirstLineText.setTextColor(localColorStateList);
      localWarningMessageSection.mDetailsWarningInfoSecondLineText.setTextColor(localColorStateList);
      int i1 = UiUtils.interpolateColor$4868c7be(CorpusResourceUtils.getPrimaryColor(localContext1, n));
      int i2 = localWarningMessageSection.getPaddingTop();
      int i3 = localWarningMessageSection.getPaddingBottom();
      int i4 = ViewCompat.getPaddingEnd(localWarningMessageSection);
      int i5 = ViewCompat.getPaddingStart(localWarningMessageSection);
      Drawable[] arrayOfDrawable = new Drawable[2];
      arrayOfDrawable[0] = new ColorDrawable(i1);
      arrayOfDrawable[1] = ContextCompat.getDrawable(localContext1, 2130837958);
      localWarningMessageSection.setBackgroundDrawable(new LayerDrawable(arrayOfDrawable));
      ViewCompat.setPaddingRelative(localWarningMessageSection, i5, i2, i4, i3);
      break;
      if (bool3)
      {
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(2131362118);
        localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(2130837761);
      }
      else if (bool4)
      {
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(2131362119);
        localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(CorpusResourceUtils.getWarningDrawable(n));
      }
      else if (bool2)
      {
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(localDocument2.getWarningMessage());
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setMovementMethod(LinkMovementMethod.getInstance());
        localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(CorpusResourceUtils.getWarningDrawable(n));
      }
      else if (k != 0)
      {
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(localDocument2.getApplicableVoucherDescription());
        localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(CorpusResourceUtils.getRewardDrawable(n));
      }
      else if (!TextUtils.isEmpty(localSpanned))
      {
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(localSpanned);
        localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(CorpusResourceUtils.getWarningDrawable(n));
      }
      else if (m != 0)
      {
        localWarningMessageSection.mDetailsWarningInfoFirstLineText.setText(2131362810);
        localWarningMessageSection.mDetailsWarningInfoIcon.setImageResource(CorpusResourceUtils.getWarningDrawable(n));
      }
    }
  }
  
  protected final void requestData()
  {
    if (this.mDfeDetails != null)
    {
      this.mDfeDetails.removeDataChangedListener(this);
      this.mDfeDetails.removeErrorListener(this);
    }
    this.mDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(null), DfeUtils.createDetailsUrlFromId(this.mDocId));
    this.mDfeDetails.addDataChangedListener(this);
    this.mDfeDetails.addErrorListener(this);
  }
  
  public final void setHasBeenAuthenticated()
  {
    this.mHasBeenAuthenticated = true;
    requestData();
  }
  
  public final void startNewImpression()
  {
    this.mImpressionId = FinskyEventLog.getNextImpressionId();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.InlineAppDetailsFragment
 * JD-Core Version:    0.7.0.1
 */