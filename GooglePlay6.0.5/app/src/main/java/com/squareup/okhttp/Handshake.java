package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public final class Handshake
{
  final String cipherSuite;
  private final List<Certificate> localCertificates;
  final List<Certificate> peerCertificates;
  
  private Handshake(String paramString, List<Certificate> paramList1, List<Certificate> paramList2)
  {
    this.cipherSuite = paramString;
    this.peerCertificates = paramList1;
    this.localCertificates = paramList2;
  }
  
  public static Handshake get(SSLSession paramSSLSession)
  {
    String str = paramSSLSession.getCipherSuite();
    if (str == null) {
      throw new IllegalStateException("cipherSuite == null");
    }
    try
    {
      Certificate[] arrayOfCertificate3 = paramSSLSession.getPeerCertificates();
      arrayOfCertificate1 = arrayOfCertificate3;
    }
    catch (SSLPeerUnverifiedException localSSLPeerUnverifiedException)
    {
      for (;;)
      {
        Certificate[] arrayOfCertificate2;
        Certificate[] arrayOfCertificate1 = null;
        continue;
        List localList1 = Collections.emptyList();
        continue;
        List localList2 = Collections.emptyList();
      }
    }
    if (arrayOfCertificate1 != null)
    {
      localList1 = Util.immutableList(arrayOfCertificate1);
      arrayOfCertificate2 = paramSSLSession.getLocalCertificates();
      if (arrayOfCertificate2 == null) {
        break label89;
      }
      localList2 = Util.immutableList(arrayOfCertificate2);
      return new Handshake(str, localList1, localList2);
    }
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Handshake)) {}
    Handshake localHandshake;
    do
    {
      return false;
      localHandshake = (Handshake)paramObject;
    } while ((!this.cipherSuite.equals(localHandshake.cipherSuite)) || (!this.peerCertificates.equals(localHandshake.peerCertificates)) || (!this.localCertificates.equals(localHandshake.localCertificates)));
    return true;
  }
  
  public final int hashCode()
  {
    return 31 * (31 * (527 + this.cipherSuite.hashCode()) + this.peerCertificates.hashCode()) + this.localCertificates.hashCode();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Handshake
 * JD-Core Version:    0.7.0.1
 */