package com.google.android.finsky.download;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.ParameterizedRunnable;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.collections.Lists;
import java.io.File;
import java.util.Collections;
import java.util.List;

public final class DownloadManagerLegacyImpl
  implements DownloadManagerFacade
{
  private static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
  private static final Uri FROYO_CONTENT_URI = Uri.parse("content://downloads/download");
  private static final String[] QUERY_FILENAME_PROJECTION = { "_data" };
  private static Boolean sDownloadManagerUsesFroyoStrings = null;
  private ContentObserver mContentObserver;
  private final ContentResolver mContentResolver;
  private final Handler mHandler;
  private DownloadManagerFacade.Listener mListener;
  private Cursor mListenerCursor;
  
  protected DownloadManagerLegacyImpl(Context paramContext)
  {
    this.mContentResolver = paramContext.getContentResolver();
    HandlerThread localHandlerThread = BackgroundThreadFactory.createHandlerThread("download-manager-thread");
    localHandlerThread.start();
    this.mHandler = new Handler(localHandlerThread.getLooper());
    this.mContentObserver = new ContentObserver(this.mHandler)
    {
      public final boolean deliverSelfNotifications()
      {
        return false;
      }
      
      public final void onChange(boolean paramAnonymousBoolean)
      {
        DownloadManagerLegacyImpl.access$000(DownloadManagerLegacyImpl.this);
      }
    };
  }
  
  private static String getContentUriString(String paramString)
  {
    if (isFroyoDownloadManager()) {
      return "content://downloads/download/" + paramString;
    }
    return "content://downloads/my_downloads/" + paramString;
  }
  
  private static boolean isFroyoDownloadManager()
  {
    if (Build.VERSION.SDK_INT > 10) {
      return false;
    }
    if (sDownloadManagerUsesFroyoStrings != null) {
      return sDownloadManagerUsesFroyoStrings.booleanValue();
    }
    return ((Boolean)FinskyPreferences.downloadManagerUsesFroyoStrings.get()).booleanValue();
  }
  
  public final void enqueue(Download paramDownload, final ParameterizedRunnable<Uri> paramParameterizedRunnable)
  {
    if ((FinskyLog.DEBUG) && (Build.MODEL.equals("google_sdk")))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramDownload.toString();
      FinskyLog.d("Skip download of %s because emulator", arrayOfObject);
      return;
    }
    final ContentValues localContentValues = new ContentValues();
    localContentValues.put("otheruid", Integer.valueOf(1000));
    localContentValues.put("uri", paramDownload.getUrl());
    Uri localUri = paramDownload.getRequestedDestination();
    String str3;
    if (localUri != null)
    {
      localContentValues.put("destination", Integer.valueOf(4));
      localContentValues.put("hint", localUri.toString());
      localContentValues.put("notificationpackage", FinskyApp.get().getPackageName());
      localContentValues.put("notificationclass", DownloadBroadcastReceiver.class.getName());
      String str1 = paramDownload.getCookieName();
      String str2 = paramDownload.getCookieValue();
      if ((str1 != null) && (str2 != null)) {
        localContentValues.put("cookiedata", str1 + "=" + str2);
      }
      boolean bool = paramDownload.getInvisible();
      str3 = paramDownload.getTitle();
      if ((!bool) && (!TextUtils.isEmpty(str3))) {
        break label332;
      }
      localContentValues.put("visibility", Integer.valueOf(2));
      label232:
      if (paramDownload.getWifiOnly())
      {
        if ((Build.VERSION.SDK_INT < 16) || (!UiUtils.isAndroidTv())) {
          break label355;
        }
        localContentValues.put("allow_metered", Boolean.valueOf(false));
        localContentValues.put("allowed_network_types", Integer.valueOf(-1));
      }
    }
    for (;;)
    {
      localContentValues.put("is_public_api", Boolean.valueOf(true));
      localContentValues.put("allow_roaming", Boolean.valueOf(true));
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          try
          {
            Uri localUri = DownloadManagerLegacyImpl.this.mContentResolver.insert(DownloadManagerLegacyImpl.CONTENT_URI, localContentValues);
            DownloadManagerLegacyImpl.access$300(localUri);
            if (paramParameterizedRunnable != null) {
              paramParameterizedRunnable.run(localUri);
            }
            return;
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = localContentValues.toString();
            FinskyLog.e(localIllegalArgumentException, "Unable to insert download request for %s", arrayOfObject);
          }
        }
      });
      return;
      localContentValues.put("destination", Integer.valueOf(2));
      break;
      label332:
      localContentValues.put("visibility", Integer.valueOf(0));
      localContentValues.put("title", str3);
      break label232;
      label355:
      localContentValues.put("allowed_network_types", Integer.valueOf(2));
    }
  }
  
  public final Uri getFileUriForContentUri(Uri paramUri)
  {
    if ("file".equalsIgnoreCase(paramUri.getScheme())) {
      return paramUri;
    }
    Cursor localCursor = null;
    Object localObject2;
    try
    {
      localCursor = this.mContentResolver.query(paramUri, QUERY_FILENAME_PROJECTION, null, null, null);
      boolean bool = localCursor.moveToFirst();
      localObject2 = null;
      if (bool)
      {
        String str = localCursor.getString(0);
        localObject2 = str;
      }
      if (localCursor != null) {
        localCursor.close();
      }
      if (localObject2 == null) {
        return null;
      }
    }
    finally
    {
      if (localCursor != null) {
        localCursor.close();
      }
    }
    return Uri.fromFile(new File(Uri.parse(localObject2).getPath()));
  }
  
  @TargetApi(11)
  public final Uri getUriFromBroadcast(Intent paramIntent)
  {
    Uri localUri = paramIntent.getData();
    if (localUri != null) {
      return localUri;
    }
    long l = paramIntent.getLongExtra("extra_download_id", -1L);
    if (l == -1L)
    {
      long[] arrayOfLong = paramIntent.getLongArrayExtra("extra_click_download_ids");
      if ((arrayOfLong != null) && (arrayOfLong.length == 1)) {
        l = arrayOfLong[0];
      }
    }
    if (l == -1L) {
      return null;
    }
    return Uri.parse(getContentUriString(String.valueOf(l)));
  }
  
  public final List<DownloadProgress> query(Uri paramUri, DownloadManagerFacade.Listener paramListener)
  {
    
    if (paramUri == null) {
      if ((paramListener == null) || (!isFroyoDownloadManager())) {
        break label52;
      }
    }
    Cursor localCursor;
    label52:
    for (paramUri = FROYO_CONTENT_URI;; paramUri = CONTENT_URI)
    {
      localCursor = this.mContentResolver.query(paramUri, null, null, null, null);
      if (localCursor != null) {
        break;
      }
      FinskyLog.w("Download progress cursor null", new Object[0]);
      return Collections.emptyList();
    }
    Object localObject;
    if (localCursor.getCount() <= 0) {
      localObject = Collections.emptyList();
    }
    while (paramListener == null)
    {
      localCursor.close();
      return localObject;
      localObject = Lists.newArrayList(localCursor.getCount());
      int i = localCursor.getColumnIndexOrThrow("_id");
      int j = localCursor.getColumnIndexOrThrow("status");
      int k = localCursor.getColumnIndexOrThrow("current_bytes");
      int m = localCursor.getColumnIndexOrThrow("total_bytes");
      int n = localCursor.getColumnIndex("allowed_network_types");
      while (localCursor.moveToNext())
      {
        Uri localUri = Uri.parse(getContentUriString(String.valueOf(localCursor.getLong(i))));
        int i1 = localCursor.getInt(j);
        long l1 = localCursor.getLong(k);
        long l2 = localCursor.getLong(m);
        if ((i1 == 195) && (n != -1) && (localCursor.getInt(n) == 2)) {
          i1 = 196;
        }
        ((List)localObject).add(new DownloadProgress(localUri, l1, l2, i1));
      }
    }
    if (this.mListenerCursor != null)
    {
      this.mListenerCursor.unregisterContentObserver(this.mContentObserver);
      this.mListenerCursor.close();
    }
    this.mListenerCursor = localCursor;
    this.mListenerCursor.registerContentObserver(this.mContentObserver);
    this.mListener = paramListener;
    return localObject;
  }
  
  public final void remove(final Uri paramUri)
  {
    this.mHandler.post(new Runnable()
    {
      public final void run()
      {
        DownloadManagerLegacyImpl.access$400(DownloadManagerLegacyImpl.this, paramUri);
      }
    });
  }
  
  public final void unregisterListener$669df7dd()
  {
    if (this.mListenerCursor != null)
    {
      this.mListenerCursor.unregisterContentObserver(this.mContentObserver);
      this.mListenerCursor.close();
    }
    this.mListener = null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadManagerLegacyImpl
 * JD-Core Version:    0.7.0.1
 */