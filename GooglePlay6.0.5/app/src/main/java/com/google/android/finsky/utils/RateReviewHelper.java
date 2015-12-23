package com.google.android.finsky.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.android.volley.NetworkError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.ReviewData;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.ReviewResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.play.dfe.api.PlayDfeApi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class RateReviewHelper
{
  private static final int[] DESCRIPTION_MAP = { 2131362678, 2131362675, 2131362671, 2131362670, 2131362669, 2131362674 };
  
  public static void checkAndConfirmGPlus(FragmentActivity paramFragmentActivity, final CheckAndConfirmGPlusListener paramCheckAndConfirmGPlusListener, final boolean paramBoolean)
  {
    FinskyApp.get().getPlayDfeApi(null).getPlusProfile(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAnonymousVolleyError.toString();
        FinskyLog.e("Error getting G+ profile: %s", arrayOfObject);
        RateReviewHelper.access$000(this.val$activity, null);
        paramCheckAndConfirmGPlusListener.onCheckAndConfirmGPlusFailed();
      }
    }, true);
  }
  
  public static void deleteReview(String paramString1, final String paramString2, final String paramString3, final Context paramContext, final RateReviewListener paramRateReviewListener)
  {
    ClientMutationCache localClientMutationCache = FinskyApp.get().getClientMutationCache(paramString1);
    localClientMutationCache.updateCachedReviewDeleted(paramString2);
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(paramString1);
    localDfeApi.deleteReview(paramString2, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        this.val$clientMutationCache.removeCachedReview(paramString2);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAnonymousVolleyError.toString();
        FinskyLog.e("Error posting review: %s", arrayOfObject);
        RateReviewHelper.access$100(paramContext);
        if (paramRateReviewListener != null) {
          paramRateReviewListener.onRateReviewFailed();
        }
      }
    });
  }
  
  public static int getRatingDescription(int paramInt)
  {
    return DESCRIPTION_MAP[paramInt];
  }
  
  public static void rateDocument$359c2010(String paramString1, final String paramString2, final String paramString3, final int paramInt, final FragmentActivity paramFragmentActivity, final RateReviewListener paramRateReviewListener)
  {
    checkAndConfirmGPlus(paramFragmentActivity, new CheckAndConfirmGPlusListener()
    {
      public final void onCheckAndConfirmGPlusFailed()
      {
        if (paramRateReviewListener != null) {
          paramRateReviewListener.onRateReviewFailed();
        }
      }
      
      public final void onCheckAndConfirmGPlusPassed(final Document paramAnonymousDocument)
      {
        if (((Boolean)FinskyPreferences.internalFakeItemRaterEnabled.get()).booleanValue())
        {
          int i = 500 + (int)(1500.0D * Math.random());
          new Handler(Looper.myLooper()).postDelayed(new Runnable()
          {
            public final void run()
            {
              FinskyApp.get().getClientMutationCache(RateReviewHelper.7.this.val$accountName).updateCachedReview(RateReviewHelper.7.this.val$docId, RateReviewHelper.7.this.val$rating, "", "", paramAnonymousDocument, RateReviewHelper.7.this.val$docDetailsUrl);
              RateReviewHelper.7.this.val$rateListener.onRateReviewCommitted$6ef37c42(RateReviewHelper.7.this.val$rating);
            }
          }, i);
          return;
        }
        RateReviewHelper.updateReview(this.val$accountName, paramString2, paramString3, paramInt, "", "", paramAnonymousDocument, paramFragmentActivity, paramRateReviewListener, this.val$reviewSource);
      }
    }, true);
  }
  
  private static void sendReviewToServer(String paramString1, final String paramString2, final String paramString3, final int paramInt, final String paramString4, final String paramString5, final Context paramContext, final RateReviewListener paramRateReviewListener)
  {
    final DfeApi localDfeApi = FinskyApp.get().getDfeApi(paramString1);
    ClientMutationCache localClientMutationCache = FinskyApp.get().getClientMutationCache(paramString1);
    localDfeApi.addReview(paramString2, paramString4, paramString5, paramInt, FinskyApp.get().mToc.mToc.gplusSignupEnabled, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        if (!(paramAnonymousVolleyError instanceof NetworkError))
        {
          this.val$clientMutationCache.removeCachedReview(paramString2);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousVolleyError.toString();
          FinskyLog.e("Error posting review: %s", arrayOfObject);
          String str = ErrorStrings.get(paramContext, paramAnonymousVolleyError);
          RateReviewHelper.access$000(paramContext, str);
        }
        if (paramRateReviewListener != null) {
          paramRateReviewListener.onRateReviewFailed();
        }
      }
    });
  }
  
  public static void updateReview(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, String paramString5, Document paramDocument, Context paramContext, RateReviewListener paramRateReviewListener, int paramInt2)
  {
    ClientMutationCache localClientMutationCache = FinskyApp.get().getClientMutationCache(paramString1);
    if ((TextUtils.isEmpty(paramString5)) && (!TextUtils.isEmpty(paramString4)))
    {
      paramString5 = paramString4;
      paramString4 = "";
    }
    String str1 = paramString4;
    String str2 = paramString5;
    localClientMutationCache.updateCachedReview(paramString2, paramInt1, str1, str2, paramDocument, paramString3);
    sendReviewToServer(paramString1, paramString2, paramString3, paramInt1, str1, str2, paramContext, paramRateReviewListener);
    int i;
    if (TextUtils.isEmpty(paramString4))
    {
      i = 0;
      if (!TextUtils.isEmpty(paramString5)) {
        break label203;
      }
    }
    label203:
    for (int j = 0;; j = paramString5.length())
    {
      int k = i + j;
      FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger(paramString1);
      PlayStore.ReviewData localReviewData = new PlayStore.ReviewData();
      localReviewData.reviewSource = paramInt2;
      localReviewData.hasReviewSource = true;
      localReviewData.rating = paramInt1;
      localReviewData.hasRating = true;
      if (k > 0)
      {
        localReviewData.textLength = k;
        localReviewData.hasTextLength = true;
      }
      BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(513);
      localBackgroundEventBuilder.event.reviewData = localReviewData;
      localFinskyEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      return;
      i = paramString4.length();
      break;
    }
  }
  
  public static void updateUnsubmittedReviews(String paramString, Context paramContext)
  {
    ClientMutationCache localClientMutationCache = FinskyApp.get().getClientMutationCache(paramString);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = localClientMutationCache.mReviewedDocIds.values().iterator();
    while (localIterator1.hasNext())
    {
      ClientMutationCache.CachedReview localCachedReview2 = (ClientMutationCache.CachedReview)localIterator1.next();
      if ((localCachedReview2 != ClientMutationCache.DELETED_REVIEW) && (!localCachedReview2.submitted)) {
        localArrayList.add(localCachedReview2);
      }
    }
    if (localArrayList.isEmpty()) {}
    for (;;)
    {
      return;
      Iterator localIterator2 = localArrayList.iterator();
      while (localIterator2.hasNext())
      {
        ClientMutationCache.CachedReview localCachedReview1 = (ClientMutationCache.CachedReview)localIterator2.next();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = FinskyLog.scrubPii(paramString);
        arrayOfObject[1] = localCachedReview1.docId;
        FinskyLog.w("Sending unsubmitted review for account: %s and docId: %s", arrayOfObject);
        Review localReview = localCachedReview1.review;
        sendReviewToServer(paramString, localCachedReview1.docId, localCachedReview1.docDetailsUrl, localReview.starRating, localReview.title, localReview.comment, paramContext, null);
      }
    }
  }
  
  public static abstract interface CheckAndConfirmGPlusListener
  {
    public abstract void onCheckAndConfirmGPlusFailed();
    
    public abstract void onCheckAndConfirmGPlusPassed(Document paramDocument);
  }
  
  public static abstract interface RateReviewListener
  {
    public abstract void onRateReviewCommitted$6ef37c42(int paramInt);
    
    public abstract void onRateReviewFailed();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.RateReviewHelper
 * JD-Core Version:    0.7.0.1
 */