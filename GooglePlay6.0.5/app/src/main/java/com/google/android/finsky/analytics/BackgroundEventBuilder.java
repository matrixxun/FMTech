package com.google.android.finsky.analytics;

import android.text.TextUtils;

public final class BackgroundEventBuilder
{
  public final PlayStore.PlayStoreBackgroundActionEvent event = FinskyEventLog.obtainPlayStoreBackgroundActionEvent();
  
  public BackgroundEventBuilder(int paramInt)
  {
    this.event.type = paramInt;
    this.event.hasType = true;
  }
  
  public final BackgroundEventBuilder setAppData(PlayStore.AppData paramAppData)
  {
    this.event.appData = paramAppData;
    return this;
  }
  
  public final BackgroundEventBuilder setAttempts(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.event.attempts = paramInt;
      this.event.hasAttempts = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setAuthContext(PlayStore.AuthContext paramAuthContext)
  {
    this.event.authContext = paramAuthContext;
    return this;
  }
  
  public final BackgroundEventBuilder setBillingProfileFlow(int paramInt)
  {
    if (this.event.billingProfileData == null) {
      this.event.billingProfileData = new PlayStore.BillingProfileData();
    }
    this.event.billingProfileData.flow = paramInt;
    return this;
  }
  
  public final BackgroundEventBuilder setCallingPackage(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.event.callingPackage = paramString;
      this.event.hasCallingPackage = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setClientLatencyMs(long paramLong)
  {
    if (paramLong >= 0L)
    {
      this.event.clientLatencyMs = paramLong;
      this.event.hasClientLatencyMs = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setDocument(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.event.document = paramString;
      this.event.hasDocument = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setErrorCode(int paramInt)
  {
    if (paramInt != 0)
    {
      this.event.errorCode = paramInt;
      this.event.hasErrorCode = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setExceptionType(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.event.exceptionType = paramString;
      this.event.hasExceptionType = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setExceptionType(Throwable paramThrowable)
  {
    if (paramThrowable != null) {
      setExceptionType(paramThrowable.getClass().getSimpleName());
    }
    return this;
  }
  
  public final BackgroundEventBuilder setExternalReferrer(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.event.externalReferrer = paramString;
      this.event.hasExternalReferrer = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setFromSetting(Integer paramInteger)
  {
    if (paramInteger != null)
    {
      this.event.fromSetting = paramInteger.intValue();
      this.event.hasFromSetting = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setOfferType(int paramInt)
  {
    if (paramInt != 0)
    {
      this.event.offerType = paramInt;
      this.event.hasOfferType = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setOperationSuccess(boolean paramBoolean)
  {
    this.event.operationSuccess = paramBoolean;
    this.event.hasOperationSuccess = true;
    return this;
  }
  
  public final BackgroundEventBuilder setReason(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.event.reason = paramString;
      this.event.hasReason = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setSearchSuggestionReport(PlayStore.SearchSuggestionReport paramSearchSuggestionReport)
  {
    this.event.searchSuggestionReport = paramSearchSuggestionReport;
    return this;
  }
  
  public final BackgroundEventBuilder setServerLatencyMs(long paramLong)
  {
    if (paramLong >= 0L)
    {
      this.event.serverLatencyMs = paramLong;
      this.event.hasServerLatencyMs = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setServerLogsCookie(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      this.event.serverLogsCookie = paramArrayOfByte;
      this.event.hasServerLogsCookie = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setToSetting(Integer paramInteger)
  {
    if (paramInteger != null)
    {
      this.event.toSetting = paramInteger.intValue();
      this.event.hasToSetting = true;
    }
    return this;
  }
  
  public final BackgroundEventBuilder setWearInfo(PlayStore.WearInfo paramWearInfo)
  {
    this.event.wearInfo = paramWearInfo;
    return this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.analytics.BackgroundEventBuilder
 * JD-Core Version:    0.7.0.1
 */