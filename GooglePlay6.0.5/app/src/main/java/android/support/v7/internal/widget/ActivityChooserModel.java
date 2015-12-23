package android.support.v7.internal.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityChooserModel
  extends DataSetObservable
{
  private static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
  private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
  private static final Object sRegistryLock = new Object();
  final List<ActivityResolveInfo> mActivities;
  private OnChooseActivityListener mActivityChoserModelPolicy;
  private ActivitySorter mActivitySorter;
  private boolean mCanReadHistoricalData;
  private final Context mContext;
  private final List<HistoricalRecord> mHistoricalRecords;
  private boolean mHistoricalRecordsChanged;
  private final String mHistoryFileName;
  private int mHistoryMaxSize;
  final Object mInstanceLock;
  private Intent mIntent;
  private boolean mReadShareHistoryCalled;
  private boolean mReloadActivities;
  
  private void pruneExcessiveHistoricalRecordsIfNeeded()
  {
    int i = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
    if (i <= 0) {}
    for (;;)
    {
      return;
      this.mHistoricalRecordsChanged = true;
      for (int j = 0; j < i; j++) {
        this.mHistoricalRecords.remove(0);
      }
    }
  }
  
  /* Error */
  private void readHistoricalDataImpl()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 59	android/support/v7/internal/widget/ActivityChooserModel:mContext	Landroid/content/Context;
    //   4: aload_0
    //   5: getfield 64	android/support/v7/internal/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
    //   8: invokevirtual 98	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   11: astore_2
    //   12: invokestatic 104	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   15: astore 11
    //   17: aload 11
    //   19: aload_2
    //   20: ldc 106
    //   22: invokeinterface 112 3 0
    //   27: iconst_0
    //   28: istore 12
    //   30: iload 12
    //   32: iconst_1
    //   33: if_icmpeq +21 -> 54
    //   36: iload 12
    //   38: iconst_2
    //   39: if_icmpeq +15 -> 54
    //   42: aload 11
    //   44: invokeinterface 115 1 0
    //   49: istore 12
    //   51: goto -21 -> 30
    //   54: ldc 117
    //   56: aload 11
    //   58: invokeinterface 120 1 0
    //   63: invokevirtual 126	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   66: ifne +52 -> 118
    //   69: new 90	org/xmlpull/v1/XmlPullParserException
    //   72: dup
    //   73: ldc 128
    //   75: invokespecial 131	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   78: athrow
    //   79: astore 8
    //   81: getstatic 43	android/support/v7/internal/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
    //   84: new 133	java/lang/StringBuilder
    //   87: dup
    //   88: ldc 135
    //   90: invokespecial 136	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   93: aload_0
    //   94: getfield 64	android/support/v7/internal/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
    //   97: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: aload 8
    //   105: invokestatic 149	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   108: pop
    //   109: aload_2
    //   110: ifnull +7 -> 117
    //   113: aload_2
    //   114: invokevirtual 154	java/io/FileInputStream:close	()V
    //   117: return
    //   118: aload_0
    //   119: getfield 71	android/support/v7/internal/widget/ActivityChooserModel:mHistoricalRecords	Ljava/util/List;
    //   122: astore 13
    //   124: aload 13
    //   126: invokeinterface 157 1 0
    //   131: aload 11
    //   133: invokeinterface 115 1 0
    //   138: istore 14
    //   140: iload 14
    //   142: iconst_1
    //   143: if_icmpeq +147 -> 290
    //   146: iload 14
    //   148: iconst_3
    //   149: if_icmpeq -18 -> 131
    //   152: iload 14
    //   154: iconst_4
    //   155: if_icmpeq -24 -> 131
    //   158: ldc 159
    //   160: aload 11
    //   162: invokeinterface 120 1 0
    //   167: invokevirtual 126	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   170: ifne +55 -> 225
    //   173: new 90	org/xmlpull/v1/XmlPullParserException
    //   176: dup
    //   177: ldc 161
    //   179: invokespecial 131	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   182: athrow
    //   183: astore 5
    //   185: getstatic 43	android/support/v7/internal/widget/ActivityChooserModel:LOG_TAG	Ljava/lang/String;
    //   188: new 133	java/lang/StringBuilder
    //   191: dup
    //   192: ldc 135
    //   194: invokespecial 136	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   197: aload_0
    //   198: getfield 64	android/support/v7/internal/widget/ActivityChooserModel:mHistoryFileName	Ljava/lang/String;
    //   201: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   207: aload 5
    //   209: invokestatic 149	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   212: pop
    //   213: aload_2
    //   214: ifnull -97 -> 117
    //   217: aload_2
    //   218: invokevirtual 154	java/io/FileInputStream:close	()V
    //   221: return
    //   222: astore 7
    //   224: return
    //   225: aload 13
    //   227: new 163	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord
    //   230: dup
    //   231: aload 11
    //   233: aconst_null
    //   234: ldc 165
    //   236: invokeinterface 169 3 0
    //   241: aload 11
    //   243: aconst_null
    //   244: ldc 171
    //   246: invokeinterface 169 3 0
    //   251: invokestatic 177	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   254: aload 11
    //   256: aconst_null
    //   257: ldc 179
    //   259: invokeinterface 169 3 0
    //   264: invokestatic 185	java/lang/Float:parseFloat	(Ljava/lang/String;)F
    //   267: invokespecial 188	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:<init>	(Ljava/lang/String;JF)V
    //   270: invokeinterface 191 2 0
    //   275: pop
    //   276: goto -145 -> 131
    //   279: astore_3
    //   280: aload_2
    //   281: ifnull +7 -> 288
    //   284: aload_2
    //   285: invokevirtual 154	java/io/FileInputStream:close	()V
    //   288: aload_3
    //   289: athrow
    //   290: aload_2
    //   291: ifnull -174 -> 117
    //   294: aload_2
    //   295: invokevirtual 154	java/io/FileInputStream:close	()V
    //   298: return
    //   299: astore 15
    //   301: return
    //   302: astore 10
    //   304: return
    //   305: astore 4
    //   307: goto -19 -> 288
    //   310: astore_1
    //   311: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	312	0	this	ActivityChooserModel
    //   310	1	1	localFileNotFoundException	java.io.FileNotFoundException
    //   11	284	2	localFileInputStream	java.io.FileInputStream
    //   279	10	3	localObject	Object
    //   305	1	4	localIOException1	java.io.IOException
    //   183	25	5	localIOException2	java.io.IOException
    //   222	1	7	localIOException3	java.io.IOException
    //   79	25	8	localXmlPullParserException	org.xmlpull.v1.XmlPullParserException
    //   302	1	10	localIOException4	java.io.IOException
    //   15	240	11	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   28	22	12	i	int
    //   122	104	13	localList	List
    //   138	18	14	j	int
    //   299	1	15	localIOException5	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   12	27	79	org/xmlpull/v1/XmlPullParserException
    //   42	51	79	org/xmlpull/v1/XmlPullParserException
    //   54	79	79	org/xmlpull/v1/XmlPullParserException
    //   118	131	79	org/xmlpull/v1/XmlPullParserException
    //   131	140	79	org/xmlpull/v1/XmlPullParserException
    //   158	183	79	org/xmlpull/v1/XmlPullParserException
    //   225	276	79	org/xmlpull/v1/XmlPullParserException
    //   12	27	183	java/io/IOException
    //   42	51	183	java/io/IOException
    //   54	79	183	java/io/IOException
    //   118	131	183	java/io/IOException
    //   131	140	183	java/io/IOException
    //   158	183	183	java/io/IOException
    //   225	276	183	java/io/IOException
    //   217	221	222	java/io/IOException
    //   12	27	279	finally
    //   42	51	279	finally
    //   54	79	279	finally
    //   81	109	279	finally
    //   118	131	279	finally
    //   131	140	279	finally
    //   158	183	279	finally
    //   185	213	279	finally
    //   225	276	279	finally
    //   294	298	299	java/io/IOException
    //   113	117	302	java/io/IOException
    //   284	288	305	java/io/IOException
    //   0	12	310	java/io/FileNotFoundException
  }
  
  private boolean sortActivitiesIfNeeded()
  {
    if ((this.mActivitySorter != null) && (this.mIntent != null) && (!this.mActivities.isEmpty()) && (!this.mHistoricalRecords.isEmpty()))
    {
      Collections.unmodifiableList(this.mHistoricalRecords);
      return true;
    }
    return false;
  }
  
  final boolean addHisoricalRecord(HistoricalRecord paramHistoricalRecord)
  {
    boolean bool = this.mHistoricalRecords.add(paramHistoricalRecord);
    PersistHistoryAsyncTask localPersistHistoryAsyncTask;
    Object[] arrayOfObject;
    if (bool)
    {
      this.mHistoricalRecordsChanged = true;
      pruneExcessiveHistoricalRecordsIfNeeded();
      if (!this.mReadShareHistoryCalled) {
        throw new IllegalStateException("No preceding call to #readHistoricalData");
      }
      if (this.mHistoricalRecordsChanged)
      {
        this.mHistoricalRecordsChanged = false;
        if (!TextUtils.isEmpty(this.mHistoryFileName))
        {
          localPersistHistoryAsyncTask = new PersistHistoryAsyncTask((byte)0);
          arrayOfObject = new Object[2];
          arrayOfObject[0] = new ArrayList(this.mHistoricalRecords);
          arrayOfObject[1] = this.mHistoryFileName;
          if (Build.VERSION.SDK_INT < 11) {
            break label133;
          }
          localPersistHistoryAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, arrayOfObject);
        }
      }
    }
    for (;;)
    {
      sortActivitiesIfNeeded();
      notifyChanged();
      return bool;
      label133:
      localPersistHistoryAsyncTask.execute(arrayOfObject);
    }
  }
  
  public final Intent chooseActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      if (this.mIntent == null) {
        return null;
      }
      ensureConsistentState();
      ActivityResolveInfo localActivityResolveInfo = (ActivityResolveInfo)this.mActivities.get(paramInt);
      ComponentName localComponentName = new ComponentName(localActivityResolveInfo.resolveInfo.activityInfo.packageName, localActivityResolveInfo.resolveInfo.activityInfo.name);
      Intent localIntent = new Intent(this.mIntent);
      localIntent.setComponent(localComponentName);
      if (this.mActivityChoserModelPolicy != null)
      {
        new Intent(localIntent);
        if (this.mActivityChoserModelPolicy.onChooseActivity$709ef6d7()) {
          return null;
        }
      }
      addHisoricalRecord(new HistoricalRecord(localComponentName, System.currentTimeMillis(), 1.0F));
      return localIntent;
    }
  }
  
  final void ensureConsistentState()
  {
    boolean bool = true;
    int i;
    if ((this.mReloadActivities) && (this.mIntent != null))
    {
      this.mReloadActivities = false;
      this.mActivities.clear();
      List localList = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
      int k = localList.size();
      for (int m = 0; m < k; m++)
      {
        ResolveInfo localResolveInfo = (ResolveInfo)localList.get(m);
        this.mActivities.add(new ActivityResolveInfo(localResolveInfo));
      }
      i = bool;
      if ((!this.mCanReadHistoricalData) || (!this.mHistoricalRecordsChanged) || (TextUtils.isEmpty(this.mHistoryFileName))) {
        break label173;
      }
      this.mCanReadHistoricalData = false;
      this.mReadShareHistoryCalled = bool;
      readHistoricalDataImpl();
    }
    for (;;)
    {
      int j = i | bool;
      pruneExcessiveHistoricalRecordsIfNeeded();
      if (j != 0)
      {
        sortActivitiesIfNeeded();
        notifyChanged();
      }
      return;
      i = 0;
      break;
      label173:
      bool = false;
    }
  }
  
  public final ResolveInfo getActivity(int paramInt)
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.mActivities.get(paramInt)).resolveInfo;
      return localResolveInfo;
    }
  }
  
  public final int getActivityCount()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      int i = this.mActivities.size();
      return i;
    }
  }
  
  public final int getActivityIndex(ResolveInfo paramResolveInfo)
  {
    for (;;)
    {
      int j;
      synchronized (this.mInstanceLock)
      {
        ensureConsistentState();
        List localList = this.mActivities;
        int i = localList.size();
        j = 0;
        if (j < i)
        {
          if (((ActivityResolveInfo)localList.get(j)).resolveInfo == paramResolveInfo) {
            return j;
          }
        }
        else {
          return -1;
        }
      }
      j++;
    }
  }
  
  public final ResolveInfo getDefaultActivity()
  {
    synchronized (this.mInstanceLock)
    {
      ensureConsistentState();
      if (!this.mActivities.isEmpty())
      {
        ResolveInfo localResolveInfo = ((ActivityResolveInfo)this.mActivities.get(0)).resolveInfo;
        return localResolveInfo;
      }
      return null;
    }
  }
  
  public final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo>
  {
    public final ResolveInfo resolveInfo;
    public float weight;
    
    public ActivityResolveInfo(ResolveInfo paramResolveInfo)
    {
      this.resolveInfo = paramResolveInfo;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      ActivityResolveInfo localActivityResolveInfo;
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        localActivityResolveInfo = (ActivityResolveInfo)paramObject;
      } while (Float.floatToIntBits(this.weight) == Float.floatToIntBits(localActivityResolveInfo.weight));
      return false;
    }
    
    public final int hashCode()
    {
      return 31 + Float.floatToIntBits(this.weight);
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("resolveInfo:").append(this.resolveInfo.toString());
      localStringBuilder.append("; weight:").append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface ActivitySorter {}
  
  public static final class HistoricalRecord
  {
    public final ComponentName activity;
    public final long time;
    public final float weight;
    
    public HistoricalRecord(ComponentName paramComponentName, long paramLong, float paramFloat)
    {
      this.activity = paramComponentName;
      this.time = paramLong;
      this.weight = paramFloat;
    }
    
    public HistoricalRecord(String paramString, long paramLong, float paramFloat)
    {
      this(ComponentName.unflattenFromString(paramString), paramLong, paramFloat);
    }
    
    public final boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      HistoricalRecord localHistoricalRecord;
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        localHistoricalRecord = (HistoricalRecord)paramObject;
        if (this.activity == null)
        {
          if (localHistoricalRecord.activity != null) {
            return false;
          }
        }
        else if (!this.activity.equals(localHistoricalRecord.activity)) {
          return false;
        }
        if (this.time != localHistoricalRecord.time) {
          return false;
        }
      } while (Float.floatToIntBits(this.weight) == Float.floatToIntBits(localHistoricalRecord.weight));
      return false;
    }
    
    public final int hashCode()
    {
      if (this.activity == null) {}
      for (int i = 0;; i = this.activity.hashCode()) {
        return 31 * (31 * (i + 31) + (int)(this.time ^ this.time >>> 32)) + Float.floatToIntBits(this.weight);
      }
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append("; activity:").append(this.activity);
      localStringBuilder.append("; time:").append(this.time);
      localStringBuilder.append("; weight:").append(new BigDecimal(this.weight));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface OnChooseActivityListener
  {
    public abstract boolean onChooseActivity$709ef6d7();
  }
  
  private final class PersistHistoryAsyncTask
    extends AsyncTask<Object, Void, Void>
  {
    private PersistHistoryAsyncTask() {}
    
    /* Error */
    private Void doInBackground(Object... paramVarArgs)
    {
      // Byte code:
      //   0: aload_1
      //   1: iconst_0
      //   2: aaload
      //   3: checkcast 29	java/util/List
      //   6: astore_2
      //   7: aload_1
      //   8: iconst_1
      //   9: aaload
      //   10: checkcast 31	java/lang/String
      //   13: astore_3
      //   14: aload_0
      //   15: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   18: invokestatic 37	android/support/v7/internal/widget/ActivityChooserModel:access$200	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Landroid/content/Context;
      //   21: aload_3
      //   22: iconst_0
      //   23: invokevirtual 43	android/content/Context:openFileOutput	(Ljava/lang/String;I)Ljava/io/FileOutputStream;
      //   26: astore 6
      //   28: invokestatic 49	android/util/Xml:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
      //   31: astore 7
      //   33: aload 7
      //   35: aload 6
      //   37: aconst_null
      //   38: invokeinterface 55 3 0
      //   43: aload 7
      //   45: ldc 57
      //   47: iconst_1
      //   48: invokestatic 63	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   51: invokeinterface 67 3 0
      //   56: aload 7
      //   58: aconst_null
      //   59: ldc 69
      //   61: invokeinterface 73 3 0
      //   66: pop
      //   67: aload_2
      //   68: invokeinterface 77 1 0
      //   73: istore 24
      //   75: iconst_0
      //   76: istore 25
      //   78: iload 25
      //   80: iload 24
      //   82: if_icmpge +129 -> 211
      //   85: aload_2
      //   86: iconst_0
      //   87: invokeinterface 81 2 0
      //   92: checkcast 83	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord
      //   95: astore 26
      //   97: aload 7
      //   99: aconst_null
      //   100: ldc 85
      //   102: invokeinterface 73 3 0
      //   107: pop
      //   108: aload 7
      //   110: aconst_null
      //   111: ldc 87
      //   113: aload 26
      //   115: getfield 90	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:activity	Landroid/content/ComponentName;
      //   118: invokevirtual 96	android/content/ComponentName:flattenToString	()Ljava/lang/String;
      //   121: invokeinterface 100 4 0
      //   126: pop
      //   127: aload 7
      //   129: aconst_null
      //   130: ldc 102
      //   132: aload 26
      //   134: getfield 105	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:time	J
      //   137: invokestatic 108	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   140: invokeinterface 100 4 0
      //   145: pop
      //   146: aload 7
      //   148: aconst_null
      //   149: ldc 110
      //   151: aload 26
      //   153: getfield 113	android/support/v7/internal/widget/ActivityChooserModel$HistoricalRecord:weight	F
      //   156: invokestatic 116	java/lang/String:valueOf	(F)Ljava/lang/String;
      //   159: invokeinterface 100 4 0
      //   164: pop
      //   165: aload 7
      //   167: aconst_null
      //   168: ldc 85
      //   170: invokeinterface 119 3 0
      //   175: pop
      //   176: iinc 25 1
      //   179: goto -101 -> 78
      //   182: astore 4
      //   184: invokestatic 122	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   187: new 124	java/lang/StringBuilder
      //   190: dup
      //   191: ldc 126
      //   193: invokespecial 129	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   196: aload_3
      //   197: invokevirtual 133	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   200: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   203: aload 4
      //   205: invokestatic 142	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   208: pop
      //   209: aconst_null
      //   210: areturn
      //   211: aload 7
      //   213: aconst_null
      //   214: ldc 69
      //   216: invokeinterface 119 3 0
      //   221: pop
      //   222: aload 7
      //   224: invokeinterface 145 1 0
      //   229: aload_0
      //   230: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   233: invokestatic 149	android/support/v7/internal/widget/ActivityChooserModel:access$502$7906a670	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Z
      //   236: pop
      //   237: aload 6
      //   239: ifnull +8 -> 247
      //   242: aload 6
      //   244: invokevirtual 154	java/io/FileOutputStream:close	()V
      //   247: aconst_null
      //   248: areturn
      //   249: astore 19
      //   251: invokestatic 122	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   254: new 124	java/lang/StringBuilder
      //   257: dup
      //   258: ldc 126
      //   260: invokespecial 129	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   263: aload_0
      //   264: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   267: invokestatic 158	android/support/v7/internal/widget/ActivityChooserModel:access$400	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Ljava/lang/String;
      //   270: invokevirtual 133	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   273: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   276: aload 19
      //   278: invokestatic 142	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   281: pop
      //   282: aload_0
      //   283: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   286: invokestatic 149	android/support/v7/internal/widget/ActivityChooserModel:access$502$7906a670	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Z
      //   289: pop
      //   290: aload 6
      //   292: ifnull -45 -> 247
      //   295: aload 6
      //   297: invokevirtual 154	java/io/FileOutputStream:close	()V
      //   300: goto -53 -> 247
      //   303: astore 22
      //   305: goto -58 -> 247
      //   308: astore 15
      //   310: invokestatic 122	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   313: new 124	java/lang/StringBuilder
      //   316: dup
      //   317: ldc 126
      //   319: invokespecial 129	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   322: aload_0
      //   323: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   326: invokestatic 158	android/support/v7/internal/widget/ActivityChooserModel:access$400	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Ljava/lang/String;
      //   329: invokevirtual 133	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   332: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   335: aload 15
      //   337: invokestatic 142	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   340: pop
      //   341: aload_0
      //   342: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   345: invokestatic 149	android/support/v7/internal/widget/ActivityChooserModel:access$502$7906a670	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Z
      //   348: pop
      //   349: aload 6
      //   351: ifnull -104 -> 247
      //   354: aload 6
      //   356: invokevirtual 154	java/io/FileOutputStream:close	()V
      //   359: goto -112 -> 247
      //   362: astore 18
      //   364: goto -117 -> 247
      //   367: astore 11
      //   369: invokestatic 122	android/support/v7/internal/widget/ActivityChooserModel:access$300	()Ljava/lang/String;
      //   372: new 124	java/lang/StringBuilder
      //   375: dup
      //   376: ldc 126
      //   378: invokespecial 129	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   381: aload_0
      //   382: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   385: invokestatic 158	android/support/v7/internal/widget/ActivityChooserModel:access$400	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Ljava/lang/String;
      //   388: invokevirtual 133	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   391: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   394: aload 11
      //   396: invokestatic 142	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   399: pop
      //   400: aload_0
      //   401: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   404: invokestatic 149	android/support/v7/internal/widget/ActivityChooserModel:access$502$7906a670	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Z
      //   407: pop
      //   408: aload 6
      //   410: ifnull -163 -> 247
      //   413: aload 6
      //   415: invokevirtual 154	java/io/FileOutputStream:close	()V
      //   418: goto -171 -> 247
      //   421: astore 14
      //   423: goto -176 -> 247
      //   426: astore 8
      //   428: aload_0
      //   429: getfield 11	android/support/v7/internal/widget/ActivityChooserModel$PersistHistoryAsyncTask:this$0	Landroid/support/v7/internal/widget/ActivityChooserModel;
      //   432: invokestatic 149	android/support/v7/internal/widget/ActivityChooserModel:access$502$7906a670	(Landroid/support/v7/internal/widget/ActivityChooserModel;)Z
      //   435: pop
      //   436: aload 6
      //   438: ifnull +8 -> 446
      //   441: aload 6
      //   443: invokevirtual 154	java/io/FileOutputStream:close	()V
      //   446: aload 8
      //   448: athrow
      //   449: astore 34
      //   451: goto -204 -> 247
      //   454: astore 10
      //   456: goto -10 -> 446
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	459	0	this	PersistHistoryAsyncTask
      //   0	459	1	paramVarArgs	Object[]
      //   6	80	2	localList	List
      //   13	184	3	str	String
      //   182	22	4	localFileNotFoundException	java.io.FileNotFoundException
      //   26	416	6	localFileOutputStream	java.io.FileOutputStream
      //   31	192	7	localXmlSerializer	org.xmlpull.v1.XmlSerializer
      //   426	21	8	localObject	Object
      //   454	1	10	localIOException1	java.io.IOException
      //   367	28	11	localIOException2	java.io.IOException
      //   421	1	14	localIOException3	java.io.IOException
      //   308	28	15	localIllegalStateException	IllegalStateException
      //   362	1	18	localIOException4	java.io.IOException
      //   249	28	19	localIllegalArgumentException	java.lang.IllegalArgumentException
      //   303	1	22	localIOException5	java.io.IOException
      //   73	10	24	i	int
      //   76	101	25	j	int
      //   95	57	26	localHistoricalRecord	ActivityChooserModel.HistoricalRecord
      //   449	1	34	localIOException6	java.io.IOException
      // Exception table:
      //   from	to	target	type
      //   14	28	182	java/io/FileNotFoundException
      //   33	75	249	java/lang/IllegalArgumentException
      //   85	176	249	java/lang/IllegalArgumentException
      //   211	229	249	java/lang/IllegalArgumentException
      //   295	300	303	java/io/IOException
      //   33	75	308	java/lang/IllegalStateException
      //   85	176	308	java/lang/IllegalStateException
      //   211	229	308	java/lang/IllegalStateException
      //   354	359	362	java/io/IOException
      //   33	75	367	java/io/IOException
      //   85	176	367	java/io/IOException
      //   211	229	367	java/io/IOException
      //   413	418	421	java/io/IOException
      //   33	75	426	finally
      //   85	176	426	finally
      //   211	229	426	finally
      //   251	282	426	finally
      //   310	341	426	finally
      //   369	400	426	finally
      //   242	247	449	java/io/IOException
      //   441	446	454	java/io/IOException
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ActivityChooserModel
 * JD-Core Version:    0.7.0.1
 */