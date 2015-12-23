package com.google.android.finsky.library;

import android.accounts.Account;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.LibraryReplicationRequest;
import com.google.android.finsky.protos.LibraryReplicationResponse;
import com.google.android.finsky.protos.LibraryUpdateProto.ClientLibraryState;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryAppDetails;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryFamilySharingDetails;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryInAppDetails;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryMutation;
import com.google.android.finsky.protos.LibraryUpdateProto.LibrarySubscriptionDetails;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryUpdate;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import com.google.protobuf.nano.MessageNanoPrinter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class LibraryReplicator
{
  private static final long RESCHEDULE_REPLICATION_MS = ((Integer)G.libraryReplicatorRescheduleDelayMs.get()).intValue();
  final AccountLibrary mAccountLibrary;
  final Handler mBackgroundHandler;
  private ReplicationRequest mCurrentRequest;
  Queue<DebugEvent> mDebugEvents;
  private final DfeApi mDfeApi;
  private final boolean mEnableDebugging;
  private final List<Listener> mListeners = new ArrayList();
  private final Handler mNotificationHandler;
  private final List<ReplicationRequest> mReplicationRequests = new ArrayList();
  private final Runnable mReplicationRunnable = new Runnable()
  {
    public final void run()
    {
      if (LibraryReplicator.this.mCurrentRequest == null)
      {
        LibraryReplicationRequest localLibraryReplicationRequest;
        try
        {
          if (LibraryReplicator.this.mReplicationRequests.size() == 0) {
            return;
          }
          LibraryReplicator.access$002(LibraryReplicator.this, (LibraryReplicator.ReplicationRequest)LibraryReplicator.this.mReplicationRequests.remove(0));
          if (LibraryReplicator.this.mEnableDebugging) {
            LibraryReplicator.access$300$4e9e4d70(LibraryReplicator.this, 2, null, LibraryReplicator.this.mCurrentRequest.debugTag);
          }
          localLibraryReplicationRequest = new LibraryReplicationRequest();
          int i = LibraryReplicator.this.mCurrentRequest.libraryIds.length;
          localLibraryReplicationRequest.libraryState = new LibraryUpdateProto.ClientLibraryState[i];
          for (int j = 0; j < i; j++) {
            localLibraryReplicationRequest.libraryState[j] = LibraryReplicator.access$400(LibraryReplicator.this, LibraryReplicator.this.mCurrentRequest.libraryIds[j]);
          }
          localLibraryReplicationRequest.libraryMutationVersion = 1;
        }
        finally {}
        LibraryReplicator.LibraryUpdateListener localLibraryUpdateListener = new LibraryReplicator.LibraryUpdateListener(LibraryReplicator.this);
        LibraryReplicator.this.mDfeApi.replicateLibrary(localLibraryReplicationRequest, localLibraryUpdateListener, localLibraryUpdateListener);
        return;
      }
      if (FinskyLog.DEBUG) {
        FinskyLog.v("Replication request pending.", new Object[0]);
      }
    }
  };
  private final SQLiteLibrary mSQLiteLibrary;
  
  public LibraryReplicator(DfeApi paramDfeApi, SQLiteLibrary paramSQLiteLibrary, AccountLibrary paramAccountLibrary, Handler paramHandler1, Handler paramHandler2, boolean paramBoolean)
  {
    this.mDfeApi = paramDfeApi;
    this.mSQLiteLibrary = paramSQLiteLibrary;
    this.mAccountLibrary = paramAccountLibrary;
    this.mNotificationHandler = paramHandler1;
    this.mBackgroundHandler = paramHandler2;
    this.mEnableDebugging = paramBoolean;
  }
  
  private String[] getSupportedLibraries(String[] paramArrayOfString)
  {
    int i = 0;
    int j = paramArrayOfString.length;
    for (int k = 0; k < j; k++)
    {
      String str2 = paramArrayOfString[k];
      if (!this.mAccountLibrary.supportsLibrary(str2)) {
        i++;
      }
    }
    String[] arrayOfString;
    if (i == paramArrayOfString.length) {
      arrayOfString = null;
    }
    for (;;)
    {
      return arrayOfString;
      if (i <= 0) {
        break;
      }
      arrayOfString = new String[paramArrayOfString.length - i];
      int m = 0;
      int n = paramArrayOfString.length;
      for (int i1 = 0; i1 < n; i1++)
      {
        String str1 = paramArrayOfString[i1];
        if (this.mAccountLibrary.supportsLibrary(str1))
        {
          arrayOfString[m] = str1;
          m++;
        }
      }
    }
    return paramArrayOfString;
  }
  
  private void handleNextRequest(long paramLong)
  {
    try
    {
      this.mBackgroundHandler.removeCallbacks(this.mReplicationRunnable);
      this.mBackgroundHandler.postDelayed(this.mReplicationRunnable, paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private boolean internalApplyLibraryUpdate(LibraryUpdateProto.LibraryUpdate paramLibraryUpdate, String paramString)
  {
    if (this.mEnableDebugging) {
      recordDebugEvent(0, paramLibraryUpdate, null, paramString);
    }
    if (Looper.myLooper() != this.mBackgroundHandler.getLooper()) {
      FinskyLog.wtf("This method must be called from the background handler.", new Object[0]);
    }
    if (!TextUtils.isEmpty(paramLibraryUpdate.libraryId)) {}
    for (String str1 = paramLibraryUpdate.libraryId; !this.mAccountLibrary.supportsLibrary(str1); str1 = AccountLibrary.getLibraryIdFromBackend(paramLibraryUpdate.corpus))
    {
      FinskyLog.d("Ignoring library update for unsupported library %s", new Object[] { str1 });
      recordDebugEvent(5, null, null, paramString);
      return false;
    }
    this.mAccountLibrary.disableListeners();
    try
    {
      switch (paramLibraryUpdate.status)
      {
      default: 
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = str1;
        arrayOfObject[1] = Integer.valueOf(paramLibraryUpdate.status);
        FinskyLog.wtf("Unknown LibraryUpdate.status: libraryId=%s, status=%d", arrayOfObject);
        if (paramLibraryUpdate.serverToken.length > 0)
        {
          byte[] arrayOfByte = paramLibraryUpdate.serverToken;
          this.mAccountLibrary.setServerToken(str1, arrayOfByte);
          PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.getLibraryServerToken(str1).get(this.mAccountLibrary.mAccount.name);
          String str9 = Base64.encodeToString(arrayOfByte, 0);
          localSharedPreference.put(str9);
          if (FinskyLog.DEBUG) {
            FinskyLog.v("Updated server token: libraryId=%s serverToken=%s", new Object[] { str1, str9 });
          }
        }
        return paramLibraryUpdate.hasMore;
      }
    }
    finally
    {
      LibraryUpdateProto.LibraryMutation[] arrayOfLibraryMutation;
      int j;
      LibraryUpdateProto.LibraryMutation localLibraryMutation;
      String str2;
      String str3;
      int m;
      long l1;
      LibraryUpdateProto.LibraryFamilySharingDetails localLibraryFamilySharingDetails;
      boolean bool2;
      int i2;
      Long localLong;
      Object localObject2;
      this.mAccountLibrary.enableListeners();
    }
    this.mSQLiteLibrary.resetAccountLibrary(this.mAccountLibrary.mAccount, str1);
    this.mAccountLibrary.resetLibrary(str1);
    ArrayList localArrayList = Lists.newArrayList(paramLibraryUpdate.mutation.length);
    int i = 0;
    arrayOfLibraryMutation = paramLibraryUpdate.mutation;
    j = arrayOfLibraryMutation.length;
    int k = 0;
    label311:
    int n;
    int i1;
    boolean bool1;
    String str4;
    label456:
    label603:
    long l6;
    if (k < j)
    {
      localLibraryMutation = arrayOfLibraryMutation[k];
      str2 = this.mAccountLibrary.mAccount.name;
      str3 = localLibraryMutation.docid.backendDocid;
      m = localLibraryMutation.docid.backend;
      n = localLibraryMutation.docid.type;
      i1 = localLibraryMutation.offerType;
      l1 = localLibraryMutation.documentHash;
      localLibraryFamilySharingDetails = localLibraryMutation.familySharingDetails;
      bool1 = false;
      str4 = null;
      if (localLibraryFamilySharingDetails != null)
      {
        bool2 = localLibraryFamilySharingDetails.hasFamilySharingRole;
        bool1 = false;
        str4 = null;
        if (bool2)
        {
          i2 = localLibraryFamilySharingDetails.familySharingRole;
          bool1 = false;
          str4 = null;
        }
      }
      switch (i2)
      {
      default: 
        boolean bool3 = localLibraryMutation.hasValidUntilTimestampMsec;
        localLong = null;
        if (bool3) {
          localLong = Long.valueOf(localLibraryMutation.validUntilTimestampMsec);
        }
        if ((!AccountLibrary.isLibraryMultiContainer(str1)) && (!localLibraryMutation.deleted))
        {
          if (n != 1) {
            break label1017;
          }
          LibraryUpdateProto.LibraryAppDetails localLibraryAppDetails = localLibraryMutation.appDetails;
          String[] arrayOfString = localLibraryAppDetails.certificateHash;
          long l3 = 0L;
          if (localLibraryAppDetails.hasRefundTimeoutTimestampMsec) {
            l3 = localLibraryAppDetails.refundTimeoutTimestampMsec;
          }
          long l4 = 0L;
          if (localLibraryAppDetails.hasPostDeliveryRefundWindowMsec) {
            l4 = localLibraryAppDetails.postDeliveryRefundWindowMsec;
          }
          boolean bool5 = localLibraryAppDetails.hasIsOwnedViaLicense;
          boolean bool6 = false;
          if (bool5) {
            bool6 = localLibraryAppDetails.isOwnedViaLicense;
          }
          localObject2 = new LibraryAppEntry(str2, str3, i1, l1, arrayOfString, l3, l4, bool6, bool1, str4);
        }
      case 2: 
        for (;;)
        {
          if (localLibraryMutation.deleted)
          {
            this.mAccountLibrary.remove((LibraryEntry)localObject2);
            this.mSQLiteLibrary.remove((LibraryEntry)localObject2);
            i = 1;
            break label1002;
            str4 = localLibraryFamilySharingDetails.sharingOriginatorGaiaId;
            bool1 = false;
            break;
            label648:
            LibraryUpdateProto.LibrarySubscriptionDetails localLibrarySubscriptionDetails = localLibraryMutation.subscriptionDetails;
            long l5 = localLibrarySubscriptionDetails.initiationTimestampMsec;
            if (localLong == null) {
              localLong = Long.valueOf(localLibrarySubscriptionDetails.deprecatedValidUntilTimestampMsec);
            }
            if (!localLibrarySubscriptionDetails.hasTrialUntilTimestampMsec) {
              break label1034;
            }
            l6 = localLibrarySubscriptionDetails.trialUntilTimestampMsec;
            label692:
            boolean bool7 = localLibrarySubscriptionDetails.autoRenewing;
            if (m == 3)
            {
              String str5 = localLibrarySubscriptionDetails.signedPurchaseData;
              String str6 = localLibrarySubscriptionDetails.signature;
              long l7 = localLong.longValue();
              localObject2 = new LibraryInAppSubscriptionEntry(str2, str1, m, str3, i1, l1, l7, l5, l6, bool7, str5, str6);
            }
            else
            {
              localObject2 = new LibrarySubscriptionEntry(str2, str1, m, str3, n, i1, l1, localLong, l5, l6, bool7);
              continue;
              label794:
              if ((n == 11) && (localLibraryMutation.inAppDetails != null))
              {
                LibraryUpdateProto.LibraryInAppDetails localLibraryInAppDetails = localLibraryMutation.inAppDetails;
                String str7 = localLibraryInAppDetails.signedPurchaseData;
                String str8 = localLibraryInAppDetails.signature;
                localObject2 = new LibraryInAppEntry(str2, str1, str3, i1, str7, str8, l1);
              }
              else
              {
                if (localLong == null) {
                  break label1040;
                }
              }
            }
          }
        }
      }
    }
    label1034:
    label1040:
    for (long l2 = localLong.longValue();; l2 = 9223372036854775807L)
    {
      boolean bool4 = localLibraryMutation.preordered;
      localObject2 = new LibraryEntry(str2, str1, m, str3, n, i1, l1, l2, bool4, bool1, str4);
      break label603;
      localArrayList.add(localObject2);
      break label1002;
      this.mAccountLibrary.addAll(localArrayList);
      this.mSQLiteLibrary.addAll(localArrayList);
      if ((localArrayList.isEmpty()) && (i == 0)) {
        break;
      }
      notifyMutationListeners(str1);
      break;
      if (FinskyLog.DEBUG) {
        FinskyLog.v("NOT_MODIFIED received for libraryId=%s", new Object[] { str1 });
      }
      this.mAccountLibrary.enableListeners();
      return false;
      label1002:
      k++;
      break label311;
      bool1 = true;
      str4 = null;
      break label456;
      label1017:
      if (n == 15) {
        break label648;
      }
      if (i1 != 13) {
        break label794;
      }
      break label648;
      l6 = 0L;
      break label692;
    }
  }
  
  private void notifyMutationListeners(final String paramString)
  {
    try
    {
      Iterator localIterator = this.mListeners.iterator();
      while (localIterator.hasNext())
      {
        final Listener localListener = (Listener)localIterator.next();
        this.mNotificationHandler.post(new Runnable()
        {
          public final void run()
          {
            localListener.onMutationsApplied(paramString);
          }
        });
      }
    }
    finally {}
  }
  
  private void recordDebugEvent(int paramInt, LibraryUpdateProto.LibraryUpdate paramLibraryUpdate, VolleyError paramVolleyError, String paramString)
  {
    try
    {
      if (this.mDebugEvents == null) {
        this.mDebugEvents = new LinkedList();
      }
      DebugEvent localDebugEvent = new DebugEvent((byte)0);
      localDebugEvent.timestampMs = System.currentTimeMillis();
      localDebugEvent.type = paramInt;
      localDebugEvent.tag = paramString;
      localDebugEvent.libraryUpdate = paramLibraryUpdate;
      localDebugEvent.volleyError = paramVolleyError;
      this.mDebugEvents.add(localDebugEvent);
      if (this.mDebugEvents.size() > 100) {
        this.mDebugEvents.remove();
      }
      return;
    }
    finally {}
  }
  
  private void scheduleRequestAtFront(ReplicationRequest paramReplicationRequest)
  {
    try
    {
      if (this.mEnableDebugging) {
        recordDebugEvent(1, null, null, paramReplicationRequest.debugTag);
      }
      this.mReplicationRequests.add(0, paramReplicationRequest);
      return;
    }
    finally {}
  }
  
  public final void addListener(Listener paramListener)
  {
    try
    {
      this.mListeners.add(paramListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final void replicate(String[] paramArrayOfString, Runnable paramRunnable, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 81	com/google/android/finsky/library/LibraryReplicator:mEnableDebugging	Z
    //   6: ifeq +39 -> 45
    //   9: new 577	java/lang/StringBuilder
    //   12: dup
    //   13: invokespecial 578	java/lang/StringBuilder:<init>	()V
    //   16: aload_3
    //   17: invokevirtual 582	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: ldc_w 584
    //   23: invokevirtual 582	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload_1
    //   27: invokestatic 590	java/util/Arrays:toString	([Ljava/lang/Object;)Ljava/lang/String;
    //   30: invokevirtual 582	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 593	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: astore_3
    //   37: aload_0
    //   38: iconst_1
    //   39: aconst_null
    //   40: aconst_null
    //   41: aload_3
    //   42: invokespecial 114	com/google/android/finsky/library/LibraryReplicator:recordDebugEvent	(ILcom/google/android/finsky/protos/LibraryUpdateProto$LibraryUpdate;Lcom/android/volley/VolleyError;Ljava/lang/String;)V
    //   45: aload_0
    //   46: aload_1
    //   47: invokespecial 595	com/google/android/finsky/library/LibraryReplicator:getSupportedLibraries	([Ljava/lang/String;)[Ljava/lang/String;
    //   50: astore 5
    //   52: aload 5
    //   54: ifnonnull +25 -> 79
    //   57: ldc_w 597
    //   60: iconst_0
    //   61: anewarray 4	java/lang/Object
    //   64: invokestatic 241	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   67: aload_0
    //   68: getfield 77	com/google/android/finsky/library/LibraryReplicator:mNotificationHandler	Landroid/os/Handler;
    //   71: aload_2
    //   72: invokevirtual 527	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   75: pop
    //   76: aload_0
    //   77: monitorexit
    //   78: return
    //   79: aload_0
    //   80: getfield 62	com/google/android/finsky/library/LibraryReplicator:mReplicationRequests	Ljava/util/List;
    //   83: new 565	com/google/android/finsky/library/LibraryReplicator$ReplicationRequest
    //   86: dup
    //   87: aload 5
    //   89: aload_2
    //   90: aload_3
    //   91: invokespecial 599	com/google/android/finsky/library/LibraryReplicator$ReplicationRequest:<init>	([Ljava/lang/String;Ljava/lang/Runnable;Ljava/lang/String;)V
    //   94: invokeinterface 489 2 0
    //   99: pop
    //   100: aload_0
    //   101: lconst_0
    //   102: invokespecial 105	com/google/android/finsky/library/LibraryReplicator:handleNextRequest	(J)V
    //   105: goto -29 -> 76
    //   108: astore 4
    //   110: aload_0
    //   111: monitorexit
    //   112: aload 4
    //   114: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	115	0	this	LibraryReplicator
    //   0	115	1	paramArrayOfString	String[]
    //   0	115	2	paramRunnable	Runnable
    //   0	115	3	paramString	String
    //   108	5	4	localObject	Object
    //   50	38	5	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   2	45	108	finally
    //   45	52	108	finally
    //   57	76	108	finally
    //   79	105	108	finally
  }
  
  private static final class DebugEvent
  {
    LibraryUpdateProto.LibraryUpdate libraryUpdate;
    String tag;
    long timestampMs;
    int type;
    VolleyError volleyError;
    
    public final void dumpState(String paramString)
    {
      Log.d("FinskyLibrary", paramString + "Event {");
      StringBuilder localStringBuilder = new StringBuilder().append(paramString).append("  type=");
      int i = this.type;
      String str1;
      switch (i)
      {
      default: 
        str1 = String.valueOf(i) + " (FIXME)";
      }
      for (;;)
      {
        Log.d("FinskyLibrary", str1);
        Log.d("FinskyLibrary", paramString + "  timestampMs=" + this.timestampMs);
        Log.d("FinskyLibrary", paramString + "  timestamp=" + DateFormat.format("MM-dd hh:mm:ss", this.timestampMs));
        if (this.tag != null) {
          Log.d("FinskyLibrary", paramString + "  tag=" + this.tag);
        }
        if (this.libraryUpdate == null) {
          break;
        }
        String[] arrayOfString = MessageNanoPrinter.print(this.libraryUpdate).split("\n");
        Log.d("FinskyLibrary", paramString + "  libraryUpdate=");
        int j = arrayOfString.length;
        for (int k = 0; k < j; k++)
        {
          String str2 = arrayOfString[k];
          Log.d("FinskyLibrary", paramString + "    " + str2);
        }
        str1 = "APPLY_LIBRARY_UPDATE";
        continue;
        str1 = "SCHEDULE_REPLICATION";
        continue;
        str1 = "REPLICATE";
        continue;
        str1 = "ERROR_VOLLEY";
        continue;
        str1 = "ERROR_TOKEN_CHANGED";
        continue;
        str1 = "ERROR_UNSUPPORTED_LIBRARY";
      }
      if (this.volleyError != null) {
        Log.d("FinskyLibrary", paramString + "  volleyError=" + this.volleyError);
      }
      Log.d("FinskyLibrary", paramString + "}");
    }
  }
  
  private final class LibraryUpdateListener
    implements Response.ErrorListener, Response.Listener<LibraryReplicationResponse>
  {
    final Map<String, byte[]> mOriginalTokens = new HashMap();
    
    public LibraryUpdateListener()
    {
      for (String str : AccountLibrary.LIBRARY_IDS) {
        this.mOriginalTokens.put(str, LibraryReplicator.this.mAccountLibrary.getServerToken(str));
      }
    }
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      FinskyLog.w("Library replication failed: %s", new Object[] { paramVolleyError });
      if (LibraryReplicator.this.mEnableDebugging) {
        LibraryReplicator.access$300$4e9e4d70(LibraryReplicator.this, 3, paramVolleyError, null);
      }
      LibraryReplicator.this.mBackgroundHandler.post(new Runnable()
      {
        public final void run()
        {
          if (LibraryReplicator.this.mCurrentRequest == null)
          {
            FinskyLog.wtf("Expected pending replication request.", new Object[0]);
            return;
          }
          if (LibraryReplicator.this.mCurrentRequest.finishRunnable != null) {
            LibraryReplicator.this.mNotificationHandler.post(LibraryReplicator.this.mCurrentRequest.finishRunnable);
          }
          LibraryReplicator.access$002(LibraryReplicator.this, null);
          LibraryReplicator.this.handleNextRequest(LibraryReplicator.RESCHEDULE_REPLICATION_MS);
        }
      });
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onMutationsApplied(String paramString);
  }
  
  private static final class ReplicationRequest
  {
    public String debugTag;
    public final Runnable finishRunnable;
    public final String[] libraryIds;
    
    public ReplicationRequest(String[] paramArrayOfString, Runnable paramRunnable, String paramString)
    {
      this.libraryIds = paramArrayOfString;
      this.finishRunnable = paramRunnable;
      this.debugTag = paramString;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryReplicator
 * JD-Core Version:    0.7.0.1
 */