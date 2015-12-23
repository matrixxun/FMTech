package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.Base64;
import okio.ByteString;

public final class CertificatePinner
{
  public static final CertificatePinner DEFAULT = new CertificatePinner(new Builder(), (byte)0);
  private final Map<String, List<ByteString>> hostnameToPins;
  
  private CertificatePinner(Builder paramBuilder)
  {
    this.hostnameToPins = Util.immutableMap(paramBuilder.hostnameToPins);
  }
  
  public static String pin(Certificate paramCertificate)
  {
    if (!(paramCertificate instanceof X509Certificate)) {
      throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }
    return "sha1/" + Base64.encode(sha1((X509Certificate)paramCertificate).data);
  }
  
  private static ByteString sha1(X509Certificate paramX509Certificate)
  {
    return Util.sha1(ByteString.of(paramX509Certificate.getPublicKey().getEncoded()));
  }
  
  public final void check(String paramString, List<Certificate> paramList)
    throws SSLPeerUnverifiedException
  {
    List localList = (List)this.hostnameToPins.get(paramString);
    if (localList == null) {
      return;
    }
    int i = 0;
    int j = paramList.size();
    for (;;)
    {
      if (i >= j) {
        break label66;
      }
      if (localList.contains(sha1((X509Certificate)paramList.get(i)))) {
        break;
      }
      i++;
    }
    label66:
    StringBuilder localStringBuilder = new StringBuilder("Certificate pinning failure!\n  Peer certificate chain:");
    int k = 0;
    int m = paramList.size();
    while (k < m)
    {
      X509Certificate localX509Certificate = (X509Certificate)paramList.get(k);
      localStringBuilder.append("\n    ").append(pin(localX509Certificate)).append(": ").append(localX509Certificate.getSubjectDN().getName());
      k++;
    }
    localStringBuilder.append("\n  Pinned certificates for ").append(paramString).append(":");
    int n = 0;
    int i1 = localList.size();
    while (n < i1)
    {
      ByteString localByteString = (ByteString)localList.get(n);
      localStringBuilder.append("\n    sha1/").append(Base64.encode(localByteString.data));
      n++;
    }
    throw new SSLPeerUnverifiedException(localStringBuilder.toString());
  }
  
  public static final class Builder
  {
    final Map<String, List<ByteString>> hostnameToPins = new LinkedHashMap();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.CertificatePinner
 * JD-Core Version:    0.7.0.1
 */