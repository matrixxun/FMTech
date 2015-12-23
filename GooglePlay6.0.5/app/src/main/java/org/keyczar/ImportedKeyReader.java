package org.keyczar;

import java.util.ArrayList;
import java.util.List;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.enums.KeyStatus;
import org.keyczar.interfaces.KeyczarReader;

public final class ImportedKeyReader
  implements KeyczarReader
{
  private final List<KeyczarKey> keys;
  private final KeyMetadata metadata = new KeyMetadata("Imported AES", KeyPurpose.DECRYPT_AND_ENCRYPT, DefaultKeyType.AES);
  
  public ImportedKeyReader(AesKey paramAesKey)
  {
    KeyVersion localKeyVersion = new KeyVersion(KeyStatus.PRIMARY);
    this.metadata.addVersion(localKeyVersion);
    this.keys = new ArrayList();
    this.keys.add(paramAesKey);
  }
  
  public final String getKey(int paramInt)
  {
    return ((KeyczarKey)this.keys.get(paramInt)).toString();
  }
  
  public final String getMetadata()
  {
    return this.metadata.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.ImportedKeyReader
 * JD-Core Version:    0.7.0.1
 */