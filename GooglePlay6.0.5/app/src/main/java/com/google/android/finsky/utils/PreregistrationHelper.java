package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.activities.PreregistrationDialog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiProvider;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiBulkPreregistrationDetails;
import com.google.android.finsky.api.model.MultiBulkPreregistrationDetails.DocumentAndAccounts;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.ModifyLibrary.ModifyLibraryResponse;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class PreregistrationHelper
{
  static final long EXPIRE_AFTER_ACKNOWLEDGE_MS = ((Long)G.preregistrationExpireAfterAcknowledgeMs.get()).longValue();
  private final DfeApiProvider mDfeApiProvider;
  public final Libraries mLibraries;
  private final LibraryReplicators mLibraryReplicators;
  final PackageStateRepository mPackageStateRepository;
  public List<PreregistrationStatusListener> mPreregistrationStatusListeners = new ArrayList();
  final TimeProvider mTimeProvider;
  
  public PreregistrationHelper(DfeApiProvider paramDfeApiProvider, Libraries paramLibraries, LibraryReplicators paramLibraryReplicators, PackageStateRepository paramPackageStateRepository, TimeProvider paramTimeProvider)
  {
    this.mDfeApiProvider = paramDfeApiProvider;
    this.mLibraries = paramLibraries;
    this.mLibraryReplicators = paramLibraryReplicators;
    this.mPackageStateRepository = paramPackageStateRepository;
    this.mTimeProvider = paramTimeProvider;
  }
  
  private void invokePreregistrationStatusListeners$44c588bf(String paramString)
  {
    for (int i = -1 + this.mPreregistrationStatusListeners.size(); i >= 0; i--) {
      ((PreregistrationStatusListener)this.mPreregistrationStatusListeners.get(i)).onPreregistrationStatusChanged$44c588bf(paramString);
    }
  }
  
  private void processPreregistration(final String paramString1, final String paramString2, DfeApi paramDfeApi, boolean paramBoolean, final Context paramContext)
  {
    final Account localAccount = paramDfeApi.getAccount();
    final boolean bool = isPreregistered(paramString1, localAccount);
    if (paramBoolean == bool) {
      return;
    }
    Response.Listener local1 = new Response.Listener() {};
    Response.ErrorListener local2 = new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        int i;
        if (bool)
        {
          i = 2131362599;
          if (!TextUtils.isEmpty(paramString2))
          {
            Resources localResources = paramContext.getResources();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = paramString2;
            String str = localResources.getString(i, arrayOfObject);
            Toast.makeText(paramContext, str, 1).show();
          }
          if (!bool) {
            break label106;
          }
          FinskyLog.e("Unable to remove from preregistration: %s", new Object[] { paramAnonymousVolleyError });
        }
        for (;;)
        {
          PreregistrationHelper.access$000$26dadc25(PreregistrationHelper.this, paramString1, bool);
          return;
          i = 2131362591;
          break;
          label106:
          FinskyLog.e("Unable to preregister: %s", new Object[] { paramAnonymousVolleyError });
        }
      }
    };
    if (bool) {
      paramDfeApi.removeFromLibrary(Lists.newArrayList(new String[] { paramString1 }), "u-pl", local1, local2);
    }
    for (;;)
    {
      invokePreregistrationStatusListeners$44c588bf(paramString1);
      return;
      paramDfeApi.addToLibrary(Lists.newArrayList(new String[] { paramString1 }), "u-pl", local1, local2);
    }
  }
  
  public final void acknowledgeNewRelease(String paramString)
  {
    long l = this.mTimeProvider.currentTimeMillis();
    FinskyPreferences.preregistrationAcknowledgedMs.get(paramString).put(Long.valueOf(l));
    ArrayList localArrayList = Lists.newArrayList(Utils.commaUnpackStrings((String)FinskyPreferences.preregistrationAcknowledgedDocs.get()));
    if (!localArrayList.contains(paramString))
    {
      localArrayList.add(paramString);
      FinskyPreferences.preregistrationAcknowledgedDocs.put(Utils.commaPackStrings(localArrayList));
    }
  }
  
  public final boolean isPreregistered(String paramString, Account paramAccount)
  {
    LibraryEntry localLibraryEntry = new LibraryEntry(paramAccount.name, "u-pl", 3, paramString, 1, 1);
    return this.mLibraries.getAccountLibrary(paramAccount).contains(localLibraryEntry);
  }
  
  public final void processPreregistration(Document paramDocument, DfeApi paramDfeApi, boolean paramBoolean, FragmentManager paramFragmentManager, Context paramContext)
  {
    processPreregistration(paramDocument.mDocument.docid, paramDocument.mDocument.title, paramDfeApi, paramBoolean, paramContext);
    if ((paramBoolean) && (paramFragmentManager != null)) {
      PreregistrationDialog.show(paramDocument, paramFragmentManager);
    }
  }
  
  final void removeFromPreregistration(String paramString, List<String> paramList, Context paramContext)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      DfeApi localDfeApi = this.mDfeApiProvider.getDfeApi(str);
      if (localDfeApi != null) {
        processPreregistration(paramString, null, localDfeApi, false, paramContext);
      }
    }
  }
  
  public static abstract interface PreregistrationHygieneListener
  {
    public abstract void onFoundNewRelease(Document paramDocument, String paramString);
    
    public abstract void onPreregistrationHygieneFinished(boolean paramBoolean);
  }
  
  public static abstract interface PreregistrationStatusListener
  {
    public abstract void onPreregistrationStatusChanged$44c588bf(String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PreregistrationHelper
 * JD-Core Version:    0.7.0.1
 */