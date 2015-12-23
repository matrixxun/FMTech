package com.google.android.libraries.happiness;

public abstract interface HatsSurveyClient
{
  public abstract void onSurveyCanceled();
  
  public abstract void onSurveyComplete(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onSurveyReady();
  
  public abstract void onSurveyResponse(String paramString1, String paramString2);
  
  public abstract void onWindowError();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.happiness.HatsSurveyClient
 * JD-Core Version:    0.7.0.1
 */