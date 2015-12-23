package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.LibrarySubscriptionEntry;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.SubscriptionDateInfo;
import com.google.android.play.layout.PlayActionButton;

public class SubscriptionView
  extends RelativeLayout
{
  public PlayActionButton mCancelButton;
  public CancelListener mCancelListener;
  public final SubscriptionDateInfo mDateInfo = new SubscriptionDateInfo();
  public View mDescriptionCollapser;
  public View mDescriptionExpander;
  public Document mDocument;
  public int mExpansionState = -1;
  public TextView mSubscriptionDescription;
  public TextView mSubscriptionPrice;
  public TextView mSubscriptionRenewal;
  public TextView mSubscriptionStatus;
  public TextView mSubscriptionTitle;
  
  public SubscriptionView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SubscriptionView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void collapseDescription()
  {
    this.mDescriptionExpander.setVisibility(0);
    this.mSubscriptionDescription.setVisibility(8);
    this.mDescriptionCollapser.setVisibility(8);
  }
  
  public final void expandDescription()
  {
    this.mDescriptionExpander.setVisibility(8);
    this.mSubscriptionDescription.setVisibility(0);
    this.mDescriptionCollapser.setVisibility(0);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSubscriptionTitle = ((TextView)findViewById(2131756137));
    this.mSubscriptionPrice = ((TextView)findViewById(2131756139));
    this.mSubscriptionRenewal = ((TextView)findViewById(2131756140));
    this.mSubscriptionStatus = ((TextView)findViewById(2131756138));
    this.mCancelButton = ((PlayActionButton)findViewById(2131756141));
    this.mSubscriptionDescription = ((TextView)findViewById(2131756143));
    this.mDescriptionExpander = findViewById(2131756142);
    this.mDescriptionCollapser = findViewById(2131756144);
  }
  
  public static abstract interface CancelListener
  {
    public abstract void onCancel(Document paramDocument, LibrarySubscriptionEntry paramLibrarySubscriptionEntry);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SubscriptionView
 * JD-Core Version:    0.7.0.1
 */