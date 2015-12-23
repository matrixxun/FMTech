package com.google.android.finsky.widget.consumption;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.RemoteViews;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.services.ConsumptionAppDataCache;
import com.google.android.finsky.services.ConsumptionAppDoc;
import com.google.android.finsky.services.LoadConsumptionDataService;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.widget.BaseWidgetProvider;
import com.google.android.finsky.widget.TrampolineActivity;
import com.google.android.finsky.widget.WidgetTypeMap;
import com.google.android.finsky.widget.WidgetUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

@TargetApi(16)
public class NowPlayingWidgetProvider
  extends BaseWidgetProvider
{
  private static final int[] ACCESSIBILITY_OVERLAY_IDS;
  private static final int[] BACKENDS;
  private static final File BG_IMAGE_OVERRIDE;
  private static final String BG_OVERRIDE_IMAGE_PATH;
  private static final int[] CONTAINERS;
  private static final int[] IMAGE_IDS;
  private static final Block[] PORTRAIT_BLOCKS;
  private static final Block PORTRAIT_LARGE_REPLACEMENT;
  private static final Block[] SQUARE_BLOCKS;
  public static BatchedImageLoader mImageLoader;
  private static final Random sRandom;
  private static int[] sSupportedBackendIds;
  private final SparseIntArray mRowStartCounts = new SparseIntArray();
  
  static
  {
    String str = (String)G.myLibraryWidgetBackgroundImageLocation.get();
    BG_OVERRIDE_IMAGE_PATH = str;
    if (TextUtils.isEmpty(str)) {}
    for (File localFile = null;; localFile = new File(BG_OVERRIDE_IMAGE_PATH))
    {
      BG_IMAGE_OVERRIDE = localFile;
      sRandom = new Random();
      sSupportedBackendIds = new int[] { 2, 4, 1, 6 };
      IMAGE_IDS = new int[] { 2131755761, 2131755765, 2131755763, 2131755767 };
      ACCESSIBILITY_OVERLAY_IDS = new int[] { 2131755762, 2131755766, 2131755764, 2131755768 };
      BACKENDS = new int[] { 1, 4, 2, 6 };
      CONTAINERS = new int[] { 2131755780, 2131755782, 2131755781, 2131755783 };
      PORTRAIT_LARGE_REPLACEMENT = new Block(2130968864).sized(2131493045, 2131493044).hosting(4, 2131493047, 2131493046);
      Block[] arrayOfBlock1 = new Block[5];
      Block localBlock1 = new Block(2130968861).sized(2131493045, 2131493044);
      localBlock1.mLastInRowReplacement = PORTRAIT_LARGE_REPLACEMENT;
      localBlock1.mSupportsMetadata = true;
      arrayOfBlock1[0] = localBlock1;
      arrayOfBlock1[1] = new Block(2130968869).sized(2131493047, 2131493044).hosting(2, 2131493047, 2131493046);
      arrayOfBlock1[2] = new Block(2130968870).sized(2131493049, 2131493044).hosting(3, 2131493049, 2131493048);
      arrayOfBlock1[3] = new Block(2130968862).sized(2131493047, 2131493046);
      arrayOfBlock1[4] = new Block(2130968863).sized(2131493049, 2131493048);
      PORTRAIT_BLOCKS = arrayOfBlock1;
      Block[] arrayOfBlock2 = new Block[10];
      Block localBlock2 = new Block(2130968867).sized(2131493052, 2131493052);
      localBlock2.mMaxRowStartCount = 1;
      localBlock2.mSupportsMetadata = true;
      arrayOfBlock2[0] = localBlock2;
      Block localBlock3 = new Block(2130968865).sized(2131493050, 2131493050);
      localBlock3.mSupportsMetadata = true;
      arrayOfBlock2[1] = localBlock3;
      arrayOfBlock2[2] = new Block(2130968871).sized(2131493051, 2131493052).hosting(2, 2131493051, 2131493051);
      Block localBlock4 = new Block(2130968866).sized(2131493051, 2131493051);
      localBlock4.mMaxRowStartCount = 1;
      localBlock4.mSupportsMetadata = true;
      arrayOfBlock2[3] = localBlock4;
      arrayOfBlock2[4] = new Block(2130968873).sized(2131493053, 2131493052).hosting(4, 2131493053, 2131493053);
      arrayOfBlock2[5] = new Block(2130968874).sized(2131493053, 2131493050).hosting(3, 2131493053, 2131493053);
      arrayOfBlock2[6] = new Block(2130968872).sized(2131493053, 2131493051).hosting(2, 2131493053, 2131493053);
      Block localBlock5 = new Block(2130968875).sized(2131493050, 2131493052);
      localBlock5.mNumImages = 4;
      Block localBlock6 = localBlock5.withChild(0, 2131493050, 2131493052).withChild(1, 2131493053, 2131493053).withChild(2, 2131493053, 2131493053).withChild(3, 2131493053, 2131493053);
      localBlock6.mMaxRowStartCount = 0;
      arrayOfBlock2[7] = localBlock6;
      Block localBlock7 = new Block(2130968876).sized(2131493051, 2131493050);
      localBlock7.mNumImages = 3;
      Block localBlock8 = localBlock7.withChild(0, 2131493051, 2131493051).withChild(1, 2131493053, 2131493053).withChild(2, 2131493053, 2131493053);
      localBlock8.mMaxRowStartCount = 0;
      arrayOfBlock2[8] = localBlock8;
      Block localBlock9 = new Block(2130968868).sized(2131493053, 2131493053);
      localBlock9.mSupportsMetadata = true;
      arrayOfBlock2[9] = localBlock9;
      SQUARE_BLOCKS = arrayOfBlock2;
      return;
    }
  }
  
  private static RemoteViews generateBaseTree(Context paramContext, boolean paramBoolean)
  {
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130968877);
    localRemoteViews.setTextViewText(2131755778, "");
    localRemoteViews.setImageViewResource(2131755777, getHeaderIconRes(0));
    localRemoteViews.setContentDescription(2131755777, "");
    int i = getEmptyBackgroundRes(0);
    if ((paramBoolean) && (i != 0)) {
      localRemoteViews.setImageViewResource(2131755769, i);
    }
    localRemoteViews.removeAllViews(2131755771);
    localRemoteViews.setViewVisibility(2131755775, 8);
    localRemoteViews.setViewVisibility(2131755258, 8);
    localRemoteViews.setViewVisibility(2131755784, 8);
    localRemoteViews.setViewVisibility(2131755772, 4);
    return localRemoteViews;
  }
  
  private static RemoteViews generateDisabledState$672be14b(Context paramContext, int paramInt)
  {
    RemoteViews localRemoteViews1 = generateBaseTree(paramContext, true);
    RemoteViews localRemoteViews2 = new RemoteViews(paramContext.getPackageName(), 2130968878);
    try
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      String str2 = paramContext.getString(2131362920, new Object[] { localPackageManager.getApplicationInfo(IntentUtils.getPackageName(paramInt), 0).loadLabel(localPackageManager).toString() });
      str1 = str2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        String str1 = "";
      }
    }
    localRemoteViews2.setTextViewText(2131755274, str1);
    localRemoteViews1.addView(2131755771, localRemoteViews2);
    localRemoteViews1.setOnClickPendingIntent(2131755775, EnableAppReceiver.getEnableIntent(paramContext, paramInt));
    localRemoteViews1.setViewVisibility(2131755775, 0);
    useCustomStylingIfNecessary(paramContext, localRemoteViews1, paramInt);
    return localRemoteViews1;
  }
  
  private ViewTreeWrapper generateViewTree(int paramInt1, int paramInt2, DfeToc paramDfeToc, Context paramContext, Map<ImageBatch.ImageSpec, Bitmap> paramMap, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt1 = getBackends(paramInt1);
    ConsumptionAppDataCache localConsumptionAppDataCache = ConsumptionAppDataCache.get();
    int i = arrayOfInt1.length;
    int[] arrayOfInt2 = new int[i];
    int j = 0;
    int k = 0;
    int i14;
    int i16;
    label69:
    int i15;
    if (k < i)
    {
      i14 = arrayOfInt1[k];
      if (!localConsumptionAppDataCache.hasConsumptionAppData(i14))
      {
        Utils.ensureOnMainThread();
        if (localConsumptionAppDataCache.getDataStateForBackend(i14) == 1)
        {
          i16 = 1;
          if (i16 == 0) {
            break label123;
          }
        }
      }
      else
      {
        if (!FinskyLog.DEBUG) {
          break label1291;
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i14);
        FinskyLog.v("Data for [%s] is available or loading, skipping", arrayOfObject);
        i15 = j;
      }
    }
    for (;;)
    {
      k++;
      j = i15;
      break;
      i16 = 0;
      break label69;
      label123:
      Utils.ensureOnMainThread();
      if (!localConsumptionAppDataCache.hasConsumptionAppData(i14)) {
        localConsumptionAppDataCache.mDataLoadState.put(i14, Integer.valueOf(1));
      }
      i15 = j + 1;
      arrayOfInt2[j] = i14;
      continue;
      if (j > 0)
      {
        int[] arrayOfInt3 = new int[j];
        System.arraycopy(arrayOfInt2, 0, arrayOfInt3, 0, j);
        LoadConsumptionDataService.scheduleAlarmForUpdate(paramContext, arrayOfInt3);
      }
      RemoteViews localRemoteViews1 = generateBaseTree(paramContext, false);
      ViewTreeWrapper localViewTreeWrapper1 = new ViewTreeWrapper();
      localViewTreeWrapper1.mRemoteViews = localRemoteViews1;
      String str = getTitle(paramDfeToc, paramContext, paramInt1).toUpperCase();
      localRemoteViews1.setTextViewText(2131755778, str);
      localRemoteViews1.setImageViewResource(2131755777, getHeaderIconRes(paramInt1));
      Intent localIntent1;
      label299:
      NowPlayingArranger.Arrangement localArrangement;
      int n;
      switch (paramInt1)
      {
      case 5: 
      default: 
        localIntent1 = null;
        if (localIntent1 != null)
        {
          localRemoteViews1.setOnClickPendingIntent(2131755779, PendingIntent.getActivity(paramContext, paramInt2, localIntent1, 0));
          localRemoteViews1.setViewVisibility(2131755779, 0);
          localRemoteViews1.setContentDescription(2131755779, str);
        }
        int m = paramContext.getResources().getInteger(2131623951);
        localArrangement = NowPlayingArranger.arrange$568a224(NowPlayingScorer.score(getConsumptionDocLists(paramInt1), m, System.currentTimeMillis()));
        n = localArrangement.quadrantToData.length;
        if (localArrangement.layoutVariant != 0) {
          break;
        }
      }
      for (int i1 = 0;; i1 = 1)
      {
        localObject = localRemoteViews1;
        if (n <= 1) {
          break label546;
        }
        switch (localArrangement.quadrantToData.length)
        {
        default: 
          throw new IllegalArgumentException("Invalid count: " + localArrangement.quadrantToData.length);
          localIntent1 = IntentUtils.buildConsumptionAppLaunchIntent(paramContext, paramInt1, FinskyApp.get().getCurrentAccountName());
          break label299;
          localIntent1 = TrampolineActivity.getLaunchIntent(paramContext, MyLibraryTrampoline.class, paramInt2);
          break label299;
        }
      }
      int i13 = 0;
      localRemoteViews1.removeAllViews(2131755771);
      RemoteViews localRemoteViews2 = new RemoteViews(paramContext.getPackageName(), i13);
      localRemoteViews1.addView(2131755771, localRemoteViews2);
      Object localObject = localRemoteViews2;
      label546:
      localViewTreeWrapper1.showBackground = false;
      localViewTreeWrapper1.showEmptyBackground = true;
      int i2 = 0;
      int i3 = 0;
      label564:
      if (i3 < n)
      {
        int i6 = paramInt3;
        int i7 = paramInt4;
        int i8 = 2131755771;
        int i9 = localArrangement.quadrantToData.length;
        int i10 = localArrangement.layoutVariant;
        int i11 = NowPlayingArranger.Arrangement.getLocation(i9, i3, i10);
        int i12;
        if (n > 1)
        {
          i8 = CONTAINERS[i3];
          i6 /= 2;
          if ((n == 4) || ((n == 3) && (i3 != i1)))
          {
            if (paramInt4 / i6 <= 2.2F) {
              break label965;
            }
            if ((i11 & 0x1) == 0) {
              break label938;
            }
            if ((i11 & 0x4) == 0) {
              break label927;
            }
            i12 = paramInt4 / 3;
          }
        }
        for (;;)
        {
          i7 = i12 + i2;
          ConsumptionAppDocList localConsumptionAppDocList = localArrangement.quadrantToData[i3];
          this.mRowStartCounts.clear();
          new WidgetLayout(null, true, i7);
          WidgetLayout localWidgetLayout = generateWidgetLayout(paramContext, localConsumptionAppDocList.size(), localConsumptionAppDocList.mBackend, i6, i7);
          ViewTreeWrapper localViewTreeWrapper2 = populateWidget$74ef0b1(paramContext, (RemoteViews)localObject, i8, i11, localWidgetLayout, localConsumptionAppDocList, paramMap);
          localViewTreeWrapper2.showBackground = localWidgetLayout.showBackground;
          localViewTreeWrapper2.showEmptyBackground = localConsumptionAppDocList.isEmpty();
          localViewTreeWrapper2.heightRemaining = localWidgetLayout.heightRemaining;
          if ((n == 4) || ((n == 3) && (i3 != i1))) {
            i2 = localViewTreeWrapper2.heightRemaining;
          }
          localViewTreeWrapper1.showBackground = ((byte)(localViewTreeWrapper2.showBackground | localViewTreeWrapper1.showBackground));
          localViewTreeWrapper1.showEmptyBackground = ((byte)(localViewTreeWrapper2.showEmptyBackground & localViewTreeWrapper1.showEmptyBackground));
          if (!localViewTreeWrapper2.mLoadedSuccessfully) {
            localViewTreeWrapper1.mLoadedSuccessfully = false;
          }
          localViewTreeWrapper1.mUris.addAll(localViewTreeWrapper2.mUris);
          i3++;
          break label564;
          i13 = 2130968887;
          break;
          if (localArrangement.layoutVariant == 0)
          {
            i13 = 2130968885;
            break;
          }
          i13 = 2130968886;
          break;
          i13 = 2130968881;
          break;
          i12 = paramInt4 * 2 / 3;
          continue;
          if ((i11 & 0x4) != 0) {}
          for (i12 = paramInt4 * 2 / 3;; i12 = paramInt4 / 3) {
            break;
          }
          label965:
          i12 = paramInt4 / 2;
        }
      }
      label927:
      label938:
      int i4;
      int i5;
      if (localViewTreeWrapper1.showBackground) {
        switch (paramInt1)
        {
        case 3: 
        case 5: 
        default: 
          i4 = 0;
          if ((!useCustomStylingIfNecessary(paramContext, localRemoteViews1, paramInt1)) && (i4 != 0)) {
            localRemoteViews1.setImageViewResource(2131755769, i4);
          }
          if ((localViewTreeWrapper1.showEmptyBackground) && (paramInt1 == 0) && (paramInt3 >= WidgetUtils.getDips(paramContext, 2131493029)))
          {
            i5 = 1;
            label1080:
            if (i5 == 0) {
              break label1268;
            }
            localRemoteViews1.setViewVisibility(2131755772, 0);
          }
          break;
        }
      }
      for (;;)
      {
        if (paramInt1 != 0) {
          break label1279;
        }
        updateHotseat(paramContext, paramDfeToc, localRemoteViews1, paramInt2, paramInt3, paramInt4);
        return localViewTreeWrapper1;
        i4 = 2130837586;
        break;
        i4 = 2130837587;
        break;
        i4 = 2130837588;
        break;
        i4 = 2130837589;
        break;
        i4 = 2130837590;
        break;
        boolean bool = localViewTreeWrapper1.showEmptyBackground;
        i4 = 0;
        if (!bool) {
          break;
        }
        if (paramInt1 != 0)
        {
          i4 = getEmptyBackgroundRes(paramInt1);
          Intent localIntent2 = IntentUtils.buildConsumptionAppLaunchIntent(paramContext, paramInt1, FinskyApp.get().getCurrentAccountName());
          if (localIntent2 != null) {
            localRemoteViews1.setOnClickPendingIntent(2131755775, PendingIntent.getActivity(paramContext, 0, localIntent2, 0));
          }
          localRemoteViews1.setViewVisibility(2131755775, 0);
          break;
        }
        i4 = 0;
        if (localIntent1 == null) {
          break;
        }
        localRemoteViews1.setOnClickPendingIntent(2131755775, PendingIntent.getActivity(paramContext, paramInt2, localIntent1, 0));
        localRemoteViews1.setViewVisibility(2131755775, 0);
        i4 = 0;
        break;
        i5 = 0;
        break label1080;
        label1268:
        localRemoteViews1.setViewVisibility(2131755772, 4);
      }
      label1279:
      localRemoteViews1.setViewVisibility(2131755784, 8);
      return localViewTreeWrapper1;
      label1291:
      i15 = j;
    }
  }
  
  private WidgetLayout generateWidgetLayout(final Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    while ((paramInt4 > 0) && (paramInt1 > i))
    {
      int j = paramInt3;
      ArrayList localArrayList2 = new ArrayList();
      int k = 0;
      int m = 0;
      for (int n = 1; (j > 0) && (paramInt1 > i); n = 0)
      {
        if (k == 0) {}
        int i7;
        Object localObject;
        for (int i6 = paramInt4;; i6 = k)
        {
          i7 = paramInt1 - i;
          localObject = null;
          switch (paramInt2)
          {
          case 5: 
          default: 
            throw new IllegalArgumentException("Invalid backend");
          }
        }
        Block[] arrayOfBlock = PORTRAIT_BLOCKS;
        int i8 = arrayOfBlock.length;
        int i9 = 0;
        label143:
        if (i9 < i8)
        {
          Block localBlock4 = arrayOfBlock[i9];
          int i12 = this.mRowStartCounts.get(localBlock4.hashCode(), 0);
          int i15;
          label188:
          int i13;
          if (n != 0)
          {
            if (localBlock4.mMaxRowStartCount < 0) {
              break label310;
            }
            i15 = 1;
            if ((i15 != 0) && (i12 >= localBlock4.mMaxRowStartCount)) {}
          }
          else
          {
            i13 = 1;
            i14 = 1;
            if (localObject != null)
            {
              if (localBlock4.getHeight(paramContext) <= localObject.getHeight(paramContext)) {
                break label316;
              }
              i13 = 1;
              label232:
              if (localBlock4.getWidth(paramContext) < localObject.getWidth(paramContext)) {
                break label322;
              }
            }
          }
          label310:
          label316:
          label322:
          for (int i14 = 1;; i14 = 0)
          {
            if ((i13 != 0) && (localBlock4.getHeight(paramContext) <= i6) && (i14 != 0) && (localBlock4.getWidth(paramContext) <= j) && (i7 >= localBlock4.mNumImages)) {
              localObject = localBlock4;
            }
            i9++;
            break label143;
            arrayOfBlock = SQUARE_BLOCKS;
            break;
            i15 = 0;
            break label188;
            i13 = 0;
            break label232;
          }
        }
        if ((localObject != null) && (n != 0))
        {
          int i11 = this.mRowStartCounts.get(localObject.hashCode(), 0);
          this.mRowStartCounts.put(localObject.hashCode(), i11 + 1);
        }
        if (localObject == null) {
          break;
        }
        m++;
        i += localObject.mNumImages;
        localArrayList2.add(localObject);
        j -= localObject.getWidth(paramContext);
        int i10 = localObject.getHeight(paramContext);
        k = Math.max(k, i10);
      }
      if (m == 0) {
        break;
      }
      HashMap localHashMap = new HashMap();
      Iterator localIterator1 = localArrayList2.iterator();
      if (localIterator1.hasNext())
      {
        Block localBlock3 = (Block)localIterator1.next();
        int i4;
        if (localBlock3.mLastInRowReplacement != null)
        {
          i4 = 1;
          label487:
          if (i4 == 0) {
            break label545;
          }
          if (!localHashMap.containsKey(localBlock3)) {
            break label547;
          }
        }
        label545:
        label547:
        for (int i5 = ((Integer)localHashMap.get(localBlock3)).intValue();; i5 = 0)
        {
          localHashMap.put(localBlock3, Integer.valueOf(i5 + 1));
          break;
          i4 = 0;
          break label487;
          break;
        }
      }
      Iterator localIterator2 = localHashMap.entrySet().iterator();
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator2.next();
        if (((Integer)localEntry.getValue()).intValue() != 1)
        {
          Block localBlock1 = (Block)localEntry.getKey();
          int i1 = -1;
          for (int i2 = -1 + localArrayList2.size();; i2--) {
            if (i2 >= 0)
            {
              if (localArrayList2.get(i2) == localBlock1) {
                i1 = i2;
              }
            }
            else
            {
              Block localBlock2 = localBlock1.mLastInRowReplacement;
              int i3 = localBlock2.mNumImages - localBlock1.mNumImages;
              if ((i3 > 0) && (paramInt1 - i < i3)) {
                break;
              }
              localArrayList2.remove(i1);
              localArrayList2.add(i1, localBlock2);
              i += i3;
              break;
            }
          }
        }
      }
      localArrayList1.add(localArrayList2);
      paramInt4 -= k;
    }
    Comparator local2 = new Comparator()
    {
      private static int getRowAverageHeight(List<Block> paramAnonymousList, Context paramAnonymousContext)
      {
        int i = 0;
        int j = 0;
        Resources localResources = paramAnonymousContext.getResources();
        Iterator localIterator = paramAnonymousList.iterator();
        while (localIterator.hasNext())
        {
          Block localBlock = (Block)localIterator.next();
          int k = localBlock.mNumImages;
          for (int m = 0; m < k; m++) {
            j += localResources.getDimensionPixelSize(localBlock.getImageHeightRes(m));
          }
          i += k;
        }
        return j / i;
      }
      
      private static int getRowCellCount(List<Block> paramAnonymousList)
      {
        int i = 0;
        Iterator localIterator = paramAnonymousList.iterator();
        while (localIterator.hasNext()) {
          i += ((Block)localIterator.next()).mNumImages;
        }
        return i;
      }
    };
    Collections.sort(localArrayList1, local2);
    if (i >= paramInt1) {}
    for (boolean bool = true;; bool = false)
    {
      WidgetLayout localWidgetLayout = new WidgetLayout(localArrayList1, bool, paramInt4);
      return localWidgetLayout;
    }
  }
  
  private static int[] getBackends(int paramInt)
  {
    if (paramInt == 0) {
      return BACKENDS;
    }
    return new int[] { paramInt };
  }
  
  private static Bitmap getCachedImage(Context paramContext, Map<ImageBatch.ImageSpec, Bitmap> paramMap, Uri paramUri, int paramInt1, int paramInt2)
  {
    int i = WidgetUtils.getDips(paramContext, paramInt1);
    int j = WidgetUtils.getDips(paramContext, paramInt2);
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      ImageBatch.ImageSpec localImageSpec = (ImageBatch.ImageSpec)localIterator.next();
      if (localImageSpec.satisfies(paramUri, i, j)) {
        return (Bitmap)paramMap.get(localImageSpec);
      }
    }
    return null;
  }
  
  private static List<ConsumptionAppDocList> getConsumptionDocLists(int paramInt)
  {
    ArrayList localArrayList1 = new ArrayList();
    for (int k : getBackends(paramInt))
    {
      ConsumptionAppDocList localConsumptionAppDocList = ConsumptionAppDataCache.get().getConsumptionAppData(k);
      ArrayList localArrayList2 = new ArrayList();
      Iterator localIterator = localConsumptionAppDocList.iterator();
      while (localIterator.hasNext())
      {
        ConsumptionAppDoc localConsumptionAppDoc = (ConsumptionAppDoc)localIterator.next();
        if (localConsumptionAppDoc.mImageUri != null)
        {
          localArrayList2.add(localConsumptionAppDoc);
        }
        else
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localConsumptionAppDoc.mDocId;
          FinskyLog.d("filtering out docId=[%s] because uri was null", arrayOfObject);
        }
      }
      if (!localArrayList2.isEmpty()) {
        localArrayList1.add(new ConsumptionAppDocList(k, localArrayList2));
      }
    }
    Collections.sort(localArrayList1, ConsumptionAppDocList.NEWEST_FIRST);
    return localArrayList1;
  }
  
  private static int getEmptyBackgroundRes(int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
    case 5: 
    default: 
      return 0;
    case 1: 
      return 2130837580;
    case 4: 
      return 2130837584;
    case 2: 
      return 2130837581;
    case 6: 
      return 2130837582;
    }
    return 2130837583;
  }
  
  private static int getHeaderIconRes(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      throw new IllegalArgumentException("Invalid backend");
    case 1: 
      return 2130837858;
    case 4: 
      return 2130837860;
    case 2: 
      return 2130837861;
    case 6: 
      return 2130837862;
    }
    return 2130837863;
  }
  
  private static BatchedImageLoader getImageLoader(Context paramContext)
  {
    if (mImageLoader == null) {
      mImageLoader = new BatchedImageLoader(paramContext, FinskyApp.get().mBitmapCache);
    }
    return mImageLoader;
  }
  
  private static String getTitle(DfeToc paramDfeToc, Context paramContext, int paramInt)
  {
    int i = 2131362927;
    switch (paramInt)
    {
    case 1: 
    case 2: 
    default: 
      if ((paramDfeToc != null) && (paramDfeToc.getCorpus(paramInt) != null)) {
        return paramDfeToc.getCorpus(paramInt).libraryName;
      }
      break;
    case 0: 
      return paramContext.getString(i);
    case 3: 
      return paramContext.getString(2131362922);
    }
    switch (paramInt)
    {
    }
    for (;;)
    {
      return paramContext.getString(i);
      i = 2131362918;
      continue;
      i = 2131362924;
      continue;
      i = 2131362925;
      continue;
      i = 2131362926;
    }
  }
  
  private static List<ImageBatch.ImageSpec> mergePortAndLandRequests(List<ImageBatch.ImageSpec> paramList1, List<ImageBatch.ImageSpec> paramList2)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramList2 != null) && (paramList1 != null))
    {
      List<ImageBatch.ImageSpec> localList1 = paramList2;
      List<ImageBatch.ImageSpec> localList2 = paramList1;
      if (paramList2.size() < paramList1.size())
      {
        localList1 = paramList1;
        localList2 = paramList2;
      }
      Iterator localIterator1 = localList1.iterator();
      while (localIterator1.hasNext())
      {
        ImageBatch.ImageSpec localImageSpec2 = (ImageBatch.ImageSpec)localIterator1.next();
        Iterator localIterator4 = localList2.iterator();
        ImageBatch.ImageSpec localImageSpec3;
        do
        {
          boolean bool2 = localIterator4.hasNext();
          j = 0;
          if (!bool2) {
            break;
          }
          localImageSpec3 = (ImageBatch.ImageSpec)localIterator4.next();
        } while (!localImageSpec2.uri.equals(localImageSpec3.uri));
        if (!localImageSpec3.uri.equals(localImageSpec2.uri)) {
          throw new IllegalStateException("tried to merge wrappers with different uris!");
        }
        localArrayList.add(new ImageBatch.ImageSpec(localImageSpec3.uri, Math.max(localImageSpec3.width, localImageSpec2.width), Math.max(localImageSpec3.height, localImageSpec2.height)));
        int j = 1;
        if (j == 0) {
          localArrayList.add(localImageSpec2);
        }
      }
      Iterator localIterator2 = localList2.iterator();
      while (localIterator2.hasNext())
      {
        ImageBatch.ImageSpec localImageSpec1 = (ImageBatch.ImageSpec)localIterator2.next();
        Iterator localIterator3 = localArrayList.iterator();
        do
        {
          boolean bool1 = localIterator3.hasNext();
          i = 0;
          if (!bool1) {
            break;
          }
        } while (!((ImageBatch.ImageSpec)localIterator3.next()).uri.equals(localImageSpec1.uri));
        int i = 1;
        if (i == 0) {
          localArrayList.add(localImageSpec1);
        }
      }
    }
    return localArrayList;
  }
  
  private ViewTreeWrapper populateWidget$74ef0b1(Context paramContext, RemoteViews paramRemoteViews, int paramInt1, int paramInt2, List<List<Block>> paramList, List<ConsumptionAppDoc> paramList1, Map<ImageBatch.ImageSpec, Bitmap> paramMap)
  {
    Resources localResources = paramContext.getResources();
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    int j = 0;
    String str1 = paramContext.getPackageName();
    paramRemoteViews.removeAllViews(paramInt1);
    NowPlayingCellSorter localNowPlayingCellSorter = new NowPlayingCellSorter();
    ArrayList localArrayList2 = new ArrayList();
    for (int k = 0;; k++)
    {
      int m = paramList.size();
      if (k >= m) {
        break;
      }
      List localList2 = (List)paramList.get(k);
      for (int i14 = 0;; i14++)
      {
        int i15 = localList2.size();
        if (i14 >= i15) {
          break;
        }
        Block localBlock2 = (Block)localList2.get(i14);
        int i16 = localBlock2.mNumImages;
        for (int i17 = 0; i17 < i16; i17++)
        {
          NowPlayingCellSorter.CellInformation localCellInformation2 = new NowPlayingCellSorter.CellInformation(localNowPlayingCellSorter, k, i14, i17);
          localCellInformation2.cellAreaInPixels = (localResources.getDimensionPixelSize(localBlock2.getImageWidthRes(i17)) * localResources.getDimensionPixelSize(localBlock2.getImageHeightRes(i17)));
          localArrayList2.add(localCellInformation2);
        }
      }
    }
    Collections.sort(localArrayList2);
    for (int n = 0;; n++)
    {
      int i1 = localArrayList2.size();
      if (n >= i1) {
        break;
      }
      NowPlayingCellSorter.CellInformation localCellInformation1 = (NowPlayingCellSorter.CellInformation)localArrayList2.get(n);
      localNowPlayingCellSorter.mSequenceMapping.put(NowPlayingCellSorter.getCellDescriptor(localCellInformation1.rowIndexInWidget, localCellInformation1.blockIndexInRow, localCellInformation1.cellIndexInBlock), Integer.valueOf(n));
    }
    for (int i2 = 0;; i2++)
    {
      int i3 = paramList.size();
      if (i2 >= i3) {
        break;
      }
      List localList1 = (List)paramList.get(i2);
      RemoteViews localRemoteViews1 = new RemoteViews(str1, 2130968879);
      ArrayList localArrayList3 = new ArrayList();
      for (int i4 = 0; i4 < localList1.size(); i4++)
      {
        Block localBlock1 = (Block)localList1.get(i4);
        RemoteViews localRemoteViews2 = new RemoteViews(str1, localBlock1.mLayoutId);
        int i9 = Math.min(IMAGE_IDS.length, localBlock1.mNumImages);
        int i10 = 0;
        if (i10 < i9)
        {
          String str2 = NowPlayingCellSorter.getCellDescriptor(i2, i4, i10);
          int i11;
          label441:
          ConsumptionAppDoc localConsumptionAppDoc;
          if (!localNowPlayingCellSorter.mSequenceMapping.containsKey(str2))
          {
            i11 = -1;
            if ((i11 >= 0) && (i11 < paramList1.size()))
            {
              localConsumptionAppDoc = (ConsumptionAppDoc)paramList1.get(i11);
              if (paramMap == null) {
                break label689;
              }
              Bitmap localBitmap = getCachedImage(paramContext, paramMap, localConsumptionAppDoc.mImageUri, localBlock1.getImageWidthRes(i10), localBlock1.getImageHeightRes(i10));
              localRemoteViews2.setImageViewBitmap(IMAGE_IDS[i10], localBitmap);
              if (localBitmap == null) {
                i = 1;
              }
              label525:
              int i12 = localResources.getDimensionPixelSize(localBlock1.getImageWidthRes(i10));
              int i13 = localResources.getDimensionPixelSize(localBlock1.getImageHeightRes(i10));
              ImageBatch.ImageSpec localImageSpec = new ImageBatch.ImageSpec(localConsumptionAppDoc.mImageUri, i12, i13);
              localArrayList1.add(localImageSpec);
              if (localBlock1.mSupportsMetadata)
              {
                if ((TextUtils.isEmpty(localConsumptionAppDoc.mReason)) || (j != 0)) {
                  break label695;
                }
                localRemoteViews2.setTextViewText(2131755792, localConsumptionAppDoc.mReason.toUpperCase());
                localRemoteViews2.setViewVisibility(2131755792, 0);
                j = 1;
              }
            }
          }
          for (;;)
          {
            PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, i10, localConsumptionAppDoc.mViewIntent, 0);
            localRemoteViews2.setOnClickPendingIntent(ACCESSIBILITY_OVERLAY_IDS[i10], localPendingIntent);
            i10++;
            break;
            i11 = ((Integer)localNowPlayingCellSorter.mSequenceMapping.get(str2)).intValue();
            break label441;
            label689:
            i = 1;
            break label525;
            label695:
            localRemoteViews2.setViewVisibility(2131755792, 8);
          }
        }
        localArrayList3.add(localRemoteViews2);
      }
      int i5;
      int i6;
      label744:
      int i7;
      if ((paramInt2 & 0x1) != 0)
      {
        i5 = 1;
        if ((paramInt2 & 0x2) == 0) {
          break label840;
        }
        i6 = 1;
        i7 = 1;
        if (i5 == 0) {
          break label846;
        }
        i7 = 8388613;
        label757:
        if (i2 % 2 != 0) {
          break label859;
        }
      }
      label840:
      label846:
      label859:
      for (int i8 = i7 | 0x50;; i8 = i7 | 0x30)
      {
        localRemoteViews1.setInt(2131755776, "setGravity", i8);
        if (i5 != 0) {
          Collections.reverse(localArrayList3);
        }
        Iterator localIterator = localArrayList3.iterator();
        while (localIterator.hasNext()) {
          localRemoteViews1.addView(2131755776, (RemoteViews)localIterator.next());
        }
        i5 = 0;
        break;
        i6 = 0;
        break label744;
        if (i6 == 0) {
          break label757;
        }
        i7 = 8388611;
        break label757;
      }
      paramRemoteViews.addView(paramInt1, localRemoteViews1);
    }
    if (i == 0) {}
    for (boolean bool = true;; bool = false)
    {
      ViewTreeWrapper localViewTreeWrapper = new ViewTreeWrapper(bool, localArrayList1);
      return localViewTreeWrapper;
    }
  }
  
  private static void updateHotseat(Context paramContext, DfeToc paramDfeToc, RemoteViews paramRemoteViews, int paramInt1, int paramInt2, int paramInt3)
  {
    paramRemoteViews.setViewVisibility(2131755784, 8);
    if (paramDfeToc == null) {}
    Resources localResources;
    do
    {
      return;
      localResources = paramContext.getResources();
    } while ((paramInt3 <= WidgetUtils.getDips(paramContext, 2131493028)) || (paramInt2 <= WidgetUtils.getDips(paramContext, 2131493029)));
    HotseatDataHolder localHotseatDataHolder = HotseatDataHolder.loadDataFromDisk(paramInt1);
    if (localHotseatDataHolder.mHasDismissedHotseat)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      FinskyLog.v("Hiding hotseat because the user had dismissed it for %d", arrayOfObject);
      return;
    }
    int i = paramInt2 / WidgetUtils.getDips(paramContext, 2131493030);
    ConsumptionAppDataCache localConsumptionAppDataCache = ConsumptionAppDataCache.get();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (i1 < sSupportedBackendIds.length)
    {
      int i3 = sSupportedBackendIds[i1];
      Toc.CorpusMetadata localCorpusMetadata = paramDfeToc.getCorpus(i3);
      RemoteViews localRemoteViews;
      label253:
      int i6;
      label261:
      label367:
      label373:
      int i7;
      if ((localCorpusMetadata != null) && (IntentUtils.isConsumptionAppInstalled(paramContext.getPackageManager(), i3)) && (!IntentUtils.isConsumptionAppDisabled(FinskyApp.get().mPackageStateRepository, i3)))
      {
        m++;
        localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130968883);
        localRemoteViews.setImageViewResource(2131755790, WidgetUtils.getHotseatCheckIcon(i3));
        localRemoteViews.setTextViewText(2131755791, localCorpusMetadata.name);
        localRemoteViews.setTextColor(2131755791, CorpusResourceUtils.getPrimaryColor(paramContext, i3));
        Utils.ensureOnMainThread();
        List localList = (List)localConsumptionAppDataCache.mConsumptionAppData.get(i3);
        int i4;
        int i5;
        if (localList == null)
        {
          i4 = 0;
          if (i4 <= 0) {
            break label367;
          }
          i5 = 1;
          n |= i5;
          if ((!localConsumptionAppDataCache.hasConsumptionAppData(i3)) || (i4 < WidgetUtils.getAwarenessThreshold(i3))) {
            break label373;
          }
          j++;
          localRemoteViews.setViewVisibility(2131755790, 0);
          i6 = 1;
        }
        for (;;)
        {
          switch (i3)
          {
          case 3: 
          case 5: 
          default: 
            throw new IllegalArgumentException("Invalid backend");
            i4 = localList.size();
            break label253;
            i5 = 0;
            break label261;
            boolean bool;
            switch (i3)
            {
            case 3: 
            case 5: 
            default: 
              bool = true;
            }
            for (;;)
            {
              if (!bool) {
                break label478;
              }
              k++;
              localRemoteViews.setViewVisibility(2131755790, 0);
              i6 = 1;
              break;
              bool = localHotseatDataHolder.mIsMusicChecked;
              continue;
              bool = localHotseatDataHolder.mIsMagazinesChecked;
              continue;
              bool = localHotseatDataHolder.mIsMoviesChecked;
              continue;
              bool = localHotseatDataHolder.mIsBooksChecked;
            }
            label478:
            localRemoteViews.setViewVisibility(2131755790, 8);
            i6 = 0;
          }
        }
        if (i6 == 0) {
          break label619;
        }
        i7 = 2131362041;
      }
      for (;;)
      {
        localRemoteViews.setContentDescription(2131755218, paramContext.getString(i7));
        Intent localIntent2 = new Intent(paramContext, NowPlayingWidgetProvider.class);
        localIntent2.setAction("NowPlayingWidgetProvider.WarmWelcome");
        localIntent2.putExtra("appWidgetId", paramInt1);
        localIntent2.putExtra("backendId", i3);
        localRemoteViews.setOnClickPendingIntent(2131755218, PendingIntent.getBroadcast(paramContext, sRandom.nextInt(), localIntent2, 0));
        if (m == 1) {
          localRemoteViews.setViewVisibility(2131755789, 8);
        }
        if (m <= i) {
          paramRemoteViews.addView(2131755788, localRemoteViews);
        }
        i1++;
        break;
        label619:
        i7 = 2131362037;
        continue;
        if (i6 != 0)
        {
          i7 = 2131362042;
        }
        else
        {
          i7 = 2131362038;
          continue;
          if (i6 != 0)
          {
            i7 = 2131362043;
          }
          else
          {
            i7 = 2131362039;
            continue;
            if (i6 != 0) {
              i7 = 2131362044;
            } else {
              i7 = 2131362040;
            }
          }
        }
      }
    }
    int i2;
    if ((j > 0) || ((k > 0) && (n != 0)))
    {
      i2 = 1;
      if (i2 == 0) {
        break label812;
      }
      paramRemoteViews.setTextViewText(2131755785, Html.fromHtml(localResources.getString(2131362923)));
      paramRemoteViews.setViewVisibility(2131755786, 0);
      paramRemoteViews.setViewVisibility(2131755787, 0);
      Intent localIntent1 = new Intent(paramContext, NowPlayingWidgetProvider.class);
      localIntent1.setAction("NowPlayingWidgetProvider.DoneButton");
      localIntent1.putExtra("appWidgetId", paramInt1);
      paramRemoteViews.setOnClickPendingIntent(2131755787, PendingIntent.getBroadcast(paramContext, sRandom.nextInt(), localIntent1, 0));
    }
    for (;;)
    {
      paramRemoteViews.setViewVisibility(2131755784, 0);
      return;
      i2 = 0;
      break;
      label812:
      paramRemoteViews.setTextViewText(2131755785, Html.fromHtml(localResources.getString(2131362923)));
      paramRemoteViews.setViewVisibility(2131755786, 8);
      paramRemoteViews.setViewVisibility(2131755787, 8);
    }
  }
  
  private static boolean useCustomStylingIfNecessary(Context paramContext, RemoteViews paramRemoteViews, int paramInt)
  {
    if ((BG_IMAGE_OVERRIDE == null) || (!BG_IMAGE_OVERRIDE.exists()) || (paramInt != 0)) {
      return false;
    }
    paramRemoteViews.setViewVisibility(2131755772, 4);
    paramRemoteViews.setTextViewCompoundDrawables(2131755774, 0, 2130837674, 0, 0);
    paramRemoteViews.setTextViewCompoundDrawables(2131755274, 0, 2130837674, 0, 0);
    paramRemoteViews.setImageViewUri(2131755769, Uri.fromFile(BG_IMAGE_OVERRIDE));
    paramRemoteViews.setOnClickPendingIntent(2131755779, null);
    paramRemoteViews.setViewVisibility(2131755779, 4);
    paramRemoteViews.setTextColor(2131755774, paramContext.getResources().getColor(2131689682));
    paramRemoteViews.setTextColor(2131755274, paramContext.getResources().getColor(2131689682));
    paramRemoteViews.setViewVisibility(2131755773, 8);
    paramRemoteViews.setTextViewText(2131755778, getTitle(FinskyApp.get().mToc, paramContext, paramInt).toUpperCase());
    return true;
  }
  
  private static void warmImageCache(Context paramContext, int paramInt)
  {
    List localList = getConsumptionDocLists(paramInt);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ConsumptionAppDocList localConsumptionAppDocList = (ConsumptionAppDocList)localIterator.next();
      for (int i = 0; i < Math.min(4, localConsumptionAppDocList.size()); i++) {
        localArrayList.add(new ImageBatch.ImageSpec(((ConsumptionAppDoc)localConsumptionAppDocList.get(i)).mImageUri, 0, 0));
      }
    }
    if (localArrayList.size() > 0)
    {
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        arrayOfObject[1] = Integer.valueOf(localArrayList.size());
        FinskyLog.v("Warming cache for %s with %d images", arrayOfObject);
      }
      getImageLoader(paramContext).enqueue(new ImageBatch(paramInt, localArrayList, null));
    }
  }
  
  protected final int getWidgetClassId()
  {
    return 3;
  }
  
  public void onAppWidgetOptionsChanged(Context paramContext, AppWidgetManager paramAppWidgetManager, int paramInt, Bundle paramBundle)
  {
    updateWidgets(paramContext, paramAppWidgetManager, new int[] { paramInt });
  }
  
  public void onDeleted(Context paramContext, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfInt[j];
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(k);
      FinskyLog.d("Deleting widget data for widget ID=%d", arrayOfObject);
      FinskyPreferences.libraryWidgetData.get(k).remove();
    }
    super.onDeleted(paramContext, paramArrayOfInt);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    super.onReceive(paramContext, paramIntent);
    if ("android.intent.action.PACKAGE_CHANGED".equals(paramIntent.getAction())) {
      if (paramIntent.getIntExtra("android.intent.extra.UID", -1) != -1) {}
    }
    int i;
    int j;
    do
    {
      do
      {
        WidgetTypeMap localWidgetTypeMap;
        int m;
        do
        {
          return;
          String str = paramIntent.getData().getSchemeSpecificPart();
          localWidgetTypeMap = WidgetTypeMap.get(paramContext);
          m = IntentUtils.getBackendId(str);
        } while ((m == -1) || (m == 9));
        int[] arrayOfInt = localWidgetTypeMap.getWidgets(NowPlayingWidgetProvider.class, WidgetUtils.translate(m), true);
        updateWidgets(paramContext, AppWidgetManager.getInstance(paramContext), arrayOfInt);
        return;
        if ("NowPlayingWidgetProvider.DoneButton".equals(paramIntent.getAction()))
        {
          int k = paramIntent.getIntExtra("appWidgetId", -1);
          if (k != -1)
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = Integer.valueOf(k);
            FinskyLog.d("Received ACTION_DONE_BUTTON, updating widget %d.", arrayOfObject2);
            HotseatDataHolder localHotseatDataHolder2 = HotseatDataHolder.loadDataFromDisk(k);
            localHotseatDataHolder2.mHasDismissedHotseat = true;
            HotseatDataHolder.flushData(k, localHotseatDataHolder2);
            updateWidgets(paramContext, AppWidgetManager.getInstance(paramContext), new int[] { k });
            return;
          }
          FinskyLog.e("Received ACTION_DONE_BUTTON, but no appWidgetId was specified.", new Object[0]);
          return;
        }
      } while (!"NowPlayingWidgetProvider.WarmWelcome".equals(paramIntent.getAction()));
      i = paramIntent.getIntExtra("appWidgetId", -1);
      j = paramIntent.getIntExtra("backendId", -1);
    } while ((i == -1) || (j == -1));
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(j);
    arrayOfObject1[1] = Integer.valueOf(i);
    FinskyLog.d("Received ACTION_LAUNCH_WARM_WELCOME for backend %d and widget %d", arrayOfObject1);
    HotseatDataHolder localHotseatDataHolder1 = HotseatDataHolder.loadDataFromDisk(i);
    switch (j)
    {
    }
    for (;;)
    {
      HotseatDataHolder.flushData(i, localHotseatDataHolder1);
      FinskyApp.get().startActivity(IntentUtils.buildConsumptionAppLaunchIntent(paramContext, j, FinskyApp.get().getCurrentAccountName()));
      updateWidgets(paramContext, AppWidgetManager.getInstance(paramContext), new int[] { i });
      return;
      localHotseatDataHolder1.mIsMusicChecked = true;
      continue;
      localHotseatDataHolder1.mIsMagazinesChecked = true;
      continue;
      localHotseatDataHolder1.mIsMoviesChecked = true;
      continue;
      localHotseatDataHolder1.mIsBooksChecked = true;
    }
  }
  
  protected final void updateWidgets(final Context paramContext, AppWidgetManager paramAppWidgetManager, Map<ImageBatch.ImageSpec, Bitmap> paramMap, int... paramVarArgs)
  {
    if (paramVarArgs == null) {
      return;
    }
    WidgetTypeMap localWidgetTypeMap = WidgetTypeMap.get(paramContext);
    int i = WidgetUtils.getDips(paramContext, 2131493056);
    int j = paramVarArgs.length;
    int k = 0;
    label29:
    final int m;
    if (k < j)
    {
      m = paramVarArgs[k];
      if (FinskyApp.get().getDfeApi(null) != null) {
        break label149;
      }
      RemoteViews localRemoteViews5 = generateBaseTree(paramContext, true);
      RemoteViews localRemoteViews6 = new RemoteViews(paramContext.getPackageName(), 2130968860);
      localRemoteViews5.removeAllViews(2131755771);
      localRemoteViews5.addView(2131755771, localRemoteViews6);
      localRemoteViews5.setOnClickPendingIntent(2131755775, getAddAccountIntent(paramContext));
      localRemoteViews5.setViewVisibility(2131755775, 0);
      if (useCustomStylingIfNecessary(paramContext, localRemoteViews5, 0)) {
        localRemoteViews5.setTextViewText(2131755274, paramContext.getString(2131361870));
      }
      paramAppWidgetManager.updateAppWidget(m, localRemoteViews5);
    }
    for (;;)
    {
      k++;
      break label29;
      break;
      label149:
      String str1 = localWidgetTypeMap.get(getClass(), m);
      if (str1 == null)
      {
        RemoteViews localRemoteViews4 = generateBaseTree(paramContext, true);
        localRemoteViews4.addView(2131755771, new RemoteViews(paramContext.getPackageName(), 2130968878));
        localRemoteViews4.setOnClickPendingIntent(2131755775, TrampolineActivity.getPendingLaunchIntent(paramContext, NowPlayingTrampoline.class, m));
        localRemoteViews4.setViewVisibility(2131755775, 0);
        useCustomStylingIfNecessary(paramContext, localRemoteViews4, 0);
        paramAppWidgetManager.updateAppWidget(m, localRemoteViews4);
      }
      else
      {
        int n = WidgetUtils.translate(str1);
        if ((n != 0) && (!IntentUtils.isConsumptionAppInstalled(paramContext.getPackageManager(), n)))
        {
          RemoteViews localRemoteViews2 = generateBaseTree(paramContext, true);
          RemoteViews localRemoteViews3 = new RemoteViews(paramContext.getPackageName(), 2130968878);
          localRemoteViews3.setTextViewText(2131755274, paramContext.getString(2131362921));
          localRemoteViews2.addView(2131755771, localRemoteViews3);
          String str3 = IntentUtils.getPackageName(n);
          Uri localUri = new Uri.Builder().scheme("https").authority("play.google.com").path("store/apps/details").appendQueryParameter("id", str3).build();
          Intent localIntent = new Intent(paramContext, MainActivity.class);
          localIntent.setAction("android.intent.action.VIEW");
          localIntent.setData(localUri);
          localIntent.addFlags(268435456);
          localRemoteViews2.setOnClickPendingIntent(2131755775, PendingIntent.getActivity(paramContext, 0, localIntent, 0));
          localRemoteViews2.setViewVisibility(2131755775, 0);
          useCustomStylingIfNecessary(paramContext, localRemoteViews2, n);
          paramAppWidgetManager.updateAppWidget(m, localRemoteViews2);
        }
        else
        {
          String str2 = IntentUtils.getPackageName(n);
          if ((str2 != null) && (paramContext.getPackageManager().getApplicationEnabledSetting(str2) == 3)) {}
          for (int i1 = 1;; i1 = 0)
          {
            if (i1 == 0) {
              break label487;
            }
            paramAppWidgetManager.updateAppWidget(m, generateDisabledState$672be14b(paramContext, n));
            break;
          }
          label487:
          int[] arrayOfInt = getBoundingBoxes(paramContext, m);
          if ((arrayOfInt[0] == 0) && (arrayOfInt[1] == 0) && (arrayOfInt[2] == 0) && (arrayOfInt[3] == 0))
          {
            if (n == 0) {
              warmImageCache(paramContext, n);
            }
            paramAppWidgetManager.updateAppWidget(m, generateBaseTree(paramContext, true));
          }
          else
          {
            int i2 = arrayOfInt[0];
            int i3 = arrayOfInt[1] - i;
            int i4 = arrayOfInt[2];
            int i5 = arrayOfInt[3] - i;
            DfeToc localDfeToc = FinskyApp.get().mToc;
            ViewTreeWrapper localViewTreeWrapper1 = generateViewTree(n, m, localDfeToc, paramContext, paramMap, i4, i3);
            ViewTreeWrapper localViewTreeWrapper2 = generateViewTree(n, m, localDfeToc, paramContext, paramMap, i2, i5);
            RemoteViews localRemoteViews1 = new RemoteViews(localViewTreeWrapper1.mRemoteViews, localViewTreeWrapper2.mRemoteViews);
            if ((!localViewTreeWrapper1.mLoadedSuccessfully) || (!localViewTreeWrapper2.mLoadedSuccessfully))
            {
              List localList = mergePortAndLandRequests(localViewTreeWrapper2.mUris, localViewTreeWrapper1.mUris);
              getImageLoader(paramContext).enqueue(new ImageBatch(n, localList, new BatchedImageLoader.BatchedImageCallback()
              {
                public final void onLoaded(Map<ImageBatch.ImageSpec, Bitmap> paramAnonymousMap)
                {
                  NowPlayingWidgetProvider localNowPlayingWidgetProvider = NowPlayingWidgetProvider.this;
                  Context localContext = paramContext;
                  AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(paramContext);
                  int[] arrayOfInt = new int[1];
                  arrayOfInt[0] = m;
                  localNowPlayingWidgetProvider.updateWidgets(localContext, localAppWidgetManager, paramAnonymousMap, arrayOfInt);
                }
              }));
            }
            else
            {
              paramAppWidgetManager.updateAppWidget(m, localRemoteViews1);
            }
          }
        }
      }
    }
  }
  
  protected final void updateWidgets(Context paramContext, AppWidgetManager paramAppWidgetManager, int... paramVarArgs)
  {
    updateWidgets(paramContext, paramAppWidgetManager, getImageLoader(paramContext).mPreviousBitmaps, paramVarArgs);
  }
  
  public static final class HotseatDataHolder
  {
    boolean mHasDismissedHotseat;
    boolean mIsBooksChecked;
    boolean mIsMagazinesChecked;
    boolean mIsMoviesChecked;
    boolean mIsMusicChecked;
    
    static void flushData(int paramInt, HotseatDataHolder paramHotseatDataHolder)
    {
      String str1 = "" + "d," + Boolean.toString(paramHotseatDataHolder.mHasDismissedHotseat);
      String str2 = str1 + "&ma," + Boolean.toString(paramHotseatDataHolder.mIsMagazinesChecked);
      String str3 = str2 + "&mo," + Boolean.toString(paramHotseatDataHolder.mIsMoviesChecked);
      String str4 = str3 + "&mu," + Boolean.toString(paramHotseatDataHolder.mIsMusicChecked);
      String str5 = str4 + "&b," + Boolean.toString(paramHotseatDataHolder.mIsBooksChecked);
      FinskyPreferences.libraryWidgetData.get(paramInt).put(str5);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = str5;
      FinskyLog.d("Writing data for widget=%d data=%s", arrayOfObject);
    }
    
    public static HotseatDataHolder loadDataFromDisk(int paramInt)
    {
      HotseatDataHolder localHotseatDataHolder = new HotseatDataHolder();
      String str1 = (String)FinskyPreferences.libraryWidgetData.get(paramInt).get();
      if (TextUtils.isEmpty(str1)) {
        return localHotseatDataHolder;
      }
      String[] arrayOfString1 = str1.split("&");
      int i = 0;
      label41:
      String str2;
      boolean bool;
      if (i < arrayOfString1.length)
      {
        String[] arrayOfString2 = arrayOfString1[i].split(",");
        if ((arrayOfString2 != null) && (arrayOfString2.length == 2) && (!TextUtils.isEmpty(arrayOfString2[0])) && (!TextUtils.isEmpty(arrayOfString2[1])))
        {
          str2 = arrayOfString2[0];
          bool = Boolean.parseBoolean(arrayOfString2[1]);
          if (!"d".equals(str2)) {
            break label128;
          }
          localHotseatDataHolder.mHasDismissedHotseat = bool;
        }
      }
      for (;;)
      {
        i++;
        break label41;
        break;
        label128:
        if ("mu".equals(str2)) {
          localHotseatDataHolder.mIsMusicChecked = bool;
        } else if ("ma".equals(str2)) {
          localHotseatDataHolder.mIsMagazinesChecked = bool;
        } else if ("mo".equals(str2)) {
          localHotseatDataHolder.mIsMoviesChecked = bool;
        } else if ("b".equals(str2)) {
          localHotseatDataHolder.mIsBooksChecked = bool;
        } else {
          FinskyLog.e("Malformed data detected in widget pref, ignoring.", new Object[0]);
        }
      }
    }
  }
  
  private final class ViewTreeWrapper
  {
    int heightRemaining;
    boolean mLoadedSuccessfully = true;
    RemoteViews mRemoteViews;
    List<ImageBatch.ImageSpec> mUris = new ArrayList();
    boolean showBackground = false;
    boolean showEmptyBackground = false;
    
    public ViewTreeWrapper() {}
    
    public ViewTreeWrapper(List<ImageBatch.ImageSpec> paramList)
    {
      this.mRemoteViews = null;
      this.mLoadedSuccessfully = paramList;
      Object localObject;
      this.mUris = localObject;
    }
  }
  
  private final class WidgetLayout
    extends ArrayList<List<Block>>
  {
    final int heightRemaining;
    final boolean showBackground;
    
    public WidgetLayout(boolean paramBoolean, int paramInt)
    {
      this.showBackground = paramInt;
      int i;
      this.heightRemaining = i;
      if (paramBoolean != null) {
        addAll(paramBoolean);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.NowPlayingWidgetProvider
 * JD-Core Version:    0.7.0.1
 */