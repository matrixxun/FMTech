package com.google.android.finsky.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import com.google.android.finsky.api.model.Document;
import java.util.List;

public abstract interface Notifier
{
  public abstract void hideAllMessagesForPackage(String paramString);
  
  public abstract void hideInstallingMessage();
  
  public abstract void hidePackageRemoveRequestMessage(String paramString);
  
  public abstract void hidePackageRemovedMessage(String paramString);
  
  public abstract void hideUpdatesAvailableMessage();
  
  public abstract void setNotificationListener(NotificationListener paramNotificationListener);
  
  public abstract void showDownloadErrorMessage(String paramString1, String paramString2, int paramInt, String paramString3, boolean paramBoolean);
  
  public abstract void showExternalStorageFull(String paramString1, String paramString2);
  
  public abstract void showExternalStorageMissing(String paramString1, String paramString2);
  
  public abstract void showHarmfulAppRemoveRequestMessage(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, int paramInt);
  
  public abstract void showHarmfulAppRemovedMessage(String paramString1, String paramString2, String paramString3, int paramInt);
  
  public abstract void showInstallationFailureMessage(String paramString1, String paramString2, int paramInt);
  
  public abstract void showInstallingMessage(String paramString1, String paramString2, boolean paramBoolean);
  
  public abstract void showInternalStorageFull(String paramString1, String paramString2);
  
  public abstract void showMaliciousAssetRemovedMessage$16da05f7(String paramString);
  
  public abstract void showMessage$14e1ec6d(String paramString1, String paramString2);
  
  public abstract void showNewUpdatesAvailableMessage(List<Document> paramList, int paramInt);
  
  public abstract void showNormalAssetRemovedMessage(String paramString1, String paramString2);
  
  public abstract void showNotification(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, Intent paramIntent, String paramString5);
  
  public abstract void showOutstandingUpdatesMessage(List<Document> paramList);
  
  public abstract void showPreregistrationReleasedMessage(Document paramDocument, String paramString, Bitmap paramBitmap);
  
  public abstract void showPurchaseErrorMessage(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract void showSubscriptionsWarningMessage(String paramString1, String paramString2, String paramString3);
  
  public abstract void showSuccessfulInstallMessage(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
  
  public abstract void showUpdatesNeedApprovalMessage(List<Document> paramList, int paramInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Notifier
 * JD-Core Version:    0.7.0.1
 */