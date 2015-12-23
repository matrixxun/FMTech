package com.google.android.finsky.analytics;

import android.accounts.Account;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.utils.ArrayUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.play.analytics.ClientAnalytics.ActiveExperiments;
import com.google.android.play.analytics.ClientAnalytics.LogEvent;
import com.google.android.play.analytics.EventLogger;
import com.google.android.play.analytics.EventLogger.Configuration;
import com.google.android.play.analytics.EventLogger.LogSource;
import com.google.android.play.analytics.ProtoCache;
import com.google.android.play.analytics.ProtoCache.ElementCache;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.Assertions;
import com.google.android.play.utils.NetworkInfoUtil;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.DisplayMessageError;
import com.google.protobuf.nano.MessageNano;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public final class FinskyEventLog
{
  private static boolean sInitializedImpressionId = false;
  private static LogListener sLogTestListener;
  private static Boolean sLogcatEnabled;
  private static long sNextImpressionId;
  private final EventLogger mEventLogger;
  private final FinskyExperiments mExperiments;
  private final boolean mIsDfeRequestLogEnabled;
  public final EventProtoCache mProtoCache;
  private final boolean mShouldLogCacheHitsInRequestLog;
  
  public FinskyEventLog(Context paramContext, Account paramAccount, FinskyExperiments paramFinskyExperiments)
  {
    this.mExperiments = paramFinskyExperiments;
    boolean bool2;
    if ((paramFinskyExperiments != null) && (paramFinskyExperiments.isEnabled(12602374L)))
    {
      bool2 = bool1;
      this.mIsDfeRequestLogEnabled = bool2;
      if ((paramFinskyExperiments == null) || (!paramFinskyExperiments.isEnabled(12603408L))) {
        break label259;
      }
    }
    for (;;)
    {
      this.mShouldLogCacheHitsInRequestLog = bool1;
      EventLogger localEventLogger = null;
      if (paramAccount != null)
      {
        boolean bool3 = ((Boolean)G.enablePlayLogs.get()).booleanValue();
        localEventLogger = null;
        if (bool3)
        {
          EventLogger.Configuration localConfiguration = new EventLogger.Configuration();
          localConfiguration.maxStorageSize = ((Long)G.playLogMaxStorageSize.get()).longValue();
          localConfiguration.recommendedLogFileSize = ((Long)G.playLogRecommendedFileSize.get()).longValue();
          localConfiguration.delayBetweenUploadsMs = ((Long)G.playLogDelayBetweenUploadsMs.get()).longValue();
          localConfiguration.minDelayBetweenUploadsMs = ((Long)G.playLogMinDelayBetweenUploadsMs.get()).longValue();
          localConfiguration.mServerUrl = ((String)G.playLogServerUrl.get());
          String str1 = ((TelephonyManager)paramContext.getSystemService("phone")).getSimOperator();
          String str2 = DfeApiContext.makeShortUserAgentString(paramContext);
          localEventLogger = new EventLogger(paramContext, "androidmarket", EventLogger.LogSource.MARKET, str2, ((Long)DfeApiConfig.androidId.get()).longValue(), Integer.toString(FinskyApp.get().getVersionCode()), str1, localConfiguration, paramAccount);
        }
      }
      this.mEventLogger = localEventLogger;
      this.mProtoCache = EventProtoCache.getInstance();
      return;
      bool2 = false;
      break;
      label259:
      bool1 = false;
    }
  }
  
  private static void addClickPath(PlayStoreUiElementNode paramPlayStoreUiElementNode, PlayStore.PlayStoreClickEvent paramPlayStoreClickEvent)
  {
    for (;;)
    {
      PlayStore.PlayStoreUiElement localPlayStoreUiElement1;
      if (paramPlayStoreUiElementNode != null)
      {
        localPlayStoreUiElement1 = paramPlayStoreUiElementNode.getPlayStoreUiElement();
        if (localPlayStoreUiElement1 == null) {
          FinskyLog.w("Unexpected null PlayStoreUiElement from node %s", new Object[] { paramPlayStoreUiElementNode });
        }
      }
      else
      {
        return;
      }
      PlayStore.PlayStoreUiElement localPlayStoreUiElement2 = cloneElement(localPlayStoreUiElement1);
      paramPlayStoreClickEvent.elementPath = ((PlayStore.PlayStoreUiElement[])safeAddElementToArray(paramPlayStoreClickEvent.elementPath, localPlayStoreUiElement2));
      paramPlayStoreUiElementNode = paramPlayStoreUiElementNode.getParentNode();
    }
  }
  
  public static void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode1, PlayStoreUiElementNode paramPlayStoreUiElementNode2)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = paramPlayStoreUiElementNode2.getPlayStoreUiElement();
    if (localPlayStoreUiElement == null) {
      throw new IllegalArgumentException("childNode has null element");
    }
    if ((findOrAddChild(paramPlayStoreUiElementNode1, localPlayStoreUiElement)) && (localPlayStoreUiElement.child.length == 0))
    {
      if (shouldSendEventToLogcat())
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(localPlayStoreUiElement.type);
        FinskyLog.d("Skip reporting existing leaf node type=%d", arrayOfObject);
      }
      return;
    }
    paramPlayStoreUiElementNode1.getParentNode().childImpression(paramPlayStoreUiElementNode1);
  }
  
  public static PlayStore.PlayStoreUiElement cloneElement(PlayStore.PlayStoreUiElement paramPlayStoreUiElement)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = obtainPlayStoreUiElement(0);
    cloneElement(paramPlayStoreUiElement, localPlayStoreUiElement);
    return localPlayStoreUiElement;
  }
  
  private static void cloneElement(PlayStore.PlayStoreUiElement paramPlayStoreUiElement1, PlayStore.PlayStoreUiElement paramPlayStoreUiElement2)
  {
    paramPlayStoreUiElement2.type = paramPlayStoreUiElement1.type;
    paramPlayStoreUiElement2.hasType = paramPlayStoreUiElement1.hasType;
    paramPlayStoreUiElement2.clientLogsCookie = paramPlayStoreUiElement1.clientLogsCookie;
    paramPlayStoreUiElement2.serverLogsCookie = paramPlayStoreUiElement1.serverLogsCookie;
    if (paramPlayStoreUiElement2.serverLogsCookie.length != 0) {}
    for (boolean bool = true;; bool = false)
    {
      paramPlayStoreUiElement2.hasServerLogsCookie = bool;
      return;
    }
  }
  
  private static void debugAppendServerLogsCookie(StringBuilder paramStringBuilder, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      int i = paramArrayOfByte.length;
      paramStringBuilder.append(" s.cookie[").append(i).append("]={ ");
      paramStringBuilder.append(Base64.encodeToString(paramArrayOfByte, 10));
      paramStringBuilder.append(" }");
      return;
    }
    paramStringBuilder.append(" (no s-cookie)");
  }
  
  private static void deepCloneAndWipeChildren(PlayStore.PlayStoreUiElement paramPlayStoreUiElement1, PlayStore.PlayStoreUiElement paramPlayStoreUiElement2)
  {
    cloneElement(paramPlayStoreUiElement1, paramPlayStoreUiElement2);
    for (PlayStore.PlayStoreUiElement localPlayStoreUiElement1 : paramPlayStoreUiElement1.child)
    {
      PlayStore.PlayStoreUiElement localPlayStoreUiElement2 = obtainPlayStoreUiElement(0);
      deepCloneAndWipeChildren(localPlayStoreUiElement1, localPlayStoreUiElement2);
      paramPlayStoreUiElement2.child = ((PlayStore.PlayStoreUiElement[])safeAddElementToArray(paramPlayStoreUiElement2.child, localPlayStoreUiElement2));
    }
    paramPlayStoreUiElement1.child = PlayStore.PlayStoreUiElement.emptyArray();
  }
  
  public static void dumpDeepLinkEvent(PlayStore.PlayStoreDeepLinkEvent paramPlayStoreDeepLinkEvent)
  {
    if (!shouldSendEventToLogcat()) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder("Sending deeplink event");
    localStringBuilder.append(" type=").append(paramPlayStoreDeepLinkEvent.resolvedType);
    localStringBuilder.append(" package_name=").append(paramPlayStoreDeepLinkEvent.packageName);
    localStringBuilder.append(" external_referrer=").append(paramPlayStoreDeepLinkEvent.externalReferrer);
    localStringBuilder.append(" external_url=").append(paramPlayStoreDeepLinkEvent.externalUrl);
    debugAppendServerLogsCookie(localStringBuilder, paramPlayStoreDeepLinkEvent.serverLogsCookie);
    FinskyLog.d("%s", new Object[] { localStringBuilder });
  }
  
  private static void dumpElement(PlayStore.PlayStoreUiElement paramPlayStoreUiElement, String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    StringBuilder localStringBuilder = new StringBuilder(paramString);
    localStringBuilder.append("type=").append(paramPlayStoreUiElement.type);
    debugAppendServerLogsCookie(localStringBuilder, paramPlayStoreUiElement.serverLogsCookie);
    FinskyLog.d("%s", new Object[] { localStringBuilder });
  }
  
  public static void dumpSearchEvent(PlayStore.PlayStoreSearchEvent paramPlayStoreSearchEvent)
  {
    if (!shouldSendEventToLogcat()) {
      return;
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramPlayStoreSearchEvent.query;
    arrayOfObject[1] = paramPlayStoreSearchEvent.queryUrl;
    FinskyLog.d("Sending search event query=%s queryUrl=%s", arrayOfObject);
  }
  
  private static void dumpTree(String paramString1, long paramLong, PlayStore.PlayStoreUiElement paramPlayStoreUiElement, String paramString2)
  {
    if (!shouldSendEventToLogcat()) {}
    for (;;)
    {
      return;
      if (paramString1 != null)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString1;
        arrayOfObject[1] = Long.valueOf(paramLong);
        FinskyLog.d("%s impression tree, id=%x", arrayOfObject);
      }
      if (paramString2 == null) {
        paramString2 = "";
      }
      dumpElement(paramPlayStoreUiElement, paramString2);
      String str = paramString2 + "  ";
      PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement = paramPlayStoreUiElement.child;
      int i = arrayOfPlayStoreUiElement.length;
      for (int j = 0; j < i; j++) {
        dumpTree(null, 0L, arrayOfPlayStoreUiElement[j], str);
      }
    }
  }
  
  public static boolean findOrAddChild(PlayStoreUiElementNode paramPlayStoreUiElementNode, PlayStore.PlayStoreUiElement paramPlayStoreUiElement)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = paramPlayStoreUiElementNode.getPlayStoreUiElement();
    PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement = localPlayStoreUiElement.child;
    int i = arrayOfPlayStoreUiElement.length;
    for (int j = 0;; j++)
    {
      boolean bool = false;
      if (j < i)
      {
        if (isEqual(paramPlayStoreUiElement, arrayOfPlayStoreUiElement[j])) {
          bool = true;
        }
      }
      else
      {
        if (!bool) {
          localPlayStoreUiElement.child = ((PlayStore.PlayStoreUiElement[])safeAddElementToArray(localPlayStoreUiElement.child, paramPlayStoreUiElement));
        }
        return bool;
      }
    }
  }
  
  public static void flushImpression(Handler paramHandler, long paramLong, RootUiElementNode paramRootUiElementNode)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = paramRootUiElementNode.getPlayStoreUiElement();
    if (shouldSendEventToLogcat()) {
      quickDebugLog("Flushing", localPlayStoreUiElement);
    }
    if (shouldSendEventToLogcat()) {
      dumpTree("Flushing", 0L, localPlayStoreUiElement, null);
    }
    paramHandler.removeCallbacksAndMessages(null);
    sendImpression(paramLong, paramRootUiElementNode, true);
  }
  
  public static void flushImpressionAtRoot(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    while (paramPlayStoreUiElementNode != null)
    {
      if ((paramPlayStoreUiElementNode instanceof RootUiElementNode))
      {
        ((RootUiElementNode)paramPlayStoreUiElementNode).flushImpression();
        return;
      }
      paramPlayStoreUiElementNode = paramPlayStoreUiElementNode.getParentNode();
    }
    FinskyLog.wtf("No RootUiElementNode found in parent chain", new Object[0]);
  }
  
  private static int getConnectionType(NetworkInfo paramNetworkInfo)
  {
    if ((paramNetworkInfo == null) || (!paramNetworkInfo.isConnected())) {
      return 1;
    }
    switch (NetworkInfoUtil.getNetworkTypeFromNetworkInfo(paramNetworkInfo))
    {
    default: 
      return 0;
    case 1: 
      return 4;
    case 2: 
      return 5;
    case 3: 
      return 6;
    case 4: 
      return 3;
    case 6: 
      return 2;
    case 5: 
      return 7;
    }
    return 8;
  }
  
  public static long getNextImpressionId()
  {
    if (!sInitializedImpressionId)
    {
      sNextImpressionId = System.currentTimeMillis() ^ System.nanoTime();
      sInitializedImpressionId = true;
    }
    long l = 1L + sNextImpressionId;
    sNextImpressionId = l;
    if (l == 0L) {
      sNextImpressionId = 1L + sNextImpressionId;
    }
    return sNextImpressionId;
  }
  
  private static boolean isEqual(PlayStore.PlayStoreUiElement paramPlayStoreUiElement1, PlayStore.PlayStoreUiElement paramPlayStoreUiElement2)
  {
    if (paramPlayStoreUiElement1 == paramPlayStoreUiElement2) {}
    do
    {
      return true;
      if ((paramPlayStoreUiElement1 == null) || (paramPlayStoreUiElement2 == null)) {
        return false;
      }
      if (paramPlayStoreUiElement1.type != paramPlayStoreUiElement2.type) {
        return false;
      }
    } while (Arrays.equals(paramPlayStoreUiElement1.serverLogsCookie, paramPlayStoreUiElement2.serverLogsCookie));
    return false;
  }
  
  private static int measureTree(PlayStore.PlayStoreUiElement paramPlayStoreUiElement, int[] paramArrayOfInt)
  {
    int i = 0;
    for (PlayStore.PlayStoreUiElement localPlayStoreUiElement : paramPlayStoreUiElement.child)
    {
      paramArrayOfInt[0] = (1 + paramArrayOfInt[0]);
      int m = 1 + measureTree(localPlayStoreUiElement, paramArrayOfInt);
      if (m > i) {
        i = m;
      }
    }
    return i;
  }
  
  public static PlayStore.PlayStoreBackgroundActionEvent obtainPlayStoreBackgroundActionEvent()
  {
    return (PlayStore.PlayStoreBackgroundActionEvent)EventProtoCache.getInstance().mCachePlayStoreBackgroundAction.obtain();
  }
  
  public static PlayStore.PlayStoreClickEvent obtainPlayStoreClickEvent()
  {
    return EventProtoCache.getInstance().obtainPlayStoreClickEvent();
  }
  
  public static PlayStore.PlayStoreImpressionEvent obtainPlayStoreImpressionEvent()
  {
    return EventProtoCache.getInstance().obtainPlayStoreImpressionEvent();
  }
  
  public static PlayStore.PlayStoreSearchEvent obtainPlayStoreSearchEvent()
  {
    return (PlayStore.PlayStoreSearchEvent)EventProtoCache.getInstance().mCachePlayStoreSearch.obtain();
  }
  
  public static PlayStore.SearchSuggestionReport obtainPlayStoreSearchSuggestionReport()
  {
    return (PlayStore.SearchSuggestionReport)EventProtoCache.getInstance().mCachePlayStoreSearchSuggestionReport.obtain();
  }
  
  public static PlayStore.PlayStoreUiElement obtainPlayStoreUiElement(int paramInt)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = EventProtoCache.getInstance().obtainPlayStoreUiElement();
    localPlayStoreUiElement.type = paramInt;
    localPlayStoreUiElement.hasType = true;
    return localPlayStoreUiElement;
  }
  
  public static PlayStore.PlayStoreUiElement pathToTree(List<PlayStore.PlayStoreUiElement> paramList)
  {
    Object localObject = null;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      PlayStore.PlayStoreUiElement localPlayStoreUiElement = cloneElement((PlayStore.PlayStoreUiElement)localIterator.next());
      if (localObject != null) {
        localPlayStoreUiElement.child = ((PlayStore.PlayStoreUiElement[])safeAddElementToArray(localPlayStoreUiElement.child, localObject));
      }
      localObject = localPlayStoreUiElement;
    }
    return localObject;
  }
  
  private static void quickDebugLog(String paramString, PlayStore.PlayStoreUiElement paramPlayStoreUiElement)
  {
    if (!shouldSendEventToLogcat()) {
      return;
    }
    int i = paramPlayStoreUiElement.type;
    int j = paramPlayStoreUiElement.child.length;
    int[] arrayOfInt = { 0 };
    int k = measureTree(paramPlayStoreUiElement, arrayOfInt);
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Integer.valueOf(i);
    arrayOfObject[2] = Integer.valueOf(j);
    arrayOfObject[3] = Integer.valueOf(arrayOfInt[0]);
    arrayOfObject[4] = Integer.valueOf(k);
    FinskyLog.d("%s impression at root. Type=%d top children=%d total children=%d depth=%d", arrayOfObject);
  }
  
  public static boolean removeChild(PlayStore.PlayStoreUiElement paramPlayStoreUiElement1, PlayStore.PlayStoreUiElement paramPlayStoreUiElement2)
  {
    PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement = paramPlayStoreUiElement1.child;
    for (int i = 0; i < arrayOfPlayStoreUiElement.length; i++) {
      if (isEqual(arrayOfPlayStoreUiElement[i], paramPlayStoreUiElement2))
      {
        paramPlayStoreUiElement1.child = ((PlayStore.PlayStoreUiElement[])ArrayUtils.remove(arrayOfPlayStoreUiElement, i));
        return true;
      }
    }
    return false;
  }
  
  private static void requestImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (shouldSendEventToLogcat())
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramPlayStoreUiElementNode.getClass().getSimpleName();
      FinskyLog.w("TRAVERSE: Found %s", arrayOfObject);
    }
    PlayStoreUiElementNode localPlayStoreUiElementNode = paramPlayStoreUiElementNode.getParentNode();
    if (localPlayStoreUiElementNode != null) {
      localPlayStoreUiElementNode.childImpression(paramPlayStoreUiElementNode);
    }
  }
  
  public static void requestImpressions(ViewGroup paramViewGroup)
  {
    if (paramViewGroup != null) {
      requestImpressionsImpl(paramViewGroup);
    }
  }
  
  private static void requestImpressionsImpl(ViewGroup paramViewGroup)
  {
    int i = paramViewGroup.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = paramViewGroup.getChildAt(j);
      if ((localView instanceof ViewGroup)) {
        requestImpressionsImpl((ViewGroup)localView);
      }
    }
    if ((paramViewGroup instanceof PlayStoreUiElementNode)) {
      requestImpression((PlayStoreUiElementNode)paramViewGroup);
    }
    do
    {
      PlayStoreUiElementNode localPlayStoreUiElementNode;
      do
      {
        return;
        if (!(paramViewGroup instanceof PlayCardViewBase)) {
          break;
        }
        localPlayStoreUiElementNode = (PlayStoreUiElementNode)((PlayCardViewBase)paramViewGroup).getLoggingData();
      } while (localPlayStoreUiElementNode == null);
      requestImpression(localPlayStoreUiElementNode);
      return;
    } while (!(paramViewGroup.getTag() instanceof PlayStoreUiElementNode));
    requestImpression((PlayStoreUiElementNode)paramViewGroup.getTag());
  }
  
  public static void rootImpression(Handler paramHandler, long paramLong, PlayStoreUiElementNode paramPlayStoreUiElementNode1, PlayStoreUiElementNode paramPlayStoreUiElementNode2)
  {
    if ((paramPlayStoreUiElementNode2 == null) || (paramPlayStoreUiElementNode2.getPlayStoreUiElement() == null)) {
      throw new IllegalArgumentException("null child node or element");
    }
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = paramPlayStoreUiElementNode1.getPlayStoreUiElement();
    if (paramPlayStoreUiElementNode2 != null) {
      findOrAddChild(paramPlayStoreUiElementNode1, paramPlayStoreUiElementNode2.getPlayStoreUiElement());
    }
    if (shouldSendEventToLogcat()) {
      quickDebugLog("Collecting", localPlayStoreUiElement);
    }
    if (shouldSendEventToLogcat()) {
      dumpTree("Collecting", 0L, localPlayStoreUiElement, null);
    }
    paramHandler.removeCallbacksAndMessages(null);
    paramHandler.postDelayed(new Runnable()
    {
      public final void run()
      {
        FinskyEventLog.access$000$1151a866(this.val$impressionId, this.val$rootNode);
      }
    }, ((Long)G.playLogImpressionSettleTimeMs.get()).longValue());
  }
  
  private static <T> T[] safeAddElementToArray(T[] paramArrayOfT, T paramT)
  {
    if (paramT == null)
    {
      FinskyLog.wtf("Adding null to element array.", new Object[0]);
      return paramArrayOfT;
    }
    Object[] arrayOfObject = (Object[])Array.newInstance(paramT.getClass(), 1 + paramArrayOfT.length);
    System.arraycopy(paramArrayOfT, 0, arrayOfObject, 0, paramArrayOfT.length);
    arrayOfObject[(-1 + arrayOfObject.length)] = paramT;
    return arrayOfObject;
  }
  
  private static void sendImpression(long paramLong, PlayStoreUiElementNode paramPlayStoreUiElementNode, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramPlayStoreUiElementNode.getPlayStoreUiElement().child.length == 0)) {
      return;
    }
    PlayStore.PlayStoreImpressionEvent localPlayStoreImpressionEvent = EventProtoCache.getInstance().obtainPlayStoreImpressionEvent();
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = obtainPlayStoreUiElement(0);
    deepCloneAndWipeChildren(paramPlayStoreUiElementNode.getPlayStoreUiElement(), localPlayStoreUiElement);
    localPlayStoreImpressionEvent.tree = localPlayStoreUiElement;
    if (paramLong != 0L)
    {
      localPlayStoreImpressionEvent.id = paramLong;
      localPlayStoreImpressionEvent.hasId = true;
    }
    FinskyApp.get().getEventLogger().logImpressionEvent(localPlayStoreImpressionEvent);
  }
  
  public static PlayStore.PlayStoreUiElement setServerLogCookie(PlayStore.PlayStoreUiElement paramPlayStoreUiElement, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      paramPlayStoreUiElement.serverLogsCookie = paramArrayOfByte;
      paramPlayStoreUiElement.hasServerLogsCookie = true;
    }
    return paramPlayStoreUiElement;
  }
  
  public static boolean shouldSendEventToLogcat()
  {
    if (sLogcatEnabled == null) {
      sLogcatEnabled = (Boolean)FinskyPreferences.internalEventlogDebuggingEnabled.get();
    }
    return sLogcatEnabled.booleanValue();
  }
  
  public static void startNewImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    for (PlayStoreUiElementNode localPlayStoreUiElementNode = paramPlayStoreUiElementNode.getParentNode(); localPlayStoreUiElementNode != null; localPlayStoreUiElementNode = localPlayStoreUiElementNode.getParentNode()) {
      paramPlayStoreUiElementNode = localPlayStoreUiElementNode;
    }
    if ((paramPlayStoreUiElementNode instanceof RootUiElementNode)) {
      ((RootUiElementNode)paramPlayStoreUiElementNode).startNewImpression();
    }
  }
  
  private static void writeLogToListener(String paramString, byte[] paramArrayOfByte)
  {
    try
    {
      if (sLogTestListener != null)
      {
        ClientAnalytics.LogEvent localLogEvent = new ClientAnalytics.LogEvent();
        localLogEvent.eventTimeMs = System.currentTimeMillis();
        localLogEvent.tag = paramString;
        if (paramArrayOfByte != null) {
          localLogEvent.sourceExtension = paramArrayOfByte;
        }
        MessageNano.toByteArray(localLogEvent);
      }
      return;
    }
    finally {}
  }
  
  public final void flush(Runnable paramRunnable)
  {
    if (this.mEventLogger != null) {
      this.mEventLogger.mHandler.obtainMessage(4, paramRunnable).sendToTarget();
    }
  }
  
  public final void logAutoUpdateData(String paramString1, PlayStore.AutoUpdateData paramAutoUpdateData, String paramString2, PlayStore.AppData paramAppData)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(131).setReason(paramString2).setDocument(paramString1);
    localBackgroundEventBuilder.event.autoUpdateData = paramAutoUpdateData;
    sendBackgroundEventToSinks(localBackgroundEventBuilder.setAppData(paramAppData).event);
  }
  
  public final void logBackgroundEvent(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, PlayStore.AppData paramAppData)
  {
    sendBackgroundEventToSinks(new BackgroundEventBuilder(paramInt1).setDocument(paramString1).setReason(paramString2).setErrorCode(paramInt2).setExceptionType(paramString3).setAppData(paramAppData).event);
  }
  
  public final void logBackgroundEvent(int paramInt, byte[] paramArrayOfByte)
  {
    sendBackgroundEventToSinks(new BackgroundEventBuilder(paramInt).setServerLogsCookie(paramArrayOfByte).event);
  }
  
  public final void logBackgroundEventAndFlush(PlayStore.PlayStoreBackgroundActionEvent paramPlayStoreBackgroundActionEvent)
  {
    sendBackgroundEventToSinks(paramPlayStoreBackgroundActionEvent);
    if (this.mExperiments.isEnabled(12604155L)) {
      flush(null);
    }
  }
  
  public final void logClickEvent(int paramInt, byte[] paramArrayOfByte, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    PlayStore.PlayStoreClickEvent localPlayStoreClickEvent = this.mProtoCache.obtainPlayStoreClickEvent();
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.mProtoCache.obtainPlayStoreUiElement();
    localPlayStoreUiElement.type = paramInt;
    localPlayStoreUiElement.hasType = true;
    if (paramArrayOfByte != null)
    {
      localPlayStoreUiElement.serverLogsCookie = paramArrayOfByte;
      localPlayStoreUiElement.hasServerLogsCookie = true;
    }
    localPlayStoreClickEvent.elementPath = ((PlayStore.PlayStoreUiElement[])safeAddElementToArray(localPlayStoreClickEvent.elementPath, localPlayStoreUiElement));
    if (paramPlayStoreUiElementNode != null) {
      addClickPath(paramPlayStoreUiElementNode, localPlayStoreClickEvent);
    }
    logClickEvent(localPlayStoreClickEvent);
  }
  
  public final void logClickEvent(PlayStore.PlayStoreClickEvent paramPlayStoreClickEvent)
  {
    int i = 0;
    if ((shouldSendEventToLogcat()) && (shouldSendEventToLogcat()))
    {
      FinskyLog.d("Sending click event:", new Object[0]);
      String str = "";
      PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement = paramPlayStoreClickEvent.elementPath;
      int j = arrayOfPlayStoreUiElement.length;
      while (i < j)
      {
        dumpElement(arrayOfPlayStoreUiElement[i], str);
        str = str + "  ";
        i++;
      }
    }
    PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = this.mProtoCache.obtainPlayStoreLogEvent();
    localPlayStoreLogEvent.click = paramPlayStoreClickEvent;
    serializeAndWrite("3", localPlayStoreLogEvent);
  }
  
  public final void logClickEvent(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (paramPlayStoreUiElementNode == null) {
      return;
    }
    PlayStore.PlayStoreClickEvent localPlayStoreClickEvent = this.mProtoCache.obtainPlayStoreClickEvent();
    addClickPath(paramPlayStoreUiElementNode, localPlayStoreClickEvent);
    logClickEvent(localPlayStoreClickEvent);
  }
  
  public final void logClickEventWithClientCookie(int paramInt, PlayStore.PlayStoreUiElementInfo paramPlayStoreUiElementInfo, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    PlayStore.PlayStoreClickEvent localPlayStoreClickEvent = this.mProtoCache.obtainPlayStoreClickEvent();
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.mProtoCache.obtainPlayStoreUiElement();
    localPlayStoreUiElement.type = paramInt;
    localPlayStoreUiElement.hasType = true;
    if (paramPlayStoreUiElementInfo != null) {
      localPlayStoreUiElement.clientLogsCookie = paramPlayStoreUiElementInfo;
    }
    localPlayStoreClickEvent.elementPath = ((PlayStore.PlayStoreUiElement[])safeAddElementToArray(localPlayStoreClickEvent.elementPath, localPlayStoreUiElement));
    if (paramPlayStoreUiElementNode != null) {
      addClickPath(paramPlayStoreUiElementNode, localPlayStoreClickEvent);
    }
    logClickEvent(localPlayStoreClickEvent);
  }
  
  public final void logDeepLinkEventAndFlush(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, byte[] paramArrayOfByte)
  {
    PlayStore.PlayStoreDeepLinkEvent localPlayStoreDeepLinkEvent = new PlayStore.PlayStoreDeepLinkEvent();
    localPlayStoreDeepLinkEvent.resolvedType = paramInt;
    localPlayStoreDeepLinkEvent.hasResolvedType = true;
    if (!TextUtils.isEmpty(paramString1))
    {
      localPlayStoreDeepLinkEvent.externalUrl = paramString1;
      localPlayStoreDeepLinkEvent.hasExternalUrl = true;
    }
    if (!TextUtils.isEmpty(paramString2))
    {
      localPlayStoreDeepLinkEvent.packageName = paramString2;
      localPlayStoreDeepLinkEvent.hasPackageName = true;
    }
    if (!TextUtils.isEmpty(paramString3))
    {
      localPlayStoreDeepLinkEvent.externalReferrer = paramString3;
      localPlayStoreDeepLinkEvent.hasExternalReferrer = true;
    }
    if (!TextUtils.isEmpty(paramString4))
    {
      localPlayStoreDeepLinkEvent.referrerPackage = paramString4;
      localPlayStoreDeepLinkEvent.hasReferrerPackage = true;
    }
    if (paramArrayOfByte != null)
    {
      localPlayStoreDeepLinkEvent.serverLogsCookie = paramArrayOfByte;
      localPlayStoreDeepLinkEvent.hasServerLogsCookie = true;
    }
    if (shouldSendEventToLogcat()) {
      dumpDeepLinkEvent(localPlayStoreDeepLinkEvent);
    }
    PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = this.mProtoCache.obtainPlayStoreLogEvent();
    localPlayStoreLogEvent.deepLink = localPlayStoreDeepLinkEvent;
    serializeAndWrite("6", localPlayStoreLogEvent);
    if (this.mExperiments.isEnabled(12604155L)) {
      flush(null);
    }
  }
  
  public final void logImpressionEvent(PlayStore.PlayStoreImpressionEvent paramPlayStoreImpressionEvent)
  {
    if (shouldSendEventToLogcat()) {
      quickDebugLog("Sending", paramPlayStoreImpressionEvent.tree);
    }
    if (shouldSendEventToLogcat()) {
      dumpTree("Sending", paramPlayStoreImpressionEvent.id, paramPlayStoreImpressionEvent.tree, null);
    }
    PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = this.mProtoCache.obtainPlayStoreLogEvent();
    localPlayStoreLogEvent.impression = paramPlayStoreImpressionEvent;
    serializeAndWrite("1", localPlayStoreLogEvent);
  }
  
  public final void logOperationSuccessBackgroundEvent(int paramInt, boolean paramBoolean)
  {
    sendBackgroundEventToSinks(new BackgroundEventBuilder(paramInt).setOperationSuccess(paramBoolean).event);
  }
  
  public final void logPathImpression(long paramLong, int paramInt, byte[] paramArrayOfByte, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    PlayStore.PlayStoreImpressionEvent localPlayStoreImpressionEvent = this.mProtoCache.obtainPlayStoreImpressionEvent();
    if (0L != 0L)
    {
      localPlayStoreImpressionEvent.id = 0L;
      localPlayStoreImpressionEvent.hasId = true;
    }
    ArrayList localArrayList = new ArrayList();
    PlayStore.PlayStoreUiElement localPlayStoreUiElement1 = this.mProtoCache.obtainPlayStoreUiElement();
    localPlayStoreUiElement1.type = paramInt;
    localPlayStoreUiElement1.hasType = true;
    setServerLogCookie(localPlayStoreUiElement1, paramArrayOfByte);
    localArrayList.add(localPlayStoreUiElement1);
    while (paramPlayStoreUiElementNode != null)
    {
      localArrayList.add(paramPlayStoreUiElementNode.getPlayStoreUiElement());
      paramPlayStoreUiElementNode = paramPlayStoreUiElementNode.getParentNode();
    }
    PlayStore.PlayStoreUiElement localPlayStoreUiElement2 = pathToTree(localArrayList);
    if (localPlayStoreUiElement2 != null) {
      localPlayStoreImpressionEvent.tree = localPlayStoreUiElement2;
    }
    for (;;)
    {
      logImpressionEvent(localPlayStoreImpressionEvent);
      return;
      FinskyLog.wtf("Encountered empty tree.", new Object[0]);
    }
  }
  
  public final void logPathImpression(long paramLong, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    PlayStore.PlayStoreImpressionEvent localPlayStoreImpressionEvent = this.mProtoCache.obtainPlayStoreImpressionEvent();
    if (paramLong != 0L)
    {
      localPlayStoreImpressionEvent.id = paramLong;
      localPlayStoreImpressionEvent.hasId = true;
    }
    PlayStoreUiElementNode localPlayStoreUiElementNode = paramPlayStoreUiElementNode;
    ArrayList localArrayList = new ArrayList();
    while (localPlayStoreUiElementNode != null)
    {
      localArrayList.add(localPlayStoreUiElementNode.getPlayStoreUiElement());
      localPlayStoreUiElementNode = localPlayStoreUiElementNode.getParentNode();
    }
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = pathToTree(localArrayList);
    if (localPlayStoreUiElement != null) {
      localPlayStoreImpressionEvent.tree = localPlayStoreUiElement;
    }
    for (;;)
    {
      logImpressionEvent(localPlayStoreImpressionEvent);
      return;
      FinskyLog.wtf("Encountered empty tree.", new Object[0]);
    }
  }
  
  public final void logPathImpression$7d139cbf(int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    logPathImpression(0L, paramInt, null, paramPlayStoreUiElementNode);
  }
  
  public final void logPurchaseBackgroundEvent$2c9b4795(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, byte[] paramArrayOfByte)
  {
    sendBackgroundEventToSinks(new BackgroundEventBuilder(paramInt1).setDocument(paramString1).setErrorCode(paramInt3).setExceptionType(paramString2).setServerLogsCookie(paramArrayOfByte).setOfferType(paramInt2).setServerLatencyMs(-1L).setClientLatencyMs(-1L).event);
  }
  
  public final void logRpcReport(String paramString, long paramLong1, long paramLong2, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, VolleyError paramVolleyError, NetworkInfo paramNetworkInfo1, NetworkInfo paramNetworkInfo2, int paramInt3, boolean paramBoolean2)
  {
    if ((!this.mIsDfeRequestLogEnabled) || ((paramBoolean2) && (!this.mShouldLogCacheHitsInRequestLog))) {
      return;
    }
    int i = 0;
    if (paramVolleyError != null)
    {
      if (!(paramVolleyError instanceof TimeoutError)) {
        break label295;
      }
      i = 1;
    }
    for (;;)
    {
      PlayStore.PlayStoreRpcReport localPlayStoreRpcReport = new PlayStore.PlayStoreRpcReport();
      if (!TextUtils.isEmpty(paramString))
      {
        localPlayStoreRpcReport.url = paramString;
        localPlayStoreRpcReport.hasUrl = true;
      }
      if (paramLong1 >= 0L)
      {
        localPlayStoreRpcReport.clientLatencyMs = paramLong1;
        localPlayStoreRpcReport.hasClientLatencyMs = true;
      }
      if (paramLong2 >= 0L)
      {
        localPlayStoreRpcReport.serverLatencyMs = paramLong2;
        localPlayStoreRpcReport.hasServerLatencyMs = true;
      }
      if (paramInt1 >= 0)
      {
        localPlayStoreRpcReport.numAttempts = paramInt1;
        localPlayStoreRpcReport.hasNumAttempts = true;
      }
      if (paramInt2 >= 0)
      {
        localPlayStoreRpcReport.timeoutMs = paramInt2;
        localPlayStoreRpcReport.hasTimeoutMs = true;
      }
      if (paramFloat > 0.0F)
      {
        localPlayStoreRpcReport.backoffMultiplier = paramFloat;
        localPlayStoreRpcReport.hasBackoffMultiplier = true;
      }
      localPlayStoreRpcReport.wasSuccessful = paramBoolean1;
      localPlayStoreRpcReport.hasWasSuccessful = true;
      if (!paramBoolean1)
      {
        localPlayStoreRpcReport.volleyErrorType = i;
        localPlayStoreRpcReport.hasVolleyErrorType = true;
      }
      if (paramNetworkInfo1 != null)
      {
        localPlayStoreRpcReport.startConnectionType = getConnectionType(paramNetworkInfo1);
        localPlayStoreRpcReport.hasStartConnectionType = true;
      }
      if (paramNetworkInfo2 != null)
      {
        localPlayStoreRpcReport.endConnectionType = getConnectionType(paramNetworkInfo2);
        localPlayStoreRpcReport.hasEndConnectionType = true;
      }
      if (paramInt3 >= 0)
      {
        localPlayStoreRpcReport.responseBodySizeBytes = paramInt3;
        localPlayStoreRpcReport.hasResponseBodySizeBytes = true;
      }
      localPlayStoreRpcReport.cacheHit = paramBoolean2;
      localPlayStoreRpcReport.hasCacheHit = true;
      BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(5);
      localBackgroundEventBuilder.event.rpcReport = localPlayStoreRpcReport;
      sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      return;
      label295:
      if ((paramVolleyError instanceof NetworkError))
      {
        if ((paramVolleyError instanceof NoConnectionError)) {
          i = 6;
        } else {
          i = 2;
        }
      }
      else if ((paramVolleyError instanceof ParseError))
      {
        i = 3;
      }
      else if ((paramVolleyError instanceof AuthFailureError))
      {
        i = 4;
      }
      else if ((paramVolleyError instanceof ServerError))
      {
        i = 5;
      }
      else
      {
        boolean bool = paramVolleyError instanceof DisplayMessageError;
        i = 0;
        if (bool) {
          i = 7;
        }
      }
    }
  }
  
  public final void logSettingsBackgroundEvent(int paramInt, Integer paramInteger1, Integer paramInteger2, String paramString)
  {
    sendBackgroundEventToSinks(new BackgroundEventBuilder(paramInt).setToSetting(paramInteger1).setFromSetting(paramInteger2).setReason(paramString).event);
  }
  
  public final void sendBackgroundEventToSinks(PlayStore.PlayStoreBackgroundActionEvent paramPlayStoreBackgroundActionEvent)
  {
    if ((shouldSendEventToLogcat()) && (shouldSendEventToLogcat()))
    {
      StringBuffer localStringBuffer = new StringBuffer("type=").append(paramPlayStoreBackgroundActionEvent.type);
      if (paramPlayStoreBackgroundActionEvent.hasDocument) {
        localStringBuffer.append(", document=").append(paramPlayStoreBackgroundActionEvent.document);
      }
      if (paramPlayStoreBackgroundActionEvent.hasErrorCode) {
        localStringBuffer.append(", error_code=").append(paramPlayStoreBackgroundActionEvent.errorCode);
      }
      if (paramPlayStoreBackgroundActionEvent.hasReason) {
        localStringBuffer.append(", reason=").append(paramPlayStoreBackgroundActionEvent.reason);
      }
      if (paramPlayStoreBackgroundActionEvent.hasExceptionType) {
        localStringBuffer.append(", exception_type=").append(paramPlayStoreBackgroundActionEvent.exceptionType);
      }
      if (paramPlayStoreBackgroundActionEvent.hasOfferType) {
        localStringBuffer.append(", offer_type=").append(paramPlayStoreBackgroundActionEvent.offerType);
      }
      if (paramPlayStoreBackgroundActionEvent.hasServerLatencyMs) {
        localStringBuffer.append(", server_latency_ms=").append(paramPlayStoreBackgroundActionEvent.serverLatencyMs);
      }
      if (paramPlayStoreBackgroundActionEvent.hasClientLatencyMs) {
        localStringBuffer.append(", client_latency_ms=").append(paramPlayStoreBackgroundActionEvent.clientLatencyMs);
      }
      if (paramPlayStoreBackgroundActionEvent.searchSuggestionReport != null)
      {
        PlayStore.SearchSuggestionReport localSearchSuggestionReport = paramPlayStoreBackgroundActionEvent.searchSuggestionReport;
        localStringBuffer.append(", query=").append(localSearchSuggestionReport.query);
        if (localSearchSuggestionReport.hasSuggestedQuery) {
          localStringBuffer.append(", suggested_query=").append(localSearchSuggestionReport.suggestedQuery);
        }
        localStringBuffer.append(", client_latency_ms=").append(localSearchSuggestionReport.clientLatencyMs);
      }
      if (paramPlayStoreBackgroundActionEvent.rpcReport != null)
      {
        PlayStore.PlayStoreRpcReport localPlayStoreRpcReport = paramPlayStoreBackgroundActionEvent.rpcReport;
        if (shouldSendEventToLogcat())
        {
          if (localPlayStoreRpcReport.hasUrl) {
            localStringBuffer.append(", url=").append(localPlayStoreRpcReport.url);
          }
          if (localPlayStoreRpcReport.hasClientLatencyMs) {
            localStringBuffer.append(", client_latency_ms=").append(localPlayStoreRpcReport.clientLatencyMs);
          }
          if (localPlayStoreRpcReport.hasServerLatencyMs) {
            localStringBuffer.append(", server_latency_ms=").append(localPlayStoreRpcReport.serverLatencyMs);
          }
          if (localPlayStoreRpcReport.hasNumAttempts) {
            localStringBuffer.append(", num_attempts=").append(localPlayStoreRpcReport.numAttempts);
          }
          if (localPlayStoreRpcReport.hasTimeoutMs) {
            localStringBuffer.append(", timeout_ms=").append(localPlayStoreRpcReport.timeoutMs);
          }
          if (localPlayStoreRpcReport.hasBackoffMultiplier) {
            localStringBuffer.append(", backoff_multiplier=").append(localPlayStoreRpcReport.backoffMultiplier);
          }
          if (localPlayStoreRpcReport.hasWasSuccessful) {
            localStringBuffer.append(", was_successful=").append(localPlayStoreRpcReport.wasSuccessful);
          }
          if (localPlayStoreRpcReport.hasStartConnectionType) {
            localStringBuffer.append(", cur_connection_type=").append(localPlayStoreRpcReport.startConnectionType);
          }
          if (localPlayStoreRpcReport.hasEndConnectionType) {
            localStringBuffer.append(", end_connection_type=").append(localPlayStoreRpcReport.endConnectionType);
          }
          if (localPlayStoreRpcReport.hasResponseBodySizeBytes) {
            localStringBuffer.append(", response_body_size_bytes=").append(localPlayStoreRpcReport.responseBodySizeBytes);
          }
          if (localPlayStoreRpcReport.hasVolleyErrorType) {
            localStringBuffer.append(", volley_error_type=").append(localPlayStoreRpcReport.volleyErrorType);
          }
          FinskyLog.d("%s", new Object[] { localStringBuffer });
        }
      }
      if (paramPlayStoreBackgroundActionEvent.autoUpdateData != null)
      {
        PlayStore.AutoUpdateData localAutoUpdateData = paramPlayStoreBackgroundActionEvent.autoUpdateData;
        if (shouldSendEventToLogcat())
        {
          if (localAutoUpdateData.hasSkippedDueToProjection) {
            localStringBuffer.append(", skipped_due_to_projection=").append(localAutoUpdateData.skippedDueToProjection);
          }
          if (localAutoUpdateData.hasSkippedDueToPower) {
            localStringBuffer.append(", skipped_due_to_power=").append(localAutoUpdateData.skippedDueToPower);
          }
          if (localAutoUpdateData.hasSkippedDueToWifi) {
            localStringBuffer.append(", skipped_due_to_wifi=").append(localAutoUpdateData.skippedDueToWifi);
          }
          if (localAutoUpdateData.hasRecheckState) {
            localStringBuffer.append(", recheck_state=").append(localAutoUpdateData.recheckState);
          }
          if (localAutoUpdateData.hasSkippedDueToNewPermission) {
            localStringBuffer.append(", skipped_due_to_new_permission=").append(localAutoUpdateData.skippedDueToNewPermission);
          }
          if (localAutoUpdateData.hasSkippedDueToLargeDownload) {
            localStringBuffer.append(", skipped_due_to_large_download=").append(localAutoUpdateData.skippedDueToLargeDownload);
          }
          if (localAutoUpdateData.hasSkippedDueToDisabledByUser) {
            localStringBuffer.append(", skipped_due_to_disabled_by_user=").append(localAutoUpdateData.skippedDueToDisabledByUser);
          }
          if (localAutoUpdateData.hasSkippedDueToGlobalDisabled) {
            localStringBuffer.append(", skipped_due_to_global_disabled=").append(localAutoUpdateData.skippedDueToGlobalDisabled);
          }
          if (localAutoUpdateData.hasSkippedDueToForeground) {
            localStringBuffer.append(", skipped_due_to_foreground=").append(localAutoUpdateData.skippedDueToForeground);
          }
          if (localAutoUpdateData.hasNumPackagesDeferred) {
            localStringBuffer.append(", num_packages_deferred=").append(localAutoUpdateData.numPackagesDeferred);
          }
          if (localAutoUpdateData.hasNumPackagesInstalled) {
            localStringBuffer.append(", num_packages_installed=").append(localAutoUpdateData.numPackagesInstalled);
          }
          if (localAutoUpdateData.hasRescheduled) {
            localStringBuffer.append(", rescheduled=").append(localAutoUpdateData.rescheduled);
          }
        }
      }
      FinskyLog.d("Sending background event %s", new Object[] { localStringBuffer });
      PlayStore.AppData localAppData = paramPlayStoreBackgroundActionEvent.appData;
      if (localAppData != null)
      {
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Integer.valueOf(localAppData.version);
        arrayOfObject[1] = Integer.valueOf(localAppData.oldVersion);
        arrayOfObject[2] = Boolean.valueOf(localAppData.systemApp);
        FinskyLog.d("  app_data {version=%d, old_version=%d, system_app=%b}", arrayOfObject);
      }
    }
    PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = this.mProtoCache.obtainPlayStoreLogEvent();
    localPlayStoreLogEvent.backgroundAction = paramPlayStoreBackgroundActionEvent;
    serializeAndWrite("4", localPlayStoreLogEvent);
  }
  
  public final void serializeAndWrite(String paramString, PlayStore.PlayStoreLogEvent paramPlayStoreLogEvent)
  {
    byte[] arrayOfByte = MessageNano.toByteArray(paramPlayStoreLogEvent);
    writeLogToListener(paramString, arrayOfByte);
    if (this.mEventLogger == null) {
      return;
    }
    EventLogger localEventLogger = this.mEventLogger;
    if (this.mExperiments != null) {}
    EventProtoCache localEventProtoCache;
    PlayStore.PlayStoreImpressionEvent localPlayStoreImpressionEvent;
    for (ClientAnalytics.ActiveExperiments localActiveExperiments = this.mExperiments.getActiveExperiments();; localActiveExperiments = null)
    {
      long l = System.currentTimeMillis();
      Assertions.checkState(true, "Extras must be null or of even length.");
      ClientAnalytics.LogEvent localLogEvent = (ClientAnalytics.LogEvent)localEventLogger.mProtoCache.mCacheLogEvent.obtain();
      localLogEvent.eventTimeMs = l;
      localLogEvent.timezoneOffsetSeconds = (TimeZone.getDefault().getRawOffset() / 1000);
      localLogEvent.tag = paramString;
      localLogEvent.exp = localActiveExperiments;
      localLogEvent.sourceExtension = arrayOfByte;
      localEventLogger.mHandler.obtainMessage(2, localLogEvent).sendToTarget();
      localEventProtoCache = this.mProtoCache;
      if (paramPlayStoreLogEvent.impression == null) {
        break label221;
      }
      localPlayStoreImpressionEvent = paramPlayStoreLogEvent.impression;
      if (localPlayStoreImpressionEvent.tree != null) {
        localEventProtoCache.recycle(localPlayStoreImpressionEvent.tree);
      }
      PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement2 = localPlayStoreImpressionEvent.referrerPath;
      int k = arrayOfPlayStoreUiElement2.length;
      for (int m = 0; m < k; m++) {
        localEventProtoCache.recycle(arrayOfPlayStoreUiElement2[m]);
      }
    }
    localPlayStoreImpressionEvent.clear();
    localEventProtoCache.mCachePlayStoreImpression.recycle(localPlayStoreImpressionEvent);
    label221:
    if (paramPlayStoreLogEvent.click != null)
    {
      PlayStore.PlayStoreClickEvent localPlayStoreClickEvent = paramPlayStoreLogEvent.click;
      PlayStore.PlayStoreUiElement[] arrayOfPlayStoreUiElement1 = localPlayStoreClickEvent.elementPath;
      int i = arrayOfPlayStoreUiElement1.length;
      for (int j = 0; j < i; j++) {
        localEventProtoCache.recycle(arrayOfPlayStoreUiElement1[j]);
      }
      localPlayStoreClickEvent.clear();
      localEventProtoCache.mCachePlayStoreClick.recycle(localPlayStoreClickEvent);
    }
    if (paramPlayStoreLogEvent.backgroundAction != null)
    {
      PlayStore.PlayStoreBackgroundActionEvent localPlayStoreBackgroundActionEvent = paramPlayStoreLogEvent.backgroundAction;
      if (localPlayStoreBackgroundActionEvent.searchSuggestionReport != null)
      {
        PlayStore.SearchSuggestionReport localSearchSuggestionReport = localPlayStoreBackgroundActionEvent.searchSuggestionReport;
        localSearchSuggestionReport.clear();
        localEventProtoCache.mCachePlayStoreSearchSuggestionReport.recycle(localSearchSuggestionReport);
      }
      localPlayStoreBackgroundActionEvent.clear();
      localEventProtoCache.mCachePlayStoreBackgroundAction.recycle(localPlayStoreBackgroundActionEvent);
    }
    if (paramPlayStoreLogEvent.search != null)
    {
      PlayStore.PlayStoreSearchEvent localPlayStoreSearchEvent = paramPlayStoreLogEvent.search;
      localPlayStoreSearchEvent.clear();
      localEventProtoCache.mCachePlayStoreSearch.recycle(localPlayStoreSearchEvent);
    }
    paramPlayStoreLogEvent.clear();
    localEventProtoCache.mCachePlayStoreLogEvent.recycle(paramPlayStoreLogEvent);
  }
  
  public static abstract interface LogListener {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.analytics.FinskyEventLog
 * JD-Core Version:    0.7.0.1
 */