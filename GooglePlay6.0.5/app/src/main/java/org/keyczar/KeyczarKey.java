package org.keyczar;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.Stream;
import org.keyczar.util.Util;

public abstract class KeyczarKey
{
  @Expose
  final int size;
  
  protected KeyczarKey(int paramInt)
  {
    this.size = paramInt;
  }
  
  final void copyHeader(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.put((byte)0);
    paramByteBuffer.put(hash());
  }
  
  public boolean equals(Object paramObject)
  {
    try
    {
      boolean bool = Arrays.equals(((KeyczarKey)paramObject).hash(), hash());
      return bool;
    }
    catch (ClassCastException localClassCastException) {}
    return false;
  }
  
  protected abstract Stream getStream()
    throws KeyczarException;
  
  protected abstract byte[] hash();
  
  public int hashCode()
  {
    return Util.toInt(hash());
  }
  
  public String toString()
  {
    return Util.gson().toJson(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.KeyczarKey
 * JD-Core Version:    0.7.0.1
 */