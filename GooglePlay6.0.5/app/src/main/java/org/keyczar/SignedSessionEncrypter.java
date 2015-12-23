package org.keyczar;

import java.util.concurrent.atomic.AtomicReference;

public final class SignedSessionEncrypter
{
  public final Encrypter encrypter;
  public AtomicReference<SessionMaterial> session = new AtomicReference();
  public final Signer signer;
  
  public SignedSessionEncrypter(Encrypter paramEncrypter, Signer paramSigner)
  {
    this.encrypter = paramEncrypter;
    this.signer = paramSigner;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.SignedSessionEncrypter
 * JD-Core Version:    0.7.0.1
 */