package com.google.android.finsky.activities;

import android.accounts.Account;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.appstate.SQLiteInstallerDataStore;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.HashingLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.protos.DebugSettingsResponse;
import com.google.android.finsky.receivers.FlushLogsReceiver;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.play.utils.config.PlayG;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDebugActivity
  extends BaseSettingsActivity
{
  private static final String ORIGINAL_DFE_URL = DfeApi.BASE_URI.toString();
  private static final String ORIGINAL_VENDING_API_URL = "https://android.clients.google.com/vending/api/ApiRequest".replace("api/ApiRequest", "");
  private final ICsvSelectorHelper<CarrierOverride> mCarrierHelper = new ICsvSelectorHelper() {};
  private final ICsvSelectorHelper<EnvironmentOverride> mEnvironmentHelper = new ICsvSelectorHelper() {};
  
  private <T> void buildSelectorFromCsv$3d3e9e54(final ICsvSelectorHelper<T> paramICsvSelectorHelper, String paramString1, final String paramString2, int paramInt, String paramString3)
  {
    final ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i = getOptionsFromCsv(paramString1, localArrayList1, localArrayList2, paramICsvSelectorHelper, paramString3);
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(this);
    localAlertDialogBuilderCompat.setTitle(paramInt);
    localAlertDialogBuilderCompat.setSingleChoiceItems((String[])localArrayList2.toArray(new String[localArrayList2.size()]), i, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        Toast.makeText(BaseDebugActivity.this, paramString2, 1).show();
        paramICsvSelectorHelper.selectItem(localArrayList1.get(paramAnonymousInt));
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
          public final void run()
          {
            BaseDebugActivity.this.clearCacheAndRestart(true);
          }
        }, 3000L);
      }
    });
    localAlertDialogBuilderCompat.create().show();
  }
  
  private void exportDatabase(String paramString)
  {
    try
    {
      File localFile1 = getDatabasePath(paramString);
      boolean bool1 = localFile1.exists();
      i = 0;
      if (bool1)
      {
        boolean bool2 = localFile1.canRead();
        i = 0;
        if (bool2)
        {
          File localFile2 = new File(Environment.getExternalStorageDirectory(), paramString);
          FileInputStream localFileInputStream = new FileInputStream(localFile1);
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
          FileChannel localFileChannel1 = localFileInputStream.getChannel();
          FileChannel localFileChannel2 = localFileOutputStream.getChannel();
          localFileChannel1.transferTo(0L, localFileChannel1.size(), localFileChannel2);
          localFileChannel1.close();
          localFileChannel2.close();
          localFileInputStream.close();
          localFileOutputStream.close();
          i = 1;
        }
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localIOException.getMessage();
        FinskyLog.e("Unable to export: %s", arrayOfObject);
        int i = 0;
      }
    }
    if (i == 0) {
      Toast.makeText(this, "Unable to export " + paramString, 0).show();
    }
  }
  
  /* Error */
  private <T> int getOptionsFromCsv(String paramString1, List<T> paramList, List<String> paramList1, ICsvSelectorHelper<T> paramICsvSelectorHelper, String paramString2)
  {
    // Byte code:
    //   0: aload_2
    //   1: aload 4
    //   3: aconst_null
    //   4: invokeinterface 308 2 0
    //   9: invokeinterface 312 2 0
    //   14: pop
    //   15: aload_3
    //   16: aload 5
    //   18: invokeinterface 312 2 0
    //   23: pop
    //   24: iconst_0
    //   25: istore 8
    //   27: aconst_null
    //   28: astore 9
    //   30: invokestatic 241	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   33: astore 13
    //   35: new 228	java/io/File
    //   38: dup
    //   39: aload 13
    //   41: aload_1
    //   42: invokespecial 244	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   45: astore 14
    //   47: aload 14
    //   49: invokevirtual 232	java/io/File:exists	()Z
    //   52: istore 15
    //   54: aconst_null
    //   55: astore 9
    //   57: iload 15
    //   59: ifne +25 -> 84
    //   62: new 228	java/io/File
    //   65: dup
    //   66: new 228	java/io/File
    //   69: dup
    //   70: aload 13
    //   72: ldc_w 314
    //   75: invokespecial 244	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   78: aload_1
    //   79: invokespecial 244	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   82: astore 14
    //   84: new 316	java/io/BufferedReader
    //   87: dup
    //   88: new 318	java/io/FileReader
    //   91: dup
    //   92: aload 14
    //   94: invokespecial 319	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   97: invokespecial 322	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   100: astore 16
    //   102: aload 16
    //   104: invokevirtual 325	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   107: astore 17
    //   109: aload 17
    //   111: ifnull +171 -> 282
    //   114: aload 17
    //   116: invokevirtual 328	java/lang/String:trim	()Ljava/lang/String;
    //   119: astore 18
    //   121: aload 18
    //   123: ldc_w 330
    //   126: invokevirtual 334	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   129: ifne -27 -> 102
    //   132: aload 18
    //   134: invokestatic 85	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   137: ifne -35 -> 102
    //   140: aload 18
    //   142: ldc_w 336
    //   145: invokevirtual 340	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   148: astore 19
    //   150: aload 19
    //   152: iconst_0
    //   153: aaload
    //   154: astore 20
    //   156: aload 20
    //   158: invokestatic 85	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   161: ifeq +63 -> 224
    //   164: ldc_w 342
    //   167: iconst_0
    //   168: anewarray 93	java/lang/Object
    //   171: invokestatic 345	com/google/android/finsky/utils/FinskyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   174: goto -72 -> 102
    //   177: astore 10
    //   179: aload 16
    //   181: astore 9
    //   183: iconst_1
    //   184: anewarray 93	java/lang/Object
    //   187: astore 12
    //   189: aload 12
    //   191: iconst_0
    //   192: aload 10
    //   194: invokevirtual 346	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   197: aastore
    //   198: ldc_w 348
    //   201: aload 12
    //   203: invokestatic 300	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   206: aload_0
    //   207: ldc_w 350
    //   210: iconst_1
    //   211: invokestatic 288	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
    //   214: invokevirtual 289	android/widget/Toast:show	()V
    //   217: aload 9
    //   219: invokestatic 356	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   222: iconst_0
    //   223: ireturn
    //   224: aload_3
    //   225: aload 20
    //   227: invokeinterface 312 2 0
    //   232: pop
    //   233: aload 4
    //   235: aload 19
    //   237: invokeinterface 308 2 0
    //   242: astore 22
    //   244: aload_2
    //   245: aload 22
    //   247: invokeinterface 312 2 0
    //   252: pop
    //   253: aload 4
    //   255: aload 22
    //   257: invokeinterface 359 2 0
    //   262: ifeq -160 -> 102
    //   265: aload_2
    //   266: invokeinterface 203 1 0
    //   271: istore 24
    //   273: iload 24
    //   275: iconst_1
    //   276: isub
    //   277: istore 8
    //   279: goto -177 -> 102
    //   282: aload 16
    //   284: invokestatic 356	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   287: iload 8
    //   289: ireturn
    //   290: astore 11
    //   292: aload 9
    //   294: invokestatic 356	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
    //   297: aload 11
    //   299: athrow
    //   300: astore 11
    //   302: aload 16
    //   304: astore 9
    //   306: goto -14 -> 292
    //   309: astore 10
    //   311: aconst_null
    //   312: astore 9
    //   314: goto -131 -> 183
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	317	0	this	BaseDebugActivity
    //   0	317	1	paramString1	String
    //   0	317	2	paramList	List<T>
    //   0	317	3	paramList1	List<String>
    //   0	317	4	paramICsvSelectorHelper	ICsvSelectorHelper<T>
    //   0	317	5	paramString2	String
    //   25	263	8	i	int
    //   28	285	9	localObject1	Object
    //   177	16	10	localException1	java.lang.Exception
    //   309	1	10	localException2	java.lang.Exception
    //   290	8	11	localObject2	Object
    //   300	1	11	localObject3	Object
    //   187	15	12	arrayOfObject	Object[]
    //   33	38	13	localFile1	File
    //   45	48	14	localFile2	File
    //   52	6	15	bool	boolean
    //   100	203	16	localBufferedReader	java.io.BufferedReader
    //   107	8	17	str1	String
    //   119	22	18	str2	String
    //   148	88	19	arrayOfString	String[]
    //   154	72	20	str3	String
    //   242	14	22	localObject4	Object
    //   271	6	24	j	int
    // Exception table:
    //   from	to	target	type
    //   102	109	177	java/lang/Exception
    //   114	174	177	java/lang/Exception
    //   224	273	177	java/lang/Exception
    //   30	54	290	finally
    //   62	84	290	finally
    //   84	102	290	finally
    //   183	217	290	finally
    //   102	109	300	finally
    //   114	174	300	finally
    //   224	273	300	finally
    //   30	54	309	java/lang/Exception
    //   62	84	309	java/lang/Exception
    //   84	102	309	java/lang/Exception
  }
  
  private void override(GservicesValue<? extends Object> paramGservicesValue, String paramString)
  {
    Intent localIntent = new Intent("com.google.gservices.intent.action.GSERVICES_OVERRIDE");
    localIntent.putExtra(paramGservicesValue.mKey, paramString);
    sendBroadcast(localIntent);
  }
  
  private void selectCarrier(CarrierOverride paramCarrierOverride)
  {
    override(DfeApiConfig.ipCountryOverride, paramCarrierOverride.countryCode);
    override(DfeApiConfig.mccMncOverride, paramCarrierOverride.simCode);
  }
  
  private void selectEnvironment(EnvironmentOverride paramEnvironmentOverride)
  {
    Intent localIntent = new Intent("com.google.gservices.intent.action.GSERVICES_OVERRIDE");
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if (paramEnvironmentOverride != null)
    {
      if (paramEnvironmentOverride.dfeBaseUrl != null) {
        break label93;
      }
      str1 = null;
      if (paramEnvironmentOverride.vendingBaseUrl != null) {
        break label126;
      }
    }
    label93:
    label126:
    for (str2 = null;; str2 = ORIGINAL_VENDING_API_URL + " rewrite " + paramEnvironmentOverride.vendingBaseUrl)
    {
      str3 = paramEnvironmentOverride.escrowUrl;
      localIntent.putExtra("url:finsky_dfe_url", str1);
      localIntent.putExtra("url:vending_machine_ssl_url", str2);
      localIntent.putExtra("url:licensing_url", str2);
      localIntent.putExtra("finsky.wallet_escrow_url", str3);
      sendBroadcast(localIntent);
      return;
      str1 = ORIGINAL_DFE_URL + " rewrite " + paramEnvironmentOverride.dfeBaseUrl;
      break;
    }
  }
  
  protected final void clearCacheAndRestart(final boolean paramBoolean)
  {
    FinskyApp.get().clearCacheAsync(new Runnable()
    {
      public final void run()
      {
        Intent localIntent = BaseDebugActivity.this.getBaseContext().getPackageManager().getLaunchIntentForPackage(BaseDebugActivity.this.getBaseContext().getPackageName()).addFlags(67108864).addFlags(32768).addFlags(268435456).addCategory("android.intent.category.HOME");
        if (paramBoolean)
        {
          BaseDebugActivity.this.startActivityForResult(localIntent, 1);
          return;
        }
        BaseDebugActivity.this.startActivityForResult(localIntent, 0);
      }
    });
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default: 
      return;
    }
    FinskyApp.get().mLibraryReplicators.replicateAllAccounts(new Runnable()
    {
      public final void run()
      {
        FinskyLog.d("Replicated libraries for all accounts.", new Object[0]);
      }
    }, "DebugActivity");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!((Boolean)G.debugOptionsEnabled.get()).booleanValue())
    {
      finish();
      return;
    }
    addPreferencesFromResource(2131165185);
    ((CheckBoxPreference)findPreference("image_debugging")).setChecked(((Boolean)PlayG.debugImageSizes.get()).booleanValue());
    ((CheckBoxPreference)findPreference("skip_all_caches")).setChecked(((Boolean)DfeApiConfig.skipAllCaches.get()).booleanValue());
    ((CheckBoxPreference)findPreference("show_staging_data")).setChecked(((Boolean)DfeApiConfig.showStagingData.get()).booleanValue());
    ((CheckBoxPreference)findPreference("disable_personalization")).setChecked(((Boolean)DfeApiConfig.prexDisabled.get()).booleanValue());
    CheckBoxPreference localCheckBoxPreference = (CheckBoxPreference)findPreference("verbose_volley_logging");
    localCheckBoxPreference.setChecked(Log.isLoggable("Volley", 2));
    localCheckBoxPreference.setEnabled(false);
    ((CheckBoxPreference)findPreference("fake_item_rater")).setChecked(((Boolean)FinskyPreferences.internalFakeItemRaterEnabled.get()).booleanValue());
    ((CheckBoxPreference)findPreference("show_all_surveys")).setChecked(((Boolean)FinskyPreferences.internalShowAllSurveys.get()).booleanValue());
    getListView().setCacheColorHint(getResources().getColor(2131689637));
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    String str1 = paramPreference.getKey();
    if ("image_debugging".equals(str1))
    {
      GservicesValue localGservicesValue4 = PlayG.debugImageSizes;
      if (!((Boolean)PlayG.debugImageSizes.get()).booleanValue()) {}
      for (boolean bool6 = true;; bool6 = false)
      {
        override(localGservicesValue4, Boolean.toString(bool6).toLowerCase());
        return true;
      }
    }
    boolean bool5;
    if ("skip_all_caches".equals(str1))
    {
      GservicesValue localGservicesValue3 = DfeApiConfig.skipAllCaches;
      if (!((Boolean)DfeApiConfig.skipAllCaches.get()).booleanValue())
      {
        bool5 = true;
        override(localGservicesValue3, Boolean.toString(bool5).toLowerCase());
      }
    }
    for (;;)
    {
      return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
      bool5 = false;
      break;
      if ("environments".equals(str1))
      {
        buildSelectorFromCsv$3d3e9e54(this.mEnvironmentHelper, "marketenvs.csv", "Switching environment...", 2131363018, "Default");
        return true;
      }
      if ("clear_cache".equals(str1))
      {
        clearCacheAndRestart(false);
        return true;
      }
      if ("show_staging_data".equals(str1))
      {
        GservicesValue localGservicesValue2 = DfeApiConfig.showStagingData;
        if (!((Boolean)DfeApiConfig.showStagingData.get()).booleanValue()) {}
        for (boolean bool4 = true;; bool4 = false)
        {
          override(localGservicesValue2, Boolean.toString(bool4).toLowerCase());
          clearCacheAndRestart(false);
          return true;
        }
      }
      if ("disable_personalization".equals(str1))
      {
        GservicesValue localGservicesValue1 = DfeApiConfig.prexDisabled;
        if (!((Boolean)DfeApiConfig.prexDisabled.get()).booleanValue()) {}
        for (boolean bool3 = true;; bool3 = false)
        {
          override(localGservicesValue1, Boolean.toString(bool3));
          clearCacheAndRestart(false);
          return true;
        }
      }
      if ("reset_all".equals(str1))
      {
        override(PlayG.debugImageSizes, "false");
        selectEnvironment(new EnvironmentOverride(null, null, null));
        selectCarrier(new CarrierOverride(null, null, null));
        clearCacheAndRestart(false);
        return true;
      }
      if ("country".equals(str1))
      {
        buildSelectorFromCsv$3d3e9e54(this.mCarrierHelper, "carriers.csv", "Switching country...", 2131363017, "Default");
        return true;
      }
      if ("play_country".equals(str1))
      {
        FinskyApp.get().getDfeApi(null).debugSettings(null, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
        {
          public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
          {
            String str = BaseDebugActivity.this.getString(2131362964);
            Toast.makeText(BaseDebugActivity.this, str, 1).show();
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = str;
            arrayOfObject[1] = paramAnonymousVolleyError.getMessage();
            FinskyLog.d("%s Volley error message: %s", arrayOfObject);
          }
        });
        return true;
      }
      if ("export_library".equals(str1))
      {
        exportDatabase(SQLiteInstallerDataStore.getDatabaseName(null));
        int i = 1;
        String[] arrayOfString = SQLiteInstallerDataStore.getDatabaseNodes(this);
        int j = arrayOfString.length;
        for (int k = 0; k < j; k++)
        {
          exportDatabase(SQLiteInstallerDataStore.getDatabaseName(arrayOfString[k]));
          i++;
        }
        exportDatabase("library.db");
        int m = i + 1;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(m);
        Toast.makeText(this, String.format("Finished %d database exports", arrayOfObject), 0).show();
        return true;
      }
      if ("dump_library_state".equals(str1))
      {
        Log.d("FinskyLibrary", "------------ LIBRARY DUMP BEGIN");
        Libraries localLibraries = FinskyApp.get().mLibraries;
        Log.d("FinskyLibrary", "| Libraries {");
        Iterator localIterator1 = localLibraries.mLibraries.values().iterator();
        while (localIterator1.hasNext())
        {
          AccountLibrary localAccountLibrary = (AccountLibrary)localIterator1.next();
          String str2 = FinskyLog.scrubPii(localAccountLibrary.mAccount.name);
          Log.d("FinskyLibrary", "|   " + "AccountLibrary (account=" + str2 + ") {");
          Iterator localIterator2 = localAccountLibrary.mLibraries.keySet().iterator();
          while (localIterator2.hasNext())
          {
            String str3 = (String)localIterator2.next();
            ((HashingLibrary)localAccountLibrary.mLibraries.get(str3)).dumpState("library=" + str3, "|   " + "  ");
          }
          Log.d("FinskyLibrary", "|   " + "} (account=" + str2 + ")");
        }
        Log.d("FinskyLibrary", "| }");
        FinskyApp.get().mLibraryReplicators.dumpState();
        Log.d("FinskyLibrary", "------------ LIBRARY DUMP END");
        Toast.makeText(this, "Library state dumped to logcat.", 0).show();
        return true;
      }
      if ("fake_item_rater".equals(str1))
      {
        PreferenceFile.SharedPreference localSharedPreference2 = FinskyPreferences.internalFakeItemRaterEnabled;
        if (!((Boolean)FinskyPreferences.internalFakeItemRaterEnabled.get()).booleanValue()) {}
        for (boolean bool2 = true;; bool2 = false)
        {
          localSharedPreference2.put(Boolean.valueOf(bool2));
          break;
        }
      }
      if ("show_all_surveys".equals(str1))
      {
        PreferenceFile.SharedPreference localSharedPreference1 = FinskyPreferences.internalShowAllSurveys;
        if (!((Boolean)FinskyPreferences.internalShowAllSurveys.get()).booleanValue()) {}
        for (boolean bool1 = true;; bool1 = false)
        {
          localSharedPreference1.put(Boolean.valueOf(bool1));
          break;
        }
      }
      if ("flush_eventlog".equals(str1))
      {
        FlushLogsReceiver.scheduleLogsFlushImmediate();
        Toast.makeText(this, "Flushing event logs", 0).show();
      }
      else if ("force_refresh_user_settings".equals(str1))
      {
        UserSettingsCache.updateUserSettingsForAllAccounts();
        Toast.makeText(this, "Refreshing user settings...", 0).show();
      }
    }
  }
  
  private static final class CarrierOverride
  {
    public final String countryCode;
    public final String countryName;
    public final String simCode;
    
    public CarrierOverride(String paramString1, String paramString2, String paramString3)
    {
      this.countryName = paramString1;
      this.countryCode = paramString2;
      this.simCode = paramString3;
    }
  }
  
  private static final class EnvironmentOverride
  {
    public final String dfeBaseUrl;
    public final String escrowUrl;
    public final String vendingBaseUrl;
    
    public EnvironmentOverride(String paramString1, String paramString2, String paramString3)
    {
      this.dfeBaseUrl = paramString1;
      this.vendingBaseUrl = paramString2;
      this.escrowUrl = paramString3;
    }
  }
  
  public static abstract interface ICsvSelectorHelper<T>
  {
    public abstract T fromStringList(String[] paramArrayOfString);
    
    public abstract boolean isSelected(T paramT);
    
    public abstract boolean selectItem(T paramT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.BaseDebugActivity
 * JD-Core Version:    0.7.0.1
 */