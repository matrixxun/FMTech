package org.keyczar;

import org.apache.log4j.Logger;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.DecryptingStream;
import org.keyczar.interfaces.KeyczarReader;

public class Crypter
  extends Encrypter
{
  private static final Logger LOG = Logger.getLogger(Crypter.class);
  private final StreamCache<DecryptingStream> CRYPT_CACHE = new StreamCache();
  
  public Crypter(KeyczarReader paramKeyczarReader)
    throws KeyczarException
  {
    super(paramKeyczarReader);
  }
  
  final boolean isAcceptablePurpose(KeyPurpose paramKeyPurpose)
  {
    return paramKeyPurpose == KeyPurpose.DECRYPT_AND_ENCRYPT;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.Crypter
 * JD-Core Version:    0.7.0.1
 */