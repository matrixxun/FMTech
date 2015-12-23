package com.google.android.finsky.widget.recommendation;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.ListResponse;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.ParcelUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.config.GservicesValue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class RecommendationsStore
{
  private static final String CACHE_FILE_PREFIX = RecommendationList.class.getSimpleName();
  private static final ExecutorService sWriteThread = Executors.newSingleThreadExecutor(new BackgroundThreadFactory());
  
  public static void deleteCachedRecommendations(Context paramContext, int paramInt)
  {
    getCacheFile(paramContext, paramInt).delete();
  }
  
  public static Bitmap getBitmap(BitmapLoader paramBitmapLoader, Recommendation paramRecommendation, int paramInt)
  {
    Common.Image localImage = paramRecommendation.mImage;
    if (localImage == null) {
      return null;
    }
    int i;
    final Semaphore localSemaphore;
    Bitmap[] arrayOfBitmap;
    if (localImage.supportsFifeUrlOptions)
    {
      i = paramInt;
      localSemaphore = new Semaphore(0);
      arrayOfBitmap = new Bitmap[1];
      BitmapLoader.BitmapContainer localBitmapContainer = paramBitmapLoader.get(localImage.imageUrl, 0, i, new BitmapLoader.BitmapLoadedHandler()
      {
        public final void onResponse(BitmapLoader.BitmapContainer paramAnonymousBitmapContainer)
        {
          this.val$bitmap[0] = paramAnonymousBitmapContainer.mBitmap;
          localSemaphore.release();
        }
      });
      if (localBitmapContainer.mBitmap == null) {
        break label89;
      }
      arrayOfBitmap[0] = localBitmapContainer.mBitmap;
    }
    for (;;)
    {
      return arrayOfBitmap[0];
      i = 0;
      break;
      try
      {
        label89:
        if (!localSemaphore.tryAcquire(((Long)G.recommendationsFetchTimeoutMs.get()).longValue(), TimeUnit.MILLISECONDS))
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localImage.imageUrl;
          FinskyLog.e("Timed out while fetching %s", arrayOfObject2);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localImage.imageUrl;
        FinskyLog.e("Interrupted while fetching %s", arrayOfObject1);
      }
    }
  }
  
  public static File getCacheFile(Context paramContext, int paramInt)
  {
    File localFile = new File(paramContext.getCacheDir(), "recs");
    localFile.mkdirs();
    return new File(localFile, CACHE_FILE_PREFIX + "_" + paramInt + ".cache");
  }
  
  public static RecommendationList getRecommendations(Context paramContext, DfeApi paramDfeApi, int paramInt, Library paramLibrary)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    File localFile = getCacheFile(paramContext, paramInt);
    boolean bool = localFile.exists();
    RecommendationList localRecommendationList = null;
    if (bool)
    {
      localRecommendationList = (RecommendationList)ParcelUtils.readFromDisk(localFile);
      if (localRecommendationList != null)
      {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = localRecommendationList.mRecommendations.iterator();
        label117:
        while (localIterator.hasNext())
        {
          Recommendation localRecommendation = (Recommendation)localIterator.next();
          if (localRecommendation.mExpirationTimeMs < System.currentTimeMillis()) {}
          for (int i = 1;; i = 0)
          {
            if (i == 0) {
              break label117;
            }
            localArrayList.add(localRecommendation);
            break;
          }
        }
        localRecommendationList.mRecommendations.removeAll(localArrayList);
        localArrayList.size();
      }
    }
    if ((localRecommendationList == null) || (localRecommendationList.isEmpty()))
    {
      RequestFuture localRequestFuture = RequestFuture.newFuture();
      String str = getRecsWidgetUrl(paramInt);
      if (TextUtils.isEmpty(str))
      {
        FinskyLog.e("No recs widget url provided in loadDocsFromNetwork().", new Object[0]);
        throw new IllegalStateException("No recs url provided");
      }
      paramDfeApi.getList(str, null, localRequestFuture, localRequestFuture);
      localRecommendationList = parseNetworkResponse((ListResponse)localRequestFuture.get(((Long)G.recommendationsFetchTimeoutMs.get()).longValue(), TimeUnit.MILLISECONDS), paramInt, paramLibrary);
    }
    try
    {
      ParcelUtils.writeToDisk(localFile, localRecommendationList);
      return localRecommendationList;
    }
    catch (IOException localIOException)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.e(localIOException, "Unable to cache recs for %d", arrayOfObject);
    }
    return localRecommendationList;
  }
  
  public static RecommendationList getRecommendationsOrShowError(Context paramContext, DfeApi paramDfeApi, int paramInt1, int paramInt2, Library paramLibrary)
  {
    
    try
    {
      RecommendationList localRecommendationList2 = getRecommendations(paramContext, paramDfeApi, paramInt1, paramLibrary);
      localRecommendationList1 = localRecommendationList2;
    }
    catch (InterruptedException localInterruptedException)
    {
      String str4;
      do
      {
        localInterruptedException = localInterruptedException;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = localInterruptedException.getMessage();
        FinskyLog.e(localInterruptedException, "Error loading recs widget: %s", arrayOfObject3);
        str4 = paramContext.getString(2131362362);
        localRecommendationList1 = null;
      } while (str4 == null);
      RecommendedWidgetProvider.showError(paramContext, paramInt2, str4);
      return null;
    }
    catch (ExecutionException localExecutionException)
    {
      String str3;
      do
      {
        localExecutionException = localExecutionException;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = localExecutionException.getCause().getMessage();
        FinskyLog.e(localExecutionException, "Error loading recs widget: %s", arrayOfObject2);
        str3 = paramContext.getString(2131362362);
        localRecommendationList1 = null;
      } while (str3 == null);
      RecommendedWidgetProvider.showError(paramContext, paramInt2, str3);
      return null;
    }
    catch (TimeoutException localTimeoutException)
    {
      String str2;
      do
      {
        localTimeoutException = localTimeoutException;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localTimeoutException.getMessage();
        FinskyLog.e(localTimeoutException, "Error loading recs widget: %s", arrayOfObject1);
        str2 = paramContext.getString(2131362787);
        localRecommendationList1 = null;
      } while (str2 == null);
      RecommendedWidgetProvider.showError(paramContext, paramInt2, str2);
      return null;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      String str1;
      do
      {
        localIllegalStateException = localIllegalStateException;
        str1 = paramContext.getString(2131362383);
        RecommendationList localRecommendationList1 = null;
      } while (str1 == null);
      RecommendedWidgetProvider.showError(paramContext, paramInt2, str1);
      return null;
    }
    finally {}
    return localRecommendationList1;
  }
  
  public static String getRecsWidgetUrl(int paramInt)
  {
    return (String)FinskyPreferences.widgetUrlsByBackend.get(paramInt).get();
  }
  
  private static RecommendationList parseNetworkResponse(ListResponse paramListResponse, int paramInt, Library paramLibrary)
  {
    if (paramListResponse.doc.length == 0)
    {
      localRecommendationList = null;
      return localRecommendationList;
    }
    Document localDocument1 = new Document(paramListResponse.doc[0]);
    RecommendationList localRecommendationList = new RecommendationList(localDocument1.mDocument.title, paramInt);
    int i = localDocument1.getChildCount();
    PackageStateRepository localPackageStateRepository = FinskyApp.get().mPackageStateRepository;
    int j = 0;
    label62:
    Document localDocument2;
    int m;
    if (j < i)
    {
      localDocument2 = localDocument1.getChildAt(j);
      int k = localDocument2.mDocument.docType;
      m = 0;
      if (k == 1)
      {
        if (localPackageStateRepository.get(localDocument2.getAppDetails().packageName) == null) {
          break label169;
        }
        m = 1;
      }
      label117:
      if ((!LibraryUtils.isOwned(localDocument2, paramLibrary)) && (m == 0)) {
        break label175;
      }
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localDocument2.mDocument.docid;
        FinskyLog.v("Already own %s, skipping", arrayOfObject);
      }
    }
    for (;;)
    {
      j++;
      break label62;
      break;
      label169:
      m = 0;
      break label117;
      label175:
      localRecommendationList.add(new Recommendation(localDocument2));
    }
  }
  
  public static void performBackFill(DfeApi paramDfeApi, final Context paramContext, RecommendationList paramRecommendationList, final Library paramLibrary, final int paramInt)
  {
    String str = getRecsWidgetUrl(paramRecommendationList.mBackendId);
    if (TextUtils.isEmpty(str))
    {
      FinskyLog.e("No recs widget url provided in performBackFill()", new Object[0]);
      return;
    }
    paramDfeApi.getList(str, null, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Error while fetching more recs: %s", new Object[] { paramAnonymousVolleyError });
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.RecommendationsStore
 * JD-Core Version:    0.7.0.1
 */