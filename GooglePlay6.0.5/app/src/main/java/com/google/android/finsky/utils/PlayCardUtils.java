package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.layout.DocImageView;
import com.google.android.finsky.layout.play.FeaturedCardView;
import com.google.android.finsky.layout.play.FeaturedWideCardView;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayBundleContainerViewRow;
import com.google.android.finsky.layout.play.PlayCardClusterMetadata;
import com.google.android.finsky.layout.play.PlayCardDismissListener;
import com.google.android.finsky.layout.play.PlayCardViewListingSmall;
import com.google.android.finsky.layout.play.PlayCardViewMediumPlus;
import com.google.android.finsky.layout.play.PlayCardViewMyApps;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationManager.UsesGenericTransition;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Dismissal;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.OverflowLink;
import com.google.android.finsky.protos.RateSuggestedContentResponse;
import com.google.android.finsky.protos.Reason;
import com.google.android.finsky.protos.ReasonPlusOne;
import com.google.android.finsky.protos.ReasonReview;
import com.google.android.finsky.protos.ReasonUserAction;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.Snow;
import com.google.android.finsky.protos.SuggestionReasons;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.protos.Warning;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayCardLabelView;
import com.google.android.play.layout.PlayCardSnippet;
import com.google.android.play.layout.PlayCardThumbnail;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.layout.PlayCardViewMini;
import com.google.android.play.layout.PlayCardViewSmall;
import com.google.android.play.layout.PlayPopupMenu;
import com.google.android.play.layout.PlayPopupMenu.OnPopupActionSelectedListener;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.layout.StarRatingBar;
import com.google.android.play.utils.DocV2Utils;
import com.google.android.play.utils.config.GservicesValue;

public final class PlayCardUtils
{
  private static final PurchaseButtonHelper.TextStyle sActionStyle;
  private static boolean sBadAdReportingEnabled;
  private static String sCachedExperimentAccountName;
  private static boolean sDisplayAppSizeEnabled;
  private static final PurchaseButtonHelper.DocumentActions sDocumentActions = new PurchaseButtonHelper.DocumentActions();
  private static boolean sHideSalesPricesExperimentEnabled;
  private static final PurchaseButtonHelper.TextStyle sListingStyle = new PurchaseButtonHelper.TextStyle();
  private static StringBuilder sTransitionNameCoverBuilder;
  private static StringBuilder sTransitionNameGenericBuilder;
  
