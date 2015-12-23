package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.keyczar.enums.KeyPurpose;
import org.keyczar.interfaces.KeyType;
import org.keyczar.util.Util;

public class KeyMetadata
{
  @Expose
  boolean encrypted = false;
  @Expose
  String name = "";
  @Expose
  KeyPurpose purpose = KeyPurpose.TEST;
  @Expose
  KeyType type = DefaultKeyType.TEST;
  protected Map<Integer, KeyVersion> versionMap = new HashMap();
  @Expose
  List<KeyVersion> versions = new ArrayList();
  
  public KeyMetadata() {}
  
  public KeyMetadata(String paramString, KeyPurpose paramKeyPurpose, KeyType paramKeyType)
  {
    this.name = paramString;
    this.purpose = paramKeyPurpose;
    this.type = paramKeyType;
  }
  
  public static KeyMetadata read(String paramString)
  {
    KeyMetadata localKeyMetadata = (KeyMetadata)Util.gson().fromJson(paramString, KeyMetadata.class);
    Iterator localIterator = localKeyMetadata.versions.iterator();
    while (localIterator.hasNext())
    {
      KeyVersion localKeyVersion = (KeyVersion)localIterator.next();
      localKeyMetadata.versionMap.put(Integer.valueOf(localKeyVersion.versionNumber), localKeyVersion);
    }
    return localKeyMetadata;
  }
  
  public final boolean addVersion(KeyVersion paramKeyVersion)
  {
    int i = paramKeyVersion.versionNumber;
    if (!this.versionMap.containsKey(Integer.valueOf(i)))
    {
      this.versionMap.put(Integer.valueOf(i), paramKeyVersion);
      this.versions.add(paramKeyVersion);
      return true;
    }
    return false;
  }
  
  public String toString()
  {
    return Util.gson().toJson(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.KeyMetadata
 * JD-Core Version:    0.7.0.1
 */