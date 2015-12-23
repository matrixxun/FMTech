package com.google.android.finsky.uninstall;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public final class UninstallManagerSpinnerViewHolder
  extends RecyclerView.ViewHolder
  implements AdapterView.OnItemSelectedListener
{
  private final ArrayAdapter<String> mAdapter;
  SpinnerViewHolderSelectionListener mListener;
  final Spinner mSpinner;
  
  public UninstallManagerSpinnerViewHolder(View paramView, Context paramContext)
  {
    super(paramView);
    this.mSpinner = ((Spinner)paramView.findViewById(2131756185));
    this.mSpinner.setOnItemSelectedListener(this);
    String[] arrayOfString = new String[2];
    Resources localResources = paramContext.getResources();
    arrayOfString[0] = localResources.getString(2131363041);
    arrayOfString[1] = localResources.getString(2131363040);
    this.mAdapter = new ArrayAdapter(paramContext, 17367048, arrayOfString);
    this.mAdapter.setDropDownViewResource(17367049);
    this.mSpinner.setAdapter(this.mAdapter);
  }
  
  public final void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mListener != null) {
      this.mListener.onSpinnerItemSelected(paramInt);
    }
  }
  
  public final void onNothingSelected(AdapterView<?> paramAdapterView) {}
  
  public static abstract interface SpinnerViewHolderSelectionListener
  {
    public abstract void onSpinnerItemSelected(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.uninstall.UninstallManagerSpinnerViewHolder
 * JD-Core Version:    0.7.0.1
 */