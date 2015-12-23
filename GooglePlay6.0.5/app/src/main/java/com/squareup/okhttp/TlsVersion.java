package com.squareup.okhttp;

public enum TlsVersion
{
  final String javaName;
  
  static
  {
    TLS_1_1 = new TlsVersion("TLS_1_1", 1, "TLSv1.1");
    TLS_1_0 = new TlsVersion("TLS_1_0", 2, "TLSv1");
    SSL_3_0 = new TlsVersion("SSL_3_0", 3, "SSLv3");
    TlsVersion[] arrayOfTlsVersion = new TlsVersion[4];
    arrayOfTlsVersion[0] = TLS_1_2;
    arrayOfTlsVersion[1] = TLS_1_1;
    arrayOfTlsVersion[2] = TLS_1_0;
    arrayOfTlsVersion[3] = SSL_3_0;
    $VALUES = arrayOfTlsVersion;
  }
  
  private TlsVersion(String paramString)
  {
    this.javaName = paramString;
  }
  
  static TlsVersion forJavaName(String paramString)
  {
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        throw new IllegalArgumentException("Unexpected TLS version: " + paramString);
        if (paramString.equals("TLSv1.2"))
        {
          i = 0;
          continue;
          if (paramString.equals("TLSv1.1"))
          {
            i = 1;
            continue;
            if (paramString.equals("TLSv1"))
            {
              i = 2;
              continue;
              if (paramString.equals("SSLv3")) {
                i = 3;
              }
            }
          }
        }
        break;
      }
    }
    return TLS_1_2;
    return TLS_1_1;
    return TLS_1_0;
    return SSL_3_0;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.TlsVersion
 * JD-Core Version:    0.7.0.1
 */