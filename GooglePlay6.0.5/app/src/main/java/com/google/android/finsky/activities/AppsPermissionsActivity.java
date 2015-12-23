package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.billing.DownloadSizeDialogFragment;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.layout.AppPermissionAdapter;
import com.google.android.finsky.layout.AppSecurityPermissions;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.DetailedPermissionsAdapter;
import com.google.android.finsky.layout.PermissionAdapter;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.local.AssetUtils;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.FlushLogsReceiver;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FastHtmlParser;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.PermissionsBucketer;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.utils.config.GservicesValue;

public class AppsPermissionsActivity
  extends AppCompatActivity
  implements View.OnClickListener, Response.ErrorListener, SimpleAlertDialog.Listener, OnDataChangedListener, RootUiElementNode
{
  private String mAccountName;
  private PlayActionButton mContinueButton;
  private DfeDetails mDfeDetails;
  private Document mDoc;
  private FinskyEventLog mEventLog;
  private AppSecurityPermissions mPermissionsView;
  private Bundle mSavedInstanceState;
  private boolean mShowDetailedPermissions;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(790);
  
  public static Intent createIntent(String paramString1, String paramString2, Document paramDocument, boolean paramBoolean)
  {
    Intent localIntent = new Intent(FinskyApp.get(), AppsPermissionsActivity.class);
    localIntent.putExtra("AppsPermissionsActivity.accountName", paramString1);
    localIntent.putExtra("AppsPermissionsActivity.docidStr", paramString2);
    localIntent.putExtra("AppsPermissionsActivity.doc", paramDocument);
    localIntent.putExtra("AppsPermissionsActivity.showDetailedPermissions", paramBoolean);
    return localIntent;
  }
  
  public static boolean extractAcceptedNewBuckets(Intent paramIntent)
  {
    return paramIntent.getBooleanExtra("AppsPermissionsActivity.acceptedNewBuckets", false);
  }
  
  public static Document extractDoc(Intent paramIntent)
  {
    return (Document)paramIntent.getParcelableExtra("AppsPermissionsActivity.doc");
  }
  
  public static String extractTitle(Intent paramIntent)
  {
    return paramIntent.getStringExtra("AppsPermissionsActivity.appTitle");
  }
  
  public static int extractVersionCode(Intent paramIntent)
  {
    return paramIntent.getIntExtra("AppsPermissionsActivity.appVersion", 0);
  }
  
  private void finishWithResultOK(boolean paramBoolean)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("AppsPermissionsActivity.doc", this.mDoc);
    localIntent.putExtra("AppsPermissionsActivity.appVersion", this.mDoc.getAppDetails().versionCode);
    localIntent.putExtra("AppsPermissionsActivity.appTitle", this.mDoc.mDocument.title);
    InstallPolicies localInstallPolicies = FinskyApp.get().mInstallPolicies;
    long l1 = localInstallPolicies.mMaxBytesOverMobileRecommended;
    long l2 = localInstallPolicies.mMaxBytesOverMobile;
    AppDetails localAppDetails = this.mDoc.getAppDetails();
    boolean bool;
    if ((localAppDetails != null) && (localInstallPolicies.hasMobileNetwork()) && (l1 > 0L) && (AssetUtils.totalDeliverySize(localAppDetails) >= l1)) {
      if (this.mDoc.getAppDetails().installationSize < l2) {
        bool = true;
      }
    }
    for (Bundle localBundle = DownloadSizeDialogFragment.makeArguments$409d583d(bool, localInstallPolicies.isMobileNetwork());; localBundle = null)
    {
      localIntent.putExtra("AppsPermissionsActivity.appDownloadSizeWarningArguments", localBundle);
      localIntent.putExtra("AppsPermissionsActivity.acceptedNewBuckets", true);
      setResult(-1, localIntent);
      finish();
      return;
      bool = false;
      break;
    }
  }
  
  public static Bundle getDownloadSizeWarningArguments(Intent paramIntent)
  {
    return paramIntent.getBundleExtra("AppsPermissionsActivity.appDownloadSizeWarningArguments");
  }
  
  private void showError(String paramString)
  {
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessage(paramString).setPositiveId(2131362418);
    localBuilder.build().show(getSupportFragmentManager(), "AppsPermissionsActivity.errorDialog");
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public void finish()
  {
    if (this.mEventLog != null) {
      this.mEventLog.logPathImpression$7d139cbf(603, this);
    }
    super.finish();
  }
  
  public final void flushImpression()
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public void onClick(View paramView)
  {
    this.mEventLog.logClickEvent(792, null, this);
    finishWithResultOK(true);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mSavedInstanceState = paramBundle;
    super.onCreate(paramBundle);
    setContentView(2130968806);
    Intent localIntent = getIntent();
    this.mAccountName = localIntent.getStringExtra("AppsPermissionsActivity.accountName");
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccountName);
    this.mShowDetailedPermissions = localIntent.getBooleanExtra("AppsPermissionsActivity.showDetailedPermissions", false);
    String str = localIntent.getStringExtra("AppsPermissionsActivity.docidStr");
    this.mDoc = ((Document)localIntent.getParcelableExtra("AppsPermissionsActivity.doc"));
    this.mPermissionsView = ((AppSecurityPermissions)findViewById(2131755615));
    if (this.mDoc != null) {
      FinskyEventLog.setServerLogCookie(this.mUiElement, this.mDoc.mDocument.serverLogsCookie);
    }
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this);
    }
    findViewById(2131755613).setVisibility(8);
    findViewById(2131755289).setVisibility(0);
    this.mEventLog.logPathImpression$7d139cbf(213, this);
    this.mDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(this.mAccountName), DfeUtils.createDetailsUrlFromId(str));
    this.mDfeDetails.addDataChangedListener(this);
    this.mDfeDetails.addErrorListener(this);
  }
  
  public final void onDataChanged()
  {
    this.mDoc = this.mDfeDetails.getDocument();
    if (this.mDoc == null)
    {
      showError(getString(2131362282));
      return;
    }
    if (AppActionAnalyzer.isMultiUserCertificateConflict(this.mDoc))
    {
      showError(getString(2131361842));
      return;
    }
    if (!this.mShowDetailedPermissions)
    {
      Account localAccount = AccountHandler.findAccount(this.mAccountName, this);
      AccountLibrary localAccountLibrary = FinskyApp.get().mLibraries.getAccountLibrary(localAccount);
      if (!LibraryUtils.isAvailable(this.mDoc, FinskyApp.get().mToc, localAccountLibrary))
      {
        showError(getString(DocUtils.getAvailabilityRestrictionResourceId(this.mDoc)));
        return;
      }
      if ((Build.VERSION.SDK_INT > 22) && (this.mDoc.getTargetSdk() > 22))
      {
        if (((Boolean)FinskyPreferences.optionalPermissionsHelpScreenShown.get()).booleanValue())
        {
          finishWithResultOK(true);
          return;
        }
        findViewById(2131755289).setVisibility(8);
        findViewById(2131755613).setVisibility(8);
        ViewGroup localViewGroup = (ViewGroup)((ViewStub)findViewById(2131755624)).inflate();
        ButtonBar localButtonBar = (ButtonBar)localViewGroup.findViewById(2131755300);
        localButtonBar.setNegativeButtonVisible(false);
        localButtonBar.setPositiveButtonEnabled(true);
        localButtonBar.setPositiveButtonVisible(true);
        localButtonBar.setPositiveButtonTitle(2131362062);
        localButtonBar.setClickListener(new ButtonBar.ClickListener()
        {
          public final void onNegativeButtonClick() {}
          
          public final void onPositiveButtonClick()
          {
            FinskyPreferences.optionalPermissionsHelpScreenShown.put(Boolean.valueOf(true));
            AppsPermissionsActivity.access$000$3bb91820(AppsPermissionsActivity.this);
          }
        });
        String str4 = (String)G.optionalPermissionsLearnMoreLink.get();
        TextView localTextView3 = (TextView)localViewGroup.findViewById(2131755793);
        localTextView3.setText(FastHtmlParser.fromHtml(getResources().getString(2131362460, new Object[] { str4 })));
        localTextView3.setMovementMethod(LinkMovementMethod.getInstance());
        return;
      }
    }
    findViewById(2131755613).setVisibility(0);
    findViewById(2131755289).setVisibility(8);
    String str1 = this.mDoc.mDocument.backendDocid;
    String[] arrayOfString = this.mDoc.getAppDetails().permission;
    ((TextView)findViewById(2131755173)).setText(this.mDoc.mDocument.title);
    TextView localTextView1 = (TextView)findViewById(2131755621);
    localTextView1.setVisibility(0);
    FifeImageView localFifeImageView = (FifeImageView)findViewById(2131755610);
    Common.Image localImage = ThumbnailUtils.getImageFromDocument(this.mDoc, localFifeImageView.getWidth(), localFifeImageView.getHeight(), PlayCardImageTypeSequence.CORE_IMAGE_TYPES);
    TextView localTextView2;
    final String str2;
    int j;
    label540:
    label618:
    boolean bool2;
    if (localImage != null)
    {
      localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      localFifeImageView.setVisibility(0);
      localTextView2 = (TextView)findViewById(2131755623);
      if (!this.mShowDetailedPermissions) {
        break label840;
      }
      if (this.mSavedInstanceState == null)
      {
        FinskyEventLog.setServerLogCookie(this.mUiElement, this.mDoc.mDocument.serverLogsCookie);
        this.mEventLog.logPathImpression$7d139cbf(793, this);
      }
      if (Build.VERSION.SDK_INT <= 22) {
        break label779;
      }
      str2 = (String)G.optionalPermissionBucketsLearnMoreLink.get();
      j = 2131362079;
      if (TextUtils.isEmpty(str2)) {
        break label798;
      }
      Resources localResources = getResources();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.mDoc.mDocument.title;
      arrayOfObject[1] = str2;
      localTextView2.setText(Html.fromHtml(localResources.getString(j, arrayOfObject)));
      localTextView2.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          AppsPermissionsActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str2)));
        }
      });
      localTextView2.setVisibility(0);
      if ((Build.VERSION.SDK_INT <= 22) || (this.mDoc.getTargetSdk() <= 22)) {
        break label808;
      }
      bool2 = true;
      label641:
      if ((Build.VERSION.SDK_INT <= 22) || (!((Boolean)G.alwaysUseOptionalPermissionBucketsM.get()).booleanValue())) {
        break label982;
      }
    }
    label779:
    label798:
    label808:
    label840:
    label982:
    for (boolean bool3 = true;; bool3 = bool2)
    {
      DetailedPermissionsAdapter localDetailedPermissionsAdapter = new DetailedPermissionsAdapter(this, str1, arrayOfString, bool3);
      String str3 = this.mDoc.getAppDetails().versionString;
      if (bool2) {
        localTextView1.setText(getResources().getString(2131362837, new Object[] { str3 }));
      }
      Object localObject;
      for (;;)
      {
        findViewById(2131755629).setVisibility(8);
        localObject = localDetailedPermissionsAdapter;
        this.mPermissionsView.bindInfo((PermissionAdapter)localObject, this.mDoc.mDocument.title, this.mSavedInstanceState);
        this.mPermissionsView.requestFocus();
        return;
        localFifeImageView.setVisibility(8);
        break;
        str2 = (String)G.permissionBucketsLearnMoreLink.get();
        j = 2131362080;
        break label540;
        localTextView2.setVisibility(8);
        break label618;
        bool2 = false;
        break label641;
        localTextView1.setText(getResources().getString(2131362836, new Object[] { str3 }));
      }
      if (this.mSavedInstanceState == null)
      {
        FinskyEventLog.setServerLogCookie(this.mUiElement, this.mDoc.mDocument.serverLogsCookie);
        this.mEventLog.logPathImpression$7d139cbf(791, this);
      }
      localTextView2.setVisibility(8);
      boolean bool1 = PermissionsBucketer.hasAcceptedBuckets(FinskyApp.get().mInstallerDataStore, str1);
      AppPermissionAdapter localAppPermissionAdapter = new AppPermissionAdapter(this, str1, arrayOfString, bool1);
      if ((localAppPermissionAdapter.mIsAppInstalled) && (bool1)) {}
      for (int i = 2131361840;; i = 2131362361)
      {
        localTextView1.setText(i);
        this.mContinueButton = ((PlayActionButton)findViewById(2131755631));
        this.mContinueButton.configure(3, 2131361811, this);
        this.mContinueButton.setEnabled(true);
        localObject = localAppPermissionAdapter;
        break;
      }
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    showError(ErrorStrings.get(this, paramVolleyError));
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  protected void onPause()
  {
    super.onPause();
    FlushLogsReceiver.scheduleLogsFlushOnAppExit();
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    setResult(0);
    finish();
  }
  
  protected void onResume()
  {
    super.onResume();
    FlushLogsReceiver.cancelLogsFlush();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mPermissionsView.saveInstanceState(paramBundle);
  }
  
  protected void onStart()
  {
    super.onStart();
    if (this.mDfeDetails != null)
    {
      this.mDfeDetails.addDataChangedListener(this);
      this.mDfeDetails.addErrorListener(this);
    }
  }
  
  protected void onStop()
  {
    if (this.mDfeDetails != null)
    {
      this.mDfeDetails.removeDataChangedListener(this);
      this.mDfeDetails.removeErrorListener(this);
    }
    super.onStop();
  }
  
  public final void startNewImpression()
  {
    FinskyLog.wtf("Not using impression id's.", new Object[0]);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AppsPermissionsActivity
 * JD-Core Version:    0.7.0.1
 */