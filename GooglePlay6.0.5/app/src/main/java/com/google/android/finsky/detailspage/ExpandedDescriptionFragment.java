package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.layout.DetailsExpandedContainer;
import com.google.android.finsky.layout.DetailsExpandedContainer.1;
import com.google.android.finsky.layout.DetailsExpandedExtraCreditsView;
import com.google.android.finsky.layout.DetailsExpandedExtraPrimaryView;
import com.google.android.finsky.layout.DetailsExpandedExtraSecondaryView;
import com.google.android.finsky.layout.DetailsTextBlock;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.SeparatorLinearLayout;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public final class ExpandedDescriptionFragment
  extends PageFragment
{
  private TextModule.ExpandedData mExpandedData;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(0);
  
  public static ExpandedDescriptionFragment newInstance(TextModule.ExpandedData paramExpandedData, DfeToc paramDfeToc)
  {
    ExpandedDescriptionFragment localExpandedDescriptionFragment = new ExpandedDescriptionFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("expanded_data", paramExpandedData);
    localExpandedDescriptionFragment.setArguments(localBundle);
    localExpandedDescriptionFragment.setArgument("finsky.PageFragment.toc", paramDfeToc);
    return localExpandedDescriptionFragment;
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new LayoutSwitcher(paramContentFrame, 2131755586, 2131755806, 2131755289, this, 2);
  }
  
  public final int getActionBarTitleColor()
  {
    return this.mContext.getResources().getColor(2131689625);
  }
  
  protected final Transition getCustomExitTransition()
  {
    return null;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968734;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mExpandedData = ((TextModule.ExpandedData)this.mArguments.getParcelable("expanded_data"));
    rebindActionBar();
    rebindViews();
    this.mActionBarController.disableActionBarOverlay();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
  }
  
  public final void onDestroyView()
  {
    this.mActionBarController.exitActionBarSectionExpandedMode();
    super.onDestroyView();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mExpandedData.title);
    this.mPageFragmentHost.updateCurrentBackendId(this.mExpandedData.backend, false);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews()
  {
    DetailsExpandedContainer localDetailsExpandedContainer = (DetailsExpandedContainer)this.mView.findViewById(2131755580);
    TextModule.ExpandedData localExpandedData = this.mExpandedData;
    NavigationManager localNavigationManager = this.mNavigationManager;
    int i = this.mExpandedData.backend;
    localDetailsExpandedContainer.mOriginalBody = localExpandedData.body;
    localDetailsExpandedContainer.mTranslatedBody = localExpandedData.translatedBody;
    DetailsTextBlock localDetailsTextBlock2;
    DetailsTextBlock localDetailsTextBlock1;
    label130:
    label195:
    CharSequence localCharSequence;
    label224:
    LayoutInflater localLayoutInflater;
    String str2;
    List localList1;
    int j;
    BitmapLoader localBitmapLoader;
    List localList2;
    int k;
    int m;
    int n;
    boolean[] arrayOfBoolean;
    if (!TextUtils.isEmpty(localExpandedData.callout))
    {
      localDetailsExpandedContainer.mDetailsExpandedCallout.setText(localExpandedData.callout);
      localDetailsExpandedContainer.mDetailsExpandedCallout.setGravity(localExpandedData.calloutGravity);
      ViewCompat.setPaddingRelative(localDetailsExpandedContainer.mDetailsExpandedCallout, ViewCompat.getPaddingStart(localDetailsExpandedContainer.mDetailsExpandedCallout), 0, ViewCompat.getPaddingEnd(localDetailsExpandedContainer.mDetailsExpandedCallout), 0);
      localDetailsExpandedContainer.mDetailsExpandedCallout.setVisibility(0);
      if (!localExpandedData.promoteWhatsNew) {
        break label439;
      }
      localDetailsTextBlock2 = localDetailsExpandedContainer.mDetailsExpandedBody1;
      localDetailsTextBlock1 = localDetailsExpandedContainer.mDetailsExpandedBody2;
      localDetailsExpandedContainer.mDetailsBodyForTranslation = localDetailsTextBlock1;
      localDetailsTextBlock2.bind(localExpandedData.whatsNewHeader, localExpandedData.whatsNew, 2147483647);
      if (!localDetailsTextBlock2.hasBody()) {
        break label454;
      }
      int i27 = localDetailsExpandedContainer.getResources().getDimensionPixelSize(2131493292);
      localDetailsTextBlock2.setCorpusStyle(localExpandedData.backend, i27 / 2, i27 * 3 / 2);
      localDetailsTextBlock2.setVisibility(0);
      String str1 = localExpandedData.bodyHeader;
      if ((!localDetailsExpandedContainer.mIsShowingTranslation) || (TextUtils.isEmpty(localDetailsExpandedContainer.mTranslatedBody))) {
        break label464;
      }
      localCharSequence = localDetailsExpandedContainer.mTranslatedBody;
      localDetailsTextBlock1.bind(str1, localCharSequence, 2147483647);
      localDetailsTextBlock1.removeCorpusStyle();
      ViewCompat.setPaddingRelative(localDetailsTextBlock1, ViewCompat.getPaddingStart(localDetailsTextBlock1), 0, ViewCompat.getPaddingEnd(localDetailsTextBlock1), 0);
      localDetailsExpandedContainer.mDetailsExpandedExtras.removeAllViews();
      localLayoutInflater = LayoutInflater.from(localDetailsExpandedContainer.getContext());
      str2 = localExpandedData.extraCreditsHeader;
      localList1 = localExpandedData.extraCreditsList;
      j = localList1.size();
      if ((!TextUtils.isEmpty(str2)) && (j != 0)) {
        break label473;
      }
      localBitmapLoader = FinskyApp.get().mBitmapLoader;
      localList2 = localExpandedData.extraPrimaryList;
      k = localList2.size();
      m = localDetailsExpandedContainer.getResources().getInteger(2131623945);
      n = (-1 + (k + m)) / m;
      arrayOfBoolean = new boolean[m];
    }
    for (int i1 = 0;; i1++)
    {
      if (i1 >= n) {
        break label644;
      }
      int i24 = 0;
      for (;;)
      {
        if (i24 < m)
        {
          int i25 = i24 + m * i1;
          if (i25 < k)
          {
            if (((TextModule.DetailsExtraPrimary)localList2.get(i25)).image != null) {
              arrayOfBoolean[i24] = true;
            }
            i24++;
            continue;
            localDetailsExpandedContainer.mDetailsExpandedCallout.setVisibility(8);
            break;
            label439:
            localDetailsTextBlock1 = localDetailsExpandedContainer.mDetailsExpandedBody1;
            localDetailsTextBlock2 = localDetailsExpandedContainer.mDetailsExpandedBody2;
            break label130;
            label454:
            localDetailsTextBlock2.setVisibility(8);
            break label195;
            label464:
            localCharSequence = localDetailsExpandedContainer.mOriginalBody;
            break label224;
            label473:
            ViewGroup localViewGroup = (ViewGroup)localLayoutInflater.inflate(2130968690, localDetailsExpandedContainer.mDetailsExpandedExtras, false);
            ((DecoratedTextView)localViewGroup.findViewById(2131755390)).setText(str2.toUpperCase());
            localDetailsExpandedContainer.mDetailsExpandedExtras.addView(localViewGroup);
            int i26 = 0;
            label522:
            DetailsExpandedExtraCreditsView localDetailsExpandedExtraCreditsView;
            TextModule.DetailsExtraCredits localDetailsExtraCredits;
            if (i26 < j)
            {
              localDetailsExpandedExtraCreditsView = (DetailsExpandedExtraCreditsView)localLayoutInflater.inflate(2130968703, localDetailsExpandedContainer.mDetailsExpandedExtras, false);
              localDetailsExtraCredits = (TextModule.DetailsExtraCredits)localList1.get(i26);
              localDetailsExpandedExtraCreditsView.mCredit.setText(localDetailsExtraCredits.credit);
              if (!TextUtils.isEmpty(localDetailsExtraCredits.names)) {
                break label610;
              }
              localDetailsExpandedExtraCreditsView.mNames.setVisibility(8);
            }
            for (;;)
            {
              localDetailsExpandedContainer.mDetailsExpandedExtras.addView(localDetailsExpandedExtraCreditsView);
              i26++;
              break label522;
              break;
              label610:
              localDetailsExpandedExtraCreditsView.mNames.setVisibility(0);
              localDetailsExpandedExtraCreditsView.mNames.setText(Html.fromHtml(localDetailsExtraCredits.names));
            }
          }
        }
      }
    }
    label644:
    for (int i2 = 0; i2 < n; i2++)
    {
      SeparatorLinearLayout localSeparatorLinearLayout3 = (SeparatorLinearLayout)localLayoutInflater.inflate(2130968705, localDetailsExpandedContainer.mDetailsExpandedExtras, false);
      int i15 = 0;
      if (i15 < m)
      {
        int i16 = i15 + m * i2;
        DetailsExpandedExtraPrimaryView localDetailsExpandedExtraPrimaryView = (DetailsExpandedExtraPrimaryView)localLayoutInflater.inflate(2130968704, localSeparatorLinearLayout3, false);
        label721:
        int i18;
        label729:
        int i19;
        label741:
        int i20;
        int i21;
        int i22;
        if (i16 >= k)
        {
          localDetailsExpandedExtraPrimaryView.setVisibility(4);
          if (i2 != 0) {
            break label1049;
          }
          i18 = 1;
          if (i2 != n - 1) {
            break label1055;
          }
          i19 = 1;
          if (i18 == 0) {
            localSeparatorLinearLayout3.hideSeparator();
          }
          i20 = ViewCompat.getPaddingStart(localSeparatorLinearLayout3);
          if (i18 == 0) {
            break label1061;
          }
          i21 = localSeparatorLinearLayout3.getPaddingTop();
          i22 = ViewCompat.getPaddingEnd(localSeparatorLinearLayout3);
          if (i19 == 0) {
            break label1067;
          }
        }
        label1034:
        label1049:
        label1055:
        label1061:
        label1067:
        for (int i23 = localSeparatorLinearLayout3.getPaddingBottom();; i23 = 0)
        {
          ViewCompat.setPaddingRelative(localSeparatorLinearLayout3, i20, i21, i22, i23);
          localSeparatorLinearLayout3.addView(localDetailsExpandedExtraPrimaryView);
          i15++;
          break;
          TextModule.DetailsExtraPrimary localDetailsExtraPrimary = (TextModule.DetailsExtraPrimary)localList2.get(i16);
          Object localObject;
          if (TextUtils.isEmpty(localDetailsExtraPrimary.url))
          {
            localObject = null;
            label843:
            int i17 = arrayOfBoolean[i15];
            if (localDetailsExtraPrimary.image != null) {
              break label955;
            }
            if (i17 == 0) {
              break label942;
            }
            localDetailsExpandedExtraPrimaryView.mThumbnail.setVisibility(4);
            label872:
            localDetailsExpandedExtraPrimaryView.mTitle.setText(localDetailsExtraPrimary.title);
            if (!TextUtils.isEmpty(localDetailsExtraPrimary.description)) {
              break label1006;
            }
            localDetailsExpandedExtraPrimaryView.mDescription.setVisibility(8);
          }
          for (;;)
          {
            if (localObject == null) {
              break label1034;
            }
            localDetailsExpandedExtraPrimaryView.setOnClickListener((View.OnClickListener)localObject);
            break;
            localObject = new DetailsExpandedContainer.1(localDetailsExpandedContainer, null, -1, localNavigationManager, localDetailsExtraPrimary, i, this);
            break label843;
            label942:
            localDetailsExpandedExtraPrimaryView.mThumbnail.setVisibility(8);
            break label872;
            label955:
            ThumbnailUtils.adjustWidthFromImageMetadata(localDetailsExpandedExtraPrimaryView.mThumbnail, localDetailsExtraPrimary.image);
            localDetailsExpandedExtraPrimaryView.mThumbnail.setVisibility(0);
            localDetailsExpandedExtraPrimaryView.mThumbnail.setImage(localDetailsExtraPrimary.image.imageUrl, localDetailsExtraPrimary.image.supportsFifeUrlOptions, localBitmapLoader);
            break label872;
            label1006:
            localDetailsExpandedExtraPrimaryView.mDescription.setVisibility(0);
            localDetailsExpandedExtraPrimaryView.mDescription.setText(Html.fromHtml(localDetailsExtraPrimary.description));
          }
          localDetailsExpandedExtraPrimaryView.setOnClickListener(null);
          localDetailsExpandedExtraPrimaryView.setClickable(false);
          break label721;
          i18 = 0;
          break label729;
          i19 = 0;
          break label741;
          i21 = 0;
          break label770;
        }
      }
      localDetailsExpandedContainer.mDetailsExpandedExtras.addView(localSeparatorLinearLayout3);
    }
    label770:
    List localList3 = localExpandedData.extraSecondaryList;
    int i3 = localList3.size();
    int i4 = localDetailsExpandedContainer.getResources().getInteger(2131623946);
    int i5 = (-1 + (i3 + i4)) / i4;
    int i6 = 0;
    if (i6 < i5)
    {
      SeparatorLinearLayout localSeparatorLinearLayout2 = (SeparatorLinearLayout)localLayoutInflater.inflate(2130968705, localDetailsExpandedContainer.mDetailsExpandedExtras, false);
      int i7 = 0;
      if (i7 < i4)
      {
        int i14 = i7 + i4 * i6;
        DetailsExpandedExtraSecondaryView localDetailsExpandedExtraSecondaryView = (DetailsExpandedExtraSecondaryView)localLayoutInflater.inflate(2130968706, localSeparatorLinearLayout2, false);
        if (i14 >= i3) {
          localDetailsExpandedExtraSecondaryView.setVisibility(4);
        }
        for (;;)
        {
          localSeparatorLinearLayout2.addView(localDetailsExpandedExtraSecondaryView);
          i7++;
          break;
          TextModule.DetailsExtraSecondary localDetailsExtraSecondary = (TextModule.DetailsExtraSecondary)localList3.get(i14);
          localDetailsExpandedExtraSecondaryView.mTitle.setText(localDetailsExtraSecondary.title);
          if (TextUtils.isEmpty(localDetailsExtraSecondary.description))
          {
            localDetailsExpandedExtraSecondaryView.mDescription.setVisibility(8);
          }
          else
          {
            localDetailsExpandedExtraSecondaryView.mDescription.setVisibility(0);
            localDetailsExpandedExtraSecondaryView.mDescription.setText(Html.fromHtml(localDetailsExtraSecondary.description));
          }
        }
      }
      int i8;
      label1304:
      int i9;
      label1316:
      int i10;
      int i11;
      label1345:
      int i12;
      if (i6 == 0)
      {
        i8 = 1;
        if (i6 != i5 - 1) {
          break label1398;
        }
        i9 = 1;
        if (i8 == 0) {
          localSeparatorLinearLayout2.hideSeparator();
        }
        i10 = ViewCompat.getPaddingStart(localSeparatorLinearLayout2);
        if (i8 == 0) {
          break label1404;
        }
        i11 = localSeparatorLinearLayout2.getPaddingTop();
        i12 = ViewCompat.getPaddingEnd(localSeparatorLinearLayout2);
        if (i9 == 0) {
          break label1410;
        }
      }
      label1410:
      for (int i13 = localSeparatorLinearLayout2.getPaddingBottom();; i13 = 0)
      {
        ViewCompat.setPaddingRelative(localSeparatorLinearLayout2, i10, i11, i12, i13);
        localDetailsExpandedContainer.mDetailsExpandedExtras.addView(localSeparatorLinearLayout2);
        i6++;
        break;
        i8 = 0;
        break label1304;
        label1398:
        i9 = 0;
        break label1316;
        label1404:
        i11 = 0;
        break label1345;
      }
    }
    String str3 = localExpandedData.extraAttributions;
    if (!TextUtils.isEmpty(str3))
    {
      SeparatorLinearLayout localSeparatorLinearLayout1 = (SeparatorLinearLayout)localLayoutInflater.inflate(2130968705, localDetailsExpandedContainer.mDetailsExpandedExtras, false);
      TextView localTextView = (TextView)localLayoutInflater.inflate(2130968702, localSeparatorLinearLayout1, false);
      localTextView.setVisibility(0);
      localTextView.setText(Html.fromHtml(str3));
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
      localSeparatorLinearLayout1.addView(localTextView);
      localDetailsExpandedContainer.mDetailsExpandedExtras.addView(localSeparatorLinearLayout1);
    }
    this.mActionBarController.enterActionBarSectionExpandedMode(this.mExpandedData.title, localDetailsExpandedContainer);
  }
  
  protected final void requestData() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ExpandedDescriptionFragment
 * JD-Core Version:    0.7.0.1
 */