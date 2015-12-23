package com.google.android.wallet.common.address;

import java.util.Arrays;
import java.util.HashSet;

public final class AddressField
{
  private static final char[] ALL_ADDRESS_FIELDS = { 83, 67, 78, 79, 49, 50, 51, 68, 90, 88, 65, 85, 70, 80, 84, 66, 82 };
  private static final HashSet<Character> sFields = new HashSet(ALL_ADDRESS_FIELDS.length);
  
  static
  {
    for (char c : ALL_ADDRESS_FIELDS) {
      sFields.add(Character.valueOf(c));
    }
  }
  
  public static int count()
  {
    return ALL_ADDRESS_FIELDS.length;
  }
  
  public static boolean exists(char paramChar)
  {
    return sFields.contains(Character.valueOf(paramChar));
  }
  
  public static char[] values()
  {
    return Arrays.copyOf(ALL_ADDRESS_FIELDS, ALL_ADDRESS_FIELDS.length);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.AddressField
 * JD-Core Version:    0.7.0.1
 */