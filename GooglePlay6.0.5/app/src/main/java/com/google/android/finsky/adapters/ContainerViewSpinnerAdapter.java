package com.google.android.finsky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.android.finsky.protos.Containers.ContainerView;

public final class ContainerViewSpinnerAdapter
  extends ArrayAdapter<Containers.ContainerView>
{
  public ContainerViewSpinnerAdapter(Context paramContext, Containers.ContainerView[] paramArrayOfContainerView)
  {
    super(paramContext, 17367049, paramArrayOfContainerView);
  }
  
  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = LayoutInflater.from(getContext()).inflate(17367049, paramViewGroup, false);
    }
    Containers.ContainerView localContainerView = (Containers.ContainerView)getItem(paramInt);
    ((TextView)paramView.findViewById(16908308)).setText(localContainerView.title);
    return paramView;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = LayoutInflater.from(getContext()).inflate(2130969093, paramViewGroup, false);
    Containers.ContainerView localContainerView = (Containers.ContainerView)getItem(paramInt);
    ((TextView)localView).setText(localContainerView.title);
    return localView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.ContainerViewSpinnerAdapter
 * JD-Core Version:    0.7.0.1
 */