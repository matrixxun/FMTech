package com.google.android.wallet.instrumentmanager.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import java.util.ArrayList;

public final class DropDownButtonAdapter
  extends ArrayAdapter<DropDownItem>
{
  private final LayoutInflater mInflater;
  
  public DropDownButtonAdapter(Context paramContext, ArrayList<DropDownItem> paramArrayList)
  {
    super(paramContext, 0, paramArrayList);
    this.mInflater = LayoutInflater.from(paramContext);
  }
  
  private View createViewFromResource(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.mInflater.inflate(R.layout.view_drop_down_button, paramViewGroup, false);
    }
    DropDownItem localDropDownItem = (DropDownItem)getItem(paramInt);
    TextView localTextView = (TextView)paramView.findViewById(R.id.dropdown_button_text);
    localTextView.setText(localDropDownItem.text);
    Context localContext = getContext();
    if (localDropDownItem.mIconDrawable == null)
    {
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = localDropDownItem.iconResId;
      TypedArray localTypedArray = localContext.obtainStyledAttributes(arrayOfInt);
      localDropDownItem.mIconDrawable = localTypedArray.getDrawable(0);
      localTypedArray.recycle();
    }
    localTextView.setCompoundDrawablesWithIntrinsicBounds(localDropDownItem.mIconDrawable, null, null, null);
    return paramView;
  }
  
  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return createViewFromResource(paramInt, paramView, paramViewGroup);
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return createViewFromResource(paramInt, paramView, paramViewGroup);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.common.DropDownButtonAdapter
 * JD-Core Version:    0.7.0.1
 */