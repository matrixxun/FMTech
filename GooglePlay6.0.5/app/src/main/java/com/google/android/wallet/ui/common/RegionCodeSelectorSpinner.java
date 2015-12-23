package com.google.android.wallet.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.android.wallet.common.address.AddressUtils;
import com.google.android.wallet.common.util.ArrayUtils;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;

public class RegionCodeSelectorSpinner
  extends FormSpinner
{
  private OnRegionCodeSelectedListener mOnRegionCodeSelectedListener;
  private ContextThemeWrapper mThemedContext;
  
  public RegionCodeSelectorSpinner(Context paramContext)
  {
    super(paramContext);
    setThemedContext(paramContext);
  }
  
  public RegionCodeSelectorSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setThemedContext(paramContext);
  }
  
  public RegionCodeSelectorSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setThemedContext(paramContext);
  }
  
  private void setThemedContext(Context paramContext)
  {
    if ((paramContext instanceof ContextThemeWrapper))
    {
      this.mThemedContext = ((ContextThemeWrapper)paramContext);
      return;
    }
    throw new IllegalArgumentException("RegionCodeSelectorSpinner must be used with a ContextThemeWrapper");
  }
  
  public int getRegionCodeCount()
  {
    return getCount();
  }
  
  public int getSelectedRegionCode()
  {
    return ((Integer)getSelectedItem()).intValue();
  }
  
  public final boolean isValid()
  {
    return (super.isValid()) && (getSelectedRegionCode() != 0);
  }
  
  public void setRegionCodeSelectedListener(OnRegionCodeSelectedListener paramOnRegionCodeSelectedListener)
  {
    this.mOnRegionCodeSelectedListener = paramOnRegionCodeSelectedListener;
  }
  
  public void setRegionCodes(int[] paramArrayOfInt)
  {
    RegionCodeAdapter localRegionCodeAdapter = new RegionCodeAdapter(this.mThemedContext, R.layout.view_row_spinner, R.id.description, ArrayUtils.toWrapperArray(paramArrayOfInt));
    localRegionCodeAdapter.setDropDownViewResource(R.layout.view_spinner_dropdown);
    setAdapter(localRegionCodeAdapter);
    setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Integer localInteger = (Integer)RegionCodeSelectorSpinner.this.getItemAtPosition(paramAnonymousInt);
        RegionCodeSelectorSpinner.OnRegionCodeSelectedListener localOnRegionCodeSelectedListener;
        int i;
        int j;
        if (RegionCodeSelectorSpinner.this.mOnRegionCodeSelectedListener != null)
        {
          localOnRegionCodeSelectedListener = RegionCodeSelectorSpinner.this.mOnRegionCodeSelectedListener;
          i = localInteger.intValue();
          j = RegionCodeSelectorSpinner.this.getId();
          if (paramAnonymousView == null) {
            break label69;
          }
        }
        label69:
        for (boolean bool = true;; bool = false)
        {
          localOnRegionCodeSelectedListener.onRegionCodeSelected(i, j, bool);
          return;
        }
      }
      
      public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
        Log.i("RegionCodeSelectorSpinn", "Listener fired for onNothingSelected; ignoring");
      }
    });
  }
  
  public void setSelectedRegionCode(int paramInt)
    throws IllegalStateException
  {
    if (getAdapter() == null) {
      throw new IllegalStateException("Populate selector with region codes before setting the selected Region Code");
    }
    if ((paramInt != 0) && (paramInt != getSelectedRegionCode()))
    {
      int i = ((RegionCodeAdapter)getAdapter()).getPosition(Integer.valueOf(paramInt));
      if (i >= 0) {
        setSelection(i);
      }
    }
  }
  
  public static abstract interface OnRegionCodeSelectedListener
  {
    public abstract void onRegionCodeSelected(int paramInt1, int paramInt2, boolean paramBoolean);
  }
  
  static final class RegionCodeAdapter
    extends ArrayAdapter<Integer>
  {
    private int mDropDownResource;
    private final int mFieldId;
    private final LayoutInflater mInflater;
    private final int mResource;
    
    public RegionCodeAdapter(ContextThemeWrapper paramContextThemeWrapper, int paramInt1, int paramInt2, Integer[] paramArrayOfInteger)
    {
      super(paramInt1, paramInt2, paramArrayOfInteger);
      this.mResource = paramInt1;
      this.mFieldId = paramInt2;
      this.mInflater = LayoutInflater.from(paramContextThemeWrapper);
    }
    
    private View createViewFromResource(int paramInt1, View paramView, ViewGroup paramViewGroup, int paramInt2)
    {
      if (paramView == null) {
        paramView = this.mInflater.inflate(paramInt2, paramViewGroup, false);
      }
      Integer localInteger = (Integer)getItem(paramInt1);
      TextView localTextView = (TextView)paramView.findViewById(this.mFieldId);
      if (localInteger.intValue() != 0)
      {
        localTextView.setText(AddressUtils.getDisplayCountryForDefaultLocale(localInteger.intValue()));
        return paramView;
      }
      localTextView.setText(null);
      return paramView;
    }
    
    public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return createViewFromResource(paramInt, paramView, paramViewGroup, this.mDropDownResource);
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return createViewFromResource(paramInt, paramView, paramViewGroup, this.mResource);
    }
    
    public final void setDropDownViewResource(int paramInt)
    {
      super.setDropDownViewResource(paramInt);
      this.mDropDownResource = paramInt;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.RegionCodeSelectorSpinner
 * JD-Core Version:    0.7.0.1
 */