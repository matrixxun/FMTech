package com.google.android.finsky.layout;

public abstract interface ClusterContentConfigurator
{
  public abstract int getChildHeight(float paramFloat1, float paramFloat2, int paramInt);
  
  public abstract float getChildPeekingAmount();
  
  public abstract int getChildVerticalOffset(float paramFloat1, float paramFloat2, int paramInt);
  
  public abstract float getChildWidthMultiplier();
  
  public abstract int getChildWidthPolicy();
  
  public abstract int getClusterHeight(int paramInt, float paramFloat);
  
  public abstract int getFixedChildWidth$255f288(int paramInt);
  
  public abstract float getPrimaryChildAspectRatio(ClusterContentBinder paramClusterContentBinder);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ClusterContentConfigurator
 * JD-Core Version:    0.7.0.1
 */