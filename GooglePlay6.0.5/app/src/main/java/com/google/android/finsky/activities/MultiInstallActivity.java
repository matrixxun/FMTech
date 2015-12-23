package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.installer.InstallPolicies.InstallWarnings;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.PermissionsBucketer;
import com.google.android.finsky.utils.PurchaseInitiator;
import com.google.android.play.layout.PlayActionButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiInstallActivity
  extends AppCompatActivity
  implements View.OnClickListener
{
  private InstallApprovalFragment mCurrentFragment;
  private int mCurrentInstallIndex;
  private int mCurrentPageType;
  private String mInstallAccountName;
  private Installer mInstaller;
  private ArrayList<InstallDetails> mInstallsForApproval;
  private int mMode;
  private PlayActionButton mNegativeButton;
  private PlayActionButton mPositiveButton;
  
  private void displayPageOrFinish(boolean paramBoolean)
  {
    int i = 3;
    if (this.mCurrentInstallIndex >= this.mInstallsForApproval.size())
    {
      finish();
      return;
    }
    InstallDetails localInstallDetails1 = (InstallDetails)this.mInstallsForApproval.get(this.mCurrentInstallIndex);
    label134:
    InstallApprovalFragment localInstallApprovalFragment;
    FragmentTransaction localFragmentTransaction;
    if (localInstallDetails1.autoUpdateDisabled)
    {
      this.mCurrentPageType = 1;
      int j = this.mCurrentInstallIndex;
      setButtonText(this.mCurrentPageType);
      InstallDetails localInstallDetails2 = (InstallDetails)this.mInstallsForApproval.get(j);
      switch (this.mCurrentPageType)
      {
      default: 
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(this.mCurrentPageType);
        FinskyLog.wtf("Invalid current page type: %d", arrayOfObject2);
        i = 2;
      case 2: 
        localInstallApprovalFragment = InstallApprovalFragment.newInstance(localInstallDetails2.mDocument.mDocument.docid, localInstallDetails2.mDocument.mDocument.title, j + 1, this.mInstallsForApproval.size(), i, localInstallDetails2.permissions);
        localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (paramBoolean) {
          localFragmentTransaction.setCustomAnimations(2131034129, 2131034127);
        }
        break;
      }
    }
    for (;;)
    {
      if (this.mCurrentFragment != null) {
        localFragmentTransaction.remove(this.mCurrentFragment);
      }
      localFragmentTransaction.add(2131756177, localInstallApprovalFragment);
      localFragmentTransaction.commit();
      this.mCurrentFragment = localInstallApprovalFragment;
      this.mPositiveButton.setEnabled(true);
      this.mNegativeButton.setEnabled(true);
      return;
      if (localInstallDetails1.largeDownload)
      {
        this.mCurrentPageType = 2;
        break;
      }
      if (localInstallDetails1.newPermissions)
      {
        this.mCurrentPageType = i;
        break;
      }
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localInstallDetails1.mDocument.mDocument.docid;
      FinskyLog.wtf("Failed to determine the next page type when updating %s.", arrayOfObject1);
      finish();
      return;
      i = 1;
      break label134;
      i = 2;
      break label134;
      localFragmentTransaction.setCustomAnimations(2131034139, 2131034142);
    }
  }
  
  public static Intent getInstallIntent(Context paramContext, List<Document> paramList, String paramString)
  {
    Intent localIntent = new Intent(paramContext, MultiInstallActivity.class);
    localIntent.putExtra("MultiInstallActivity.installs", Lists.newArrayList(paramList));
    localIntent.putExtra("MultiInstallActivity.mode", 1);
    localIntent.putExtra("MultiInstallActivity.install-account-name", paramString);
    return localIntent;
  }
  
  public static Intent getUpdateIntent(Context paramContext, List<Document> paramList)
  {
    Intent localIntent = new Intent(paramContext, MultiInstallActivity.class);
    localIntent.putExtra("MultiInstallActivity.installs", Lists.newArrayList(paramList));
    localIntent.putExtra("MultiInstallActivity.mode", 2);
    return localIntent;
  }
  
  private void performSingleInstall(InstallDetails paramInstallDetails)
  {
    String str = paramInstallDetails.mDocument.mDocument.docid;
    if (this.mMode == 2)
    {
      this.mInstaller.setOverrideForegroundCheck(str);
      this.mInstaller.updateSingleInstalledApp(str, paramInstallDetails.mDocument.getVersionCode(), paramInstallDetails.mDocument.mDocument.title, paramInstallDetails.reason, false, 3, this.mInstaller.extractInstallLocation(paramInstallDetails.mDocument));
      return;
    }
    Libraries localLibraries = FinskyApp.get().mLibraries;
    Context localContext = getApplicationContext();
    Account localAccount = AccountHandler.findAccount(this.mInstallAccountName, localContext);
    AccountLibrary localAccountLibrary = localLibraries.getAccountLibrary(localAccount);
    if (localAccountLibrary == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = FinskyLog.scrubPii(this.mInstallAccountName);
      FinskyLog.d("Cannot perform install because cannot find library for %s.", arrayOfObject);
      return;
    }
    Document localDocument = paramInstallDetails.mDocument;
    if (!LibraryUtils.isOwned(localDocument, localAccountLibrary))
    {
      PurchaseInitiator.makeFreePurchase(localAccount, localDocument, 1, null, null, true, false);
      return;
    }
    this.mInstaller.requestInstall(str, paramInstallDetails.mDocument.getVersionCode(), this.mInstallAccountName, paramInstallDetails.mDocument.mDocument.title, false, paramInstallDetails.reason, 3, this.mInstaller.extractInstallLocation(paramInstallDetails.mDocument));
  }
  
  private void setButtonText(int paramInt)
  {
    int i;
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Invalid current page type: %d", arrayOfObject);
      i = 2131362233;
    }
    for (;;)
    {
      this.mPositiveButton.setText(getResources().getString(i).toUpperCase());
      this.mNegativeButton.setText(getResources().getString(2131362232).toUpperCase());
      return;
      i = 2131362228;
      continue;
      i = 2131362231;
      continue;
      i = 2131362233;
    }
  }
  
  public void onClick(View paramView)
  {
    if (this.mCurrentPageType == 0)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(0);
      FinskyLog.wtf("Unexpected click for page type: %d", arrayOfObject);
    }
    do
    {
      return;
      if ((paramView == this.mPositiveButton) || (paramView == this.mNegativeButton))
      {
        this.mPositiveButton.setEnabled(false);
        this.mNegativeButton.setEnabled(false);
      }
      if (paramView == this.mPositiveButton)
      {
        InstallDetails localInstallDetails = (InstallDetails)this.mInstallsForApproval.get(this.mCurrentInstallIndex);
        switch (this.mCurrentPageType)
        {
        }
        while (localInstallDetails.done())
        {
          performSingleInstall(localInstallDetails);
          this.mCurrentInstallIndex = (1 + this.mCurrentInstallIndex);
          displayPageOrFinish(false);
          return;
          localInstallDetails.autoUpdateDisabled = false;
          continue;
          localInstallDetails.largeDownload = false;
          this.mInstaller.setMobileDataAllowed(localInstallDetails.mDocument.mDocument.docid);
          continue;
          localInstallDetails.newPermissions = false;
          PermissionsBucketer.setAcceptedNewBuckets(FinskyApp.get().mInstallerDataStore, localInstallDetails.mDocument.mDocument.docid);
        }
        displayPageOrFinish(true);
        return;
      }
    } while (paramView != this.mNegativeButton);
    this.mCurrentInstallIndex = (1 + this.mCurrentInstallIndex);
    displayPageOrFinish(false);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130969145);
    this.mInstaller = FinskyApp.get().mInstaller;
    this.mPositiveButton = ((PlayActionButton)findViewById(2131755302));
    this.mPositiveButton.configure(3, 2131362418, this);
    this.mNegativeButton = ((PlayActionButton)findViewById(2131755301));
    this.mNegativeButton.configure(3, getResources().getString(2131361915), this);
    this.mMode = getIntent().getIntExtra("MultiInstallActivity.mode", 1);
    this.mInstallAccountName = getIntent().getStringExtra("MultiInstallActivity.install-account-name");
    if (paramBundle == null)
    {
      this.mCurrentInstallIndex = 0;
      this.mCurrentPageType = 0;
      ArrayList localArrayList1 = getIntent().getParcelableArrayListExtra("MultiInstallActivity.installs");
      ArrayList localArrayList2 = new ArrayList();
      ArrayList localArrayList3 = new ArrayList();
      int i = this.mMode;
      if ((!localArrayList2.isEmpty()) || (!localArrayList3.isEmpty())) {
        FinskyLog.wtf("The output lists are not initially empty.", new Object[0]);
      }
      InstallPolicies localInstallPolicies = FinskyApp.get().mInstallPolicies;
      String str;
      boolean bool;
      Iterator localIterator1;
      if (i == 2)
      {
        str = "bulk_update";
        bool = ((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue();
        localIterator1 = localArrayList1.iterator();
      }
      for (;;)
      {
        label221:
        if (!localIterator1.hasNext()) {
          break label325;
        }
        Document localDocument = (Document)localIterator1.next();
        if (i == 2) {}
        InstallDetails localInstallDetails;
        for (InstallPolicies.InstallWarnings localInstallWarnings = localInstallPolicies.getUpdateWarningsForDocument(localDocument, bool);; localInstallWarnings = localInstallPolicies.getUpdateWarningsForDocument(localDocument, false))
        {
          localInstallDetails = new InstallDetails(localDocument, localInstallWarnings, str);
          if (!localInstallDetails.done()) {
            break label314;
          }
          localArrayList2.add(localInstallDetails);
          break label221;
          str = "bulk_install";
          break;
        }
        label314:
        localArrayList3.add(localInstallDetails);
      }
      label325:
      Iterator localIterator2 = localArrayList2.iterator();
      while (localIterator2.hasNext()) {
        performSingleInstall((InstallDetails)localIterator2.next());
      }
      this.mInstallsForApproval = localArrayList3;
      displayPageOrFinish(true);
      return;
    }
    this.mInstallsForApproval = paramBundle.getParcelableArrayList("MultiInstallActivity.installs-for-approval");
    this.mCurrentInstallIndex = paramBundle.getInt("MultiInstallActivity.current-install-index", 0);
    this.mCurrentPageType = paramBundle.getInt("MultiInstallActivity.current-page-type", 0);
    setButtonText(this.mCurrentPageType);
    this.mCurrentFragment = ((InstallApprovalFragment)getSupportFragmentManager().findFragmentById(2131756177));
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelableArrayList("MultiInstallActivity.installs-for-approval", this.mInstallsForApproval);
    paramBundle.putInt("MultiInstallActivity.current-install-index", this.mCurrentInstallIndex);
    paramBundle.putInt("MultiInstallActivity.current-page-type", this.mCurrentPageType);
  }
  
  public static class InstallDetails
    implements Parcelable
  {
    public static final Parcelable.Creator<InstallDetails> CREATOR = new Parcelable.Creator() {};
    boolean autoUpdateDisabled;
    boolean largeDownload;
    final Document mDocument;
    boolean newPermissions;
    private final String[] permissions;
    final String reason;
    
    protected InstallDetails(Parcel paramParcel)
    {
      boolean bool2;
      boolean bool3;
      if (paramParcel.readByte() > 0)
      {
        bool2 = bool1;
        this.largeDownload = bool2;
        if (paramParcel.readByte() <= 0) {
          break label104;
        }
        bool3 = bool1;
        label30:
        this.newPermissions = bool3;
        if (paramParcel.readByte() <= 0) {
          break label110;
        }
      }
      for (;;)
      {
        this.autoUpdateDisabled = bool1;
        this.reason = paramParcel.readString();
        this.mDocument = ((Document)Document.CREATOR.createFromParcel(paramParcel));
        if (!this.newPermissions) {
          break label115;
        }
        this.permissions = new String[paramParcel.readInt()];
        paramParcel.readStringArray(this.permissions);
        return;
        bool2 = false;
        break;
        label104:
        bool3 = false;
        break label30;
        label110:
        bool1 = false;
      }
      label115:
      this.permissions = null;
    }
    
    public InstallDetails(Document paramDocument, InstallPolicies.InstallWarnings paramInstallWarnings, String paramString)
    {
      this.autoUpdateDisabled = paramInstallWarnings.autoUpdateDisabled;
      this.largeDownload = paramInstallWarnings.largeDownload;
      this.newPermissions = paramInstallWarnings.newPermissions;
      this.mDocument = paramDocument;
      this.reason = paramString;
      if (this.newPermissions)
      {
        this.permissions = paramDocument.getAppDetails().permission;
        return;
      }
      this.permissions = null;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public final boolean done()
    {
      return (!this.largeDownload) && (!this.newPermissions) && (!this.autoUpdateDisabled);
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      int i = 1;
      int j;
      int k;
      if (this.largeDownload)
      {
        j = i;
        paramParcel.writeByte((byte)j);
        if (!this.newPermissions) {
          break label97;
        }
        k = i;
        label29:
        paramParcel.writeByte((byte)k);
        if (!this.autoUpdateDisabled) {
          break label103;
        }
      }
      for (;;)
      {
        paramParcel.writeByte((byte)i);
        paramParcel.writeString(this.reason);
        this.mDocument.writeToParcel(paramParcel, paramInt);
        if (this.newPermissions)
        {
          paramParcel.writeInt(this.permissions.length);
          paramParcel.writeStringArray(this.permissions);
        }
        return;
        j = 0;
        break;
        label97:
        k = 0;
        break label29;
        label103:
        i = 0;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.MultiInstallActivity
 * JD-Core Version:    0.7.0.1
 */