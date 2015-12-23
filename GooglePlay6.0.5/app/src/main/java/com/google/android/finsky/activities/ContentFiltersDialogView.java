package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.android.finsky.adapters.ContentFilterChoiceAdapter;
import com.google.android.finsky.config.ContentFiltersUtils;
import com.google.android.finsky.config.ContentFiltersUtils.ContentFilterSelection;
import com.google.android.finsky.protos.ContentFilters.FilterRange;
import com.google.android.finsky.utils.FinskyLog;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;

public class ContentFiltersDialogView
  extends LinearLayout
  implements AdapterView.OnItemClickListener, SimpleAlertDialog.ConfigurableView
{
  private ContentFilters.FilterRange mFilterRange;
  private ListView mListView;
  private View.OnClickListener mOnDoneListener;
  private int mSelectedIndex;
  private ContentFiltersUtils.ContentFilterSelection[] mSelections;
  
  public ContentFiltersDialogView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ContentFiltersDialogView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configureView(Bundle paramBundle)
  {
    this.mSelections = ContentFiltersUtils.decodeSelections(paramBundle.getString("ContentFiltersDialogView.contentFilterSelections"));
    byte[] arrayOfByte = paramBundle.getByteArray("ContentFiltersDialogView.encodedFilterRange");
    try
    {
      this.mFilterRange = ContentFilters.FilterRange.parseFrom(arrayOfByte);
      this.mListView.setAdapter(new ContentFilterChoiceAdapter(getContext(), this.mSelections, this.mFilterRange));
      this.mListView.setOnItemClickListener(this);
      return;
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      FinskyLog.wtf(localInvalidProtocolBufferNanoException, "Cannot parse FilterRange proto from byte[] in arguments.", new Object[0]);
    }
  }
  
  public Bundle getResult()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("ContentFiltersDialogView.selectedChoiceIndex", this.mSelectedIndex);
    return localBundle;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mListView = ((ListView)findViewById(2131755353));
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mSelectedIndex = paramInt;
    this.mOnDoneListener.onClick(paramView);
  }
  
  public void setOnPositiveClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mOnDoneListener = paramOnClickListener;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ContentFiltersDialogView
 * JD-Core Version:    0.7.0.1
 */