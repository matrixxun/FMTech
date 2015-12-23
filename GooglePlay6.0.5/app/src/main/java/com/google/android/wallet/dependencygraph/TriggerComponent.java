package com.google.android.wallet.dependencygraph;

import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import java.util.ArrayList;

public abstract interface TriggerComponent
{
  public abstract void addTriggers(ArrayList<DependencyGraphOuterClass.TriggerValueReference> paramArrayList);
  
  public abstract boolean checkTrigger(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference);
  
  public abstract void setTriggerListener(TriggerListener paramTriggerListener);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.dependencygraph.TriggerComponent
 * JD-Core Version:    0.7.0.1
 */