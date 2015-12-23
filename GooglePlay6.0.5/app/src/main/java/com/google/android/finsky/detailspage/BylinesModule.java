package com.google.android.finsky.detailspage;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppsPermissionsActivity;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.ArtistDetails;
import com.google.android.finsky.protos.ArtistExternalLinks;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.DeveloperDetails;
import com.google.android.finsky.protos.DocDetails.DocumentDetails;
import com.google.android.finsky.protos.DocDetails.TalentDetails;
import com.google.android.finsky.protos.DocDetails.TalentExternalLinks;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.List;

public class BylinesModule
  extends FinskyModule<Data>
  implements BylinesModuleLayout.BylinesClickListener
{
  private static BylinesModuleLayout.BylineEntryInfo createBylineEntry(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Document paramDocument, String paramString)
  {
    return new BylinesModuleLayout.BylineEntryInfo(paramInt1, paramInt2, paramInt3, paramDocument, paramString, paramInt4);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    ArrayList localArrayList;
    int k;
    label152:
    String str1;
    label160:
    Data localData2;
    Badge localBadge;
    if ((this.mModuleData == null) && (paramBoolean))
    {
      int i = paramDocument1.mDocument.docType;
      localArrayList = new ArrayList();
      switch (i)
      {
      default: 
        if (!localArrayList.isEmpty())
        {
          this.mModuleData = new Data();
          Data localData1 = (Data)this.mModuleData;
          int j = paramDocument1.mDocument.docType;
          k = -1;
          switch (j)
          {
          default: 
            if (k < 0)
            {
              str1 = null;
              localData1.title = str1;
              localData2 = (Data)this.mModuleData;
              if (paramDocument1.mDocument.docType != 1) {
                break label911;
              }
              if (!paramDocument1.hasCreatorBadges()) {
                break label905;
              }
              localBadge = paramDocument1.getFirstCreatorBadge();
            }
            break;
          }
        }
        break;
      }
    }
    for (;;)
    {
      localData2.titleBadge = localBadge;
      ((Data)this.mModuleData).bylineEntryInfoList = localArrayList;
      return;
      AppDetails localAppDetails = paramDocument1.getAppDetails();
      if (!TextUtils.isEmpty(localAppDetails.developerWebsite)) {
        localArrayList.add(createBylineEntry(2131362297, 2130837708, 114, 1, paramDocument1, localAppDetails.developerWebsite));
      }
      if (!TextUtils.isEmpty(localAppDetails.developerEmail)) {
        localArrayList.add(createBylineEntry(2131362295, 2130837704, 115, 0, paramDocument1, localAppDetails.developerEmail));
      }
      Annotations localAnnotations = paramDocument1.mDocument.annotations;
      if (localAnnotations != null) {}
      for (String str3 = localAnnotations.privacyPolicyUrl;; str3 = null)
      {
        if (!TextUtils.isEmpty(str3)) {
          localArrayList.add(createBylineEntry(2131362600, 2130837707, 116, 1, paramDocument1, str3));
        }
        if (paramDocument1.isPreregistration()) {
          break;
        }
        localArrayList.add(createBylineEntry(2131362533, 2130837706, 130, 2, paramDocument1, null));
        break;
      }
      if (paramDocument1.hasDetails()) {}
      for (DeveloperDetails localDeveloperDetails = paramDocument1.mDocument.details.developerDetails; (localDeveloperDetails != null) && (!TextUtils.isEmpty(localDeveloperDetails.websiteUrl)); localDeveloperDetails = null)
      {
        localArrayList.add(createBylineEntry(2131362297, 2130837708, 114, 1, paramDocument1, localDeveloperDetails.websiteUrl));
        break;
      }
      if (paramDocument1.hasDetails()) {}
      DocDetails.TalentExternalLinks localTalentExternalLinks;
      for (DocDetails.TalentDetails localTalentDetails = paramDocument1.mDocument.details.talentDetails; (localTalentDetails != null) && (localTalentDetails.externalLinks != null); localTalentDetails = null)
      {
        localTalentExternalLinks = localTalentDetails.externalLinks;
        if (localTalentExternalLinks.websiteUrl.length <= 0) {
          break label575;
        }
        for (Link localLink : localTalentExternalLinks.websiteUrl) {
          if (!TextUtils.isEmpty(localLink.uri)) {
            localArrayList.add(createBylineEntry(2131362297, 2130837708, 117, 1, paramDocument1, localLink.uri));
          }
        }
      }
      label575:
      if ((localTalentExternalLinks.googlePlusProfileUrl != null) && (!TextUtils.isEmpty(localTalentExternalLinks.googlePlusProfileUrl.uri))) {
        localArrayList.add(createBylineEntry(2131362295, 2130837705, 119, 1, paramDocument1, localTalentExternalLinks.googlePlusProfileUrl.uri));
      }
      if ((localTalentExternalLinks.youtubeChannelUrl == null) || (TextUtils.isEmpty(localTalentExternalLinks.youtubeChannelUrl.uri))) {
        break;
      }
      localArrayList.add(createBylineEntry(2131362295, 2130837709, 118, 1, paramDocument1, localTalentExternalLinks.youtubeChannelUrl.uri));
      break;
      if (paramDocument1.hasDetails()) {}
      ArtistExternalLinks localArtistExternalLinks;
      for (ArtistDetails localArtistDetails = paramDocument1.mDocument.details.artistDetails; (localArtistDetails != null) && (localArtistDetails.externalLinks != null); localArtistDetails = null)
      {
        localArtistExternalLinks = localArtistDetails.externalLinks;
        if (localArtistExternalLinks.websiteUrl.length <= 0) {
          break label794;
        }
        for (String str2 : localArtistExternalLinks.websiteUrl) {
          if (!TextUtils.isEmpty(str2)) {
            localArrayList.add(createBylineEntry(2131362297, 2130837708, 117, 1, paramDocument1, str2));
          }
        }
      }
      label794:
      if (!TextUtils.isEmpty(localArtistExternalLinks.youtubeChannelUrl)) {
        localArrayList.add(createBylineEntry(2131362298, 2130837709, 118, 1, paramDocument1, localArtistExternalLinks.youtubeChannelUrl));
      }
      if (TextUtils.isEmpty(localArtistExternalLinks.googlePlusProfileUrl)) {
        break;
      }
      localArrayList.add(createBylineEntry(2131362296, 2130837705, 119, 1, paramDocument1, localArtistExternalLinks.googlePlusProfileUrl));
      break;
      k = 2131362085;
      break label152;
      k = 2131362082;
      break label152;
      k = 2131362082;
      break label152;
      str1 = this.mContext.getString(k).toUpperCase();
      break label160;
      label905:
      localBadge = null;
      continue;
      label911:
      localBadge = null;
    }
  }
  
  public final void bindView(View paramView)
  {
    BylinesModuleLayout localBylinesModuleLayout = (BylinesModuleLayout)paramView;
    BitmapLoader localBitmapLoader;
    String str;
    Badge localBadge;
    List localList;
    if (!localBylinesModuleLayout.mBinded)
    {
      localBitmapLoader = this.mBitmapLoader;
      str = ((Data)this.mModuleData).title;
      localBadge = ((Data)this.mModuleData).titleBadge;
      localList = ((Data)this.mModuleData).bylineEntryInfoList;
      localBylinesModuleLayout.mListingLayout.removeAllViews();
      if (localList.isEmpty()) {
        localBylinesModuleLayout.setVisibility(8);
      }
    }
    else
    {
      return;
    }
    int i = localList.size();
    int j = localBylinesModuleLayout.getResources().getInteger(2131623945);
    int k = (-1 + (i + j)) / j;
    for (int m = 0; m < k; m++)
    {
      ViewGroup localViewGroup = (ViewGroup)localBylinesModuleLayout.mInflater.inflate(2130968651, localBylinesModuleLayout.mListingLayout, false);
      int n = 0;
      while (n < j)
      {
        int i1 = n + j * m;
        BylinesModuleCellLayout localBylinesModuleCellLayout = (BylinesModuleCellLayout)localBylinesModuleLayout.mInflater.inflate(2130968650, localViewGroup, false);
        if (i1 >= i)
        {
          localBylinesModuleCellLayout.setVisibility(4);
          localViewGroup.addView(localBylinesModuleCellLayout);
          n++;
        }
        else
        {
          BylinesModuleLayout.BylineEntryInfo localBylineEntryInfo = (BylinesModuleLayout.BylineEntryInfo)localList.get(i1);
          BylinesModuleLayout.1 local1 = new BylinesModuleLayout.1(localBylinesModuleLayout, this, localBylineEntryInfo);
          if (localBylineEntryInfo.iconResourceId < 0)
          {
            localBylinesModuleCellLayout.mThumbnail.setVisibility(4);
            label245:
            if (localBylineEntryInfo.textResourceId <= 0) {
              break label314;
            }
            localBylinesModuleCellLayout.mTitle.setText(localBylineEntryInfo.textResourceId);
          }
          for (;;)
          {
            localBylinesModuleCellLayout.setOnClickListener(local1);
            localBylinesModuleCellLayout.setContentDescription(localBylinesModuleCellLayout.mTitle.getText());
            break;
            localBylinesModuleCellLayout.mThumbnail.setVisibility(0);
            localBylinesModuleCellLayout.mThumbnail.setImageResource(localBylineEntryInfo.iconResourceId);
            break label245;
            label314:
            localBylinesModuleCellLayout.mTitle.setText(localBylineEntryInfo.text);
          }
        }
      }
      localBylinesModuleLayout.mListingLayout.addView(localViewGroup);
    }
    if (TextUtils.isEmpty(str)) {
      localBylinesModuleLayout.mHeaderView.setVisibility(8);
    }
    for (;;)
    {
      localBylinesModuleLayout.mBinded = true;
      return;
      localBylinesModuleLayout.mHeaderView.setVisibility(0);
      localBylinesModuleLayout.mHeaderView.setText(str);
      if (localBadge != null) {
        BadgeUtils.configureBadge(localBadge, localBitmapLoader, localBylinesModuleLayout.mHeaderView);
      }
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130968649;
  }
  
  public final void onBylinesClick(BylinesModuleLayout.BylineEntryInfo paramBylineEntryInfo)
  {
    try
    {
      localDocument = paramBylineEntryInfo.doc;
      str2 = paramBylineEntryInfo.link;
      switch (paramBylineEntryInfo.bylineEntryType)
      {
      case 0: 
        for (;;)
        {
          this.mContext.startActivity((Intent)localObject);
          label53:
          if (paramBylineEntryInfo.clickEventType != -1) {
            FinskyApp.get().getEventLogger().logClickEvent(paramBylineEntryInfo.clickEventType, null, this.mParentNode);
          }
          return;
          localObject = IntentUtils.createSendIntentForUrl(Uri.fromParts("mailto", str2, null));
          ((Intent)localObject).putExtra("android.intent.extra.SUBJECT", localDocument.mDocument.title);
        }
      }
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      for (;;)
      {
        Document localDocument;
        String str2;
        int i;
        label142:
        String str1;
        switch (paramBylineEntryInfo.bylineEntryType)
        {
        default: 
          i = -1;
          switch (paramBylineEntryInfo.bylineEntryType)
          {
          default: 
            str1 = "activity_not_found_dialog";
          }
          break;
        }
        for (;;)
        {
          SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
          localBuilder.setMessageId(i).setPositiveId(2131362418).setCanceledOnTouchOutside(true);
          localBuilder.build().show(((FragmentActivity)this.mContext).getSupportFragmentManager(), str1);
          break label53;
          localObject = IntentUtils.createViewIntentForUrl(Uri.parse(str2));
          break;
          Intent localIntent = AppsPermissionsActivity.createIntent(FinskyApp.get().getCurrentAccountName(), localDocument.mDocument.docid, localDocument, true);
          localObject = localIntent;
          break;
          i = 2131362375;
          break label142;
          i = 2131362393;
          break label142;
          str1 = "no_email_app_dialog";
          continue;
          str1 = "no_web_app_dialog";
        }
        Object localObject = null;
      }
    }
  }
  
  public final boolean readyForDisplay()
  {
    return this.mModuleData != null;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    List<BylinesModuleLayout.BylineEntryInfo> bylineEntryInfoList;
    String title;
    Badge titleBadge;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BylinesModule
 * JD-Core Version:    0.7.0.1
 */