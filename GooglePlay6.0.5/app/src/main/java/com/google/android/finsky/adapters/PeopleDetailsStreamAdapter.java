package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.layout.IdentifiableTextView;
import com.google.android.finsky.layout.play.PeopleDetailsProfileInfoView;
import com.google.android.finsky.layout.play.PlayCardDismissListener;
import com.google.android.finsky.layout.play.PlayCirclesButton;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.ProfileButton;
import com.google.android.finsky.layout.play.WarmWelcomeCard;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocDetails.PersonDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public final class PeopleDetailsStreamAdapter<T extends ContainerList<?>>
  extends CardRecyclerViewAdapter<T>
{
  private final boolean mIsShowingOwnProfile;
  boolean mIsShowingWarmWelcome;
  private final Document mPlusDoc;
  private final int mWarmWelcomeCardColumns;
  private final boolean mWarmWelcomeHideGraphic;
  
  public PeopleDetailsStreamAdapter(Document paramDocument, Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, MultiDfeList<T> paramMultiDfeList, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramContext, paramDfeApi, paramNavigationManager, paramBitmapLoader, paramDfeToc, paramClientMutationCache, paramMultiDfeList, null, paramDocument.getCoreContentHeader(), paramBoolean, true, 2, paramPlayStoreUiElementNode, null);
    this.mPlusDoc = paramDocument;
    DocDetails.PersonDetails localPersonDetails = this.mPlusDoc.getPersonDetails();
    boolean bool1;
    boolean bool2;
    if ((localPersonDetails != null) && (localPersonDetails.personIsRequester))
    {
      bool1 = true;
      this.mIsShowingOwnProfile = bool1;
      Resources localResources = paramContext.getResources();
      this.mWarmWelcomeCardColumns = localResources.getInteger(2131623942);
      if ((this.mWarmWelcomeCardColumns != 1) || (localResources.getBoolean(2131427343))) {
        break label146;
      }
      bool2 = true;
      label102:
      this.mWarmWelcomeHideGraphic = bool2;
      if ((!this.mIsShowingOwnProfile) || (((Boolean)FinskyPreferences.warmWelcomeOwnProfileAcknowledged.get()).booleanValue())) {
        break label152;
      }
    }
    label146:
    label152:
    for (boolean bool3 = true;; bool3 = false)
    {
      this.mIsShowingWarmWelcome = bool3;
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label102;
    }
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 40;
    }
    int i = paramInt - 1;
    if (this.mIsShowingWarmWelcome)
    {
      if (i == 0) {
        return 42;
      }
      i--;
    }
    if (this.mItems.size() == 0) {
      return 41;
    }
    return super.getItemViewType(i);
  }
  
  protected final PlayCardDismissListener getPlayCardDismissListener()
  {
    return null;
  }
  
  public final int getPrependedRowsCount()
  {
    int i = super.getPrependedRowsCount();
    int j = 1;
    if (this.mIsShowingWarmWelcome) {
      j = 2;
    }
    if (this.mItems.size() == 0) {
      j++;
    }
    return j + i;
  }
  
  protected final boolean hasExtraLeadingSpacer()
  {
    return false;
  }
  
  protected final boolean hasLeadingSpacer()
  {
    return false;
  }
  
  protected final boolean isDismissed(Document paramDocument)
  {
    return false;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, final int paramInt)
  {
    int i = paramViewHolder.mItemViewType;
    View localView = paramViewHolder.itemView;
    switch (i)
    {
    default: 
      super.onBindViewHolder(paramViewHolder, paramInt);
      return;
    case 40: 
      PeopleDetailsProfileInfoView localPeopleDetailsProfileInfoView = (PeopleDetailsProfileInfoView)localView;
      localPeopleDetailsProfileInfoView.setId(2131755059);
      localPeopleDetailsProfileInfoView.setIdentifier("profile_info");
      Document localDocument = this.mPlusDoc;
      PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParentNode;
      localPeopleDetailsProfileInfoView.mPlusDoc = localDocument;
      localPeopleDetailsProfileInfoView.mParentNode = localPlayStoreUiElementNode;
      BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
      Common.Image localImage4 = (Common.Image)localPeopleDetailsProfileInfoView.mPlusDoc.getImages(4).get(0);
      localPeopleDetailsProfileInfoView.mProfileAvatarImage.setImage(localImage4.imageUrl, localImage4.supportsFifeUrlOptions, localBitmapLoader);
      String str4 = localPeopleDetailsProfileInfoView.mPlusDoc.mDocument.title;
      localPeopleDetailsProfileInfoView.mDisplayName.setText(str4);
      localPeopleDetailsProfileInfoView.mCirclesButton.bind(localPeopleDetailsProfileInfoView.mPlusDoc, FinskyApp.get().getCurrentAccountName(), localPlayStoreUiElementNode);
      DocDetails.PersonDetails localPersonDetails = localPeopleDetailsProfileInfoView.mPlusDoc.getPersonDetails();
      if (localPersonDetails != null)
      {
        if (!localPersonDetails.personIsRequester) {
          break label280;
        }
        localPeopleDetailsProfileInfoView.mCirclesButton.setVisibility(8);
      }
      for (;;)
      {
        Resources localResources = localPeopleDetailsProfileInfoView.getResources();
        localPeopleDetailsProfileInfoView.mViewProfileButton.configure(localResources.getString(2131362201), 2130837855, 2130837960);
        localPeopleDetailsProfileInfoView.mViewProfileButton.setOnClickListener(localPeopleDetailsProfileInfoView);
        localPeopleDetailsProfileInfoView.mViewProfileButton.setContentDescription(localResources.getString(2131362036, new Object[] { str4 }));
        return;
        localPeopleDetailsProfileInfoView.mCirclesButton.setVisibility(0);
      }
    case 42: 
      label280:
      final WarmWelcomeCard localWarmWelcomeCard = (WarmWelcomeCard)localView;
      String str1 = this.mContext.getString(2131362916);
      String str2 = this.mContext.getString(2131362914);
      String str3 = this.mContext.getString(2131362915);
      Common.Image localImage1 = new Common.Image();
      localImage1.imageType = 4;
      localImage1.hasImageType = true;
      localImage1.imageUrl = ((String)G.peoplePageWarmWelcomeGraphicUrl.get());
      localImage1.hasImageUrl = true;
      Common.Image localImage2 = new Common.Image();
      localImage2.imageType = 4;
      localImage2.hasImageType = true;
      localImage2.imageUrl = ((String)G.peoplePageWarmWelcomeButtonIconUrl.get());
      localImage2.hasImageUrl = true;
      if (this.mWarmWelcomeHideGraphic) {}
      for (Common.Image localImage3 = null;; localImage3 = localImage1)
      {
        localWarmWelcomeCard.configureContent(str1, str2, localImage3, 9, this.mParentNode, null);
        localWarmWelcomeCard.configureActionButton(str3, localImage2, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().getEventLogger().logClickEvent(1231, null, localWarmWelcomeCard);
            FinskyPreferences.warmWelcomeOwnProfileAcknowledged.put(Boolean.TRUE);
            PeopleDetailsStreamAdapter.this.mIsShowingWarmWelcome = false;
            PeopleDetailsStreamAdapter.this.notifyItemRemoved(paramInt);
          }
        }, true);
        localWarmWelcomeCard.hideSecondaryAction();
        ViewCompat.setPaddingRelative(localWarmWelcomeCard, this.mCardContentPadding, 0, this.mCardContentPadding, 0);
        localWarmWelcomeCard.setIdentifier("self_warm_welcome");
        return;
      }
    }
    IdentifiableTextView localIdentifiableTextView = (IdentifiableTextView)localView;
    if (this.mIsShowingOwnProfile) {}
    for (int j = 2131362465;; j = 2131362464)
    {
      localIdentifiableTextView.setText(j);
      int k = localView.getResources().getDimensionPixelSize(2131493117) + this.mCardContentPadding;
      ViewCompat.setPaddingRelative(localIdentifiableTextView, k, localView.getPaddingTop(), k, localView.getPaddingBottom());
      localIdentifiableTextView.setIdentifier("empty_state");
      return;
    }
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView;
    switch (paramInt)
    {
    default: 
      return super.onCreateViewHolder(paramViewGroup, paramInt);
    case 40: 
      localView = inflate(2130968902, paramViewGroup, false);
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder(localView);
      if (this.mWarmWelcomeCardColumns == 1) {}
      for (int i = 2130969179;; i = 2130969178)
      {
        localView = inflate(i, paramViewGroup, false);
        break;
      }
      localView = inflate(2130969023, paramViewGroup, false);
    }
  }
  
  protected final boolean shouldHidePlainHeaderDuringInitialLoading()
  {
    return true;
  }
  
  protected final boolean shouldHidePlainHeaderOnEmpty()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.PeopleDetailsStreamAdapter
 * JD-Core Version:    0.7.0.1
 */