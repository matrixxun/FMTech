package com.google.android.play.analytics;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.play.utils.Assertions;
import com.google.android.volley.guava.UrlRules;
import com.google.android.volley.guava.UrlRules.Rule;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.zip.GZIPOutputStream;

public final class EventLogger
  implements RollingFileStream.WriteCallbacks<ClientAnalytics.LogEvent>
{
  private static final ClientAnalytics.ActiveExperiments EMPTY_EXPERIMENTS = new ClientAnalytics.ActiveExperiments();
  private static final HashSet<Account> sInstantiatedAccounts = new HashSet();
  private final Account mAccount;
  private final long mAndroidId;
  private final String mAppVersion;
  private final String mAuthTokenType;
  private final Handler mCallbackHandler;
  private final Context mContext;
  private final String mCountry;
  private final long mDelayBetweenUploadsMs;
  private final long mDeviceId;
  public final Handler mHandler;
  private ClientAnalytics.ActiveExperiments mLastSentExperiments;
  private final int mLogSource;
  private final String mLoggingId;
  private final long mMaxUploadSize;
  private final String mMccmnc;
  private final long mMinDelayBetweenUploadsMs;
  private final long mMinImmediateUploadSize;
  private volatile long mNextAllowedUploadTimeMs;
  public final ProtoCache mProtoCache;
  private final ContentResolver mResolver;
  private final RollingFileStream<ClientAnalytics.LogEvent> mRollingFileStream;
  private final int mServerTimeoutMs;
  private final String mServerUrl;
  private final String mUserAgent;
  
  public EventLogger(Context paramContext, String paramString1, LogSource paramLogSource, String paramString2, long paramLong, String paramString3, String paramString4, Configuration paramConfiguration, Account paramAccount)
  {
    this(paramContext, null, paramString1, paramLogSource, paramString2, paramLong, paramString3, paramString4, Locale.getDefault().getCountry(), paramConfiguration, paramAccount);
  }
  
  private EventLogger(Context paramContext, String paramString1, String paramString2, LogSource paramLogSource, String paramString3, long paramLong, String paramString4, String paramString5, String paramString6, Configuration paramConfiguration, Account paramAccount)
  {
    synchronized (sInstantiatedAccounts)
    {
      Assertions.checkState(sInstantiatedAccounts.add(paramAccount), "Already instantiated an EventLogger for " + paramAccount);
      this.mContext = paramContext;
      this.mResolver = paramContext.getContentResolver();
      this.mLogSource = paramLogSource.mProtoValue;
      this.mLoggingId = null;
      this.mAccount = paramAccount;
      this.mAuthTokenType = paramString2;
      this.mProtoCache = ProtoCache.getInstance();
      this.mUserAgent = paramString3;
      this.mAndroidId = paramLong;
      str1 = Settings.Secure.getString(this.mResolver, "android_id");
      long l1 = 0L;
      try
      {
        long l2 = new BigInteger(str1, 16).longValue();
        l1 = l2;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          final Semaphore localSemaphore;
          HandlerThread local2;
          Log.e("PlayEventLogger", "Invalid device id " + str1);
        }
      }
      catch (NullPointerException localNullPointerException)
      {
        for (;;)
        {
          File localFile1;
          File localFile2;
          Log.e("PlayEventLogger", "Null device id");
          continue;
          String str2 = paramAccount.type + "." + paramAccount.name;
          continue;
          File localFile3 = new File(localFile1, Uri.encode(paramAccount.name));
          if ((localFile3.exists()) && (localFile3.isDirectory())) {
            localFile3.renameTo(localFile2);
          }
        }
      }
      this.mDeviceId = l1;
      this.mAppVersion = paramString4;
      this.mMccmnc = paramString5;
      this.mCountry = paramString6;
      this.mServerUrl = paramConfiguration.mServerUrl;
      this.mDelayBetweenUploadsMs = paramConfiguration.delayBetweenUploadsMs;
      this.mMinDelayBetweenUploadsMs = paramConfiguration.minDelayBetweenUploadsMs;
      this.mServerTimeoutMs = paramConfiguration.mServerTimeoutMs;
      this.mMinImmediateUploadSize = (1L + 50L * paramConfiguration.recommendedLogFileSize / 100L);
      this.mMaxUploadSize = (125L * paramConfiguration.recommendedLogFileSize / 100L);
      localFile1 = new File(this.mContext.getCacheDir(), paramConfiguration.logDirectoryName);
      if (paramAccount == null)
      {
        str2 = "null_account";
        localFile2 = new File(localFile1, Uri.encode(str2));
        if ((paramAccount != null) && (!localFile2.exists())) {
          break label487;
        }
        this.mRollingFileStream = new RollingFileStream(localFile2, "eventlog.store", ".log", paramConfiguration.recommendedLogFileSize, paramConfiguration.maxStorageSize, paramConfiguration.recommendedTempFilesCountThreshold, this);
        this.mCallbackHandler = new Handler(Looper.getMainLooper());
        localSemaphore = new Semaphore(0);
        local2 = new HandlerThread("PlayEventLogger", localSemaphore)
        {
          protected final void onLooperPrepared()
          {
            localSemaphore.release();
          }
        };
        local2.start();
        localSemaphore.acquireUninterruptibly();
        this.mHandler = new Handler(local2.getLooper())
        {
          public final void dispatchMessage(Message paramAnonymousMessage)
          {
            EventLogger.access$000(EventLogger.this, paramAnonymousMessage);
          }
        };
        this.mHandler.sendEmptyMessage(1);
      }
    }
  }
  
  private String getAuthToken(Account paramAccount)
  {
    if (paramAccount == null)
    {
      Log.w("PlayEventLogger", "No account for auth token provided");
      return null;
    }
    try
    {
      String str = AccountManager.get(this.mContext).blockingGetAuthToken(paramAccount, this.mAuthTokenType, true);
      return str;
    }
    catch (OperationCanceledException localOperationCanceledException)
    {
      Log.e("PlayEventLogger", "Failed to get auth token: " + localOperationCanceledException.getMessage(), localOperationCanceledException);
      return null;
    }
    catch (AuthenticatorException localAuthenticatorException)
    {
      Log.e("PlayEventLogger", "Failed to get auth token: " + localAuthenticatorException.getMessage(), localAuthenticatorException);
      return null;
    }
    catch (IOException localIOException)
    {
      Log.e("PlayEventLogger", "Failed to get auth token: " + localIOException.getMessage(), localIOException);
      return null;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("PlayEventLogger", "Failed to get auth token: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
    return null;
  }
  
  private boolean handleResponse(HttpURLConnection paramHttpURLConnection, String paramString)
    throws IOException
  {
    int i = paramHttpURLConnection.getResponseCode();
    String str1 = paramHttpURLConnection.getResponseMessage();
    if ((200 <= i) && (i < 300)) {
      try
      {
        InputStream localInputStream = paramHttpURLConnection.getInputStream();
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte1 = new byte[''];
        int k;
        do
        {
          k = localInputStream.read(arrayOfByte1);
          if (k > 0) {
            localByteArrayOutputStream.write(arrayOfByte1, 0, k);
          }
        } while (k >= 0);
        byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
        ClientAnalytics.LogResponse localLogResponse = (ClientAnalytics.LogResponse)MessageNano.mergeFrom$1ec43da(new ClientAnalytics.LogResponse(), arrayOfByte2, arrayOfByte2.length);
        if (localLogResponse.nextRequestWaitMillis >= 0L) {
          setNextUploadTimeAfter(localLogResponse.nextRequestWaitMillis);
        }
        localInputStream.close();
        return true;
      }
      catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
      {
        Log.e("PlayEventLogger", "Error parsing content: " + localInvalidProtocolBufferNanoException.getMessage());
        return true;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e("PlayEventLogger", "Error getting the content of the response body: " + localIllegalStateException.getMessage());
        return true;
      }
      catch (IOException localIOException)
      {
        Log.e("PlayEventLogger", "Error reading the content of the response body: " + localIOException.getMessage());
        return true;
      }
    }
    if ((300 <= i) && (i < 400))
    {
      Log.e("PlayEventLogger", "Too many redirects for HttpUrlConnection. " + i);
      return false;
    }
    if (i == 400)
    {
      Log.e("PlayEventLogger", "Server returned 400... deleting local malformed logs");
      return true;
    }
    if (i == 401)
    {
      Log.w("PlayEventLogger", "Server returned 401... invalidating auth token");
      AccountManager.get(this.mContext).invalidateAuthToken(this.mAccount.type, paramString);
      return false;
    }
    if (i == 500)
    {
      Log.w("PlayEventLogger", "Server returned 500... server crashed");
      return false;
    }
    if (i == 501)
    {
      Log.w("PlayEventLogger", "Server returned 501... service doesn't seem to exist");
      return false;
    }
    if (i == 502)
    {
      Log.w("PlayEventLogger", "Server returned 502... servers are down");
      return false;
    }
    if (i == 503)
    {
      String str2 = paramHttpURLConnection.getHeaderField("Retry-After");
      if (str2 != null)
      {
        try
        {
          long l = Long.valueOf(str2).longValue();
          Log.w("PlayEventLogger", "Server said to retry after " + l + " seconds");
          setNextUploadTimeAfter(1000L * l);
          j = 1;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          for (;;)
          {
            Log.e("PlayEventLogger", "Unknown retry value: " + str2);
            int j = 0;
          }
        }
        return j == 0;
      }
      Log.e("PlayEventLogger", "Status 503 without retry-after header");
      return true;
    }
    if (i == 504)
    {
      Log.w("PlayEventLogger", "Server returned 504... timeout");
      return false;
    }
    Log.e("PlayEventLogger", "Unexpected error received from server: " + i + " " + str1);
    return true;
  }
  
  private void maybeQueueImmediateUpload()
  {
    RollingFileStream localRollingFileStream = this.mRollingFileStream;
    int i = 0;
    long l = 0L;
    while (i < localRollingFileStream.mWrittenFiles.size())
    {
      l += ((File)localRollingFileStream.mWrittenFiles.get(i)).length();
      i++;
    }
    if (l >= this.mMinImmediateUploadSize) {
      queueUpload(0L);
    }
  }
  
  private void queueUpload(long paramLong)
  {
    long l = System.currentTimeMillis();
    if (paramLong > 0L)
    {
      if (l + paramLong < this.mNextAllowedUploadTimeMs) {
        paramLong = this.mNextAllowedUploadTimeMs - l;
      }
      this.mHandler.sendEmptyMessageDelayed(3, paramLong);
    }
    for (;;)
    {
      this.mNextAllowedUploadTimeMs = Math.max(this.mNextAllowedUploadTimeMs, l + this.mMinDelayBetweenUploadsMs);
      return;
      this.mHandler.sendEmptyMessage(3);
    }
  }
  
  private void setNextUploadTimeAfter(long paramLong)
  {
    this.mNextAllowedUploadTimeMs = (Math.max(this.mMinDelayBetweenUploadsMs, paramLong) + System.currentTimeMillis());
  }
  
  private boolean uploadEventsImpl()
  {
    if (!this.mRollingFileStream.mWrittenFiles.isEmpty()) {}
    for (int i = 1; i == 0; i = 0) {
      return false;
    }
    long l1;
    long l2;
    label92:
    byte[][] arrayOfByte1;
    try
    {
      ArrayList localArrayList = new ArrayList();
      l1 = 0L;
      RollingFileStream localRollingFileStream1 = this.mRollingFileStream;
      Object localObject;
      RollingFileStream localRollingFileStream2;
      long l3;
      if (localRollingFileStream1.mWrittenFiles.isEmpty())
      {
        Log.e("RollingFileStream", "This method should never be called when there are no written files.");
        localObject = null;
        if (localObject != null)
        {
          if (localObject.length <= 0) {
            break label600;
          }
          localArrayList.add(localObject);
          l2 = l1 + localObject.length;
          localRollingFileStream2 = this.mRollingFileStream;
          if (!localRollingFileStream2.mWrittenFiles.isEmpty()) {
            break label186;
          }
          l3 = -1L;
        }
      }
      for (;;)
      {
        if ((l3 < 0L) || (l3 + l2 > this.mMaxUploadSize))
        {
          if (localArrayList.isEmpty())
          {
            arrayOfByte1 = null;
            break label606;
            File localFile = (File)localRollingFileStream1.mWrittenFiles.remove(0);
            byte[] arrayOfByte = RollingFileStream.toByteArray(localFile);
            localRollingFileStream1.mReadFiles.add(localFile);
            localObject = arrayOfByte;
            break;
            label186:
            l3 = ((File)localRollingFileStream2.mWrittenFiles.get(0)).length();
            continue;
          }
          arrayOfByte1 = new byte[localArrayList.size()][];
          localArrayList.toArray(arrayOfByte1);
        }
      }
    }
    catch (IOException localIOException)
    {
      Log.e("PlayEventLogger", "Read failed " + localIOException.getClass() + "(" + localIOException.getMessage() + ")");
      this.mRollingFileStream.markAllFilesAsUnread();
      return false;
    }
    label600:
    label606:
    do
    {
      ClientAnalytics.AndroidClientInfo localAndroidClientInfo = new ClientAnalytics.AndroidClientInfo();
      localAndroidClientInfo.androidId = this.mAndroidId;
      localAndroidClientInfo.deviceId = this.mDeviceId;
      if (this.mLoggingId != null) {
        localAndroidClientInfo.loggingId = this.mLoggingId;
      }
      localAndroidClientInfo.sdkVersion = Build.VERSION.SDK_INT;
      localAndroidClientInfo.manufacturer = Build.MANUFACTURER;
      localAndroidClientInfo.model = Build.MODEL;
      localAndroidClientInfo.product = Build.PRODUCT;
      localAndroidClientInfo.hardware = Build.HARDWARE;
      localAndroidClientInfo.device = Build.DEVICE;
      localAndroidClientInfo.osBuild = Build.ID;
      localAndroidClientInfo.brand = Build.BRAND;
      localAndroidClientInfo.board = Build.BOARD;
      localAndroidClientInfo.fingerprint = Build.FINGERPRINT;
      if (Build.VERSION.SDK_INT >= 14) {}
      boolean bool;
      for (String str = Build.getRadioVersion();; str = Build.RADIO)
      {
        if (str != null) {
          localAndroidClientInfo.radioVersion = str;
        }
        if (this.mMccmnc != null) {
          localAndroidClientInfo.mccMnc = this.mMccmnc;
        }
        localAndroidClientInfo.locale = Locale.getDefault().getLanguage();
        if (this.mCountry != null) {
          localAndroidClientInfo.country = this.mCountry;
        }
        if (this.mAppVersion != null) {
          localAndroidClientInfo.applicationBuild = this.mAppVersion;
        }
        ClientAnalytics.ClientInfo localClientInfo = new ClientAnalytics.ClientInfo();
        localClientInfo.clientType = 4;
        localClientInfo.androidClientInfo = localAndroidClientInfo;
        ClientAnalytics.LogRequest localLogRequest = new ClientAnalytics.LogRequest();
        localLogRequest.requestTimeMs = System.currentTimeMillis();
        localLogRequest.serializedLogEvents = arrayOfByte1;
        localLogRequest.logSource = this.mLogSource;
        localLogRequest.clientInfo = localClientInfo;
        bool = uploadLog(localLogRequest);
        if (!bool) {
          break;
        }
        this.mRollingFileStream.deleteAllReadFiles();
        return bool;
      }
      this.mRollingFileStream.markAllFilesAsUnread();
      return bool;
      l1 = l2;
      break;
      l2 = l1;
      break label92;
    } while (arrayOfByte1 != null);
    return false;
  }
  
  private boolean uploadLog(ClientAnalytics.LogRequest paramLogRequest)
  {
    String str1 = getAuthToken(this.mAccount);
    Object localObject1;
    try
    {
      String str2 = UrlRules.getRules(this.mResolver).matchRule(this.mServerUrl).apply(this.mServerUrl);
      if (TextUtils.isEmpty(str2))
      {
        localObject1 = null;
      }
      else
      {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str2).openConnection();
        localHttpURLConnection.setConnectTimeout(this.mServerTimeoutMs);
        localHttpURLConnection.setReadTimeout(this.mServerTimeoutMs);
        localHttpURLConnection.setDoOutput(true);
        localHttpURLConnection.setRequestMethod("POST");
        localHttpURLConnection.setRequestProperty("Content-Encoding", "gzip");
        localHttpURLConnection.setRequestProperty("Content-Type", "application/x-gzip");
        localHttpURLConnection.setRequestProperty("User-Agent", this.mUserAgent);
        if (str1 != null) {
          if (!this.mAuthTokenType.startsWith("oauth2:")) {
            break label190;
          }
        }
        label190:
        for (String str3 = "Bearer ";; str3 = "GoogleLogin auth=")
        {
          localHttpURLConnection.setRequestProperty("Authorization", str3 + str1);
          localHttpURLConnection.connect();
          localObject1 = localHttpURLConnection;
          break;
        }
        try
        {
          GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localObject1.getOutputStream());
          localGZIPOutputStream.write(MessageNano.toByteArray(paramLogRequest));
          localGZIPOutputStream.flush();
          localGZIPOutputStream.close();
          boolean bool = handleResponse(localObject1, str1);
          return bool;
        }
        catch (IOException localIOException2)
        {
          Log.e("PlayEventLogger", "Upload failed " + localIOException2.getClass() + "(" + localIOException2.getMessage() + ")");
          return false;
        }
        finally
        {
          localObject1.disconnect();
        }
      }
    }
    catch (IOException localIOException1)
    {
      return false;
    }
    while (localObject1 != null) {}
    return true;
  }
  
  public final void onNewOutputFile()
  {
    this.mLastSentExperiments = null;
  }
  
  public static final class Configuration
  {
    public long delayBetweenUploadsMs = 300000L;
    public String logDirectoryName = "logs";
    public int mServerTimeoutMs = 10000;
    public String mServerUrl = "https://play.googleapis.com/play/log";
    public long maxStorageSize = 2097152L;
    public long minDelayBetweenUploadsMs = 60000L;
    public long recommendedLogFileSize = 51200L;
    public int recommendedTempFilesCountThreshold = 3000;
  }
  
  public static enum LogSource
  {
    final int mProtoValue;
    
    static
    {
      BOOKS = new LogSource("BOOKS", 2, 2);
      VIDEO = new LogSource("VIDEO", 3, 3);
      MOVIES = new LogSource("MOVIES", 4, 74);
      MAGAZINES = new LogSource("MAGAZINES", 5, 4);
      GAMES = new LogSource("GAMES", 6, 5);
      LB_A = new LogSource("LB_A", 7, 6);
      ANDROID_IDE = new LogSource("ANDROID_IDE", 8, 7);
      LB_P = new LogSource("LB_P", 9, 8);
      LB_S = new LogSource("LB_S", 10, 9);
      GMS_CORE = new LogSource("GMS_CORE", 11, 10);
      CW = new LogSource("CW", 12, 27);
      UDR = new LogSource("UDR", 13, 30);
      NEWSSTAND = new LogSource("NEWSSTAND", 14, 63);
      LogSource[] arrayOfLogSource = new LogSource[15];
      arrayOfLogSource[0] = MARKET;
      arrayOfLogSource[1] = MUSIC;
      arrayOfLogSource[2] = BOOKS;
      arrayOfLogSource[3] = VIDEO;
      arrayOfLogSource[4] = MOVIES;
      arrayOfLogSource[5] = MAGAZINES;
      arrayOfLogSource[6] = GAMES;
      arrayOfLogSource[7] = LB_A;
      arrayOfLogSource[8] = ANDROID_IDE;
      arrayOfLogSource[9] = LB_P;
      arrayOfLogSource[10] = LB_S;
      arrayOfLogSource[11] = GMS_CORE;
      arrayOfLogSource[12] = CW;
      arrayOfLogSource[13] = UDR;
      arrayOfLogSource[14] = NEWSSTAND;
      $VALUES = arrayOfLogSource;
    }
    
    private LogSource(int paramInt)
    {
      this.mProtoValue = paramInt;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.analytics.EventLogger
 * JD-Core Version:    0.7.0.1
 */