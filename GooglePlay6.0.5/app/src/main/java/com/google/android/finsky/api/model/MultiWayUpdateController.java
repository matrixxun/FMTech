package com.google.android.finsky.api.model;

import android.accounts.Account;
import android.content.Context;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Objects;
import com.google.android.finsky.utils.PurchaseInitiator;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class MultiWayUpdateController
  extends MultiDfeBulkDetails
{
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  private static Set<String> sPackageBlacklist = null;
  private List<AppToAccountTagMatch> mAppToAccountTagMatchList = null;
  public List<Document> mCollatedDocuments;
  private DfeApiProvider mDfeApiProvider;
  private InstallerDataStore mInstallerDataStore;
  private boolean mIsAutoUpdate;
  private boolean mIsAvengerDevice;
  private Libraries mLibraries;
  
  public MultiWayUpdateController(Context paramContext, InstallerDataStore paramInstallerDataStore, Libraries paramLibraries, DfeApiProvider paramDfeApiProvider, boolean paramBoolean)
  {
    this.mInstallerDataStore = paramInstallerDataStore;
    this.mLibraries = paramLibraries;
    this.mDfeApiProvider = paramDfeApiProvider;
    this.mIsAvengerDevice = AccountHandler.isAvengerDevice(paramContext);
    this.mIsAutoUpdate = paramBoolean;
  }
  
  private void addAutoAcquireAppsToRequests(Map<String, List<String>> paramMap)
  {
    if (this.mAppToAccountTagMatchList != null)
    {
      Iterator localIterator = this.mAppToAccountTagMatchList.iterator();
      while (localIterator.hasNext())
      {
        AppToAccountTagMatch localAppToAccountTagMatch = (AppToAccountTagMatch)localIterator.next();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localAppToAccountTagMatch.docId;
        arrayOfObject[1] = FinskyLog.scrubPii(localAppToAccountTagMatch.accountName);
        FinskyLog.d("Add %s to check for potential auto-acquire by %s", arrayOfObject);
        ((List)paramMap.get(localAppToAccountTagMatch.accountName)).add(localAppToAccountTagMatch.docId);
      }
    }
  }
  
  private static void blacklistPackage(String paramString)
  {
    if (sPackageBlacklist == null) {
      sPackageBlacklist = new HashSet();
    }
    sPackageBlacklist.add(paramString);
  }
  
  private void captureUpdateAccount(String paramString1, String paramString2)
  {
    InstallerDataStore.InstallerData localInstallerData = this.mInstallerDataStore.get(paramString1);
    if (localInstallerData == null) {}
    for (Object localObject = null;; localObject = localInstallerData.accountForUpdate)
    {
      if (!Objects.equal(localObject, paramString2))
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = FinskyLog.scrubPii(paramString2);
        arrayOfObject[1] = paramString1;
        FinskyLog.d("Capture account %s for next update of %s", arrayOfObject);
        this.mInstallerDataStore.setAccountForUpdate(paramString1, paramString2);
      }
      return;
    }
  }
  
  private void collectAutoAcquireApps(Map<String, List<String>> paramMap, AccountAutoAcquireTags[] paramArrayOfAccountAutoAcquireTags)
  {
    Iterator localIterator1 = paramMap.entrySet().iterator();
    label61:
    label320:
    label326:
    if (localIterator1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator1.next();
      String str1 = (String)localEntry.getKey();
      Iterator localIterator2 = ((List)localEntry.getValue()).iterator();
      label200:
      label332:
      label336:
      for (;;)
      {
        String str2;
        int j;
        AccountAutoAcquireTags localAccountAutoAcquireTags;
        int m;
        int i1;
        if (localIterator2.hasNext())
        {
          str2 = (String)localIterator2.next();
          if ((sPackageBlacklist != null) && (sPackageBlacklist.contains(str2))) {
            continue;
          }
          InstallerDataStore.InstallerData localInstallerData = this.mInstallerDataStore.get(str2);
          if ((localInstallerData == null) || ((0x1 & localInstallerData.persistentFlags) == 0)) {
            continue;
          }
          String[] arrayOfString1 = localInstallerData.getAutoAcquireTags();
          if (arrayOfString1.length == 0) {
            continue;
          }
          int i = paramArrayOfAccountAutoAcquireTags.length;
          j = 0;
          if (j >= i) {
            break label332;
          }
          localAccountAutoAcquireTags = paramArrayOfAccountAutoAcquireTags[j];
          String[] arrayOfString2 = localAccountAutoAcquireTags.tags;
          int k = arrayOfString2.length;
          m = 0;
          if (m >= k) {
            break label326;
          }
          String str4 = arrayOfString2[m];
          int n = arrayOfString1.length;
          i1 = 0;
          if (i1 >= n) {
            break label320;
          }
          if (!arrayOfString1[i1].equals(str4)) {
            break label314;
          }
        }
        for (String str3 = localAccountAutoAcquireTags.accountName;; str3 = null)
        {
          if ((str3 == null) || (str3.equals(str1))) {
            break label336;
          }
          List localList = (List)paramMap.get(str3);
          if ((localList == null) || (localList.contains(str2))) {
            break label61;
          }
          if (this.mAppToAccountTagMatchList == null) {
            this.mAppToAccountTagMatchList = new ArrayList();
          }
          this.mAppToAccountTagMatchList.add(new AppToAccountTagMatch(str2, str3));
          break label61;
          break;
          i1++;
          break label200;
          m++;
          break label178;
          j++;
          break label150;
        }
      }
    }
    label150:
    label178:
    label314:
    return;
  }
  
  private Map<String, AccountVersionDocument> generatePackageMap(boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = this.mRequests.iterator();
    DfeBulkDetails localDfeBulkDetails;
    List localList;
    if (localIterator1.hasNext())
    {
      localDfeBulkDetails = (DfeBulkDetails)localIterator1.next();
      localList = localDfeBulkDetails.getDocuments();
      if (localList == null) {
        localHashMap = null;
      }
    }
    for (;;)
    {
      return localHashMap;
      localArrayList.addAll(localDfeBulkDetails.getMissingDocIds());
      String str1 = localDfeBulkDetails.mDfeApi.getAccountName();
      Iterator localIterator3 = localList.iterator();
      while (localIterator3.hasNext())
      {
        Document localDocument = (Document)localIterator3.next();
        String str2 = localDocument.getAppDetails().packageName;
        int i = localDocument.getAppDetails().versionCode;
        AccountVersionDocument localAccountVersionDocument2 = (AccountVersionDocument)localHashMap.get(str2);
        if (paramBoolean == isAutoAcquireAttempt(str2, str1)) {
          if (localAccountVersionDocument2 == null)
          {
            localHashMap.put(str2, new AccountVersionDocument(str1, i, localDocument));
          }
          else
          {
            if (i != localAccountVersionDocument2.versionCode) {
              localAccountVersionDocument2.needAccountForUpdate = true;
            }
            if (i > localAccountVersionDocument2.versionCode)
            {
              localAccountVersionDocument2.versionCode = i;
              localAccountVersionDocument2.account = str1;
              localAccountVersionDocument2.document = localDocument;
            }
          }
        }
      }
      break;
      Iterator localIterator2 = localArrayList.iterator();
      while (localIterator2.hasNext())
      {
        AccountVersionDocument localAccountVersionDocument1 = (AccountVersionDocument)localHashMap.get((String)localIterator2.next());
        if (localAccountVersionDocument1 != null) {
          localAccountVersionDocument1.needAccountForUpdate = true;
        }
      }
    }
  }
  
  private boolean isAutoAcquireAttempt(String paramString1, String paramString2)
  {
    if (this.mAppToAccountTagMatchList == null) {}
    for (;;)
    {
      return false;
      for (int i = 0; i < this.mAppToAccountTagMatchList.size(); i++)
      {
        AppToAccountTagMatch localAppToAccountTagMatch = (AppToAccountTagMatch)this.mAppToAccountTagMatchList.get(i);
        if ((localAppToAccountTagMatch.docId.equals(paramString1)) && (localAppToAccountTagMatch.accountName.equals(paramString2))) {
          return true;
        }
      }
    }
  }
  
  public final void addRequests(Map<String, List<String>> paramMap)
  {
    List localList1 = this.mLibraries.getAccountLibraries();
    if (localList1.size() > 1)
    {
      Iterator localIterator2 = localList1.iterator();
      localArrayList = null;
      while (localIterator2.hasNext())
      {
        AccountLibrary localAccountLibrary = (AccountLibrary)localIterator2.next();
        String[] arrayOfString = localAccountLibrary.getAutoAcquireTags();
        if ((arrayOfString != null) && (arrayOfString.length > 0))
        {
          if (localArrayList == null) {
            localArrayList = new ArrayList();
          }
          localArrayList.add(new AccountAutoAcquireTags(localAccountLibrary.mAccount.name, arrayOfString));
        }
      }
    }
    ArrayList localArrayList = null;
    if (localArrayList == null) {}
    for (AccountAutoAcquireTags[] arrayOfAccountAutoAcquireTags = null;; arrayOfAccountAutoAcquireTags = (AccountAutoAcquireTags[])localArrayList.toArray(new AccountAutoAcquireTags[localArrayList.size()]))
    {
      if (arrayOfAccountAutoAcquireTags != null)
      {
        collectAutoAcquireApps(paramMap, arrayOfAccountAutoAcquireTags);
        addAutoAcquireAppsToRequests(paramMap);
      }
      Iterator localIterator1 = paramMap.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        String str = (String)localEntry.getKey();
        List localList2 = (List)localEntry.getValue();
        if (localList2.size() != 0) {
          addRequest(this.mDfeApiProvider.getDfeApi(str), localList2, this.mIsAutoUpdate);
        }
      }
    }
  }
  
  protected final void collateResponses()
  {
    if (this.mCollatedDocuments != null) {
      FinskyLog.w("Unexpected repeat collation", new Object[0]);
    }
    Map localMap1 = generatePackageMap(false);
    if (localMap1 == null) {}
    for (;;)
    {
      return;
      this.mCollatedDocuments = Lists.newArrayList(localMap1.size());
      Iterator localIterator1 = localMap1.entrySet().iterator();
      if (localIterator1.hasNext())
      {
        Map.Entry localEntry2 = (Map.Entry)localIterator1.next();
        String str3 = (String)localEntry2.getKey();
        AccountVersionDocument localAccountVersionDocument3 = (AccountVersionDocument)localEntry2.getValue();
        Document localDocument = localAccountVersionDocument3.document;
        String str4 = localDocument.getAppDetails().packageName;
        boolean bool = localDocument.getAppDetails().variesByAccount;
        int j;
        label132:
        InstallerDataStore.InstallerData localInstallerData1;
        int k;
        label153:
        InstallerDataStore.InstallerData localInstallerData2;
        String[] arrayOfString2;
        if (bool)
        {
          j = 1;
          localInstallerData1 = this.mInstallerDataStore.get(str4);
          if (localInstallerData1 != null) {
            break label398;
          }
          k = 0;
          if (j != (k & 0x1))
          {
            Object[] arrayOfObject6 = new Object[2];
            arrayOfObject6[0] = str4;
            arrayOfObject6[1] = Boolean.valueOf(bool);
            FinskyLog.d("Change varies-by-account for %s to %b", arrayOfObject6);
            if ((localInstallerData1 != null) || (j != 0))
            {
              int m = j | k & 0xFFFFFFFE;
              this.mInstallerDataStore.setPersistentFlags(str4, m);
            }
          }
          AppDetails localAppDetails = localDocument.getAppDetails();
          String str5 = localAppDetails.packageName;
          String[] arrayOfString1 = localAppDetails.autoAcquireFreeAppIfHigherVersionAvailableTag;
          localInstallerData2 = this.mInstallerDataStore.get(str5);
          if (localInstallerData2 != null) {
            break label408;
          }
          arrayOfString2 = EMPTY_STRING_ARRAY;
          label268:
          if (!Arrays.equals(arrayOfString1, arrayOfString2))
          {
            Object[] arrayOfObject5 = new Object[3];
            arrayOfObject5[0] = str5;
            arrayOfObject5[1] = Utils.commaPackStrings(arrayOfString2);
            arrayOfObject5[2] = Utils.commaPackStrings(arrayOfString1);
            FinskyLog.d("Change auto-acquire tags for %s from %s to %s", arrayOfObject5);
            if ((localInstallerData2 != null) || (arrayOfString1.length != 0)) {
              this.mInstallerDataStore.setAutoAcquireTags(str5, arrayOfString1);
            }
          }
          if ((!this.mIsAvengerDevice) && ((!localDocument.getAppDetails().variesByAccount) || (!localAccountVersionDocument3.needAccountForUpdate))) {
            break label418;
          }
          captureUpdateAccount(str3, localAccountVersionDocument3.account);
        }
        for (;;)
        {
          this.mCollatedDocuments.add(localDocument);
          break;
          j = 0;
          break label132;
          label398:
          k = localInstallerData1.persistentFlags;
          break label153;
          label408:
          arrayOfString2 = localInstallerData2.getAutoAcquireTags();
          break label268;
          label418:
          captureUpdateAccount(str3, null);
        }
      }
      Map localMap2 = generatePackageMap(true);
      if ((localMap2 != null) && (localMap2.size() != 0))
      {
        Iterator localIterator2 = localMap2.entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
          String str1 = (String)localEntry1.getKey();
          int i = ((AccountVersionDocument)localEntry1.getValue()).versionCode;
          String str2 = ((AccountVersionDocument)localEntry1.getValue()).account;
          Common.Offer localOffer = ((AccountVersionDocument)localEntry1.getValue()).document.getOffer(1);
          if ((localOffer != null) && (localOffer.checkoutFlowRequired))
          {
            Object[] arrayOfObject4 = new Object[2];
            arrayOfObject4[0] = str1;
            arrayOfObject4[1] = FinskyLog.scrubPii(str2);
            FinskyLog.d("Skipping proposed auto-acquire - Unexpected checkoutFlowRequired=true for %s by %s", arrayOfObject4);
            blacklistPackage(str1);
          }
          else
          {
            AccountVersionDocument localAccountVersionDocument1 = (AccountVersionDocument)localMap1.get(str1);
            if ((localAccountVersionDocument1 != null) && (i > localAccountVersionDocument1.versionCode))
            {
              Object[] arrayOfObject2 = new Object[3];
              arrayOfObject2[0] = str1;
              arrayOfObject2[1] = FinskyLog.scrubPii(str2);
              arrayOfObject2[2] = Integer.valueOf(i);
              FinskyLog.d("Proposed auto-acquire of %s by %s revealed higher version %d", arrayOfObject2);
              AccountVersionDocument localAccountVersionDocument2 = (AccountVersionDocument)localEntry1.getValue();
              Account localAccount = AccountHandler.findAccount(localAccountVersionDocument2.account, FinskyApp.get());
              if (LibraryUtils.isOwned(localAccountVersionDocument2.document, this.mLibraries.getAccountLibrary(localAccount)))
              {
                Object[] arrayOfObject3 = new Object[2];
                arrayOfObject3[0] = str1;
                arrayOfObject3[1] = FinskyLog.scrubPii(localAccountVersionDocument2.account);
                FinskyLog.w("Skip auto-acquire of %s by %s because already owned", arrayOfObject3);
                blacklistPackage(str1);
              }
              for (;;)
              {
                PlayStore.AppData localAppData = new PlayStore.AppData();
                localAppData.oldVersion = localAccountVersionDocument1.versionCode;
                localAppData.version = i;
                FinskyApp.get().getEventLogger(localAccountVersionDocument1.account).logBackgroundEvent(116, str1, "auto-acquire", 0, null, localAppData);
                FinskyApp.get().getEventLogger(str2).logBackgroundEvent(117, str1, "auto-acquire", 0, null, localAppData);
                break;
                PurchaseInitiator.makeFreePurchase(localAccount, localAccountVersionDocument2.document, 1, null, null, false, false);
                blacklistPackage(str1);
              }
            }
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = str1;
            arrayOfObject1[1] = FinskyLog.scrubPii(str2);
            FinskyLog.d("Skipping proposed auto-acquire of %s by %s", arrayOfObject1);
            blacklistPackage(str1);
          }
        }
      }
    }
  }
  
  public final void selectAccountsForUpdateChecks(String paramString, Map<String, List<String>> paramMap)
  {
    if (paramMap.size() <= 1) {}
    for (;;)
    {
      return;
      List localList1 = (List)paramMap.get(paramString);
      if (localList1 == null) {
        localList1 = (List)paramMap.get((String)paramMap.keySet().iterator().next());
      }
      HashSet localHashSet = new HashSet();
      Iterator localIterator1 = localList1.iterator();
      label162:
      while (localIterator1.hasNext())
      {
        String str2 = (String)localIterator1.next();
        if (!GmsCoreHelper.isGmsCore(str2))
        {
          InstallerDataStore.InstallerData localInstallerData2 = this.mInstallerDataStore.get(str2);
          if ((this.mIsAvengerDevice) || ((localInstallerData2 != null) && ((0x1 & localInstallerData2.persistentFlags) != 0))) {}
          for (int j = 1;; j = 0)
          {
            if (j != 0) {
              break label162;
            }
            localHashSet.add(str2);
            break;
          }
        }
      }
      Iterator localIterator2 = paramMap.entrySet().iterator();
      while (localIterator2.hasNext())
      {
        List localList2 = (List)((Map.Entry)localIterator2.next()).getValue();
        if (localList2 != localList1)
        {
          Iterator localIterator3 = localList2.iterator();
          while (localIterator3.hasNext())
          {
            String str1 = (String)localIterator3.next();
            if (!GmsCoreHelper.isGmsCore(str1))
            {
              InstallerDataStore.InstallerData localInstallerData1 = this.mInstallerDataStore.get(str1);
              if ((this.mIsAvengerDevice) || ((localInstallerData1 != null) && ((0x1 & localInstallerData1.persistentFlags) != 0))) {}
              for (int i = 1;; i = 0)
              {
                if (i != 0) {
                  break label321;
                }
                if (!localHashSet.contains(str1)) {
                  break label323;
                }
                localIterator3.remove();
                break;
              }
              label321:
              continue;
              label323:
              localHashSet.add(str1);
            }
          }
          if (localList2.isEmpty()) {
            localIterator2.remove();
          }
        }
      }
    }
  }
  
  protected static final class AccountAutoAcquireTags
  {
    final String accountName;
    final String[] tags;
    
    public AccountAutoAcquireTags(String paramString, String[] paramArrayOfString)
    {
      this.accountName = paramString;
      this.tags = paramArrayOfString;
    }
  }
  
  protected static final class AccountVersionDocument
  {
    protected String account;
    protected Document document;
    protected boolean needAccountForUpdate;
    protected int versionCode;
    
    public AccountVersionDocument(String paramString, int paramInt, Document paramDocument)
    {
      this.account = paramString;
      this.versionCode = paramInt;
      this.document = paramDocument;
      this.needAccountForUpdate = false;
    }
  }
  
  private static final class AppToAccountTagMatch
  {
    public final String accountName;
    public final String docId;
    
    public AppToAccountTagMatch(String paramString1, String paramString2)
    {
      this.docId = paramString1;
      this.accountName = paramString2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.MultiWayUpdateController
 * JD-Core Version:    0.7.0.1
 */