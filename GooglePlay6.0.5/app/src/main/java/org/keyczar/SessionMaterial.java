package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.keyczar.util.Util;

public final class SessionMaterial
{
  @Expose
  public AesKey key = null;
  @Expose
  public String nonce = "";
  
  public SessionMaterial() {}
  
  public SessionMaterial(AesKey paramAesKey, String paramString)
  {
    this.key = paramAesKey;
    this.nonce = paramString;
  }
  
  public final String toString()
  {
    return Util.gson().toJson(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.SessionMaterial
 * JD-Core Version:    0.7.0.1
 */