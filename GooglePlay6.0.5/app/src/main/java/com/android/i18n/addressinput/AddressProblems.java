package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;

public final class AddressProblems
{
  public Map<AddressField, AddressProblemType> mProblems = new HashMap();
  
  public final boolean isEmpty()
  {
    return this.mProblems.isEmpty();
  }
  
  public final String toString()
  {
    return this.mProblems.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressProblems
 * JD-Core Version:    0.7.0.1
 */