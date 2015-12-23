package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import java.util.List;

public class FormHeaderView
  extends LinearLayout
{
  private FormHeaderOuterClass.FormHeader mFormHeader;
  private int mRequestedVisibility;
  
  public FormHeaderView(Context paramContext)
  {
    super(paramContext);
    setVisibility(getVisibility());
  }
  
  public FormHeaderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setVisibility(getVisibility());
  }
  
  @TargetApi(11)
  public FormHeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setVisibility(getVisibility());
  }
  
  @TargetApi(21)
  public FormHeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setVisibility(getVisibility());
  }
  
  public final int setFormHeader(FormHeaderOuterClass.FormHeader paramFormHeader, LayoutInflater paramLayoutInflater, ClickSpan.OnClickListener paramOnClickListener, UiNode paramUiNode, List<UiNode> paramList, int paramInt)
  {
    if (!TextUtils.isEmpty(paramFormHeader.title))
    {
      TextView localTextView = (TextView)findViewById(R.id.header_title);
      localTextView.setText(paramFormHeader.title);
      localTextView.setVisibility(0);
    }
    Context localContext = paramLayoutInflater.getContext();
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.internalUicInfoMessageLayout;
    TypedArray localTypedArray = localContext.obtainStyledAttributes(arrayOfInt);
    int i = localTypedArray.getResourceId(0, R.layout.view_info_message_text);
    localTypedArray.recycle();
    InfoMessageOuterClass.InfoMessage[] arrayOfInfoMessage = paramFormHeader.description;
    int j = arrayOfInfoMessage.length;
    int k = 0;
    int n;
    for (int m = paramInt; k < j; m = n)
    {
      InfoMessageOuterClass.InfoMessage localInfoMessage = arrayOfInfoMessage[k];
      InfoMessageTextView localInfoMessageTextView = (InfoMessageTextView)paramLayoutInflater.inflate(i, this, false);
      n = m + 1;
      localInfoMessageTextView.setId(m);
      localInfoMessageTextView.setInfoMessage(localInfoMessage);
      localInfoMessageTextView.setUrlClickListener(paramOnClickListener);
      addView(localInfoMessageTextView);
      localInfoMessageTextView.setParentUiNode(paramUiNode);
      paramList.add(localInfoMessageTextView);
      k++;
    }
    this.mFormHeader = paramFormHeader;
    setVisibility(this.mRequestedVisibility);
    return m;
  }
  
  public void setVisibility(int paramInt)
  {
    this.mRequestedVisibility = paramInt;
    if ((this.mFormHeader == null) || ((TextUtils.isEmpty(this.mFormHeader.title)) && (this.mFormHeader.description.length == 0)))
    {
      super.setVisibility(8);
      return;
    }
    super.setVisibility(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FormHeaderView
 * JD-Core Version:    0.7.0.1
 */