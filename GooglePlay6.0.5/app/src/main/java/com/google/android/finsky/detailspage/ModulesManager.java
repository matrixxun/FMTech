package com.google.android.finsky.detailspage;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ModulesManager
{
  private static List<Class> ANTENNA_MODULES;
  private static final List<Class> DETAILS_MODULES;
  private static final List<Class> DETAILS_MODULES_COMBINED_TITLE;
  private static final boolean mShowPostPurchaseRelatedTopicsAboveXSell = FinskyApp.get().getExperiments().isEnabled(12602823L);
  
  static
  {
    Class[] arrayOfClass1 = new Class[27];
    arrayOfClass1[0] = HeaderListSpacerModule.class;
    arrayOfClass1[1] = AvatarTitleModule.class;
    arrayOfClass1[2] = TitleModule.class;
    arrayOfClass1[3] = WarningMessageModule.class;
    Object localObject1;
    Object localObject2;
    label63:
    Class[] arrayOfClass2;
    Object localObject3;
    if (mShowPostPurchaseRelatedTopicsAboveXSell)
    {
      localObject1 = PostPurchaseRelatedTopicsClusterModule.class;
      arrayOfClass1[4] = localObject1;
      if (!mShowPostPurchaseRelatedTopicsAboveXSell) {
        break label419;
      }
      localObject2 = PostPurchaseCrossSellClusterModule.class;
      arrayOfClass1[5] = localObject2;
      arrayOfClass1[6] = SubscriptionsModule.class;
      arrayOfClass1[7] = DiscoveryBarModule.class;
      arrayOfClass1[8] = ArtistRadioModule.class;
      arrayOfClass1[9] = DescriptionTextModule.class;
      arrayOfClass1[10] = ScreenshotsModule.class;
      arrayOfClass1[11] = BundleContentModule.class;
      arrayOfClass1[12] = ExtrasContentListModule.class;
      arrayOfClass1[13] = AlsoAvailableInModule.class;
      arrayOfClass1[14] = AboutAuthorTextModule.class;
      arrayOfClass1[15] = RateReviewModule.class;
      arrayOfClass1[16] = SongListModule.class;
      arrayOfClass1[17] = EpisodeListModule.class;
      arrayOfClass1[18] = ReviewsStatisticsModule.class;
      arrayOfClass1[19] = ReviewSnippetsModule.class;
      arrayOfClass1[20] = ReviewsSamplesModule.class;
      arrayOfClass1[21] = SecondaryActionsModule.class;
      arrayOfClass1[22] = DynamicModulesPlaceholder.class;
      arrayOfClass1[23] = BylinesModule.class;
      arrayOfClass1[24] = FlagContentModule.class;
      arrayOfClass1[25] = FooterTextModule.class;
      arrayOfClass1[26] = FooterSpacerModule.class;
      DETAILS_MODULES = Arrays.asList(arrayOfClass1);
      arrayOfClass2 = new Class[25];
      arrayOfClass2[0] = HeaderListSpacerModule.class;
      arrayOfClass2[1] = AvatarTitleModule.class;
      arrayOfClass2[2] = CombinedTitleModule.class;
      if (!mShowPostPurchaseRelatedTopicsAboveXSell) {
        break label425;
      }
      localObject3 = PostPurchaseRelatedTopicsClusterModule.class;
      label231:
      arrayOfClass2[3] = localObject3;
      if (!mShowPostPurchaseRelatedTopicsAboveXSell) {
        break label432;
      }
    }
    label419:
    label425:
    label432:
    for (Object localObject4 = PostPurchaseCrossSellClusterModule.class;; localObject4 = PostPurchaseRelatedTopicsClusterModule.class)
    {
      arrayOfClass2[4] = localObject4;
      arrayOfClass2[5] = SubscriptionsModule.class;
      arrayOfClass2[6] = ArtistRadioModule.class;
      arrayOfClass2[7] = DescriptionTextModule.class;
      arrayOfClass2[8] = ScreenshotsModule.class;
      arrayOfClass2[9] = BundleContentModule.class;
      arrayOfClass2[10] = ExtrasContentListModule.class;
      arrayOfClass2[11] = AlsoAvailableInModule.class;
      arrayOfClass2[12] = AboutAuthorTextModule.class;
      arrayOfClass2[13] = RateReviewModule.class;
      arrayOfClass2[14] = SongListModule.class;
      arrayOfClass2[15] = EpisodeListModule.class;
      arrayOfClass2[16] = ReviewsStatisticsModule.class;
      arrayOfClass2[17] = ReviewSnippetsModule.class;
      arrayOfClass2[18] = ReviewsSamplesModule.class;
      arrayOfClass2[19] = SecondaryActionsModule.class;
      arrayOfClass2[20] = DynamicModulesPlaceholder.class;
      arrayOfClass2[21] = BylinesModule.class;
      arrayOfClass2[22] = FlagContentModule.class;
      arrayOfClass2[23] = FooterTextModule.class;
      arrayOfClass2[24] = FooterSpacerModule.class;
      DETAILS_MODULES_COMBINED_TITLE = Arrays.asList(arrayOfClass2);
      ANTENNA_MODULES = Arrays.asList(new Class[] { HeaderListSpacerModule.class, AntennaDescriptionTextModule.class, AntennaSongListModule.class, SecondaryActionsModule.class, AntennaAlbumsCardClusterModule.class });
      return;
      localObject1 = PostPurchaseCrossSellClusterModule.class;
      break;
      localObject2 = PostPurchaseRelatedTopicsClusterModule.class;
      break label63;
      localObject3 = PostPurchaseCrossSellClusterModule.class;
      break label231;
    }
  }
  
  public static List<FinskyModule> generateDynamicModules(Document paramDocument, boolean paramBoolean, List<FinskyModule> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    if (isAntennaPage(paramDocument)) {
      return localArrayList;
    }
    if (paramBoolean) {}
    for (List localList = DETAILS_MODULES_COMBINED_TITLE;; localList = DETAILS_MODULES)
    {
      int i = localList.indexOf(DynamicModulesPlaceholder.class);
      int j = paramDocument.getSectionMetaDataList().length;
      for (int k = 0; k < j; k++)
      {
        DynamicCardClusterModule localDynamicCardClusterModule = new DynamicCardClusterModule();
        localDynamicCardClusterModule.mSectionMetadataIndex = k;
        paramList.add(i + k, localDynamicCardClusterModule);
        localArrayList.add(localDynamicCardClusterModule);
      }
      break;
    }
  }
  
  public static List<FinskyModule> generateModules(List<Class> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i < paramList.size())
      {
        if ((Class)paramList.get(i) != DynamicModulesPlaceholder.class) {}
        try
        {
          localArrayList.add((FinskyModule)((Class)paramList.get(i)).newInstance());
          i++;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            FinskyLog.wtf("Exception trying to instantiate module: " + localException.getMessage(), new Object[0]);
          }
        }
      }
    }
    return localArrayList;
  }
  
  public static List<FinskyModule> generateStaticModules(Document paramDocument, boolean paramBoolean)
  {
    if (isAntennaPage(paramDocument)) {
      return generateModules(ANTENNA_MODULES);
    }
    if (paramBoolean) {}
    for (List localList = DETAILS_MODULES_COMBINED_TITLE;; localList = DETAILS_MODULES) {
      return generateModules(localList);
    }
  }
  
  private static boolean isAntennaPage(Document paramDocument)
  {
    return (paramDocument.mDocument.backendId == 2) && (paramDocument.hasAntennaInfo());
  }
  
  public static int remapModuleIndexAfterRotation(int paramInt, Document paramDocument, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (isAntennaPage(paramDocument)) {}
    do
    {
      return paramInt;
      if ((!paramBoolean1) && (paramBoolean2))
      {
        int k = paramInt;
        if (paramInt > DETAILS_MODULES.indexOf(TitleModule.class)) {
          k--;
        }
        if (paramInt > DETAILS_MODULES.indexOf(WarningMessageModule.class)) {
          k--;
        }
        if (paramInt > DETAILS_MODULES.indexOf(DiscoveryBarModule.class)) {
          k--;
        }
        if (k > DETAILS_MODULES_COMBINED_TITLE.indexOf(CombinedTitleModule.class)) {
          k++;
        }
        return k;
      }
    } while ((!paramBoolean1) || (paramBoolean2));
    int i = paramInt;
    if (paramInt > DETAILS_MODULES_COMBINED_TITLE.indexOf(CombinedTitleModule.class)) {
      i--;
    }
    Integer[] arrayOfInteger = new Integer[3];
    arrayOfInteger[0] = Integer.valueOf(DETAILS_MODULES.indexOf(TitleModule.class));
    arrayOfInteger[1] = Integer.valueOf(DETAILS_MODULES.indexOf(WarningMessageModule.class));
    arrayOfInteger[2] = Integer.valueOf(DETAILS_MODULES.indexOf(DiscoveryBarModule.class));
    ArrayList localArrayList = Lists.newArrayList(arrayOfInteger);
    Collections.sort(localArrayList);
    for (int j = 0; j < localArrayList.size(); j++) {
      if (i > ((Integer)localArrayList.get(j)).intValue()) {
        i++;
      }
    }
    return i;
  }
  
  public static void remapModulesAfterRotation(List<Class> paramList, List<FinskyModule.ModuleData> paramList1, Document paramDocument, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (isAntennaPage(paramDocument)) {}
    for (;;)
    {
      return;
      int i = DETAILS_MODULES.indexOf(TitleModule.class);
      int j = DETAILS_MODULES.indexOf(WarningMessageModule.class);
      int k = DETAILS_MODULES.indexOf(DiscoveryBarModule.class);
      int m = DETAILS_MODULES_COMBINED_TITLE.indexOf(CombinedTitleModule.class);
      if ((!paramBoolean1) && (paramBoolean2))
      {
        CombinedTitleModule.CombinedTitleModuleData localCombinedTitleModuleData2 = new CombinedTitleModule.CombinedTitleModuleData();
        localCombinedTitleModuleData2.titleModuleData = ((TitleModule.TitleModuleData)paramList1.get(i));
        localCombinedTitleModuleData2.warningMessageModuleData = ((WarningMessageModule.WarningMessageModuleData)paramList1.get(j));
        localCombinedTitleModuleData2.discoveryBarModuleData = ((DiscoveryBarModule.DiscoveryBarModuleData)paramList1.get(k));
        Integer[] arrayOfInteger = new Integer[3];
        arrayOfInteger[0] = Integer.valueOf(i);
        arrayOfInteger[1] = Integer.valueOf(j);
        arrayOfInteger[2] = Integer.valueOf(k);
        ArrayList localArrayList2 = Lists.newArrayList(arrayOfInteger);
        Collections.sort(localArrayList2);
        Collections.reverse(localArrayList2);
        for (int i2 = 0; i2 < localArrayList2.size(); i2++)
        {
          int i3 = ((Integer)localArrayList2.get(i2)).intValue();
          paramList.remove(i3);
          paramList1.remove(i3);
        }
        paramList1.add(m, localCombinedTitleModuleData2);
        paramList.add(m, CombinedTitleModule.class);
      }
      if ((paramBoolean1) && (!paramBoolean2))
      {
        CombinedTitleModule.CombinedTitleModuleData localCombinedTitleModuleData1 = (CombinedTitleModule.CombinedTitleModuleData)paramList1.get(m);
        if (localCombinedTitleModuleData1 == null) {
          localCombinedTitleModuleData1 = new CombinedTitleModule.CombinedTitleModuleData();
        }
        paramList.remove(m);
        paramList1.remove(m);
        HashMap localHashMap1 = new HashMap();
        HashMap localHashMap2 = new HashMap();
        localHashMap1.put(Integer.valueOf(i), TitleModule.class);
        localHashMap1.put(Integer.valueOf(j), WarningMessageModule.class);
        localHashMap1.put(Integer.valueOf(k), DiscoveryBarModule.class);
        localHashMap2.put(Integer.valueOf(i), localCombinedTitleModuleData1.titleModuleData);
        localHashMap2.put(Integer.valueOf(j), localCombinedTitleModuleData1.warningMessageModuleData);
        localHashMap2.put(Integer.valueOf(k), localCombinedTitleModuleData1.discoveryBarModuleData);
        ArrayList localArrayList1 = Lists.newArrayList(localHashMap2.keySet());
        Collections.sort(localArrayList1);
        for (int n = 0; n < localArrayList1.size(); n++)
        {
          int i1 = ((Integer)localArrayList1.get(n)).intValue();
          paramList.add(i1, localHashMap1.get(Integer.valueOf(i1)));
          paramList1.add(i1, localHashMap2.get(Integer.valueOf(i1)));
        }
      }
    }
  }
  
  private static class DynamicModulesPlaceholder {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ModulesManager
 * JD-Core Version:    0.7.0.1
 */