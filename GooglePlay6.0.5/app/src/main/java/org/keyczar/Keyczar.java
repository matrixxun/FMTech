package org.keyczar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.enums.KeyStatus;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.i18n.Messages;
import org.keyczar.interfaces.EncryptedReader;
import org.keyczar.interfaces.KeyType;
import org.keyczar.interfaces.KeyType.Builder;
import org.keyczar.interfaces.KeyczarReader;
import org.keyczar.util.Util;

public abstract class Keyczar
{
  public static final byte[] FORMAT_BYTES = { 0 };
  private static final Logger LOG = Logger.getLogger(Keyczar.class);
  final HashMap<KeyHash, KeyczarKey> hashMap = new HashMap();
  final KeyMetadata kmd;
  KeyVersion primaryVersion;
  final HashMap<KeyVersion, KeyczarKey> versionMap = new HashMap();
  
  public Keyczar(KeyczarReader paramKeyczarReader)
    throws KeyczarException
  {
    this.kmd = KeyMetadata.read(paramKeyczarReader.getMetadata());
    if (!isAcceptablePurpose(this.kmd.purpose))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.kmd.purpose;
      throw new KeyczarException(Messages.getString("Keyczar.UnacceptablePurpose", arrayOfObject));
    }
    if ((this.kmd.encrypted) && (!(paramKeyczarReader instanceof EncryptedReader))) {
      throw new KeyczarException(Messages.getString("Keyczar.NeedEncryptedReader", new Object[0]));
    }
    Iterator localIterator = this.kmd.versions.iterator();
    while (localIterator.hasNext())
    {
      KeyVersion localKeyVersion = (KeyVersion)localIterator.next();
      if (localKeyVersion.status == KeyStatus.PRIMARY)
      {
        if (this.primaryVersion != null) {
          throw new KeyczarException(Messages.getString("Keyczar.SinglePrimary", new Object[0]));
        }
        this.primaryVersion = localKeyVersion;
      }
      KeyType localKeyType = this.kmd.type;
      String str = paramKeyczarReader.getKey(localKeyVersion.versionNumber);
      KeyczarKey localKeyczarKey = localKeyType.getBuilder().read(str);
      LOG.debug(Messages.getString("Keyczar.ReadVersion", new Object[] { localKeyVersion }));
      this.hashMap.put(new KeyHash(localKeyczarKey.hash(), (byte)0), localKeyczarKey);
      this.versionMap.put(localKeyVersion, localKeyczarKey);
    }
  }
  
  final KeyczarKey getPrimaryKey()
  {
    if (this.primaryVersion == null) {
      return null;
    }
    return (KeyczarKey)this.versionMap.get(this.primaryVersion);
  }
  
  abstract boolean isAcceptablePurpose(KeyPurpose paramKeyPurpose);
  
  public String toString()
  {
    return this.kmd.toString();
  }
  
  private final class KeyHash
  {
    private byte[] data;
    
    private KeyHash(byte[] paramArrayOfByte)
    {
      if (paramArrayOfByte.length != 4) {
        throw new IllegalArgumentException();
      }
      this.data = paramArrayOfByte;
    }
    
    public final boolean equals(Object paramObject)
    {
      return ((paramObject instanceof KeyHash)) && (paramObject.hashCode() == hashCode());
    }
    
    public final int hashCode()
    {
      return Util.toInt(this.data);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.Keyczar
 * JD-Core Version:    0.7.0.1
 */