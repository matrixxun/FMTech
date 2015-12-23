package org.keyczar.enums;

public enum KeyStatus
{
  private String name;
  private int value;
  
  static
  {
    ACTIVE = new KeyStatus("ACTIVE", 1, 1, "active");
    INACTIVE = new KeyStatus("INACTIVE", 2, 2, "inactive");
    KeyStatus[] arrayOfKeyStatus = new KeyStatus[3];
    arrayOfKeyStatus[0] = PRIMARY;
    arrayOfKeyStatus[1] = ACTIVE;
    arrayOfKeyStatus[2] = INACTIVE;
    $VALUES = arrayOfKeyStatus;
  }
  
  private KeyStatus(int paramInt, String paramString)
  {
    this.value = paramInt;
    this.name = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.enums.KeyStatus
 * JD-Core Version:    0.7.0.1
 */