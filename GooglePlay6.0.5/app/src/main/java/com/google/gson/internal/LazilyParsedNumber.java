package com.google.gson.internal;

import java.math.BigInteger;

public final class LazilyParsedNumber
  extends Number
{
  private final String value;
  
  public LazilyParsedNumber(String paramString)
  {
    this.value = paramString;
  }
  
  public final double doubleValue()
  {
    return Double.parseDouble(this.value);
  }
  
  public final float floatValue()
  {
    return Float.parseFloat(this.value);
  }
  
  public final int intValue()
  {
    try
    {
      int i = Integer.parseInt(this.value);
      return i;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        long l = Long.parseLong(this.value);
        return (int)l;
      }
      catch (NumberFormatException localNumberFormatException2) {}
    }
    return new BigInteger(this.value).intValue();
  }
  
  public final long longValue()
  {
    try
    {
      long l = Long.parseLong(this.value);
      return l;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return new BigInteger(this.value).longValue();
  }
  
  public final String toString()
  {
    return this.value;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.LazilyParsedNumber
 * JD-Core Version:    0.7.0.1
 */