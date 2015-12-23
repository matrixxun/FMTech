package com.google.android.finsky.widget.consumption;

import android.util.SparseIntArray;
import com.google.android.finsky.config.G;
import com.google.android.finsky.services.ConsumptionAppDoc;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.widget.WidgetUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class NowPlayingScorer
{
  private static final boolean ALWAYS_INCLUDE_MOST_RECENT = ((Boolean)G.myLibraryWidgetAlwaysIncludeMostRecentBackend.get()).booleanValue();
  private static final SparseIntArray HAS_CONTENT_SCORE_MAP;
  private static final float IMPORTANT_CONTRIBUTION_FRACTION;
  private static final long INTERACTION_CLUSTER_MS = ((Long)G.myLibraryWidgetInteractionClusterMs.get()).longValue();
  private static final long MIDTERM_INTERACTION_MS;
  private static final SparseIntArray MIDTERM_INTERACTION_SCORE_MAP;
  private static final float SHORTTERM_DECAY;
  private static final long SHORTTERM_INTERACTION_MS = ((Long)G.myLibraryWidgetShorttermDurationMs.get()).longValue();
  private static final SparseIntArray SHORTTERM_INTERACTION_SCORE_MAP;
  private static final Comparator<ConsumptionAppDoc> UPDATE_TIME_COMPARATOR = new Comparator() {};
  
  static
  {
    MIDTERM_INTERACTION_MS = ((Long)G.myLibraryWidgetMidtermDurationMs.get()).longValue();
    SHORTTERM_INTERACTION_SCORE_MAP = WidgetUtils.parseSparseIntArray((String)G.myLibraryWidgetShorttermScores.get());
    MIDTERM_INTERACTION_SCORE_MAP = WidgetUtils.parseSparseIntArray((String)G.myLibraryWidgeMidtermScores.get());
    SHORTTERM_DECAY = ((Float)G.myLibraryWidgetShorttermDecay.get()).floatValue();
    HAS_CONTENT_SCORE_MAP = WidgetUtils.parseSparseIntArray((String)G.myLibraryWidgetHasContentScoreMap.get());
    IMPORTANT_CONTRIBUTION_FRACTION = ((Float)G.myLibraryWidgetImportantContributionFraction.get()).floatValue();
  }
  
  private static List<ConsumptionAppDocList> getImportantBackends(List<ConsumptionAppDocList> paramList, int paramInt, Map<ConsumptionAppDocList, Integer> paramMap, ConsumptionAppDocList paramConsumptionAppDocList)
  {
    ArrayList localArrayList = Lists.newArrayList(paramInt);
    int i = 0;
    int j = 0;
    int k = 0;
    ConsumptionAppDocList localConsumptionAppDocList;
    int n;
    if (k < paramInt)
    {
      localConsumptionAppDocList = (ConsumptionAppDocList)paramList.get(k);
      int m = ((Integer)paramMap.get(localConsumptionAppDocList)).intValue();
      n = i + m;
      if (n != 0)
      {
        if ((k <= 1) || (m / n >= IMPORTANT_CONTRIBUTION_FRACTION)) {
          break label168;
        }
        if (FinskyLog.DEBUG)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(localConsumptionAppDocList.mBackend);
          FinskyLog.v("Dropping backend=%d and after.", arrayOfObject);
        }
      }
    }
    if ((ALWAYS_INCLUDE_MOST_RECENT) && (j == 0) && (paramConsumptionAppDocList != null))
    {
      if (localArrayList.size() == paramInt) {
        localArrayList.remove(-1 + localArrayList.size());
      }
      localArrayList.add(paramConsumptionAppDocList);
    }
    return localArrayList;
    label168:
    localArrayList.add(localConsumptionAppDocList);
    i = n;
    if (localConsumptionAppDocList == paramConsumptionAppDocList) {}
    for (int i1 = 1;; i1 = 0)
    {
      j |= i1;
      k++;
      break;
    }
  }
  
  public static List<ConsumptionAppDocList> score(List<ConsumptionAppDocList> paramList, int paramInt, long paramLong)
  {
    int i = Math.min(paramInt, paramList.size());
    HashMap localHashMap = new HashMap();
    Object localObject1 = null;
    long l1 = 0L;
    Iterator localIterator1 = paramList.iterator();
    Object localObject2;
    int j;
    long l2;
    long l3;
    long l4;
    long l5;
    int k;
    float f2;
    float f3;
    int m;
    label143:
    long l7;
    int i5;
    float f4;
    float f5;
    int i6;
    if (localIterator1.hasNext())
    {
      localObject2 = (ConsumptionAppDocList)localIterator1.next();
      j = ((ConsumptionAppDocList)localObject2).mBackend;
      ArrayList localArrayList2 = new ArrayList((Collection)localObject2);
      Collections.sort(localArrayList2, UPDATE_TIME_COMPARATOR);
      float f1 = SHORTTERM_INTERACTION_SCORE_MAP.get(j, SHORTTERM_INTERACTION_SCORE_MAP.get(0));
      l2 = paramLong - SHORTTERM_INTERACTION_MS;
      l3 = paramLong - MIDTERM_INTERACTION_MS;
      Iterator localIterator2 = localArrayList2.iterator();
      l4 = 0L;
      l5 = 0L;
      k = 0;
      f2 = 0.0F;
      f3 = f1;
      m = 0;
      if (localIterator2.hasNext())
      {
        ConsumptionAppDoc localConsumptionAppDoc = (ConsumptionAppDoc)localIterator2.next();
        l7 = localConsumptionAppDoc.mLastUpdateTimeMs;
        if (l7 - l4 < INTERACTION_CLUSTER_MS)
        {
          if (!FinskyLog.DEBUG) {
            break label629;
          }
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = localConsumptionAppDoc.mDocId;
          arrayOfObject2[1] = Long.valueOf(INTERACTION_CLUSTER_MS);
          FinskyLog.v("Not scoring doc %s, since it's within %d ms of previous interaction", arrayOfObject2);
          i5 = m;
          f4 = f3;
          f5 = f2;
          i6 = k;
        }
      }
    }
    for (;;)
    {
      l5 = Math.max(l5, l7);
      l4 = l7;
      k = i6;
      f2 = f5;
      f3 = f4;
      m = i5;
      break label143;
      if (l7 > l2)
      {
        int i7 = k + 1;
        float f6 = f2 + f3;
        float f7 = f3 * SHORTTERM_DECAY;
        i5 = m;
        f4 = f7;
        f5 = f6;
        i6 = i7;
      }
      else if (l7 > l3)
      {
        i5 = m + 1;
        f4 = f3;
        f5 = f2;
        i6 = k;
        continue;
        int n = MIDTERM_INTERACTION_SCORE_MAP.get(j, MIDTERM_INTERACTION_SCORE_MAP.get(0));
        int i1 = ((ConsumptionAppDocList)localObject2).size();
        int i2 = 0;
        if (i1 > 0) {
          i2 = HAS_CONTENT_SCORE_MAP.get(j, HAS_CONTENT_SCORE_MAP.get(0));
        }
        int i3 = n * m;
        int i4 = i2 + (i3 + (int)f2);
        if (FinskyLog.DEBUG)
        {
          Object[] arrayOfObject1 = new Object[8];
          arrayOfObject1[0] = Integer.valueOf(j);
          arrayOfObject1[1] = Integer.valueOf(i4);
          arrayOfObject1[2] = Float.valueOf(f2);
          arrayOfObject1[3] = Integer.valueOf(i3);
          arrayOfObject1[4] = Integer.valueOf(i2);
          arrayOfObject1[5] = Integer.valueOf(k);
          arrayOfObject1[6] = Integer.valueOf(m);
          arrayOfObject1[7] = Integer.valueOf(((ConsumptionAppDocList)localObject2).size());
          FinskyLog.d("Score for backend %d: %d (shorttermscore=%.3g,midtermscore=%d,hascontentscore=%d,shorttermcount=%d,midtermcount=%d,totalcount=%d)", arrayOfObject1);
        }
        CorpusScore localCorpusScore = new CorpusScore((byte)0);
        localCorpusScore.score = i4;
        localCorpusScore.lastInteractionMs = l5;
        localHashMap.put(localObject2, Integer.valueOf(localCorpusScore.score));
        long l6;
        if (localCorpusScore.lastInteractionMs > l1) {
          l6 = localCorpusScore.lastInteractionMs;
        }
        for (;;)
        {
          l1 = l6;
          localObject1 = localObject2;
          break;
          ArrayList localArrayList1 = new ArrayList(paramList);
          Collections.sort(localArrayList1, new Comparator() {});
          return getImportantBackends(localArrayList1, i, localHashMap, localObject1);
          l6 = l1;
          localObject2 = localObject1;
        }
      }
      else
      {
        label629:
        i5 = m;
        f4 = f3;
        f5 = f2;
        i6 = k;
      }
    }
  }
  
  private static final class CorpusScore
  {
    long lastInteractionMs;
    int score;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.NowPlayingScorer
 * JD-Core Version:    0.7.0.1
 */