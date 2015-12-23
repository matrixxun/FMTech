package com.google.android.finsky.detailspage;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public class ArtistRadioModule
  extends FinskyModule<Data>
  implements ArtistRadioModuleLayout.ArtistRadioClickListener
{
  private Intent createArtistRadioIntent(String paramString)
  {
    Intent localIntent = IntentUtils.buildConsumptionAppUrlIntent$5928b7f1(2, paramString, FinskyApp.get().getCurrentAccount().name);
    localIntent.addFlags(268435456);
    return localIntent;
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    boolean bool1 = true;
    int i;
    int j;
    label153:
    Data localData;
    if (this.mModuleData == null)
    {
      this.mModuleData = new Data();
      String str1 = (String)G.musicArtistRadioIntentUrl.get();
      String str2 = paramDocument1.mDocument.backendDocid;
      ((Data)this.mModuleData).buttonUrl = str1.replace("%artistId%", str2);
      boolean bool2 = FinskyApp.get().getExperiments().isEnabled(12603719L);
      if (paramDocument1.mDocument.docType != 3) {
        break label185;
      }
      i = bool1;
      String str3 = ((Data)this.mModuleData).buttonUrl;
      PackageManager localPackageManager = this.mContext.getPackageManager();
      if (!IntentUtils.isConsumptionAppInstalled(localPackageManager, 2)) {
        break label191;
      }
      List localList = localPackageManager.queryIntentActivities(createArtistRadioIntent(str3), 65536);
      if ((localList == null) || (localList.isEmpty())) {
        break label191;
      }
      j = bool1;
      localData = (Data)this.mModuleData;
      if ((j == 0) || (!bool2) || (i == 0)) {
        break label197;
      }
    }
    for (;;)
    {
      localData.showRadioLink = bool1;
      return;
      label185:
      i = 0;
      break;
      label191:
      j = 0;
      break label153;
      label197:
      bool1 = false;
    }
  }
  
  public final void bindView(View paramView)
  {
    ArtistRadioModuleLayout localArtistRadioModuleLayout = (ArtistRadioModuleLayout)paramView;
    if (this.mModuleData != null) {}
    for (int i = 1; i == 0; i = 0)
    {
      localArtistRadioModuleLayout.setVisibility(8);
      return;
    }
    localArtistRadioModuleLayout.setVisibility(0);
    localArtistRadioModuleLayout.mArtistRadioClickListener = this;
  }
  
  public final int getLayoutResId()
  {
    return 2130968620;
  }
  
  public final void onArtistRadioClick$3c7ec8c3()
  {
    this.mContext.startActivity(createArtistRadioIntent(((Data)this.mModuleData).buttonUrl));
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).showRadioLink);
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    String buttonUrl;
    boolean showRadioLink;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ArtistRadioModule
 * JD-Core Version:    0.7.0.1
 */