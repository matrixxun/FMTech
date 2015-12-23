package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Pair;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ContentFilterActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AlbumDetails;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.BadgeContainer;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocDetails.ProductDetails;
import com.google.android.finsky.protos.DocDetails.ProductDetailsDescription;
import com.google.android.finsky.protos.DocDetails.ProductDetailsSection;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MagazineDetails;
import com.google.android.finsky.protos.MusicDetails;
import com.google.android.finsky.protos.VideoCredit;
import com.google.android.finsky.protos.VideoDetails;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DescriptionTextModule
  extends TextModule
  implements InstallerListener
{
  private Document mDetailsDoc;
  private Document mSeasonDocument;
  
  private static void addProductDetails(Document paramDocument, TextModule.ExpandedData paramExpandedData)
  {
    if (paramDocument.hasProductDetails())
    {
      DocDetails.ProductDetails localProductDetails = paramDocument.mDocument.productDetails;
      int i = localProductDetails.section.length;
      for (int j = 0; j < i; j++)
      {
        DocDetails.ProductDetailsSection localProductDetailsSection = localProductDetails.section[j];
        int k = localProductDetailsSection.description.length;
        int m = 0;
        if (m < k)
        {
          DocDetails.ProductDetailsDescription localProductDetailsDescription = localProductDetailsSection.description[m];
          if (localProductDetailsDescription.image != null) {
            paramExpandedData.extraPrimaryList.add(new TextModule.DetailsExtraPrimary(localProductDetailsSection.title, localProductDetailsDescription.description, null, localProductDetailsDescription.image, false));
          }
          for (;;)
          {
            m++;
            break;
            paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(localProductDetailsSection.title, localProductDetailsDescription.description));
          }
        }
      }
    }
  }
  
  private void fillContentRatingDetails(Document paramDocument, TextModule.ExpandedData paramExpandedData)
  {
    int i = paramDocument.mDocument.docType;
    Badge localBadge = paramDocument.getBadgeForContentRating();
    if ((i != 1) && (((i != 6) && (i != 18)) || (localBadge == null))) {
      return;
    }
    if (localBadge == null)
    {
      List localList = paramExpandedData.extraSecondaryList;
      String str = this.mContext.getString(2131361844);
      Context localContext = this.mContext;
      AppDetails localAppDetails = paramDocument.getAppDetails();
      if (localAppDetails == null) {}
      for (int j = -1;; j = -1 + localAppDetails.contentRating)
      {
        localList.add(new TextModule.DetailsExtraSecondary(str, ContentFilterActivity.getLabel(localContext, j)));
        return;
      }
    }
    if (((i == 18) || (i == 6)) && ((localBadge.image == null) || (localBadge.image.length == 0)) && (TextUtils.isEmpty(localBadge.description)))
    {
      paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362335), localBadge.title));
      return;
    }
    if ((localBadge.image != null) && (localBadge.image.length > 0)) {}
    for (Common.Image localImage = localBadge.image[0];; localImage = null)
    {
      paramExpandedData.extraPrimaryList.add(new TextModule.DetailsExtraPrimary(localBadge.title, localBadge.description, null, localImage, true));
      return;
    }
  }
  
  private static void fillExtraAttributionsString(Document paramDocument, TextModule.ExpandedData paramExpandedData)
  {
    if (paramDocument.mDocument.annotations != null) {}
    for (String str = paramDocument.mDocument.annotations.attributionHtml;; str = "")
    {
      paramExpandedData.extraAttributions = str;
      return;
    }
  }
  
  private void fillExtraCreditsList(Document paramDocument, TextModule.ExpandedData paramExpandedData)
  {
    VideoDetails localVideoDetails = paramDocument.getVideoDetails();
    VideoCredit[] arrayOfVideoCredit1;
    if (localVideoDetails != null)
    {
      arrayOfVideoCredit1 = localVideoDetails.credit;
      if ((arrayOfVideoCredit1 != null) && (arrayOfVideoCredit1.length != 0)) {
        break label33;
      }
    }
    for (;;)
    {
      return;
      arrayOfVideoCredit1 = null;
      break;
      label33:
      paramExpandedData.extraCreditsHeader = this.mContext.getString(2131362083);
      paramExpandedData.extraCreditsList = new ArrayList();
      VideoCredit[] arrayOfVideoCredit2 = arrayOfVideoCredit1;
      int i = arrayOfVideoCredit1.length;
      for (int j = 0; j < i; j++)
      {
        VideoCredit localVideoCredit = arrayOfVideoCredit2[j];
        paramExpandedData.extraCreditsList.add(new TextModule.DetailsExtraCredits(localVideoCredit.credit, TextUtils.join(", ", localVideoCredit.name)));
      }
    }
  }
  
  private static void fillExtraPrimaryList(Document paramDocument, TextModule.ExpandedData paramExpandedData)
  {
    if (paramDocument.hasCreatorBadges())
    {
      Badge[] arrayOfBadge3 = paramDocument.getCreatorBadges();
      int i2 = arrayOfBadge3.length;
      int i3 = 0;
      if (i3 < i2)
      {
        Badge localBadge4 = arrayOfBadge3[i3];
        if (localBadge4.expandedBadgeImage != null) {}
        for (Common.Image localImage4 = localBadge4.expandedBadgeImage;; localImage4 = BadgeUtils.getImage$7bb5454b(localBadge4))
        {
          paramExpandedData.extraPrimaryList.add(new TextModule.DetailsExtraPrimary(localBadge4.title, localBadge4.description, localBadge4.browseUrl, localImage4, true));
          i3++;
          break;
        }
      }
    }
    if (paramDocument.hasItemBadges())
    {
      Badge[] arrayOfBadge2 = paramDocument.mDocument.annotations.badgeForDoc;
      int n = arrayOfBadge2.length;
      int i1 = 0;
      if (i1 < n)
      {
        Badge localBadge3 = arrayOfBadge2[i1];
        if (localBadge3.expandedBadgeImage != null) {}
        for (Common.Image localImage3 = localBadge3.expandedBadgeImage;; localImage3 = BadgeUtils.getImage$7bb5454b(localBadge3))
        {
          paramExpandedData.extraPrimaryList.add(new TextModule.DetailsExtraPrimary(localBadge3.title, localBadge3.description, localBadge3.browseUrl, localImage3, true));
          i1++;
          break;
        }
      }
    }
    if (paramDocument.hasBadgeContainer())
    {
      BadgeContainer localBadgeContainer = paramDocument.getBadgeContainer();
      int k = localBadgeContainer.badge.length;
      int m = 0;
      if (m < k)
      {
        Badge localBadge2 = localBadgeContainer.badge[m];
        if (localBadge2.expandedBadgeImage != null) {}
        for (Common.Image localImage2 = localBadge2.expandedBadgeImage;; localImage2 = BadgeUtils.getImage$7bb5454b(localBadge2))
        {
          paramExpandedData.extraPrimaryList.add(new TextModule.DetailsExtraPrimary(localBadge2.title, localBadge2.description, localBadge2.browseUrl, localImage2, false));
          m++;
          break;
        }
      }
    }
    if (paramDocument.hasFeatureBadges())
    {
      Badge[] arrayOfBadge1 = paramDocument.getFeatureBadges();
      int i = arrayOfBadge1.length;
      int j = 0;
      if (j < i)
      {
        Badge localBadge1 = arrayOfBadge1[j];
        if (localBadge1.expandedBadgeImage != null) {}
        for (Common.Image localImage1 = localBadge1.expandedBadgeImage;; localImage1 = BadgeUtils.getImage$7bb5454b(localBadge1))
        {
          paramExpandedData.extraPrimaryList.add(new TextModule.DetailsExtraPrimary(localBadge1.title, localBadge1.description, localBadge1.browseUrl, localImage1, true));
          j++;
          break;
        }
      }
    }
  }
  
  private void fillExtraSecondaryList(Document paramDocument, TextModule.ExpandedData paramExpandedData)
  {
    int i = paramDocument.mDocument.docType;
    switch (i)
    {
    }
    for (;;)
    {
      return;
      AppDetails localAppDetails = paramDocument.getAppDetails();
      if (!TextUtils.isEmpty(localAppDetails.versionString)) {
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361853), localAppDetails.versionString));
      }
      if (!TextUtils.isEmpty(localAppDetails.uploadDate)) {
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361852), localAppDetails.uploadDate));
      }
      if (!TextUtils.isEmpty(localAppDetails.numDownloads)) {
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361845), localAppDetails.numDownloads));
      }
      if ((localAppDetails.hasInstallationSize) && (localAppDetails.installationSize > 0L))
      {
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361850), Formatter.formatFileSize(this.mContext, localAppDetails.installationSize)));
        return;
      }
      paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361850), this.mContext.getString(2131362371)));
      return;
      VideoDetails localVideoDetails = paramDocument.getVideoDetails();
      if (paramDocument.getBadgeForContentRating() == null)
      {
        if (TextUtils.isEmpty(localVideoDetails.contentRating)) {
          break label539;
        }
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362335), localVideoDetails.contentRating));
      }
      for (;;)
      {
        if (!TextUtils.isEmpty(localVideoDetails.releaseDate)) {
          paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362336), localVideoDetails.releaseDate));
        }
        if (!TextUtils.isEmpty(localVideoDetails.duration)) {
          paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362333), localVideoDetails.duration));
        }
        if (localVideoDetails.audioLanguage.length > 0) {
          paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362332), TextUtils.join(",", localVideoDetails.audioLanguage)));
        }
        if (localVideoDetails.captionLanguage.length <= 0) {
          break;
        }
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362337), TextUtils.join(",", localVideoDetails.captionLanguage)));
        return;
        label539:
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362335), this.mContext.getString(2131362378)));
      }
      MagazineDetails localMagazineDetails1 = paramDocument.getMagazineDetails();
      if (localMagazineDetails1 != null)
      {
        if (!TextUtils.isEmpty(localMagazineDetails1.deliveryFrequencyDescription)) {
          paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362311), localMagazineDetails1.deliveryFrequencyDescription));
        }
        if (!TextUtils.isEmpty(localMagazineDetails1.psvDescription)) {
          paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362313), localMagazineDetails1.psvDescription));
        }
      }
      Document localDocument = paramDocument;
      if ((i == 16) || (i == 24)) {
        localDocument = DocUtils.getMagazineCurrentIssueDocument(paramDocument);
      }
      if (localDocument == null) {
        continue;
      }
      MagazineDetails localMagazineDetails2 = localDocument.getMagazineDetails();
      if ((localMagazineDetails2 == null) || (TextUtils.isEmpty(localMagazineDetails2.deviceAvailabilityDescriptionHtml))) {
        continue;
      }
      paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131362312), localMagazineDetails2.deviceAvailabilityDescriptionHtml));
      return;
      AlbumDetails localAlbumDetails = paramDocument.getAlbumDetails();
      if (localAlbumDetails == null) {
        continue;
      }
      MusicDetails localMusicDetails = localAlbumDetails.details;
      if (!TextUtils.isEmpty(localMusicDetails.originalReleaseDate)) {}
      try
      {
        paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361831), DateUtils.formatIso8601Date(localMusicDetails.originalReleaseDate)));
        if (!TextUtils.isEmpty(localMusicDetails.label))
        {
          if ((!TextUtils.isEmpty(localMusicDetails.releaseDate)) && (localMusicDetails.releaseDate.length() >= 4))
          {
            String str2 = localMusicDetails.releaseDate.substring(0, 4);
            Context localContext2 = this.mContext;
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = str2;
            arrayOfObject2[1] = localMusicDetails.label;
            str1 = localContext2.getString(2131362344, arrayOfObject2);
            paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361828), str1));
          }
        }
        else
        {
          if (localMusicDetails.genre.length <= 0) {
            continue;
          }
          paramExpandedData.extraSecondaryList.add(new TextModule.DetailsExtraSecondary(this.mContext.getString(2131361829), TextUtils.join(",", localMusicDetails.genre)));
          return;
        }
      }
      catch (ParseException localParseException)
      {
        for (;;)
        {
          FinskyLog.e(localParseException, "Cannot parse ISO 8601 date", new Object[0]);
          continue;
          Context localContext1 = this.mContext;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localMusicDetails.label;
          String str1 = localContext1.getString(2131362343, arrayOfObject1);
        }
      }
    }
  }
  
  private boolean isOwned(Document paramDocument)
  {
    if (paramDocument.mDocument.docType == 1)
    {
      String str = paramDocument.getAppDetails().packageName;
      int i;
      if (FinskyApp.get().mPackageStateRepository.get(str) != null)
      {
        i = 1;
        if (FinskyApp.get().mInstaller.getState(str) == 0) {
          break label70;
        }
      }
      label70:
      for (int j = 1;; j = 0)
      {
        if ((i == 0) && (j == 0)) {
          break label76;
        }
        return true;
        i = 0;
        break;
      }
      label76:
      return false;
    }
    return LibraryUtils.isOwned(paramDocument, this.mLibraries.getAccountLibrary(this.mDfeApi.getAccount()));
  }
  
  private void updateExpandedDataWithSeasonDocument(TextModule.ExpandedData paramExpandedData)
  {
    if ((paramExpandedData == null) || (this.mSeasonDocument == null)) {
      return;
    }
    fillContentRatingDetails(this.mSeasonDocument, paramExpandedData);
    fillExtraCreditsList(this.mSeasonDocument, paramExpandedData);
    fillExtraPrimaryList(this.mSeasonDocument, paramExpandedData);
    fillExtraSecondaryList(this.mSeasonDocument, paramExpandedData);
    fillExtraAttributionsString(this.mSeasonDocument, paramExpandedData);
    addProductDetails(this.mSeasonDocument, paramExpandedData);
  }
  
  protected final TextModule.Data getData(Document paramDocument, boolean paramBoolean)
  {
    String str1 = paramDocument.mDocument.promotionalDescription;
    if ((TextUtils.isEmpty(str1)) && (!paramBoolean)) {
      return null;
    }
    Installer localInstaller = FinskyApp.get().mInstaller;
    localInstaller.removeListener(this);
    localInstaller.addListener(this);
    this.mDetailsDoc = paramDocument;
    TextModule.Data localData = new TextModule.Data();
    localData.backend = paramDocument.mDocument.backendId;
    localData.docType = paramDocument.mDocument.docType;
    CharSequence localCharSequence = paramDocument.getDescription();
    String str2 = paramDocument.mDocument.translatedDescriptionHtml;
    Spanned localSpanned;
    int i;
    label223:
    Object localObject;
    label246:
    boolean bool;
    if (TextUtils.isEmpty(str2))
    {
      localSpanned = null;
      localData.callout = str1;
      localData.calloutGravity = 1;
      localData.restrictCalloutMaxLines = false;
      if ((TextUtils.isEmpty(localData.callout)) && (paramDocument.mDocument.docType != 1))
      {
        localData.callout = localCharSequence;
        localData.calloutGravity = 8388611;
        localData.restrictCalloutMaxLines = true;
        localCharSequence = null;
      }
      localData.bodyHeader = null;
      localData.body = localCharSequence;
      localData.translatedBody = localSpanned;
      if ((!paramDocument.hasDetails()) || (paramDocument.getAppDetails() == null) || (TextUtils.isEmpty(paramDocument.getAppDetails().recentChangesHtml))) {
        break label402;
      }
      i = 1;
      if (i == 0) {
        break label423;
      }
      if ((paramDocument.hasDetails()) && (paramDocument.getAppDetails() != null)) {
        break label408;
      }
      localObject = "";
      localData.whatsNew = ((CharSequence)localObject);
      if (isOwned(paramDocument)) {
        break label429;
      }
      bool = true;
      label264:
      localData.hideWhatsNewInCollapsed = bool;
      if ((paramDocument.mDocument.docType != 8) || (!paramDocument.hasCreatorBadges())) {
        break label435;
      }
    }
    ArrayList localArrayList;
    label402:
    label408:
    label423:
    label429:
    label435:
    for (Badge localBadge1 = paramDocument.getCreatorBadges()[0];; localBadge1 = null)
    {
      localData.creatorBadge = localBadge1;
      localArrayList = new ArrayList();
      if (!paramDocument.hasBadgeContainer()) {
        break label441;
      }
      for (Badge localBadge3 : paramDocument.getBadgeContainer().badge)
      {
        Common.Image localImage3 = BadgeUtils.getImage$7bb5454b(localBadge3);
        if (localImage3 != null) {
          localArrayList.add(new Pair(localImage3, localBadge3.title));
        }
      }
      localSpanned = Html.fromHtml(str2);
      break;
      i = 0;
      break label223;
      localObject = FastHtmlParser.fromHtml(paramDocument.getAppDetails().recentChangesHtml);
      break label246;
      localObject = null;
      break label246;
      bool = false;
      break label264;
    }
    label441:
    if (paramDocument.hasFeatureBadges()) {
      for (Badge localBadge2 : paramDocument.getFeatureBadges())
      {
        Common.Image localImage2 = BadgeUtils.getImage$7bb5454b(localBadge2);
        if (localImage2 != null) {
          localArrayList.add(new Pair(localImage2, localBadge2.title));
        }
      }
    }
    if (paramDocument.hasProductDetails()) {
      for (DocDetails.ProductDetailsSection localProductDetailsSection : paramDocument.mDocument.productDetails.section)
      {
        DocDetails.ProductDetailsDescription[] arrayOfProductDetailsDescription = localProductDetailsSection.description;
        int m = arrayOfProductDetailsDescription.length;
        for (int n = 0; n < m; n++)
        {
          Common.Image localImage1 = arrayOfProductDetailsDescription[n].image;
          if (localImage1 != null)
          {
            Pair localPair = new Pair(localImage1, localProductDetailsSection.title);
            localArrayList.add(localPair);
          }
        }
      }
    }
    localData.iconDescriptionPairs = localArrayList;
    localData.backgroundFillColor = Integer.valueOf(this.mContext.getResources().getColor(2131689682));
    return localData;
  }
  
  protected final TextModule.ExpandedData getExpandedData(Document paramDocument, boolean paramBoolean, TextModule.Data paramData)
  {
    TextModule.ExpandedData localExpandedData = super.getExpandedData(paramDocument, paramBoolean, paramData);
    if (localExpandedData == null) {
      return null;
    }
    fillContentRatingDetails(paramDocument, localExpandedData);
    fillExtraCreditsList(paramDocument, localExpandedData);
    fillExtraPrimaryList(paramDocument, localExpandedData);
    fillExtraSecondaryList(paramDocument, localExpandedData);
    fillExtraAttributionsString(paramDocument, localExpandedData);
    addProductDetails(paramDocument, localExpandedData);
    updateExpandedDataWithSeasonDocument(localExpandedData);
    return localExpandedData;
  }
  
  public final void onDestroyModule()
  {
    super.onDestroyModule();
    FinskyApp.get().mInstaller.removeListener(this);
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    AppDetails localAppDetails = this.mDetailsDoc.getAppDetails();
    TextModule.Data localData;
    if ((localAppDetails != null) && (paramString.equals(localAppDetails.packageName)))
    {
      localData = (TextModule.Data)this.mModuleData;
      if (isOwned(this.mDetailsDoc)) {
        break label68;
      }
    }
    label68:
    for (boolean bool = true;; bool = false)
    {
      localData.hideWhatsNewInCollapsed = bool;
      this.mFinskyModuleController.refreshModule(this, true);
      return;
    }
  }
  
  protected final void onReceiveBroadcastData(String paramString, Object paramObject)
  {
    if (!"EpisodeListModule.SeasonDocument".equals(paramString)) {}
    do
    {
      return;
      this.mSeasonDocument = ((Document)paramObject);
    } while (this.mModuleData == null);
    updateExpandedDataWithSeasonDocument(((TextModule.Data)this.mModuleData).expandedData);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.DescriptionTextModule
 * JD-Core Version:    0.7.0.1
 */