  static
  {
    sActionStyle = new PurchaseButtonHelper.TextStyle();
    if (NavigationManager.areTransitionsEnabled())
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      sTransitionNameCoverBuilder = localStringBuilder1;
      localStringBuilder1.append("transition_card_details:cover:");
      StringBuilder localStringBuilder2 = new StringBuilder();
      sTransitionNameGenericBuilder = localStringBuilder2;
      localStringBuilder2.append("transition_generic_circle:");
    }
  }
  
  public static void bindCard(PlayCardViewBase paramPlayCardViewBase, Document paramDocument, String paramString, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    bindCard(paramPlayCardViewBase, paramDocument, paramString, paramBitmapLoader, paramNavigationManager, false, null, paramPlayStoreUiElementNode, true, -1, false);
  }
  
  public static void bindCard(final PlayCardViewBase paramPlayCardViewBase, final Document paramDocument, String paramString, BitmapLoader paramBitmapLoader, final NavigationManager paramNavigationManager, boolean paramBoolean1, final PlayCardDismissListener paramPlayCardDismissListener, PlayStoreUiElementNode paramPlayStoreUiElementNode, boolean paramBoolean2, int paramInt, boolean paramBoolean3)
  {
    Utils.ensureOnMainThread();
    CardUiElementNode localCardUiElementNode1 = (CardUiElementNode)paramPlayCardViewBase.getLoggingData();
    final CardUiElementNode localCardUiElementNode2;
    boolean bool1;
    String str2;
    label177:
    label189:
    DocImageView localDocImageView;
    int[] arrayOfInt;
    label239:
    int i7;
    label302:
    int i8;
    label342:
    DecoratedTextView localDecoratedTextView1;
    label429:
    label434:
    int i;
    label535:
    int j;
    label548:
    PlayTextView localPlayTextView1;
    int k;
    label567:
    Object localObject1;
    String str3;
    String str8;
    label635:
    String str7;
    label673:
    label694:
    Object localObject4;
    label716:
    int i5;
    label735:
    Object localObject2;
    PlayCardSnippet localPlayCardSnippet1;
    int n;
    int i1;
    Object localObject3;
    label835:
    label868:
    final ImageView localImageView1;
    label923:
    TextView localTextView2;
    label950:
    CharSequence localCharSequence1;
    label1009:
    int i2;
    if (localCardUiElementNode1 != null)
    {
      localCardUiElementNode1.initNode(paramPlayCardViewBase.getCardType(), paramPlayStoreUiElementNode);
      localCardUiElementNode2 = (CardUiElementNode)paramPlayCardViewBase.getLoggingData();
      Resources localResources1 = paramPlayCardViewBase.getResources();
      bool1 = paramDocument.isAdvertisement();
      PlayCardCustomizerRepository localPlayCardCustomizerRepository = PlayCardCustomizerRepository.getInstance();
      PlayCardCustomizer localPlayCardCustomizer = localPlayCardCustomizerRepository.mCustomizers[paramPlayCardViewBase.getCardType()];
      if (localPlayCardCustomizer == null) {
        localPlayCardCustomizer = localPlayCardCustomizerRepository.mDefaultCustomizer;
      }
      localPlayCardCustomizer.preBind(paramPlayCardViewBase, paramDocument);
      TextView localTextView1 = paramPlayCardViewBase.getTitle();
      String str1 = paramDocument.mDocument.title;
      if ((paramInt >= 0) && (!bool1))
      {
        Resources localResources3 = paramPlayCardViewBase.getResources();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramInt + 1);
        arrayOfObject[1] = str1;
        str1 = localResources3.getString(2131362416, arrayOfObject);
      }
      localTextView1.setVisibility(0);
      localTextView1.setText(str1);
      if (paramDocument == null) {
        break label1127;
      }
      str2 = paramDocument.mDocument.title;
      if ((paramDocument != null) && (!TextUtils.isEmpty(str2))) {
        break label1133;
      }
      PlayCardThumbnail localPlayCardThumbnail = paramPlayCardViewBase.getThumbnail();
      localDocImageView = null;
      if (localPlayCardThumbnail != null)
      {
        localPlayCardThumbnail.setVisibility(0);
        localPlayCardThumbnail.updateThumbnailPadding(paramDocument.mDocument.backendId);
        if (!(paramPlayCardViewBase instanceof PlayCardImageTypeSequence)) {
          break label1191;
        }
        arrayOfInt = ((PlayCardImageTypeSequence)paramPlayCardViewBase).getImageTypePreference();
        localDocImageView = (DocImageView)localPlayCardThumbnail.getImageView();
        localDocImageView.bind(paramDocument, paramBitmapLoader, arrayOfInt);
      }
      if (NavigationManager.areTransitionsEnabled())
      {
        int i6 = paramDocument.mDocument.docType;
        if ((i6 != 2) && (i6 != 4) && (i6 != 24) && (i6 != 25)) {
          break label1199;
        }
        i7 = 1;
        if ((i7 != 0) || (localDocImageView == null)) {
          break label1264;
        }
        if ((!(paramPlayCardViewBase instanceof NavigationManager.UsesGenericTransition)) && (i6 != 18) && (i6 != 19) && (i6 != 3)) {
          break label1205;
        }
        i8 = 1;
        if (((i6 == 30) || (i6 == 34) || (i6 == 8)) && (!paramDocument.hasImages(4))) {
          i8 = 1;
        }
        if (i8 == 0) {
          break label1211;
        }
        sTransitionNameGenericBuilder.setLength(26);
        sTransitionNameGenericBuilder.append(paramDocument.mDocument.docid);
        sTransitionNameGenericBuilder.append(':');
        sTransitionNameGenericBuilder.append(paramString);
        localDocImageView.setTransitionName(sTransitionNameGenericBuilder.toString());
        paramPlayCardViewBase.setTransitionGroup(true);
      }
      if (paramBoolean2) {
        paramPlayCardViewBase.setThumbnailAspectRatio(PlayCardClusterMetadata.getAspectRatio(paramDocument.mDocument.docType));
      }
      localDecoratedTextView1 = (DecoratedTextView)paramPlayCardViewBase.getSubtitle();
      StarRatingBar localStarRatingBar = paramPlayCardViewBase.getRatingBar();
      DecoratedTextView localDecoratedTextView2 = (DecoratedTextView)paramPlayCardViewBase.getItemBadge();
      if (localDecoratedTextView1 != null) {
        localDecoratedTextView1.setVisibility(4);
      }
      if (localStarRatingBar != null) {
        localStarRatingBar.setVisibility(4);
      }
      if (localDecoratedTextView2 != null) {
        localDecoratedTextView2.setVisibility(4);
      }
      boolean bool2 = paramPlayCardViewBase.supportsSubtitleAndRating();
      if ((localStarRatingBar == null) || (paramDocument.mDocument.docType != 1)) {
        break label1278;
      }
      i = 1;
      if ((!bool2) && (i == 0)) {
        break label1284;
      }
      j = 1;
      localPlayTextView1 = paramPlayCardViewBase.getDescription();
      if ((!bool2) && (i != 0)) {
        break label1290;
      }
      k = 1;
      localObject1 = null;
      PlayTextView localPlayTextView2 = paramPlayCardViewBase.getSubtitle2();
      if (localPlayTextView2 != null) {
        localPlayTextView2.setVisibility(8);
      }
      if ((!paramDocument.hasSeriesLine()) || (k == 0) || (localDecoratedTextView1 == null)) {
        break label1303;
      }
      str3 = paramDocument.getSeriesLine();
      str8 = getDocDisplaySubtitle(paramDocument);
      if (localPlayTextView2 == null) {
        break label1296;
      }
      localPlayTextView2.setVisibility(0);
      localPlayTextView2.setText(str8);
      if (j != 0) {
        BadgeUtils.configureRatingItemSection(paramDocument, localStarRatingBar, localDecoratedTextView2);
      }
      if (localDecoratedTextView1 != null)
      {
        if (k == 0) {
          break label1360;
        }
        localDecoratedTextView1.setVisibility(0);
        if (str3 == null) {
          break label1351;
        }
        str7 = str3;
        localDecoratedTextView1.setText(str7);
        if (paramPlayCardViewBase.mShowInlineCreatorBadge) {
          BadgeUtils.configureCreatorBadge(paramDocument, paramBitmapLoader, localDecoratedTextView1);
        }
      }
      updateCardLabel(paramDocument, paramPlayCardViewBase);
      if (localPlayTextView1 != null)
      {
        if (TextUtils.isEmpty((CharSequence)localObject1)) {
          break label1370;
        }
        localObject4 = localObject1;
        localPlayTextView1.setText(localObject4);
        if (!TextUtils.isEmpty(localObject4)) {
          break label1420;
        }
        i5 = 8;
        localPlayTextView1.setVisibility(i5);
      }
      localObject2 = paramPlayCardViewBase.getSnippet1();
      localPlayCardSnippet1 = paramPlayCardViewBase.getSnippet2();
      if ((localObject2 != null) || (localPlayCardSnippet1 != null))
      {
        if (localObject2 != null)
        {
          ((PlayCardSnippet)localObject2).setVisibility(8);
          ((PlayCardSnippet)localObject2).setSeparatorVisible(false);
        }
        if (localPlayCardSnippet1 != null)
        {
          localPlayCardSnippet1.setVisibility(8);
          localPlayCardSnippet1.setSeparatorVisible(false);
        }
        n = paramPlayCardViewBase.getTextOnlySnippetMarginLeft();
        i1 = paramPlayCardViewBase.getAvatarSnippetMarginLeft();
        String str4 = paramDocument.getSnippetHtml();
        if (TextUtils.isEmpty(str4)) {
          break label1433;
        }
        if (localPlayCardSnippet1 == null) {
          break label1426;
        }
        localObject3 = localPlayCardSnippet1;
        ((PlayCardSnippet)localObject3).setSnippetText(FastHtmlParser.fromHtml(str4), n, i1);
        ((FifeImageView)((PlayCardSnippet)localObject3).getImageView()).setVisibility(8);
        ((PlayCardSnippet)localObject3).setVisibility(0);
      }
      localImageView1 = paramPlayCardViewBase.getOverflow();
      if ((localImageView1 != null) && (paramDocument != null))
      {
        if ((!paramBoolean1) && (paramDocument.mDocument.docType != 3) && (paramDocument.mDocument.docType != 44)) {
          break label1626;
        }
        localImageView1.setVisibility(4);
        localImageView1.setOnClickListener(null);
      }
      localTextView2 = paramPlayCardViewBase.getAdLabel();
      if (localTextView2 != null)
      {
        if (!bool1) {
          break label1882;
        }
        if (!paramBoolean1) {
          break label1831;
        }
        localTextView2.setVisibility(4);
      }
      TextView localTextView3 = paramPlayCardViewBase.getAdCreative();
      if ((localTextView3 != null) && (bool1) && (!localResources1.getBoolean(2131427347)))
      {
        Template localTemplate1 = paramDocument.getTemplate();
        if ((localTemplate1 == null) || (localTemplate1.snow == null)) {
          break label1892;
        }
        localCharSequence1 = FastHtmlParser.fromHtml(localTemplate1.snow.snowText);
        localTextView3.setText(localCharSequence1);
        if (!TextUtils.isEmpty(localCharSequence1)) {
          break label1898;
        }
        i2 = 8;
        label1028:
        localTextView3.setVisibility(i2);
      }
      if (!paramBoolean1) {
        break label1904;
      }
      paramPlayCardViewBase.setDisplayAsDisabled(true);
      paramPlayCardViewBase.setOnClickListener(null);
      paramPlayCardViewBase.setClickable(false);
    }
    for (;;)
    {
      View localView = paramPlayCardViewBase.getLoadingIndicator();
      if (localView != null) {
        localView.setVisibility(8);
      }
      FinskyEventLog.setServerLogCookie(localCardUiElementNode2.getPlayStoreUiElement(), paramDocument.mDocument.serverLogsCookie);
      localCardUiElementNode2.getParentNode().childImpression(localCardUiElementNode2);
      paramPlayCardViewBase.setVisibility(0);
      return;
      paramPlayCardViewBase.setLoggingData(new CardUiElementNode(paramPlayCardViewBase.getCardType(), paramPlayStoreUiElementNode));
      break;
      label1127:
      str2 = null;
      break label177;
      label1133:
      Resources localResources2 = paramPlayCardViewBase.getResources();
      int i9 = CorpusResourceUtils.getTitleContentDescriptionResourceId(paramDocument.mDocument.docType);
      if (i9 < 0) {
        break label189;
      }
      TextView localTextView4 = paramPlayCardViewBase.getTitle();
      if (localTextView4 == null) {
        break label189;
      }
      localTextView4.setContentDescription(localResources2.getString(i9, new Object[] { str2 }));
      break label189;
      label1191:
      arrayOfInt = PlayCardImageTypeSequence.CORE_IMAGE_TYPES;
      break label239;
      label1199:
      i7 = 0;
      break label302;
      label1205:
      i8 = 0;
      break label342;
      label1211:
      sTransitionNameCoverBuilder.setLength(30);
      sTransitionNameCoverBuilder.append(paramDocument.mDocument.docid);
      sTransitionNameCoverBuilder.append(':');
      sTransitionNameCoverBuilder.append(paramString);
      localDocImageView.setTransitionName(sTransitionNameCoverBuilder.toString());
      break label429;
      label1264:
      localDocImageView.setTransitionName(null);
      paramPlayCardViewBase.setTransitionGroup(false);
      break label434;
      label1278:
      i = 0;
      break label535;
      label1284:
      j = 0;
      break label548;
      label1290:
      k = 0;
      break label567;
      label1296:
      localObject1 = str8;
      break label635;
      label1303:
      int m = paramDocument.mDocument.docType;
      localObject1 = null;
      str3 = null;
      if (m != 44) {
        break label635;
      }
      localObject1 = null;
      str3 = null;
      if (localPlayTextView1 == null) {
        break label635;
      }
      localObject1 = paramDocument.getSeriesComposition();
      str3 = null;
      k = 0;
      break label635;
      label1351:
      str7 = getDocDisplaySubtitle(paramDocument);
      break label673;
      label1360:
      localDecoratedTextView1.setVisibility(8);
      break label694;
      label1370:
      String str6 = paramDocument.mDocument.promotionalDescription;
      CharSequence localCharSequence2 = paramDocument.getDescription();
      if ((!TextUtils.isEmpty(str6)) && ((paramBoolean3) || (TextUtils.isEmpty(localCharSequence2))))
      {
        localObject4 = str6;
        break label716;
      }
      localObject4 = localCharSequence2;
      break label716;
      label1420:
      i5 = 0;
      break label735;
      label1426:
      localObject3 = localObject2;
      break label835;
      label1433:
      SuggestionReasons localSuggestionReasons = paramDocument.getSuggestionReasons();
      if ((localSuggestionReasons == null) || (localSuggestionReasons.reason.length == 0))
      {
        if ((localPlayCardSnippet1 == null) || (!paramDocument.hasOptimalDeviceClassWarning())) {
          break label868;
        }
        localPlayCardSnippet1.setSnippetText(paramDocument.getOptimalDeviceClassWarning().localizedMessage, n, i1);
        ((FifeImageView)localPlayCardSnippet1.getImageView()).setVisibility(8);
        localPlayCardSnippet1.setVisibility(0);
        break label868;
      }
      PlayStoreUiElementNode localPlayStoreUiElementNode = (PlayStoreUiElementNode)paramPlayCardViewBase.getLoggingData();
      if ((localSuggestionReasons.reason.length == 1) || (localObject2 == null) || (localPlayCardSnippet1 == null))
      {
        Reason localReason1 = findHighestPriorityReason(localSuggestionReasons, null);
        if (localPlayCardSnippet1 != null) {
          localObject2 = localPlayCardSnippet1;
        }
        bindReason((PlayCardSnippet)localObject2, localReason1, paramBitmapLoader, paramNavigationManager, n, i1, localPlayStoreUiElementNode);
        break label868;
      }
      localPlayCardSnippet1.setSeparatorVisible(true);
      Reason localReason2 = findHighestPriorityReason(localSuggestionReasons, null);
      Reason localReason3 = findHighestPriorityReason(localSuggestionReasons, localReason2);
      bindReason((PlayCardSnippet)localObject2, localReason2, paramBitmapLoader, paramNavigationManager, n, i1, localPlayStoreUiElementNode);
      bindReason(localPlayCardSnippet1, localReason3, paramBitmapLoader, paramNavigationManager, n, i1, localPlayStoreUiElementNode);
      break label868;
      label1626:
      localImageView1.setVisibility(0);
      Context localContext = localImageView1.getContext();
      label1658:
      PlayCardSnippet localPlayCardSnippet3;
      PlayCardSnippet localPlayCardSnippet4;
      if (paramDocument.isAdvertisement())
      {
        localImageView1.setOnTouchListener(new View.OnTouchListener()
        {
          public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
          {
            AdUtils.addTouchEventForAdShield(paramAnonymousView.getContext(), paramAnonymousMotionEvent);
            return false;
          }
        });
        localImageView1.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PlayPopupMenu localPlayPopupMenu = new PlayPopupMenu(this.val$context, localImageView1);
            Resources localResources = this.val$context.getResources();
            final FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
            localFinskyEventLog.logClickEvent(238, null, localCardUiElementNode2);
            final DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
            boolean bool1 = paramDocument.isPreregistration();
            int i;
            boolean bool2;
            label91:
            int i1;
            final int i2;
            label172:
            int m;
            final int n;
            label270:
            label412:
            Annotations localAnnotations;
            if (!WishlistHelper.shouldHideWishlistAction(paramDocument, localDfeApi))
            {
              i = 1;
              if ((bool1) || (i != 0)) {
                break label506;
              }
              bool2 = true;
              Account localAccount = localDfeApi.getAccount();
              PlayCardUtils.access$100$1fa26ddc(localPlayPopupMenu, this.val$context, paramDocument, paramNavigationManager, localCardUiElementNode2, bool2, paramPlayCardViewBase);
              if (bool1)
              {
                final PreregistrationHelper localPreregistrationHelper = FinskyApp.get().mPreregistrationHelper;
                final boolean bool4 = localPreregistrationHelper.isPreregistered(paramDocument.mDocument.docid, localAccount);
                if (!bool4) {
                  break label512;
                }
                i1 = 2131362598;
                i2 = 296;
                PlayPopupMenu.OnPopupActionSelectedListener local1 = new PlayPopupMenu.OnPopupActionSelectedListener()
                {
                  public final void onActionSelected(int paramAnonymous2Int)
                  {
                    localFinskyEventLog.logClickEvent(i2, null, PlayCardUtils.14.this.val$clickedNode);
                    PreregistrationHelper localPreregistrationHelper = localPreregistrationHelper;
                    Document localDocument = PlayCardUtils.14.this.val$doc;
                    DfeApi localDfeApi = localDfeApi;
                    if (!bool4) {}
                    for (boolean bool = true;; bool = false)
                    {
                      localPreregistrationHelper.processPreregistration(localDocument, localDfeApi, bool, this.val$fragmentManager, PlayCardUtils.14.this.val$context);
                      return;
                    }
                  }
                };
                localPlayPopupMenu.addMenuItem(0, localResources.getString(i1), true, local1);
                PlayPopupMenu.OnPopupActionSelectedListener local2 = new PlayPopupMenu.OnPopupActionSelectedListener()
                {
                  public final void onActionSelected(int paramAnonymous2Int)
                  {
                    IntentUtils.shareDocument(PlayCardUtils.14.this.val$context, PlayCardUtils.14.this.val$doc, PlayCardUtils.14.this.val$clickedNode);
                  }
                };
                localPlayPopupMenu.addMenuItem(0, localResources.getString(2131362753), true, local2);
              }
              if (i != 0)
              {
                final boolean bool3 = WishlistHelper.isInWishlist(paramDocument, localAccount);
                if (!bool3) {
                  break label524;
                }
                m = 2131362934;
                n = 205;
                PlayPopupMenu.OnPopupActionSelectedListener local3 = new PlayPopupMenu.OnPopupActionSelectedListener()
                {
                  public final void onActionSelected(int paramAnonymous2Int)
                  {
                    localFinskyEventLog.logClickEvent(n, null, PlayCardUtils.14.this.val$clickedNode);
                    if (!bool3) {
                      AdUtils.trackCardClick(PlayCardUtils.14.this.val$context, PlayCardUtils.14.this.val$doc, "24", PlayCardUtils.14.this.val$card.getWidth(), PlayCardUtils.14.this.val$card.getHeight());
                    }
                    WishlistHelper.processWishlistClick(PlayCardUtils.14.this.val$card, PlayCardUtils.14.this.val$doc, localDfeApi);
                  }
                };
                localPlayPopupMenu.addMenuItem(0, localResources.getString(m), true, local3);
              }
              if ((paramDocument.isAdvertisement()) && (PlayCardUtils.sBadAdReportingEnabled))
              {
                PlayPopupMenu.OnPopupActionSelectedListener local4 = new PlayPopupMenu.OnPopupActionSelectedListener()
                {
                  public final void onActionSelected(int paramAnonymous2Int)
                  {
                    Intent localIntent = IntentUtils.createSendIntentForUrl(Uri.fromParts("mailto", (String)G.emailToReportBadAd.get(), null));
                    localIntent.putExtra("android.intent.extra.SUBJECT", PlayCardUtils.14.this.val$doc.mDocument.title);
                    localIntent.putExtra("android.intent.extra.TEXT", PlayCardUtils.14.this.val$doc.getClickUrl());
                    try
                    {
                      PlayCardUtils.14.this.val$context.startActivity(localIntent);
                      return;
                    }
                    catch (ActivityNotFoundException localActivityNotFoundException)
                    {
                      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
                      localBuilder.setMessageId(2131362375).setPositiveId(2131362418).setCanceledOnTouchOutside(true);
                      localBuilder.build().show(((FragmentActivity)PlayCardUtils.14.this.val$context).getSupportFragmentManager(), "no_email_app_dialog");
                    }
                  }
                };
                localPlayPopupMenu.addMenuItem(0, localResources.getString(2131362659), true, local4);
              }
              if ((paramPlayCardDismissListener != null) && (paramDocument.isDismissable()))
              {
                String str = paramDocument.getNeutralDismissal().descriptionHtml;
                if (TextUtils.isEmpty(str)) {
                  break label536;
                }
                localPlayPopupMenu.addMenuItem(0, str, true, new PlayCardUtils.CardDismissalAction(paramPlayCardViewBase, paramDocument, localDfeApi, paramPlayCardDismissListener, localCardUiElementNode2));
              }
              localAnnotations = paramDocument.mDocument.annotations;
              if (localAnnotations == null) {
                break label566;
              }
            }
            label512:
            label524:
            label536:
            label566:
            for (OverflowLink[] arrayOfOverflowLink1 = localAnnotations.overflowLink;; arrayOfOverflowLink1 = null)
            {
              if ((arrayOfOverflowLink1 == null) || (arrayOfOverflowLink1.length <= 0)) {
                break label572;
              }
              OverflowLink[] arrayOfOverflowLink2 = arrayOfOverflowLink1;
              int j = arrayOfOverflowLink1.length;
              for (int k = 0; k < j; k++)
              {
                final OverflowLink localOverflowLink = arrayOfOverflowLink2[k];
                localPlayPopupMenu.addMenuItem(0, localOverflowLink.title, true, new PlayPopupMenu.OnPopupActionSelectedListener()
                {
                  public final void onActionSelected(int paramAnonymous2Int)
                  {
                    PlayCardUtils.14.this.val$navigationManager.resolveLink(localOverflowLink.link, FinskyApp.get().mToc, PlayCardUtils.14.this.val$context.getPackageManager());
                  }
                });
              }
              i = 0;
              break;
              label506:
              bool2 = false;
              break label91;
              i1 = 2131362590;
              i2 = 295;
              break label172;
              m = 2131362931;
              n = 204;
              break label270;
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = paramDocument.mDocument.docid;
              FinskyLog.wtf("Empty dismissal text received from the server for doc %s", arrayOfObject);
              break label412;
            }
            label572:
            localImageView1.setImageResource(2130838073);
            localPlayPopupMenu.mOnPopupDismissListener = new PopupWindow.OnDismissListener()
            {
              public final void onDismiss()
              {
                PlayCardUtils.14.this.val$overflowView.setImageResource(2130838071);
              }
            };
            localPlayPopupMenu.show();
          }
        });
        if (!localImageView1.isFocusable()) {
          break label923;
        }
        PlayCardSnippet localPlayCardSnippet2 = paramPlayCardViewBase.getSnippet1();
        localPlayCardSnippet3 = paramPlayCardViewBase.getSnippet2();
        if ((localPlayCardSnippet2 == null) || (localPlayCardSnippet2.getVisibility() != 0)) {
          break label1782;
        }
        localPlayCardSnippet4 = localPlayCardSnippet2;
        label1719:
        if (localPlayCardSnippet4 != null) {
          break label1812;
        }
      }
      label1812:
      for (ImageView localImageView2 = null;; localImageView2 = localPlayCardSnippet4.getImageView())
      {
        if ((localPlayCardSnippet4 == null) || (localImageView2.getVisibility() != 0)) {
          break label1822;
        }
        localImageView1.setNextFocusDownId(localImageView2.getId());
        int i4 = localImageView1.getId();
        localImageView2.setNextFocusUpId(i4);
        localImageView2.setFocusable(true);
        break;
        localImageView1.setOnTouchListener(null);
        break label1658;
        label1782:
        localPlayCardSnippet4 = null;
        if (localPlayCardSnippet3 == null) {
          break label1719;
        }
        int i3 = localPlayCardSnippet3.getVisibility();
        localPlayCardSnippet4 = null;
        if (i3 != 0) {
          break label1719;
        }
        localPlayCardSnippet4 = localPlayCardSnippet3;
        break label1719;
      }
      label1822:
      localImageView1.setNextFocusDownId(-1);
      break label923;
      label1831:
      localTextView2.setVisibility(0);
      Template localTemplate2 = paramDocument.getTemplate();
      if ((localTemplate2 != null) && (localTemplate2.snow != null)) {}
      for (String str5 = localTemplate2.snow.snowBadgeText;; str5 = null)
      {
        localTextView2.setText(str5);
        break;
      }
      label1882:
      localTextView2.setVisibility(8);
      break label950;
      label1892:
      localCharSequence1 = null;
      break label1009;
      label1898:
      i2 = 0;
      break label1028;
      label1904:
      paramPlayCardViewBase.setDisplayAsDisabled(false);
      if (paramNavigationManager != null)
      {
        paramPlayCardViewBase.setOnClickListener(paramNavigationManager.getClickListener(paramDocument, localCardUiElementNode2, null, -1, localDocImageView));
        if (bool1) {
          paramPlayCardViewBase.setOnTouchListener(new View.OnTouchListener()
          {
            public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
            {
              AdUtils.addTouchEventForAdShield(paramAnonymousView.getContext(), paramAnonymousMotionEvent);
              return false;
            }
          });
        } else {
          paramPlayCardViewBase.setOnTouchListener(null);
        }
      }
    }
  }
  
  private static void bindReason(PlayCardSnippet paramPlayCardSnippet, Reason paramReason, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, int paramInt1, int paramInt2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (paramReason == null) {
      return;
    }
    paramPlayCardSnippet.setVisibility(0);
    FifeImageView localFifeImageView = (FifeImageView)paramPlayCardSnippet.getImageView();
    Resources localResources = paramPlayCardSnippet.getResources();
    ReasonReview localReasonReview = paramReason.reasonReview;
    if (localReasonReview != null)
    {
      Review localReview = localReasonReview.review;
      String str1;
      int j;
      label72:
      int k;
      int m;
      label93:
      int n;
      label107:
      String str3;
      label191:
      DocV2 localDocV25;
      if (localReview.author != null)
      {
        str1 = localReview.author.title;
        if (TextUtils.isEmpty(str1)) {
          break label268;
        }
        j = 1;
        k = localReview.starRating;
        if (TextUtils.isEmpty(localReview.title)) {
          break label274;
        }
        m = 1;
        if (TextUtils.isEmpty(localReview.comment)) {
          break label280;
        }
        n = 1;
        if (j == 0) {
          break label400;
        }
        if ((n == 0) || (m == 0) || (localReview.comment.indexOf(localReview.title) == 0)) {
          break label286;
        }
        Object[] arrayOfObject7 = new Object[4];
        arrayOfObject7[0] = str1;
        arrayOfObject7[1] = Integer.valueOf(k);
        arrayOfObject7[2] = localReview.title;
        arrayOfObject7[3] = localReview.comment;
        str3 = localResources.getQuantityString(2131296269, k, arrayOfObject7);
        paramPlayCardSnippet.setSnippetText(Html.fromHtml(str3), paramInt1, paramInt2);
        localDocV25 = localReview.author;
        if (localDocV25 == null) {
          break label502;
        }
      }
      label268:
      label274:
      label280:
      label286:
      label502:
      for (Common.Image localImage4 = DocV2Utils.getFirstImageOfType(localDocV25, 4);; localImage4 = null)
      {
        if (localImage4 == null) {
          break label508;
        }
        localFifeImageView.setImage(localImage4.imageUrl, localImage4.supportsFifeUrlOptions, paramBitmapLoader);
        bindReasonUser(localFifeImageView, localDocV25, paramNavigationManager, paramPlayStoreUiElementNode);
        localFifeImageView.setVisibility(0);
        return;
        str1 = null;
        break;
        j = 0;
        break label72;
        m = 0;
        break label93;
        n = 0;
        break label107;
        if ((n != 0) || (m != 0))
        {
          Object[] arrayOfObject5 = new Object[3];
          arrayOfObject5[0] = str1;
          arrayOfObject5[1] = Integer.valueOf(k);
          if (n != 0) {}
          for (String str4 = localReview.comment;; str4 = localReview.title)
          {
            arrayOfObject5[2] = str4;
            str3 = localResources.getQuantityString(2131296268, k, arrayOfObject5);
            break;
          }
        }
        Object[] arrayOfObject6 = new Object[2];
        arrayOfObject6[0] = str1;
        arrayOfObject6[1] = Integer.valueOf(k);
        str3 = localResources.getQuantityString(2131296267, k, arrayOfObject6);
        break label191;
        if ((n != 0) || (m != 0))
        {
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = Integer.valueOf(k);
          if (n != 0) {}
          for (String str2 = localReview.comment;; str2 = localReview.title)
          {
            arrayOfObject3[1] = str2;
            str3 = localResources.getQuantityString(2131296274, k, arrayOfObject3);
            break;
          }
        }
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(k);
        str3 = localResources.getQuantityString(2131296272, k, arrayOfObject4);
        break label191;
      }
      label400:
      label508:
      localFifeImageView.setVisibility(8);
      return;
    }
    if (paramReason.reasonPlusOne != null)
    {
      ReasonPlusOne localReasonPlusOne = paramReason.reasonPlusOne;
      int i = localReasonPlusOne.person.length;
      if (i > 1)
      {
        DocV2 localDocV23 = localReasonPlusOne.person[0];
        DocV2 localDocV24 = localReasonPlusOne.person[1];
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = localDocV23.title;
        arrayOfObject2[1] = localDocV24.title;
        paramPlayCardSnippet.setSnippetText(Html.fromHtml(localResources.getString(2131362584, arrayOfObject2)), paramInt1, paramInt2);
        Common.Image localImage3 = DocV2Utils.getFirstImageOfType(localDocV23, 4);
        localFifeImageView.setImage(localImage3.imageUrl, localImage3.supportsFifeUrlOptions, paramBitmapLoader);
        bindReasonUser(localFifeImageView, localDocV23, paramNavigationManager, paramPlayStoreUiElementNode);
        localFifeImageView.setVisibility(0);
        return;
      }
      if (i == 1)
      {
        DocV2 localDocV22 = localReasonPlusOne.person[0];
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localDocV22.title;
        paramPlayCardSnippet.setSnippetText(Html.fromHtml(localResources.getString(2131362585, arrayOfObject1)), paramInt1, paramInt2);
        Common.Image localImage2 = DocV2Utils.getFirstImageOfType(localDocV22, 4);
        localFifeImageView.setImage(localImage2.imageUrl, localImage2.supportsFifeUrlOptions, paramBitmapLoader);
        bindReasonUser(localFifeImageView, localDocV22, paramNavigationManager, paramPlayStoreUiElementNode);
        localFifeImageView.setVisibility(0);
        return;
      }
      FinskyLog.e("Server returned plus profile reason with no profiles", new Object[0]);
      return;
    }
    if (paramReason.reasonUserAction != null)
    {
      ReasonUserAction localReasonUserAction = paramReason.reasonUserAction;
      paramPlayCardSnippet.setSnippetText(FastHtmlParser.fromHtml(localReasonUserAction.localizedDescriptionHtml), paramInt1, paramInt2);
      DocV2 localDocV21 = localReasonUserAction.person;
      Common.Image localImage1 = DocV2Utils.getFirstImageOfType(localDocV21, 4);
      localFifeImageView.setImage(localImage1.imageUrl, localImage1.supportsFifeUrlOptions, paramBitmapLoader);
      localFifeImageView.setVisibility(0);
      bindReasonUser(localFifeImageView, localDocV21, paramNavigationManager, paramPlayStoreUiElementNode);
      return;
    }
    paramPlayCardSnippet.setSnippetText(FastHtmlParser.fromHtml(paramReason.descriptionHtml), paramInt1, paramInt2);
    localFifeImageView.setVisibility(8);
  }
  
  private static void bindReasonUser(FifeImageView paramFifeImageView, final DocV2 paramDocV2, NavigationManager paramNavigationManager, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    paramFifeImageView.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if ((this.val$navigationManager != null) && (paramDocV2.hasDetailsUrl))
        {
          Document localDocument = new Document(paramDocV2);
          GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(279, localDocument.mDocument.serverLogsCookie, null, paramPlayStoreUiElementNode);
          this.val$navigationManager.getClickListener(localDocument, localGenericUiElementNode).onClick(paramAnonymousView);
        }
      }
    });
    Resources localResources = paramFifeImageView.getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramDocV2.title;
    paramFifeImageView.setContentDescription(localResources.getString(2131362036, arrayOfObject));
  }
  
  public static void dismissCard(final PlayCardViewBase paramPlayCardViewBase, final Document paramDocument, DfeApi paramDfeApi, PlayCardDismissListener paramPlayCardDismissListener, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyApp.get().getEventLogger().logClickEvent(212, null, paramPlayStoreUiElementNode);
    paramDfeApi.rateSuggestedContent(paramDocument.getNeutralDismissal().url, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = this.val$doc.mDocument.docid;
        arrayOfObject[1] = paramAnonymousVolleyError;
        FinskyLog.d("Volley error while dismissing %s: %s", arrayOfObject);
      }
    });
  }
  
  public static Reason findHighestPriorityReason(SuggestionReasons paramSuggestionReasons)
  {
    return findHighestPriorityReason(paramSuggestionReasons, null);
  }
  
  private static Reason findHighestPriorityReason(SuggestionReasons paramSuggestionReasons, Reason paramReason)
  {
    Object localObject;
    if (paramSuggestionReasons == null) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = null;
      for (int i = 0; i < paramSuggestionReasons.reason.length; i++)
      {
        Reason localReason = paramSuggestionReasons.reason[i];
        if (localReason != paramReason)
        {
          if (localReason.reasonReview != null) {
            return localReason;
          }
          if ((localReason.reasonPlusOne != null) || ((localReason.descriptionHtml.length() > 0) && ((localObject == null) || (((Reason)localObject).reasonPlusOne == null)))) {
            localObject = localReason;
          }
        }
      }
    }
  }
  
  public static PlayStoreUiElementNode getCardParentNode(PlayCardViewBase paramPlayCardViewBase)
  {
    CardUiElementNode localCardUiElementNode = (CardUiElementNode)paramPlayCardViewBase.getLoggingData();
    if (localCardUiElementNode == null) {
      return null;
    }
    return localCardUiElementNode.getParentNode();
  }
  
  public static String getDocDisplaySubtitle(Document paramDocument)
  {
    switch (paramDocument.mDocument.docType)
    {
    default: 
      return null;
    case 1: 
    case 2: 
    case 4: 
    case 5: 
    case 6: 
    case 18: 
    case 19: 
    case 20: 
      return paramDocument.mDocument.creator;
    case 16: 
    case 17: 
    case 24: 
    case 25: 
    case 28: 
      return paramDocument.mDocument.subtitle;
    }
    return paramDocument.getSeriesComposition();
  }
  
  public static void initializeCardTrackers()
  {
    PlayCardLabelTracker.initializeCardTrackers();
    PlayCardCustomizerRepository localPlayCardCustomizerRepository = PlayCardCustomizerRepository.getInstance();
    PlayCardCustomizer local1 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[0] = local1;
    PlayCardCustomizer local2 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[1] = local2;
    PlayCardCustomizer local3 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[10] = local3;
    PlayCardCustomizer local4 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[15] = local4;
    PlayCardCustomizer local5 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[20] = local5;
    PlayCardCustomizer local6 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[19] = local6;
    PlayCardCustomizer local7 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[9] = local7;
    PlayCardCustomizer local8 = new PlayCardCustomizer() {};
    localPlayCardCustomizerRepository.mCustomizers[4] = local8;
  }
  
  public static void recycleLoggingData(PlayCardViewBase paramPlayCardViewBase)
  {
    CardUiElementNode localCardUiElementNode = (CardUiElementNode)paramPlayCardViewBase.getLoggingData();
    if (localCardUiElementNode != null)
    {
      localCardUiElementNode.mUiElementProto = null;
      localCardUiElementNode.mParentNode = null;
    }
  }
  
  private static void updateCachedExperimentsValues()
  {
    label103:
    for (;;)
    {
      try
      {
        FinskyApp localFinskyApp = FinskyApp.get();
        String str = localFinskyApp.getCurrentAccountName();
        if ((sCachedExperimentAccountName == null) || (!sCachedExperimentAccountName.equals(str)))
        {
          sHideSalesPricesExperimentEnabled = localFinskyApp.getExperiments().isEnabled(12603136L);
          if (localFinskyApp.getExperiments().isEnabled(12603329L)) {
            break label103;
          }
          if (GooglePlayServicesUtil.isSidewinderDevice(localFinskyApp))
          {
            break label103;
            sDisplayAppSizeEnabled = bool;
            sBadAdReportingEnabled = localFinskyApp.getExperiments().isEnabled(12603517L);
            sCachedExperimentAccountName = str;
            PlayCardLabelTracker.clearAllEntries();
          }
        }
        else
        {
          return;
        }
        boolean bool = false;
        continue;
        bool = true;
      }
      finally {}
    }
  }
  
  static void updateCardLabel(Document paramDocument, PlayCardViewBase paramPlayCardViewBase)
  {
    PlayCardLabelView localPlayCardLabelView;
    int i;
    PlayCardLabelTracker.PlayCardLabelInfo localPlayCardLabelInfo;
    label230:
    int j;
    for (;;)
    {
      PurchaseButtonHelper.DocumentActions localDocumentActions;
      PurchaseButtonHelper.TextStyle localTextStyle1;
      try
      {
        localPlayCardLabelView = paramPlayCardViewBase.getLabel();
        if ((paramDocument == null) || (localPlayCardLabelView == null)) {
          return;
        }
        i = paramPlayCardViewBase.getOwnershipRenderingType();
        String str1 = paramDocument.mDocument.docid;
        localPlayCardLabelInfo = PlayCardLabelTracker.getLabelInfoFor(str1);
        if (localPlayCardLabelInfo != null) {
          break;
        }
        FinskyApp localFinskyApp = FinskyApp.get();
        PurchaseButtonHelper.getDocumentActions(localFinskyApp.getCurrentAccount(), localFinskyApp.mInstaller, localFinskyApp.mLibraries, localFinskyApp.mAppStates, localFinskyApp.mToc, 1, paramDocument, sDocumentActions);
        localDocumentActions = sDocumentActions;
        localTextStyle1 = sListingStyle;
        localTextStyle1.reset();
        if (!localDocumentActions.hasStatus()) {
          break label475;
        }
        switch (localDocumentActions.status)
        {
        default: 
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(localDocumentActions.status);
          FinskyLog.wtf("Unrecognized status %d", arrayOfObject2);
          localTextStyle2 = sListingStyle;
          bool = Boolean.valueOf(sDocumentActions.displayAsOwned).booleanValue();
          PlayCardLabelTracker.setLabelInfoFor(str1, sListingStyle, bool);
        }
      }
      finally {}
      paramPlayCardViewBase.setItemOwned(bool);
      localPlayCardLabelView.setVisibility(8);
      if ((!bool) || (j == 0)) {
        break label697;
      }
      localPlayCardLabelView.setVisibility(0);
      int n = paramDocument.mDocument.backendId;
      switch (n)
      {
      case 5: 
      default: 
        throw new IllegalArgumentException("Unsupported backend ID (" + n + ")");
        localTextStyle1.resourceId = 2131362592;
        continue;
        localTextStyle1.resourceId = 2131362597;
        continue;
        localTextStyle1.resourceId = 2131362250;
        continue;
        localTextStyle1.resourceId = 2131362821;
        continue;
        localTextStyle1.resourceId = 2131362096;
        continue;
        localTextStyle1.resourceId = 2131362589;
        continue;
        localTextStyle1.offerFullText = localDocumentActions.listingOfferFullText;
        localTextStyle1.offerText = localDocumentActions.listingOfferText;
        continue;
        localTextStyle1.resourceId = 2131362820;
        continue;
        localTextStyle1.resourceId = 2131362617;
        continue;
        localTextStyle1.resourceId = 2131362658;
        continue;
        localTextStyle1.resourceId = 2131362771;
        continue;
        label475:
        if ((localDocumentActions.displayAsOwned) && (localDocumentActions.backend == 3)) {
          localTextStyle1.resourceId = 2131362248;
        }
        break;
      }
    }
    PurchaseButtonHelper.TextStyle localTextStyle2 = localPlayCardLabelInfo.textStyle;
    boolean bool = localPlayCardLabelInfo.displayAsOwned;
    break label777;
    int i1;
    if (CorpusResourceUtils.isEnterpriseTheme())
    {
      i1 = 2130837686;
      label531:
      localPlayCardLabelView.setIcon(i1);
    }
    for (;;)
    {
      if (m != 0)
      {
        localPlayCardLabelView.setVisibility(0);
        updateCachedExperimentsValues();
        if ((!TextUtils.isEmpty(localTextStyle2.offerFullText)) && (!sHideSalesPricesExperimentEnabled))
        {
          Resources localResources = localPlayCardLabelView.getResources();
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localTextStyle2.offerFullText;
          arrayOfObject1[1] = localTextStyle2.offerText;
          String str4 = localResources.getString(2131361996, arrayOfObject1);
          localPlayCardLabelView.setText(localTextStyle2.offerText, localTextStyle2.offerFullText, paramDocument.mDocument.backendId, str4);
        }
      }
      while ((localPlayCardLabelView.getVisibility() == 0) && (bool) && (j != 0) && (k == 0))
      {
        String str3 = localTextStyle2.getString(localPlayCardLabelView.getContext());
        if (TextUtils.isEmpty(str3)) {
          str3 = localPlayCardLabelView.getResources().getString(2131362617);
        }
        localPlayCardLabelView.setContentDescription(str3);
        break;
        label697:
        localPlayCardLabelView.mIcon = null;
        localPlayCardLabelView.invalidate();
        localPlayCardLabelView.requestLayout();
        break label812;
        String str2 = localTextStyle2.getString(localPlayCardLabelView.getContext());
        localPlayCardLabelView.setText(str2, paramDocument.mDocument.backendId);
        if (TextUtils.isEmpty(str2))
        {
          localPlayCardLabelView.setContentDescription(localPlayCardLabelView.getResources().getString(2131362617));
          continue;
          localPlayCardLabelView.setText(null, paramDocument.mDocument.backendId);
        }
      }
      label777:
      if ((i & 0x1) != 0) {}
      for (j = 1;; j = 0)
      {
        if ((i & 0x2) == 0) {
          break label806;
        }
        k = 1;
        break;
      }
      label806:
      int k = 0;
      break label230;
      label812:
      if ((!bool) || (k != 0))
      {
        m = 1;
        continue;
        i1 = 2130837685;
        break label531;
        i1 = 2130837687;
        break label531;
        i1 = 2130837690;
        break label531;
        i1 = 2130837688;
        break label531;
        i1 = 2130837689;
        break label531;
      }
      int m = 0;
    }
  }
  
  private static final class CardDismissalAction
    implements PlayPopupMenu.OnPopupActionSelectedListener
  {
    private final PlayCardViewBase mCard;
    private final PlayStoreUiElementNode mClickedNode;
    private final DfeApi mDfeApi;
    private final PlayCardDismissListener mDismissListener;
    private final Document mDoc;
    
    public CardDismissalAction(PlayCardViewBase paramPlayCardViewBase, Document paramDocument, DfeApi paramDfeApi, PlayCardDismissListener paramPlayCardDismissListener, PlayStoreUiElementNode paramPlayStoreUiElementNode)
    {
      this.mCard = paramPlayCardViewBase;
      this.mDoc = paramDocument;
      this.mDfeApi = paramDfeApi;
      this.mDismissListener = paramPlayCardDismissListener;
      this.mClickedNode = paramPlayStoreUiElementNode;
    }
    
    public final void onActionSelected(int paramInt)
    {
      PlayCardUtils.dismissCard(this.mCard, this.mDoc, this.mDfeApi, this.mDismissListener, this.mClickedNode);
    }
  }
  
  private static final class CardUiElementNode
    implements PlayStoreUiElementNode
  {
    PlayStoreUiElementNode mParentNode;
    PlayStore.PlayStoreUiElement mUiElementProto;
    
    public CardUiElementNode(int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode)
    {
      initNode(paramInt, paramPlayStoreUiElementNode);
    }
    
    public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
    {
      throw new IllegalStateException("unwanted children");
    }
    
    public final PlayStoreUiElementNode getParentNode()
    {
      return this.mParentNode;
    }
    
    public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
    {
      return this.mUiElementProto;
    }
    
    public final void initNode(int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode)
    {
      this.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(PlayCardUtils.access$300(paramInt));
      this.mParentNode = paramPlayStoreUiElementNode;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PlayCardUtils
 * JD-Core Version:    0.7.0.1
 */