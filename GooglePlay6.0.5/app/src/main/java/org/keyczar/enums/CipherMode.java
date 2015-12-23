package org.keyczar.enums;

import com.google.gson.annotations.Expose;

public enum CipherMode
{
  public String jceMode;
  @Expose
  private int value;
  
  static
  {
    DET_CBC = new CipherMode("DET_CBC", 3, 3, "AES/CBC/PKCS5Padding");
    CipherMode[] arrayOfCipherMode = new CipherMode[4];
    arrayOfCipherMode[0] = CBC;
    arrayOfCipherMode[1] = CTR;
    arrayOfCipherMode[2] = ECB;
    arrayOfCipherMode[3] = DET_CBC;
    $VALUES = arrayOfCipherMode;
  }
  
  private CipherMode(int paramInt, String paramString, boolean paramBoolean)
  {
    this.value = paramInt;
    this.jceMode = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.enums.CipherMode
 * JD-Core Version:    0.7.0.1
 */