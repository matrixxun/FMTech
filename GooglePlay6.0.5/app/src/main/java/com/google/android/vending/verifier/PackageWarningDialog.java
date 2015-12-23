package com.google.android.vending.verifier;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.utils.Notifier;

public class PackageWarningDialog
  extends AppCompatActivity
  implements CompoundButton.OnCheckedChangeListener, SimpleAlertDialog.Listener, ButtonBar.ClickListener
{
  public static int NO_ID = -1;
  private int mAction;
  private int mAlternateLayoutVersion;
  private String mAppName;
  private ButtonBar mButtonBar;
  private CheckBox mCheckBox;
  private boolean mDontWarnAgain = false;
  private int mId;
  private String mMessage;
  private String mPackageName;
  private boolean mPressedBackButton = false;
  private byte[] mResponseToken;
  private boolean mUserChoiceWasReported = false;
  private boolean mUsingAlternateLayout;
  
  public static Intent createIntent(Context paramContext, int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, int paramInt3)
  {
    Intent localIntent = new Intent(paramContext, PackageWarningDialog.class);
    localIntent.setFlags(1476395008);
    localIntent.putExtra("verification_id", paramInt1);
    localIntent.putExtra("action", paramInt2);
    localIntent.putExtra("app_name", paramString1);
    localIntent.putExtra("message", paramString3);
    localIntent.putExtra("package_name", paramString2);
    localIntent.putExtra("response_token", paramArrayOfByte);
    localIntent.putExtra("layout_version", paramInt3);
    return localIntent;
  }
  
  public static void show(Context paramContext, int paramInt1, int paramInt2, String paramString1, String paramString2, int paramInt3)
  {
    paramContext.startActivity(createIntent(paramContext, paramInt1, paramInt2, paramString1, null, paramString2, null, paramInt3));
  }
  
  public void onBackPressed()
  {
    this.mPressedBackButton = true;
    if (this.mAction == 2)
    {
      onNegativeButtonClick();
      return;
    }
    onPositiveButtonClick();
  }
  
  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    if (this.mAction == 0) {
      this.mButtonBar.setNegativeButtonEnabled(paramBoolean);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    int i = 1;
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    this.mAlternateLayoutVersion = localIntent.getIntExtra("layout_version", 0);
    this.mId = localIntent.getIntExtra("verification_id", NO_ID);
    this.mAction = localIntent.getIntExtra("action", 0);
    this.mAppName = localIntent.getStringExtra("app_name");
    this.mMessage = localIntent.getStringExtra("message");
    this.mPackageName = localIntent.getStringExtra("package_name");
    this.mResponseToken = localIntent.getByteArrayExtra("response_token");
    SimpleAlertDialog.Builder localBuilder;
    if (this.mAlternateLayoutVersion == i)
    {
      this.mUsingAlternateLayout = i;
      if (!this.mUsingAlternateLayout) {
        break label354;
      }
      Bundle localBundle = new Bundle();
      localBundle.putString("app_name", this.mAppName);
      localBundle.putString("warning_message", this.mMessage);
      localBundle.putInt("action", this.mAction);
      localBuilder = new SimpleAlertDialog.Builder();
      localBuilder.setLayoutId(2130968892).setViewConfiguration(localBundle).setPositiveId(2131362418).setCancelDoesNegativeAction$5e554932().setCanceledOnTouchOutside(false);
      switch (this.mAction)
      {
      default: 
        label216:
        PackageWarningSimpleAlertDialog localPackageWarningSimpleAlertDialog = new PackageWarningSimpleAlertDialog();
        localBuilder.configureDialog(localPackageWarningSimpleAlertDialog);
        localPackageWarningSimpleAlertDialog.show(getSupportFragmentManager(), "PackageWarningSimpleAlertDialog");
      }
    }
    for (;;)
    {
      if (Build.VERSION.SDK_INT >= 11) {
        setFinishOnTouchOutside(false);
      }
      if ((this.mId != NO_ID) && (!PackageVerificationService.registerDialog(this.mId, this))) {
        finish();
      }
      return;
      i = 0;
      break;
      localBuilder.setTitleId(2131362469);
      break label216;
      localBuilder.setTitleId(2131362469);
      break label216;
      localBuilder.setTitleId(2131362472);
      localBuilder.setNegativeId(2131362481);
      FinskyApp.get().mNotificationHelper.hidePackageRemoveRequestMessage(this.mPackageName);
      break label216;
      localBuilder.setTitleId(2131362470);
      break label216;
      label354:
      setContentView(2130968891);
      ImageView localImageView = (ImageView)findViewById(2131755706);
      TextView localTextView1 = (TextView)findViewById(2131755798);
      TextView localTextView2 = (TextView)findViewById(2131755233);
      TextView localTextView3 = (TextView)findViewById(2131755799);
      this.mCheckBox = ((CheckBox)findViewById(2131755800));
      if (!TextUtils.isEmpty(this.mMessage)) {
        localTextView2.setText(this.mMessage);
      }
      if (!TextUtils.isEmpty(this.mAppName)) {
        localTextView3.setText(this.mAppName);
      }
      this.mButtonBar = ((ButtonBar)findViewById(2131755300));
      this.mButtonBar.setClickListener(this);
      switch (this.mAction)
      {
      default: 
        break;
      case 0: 
        localTextView1.setText(2131362473);
        localImageView.setImageResource(2130837888);
        this.mCheckBox.setText(2131362475);
        this.mCheckBox.setOnCheckedChangeListener(this);
        this.mButtonBar.setPositiveButtonTitle(2131362418);
        this.mButtonBar.setNegativeButtonTitle(2131362478);
        this.mButtonBar.setNegativeButtonEnabled(false);
        break;
      case 1: 
        localTextView1.setText(2131362469);
        localImageView.setImageResource(2130837887);
        this.mCheckBox.setVisibility(8);
        this.mButtonBar.setPositiveButtonVisible(false);
        this.mButtonBar.setNegativeButtonTitle(2131362418);
        break;
      case 2: 
        localTextView1.setText(2131362471);
        localImageView.setImageResource(2130837888);
        this.mCheckBox.setText(2131362474);
        this.mCheckBox.setOnCheckedChangeListener(this);
        this.mButtonBar.setPositiveButtonTitle(2131362418);
        this.mButtonBar.setNegativeButtonTitle(2131362480);
        FinskyApp.get().mNotificationHelper.hidePackageRemoveRequestMessage(this.mPackageName);
        break;
      case 3: 
        localTextView1.setText(2131362470);
        localImageView.setImageResource(2130837887);
        this.mCheckBox.setVisibility(8);
        this.mButtonBar.setPositiveButtonVisible(false);
        this.mButtonBar.setNegativeButtonTitle(2131362418);
      }
    }
  }
  
  protected void onDestroy()
  {
    if ((!this.mUserChoiceWasReported) && (isFinishing()))
    {
      if ((this.mAction != 0) && (this.mAction != 1)) {
        break label52;
      }
      PackageVerificationService.reportUserChoice(this.mId, -1, false);
    }
    label52:
    while (this.mAction != 2)
    {
      PackageVerificationService.registerDialog(this.mId, null);
      super.onDestroy();
      return;
    }
    String str = this.mPackageName;
    if (this.mCheckBox != null) {}
    for (boolean bool = this.mCheckBox.isChecked();; bool = this.mDontWarnAgain)
    {
      PackageVerificationService.sendRemovalResponse(this, str, false, bool, this.mPressedBackButton, this.mResponseToken);
      break;
    }
  }
  
  public final void onNegativeButtonClick()
  {
    if ((this.mAction == 0) || (this.mAction == 1))
    {
      PackageVerificationService.reportUserChoice(this.mId, 1, this.mPressedBackButton);
      PackageVerificationService.registerDialog(this.mId, null);
    }
    while (this.mAction != 2)
    {
      this.mUserChoiceWasReported = true;
      finish();
      return;
    }
    String str = this.mPackageName;
    if (this.mCheckBox != null) {}
    for (boolean bool = this.mCheckBox.isChecked();; bool = this.mDontWarnAgain)
    {
      PackageVerificationService.sendRemovalResponse(this, str, false, bool, this.mPressedBackButton, this.mResponseToken);
      break;
    }
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    this.mPressedBackButton = paramBundle.getBoolean("pressed_back_button", false);
    this.mDontWarnAgain = paramBundle.getBoolean("dont_warn", false);
    onNegativeButtonClick();
  }
  
  public final void onPositiveButtonClick()
  {
    if ((this.mAction == 0) || (this.mAction == 1))
    {
      PackageVerificationService.reportUserChoice(this.mId, -1, this.mPressedBackButton);
      PackageVerificationService.registerDialog(this.mId, null);
    }
    while (this.mAction != 2)
    {
      this.mUserChoiceWasReported = true;
      finish();
      return;
    }
    String str = this.mPackageName;
    if (this.mCheckBox != null) {}
    for (boolean bool = this.mCheckBox.isChecked();; bool = this.mDontWarnAgain)
    {
      PackageVerificationService.sendRemovalResponse(this, str, true, bool, this.mPressedBackButton, this.mResponseToken);
      break;
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    this.mPressedBackButton = paramBundle.getBoolean("pressed_back_button", false);
    this.mDontWarnAgain = paramBundle.getBoolean("dont_warn", false);
    onPositiveButtonClick();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageWarningDialog
 * JD-Core Version:    0.7.0.1
 */