package com.google.android.finsky.setup;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Restore.BackupDeviceInfo;
import com.google.android.finsky.protos.Restore.BackupDocumentInfo;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SetupWizardRestoreAppsActivity
  extends FragmentActivity
  implements SimpleAlertDialog.Listener, SidecarFragment.Listener, PlayStoreUiElementNode
{
  private static final PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(2500);
  private String mAccountName;
  private SetupWizardRestoreAppsSelector mAppListSelector;
  private final View.OnClickListener mAppsMenuOnClickListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      SetupWizardRestoreAppsActivity.this.mEventLogger.logClickEvent(2510, null, SetupWizardRestoreAppsActivity.this);
      if (SetupWizardRestoreAppsActivity.this.mSidecar.mState == 5) {
        SetupWizardRestoreAppsActivity.this.showAppListDialog();
      }
      while (SetupWizardRestoreAppsActivity.this.shouldSetupAsNewDevice()) {
        return;
      }
      long l = SetupWizardRestoreAppsActivity.this.mBackupDeviceInfos[SetupWizardRestoreAppsActivity.this.mCurrentDevicePosition].androidId;
      SetupWizardRestoreAppsActivity.access$802$8281e2(SetupWizardRestoreAppsActivity.this);
      SetupWizardRestoreAppsActivity.access$902$8281e2(SetupWizardRestoreAppsActivity.this);
      SetupWizardRestoreAppsActivity.this.mSidecar.fetchBackupDocs(l);
    }
  };
  private Restore.BackupDeviceInfo[] mBackupDeviceInfos;
  private int mCurrentDevicePosition;
  private boolean[] mCurrentSelectedBackupDocs;
  private SetupWizardRestoreAppsSelector mDeviceListSelector;
  private final View.OnClickListener mDeviceMenuOnClickListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      SetupWizardRestoreAppsActivity.this.mEventLogger.logClickEvent(2509, null, SetupWizardRestoreAppsActivity.this);
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      localBuilder.setLayoutId(2130969117).setPositiveId(2131362098).setNegativeId(2131361915).setEventLog(2501, null, 2503, 2504, FinskyApp.get().getCurrentAccount()).setCallback(null, 1, null);
      Bundle localBundle = new Bundle();
      localBundle.putInt("SetupWizardRestoreDeviceDialogView.selectedDevicePosition", SetupWizardRestoreAppsActivity.this.mCurrentDevicePosition);
      localBundle.putParcelableArray("SetupWizardRestoreDeviceDialogView.selectedDevices", SetupWizardRestoreAppsActivity.this.mParcelableBackupDeviceInfos);
      localBuilder.setViewConfiguration(localBundle);
      localBuilder.build().show(SetupWizardRestoreAppsActivity.this.getSupportFragmentManager(), "SetupWizardRestoreAppsActivity.backupDeviceDialog");
    }
  };
  private FinskyEventLog mEventLogger;
  private boolean mIsBackupDocsLoaded;
  private boolean mIsCreatedFinished;
  protected ViewGroup mMainView;
  private Parcelable[] mParcelableBackupDeviceInfos;
  private SetupWizardUtils.SetupWizardParams mSetupWizardParams;
  private boolean mShowAppDialogPostLoad;
  private RestoreAppsSidecar mSidecar;
  
  private void configureAppsMenuClickListener(boolean paramBoolean)
  {
    SetupWizardRestoreAppsSelector localSetupWizardRestoreAppsSelector;
    if (this.mAppListSelector != null)
    {
      localSetupWizardRestoreAppsSelector = this.mAppListSelector;
      if (!paramBoolean) {
        break label27;
      }
    }
    label27:
    for (View.OnClickListener localOnClickListener = this.mAppsMenuOnClickListener;; localOnClickListener = null)
    {
      localSetupWizardRestoreAppsSelector.setOnClickListener(localOnClickListener);
      return;
    }
  }
  
  private void configureDeviceMenuClickListener(boolean paramBoolean)
  {
    SetupWizardRestoreAppsSelector localSetupWizardRestoreAppsSelector;
    if (this.mDeviceListSelector != null)
    {
      localSetupWizardRestoreAppsSelector = this.mDeviceListSelector;
      if (!paramBoolean) {
        break label27;
      }
    }
    label27:
    for (View.OnClickListener localOnClickListener = this.mDeviceMenuOnClickListener;; localOnClickListener = null)
    {
      localSetupWizardRestoreAppsSelector.setOnClickListener(localOnClickListener);
      return;
    }
  }
  
  public static Intent createIntent(String paramString, Restore.BackupDeviceInfo[] paramArrayOfBackupDeviceInfo)
  {
    FinskyApp.get().getDfeApi(paramString);
    Intent localIntent = new Intent(FinskyApp.get(), SetupWizardRestoreAppsActivity.class);
    localIntent.putExtra("authAccount", paramString);
    ParcelableProto[] arrayOfParcelableProto = new ParcelableProto[paramArrayOfBackupDeviceInfo.length];
    for (int i = 0; i < paramArrayOfBackupDeviceInfo.length; i++) {
      arrayOfParcelableProto[i] = ParcelableProto.forProto(paramArrayOfBackupDeviceInfo[i]);
    }
    Bundle localBundle = new Bundle();
    localBundle.putParcelableArray("SetupWizardRestoreAppsActivity.backup_device_infos", arrayOfParcelableProto);
    localIntent.putExtra("SetupWizardRestoreAppsActivity.backup_device_infos_bundle", localBundle);
    return localIntent;
  }
  
  private void finishCreate()
  {
    if (this.mIsCreatedFinished) {
      return;
    }
    this.mIsCreatedFinished = true;
    LayoutInflater localLayoutInflater = LayoutInflater.from(this);
    this.mMainView = ((ViewGroup)localLayoutInflater.inflate(2130969107, null));
    setContentView(this.mMainView);
    ((TextView)this.mMainView.findViewById(2131755173)).setText(2131362746);
    ((ViewGroup)this.mMainView.findViewById(2131755234)).addView(localLayoutInflater.inflate(2130969116, this.mMainView, false));
    SetupWizardUtils.configureBasicUi(this, this.mSetupWizardParams, 1, false, false, false);
    this.mDeviceListSelector = ((SetupWizardRestoreAppsSelector)findViewById(2131756126));
    syncDeviceSelector();
    configureDeviceMenuClickListener(true);
    this.mAppListSelector = ((SetupWizardRestoreAppsSelector)findViewById(2131756128));
    syncAppSelector();
    configureAppsMenuClickListener(true);
    final SetupWizardNavBar localSetupWizardNavBar = SetupWizardUtils.getNavBarIfPossible(this);
    localSetupWizardNavBar.mNextButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        localSetupWizardNavBar.mNextButton.setOnClickListener(null);
        if (SetupWizardRestoreAppsActivity.this.shouldSetupAsNewDevice())
        {
          SetupWizardRestoreAppsActivity.access$100$82c1b7(SetupWizardRestoreAppsActivity.this);
          return;
        }
        SetupWizardRestoreAppsActivity.access$200(SetupWizardRestoreAppsActivity.this);
        Intent localIntent = new Intent();
        localIntent.putExtra("restoreToken", SetupWizardRestoreAppsActivity.this.mBackupDeviceInfos[SetupWizardRestoreAppsActivity.this.mCurrentDevicePosition].restoreToken);
        SetupWizardRestoreAppsActivity.this.setResult(-1, localIntent);
        SetupWizardRestoreAppsActivity.this.finish();
      }
    });
  }
  
  private int getSelectedAppsCount()
  {
    int i = 0;
    boolean[] arrayOfBoolean = this.mCurrentSelectedBackupDocs;
    int j = arrayOfBoolean.length;
    for (int k = 0; k < j; k++) {
      if (arrayOfBoolean[k] != 0) {
        i++;
      }
    }
    return i;
  }
  
  private boolean shouldSetupAsNewDevice()
  {
    return this.mCurrentDevicePosition == -1;
  }
  
  private void showAppListDialog()
  {
    if (shouldSetupAsNewDevice()) {
      return;
    }
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setLayoutId(2130969114).setPositiveId(2131362098).setNegativeId(2131361915).setEventLog(2505, null, 2506, 2507, FinskyApp.get().getCurrentAccount()).setCallback(null, 2, null);
    Restore.BackupDocumentInfo[] arrayOfBackupDocumentInfo = this.mSidecar.mBackupDocumentInfos;
    ArrayList localArrayList = new ArrayList(arrayOfBackupDocumentInfo.length);
    for (int i = 0; i < arrayOfBackupDocumentInfo.length; i++) {
      localArrayList.add(ParcelableProto.forProto(arrayOfBackupDocumentInfo[i]));
    }
    Bundle localBundle = new Bundle();
    localBundle.putParcelableArrayList("SetupWizardAppListDialog.backupDocs", localArrayList);
    localBundle.putBooleanArray("SetupWizardAppListDialog.selectedBackupDocs", this.mCurrentSelectedBackupDocs);
    localBuilder.setViewConfiguration(localBundle);
    localBuilder.build().show(getSupportFragmentManager(), "SetupWizardRestoreAppsActivity.backupAppsDialog");
  }
  
  private void syncAppSelector()
  {
    if (this.mAppListSelector == null) {
      return;
    }
    TextView localTextView = (TextView)findViewById(2131756127);
    Resources localResources = getResources();
    if (shouldSetupAsNewDevice())
    {
      this.mAppListSelector.setVisibility(8);
      localTextView.setVisibility(8);
      return;
    }
    this.mAppListSelector.setVisibility(0);
    localTextView.setVisibility(0);
    switch (this.mSidecar.mState)
    {
    default: 
      return;
    case 4: 
      this.mAppListSelector.setTexts(localResources.getString(2131362306));
      return;
    case 6: 
      int j = this.mBackupDeviceInfos.length;
      this.mAppListSelector.setTexts(localResources.getQuantityString(2131296275, j));
      return;
    }
    int i = getSelectedAppsCount();
    if (i == this.mSidecar.mBackupDocumentInfos.length)
    {
      this.mAppListSelector.setTexts(localResources.getString(2131362735), Integer.toString(i));
      return;
    }
    if (i == 0)
    {
      this.mAppListSelector.setTexts(localResources.getString(2131362742));
      return;
    }
    this.mAppListSelector.setTexts(localResources.getQuantityString(2131296277, i), Integer.toString(i));
  }
  
  private void syncDeviceSelector()
  {
    if (shouldSetupAsNewDevice())
    {
      this.mDeviceListSelector.setTexts(getResources().getString(2131362752));
      return;
    }
    long l = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - this.mBackupDeviceInfos[this.mCurrentDevicePosition].lastCheckinTimeMs);
    Resources localResources1 = getResources();
    if (l == 0L) {}
    Resources localResources2;
    int i;
    Object[] arrayOfObject;
    for (String str = localResources1.getString(2131362738);; str = localResources2.getQuantityString(2131296276, i, arrayOfObject))
    {
      this.mDeviceListSelector.setTexts(this.mBackupDeviceInfos[this.mCurrentDevicePosition].name, str);
      return;
      localResources2 = getResources();
      i = (int)l;
      arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(l);
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new UnsupportedOperationException("Unwanted children.");
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return mUiElementProto;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    this.mSetupWizardParams = new SetupWizardUtils.SetupWizardParams(localIntent);
    if (this.mSetupWizardParams.mIsLightTheme) {}
    for (int i = 2131558753;; i = 2131558650)
    {
      setTheme(i);
      this.mAccountName = localIntent.getStringExtra("authAccount");
      this.mEventLogger = FinskyApp.get().getEventLogger(this.mAccountName);
      this.mParcelableBackupDeviceInfos = localIntent.getBundleExtra("SetupWizardRestoreAppsActivity.backup_device_infos_bundle").getParcelableArray("SetupWizardRestoreAppsActivity.backup_device_infos");
      this.mBackupDeviceInfos = new Restore.BackupDeviceInfo[this.mParcelableBackupDeviceInfos.length];
      for (int j = 0; j < this.mParcelableBackupDeviceInfos.length; j++) {
        this.mBackupDeviceInfos[j] = ((Restore.BackupDeviceInfo)((ParcelableProto)this.mParcelableBackupDeviceInfos[j]).mPayload);
      }
    }
    if (paramBundle != null)
    {
      this.mCurrentDevicePosition = paramBundle.getInt("SetupWizardRestoreAppsActivity.current_device", 0);
      this.mIsBackupDocsLoaded = paramBundle.getBoolean("SetupWizardRestoreAppsActivity.backup_docs_loaded", false);
      this.mCurrentSelectedBackupDocs = paramBundle.getBooleanArray("SetupWizardRestoreAppsActivity.current_selected_backup_docs");
    }
    for (;;)
    {
      this.mSidecar = ((RestoreAppsSidecar)getSupportFragmentManager().findFragmentByTag("SetupWizardRestoreAppsActivity.sidecar"));
      if (this.mSidecar == null)
      {
        this.mSidecar = RestoreAppsSidecar.newInstance(this.mAccountName);
        getSupportFragmentManager().beginTransaction().add(this.mSidecar, "SetupWizardRestoreAppsActivity.sidecar").commit();
      }
      return;
      this.mEventLogger.logPathImpression(0L, this);
    }
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
    case 1: 
      int i;
      do
      {
        return;
        i = paramBundle.getInt("SetupWizardRestoreDeviceDialogView.selectedDevicePosition");
      } while (i == this.mCurrentDevicePosition);
      this.mCurrentDevicePosition = i;
      if (shouldSetupAsNewDevice())
      {
        this.mEventLogger.logClickEvent(2502, null, getParentNode());
        this.mIsBackupDocsLoaded = true;
      }
      for (;;)
      {
        syncDeviceSelector();
        syncAppSelector();
        return;
        configureDeviceMenuClickListener(false);
        this.mIsBackupDocsLoaded = false;
        this.mSidecar.fetchBackupDocs(this.mBackupDeviceInfos[i].androidId);
      }
    }
    boolean[] arrayOfBoolean = paramBundle.getBooleanArray("SetupWizardAppListDialog.selectedBackupDocs");
    if (arrayOfBoolean.length != this.mCurrentSelectedBackupDocs.length)
    {
      FinskyLog.wtf("Length mismatched, can't update", new Object[0]);
      return;
    }
    this.mCurrentSelectedBackupDocs = arrayOfBoolean;
    syncAppSelector();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("SetupWizardRestoreAppsActivity.current_device", this.mCurrentDevicePosition);
    paramBundle.putBooleanArray("SetupWizardRestoreAppsActivity.current_selected_backup_docs", this.mCurrentSelectedBackupDocs);
    paramBundle.putBoolean("SetupWizardRestoreAppsActivity.backup_docs_loaded", this.mIsBackupDocsLoaded);
  }
  
  public void onStart()
  {
    super.onStart();
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    if (paramSidecarFragment != this.mSidecar) {
      FinskyLog.wtf("Received state change for unknown fragment: %s", new Object[] { paramSidecarFragment });
    }
    do
    {
      return;
      switch (this.mSidecar.mState)
      {
      case 1: 
      case 2: 
      case 3: 
      default: 
        return;
      case 0: 
        this.mSidecar.fetchBackupDocs(this.mBackupDeviceInfos[this.mCurrentDevicePosition].androidId);
        finishCreate();
        return;
      case 4: 
        finishCreate();
        configureDeviceMenuClickListener(false);
        configureAppsMenuClickListener(false);
        syncAppSelector();
        return;
      case 5: 
        finishCreate();
        configureDeviceMenuClickListener(true);
        configureAppsMenuClickListener(true);
        Restore.BackupDocumentInfo[] arrayOfBackupDocumentInfo = this.mSidecar.mBackupDocumentInfos;
        if (!this.mIsBackupDocsLoaded)
        {
          this.mCurrentSelectedBackupDocs = new boolean[arrayOfBackupDocumentInfo.length];
          for (int i = 0; i < arrayOfBackupDocumentInfo.length; i++) {
            this.mCurrentSelectedBackupDocs[i] = true;
          }
          this.mIsBackupDocsLoaded = true;
        }
        syncAppSelector();
      }
    } while (!this.mShowAppDialogPostLoad);
    showAppListDialog();
    this.mShowAppDialogPostLoad = false;
    return;
    finishCreate();
    configureDeviceMenuClickListener(true);
    configureAppsMenuClickListener(false);
    syncAppSelector();
  }
  
  public void onStop()
  {
    this.mSidecar.setListener(null);
    super.onStop();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.SetupWizardRestoreAppsActivity
 * JD-Core Version:    0.7.0.1
 */