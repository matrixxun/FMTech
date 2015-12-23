package com.google.android.finsky.utils;

public abstract interface PlayCardImageTypeSequence
{
  public static final int[] CORE_IMAGE_TYPES = { 4, 0 };
  public static final int[] PROMO_IMAGE_TYPES = { 2, 4, 0 };
  
  public abstract int[] getImageTypePreference();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PlayCardImageTypeSequence
 * JD-Core Version:    0.7.0.1
 */