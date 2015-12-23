package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.DecoratedTextView;
import com.google.android.finsky.layout.DetailsTextBlock;
import com.google.android.finsky.layout.DetailsTextIconContainer;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.BadgeUtils;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayTextView;
import com.google.android.play.utils.PlayCorpusUtils;
import com.google.android.play.utils.UrlSpanUtils.Listener;
import java.util.ArrayList;
import java.util.List;

public abstract class TextModule
  extends FinskyModule<Data>
  implements View.OnClickListener, UrlSpanUtils.Listener
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mModuleData == null) {
      this.mModuleData = getData(paramDocument1, paramBoolean);
    }
    if ((this.mModuleData != null) && ((((Data)this.mModuleData).expandedData == null) || (paramBoolean)))
    {
      Data localData = (Data)this.mModuleData;
      this.mModuleData = getData(paramDocument1, paramBoolean);
      ((Data)this.mModuleData).expandedData = getExpandedData(paramDocument1, paramBoolean, (Data)this.mModuleData);
      if (!localData.equals(this.mModuleData)) {
        this.mFinskyModuleController.refreshModule(this, true);
      }
    }
  }
  
  public final void bindView(View paramView)
  {
    TextModuleLayout localTextModuleLayout = (TextModuleLayout)paramView;
    boolean bool1;
    String str;
    CharSequence localCharSequence2;
    List localList;
    Integer localInteger;
    TextModule localTextModule;
    label195:
    BitmapLoader localBitmapLoader;
    label223:
    Resources localResources;
    DetailsTextIconContainer localDetailsTextIconContainer;
    ColorStateList localColorStateList;
    int m;
    label320:
    int n;
    label331:
    int i3;
    if (((Data)this.mModuleData).expandedData != null)
    {
      bool1 = ((Data)this.mModuleData).expandedData.hasContents();
      int i = ((Data)this.mModuleData).backend;
      int j = ((Data)this.mModuleData).docType;
      CharSequence localCharSequence1 = ((Data)this.mModuleData).callout;
      int k = ((Data)this.mModuleData).calloutGravity;
      boolean bool2 = ((Data)this.mModuleData).restrictCalloutMaxLines;
      str = ((Data)this.mModuleData).bodyHeader;
      localCharSequence2 = ((Data)this.mModuleData).body;
      CharSequence localCharSequence3 = ((Data)this.mModuleData).translatedBody;
      CharSequence localCharSequence4 = ((Data)this.mModuleData).whatsNew;
      boolean bool3 = ((Data)this.mModuleData).hideWhatsNewInCollapsed;
      Badge localBadge = ((Data)this.mModuleData).creatorBadge;
      localList = ((Data)this.mModuleData).iconDescriptionPairs;
      localInteger = ((Data)this.mModuleData).backgroundFillColor;
      if (!bool1) {
        break label548;
      }
      localTextModule = this;
      localBitmapLoader = this.mBitmapLoader;
      localTextModuleLayout.mReadMoreClickListener = localTextModule;
      if (localTextModuleLayout.mReadMoreClickListener != null) {
        break label554;
      }
      localTextModuleLayout.mFooterLabel.setVisibility(8);
      localTextModuleLayout.mLinkClickListener = this;
      localTextModuleLayout.selfishifyUrlSpans(localCharSequence1);
      localTextModuleLayout.selfishifyUrlSpans(localCharSequence2);
      localTextModuleLayout.selfishifyUrlSpans(localCharSequence3);
      localTextModuleLayout.selfishifyUrlSpans(localCharSequence4);
      Context localContext = localTextModuleLayout.getContext();
      localResources = localContext.getResources();
      localDetailsTextIconContainer = localTextModuleLayout.mIconContainer;
      if ((localList != null) && (!localList.isEmpty())) {
        break label565;
      }
      localDetailsTextIconContainer.setVisibility(8);
      localColorStateList = PlayCorpusUtils.getPrimaryTextColor(localContext, i);
      localTextModuleLayout.mFooterLabel.setTextColor(localColorStateList);
      if (j != 1) {
        break label747;
      }
      m = 1;
      if (TextUtils.isEmpty(localCharSequence1)) {
        break label753;
      }
      n = 1;
      if (n == 0) {
        break label767;
      }
      localTextModuleLayout.mCalloutView.setVisibility(0);
      localTextModuleLayout.mCalloutView.setText(localCharSequence1);
      PlayTextView localPlayTextView = localTextModuleLayout.mCalloutView;
      if (!bool2) {
        break label759;
      }
      i3 = localTextModuleLayout.mMaxCollapsedLines;
      label370:
      localPlayTextView.setMaxLines(i3);
      localTextModuleLayout.mCalloutView.setGravity(k);
      label386:
      localTextModuleLayout.mSpacerView.setVisibility(8);
      if (localInteger != null) {
        localTextModuleLayout.setBackgroundColor(localInteger.intValue());
      }
      if ((bool3) || (TextUtils.isEmpty(localCharSequence4))) {
        break label779;
      }
      localTextModuleLayout.mBodyContainerView.bind(localTextModuleLayout.mExpandedWhatsNewHeader, localCharSequence4, localTextModuleLayout.mMaxCollapsedLines);
      localTextModuleLayout.mBodyContainerView.setCorpusStyle(i, localTextModuleLayout.mWhatsNewVerticalMargin, localTextModuleLayout.mWhatsNewVerticalMargin);
      label456:
      if (localBadge == null) {
        break label936;
      }
      localTextModuleLayout.mTopDeveloperLabel.setText(localBadge.title);
      localTextModuleLayout.mTopDeveloperLabel.setTextColor(localTextModuleLayout.mTopDeveloperColor);
      BadgeUtils.configureBadge(localBadge, localBitmapLoader, localTextModuleLayout.mTopDeveloperLabel);
      localTextModuleLayout.mTopDeveloperLabel.setVisibility(0);
    }
    for (;;)
    {
      TextModuleLayout.1 local1 = new TextModuleLayout.1(localTextModuleLayout);
      localTextModuleLayout.setOnClickListener(local1);
      localTextModuleLayout.mBodyContainerView.setBodyClickListener(local1);
      localTextModuleLayout.mCalloutView.setOnClickListener(local1);
      localTextModuleLayout.setVisibility(0);
      return;
      bool1 = false;
      break;
      label548:
      localTextModule = null;
      break label195;
      label554:
      localTextModuleLayout.mFooterLabel.setVisibility(0);
      break label223;
      label565:
      localDetailsTextIconContainer.setVisibility(0);
      LayoutInflater localLayoutInflater = LayoutInflater.from(localDetailsTextIconContainer.getContext());
      for (int i4 = Math.max(0, localList.size() - localDetailsTextIconContainer.getChildCount()); i4 > 0; i4--) {
        localDetailsTextIconContainer.addView((FifeImageView)localLayoutInflater.inflate(2130968707, localDetailsTextIconContainer, false));
      }
      int i5 = localDetailsTextIconContainer.getChildCount();
      int i6 = 0;
      label640:
      FifeImageView localFifeImageView;
      if (i6 < i5)
      {
        localFifeImageView = (FifeImageView)localDetailsTextIconContainer.getChildAt(i6);
        if (i6 >= localList.size()) {
          break label737;
        }
        localFifeImageView.setVisibility(0);
        Pair localPair = (Pair)localList.get(i6);
        Common.Image localImage = (Common.Image)localPair.first;
        localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, localBitmapLoader);
        localFifeImageView.setContentDescription((CharSequence)localPair.second);
      }
      for (;;)
      {
        i6++;
        break label640;
        break;
        label737:
        localFifeImageView.setVisibility(8);
      }
      label747:
      m = 0;
      break label320;
      label753:
      n = 0;
      break label331;
      label759:
      i3 = 2147483647;
      break label370;
      label767:
      localTextModuleLayout.mCalloutView.setVisibility(8);
      break label386;
      label779:
      if ((m != 0) && (n != 0))
      {
        localTextModuleLayout.mBodyContainerView.setVisibility(8);
        break label456;
      }
      localTextModuleLayout.mBodyContainerView.bind(str, localCharSequence2, localTextModuleLayout.mMaxCollapsedLines);
      localTextModuleLayout.mBodyContainerView.removeCorpusStyle();
      if ((n == 0) && (TextUtils.isEmpty(str))) {
        localTextModuleLayout.mSpacerView.setVisibility(0);
      }
      if (localInteger == null) {
        break label456;
      }
      if (UiUtils.isColorBright(localInteger.intValue())) {}
      int i2;
      for (int i1 = 2131689624;; i1 = 2131689682)
      {
        i2 = localResources.getColor(i1);
        localTextModuleLayout.mBodyContainerView.setEditorialStyle(localInteger.intValue(), i2);
        if (localInteger.intValue() != localResources.getColor(2131689682)) {
          break label924;
        }
        localTextModuleLayout.mFooterLabel.setTextColor(localColorStateList);
        break;
      }
      label924:
      localTextModuleLayout.mFooterLabel.setTextColor(i2);
      break label456;
      label936:
      localTextModuleLayout.mTopDeveloperLabel.setVisibility(8);
    }
  }
  
  protected abstract Data getData(Document paramDocument, boolean paramBoolean);
  
  protected ExpandedData getExpandedData(Document paramDocument, boolean paramBoolean, Data paramData)
  {
    if (!paramBoolean) {
      return null;
    }
    ExpandedData localExpandedData = new ExpandedData();
    localExpandedData.backend = paramDocument.mDocument.backendId;
    localExpandedData.title = paramDocument.mDocument.title;
    localExpandedData.callout = paramData.callout;
    localExpandedData.calloutGravity = paramData.calloutGravity;
    localExpandedData.bodyHeader = paramData.bodyHeader;
    localExpandedData.body = paramData.body;
    localExpandedData.translatedBody = paramData.translatedBody;
    localExpandedData.whatsNewHeader = this.mContext.getResources().getString(2131362092).toUpperCase();
    localExpandedData.whatsNew = paramData.whatsNew;
    if ((!paramData.hideWhatsNewInCollapsed) && (!TextUtils.isEmpty(paramData.whatsNew))) {}
    for (boolean bool = true;; bool = false)
    {
      localExpandedData.promoteWhatsNew = bool;
      return localExpandedData;
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130969134;
  }
  
  public void onClick(View paramView)
  {
    if (((Data)this.mModuleData).expandedData != null)
    {
      NavigationManager localNavigationManager = this.mNavigationManager;
      ExpandedData localExpandedData = ((Data)this.mModuleData).expandedData;
      DfeToc localDfeToc = this.mDfeToc;
      if (localNavigationManager.canNavigate()) {
        localNavigationManager.showPage(14, null, ExpandedDescriptionFragment.newInstance(localExpandedData, localDfeToc), false, new View[0]);
      }
    }
  }
  
  public final void onClick(View paramView, String paramString)
  {
    Context localContext = paramView.getContext();
    Intent localIntent = new Intent("android.intent.action.VIEW");
    Uri localUri = Uri.parse(paramString);
    localIntent.setData(localUri);
    localIntent.setPackage(localContext.getPackageName());
    if (localContext.getPackageManager().resolveActivity(localIntent, 65536) != null)
    {
      this.mNavigationManager.handleDeepLink(localUri, null);
      return;
    }
    localIntent.setPackage(null);
    localContext.startActivity(localIntent);
  }
  
  public final boolean readyForDisplay()
  {
    if (this.mModuleData == null) {
      return false;
    }
    Data localData = (Data)this.mModuleData;
    if ((!TextUtils.isEmpty(localData.callout)) || (!TextUtils.isEmpty(localData.bodyHeader)) || (!TextUtils.isEmpty(localData.body)) || (!TextUtils.isEmpty(localData.translatedBody)) || ((!TextUtils.isEmpty(localData.whatsNew)) && (!localData.hideWhatsNewInCollapsed)) || (localData.creatorBadge != null) || ((localData.iconDescriptionPairs != null) && (!localData.iconDescriptionPairs.isEmpty()))) {}
    for (int i = 1; (i != 0) || ((((Data)this.mModuleData).expandedData != null) && (((Data)this.mModuleData).expandedData.hasContents())); i = 0) {
      return true;
    }
    return false;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    int backend;
    Integer backgroundFillColor;
    CharSequence body;
    String bodyHeader;
    CharSequence callout;
    int calloutGravity;
    Badge creatorBadge;
    int docType;
    TextModule.ExpandedData expandedData;
    boolean hideWhatsNewInCollapsed;
    List<Pair<Common.Image, String>> iconDescriptionPairs;
    boolean restrictCalloutMaxLines;
    CharSequence translatedBody;
    CharSequence whatsNew;
    
    public final boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      Data localData;
      do
      {
        return true;
        if ((paramObject == null) || (getClass() != paramObject.getClass())) {
          return false;
        }
        localData = (Data)paramObject;
        if (this.backend != localData.backend) {
          return false;
        }
        if (this.docType != localData.docType) {
          return false;
        }
        if (this.calloutGravity != localData.calloutGravity) {
          return false;
        }
        if (this.restrictCalloutMaxLines != localData.restrictCalloutMaxLines) {
          return false;
        }
        if (this.hideWhatsNewInCollapsed != localData.hideWhatsNewInCollapsed) {
          return false;
        }
        if (this.callout != null)
        {
          if (this.callout.equals(localData.callout)) {}
        }
        else {
          while (localData.callout != null) {
            return false;
          }
        }
        if (this.bodyHeader != null)
        {
          if (this.bodyHeader.equals(localData.bodyHeader)) {}
        }
        else {
          while (localData.bodyHeader != null) {
            return false;
          }
        }
        if (this.body != null)
        {
          if (this.body.equals(localData.body)) {}
        }
        else {
          while (localData.body != null) {
            return false;
          }
        }
        if (this.translatedBody != null)
        {
          if (this.translatedBody.equals(localData.translatedBody)) {}
        }
        else {
          while (localData.translatedBody != null) {
            return false;
          }
        }
        if (this.whatsNew != null)
        {
          if (this.whatsNew.equals(localData.whatsNew)) {}
        }
        else {
          while (localData.whatsNew != null) {
            return false;
          }
        }
        if (this.creatorBadge != null)
        {
          if (this.creatorBadge.equals(localData.creatorBadge)) {}
        }
        else {
          while (localData.creatorBadge != null) {
            return false;
          }
        }
        if (this.iconDescriptionPairs != null)
        {
          if (this.iconDescriptionPairs.equals(localData.iconDescriptionPairs)) {}
        }
        else {
          while (localData.iconDescriptionPairs != null) {
            return false;
          }
        }
        if (this.backgroundFillColor != null)
        {
          if (this.backgroundFillColor.equals(localData.backgroundFillColor)) {}
        }
        else {
          while (localData.backgroundFillColor != null) {
            return false;
          }
        }
        if (this.expandedData == null) {
          break;
        }
      } while (this.expandedData.equals(localData.expandedData));
      for (;;)
      {
        return false;
        if (localData.expandedData == null) {
          break;
        }
      }
    }
    
    public final int hashCode()
    {
      int i = 1;
      int j = 31 * (31 * this.backend + this.docType);
      int k;
      int n;
      label59:
      int i2;
      label85:
      int i4;
      label111:
      int i6;
      label137:
      int i8;
      label163:
      label180:
      int i11;
      label205:
      int i13;
      label233:
      int i14;
      if (this.callout != null)
      {
        k = this.callout.hashCode();
        int m = 31 * (31 * (j + k) + this.calloutGravity);
        if (!this.restrictCalloutMaxLines) {
          break label303;
        }
        n = i;
        int i1 = 31 * (m + n);
        if (this.bodyHeader == null) {
          break label309;
        }
        i2 = this.bodyHeader.hashCode();
        int i3 = 31 * (i1 + i2);
        if (this.body == null) {
          break label315;
        }
        i4 = this.body.hashCode();
        int i5 = 31 * (i3 + i4);
        if (this.translatedBody == null) {
          break label321;
        }
        i6 = this.translatedBody.hashCode();
        int i7 = 31 * (i5 + i6);
        if (this.whatsNew == null) {
          break label327;
        }
        i8 = this.whatsNew.hashCode();
        int i9 = 31 * (i7 + i8);
        if (!this.hideWhatsNewInCollapsed) {
          break label333;
        }
        int i10 = 31 * (i9 + i);
        if (this.creatorBadge == null) {
          break label338;
        }
        i11 = this.creatorBadge.hashCode();
        int i12 = 31 * (i10 + i11);
        if (this.iconDescriptionPairs == null) {
          break label344;
        }
        i13 = this.iconDescriptionPairs.hashCode();
        i14 = 31 * (i12 + i13);
        if (this.backgroundFillColor == null) {
          break label350;
        }
      }
      label303:
      label309:
      label315:
      label321:
      label327:
      label333:
      label338:
      label344:
      label350:
      for (int i15 = this.backgroundFillColor.hashCode();; i15 = 0)
      {
        int i16 = 31 * (i14 + i15);
        TextModule.ExpandedData localExpandedData = this.expandedData;
        int i17 = 0;
        if (localExpandedData != null) {
          i17 = this.expandedData.hashCode();
        }
        return i16 + i17;
        k = 0;
        break;
        n = 0;
        break label59;
        i2 = 0;
        break label85;
        i4 = 0;
        break label111;
        i6 = 0;
        break label137;
        i8 = 0;
        break label163;
        i = 0;
        break label180;
        i11 = 0;
        break label205;
        i13 = 0;
        break label233;
      }
    }
  }
  
  public static class DetailsExtraCredits
    implements Parcelable
  {
    public static final Parcelable.Creator<DetailsExtraCredits> CREATOR = new Parcelable.Creator() {};
    public String credit;
    public String names;
    
    public DetailsExtraCredits(String paramString1, String paramString2)
    {
      this.credit = paramString1;
      this.names = paramString2;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeString(this.credit);
      paramParcel.writeString(this.names);
    }
  }
  
  public static class DetailsExtraPrimary
    implements Parcelable
  {
    public static final Parcelable.Creator<DetailsExtraPrimary> CREATOR = new Parcelable.Creator() {};
    public String description;
    public Common.Image image;
    public boolean skipInCollapsedMode;
    public String title;
    public String url;
    
    public DetailsExtraPrimary(String paramString1, String paramString2, String paramString3, Common.Image paramImage, boolean paramBoolean)
    {
      this.title = paramString1;
      this.description = paramString2;
      this.url = paramString3;
      this.image = paramImage;
      this.skipInCollapsedMode = paramBoolean;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeString(this.title);
      paramParcel.writeString(this.description);
      paramParcel.writeString(this.url);
      paramParcel.writeParcelable(ParcelableProto.forProto(this.image), 0);
      boolean bool = this.skipInCollapsedMode;
      int i = 0;
      if (bool) {
        i = 1;
      }
      paramParcel.writeInt(i);
    }
  }
  
  public static class DetailsExtraSecondary
    implements Parcelable
  {
    public static final Parcelable.Creator<DetailsExtraSecondary> CREATOR = new Parcelable.Creator() {};
    public String description;
    public String title;
    
    public DetailsExtraSecondary(String paramString1, String paramString2)
    {
      this.title = paramString1;
      this.description = paramString2;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeString(this.title);
      paramParcel.writeString(this.description);
    }
  }
  
  public static class ExpandedData
    implements Parcelable
  {
    public static final Parcelable.Creator<ExpandedData> CREATOR = new Parcelable.Creator() {};
    public int backend;
    public CharSequence body;
    public String bodyHeader;
    public CharSequence callout;
    public int calloutGravity;
    public String extraAttributions;
    public String extraCreditsHeader;
    public List<TextModule.DetailsExtraCredits> extraCreditsList = new ArrayList();
    public List<TextModule.DetailsExtraPrimary> extraPrimaryList = new ArrayList();
    public List<TextModule.DetailsExtraSecondary> extraSecondaryList = new ArrayList();
    public boolean promoteWhatsNew;
    public String title;
    public CharSequence translatedBody;
    public CharSequence whatsNew;
    public CharSequence whatsNewHeader;
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      ExpandedData localExpandedData;
      do
      {
        return true;
        if ((paramObject == null) || (getClass() != paramObject.getClass())) {
          return false;
        }
        localExpandedData = (ExpandedData)paramObject;
        if (this.backend != localExpandedData.backend) {
          return false;
        }
        if (this.calloutGravity != localExpandedData.calloutGravity) {
          return false;
        }
        if (this.promoteWhatsNew != localExpandedData.promoteWhatsNew) {
          return false;
        }
        if (this.title != null)
        {
          if (this.title.equals(localExpandedData.title)) {}
        }
        else {
          while (localExpandedData.title != null) {
            return false;
          }
        }
        if (this.callout != null)
        {
          if (this.callout.equals(localExpandedData.callout)) {}
        }
        else {
          while (localExpandedData.callout != null) {
            return false;
          }
        }
        if (this.bodyHeader != null)
        {
          if (this.bodyHeader.equals(localExpandedData.bodyHeader)) {}
        }
        else {
          while (localExpandedData.bodyHeader != null) {
            return false;
          }
        }
        if (this.body != null)
        {
          if (this.body.equals(localExpandedData.body)) {}
        }
        else {
          while (localExpandedData.body != null) {
            return false;
          }
        }
        if (this.translatedBody != null)
        {
          if (this.translatedBody.equals(localExpandedData.translatedBody)) {}
        }
        else {
          while (localExpandedData.translatedBody != null) {
            return false;
          }
        }
        if (this.whatsNewHeader != null)
        {
          if (this.whatsNewHeader.equals(localExpandedData.whatsNewHeader)) {}
        }
        else {
          while (localExpandedData.whatsNewHeader != null) {
            return false;
          }
        }
        if (this.whatsNew != null)
        {
          if (this.whatsNew.equals(localExpandedData.whatsNew)) {}
        }
        else {
          while (localExpandedData.whatsNew != null) {
            return false;
          }
        }
        if (this.extraCreditsHeader != null)
        {
          if (this.extraCreditsHeader.equals(localExpandedData.extraCreditsHeader)) {}
        }
        else {
          while (localExpandedData.extraCreditsHeader != null) {
            return false;
          }
        }
        if (this.extraCreditsList != null)
        {
          if (this.extraCreditsList.equals(localExpandedData.extraCreditsList)) {}
        }
        else {
          while (localExpandedData.extraCreditsList != null) {
            return false;
          }
        }
        if (this.extraPrimaryList != null)
        {
          if (this.extraPrimaryList.equals(localExpandedData.extraPrimaryList)) {}
        }
        else {
          while (localExpandedData.extraPrimaryList != null) {
            return false;
          }
        }
        if (this.extraSecondaryList != null)
        {
          if (this.extraSecondaryList.equals(localExpandedData.extraSecondaryList)) {}
        }
        else {
          while (localExpandedData.extraSecondaryList != null) {
            return false;
          }
        }
        if (this.extraAttributions == null) {
          break;
        }
      } while (this.extraAttributions.equals(localExpandedData.extraAttributions));
      for (;;)
      {
        return false;
        if (localExpandedData.extraAttributions == null) {
          break;
        }
      }
    }
    
    public final boolean hasContents()
    {
      return ((!TextUtils.isEmpty(this.callout)) && (this.callout.length() >= 140)) || (!TextUtils.isEmpty(this.body)) || (!TextUtils.isEmpty(this.translatedBody)) || (!TextUtils.isEmpty(this.whatsNew)) || (!TextUtils.isEmpty(this.extraCreditsHeader)) || ((this.extraCreditsList != null) && (!this.extraCreditsList.isEmpty())) || ((this.extraPrimaryList != null) && (!this.extraPrimaryList.isEmpty())) || ((this.extraSecondaryList != null) && (!this.extraSecondaryList.isEmpty())) || (!TextUtils.isEmpty(this.extraAttributions));
    }
    
    public int hashCode()
    {
      int i = 31 * this.backend;
      int j;
      int m;
      label46:
      int i1;
      label79:
      int i3;
      label105:
      int i5;
      label131:
      int i7;
      label157:
      int i9;
      label183:
      int i11;
      label203:
      int i13;
      label229:
      int i15;
      label257:
      int i17;
      label285:
      int i18;
      if (this.title != null)
      {
        j = this.title.hashCode();
        int k = 31 * (i + j);
        if (this.callout == null) {
          break label357;
        }
        m = this.callout.hashCode();
        int n = 31 * (31 * (k + m) + this.calloutGravity);
        if (this.bodyHeader == null) {
          break label363;
        }
        i1 = this.bodyHeader.hashCode();
        int i2 = 31 * (n + i1);
        if (this.body == null) {
          break label369;
        }
        i3 = this.body.hashCode();
        int i4 = 31 * (i2 + i3);
        if (this.translatedBody == null) {
          break label375;
        }
        i5 = this.translatedBody.hashCode();
        int i6 = 31 * (i4 + i5);
        if (this.whatsNewHeader == null) {
          break label381;
        }
        i7 = this.whatsNewHeader.hashCode();
        int i8 = 31 * (i6 + i7);
        if (this.whatsNew == null) {
          break label387;
        }
        i9 = this.whatsNew.hashCode();
        int i10 = 31 * (i8 + i9);
        if (!this.promoteWhatsNew) {
          break label393;
        }
        i11 = 1;
        int i12 = 31 * (i10 + i11);
        if (this.extraCreditsHeader == null) {
          break label399;
        }
        i13 = this.extraCreditsHeader.hashCode();
        int i14 = 31 * (i12 + i13);
        if (this.extraCreditsList == null) {
          break label405;
        }
        i15 = this.extraCreditsList.hashCode();
        int i16 = 31 * (i14 + i15);
        if (this.extraPrimaryList == null) {
          break label411;
        }
        i17 = this.extraPrimaryList.hashCode();
        i18 = 31 * (i16 + i17);
        if (this.extraSecondaryList == null) {
          break label417;
        }
      }
      label387:
      label393:
      label399:
      label405:
      label411:
      label417:
      for (int i19 = this.extraSecondaryList.hashCode();; i19 = 0)
      {
        int i20 = 31 * (i18 + i19);
        String str = this.extraAttributions;
        int i21 = 0;
        if (str != null) {
          i21 = this.extraAttributions.hashCode();
        }
        return i20 + i21;
        j = 0;
        break;
        label357:
        m = 0;
        break label46;
        label363:
        i1 = 0;
        break label79;
        label369:
        i3 = 0;
        break label105;
        label375:
        i5 = 0;
        break label131;
        label381:
        i7 = 0;
        break label157;
        i9 = 0;
        break label183;
        i11 = 0;
        break label203;
        i13 = 0;
        break label229;
        i15 = 0;
        break label257;
        i17 = 0;
        break label285;
      }
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.backend);
      paramParcel.writeString(this.title);
      TextUtils.writeToParcel(this.callout, paramParcel, 0);
      paramParcel.writeInt(this.calloutGravity);
      paramParcel.writeString(this.bodyHeader);
      TextUtils.writeToParcel(this.body, paramParcel, 0);
      TextUtils.writeToParcel(this.translatedBody, paramParcel, 0);
      TextUtils.writeToParcel(this.whatsNewHeader, paramParcel, 0);
      TextUtils.writeToParcel(this.whatsNew, paramParcel, 0);
      boolean bool = this.promoteWhatsNew;
      int i = 0;
      if (bool) {
        i = 1;
      }
      paramParcel.writeInt(i);
      paramParcel.writeString(this.extraCreditsHeader);
      paramParcel.writeTypedList(this.extraCreditsList);
      paramParcel.writeTypedList(this.extraPrimaryList);
      paramParcel.writeTypedList(this.extraSecondaryList);
      paramParcel.writeString(this.extraAttributions);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.TextModule
 * JD-Core Version:    0.7.0.1
 */