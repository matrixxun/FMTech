package com.google.android.finsky.services;

import android.accounts.Account;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.reviews.IReviewsService.Stub;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.RateReviewActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Details.DetailsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlusProfileResponse;
import com.google.android.finsky.protos.Review;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.SignatureUtils;
import com.google.android.play.dfe.api.PlayDfeApi;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewsService
  extends Service
{
  private static AtomicInteger sRequestCounter = new AtomicInteger(0);
  private final IReviewsService.Stub mBinder = new IReviewsService.Stub()
  {
    public final Bundle getRateAndReviewIntent(String paramAnonymousString1, String paramAnonymousString2)
      throws RemoteException
    {
      FinskyApp localFinskyApp = FinskyApp.get();
      Account[] arrayOfAccount = AccountHandler.getAccounts(localFinskyApp);
      int i = arrayOfAccount.length;
      int j = 0;
      int k = j;
      Object localObject = null;
      Bundle localBundle;
      if (k < i)
      {
        Account localAccount = arrayOfAccount[j];
        if (localAccount.name.equals(paramAnonymousString1)) {
          localObject = localAccount;
        }
      }
      else
      {
        if (localObject != null) {
          break label93;
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = FinskyLog.scrubPii(paramAnonymousString1);
        FinskyLog.d("No account found for %s", arrayOfObject);
        localBundle = null;
      }
      label93:
      FinskyEventLog localFinskyEventLog;
      String str1;
      Document localDocument1;
      for (;;)
      {
        return localBundle;
        j++;
        break;
        localFinskyEventLog = localFinskyApp.getEventLogger(paramAnonymousString1);
        str1 = SignatureUtils.getCallingAppIfAuthorized(ReviewsService.this, paramAnonymousString2, null, localFinskyEventLog, 514);
        if (str1 == null) {
          return null;
        }
        FinskyLog.d("Received rate&review request for %s from %s", new Object[] { paramAnonymousString2, str1 });
        localBundle = new Bundle();
        DfeApi localDfeApi = FinskyApp.get().getDfeApi(paramAnonymousString1);
        PlayDfeApi localPlayDfeApi = FinskyApp.get().getPlayDfeApi(null);
        RequestFuture localRequestFuture1 = RequestFuture.newFuture();
        localPlayDfeApi.getPlusProfile(localRequestFuture1, localRequestFuture1, true);
        try
        {
          PlusProfileResponse localPlusProfileResponse = (PlusProfileResponse)localRequestFuture1.get();
          DocV2 localDocV21 = localPlusProfileResponse.partialUserProfile;
          boolean bool1 = ((Boolean)FinskyPreferences.acceptedPlusReviews.get(paramAnonymousString1).get()).booleanValue();
          if ((!FinskyApp.get().mToc.mToc.gplusSignupEnabled) || ((bool1) && (localDocV21 != null)))
          {
            if (localDocV21 == null) {
              break label400;
            }
            localDocument1 = new Document(localDocV21);
          }
        }
        catch (InterruptedException localInterruptedException1)
        {
          try
          {
            RequestFuture localRequestFuture2;
            localDetailsResponse = (Details.DetailsResponse)localRequestFuture2.get();
            localDocV22 = localDetailsResponse.docV2;
            if (localDocV22 != null) {
              break label482;
            }
            FinskyLog.d("No doc in details response for %s", new Object[] { paramAnonymousString2 });
            return localBundle;
          }
          catch (InterruptedException localInterruptedException2)
          {
            Throwable localThrowable1;
            String str2;
            FinskyLog.d("Interrupted while trying to retrieve item details", new Object[0]);
            return localBundle;
          }
          catch (ExecutionException localExecutionException2)
          {
            Details.DetailsResponse localDetailsResponse;
            Throwable localThrowable2 = localExecutionException2.getCause();
            if (localThrowable2 != null) {
              break label469;
            }
            for (String str3 = null;; str3 = localThrowable2.getClass().getSimpleName())
            {
              FinskyLog.d("Unable to retrieve item details: %s", new Object[] { str3 });
              SignatureUtils.logEvent(localFinskyEventLog, 514, paramAnonymousString2, "fetch-doc-error", str1, str3);
              return localBundle;
            }
            localReview = localFinskyApp.getClientMutationCache(paramAnonymousString1).getCachedReview(paramAnonymousString2, localDetailsResponse.userReview);
            if (localDocument1 != null) {
              break label774;
            }
          }
          localInterruptedException1 = localInterruptedException1;
          FinskyLog.d("Interrupted while trying to retrieve plus profile", new Object[0]);
          return localBundle;
        }
        catch (ExecutionException localExecutionException1)
        {
          for (;;)
          {
            localThrowable1 = localExecutionException1.getCause();
            if (localThrowable1 == null) {}
            for (str2 = null;; str2 = localThrowable1.getClass().getSimpleName())
            {
              FinskyLog.d("Unable to retrieve plus profile: %s", new Object[] { str2 });
              SignatureUtils.logEvent(localFinskyEventLog, 514, paramAnonymousString2, "fetch-plus-error", str1, str2);
              return localBundle;
            }
            localDocument1 = null;
          }
        }
      }
      localRequestFuture2 = RequestFuture.newFuture();
      localDfeApi.getDetails(DfeUtils.createDetailsUrlFromId(paramAnonymousString2), true, true, null, localRequestFuture2, localRequestFuture2);
      DocV2 localDocV22;
      label400:
      label469:
      label482:
      Review localReview;
      boolean bool2 = true;
      Document localDocument2 = new Document(localDocV22);
      if (localReview != null) {}
      for (int m = localReview.starRating;; m = 0)
      {
        Intent localIntent = RateReviewActivity.createIntent(paramAnonymousString1, localDocument2, localDocument1, localReview, m, true, bool2, ReviewsService.this.getBaseContext());
        localIntent.setData(Uri.fromParts("reviewsservice", localDocV22.docid, Integer.toString(ReviewsService.sRequestCounter.getAndIncrement())));
        PendingIntent localPendingIntent = PendingIntent.getActivity(ReviewsService.this, 0, localIntent, 1073741824);
        localBundle.putParcelable("rate_and_review_intent", localPendingIntent);
        localBundle.putInt("rate_and_review_request_code", 43);
        String str4 = localDocV22.docid;
        localBundle.putString("doc_id", str4);
        String str5 = localDocV22.title;
        localBundle.putString("doc_title", str5);
        if (localReview != null)
        {
          int n = localReview.starRating;
          localBundle.putInt("rating", n);
          String str8 = localReview.title;
          localBundle.putString("review_title", str8);
          String str9 = localReview.comment;
          localBundle.putString("review_comment", str9);
        }
        if (localDocument1 != null)
        {
          String str6 = localDocument1.mDocument.title;
          localBundle.putString("author_title", str6);
          String str7 = ((Common.Image)localDocument1.getImages(4).get(0)).imageUrl;
          localBundle.putString("author_profile_image_url", str7);
        }
        SignatureUtils.logEvent(localFinskyEventLog, 514, paramAnonymousString2, null, str1, null);
        return localBundle;
        label774:
        bool2 = false;
        break;
      }
    }
  };
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.ReviewsService
 * JD-Core Version:    0.7.0.1
 */