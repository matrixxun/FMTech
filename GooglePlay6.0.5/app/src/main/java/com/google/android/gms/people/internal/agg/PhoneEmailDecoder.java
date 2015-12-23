package com.google.android.gms.people.internal.agg;

import android.os.Bundle;
import java.util.regex.Pattern;

public abstract class PhoneEmailDecoder<T>
{
  public static EmailDecoder DummyEmailDecoder = new EmailDecoder(Bundle.EMPTY);
  public static PhoneDecoder DummyPhoneDecoder = new PhoneDecoder(Bundle.EMPTY);
  private final char zzbze;
  private final char zzbzf;
  private final String zzbzg;
  private final String zzbzh;
  private final Bundle zzbzi;
  
  PhoneEmailDecoder(Bundle paramBundle)
  {
    this.zzbzi = paramBundle;
    this.zzbze = '\001';
    this.zzbzf = '\002';
    this.zzbzg = Pattern.quote(String.valueOf(this.zzbze));
    this.zzbzh = Pattern.quote(String.valueOf(this.zzbzf));
  }
  
  public static final class EmailDecoder
    extends PhoneEmailDecoder<Object>
  {
    public EmailDecoder(Bundle paramBundle)
    {
      super();
    }
  }
  
  public static final class PhoneDecoder
    extends PhoneEmailDecoder<Object>
  {
    public PhoneDecoder(Bundle paramBundle)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.agg.PhoneEmailDecoder
 * JD-Core Version:    0.7.0.1
 */