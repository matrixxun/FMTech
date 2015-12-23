package com.google.android.finsky.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri.Builder;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Dismissal;
import com.google.android.finsky.protos.DocV2;

public class DismissRecommendationService
  extends IntentService
{
  public DismissRecommendationService()
  {
    super(DismissRecommendationService.class.getSimpleName());
  }
  
  public static PendingIntent getDismissPendingIntent(Context paramContext, int paramInt1, Document paramDocument, int paramInt2, int paramInt3)
  {
    if (!paramDocument.hasNeutralDismissal()) {
      return null;
    }
    String str = paramDocument.getNeutralDismissal().url;
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("content");
    localBuilder.authority("dismiss");
    localBuilder.appendPath(String.valueOf(paramInt1));
    localBuilder.appendPath(String.valueOf(paramInt3));
    localBuilder.appendPath(str);
    Intent localIntent = new Intent(paramContext, DismissRecommendationService.class);
    localIntent.setData(localBuilder.build());
    localIntent.putExtra("appWidgetId", paramInt1);
    localIntent.putExtra("documentId", paramDocument.mDocument.docid);
    localIntent.putExtra("dismissalUrl", str);
    localIntent.putExtra("backendId", paramInt2);
    return PendingIntent.getService(paramContext, 0, localIntent, 0);
  }
  
  /* Error */
  protected void onHandleIntent(Intent paramIntent)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 72
    //   3: iconst_0
    //   4: invokevirtual 112	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
    //   7: istore_2
    //   8: aload_1
    //   9: ldc 78
    //   11: invokevirtual 116	android/content/Intent:getStringExtra	(Ljava/lang/String;)Ljava/lang/String;
    //   14: astore_3
    //   15: aload_1
    //   16: ldc 92
    //   18: invokevirtual 116	android/content/Intent:getStringExtra	(Ljava/lang/String;)Ljava/lang/String;
    //   21: astore 4
    //   23: aload_1
    //   24: ldc 94
    //   26: iconst_3
    //   27: invokevirtual 112	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
    //   30: istore 5
    //   32: invokestatic 122	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   35: getfield 126	com/google/android/finsky/FinskyApp:mLibraries	Lcom/google/android/finsky/library/Libraries;
    //   38: astore 6
    //   40: invokestatic 122	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   43: aconst_null
    //   44: invokevirtual 130	com/google/android/finsky/FinskyApp:getDfeApi	(Ljava/lang/String;)Lcom/google/android/finsky/api/DfeApi;
    //   47: astore 7
    //   49: iload 5
    //   51: invokestatic 135	com/google/android/finsky/widget/recommendation/RecommendationsStore:getRecsWidgetUrl	(I)Ljava/lang/String;
    //   54: astore 8
    //   56: aload 7
    //   58: ifnull +11 -> 69
    //   61: aload 8
    //   63: invokestatic 141	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   66: ifeq +17 -> 83
    //   69: aload_0
    //   70: ldc 143
    //   72: iconst_1
    //   73: newarray int
    //   75: dup
    //   76: iconst_0
    //   77: iload_2
    //   78: iastore
    //   79: invokestatic 149	com/google/android/finsky/widget/BaseWidgetProvider:update	(Landroid/content/Context;Ljava/lang/Class;[I)V
    //   82: return
    //   83: aconst_null
    //   84: astore 9
    //   86: aload_0
    //   87: aload 7
    //   89: iload 5
    //   91: aload 6
    //   93: invokestatic 153	com/google/android/finsky/widget/recommendation/RecommendationsStore:getRecommendations	(Landroid/content/Context;Lcom/google/android/finsky/api/DfeApi;ILcom/google/android/finsky/library/Library;)Lcom/google/android/finsky/widget/recommendation/RecommendationList;
    //   96: astore 9
    //   98: aload 9
    //   100: getfield 159	com/google/android/finsky/widget/recommendation/RecommendationList:mRecommendations	Ljava/util/List;
    //   103: invokeinterface 165 1 0
    //   108: astore 24
    //   110: aload 24
    //   112: invokeinterface 170 1 0
    //   117: ifeq +41 -> 158
    //   120: aload 24
    //   122: invokeinterface 174 1 0
    //   127: checkcast 176	com/google/android/finsky/widget/recommendation/Recommendation
    //   130: astore 25
    //   132: aload 25
    //   134: getfield 179	com/google/android/finsky/widget/recommendation/Recommendation:mDocument	Lcom/google/android/finsky/api/model/Document;
    //   137: getfield 82	com/google/android/finsky/api/model/Document:mDocument	Lcom/google/android/finsky/protos/DocV2;
    //   140: getfield 87	com/google/android/finsky/protos/DocV2:docid	Ljava/lang/String;
    //   143: aload_3
    //   144: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   147: ifeq -37 -> 110
    //   150: aload 9
    //   152: aload 25
    //   154: invokevirtual 186	com/google/android/finsky/widget/recommendation/RecommendationList:remove	(Ljava/lang/Object;)Z
    //   157: pop
    //   158: aload 9
    //   160: aload_0
    //   161: invokevirtual 190	com/google/android/finsky/widget/recommendation/RecommendationList:writeToDisk	(Landroid/content/Context;)V
    //   164: aload_0
    //   165: iconst_1
    //   166: newarray int
    //   168: dup
    //   169: iconst_0
    //   170: iload_2
    //   171: iastore
    //   172: invokestatic 196	com/google/android/finsky/widget/recommendation/RecommendationsViewFactory:notifyDataSetChanged	(Landroid/content/Context;[I)V
    //   175: ldc 198
    //   177: iconst_2
    //   178: anewarray 200	java/lang/Object
    //   181: dup
    //   182: iconst_0
    //   183: aload_3
    //   184: aastore
    //   185: dup
    //   186: iconst_1
    //   187: aload 4
    //   189: aastore
    //   190: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   193: invokestatic 212	com/android/volley/toolbox/RequestFuture:newFuture	()Lcom/android/volley/toolbox/RequestFuture;
    //   196: astore 14
    //   198: aload 14
    //   200: aload 7
    //   202: aload 4
    //   204: aload 14
    //   206: aload 14
    //   208: invokeinterface 218 4 0
    //   213: putfield 222	com/android/volley/toolbox/RequestFuture:mRequest	Lcom/android/volley/Request;
    //   216: aload 14
    //   218: getstatic 228	com/google/android/finsky/config/G:recommendationsFetchTimeoutMs	Lcom/google/android/play/utils/config/GservicesValue;
    //   221: invokevirtual 232	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   224: checkcast 234	java/lang/Long
    //   227: invokevirtual 238	java/lang/Long:longValue	()J
    //   230: getstatic 244	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   233: invokevirtual 247	com/android/volley/toolbox/RequestFuture:get	(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;
    //   236: pop
    //   237: aload 9
    //   239: ifnull +54 -> 293
    //   242: aload 9
    //   244: invokevirtual 251	com/google/android/finsky/widget/recommendation/RecommendationList:size	()I
    //   247: getstatic 254	com/google/android/finsky/config/G:minimumNumberOfRecs	Lcom/google/android/play/utils/config/GservicesValue;
    //   250: invokevirtual 232	com/google/android/play/utils/config/GservicesValue:get	()Ljava/lang/Object;
    //   253: checkcast 256	java/lang/Integer
    //   256: invokevirtual 259	java/lang/Integer:intValue	()I
    //   259: if_icmpge +280 -> 539
    //   262: iconst_1
    //   263: istore 19
    //   265: iload 19
    //   267: ifeq +26 -> 293
    //   270: aload 7
    //   272: iload 5
    //   274: invokestatic 135	com/google/android/finsky/widget/recommendation/RecommendationsStore:getRecsWidgetUrl	(I)Ljava/lang/String;
    //   277: invokeinterface 262 2 0
    //   282: aload 7
    //   284: aload_0
    //   285: aload 9
    //   287: aload 6
    //   289: iload_2
    //   290: invokestatic 266	com/google/android/finsky/widget/recommendation/RecommendationsStore:performBackFill	(Lcom/google/android/finsky/api/DfeApi;Landroid/content/Context;Lcom/google/android/finsky/widget/recommendation/RecommendationList;Lcom/google/android/finsky/library/Library;I)V
    //   293: ldc_w 268
    //   296: iconst_1
    //   297: anewarray 200	java/lang/Object
    //   300: dup
    //   301: iconst_0
    //   302: aload 4
    //   304: aastore
    //   305: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   308: return
    //   309: astore 17
    //   311: ldc_w 270
    //   314: iconst_0
    //   315: anewarray 200	java/lang/Object
    //   318: invokestatic 273	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   321: return
    //   322: astore 22
    //   324: ldc_w 275
    //   327: iconst_1
    //   328: anewarray 200	java/lang/Object
    //   331: dup
    //   332: iconst_0
    //   333: aload 22
    //   335: aastore
    //   336: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   339: iconst_1
    //   340: anewarray 200	java/lang/Object
    //   343: astore 23
    //   345: aload 23
    //   347: iconst_0
    //   348: iload 5
    //   350: invokestatic 278	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   353: aastore
    //   354: ldc_w 280
    //   357: aload 23
    //   359: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   362: aload 7
    //   364: aload 8
    //   366: invokeinterface 262 2 0
    //   371: aload_0
    //   372: iload 5
    //   374: invokestatic 284	com/google/android/finsky/widget/recommendation/RecommendationsStore:deleteCachedRecommendations	(Landroid/content/Context;I)V
    //   377: goto -202 -> 175
    //   380: astore 20
    //   382: ldc_w 286
    //   385: iconst_1
    //   386: anewarray 200	java/lang/Object
    //   389: dup
    //   390: iconst_0
    //   391: aload 20
    //   393: aastore
    //   394: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   397: iconst_1
    //   398: anewarray 200	java/lang/Object
    //   401: astore 21
    //   403: aload 21
    //   405: iconst_0
    //   406: iload 5
    //   408: invokestatic 278	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   411: aastore
    //   412: ldc_w 280
    //   415: aload 21
    //   417: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   420: aload 7
    //   422: aload 8
    //   424: invokeinterface 262 2 0
    //   429: aload_0
    //   430: iload 5
    //   432: invokestatic 284	com/google/android/finsky/widget/recommendation/RecommendationsStore:deleteCachedRecommendations	(Landroid/content/Context;I)V
    //   435: goto -260 -> 175
    //   438: astore 12
    //   440: ldc_w 288
    //   443: iconst_1
    //   444: anewarray 200	java/lang/Object
    //   447: dup
    //   448: iconst_0
    //   449: aload 12
    //   451: aastore
    //   452: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   455: iconst_1
    //   456: anewarray 200	java/lang/Object
    //   459: astore 13
    //   461: aload 13
    //   463: iconst_0
    //   464: iload 5
    //   466: invokestatic 278	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   469: aastore
    //   470: ldc_w 280
    //   473: aload 13
    //   475: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   478: aload 7
    //   480: aload 8
    //   482: invokeinterface 262 2 0
    //   487: aload_0
    //   488: iload 5
    //   490: invokestatic 284	com/google/android/finsky/widget/recommendation/RecommendationsStore:deleteCachedRecommendations	(Landroid/content/Context;I)V
    //   493: goto -318 -> 175
    //   496: astore 10
    //   498: iconst_1
    //   499: anewarray 200	java/lang/Object
    //   502: astore 11
    //   504: aload 11
    //   506: iconst_0
    //   507: iload 5
    //   509: invokestatic 278	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   512: aastore
    //   513: ldc_w 280
    //   516: aload 11
    //   518: invokestatic 206	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   521: aload 7
    //   523: aload 8
    //   525: invokeinterface 262 2 0
    //   530: aload_0
    //   531: iload 5
    //   533: invokestatic 284	com/google/android/finsky/widget/recommendation/RecommendationsStore:deleteCachedRecommendations	(Landroid/content/Context;I)V
    //   536: aload 10
    //   538: athrow
    //   539: iconst_0
    //   540: istore 19
    //   542: goto -277 -> 265
    //   545: astore 16
    //   547: ldc_w 290
    //   550: iconst_1
    //   551: anewarray 200	java/lang/Object
    //   554: dup
    //   555: iconst_0
    //   556: aload 16
    //   558: aastore
    //   559: invokestatic 273	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   562: return
    //   563: astore 15
    //   565: ldc_w 292
    //   568: iconst_0
    //   569: anewarray 200	java/lang/Object
    //   572: invokestatic 273	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   575: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	576	0	this	DismissRecommendationService
    //   0	576	1	paramIntent	Intent
    //   7	283	2	i	int
    //   14	170	3	str1	String
    //   21	282	4	str2	String
    //   30	502	5	j	int
    //   38	250	6	localLibraries	com.google.android.finsky.library.Libraries
    //   47	475	7	localDfeApi	com.google.android.finsky.api.DfeApi
    //   54	470	8	str3	String
    //   84	202	9	localRecommendationList	com.google.android.finsky.widget.recommendation.RecommendationList
    //   496	41	10	localObject	java.lang.Object
    //   502	15	11	arrayOfObject1	java.lang.Object[]
    //   438	12	12	localTimeoutException1	java.util.concurrent.TimeoutException
    //   459	15	13	arrayOfObject2	java.lang.Object[]
    //   196	21	14	localRequestFuture	com.android.volley.toolbox.RequestFuture
    //   563	1	15	localTimeoutException2	java.util.concurrent.TimeoutException
    //   545	12	16	localExecutionException1	java.util.concurrent.ExecutionException
    //   309	1	17	localInterruptedException1	java.lang.InterruptedException
    //   263	278	19	k	int
    //   380	12	20	localInterruptedException2	java.lang.InterruptedException
    //   401	15	21	arrayOfObject3	java.lang.Object[]
    //   322	12	22	localExecutionException2	java.util.concurrent.ExecutionException
    //   343	15	23	arrayOfObject4	java.lang.Object[]
    //   108	13	24	localIterator	java.util.Iterator
    //   130	23	25	localRecommendation	com.google.android.finsky.widget.recommendation.Recommendation
    // Exception table:
    //   from	to	target	type
    //   216	237	309	java/lang/InterruptedException
    //   242	262	309	java/lang/InterruptedException
    //   270	293	309	java/lang/InterruptedException
    //   293	308	309	java/lang/InterruptedException
    //   86	110	322	java/util/concurrent/ExecutionException
    //   110	158	322	java/util/concurrent/ExecutionException
    //   158	175	322	java/util/concurrent/ExecutionException
    //   86	110	380	java/lang/InterruptedException
    //   110	158	380	java/lang/InterruptedException
    //   158	175	380	java/lang/InterruptedException
    //   86	110	438	java/util/concurrent/TimeoutException
    //   110	158	438	java/util/concurrent/TimeoutException
    //   158	175	438	java/util/concurrent/TimeoutException
    //   86	110	496	finally
    //   110	158	496	finally
    //   158	175	496	finally
    //   324	339	496	finally
    //   382	397	496	finally
    //   440	455	496	finally
    //   216	237	545	java/util/concurrent/ExecutionException
    //   242	262	545	java/util/concurrent/ExecutionException
    //   270	293	545	java/util/concurrent/ExecutionException
    //   293	308	545	java/util/concurrent/ExecutionException
    //   216	237	563	java/util/concurrent/TimeoutException
    //   242	262	563	java/util/concurrent/TimeoutException
    //   270	293	563	java/util/concurrent/TimeoutException
    //   293	308	563	java/util/concurrent/TimeoutException
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.DismissRecommendationService
 * JD-Core Version:    0.7.0.1
 */