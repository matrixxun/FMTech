package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.util.Arrays;
import java.util.List;

public final class ConnectionSpec
{
  public static final ConnectionSpec CLEARTEXT = new Builder(false, (byte)0).build();
  public static final ConnectionSpec COMPATIBLE_TLS;
  public static final ConnectionSpec MODERN_TLS;
  final String[] cipherSuites;
  ConnectionSpec supportedSpec;
  final boolean supportsTlsExtensions;
  public final boolean tls;
  final String[] tlsVersions;
  
  static
  {
    Builder localBuilder1 = new Builder(true, (byte)0);
    CipherSuite[] arrayOfCipherSuite = new CipherSuite[18];
    arrayOfCipherSuite[0] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[1] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[2] = CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[3] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[4] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[5] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[6] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[7] = CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA;
    arrayOfCipherSuite[8] = CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA;
    arrayOfCipherSuite[9] = CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[10] = CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[11] = CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[12] = CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[13] = CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[14] = CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[15] = CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA;
    arrayOfCipherSuite[16] = CipherSuite.TLS_RSA_WITH_RC4_128_SHA;
    arrayOfCipherSuite[17] = CipherSuite.TLS_RSA_WITH_RC4_128_MD5;
    Builder localBuilder2 = localBuilder1.cipherSuites(arrayOfCipherSuite);
    TlsVersion[] arrayOfTlsVersion1 = new TlsVersion[3];
    arrayOfTlsVersion1[0] = TlsVersion.TLS_1_2;
    arrayOfTlsVersion1[1] = TlsVersion.TLS_1_1;
    arrayOfTlsVersion1[2] = TlsVersion.TLS_1_0;
    Builder localBuilder3 = localBuilder2.tlsVersions(arrayOfTlsVersion1);
    if (!localBuilder3.tls) {
      throw new IllegalStateException("no TLS extensions for cleartext connections");
    }
    localBuilder3.supportsTlsExtensions = true;
    MODERN_TLS = localBuilder3.build();
    Builder localBuilder4 = new Builder(MODERN_TLS);
    TlsVersion[] arrayOfTlsVersion2 = new TlsVersion[1];
    arrayOfTlsVersion2[0] = TlsVersion.TLS_1_0;
    COMPATIBLE_TLS = localBuilder4.tlsVersions(arrayOfTlsVersion2).build();
  }
  
  private ConnectionSpec(Builder paramBuilder)
  {
    this.tls = paramBuilder.tls;
    this.cipherSuites = paramBuilder.cipherSuites;
    this.tlsVersions = paramBuilder.tlsVersions;
    this.supportsTlsExtensions = paramBuilder.supportsTlsExtensions;
  }
  
  private List<CipherSuite> cipherSuites()
  {
    CipherSuite[] arrayOfCipherSuite = new CipherSuite[this.cipherSuites.length];
    for (int i = 0; i < this.cipherSuites.length; i++) {
      arrayOfCipherSuite[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
    }
    return Util.immutableList(arrayOfCipherSuite);
  }
  
  private List<TlsVersion> tlsVersions()
  {
    TlsVersion[] arrayOfTlsVersion = new TlsVersion[this.tlsVersions.length];
    for (int i = 0; i < this.tlsVersions.length; i++) {
      arrayOfTlsVersion[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
    }
    return Util.immutableList(arrayOfTlsVersion);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ConnectionSpec)) {}
    ConnectionSpec localConnectionSpec;
    do
    {
      return false;
      localConnectionSpec = (ConnectionSpec)paramObject;
    } while ((this.tls != localConnectionSpec.tls) || ((this.tls) && ((!Arrays.equals(this.cipherSuites, localConnectionSpec.cipherSuites)) || (!Arrays.equals(this.tlsVersions, localConnectionSpec.tlsVersions)) || (this.supportsTlsExtensions != localConnectionSpec.supportsTlsExtensions))));
    return true;
  }
  
  public final int hashCode()
  {
    int i = 17;
    int j;
    if (this.tls)
    {
      j = 31 * (31 * (527 + Arrays.hashCode(this.cipherSuites)) + Arrays.hashCode(this.tlsVersions));
      if (!this.supportsTlsExtensions) {
        break label51;
      }
    }
    label51:
    for (int k = 0;; k = 1)
    {
      i = j + k;
      return i;
    }
  }
  
  public final String toString()
  {
    if (this.tls) {
      return "ConnectionSpec(cipherSuites=" + cipherSuites() + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
    }
    return "ConnectionSpec()";
  }
  
  public static final class Builder
  {
    String[] cipherSuites;
    boolean supportsTlsExtensions;
    boolean tls;
    String[] tlsVersions;
    
    public Builder(ConnectionSpec paramConnectionSpec)
    {
      this.tls = paramConnectionSpec.tls;
      this.cipherSuites = paramConnectionSpec.cipherSuites;
      this.tlsVersions = paramConnectionSpec.tlsVersions;
      this.supportsTlsExtensions = paramConnectionSpec.supportsTlsExtensions;
    }
    
    private Builder(boolean paramBoolean)
    {
      this.tls = paramBoolean;
    }
    
    public final ConnectionSpec build()
    {
      return new ConnectionSpec(this, (byte)0);
    }
    
    public final Builder cipherSuites(CipherSuite... paramVarArgs)
    {
      if (!this.tls) {
        throw new IllegalStateException("no cipher suites for cleartext connections");
      }
      String[] arrayOfString = new String[paramVarArgs.length];
      for (int i = 0; i < paramVarArgs.length; i++) {
        arrayOfString[i] = paramVarArgs[i].javaName;
      }
      this.cipherSuites = arrayOfString;
      return this;
    }
    
    public final Builder tlsVersions(TlsVersion... paramVarArgs)
    {
      if (!this.tls) {
        throw new IllegalStateException("no TLS versions for cleartext connections");
      }
      String[] arrayOfString = new String[paramVarArgs.length];
      for (int i = 0; i < paramVarArgs.length; i++) {
        arrayOfString[i] = paramVarArgs[i].javaName;
      }
      this.tlsVersions = arrayOfString;
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.ConnectionSpec
 * JD-Core Version:    0.7.0.1
 */