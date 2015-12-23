package com.google.android.wallet.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.wallet.analytics.AnalyticsClickListener;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.styleable;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import java.util.List;

public class InfoMessageTextView
  extends AppCompatTextView
  implements UiNode, ClickSpan.OnClickListener, FieldValidatable
{
  private AnalyticsClickListener mAnalyticsClickListener;
  public boolean mExpanded = true;
  private InfoMessageOuterClass.InfoMessage mInfoMessage;
  public boolean mInlineExpandLabel = true;
  private UiNode mParentUiNode;
  private int mRequestedVisibility;
  private final WalletUiElement mUiElement = new WalletUiElement(1627);
  private ClickSpan.OnClickListener mUrlClickListener;
  
  public InfoMessageTextView(Context paramContext)
  {
    super(paramContext, null);
    setVisibility(getVisibility());
  }
  
  public InfoMessageTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
    setVisibility(getVisibility());
  }
  
  public InfoMessageTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes(paramContext, paramAttributeSet);
    setVisibility(getVisibility());
  }
  
  private void onAnalyticsClickEvent(int paramInt)
  {
    if (this.mAnalyticsClickListener != null) {
      this.mAnalyticsClickListener.onAnalyticsClickEvent(this, paramInt);
    }
  }
  
  private void readAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.uicAlwaysInlineExpandLabel;
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(arrayOfInt);
    boolean bool = localTypedArray1.getBoolean(0, false);
    localTypedArray1.recycle();
    if (bool)
    {
      this.mInlineExpandLabel = true;
      return;
    }
    TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletUicInfoMessageTextView);
    this.mInlineExpandLabel = localTypedArray2.getBoolean(R.styleable.WalletUicInfoMessageTextView_internalUicInlineExpandLabel, true);
    localTypedArray2.recycle();
  }
  
  private void updateContent()
  {
    if (this.mInfoMessage == null)
    {
      setText("");
      this.mExpanded = true;
    }
    for (;;)
    {
      setVisibility(this.mRequestedVisibility);
      return;
      if (!TextUtils.isEmpty(this.mInfoMessage.detailedMessageHtml))
      {
        if (this.mExpanded)
        {
          ClickSpan.clickify(this, this.mInfoMessage.detailedMessageHtml, this);
        }
        else if (this.mInlineExpandLabel)
        {
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = this.mInfoMessage.messageHtml;
          arrayOfObject[1] = "expandInfoText";
          arrayOfObject[2] = this.mInfoMessage.showDetailedMessageLabel;
          ClickSpan.clickify(this, String.format("%s <a href=\"%s\">%s</a>", arrayOfObject), this);
        }
        else
        {
          ClickSpan.clickify(this, this.mInfoMessage.messageHtml, this);
        }
      }
      else
      {
        this.mExpanded = true;
        ClickSpan.clickify(this, this.mInfoMessage.messageHtml, this);
      }
    }
  }
  
  public final void expand(boolean paramBoolean)
  {
    if (this.mExpanded != paramBoolean)
    {
      if (paramBoolean) {
        onAnalyticsClickEvent(1628);
      }
      this.mExpanded = paramBoolean;
      updateContent();
    }
  }
  
  public List<UiNode> getChildren()
  {
    return null;
  }
  
  public String getExpandLabel()
  {
    return this.mInfoMessage.showDetailedMessageLabel;
  }
  
  public UiNode getParentUiNode()
  {
    return this.mParentUiNode;
  }
  
  public WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isValid()
  {
    return true;
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if ("expandInfoText".equals(paramString))
    {
      expand(true);
      return;
    }
    onAnalyticsClickEvent(1629);
    this.mUrlClickListener.onClick(this, paramString);
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mInfoMessage = ((InfoMessageOuterClass.InfoMessage)ParcelableProto.getProtoFromBundle(localBundle, "infoMessage"));
      this.mExpanded = localBundle.getBoolean("expanded");
      super.onRestoreInstanceState(localBundle.getParcelable("parentState"));
      updateContent();
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("parentState", super.onSaveInstanceState());
    localBundle.putParcelable("infoMessage", ParcelableProto.forProto(this.mInfoMessage));
    localBundle.putBoolean("expanded", this.mExpanded);
    return localBundle;
  }
  
  public void setAnalyticsClickListener(AnalyticsClickListener paramAnalyticsClickListener)
  {
    this.mAnalyticsClickListener = paramAnalyticsClickListener;
  }
  
  public void setInfoMessage(InfoMessageOuterClass.InfoMessage paramInfoMessage)
  {
    if (paramInfoMessage != null)
    {
      if (TextUtils.isEmpty(paramInfoMessage.messageHtml)) {
        throw new IllegalArgumentException("Info message must contain messageHtml.");
      }
      if (TextUtils.isEmpty(paramInfoMessage.detailedMessageHtml) != TextUtils.isEmpty(paramInfoMessage.showDetailedMessageLabel)) {
        throw new IllegalArgumentException("Info message must either contain both detailedMessageHtml and showDetailedMessageLabel, or neither.");
      }
    }
    this.mInfoMessage = paramInfoMessage;
    this.mExpanded = false;
    updateContent();
  }
  
  public void setParentUiNode(UiNode paramUiNode)
  {
    this.mParentUiNode = paramUiNode;
  }
  
  public void setUrlClickListener(ClickSpan.OnClickListener paramOnClickListener)
  {
    this.mUrlClickListener = paramOnClickListener;
  }
  
  public void setVisibility(int paramInt)
  {
    this.mRequestedVisibility = paramInt;
    if (this.mInfoMessage == null)
    {
      super.setVisibility(8);
      return;
    }
    super.setVisibility(paramInt);
  }
  
  public final boolean validate()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.InfoMessageTextView
 * JD-Core Version:    0.7.0.1
 */