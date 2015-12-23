package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.utils.UiUtils;

public class PinEntryDialog
  extends AppCompatActivity
  implements TextView.OnEditorActionListener, ButtonBar.ClickListener
{
  private ButtonBar mButtonBar;
  private FinskyEventLog mEventLogger;
  private boolean mIsInSetupConfirmStage;
  private boolean mIsRunningInSetupMode;
  private String mMatchPin;
  private GenericUiElementNode mNode = new GenericUiElementNode(311, null, null, null);
  private EditText mPinEntryView;
  private TextWatcher mPinWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable) {}
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      PinEntryDialog.this.syncOkButtonState();
    }
  };
  private TextView mPromptView;
  
  public static Intent getIntent(Context paramContext, int paramInt1, int paramInt2, String paramString, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramContext, PinEntryDialog.class);
    localIntent.putExtra("PinEntryDialog.isInEnterAndConfirmMode", false);
    localIntent.putExtra("PinEntryDialog.titleStringId", paramInt1);
    localIntent.putExtra("PinEntryDialog.promptStringId", paramInt2);
    localIntent.putExtra("PinEntryDialog.pinToMatch", paramString);
    localIntent.putExtra("PinEntryDialog.extraParams", paramBundle);
    return localIntent;
  }
  
  public static Intent getSetupIntent$19bcc0ce(Context paramContext, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramContext, PinEntryDialog.class);
    localIntent.putExtra("PinEntryDialog.isInEnterAndConfirmMode", true);
    localIntent.putExtra("PinEntryDialog.titleStringId", 2131362548);
    localIntent.putExtra("PinEntryDialog.promptStringId", 2131362549);
    localIntent.putExtra("PinEntryDialog.confirmTitleStringId", 2131362540);
    localIntent.putExtra("PinEntryDialog.confirmPromptStringId", 2131362541);
    localIntent.putExtra("PinEntryDialog.extraParams", paramBundle);
    return localIntent;
  }
  
  private String getUserPin()
  {
    return this.mPinEntryView.getText().toString().trim();
  }
  
  private void syncOkButtonState()
  {
    if (getUserPin().length() >= 4) {}
    for (boolean bool = true;; bool = false)
    {
      this.mButtonBar.setPositiveButtonEnabled(bool);
      return;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968907);
    Intent localIntent = getIntent();
    this.mIsRunningInSetupMode = localIntent.getBooleanExtra("PinEntryDialog.isInEnterAndConfirmMode", false);
    int i = localIntent.getIntExtra("PinEntryDialog.titleStringId", -1);
    int j = localIntent.getIntExtra("PinEntryDialog.promptStringId", -1);
    this.mMatchPin = localIntent.getStringExtra("PinEntryDialog.pinToMatch");
    this.mPromptView = ((TextView)findViewById(2131755830));
    this.mPinEntryView = ((EditText)findViewById(2131755831));
    this.mButtonBar = ((ButtonBar)findViewById(2131755300));
    setTitle(i);
    this.mPromptView.setText(j);
    this.mButtonBar.setPositiveButtonTitle(2131362053);
    this.mButtonBar.setNegativeButtonTitle(2131362047);
    this.mButtonBar.setClickListener(this);
    this.mPinEntryView.addTextChangedListener(this.mPinWatcher);
    this.mPinEntryView.setOnEditorActionListener(this);
    this.mEventLogger = FinskyApp.get().getEventLogger();
    if (paramBundle == null) {
      this.mEventLogger.logPathImpression(0L, this.mNode);
    }
    this.mPinEntryView.requestFocus();
  }
  
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (((paramKeyEvent != null) && (paramKeyEvent.getKeyCode() == 66)) || (paramInt == 6)) {
      onPositiveButtonClick();
    }
    return false;
  }
  
  public final void onNegativeButtonClick()
  {
    this.mEventLogger.logClickEvent(259, null, this.mNode);
    setResult(0);
    finish();
  }
  
  public final void onPositiveButtonClick()
  {
    this.mEventLogger.logClickEvent(258, null, this.mNode);
    String str = getUserPin();
    if ((this.mMatchPin != null) && (!this.mMatchPin.equals(str)))
    {
      this.mEventLogger.logOperationSuccessBackgroundEvent(501, false);
      this.mPinEntryView.setText("");
      UiUtils.setErrorOnTextView(this.mPinEntryView, getString(2131362547), getString(2131362543));
      return;
    }
    this.mEventLogger.logOperationSuccessBackgroundEvent(501, true);
    if ((this.mIsRunningInSetupMode) && (!this.mIsInSetupConfirmStage))
    {
      this.mMatchPin = str;
      this.mIsInSetupConfirmStage = true;
      Intent localIntent2 = getIntent();
      setTitle(localIntent2.getIntExtra("PinEntryDialog.confirmTitleStringId", -1));
      this.mPromptView.setText(localIntent2.getIntExtra("PinEntryDialog.confirmPromptStringId", -1));
      UiUtils.sendAccessibilityEventWithText(getBaseContext(), this.mPromptView.getText(), this.mPromptView, true);
      this.mPinEntryView.setText("");
      this.mPinEntryView.requestFocus();
      return;
    }
    Intent localIntent1 = new Intent();
    localIntent1.putExtra("PinEntryDialog.resultPin", str);
    localIntent1.putExtra("PinEntryDialog.extraParams", getIntent().getBundleExtra("PinEntryDialog.extraParams"));
    setResult(-1, localIntent1);
    finish();
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    if ((this.mIsRunningInSetupMode) && (paramBundle != null))
    {
      this.mIsInSetupConfirmStage = paramBundle.getBoolean("PinEntryDialog.keyIsInSetupConfirmStage", false);
      if (this.mIsInSetupConfirmStage)
      {
        this.mMatchPin = paramBundle.getString("PinEntryDialog.keyCurrentPin");
        Intent localIntent = getIntent();
        setTitle(localIntent.getIntExtra("PinEntryDialog.confirmTitleStringId", -1));
        this.mPromptView.setText(localIntent.getIntExtra("PinEntryDialog.confirmPromptStringId", -1));
      }
    }
  }
  
  public void onResume()
  {
    super.onResume();
    syncOkButtonState();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("PinEntryDialog.keyIsInSetupConfirmStage", this.mIsInSetupConfirmStage);
    if (this.mIsInSetupConfirmStage) {
      paramBundle.putString("PinEntryDialog.keyCurrentPin", this.mMatchPin);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.PinEntryDialog
 * JD-Core Version:    0.7.0.1
 */