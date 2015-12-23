package com.google.android.finsky.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Html;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ErrorDialog.Builder;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.NotificationReceiver;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.verifier.PackageVerificationService;
import com.google.android.vending.verifier.PackageWarningDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class NotificationManager
  implements Notifier
{
  private static final boolean SUPPORTS_RICH_NOTIFICATIONS;
  private static final int UNKNOWN_PACKAGE_ID = "unknown package".hashCode();
  private final Context mContext;
  private NotificationListener mListener;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_RICH_NOTIFICATIONS = bool;
      return;
    }
  }
  
  public NotificationManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static Intent createDefaultClickIntent(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(paramContext, MainActivity.class);
    if (!TextUtils.isEmpty(paramString1))
    {
      localIntent = IntentUtils.createViewDocumentUrlIntent(paramContext, paramString4);
      localIntent.putExtra("error_doc_id", paramString1);
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localIntent.putExtra("error_title", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localIntent.putExtra("error_html_message", paramString3);
    }
    return localIntent;
  }
  
  /* Error */
  private Bitmap drawableToBitmap(Drawable paramDrawable, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 39	com/google/android/finsky/utils/NotificationManager:mContext	Landroid/content/Context;
    //   6: invokevirtual 126	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   9: astore 4
    //   11: aload 4
    //   13: ldc 127
    //   15: invokevirtual 133	android/content/res/Resources:getDimensionPixelSize	(I)I
    //   18: istore 5
    //   20: aload 4
    //   22: ldc 134
    //   24: invokevirtual 133	android/content/res/Resources:getDimensionPixelSize	(I)I
    //   27: istore 6
    //   29: aload_1
    //   30: invokevirtual 139	android/graphics/drawable/Drawable:getIntrinsicWidth	()I
    //   33: istore 7
    //   35: aload_1
    //   36: invokevirtual 142	android/graphics/drawable/Drawable:getIntrinsicHeight	()I
    //   39: istore 8
    //   41: iload 7
    //   43: iload 5
    //   45: if_icmpgt +35 -> 80
    //   48: iload 8
    //   50: iload 6
    //   52: if_icmpgt +28 -> 80
    //   55: aload_1
    //   56: instanceof 144
    //   59: ifeq +21 -> 80
    //   62: aload_1
    //   63: checkcast 144	android/graphics/drawable/BitmapDrawable
    //   66: invokevirtual 148	android/graphics/drawable/BitmapDrawable:getBitmap	()Landroid/graphics/Bitmap;
    //   69: astore 15
    //   71: aload 15
    //   73: astore 13
    //   75: aload_0
    //   76: monitorexit
    //   77: aload 13
    //   79: areturn
    //   80: iconst_4
    //   81: anewarray 4	java/lang/Object
    //   84: astore 9
    //   86: aload 9
    //   88: iconst_0
    //   89: aload_2
    //   90: aastore
    //   91: aload 9
    //   93: iconst_1
    //   94: aload_1
    //   95: invokevirtual 152	java/lang/Object:getClass	()Ljava/lang/Class;
    //   98: invokevirtual 158	java/lang/Class:getName	()Ljava/lang/String;
    //   101: aastore
    //   102: aload 9
    //   104: iconst_2
    //   105: iload 7
    //   107: invokestatic 77	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   110: aastore
    //   111: aload 9
    //   113: iconst_3
    //   114: iload 8
    //   116: invokestatic 77	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   119: aastore
    //   120: ldc 160
    //   122: aload 9
    //   124: invokestatic 163	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   127: fconst_1
    //   128: iload 5
    //   130: i2f
    //   131: iload 7
    //   133: i2f
    //   134: fdiv
    //   135: iload 6
    //   137: i2f
    //   138: iload 8
    //   140: i2f
    //   141: fdiv
    //   142: invokestatic 169	java/lang/Math:min	(FF)F
    //   145: invokestatic 169	java/lang/Math:min	(FF)F
    //   148: fstore 10
    //   150: fload 10
    //   152: iload 7
    //   154: i2f
    //   155: fmul
    //   156: f2i
    //   157: istore 11
    //   159: fload 10
    //   161: iload 8
    //   163: i2f
    //   164: fmul
    //   165: f2i
    //   166: istore 12
    //   168: iload 11
    //   170: iload 12
    //   172: getstatic 175	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   175: invokestatic 181	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   178: astore 13
    //   180: new 183	android/graphics/Canvas
    //   183: dup
    //   184: aload 13
    //   186: invokespecial 186	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   189: astore 14
    //   191: aload_1
    //   192: iconst_0
    //   193: iconst_0
    //   194: iload 11
    //   196: iload 12
    //   198: invokevirtual 190	android/graphics/drawable/Drawable:setBounds	(IIII)V
    //   201: aload_1
    //   202: aload 14
    //   204: invokevirtual 194	android/graphics/drawable/Drawable:draw	(Landroid/graphics/Canvas;)V
    //   207: goto -132 -> 75
    //   210: astore_3
    //   211: aload_0
    //   212: monitorexit
    //   213: aload_3
    //   214: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	215	0	this	NotificationManager
    //   0	215	1	paramDrawable	Drawable
    //   0	215	2	paramString	String
    //   210	4	3	localObject	Object
    //   9	12	4	localResources	Resources
    //   18	111	5	i	int
    //   27	109	6	j	int
    //   33	120	7	k	int
    //   39	123	8	m	int
    //   84	39	9	arrayOfObject	Object[]
    //   148	12	10	f	float
    //   157	38	11	n	int
    //   166	31	12	i1	int
    //   73	112	13	localBitmap1	Bitmap
    //   189	14	14	localCanvas	android.graphics.Canvas
    //   69	3	15	localBitmap2	Bitmap
    // Exception table:
    //   from	to	target	type
    //   2	41	210	finally
    //   55	71	210	finally
    //   80	150	210	finally
    //   168	207	210	finally
  }
  
  private String getAppListContentText(List<Document> paramList)
  {
    Resources localResources = this.mContext.getResources();
    int i = paramList.size();
    switch (i)
    {
    default: 
      Object[] arrayOfObject6 = new Object[5];
      arrayOfObject6[0] = ((Document)paramList.get(0)).mDocument.title;
      arrayOfObject6[1] = ((Document)paramList.get(1)).mDocument.title;
      arrayOfObject6[2] = ((Document)paramList.get(2)).mDocument.title;
      arrayOfObject6[3] = ((Document)paramList.get(3)).mDocument.title;
      arrayOfObject6[4] = Integer.valueOf(i - 4);
      return localResources.getString(2131362412, arrayOfObject6);
    case 1: 
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = ((Document)paramList.get(0)).mDocument.title;
      return localResources.getString(2131363011, arrayOfObject5);
    case 2: 
      Object[] arrayOfObject4 = new Object[2];
      arrayOfObject4[0] = ((Document)paramList.get(0)).mDocument.title;
      arrayOfObject4[1] = ((Document)paramList.get(1)).mDocument.title;
      return localResources.getString(2131362408, arrayOfObject4);
    case 3: 
      Object[] arrayOfObject3 = new Object[3];
      arrayOfObject3[0] = ((Document)paramList.get(0)).mDocument.title;
      arrayOfObject3[1] = ((Document)paramList.get(1)).mDocument.title;
      arrayOfObject3[2] = ((Document)paramList.get(2)).mDocument.title;
      return localResources.getString(2131362409, arrayOfObject3);
    case 4: 
      Object[] arrayOfObject2 = new Object[4];
      arrayOfObject2[0] = ((Document)paramList.get(0)).mDocument.title;
      arrayOfObject2[1] = ((Document)paramList.get(1)).mDocument.title;
      arrayOfObject2[2] = ((Document)paramList.get(2)).mDocument.title;
      arrayOfObject2[3] = ((Document)paramList.get(3)).mDocument.title;
      return localResources.getString(2131362410, arrayOfObject2);
    }
    Object[] arrayOfObject1 = new Object[5];
    arrayOfObject1[0] = ((Document)paramList.get(0)).mDocument.title;
    arrayOfObject1[1] = ((Document)paramList.get(1)).mDocument.title;
    arrayOfObject1[2] = ((Document)paramList.get(2)).mDocument.title;
    arrayOfObject1[3] = ((Document)paramList.get(3)).mDocument.title;
    arrayOfObject1[4] = ((Document)paramList.get(4)).mDocument.title;
    return localResources.getString(2131362411, arrayOfObject1);
  }
  
  private static String getLanguageSpecificHelpUrlForError(int paramInt1, int paramInt2)
  {
    String str;
    switch (paramInt1)
    {
    default: 
      switch (paramInt2)
      {
      default: 
        str = (String)G.helpUrlForGeneralIssues.get();
      }
      break;
    }
    for (;;)
    {
      return str.replace("%lang%", Locale.getDefault().getLanguage().toLowerCase());
      str = (String)G.helpUrlForConnectionIssues.get();
      continue;
      str = (String)G.helpUrlForExternalStorageMissing.get();
      continue;
      str = (String)G.helpUrlForDownloadIssues.get();
      continue;
      str = (String)G.helpUrlForStorageIssues.get();
    }
  }
  
  private static int getNotificationId(String paramString)
  {
    if (paramString == null) {
      return UNKNOWN_PACKAGE_ID;
    }
    return paramString.hashCode();
  }
  
  private static void logNotificationImpression(int paramInt, Document paramDocument)
  {
    if (paramDocument != null) {}
    for (byte[] arrayOfByte = paramDocument.mDocument.serverLogsCookie;; arrayOfByte = null)
    {
      GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(paramInt, arrayOfByte, null, null);
      FinskyApp.get().getEventLogger().logPathImpression(0L, localGenericUiElementNode);
      return;
    }
  }
  
  private void showAppErrorMessage$2ed9a806(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    showAppMessage(paramString1, paramString2, paramString3, paramString4, 17301642, "err");
  }
  
  private void showAppMessage(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
  {
    showAppNotificationOrAlert(paramString1, paramString2, paramString3, paramString4, 17301642, -1, paramString5);
  }
  
  private void showAppNotificationOnly$1f519fb9(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5)
  {
    if ((this.mListener == null) || (this.mListener.shouldShowAppNotification$14e1ec69(paramString1)))
    {
      String str1 = paramString3;
      String str2 = paramString4;
      if (paramInt == 2)
      {
        str1 = null;
        str2 = null;
      }
      Intent localIntent = createDefaultClickIntent(this.mContext, paramString1, str1, str2, DfeUtils.createDetailsUrlFromId(paramString1));
      localIntent.putExtra("error_return_code", paramInt);
      showNotification(paramString1, paramString2, paramString3, paramString4, 17301642, localIntent, paramString5);
    }
  }
  
  private void showAppNotificationOrAlert(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, String paramString5)
  {
    if ((this.mListener == null) || (!this.mListener.showAppAlert(paramString1, paramString3, paramString4, paramInt2)))
    {
      Intent localIntent = createDefaultClickIntent(this.mContext, paramString1, paramString3, paramString4, DfeUtils.createDetailsUrlFromId(paramString1));
      localIntent.putExtra("error_return_code", paramInt2);
      showNotification(paramString1, paramString2, paramString3, paramString4, paramInt1, localIntent, paramString5);
    }
  }
  
  private void showNewNotification(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, Bitmap paramBitmap, int paramInt2, Intent paramIntent1, boolean paramBoolean, Intent paramIntent2)
  {
    showNewNotification(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramBitmap, paramInt2, paramIntent1, paramBoolean, paramIntent2, false, "status", null, null);
  }
  
  private void showNewNotification(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, Bitmap paramBitmap, int paramInt2, Intent paramIntent1, boolean paramBoolean1, Intent paramIntent2, boolean paramBoolean2, String paramString6, Intent paramIntent3, String paramString7)
  {
    showNewNotification(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramBitmap, paramInt2, paramIntent1, paramBoolean1, paramIntent2, false, paramString6, paramIntent3, paramString7, this.mContext.getResources().getColor(2131689595), -1, null);
  }
  
  private void showNewNotification(final String paramString1, String paramString2, final String paramString3, final String paramString4, String paramString5, int paramInt1, Bitmap paramBitmap, int paramInt2, final Intent paramIntent1, boolean paramBoolean1, Intent paramIntent2, boolean paramBoolean2, String paramString6, Intent paramIntent3, String paramString7, int paramInt3, int paramInt4, NotificationCompat.Action paramAction)
  {
    final NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this.mContext).setTicker(paramString2).setContentTitle(paramString3).setContentText(paramString4);
    localBuilder.mCategory = paramString6;
    localBuilder.mVisibility = 0;
    localBuilder.mLocalOnly = true;
    if (!TextUtils.isEmpty(paramString5)) {
      localBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(paramString5));
    }
    localBuilder.setSmallIcon(paramInt1);
    if (paramBitmap != null) {
      localBuilder.mLargeIcon = paramBitmap;
    }
    if (paramInt2 > 0) {
      localBuilder.mNumber = paramInt2;
    }
    localBuilder.mColor = paramInt3;
    localBuilder.mPriority = paramInt4;
    if (paramAction != null) {
      localBuilder.mActions.add(paramAction);
    }
    final int i = getNotificationId(paramString1);
    PendingIntent localPendingIntent1;
    if (!paramBoolean1)
    {
      localPendingIntent1 = PendingIntent.getActivity(this.mContext, i, paramIntent1, 1342177280);
      localBuilder.mContentIntent = localPendingIntent1;
      if (paramIntent2 != null)
      {
        PendingIntent localPendingIntent3 = PendingIntent.getBroadcast(this.mContext, i, paramIntent2, 1342177280);
        localBuilder.mNotification.deleteIntent = localPendingIntent3;
      }
      if ((paramIntent3 != null) && (!TextUtils.isEmpty(paramString7)))
      {
        PendingIntent localPendingIntent2 = PendingIntent.getBroadcast(this.mContext, i, paramIntent3, 1342177280);
        localBuilder.mActions.add(new NotificationCompat.Action(2130838134, paramString7, localPendingIntent2));
      }
      if (!paramBoolean2) {
        break label316;
      }
      localBuilder.setFlag(2, true);
    }
    for (;;)
    {
      RestrictedDeviceHelper.isLimitedOrSchoolEduUser(new RestrictedDeviceHelper.OnCompleteListener()
      {
        public final void onComplete(boolean paramAnonymousBoolean)
        {
          if (!paramAnonymousBoolean) {
            this.val$mgr.notify(i, localBuilder.build());
          }
          if (!paramAnonymousBoolean) {}
          for (boolean bool = true;; bool = false)
          {
            NotificationManager.access$100(bool, paramString1, paramString3, paramString4, paramIntent1);
            return;
          }
        }
      });
      return;
      localPendingIntent1 = PendingIntent.getBroadcast(this.mContext, i, paramIntent1, 1342177280);
      break;
      label316:
      localBuilder.setAutoCancel$7abcb88d();
    }
  }
  
  private void showStorageFullAlertOrNotification$fb5dc8d(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    if ((this.mListener != null) && (!this.mListener.shouldShowAppNotification$14e1ec69(paramString1)))
    {
      if (FinskyApp.get().getExperiments().isEnabled(12603367L))
      {
        this.mListener.showAppAlert(paramString1, paramString3, paramString4, 3);
        return;
      }
      if (paramBoolean) {}
      for (int i = 47;; i = 48)
      {
        ErrorDialog.Builder localBuilder = (ErrorDialog.Builder)((ErrorDialog.Builder)((ErrorDialog.Builder)((ErrorDialog.Builder)((ErrorDialog.Builder)((ErrorDialog.Builder)new ErrorDialog.Builder().setTitle(paramString3)).setMessageHtml(paramString4)).setPositiveId(2131362841)).setNegativeId(2131361915)).setCallback(null, i, null)).setEventLog(324, null, 2904, 2903, null);
        this.mListener.showAppAlert(paramString1, localBuilder);
        return;
      }
    }
    showAppNotificationOnly$1f519fb9(paramString1, paramString2, paramString3, paramString4, -1, "err");
  }
  
  public final void hideAllMessagesForPackage(String paramString)
  {
    android.app.NotificationManager localNotificationManager = (android.app.NotificationManager)this.mContext.getSystemService("notification");
    if (paramString == null) {}
    for (int i = UNKNOWN_PACKAGE_ID;; i = paramString.hashCode())
    {
      localNotificationManager.cancel(i);
      return;
    }
  }
  
  public final void hideInstallingMessage()
  {
    ((android.app.NotificationManager)this.mContext.getSystemService("notification")).cancel("package installing".hashCode());
  }
  
  public final void hidePackageRemoveRequestMessage(String paramString)
  {
    ((android.app.NotificationManager)this.mContext.getSystemService("notification")).cancel("package..remove..request..".concat(paramString).hashCode());
  }
  
  public final void hidePackageRemovedMessage(String paramString)
  {
    ((android.app.NotificationManager)this.mContext.getSystemService("notification")).cancel("package..removed..".concat(paramString).hashCode());
  }
  
  public final void hideUpdatesAvailableMessage()
  {
    ((android.app.NotificationManager)this.mContext.getSystemService("notification")).cancel("updates".hashCode());
  }
  
  public final void setNotificationListener(NotificationListener paramNotificationListener)
  {
    this.mListener = paramNotificationListener;
  }
  
  public final void showDownloadErrorMessage(String paramString1, String paramString2, int paramInt, String paramString3, boolean paramBoolean)
  {
    Context localContext1 = this.mContext;
    int i;
    String str;
    int j;
    label74:
    Context localContext2;
    Object[] arrayOfObject;
    switch (paramInt)
    {
    default: 
      if (paramBoolean)
      {
        i = 2131362135;
        str = localContext1.getString(i, new Object[] { paramString1 });
        if (paramString3 == null) {
          break label162;
        }
        if (!paramBoolean) {
          break label154;
        }
        j = 2131362134;
        localContext2 = this.mContext;
        arrayOfObject = new Object[3];
        arrayOfObject[0] = paramString1;
        if (paramString3 == null) {
          break label225;
        }
      }
      break;
    }
    for (;;)
    {
      arrayOfObject[1] = paramString3;
      arrayOfObject[2] = getLanguageSpecificHelpUrlForError(paramInt, 1);
      showAppErrorMessage$2ed9a806(paramString2, str, str, localContext2.getString(j, arrayOfObject));
      return;
      i = 2131362130;
      break;
      i = 2131362128;
      break;
      i = 2131362131;
      break;
      label154:
      j = 2131362126;
      break label74;
      switch (paramInt)
      {
      default: 
        if (paramBoolean) {
          j = 2131362133;
        }
        break;
      case 927: 
        j = 2131362129;
        break;
      case 944: 
        label162:
        j = 2131362127;
        break;
        j = 2131362125;
        break;
        label225:
        paramString3 = Integer.valueOf(paramInt);
      }
    }
  }
  
  public final void showExternalStorageFull(String paramString1, String paramString2)
  {
    showStorageFullAlertOrNotification$fb5dc8d(paramString2, this.mContext.getString(2131362138, new Object[] { paramString1 }), this.mContext.getString(2131362143, new Object[] { paramString1 }), this.mContext.getString(2131362139, new Object[] { paramString1 }), false);
  }
  
  public final void showExternalStorageMissing(String paramString1, String paramString2)
  {
    String str1 = this.mContext.getString(2131362140, new Object[] { paramString1 });
    String str2 = this.mContext.getString(2131362142, new Object[] { paramString1 });
    Context localContext = this.mContext;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = getLanguageSpecificHelpUrlForError(901, 2);
    showAppErrorMessage$2ed9a806(paramString2, str1, str2, localContext.getString(2131362141, arrayOfObject));
  }
  
  public final void showHarmfulAppRemoveRequestMessage(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, int paramInt)
  {
    Intent localIntent1 = PackageWarningDialog.createIntent(this.mContext, PackageWarningDialog.NO_ID, 2, paramString1, paramString2, paramString3, paramArrayOfByte, paramInt);
    String str1 = this.mContext.getString(2131362833);
    String str2 = this.mContext.getString(2131362832, new Object[] { paramString1 });
    int i = this.mContext.getResources().getColor(2131689580);
    Intent localIntent2 = PackageVerificationService.createRemovalRequestIntent$6548352b(this.mContext, paramString2, paramArrayOfByte);
    PendingIntent localPendingIntent = PendingIntent.getService(this.mContext, 0, localIntent2, 268435456);
    NotificationCompat.Action localAction = new NotificationCompat.Action(2130837703, this.mContext.getString(2131362807), localPendingIntent);
    showNewNotification("package..remove..request..".concat(paramString2), str1, str1, str2, str2, 2130837900, null, -1, localIntent1, false, null, true, "status", null, null, i, 2, localAction);
  }
  
  public final void showHarmfulAppRemovedMessage(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    Intent localIntent = PackageWarningDialog.createIntent(this.mContext, PackageWarningDialog.NO_ID, 3, paramString1, paramString2, paramString3, null, paramInt);
    String str1 = this.mContext.getString(2131362834);
    String str2 = this.mContext.getString(2131362832, new Object[] { paramString1 });
    showNewNotification("package..removed..".concat(paramString2), str1, str1, str2, str2, 2130838133, null, -1, localIntent, false, null);
  }
  
  public final void showInstallationFailureMessage(String paramString1, String paramString2, int paramInt)
  {
    int i = 2131362235;
    hideInstallingMessage();
    String str1 = this.mContext.getString(2131362236, new Object[] { paramString1 });
    String str2 = this.mContext.getString(2131362243);
    String str3 = getLanguageSpecificHelpUrlForError(paramInt, 1);
    switch (paramInt)
    {
    default: 
      i = 2131362240;
    }
    String str4;
    for (;;)
    {
      Context localContext = this.mContext;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = Integer.valueOf(paramInt);
      arrayOfObject[2] = str3;
      str4 = localContext.getString(i, arrayOfObject);
      if (paramInt != -18) {
        break;
      }
      showStorageFullAlertOrNotification$fb5dc8d(paramString2, str1, str2, str4, false);
      return;
      i = 2131362239;
      continue;
      i = 2131362242;
      continue;
      i = 2131362237;
      continue;
      i = 2131362241;
      continue;
      i = 2131362238;
      continue;
      i = 2131362244;
      continue;
      i = 2131362240;
    }
    int j = -1;
    if (paramInt == -104) {
      j = 1;
    }
    showAppNotificationOrAlert(paramString2, str1, str2, str4, 17301642, j, "err");
  }
  
  public final void showInstallingMessage(String paramString1, String paramString2, boolean paramBoolean)
  {
    Context localContext1 = this.mContext;
    int i;
    String str1;
    int j;
    label50:
    String str2;
    if (paramBoolean)
    {
      i = 2131362415;
      str1 = String.format(localContext1.getString(i), new Object[] { paramString1 });
      Context localContext2 = this.mContext;
      if (!paramBoolean) {
        break label120;
      }
      j = 2131362414;
      str2 = String.format(localContext2.getString(j), new Object[] { paramString1 });
      if (!paramBoolean) {
        break label128;
      }
    }
    label128:
    for (Intent localIntent = MainActivity.getMyDownloadsIntent(this.mContext);; localIntent = createDefaultClickIntent(this.mContext, paramString2, null, null, DfeUtils.createDetailsUrlFromId(paramString2)))
    {
      showNewNotification("package installing", str1, paramString1, str2, null, 17301633, null, 0, localIntent, false, null, false, "progress", null, null);
      return;
      i = 2131362400;
      break;
      label120:
      j = 2131362399;
      break label50;
    }
  }
  
  public final void showInternalStorageFull(String paramString1, String paramString2)
  {
    showStorageFullAlertOrNotification$fb5dc8d(paramString2, this.mContext.getString(2131362252, new Object[] { paramString1 }), this.mContext.getString(2131362254, new Object[] { paramString1 }), this.mContext.getString(2131362253, new Object[] { paramString1 }), true);
  }
  
  public final void showMaliciousAssetRemovedMessage$16da05f7(String paramString)
  {
    String str = this.mContext.getString(2131362315, new Object[] { paramString });
    this.mContext.getString(2131362317, new Object[] { paramString });
    showMessage$14e1ec6d(str, this.mContext.getString(2131362316, new Object[] { paramString }));
  }
  
  public final void showMessage$14e1ec6d(String paramString1, String paramString2)
  {
    showNotification(null, paramString1, paramString1, paramString2, 17301642, createDefaultClickIntent(this.mContext, null, paramString1, paramString2, null), "status");
  }
  
  public final void showNewUpdatesAvailableMessage(List<Document> paramList, int paramInt)
  {
    Resources localResources = this.mContext.getResources();
    int i = paramList.size();
    if (i == 0)
    {
      FinskyLog.w("App count is 0 in new updates notification", new Object[0]);
      return;
    }
    String str1 = localResources.getString(2131362402);
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(i);
    String str2 = localResources.getQuantityString(2131296263, i, arrayOfObject1);
    String str3;
    if (i == paramInt)
    {
      str3 = getAppListContentText(paramList);
      if ((!SUPPORTS_RICH_NOTIFICATIONS) || (i <= 1)) {
        break label203;
      }
    }
    label203:
    for (int j = 2130838135;; j = 2130838134)
    {
      logNotificationImpression(900, null);
      Intent localIntent1 = NotificationReceiver.getNewUpdateClickedIntent();
      String str4 = localResources.getQuantityString(2131296265, paramInt);
      Intent localIntent2 = NotificationReceiver.getUpdateAllClicked();
      showNewNotification("updates", str1, str2, str3, str3, j, null, paramInt, localIntent1, true, null, false, "status", localIntent2, str4);
      return;
      if (i < paramInt)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt);
        str3 = localResources.getString(2131362407, arrayOfObject2);
        break;
      }
      FinskyLog.w("all updates count is less than new updates notification", new Object[0]);
      return;
    }
  }
  
  public final void showNormalAssetRemovedMessage(String paramString1, String paramString2)
  {
    showAppMessage(paramString2, this.mContext.getString(2131361862, new Object[] { paramString1 }), this.mContext.getString(2131361864, new Object[] { paramString1 }), this.mContext.getString(2131361863, new Object[] { paramString1 }), 17301642, "status");
  }
  
  @SuppressLint({"NewApi"})
  public final void showNotification(final String paramString1, String paramString2, final String paramString3, String paramString4, int paramInt, final Intent paramIntent, String paramString5)
  {
    final String str = Html.fromHtml(paramString4).toString();
    final int i = getNotificationId(paramString1);
    PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, i, paramIntent, 1342177280);
    NotificationCompat.Builder localBuilder1 = new NotificationCompat.Builder(this.mContext).setSmallIcon(paramInt).setTicker(paramString2);
    long l = System.currentTimeMillis();
    localBuilder1.mNotification.when = l;
    localBuilder1.mCategory = paramString5;
    localBuilder1.mVisibility = 0;
    localBuilder1.mPriority = -1;
    NotificationCompat.Builder localBuilder2 = localBuilder1.setAutoCancel$7abcb88d();
    localBuilder2.mLocalOnly = true;
    NotificationCompat.Builder localBuilder3 = localBuilder2.setContentTitle(paramString3).setContentText(str);
    localBuilder3.mContentIntent = localPendingIntent;
    RestrictedDeviceHelper.isLimitedOrSchoolEduUser(new RestrictedDeviceHelper.OnCompleteListener()
    {
      public final void onComplete(boolean paramAnonymousBoolean)
      {
        if (!paramAnonymousBoolean) {
          ((android.app.NotificationManager)NotificationManager.this.mContext.getSystemService("notification")).notify(i, this.val$notification);
        }
        if (!paramAnonymousBoolean) {}
        for (boolean bool = true;; bool = false)
        {
          NotificationManager.access$100(bool, paramString1, paramString3, str, paramIntent);
          return;
        }
      }
    });
  }
  
  public final void showOutstandingUpdatesMessage(List<Document> paramList)
  {
    Resources localResources = this.mContext.getResources();
    int i = paramList.size();
    if (i == 0)
    {
      FinskyLog.w("App count is 0 in new outstanding updates notification", new Object[0]);
      return;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(i);
    String str1 = localResources.getQuantityString(2131296264, i, arrayOfObject);
    String str2 = getAppListContentText(paramList);
    if ((SUPPORTS_RICH_NOTIFICATIONS) && (i > 1)) {}
    for (int j = 2130838135;; j = 2130838134)
    {
      logNotificationImpression(903, null);
      Intent localIntent = NotificationReceiver.getOutstandingUpdateClickedIntent();
      String str3 = localResources.getQuantityString(2131296265, i);
      showNewNotification("updates", str1, str1, str2, str2, j, null, -1, localIntent, true, null, false, "status", NotificationReceiver.getUpdateAllClicked(), str3);
      return;
    }
  }
  
  public final void showPreregistrationReleasedMessage(Document paramDocument, String paramString, Bitmap paramBitmap)
  {
    String str1 = paramDocument.mDocument.docid;
    if (paramDocument.getAppDetails() == null)
    {
      FinskyLog.wtf("appDocument doesn't have app details (%s)", new Object[] { str1 });
      return;
    }
    logNotificationImpression(905, paramDocument);
    Intent localIntent1 = NotificationReceiver.getPreregistrationReleasedClickedIntent(str1, paramString);
    Intent localIntent2 = NotificationReceiver.getPreregistrationReleasedDeleteIntent(str1);
    Resources localResources = this.mContext.getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramDocument.mDocument.title;
    String str2 = localResources.getString(2131362404, arrayOfObject);
    String str3 = localResources.getString(2131362403);
    showNewNotification("preregistration..released..".concat(str1), str2, str2, str3, str3, 2130838132, paramBitmap, -1, localIntent1, true, localIntent2);
  }
  
  public final void showPurchaseErrorMessage(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((this.mListener == null) || (!this.mListener.showDocAlert(paramString4, paramString1, paramString3, paramString5))) {
      showNotification(paramString4, paramString2, paramString1, paramString3, 17301642, createDefaultClickIntent(this.mContext, paramString4, paramString1, paramString3, paramString5), "err");
    }
  }
  
  public final void showSubscriptionsWarningMessage(String paramString1, String paramString2, String paramString3)
  {
    showAppNotificationOnly$1f519fb9(paramString2, paramString3, paramString1, paramString3, 2, "err");
  }
  
  public final void showSuccessfulInstallMessage(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    hideInstallingMessage();
    if (paramBoolean)
    {
      hideAllMessagesForPackage(paramString2);
      String str3 = (String)FinskyPreferences.successfulUpdateNotificationAppList.get();
      String str4 = paramString1.replace('\n', ' ');
      ArrayList localArrayList;
      String str6;
      String str7;
      Resources localResources2;
      String str8;
      label265:
      Intent localIntent2;
      Intent localIntent3;
      if (TextUtils.isEmpty(str3))
      {
        localArrayList = new ArrayList();
        localArrayList.add(0, str4);
        String str5 = TextUtils.join("\n", localArrayList);
        FinskyPreferences.successfulUpdateNotificationAppList.put(str5);
        int i = localArrayList.size();
        str6 = String.format(this.mContext.getString(2131362413), new Object[] { str4 });
        Resources localResources1 = this.mContext.getResources();
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        str7 = localResources1.getQuantityString(2131296266, i, arrayOfObject1);
        localResources2 = this.mContext.getResources();
        switch (i)
        {
        default: 
          Object[] arrayOfObject6 = new Object[5];
          arrayOfObject6[0] = localArrayList.get(0);
          arrayOfObject6[1] = localArrayList.get(1);
          arrayOfObject6[2] = localArrayList.get(2);
          arrayOfObject6[3] = localArrayList.get(3);
          arrayOfObject6[4] = Integer.valueOf(i - 4);
          str8 = localResources2.getString(2131362401, arrayOfObject6);
          if (i <= 1) {
            logNotificationImpression(902, null);
          }
          localIntent2 = NotificationReceiver.getSuccessfullyUpdatedClickedIntent();
          localIntent3 = NotificationReceiver.getSuccessfullyUpdatedDeletedIntent();
          if ((!SUPPORTS_RICH_NOTIFICATIONS) || (i <= 1)) {
            break;
          }
        }
      }
      for (int j = 2130838131;; j = 2130838130)
      {
        showNewNotification("successful update", str6, str7, str8, str8, j, null, -1, localIntent2, true, localIntent3);
        return;
        localArrayList = Lists.newArrayList(str3.split("\n"));
        localArrayList.remove(str4);
        break;
        FinskyLog.w("App count is 0 in successful update notification", new Object[0]);
        return;
        str8 = (String)localArrayList.get(0);
        break label265;
        Object[] arrayOfObject5 = new Object[2];
        arrayOfObject5[0] = localArrayList.get(0);
        arrayOfObject5[1] = localArrayList.get(1);
        str8 = localResources2.getString(2131362338, arrayOfObject5);
        break label265;
        Object[] arrayOfObject4 = new Object[3];
        arrayOfObject4[0] = localArrayList.get(0);
        arrayOfObject4[1] = localArrayList.get(1);
        arrayOfObject4[2] = localArrayList.get(2);
        str8 = localResources2.getString(2131362339, arrayOfObject4);
        break label265;
        Object[] arrayOfObject3 = new Object[4];
        arrayOfObject3[0] = localArrayList.get(0);
        arrayOfObject3[1] = localArrayList.get(1);
        arrayOfObject3[2] = localArrayList.get(2);
        arrayOfObject3[3] = localArrayList.get(3);
        str8 = localResources2.getString(2131362340, arrayOfObject3);
        break label265;
        Object[] arrayOfObject2 = new Object[5];
        arrayOfObject2[0] = localArrayList.get(0);
        arrayOfObject2[1] = localArrayList.get(1);
        arrayOfObject2[2] = localArrayList.get(2);
        arrayOfObject2[3] = localArrayList.get(3);
        arrayOfObject2[4] = localArrayList.get(4);
        str8 = localResources2.getString(2131362341, arrayOfObject2);
        break label265;
      }
    }
    String str1 = String.format(this.mContext.getString(2131362398), new Object[] { paramString1 });
    String str2 = this.mContext.getString(2131362397);
    if (!TextUtils.isEmpty(paramString3)) {
      str2 = this.mContext.getString(2131362396);
    }
    logNotificationImpression(901, null);
    Intent localIntent1 = NotificationReceiver.getSuccessfullyInstalledClickedIntent(paramString2, paramString3);
    if (SUPPORTS_RICH_NOTIFICATIONS) {}
    for (;;)
    {
      try
      {
        PackageManager localPackageManager = this.mContext.getPackageManager();
        if (Build.VERSION.SDK_INT >= 22)
        {
          localObject2 = localPackageManager.getApplicationInfo(paramString2, 1024).loadUnbadgedIcon(localPackageManager);
          Object localObject3 = null;
          if (localObject2 != null)
          {
            Bitmap localBitmap = drawableToBitmap((Drawable)localObject2, paramString2);
            localObject3 = localBitmap;
          }
          localObject1 = localObject3;
          showNewNotification(paramString2, str1, paramString1, str2, null, 2130838130, localObject1, 0, localIntent1, true, null);
          return;
        }
        Drawable localDrawable = localPackageManager.getApplicationIcon(paramString2);
        Object localObject2 = localDrawable;
        continue;
        Object localObject1 = null;
      }
      catch (Exception localException) {}
    }
  }
  
  public final void showUpdatesNeedApprovalMessage(List<Document> paramList, int paramInt)
  {
    Resources localResources = this.mContext.getResources();
    int i = paramList.size();
    if (i == 0)
    {
      FinskyLog.w("App count is 0 in need approval notification", new Object[0]);
      return;
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(i);
    String str1 = localResources.getQuantityString(2131296262, i, arrayOfObject1);
    String str2;
    if (i == paramInt)
    {
      str2 = getAppListContentText(paramList);
      if ((!SUPPORTS_RICH_NOTIFICATIONS) || (i <= 1)) {
        break label194;
      }
    }
    label194:
    for (int j = 2130838135;; j = 2130838134)
    {
      logNotificationImpression(904, null);
      Intent localIntent1 = NotificationReceiver.getNewUpdateNeedApprovalClicked();
      String str3 = localResources.getQuantityString(2131296265, paramInt);
      Intent localIntent2 = NotificationReceiver.getUpdateAllClicked();
      showNewNotification("updates", str1, str1, str2, str2, j, null, -1, localIntent1, true, null, false, "status", localIntent2, str3);
      return;
      if (i < paramInt)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt);
        str2 = localResources.getString(2131362407, arrayOfObject2);
        break;
      }
      FinskyLog.w("all updates count is less than updates requiring approval notification", new Object[0]);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.NotificationManager
 * JD-Core Version:    0.7.0.1
 */