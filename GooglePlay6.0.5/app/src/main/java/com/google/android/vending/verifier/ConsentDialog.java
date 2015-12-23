package com.google.android.vending.verifier;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;

public class ConsentDialog
  extends AppCompatActivity
  implements ButtonBar.ClickListener
{
  private static String KEY_VERIFICATION_ID = "verification_id";
  private ButtonBar mButtonBar;
  private int mId;
  private boolean mResponseSent = false;
  
  public static void show(Context paramContext, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, ConsentDialog.class);
    localIntent.setFlags(1342177280);
    localIntent.putExtra(KEY_VERIFICATION_ID, paramInt);
    paramContext.startActivity(localIntent);
  }
  
  public void onBackPressed() {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968890);
    if (Build.VERSION.SDK_INT >= 11) {
      setFinishOnTouchOutside(false);
    }
    this.mId = getIntent().getIntExtra(KEY_VERIFICATION_ID, -1);
    if (!PackageVerificationService.registerDialog(this.mId, this))
    {
      finish();
      return;
    }
    this.mButtonBar = ((ButtonBar)findViewById(2131755300));
    this.mButtonBar.setClickListener(this);
    this.mButtonBar.setPositiveButtonTitle(2131361811);
    this.mButtonBar.setNegativeButtonTitle(2131362077);
  }
  
  protected void onDestroy()
  {
    if ((!this.mResponseSent) && (isFinishing())) {
      PackageVerificationService.reportConsentDialog(this.mId, false);
    }
    PackageVerificationService.registerDialog(this.mId, null);
    super.onDestroy();
  }
  
  public final void onNegativeButtonClick()
  {
    this.mResponseSent = true;
    PackageVerificationService.reportConsentDialog(this.mId, false);
    PackageVerificationService.registerDialog(this.mId, null);
    finish();
  }
  
  public final void onPositiveButtonClick()
  {
    this.mResponseSent = true;
    PackageVerificationService.reportConsentDialog(this.mId, true);
    PackageVerificationService.registerDialog(this.mId, null);
    finish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.ConsentDialog
 * JD-Core Version:    0.7.0.1
 */