package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

public class PurchaseAuthActivity
  extends AppCompatActivity
  implements View.OnClickListener
{
  private Button mCancelButton;
  private int mCurrentPurchaseAuthEnum;
  
  private void setupRadioButton(int paramInt1, final int paramInt2)
  {
    RadioButton localRadioButton = (RadioButton)findViewById(paramInt1);
    if (paramInt2 == this.mCurrentPurchaseAuthEnum) {}
    for (boolean bool = true;; bool = false)
    {
      localRadioButton.setChecked(bool);
      localRadioButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PurchaseAuthActivity.access$000(PurchaseAuthActivity.this, paramInt2);
          PurchaseAuthActivity.this.finish();
        }
      });
      return;
    }
  }
  
  public void onClick(View paramView)
  {
    if (paramView == this.mCancelButton) {
      finish();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130969030);
    this.mCurrentPurchaseAuthEnum = getIntent().getIntExtra("purchase-auth-current", -1);
    setupRadioButton(2131755142, 2);
    setupRadioButton(2131755979, 1);
    setupRadioButton(2131755145, 0);
    this.mCancelButton = ((Button)findViewById(2131755980));
    this.mCancelButton.setOnClickListener(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.PurchaseAuthActivity
 * JD-Core Version:    0.7.0.1
 */