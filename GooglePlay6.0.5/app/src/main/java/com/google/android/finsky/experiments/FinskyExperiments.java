package com.google.android.finsky.experiments;

import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Targets;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.analytics.ClientAnalytics.ActiveExperiments;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class FinskyExperiments
{
  private static final LongSparseArray<String> sProcessStableTargets;
  private static int sProcessStableTargetsCount;
  private static final LongSparseArray<String> sRecognizedTargets = new LongSparseArray();
  private static int sRecognizedTargetsCount;
  private final String mAccountName;
  private ClientAnalytics.ActiveExperiments mActiveTargets = new ClientAnalytics.ActiveExperiments();
  private final List<TargetsChangeListener> mChangeListeners = new ArrayList();
  private final LongSparseArray<String> mEnabledTargets = new LongSparseArray();
  private String mEnabledTargetsHeaderValue;
  private LongSparseArray<String> mOverriddenTargets;
  private String mUnsupportedTargetsHeaderValue;
  
  static
  {
    sProcessStableTargets = new LongSparseArray();
    setupTarget(12605163L, "prevent tab preloading on home");
    setupTarget(12605193L, "prevent tab preloading on highlights");
    setupTarget(12603142L, "disable daily hygiene preload experiments");
    setupTarget(12602328L, "show buy verb in button");
    setupTarget(12603136L, "hide sale prices");
    setupTarget(12602050L, "cleanup auth settings");
    setupTarget(12602632L, "increase cart details touch target");
    setupTarget(12602631L, "always show play logo");
    setupTarget(12604305L, "enable purchase flow instrument update");
    setupTarget(12602810L, "use 2500ms as prepare purchase timeout");
    setupTarget(12602812L, "use 3500ms as prepare purchase timeout");
    setupTarget(12602814L, "use 5000ms as prepare purchase timeout");
    setupTarget(12602816L, "use 7000ms as prepare purchase timeout");
    setupTarget(12603704L, "enable inline consumption app install flow");
    setupTarget(12602035L, "prompt for fop");
    setupTarget(12603124L, "cart details old expander");
    setupTarget(12603133L, "hide edit payment my account");
    setupTarget(12603134L, "enable post purchase xsell all corpora");
    setupTarget(12602822L, "enable post purchase related topics");
    setupTarget(12602823L, "show post purchase related topics above xsell");
    setupTarget(89L, "details album start cover expanded");
    setupTarget(12602358L, "force through sdcard");
    setupTarget(87L, "details album all access enabled");
    setupTarget(12602761L, "details album all access alternate enabled");
    setupTarget(12602049L, "details hide download count in title");
    setupTarget(12602373L, "use reauth api");
    setupTarget(12603111L, "use reauth api for password");
    setupTarget(12604372L, "use client login for delegated auth");
    setupTarget(12604203L, "send auth token with ad click request");
    setupTarget(12603961L, "disable fingerprint auth for purchase");
    setupTarget(739L, "cap local search suggestions 2");
    setupTarget(12603135L, "cap local search suggestions 3");
    setupTarget(12603137L, "transitions enabled");
    setupTarget(12603044L, "use dfe for music search suggestions");
    setupTarget(12602735L, "auto update pre l strategy using broadcast enabled");
    setupTarget(12603102L, "dora searchbox zero query suggest enabled");
    setupTarget(742L, "backstack clear enabled");
    setupTarget(12603143L, "preserve old obb files");
    setupTarget(12602880L, "disable patch output buffering");
    setupTarget(745L, "happiness survey in search with snow");
    setupTarget(746L, "happiness survey in search no snow");
    setupTarget(747L, "happiness survey in home");
    setupTarget(12602392L, "multi vertical content filtering");
    setupTarget(12603103L, "stop flushing dfe list");
    setupTarget(12602636L, "reviews editor v2 enabled");
    setupTarget(12602952L, "enable free sample magazines");
    setupTarget(12603110L, "disable preregistration hygiene");
    setupTarget(12602819L, "enable screenshots activity v2");
    setupTarget(12602623L, "compact rating enabled");
    setupTarget(12602796L, "log ad id client events");
    setupTarget(12602624L, "reviews feedback v2 enabled");
    setupTarget(12603108L, "disable dfe toc consistency token");
    setupTarget(12603140L, "enable creator avatar cluster v2 for more by author");
    setupTarget(12603105L, "use pin recovery url");
    setupTarget(12603159L, "use svg resources");
    setupTarget(12603097L, "hide empty stars in reviews");
    setupTarget(12603067L, "enable rapid auto update");
    setupTarget(12603106L, "log package first launch");
    setupTarget(12603098L, "searchbox hint per corpus enabled");
    setupTarget(12602778L, "youtube card labels no prices");
    setupTarget(12602779L, "youtube card labels hd prices");
    setupTarget(12602780L, "youtube purchase actions hd prices");
    setupTarget(12602795L, "show family parent guide in drawer");
    setupTarget(12603131L, "enable my subscription");
    setupTarget(12603254L, "enable request refund from order history");
    setupTarget(12603117L, "voucher resolve offer request details");
    setupTarget(12603100L, "location suggestions enabled");
    setupTarget(12603101L, "throttle search suggestions requests");
    setupTarget(12603193L, "enable auto update v2");
    setupTarget(12602981L, "do not load all screenshots at once");
    setupTarget(12603286L, "enable tap to load screenshots");
    setupTarget(12603132L, "remove dcb3 flows cancel buttons");
    setupTarget(12603428L, "enable basic family features");
    setupTarget(12603252L, "enable family sharing features");
    setupTarget(12603772L, "enable family creation card");
    setupTarget(12604079L, "enable family member ask to buy option");
    setupTarget(12603401L, "send user context in search suggest");
    setupTarget(12604087L, "send user context in search");
    setupTarget(12603109L, "disable dfe managed context header");
    setupTarget(12603396L, "enable toggle between help and play store");
    setupTarget(12603380L, "backfill referrer organic install");
    setupTarget(12603394L, "backfill referrer deep link install");
    setupTarget(12603791L, "send install referrer timestamp");
    setupTarget(12604059L, "capture install referrer from notification");
    setupTarget(12603367L, "enable uninstall wizard");
    setupTarget(12603513L, "enable API in PlayAfwAppService");
    setupTarget(12603385L, "support trimmed dfe streams");
    setupTarget(12603406L, "enable adId caching");
    setupTarget(12603301L, "show app size in details summary");
    setupTarget(12603329L, "show app size in listing cards");
    setupTarget(12603516L, "show app size in overflow menu");
    setupTarget(12603517L, "enable bad ad reporting");
    setupTarget(12603514L, "enable logs flush on app exit");
    setupTarget(12603602L, "enable logs flush on daily hygiene");
    setupTarget(12604155L, "enable instant logs flush");
    setupTarget(12603647L, "disable ATV search suggestions");
    setupTarget(12603707L, "enable ATV X-sell in details page");
    setupTarget(12603787L, "display seasonal subscription byline");
    setupTarget(12603719L, "Enable link to artist radio on artist pages");
    setupTarget(12603788L, "enable secure data tokenization for redirect fop");
    setupTarget(12603746L, "enable redeeming monetary code with add-on bonus contents");
    setupTarget(12603431L, "divert inline app details to light purchase flow");
    setupTarget(12603844L, "load full document for reviews/snippets");
    setupTarget(12603770L, "enable edu access via PIN");
    setupTarget(12603948L, "enable wear support on Phonesky");
    setupTarget(12604572L, "disable Top Charts on home and games tabs on ATV");
    setupTarget(12603210L, "enable on-boarding for entertainment");
    setupTarget(12604142L, "enable on-boarding for entertainment v2");
    setupTarget(12604043L, "enable on-boarding for entertainment forcibly over server setting");
    setupTarget(12603427L, "enable instant purchase analytics");
    setupTarget(12603718L, "enable instant purchase flow");
    setupTarget(12603886L, "enable instant purchase use converted offer");
    setupTarget(12603991L, "enable instant purchase network check");
    setupTarget(12604268L, "enable instant purchase allow split tender");
    setupTarget(12604266L, "report in-app item purchases to GMP");
    setupTarget(12604323L, "log in-app item purchases to GMP");
    setupTarget(12604230L, "enable gift purchase via email");
    setupTarget(12604154L, "enable postpone download until Wi-Fi");
    setupTarget(12604300L, "enable purchase error library replication");
    setupTarget(12603992L, "enable dot for complete account");
    setupTarget(12604224L, "dummy experiment a");
    setupTarget(12604379L, "tubesky recommendation max count 0");
    setupTarget(12604380L, "tubesky recommendation max count 1");
    setupTarget(12604381L, "tubesky recommendation max count 2");
    setupTarget(12604360L, "enable SYSTEM_ALERT_WINDOW policy #1");
    setupTarget(12605120L, "enable SYSTEM_ALERT_WINDOW policy #2");
    setupTarget(12605124L, "enable SYSTEM_ALERT_WINDOW policy #3");
    setupTarget(12604495L, "disable user settings cache");
    setupProcessStableTarget(12604225L, "dummy process stable exp a");
    setupProcessStableTarget(12604226L, "dummy process stable exp b");
    setupProcessStableTarget(12604227L, "dummy process stable exp c");
    setupProcessStableTarget(12604228L, "dummy process stable exp d");
    setupProcessStableTarget(12604229L, "dummy process stable exp e");
    setupProcessStableTarget(12603505L, "freshwater_tabs");
    setupProcessStableTarget(12603731L, "freshwater_quick_links");
    setupProcessStableTarget(12604101L, "freshwater_disable_hscroll");
    setupProcessStableTarget(12604267L, "h20 alternate density mode");
    setupProcessStableTarget(12603123L, "fife max density dpi 200");
    setupProcessStableTarget(12603125L, "fife max density dpi 225");
    setupProcessStableTarget(12603127L, "fife max density dpi 250");
    setupProcessStableTarget(12603128L, "fife max density dpi 275");
    setupProcessStableTarget(12603129L, "fife max density dpi 300");
    setupProcessStableTarget(12603130L, "fife max density dpi 325");
    setupProcessStableTarget(12603642L, "network use okhttp stack without SPDY");
    setupProcessStableTarget(12602748L, "network use okhttp stack with SPDY");
    setupProcessStableTarget(12604235L, "network use okhttp stack with SPDY and 150 percent timeout");
    setupProcessStableTarget(12604236L, "network use okhttp stack with SPDY and 200 percent timeout");
    setupProcessStableTarget(12603144L, "network timeout multiplier 50 percent");
    setupProcessStableTarget(12603145L, "network timeout multiplier 75 percent");
    setupProcessStableTarget(12603146L, "network timeout multiplier 100 percent");
    setupProcessStableTarget(12603147L, "network timeout multiplier 125 percent");
    setupProcessStableTarget(12603148L, "network timeout multiplier 150 percent");
    setupProcessStableTarget(12603149L, "network timeout multiplier 175 percent");
    setupProcessStableTarget(12603118L, "bitmap disk cache size multiplier 50 percent");
    setupProcessStableTarget(12603119L, "bitmap disk cache size multiplier 75 percent");
    setupProcessStableTarget(12603120L, "bitmap disk cache size multiplier 100 percent");
    setupProcessStableTarget(12603121L, "bitmap disk cache size multiplier 125 percent");
    setupProcessStableTarget(12603122L, "bitmap disk cache size multiplier 150 percent");
    setupProcessStableTarget(12603247L, "main disk cache size multiplier 50 percent");
    setupProcessStableTarget(12603248L, "main disk cache size multiplier 75 percent");
    setupProcessStableTarget(12603249L, "main disk cache size multiplier 100 percent");
    setupProcessStableTarget(12603250L, "main disk cache size multiplier 125 percent");
    setupProcessStableTarget(12603251L, "main disk cache size multiplier 150 percent");
    setupProcessStableTarget(12602374L, "enable dfe request logging");
    setupProcessStableTarget(12603408L, "log cache hits in request log");
    setupProcessStableTarget(12603629L, "disable recyclerview scroll workaround");
    if (sRecognizedTargetsCount != sRecognizedTargets.size())
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(sRecognizedTargetsCount);
      arrayOfObject2[1] = Integer.valueOf(sRecognizedTargets.size());
      FinskyLog.wtf("Mismatch in recognized targets count. Expected: %d, Actual: %d", arrayOfObject2);
    }
    if (sProcessStableTargetsCount != sProcessStableTargets.size())
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Integer.valueOf(sProcessStableTargetsCount);
      arrayOfObject1[1] = Integer.valueOf(sProcessStableTargets.size());
      FinskyLog.wtf("Mismatch in process stable targets count. Expected: %d, Actual: %d", arrayOfObject1);
    }
  }
  
  public FinskyExperiments(String paramString, TargetsChangeListener... paramVarArgs)
  {
    this.mAccountName = paramString;
    Collections.addAll(this.mChangeListeners, paramVarArgs);
    setTargetsLoadedFromDisk(Utils.commaUnpackLongs((String)FinskyPreferences.targetList.get(this.mAccountName).get()));
    long[] arrayOfLong = Utils.commaUnpackLongs((String)FinskyPreferences.targetOverrideList.get(this.mAccountName).get());
    if (arrayOfLong.length > 0) {}
    for (;;)
    {
      setOverriddenTargets(arrayOfLong);
      return;
      arrayOfLong = null;
    }
  }
  
  private static long[] getNewTargetsToBeEnabled(long[] paramArrayOfLong)
  {
    long[] arrayOfLong1 = Utils.commaUnpackLongs((String)G.additionalTargets.get());
    if (arrayOfLong1.length == 0) {
      return paramArrayOfLong;
    }
    long[] arrayOfLong2 = new long[paramArrayOfLong.length + arrayOfLong1.length];
    for (int i = 0; i < paramArrayOfLong.length; i++) {
      arrayOfLong2[i] = paramArrayOfLong[i];
    }
    for (int j = 0; j < arrayOfLong1.length; j++) {
      arrayOfLong2[(j + paramArrayOfLong.length)] = arrayOfLong1[j];
    }
    return arrayOfLong2;
  }
  
  private void setHeadersAndAnalytics(long[] paramArrayOfLong1, long[] paramArrayOfLong2, int paramInt1, int paramInt2)
  {
    long[] arrayOfLong1 = Arrays.copyOf(paramArrayOfLong1, paramInt1);
    long[] arrayOfLong2 = Arrays.copyOf(paramArrayOfLong2, paramInt2);
    this.mActiveTargets = new ClientAnalytics.ActiveExperiments();
    if (arrayOfLong1.length > 0) {
      this.mActiveTargets.playExperiment = arrayOfLong1;
    }
    if (arrayOfLong2.length > 0) {
      this.mActiveTargets.unsupportedPlayExperiment = arrayOfLong2;
    }
    this.mEnabledTargetsHeaderValue = Utils.commaPackLongs(arrayOfLong1);
    this.mUnsupportedTargetsHeaderValue = Utils.commaPackLongs(arrayOfLong2);
  }
  
  private void setOverriddenTargets(long[] paramArrayOfLong)
  {
    PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.targetOverrideList.get(this.mAccountName);
    if ((paramArrayOfLong == null) || (paramArrayOfLong.length == 0))
    {
      localSharedPreference.remove();
      this.mOverriddenTargets = null;
    }
    for (;;)
    {
      return;
      localSharedPreference.put(Utils.commaPackLongs(paramArrayOfLong));
      this.mOverriddenTargets = new LongSparseArray(paramArrayOfLong.length);
      int i = paramArrayOfLong.length;
      for (int j = 0; j < i; j++)
      {
        long l = paramArrayOfLong[j];
        this.mOverriddenTargets.append(l, null);
      }
    }
  }
  
  private void setTargetsLoadedFromDisk(long[] paramArrayOfLong)
  {
    long[] arrayOfLong1 = getNewTargetsToBeEnabled(paramArrayOfLong);
    this.mEnabledTargets.clear();
    long[] arrayOfLong2 = new long[arrayOfLong1.length];
    long[] arrayOfLong3 = new long[arrayOfLong1.length];
    int i = arrayOfLong1.length;
    int j = 0;
    int k = 0;
    int m = 0;
    if (j < i)
    {
      long l = arrayOfLong1[j];
      int i1;
      int n;
      if (sRecognizedTargets.indexOfKey(l) >= 0)
      {
        i1 = m + 1;
        arrayOfLong2[m] = l;
        this.mEnabledTargets.append(l, null);
        n = k;
      }
      for (;;)
      {
        j++;
        k = n;
        m = i1;
        break;
        n = k + 1;
        arrayOfLong3[k] = l;
        i1 = m;
      }
    }
    setHeadersAndAnalytics(arrayOfLong2, arrayOfLong3, m, k);
  }
  
  private static void setupProcessStableTarget(long paramLong, String paramString)
  {
    sProcessStableTargets.append(paramLong, null);
    sProcessStableTargetsCount = 1 + sProcessStableTargetsCount;
    setupTarget(paramLong, paramString);
  }
  
  private static void setupTarget(long paramLong, String paramString)
  {
    sRecognizedTargets.append(paramLong, paramString);
    sRecognizedTargetsCount = 1 + sRecognizedTargetsCount;
  }
  
  public final ClientAnalytics.ActiveExperiments getActiveExperiments()
  {
    try
    {
      ClientAnalytics.ActiveExperiments localActiveExperiments = this.mActiveTargets;
      return localActiveExperiments;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final String getEnabledTargetsHeaderValue()
  {
    try
    {
      String str = this.mEnabledTargetsHeaderValue;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final String getUnsupportedTargetsHeaderValue()
  {
    try
    {
      String str = this.mUnsupportedTargetsHeaderValue;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final boolean hasEnabledTargets()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 824	com/google/android/finsky/experiments/FinskyExperiments:mOverriddenTargets	Landroid/support/v4/util/LongSparseArray;
    //   8: ifnull +26 -> 34
    //   11: aload_0
    //   12: getfield 824	com/google/android/finsky/experiments/FinskyExperiments:mOverriddenTargets	Landroid/support/v4/util/LongSparseArray;
    //   15: invokevirtual 710	android/support/v4/util/LongSparseArray:size	()I
    //   18: istore 4
    //   20: iload 4
    //   22: ifle +7 -> 29
    //   25: aload_0
    //   26: monitorexit
    //   27: iload_1
    //   28: ireturn
    //   29: iconst_0
    //   30: istore_1
    //   31: goto -6 -> 25
    //   34: aload_0
    //   35: getfield 737	com/google/android/finsky/experiments/FinskyExperiments:mEnabledTargets	Landroid/support/v4/util/LongSparseArray;
    //   38: invokevirtual 710	android/support/v4/util/LongSparseArray:size	()I
    //   41: istore_3
    //   42: iload_3
    //   43: ifgt -18 -> 25
    //   46: iconst_0
    //   47: istore_1
    //   48: goto -23 -> 25
    //   51: astore_2
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_2
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	FinskyExperiments
    //   1	47	1	bool	boolean
    //   51	4	2	localObject	Object
    //   41	2	3	i	int
    //   18	3	4	j	int
    // Exception table:
    //   from	to	target	type
    //   4	20	51	finally
    //   34	42	51	finally
  }
  
  /* Error */
  public final boolean hasUnsupportedTargets()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 819	com/google/android/finsky/experiments/FinskyExperiments:mUnsupportedTargetsHeaderValue	Ljava/lang/String;
    //   6: invokestatic 860	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   9: istore_2
    //   10: iload_2
    //   11: ifne +9 -> 20
    //   14: iconst_1
    //   15: istore_3
    //   16: aload_0
    //   17: monitorexit
    //   18: iload_3
    //   19: ireturn
    //   20: iconst_0
    //   21: istore_3
    //   22: goto -6 -> 16
    //   25: astore_1
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	FinskyExperiments
    //   25	4	1	localObject	Object
    //   9	2	2	bool1	boolean
    //   15	7	3	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	10	25	finally
  }
  
  /* Error */
  public final boolean isEnabled(long paramLong)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 824	com/google/android/finsky/experiments/FinskyExperiments:mOverriddenTargets	Landroid/support/v4/util/LongSparseArray;
    //   8: ifnull +81 -> 89
    //   11: aload_0
    //   12: getfield 824	com/google/android/finsky/experiments/FinskyExperiments:mOverriddenTargets	Landroid/support/v4/util/LongSparseArray;
    //   15: lload_1
    //   16: invokevirtual 844	android/support/v4/util/LongSparseArray:indexOfKey	(J)I
    //   19: iflt +59 -> 78
    //   22: iload_3
    //   23: istore 6
    //   25: aload_0
    //   26: getfield 737	com/google/android/finsky/experiments/FinskyExperiments:mEnabledTargets	Landroid/support/v4/util/LongSparseArray;
    //   29: lload_1
    //   30: invokevirtual 844	android/support/v4/util/LongSparseArray:indexOfKey	(J)I
    //   33: iflt +51 -> 84
    //   36: iload 6
    //   38: iload_3
    //   39: if_icmpeq +34 -> 73
    //   42: iconst_2
    //   43: anewarray 4	java/lang/Object
    //   46: astore 7
    //   48: aload 7
    //   50: iconst_0
    //   51: lload_1
    //   52: invokestatic 867	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   55: aastore
    //   56: aload 7
    //   58: iconst_1
    //   59: iload 6
    //   61: invokestatic 872	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   64: aastore
    //   65: ldc_w 874
    //   68: aload 7
    //   70: invokestatic 877	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   73: aload_0
    //   74: monitorexit
    //   75: iload 6
    //   77: ireturn
    //   78: iconst_0
    //   79: istore 6
    //   81: goto -56 -> 25
    //   84: iconst_0
    //   85: istore_3
    //   86: goto -50 -> 36
    //   89: aload_0
    //   90: getfield 737	com/google/android/finsky/experiments/FinskyExperiments:mEnabledTargets	Landroid/support/v4/util/LongSparseArray;
    //   93: lload_1
    //   94: invokevirtual 844	android/support/v4/util/LongSparseArray:indexOfKey	(J)I
    //   97: istore 5
    //   99: iload 5
    //   101: iflt +9 -> 110
    //   104: iload_3
    //   105: istore 6
    //   107: goto -34 -> 73
    //   110: iconst_0
    //   111: istore 6
    //   113: goto -40 -> 73
    //   116: astore 4
    //   118: aload_0
    //   119: monitorexit
    //   120: aload 4
    //   122: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	this	FinskyExperiments
    //   0	123	1	paramLong	long
    //   1	104	3	bool1	boolean
    //   116	5	4	localObject	Object
    //   97	3	5	i	int
    //   23	89	6	bool2	boolean
    //   46	23	7	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   4	22	116	finally
    //   25	36	116	finally
    //   42	73	116	finally
    //   89	99	116	finally
  }
  
  public final boolean isH20StoreEnabled()
  {
    return (isEnabled(12603505L)) || (isEnabled(12603731L));
  }
  
  public final void setTargets(Targets paramTargets)
  {
    long[] arrayOfLong2;
    int j;
    label108:
    int n;
    try
    {
      PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.targetList.get(this.mAccountName);
      String str1 = (String)localSharedPreference.get();
      long[] arrayOfLong1 = paramTargets.targetId;
      Arrays.sort(arrayOfLong1);
      String str2 = Utils.commaPackLongs(arrayOfLong1);
      boolean bool = TextUtils.equals(str1, str2);
      if (bool) {
        return;
      }
      localSharedPreference.put(str2);
      arrayOfLong2 = getNewTargetsToBeEnabled(arrayOfLong1);
      LongSparseArray localLongSparseArray1 = this.mEnabledTargets;
      LongSparseArray localLongSparseArray2 = new LongSparseArray(arrayOfLong2.length);
      LongSparseArray localLongSparseArray3 = new LongSparseArray(arrayOfLong2.length);
      int i = arrayOfLong2.length;
      j = 0;
      if (j < i)
      {
        long l1 = arrayOfLong2[j];
        if (localLongSparseArray1.indexOfKey(l1) >= 0) {
          break label789;
        }
        localLongSparseArray2.append(l1, null);
        break label789;
      }
      LongSparseArray localLongSparseArray4 = new LongSparseArray(arrayOfLong2.length);
      int k = arrayOfLong2.length;
      for (int m = 0; m < k; m++) {
        localLongSparseArray4.append(arrayOfLong2[m], null);
      }
      label187:
      if (n < localLongSparseArray1.size())
      {
        long l6 = localLongSparseArray1.keyAt(n);
        if (localLongSparseArray4.indexOfKey(l6) >= 0) {
          break label801;
        }
        localLongSparseArray3.append(l6, null);
        break label801;
      }
      if ((localLongSparseArray2.size() > 0) || (localLongSparseArray3.size() > 0))
      {
        Iterator localIterator1 = this.mChangeListeners.iterator();
        while (localIterator1.hasNext()) {
          ((TargetsChangeListener)localIterator1.next()).onTargetsChanged(localLongSparseArray2, localLongSparseArray3);
        }
      }
      arrayOfLong3 = new long[sProcessStableTargets.size()];
    }
    finally {}
    long[] arrayOfLong3;
    int i1 = 0;
    int i2 = 0;
    label307:
    int i18;
    long[] arrayOfLong5;
    long[] arrayOfLong6;
    int i3;
    int i6;
    label455:
    int i16;
    label478:
    int i9;
    label533:
    int i8;
    label585:
    int i10;
    int i11;
    int i12;
    if (i2 < sProcessStableTargets.size())
    {
      long l5 = sProcessStableTargets.keyAt(i2);
      if (isEnabled(l5))
      {
        i18 = i1 + 1;
        arrayOfLong3[i1] = l5;
        break label807;
      }
    }
    else
    {
      long[] arrayOfLong4 = Arrays.copyOf(arrayOfLong3, i1);
      this.mEnabledTargets.clear();
      arrayOfLong5 = new long[arrayOfLong2.length + arrayOfLong4.length];
      arrayOfLong6 = new long[arrayOfLong2.length];
      i3 = 0;
      int i4 = 0;
      while (i4 < arrayOfLong4.length)
      {
        int i5 = i3 + 1;
        arrayOfLong5[i3] = arrayOfLong4[i4];
        this.mEnabledTargets.append(arrayOfLong4[i4], null);
        i4++;
        i3 = i5;
      }
      if (this.mChangeListeners.size() > 0)
      {
        i6 = 0;
        if (i6 < arrayOfLong4.length)
        {
          long l4 = arrayOfLong4[i6];
          int i15 = arrayOfLong2.length;
          i16 = 0;
          int i17 = 0;
          if (i16 < i15)
          {
            if (arrayOfLong2[i16] != l4) {
              break label817;
            }
            i17 = 1;
          }
          if (i17 != 0) {
            break label823;
          }
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Long.valueOf(l4);
          FinskyLog.d("Process stable target turned off mid-process: %d", arrayOfObject2);
        }
        for (i9 = 1; i9 != 0; i9 = 1)
        {
          Iterator localIterator2 = this.mChangeListeners.iterator();
          while (localIterator2.hasNext()) {
            ((TargetsChangeListener)localIterator2.next()).onProcessStableTargetsChanged();
          }
          int i7 = arrayOfLong2.length;
          i8 = 0;
          if (i8 >= i7) {
            break label835;
          }
          long l2 = arrayOfLong2[i8];
          if ((sProcessStableTargets.indexOfKey(l2) < 0) || (this.mEnabledTargets.indexOfKey(l2) >= 0)) {
            break label829;
          }
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Long.valueOf(l2);
          FinskyLog.d("Process stable target turned on mid-process: %d", arrayOfObject1);
        }
      }
      i10 = arrayOfLong2.length;
      i11 = 0;
      i12 = 0;
    }
    for (;;)
    {
      if (i11 < i10)
      {
        long l3 = arrayOfLong2[i11];
        if (this.mEnabledTargets.indexOfKey(l3) < 0)
        {
          if ((sRecognizedTargets.indexOfKey(l3) >= 0) && (sProcessStableTargets.indexOfKey(l3) < 0))
          {
            i13 = i3 + 1;
            arrayOfLong5[i3] = l3;
            this.mEnabledTargets.append(l3, null);
            break label841;
          }
          int i14 = i12 + 1;
          arrayOfLong6[i12] = l3;
          i12 = i14;
          i13 = i3;
          break label841;
        }
      }
      else
      {
        setHeadersAndAnalytics(arrayOfLong5, arrayOfLong6, i3, i12);
        break;
      }
      int i13 = i3;
      break label841;
      i18 = i1;
      break label807;
      label789:
      j++;
      break label108;
      n = 0;
      break label187;
      label801:
      n++;
      break label187;
      label807:
      i2++;
      i1 = i18;
      break label307;
      label817:
      i16++;
      break label478;
      label823:
      i6++;
      break label455;
      label829:
      i8++;
      break label585;
      label835:
      i9 = 0;
      break label533;
      label841:
      i11++;
      i3 = i13;
    }
  }
  
  public static abstract interface TargetsChangeListener
  {
    public abstract void onProcessStableTargetsChanged();
    
    public abstract void onTargetsChanged(LongSparseArray<Object> paramLongSparseArray1, LongSparseArray<Object> paramLongSparseArray2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.experiments.FinskyExperiments
 * JD-Core Version:    0.7.0.1
 */