package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.keyczar.enums.KeyStatus;
import org.keyczar.util.Util;

public final class KeyVersion
{
  @Expose
  private boolean exportable = false;
  @Expose
  KeyStatus status = KeyStatus.ACTIVE;
  @Expose
  int versionNumber = 0;
  
  private KeyVersion() {}
  
  public KeyVersion(KeyStatus paramKeyStatus)
  {
    this.versionNumber = 0;
    this.status = paramKeyStatus;
    this.exportable = false;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof KeyVersion)) {}
    KeyVersion localKeyVersion;
    do
    {
      return false;
      localKeyVersion = (KeyVersion)paramObject;
    } while (this.versionNumber != localKeyVersion.versionNumber);
    return true;
  }
  
  public final int hashCode()
  {
    return this.versionNumber;
  }
  
  public final String toString()
  {
    return Util.gson().toJson(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.KeyVersion
 * JD-Core Version:    0.7.0.1
 */