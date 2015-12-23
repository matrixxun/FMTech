package com.google.android.vending.verifier;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.finsky.activities.SimpleAlertDialog.ConfigurableView;

public class PackageWarningDialogView
  extends ScrollView
  implements View.OnClickListener, SimpleAlertDialog.ConfigurableView
{
  private int mAction;
  private View.OnClickListener mOnContinueAnywayClickListener;
  Bundle mResult = new Bundle();
  
  public PackageWarningDialogView(Context paramContext)
  {
    super(paramContext);
  }
  
  public PackageWarningDialogView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configureView(Bundle paramBundle)
  {
    String str1 = paramBundle.getString("warning_message");
    String str2 = paramBundle.getString("app_name");
    this.mAction = paramBundle.getInt("action");
    TextView localTextView1 = (TextView)findViewById(2131755233);
    TextView localTextView2 = (TextView)findViewById(2131755799);
    if (!TextUtils.isEmpty(str1)) {
      localTextView1.setText(str1);
    }
    if (!TextUtils.isEmpty(str2)) {
      localTextView2.setText(str2);
    }
    TextView localTextView3 = (TextView)findViewById(2131755804);
    int i;
    label114:
    int j;
    label164:
    TextView localTextView4;
    if ((this.mAction == 2) || (this.mAction == 3))
    {
      i = 1;
      if (i == 0) {
        break label213;
      }
      localTextView3.setText(2131362484);
      findViewById(2131755801).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (this.val$detailsView.getVisibility() != 0)
          {
            this.val$detailsView.setVisibility(0);
            this.val$detailsArrow.setImageResource(2130837793);
            return;
          }
          this.val$detailsView.setVisibility(8);
          this.val$detailsArrow.setImageResource(2130837795);
        }
      });
      if ((this.mAction != 0) && (this.mAction != 2)) {
        break label223;
      }
      j = 1;
      if (j != 0)
      {
        localTextView4 = (TextView)findViewById(2131755805);
        if (this.mAction != 0) {
          break label229;
        }
        localTextView4.setText(2131362479);
      }
    }
    for (;;)
    {
      localTextView4.setVisibility(0);
      localTextView4.setOnClickListener(this);
      return;
      i = 0;
      break;
      label213:
      localTextView3.setText(2131362483);
      break label114;
      label223:
      j = 0;
      break label164;
      label229:
      if (this.mAction == 2) {
        localTextView4.setText(2131362477);
      }
    }
  }
  
  public int getAction()
  {
    return this.mAction;
  }
  
  public Bundle getResult()
  {
    return this.mResult;
  }
  
  public void onClick(View paramView)
  {
    this.mResult.putBoolean("dont_warn", true);
    if (this.mOnContinueAnywayClickListener != null) {
      this.mOnContinueAnywayClickListener.onClick(paramView);
    }
  }
  
  public void setOnContinueAnywayClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mOnContinueAnywayClickListener = paramOnClickListener;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageWarningDialogView
 * JD-Core Version:    0.7.0.1
 */