package com.google.android.gms.internal;

import java.io.IOException;

public final class zzaij
  extends IOException
{
  private zzaij(String paramString)
  {
    super(paramString);
  }
  
  static zzaij zzPa()
  {
    return new zzaij("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
  }
  
  static zzaij zzPb()
  {
    return new zzaij("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
  }
  
  static zzaij zzPc()
  {
    return new zzaij("CodedInputStream encountered a malformed varint.");
  }
  
  static zzaij zzPd()
  {
    return new zzaij("Protocol message contained an invalid tag (zero).");
  }
  
  static zzaij zzPe()
  {
    return new zzaij("Protocol message end-group tag did not match expected tag.");
  }
  
  static zzaij zzPf()
  {
    return new zzaij("Protocol message tag had invalid wire type.");
  }
  
  static zzaij zzPg()
  {
    return new zzaij("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaij
 * JD-Core Version:    0.7.0.1
 */