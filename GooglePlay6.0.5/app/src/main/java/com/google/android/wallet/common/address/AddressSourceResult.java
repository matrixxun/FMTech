package com.google.android.wallet.common.address;

import android.text.TextUtils;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.Comparator;

public final class AddressSourceResult
{
  public static final Comparator<AddressSourceResult> DEFAULT_COMPARATOR = new Comparator() {};
  public static final char[] EXCLUDED_ADDRESS_FIELDS = { 'R' };
  public static final String NEW_LINE_REPLACEMENT_SEPARATOR = null;
  public final Postaladdress.PostalAddress address;
  public final CharSequence description;
  public final String matchingTerm;
  public final String reference;
  public final String sourceName;
  
  public AddressSourceResult(String paramString1, Postaladdress.PostalAddress paramPostalAddress, CharSequence paramCharSequence, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2)) {
      throw new IllegalArgumentException("source name should not be empty");
    }
    this.matchingTerm = paramString1;
    this.address = paramPostalAddress;
    this.description = paramCharSequence;
    this.sourceName = paramString2;
    this.reference = null;
  }
  
  public AddressSourceResult(String paramString1, CharSequence paramCharSequence, String paramString2, String paramString3)
  {
    if (TextUtils.isEmpty(paramString2)) {
      throw new IllegalArgumentException("source name should not be empty");
    }
    this.matchingTerm = paramString1;
    this.address = null;
    this.description = paramCharSequence;
    this.sourceName = paramString2;
    this.reference = paramString3;
  }
  
  public AddressSourceResult(String paramString1, String paramString2)
  {
    this(paramString1, paramString1, paramString2, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.AddressSourceResult
 * JD-Core Version:    0.7.0.1
 */