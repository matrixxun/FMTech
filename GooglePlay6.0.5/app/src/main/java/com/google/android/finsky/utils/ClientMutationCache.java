package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ReviewFeedbackListener.ReviewFeedbackRating;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.model.CirclesModel;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.hats.SurveyStore;
import com.google.android.finsky.utils.persistence.FileBasedKeyValueStore;
import com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore;
import com.google.android.play.dfe.api.PlayDfeApi;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ClientMutationCache
{
  static final CachedReview DELETED_REVIEW = null;
  private static final int MAX_NUM_CACHED_CIRCLES_MODEL = ((Integer)G.clientMutationCacheCirclesModelCacheSize.get()).intValue();
  private final String mAccount;
  public Boolean mAgeVerificationRequired;
  private List<CirclesModel> mCachedCirclesModels = new ArrayList();
  private Map<Pair<String, String>, EnumSet<ReviewFeedbackListener.ReviewFeedbackRating>> mCachedReviewFeedback = new HashMap();
  private Set<String> mDismissedRecommendationDocIds = new HashSet();
  public boolean mFamilySafeSearchMode;
  Map<String, CachedReview> mReviewedDocIds = new HashMap();
  private SurveyStore mSurveyStore;
  WriteThroughKeyValueStore mUnsubmittedReviewsBackingStore;
  
  public ClientMutationCache(Context paramContext, String paramString)
  {
    this.mAccount = paramString;
    this.mUnsubmittedReviewsBackingStore = new WriteThroughKeyValueStore(new FileBasedKeyValueStore(paramContext.getCacheDir(), "unsubmitted_reviews_" + Uri.encode(this.mAccount)));
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        ClientMutationCache.access$000(ClientMutationCache.this);
      }
    });
  }
  
  public static void pruneUnsubmittedReviews(Context paramContext)
  {
    File localFile1 = paramContext.getCacheDir();
    try
    {
      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile == null) {
        return;
      }
      long l = System.currentTimeMillis() - ((Long)G.unsubmittedReviewLifespanMs.get()).longValue();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
      {
        File localFile2 = arrayOfFile[j];
        if ((localFile2.getName().startsWith("unsubmitted_reviews_")) && ((localFile2.length() == 0L) || (localFile2.lastModified() < l))) {
          localFile2.delete();
        }
      }
      Object[] arrayOfObject;
      return;
    }
    catch (Exception localException)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = localException.toString();
      FinskyLog.e("Error pruning unsubmitted reviews: %s", arrayOfObject);
    }
  }
  
  public final void addReviewFeedback(String paramString1, String paramString2, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    Pair localPair = Pair.create(paramString1, paramString2);
    EnumSet localEnumSet1 = (EnumSet)this.mCachedReviewFeedback.get(localPair);
    if (localEnumSet1 == null)
    {
      EnumSet localEnumSet2 = EnumSet.of(paramReviewFeedbackRating);
      this.mCachedReviewFeedback.put(localPair, localEnumSet2);
      return;
    }
    localEnumSet1.add(paramReviewFeedbackRating);
  }
  
  public final void dismissRecommendation(String paramString)
  {
    this.mDismissedRecommendationDocIds.add(paramString);
  }
  
  public final CirclesModel getCachedCirclesModel(Document paramDocument, String paramString)
  {
    for (int i = 0; i < this.mCachedCirclesModels.size(); i++)
    {
      CirclesModel localCirclesModel2 = (CirclesModel)this.mCachedCirclesModels.get(i);
      if ((localCirclesModel2.mTargetPersonDoc.mDocument.docid.equals(paramDocument.mDocument.docid)) && (localCirclesModel2.mOwnerAccountName.equals(paramString)))
      {
        this.mCachedCirclesModels.remove(i);
        this.mCachedCirclesModels.add(localCirclesModel2);
        return localCirclesModel2;
      }
    }
    CirclesModel localCirclesModel1 = new CirclesModel(paramDocument, paramString);
    if (this.mCachedCirclesModels.size() >= MAX_NUM_CACHED_CIRCLES_MODEL) {
      this.mCachedCirclesModels.remove(0);
    }
    this.mCachedCirclesModels.add(localCirclesModel1);
    return localCirclesModel1;
  }
  
  /* Error */
  public final Review getCachedReview(String paramString, Review paramReview)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 62	com/google/android/finsky/utils/ClientMutationCache:mReviewedDocIds	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 276 2 0
    //   12: ifeq +25 -> 37
    //   15: aload_0
    //   16: getfield 62	com/google/android/finsky/utils/ClientMutationCache:mReviewedDocIds	Ljava/util/Map;
    //   19: aload_1
    //   20: invokeinterface 212 2 0
    //   25: checkcast 278	com/google/android/finsky/utils/ClientMutationCache$CachedReview
    //   28: astore 4
    //   30: aload 4
    //   32: ifnonnull +9 -> 41
    //   35: aconst_null
    //   36: astore_2
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_2
    //   40: areturn
    //   41: aload 4
    //   43: getfield 282	com/google/android/finsky/utils/ClientMutationCache$CachedReview:review	Lcom/google/android/finsky/protos/Review;
    //   46: astore_2
    //   47: goto -10 -> 37
    //   50: astore_3
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_3
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	this	ClientMutationCache
    //   0	55	1	paramString	String
    //   0	55	2	paramReview	Review
    //   50	4	3	localObject	Object
    //   28	14	4	localCachedReview	CachedReview
    // Exception table:
    //   from	to	target	type
    //   2	30	50	finally
    //   41	47	50	finally
  }
  
  public final SurveyStore getSurveyStore()
  {
    if (this.mSurveyStore == null) {
      this.mSurveyStore = new SurveyStore(this.mAccount);
    }
    return this.mSurveyStore;
  }
  
  public final boolean isAgeVerificationRequired()
  {
    if (this.mAgeVerificationRequired == null) {
      this.mAgeVerificationRequired = Boolean.valueOf(FinskyApp.get().mToc.mToc.ageVerificationRequired);
    }
    return this.mAgeVerificationRequired.booleanValue();
  }
  
  public final boolean isDismissedRecommendation(String paramString)
  {
    return this.mDismissedRecommendationDocIds.contains(paramString);
  }
  
  public final boolean isReviewFeedbackCached(String paramString1, String paramString2, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    EnumSet localEnumSet = (EnumSet)this.mCachedReviewFeedback.get(Pair.create(paramString1, paramString2));
    return (localEnumSet != null) && (localEnumSet.contains(paramReviewFeedbackRating));
  }
  
  /* Error */
  public final void removeCachedReview(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 113	com/google/android/finsky/utils/ClientMutationCache:mUnsubmittedReviewsBackingStore	Lcom/google/android/finsky/utils/persistence/WriteThroughKeyValueStore;
    //   6: aload_1
    //   7: invokevirtual 331	com/google/android/finsky/utils/persistence/WriteThroughKeyValueStore:delete	(Ljava/lang/String;)V
    //   10: aload_0
    //   11: getfield 62	com/google/android/finsky/utils/ClientMutationCache:mReviewedDocIds	Ljava/util/Map;
    //   14: aload_1
    //   15: invokeinterface 276 2 0
    //   20: istore_3
    //   21: iload_3
    //   22: ifne +6 -> 28
    //   25: aload_0
    //   26: monitorexit
    //   27: return
    //   28: aload_0
    //   29: getfield 62	com/google/android/finsky/utils/ClientMutationCache:mReviewedDocIds	Ljava/util/Map;
    //   32: aload_1
    //   33: invokeinterface 333 2 0
    //   38: pop
    //   39: goto -14 -> 25
    //   42: astore_2
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_2
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	ClientMutationCache
    //   0	47	1	paramString	String
    //   42	4	2	localObject	Object
    //   20	2	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	21	42	finally
    //   28	39	42	finally
  }
  
  public final void removeReviewFeedback(String paramString1, String paramString2, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    Pair localPair = Pair.create(paramString1, paramString2);
    EnumSet localEnumSet = (EnumSet)this.mCachedReviewFeedback.get(localPair);
    if (localEnumSet != null) {
      localEnumSet.remove(paramReviewFeedbackRating);
    }
  }
  
  public final void updateCachedReview(String paramString1, int paramInt, String paramString2, String paramString3, Document paramDocument, String paramString4)
  {
    try
    {
      CachedReview localCachedReview = new CachedReview(paramString1, paramInt, paramString2, paramString3, paramDocument, paramString4, System.currentTimeMillis());
      this.mReviewedDocIds.put(paramString1, localCachedReview);
      WriteThroughKeyValueStore localWriteThroughKeyValueStore = this.mUnsubmittedReviewsBackingStore;
      HashMap localHashMap = new HashMap();
      localHashMap.put("doc_id", localCachedReview.docId);
      localHashMap.put("rating", localCachedReview.review.starRating);
      localHashMap.put("title", localCachedReview.review.title);
      localHashMap.put("content", localCachedReview.review.comment);
      localHashMap.put("doc_details_url_key", localCachedReview.docDetailsUrl);
      localHashMap.put("doc_timestamp", localCachedReview.review.timestampMsec);
      localWriteThroughKeyValueStore.put(paramString1, localHashMap);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void updateCachedReviewDeleted(String paramString)
  {
    try
    {
      this.mReviewedDocIds.put(paramString, DELETED_REVIEW);
      this.mUnsubmittedReviewsBackingStore.delete(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static final class CachedReview
  {
    public final String docDetailsUrl;
    public final String docId;
    final Review review = new Review();
    boolean submitted = false;
    
    public CachedReview(String paramString1, int paramInt, String paramString2, String paramString3, Document paramDocument, String paramString4, long paramLong)
    {
      this.review.starRating = paramInt;
      this.review.hasStarRating = true;
      this.review.title = paramString2;
      this.review.hasTitle = true;
      this.review.comment = paramString3;
      this.review.hasComment = true;
      Review localReview = this.review;
      if (paramDocument != null) {}
      for (DocV2 localDocV2 = paramDocument.mDocument;; localDocV2 = null)
      {
        localReview.author = localDocV2;
        this.review.timestampMsec = paramLong;
        this.review.hasTimestampMsec = true;
        this.docId = paramString1;
        this.docDetailsUrl = paramString4;
        return;
      }
    }
    
    public static CachedReview fromMap(Map<String, String> paramMap, Document paramDocument)
    {
      if ((!paramMap.containsKey("doc_id")) || (!paramMap.containsKey("rating")) || (!paramMap.containsKey("title")) || (!paramMap.containsKey("content")) || (!paramMap.containsKey("doc_details_url_key")) || (!paramMap.containsKey("doc_timestamp")))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramMap.keySet().toString();
        FinskyLog.e("Review badly persisted: %s", arrayOfObject1);
        return null;
      }
      try
      {
        CachedReview localCachedReview = new CachedReview((String)paramMap.get("doc_id"), Integer.parseInt((String)paramMap.get("rating")), (String)paramMap.get("title"), (String)paramMap.get("content"), paramDocument, (String)paramMap.get("doc_details_url_key"), Long.parseLong((String)paramMap.get("doc_timestamp")));
        return localCachedReview;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localNumberFormatException.toString();
        FinskyLog.e("Error parsing numbers from persisted cache: %s", arrayOfObject2);
      }
      return null;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ClientMutationCache
 * JD-Core Version:    0.7.0.1
 */