package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;

public final class Challenge
{
  public final String realm;
  public final String scheme;
  
  public Challenge(String paramString1, String paramString2)
  {
    this.scheme = paramString1;
    this.realm = paramString2;
  }
  
  public final boolean equals(Object paramObject)
  {
    return ((paramObject instanceof Challenge)) && (Util.equal(this.scheme, ((Challenge)paramObject).scheme)) && (Util.equal(this.realm, ((Challenge)paramObject).realm));
  }
  
  public final int hashCode()
  {
    if (this.realm != null) {}
    for (int i = this.realm.hashCode();; i = 0)
    {
      int j = 31 * (i + 899);
      String str = this.scheme;
      int k = 0;
      if (str != null) {
        k = this.scheme.hashCode();
      }
      return j + k;
    }
  }
  
  public final String toString()
  {
    return this.scheme + " realm=\"" + this.realm + "\"";
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Challenge
 * JD-Core Version:    0.7.0.1
 */