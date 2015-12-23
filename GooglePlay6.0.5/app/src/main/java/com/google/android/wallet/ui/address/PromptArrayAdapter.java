package com.google.android.wallet.ui.address;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public final class PromptArrayAdapter<T>
  extends ArrayAdapter<T>
{
  private View mHiddenView;
  private TextView mPromptView;
  
  public PromptArrayAdapter(Context paramContext, int paramInt1, int paramInt2, List<T> paramList, T paramT)
  {
    super(paramContext, paramInt1, paramInt2, paramList);
    if (paramT == null) {
      throw new NullPointerException("Prompt object must be non null.");
    }
    insert(paramT, 0);
  }
  
  public final boolean areAllItemsEnabled()
  {
    return false;
  }
  
  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (!isEnabled(paramInt))
    {
      if (this.mHiddenView == null)
      {
        this.mHiddenView = new View(getContext());
        this.mHiddenView.setVisibility(8);
        this.mHiddenView.setLayoutParams(new AbsListView.LayoutParams(0, 0));
      }
      return this.mHiddenView;
    }
    if (paramView == this.mHiddenView) {
      paramView = null;
    }
    return super.getDropDownView(paramInt, paramView, paramViewGroup);
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (!isEnabled(paramInt))
    {
      if (this.mPromptView == null)
      {
        this.mPromptView = ((TextView)super.getView(0, null, paramViewGroup));
        this.mPromptView.setHint(this.mPromptView.getText());
        this.mPromptView.setText(null);
      }
      return this.mPromptView;
    }
    if (paramView == this.mPromptView) {
      paramView = null;
    }
    return super.getView(paramInt, paramView, paramViewGroup);
  }
  
  public final boolean isEnabled(int paramInt)
  {
    return paramInt != 0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.address.PromptArrayAdapter
 * JD-Core Version:    0.7.0.1
 */