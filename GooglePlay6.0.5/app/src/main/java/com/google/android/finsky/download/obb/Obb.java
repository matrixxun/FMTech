package com.google.android.finsky.download.obb;

import java.io.File;

public abstract interface Obb
{
  public abstract boolean finalizeTempFile();
  
  public abstract File getFile();
  
  public abstract String getPackage();
  
  public abstract long getSize();
  
  public abstract int getState();
  
  public abstract File getTempFile();
  
  public abstract String getUrl();
  
  public abstract boolean isFinalized();
  
  public abstract boolean isPatch();
  
  public abstract void syncStateWithStorage();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.obb.Obb
 * JD-Core Version:    0.7.0.1
 */