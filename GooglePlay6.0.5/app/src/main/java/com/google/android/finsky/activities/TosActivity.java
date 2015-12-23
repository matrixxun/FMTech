package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.TosUtil;
import com.google.android.finsky.utils.Utils;

public class TosActivity
  extends AppCompatActivity
  implements ButtonBar.ClickListener
{
  private String mAccount = null;
  private ButtonBar mButtonBar;
  private CheckBox mEmailOptIn;
  private DfeToc mToc = null;
  
  private void setupView(Boolean paramBoolean)
  {
    setContentView(2130969133);
    this.mButtonBar = ((ButtonBar)findViewById(2131755300));
    this.mButtonBar.setPositiveButtonTitle(2131361811);
    this.mButtonBar.setNegativeButtonTitle(2131362077);
    this.mButtonBar.setClickListener(this);
    ((TextView)findViewById(2131755219)).setText(Utils.getDisplayAccountName(this.mAccount, getApplicationContext()));
    TextView localTextView = (TextView)findViewById(2131755214);
    localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    localTextView.setText(Html.fromHtml(this.mToc.mToc.tosContent));
    this.mEmailOptIn = ((CheckBox)findViewById(2131756160));
    String str = this.mToc.mToc.tosCheckboxTextMarketingEmails;
    if ((TextUtils.isEmpty(str)) || (paramBoolean == null))
    {
      this.mEmailOptIn.setVisibility(8);
      return;
    }
    this.mEmailOptIn.setText(str);
    this.mEmailOptIn.setChecked(paramBoolean.booleanValue());
    this.mEmailOptIn.setVisibility(0);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null) {}
    for (Bundle localBundle = paramBundle;; localBundle = getIntent().getExtras())
    {
      if (localBundle != null)
      {
        this.mAccount = localBundle.getString("finsky.TosActivity.account");
        this.mToc = ((DfeToc)localBundle.getParcelable("finsky.TosActivity.toc"));
      }
      if ((this.mAccount != null) && (this.mToc != null)) {
        break;
      }
      FinskyLog.w("Bad request to Terms of Service activity.", new Object[0]);
      finish();
      return;
    }
    if (TextUtils.isEmpty(this.mToc.mToc.tosCheckboxTextMarketingEmails))
    {
      setupView(null);
      return;
    }
    setupView(Boolean.valueOf(this.mToc.hasCurrentUserPreviouslyOptedIn()));
  }
  
  public final void onNegativeButtonClick()
  {
    setResult(0);
    finish();
  }
  
  public final void onPositiveButtonClick()
  {
    int i = this.mEmailOptIn.getVisibility();
    Boolean localBoolean = null;
    if (i == 0) {
      localBoolean = Boolean.valueOf(this.mEmailOptIn.isChecked());
    }
    TosUtil.ackTos(this.mAccount, this.mToc, localBoolean);
    setResult(-1);
    finish();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("finsky.TosActivity.account", this.mAccount);
    paramBundle.putParcelable("finsky.TosActivity.toc", this.mToc);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.TosActivity
 * JD-Core Version:    0.7.0.1
 */