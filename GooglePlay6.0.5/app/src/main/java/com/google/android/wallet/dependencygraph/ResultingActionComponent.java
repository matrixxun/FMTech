package com.google.android.wallet.dependencygraph;

import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;

public abstract interface ResultingActionComponent
{
  public abstract void applyResultingAction(DependencyGraphOuterClass.ResultingActionReference paramResultingActionReference, DependencyGraphOuterClass.TriggerValueReference[] paramArrayOfTriggerValueReference);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.dependencygraph.ResultingActionComponent
 * JD-Core Version:    0.7.0.1
 */