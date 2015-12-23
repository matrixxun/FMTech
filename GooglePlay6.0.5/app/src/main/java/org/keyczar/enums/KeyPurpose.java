package org.keyczar.enums;

public enum KeyPurpose
{
  private String name;
  private int value;
  
  static
  {
    TEST = new KeyPurpose("TEST", 4, 127, "test");
    KeyPurpose[] arrayOfKeyPurpose = new KeyPurpose[5];
    arrayOfKeyPurpose[0] = DECRYPT_AND_ENCRYPT;
    arrayOfKeyPurpose[1] = ENCRYPT;
    arrayOfKeyPurpose[2] = SIGN_AND_VERIFY;
    arrayOfKeyPurpose[3] = VERIFY;
    arrayOfKeyPurpose[4] = TEST;
    $VALUES = arrayOfKeyPurpose;
  }
  
  private KeyPurpose(int paramInt, String paramString)
  {
    this.value = paramInt;
    this.name = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.enums.KeyPurpose
 * JD-Core Version:    0.7.0.1
 */